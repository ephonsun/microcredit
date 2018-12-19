$(function(){
	//$('select').selectbox({});
	$('#modelMatchProjectList').flexigrid({
		height: 'auto',
		width:425,
		url: '',
		fields: [
		         //获取下拉框中的text
		         { display: '匹配字段', field: 'projectName', width: 200 ,align: 'center'},
		         { display: '模型选择', field: 'modelSelect', width: 200 ,align: 'center'}
		         ],
//	     extendCell: {
//	    	 // "cdSysModelMatch" #options("cdSysModelMatch","","")
//	 		'modelSelect': function(value,row){
//	 		 var _configValue =
//	 		'<select class="sysModelSelect">'+
//	 		'#options("cdSysModelMatch","","")'
//	 		+'</select>';
//	 		 return _configValue;
//	 		}
//	     },
		 usePage: false
	});
	//校验 
	banger.verify('#modelName',[
	     {name: 'required', tips: '模型名称必须填写'},
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
    var postJson = {};
	var projectIds = [];
	var modelNames = [];
    var modelIds = [];
	
    $(".projectIds").each(function(){
        projectIds.push($(this).val());
    });
    $(".modelSelectList").each(function(){
        modelIds.push($(this).val());
        modelNames.push($(this).find("option:selected").text());
    });
    postJson["projectIds"]= projectIds.join(",");
    postJson["modelIds"]= modelIds.join(",");
    postJson["modelNames"]= modelNames.join(",");

    jQuery.ajax({
        type : 'post',
        url : '../modelMatch/saveModelMatchProjectList.html',
        data : postJson,
        success : function() {
			showConfirm({
				icon: 'succeed',
				content: '操作成功'
			});
			//选中主页面，并刷新
			 tabs.close(tabs.getSelfId(window));
			
        }
    });
}
