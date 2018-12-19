var potentialId = "0";
$(function(){
    potentialId=$("#potentialId").val();
    $('#toApplyLoan').click(function () {
        tabs.add({
            id : 'addOrUpdateLoan'+potentialId,
            name : 'addOrUpdateLoan'+potentialId,
            pid: tabs.getSelfId(window),
            title : '贷款-新建',
            url : '../loan/getLoanTabs.html?module=apply'+'&potentialId='+potentialId+'&random='+Math.random()*10000,
            lock : false
        });
    });
    //关闭
    $('#btnCancelBase').click(function() {
        closeTab();
    });
});
//关闭页卡
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refreshList){
        win.refreshList();
    }
    tabs.close(tabs.getSelfId(window));
}