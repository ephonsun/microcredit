$(function(){

    // 更改启用状态
    $('#btnSave').click(function() {
        updateStatus();
    });

    // 解绑状态
    $('#btnCancel').click(function() {
        unBind();
    });
});

function updateStatus(){
    var serialNo= $("#serialNo").val();
    var isActived = $("#isActived").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysMobileInfo/updateStatus.html',
        data : {"serialNo":serialNo,"isActived":isActived,},
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
                    content: result.cause
                });
            }
        }
    });
}

function unBind(){
    var serialNo= $("#serialNo").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../sysMobileInfo/unBind.html',
        data : {"serialNo":serialNo},
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
                    content: result.cause
                });
            }
        }
    });
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog("mobileDetail");
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshList();
    setTimeout(function() {
        dialog.close();
    }, 0);
}