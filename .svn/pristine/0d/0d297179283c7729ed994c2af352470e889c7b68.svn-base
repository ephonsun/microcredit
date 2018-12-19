var loanId;
var fieldFlag;
var module;
var all=$("input[name=loanPrincipalAmountAll]").val();
$(function () {
    $('.txtDate').each(function () {
        var id = $(this).attr('id');
        banger.verify('#' + id, [{name : 'required', tips : '日期必须选择'}]);
        $('#' + id).datepicker({
            onpicked:function () {
                checkDate('#' + id);
            }
        });
    });
    loanId = $("#loanId").val();
    fieldFlag = $("#fieldFlag").val();
    module = $("#module").val();
    $('#btnConfirm').click(function() {
        saveRepayPlanInfo();
    });
    $('#btnReset').click(function() {
        planReset();
    });
    //关闭
    $('#btnCancel').click(function() {
        closeDialog();
    });
});
var showfirm = false;
function checkDate(e) {
    var index = $(e).attr('index');
    var prevValue;
    if (index == 1) {
        prevValue = $('#ratioDate').val();
    } else {
        prevValue = $(e).parent().parent().parent().prev().find('input[name="loanRepayPlanDate"]').val();
    }
    var nextValue = $(e).parent().parent().parent().next().find('input[name="loanRepayPlanDate"]').val();
    var thisValue = $(e).val();
    if (prevValue && thisValue <= prevValue) {
        if (!showfirm) {
            var content = '日期应大于上一个日期';
            showfirm = true;
            if (index == 1) {
                content = '日期应大于起息日期';
            }
            showConfirm({
                icon: 'warning',
                content: content,
                ok:function () {
                    $(e).val('');
                    showfirm = false;
                }
            });
        }
    } else if (nextValue && nextValue <= thisValue) {
        if (!showfirm) {
            showfirm = true;
            showConfirm({
                icon: 'warning',
                content: '日期应小于下一个日期',
                ok:function () {
                    $(e).val('');
                    showfirm = false;
                }
            });
        }
    } else {
        $(e).parents('.tr-input').find('input[name="loanRepayPlanDate"]').val(thisValue);
    }
}
var check = true;
var showfirm = false;
function amountChange(e,oldValue,newValue){
    if(oldValue===newValue){
        return;
    }
    if(newValue==''){
        newValue = 0;
        $(e).val(0);
    }
    //输入校验，只能输入数字
    var reg =  /^\d+(\.\d+)?$/;
    //循环输入框中的值，计算剩余

    var total = 0;
    var thisCheck = true;
    $("input[name=loanPrincipalAmount]").each(function(){
        var thisv = $(this).val();
        if(reg.test(thisv) == false){
            if (!showfirm) {
                showfirm = true;
                showConfirm({
                    icon: 'warning',
                    content: '还款本金只能输入数字',
                    ok:function () {
                        showfirm = false;
                    }
                });
            }
            thisCheck = false;
            check = false;
        }
        var num=parseFloat($(this).val());
        if (thisCheck) $(this).val(num);
        total=numAdd(total, num);
    });
    if (!thisCheck) return;
    // var num=parseFloat($(e).val());
    // $(e).val(num);

    //

    var left=numSub(all.toFixed(2), total.toFixed(2));
    left=parseFloat(left);
    $('#loanPrincipalAmount').html(left);
    $("input[name=loanPrincipalAmountLast]").val(left);
    //如果超出了目标，需要提示
    if(left < 0){
        if (!showfirm) {
            showfirm = true;
            showConfirm({
                icon: 'warning',
                content: '还款金额输入额度过大',
                ok:function () {
                    showfirm = false;
                }
            });
        }
        check = false;
        return;
    }

    check = true;
}

function numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch (e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch (e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (num1 * baseNum + num2 * baseNum) / baseNum;
};
/**
 * 加法运算，避免数据相减小数点后产生多位数和计算精度损失。
 *
 * @param num1被减数  |  num2减数
 */
function numSub(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    var precision;// 精度
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch (e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch (e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
    return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
};

function saveRepayPlanInfo(){
    var bool = banger.verify(".tr-input");
    if (!bool) return;
    if (!check) {
        if (!showfirm) {
            showfirm = true;
            showConfirm({
                icon: 'warning',
                content: '还款本金填写错误',
                ok:function () {
                    showfirm = false;
                }
            });
        }
        return false;
    }
    var plans = [];
    $('.tr-input').each(function () {
        var plan = {};
        plan['id'] = $(this).find("input[name='id']").val();
        plan['repaymentMode'] = $(this).find("input[name='repaymentMode']").val();
        plan['loanRepayPlanDate'] = $(this).find("input[name='loanRepayPlanDate']").val();
        plan['loanPrincipalAmount'] = $(this).find("input[name='loanPrincipalAmount']").val();

        plan['loanAccrualAmount'] = $(this).find("input[name='loanAccrualAmount']").val();
        plan['loanAccrualDays'] = $(this).find("input[name='loanAccrualDays']").val();

        plans.push(plan);
    });
    var plansJson = JSON.stringify(plans);
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanRepayPlanInfo/savePlans.html',
        data: {
            "json": plansJson,
            "module":module,
            "loanId":loanId,
            "money":all
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: '保存成功'
                });
                closeDialog();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
function planReset(){
    var flag = '2';
    if (fieldFlag == '0'){
        flag = '0';
    }
    if (!showfirm){
        var titlec = 'clearLoan';
        if (module == 'investigate')
            titlec = 'clear';
        else if (module == 'approval')
            titlec = 'clearApproval';
        if (module == 'loan')
            titlec = 'clearLoan';
        showConfirm({
            icon:'confirm',
            content:'您确定要重新生成吗？重新生成后老数据将无法恢复。',
            ok:function(){
                var dialog = getDialog('showPlanPage');
                var win = tabs.getIframeWindow(dialog.config.tabId);
                setTimeout(function() {
                    dialog.close();
                    if( win && win.showPlanInfo){
                        win.showPlanInfo(titlec,flag);
                    }
                }, 0);
            },
            cancel:function () {}
        });
    }
}
//关闭dialog
function closeDialog(){
    if (!showfirm){
        var dialog = getDialog('showPlanPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    }
}