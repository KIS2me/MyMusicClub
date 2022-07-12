package music.service;

import music.domain.Collect;

import java.util.List;

/**
 * 收藏service接口
 */
public interface CollectService {
    /**
     *增加
     */
    boolean insert(Collect collect);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 删除某个用户收藏的歌曲
     */
    boolean deleteByUserIdAndSongId(Integer userId, Integer songId);

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
    boolean isSongExist(Integer userId, Integer songId);
}
