package com.lb.controller;

import com.lb.service.CustomerService;
import com.lb.utils.Constant;
import com.lb.utils.DateUtil;
import com.lb.utils.MD5Util;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-27
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 检查登录
     *
     * @param request
     * @param account
     * @param password
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(HttpServletRequest request, String account, String password, String key, String loginStr, String timeStamp) throws JSONException {
        Map<String, Object> jsonObject = new HashMap<String, Object>();
        String keyLocal = MD5Util.toHexString(account + password + loginStr + timeStamp);
        String reqAddr = request.getRemoteAddr();

        if (!keyLocal.equals(key)) {
            jsonObject.put(Constant.REQRESULT, "0");  //非法登录
        } else {
            List<Map<String, Object>> customers = customerService.existsCustomer(account, password);
            if (customers != null && customers.size() > 0) {
                Map<String, Object> customer = customers.get(0);
                String forbidden = customer.get("forbidden").toString();
                if (forbidden.equals("1")) {
                    jsonObject.put(Constant.REQRESULT, "1");//账号禁用
                } else {
                    Date validDate = DateUtil.getDate(customer.get("validTime").toString());
                    Date curDate = new Date();
                    if (curDate.after(validDate)) {
                        jsonObject.put(Constant.REQRESULT, "2"); //账户到期
                    } else {
                        Date loginTime = DateUtil.getTime(customer.get("loginTime").toString());
                        long lt = loginTime.getTime();
                        long cd = curDate.getTime();
                        if ((lt + 60 * 1000) > cd) {
                            String loginIp = customer.get("loginIp").toString();
                            String loginStrCur = customer.get("loginStr").toString();
                            if (loginIp.equals(reqAddr) && loginStrCur.equals(loginStr)) {
                                jsonObject.put("customer", customer);
                                customerService.changeLoginInfo(customer.get("id").toString(), loginStr, reqAddr);
                                jsonObject.put(Constant.REQRESULT, "3");
                            } else {
                                jsonObject.put(Constant.REQRESULT, "4"); //账户已登录
                            }
                        } else {
                            jsonObject.put("customer", customer);
                            customerService.changeLoginInfo(customer.get("id").toString(), loginStr, reqAddr);
                            jsonObject.put(Constant.REQRESULT, "3");
                        }
                    }
                }
            } else {
                jsonObject.put(Constant.TIPMESSAGE, "5");//账户或密码错误
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            }
        }

        return jsonObject;
    }

    @RequestMapping("queryAllCustomers")
    public ModelAndView queryAllCustomers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int recordCount = customerService.getCustomerCount();
        List<Map<String, Object>> customers = customerService.getCustomerByPage(1, Constant.PAGENUM);
        modelAndView.addObject("customers", customers);
        int totalPage = recordCount / Constant.PAGENUM + (recordCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("recordCount", recordCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.addObject("pageJumpController", "queryCustomerByPage.do");
        modelAndView.setViewName("frame/admin/body/customer_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryCustomerByPage")
    public ModelAndView queryCustomerByPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        int totalPage = Integer.parseInt(request.getParameter("totalPage"));
        int recordCount = Integer.parseInt(request.getParameter("recordCount"));
        List<Map<String, Object>> customers = customerService.getCustomerByPage(curPage, Constant.PAGENUM);
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("curPage", curPage);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("recordCount", recordCount);
        modelAndView.addObject("pageJumpController", "queryCustomerByPage.do");
        modelAndView.setViewName("frame/admin/body/customer_list");
        return modelAndView;
    }

    /**
     * 禁用客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "forbiddenCustomer")
    @ResponseBody
    public JSONObject forbiddenCustomer(HttpServletRequest request, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.forbiddenCustomer(customerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 没半分钟修改一次登录信息客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changLoginStatus")
    @ResponseBody
    public JSONObject changLoginStatus(HttpServletRequest request, String customerId, String loginStr) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.updateLoginStatus(customerId, loginStr);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 启用客户
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "reUseCustomer")
    @ResponseBody
    public JSONObject reUseCustomer(HttpServletRequest request, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.reUseCustomer(customerId);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 续费
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "renew")
    @ResponseBody
    public JSONObject renew(HttpServletRequest request, int renewMonth, String customerId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            customerService.renew(customerId, renewMonth);
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

}
