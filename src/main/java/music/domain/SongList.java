package music.domain;

import lombok.Data;

/**
 * 歌单
 */
@Data
public class SongList {
    //主键
    private Integer id;
    //标题
    private String title;
    //歌单图片
    private String pic;
    //简介
    private String introduction;
    //风格
    private String style;
}
