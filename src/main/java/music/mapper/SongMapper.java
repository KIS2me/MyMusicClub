package music.mapper;

import music.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌曲dao
 */
@Repository
public interface SongMapper {
    /**
     * 增加
     */
    int insert(Song song);

    /**
     * 修改
     */
    int update(Song song);

    /**
     * 删除
     */
    int delete(Integer id);

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
