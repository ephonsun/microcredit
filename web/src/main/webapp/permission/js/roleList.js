// 页面加载完成时...
$(function(){
	// 加载角色列表
	$('#roleGrid').flexigrid({
        height: 312,
        url: '../role/queryRoleList.html',
        fields: [
        	{ display: '角色名称', field: 'roleName', width: 150 },
        	{ display: '角色描述', field: 'roleRemark', width: 800 }
        ],
        action: {
        	display: '操作',
        	width: 180,
        	buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){
                        queryRole(data);
                    }
                },
            	{
            		display: '编辑',
            		onClick: function(tr, data){
            			addOrUpdateRole(data);
            		} ,
                    isDisabled: function(data){
                        if(data.cols.roleType > 1){
                            return false;
                        }
                        return true;
                    }
        		},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteRole(data);
            		},
                    isDisabled: function(data){
                        if(data.cols.roleType ==99){
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
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
		searchRoleList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshRoleList();
	});
});

function queryRole(data){
	tabs.add({
        id :'lookRole'+data.id,
        pid:tabs.getSelfId(window),
        name:'lookRole',
        title:'查看角色',
        url:'../role/initRoleDetailPage.html?roleId='+data.id+'&flag=0',
        lock:false
    });
}

function addOrUpdateRole(data){
	tabs.add({
		id :'addOrUpdateRole'+data.id,
		pid:tabs.getSelfId(window),
		name:'addOrUpdateRole',
		title:'角色-详情',
		url:'../role/initPmsRolePage.html?roleId='+data.id+'&flag=1',
		lock:false
	});
}

function deleteRole(data){
	jQuery.ajax({
        type: 'post',
        dataType:'json',
        data: {'roleId': data.id, 'roleName': data.cols.roleName},
        url: '../role/checkRoleCanDelete.html',
        sync: false,
        success: function(result){
            if(result.success){
            	showConfirmDelete(data);
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.value
                });
            }
        }
    });
}

function showConfirmDelete(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除角色“'+ data.cols.roleName+ '”吗',
        ok:function(){
            var postJson = {};
            postJson['roleId'] = data.id;
            jQuery.ajax({
                type:'POST',
                url:'../role/deleteRoleById.html',
                data:postJson,
                success:function(text){
                    showConfirm({
                        icon: 'succeed',
                        content: '删除成功'
                    });
                    refreshRoleList();
                }
            });
        },
        cancel: function() {}
    });
}

//跳转到新建角色页面
$("#btnAdd").click(function() {
	tabs.add({
		id : 'addOrUpdateRole',
		name : 'addOrUpdateRole',
		pid: tabs.getSelfId(window),
		title : '角色-新建',
		url : '../role/initPmsRolePage.html',
		lock : false
	});
});

function searchRoleList() {
	var postJson = {};
	postJson['roleName'] = jQuery.trim($('#roleName').val());
	$('#roleGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshRoleList() {
	$('#roleGrid').flexReload();
}