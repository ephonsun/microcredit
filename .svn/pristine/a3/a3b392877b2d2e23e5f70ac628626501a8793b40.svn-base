$(function(){
	$('select').selectbox({});
	$('#modelConfigList').flexigrid({
		height: 'auto',
		url: '../modelConfig/queryModelConfig.html?modelId='+$("#modelId").val(),
		fields: [
		         //获取下拉框中的text
		         { display: $("#sysModelLevelSelect").find("option:selected").text(), field: 'configKey', width: 500 ,align: 'center'},
		         { display: '参考值', field: 'configValue', width: 500 ,align: 'center'}
		         ],
	     extendCell: {
	 		'configValue': function(value,row){
	 		 var _configValue = '<input type="text" class="configValues" class="ui-text-text" value='+row.cols.configValue+' />'+
	 		 '<input class="configIds" type="hidden" value='+row.id+' /></input>'
	 		 return _configValue;
	 		}
	     },
		 usePage: false
	});
	//校验 
	banger.verify('#modelName',[
	     {name: 'required', tips: '模型名称必须填写'},
	     {name: 'specialAccount', tips: '模型名称必须由中英文、数字或下划线组成'},
	     ]);
	
	$('#btnSave').click(function() {
		updateModelManagement();
	});
	//关闭
	$('#btnCancel').click(function() {
		tabs.close(tabs.getSelfId(window));
	});

});
function updateModelManagement(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
	var configKeys = [];
    var configValues = [];
	
    $(".configValues").each(function(){
    	configValues.push($(this).val());
    });
    $(".configIds").each(function(){
    	configKeys.push($(this).val());
    });
    postJson["configIds"]= configKeys.join(",");
    postJson["configValues"]= configValues.join(",");
    
    jQuery.ajax({
        type : 'post',
        url : '../modelManagement/updateModelManagement.html?modelId='+$("#modelId").val(),
        data : postJson,
        success : function() {
			showConfirm({
				icon: 'succeed',
				content: '操作成功'
			});
			//选中主页面，并刷新
			tabs.click('modelManagement');
			tabs.refresh('modelManagement');
			tabs.close(tabs.getSelfId(window));
			
        }
    });
}
