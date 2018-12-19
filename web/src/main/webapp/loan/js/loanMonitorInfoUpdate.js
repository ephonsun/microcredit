$(function(){
    var $list=$("#thelist");
    // //校验
    banger.verify('#loanResultContent', {name : 'required', tips : '结果必须填写！'},checkMonitorContentRule);
    banger.verify('#createDate', {name : 'required', tips : '日期必须填写！'},checkMonitorContentRule);
    banger.verify('#loanRevisitType', {name : 'required', tips : '回访类型必须填写！'},checkMonitorContentRule);
    $('#btnUpdate').click(function() {
        updateMonitorInfo();
    });

    //新增保存
    $('#btnAddSave').click(function () {
        addMonitorInfo();
    });

    //加载时间
    $('#createDate').datepicker();

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


//编辑保存
function updateMonitorInfo(){
    if (checkFileNumRule()||!banger.verify('.ui-form-fields')) {
        return false;
    }
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../loanMonitorInfo/updateLoanMonitorInfo.html',
        data : {
            loanRevisitType:$("#loanRevisitType").find("option:selected").text(),
            loanMonitorState:$("#loanMonitorState").find("option:selected").text(),
            loanResultContent:$("#loanResultContent").val(),
            id:$("#monitorId").val()
        },
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog('editMonitorInfo');
        }
    });
}

//保存新增监控
function  addMonitorInfo() {
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    $.ajax({
        type : 'POST',
        url : '../loanMonitorInfo/addLoanMonitorInfo.html?loanId='+$("#loanId").val(),
        data : {
            loanAddRevisitType : $('#loanAddRevisitType').find("option:selected").val(),
            loanMonitorState : $('#loanMonitorState').find("option:selected").val(),
            loanResultContent : $('#loanAddResultContent').val(),
            createDate : $('#createDate').val(),
            monitorId : $("#monitorId").val()
        },
        success : function () {
            showConfirm({
                icon : 'succeed',
                content : '操作成功'
            });
            closeDialog('addMonitorInfo');
        }
    });
}

//编辑监控窗口关闭
$('#btnCancel').click(function() {
    var dialog = getDialog('editMonitorInfo');
    dialog.close();
});

//新增窗口关闭
$('#btnAddCancel').click(function () {
   var dialog = getDialog('addMonitorInfo');
   dialog.close();
});

function downloadFile(id){
    window.location="../loanMonitorInfo/downloadFile.html?id=" + id+"&random="+Math.random()*10000;
}

function deleteFile(id){
    jQuery.ajax({
        type : 'post',
        url : '../loanMonitorInfo/deleteFile.html?id='+id,
        data : {},
        success : function() {
            $("#div_"+id).remove();
        }
    });
}

//关闭dialog
function closeDialog(monitorInfo){
    var dialog = getDialog(monitorInfo);
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

var checkMonitorContentRule = {
    name : 'checkMonitorContentRule',
    tips : '请输入结果!',
    rule : function() {
        var rule = this, bool = true
        if($("#loanMonitorState").find("option:selected").text() == "已完成"){
            if(!$("#loanResultContent").val()){
                return false;
            }
        }
    }
};

// var checkFileNumRule = {
//     name : 'checkFileNumRule',
//     tips : '实地回访必须上传附件',
//     rule : function() {
//         var rule = this, bool = true;
//         var url = '../loanMonitorInfo/checkFileNumRule.html';
//         jQuery.ajax({
//             type : 'post',
//             url : url,
//             dataType:'json',
//             async : false,
//             data : {"monitorId":$("#monitorId").val()},
//             success : function(result) {
//                 bool = result.success;
//             }
//         });
//         if($("#loanRevisitType").find("option:selected").text() == '实地回访'){
//            return bool;
//         }else{
//             return true;
//         }
//     }
// };

function checkFileNumRule(){
        var bool = true;
        var url = '../loanMonitorInfo/checkFileNumRule.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            dataType:'json',
            async : false,
            data : {"monitorId":$("#monitorId").val()},
            success : function(result) {
                bool = result.success;
            }
        });
        if($("#loanRevisitType").find("option:selected").text() == '实地回访'){
            if(bool==false){
            showConfirm({
                content: '实地回访必须上传附件'
            });
                return true;
            }
            //实地回访有附近
            return false;
        }else{
            return false;
        }

};