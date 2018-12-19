// 页面加载完成时...
$(function(){
    //渲染下拉框
    $('select').selectbox({});
    //=================================================================================================================
    $('select').change(function () {
       changeSelect($(this).attr("name"),$(this).val());
    });
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../functionSwitch/functionSwitchList.html',
        data : {},
        success : function(result) {
          //  alert(result.rows[0].cols.basicConfigKey);
            var length  = result.rows.length;
            for (var i =0;i<length;i++){

                addOptions(result.rows[i].id,  result.rows[i].cols.basicConfigKey, result.rows[i].cols.configStatus);
            }

        }
    });

    //=================================================================================================================
    // $('#functionSwitch').flexigrid({
		// //width:  500,
    //     height: 500,
    //     url: '../functionSwitch/functionSwitchList.html',
    //     fields: [
    //     	{ display: '名称', field: 'basicConfigName', width: 300 ,align: 'center'},
    //     	{ display: '开启状态', field: 'configStatus', width: 300 ,align: 'center'}
    //     ],
    //     action: {
    //     	display: '操作',
    //     	width: 200,
    //     	align: 'center',
    //     	buttons: [
    //             {
    //                 display: '开启',
    //                 onClick: function(tr, data){
    //                     functionSwitch(data,"open");
    //                 },
    //                 isDisabled: function(data){
    //                     return data.cols.configStatus == "开启";
    //                 }
    //             },
    //             {
    //                 display: '关闭',
    //                 onClick: function(tr, data){
    //                     functionSwitch(data,"close");
    //                 },
    //                 isDisabled: function(data){
    //                     return data.cols.configStatus == "关闭";
    //                 }
    //             }
    //         ]
    //     },
    //     usePage: false
    // });

	//添加下拉选项
    function  addOptions(id,basicConfigKey,configStatus) {
        $("#"+basicConfigKey).attr("name",id);
        if (configStatus=="关闭"){
            $("#"+basicConfigKey).append('<option value ="1"  >开启</option>');
            $("#"+basicConfigKey).append('<option value ="2"  selected="selected" >关闭</option>');
        }else if (configStatus=="开启"){
            $("#"+basicConfigKey).append('<option value ="1"  selected="selected" >开启</option>');
            $("#"+basicConfigKey).append('<option value ="2"   >关闭</option>');
        }
        $('select').selectbox({});
    }
    //开启关闭
    function changeSelect(id,status){
        jQuery.ajax({
            type : 'post',
            dataType:'json',
            url : '../functionSwitch/updateFunctionSwitch.html',
            data : {
                basicConfigId:id,
                configStatus:status
            },
            success : function(result) {
                if(result.success){
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                  //  $('#functionSwitch').flexReload();
                }else{
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });

    }
    // //开启功能开关
    // function functionSwitch(data,type){
    // 	var configStatus = "1";
    // 	if("open"===type){
    //         configStatus = "1";
		// }else{
    //         configStatus = "2";
		// }
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../functionSwitch/updateFunctionSwitch.html',
    //         data : {
    //             basicConfigId:data.id,
    //             configStatus:configStatus
		// 	},
    //         success : function(result) {
    //             if(result.success){
    //                  showConfirm({
    //                     icon: 'succeed',
    //                     content: result.cause
    //                 });
    //                 $('#functionSwitch').flexReload();
    //             }else{
    //                 showConfirm({
    //                     icon: 'warning',
    //                     content: result.cause
    //                 });
    //             }
    //         }
    //     });
    // }
});
