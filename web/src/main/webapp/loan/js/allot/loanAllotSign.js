$(function(){

    //渲染下拉框
    $('select').selectbox({scrollRange:7});
    $("#memberUserId").selectbox({scrollRange:7}).val($("#userId").val() ).selectbox({scrollRange:7});

    $('#btnConfirm').click(function() {
        saveSign();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('loanAllotSign');
        dialog.close();
    });

});

function saveSign(){
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanAllot/loanAllotSignSave.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause,
                    ok: function() {
                        closeDialog();
                    }
                });
            }
        }
    });
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('loanAllotSign');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.closeApplyTab){
        win.closeApplyTab();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}