// 页面加载完成时...
$(function(){
    //渲染下拉框
    $('select').selectbox({});

    //校验
    banger.verify('#cfsz',[{name: 'required', tips: '还款提醒天数必须填写'},{ name: 'posiInteger', tips: '还款提醒天数必须为正整数' }]);
    banger.verify('#scjk',[{name: 'required', tips: '首次监控天数必须填写'},{ name: 'posiInteger', tips: '首次监控天数必须为正整数' }]);
    banger.verify('#cgjkzc',[{name: 'required', tips: '监控频率天数必须填写'},{ name: 'posiInteger', tips: '监控频率天数必须为正整数' }]);
    banger.verify('#cgjkgz',[{name: 'required', tips: '监控频率天数必须填写'},{ name: 'posiInteger', tips: '监控频率天数必须为正整数' }]);
    banger.verify('#qxsz',[{name: 'required', tips: '起息设置天数必须填写'},{ name: 'posiInteger', tips: '起息设置天数必须为正整数' }]);

    $("#btnSave").click(function(){
       save();
    });
    $("#btnCancel").click(function(){
        tabs.close(tabs.getSelfId(window));
    })
    // //交叉调查
    // $("#jcdc").change(function(){
    //
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../sysBasicConfig/updateBasicConfig.html',
    //         data : {
    //             'basicConfigId':$("#jcdc_id").val(),
    //             'configValue':$("#jcdc").find("option:selected").val()
    //         }
    //     });
    // });
    //
    // //催收设置
    // $("#cfsz").change(function(){
    //     if (!banger.verify('.ui-form-fields')) {
    //         return false;
    //     }
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../sysBasicConfig/updateBasicConfig.html',
    //         data : {
    //             'basicConfigId':$("#cfsz_id").val(),
    //             'configValue':$("#cfsz").val()
    //         }
    //     });
    // });
    //
    // //首次监控
    // $("#scjk").change(function(){
    //     if (!banger.verify('.ui-form-fields')) {
    //         return false;
    //     }
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../sysBasicConfig/updateBasicConfig.html',
    //         data : {
    //             'basicConfigId':$("#scjk_id").val(),
    //             'configValue':$("#scjk").val()
    //         }
    //     });
    // });
    //
    //常规监控 正常
    // $("#cgjkzc").change(function(){
    //     if (!banger.verify('.ui-form-fields')) {
    //         return false;
    //     }
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../sysBasicConfig/updateBasicConfig.html',
    //         data : {
    //             'basicConfigId':$("#cgjkzc_id").val(),
    //             'configValue':$("#cgjkzc").val()
    //         }
    //     });
    // });
    //
    // //常规监控 关注
    // $("#cgjkgz").change(function(){
    //     if (!banger.verify('.ui-form-fields')) {
    //         return false;
    //     }
    //     jQuery.ajax({
    //         type : 'post',
    //         dataType:'json',
    //         url : '../sysBasicConfig/updateBasicConfig.html',
    //         data : {
    //             'basicConfigId':$("#cgjkgz_id").val(),
    //             'configValue':$("#cgjkgz").val()
    //         }
    //     });
    // });

})

function save() {
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var url = '../sysBasicConfig/updateBasicConfig.html'
    jQuery.ajax({
        type: 'POST',
        dataType:'json',
        url: url,
        data: {
            // 'jcdc_id':$("#jcdc_id").val(),
            // 'jcdc':$("#jcdc").find("option:selected").val(),
            'cfsz_id':$("#cfsz_id").val(),
            'cfsz':$("#cfsz").val(),
            'scjk_id':$("#scjk_id").val(),
            'scjk':$("#scjk").val(),
            'cgjkzc_id':$("#cgjkzc_id").val(),
            'cgjkzc':$("#cgjkzc").val(),
            'cgjkgz_id':$("#cgjkgz_id").val(),
            'cgjkgz':$("#cgjkgz").val(),
            'qxsz_id':$("#qxsz_id").val(),
            'qxsz':$("#qxsz").val(),
            'lxdw_id':$("#lxdw_id").val(),
            'lxdw':$("#lxdw").val()
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