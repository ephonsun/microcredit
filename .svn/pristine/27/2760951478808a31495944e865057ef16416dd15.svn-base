$(function () {
    $("#loanActionHistoryGrid").flexigrid({
        url: '../loanActionHistory/queryHistory.html?loanId=' + $("#loanId").val(),
        dataType: 'json',
        fields: [
            {display: '环节', field: 'loanProcessType', width: 150, align: 'center', data: cdLoanType},
            {display: '操作内容', field: 'actionName', width: 150, align: 'center'},
            {display: '备注', field: 'actionContent', width: 300, align: 'center'},
            {display: '操作时间', field: 'actionDate', width: 150, align: 'center'},
            {display: '操作人员', field: 'actionUserName', width: 150, align: 'center'}
            ],
        usePage: false
    });
});