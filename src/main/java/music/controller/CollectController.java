package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Collect;
import music.service.CollectService;
import music.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 收藏控制类
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService CollectService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加收藏
     */
    @PostMapping(value = "/add")
    public Object addCollect(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String songId = request.getParameter("songId");

        if(songId == null || songId.equals("")) {
            jsonObject.put(Constants.CODE, 0);
            jsonObject.put(Constants.MSG, "收藏歌曲为空");
            return jsonObject;
        }
        if(CollectService.isSongExist(Integer.parseInt(userId), Integer.parseInt(songId))) {
            jsonObject.put(Constants.CODE, 2);
            jsonObject.put(Constants.MSG,"已收藏");
            return jsonObject;
        }

        //保存到收藏的对象中
        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        collect.setType(new Byte(type));
        collect.setSongId(Integer.parseInt(songId));

        // boolean flag = CollectService.insert(collect);
        // if(flag) {
        //     jsonObject.put(Constants.CODE, 1);
        //     jsonObject.put(Constants.MSG, "收藏成功");
        // }else {
        //     jsonObject.put(Constants.CODE,0);
        //     jsonObject.put(Constants.MSG,"收藏失败");
        // }

        //将Collect对象发到消息队列中
        rabbitTemplate.convertAndSend("collect-exchange", "collect.create", collect);

        jsonObject.put(Constants.CODE, 1);
        jsonObject.put(Constants.MSG, "收藏成功");

        return jsonObject;
    }

    /**
     * 删除收藏
     */
    @GetMapping(value = "/delete")
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id");
        boolean flag = CollectService.delete(Integer.parseInt(id));

        return flag;
    }

    /**
     * 删除某个用户收藏的歌曲
     */
    @GetMapping(value = "/deleteByUserIdAndSongId")
    public Object deleteByUserIdAndSongId(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String songId = request.getParameter("songId");
        boolean flag = CollectService.deleteByUserIdAndSongId(Integer.parseInt(userId), Integer.parseInt(songId));

        return flag;
    }

    /**
     * 查询所有收藏
     */
    @GetMapping(value = "/selectAllCollect")
    public Object selectAllCollect(HttpServletRequest request){
        return CollectService.selectAllCollect();
    }

    /**
     * 查询某个用户的收藏列表
     */
    @GetMapping(value = "/selectByUserId")
    public Object selectByUserId(HttpServletRequest request){
        String userId = request.getParameter("userId");
        return CollectService.selectByUserId(Integer.parseInt(userId));
    }
}






















