$(function(){
    $('#btnPass').click(function() {
        auditPass();
    });
    $('#btnUnPass').click(function() {
        auditUnPass();
    });
    function auditPass(){
        jQuery.ajax({
            type : 'post',
            url : '../loanAuditManage/updateAuditDetail.html',
            data : {
                "loanAuditRemark":$("#loanAuditRemark").val(),
                "loanAuditState":1,
                "auditLoanId":$("#auditLoanId").val()
            },
            success : function(result) {
                showConfirm({
                    icon: 'succeed',
                    content: '操作成功'
                });
                close();
            }
        });
    }
    function auditUnPass(){
        jQuery.ajax({
            type : 'post',
            url : '../loanAuditManage/updateAuditDetail.html',
            data : {
                "loanAuditRemark":$("#loanAuditRemark").val(),
                "loanAuditState":2,
                "auditLoanId":$("#auditLoanId").val()
            },
            success : function(result) {
                showConfirm({
                    icon: 'succeed',
                    content: '操作成功'
                });
                close();
            }
        });
    }

    //关闭
    $('#btnCancel').click(function() {
        tabs.close(tabs.getSelfId(window));
    });

});

function close(){
    var win = tabs.getIframeWindow('loanAuditManage');
    win.location.reload(true);
    tabs.close(tabs.getSelfId(window));
}

