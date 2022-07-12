package music.service;

import music.domain.Comment;

import java.util.List;

/**
 * 评论service接口
 */
public interface CommentService {
    /**
     * 增加
     */
    boolean insert(Comment comment);

    /**
     * 修改
     */
    boolean update(Comment comment);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据id查询
     */
    Comment selectById(Integer id);

    /**
     * 查询所有评论
     */
    List<Comment> selectAllComment();

    /**
     * 查询某首歌曲下的所有评论
     */
    List<Comment> selectBySongId(Integer songId);

    /**
     * 查询某个歌单下的所有评论
     */
    List<Comment> selectBySongListId(Integer songListId);
}
