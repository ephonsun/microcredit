var refuseReason = '';
$(function(){
    banger.verify('#note', [{name : 'required', tips : '退回原因必须填写'},{ name: 'maxlength', tips: '“备注”内容过长' }]);


    $('#btnConfirm').click(function() {
        saveBackInfo();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('backLoanApply');
        dialog.close();
    });

});

function saveBackInfo(){
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var loanId = $("#loanId").val();
    var module = $("#module").val();
    var note = $("#note").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loan/saveBackApplyInfo.html',
        data : {"loanId": loanId, "note": note, "module":module},
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


//关闭dialog
function closeDialog(){
    var dialog = getDialog('backLoanApply');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.closeApplyTab){
        win.closeApplyTab();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}