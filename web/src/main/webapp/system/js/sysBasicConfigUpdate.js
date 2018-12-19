$(function(){
	// 提交修改基础数据
	$('#btnUpdate').click(function() {
        updateBasicConfig();
	});

	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('dataDictHandle');
		dialog.close();
	});

    //刷新
    $('#btnRefresh').click(function(){
        $('#sysBasicConfig').flexReload();
    });

    //刷新
    $('#btnRefresh').click(function(){
        $('#functionSwitch').flexReload();
    });

    //刷新
    $('#btnRefresh').click(function(){
        $('#interfaceSwitch').flexReload();
    });
});

function updateBasicConfig(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysBasicConfig/updateBasicConfig.html',
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
    var dialog = getDialog('dataDictHandle');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
