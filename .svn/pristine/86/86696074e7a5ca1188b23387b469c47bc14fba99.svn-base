$(function(){
    $('select').selectbox({});
    //校验
	banger.verify('#loanTypeName',[
	     {name: 'required', tips: '贷款类型必须填写'},
	     {name: 'specialAccount', tips: '贷款类型必须由中英文、数字或下划线组成'},
	     checkLoanTypeNameRule
	]);

    // banger.verify('#loanClassId',[
    //     {name: 'required', tips: '三表类型必须选择'}]);

	$('#btnUpdate').click(function() {
		updateLoanType();
	});
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('editTableTemplate');
		dialog.close();
	});

});
function updateLoanType(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanType/updateLoanType.html',
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
//名字校验
var checkLoanTypeNameRule = {
	name : 'checkLoanTypeNameRule',
	tips : '贷款类型已经存在！',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
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


//重置
function reset(){
	$("#form")[0].reset();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('editTableTemplate');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

