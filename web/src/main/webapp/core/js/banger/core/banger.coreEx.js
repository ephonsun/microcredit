Namespace.register('banger.coreEx');
//限制多文本输入框字数
banger.coreEx.globalTextareaLmt = function(){
    $('textarea').bind('input propertychange', function() {
        var value = $(this).val();
        var maxLength = $(this).attr('maxlength');
        if(value.length > maxLength) {
            $(this).val(value.substr(0,maxLength));
        }
    });
}
//从身份证中得到出生年月日
banger.coreEx.getBirthDayFromIDCard = function(IDCard){
    var strDate = IDCard.length == 15 ? '19' + IDCard.substr(6, 6) : IDCard.substr(6, 8);
    return strDate.replace(/(\d{4})/, '$1-').replace(/(-\d{2})/, '$1-');
} 
function closeDialog(){
    var dialog = getDialog('viewCusLookupItemDetail');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if(win&&win.refresh){
        win.refresh();
    }
    dialog.close();
}
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
    tabs.close(tabs.getSelfId(window));
}