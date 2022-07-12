package music.mapper;

import music.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao
 */
@Repository
public interface ConsumerMapper {
    /**
     * 增加
     */
    int insert(Consumer consumer);

    /**
     * 修改
     */
    int update(Consumer consumer);

    /**
     * 验证密码
     */
    int verifyPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 根据id查询
     */
    Consumer selectById(Integer id);

    /**
     * 查询所有用户
     */
    List<Consumer> selectAllConsumer();

    /**
     * 根据用户名字查询
     */
    Consumer selectByUsername(String username);
}
