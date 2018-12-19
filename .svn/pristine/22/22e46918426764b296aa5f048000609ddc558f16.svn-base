$(function(){
	$('select').selectbox({});
	
	//是否点击确定
	var isDetermined = false;
	
	banger.verify('#modelName',[{name: 'required', tips: '请填写模型名'},{name: 'specialAccount', tips: '贷款类型必须由中英文、数字或下划线组成'}]);
	banger.verify('#sysModelLevelSelect',[{name: 'required', tips: '请选择分级基础'}]);
	
	$('#determine').click(function() {
		determineSelect();
	});
	$('#btnSave').click(function() {
		saveModelManagement();
	});
	//关闭
	$('#btnCancel').click(function() {
		tabs.close(tabs.getSelfId(window));
	});

});


//点击确定按钮
function determineSelect(){
	var dataDictId = $("#sysModelLevelSelect").val();
	var modelName = $("#modelName").val();
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	isDetermined = true;
	//禁用下拉框和确认按钮
	$("#sysModelLevelSelect").attr({"disabled":"disabled"});
	$("#determine").attr({"disabled":"disabled"});
	//启用确认取消按钮
	$("#btnSave").show();
	$("#btnCancel").show();
	//查询数据
	var name_ = $("#sysModelLevelSelect").find("option:selected").text();

	$('#modelConfigList').flexigrid({
		height: 'auto',
		url: '../sysDataDictCol/queryDataDictColListData.html?dataDictId='+dataDictId,
		fields: [
		         //获取下拉框中的text
		         { display: name_, field: 'dictName', width: 500 ,align: 'center'},
		         { display: '参考值', field: 'configValue', width: 500 ,align: 'center'}
		         ],
		extendCell: {
	 		'configValue': function(value,row){
	 		 var _configValue = '<input type="text" class="configValues" class="ui-text-text" name="configValues"/>'+
	 		 '<input class="configKeys" type="hidden" value='+row.cols.dictName+' /></input>'
	 		 return _configValue;
	 		}
	     },
		 usePage: false
		});
}
//保存模型
function saveModelManagement(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	
	var postJson = getPostFields();
	var configKeys = [];
    var configValues = [];
	
    $(".configValues").each(function(){
    	configValues.push($(this).val());
    });
    $(".configKeys").each(function(){
    	configKeys.push($(this).val());
    });
    postJson["configKeys"]= configKeys.join(",");
    postJson["configValues"]= configValues.join(",");
    var dictCnName = $("#sysModelLevelSelect").find("option:selected").text()
    jQuery.ajax({
        type : 'post',
        url : '../modelManagement/saveModelManagement.html?dictCnName='+encodeURI(encodeURI(dictCnName)),
        data : postJson,
        success : function() {
			showConfirm({
				icon: 'succeed',
				content: '保存成功'
			});
			//选中主页面，并刷新
			tabs.click('modelManagement');
			tabs.refresh('modelManagement');
			tabs.close(tabs.getSelfId(window));
        }
    });
}



