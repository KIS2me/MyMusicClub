package music.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * 管理员
 */
@Data //生成类中默认的构造方法、属性的getter/setter方法、equals、canEqual、hashCode、toString方法
public class Admin implements Serializable { //Serializable实现序列化
    //主键
    private Integer id;
    //账号
    private String username;
    //密码
    private String password;
    //状态(是否被禁用)
    private int status;
}
