package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Consumer;
import music.service.ConsumerService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /**
     * 添加用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String username = request.getParameter("username").trim(); //删除字符串的头尾空白符
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        String avator = request.getParameter("avator").trim();

        if(username == null || username.equals("")) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名不能为空");
            return jsonObject;
        }
        //判断用户名是否重复
        if(consumerService.selectByUsername(username) != null) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名已被占用");
            return jsonObject;
        }
        if(password == null || password.equals("")) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码不能为空");
            return jsonObject;
        }

        //生日类型的转换
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        try {
            birthday = dateFormat.parse(birth);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        //保存到Consumer对象z中
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.parseByte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthday);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);

        //添加到数据库
        boolean flag = consumerService.insert(consumer);

        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "添加成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "添加失败");
        }

        return jsonObject;
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim(); //删除字符串的头尾空白符
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();

        //生日类型的转换
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        try {
            birthday = dateFormat.parse(birth);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        //保存到Consumer对象中
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Byte.parseByte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthday);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);

        //修改歌手
        boolean flag = consumerService.update(consumer);
        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "更新成功");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "更新失败");
        }

        return jsonObject;
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        //删除歌手
        boolean flag = consumerService.delete(Integer.parseInt(id));

        //前端不取json格式数据
        return flag;
    }

    /**
     * 根据id查询用户
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public Object selectById(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        return consumerService.selectById(Integer.parseInt(id));
    }

    /**
     * 验证密码
     */
    @RequestMapping(value = "/verifyPassword", method = RequestMethod.GET)
    public Object verifyPassword(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        boolean flag = consumerService.verifyPassword(username, password);
        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "密码正确");
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码错误");
        }

        return jsonObject;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        if(username == null || username.equals("")) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名不能为空");
        }
        if(password == null || password.equals("")) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码不能为空");
        }

        boolean flag = consumerService.verifyPassword(username, password);
        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "登录成功");
            jsonObject.put("userMsg", consumerService.selectByUsername(username));
        }else {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "登录失败");
        }

        return jsonObject;
    }

    /**
     * 查询所有用户
     */
    @RequestMapping(value = "/selectAllConsumer", method = RequestMethod.GET)
    public Object selectAllConsumer(HttpServletRequest request) {
        return consumerService.selectAllConsumer();
    }

    /**
     * 根据名字查询用户
     */
    @RequestMapping(value = "/selectByUsername", method = RequestMethod.GET)
    public Object selectByName(HttpServletRequest request) {
        String username = request.getParameter("username").trim();

        return consumerService.selectByUsername(username);
    }

    /**
     * 更新用户头像图片
     *
     * MultipartFile是SpringMVC提供简化上传操作的工具类，SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
     */
    @RequestMapping(value = "/updateConsumerPic", method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
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
                + "img" + System.getProperty("file.separator") + "consumerPic";
        //若父文件路径不存在，则新建文件夹
        File file = new File(filePath);
        if(!file.exists()) {
            file.mkdir();
        }
        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") +  fileName);
        //存储数据库中的相对文件地址
        String storeAvatorPath = "/img/consumerPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            //更新头像
            boolean flag = consumerService.update(consumer);

            //更新成功
            if(flag) {
                jsonObject.put(Constants.CODE, 1);
                jsonObject.put(Constants.MSG, "更新成功");
                //传回存储的地址
                jsonObject.put("avator", storeAvatorPath);
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
