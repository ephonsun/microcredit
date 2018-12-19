
$(function(){
    //var loanId = '$!loanId';
    $('select').selectbox({scrollRange:7});
    //二级下拉选change事件
    $('#roleId').change(
        function () {
            var roleId = $('#roleId').val();
            var teamGroupId = $('#teamGroupId').val();
            $('#userId').val('');
            $('select').selectbox({scrollRange:7});
            $.getJSON("../contract/queryUserByRoleId.html", {roleId: roleId,teamGroupId:teamGroupId}, function (data) {
                $('#userId').empty();
                $('#userId').append("<option></option>");
                for (var index in data.data) {
                    $('#userId').append("<option value=" + data.data[index].userId + ">" + data.data[index].userName+"("+data.data[index].userAccount+")" + "</option>");
                }
            });
        });

    $('#btnConfirm').click(function(){
        var roleId = $('#roleId').val();
        var userId = $('#userId').val();
        var loanId = $('#loanId').val();
        if(userId==''){
            showConfirm({
                icon: 'warning',
                content: '请选择签订对象!'
            });
            return false;
        }
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../contract/loanContractSubmit.html',
            data: {
                "userId": userId,
                "loanId":loanId
            },
            async: false,
            success: function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                    closeDialog();
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    });

    $('#btnCancel').click(function(){
        var dialog = getDialog('contractSubmit');
        dialog.close();
    });

});
//关闭dialog
function closeDialog(){
    var dialog = getDialog('contractSubmit');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.closeApplyTab){
        win.closeApplyTab();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}
