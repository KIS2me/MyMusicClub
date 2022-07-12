package music.service.impl;

import music.domain.SongList;
import music.mapper.SongListMapper;
import music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单service实现类
 */
@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id) > 0;
    }

    @Override
    public SongList selectById(Integer id) {
        return songListMapper.selectById(id);
    }

    @Override
    public List<SongList> selectAllSongList() {
        return songListMapper.selectAllSongList();
    }

    @Override
    public List<SongList> selectLikeTitle(String title) {
        return songListMapper.selectLikeTitle(title);
    }

    @Override
    public List<SongList> selectByTitle(String title) {
        return songListMapper.selectByTitle(title);
    }

    @Override
    public List<SongList> selectLikeStyle(String style) {
        return songListMapper.selectLikeStyle(style);
    }
}
