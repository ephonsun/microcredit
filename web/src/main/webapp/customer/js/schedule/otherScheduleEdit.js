$(function(){
    //渲染下拉框
    $('select').selectbox({});
    // 加载操作时间
    $('#txtPlanTime').datepicker({
        dateFmt: 'yyyy-MM-dd HH:mm'
    });

    initMaxlengthTips('#planRemark','#tips');
    //校验
    banger.verify('#txtPlanTime',{name: 'required', tips: '时间必须填写'});
    banger.verify('#planRemark',{ name: 'required', tips: '必须填写', rule: /.+/ },{ name: 'maxlength', tips: '日程备注内容过长' });


    // 提交新增数据源
    $('#btnSave').click(function() {
        saveschedule();
    });

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('editSchedule');
        dialog.close();
    });

});

function saveschedule(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();

    postJson['planRemark'] = jQuery.trim($('#planRemark').val());

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../schedule/updateCustSchedule.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog(result.value);
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
    var dialog = getDialog("editSchedule");
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshScheduleList();
    setTimeout(function() {
        dialog.close();
    }, 0);
}