$(function(){
	//校验 
	banger.verify('#dictName',[
	     {name: 'required', tips: '字典值必须填写'},
	     // {name: 'specialAccount', tips: '字典值必须由中英文、数字或下划线组成'},
	     // {name: 'specialAccountKuohao', tips: '字典值必须由中英文、数字、下划线或括号组成'},
	     checkDictNameRule]);

	banger.verify('#dictCode',checkDictCodeRule);
	// 提交新增字典值
	$('#btnSave').click(function() {
		saveDataDictCol('saveClose');
	});
	
	// 提交修改字典值
	$('#btnUpdate').click(function() {
		updateDataDictCol();
	});

	// 提交新建字典值并继续新建
	$('#btnContinue').click(function() {
		saveDataDictCol('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('dataDictColHandle');
		dialog.close();
	});

});

function updateDataDictCol(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysDataDictCol/updateDataDictCol.html',
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

function saveDataDictCol(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysDataDictCol/insertDataDictCol.html',
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

//字典值合法性校验  (长度校验 重复性校验)
var checkDictNameRule = {
	name : 'checkDictNameRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../sysDataDictCol/checkDictNameRule.html';
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
//字典编码合法性校验  (长度校验 重复性校验)
var checkDictCodeRule = {
	name : 'checkDictCodeRule',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../sysDataDictCol/checkDictCodeRule.html';
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
	$("#form")[0].reset();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('dataDictColHandle');
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
