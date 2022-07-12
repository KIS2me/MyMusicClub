package music.service.impl;

import music.domain.Song;
import music.mapper.SongMapper;
import music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌曲service实现类
 */
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song) > 0;
    }

    @Override
    public boolean update(Song song) {
        return songMapper.update(song) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id) > 0;
    }

    @Override
    public Song selectById(Integer id) {
        return songMapper.selectById(id);
    }

    @Override
    public List<Song> selectAllSong() {
        return songMapper.selectAllSong();
    }

    @Override
    public List<Song> selectLikeName(String name) {
        return songMapper.selectLikeName(name);
    }

    @Override
    public List<Song> selectBySingerId(Integer singerId) {
        return songMapper.selectBySingerId(singerId);
    }
}
