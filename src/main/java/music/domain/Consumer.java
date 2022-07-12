package music.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Data
public class Consumer implements Serializable { //Serializable实现序列化
    //主键
    private Integer id;
    //账号
    private String username;
    //密码
    private String password;
    //性别
    private Byte sex;
    //电话号码
    private String phoneNum;
    //邮箱
    private String email;
    //生日
    private Date birth;
    //简介
    private String introduction;
    //地区
    private String location;
    //头像
    private String avator;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
