$(function(){
	$('select').selectbox({});
	// 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });

	// 加载任务列表
	$('#myTskGrid').flexigrid({
        height: 280,
        url: '../task/queryTaskInfoListData.html?selectMy=1',
        //工作台需要参数
        params:{"taskStatus":$("#taskStatus").val()},
        fields: [
        	{ display: '任务名称', field: 'taskTitle', width: 150 ,align: 'center'},
        	{ display: '任务期限', field: 'taskDateLimit', width: 180 ,align: 'center'},
        	{ display: '任务目标', field: 'target', width: 100 ,align: 'center'},
        	{ display: '任务简介', field: 'remark', width: 250 ,align: 'center'},
        	{ display: '创建者', field: 'createUserName', width: 100 ,align: 'center'},
        	{ display: '任务状态', field: 'taskStatus', width: 70 ,align: 'center',data:{0:"未完成",1:"未完成",2:"已完成"}},
        	{ display: '完成进度', field: 'taskPercent', width: 70 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 120,
        	align: 'center',
        	buttons: [
        	    {
            		display: '编辑',
            		onClick: function(tr, data){            			            			
            			updateTask(data);
            		},
                    isDisabled: function(data){
                        if(data.cols.tskLevel == 3){
                            return false;
                        }
                        return true;
                    }
        		},
                {
                    display: '详情',
                    onClick: function(tr, data){
                    	detailTask(data);
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        deleteTask(data);
                    },
                    isDisabled: function(data){
                         if(data.cols.tskLevel == "3"){
                         return false;
                         }
                        return true;
                    }
                }
            ]
        },
        usePage: true,
        onComplete : function(data) {
			$('#lblStatistics').text(data.total);
		}
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
        $("#ntaskStatus,#taskStatus").val("");
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
		searchTaskList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshTaskList();
	});
});

function deleteTask(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要彻底删除任务“'+data.cols.taskTitle+'”吗？删除后将无法恢复。',
        ok:function(){
        	jQuery.ajax({
    			url: '../task/deleteTaskInfo.html',
    			type:'POST', 
    			dataType:'json',
                data: {"taskId": data.id},
                sync: false,
                success: function(result){
                	if(result.success){
                		showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        refreshTaskList();
                	}else {
                		showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
					}
                }
    		});
        },
        cancel: function() {}
	});
}

function updateTask(data){
	var url = '../task/getTaskInfoUpdatePage.html?taskId='+data.id;
    showDialog({
        id: 'editTask',
        title: '编辑任务',
        url: url,
        width: 660,
        height: 420,
        tabId: tabs.getSelfId(window)
    });
}

function detailTask(data){
    var targetNum = data.cols.target;
    targetNum = targetNum.substring(0, targetNum.length-1);
	var url = '../task/getTaskInfoUpdatePage.html?taskId='+data.id+"&&isShow=true&&targetNum="+targetNum;
    showDialog({
        id: 'editTask',
        title: '任务详情',
        url: url,
        width: 660,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
}

$("#btnAdd").click(function() {
	var url = '../task/getTaskInfoInsertPage.html?taskType=addMyTask';
    showDialog({
        id: 'addMyTask',
        title: '新建任务',
        url: url,
        width: 660,
        height: 480,
        tabId: tabs.getSelfId(window)
    });
});

function searchTaskList() {
	var postJson =getPostFields();
    // postJson['myTask'] ='1';
	$('#myTskGrid').flexSearch(postJson);
}
// 刷新
function refreshTaskList() {
	$('#myTskGrid').flexReload();
}