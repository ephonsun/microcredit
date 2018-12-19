// 页面加载完成时...
$(function(){
    $('select').selectbox();

    $('#afterState').change(function() {
        changeAfterLoanState();
    });

    //手动添加监控
    $('#btnAddMonitor').click(function () {
        addLoanMonitorInfo();
    });

    // 加载
    $('#grid').flexigrid({
        height: 500,
        url: '../loanMonitorInfo/queryMonitorInfo.html?loanId='+$("#loanId").val(),
        usePage: true,
        fields: [
            { display: '监控日期', field: 'loanMonitorDate', width: 100 ,align : 'center' },
            { display: '监控类型', field: 'loanMonitorType', width: 100 ,align : 'center',data:{"firstMonitor":"首次监控","normalMonitor":"常规监控","temporaryMonitor":"临时监控"}},
            { display: '回访类型', field: 'reType', width: 100 ,align : 'center' },
            { display: '结果', field: 'loanResultContent', width: 100 ,align : 'center' },
            { display: '状态', field: 'loanMonitorState', width: 100 ,align : 'center',data:{'MonitorComplete':'已完成','Monitor':'未完成'} },
            { display: '完成日期', field: 'loanCompleteDate', width: 100 ,align : 'center'},
            { display: '监控人', field: 'userName', width: 150 ,align : 'center'}
        ],
        action: {
            display: '操作',
            width: 200,
            align: 'center',
            buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){
                        viewLoanMonitorInfo(data);
                    }
                },
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        updateLoanMonitorInfo(data);
                    },
                    isDisabled : function(data) {
                    return  $("#showApply").val() == 1 || !monitorEdit
                    }
                }
            ]
        },
        usePage: false
    });
});

function updateLoanMonitorInfo(data){
    var url = '../loanMonitorInfo/getUpdatePage.html?id='+data.id;
    showDialog({
        id: 'editMonitorInfo',
        title: '编辑监控',
        url: url,
        width: 660,
        height: 270,
        tabId: tabs.getSelfId(window)
    });
}


function viewLoanMonitorInfo(data){
    var url = '../loanMonitorInfo/getViewPage.html?id='+data.id;
    showDialog({
        id: 'viewMonitorInfo',
        title: '查看监控',
        url: url,
        width: 660,
        height: 270,
        tabId: tabs.getSelfId(window)
    });
}

function addLoanMonitorInfo(){
    var url = '../loanMonitorInfo/getAddPage.html?loanId='+$("#loanId").val();
    showDialog({
        id: 'addMonitorInfo',
        title: '新增监控',
        url: url,
        width: 660,
        height: 270,
        tabId: tabs.getSelfId(window)
    });
}

//改变贷款状态
function changeAfterLoanState(){
    jQuery.ajax({
        type : 'POST',
        url : '../loanMonitorInfo/changeAfterLoanState.html',
        data : {
            "loanId":$("#loanId").val(),
            "afterLoanState":$("#afterState").val()
        },
        success : function(result) {
        }
    });
}
