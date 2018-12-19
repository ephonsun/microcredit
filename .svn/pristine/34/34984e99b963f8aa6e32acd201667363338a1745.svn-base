$(function() {
    //取消
    $('#btnCancel').click(function () {
        var dialog = getDialog('selectDownload');
        dialog.close();
    });

    /*获得导出选择界面*/
    $('#btnDownload').click(function(data) {
        var url='../loanFile/getDownloadSelectPage.html?loanId='+$("#_loanId").val();
        showDialog({
            id: 'selectDownload',
            title: '选择导出类型',
            url: url,
            width: 400,
            height: 230,
            tabId: tabs.getSelfId(window)
        });
    });
    /*预览图片*/
    $('#previewPic').click(function(data) {
        var url='../loanFile/getPreviewPicPage.html?loanId='+$("#_loanId").val();
        showDialog({
            id: 'selectDownload',
            title: '照片预览',
            url: url,
            width: 800,
            height: 800,
            tabId: tabs.getSelfId(window)
        });
    });
    /*获得预览图片页面*/
    $('#previewImage').click(function(data) {
        var url='../loanFile/getPreviewImagePage.html?loanId='+$("#_loanId").val();
        showDialog({
            id: 'previewImage',
            title: '预览照片',
            url: url,
            width: 1000,
            height: 600,
            tabId: tabs.getSelfId(window)
        });
    });


    $('#btnSure').click(function () {
        var chk_value =[];
        $('input[name="checkFileType"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        if(chk_value.length==0) {
            showConfirm({
                content: '你还没有选择任何内容!'
            });
        }else{
            downloadFiles(chk_value);
        }

    });


    $('#btnFileRefresh').click(function(data) {
        refresh();
    });





    $('#fileList').flexigrid({
        height: 400,
        url: '../loanFile/queryLoanFile.html?loanId='+$("#_loanId").val(),
        dataType: 'json',
        //multiSelect: true,
        fields: [
            {display: '阶段', field: 'loanProcessType', width: 100, align: 'center',data:cdTypeName},
            {display: '资料类型', field: 'fileType', width: 100, align: 'center',data:cdFileType},
            {display: '资料分类', field: 'ownerName', width: 100, align: 'center'},
            {display: '子类', field: 'className', width: 100, align: 'center'},
            {display: '文件名', field: 'fileSrcName', width: 200, align: 'center'},
            {display: '上传时间', field: 'createDate', width: 200, align: 'center'}
        ],
        action: {
            display: '操作',
            width: 130,
            align: 'center',
            buttons: [
                {
                    display: '预览',
                    onClick: function (tr, data) {
                        preview(data);
                    },
                    isDisabled: function(data){
                        return data.cols.fileType == 'Video' || data.cols.fileType == 'Other' || data.cols.fileType == 'Audio' ;
                    }
                },
                {
                    display: '播放',
                    onClick: function (tr, data) {
                        play(data);
                    },
                    isDisabled: function(data){
                        return data.cols.fileType == 'Image' || data.cols.fileType == 'Other' ;
                    }
                },
                {
                    display: '下载',
                    onClick: function (tr, data) {
                        downloadFile(data.id);
                    }
                },
                {
                    display: '删除',
                    onClick: function (tr, data) {
                        deleteloanFile(data.id);
                    },
                    isDisabled: function(){
                        var isDelete = $('#isDelete').val();
                        if(isDelete == "true"){
                            return false;
                        }
                        return true;
                    }
                }
            ]
        },
        usePage: false
    });

    //预览
    function preview(data){
        jQuery.ajax({
            type : 'post',
            url : '../loanFile/checkFileExsit.html?id=' + data.id,
            dataType:'json',
            async : false,
            data : {},
            success : function(result) {
                if (!result.success) {
                    showConfirm({
                        content: '文件不存在!'
                    });
                    return;
                }
                lookPhoto(data);
            }
        });
    }

    function lookPhoto(data){
        var urlStr = "../loanFile/downloadFile.html?download=2%26id=" + data.id;
        //var className=data.cols.className;
        //if(className==''){
        //    className="未分类";
        //}
        //var r= window.open();
        //r.document.write("<title>"+className+"</title>"+"<img src="+url+" style='height: 480px;width: 270px;margin:0 auto'/>")
        var url = "../loanFile/showImageDiv.html?urlStr="+urlStr+"&random="+Math.random()*10000;
        showDialog({
            id: 'previewImage',
            title: '预览照片',
            url: url,
            width: 960,
            height: 500
        });
    }

    //播放
    function play(data){
        jQuery.ajax({
            type : 'post',
            url : '../loanFile/checkFileExsit.html?id=' + data.id,
            dataType:'json',
            async : false,
            data : {},
            success : function(result) {
                if (!result.success) {
                    showConfirm({
                        content: '文件不存在!'
                    });
                    return;
                }


                // playVideo(data.cols.fileType,result.value);
                var v=result.value;
                if(data.cols.fileType=='Video'){
                    videoPlayer("播放视频","../loanFile/playVideoFile.html?id="+data.id);
                }else{
                    audioPlayer("播放录音","../loanFile/playVideoFile.html?id="+data.id);
                }

            }
        });
    }

    function downloadFile(id){
        jQuery.ajax({
            type : 'post',
            url : '../loanFile/checkFileExsit.html?id=' + id,
            dataType:'json',
            async : false,
            data : {},
            success : function(result) {
                if (!result.success) {
                    showConfirm({
                        content: '文件不存在!'
                    });
                    return;
                }
                window.location = "../loanFile/downloadFile.html?download=1&id=" + id +'&random='+Math.random()*10000;
            }
        });
    }

    function downloadFiles(chk_value){
        var loanId=$("#loanId").val();
        var fileType=chk_value;
        jQuery.ajax({
            type: 'get',
            url: '../loanFile/getLoanInfoIds.html?loanId='+loanId+'&fileType='+fileType,
            success: function (result) {
                if(!result){
                    showConfirm({
                        content: '当前贷款没有该类型资料！'
                    });
                    return;
                }
                var _ids = result;
                window.parent.location.href="../loanFile/downloadFiles.html?ids="+_ids+"&loanId="+loanId+"&fileType="+fileType;
                closedialog();
            }
        });
    }

    function deleteloanFile(id) {
        showConfirm({
            icon: 'warning',
            content: '您确认要删除吗',
            ok: function () {
                jQuery.ajax({
                    type: 'POST',
                    url: '../loanFile/deleteloanFile.html?id=' + id,
                    success: function (result) {
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                        refresh();
                    }
                });
            },
            cancel: function () {
            }
        });
    }

//按条件搜索列表
    $('#btnSearch').click(function () {

        var postJson = {};
        postJson['loanProcessType'] =jQuery.trim($('#loanProcessType').val());
        postJson['fileType'] = jQuery.trim($('#fileType').val());
        postJson['loanId']=$('#_loanId').val();

        $('#fileList').flexSearch(postJson);
    });

    //重置
    $('#btnClean').click(function () {
        toCleanForm('#zl');
    });

    function closedialog() {
        var dialog =  getDialog("selectDownload");
        setTimeout(function() {
            dialog.close();
        }, 3000);
    }

});



