$(function () {
    $(".nextStep").attr("disabled",false);
    $(".nextStep").css("background","#109add");
    $(".nextStep").css("color","#FFF");
    //判断是否为空
    var message_ss="";//弹出信息
    var flag_notNull=false;

    function jx() {
        var loanNeed=$("#loanNeed").val();
        var intoUse=$('#loanUserOption').val();
        var rule=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
        if(loanNeed!=""&&intoUse!=""){
            if(!rule.test(loanNeed)){
                message_ss="贷款需求必须为数字";
                flag_notNull=false;
            }else{
                flag_notNull=true;
            }
        }else {
            message_ss="必填项不能为空";
            flag_notNull=false;
        }

    }

    // var postJson = getPostFieldsForTemplate('#intoBaseForm');
    // var postJsonString = JSON.stringify(postJson);
    // var deleteIdJson = JSON.stringify(deleteIds);



    $('#apply').click(function () {
        jx();
        //是否打开下一步的开关
        if(flag_notNull==true){
            var userAccount = $('#userAccount').val();
            jQuery.ajax({
                type : 'post',
                url : '../api/saveIntoInfo.html',
                data:{"name":$('#name').val(),"sex":$('#sex').val(),
                    "cardNum":$('#cardNum').val(),"address":$('#address').val(),
                    "age":$('#age').val(),"phone":$('#phone').val(),
                    "loanUserOption":$('#loanUserOption').val(),"applyAmount":$('#loanNeed').val(),
                    "id1":$('#id1').val(),"id2":$('#id2').val(),"id3":$('#id3').val(),
                    "id4":$('#id4').val(),"id5":$('#id5').val(),"userAccount":userAccount},
                success:function (data) {
                    layer.open({
                        time:10000,
                        offset: '100px',
                        shadeClose: false,
                        shade:'background-color: rgba(0,0,0,0.3)',
                        btn: ['确定'],
                        content: "<i style=\" font-size:0.42rem;\">"+'申请成功'+"</i>",
                        yes: function(index){
                            window.location.href="http://www.baihang-china.com";
                        },
                        style:'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                    });
                }

            });


        }else{
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

    });


    $("#backstep").click(function () {
        window.history.back(-1);
     })
    });

