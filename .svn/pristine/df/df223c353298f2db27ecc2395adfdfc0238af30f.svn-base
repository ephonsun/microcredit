// 页面加载完成时...
$(function(){
    //渲染下拉框
    $('select').selectbox({});
    //=================================================================================================================
    $('select').change(function () {
       changeSelect($(this).attr("name"),$(this).val(),this.id);
    });
    /*jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../mapParamsSet/updateMapParams.html',
        data : {},
        success : function(result) {
          //  alert(result.rows[0].cols.basicConfigKey);
            var length  = result.rows.length;
            for (var i =0;i<length;i++){

                addOptions(result.rows[i].id,  result.rows[i].cols.basicConfigKey, result.rows[i].cols.configStatus);
            }

        }
    });*/

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
    function changeSelect(id,status,key){
        var data = {};
        data.basicConfigId = id;
        if("qzdw" == key){
            data.configStatus = status;
        }else{
            data.configValue = status;
        }

        jQuery.ajax({
            type : 'post',
            dataType:'json',
            url : '../mapParamsSet/updateMapParams.html',
            data : data,
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
});
