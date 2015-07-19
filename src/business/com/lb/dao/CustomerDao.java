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
public class CustomerDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findCustomer(String account, String password) {
        String sql = "select * from customer where account = ? and password= ?";
        return jdbcTemplate.queryForList(sql, new Object[]{account, password});
    }

    public int getCustomerCount() {
        String sql = "select count(1) from customer";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getCustomerByPage(int pageIndex, int pageSize) {
        String sql = "SELECT c.*  FROM customer AS c limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("index", (pageIndex - 1) * pageSize + i + 1);
        }
        return list;
    }

    public void forbiddenCustomer(String customerId) {
        String sql = "update customer set forbidden =1 where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void reUseCustomer(String customerId) {
        String sql = "update customer set forbidden = 0 where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void renew(String customerId, int renewMonth) {
        String sql = "UPDATE customer SET validTime = DATE_ADD(validTime, INTERVAL " + renewMonth + " MONTH) where id = " + customerId;
        jdbcTemplate.update(sql);
    }

    public void changeLoginInfo(String id, String loginStr, String reqAddr) {
        String sql = "update customer set loginTime = now(),loginStr=?,loginIp = ? where id= ?";
        jdbcTemplate.update(sql, new Object[]{loginStr, reqAddr, id});
    }

    public void updateLoginStatus(String customerId, String loginStr) {
        String sql = "update customer set loginTime=now() where id = " + customerId;
        jdbcTemplate.update(sql);
    }
}
