$(function(){
    layout.initForms();
    $('select').selectbox();
    // 加载操作时间
    $('#issueDate').datepicker({
        dateFmt: 'yyyy-MM-dd',
        maxDate:new Date()
    });
    banger.verify('#debtsSource', {name : 'required'});
    banger.verify('#bebtsAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
    banger.verify('#termLimit',{name : 'posiInteger'});
    banger.verify('#balanceAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]);
})


var loanId=$("#loanId").val();
var loanClassId=$("#loanClassId").val();

function updateDebtsAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    if(!checkMoney()){
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/updateDebtsAssets.html?columnName='+columnName,
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
function checkMoney(){
    //金额
    var bebtsAmount = $("#bebtsAmount").val();
    bebtsAmount = bebtsAmount == "" ? 0 : bebtsAmount;
    //余额
    var balanceAmount = $("#balanceAmount").val();
    balanceAmount = balanceAmount == "" ? 0 : balanceAmount;

    if(parseFloat(bebtsAmount) < parseFloat(balanceAmount)){
        showConfirm({
            icon: 'warning',
            content: "余额不能大于金额！"
        });
        return false;
    }
    return true;
}
function addDebtsAssets(columnName) {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    if(!checkMoney()){
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../assets/addDebtsAssets.html?columnName='+columnName,
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