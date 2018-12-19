// 页面加载完成时...

var loanId=$("#loanId").val();

// 加载操作时间
$('#txtStartDate').datepicker({
    dateFmt: 'yyyy-MM',
    maxDate: '#F{$dp.$D(\'txtEndDate\')}'
});
$('#txtEndDate').datepicker({
    dateFmt: 'yyyy-MM',
    minDate: '#F{$dp.$D(\'txtStartDate\')}',
    maxDate:new Date
});



/*?conlumeName=' + conlumeName + '&id=' + id + '&updordet=1*/
/*获取编辑界面*/

$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('updateConTime');
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
    /*if (!banger.verify('#updateMaolilv-form')) {
     return false;
     }*/

    //postJson['picProductName'] = $('#picProductId option:selected').text();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanConInvestigate/updateConTime.html',
        data : {'loanId':loanId,'createDate':createDate,'createDateEnd':createDateEnd},
        sync: false,
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
    });
}

//重置
function reset(){
    $("#form")[0].reset();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('updateConTime');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}


