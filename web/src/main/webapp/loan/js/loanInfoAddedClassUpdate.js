$(function(){
    //校验
    banger.verify('#className',[
        {name: 'required', tips: '子类名称必须填写'},
        {name: 'specialAccount', tips: '子类名称必须由中英文、数字或下划线组成'},
        checkClassNameRule
    ]);
    $('#btnSave').click(function() {
        updateLoanInfoAddedClass();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('loanInfoAddedClassUpdatePage');
        dialog.close();
    });

});
//保存更新
function updateLoanInfoAddedClass(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        url : '../loanInfoAddedClass/updateClass.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
        }
    });
}

//名字校验
var checkClassNameRule = {
    name : 'checkClassNameRule',
    tips : '子类名字已经存在，请重新输入',
    rule : function() {
        var rule = this, bool = true, data = getPostFields();
        var url = '../loanInfoAddedClass/checkClasNameUpdate.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            dataType:'json',
            async : false,
            data : data,
            success : function(result) {
                bool = result.success;
            }
        });
        return bool;
    }
};

//关闭dialog
function closeDialog(){
    var dialog = getDialog('loanInfoAddedClassUpdatePage');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}
