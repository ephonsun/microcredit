var loanId=$("#loanId").val();
var columnName=$("#columnName").val();
//初始化选项栏
$(function(){
    $('select').selectbox();
    layout.initForms();

    banger.verify('#itemName', {name : 'required', tips: '项目名必须选择'});
    banger.verify('#averageAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );
    banger.verify('#remark', {name : 'maxlength', tips: '内容过长'});

})



$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('addMingxi')
        dialog.close();
    });

});

// 提交新增数据源
$('#btnSave').click(function() {
    saveMingxi();
});
function saveMingxi(){
    var bool = banger.verify("#addMingxi-form");
    if (!bool) {
        return false;
    }
    var postJson = getPostFields();

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanInvestigate/addMingxi.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog(loanId,columnName);
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
function closeDialog(loanId,columnName){
    var dialog = getDialog('addMingxi');
    var win = tabs.getIframeWindow(dialog.config.tabId);
	if(win && win.reflushGrid){
        win.reflushGrid(loanId,columnName);
    }
 /*   win.location.reload(true);*/
    setTimeout(function() {
        dialog.close();
    }, 0);
}

