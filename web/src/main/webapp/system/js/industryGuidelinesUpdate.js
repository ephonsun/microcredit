$(function(){
    //渲染下拉框
    $('select').selectbox({});
    //剩余字提示
    initMaxlengthTips('#content','#tips');
    //校验
    banger.verify('#dictNameSelect',{name: 'required', tips: '行业分类必须选择'});
    banger.verify('#title',[{name: 'required', tips: '标题必须填写'},{name: 'specialAccount', tips: '标题必须由中英文、数字或下划线组成'}]);
    banger.verify('#content',[{name: 'required', tips: '内容必须填写'},{ name: 'maxlength', tips: '内容过长' }]);

    // 提交更新
    $('#btnSave').click(function() {
        updateIndustryGuidelines();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('editIndustryGuidelines');
        dialog.close();
    });

});

function updateIndustryGuidelines(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = {};
    postJson['dictColId'] = $("#dictNameSelect").val();
    postJson['dictName'] = $("#dictNameSelect").find("option:selected").text();
    postJson['title'] = $("#title").val();
    postJson['content'] = $("#content").val();
    postJson['id'] = $("#id").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../industryGuidelines/updateIndustryGuidelines.html',
        data : postJson,
        success : function(result) {
            showConfirm({
                icon: 'succeed',
                content: '更新成功'
            });
            closeDialog();
        }
    });
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('editIndustryGuidelines');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}