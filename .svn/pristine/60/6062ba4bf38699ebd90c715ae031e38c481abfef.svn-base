$(function() {
    $('#btnAdd').click(function () {
        var url = '../resource/getResourcesUploadPage.html';
        showDialog({
            id: 'openResourcesUploadPage',
            title : '上传资源文件',
            url : url,
            width : 550,
            height : 240,
            tabId: tabs.getSelfId(window)
        });
    });
})

function delResourceFile(fileName){
    var postJson = {};
    var url = "../resource/delResourcesFile.html?fileName="+encodeURI(encodeURI(fileName))+"&random="+Math.random()*100000;
    showConfirm({
        icon: 'confirm',
        content: '您确认要删除吗',
        ok: function() {
            postJson['fileName'] = fileName;
            jQuery.ajax({
                type : 'POST',
                url : url,
                data : postJson,
                success : function(result) {
                    showConfirm({
                        icon: 'succeed',
                        content: '删除成功'
                    });
                    window.location.reload();
                }
            });
        },
        cancel: function() {}
    });
}

function downResourceFile(fileName){
    var url = "../desktop/downloadResources.html?fileName="+encodeURI(encodeURI(fileName))+"&random="+Math.random()*100000;
    window.location.href = url;
}

