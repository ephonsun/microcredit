// 页面加载完成时...
$(function(){
	$('#modelConfigList').flexigrid({
        height: 500,
        url: '../modelConfig/queryModelConfig.html?'+$("#modelId").val(),
        fields: [
        	{ display: '客户等级', field: 'configKey', width: 200 ,align: 'center'},
        	{ display: '参考值', field: 'configValue', width: 150 ,align: 'center'}
        ],
//        extendCell: {
//    		'configValue': function(value,row){
//    		var configValue = '<input class="configValue" type = \'text\' value='+row.cols.configValue+' ></input>'+
//    		'<input class="configId" type="hidden"  ids='+row.id+' /></input>';
//    		 return configValue;
//    		}
//        },
//        usePage: false
    });
	
});