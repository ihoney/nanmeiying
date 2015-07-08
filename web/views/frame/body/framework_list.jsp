<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
<script type="text/javascript" src="${rootPath}/js/paging.js"></script>
<script type="text/javascript" src="${rootPath}/js/common_operation.js"></script>
<script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>

<link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>

<link type="text/css" rel="stylesheet" href="${rootPath}/css/colorbox.css"/>
<script type="text/javascript" src="${rootPath}/js/jquery.colorbox.js"></script>

<script type="text/javascript">
    $(function () {
        $(".btn_add_img").click(function () {
            location.href = getRootPath() + "demo/addInit.do";
        });

        $(".btn_list_mode_img").click(function () {
            location.href = getRootPath() + "demo/queryAllDemos.do?showType=2";
        });
        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});

        var height = $(".tab_header").outerHeight()+ $("#tb_body_footer").outerHeight();
        $("#tb_body").height($(document).height()-height);
    });

    //编辑作品
    function demo_edit(node) {
        var demoId = $(node).attr("demoId");
        location.href = getRootPath() + "demo/editDemo.do?demoId=" + demoId;
    }

    //删除作品
    function demo_delete(node) {
        var demoId = $(node).attr("demoId");
        var url = getRootPath() + "demo/deleteDemo.do?demoId=" + demoId + "&fileName=" + $(node).attr("fileName");
        deleteRecord(node, url);
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[作品列表]

    <span class="btn_pos">
        <span class="btn_bg_img_2 btn_list_mode_img">网格模式</span>
        <span class="btn_bg_img btn_add_img">添加</span>
    </span>

</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>作品名称</td>
            <td>手艺人</td>
            <td>作品类型</td>
            <td>价格(元)</td>
            <td>首次优惠价格(元)</td>
            <td>耗时(分钟)</td>
            <td>保持(天)</td>
            <td>可预约时间(点)</td>
            <td>操作</td>
        </tr>
        <c:forEach var="demo" items="${demos}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" demoId="${demo.id}"/></td>
                <td>${vst.index}</td>
                <td>${demo.NAME}</td>
                <td><a href="${rootPath}/employee/getEmployeeDetail.do?employeeId=${demo.employeeId}">${demo.empName}</a></td>
                <td>${demo.demoType}</td>
                <td>${demo.price}</td>
                <td>${demo.PreferentialPrice}</td>
                <td>${demo.timeConsuming}</td>
                <td>${demo.keepTime}</td>
                <td>${demo.booktime}</td>
                <td>
                    <a href="${rootPath}/fileUpload/${demo.fileEName}" class="img_class_a">预览</a>
                    <a href="javascript:void(0);" demoId="${demo.id}" onclick="demo_edit(this);">编辑</a>
                    <a href="${rootPath}/demo/getDemoDetail.do?demoId=${demo.id}">详情</a>
                    <a href="javascript:void(0);" demoId="${demo.id}" fileName="${demo.fileEName}" onclick="demo_delete(this);">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 <span id="recordCount">${recordCount}</span> 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
        </span>
        <span class="footer_text_align_two">
            <button class="footer_text_two" onclick="firstPage();">首页</button>
            <button class="footer_text_two" onclick="prePage();">上一页</button>
            <button class="footer_text_two" onclick="nextPage();">下一页</button>
            <button class="footer_text_two" onclick="lastPage();">尾页</button>
            转到第<input id="go_page" type="text" size="8"/>页
            <button class="footer_text_two" onclick="btnGo();">跳转</button>
        </span>
</div>
<input id="pageJumpController" type="hidden" value="${pageJumpController}"/>