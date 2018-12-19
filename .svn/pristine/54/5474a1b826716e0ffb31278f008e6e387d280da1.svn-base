var loanId=$("#loanId").val();
var columnName=$("#columnName").val();


//初始化选项栏
$(function(){

    $('select').selectbox();
    layout.initForms();
    banger.verify('#monthAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );
    banger.verify('#remark', {name : 'maxlength', tips: '内容过长'});



})
$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('showDetail');
        dialog.close();
    });
});

// 提交新增数据源
$('#btnSave').click(function() {
    saveDetail();
});
function saveDetail(){
    var bool = banger.verify("#showDetail-form");
    if (!bool) {
        return false;
    }

    var remark=$("#remark").val();
    var json = {};
    $("#showDetail-form .monthData").each(function () {
        var _input = [];
        var id = $(this).find('input').val();
        var val = $(this).find('input').next().val();
        json[id] = val;
    });
    var jsonStr = JSON.stringify(json);
    // var postJson = getPostFields();
    //postJson['picProductName'] = $('#picProductId option:selected').text();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanInvestigate/updateDetail.html',
        data : {'json':jsonStr,'remark':remark},
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
    var dialog = getDialog('showDetail');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if(win && win.reflushGrid){
        win.reflushGrid(loanId,columnName);
    }
    /*   win.location.reload(true);*/
    setTimeout(function() {
        dialog.close();
    }, 0);
}


/*//关闭dialog
function closeDialog(){
    var dialog = getDialog('showDetail');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}*/

