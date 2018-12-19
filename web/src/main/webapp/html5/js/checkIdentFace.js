$(function () {

    var srcs="";
    var isNext1 = false;
    $('#image').live('change',function () {
        setImagePreview1();
    });
    function setImagePreview1() {
        var file_head = document.getElementById("image"),
            picture = file_head.value;
        if (!picture.match(/.jpg|.gif|.png|.bmp|.jpeg/i)){
            isNext1 = false ;
            if(picture != ''){
                return alert("您上传的图片格式不正确，请重新选择！"); ! 1;
            }else{
                return ! 1;
            }
        }


        var img_src =window.navigator.userAgent.indexOf('Chrome') >= 1 || window.navigator.userAgent.indexOf('Safari') >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);

        var preview = document.getElementById("image_1");
        //alert(Orientation);
        preview.src = img_src;

        var Orientation = null;

        preview.onload = function () {
            //获取照片方向角属性，用户旋转控制
            EXIF.getData(preview, function() {
                EXIF.getAllTags(preview);
                Orientation = EXIF.getTag(preview, 'Orientation');
            });
        };
        var image = new Image();
        image.onload = function () {
            var canvas = document.getElementById("phone1");
            var expectWidth, expectHeight;

            // *) 最大宽度限制及缩小变化
            var MAX_WIDTH;

            // *) 确定目标的宽和高
            if ( Orientation == 6 || Orientation == 8 ) {
                expectWidth = image.height;
                expectHeight = image.width;
                MAX_WIDTH = 480;
            } else {
                expectWidth = image.width;
                expectHeight = image.height;
                MAX_WIDTH = 720;
            }

            if ( expectWidth > MAX_WIDTH ) {
                expectHeight = expectHeight * MAX_WIDTH / expectWidth;
                expectWidth = MAX_WIDTH;
            }
            canvas.width = expectWidth;
            canvas.height = expectHeight;

            var ctx = canvas.getContext("2d");

            if ( Orientation == 6 ) {
                // 顺时针90°
                ctx.save();
                ctx.translate(expectWidth/2, expectHeight/2);
                ctx.rotate(90 * Math.PI / 180.0);
                ctx.drawImage(image, -expectHeight/2, -expectWidth/2, expectHeight, expectWidth);
            } else if ( Orientation == 8 ) {
                // 逆时针90°
                ctx.save();
                ctx.translate(expectWidth/2, expectHeight/2);
                ctx.rotate(270 * Math.PI / 180.0);
                ctx.drawImage(image, -expectHeight/2, -expectWidth/2, expectHeight, expectWidth);
                ctx.restore();
            } else if ( Orientation == 3 ) {
                // 180°
                ctx.save();
                ctx.translate(expectWidth/2, expectHeight/2);
                ctx.rotate(Math.PI);
                ctx.drawImage(image, -expectWidth/2, -expectHeight/2, expectWidth, expectHeight);
                ctx.restore();
            } else {
                ctx.drawImage(image, 0, 0, expectWidth, expectHeight);
            }
        };

        setTimeout(function () {
            image.src = img_src;
        },500);

        isNext1 = true;

        if(isNext1 == true ){
            $(".nextStep").attr("disabled",false);
            $(".nextStep").css("background","#109add");
            $(".nextStep").css("color","#FFF");
        }else{
            $(".nextStep").attr("disabled",true);
            $(".nextStep").css("background","#dfdfdf");
            $(".nextStep").css("color","#b3b3b3");
        }
    }
    $("#nextStep").click(function(){

        var image_1 = document.getElementById("image_1");
        var fileImage1 = document.getElementById("image");
        var imageType1 = null;
        var imageName1 = null;
        var base1 = null;

        function getBase64Image(canvas) {
            var dataURL = canvas.toDataURL("image/jpeg");
            return dataURL;
        }
        imageType1 = fileImage1.value;
        var index11=imageType1.lastIndexOf(".");
        var index12=imageType1.length;
        imageType1=imageType1.substring(index11+1,index12);
        imageType1 = "." + imageType1;

        imageName1 = fileImage1.value;
        var arr1 = imageName1.split('\\');
        imageName1 = arr1[arr1.length-1];


        var canvas1 = document.getElementById("phone1");
        base1 = getBase64Image(canvas1);


        var interfaceType = 1; //
        var isSuccess= 0;//调用结果0失败，1成功
        var errorType  =0;//错误类型
        var errorMessage ="";
        var applyId=0;//申请信息ID
        var url ="../base/faceid/v2/verify.html?comparison_type="+1+'&idcard_name='+encodeURI(encodeURI(name))+"&idcard_number="+cardId;
        var id =null;//附件id
        var index = layer.open({type: 2 ,content: '正在识别人脸'});
        var formData = new FormData($("#img")[0]);
        $.ajax({
            url: url,
            type: 'POST',
            cache: false,
            dataType: 'json',
            data: {
                "imag":base1,
                "imagType":imageType1,
                "imagName":imageName1,
                "fileName":fileImage1.value
            },
            error:function (result) {
                result = JSON.parse(result.responseText);
                id =result.id;
                var ids =id+"";
                if (result.result_faceid==null || ''==result.result_faceid){
                    if (result.error_message!=null && ''!=result.error_message ){
                        errorMessage =result.error_message;
                    }else {
                        errorMessage ="程序异常";
                    }
                    intoInterFaceRecord(ids)
                    layer.close(index);
                    layer.open({
                        offset: '100px',
                        content: "<span style=\" font-size:0.42rem;\">"+'对比失败，请重新拍摄照片'+"</span>",
                        shade:false,
                        style:'background-color:rgba(52, 52, 52, 0.5);color:white;font-family:STHeiti; border:none;',
                        time:5
                    });
                }
//插入调用记录
                function intoInterFaceRecord(idd) {
                    var url='../api/insertIntefaceRecord.html';
                    var data={"interfaceType":interfaceType,"isSuccess":isSuccess,"errorType":errorType,"errorMessage":errorMessage,"applyId":applyId,"ids": idd};
                    var img =$("#image").val();
                    $.ajax({
                        type: "post",
                        url: url,
                        data: data,
                        async:false,
                        dataType: "json",
                        success:function (data) {
                            id5=data.requestId;
                        }
                    });
                }
            },
            success: function(result) {
                var id5=null;
                id =result.id;
                var ids =id+"";
                    isSuccess= 1;
                    intoInterFaceRecord(ids);
                    layer.close(index);
                    if(result.result_faceid.confidence>60){
                        var userAccount = $('#userAccount').val();
                        window.location.href = "../api/getApplyPerson.html?name=" + encodeURI(encodeURI(name)) + '&cardNum=' + $('#cardNum').val()+'&sex='+$('#sex').val()+'&address='+encodeURI(encodeURI($('#address').val()))+'&age='+$('#age').val()+'&phone='+$('#phone').val()+'&id1='+$('#id1').val()+'&id2='+$('#id2').val()+'&id3='+id+'&id4='+$('#id4').val()+'&id5='+id5+'&userAccount='+userAccount;
                    }else {
                        layer.open({
                            offset: '100px',
                            content: "<span style=\" font-size:0.42rem;\">"+'对比失败，请重新拍摄照片'+"</span>",
                            shade:false,
                            style:'background-color:rgba(52, 52, 52, 0.5);color:white;font-family:STHeiti; border:none;',
                            time:5
                        });
                    }
                //插入调用记录
                function intoInterFaceRecord(idd) {
                    var url='../api/insertIntefaceRecord.html';
                    var data={"interfaceType":interfaceType,"isSuccess":isSuccess,"errorType":errorType,"errorMessage":errorMessage,"applyId":applyId,"ids": idd};
                    var img =$("#image").val();
                    $.ajax({
                        type: "post",
                        url: url,
                        data: data,
                        async:false,
                        dataType: "json",
                        success:function (data) {
                            id5=data.requestId;
                        }
                    });
                }
            }
        });
    });


    $("#backstep").click(function () {
        window.history.back(-1);
    })
});