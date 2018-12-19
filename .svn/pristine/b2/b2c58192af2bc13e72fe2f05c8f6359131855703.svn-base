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
   // #*10.13 夏俊确认，所有配置权限的人都可以具有菜单权限,不具有新建权限,不具有查看团队任务列表权限，由角色决定*#
    var roleId = $("#roleId").val();
    var url = "";
    if(roleId == "2" || roleId == "3")
        url = "../task/queryTaskInfoListData.html";
	$('#taskGrid').flexigrid({
        height: 280,
        // url: '../task/queryTaskInfoListData.html?selectMy=' + selectMy,
        url: url,
        //工作台需要参数
        params:{"taskStatus":$("#taskStatus").val()},
        fields: [
        	{ display: '任务名称', field: 'taskTitle', width: 150 ,align: 'center'},
        	{ display: '任务期限', field: 'taskDateLimit', width: 180 ,align: 'center'},
        	{ display: '任务类型', field: 'taskMold', width: 100 ,align: 'center',data:{0:"贷款任务",1:"营销任务"}},
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
                    isDisabled: function(tr, data){
            		    return checkButton(tr);
                    }
        		},
                {
                    display: '分配信息',
                    onClick: function(tr, data){
                    	assignTask(data);
                    },
                    isDisabled: function(tr, data){
                        return checkButton(tr, true);
                    }
                },
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteTask(data);
            		},isDisabled: function(tr, data){
                        return checkButton(tr);
                    }
        		},
                
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
        selectFlag = '0';
        // if($('#myTask').attr('checked')=='checked'){
        //     selectFlag = '1';
        // }else{
        //     selectFlag = '0';
        // }
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
function checkButton(tr, assign){
    var userId=$('#userId').val();
    var roleId=$('#roleId').val();
    var groupId=$('#groupId').val();
    if (roleId == 2) {
        if(userId==tr.cols.createUser || assign){
            return false;
        }
    } else if(roleId == 3){
        if(groupId !='' && (groupId==tr.cols.teamGroupId || assign)){
            return false;
        }
    } else {
        if(groupId !='' && assign){
            return false;
        }
    }
    return true;
}

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

function assignTask(data){
	var url = '../groupTask/getGroupTaskAssignListPage.html?taskId='+data.id
	             +'&taskDateLimit='+data.cols.taskDateLimit+"&taskTitle="+encodeURI(encodeURI(data.cols.taskTitle));
    tabs.add({
        id: 'groupTaskAssign'+data.id,
        name: 'groupTaskAssign',
        pid: tabs.getSelfId(window),
        title : '分配信息',
        url : url,
        lock : false
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

$("#btnAdd").click(function() {
	var url = '../task/getTaskInfoInsertPage.html?taskType=addTask';
    showDialog({
        id: 'addTask',
        title: '新建任务',
        url: url,
        width: 660,
        height: 480,
        tabId: tabs.getSelfId(window)
    });
});

function searchTaskList() {
	var postJson =getPostFields();
    // if($('#myTask').attr('checked')=='checked'){
    //     selectFlag = '1';
    // }else{
    //     selectFlag = '0';
    // }
    // postJson['myTask'] =selectFlag;
	$('#taskGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshTaskList() {
	$('#taskGrid').flexReload();
}