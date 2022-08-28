package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Collect;
import music.service.CollectService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 收藏控制类
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService CollectService;

    /**
     * 添加收藏
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
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
            jsonObject.put(Constants.CODE,2);
            jsonObject.put(Constants.MSG,"已收藏");
            return jsonObject;
        }

        //保存到收藏的对象中
        Collect Collect = new Collect();
        Collect.setUserId(Integer.parseInt(userId));
        Collect.setType(new Byte(type));
        Collect.setSongId(Integer.parseInt(songId));

        boolean flag = CollectService.insert(Collect);
        if(flag) {
            jsonObject.put(Constants.CODE, 1);
            jsonObject.put(Constants.MSG, "收藏成功");
        }else {
            jsonObject.put(Constants.CODE,0);
            jsonObject.put(Constants.MSG,"收藏失败");
        }

        return jsonObject;
    }

    /**
     * 删除收藏
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id");
        boolean flag = CollectService.delete(Integer.parseInt(id));

        return flag;
    }

    /**
     * 删除某个用户收藏的歌曲
     */
    @RequestMapping(value = "/deleteByUserIdAndSongId",method = RequestMethod.GET)
    public Object deleteByUserIdAndSongId(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String songId = request.getParameter("songId");
        boolean flag = CollectService.deleteByUserIdAndSongId(Integer.parseInt(userId), Integer.parseInt(songId));

        return flag;
    }

    /**
     * 查询所有收藏
     */
    @RequestMapping(value = "/selectAllCollect",method = RequestMethod.GET)
    public Object selectAllCollect(HttpServletRequest request){
        return CollectService.selectAllCollect();
    }

    /**
     * 查询某个用户的收藏列表
     */
    @RequestMapping(value = "/selectByUserId",method = RequestMethod.GET)
    public Object selectByUserId(HttpServletRequest request){
        String userId = request.getParameter("userId");
        return CollectService.selectByUserId(Integer.parseInt(userId));
    }
}






















