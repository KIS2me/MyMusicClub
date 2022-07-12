package music.domain;

import lombok.Data;

import java.util.Date;

/**
 * 收藏
 */
@Data
public class Collect {
    //主键
    private Integer id;
    //用户id
    private Integer userId;
    //收藏类型（0歌曲，1歌单）
    private Byte type;
    //歌曲id
    private Integer songId;
    //歌单id
    private Integer songListId;
    //收藏时间
    private Date createTime;
}
