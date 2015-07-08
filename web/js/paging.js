/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-23
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */

    //第一页
function firstPage() {
    if ($("#currentPage").text() != "1") {
        var totalPage = $("#totalPage").text();
        var recordCount = $("#recordCount").text();
        var pageJumpController = $("#pageJumpController").val();
        window.location = pageJumpController + "?curPage=1&totalPage=" + totalPage + "&recordCount=" + recordCount;
    }
}

//上一页
function prePage() {
    if ($("#currentPage").text() != "1") {
        var curPage = parseInt($("#currentPage").text()) - 1;
        var totalPage = $("#totalPage").text();
        var recordCount = $("#recordCount").text();
        var pageJumpController = $("#pageJumpController").val();
        window.location = pageJumpController + "?curPage=" + curPage + "&totalPage=" + totalPage + "&recordCount=" + recordCount;
    }
}
//下一页
function nextPage() {
    if ($("#currentPage").text() != $("#totalPage").text()) {
        var curPage = parseInt($("#currentPage").text()) + 1;
        var totalPage = $("#totalPage").text();
        var recordCount = $("#recordCount").text();
        var pageJumpController = $("#pageJumpController").val();
        window.location = pageJumpController + "?curPage=" + curPage + "&totalPage=" + totalPage + "&recordCount=" + recordCount;
    }
}

//最后一页
function lastPage() {
    if ($("#currentPage").text() != $("#totalPage").text()) {
        var curPage = $("#totalPage").text();
        var totalPage = $("#totalPage").text();
        var recordCount = $("#recordCount").text();
        var pageJumpController = $("#pageJumpController").val();
        window.location = pageJumpController + "?curPage=" + curPage + "&totalPage=" + totalPage + "&recordCount=" + recordCount;
    }
}

//跳转到第几页
function btnGo() {
    var go_page = $("#go_page").val();
    var totalPage = $("#totalPage").text();
    var totalPage = $("#totalPage").text();
    var recordCount = $("#recordCount").text();
    var pageJumpController = $("#pageJumpController").val();
    if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
        if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
            window.location = pageJumpController + "?curPage=" + go_page + "&totalPage=" + totalPage + "&recordCount=" + recordCount;
        }
    }
}