$(function(){
    $('select').selectbox({});
    // 加载操作时间
    $('#txtStartDate').datepicker({
        dateFmt: 'yyyy-MM-dd HH:mm',
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        dateFmt: 'yyyy-MM-dd HH:mm',
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });

    // 加载日程列表
    $('#scheduleGrid').flexigrid({
        height: 280,
        url: '../schedule/queryCustScheduleListData.html?selectMy=' + selectMy + "&scheduleType=" + 0,
        //控制台参数
        params :getPostFields(),
        fields: [
            // { display: '客户信息', field: 'customerName', width: 150 ,align: 'center'},
            // { display: '客户类型', field: 'customerLevelCN', width: 100 ,align: 'center'},
            { display: '时间', field: 'planTypeCN', width: 150 ,align: 'center'},
            // { display: '沟通进度', field: 'planRateCN', width: 100 ,align: 'center'},
            { display: '日程备注', field: 'planRemark', width: 300,align: 'center'},
            { display: '完成情况', field: 'state', width: 100 ,align: 'center',data:{1:"未完成",2:"已完成"}}
        ],
        action: {
            display: '操作',
            width: 120,
            align: 'center',
            buttons: [
                {
                    display: '编辑日程',
                    onClick: function(tr, data){
                        updateSchedule(data);
                    },
                    isDisabled: function(tr, data){
                        var userId=$('#userId').val();
                        if(userId==tr.cols.createUser){
                            return false;
                        }
                        return true;
                    }
                }
            ]
        },
        extendCell: {
            // 'customerName': function(value, row){
            //     var value = row.cols.customerName+"&nbsp;&nbsp;&nbsp;"+row.cols.customerSexCN+"&nbsp;&nbsp;&nbsp;"+row.cols.customerAgeStr
            //         +"</br>"+row.cols.phoneNumber;
            //     return value;
            // },
            'planTypeCN': function(value, row){
                var value = row.cols.planTimeStr;
                return value;
            }
        },
        rowIdProperty: 'id',
        usePage: true,
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');
        if($('#mySchedule').attr('checked')=='checked'){
            selectFlag = '1';
        }else{
            selectFlag = '0';
        }
        $("#txtStartDate,#txtEndDate").val("");
    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchList();
    });
    //刷新
    $('#btnRefresh').click(function(){
        refreshScheduleList();
    });
});


function updateSchedule(data){
    var url = '../schedule/getCustScheduleUpdatePage.html?scheduleId='+data.id+"&scheduleType="+0;
    showDialog({
        id: 'editSchedule',
        title: '编辑日程',
        url: url,
        width: 450,
        height: 375,
        tabId: tabs.getSelfId(window)
    });
}

$("#btnAdd").click(function() {
    var url = '../schedule/getOtherScheduleAddPage.html?customerId='+$('#customerId').val();
    showDialog({
        id: 'editSchedule',
        title: '添加日程',
        url: url,
        width: 450,
        height: 375,
        tabId: tabs.getSelfId(window)
    });
});


function searchList() {
    var postJson =getPostFields();
    if($('#mySchedule').attr('checked')=='checked'){
        selectFlag = '1';
    }else{
        selectFlag = '0';
    }
    postJson['mySchedule'] =selectFlag;
    $('#scheduleGrid').flexSearch(postJson);
}
// 刷新列表表
function refreshScheduleList() {
    $('#scheduleGrid').flexReload();
}