package com.lb.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AdminDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAdmin(String account, String password) {
        String sql = "select id,account,password,loginStatus,sessionId from manager where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, account, password);
    }

    public void changePwd(String adminId, String newPassword) {
        String sql = "update manager set password = ? where id = " + adminId;
        jdbcTemplate.update(sql, newPassword);
    }

    public void changeLoginStatus(String id, String sessionId) {
        String sql = "update manager set loginStatus = 1,sessionId = '" + sessionId + "' where id = " + id;
        jdbcTemplate.update(sql);
    }

    public void quitSys(String adminId) {
        String sql = "update manager set loginStatus = 0 where id = " + adminId;
        jdbcTemplate.update(sql);
    }
}
