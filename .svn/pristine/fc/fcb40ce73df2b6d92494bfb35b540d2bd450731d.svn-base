$(function(){
	//渲染下拉框
    $('select').selectbox({});

	$('#btnConfirm').click(function() {
		saveSign('saveClose');
	});
	
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('customerGroupSign');
		dialog.close();
	});

});

function saveSign(opType){
    var postJson = getPostFields();
    	
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../intoCustApplyInfo/intoCustGroupSign.html',
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
    var dialog = getDialog('customerGroupSign');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshList();
    setTimeout(function() {
        dialog.close();
    }, 0);
}