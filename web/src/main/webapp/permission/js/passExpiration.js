// 页面加载完成时...
$(function(){
    //渲染下拉框
    $('select').selectbox({});
    //校验
    banger.verify('#mmgq_day',[{name: 'required', tips: '密码过期天数必须填写'},{ name: 'posiInteger', tips: '密码过期天数必须为正整数' }]);


    $("#btnSave").click(function(){
        save();
    });
    $("#btnCancel").click(function(){
        tabs.close(tabs.getSelfId(window));
    })


})

function save() {
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var url = '../passExpiration/updatePassExpiration.html'
    jQuery.ajax({
        type: 'POST',
        dataType:'json',
        url: url,
        data: {
            // 'jcdc_id':$("#jcdc_id").val(),
            // 'jcdc':$("#jcdc").find("option:selected").val(),
            'mmgq_id':$("#mmgq_id").val(),
            'mmgq_kg':$("#mmgq_kg").val(),
            'mmgq_day':$("#mmgq_day").val()
        },
        success: function (result) {
            showConfirm({
                icon: 'succeed',
                content: '保存成功'
            });
            window.location.reload();
        }
    });
}