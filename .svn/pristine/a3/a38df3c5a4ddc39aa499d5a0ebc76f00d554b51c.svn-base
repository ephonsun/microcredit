var loanId=$("#loanId").val();
var loanClassId=$("#loanClassId").val();
$(function(){
    $('select').selectbox();
    layout.initForms();
    banger.verify('#offAssetsAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
    banger.verify('#offDebtsAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
})
function updateOffAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/updateOffAssets.html?columnName='+columnName,
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialogFlush(columnName);
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

/*function addAmountAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/addAmountAssets.html?columnName='+columnName,
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialogFlush(columnName);
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}*/



//取消
$('#btnCancel').click(function () {
    var dialog = getDialog('updateAssets');
    dialog.close();
});


//关闭dialog
function closeDialogFlush(columnName){
    var dialog = getDialog('updateAssets');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.reflushGrid){
        win.reflushGrid(columnName);
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}