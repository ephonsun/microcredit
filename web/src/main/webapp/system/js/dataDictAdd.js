$(function(){
    //初始化控件
    layout.initForms();
	//校验 
	banger.verify('#dictCnName',[
	     {name: 'required', tips: '数据源名称必须填写'},
	     {name: 'chinese', tips: '数据源名称必须由中文组成', rule: /^[\u4E00-\u9FA5\uF900-\uFA2D]+$/},
		 // {name: 'specialAccount', tips: '字典值必须由中英文、数字或下划线组成'},
        checkDataCnNameRule]);
	/*banger.verify('#dictEnName',[
	      {name: 'required', tips: '数据源编码必须填写'},
	      {name: 'account', tips: '数据源编码必须由数字、字母或下划线组成'},
	      checkDataEnNameRule]);*/

	// 提交新增数据源
	$('#btnSave').click(function() {
		saveDataDict('saveClose');
	});
	
	// 提交修改数据源
	$('#btnUpdate').click(function() {
		updateDataDict();
	});

	// 提交新建数据源数据并继续新建
	$('#btnContinue').click(function() {
		saveDataDict('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('dataDictHandle');
		dialog.close();
	});

});

function updateDataDict(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysDataDict/updateDataDict.html',
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

function saveDataDict(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysDataDict/insertDataDict.html',
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

//数据源名称合法性校验  (长度校验)
var checkDataCnNameRule = {
	name : 'checkDataCnNameRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../sysDataDict/checkDataCnNameRule.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			dataType:'json',
			async : false,
			data : data,
			success : function(result) {
				bool = result.success;
				rule.tips = result.cause;
			}
		});
		return bool;
	}
};

//数据源编码合法性校验  (长度 重复性校验)
var checkDataEnNameRule = {
	name : 'checkDataEnNameRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../sysDataDict/checkDataEnNameRule.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			dataType:'json',
			async : false,
			data : data,
			success : function(result) {
				bool = result.success;
				rule.tips = result.cause;
			}
		});
		return bool;
	}
};

//重置
function reset(){
    var win = tabs.getIframeWindow('sysDataDict');
    win.location.reload(true);
	$("#form")[0].reset();
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

// 回刷父页
/*function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
}*/
