var ImgCls =
    {
        Obj: null,

        //按给定的宽和高进行智能缩小
        Resize: function (nWidth, nHeight) {
            var w, h, p1, p2;
            //计算宽和高的比例
            p1 = nWidth / nHeight;
            p2 = ImgCls.Obj.width / ImgCls.Obj.height;

            w = 0;
            h = 0;
            if (p1 < p2) {
                //按宽度来计算新图片的宽和高
                w = nWidth;
                h = nWidth * ( 1 / p2 );
            }
            else {
                //按高度来计算新图片的宽和高
                h = nHeight;
                w = nHeight * p2;
            }
            ImgCls.Obj.width = w;
            ImgCls.Obj.height = h;
        },

        //按给定的宽和高进行固定缩小(会出现图片变形情况)
        //http://bizhi.knowsky.com/
        ResizedByWH: function (nWidth, nHeight) {
            ImgCls.Obj.width = nWidth;
            ImgCls.Obj.height = nHeight;
        },

        //按给定的宽进行等比例缩小
        ResizedByWidth: function (nWidth) {
            var p = nWidth / ImgCls.Obj.width;
            ImgCls.Obj.width = nWidth;
            ImgCls.Obj.height = parseInt(ImgCls.Obj.height * p);
        },

        //按给定的高进行等比例缩小
        ResizedByHeight: function (nHeight) {
            var p = nHeight / ImgCls.Obj.height;
            ImgCls.Obj.height = nHeight;
            ImgCls.Obj.width = parseInt(ImgCls.Obj.width * p);
        },

        //宽和高按百分比缩小
        ResizedByPer: function (nWidthPer, nHeightPer) {
            ImgCls.Obj.width = parseInt(ImgCls.Obj.width * nWidthPer / 100);
            ImgCls.Obj.height = parseInt(ImgCls.Obj.height * nHeightPer / 100);
        }
    };
$(function () {
    //渲染下拉框
    $('select').selectbox({});
    //初始化控件
    layout.initForms();

    //上传附件初始化(单个文件最大10M，队列中同时最多存在5个文件，文件类型为图片)
    var uploadImageType = '.jpg|.jpeg|.png';
    setImageUpload('../prodProductFile/uploadImage.html', 1024 * 1024 * 5, 10, uploadImageType);
    var uploadVideoType = '.mp4|.avi';
    setVideoUpload('../prodProductFile/uploadVideo.html', 1024 * 1024 * 50, 1, uploadVideoType);

    initMaxlengthTips('#productInfo', '#tips');
    //校验
    banger.verify('#productCode', [
        {name: 'required', tips: '产品代码必须填写'},
        checkProductCode]);
    banger.verify('#productName', [
        {name: 'required', tips: '产品名称必须填写'},
        checkProductName]);
    banger.verify('#productType', {name: 'required', tips: '请选择产品类型'});

    // 提交新增数据源
    $('#btnSave').click(function () {
        if(checkUploadComplete()){
            saveProduct('saveClose');
        }else{
            showConfirm({
                icon: 'warning',
                content: '请等待上传完成'
            });
        }
    });

    // 提交修改数据源
    $('#btnUpdate').click(function () {
        if(checkUploadComplete()){
            updateProduct();
        }else{
            showConfirm({
                icon: 'warning',
                content: '请等待上传完成'
            });
        }
    });

    // 提交新建数据源数据并继续新建
    $('#btnContinue').click(function () {
        if(checkUploadComplete()){
            saveProduct('saveContinue');
        }else{
            showConfirm({
                icon: 'warning',
                content: '请等待上传完成'
            });
        }
    });

    //关闭
    $('#btnCancel').click(function () {
        var dialog = getDialog('productHandle');
        dialog.close();
    });
    initImgDiv();
});

function checkUploadComplete() {
    var success = true;
    $(".percentage").each(function () {
        var test = $(this).text();
        if(test.indexOf("100%") < 0) {
            success = false;
            return false;
        }
    });
    return success ;
}
function initImgDiv() {
    var total = $("#total").val();
    var i = 0;
    for (; i < total; i++) {
        fileId = $("#fileId_" + i).val();
        var url = '../prodProductFile/downloadFile.html?download=0&ppfId=' + fileId;
        var imgStr = "<img width='20' height='10' onclick=lookPhoto('" + fileId + "'); " +
            "src=" + url + " onload='ImgThumbnail(this).ResizedByPer(320,480);'/>";
        $('#imagePreview_' + i).prepend(imgStr);
    }
}

function ImgThumbnail(im) {
    ImgCls.Obj = ( im && typeof im == 'object' ) ? im : document.getElementById(im);
    return ImgCls;
}

