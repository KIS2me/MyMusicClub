package music.mapper;

import music.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论dao
 */
@Repository
public interface CommentMapper {
    /**
     * 增加
     */
    int insert(Comment comment);

    /**
     * 修改
     */
    int update(Comment comment);

    /**
     * 删除
     */
    int delete(Integer id);

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
