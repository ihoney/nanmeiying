package com.lb.service;

import com.lb.dao.FrameworkDao;
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
public class FrameworkService {
    @Resource
    private FrameworkDao frameworkDao;

    public int getRecordCount() {
        return frameworkDao.getRecordCount();
    }

    public List<Map<String, Object>> getRecordByPage(String sellerId, int pageIndex, int pageSize) {
        return frameworkDao.getRecordByPage(sellerId, pageIndex, pageSize);
    }

    public void deleteRecord(String frameworkId) {
        frameworkDao.deleteRecord(frameworkId);
    }

    /**
     * 根据Id获取作品i信息
     *
     * @param frameworkId
     * @return
     */
    public List<Map<String, Object>> getRecordById(String frameworkId) {
        return frameworkDao.getRecordById(frameworkId);
    }

    public Map<String, Object> getSingleRecordById(String frameworkId) {
        return frameworkDao.getSingleRecordById(frameworkId);
    }
}
