$(function () {
    banger.verify('#accessPath',[{name: 'url', tips: '设置的连接格式不正确'}]);


    var isNext1 = true;
    $("#imageInput").live('change', function() {setImagePreview1(this);});
    function setImagePreview1(file) {
        var file_head = document.getElementById("imageInput"),
            picture = file_head.value;
        if ((!picture.match(/.jpg|.png|.bmp|.jpeg/i))) {

            if(picture != ''){
                isNext1 = false;
                return alert("您上传的图片格式不正确，请重新选择！"); ! 1;
            }else{
                return ! 1;
            }
        }else{
            isNext1 = true;
        }
    }
    $("#btnSave").click(function(){
        if (!banger.verify('.ui-form-fields')) {
            return false;
        }
        if(!isNext1){
            return false;
        }
        var formData = new FormData($("#img")[0])
        var accessPath = document.getElementById("accessPath").value;
        // formData.append("accessPath",accessPath);
        $.ajax({
            url: '../qrcodeSetting/updateqrcodeSetting.html?accessPath=' + accessPath,
            type: 'POST',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function(){
                // window.location.href = "../qrcodeSetting/createQrCode.html";
                var url = '../qrcodeSetting/createQrCode.html';
                showDialog({
                    id: 'qrshow',
                    title: '二维码展示',
                    url: url,
                    width: 450,
                    height: 325,
                    tabId: tabs.getSelfId(window)
                });
            }
        });
    });
    $("#btnCancel").click(function(){
        tabs.close(tabs.getSelfId(window));
    })
    $("#backstep").click(function() {
        window.history.back( - 1);
    })
})
