$(function(){
    //渲染下拉框
    $('select').selectbox({});
    //剩余字提示
    initMaxlengthTips('#content','#tips');
    //校验
    banger.verify('#dictNameSelect',{name: 'required', tips: '行业分类必须选择'});
    banger.verify('#title',[{name: 'required', tips: '标题必须填写'},{name: 'specialAccount', tips: '标题必须由中英文、数字或下划线组成'}]);
    banger.verify('#content',[{name: 'required', tips: '内容必须填写'},{ name: 'maxlength', tips: '内容过长' }]);

    // 提交新增
    $('#btnSave').click(function() {
        addIndustryGuidelines();
    });
    // 保存并新建
    $('#btnSaveContine').click(function() {
        addContinueIndustryGuidelines();
    });
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('addIndustryGuidelines');
        dialog.close();
    });

});

function addIndustryGuidelines(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = {};
    postJson['dictColId'] = $("#dictNameSelect").val();
    postJson['dictName'] = $("#dictNameSelect").find("option:selected").text();
    postJson['title'] = $("#title").val();
    postJson['content'] = $("#content").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../industryGuidelines/addIndustryGuidelines.html',
        data : postJson,
        success : function(result) {
                showConfirm({
                    icon: 'succeed',
                    content: '新增成功'
                });
                closeDialog();
            }
    });
}

function addContinueIndustryGuidelines(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = {};
    postJson['dictColId'] = $("#dictNameSelect").val();
    postJson['dictName'] = $("#dictNameSelect").find("option:selected").text();
    postJson['title'] = $("#title").val();
    postJson['content'] = $("#content").val();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../industryGuidelines/addIndustryGuidelines.html',
        data : postJson,
        success : function(result) {
            showConfirm({
                icon: 'succeed',
                content: '新增成功'
            });
            var dialog = getDialog('addIndustryGuidelines');
            var win = tabs.getIframeWindow(dialog.config.tabId);
            win.location.reload(true);
            $("#form")[0].reset();
            initMaxlengthTips('#content','#tips');
        }
    });

}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('addIndustryGuidelines');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}