$(function(){
    // alert($("#isShow").val());
    if($("#isShow").val()!="true")
        initMaxlengthTips('#remark','#tips');
    banger.verify('#remark',{name: 'maxlength', tips: '内容过长'});
	// 提交新增数据源
	$('#btnSave').click(function() {
		saveTask();
	});
		
	//关闭
	$('#btnCancel,#btnSure').click(function() {
		var dialog = getDialog('editTask');
		dialog.close();
	});

});

function saveTask(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
       
	postJson['remark'] = jQuery.trim($('#remark').val());
	
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../task/updateTaskInfo.html',
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
    var dialog = getDialog('editTask');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshTaskList();    
    setTimeout(function() {
        dialog.close();
    }, 0);
}