function lookPhoto(fileId) {
    var url = "../prodProductFile/downloadFile.html?download=2&ppfId=" + fileId;
    showDialog({
        id: 'vPhotoDiv',
        title: '图片原图',
        url: url,
        width: 850,
        height: 600
    });
}

function downloadFile(fileId) {
    window.location = "../prodProductFile/downloadFile.html ?download=1&ppfId=" + fileId;
}

function deleteFile(fileId) {
    var deleteFileDiv = document.getElementById('deleteFileInfo');
    var deleteFileId = document.createElement("input");
    deleteFileId.setAttribute("name", "deleteFileIds");
    deleteFileId.setAttribute("value", fileId);
    deleteFileId.setAttribute("type", "hidden");
    deleteFileDiv.appendChild(deleteFileId);
    $('#' + fileId).remove();
}

function checkProductInfoLen() {
    var info = $("#productInfo").val();
    if (info.length > 500) {
        showConfirm({
            content: '产品描述内容不能大于500个字！'
        });
        return false;
    }
    return true;
}

function updateProduct() {
    if (!banger.verify('.ui-form-fields') || !checkProductInfoLen()) {
        return false;
    }
    var postJson = getPostFields();
    var fileIdImages = [];
    var fileIdVideos = [];
    var fileSizeImages = [];
    var fileSizeVideos = [];
    var deleteFileIds = [];

    $('input[name=fileIdImages]').each(function () {
        fileIdImages.push($(this).val());
    });
    $('input[name=fileIdVideos]').each(function () {
        fileIdVideos.push($(this).val());
    });
    $('input[name=fileSizeImages]').each(function () {
        fileSizeImages.push($(this).val());
    });
    $('input[name=fileSizeVideos]').each(function () {
        fileSizeVideos.push($(this).val());
    });
    $('input[name=deleteFileIds]').each(function () {
        deleteFileIds.push($(this).val());
    });
    postJson["fileIdImages"] = fileIdImages.join(",");
    postJson["fileIdVideos"] = fileIdVideos.join(",");
    postJson["fileSizeImages"] = fileSizeImages.join(",");
    postJson["fileSizeVideos"] = fileSizeVideos.join(",");
    postJson["deleteFileIds"] = deleteFileIds.join(",");

    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../prodProduct/updateProduct.html',
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

function saveProduct(opType) {
    if (!banger.verify('.ui-form-fields') || !checkProductInfoLen()) {
        return false;
    }
    var postJson = getPostFields();
    var fileIdImages = [];
    var fileIdVideos = [];
    var fileSizeImages = [];
    var fileSizeVideos = [];

    $('input[name=fileIdImages]').each(function () {
        fileIdImages.push($(this).val());
    });
    $('input[name=fileIdVideos]').each(function () {
        fileIdVideos.push($(this).val());
    });
    $('input[name=fileSizeImages]').each(function () {
        fileSizeImages.push($(this).val());
    });
    $('input[name=fileSizeVideos]').each(function () {
        fileSizeVideos.push($(this).val());
    });
    postJson["fileIdImages"] = fileIdImages.join(",");
    postJson["fileIdVideos"] = fileIdVideos.join(",");
    postJson["fileSizeImages"] = fileSizeImages.join(",");
    postJson["fileSizeVideos"] = fileSizeVideos.join(",");

    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../prodProduct/insertProduct.html',
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if (opType == 'saveContinue') {
                    reset();
                } else {
                    closeDialog();
                }
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

var checkProductCode = {
    name: 'checkProductCode',
    tips: '',
    rule: function () {
        var rule = this, bool = true, data = getPostFields();
        var url = '../prodProduct/checkProductCode.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            async: false,
            data: data,
            success: function (result) {
                bool = result.success;
                rule.tips = result.cause;
            }
        });
        return bool;
    }
};

var checkProductName = {
    name: 'checkProductName',
    tips: '',
    rule: function () {
        var rule = this, bool = true, data = getPostFields();
        var url = '../prodProduct/checkProductName.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            async: false,
            data: data,
            success: function (result) {
                bool = result.success;
                rule.tips = result.cause;
            }
        });
        return bool;
    }
};


//重置
function reset() {
    // $("#form")[0].reset();
    // $(".uploadifyQueueItem").remove();
    // $("#flag").nextAll().remove();
    window.location.href = "../prodProduct/getProductInsertPage.html";
}

//关闭dialog
function closeDialog() {
    var dialog = getDialog('productHandle');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function () {
        dialog.close();
    }, 0);
}

// 回刷父页
/*function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
}*/

