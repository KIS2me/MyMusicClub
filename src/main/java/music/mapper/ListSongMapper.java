package music.mapper;

import music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单里的歌曲dao
 */
@Repository
public interface ListSongMapper {
    /**
     * 增加
     */
    int insert(ListSong listSong);

    /**
     * 修改
     */
    int update(ListSong listSong);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 根据歌曲id和歌单id删除
     */
    public int deleteBySongIdAndSongListId(Integer songId, Integer songListId);

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
