// 页面加载完成时...
$(function(){
    $('select').selectbox({});

    banger.verify('#newBelongId',[{name: 'required', tips: '请选择客户经理！'}]);

    $('#btnHandOver').click(function(){
        if (!banger.verify('.ui-form-fields')) {
            return false;
        }
        var url = '../customerHandOver/saveHandOver.html'
        jQuery.ajax({
            type: 'POST',
            url: url,
            data:{
                oldBelongId:$("#oldBelongId").val(),
                ids:$("#ids").val(),
                newBelongId:$("#newBelongId").val()
            },
            success: function (result) {
                showConfirm({
                    icon: 'succeed',
                    content: '移交成功'
                });
                closeDialog();
            }
        });
    });

    $('#btnCancel').click(function(){
        getDialog('customerHandOverUpdate').close();

    });
});
//关闭dialog
function closeDialog(){
    var dialog = getDialog('customerHandOverUpdate');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    // var win2 = tabs.getIframeWindow('customerHandOver');
    if( win && win.closeThisTab){
        win.closeThisTab();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    // win.location.reload(true);
    // win2.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
