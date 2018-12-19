$(function(){
    banger.verify('#loanTypeRelTableSelect',[
        {name: 'required', tips: '已全部添加，请关闭！'}]);
    //渲染下拉框
    $('#loanTypeRelTableSelect').selectbox({});
	//校验
	banger.verify('#loanTypeName',[
	     {name: 'required', tips: '贷款类型必须填写'},
	     ]);
	
	$('#btnSave').click(function() {
		saveLoanTypeRelTable();
	});
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('LoanTypeRelTableHandle');
		dialog.close();
	});

});
//保存
function saveLoanTypeRelTable(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanTypeRelTable/saveLoanTypeRelTable.html',
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
    var dialog = getDialog('LoanTypeRelTableHandle');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
