$(function() {
    var isNext1 = false;
    var isNext2 = false;

    $("#image").live('change', function() {setImagePreview1();});
    $("#image2").live('change', function() {setImagePreview2();});
    function setImagePreview1() {
        var file_head = document.getElementById("image"),
            picture = file_head.value;
        if (!picture.match(/.jpg|.png|.bmp|.jpeg/i)) {
            isNext1 = false;
            return alert("您上传的图片格式不正确，请重新选择！"); ! 1;
        }
        var preview = document.getElementById("image_1");
        preview.src = window.navigator.userAgent.indexOf('Chrome') >= 1 || window.navigator.userAgent.indexOf('Safari') >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);
        isNext1 = true;
        if (isNext1 == true && isNext2 == true) {
            $(".nextStep").attr("disabled", false);
            $(".nextStep").css("background", "#109add");
            $(".nextStep").css("color", "#FFF");

        } else {
            $(".nextStep").attr("disabled", true);
            $(".nextStep").css("background", "#dfdfdf");
            $(".nextStep").css("color", "#b3b3b3");
        }
    }

    function setImagePreview2() {
        var file_head = document.getElementById("image2"),
            picture = file_head.value;
        if (!picture.match(/.jpg|.png|.bmp|.jpeg/i)) {
            isNext2 = false;
            return alert("您上传的图片格式不正确，请重新选择！"),
                !1;
        }

        var preview = document.getElementById("image_2");
        preview.src = window.navigator.userAgent.indexOf('Chrome') >= 1 || window.navigator.userAgent.indexOf('Safari') >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);
        isNext2 = true;
        if (isNext1 == true && isNext2 == true) {
            $(".nextStep").attr("disabled", false);
            $(".nextStep").css("background", "#109add");
            $(".nextStep").css("color", "#FFF");

        } else {
            $(".nextStep").attr("disabled", true);
            $(".nextStep").css("background", "#dfdfdf");
            $(".nextStep").css("color", "#b3b3b3");
        }

    }
    $("#next").click(function() {
        var interfaceType = 0; //ocr
        var isSuccess = 0; //调用结果0失败，1成功
        var errorType = 0; //错误类型
        var errorMessage = "";
        var applyId = 0; //申请信息ID
        var id1=0;//正面照片
        var id2=0;//反面照片
        var id4=null;
        var index = layer.open({type: 2 ,content: 'ORC正在识别'});

        var formData = new FormData($("#img")[0]);
        $.ajax({
            url: '../api/faceid/v1/ocridcard.html?legality=' + 0,
            type: 'POST',
            data: formData,
            // async: false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function(result) {
                 id1=result.id1;
                 id2=result.id2;
                var name=null;
                var cardNum=null;
                var sex=null;
                var address=null;
                var year=null;
                var ids=null;
                ids=id1+","+id2;
                layer.close(index);

                if (result.name != null && result.id_card_number != null&&""!==result.name&&""!==result.id_card_number) {
                    name=result.name;
                    sex=result.gender;
                    cardNum=result.id_card_number;
                    address=result.address;
                    year=result.birthday.year;
                    isSuccess = 1;
                    intoInterFaceRecord(ids);

                     window.location.href = "../api/getCheckIdent.html?name=" + encodeURI(encodeURI(name)) + '&cardNum=' + cardNum+'&sex='+encodeURI(encodeURI(sex))+'&address='+encodeURI(encodeURI(address))+'&year='+year+'&phone='+phone+'&id1='+id1+'&id2='+id2+'&id4='+id4;

                } else {

                    if (result.error == null || '' == result.error) {
                        errorMessage = "IMG_NOT_CLEAR";
                    } else {
                        errorMessage = result.error;
                    }



                    intoInterFaceRecord(ids);
                    layer.open({
                        offset: '100px',
                        content: "<i style=\" font-size:0.42rem;\">" + '请确认身份证照片是否清晰后再试' + "</i>",
                        shade: false,
                        style: 'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;',
                        time: 4
                    });

                }
                //插入调用记录正面
                function intoInterFaceRecord(idd) {
                    var url = '../api/insertIntefaceRecord.html';
                    var data = {
                        "interfaceType": 0,//ocr
                        "isSuccess": isSuccess,
                        "errorType": errorType,
                        "errorMessage": errorMessage,
                        "applyId": applyId,
                        "ids":idd
                    };
                    $.ajax({
                        type: "post",
                        url: url,
                        data: data,
                        async:false,
                        dataType: "json",
                        success: function(data) {
                             id4=data.requestId;
                        }
                    });
                }

            }
        });

    });

    $("#backstep").click(function() {
        window.history.back( - 1);
    })
});