$(function() {
    $('#btnSaveMode').click(function () {
        btnSaveMode();
    });
    $('#btnCloseMode').click(function () {
        closeTab();
    });
    var $list=$("#thelist2");
    banger.verify('#modeName' , [{name : 'required', tips : '模板名称必填'},
                                {name: 'maxlength' , tips : '模板名称超出长度' }]);
    var _script = '../modeConfigFile/uploadFiles.html';

    var uploader = WebUploader.create({
        auto: true, // 选完文件后，是否自动上传
        swf: '../system/WebUploader/Uploader.swf', // swf文件路径
        server: _script, // 文件接收服务端
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker2',
        fileSizeLimit: 1024*1024,
        duplicate :true,
        multi: false,
        fileNumLimit: 1,
        accept:{ extensions: 'xlsx'}
    });

    // 当有文件被添加进队列的时候
    uploader.on( 'fileQueued', function( file ) {
        // var id = file.id + new Date()
        $list.append( '<div id="' + file.id + '" class="item" style="margin-top: 4px">' +
            '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class="info"  style="font-size: 13px;">' + file.name + '&nbsp;<a id="'+file.id+file.id+'"  ><strong style="color: #0B61A4">移除</strong></a></label>' +
            '</div>' );
        $('#'+file.id+file.id).on("click", function(){
            uploader.removeFile(file);
            $("#filePath").val('');
            $("#importTable").html('');
            $("#modeName").val('');
            $('#'+file.id).empty();
        });
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo( $li ).find('.progress-bar');
        }
        $li.find('p.state').text('上传中');
        $percent.css( 'width', percentage * 100 + '%' );
    });
    uploader.on( 'uploadSuccess', function( file,response) {
        $("#filePath").val(response.cause);
        $("#importTable").html(response.value);



        var fileName = file.name;
        fileName = fileName.replace('.' + file.ext, '');
        $("#modeName").val(fileName);
        $("input[type='text']").eq(0).focus();
        $("input[type='text']").eq(0).blur();
        $("#modeName").focus();
        $("#modeName").blur();
        $( '#'+file.id ).find('p.state').text('已上传');
    });
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });
    uploader.on( 'error', function( msg ) {
        var content = '';
        if ("F_DUPLICATE" == msg) {
            content = '已经上传过该文件';
        } else if ("Q_TYPE_DENIED" == msg) {
            content = '只允许上传后缀名为.xlsx的2007版Excel';
        }  else if ("Q_EXCEED_NUM_LIMIT" == msg) {
            content = '只允许一个文件';
        }   else if ("Q_EXCEED_SIZE_LIMIT" == msg) {
            content = '文件大小请不要超过1M';
        }  else {
            content = msg;
        }
        showConfirm({
            content: content
        });
    });

});

function btnSaveMode() {
    var bool = banger.verify('#modeName');
    if (!bool) {
        return false;
    }
   var filePath = $("#filePath").val();
    var modeName = $("#modeName").val();
    var id = $("#id").val();
    if (!filePath) {
        showConfirm({
            content: '请先选择模板文件'
        });
        return false;
    }
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../modeConfigFile/checkFileName.html',
        data : {"modeName": modeName, "id": id},
        success : function(result) {
            if(result.success){
                showConfirm({
                    content: '模板名称已存在'
                });
                return false;
            } else {
                jQuery.ajax({
                    type: 'post',
                    dataType: 'json',
                    url: '../modeConfigFile/saveConfigFiles.html',
                    data: {
                        "filePath":filePath,
                        "id": id,
                        "modeName": modeName
                    },
                    async: false,
                    success: function (result) {
                        if (result.success) {
                            showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });
                            closeTab();
                        } else {
                            showConfirm({
                                icon: 'warning',
                                content: result.cause
                            });
                        }
                    }
                });
            }
        }
    });

}

//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.reloadList){
        win.reloadList();
    }
    tabs.close(tabs.getSelfId(window));
}
