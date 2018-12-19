$(function() {
    var win = tabs.getIframeWindow("transitionPictures");
    var img_src ='../apk/start.jpg?t=' + Math.random()*10000;
    $("#myimg").attr('src',img_src);
    var uploader = WebUploader.create({
        auto: true, // 选完文件后，是否自动上传
        swf: '../system/WebUploader/Uploader.swf', // swf文件路径
        server: '../transitionPictures/uploadImg.html', // 文件接收服务端
        pick: '#imgPicker', // 选择文件的按钮。可选
        // 只允许选择图片文件。
        fileSingleSizeLimit: 1*1024*1024, //大小
        accept: {
            title: 'Images',
            extensions: 'jpg,jpeg,png',
            mimeTypes: 'image/*'
        }
    });
    uploader.on( 'uploadSuccess', function( file,response) {
            setTimeout(function() {
                win.location.reload(true);
            }, 1000);
    });

    uploader.on( 'fileQueued', function( file ) {
        var $list = $("#fileList"),
            $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
                '</div>'
            ),
            $img = $li.find('img');


        // $list为容器jQuery实例
        $list.append( $li );
    });


// 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

// 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });

})

function initImgDiv() {
        var url = '../apk/start.jpg?num='+Math.random();
        var imgStr = "<img id='myimg' width='258' height='461' " +
            "src=" + url + " '/>";
        $('#imagePreview_').prepend(imgStr);
}
