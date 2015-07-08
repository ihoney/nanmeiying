package com.lb.controller;

import com.lb.service.FrameworkService;
import com.lb.utils.Constant;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
@RequestMapping(value = "framework")
public class FrameworkController {

    @Resource
    private FrameworkService frameworkService;

    @RequestMapping("queryAllFrameworks")
    public ModelAndView queryAllFrameworks(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        int recordCount = frameworkService.getRecordCount();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        List<Map<String, Object>> frameworks = frameworkService.getRecordByPage(seller.get("id").toString(), 1, Constant.PAGENUM);
        modelAndView.addObject("frameworks", frameworks);
        int totalPage = recordCount / Constant.PAGENUM + (recordCount % Constant.PAGENUM == 0 ? 0 : 1);
        modelAndView.addObject("recordCount", recordCount);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("curPage", 1);
        modelAndView.addObject("pageJumpController", "queryRecordByPage.do");
        modelAndView.setViewName("frame/body/framework_list");
        return modelAndView;
    }

    /**
     * 调到指定页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "queryRecordByPage")
    public ModelAndView queryRecordByPage(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        int totalPage = Integer.parseInt(request.getParameter("totalPage"));
        int recordCount = Integer.parseInt(request.getParameter("recordCount"));
        List<Map<String, Object>> frameworks = frameworkService.getRecordByPage(seller.get("id").toString(), curPage, Constant.PAGENUM);
        modelAndView.addObject("frameworks", frameworks);
        modelAndView.addObject("curPage", curPage);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("recordCount", recordCount);
        modelAndView.addObject("pageJumpController", "queryRecordByPage.do");
        modelAndView.setViewName("frame/body/framework_list");
        return modelAndView;
    }

    @RequestMapping("addInit")
    public ModelAndView addInit(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> seller = (Map<String, Object>) session.getAttribute("seller");
        String sellerId = seller.get("id").toString();
        return modelAndView;
    }

    /**
     * 删除作品
     *
     * @param request
     * @return
     * @throws net.sf.json.JSONException
     */
    @RequestMapping(value = "deleteRecord")
    @ResponseBody
    public JSONObject deleteRecord(HttpServletRequest request, String frameworkId, String fileName) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            frameworkService.deleteRecord(frameworkId);
            if (fileName != null && !"".equals(fileName)) {
                String filePath = request.getRealPath("/fileUpload");
                String fileTmp = filePath + "/" + fileName;
                File file = new File(fileTmp);
                file.delete();
            }
            jsonObject.put(Constant.REQRESULT, Constant.REQSUCCESS);
        } catch (Exception e) {
            jsonObject.put(Constant.REQRESULT, Constant.REQFAILED);
        }
        return jsonObject;
    }
}
