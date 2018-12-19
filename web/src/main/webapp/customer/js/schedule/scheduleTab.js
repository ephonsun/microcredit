$(function(){
    var action = null;
    if ($("#flag").val() !="1") {
        action = {
            display: '操作',
                width: 120,
            align: 'center',
            buttons: [
                {
                    display: '编辑日程',
                    onClick: function(tr, data){
                        updateSchedule(data);
                    }/*,
                 isDisabled: function(tr, data){
                 var userId=$('#userId').val();
                 if(userId==tr.cols.createUser&&){
                 return false;
                 }
                 return true;
                 }*/
                }
            ]
        };
    }
	// 加载日程列表
	$('#scheduleGrid').flexigrid({
        height: 280,
        url: '../schedule/queryOneCustScheduleList.html?customerId='+$('#customerId').val(),
        fields: [
        	{ display: '日程', field: 'planTimeStr', width: 150 ,align: 'center'},
        	{ display: '沟通进度', field: 'planRateCN', width: 150 ,align: 'center'},
        	{ display: '日程备注', field: 'planRemark', width: 200 ,align: 'center'},
        	{ display: '完成情况', field: 'state', width: 100 ,align: 'center',data:{1:"未完成",2:"已完成"}}
        ],
        action: action,
        extendCell: {
	   	 	'planTimeStr': function(value, row){
	   			var value = row.cols.planTypeCN+"</br>"+row.cols.planTimeStr;
	   			return value;
	   	 	}
        },        
        rowIdProperty: 'id',
        usePage: true,
        onComplete : function(data) {
			
		}
    });
});

$("#btnAdd").click(function() {
	var url = '../schedule/getCustScheduleAddPage.html?customerId='+$('#customerId').val();
    showDialog({
        id: 'addSchedule',
        title: '添加日程',
        url: url,
        width: 450,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
});

function updateSchedule(data){
	var url = '../schedule/getCustScheduleUpdatePage.html?scheduleId='+data.id;
    showDialog({
        id: 'editSchedule',
        title: '编辑日程',
        url: url,
        width: 480,
        height: 450,
        tabId: tabs.getSelfId(window)
    });
}


// 刷新列表表
function refreshScheduleList() {
	$('#scheduleGrid').flexReload();
}