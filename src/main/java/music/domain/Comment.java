package music.domain;

import lombok.Data;

import java.util.Date;

/**
 * 评论
 */
@Data
public class Comment {
    //主键
    private Integer id;
    //用户id
    private Integer userId;
    //歌曲id
    private Integer songId;
    //歌单id
    private Integer songListId;
    //内容
    private String content;
    //评论时间
    private Date createTime;
    //评论类型(0歌曲，1歌单)
    private Byte type;
    //评论点赞数
    private Integer up;
}
