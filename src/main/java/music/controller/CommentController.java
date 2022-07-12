package music.controller;

import com.alibaba.fastjson.JSONObject;
import music.domain.Comment;
import music.service.CommentService;
import music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论控制类
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addComment(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String userId = request.getParameter("userId");
        String songId = request.getParameter("songId");
        String songListId = request.getParameter("songListId");
        String content = request.getParameter("content").trim();
        String type = request.getParameter("type");
        String up = request.getParameter("up").trim();

        //保存到Comment对象中
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setContent(content);
        comment.setType(Byte.parseByte(type));
        comment.setUp(Integer.parseInt(up));
        //type=0表示歌曲，type=1表示歌单
        if(Byte.parseByte(type) == 0) {
            comment.setSongId(Integer.parseInt(songId));
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }

        //添加到数据库
        boolean flag = commentService.insert(comment);
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
     * 点赞
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public Object updateComment(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String id = request.getParameter("id").trim();
        String up = request.getParameter("up").trim();

        //保存到Comment对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));

        //更新点赞数
        boolean flag = commentService.update(comment);
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
     * 删除评论
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteComment(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        //删除歌手
        boolean flag = commentService.delete(Integer.parseInt(id));

        //前端不取json格式数据
        return flag;
    }

    /**
     * 根据id查询评论
     */
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public Object selectById(HttpServletRequest request) {
        String id = request.getParameter("id").trim();

        return commentService.selectById(Integer.parseInt(id));
    }

    /**
     * 查询所有评论
     */
    @RequestMapping(value = "/selectAllComment", method = RequestMethod.GET)
    public Object selectAllComment(HttpServletRequest request) {
        return commentService.selectAllComment();
    }

    /**
     * 查询某首歌曲下的所有评论
     */
    @RequestMapping(value = "/selectBySongId", method = RequestMethod.GET)
    public Object selectBySongId(HttpServletRequest request) {
        String songId = request.getParameter("songId").trim();

        return commentService.selectBySongId(Integer.parseInt(songId));
    }

    /**
     * 查询某个歌单下的所有评论
     */
    @RequestMapping(value = "/selectBySongListId", method = RequestMethod.GET)
    public Object selectBySongListId(HttpServletRequest request) {
        String songListId = request.getParameter("songListId").trim();

        return commentService.selectBySongListId(Integer.parseInt(songListId));
    }
}
