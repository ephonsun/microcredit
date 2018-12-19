var loanId=$("#loanId").val();
var loanClassId=$("#loanClassId").val();
$(function(){
    $('select').selectbox();
    layout.initForms();

    banger.verify('#itemType', {name : 'required'});
    banger.verify('#itemName', {name : 'required'});
    banger.verify('#itemAccount',{name : 'posiInteger'});
    //banger.verify('#oldAmount', {name : 'money'});
    banger.verify('#oldAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
    banger.verify('#depreciationAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
    banger.verify('#oldAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
})

function updateFixedAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/updateFixedAssets.html?columnName='+columnName,
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

function addFixedAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/addFixedAssets.html?columnName='+columnName,
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
    if( win && win.reflushTitle){
        win.reflushTitle(columnName);
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}