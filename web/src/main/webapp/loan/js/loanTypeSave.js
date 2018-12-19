$(function(){
    $('select').selectbox({});
    //校验
	banger.verify('#loanTypeName',[
	     {name: 'required', tips: '贷款类型必须填写'},
	     {name: 'specialAccount', tips: '贷款类型必须由中英文、数字或下划线组成'},
	     checkLoanTypeNameRule]);

    // banger.verify('#modeId',[{name: 'required', tips: '评分模型必须选择'}]);

	$('#btnSave').click(function() {
		saveLoanType();
	});
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('LoanTypeHandle');
		dialog.close();
	});

	$("#btnContinue").click(function(){
		saveContinue();
	});


});
function saveLoanType(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
	postJson['type']='loan';
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanType/saveLoanType.html',
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

//贷款类型名字合法性校验  (长度校验 重复性校验)
var checkLoanTypeNameRule = {
	name : 'checkLoanTypeNameRule',
	tips : '贷款类型已经存在！',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		debugger;
		data['type']='loan';
		var url = '../loanType/checkLoanTypeNameRule.html';
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
    var dialog = getDialog('LoanTypeHandle');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

function saveContinue(){

    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    // postJson['type']='loan';
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanType/saveLoanType.html?type=loan',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            var dialog = getDialog('LoanTypeHandle');
            var win = tabs.getIframeWindow(dialog.config.tabId);
            win.location.reload(true);
            $("#form")[0].reset();
            $('#allotTarget').val(0);
            $('#nallotTarget').val('团队成员');
            $('#isAutoAllot').removeAttr("disabled",true);
            $('#nisAutoAllot').removeAttr("style");
            $('#isAutoAllot').val(0);
            $('#nisAutoAllot').val('否');
        }
    });
}
