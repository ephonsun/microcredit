$(function(){
	//渲染下拉框
    $('select').selectbox({});
	//初始化控件
    layout.initForms();
    
	initMaxlengthTips('#picRemark','#tips');
	//校验 
	banger.verify('#picName',{name: 'required', tips: '客户姓名必须填写'});
	banger.verify('#picGender',{name: 'required', tips: '请选择性别'});
	banger.verify('#picCertificateType',{name: 'required', tips: '请选择证件类型'});
	banger.verify('#picCertificateNum',[{name: 'required', tips: '证件号码必须填写'},{ name: 'certificateNum', tips: '证件号码只能由英文或数字组成', rule: /^[a-zA-Z0-9]+$/ },checkCertificate]);
	banger.verify('#picPhone',[{name: 'required', tips: '联系电话必须填写'},{ name: 'phoneNum', tips: '证件号码只能由英文或数字组成', rule: /^[a-zA-Z0-9]+$/ }]);

	// 提交新增数据源
	$('#btnSave').click(function() {
		saveIntentCustomer('saveClose');
	});

	// 提交新建数据源数据并继续新建
	$('#btnContinue').click(function() {
		saveIntentCustomer('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('intentCustomerHandle');
		dialog.close();
	});

});

var checkCertificate = {
	name : 'checkCertificate',
	tips : '身份证只允许输入15位或者18位数字或字母',
	rule : function(obj) {
		var rule = this, bool = true;
		var type = $('#picCertificateType').find("option:selected").text();
		if(type == '身份证' || type == '居民身份证'){
			var num = $('#picCertificateNum').val();
			var ret = /^\w{15}$|^\w{18}$/;
			if(!ret.test(num)){
				bool = false;
		    }
		}
		return bool;
	}                   
}

function checkRemarkLen(){
	var remark = $("#picRemark").val();
	if(remark.length>500){
        showConfirm({
            content: '意向说明内容不能大于500个字！'
        });
        return false;
    }
	return true;
}

function saveIntentCustomer(opType){
    if (!banger.verify('.ui-form-fields') || !checkRemarkLen()) {
        return false;
    }
    var postJson = getPostFields();
    //postJson['picProductName'] = $('#picProductId option:selected').text();
    
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../prodIntentCustomer/insertIntentCustomer.html',
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
    initMaxlengthTips('#picRemark','#tips');
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('intentCustomerHandle');
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

