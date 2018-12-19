// 页面加载完成时...

var loanId=$("#loanId").val();

$(function(){

    banger.verify('#txtStartDate', {name : 'required', tips: '起始时间必填'});
    banger.verify('#txtEndDate', {name : 'required', tips: '结束时间必填'});
})

// 加载操作时间
$('#txtStartDate').datepicker({
    dateFmt: 'yyyy-MM',
    maxDate: '#F{$dp.$D(\'txtEndDate\')}'
});
$('#txtEndDate').datepicker({
    dateFmt: 'yyyy-MM',
    minDate: '#F{$dp.$D(\'txtStartDate\')}',
    maxDate:new Date()
});



/*?conlumeName=' + conlumeName + '&id=' + id + '&updordet=1*/
/*获取编辑界面*/

$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('updateTime');
        dialog.close();
    });
});

// 提交新增数据源
$('#btnSave').click(function() {
    var createDate=$("#txtStartDate").val();
    var createDateEnd=$("#txtEndDate").val();
    saveTime(loanId,createDate,createDateEnd);
});

function saveTime(loanId,createDate,createDateEnd){

    var bool = banger.verify("#addMaolilv-form");
    if (!bool) {
        return false;
    }

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanInvestigate/updateTime.html',
        data : {'loanId':loanId,'createDate':createDate,'createDateEnd':createDateEnd},
        sync: false,

        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
               /* var url='../loanInvestigate/getProfitLossPage.html?loanId='+loanId;
                investigateReload(url);*/
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }

    });
/*    closeDialog();*/
}
/*function saveTime(){
    /!*if (!banger.verify('#updateMaolilv-form')) {
     return false;
     }*!/
    var postJson = getPostFields();
    //postJson['picProductName'] = $('#picProductId option:selected').text();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanInvestigate/updateTime.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }


}*/

//重置
function reset(){
    $("#form")[0].reset();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('updateTime');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    //获取父页面
    win.document.getElementById("investigateTabs");
    //需要加载的路径
    var _url = "../loanInvestigate/getProfitLossPage.html?loanId=" + $("#loanId").val() + "&showApply=&random= " + Math.random();
    //tab下所有的div形式的iframe
    var divs = $(win.document.getElementById("investigateTabs")).find("div [class='ui-tabs-iframe']");
    var div = divs.eq(2);
    $.get(_url, function(html){
        div.html(html);
    });
    setTimeout(function() {
        dialog.close();
    }, 200);
}








