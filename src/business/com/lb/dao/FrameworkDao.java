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
public class FrameworkDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    public int getRecordCount() {
        String sql = "select count(*) from framework";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getRecordByPage(String sellerId, int pageIndex, int pageSize) {
        String sql = "SELECT d.*,e.nickName as empName   FROM   framework d left join employee e on e.id=d.employeeId  WHERE  d.sellerid = " + sellerId + " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }



    public void deleteRecord(String frameworkId) {
        String sql = "delete from framework where id = " + frameworkId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> getRecordById(String frameworkId) {
        String sql = "select * from framework where id = " + frameworkId;
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getSingleRecordById(String frameworkId) {
        String sql = "SELECT " +
                " d.*,e.nickName, AVG(c.score) AS avgScore " +
                " FROM " +
                " framework d,employee e, " +
                " `comment` c " +
                " WHERE " +
                " d.id = " + frameworkId +
                " and e.id = d.employeeId " +
                " AND c.frameworkid = d.id";
        return jdbcTemplate.queryForMap(sql);
    }
}
