// 页面加载完成时...
$(function(){
	$('#interfaceSwitch').flexigrid({
		//width:  500,
        height: 500,
        url: '../interfaceSwitch/interfaceSwitchList.html',
        fields: [
        	{ display: '名称', field: 'basicConfigName', width: 300 ,align: 'center'},
        	{ display: '开启状态', field: 'configStatus', width: 300 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 200,
        	align: 'center',
        	buttons: [
                {
                    display: '开启',
                    onClick: function(tr, data){
                        interfaceSwitch(data,"open");
                    },
                    isDisabled: function(data){
                        return data.cols.configStatus == "开启";
                    }
                },
                {
                    display: '关闭',
                    onClick: function(tr, data){
                        interfaceSwitch(data,"close");
                    },
                    isDisabled: function(data){
                        return data.cols.configStatus == "关闭";
                    }
                }
            ]
        },
        usePage: false
    });

    //接口开关
    function interfaceSwitch(data,type){
    	var configStatus = "1";
    	if("open"===type){
            configStatus = "1";
		}else{
            configStatus = "2";
		}
        jQuery.ajax({
            type : 'post',
            dataType:'json',
            url : '../functionSwitch/updateFunctionSwitch.html',
            data : {
                basicConfigId:data.id,
                configStatus:configStatus
			},
            success : function(result) {
                if(result.success){
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                    $('#interfaceSwitch').flexReload();
                }else{
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    }
});
