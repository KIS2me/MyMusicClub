package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Admin;
import music.service.AdminService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * 管理员控制类
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 判断管理员是否登录成功
     */
    @PostMapping(value = "/login")
    public Object login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(); //传回前端的是json类型的数据

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //验证信息
        Admin admin = adminService.login(username);

        //判断是否登录成功
        //1、若没有结果则登录失败
        if(admin == null) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户名错误，请重新输入");
            return jsonObject;
        }
        //2、密码比对，若不一致则登录失败
        if(!admin.getPassword().equals(password)) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "密码错误，请重新输入");
            return jsonObject;
        }
        //3、若管理员账号被禁用，则登录失败
        if(admin.getStatus() == 0) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "用户被禁用，请重新输入");
            return jsonObject;
        }

        //4、登录成功
        request.getSession().setAttribute("admin", admin.getId());
        jsonObject.put(Constants.CODE, 1);
        jsonObject.put(Constants.MSG, "登录成功");
        return jsonObject;
    }

    /**
     * 管理员退出登录
     */
    @PostMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        request.getSession().removeAttribute("admin");
        jsonObject.put(Constants.CODE, 1);
        jsonObject.put(Constants.MSG, "用户已退出");
        return jsonObject;
    }
}
