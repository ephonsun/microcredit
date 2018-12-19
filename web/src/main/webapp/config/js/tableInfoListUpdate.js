$(function(){
	//校验 
	banger.verify('#tableDisplayName',[
		{name: 'required', tips: '显示名必须填写'},
		{name: 'specialAccount', tips: '必须由中英文、数字或下划线组成'},
		checkTableInfoNameRule]);
	
	// 提交修改
	$('#btnUpdate').click(function() {
		updateTableInfo();
	});

	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('editTableTemplate');
		dialog.close();
	});

});

function updateTableInfo(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../tableTemplate/updateTableInfo.html',
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
var checkTableInfoNameRule = {
	name : 'checkTableInfoNameRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../tableTemplate/checkTableInfoNameRule.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			dataType:'json',
			async : false,
			data : data,
			success : function(result) {
				bool = result.success;
				if(!bool){
				showConfirm({
					content: result.cause
				});}
			}
		});
		return bool;
	}
};
$("#tableDisplayName").keypress(function (e) {
	  if (e.which == 13) {
		updateTableInfo();
	    return false;    
	  }
	});

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
