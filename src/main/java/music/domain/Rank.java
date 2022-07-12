package music.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 歌单评分
 */
@Data //生成类中默认的构造方法、属性的getter/setter方法、equals、canEqual、hashCode、toString方法
public class Rank implements Serializable { //Serializable实现序列化
    //主键
    private Integer id;
    //歌单id
    private Integer songListId;
    //用户id
    private Integer consumerId;
    //分数
    private Integer score;
}
