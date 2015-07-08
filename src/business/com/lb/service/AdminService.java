package com.lb.service;

import com.lb.dao.AdminDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

    public List<Map<String, Object>> existsAdmin(String account, String password) {
        return adminDao.findAdmin(account, password);
    }

    public void changePwd(String adminId, String newPassword) {
        adminDao.changePwd(adminId, newPassword);
    }

    public void changeLoginStatus(String id, String sessionId) {
        adminDao.changeLoginStatus(id, sessionId);
    }

    public void quitSys(String adminId) {
        adminDao.quitSys(adminId);
    }
}
