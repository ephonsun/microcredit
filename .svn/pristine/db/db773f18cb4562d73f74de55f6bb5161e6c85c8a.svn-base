// 页面加载完成时...
$(function(){
	// 加载角色列表
	$('#groupGrid').flexigrid({
        height: 500,
        url: '../group/queryGroupList.html',
        usePage: true,
        fields: [
        	{ display: '团队名称', field: 'teamGroupName', width: 100 ,align : 'center' },
        	{ display: '团队主管', field: 'leaderName', width: 100 ,align : 'center'},
        	{ display: '客户经理', field: 'managerName', width: 200 ,align : 'center'},
        	{ display: '后台人员', field: 'backerName', width: 200 ,align : 'center'}
        ],
        action: {
        	display: '操作',
        	width: 100,
            align : 'center',
        	buttons: [
                //{
                //    display: '查看',
                //    onClick: function(tr, data){
                //        queryRole(data);
                //    }
                //},
            	{
            		display: '编辑',
            		onClick: function(tr, data){
            			addOrUpdateGroup(data);
            		}
        		},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteGroup(data);
            		}
        		}
            ]
        },
        onComplete : function(data) {
			$('#lblStatistics').text(data.total);
		}
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
        searchGroupList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshGroupList();
	});
});

function addOrUpdateGroup(data){

	showDialog({
		id: 'addOrUpdateGroup',
		title : '团队列表-编辑',
		url : '../group/getAddSysTeamGroupPage.html?groupId='+data.id+"&groupName="+encodeURI(encodeURI(data.cols.teamGroupName)),
		width : 660,
		height : 435,
		tabId: tabs.getSelfId(window)
	});
}

function deleteGroup(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除团队“'+ data.cols.teamGroupName+ '”吗？',
        ok:function(){
            var postJson = {};
            postJson['groupId'] = data.id;
            jQuery.ajax({
                type:'POST',
                dataType:'json',
                url:'../group/deleteGroupById.html',
                data:postJson,
                success: function(re){
                    if(re.success){
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                        $('#groupGrid').flexReload();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: '团队里还有成员，不能删除！'
                        });
					}
                }
            });
        },
        cancel: function() {}
    });
}

//跳转到新建页面
$("#btnAdd").click(function() {

	showDialog({
		id: 'addOrUpdateGroup',
		title : '团队列表-新建',
		url : '../group/getAddSysTeamGroupPage.html',
		width : 660,
		height : 435,
		tabId: tabs.getSelfId(window)
	});

});

function searchGroupList() {
	var postJson = {};
	postJson['groupName'] = $('#groupName').val();
	postJson['userName'] = $('#userName').val();
	$('#groupGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshGroupList() {
	$('#groupGrid').flexReload();
}