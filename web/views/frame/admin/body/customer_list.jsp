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

<script type="text/javascript">
    $(function () {
        var height = $(".tab_header").outerHeight() + $("#tb_body_footer").outerHeight();
        $("#tb_body").height($(document).height() - height);
    });

    function customer_forbidden(node) {
        if (!window.confirm("确认禁用！")) {
            return;
        }
        var customerId = $(node).attr("customerId");
        var url = getRootPath() + "customer/forbiddenCustomer.do?customerId=" + customerId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    $(node).parent("td").siblings(".forbidden_cls").text("是");
                    $(node).attr("onclick", "customer_reUse(this)").text("启用");
                } else {
                    alert("服务器出现故障！");
                }
            }
        });
    }

    function customer_reUse(node) {
        if (!window.confirm("确认启用！")) {
            return;
        }
        var customerId = $(node).attr("customerId");
        var url = getRootPath() + "customer/reUseCustomer.do?customerId=" + customerId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    $(node).parent("td").siblings(".forbidden_cls").text("否");
                    $(node).attr("onclick", "customer_forbidden(this)").text("禁用");
                } else {
                    alert("服务器出现故障！");
                }
            }
        });
    }
    function renew(node) {
        var tag = $(node);
        var customerId = $(node).attr("customerId");
        var forbidden = $(node).parent("td").siblings(".forbidden_cls").text();
        if (forbidden == '是') {
            alert("客户被禁用，无法续费!");
            return;
        }
        var curDate = $(node).attr("validTime");
        var dates = curDate.split("-");
        var year = parseInt(dates[0]);
        var month = parseInt(dates[1]);
        var day = parseInt(dates[2]);
        var dateTmp = new Date();
        dateTmp.setYear(year);
        dateTmp.setDate(day);

        $("#renew_div").dialog({
            modal: true,
            width: '240',
            height: '160',
            title: '续费',
            resizable: false,
            buttons: {
                "确 认": function () {
                    var renew_month = parseInt($("#renew_month").val());
                    if (renew_month <= 0) {
                        alert("月数须大于0！");
                        return;
                    }
                    if (parseInt(renew_month) == renew_month)
                        $.ajax({
                            url: getRootPath() + 'customer/renew.do',
                            type: 'POST',
                            data: {"renewMonth": renew_month, "customerId": customerId},
                            success: function (data) {
                                alert("续费成功！");
                                dateTmp.setMonth(month + renew_month);
                                var afterDate = dateTmp.getFullYear() + "-" + dateTmp.getMonth() + "-" + dateTmp.getDate();
                                tag.attr("validTime", afterDate);
                                tag.parent("td").siblings(".validTime").text(afterDate);
                                $("#renew_month").val("0");
                                $("#renew_div").dialog("close");
                            }
                        })
                },
                "取 消": function () {
                    $("#renew_month").val("0");
                    $("#renew_div").dialog("close");
                }
            }
        });
    }

    function numValidate() {
        var renew_month = $.trim($("#renew_month").val());
        if (renew_month == "") {
            $("#renew_month").val("0");
        } else if (isNaN(renew_month)) {
            $("#renew_month").val("0");
        } else {
            $("#renew_month").val(parseInt(renew_month));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[客户列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td>序号</td>
            <td>账号</td>
            <td>密码</td>
            <td>有效期</td>
            <td>是否禁用</td>
            <td>操作</td>
        </tr>
        <c:forEach var="customer" items="${customers}">
            <tr class="tr_body">
                <td>${customer.index}</td>
                <td>${customer.account}</td>
                <td>${customer.password}</td>
                <td class="validTime">${customer.validTime}</td>
                <td class="forbidden_cls"><c:if test="${customer.forbidden == 0}">否</c:if><c:if test="${customer.forbidden == 1}">是</c:if></td>
                <td>
                    <c:if test="${customer.forbidden ==0}">
                        <a href="javascript:void(0);" customerId="${customer.id}" onclick="customer_forbidden(this);">禁用</a>
                    </c:if>
                    <c:if test="${customer.forbidden == 1}">
                        <a href="javascript:void(0);" customerId="${customer.id}" onclick="customer_reUse(this);">启用</a>
                    </c:if>
                    <a href="javascript:void(0);" customerId="${customer.id}" validTime="${customer.validTime}" onclick="renew(this);">续费</a>
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

<div id="renew_div" style="display: none;padding-top: 30px;">
    <div style="margin-left: 40px;">续费 <input id="renew_month" onkeyup="numValidate();" size="10" value="0"/>个月</div>
</div>
