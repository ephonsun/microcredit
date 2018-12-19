$(function(){
	// 加载任务列表
	$('#teamAssignGrid').flexigrid({
        height: 280,
        url: '../groupTask/queryTeamTaskAssignListData.html?taskId='+$('#taskId').val()+'&groupTaskAssignId='+$('#groupTaskAssignId').val()+'&teamGroupId='+$('#teamGroupId').val(),
        fields: [
        	{ display: '分配对象', field: 'teamMemberName', width: 150 ,align: 'center'},
        	{ display: '任务目标', field: 'assignTargetF', width: 100 ,align: 'center'},
        	/*{ display: '已分配', field: 'assignNum', width: 100 ,align: 'center'},*/
        	{ display: '未分配', field: 'unSiginedNumF', width: 100 ,align: 'center'},
        	{ display: '分配比率', field: 'assignPercent', width: 100 ,align: 'center'},
        	{ display: '已完成', field: 'finishNumF', width: 100 ,align: 'center'},
        	{ display: '完成进度', field: 'finishPercent', width: 100 ,align: 'center'}
        ],
        usePage: false,
        onComplete : function(data) {
			
		}
    });


});

$("#btnAssign").click(function() {
	var url = '../groupTask/getGroupTaskAssignPage.html?taskId='+$('#taskId').val();
    showDialog({
        id: 'assignTask',
        title: '分配任务',
        url: url,
        width: 360,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
});

function refreshGroupList() {
	$('#teamAssignGrid').flexReload();
}

//关闭
$('#btnCancel').click(function() {
    tabs.close(tabs.getSelfId(window))
    var dialog = getDialog('teamTaskDetail');
    dialog.close();
});