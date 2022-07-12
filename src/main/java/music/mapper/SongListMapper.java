package music.mapper;

import music.domain.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单dao
 */
@Repository
public interface SongListMapper {
    /**
     * 增加
     */
    int insert(SongList songList);

    /**
     * 修改
     */
    int update(SongList songList);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 根据id查询
     */
    SongList selectById(Integer id);

    /**
     * 查询所有歌单
     */
    List<SongList> selectAllSongList();

    /**
     * 根据标题模糊查询列表
     */
    List<SongList> selectLikeTitle(String title);

    /**
     * 根据标题精确查询列表
     */
    List<SongList> selectByTitle(String title);

    /**
     * 根据风格模糊查询列表
     */
    List<SongList> selectLikeStyle(String style);
}
