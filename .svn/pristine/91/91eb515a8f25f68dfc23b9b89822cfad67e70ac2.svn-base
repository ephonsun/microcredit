$(function () {
    var isNext1 = false;
    var isNext2 = false;
    $(".nextStep").attr("disabled",false);
    $(".nextStep").css("background","#109add");
    $(".nextStep").css("color","#FFF");
    // $("#personName").blur(function(){
    //
    // })
    // $("#idCard").blur(function(){
    //
    // })
    var message_ss="";//弹出信息
    $("#nextStep").click(function () {
        //填写校验规则
        if($("#personName").val()!= ''){
            isNext1 = true;
        }else{
            message_ss="请填写姓名";
            isNext1 = false;
        }

        if($("#idCard").val()!= ''){
            // debugger
            var length = $("#idCard").val().length;
            var val = $("#idCard").val();
            if (length == 15) {
                var regx =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
                if(regx.test(val))
                {
                    isNext2 = true;

                }else {
                    message_ss="身份证号不正确，请重新输入";
                    isNext2 = false;
                }
            } else if (length == 18) {
                var regx = /^(\d{6})(19|20)(\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\d{3})(\d|X|x)?$/;
                if(regx.test(val))
                {

                    isNext2 = true;

                }else {
                    message_ss="身份证号不正确，请重新输入";
                    isNext2 = false;
                }
            }else{
                message_ss="请填写正确身份证号";
                isNext2 = false;
            }
        }else{
            message_ss="请填写身份证号";
            isNext2 = false;
        }
        if(isNext1 == true && isNext2 ==true){
            var userAccount = $('#userAccount').val();
            window.location.href = "../api/getApplyPerson.html?phone="+$('#phone').val()+'&id1='+$('#id1').val()+'&id2='+$('#id2').val()+'&name='+encodeURI(encodeURI($('#personName').val()))+'&cardNum='+$('#idCard').val()+'&userAccount='+userAccount; //跳转url
        }else{

           //弹框
            layer.open({
                time:3,
                offset: '100px',
                shade:false,
                content: "<i style=\" font-size:0.42rem;\">"+message_ss+"</i>",
                style:'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
            });
        }
    })
    $("#backstep").click(function () {
        window.history.back(-1);
    })

});