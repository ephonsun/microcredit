$(function(){
    //渲染下拉框
    $('select').selectbox({});

    $('#btnConfirm').click(function() {
        saveSign();
    });


    //关闭
    $('#btnCancel').click(function() {
        closeDialog();
    });

});

function saveSign(){
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanType/selectModeSave.html',
        data : postJson,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause,
                    ok: function() {
                        closeDialog();
                    }
                });
            }
        }
    });
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('editConfigFile');
    setTimeout(function() {
        dialog.close();
    }, 0);
}