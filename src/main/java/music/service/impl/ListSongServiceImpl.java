package music.service.impl;

import music.domain.ListSong;
import music.mapper.ListSongMapper;
import music.service.ListSongService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌单里的歌曲service实现类
 */
@Service
public class ListSongServiceImpl implements ListSongService {
    private ListSongMapper listSongMapper;

    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong) > 0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return listSongMapper.delete(id) > 0;
    }

    @Override
    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId) {
        return listSongMapper.deleteBySongIdAndSongListId(songId, songListId) > 0;
    }

    @Override
    public ListSong selectById(Integer id) {
        return listSongMapper.selectById(id);
    }

    @Override
    public List<ListSong> selectAllListSong() {
        return listSongMapper.selectAllListSong();
    }

    @Override
    public List<ListSong> selectBySongListId(Integer songListId) {
        return listSongMapper.selectBySongListId(songListId);
    }
}
