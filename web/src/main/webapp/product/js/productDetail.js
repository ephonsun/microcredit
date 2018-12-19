var ImgCls =
{
    Obj: null,

    //按给定的宽和高进行智能缩小
    Resize: function (nWidth, nHeight) {
        var w , h , p1 , p2;
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
$(function(){
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('productHandle');
		dialog.close();
	});
	initImgDiv();
});

function initImgDiv() {
    var total = $("#total").val();
    var i = 0;
    for (; i < total; i++) {
    	fileId = $("#fileId_" + i).val();
        var url = '../prodProductFile/downloadFile.html?download=0&ppfId=' + fileId;
        var imgStr = "<img width='20' height='10'  onclick=lookPhoto('"+fileId+"'); " +
            "src=" + url + " onload='ImgThumbnail(this).ResizedByPer(320,480);'/>";
        $('#imagePreview_'+i).prepend(imgStr);
    }
}

function ImgThumbnail(im) {
    ImgCls.Obj = ( im && typeof im == 'object' ) ? im : document.getElementById(im);
    return ImgCls;
}

function lookPhoto(fileId){
    var url = "../prodProductFile/downloadFile.html?download=2&ppfId="+fileId;
    showDialog({
        id: 'vPhotoDiv',
        title : '图片原图',
        url : url,
        width : 850,
        height : 600
    });
}

//重置
function reset(){
	$("#form")[0].reset();
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('productHandle');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
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
function downloadFile(fileId){
    window.location="../prodProductFile/downloadFile.html?download=1&ppfId=" + fileId;
}
