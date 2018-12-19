// 页面加载完成时...
$(function(){
    $('select').selectbox();

    $("#btnSave").click(function(){
       saveCopy();
    });

    $("#btnCancel").click(function(){
        var dialog = getDialog('modelScoreInfoCopyPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    });
});

function saveCopy(){
    var postJson = getPostFields();
    showConfirm({
        icon:'confirm',
        content:'您确定要复制这个模型吗？复制后将覆盖当前模型！',
        ok:function(){
            jQuery.ajax({
                type : 'post',
                url : '../modelScoreField/saveCopyModel.html',
                data : {
                    'copyModeId':$("#copyModeId").val(),
                    'modeId':$("#modeId").val()
                },
                success : function() {
                    showConfirm({
                        icon: 'succeed',
                        content: '复制成功'
                    });
                    closeDialog();
                }
            });
        },
        cancel: function() {}
    });

}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('modelScoreInfoCopyPage');
    var win = tabs.getIframeWindow("modelScoreField"+$("#modeId").val());
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
