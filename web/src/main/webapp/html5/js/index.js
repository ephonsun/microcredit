// var isOpen=true;
$(function() {
    $(".nextStep").attr("disabled",false);
    $(".nextStep").css("background","#109add");
    $(".nextStep").css("color","#FFF");
    $("#vaileBtn").attr("disabled",false);
    $("#vaileBtn").attr("class","vaileBtn")
    var flag = false;
    var message_ss="";
    function invokeSettime(obj){
        var countdown=60;
        settime(obj);
        function settime(obj) {
            if (countdown == 0) {
                $(obj).attr("disabled",false);
                $(obj).text("重新获取");
                $(obj).attr("class","vaileBtn")
                countdown = 60;
                return;
            } else {
                $(obj).attr("disabled",true);
                $(obj).text(countdown + "秒");
                $(obj).attr("class","vaileBtn_1")
                countdown--;
            }
            setTimeout(function() {
                    settime(obj) }
                ,1000)
        }
    }

    if(isOpen){
        $("#vaileBtn").click(function(){
            $("#vaileCode").val('');
            vailePhone();
            if(flag){
                jQuery.ajax({
                    type : 'post',
                    url : "../api/getMsg.html",
                    async : false,
                    data : {"phone":$("#phoneNum").val()},
                    success : function(result) {
                        new invokeSettime("#vaileBtn");
                    }

                });

            }else {
                layer.open({
                    time:3,
                    offset: '100px',
                    shade:false,
                    content: "<i style=\" font-size:0.42rem;\">"+message_ss+"</i>",
                    style:'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                });
            }
        });
    }
    //监测手机号码输入
    // $("#phoneNum").keyup(
    //     function () {
    //         if (this.value.length==11)
    //         {
    //             vailePhone()
    //         }else {
    //             $(".nextStep").attr("disabled",true);
    //             $(".nextStep").css("background","#dfdfdf");
    //             $(".nextStep").css("color","#b3b3b3");
    //             $("#vaileBtn").attr("disabled",true);
    //             $("#vaileBtn").attr("class","vaileBtn_1")
    //         }
    //     }
    // )




    /*校验手机号*/
    function vailePhone (){
        var phone = $("#phoneNum").val();
        var message = "";
        var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(phone == ''){
            flag = false;
            message_ss = "手机号码不能为空！";
        }else if(phone.length !=11){
            flag = false;
            message_ss = "请输入有效的手机号码！";
        }else if(!myreg.test(phone)){
            message_ss = "请输入有效的手机号码！";
            flag = false;
        }else{
            flag = true;
        }
    }

//自定义弹框 错误信息提醒
//     function Toast(msg,duration){
//         duration=isNaN(duration)?3000:duration;
//         var m = document.createElement('div');
//         m.innerHTML = msg;
//         m.style.cssText="width:80%; min-width:5rem; background:#CCC; opacity:0.5; height:0.8rem; color:#F00; line-height:0.8rem; text-align:center; border-radius:0.2rem; position:fixed; top:9%; left:10%; z-index:999999; font-weight:bold; font-size:0.5rem;";
//         document.body.appendChild(m);
//         setTimeout(function() {
//             var d = 0.5;
//             m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
//             m.style.opacity = '0';
//             setTimeout(function() { document.body.removeChild(m) }, d * 1000);
//         }, duration);
//     }
    $("#nextStep").click(function() {
        if ($("#phoneNum").val().length == 11) {
            vailePhone()
        } else {
            flag = false;
            message_ss = "请输入正确手机号码";
        }
        if (isOpen) {
            //如果没有开启验证码 就不监测
            if (flag) {
                var isNumber = /^[0-9]+$/;
                if (isOpen) {
                    if ($("#vaileCode").val().length == 4) {
                        if (isNumber.test($("#vaileCode").val())) {
                            flag = true;
                        } else {
                            flag = false;
                            message_ss = "验证码格式错误";
                        }

                    } else {
                        flag = false;
                        message_ss = "验证码长度错误";
                    }
                }
                if (flag) {
                    jQuery.ajax({
                        type: 'post',
                        dataType: 'json',
                        url: "../api/checkCode.html",
                        async: false,
                        data: {
                            "phone": $("#phoneNum").val(),
                            "checkCode": $("#vaileCode").val()
                        },
                        success: function(result) {
                            if (result.success == false) {
                                layer.open({
                                    time: 3,
                                    offset: '100px',
                                    shade: false,
                                    content: "<i style=\" font-size:0.42rem;\">" + result.cause + "</i>",
                                    style: 'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                                });
                            } else if (result.success == true) {
                                var userAccount = $('#userAccount').val();
                                window.location.href = "../api/getIdCardPage.html?phone="+$('#phoneNum').val()+'&userAccount='+userAccount;
                            }
                        }
                    });
                } else {
                    layer.open({
                        time: 3,
                        offset: '100px',
                        shade: false,
                        content: "<i style=\" font-size:0.42rem;\">" + message_ss + "</i>",
                        style: 'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                    });
                }

            }
        }else{
            if(flag){
                var userAccount = $('#userAccount').val();
                window.location.href = "../api/getIdCardPage.html?phone="+$('#phoneNum').val()+'&userAccount='+userAccount;
            }else{
                layer.open({
                    time: 3,
                    offset: '100px',
                    shade: false,
                    content: "<i style=\" font-size:0.42rem;\">" + message_ss + "</i>",
                    style: 'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                });
            }
        }
    });

})