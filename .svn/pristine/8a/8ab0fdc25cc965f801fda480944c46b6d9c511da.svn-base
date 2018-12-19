$(function(){
    $('select').selectbox();

    //加载时间
    $('#loanRepayPlanDate').datepicker({

    });
    //校验
    banger.verify('#loanRepayPlanDate',[
        {name: 'required', tips: '请选择计划还款日期'},
        checkLoanRepayPlanDateRule]);
    banger.verify('#loanPrincipalAmount',[{name: 'money', tips: '金额格式不正确，格式为[10位].[2位]'},{name: 'required', tips: '必须填写'}]);
    banger.verify('#loanAccrualAmount',[{name: 'money', tips: '金额格式不正确，格式为[10位].[2位]'},{name: 'required', tips: '必须填写'}]);

    //保存
    $('#btnSaveLoanRepayPlanInfo').click(function() {
        saveLoanRepayPlanInfo();
    });

    $('#btnContinueLoanRepayPlanInfo').click(function(){
        continueLoanRepayPlanInfo();
    });

    //关闭
    $('#btnCancelLoanRepayPlanInfo').click(function() {
        var dialog = getDialog('addLoanRepayPlanInfo');
        dialog.close();
    });

});

function saveLoanRepayPlanInfo(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        url : '../loanRepayPlanInfo/saveLoanRepayPlanInfo.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '保存成功'
            });
            closeDialog();
        }
    });
}
function continueLoanRepayPlanInfo(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        url : '../loanRepayPlanInfo/saveLoanRepayPlanInfo.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '保存成功'
            });
            $("#form")[0].reset();
            var dialog = getDialog('addLoanRepayPlanInfo');
            var win = tabs.getIframeWindow(dialog.config.tabId);
            win.refresh();
        }
    });
}




//贷款类型名字合法性校验  (长度校验 重复性校验)
var checkLoanRepayPlanDateRule = {
    name : 'checkLoanRepayPlanDateRule',
    tips : '该日已有计划',
    rule : function() {
        var rule = this, bool = true, data = {"date":$("#loanRepayPlanDate").val(),"loanId":$("#loanId").val()}
        var url = '../loanRepayPlanInfo/checkLoanRepayPlanDateRuleAdd.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            dataType:'json',
            async : false,
            data : data,
            success : function(result) {
                bool = result.success;
            }
        });
        return bool;
    }
};
//关闭dialog
function closeDialog(){
    var dialog = getDialog('addLoanRepayPlanInfo');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refresh();
    // win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

