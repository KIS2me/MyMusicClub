package music.mapper;

import music.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * 管理员dao
 */
@Repository
public interface AdminMapper {
    /**
     * 登录验证方法
     */
    /*
        当只有一个参数时，Mapper层中的方法可以不使用注解
        多个参数时必须用@Param(“XXX”)来指明
    */
    Admin login(String username);
}
