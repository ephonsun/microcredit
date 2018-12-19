$(function(){
	//渲染下拉框
    $('select').selectbox({});
	//初始化控件
    layout.initForms();
	//校验
	banger.verify('#userId',{name: 'required', tips: '请选择分配人员'});
});

	// 提交新增数据源
	$('#btnSure').click(function() {
		savePotentialToAllot();
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('allotPotentialaCust');
		dialog.close();
	});

function savePotentialToAllot(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
	jQuery.ajax({
		// url : '../loanApproveFlow/saveApproveCondition.html',
		url : '../custPotentialCustomers/saveAllotPotentialaCust.html',
		type : 'POST',
		dataType : 'json',
		data : postJson,
		sync : false,
		success : function(result) {
			if (result.success) {
				showConfirm({
					icon: 'succeed',
					content: result.cause
				});
				closeDialog();
			}else{
				showConfirm({
					icon : 'warning',
					content : '操作失败'
				});
			}
		}
	});
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('allotPotentialaCust');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

