$(function(){
    banger.verify('#flowName',{name : 'maxlength'});
    banger.verify('#flowName', {name : 'required'});
})

function updateSurveyFlow() {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../SurveyFlow/updateSurveyFlowInfo.html',
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });

}

function addSurveyFlow() {
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../SurveyFlow/insertSurveyFlowInfo.html',
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}



//取消
$('#btnCancel').click(function () {
    var dialog = getDialog('editSurveyFlow');
    dialog.close();
});


//关闭dialog
function closeDialog() {
    var dialog = getDialog('editSurveyFlow');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function () {
        dialog.close();
    }, 0);
}