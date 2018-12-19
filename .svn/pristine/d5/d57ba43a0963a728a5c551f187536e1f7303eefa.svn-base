$(function () {
    $(".nextStep").attr("disabled",false);
    $(".nextStep").css("background","#109add");
    $(".nextStep").css("color","#FFF");
    //判断是否为空
    var message_ss="";//弹出信息
    var flag_notNull=true;
    // $(".notNull").each(function() {
    //     $(this).keyup(function () {
    //
    //
    //     })
    //
    // })
    $("#nextStep").click(function () {
        var number=true;//用来判断是否 有为空项
        var rule=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
        $(".notNull").each(function(){
            if ($(this).val()=="")
            {
                message_ss="必填项不能为空";
                number=false;
            }
        })
        $(".isNumber").each(function() {
                if ($(this).val() != "")
                {
                    if (!rule.test($(this).val()))
                    {
                        message_ss="请填写数字";
                        number=false;
                    }
                }
        })
        if (!number)
        {
            flag_notNull=false;
        }else {
            flag_notNull=true;
        }
        //是否打开下一步的开关
        if(flag_notNull){
            window.location.href = "../api/getIdCardPage.html"; //跳转url
        }else{
           //弹框
            //弹框
            layer.open({
                time:3,
                offset: '100px',
                shade:false,
                // shadeClose: false,
                content: "<i style=\" font-size:0.42rem;\">"+message_ss+"</i>",
                style:'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
            });
        }
    })
    $("#backstep").click(function () {
        window.history.back(-1);
    })
});