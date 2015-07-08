package com.lb.controller;

import com.lb.service.AdminService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value = "admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 检查登录
     *
     * @param request
     * @param session
     * @param account
     * @param password
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "checkLogin")
    @ResponseBody
    public JSONObject checkLogin(HttpServletRequest request, HttpSession session, String account, String password) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String sessionId = session.getId();
        List<Map<String, Object>> admins = adminService.existsAdmin(account, password);
        if (admins != null && admins.size() > 0) {
            Map<String, Object> admin = admins.get(0);
            if (admin.get("loginStatus").toString().equals("1")) {
                if (!admin.get("sessionId").toString().equals(sessionId)) {
                    jsonObject.put(Constant.TIPMESSAGE, "当前用户正在登录中，请退出其他登录设备！");
                    jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                } else {
                    adminService.changeLoginStatus(admin.get("id").toString(), sessionId);
                    session.setAttribute("admin", admins.get(0));
                    jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
                }
            } else {
                adminService.changeLoginStatus(admin.get("id").toString(), sessionId);
                session.setAttribute("admin", admins.get(0));
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            }
        } else {
            jsonObject.put(Constant.TIPMESSAGE, "用户名或密码错误！");
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }

    /**
     * 初始化修改密码界面
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changePwdInit")
    public String changePwdInit() throws JSONException {
        return "frame/admin/body/changePassword";
    }

    /**
     * 修改密码
     *
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "changePwd")
    @ResponseBody
    public JSONObject changePwd(HttpSession session, String oldPassword, String newPassword) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> admin = (Map<String, Object>) session.getAttribute("admin");
        String adminId = admin.get("id") + "";
        String adminPass = admin.get("password") + "";
        if (!adminPass.equals(oldPassword)) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
            jsonObject.put(Constant.TIPMESSAGE, "当前密码输入错误！");
        } else {
            try {
                adminService.changePwd(adminId, newPassword);
                admin.put("password", newPassword);
                session.setAttribute("admin", admin);
                jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
            } catch (Exception e) {
                jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
                jsonObject.put(Constant.TIPMESSAGE, "操作失败！");
            }
        }
        return jsonObject;
    }

    /**
     * 退出系统
     *
     * @param request
     * @param session
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "quitSys")
    @ResponseBody
    public JSONObject quitSys(HttpServletRequest request, HttpSession session) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> admin = (Map<String, Object>) session.getAttribute("admin");
        String adminId = admin.get("id").toString();
        adminService.quitSys(adminId);
        session.removeAttribute("admin");
        jsonObject.put(Constant.TIPMESSAGE, "exit success！");
        return jsonObject;
    }
}
