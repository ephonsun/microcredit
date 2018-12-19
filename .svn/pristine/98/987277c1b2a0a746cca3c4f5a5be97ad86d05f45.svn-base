$(function(){
	//渲染下拉框
    $('select').selectbox({});
    // 加载操作时间
    $('#txtPlanTime').datepicker({
    	dateFmt: 'yyyy-MM-dd HH:mm'
    });
          
	initMaxlengthTips('#planRemark','#tips');
	//校验 
	banger.verify('#txtPlanTime',{name: 'required', tips: '联系时间必须填写'});
	banger.verify('#planRemark',{ name: 'maxlength', tips: '日程备注内容过长' });

	// 提交新增数据源
	$('#btnSave').click(function() {
		saveSchedule('saveClose');
	});
	

	// 提交新建数据源数据并继续新建
	$('#btnContinue').click(function() {
		saveSchedule('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('addSchedule');
		dialog.close();
	});

});

function saveSchedule(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    
	postJson['planRemark'] = jQuery.trim($('#planRemark').val());
	
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../schedule/addCustSchedule.html',
        data : postJson,
        success : function(result) {
        	if(result.success){
        		showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if(opType == 'saveContinue'){
                	reset();
                }else{
                	closeDialog();
                }
        	}else{
        		showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
        	}
        }

    });
}


//重置
function reset(){
	$("#form")[0].reset();
	initMaxlengthTips('#planRemark','#tips');
	var dialog = getDialog('addSchedule');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshScheduleList();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('addSchedule');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshScheduleList();    
    setTimeout(function() {
        dialog.close();
    }, 0);
}