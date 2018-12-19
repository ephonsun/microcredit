$(function(){
    if($('#loanRepayDate').val() == ""){
        $('#loanRepayDate').val($("#nowDate").val().substring(0,10));
    }
    //渲染下拉框
    $('select').selectbox();

    // 加载操作时间
    $('#loanRepayDate').datepicker({
        maxDate: '#F{$dp.$D(\'nowDate\')}'
    });

    banger.verify('#loanRepayAmount',[{name: 'money', tips: '金额格式不正确，格式为[10位].[2位]'},{name: 'required', tips: '必须填写'},checkloanRepayAmount]);
    banger.verify('#loanRepayAccrualAmount',[{name: 'money', tips: '金额格式不正确，格式为[10位].[2位]'},{name: 'required', tips: '必须填写'},checkloanRepayAccrualAmount]);
    banger.verify('#loanRepayDate',{name: 'required', tips: '还款时间必须选择'});

    $('#btnSaveLoanRepayPlanInfo').click(function() {
        updateLoanInfoAddedClass();
    });
    //关闭
    $('#btnCancelLoanRepayPlanInfo').click(function() {
        var dialog = getDialog('updateLoanRepayPlanInfo');
        dialog.close();
    });
});

var checkloanRepayAmount = {
    name : 'checkloanRepayAmount',
    tips : '已还本金不能大于应还本金',
    rule : function() {
        var rule = this, bool = true, data = "";
        if(parseFloat($("#loanPrincipalAmount").val()) < parseFloat($("#loanRepayAmount").val()))
            bool = false;
        return bool;
    }
};

var checkloanRepayAccrualAmount = {
    name : 'checkloanRepayAccrualAmount',
    tips : '已还利息不能大于应还利息',
    rule : function() {
        var rule = this, bool = true, data = "";
        if(parseFloat($("#loanAccrualAmount").val()) < parseFloat($("#loanRepayAccrualAmount").val()))
            bool = false;
        return bool;
    }
};
//保存更新
function updateLoanInfoAddedClass(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        url : '../loanRepayPlanInfo/updateRepayPlanInfo.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
        }
    });
}
//关闭dialog
function closeDialog(){
    var dialog = getDialog('updateLoanRepayPlanInfo');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    // win.location.reload(true);
    win.refresh()
    setTimeout(function() {
        dialog.close();
    }, 0);
}
