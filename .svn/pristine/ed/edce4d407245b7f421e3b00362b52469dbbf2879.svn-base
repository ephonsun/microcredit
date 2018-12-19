$(function(){
    $('select').selectbox({});
    //校验
	banger.verify('#fieldColumnDisplay',[
	     {name: 'required', tips: '显示名称必须填写'},
	     {name: 'specialAccount', tips: '必须由中英文、数字或下划线组成'},
	     checkfieldColumnNameRule
	]);

    banger.verify('#fieldDictName',[
        {name: 'required', tips: '必须选择数据源'}
    ]);

	// 提交修改
	$('#btnUpdate').click(function() {
		updateTableInfo();
	});

	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('editTableColumn');
		dialog.close();
	});
});

function updateTableInfo(){
	if($("#fieldConstraintRule").val() == "positiveInteger"){
		banger.verify('#defaultValue',[
			{name: 'positiveInteger', tips: '默认值必须为正整数'}
		]);
	}else if($("#fieldConstraintRule").val() == "integer"){
		banger.verify('#defaultValue',[
			{name: 'integer', tips: '默认值必须为整数'}
		]);
	}

	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
	postJson["fieldDictName"] = $("#fieldDictName").val();
	var fieldConstraintRule = $("#fieldConstraintRule").val();
	var defaultValueDate = $("#defaultValueDate").val()
	if (null!=fieldConstraintRule  && '' != fieldConstraintRule){

        postJson["fieldConstraintRule"] = fieldConstraintRule;
	}
	if("0"==defaultValueDate || "1"==defaultValueDate || "2"==defaultValueDate || ""==defaultValueDate){
        postJson["defaultValue"] = defaultValueDate;
	}
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../tableColumn/updateTableColumn.html',
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

// 名字校验
var checkfieldColumnNameRule = {
	name : 'checkfieldColumnNameRule',
	tips : '字段显示名称已经存在，请重新输入',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../tableColumn/checkfieldColumnNameRule.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			dataType:'json',
			async : false,
			data : data,
			success : function(result) {
				bool = result.success;
				if(!bool){
				}
			}
		});
		return bool;
	}
};


//关闭dialog
function closeDialog(){
    var dialog = getDialog('editTableColumn');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

