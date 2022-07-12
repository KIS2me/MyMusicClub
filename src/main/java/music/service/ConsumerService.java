package music.service;

import music.domain.Consumer;

import java.util.List;

/**
 * 用户service接口
 */
public interface ConsumerService {
    /**
     * 增加
     */
    boolean insert(Consumer consumer);

    /**
     * 修改
     */
    boolean update(Consumer consumer);

    /**
     * 删除
     */
    boolean delete(Integer id);

    /**
     * 根据id查询
     */
    Consumer selectById(Integer id);

    /**
     * 验证密码
     */
    boolean verifyPassword(String username, String password);

    /**
     * 查询所有用户
     */
    List<Consumer> selectAllConsumer();

    /**
     * 根据用户名字查询
     */
    Consumer selectByUsername(String username);
}
