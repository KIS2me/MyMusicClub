package music.mapper;

import music.domain.Collect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏Dao
 */
@Repository
public interface CollectMapper {
    /**
     *增加
     */
    int insert(Collect collect);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 删除某个用户收藏的歌曲
     */
    int deleteByUserIdAndSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

    /**
     * 查询所有收藏
     */
    List<Collect> selectAllCollect();

    /**
     * 查询某个用户的收藏列表
     */
    List<Collect> selectByUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏了某个歌曲
     */
    int isSongExist(@Param("userId") Integer userId, @Param("songId") Integer songId);
}
















