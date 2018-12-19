var refuseReason = '';
$(function(){
    //渲染下拉框
    $('select').selectbox({});
    if (loanProcessType == 'Loan') {
        banger.verify('#refuseReason_Customer', {name : 'required', tips : '拒绝原因必须选择'});
    } else {
        banger.verify('#refuseReason_Bank', {name : 'required', tips : '拒绝原因必须选择'});
    }

    banger.verify('#refuseRemark',{ name: 'maxlength', tips: '“备注”内容过长' });

    $('#btnConfirm').click(function() {
        saveRefuseInfo();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('refuseLoanApply');
        dialog.close();
    });

    $(".refuseType").click(function () {
        banger.verify('#refuseRemark',[{ name: 'maxlength', tips: '“备注”内容过长' }]);

        var refuseId = $(this).val();
        var otherId = "Customer";
        if (refuseId == 'Bank') {
            otherId = "Customer";
            banger.deleteVerify('#refuseReason_Customer', 'required');
            $('.joinBlack').removeAttr("disabled");
        } else {
            otherId = "Bank";
            banger.deleteVerify('#refuseReason_Bank', 'required');
            $(".joinBlack[value='0']").prop("checked", "checked");
            $('.joinBlack').attr("disabled","disabled");
        }

        $('#refuseReason_' + otherId).prop('selectedIndex', 0);
        $('#select_' + otherId).hide();

        $('#select_' + refuseId).show();
        $('select').selectbox({});

        // banger.deleteShielded('#refuseReason_' + refuseId);
        banger.verify('#refuseReason_' + refuseId, {name : 'required', tips : '拒绝原因必须选择'});



    });

    $(".refuseReason").change(function(){
        var val = $(this).val();
        refuseReason = val;
        if (val == '99') {
            banger.verify('#refuseRemark',[{ name: 'maxlength', tips: '“备注”内容过长' },{name : 'required', tips : '备注信息必须填写'}]);
        } else {
            banger.verify('#refuseRemark',[{ name: 'maxlength', tips: '“备注”内容过长' }]);
        }
    });

});

function saveRefuseInfo(){
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var module = $("#module").val();
    var postJson = getPostFields();
    postJson['refuseReason'] = refuseReason;
    postJson['module'] = module;
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loan/saveRefusePage.html',
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
    });
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('refuseLoanApply');
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