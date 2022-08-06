package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Singer;
import music.domain.SongList;
import music.service.SongListService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 歌单控制类
 */
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加歌单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String title = request.getParameter("title").trim(); //删除字符串的头尾空白符
        String pic = request.getParameter("pic").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        //修改歌单
        boolean flag = songListService.insert(songList);
        if(flag) {
            //新增歌单时，需要将歌手数据添加到缓存中
            String key1 = "songList_all";
            redisTemplate.opsForList().rightPush(key1, songList);

            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "添加成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "添加失败");
        }

        return jsonObject;
    }

    /**
     * 更新歌单
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        //获取前端传来的参数
        String id = request.getParameter("id").trim();
        String title = request.getParameter("title").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        //修改歌单
        boolean flag = songListService.update(songList);
        if(flag) {
            //若修改歌单成功，则需要删除Redis中原来的缓存
            Set keys = redisTemplate.keys("songList_all");
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
     * 删除歌单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        //删除歌单
        boolean flag = songListService.delete(Integer.parseInt(id));

        if(flag) {
            //若修改歌单成功，则需要删除Redis中原来的缓存
            Set keys = redisTemplate.keys("songList_all");
            redisTemplate.delete(keys);
        }

        //前端不取json格式数据
        return flag;
    }

    /**
     * 根据id查询歌单
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public Object selectById(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        return songListService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有歌单
     */
    @RequestMapping(value = "/selectAllSongList", method = RequestMethod.GET)
    public Object selectAllSongList(HttpServletRequest request) {
        List<SongList> list = null;
        String key = "songList_all";

        //先从Redis中获取数据
        list = redisTemplate.opsForList().range(key, 0, -1); //获取list中的全部数据

        //若Redis中数据不为空，则直接返回
        if(list.size() != 0) {
            return list;
        }

        //否则从数据库中获取数据
        list = songListService.selectAllSongList();

        //将数据库中查询到的数据写入Redis
        for(SongList songList : list) {
            redisTemplate.opsForList().rightPush(key, songList);
        }

        //给Redis数据设置过期时间
        redisTemplate.opsForList().set(key, 60, TimeUnit.MINUTES);

        return list;
    }

    /**
     * 根据标题模糊查询列表
     */
    @RequestMapping(value = "/selectLikeTitle", method = RequestMethod.GET)
    public Object selectLikeTitle(HttpServletRequest request) {
        String title = request.getParameter("title").trim();

        return songListService.selectLikeTitle(title);
    }

    /**
     * 根据标题精确查询歌单
     */
    @RequestMapping(value = "/selectByTitle", method = RequestMethod.GET)
    public Object selectByTitle(HttpServletRequest request) {
        String title = request.getParameter("title").trim();

        return songListService.selectByTitle(title);
    }

    /**
     * 根据风格模糊查询歌单
     */
    @RequestMapping(value = "/selectLikeStyle", method = RequestMethod.GET)
    public Object selectLikeStyle(HttpServletRequest request) {
        String style = request.getParameter("style").trim();

        return songListService.selectLikeStyle(style);
    }

    /**
     * 更新歌单图片
     *
     * MultipartFile是SpringMVC提供简化上传操作的工具类，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
     */
    @RequestMapping(value = "/updateSongListPic", method = RequestMethod.POST)
    public Object updateSongListPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();

        //若MultipartFile对象不存在，说明没有文件，更新失败
        if(avatorFile.isEmpty()) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传失败");
            return jsonObject;
        }

        //新文件名=当前时间毫秒值+原文件名，避免相同文件名重名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //被存储文件的父文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + "img" + System.getProperty("file.separator") + "songListPic";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储到数据库中的文件的相对地址
        String storeAvatorPath = "/img/songListPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            //更新头像
            boolean flag = songListService.update(songList);

            //更新成功
            if(flag) {
                //若修改歌单成功，则需要删除Redis中原来的缓存
                Set keys = redisTemplate.keys("songList_all");
                redisTemplate.delete(keys);

                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                //传回存储的地址
                jsonObject.put("pic", storeAvatorPath);
            }else {
                jsonObject.put(Constants.CODE, 0);
                jsonObject.put(Constants.MSG, "更新失败");
            }
        } catch(IOException e) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "文件上传错误" + e.getMessage());
        }

        return jsonObject;
    }
}
