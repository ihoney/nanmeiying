<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link href="${rootPath}/css/frame.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/frame_left.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/frame_left.js"></script>
<script type="text/javascript" src="${rootPath}/js/admin_login.js"></script>
<script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
<div id="content">
    <div id="menu_manager">
        管理菜单
    </div>
    <div class="business_menu">
        <div class="business_title">
            业务中心
        </div>
        <div class="business_body div_show">
            <ul>
                <li><a href="${rootPath}/customer/queryAllCustomers.do" target="rightFrame" class="STYLE3">客户列表</a></li>
            </ul>
        </div>
    </div>

    <div class="business_menu">
        <div class="business_title">
            系统管理
        </div>
        <div class="business_body">
            <ul>
                <li><a href="${rootPath}/admin/changePwdInit.do" target="rightFrame" class="STYLE3">修改密码</a></li>
                <li><a href="javascript:void(0);" onclick="quitSys()" class="STYLE3">退出</a></li>
            </ul>
        </div>
    </div>
</div>