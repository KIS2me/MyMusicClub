package music.service;

import music.domain.ListSong;

import java.util.List;

/**
 * 歌单里的歌曲service
 */
public interface ListSongService {
    /**
     * 增加
     */
    boolean insert(ListSong listSong);

    /**
     * 修改
     */
    boolean update(ListSong listSong);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据歌曲id和歌单id删除
     */
    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId);

    /**
     * 根据id查询
     */
    ListSong selectById(Integer id);

    /**
     * 查询歌单里的所有歌曲
     */
    List<ListSong> selectAllListSong();

    /**
     * 根据歌单id查询歌曲
     */
    List<ListSong> selectBySongListId(Integer songListId);
}
