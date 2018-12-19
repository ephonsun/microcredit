var loanId=$("#loanId").val();


//初始化选项栏
$(function(){

    $('select').selectbox();
    layout.initForms();

    banger.verify('#productCategory', {name : 'required', tips: '产品名必填'});
    banger.verify('#saleRatio', {name : 'number', tips: '必须为数值'});
    banger.verify('#salePrice', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );
    banger.verify('#costPrice', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );
    banger.verify('#remark', {name : 'maxlength', tips: '内容过长'});

})
$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('updateMaolilv');
        dialog.close();
    });
    banger.verify('#salePrice', [{name : 'required'},{ name: 'maxlength', tips: '内容过长' }]);

});

// 提交新增数据源
$('#btnSave').click(function() {
    saveMaolilv();
});
function saveMaolilv(){
    if (!banger.verify('#updateMaolilv-form')) {
        return false;
    }
    var postJson = getPostFields();
    //postJson['picProductName'] = $('#picProductId option:selected').text();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanInvestigate/updateMaolilv.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog(loanId);
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

//重置
function reset(){
    $("#form")[0].reset();
}

//关闭dialog
function closeDialog(loanId){
    var dialog = getDialog('updateMaolilv');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if(win && win.reflushMao){
        win.reflushMao(loanId);
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}

