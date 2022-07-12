package music.service;

import music.domain.Admin;

/**
 * 管理员service接口
 */
public interface AdminService {
    /**
     * 登录验证
     */
    Admin login(String username);
}
