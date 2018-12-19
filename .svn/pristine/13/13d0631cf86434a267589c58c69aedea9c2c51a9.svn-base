$(function(){
	// 加载任务列表
	$('#taskAssignGrid').flexigrid({
        height: 280,
        url: '../groupTask/queryGroupTaskAssignListData.html?taskId='+$('#taskId').val(),
        fields: [
        	{ display: '分配对象', field: 'teamGroupName', width: 150 ,align: 'center'},
        	{ display: '任务目标', field: 'assignTargetF', width: 100 ,align: 'center'},
        	/*{ display: '已分配', field: 'assignNum', width: 100 ,align: 'center'},*/
        	{ display: '未分配', field: 'unSiginedNumF', width: 100 ,align: 'center'},
        	{ display: '分配比率', field: 'siginedPercent', width: 100 ,align: 'center'},
        	{ display: '已完成', field: 'finishNumF', width: 100 ,align: 'center'},
        	{ display: '完成进度', field: 'finishPercent', width: 100 ,align: 'center'},
        	{ display: '团队明细', field: 'taskId', width: 100 ,align: 'center'}
        ],
        rowIdProperty: 'id',
        extendCell: {
            'taskId': function(value,row){
                if(row.cols.teamGroupName=='合计'){
                	value = '';
                }else{
                    if(row.cols.teamGroupId == $("#userTeamGroupId").val()){
                        value = '<label class=\"ui-link mr5\" onclick=\"btnAssign('+row.id+','+row.cols.teamGroupId+')\">分配</label> <label class=\"ui-link mr5\" onclick=\"viewTeamTask('+row.id+','+row.cols.teamGroupId+')\">查看</label>';
                    }else {
                        value = '<label class=\"ui-link mr5\" onclick=\"viewTeamTask('+row.id+','+row.cols.teamGroupId+')\">查看</label>';
                    }
                }
                return value;
            }
        },
        usePage: false,
        onComplete : function(data) {
			
		}
    });

    //关闭
    $('#btnCancel').click(function() { //teamTaskDetail
        tabs.close(tabs.getSelfId(window))
    });

});

function viewTeamTask(id,teamGroupId){
	var url = '../groupTask/getTeamTaskAssignListPage.html?taskId='+$('#taskId').val()
	              +'&groupTaskAssignId='+id+'&teamGroupId='+teamGroupId;
    showDialog({
        id: 'teamTaskDetail',
        title: '团队分配明细',
        url: url,
        width: 950,
        height: 420,
        tabId: tabs.getSelfId(window)
    });
}

$("#btnAssign").click(function() {
    btnAssign();
});

function btnAssign() {
    var url = '../groupTask/getGroupTaskAssignPage.html?taskId='+$('#taskId').val();
    showDialog({
        id: 'assignTask',
        title: '分配任务',
        url: url,
        width: 360,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
}

//刷新人员表
function refreshGroupList() {
	$('#taskAssignGrid').flexReload();
}