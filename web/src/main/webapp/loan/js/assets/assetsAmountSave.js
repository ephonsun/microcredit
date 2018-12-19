var loanId=$("#loanId").val();
var loanClassId=$("#loanClassId").val()

$(function(){
    layout.initForms();
    banger.verify('#itemName', {name : 'required'});
    banger.verify('#amount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
    banger.verify('#remark',{name : 'maxlength',tips: '“备注”内容过长' });

})

function updateAmountAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/updateAmountAssets.html?columnName='+columnName,
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

function addAmountAssets(columnName) {
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
}



//取消
$('#btnCancel').click(function () {
    closeDialog();
});

//关闭dialog
function closeDialog() {
    var dialog = getDialog('updateAssets');
    dialog.close();
}

//关闭dialog
function closeDialogFlush(columnName){
    var dialog = getDialog('updateAssets');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.reflushGrid){
        win.reflushGrid(columnName);
    }
    if( win && win.reflushTitle){
        win.reflushTitle(columnName);
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}