/*

 $.ajax({

 /!* url: url,
 data:{Full:"fu"},
 type: "POST",
 dataType:'json',
 success:function(data){
 $('#result').append('<p>interval:'+data.interval+'</p>');
 //alert("22");
 }*!/

 });
 */
/*?conlumeName=' + conlumeName + '&id=' + id + '&updordet=1*/
/*获取编辑界面*/


var loanId=$("#loanId").val();
var columnName=$("#columnName").val();

//初始化选项栏
$(function(){

    $('select').selectbox();
    layout.initForms();

    banger.verify('#itemName', {name : 'required', tips: '项目名必须选择'});
    banger.verify('#preYearAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );
    banger.verify('#curYearAmount', [{name: 'number', tips: '不能为非数字'},{ name: 'nonNegaDecimal', tips: '不能为负数'}]
    );

})

$(function(){
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('addConMingxi');
        dialog.close();
    });

});

// 提交新增数据源
$('#btnSave').click(function() {
    saveConMingxi();
});
function saveConMingxi(){
    var bool = banger.verify("#addConMingxi-form");
    if (!bool) {
        return false;
    }

    var postJson = getPostFields();
    //postJson['picProductName'] = $('#picProductId option:selected').text();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanConInvestigate/addConMingxi.html',
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
    var dialog = getDialog('addConMingxi');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if(win && win.reflushGrid){
        win.reflushGrid(loanId,columnName);
    }
    /*   win.location.reload(true);*/
    setTimeout(function() {
        dialog.close();
    }, 0);
}