function checkFileType(id){
    var value=id.value
    if (value){
        uploadFileVersion(value);
    }else  showConfirm({
        icon:"warning",
        content:"请选择文件！"
    });
}
function uploadFileVersion(filename){
    var _script = '../loanFile/uploadFiles.html?loanId='+$("#_loanId").val();
    ajaxFileUpload({
        url: _script + '&fullFilename='+encodeURI(encodeURI(filename))+'&random='+Math.random()*10000,
        id:'file',
        callback:function(){
            var newFileName = filename.substring(filename.lastIndexOf('\\') + 1, filename.length);
            var html =  '<div class="item" style="line-height: 20px;">' +
                '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class="info"  style="font-size: 13px;">' + newFileName + '&nbsp;<a onclick="deleteFile(this,\''+ newFileName +'\')"><strong style="color: #0B61A4">移除</strong></a></label>' +
                '</div>';
            $("#uploadSpan").append(html);

        }
    });
}

function deleteFile(e,fileName) {
    //删除
    jQuery.ajax({
        type : 'post',
        url : '../loanFile/deleteFile.html',
        dataType:'json',
        async : false,
        data : {
            "loanId":$("#_loanId").val(),
            "fileName":fileName
        },
        success : function(result) {
            $(e).parent().parent().remove();
            refresh();
        }
    });
}

function  refresh(){
    jQuery.ajax({
        type: 'get',
        url: '../loanFile/getLoanInfoIds.html?loanId='+$("#_loanId").val(),
        success: function (result) {
            $('#fileList').flexReload();
        }
    });
}
