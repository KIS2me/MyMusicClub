package music.service;

import music.domain.Song;

import java.util.List;

/**
 * 歌曲service接口
 */
public interface SongService {
    /**
     * 增加
     */
    boolean insert(Song song);

    /**
     * 修改
     */
    boolean update(Song song);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据id查询
     */
    Song selectById(Integer id);

    /**
     * 查询所有歌曲
     */
    List<Song> selectAllSong();

    /**
     * 根据歌名模糊查询
     */
    List<Song> selectLikeName(String name);

    /**
     * 根据歌手id查询歌曲
     */
    List<Song> selectBySingerId(Integer singerId);
}
