package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Singer;
import music.service.SingerService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 歌手控制类
 */
@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加歌手
     */
    @PostMapping(value = "/add")
    public Object addSinger(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String name = request.getParameter("name").trim(); //删除字符串的头尾空白符
        String sex = request.getParameter("sex").trim();
        String pic = request.getParameter("pic").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();

        //日期类型的转换
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        try {
            birthday = dateFormat.parse(birth);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        //保存到Singer对象中
        Singer singer = new Singer();
        singer.setName(name);
        singer.setSex(Byte.parseByte(sex));
        singer.setPic(pic);
        singer.setBirth(birthday);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        //添加到数据库
        boolean flag = singerService.insert(singer);

        if(flag) {
            //新增歌手时，需要将歌手数据添加到缓存中
            String key1 = "singer_all";
            redisTemplate.opsForList().rightPush(key1, singer);

            String key2 = "singer_" + sex;
            redisTemplate.opsForList().rightPush(key2, singer);

            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "添加成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "添加失败");
        }

        return jsonObject;
    }

    /**
     * 修改歌手
     */
    @PostMapping(value = "/update")
    public Object updateSinger(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();

        //生日类型的转换
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        try {
            birthday = dateFormat.parse(birth);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        //保存到Singer对象中
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setSex(Byte.parseByte(sex));
        singer.setBirth(birthday);
        singer.setLocation(location);
        singer.setIntroduction(introduction);

        //修改歌手
        boolean flag = singerService.update(singer);
        if(flag) {
            //若修改歌手成功，则需要删除Redis中原来的缓存
            Set keys = redisTemplate.keys("*singer*"); //因为没自定义序列化器，所以需要在前面也加*号
            redisTemplate.delete(keys);

            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "更新成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }

        return jsonObject;
    }

    /**
     * 删除歌手
     */
    @GetMapping(value = "/delete")
    public Object deleteSinger(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        //删除歌手
        boolean flag = singerService.delete(Integer.parseInt(id));

        if(flag) {
            //若删除歌手成功，则需要删除Redis中原来的缓存
            Set keys = redisTemplate.keys("*singer*"); //因为没自定义序列化器，所以需要在前面也加*号
            redisTemplate.delete(keys);
        }

        //前端不取json格式数据
        return flag;
    }

    /**
     * 根据id查询歌手
     */
    @GetMapping(value = "/selectById")
    public Object selectById(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        return singerService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有歌手
     */
    @GetMapping(value = "/selectAllSinger")
    public Object selectAllSinger(HttpServletRequest request) {
        List<Singer> list = null;
        String key = "singer_all";

        //先从Redis中获取数据
        list = redisTemplate.opsForList().range(key, 0, -1); //获取list中的全部数据

        //若Redis中数据不为空，则直接返回
        if(list.size() != 0) {
            return list;
        }

        //否则从数据库中获取数据
        list = singerService.selectAllSinger();

        //将数据库中查询到的数据写入Redis
        for(Singer singer : list) {
            redisTemplate.opsForList().rightPush(key, singer);
        }

        //给Redis数据设置过期时间
        redisTemplate.opsForList().set(key, 60, TimeUnit.MINUTES);

        return list;
    }

    /**
     * 分页查询歌手
     */
    @GetMapping(value = "/selectSingerForPage")
    public Object selectSingerForPage(HttpServletRequest request) {
        String page = request.getParameter("currentPage").trim();
        return singerService.selectSingerForPage(Integer.parseInt(page) - 1);
    }

    /**
     * 根据名字模糊查询歌手
     */
    @GetMapping(value = "/selectLikeName")
    public Object selectLikeName(HttpServletRequest request) {
        String name = request.getParameter("name");

        return singerService.selectLikeName(name);
    }

    /**
     * 根据性别查询歌手
     */
    @GetMapping(value = "/selectBySex")
    public Object selectBySex(HttpServletRequest request) {
        String sex = request.getParameter("sex").trim();

        List<Singer> list = null;
        String key = "singer_" + sex;

        //先从Redis中获取数据
        list = redisTemplate.opsForList().range(key, 0, -1); //获取list中的全部数据

        //若Redis中数据不为空，则直接返回
        if(list.size() != 0) {
            return list;
        }

        //否则从数据库中获取数据
        list = singerService.selectBySex(Byte.parseByte(sex));

        //将数据库中查询到的数据写入Redis
        for(Singer singer : list) {
            redisTemplate.opsForList().rightPush(key, singer);
        }

        //给Redis数据设置过期时间
        redisTemplate.opsForList().set(key, 60, TimeUnit.MINUTES);

        return list;
    }

    /**
     * 更新歌手头像图片
     *
     * MultipartFile是SpringMVC提供简化上传操作的工具类，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
     */
    @PostMapping(value = "/updateSingerPic")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) throws IOException {
        JSONObject jsonObject = new JSONObject();

        //若MultipartFile对象不存在，说明没有文件，更新失败
        if(avatorFile.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传失败");
            return jsonObject;
        }

        //文件名=当前时间毫秒值+原文件名，避免相同文件名重名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //被存储文件的父文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "singerPic";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储数据库中的相对文件地址
        String storeAvatorPath = "/img/singerPic/" + fileName;
        //将收到的文件传输到dest给定的目标文件中
        avatorFile.transferTo(dest);

        Singer singer = new Singer();
        singer.setId(id);
        singer.setPic(storeAvatorPath);
        //更新头像
        boolean flag = singerService.update(singer);

        //更新成功
        if(flag) {
            //若更新歌手成功，则需要删除Redis中原来的缓存
            Set keys = redisTemplate.keys("*singer*"); //因为没自定义序列化器，所以需要在前面也加*号
            redisTemplate.delete(keys);

            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "更新成功");
            //传回存储的地址
            jsonObject.put("pic", storeAvatorPath);
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }

        return jsonObject;
    }
}
