$(function(){
	//校验 
	banger.verify('#tableDisplayName',[
	     {name: 'required', tips: '自定义表名必须填写'},
	     {name: 'specialAccount', tips: '必须由中英文、数字或下划线组成'},
	     checkTableDisplayNameRule]);
	
	$('#btnUpdate').click(function() {
		saveAutoTableInfo();
	});
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('editTableInfoPage');
		dialog.close();
	});

});
function saveAutoTableInfo(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanTypeRelTable/updateTableInfo.html',
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
//名字合法性校验  
var checkTableDisplayNameRule = {
	name : 'checkTableDisplayNameRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../loanTypeRelTable/checkTableDisplayNameRule.html';
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
//关闭dialog
function closeDialog(){
    var dialog = getDialog('editTableInfoPage');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
