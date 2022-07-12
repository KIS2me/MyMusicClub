package music.service;

import music.domain.Singer;

import java.util.List;

/**
 * 歌手service接口
 */
public interface SingerService {
    /**
     * 增加
     */
    boolean insert(Singer singer);

    /**
     * 修改
     */
    boolean update(Singer singer);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据id查询
     */
    Singer selectById(Integer id);

    /**
     * 查询所有歌手
     */
    List<Singer> selectAllSinger();

    /**
     * 根据歌手名字模糊查询列表
     */
    List<Singer> selectLikeName(String name);

    /**
     * 根据性别查询
     */
    List<Singer> selectBySex(Byte sex);

    /**
     * 分页查询歌手
     */
    List<Singer> selectSingerForPage(int page);
}
