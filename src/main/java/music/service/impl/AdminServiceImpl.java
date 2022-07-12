package music.service.impl;

import music.domain.Admin;
import music.mapper.AdminMapper;
import music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员service实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证密码是否正确
     */
    @Override
    public Admin login(String username) {
        return adminMapper.login(username) ;
    }
}
