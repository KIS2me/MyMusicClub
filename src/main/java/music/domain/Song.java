package music.domain;

import lombok.Data;

import java.util.Date;

/**
 * 歌曲
 */
@Data
public class Song {
    //主键
    private Integer id;
    //歌手id
    private Integer singerId;
    //歌名
    private String name;
    //简介
    private String introduction;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //歌曲图片
    private String pic;
    //歌词
    private String lyric;
    //保存地址
    private String url;
}
