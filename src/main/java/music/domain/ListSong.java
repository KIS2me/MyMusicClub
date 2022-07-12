package music.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 歌单里的歌曲
 */
@Data
public class ListSong implements Serializable {
    //主键
    private Integer id;
    //对应的歌曲id
    private Integer songId;
    //所属列表的id
    private Integer songListId;
}
