package music.service.impl;

import music.domain.Singer;
import music.mapper.SingerMapper;
import music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌手service实现类
 */
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;

    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer) > 0;
    }

    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id) > 0;
    }

    @Override
    public Singer selectById(Integer id) {
        return singerMapper.selectById(id);
    }

    @Override
    public List<Singer> selectAllSinger() {
        return singerMapper.selectAllSinger();
    }

    @Override
    public List<Singer> selectLikeName(String name) {
        return singerMapper.selectLikeName(name);
    }

    @Override
    public List<Singer> selectBySex(Byte sex) {
        return singerMapper.selectBySex(sex);
    }

    @Override
    public List<Singer> selectSingerForPage(int page) {
        return singerMapper.selectSingerForPage(page);
    }
}
