$(function(){
    var $list=$("#thelist");
    // //校验
    banger.verify('#loanResultContent', {name : 'required', tips : '结果必须填写！'},checkMonitorContentRule);
    // banger.verify('#loanRevisitType',checkFileNumRule);

    $('#btnSave').click(function() {
       alert("btnSave");
    });

    //渲染下拉框
    $('select').selectbox();

    var uploader = WebUploader.create({
        // swf文件路径
        auto: true, // 选完文件后，是否自动上传
        swf: '../system/WebUploader/Uploader.swf', // swf文件路径
        server: '../loanMonitorInfo/uploadFile.html?loanId='+$("#loanId").val()+"&monitorId="+$("#monitorId").val(), // 文件接收服务端
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id:'#picker1',
            multiple:false,
            innerHTML :"<img src='../uploadify/attach_select.png' style='width:100%;height:100%;'/>"
        },
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false,
        //可重复上传
        duplicate :true
    });

    // 当有文件被添加进队列的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.append( '<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>' );
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

    uploader.on( 'uploadSuccess', function( file ) {
        $(".ui-text").removeClass("ui-text-failed");
        $( '#'+file.id ).find('p.state').text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });
});

//关闭
$('#btnCancel').click(function () {
   var dialog = getDialog("addLoanMonitorInfo");
   diglog.close();
});