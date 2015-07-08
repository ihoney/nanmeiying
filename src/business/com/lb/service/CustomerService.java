package com.lb.service;

import com.lb.dao.CustomerDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CustomerService {
    @Resource
    private CustomerDao customerDao;

    /**
     * 检查登录
     *
     * @param account
     * @param password
     * @return
     */
    public List<Map<String, Object>> existsCustomer(String account, String password) {
        return customerDao.findCustomer(account, password);
    }

    /**
     * 获取客户总数
     *
     * @return
     */
    public int getCustomerCount() {
        return customerDao.getCustomerCount();
    }

    /**
     * 分页获取记录
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> getCustomerByPage(int pageIndex, int pageSize) {
        return customerDao.getCustomerByPage(pageIndex, pageSize);
    }

    public void forbiddenCustomer(String customerId) {
        customerDao.forbiddenCustomer(customerId);
    }

    public void loginInfo(String account, String loginIp) {
        customerDao.loginInfo(account, loginIp);
    }

    public void reUseCustomer(String customerId) {
        customerDao.reUseCustomer(customerId);
    }

    public void renew(String customerId, int renewMonth) {
        customerDao.renew(customerId, renewMonth);
    }
}
