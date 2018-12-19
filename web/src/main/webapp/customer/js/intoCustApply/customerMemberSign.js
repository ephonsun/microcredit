$(function(){
	//渲染下拉框
    $('select').selectbox({});

	$('#btnConfirm').click(function() {
		saveSign('saveClose');
	});
	
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('customerMemberSign');
		dialog.close();
	});

});

function saveSign(opType){
    var postJson = getPostFields();
    	
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../intoCustApplyInfo/intoCustMemberSign.html',
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
    var dialog = getDialog('customerMemberSign');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshMarketCustomerGridList();
    setTimeout(function() {
        dialog.close();
    }, 0);
}