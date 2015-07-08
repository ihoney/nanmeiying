<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link href="${rootPath}/css/frame.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/frame_left.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/frame_left.js"></script>
<div id="content">
    <div id="menu_manager">
        管理菜单
    </div>
    <div class="business_menu">
        <div class="business_title">
            业务中心
        </div>
        <div class="business_body div_show">
            <c:if test="${checked == '是'}">
                <ul>
                    <li><a href="${rootPath}/framework/queryAllFrameworks.do?showType=1" target="rightFrame" class="STYLE3">测试列表</a></li>
                </ul>
            </c:if>
            <c:if test="${checked == '否'}">
                <span class="STYLE7">还未通过审核</span>
                <c:if test="${authed == '否'}">
                    <span class="STYLE7">请先进行认证！</span>
                </c:if>
            </c:if>
        </div>
    </div>

    <div class="business_menu">
        <div class="business_title">
            安全管理
        </div>
        <div class="business_body">
            <ul>
                <li><a href="${rootPath}/seller/changePwdInit.do" target="rightFrame" class="STYLE3">修改密码</a></li>
                <li><a href="${rootPath}/seller/authInit.do" target="rightFrame" class="STYLE3">个人信息认证</a></li>
            </ul>
        </div>
    </div>
</div>