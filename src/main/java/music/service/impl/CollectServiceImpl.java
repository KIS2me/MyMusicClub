package music.service.impl;

import music.domain.Collect;
import music.mapper.CollectMapper;
import music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收藏service实现类
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;


    /**
     * 增加
     */
    @Override
    @Transactional
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect) > 0;
    }

    /**
     * 删除
     */
    @Override
    @Transactional
    public boolean delete(Integer id) {
        return collectMapper.delete(id) > 0;
    }

    /**
     * 删除某个用户收藏的歌曲
     */
    @Override
    @Transactional
    public boolean deleteByUserIdAndSongId(Integer userId, Integer songId) {
        return collectMapper.deleteByUserIdAndSongId(userId, songId) > 0;
    }

    /**
     * 查询所有收藏
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Collect> selectAllCollect() {
        return collectMapper.selectAllCollect();
    }

    /**
     * 查询某个用户的收藏列表
     */
    @Override
    @Transactional
    public List<Collect> selectByUserId(Integer userId) {
        return collectMapper.selectByUserId(userId);
    }

    /**
     * 查询某个用户是否已经收藏了某个歌曲
     */
    @Override
    @Transactional
    public boolean isSongExist(Integer userId, Integer songId) {
        return collectMapper.isSongExist(userId,songId) > 0;
    }
}
