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
            if(picture != ''){
                return alert("您上传的图片格式不正确，请重新选择！"); ! 1;
            }else{
                return ! 1;
            }
        }
        //var preview = document.getElementById("image_1");
        var img_src = window.navigator.userAgent.indexOf('Chrome') >= 1 || window.navigator.userAgent.indexOf('Safari') >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);

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
            if(picture != ''){
                return alert("您上传的图片格式不正确，请重新选择！"); ! 1;
            }else{
                return ! 1;
            }
        }

        //var preview = document.getElementById("image_2");
        var img_src = window.navigator.userAgent.indexOf('Chrome') >= 1 || window.navigator.userAgent.indexOf('Safari') >= 1 ? window.webkitURL.createObjectURL(file_head.files[0]) : window.URL.createObjectURL(file_head.files[0]);

        var preview = document.getElementById("image_2");
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
            var canvas = document.getElementById("phone2");
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
        var image_1 = document.getElementById("image_1");
        var image_2 = document.getElementById("image_2");
        var fileImage1 = document.getElementById("image");
        var fileImage2 = document.getElementById("image2");
        var imageType1 = null;
        var imageType2 = null;
        var imageName1 = null;
        var imageName2 = null;
        var base1 = null;
        var base2 = null;
        function getBase64Image(canvas) {
            //var canvas = document.getElementById("phone1");
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

        imageType2 = fileImage2.value;
        var index21=imageType2.lastIndexOf(".");
        var index22=imageType2.length;
        imageType2 = imageType2.substring(index21+1,index22);
        imageType2 = "." + imageType2;

        imageName2 = fileImage2.value;
        var arr2 = imageName2.split('\\');
        imageName2 = arr2[arr2.length-1];


        var canvas1 = document.getElementById("phone1");
        var canvas2 = document.getElementById("phone2");
        base1 = getBase64Image(canvas1);
        base2 = getBase64Image(canvas2);

        $.ajax({
            url: '../base/upLoadFile.html' ,
            type: 'POST',
            data: {
                "imag":base1,
                "imagType":imageType1,
                "imagName":imageName1,
                "fileName":fileImage1.value,
                "imag2":base2,
                "imag2Type":imageType2,
                "imag2Name":imageName2
            },
            dataType: 'json',
            success: function(result) {

                if(result.success==true){
                    var userAccount = $('#userAccount').val();
                    window.location.href="../api/getCheckIdent1.html?phone="+phone+'&id1='+result.id1+'&id2='+result.id2+'&userAccount='+userAccount;
                }
            }
        });

    });

    $("#backstep").click(function() {
        window.history.back( - 1);
    })
});