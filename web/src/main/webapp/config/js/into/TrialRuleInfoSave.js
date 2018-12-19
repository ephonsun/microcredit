$(function(){
	//渲染下拉框
    $('select').selectbox({});
	//初始化控件
    layout.initForms();
    
	//initMaxlengthTips('#picRemark','#tips');
	//校验 
	banger.verify('#ruleName',{name: 'required', tips: '规则名称必须填写'});
	// 提交新增数据源
    var issave =1;
	$('#btnSave').click(function() {
	    if ($("#ruleName").val()!=""&& issave==1)
        {
            saveIntentCustomer('saveClose');
        }else{
        }

	});
	//检验唯一性
    $("#ruleName").change(function () {
        jQuery.ajax({
            type : 'post',
            dataType:'json',
            url : '../trialRuleInfo/checkTrialRuleInfoNmae.html',
            data : {"ruleName":$("#ruleName").val(),"ruleId":$("#ruleId").val()},
            success : function(result) {
                if(result.success){
                    issave=1;
                }else{
                    issave=0;
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    })


	// 提交新建数据源数据并继续新建
	$('#btnContinue').click(function() {
		saveIntentCustomer('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
        var dialog;
	    if($("#isedit").val()==0)
        {
            dialog = getDialog('trialRuleInfoHandle');
        }else {
            dialog = getDialog('editTrialRuleInfo');
        }


		dialog.close();
	});

});
function saveIntentCustomer(opType){
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../trialRuleInfo/InsertTrialRuleInfoInsert.html',
        data : {"ruleName":$("#ruleName").val(),"ruleId":$("#ruleId").val()},
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
    //initMaxlengthTips('#picRemark','#tips');
}

//关闭dialog
function closeDialog(){
    var dialog;
    if($("#isedit").val()==0)
    {
        dialog = getDialog('trialRuleInfoHandle');
    }else {
        dialog = getDialog('editTrialRuleInfo');
    }
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

// 回刷父页
/*function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
}*/

