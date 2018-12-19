// 机构数节点数据
var agyNodes;
var oTree;
// 根节点数据
var nodes;
// 当前节点数据
var currentNode;
// 当前节点父节点数据
var pNode;
var isRoot;

// 机构树配置
var agyConfig = {
	callback : {
		onClick : treeOnclick
	},
	view : {
		showIcon : false,
		expandSpeed : ''
	},
	data : {
		simpleData : {
			enable : true
		}
	}
};

// 选择第一个节点
function initNodeSelect() {
	oTree = jQuery.fn.zTree.getZTreeObj('ulAgencys');
	nodes = oTree.getNodes();
	// 如果节点数量>零
	if (nodes.length > 0) {
		currentNode = nodes[0];
		// 选中第一个节点
		oTree.selectNode(currentNode);
		// 禁用“上移”按钮
		upDisabled();
		downDisabled();
		// 启用“新增”，“删除”按钮
		addEnable();
		delEnable();
		// 点击节点
		treeOnclick(event, currentNode.id, currentNode);
	}
}
// 点击节点
function treeOnclick(event, treeId, node) {
	currentNode = node;
	pNode = node.getParentNode();
	var childs;
	if (null == pNode) {
		childs = nodes;
	} else {
		childs = pNode.children;
	}
	if (node.id == childs[0].id) { // 第一个节点
		// 禁用“上移”按钮
		upDisabled();
	} else {
		// 启用“上移”按钮
		upEnable();
	}
	if (node.id == childs[childs.length - 1].id) { // 最后一个节点
		// 禁用“下移”按钮
		downDisabled();
	} else {
		// 启用“下移”按钮
		downEnable();
	}
	if(node.id==1){
		// 禁用“删除”按钮
		delDisabled();
	}else{
		delEnable();
	}
    isRoot = isRootTreeNode(node);
	searchUserList();
}
// 页面加载完成时...
$(function() {
	
	// 获取显示机构树
	showDeptList(deptJson);

	// 加载状态下拉单选框
	$('#sltState').selectbox();

	// 默认第一级节点
	initNodeSelect();

	// 加载机构人员列表
	$('#staffGrid').flexigrid({
		height : 312,
		// multiSelect : true,
		url : '../user/queryDeptUserList.html',
		dataType : 'json',
		params : {
			'deptId' : currentNode.id
		},
		fields : [
			{ display : '用户名', field : 'userAccount', width : 100, align : 'center' },
			{ display : '姓名', field : 'userName', width : 100, align : 'center' },
			{ display : '角色', field : 'roleNames', width : 185, align : 'center'},
			{ display : '状态', field : 'userStatueName', width : 90, align : 'center' },
            { display : '归属机构', field : 'deptName', width : 135, align : 'center' },
            { display : '上次登录时间', field : 'userLoginDate', width : 135, align : 'center' }
//			{ display : '虚拟服务经理', field : 'userVirtualManager', width : 80, align : 'left' }
		],
		action : {
			display : '操作',
			width : 200,
			align : 'center',
			buttons : [{
				display : '编辑',
				onClick : function(tr, data) {
					tabs.add({
						id : 'addOrUpdateUser'+data.id,
						pid: tabs.getSelfId(window),
						name : 'addOrUpdateUser',
						title : '人员-详情',
						url : '../user/initPmsUserPage.html?userId=' + data.id,
						lock : false
					});
				},
                isDisabled: function(data){
                 if(editUserEnable == 1){
                	if(data.cols.optionFlag==0){
                		  return true;	
                	}else{
                		return false;	
                	}}
                   return true;
                }
			}, {
				display : '删除',
				onClick : function(tr, data) {
					showConfirm({
						icon: 'confirm',
						content: '您确认要删除此用户吗',
						ok: function() {
							var postJson = {};
							postJson['user.userId'] = data.id;
							jQuery.ajax({
								type : 'POST',
								url : '../user/deleteUser.html?userId='+data.id,
								data : postJson,
								success : function(result) {
                                    result = JSON.parse(result);
									if(result.success){
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '删除成功'
                                        });
                                        searchUserList();
									}else{
                                        showConfirm({
                                            icon: 'warning',
                                            content: result.value
                                        });
									}
								}
							});
						},
                        cancel: function() {}
					});
				},
                isDisabled: function(data){
                	 if(delUserEnable == 1){
                     	if(data.cols.optionFlag==0){
                     		  return true;	
                     	}else{
                     		return false;	
                     	}}
                        return true;
                     }
			}, {
				display : '重置密码',
				onClick : function(tr, data) {
					showConfirm({
						icon: 'confirm',
						content: '您确认要重置此用户的密码吗',
						ok: function() {
							var postJson = {};
							postJson['user.userId'] = data.id;
							jQuery.ajax({
								type : 'POST',
								url : '../user/resetPmsUserPasswd.html?userId='+data.id,
								data : postJson,
								success : function(result) {
									showConfirm({
										icon: 'succeed',
										content: '重置成功'
									});
								}
							});
						},
						cancel: function() {}
					});	
				},
                isDisabled: function(data){
                	 if(resetPasswordEnable == 1){
                      	if(data.cols.optionFlag==0){
                      		  return true;	
                      	}else{
                      		return false;	
                      	}}
                         return true;
                      }
			},{
                display: '启用',
                name: 'enable',
                isDisabled: function(data){
                    if(isEnableEnable == 1){
                    if(data.cols.optionFlag==1){	
                        if(data.cols.userStatus== 1){
                            return true;
                        }
                        return false;
                    }}
                    return true;
                },
                onClick: function(tr,data){
                    showConfirm({
                        icon: 'confirm',
                        content: '您确认要启用吗',
                        ok: function() {
                            jQuery.ajax({
                                type : 'POST',
                                url : '../user/enableUser.html?userId='+data.id,
                                data : {},
                                success : function(result) {
                                    showConfirm({
                                        icon: 'succeed',
                                        content: '启用成功'
                                    });
                                    $('#staffGrid').flexReload();
                                }
                            });
                        },
                        cancel: function() {}
                    });
                }
            },
                {
                    display: '停用',
                    name: 'disable',
                    isDisabled: function(data){
                        if(isEnableEnable == 1){
                        if(data.cols.optionFlag==1){	
                            if(data.cols.userStatus==0){
                                return true;
                            }
                            return false;
                        }}
                        return true;
                    },
                    onClick: function(tr,data){
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要停用吗',
                            ok: function() {
                                var postJson = {};
                                postJson['user.id'] = data.id;
                                jQuery.ajax({
                                    type : 'POST',
                                    url : '../user/disableUser.html?userId='+data.id,
                                    data : postJson,
                                    success : function(result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '停用成功'
                                        });
                                        $('#staffGrid').flexReload();
                                    }
                                });
                            },
                            cancel: function() {}
                        });
                    }
                }]
		},

		factory:{
			extendDataCol:function(o,field,cell){
				if(field == 'userVirtualManager'){
					if(cell == '1'){
						cell = '是';
					}else{
						cell = '否';
					}
				}
				return cell;
			}
		},
		// 使用分页
		usePage : true,
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
		searchUserList();
	});
	
	$('#btnRefresh').click(function(){
		$('#staffGrid').flexReload();
	});
});

//获取显示机构树
function showDeptList(deptJson) {
	agyNodes = jQuery.parseJSON(deptJson);
	// 加载机构树结构
	$.fn.zTree.init($('#ulAgencys'), agyConfig, agyNodes);
	// 默认第一级节点
	initNodeSelect();
}

//刷新机构树
function refreshDeptTree(text){
	var postJson={};
	$.ajax({
		type: 'POST',
		url: '../deptManager/refreshTree.html',
		async: false,
		data: postJson,
		success: function(deptJson) {
			// 加载机构树结构
			$.fn.zTree.init($('#ulAgencys'), agyConfig, $.parseJSON(deptJson));
			oTree = jQuery.fn.zTree.getZTreeObj('ulAgencys');
			currentNode = oTree.getNodeByParam("id", text, null);
            if(currentNode){
                pNode = currentNode.getParentNode();
                oTree.selectNode(currentNode);
                treeOnclick(event, currentNode.id, currentNode);
            }else{
                initNodeSelect();
            }

		}
	});
}

function searchUserList() {
	var postJson = getPostFields();
	if (currentNode) {
		postJson['deptId'] = currentNode.id;
		postJson['searchCode'] = currentNode.flag;
		postJson['userAccount'] = jQuery.trim($('#userAccount').val());
		postJson['userName'] = jQuery.trim($('#userName').val());
        postJson['userStatus'] = jQuery.trim($('#sltState').val());
		$('#lblAgency').text(currentNode.name);
	}
	if ($('#chkInclude')[0].checked) {
		postJson['includeSub'] = '1';
	}else{
		postJson['includeSub'] = '0';
	}
	$('#staffGrid').flexSearch(postJson);
}

// 新增、删除、编辑、上移、下移操作
function changeActive(flag) {
	//if($('.editor-delete').attr('disabled'))
	//  return;
	var index;
	var childs;
	if (null == pNode) {
		childs = nodes;
	} else {
		childs = pNode.children;
	}
	for (var i = 0; (i < childs.length) && (childs.length > 0); i++) {
		if (currentNode.id == childs[i].id)
			index = i;
	}
	if (flag == 'insert')
		opendeptDetail('', currentNode.id, flag);
	if (flag == 'delete')
		deleteDept(currentNode.id);
	if (flag == 'update')
		opendeptDetail(currentNode.id, currentNode.pId,flag);
	if (flag == 'up') {
		upMovingDept(currentNode.id, childs[index - 1].id);
	}
	if (flag == 'down')
		downMovingDept(currentNode.id, childs[index + 1].id);

}

// 打开新增或编辑机构页面请求
function opendeptDetail(deptId, deptParentId, flag) {
    //alert(flag);
	var title;
	if (flag == 'insert'){
		title = '组织机构-新建';
		var url = '../deptManager/getAddDeptPage.html?deptParentId=' + deptParentId;
	}else{
		title = '组织机构-编辑';
		var url = '../deptManager/getUpdateDeptPage.html?dept.deptId=' + deptId
				+ '&deptParentId=' + deptParentId + '&isRoot=' + isRoot;
	}
	showDialog({
		id: 'opendeptDetail',
		title : title,
		url : url,
		width : 660,
		height : 275,
		tabId: tabs.getSelfId(window)
	});
}

// 删除机构
function deleteDept(deptId) {
	var url="";
	var postJson={};
	postJson['deptId'] = currentNode.id;
	jQuery.ajax({
		type : 'POST',
		url : '../deptManager/checkDelete.html',
		async : false,
		data : postJson,
		success : function(result) {
			if(result){
				if('hasUser' == result){
					showConfirm('机构“'+currentNode.name+'”或其子机构下还有人员，无法删除！请将人员转移至其他机构，再进行删除');
				}else if('hasCustomer' == result){
					showConfirm('机构“'+currentNode.name+'”或其子机构下还有客户，无法删除！');
				}	
			}else{
				showConfirm({
					icon: 'confirm',
					content: '您确认要删除机构“'+currentNode.name+'（包含下属机构）”吗',
					ok: function() {
						jQuery.ajax({
							type : 'POST',
							url : '../deptManager/deleteDept.html',
							data : postJson,
							success : function(result) {
								showConfirm({
									icon: 'succeed',
									content: '删除成功'
								});
								refreshDeptTree(result);
							}
						});
					},
					cancel: function() {}
				});
			}
		}
	});
}
// 上移机构
function upMovingDept(currentDeptId, prevDeptId) {
	var urlStr = '../deptManager/moveupPmsDept.html?currentDeptId='
			+ currentDeptId + '&prevDeptId=' + prevDeptId;
	movingDept(urlStr);
}
// 下移机构
function downMovingDept(currentDeptId, nextDeptId) {
	var urlStr = '../deptManager/movedownPmsDept.html?currentDeptId='
			+ currentDeptId + '&nextDeptId=' + nextDeptId;
	movingDept(urlStr);
}
function movingDept(urlStr){
	var postJson={};
	jQuery.ajax({
		type : 'POST',
		url : urlStr,
		async : false,
		data : postJson,
		success : function(text) {
			refreshDeptTree(text);
		}
	});
}
// 禁用新增
function addDisabled() {
	$('.editor-add').addClass('editor-add-disabled').attr('disabled', true);
}
// 启用新增
function addEnable() {
	$('.editor-add').removeClass('editor-add-disabled').removeAttr('disabled');
}

// 禁用上移
function upDisabled() {
	$('.editor-up').addClass('editor-up-disabled').attr('disabled', true);
}
// 启用上移
function upEnable() {
	$('.editor-up').removeClass('editor-up-disabled').removeAttr('disabled');
}
// 禁用下移
function downDisabled() {
	$('.editor-down').addClass('editor-down-disabled').attr('disabled', true);
}
// 启用下移
function downEnable() {
	$('.editor-down').removeClass('editor-down-disabled').removeAttr('disabled');
}
// 禁用删除
function delDisabled() {
	$('.editor-delete').addClass('editor-delete-disabled').attr('disabled', true);
}
// 启用删除
function delEnable() {
	$('.editor-delete').removeClass('editor-delete-disabled').removeAttr('disabled');
}
// 禁用编辑
function ediDisabled() {
	$('.editor-edit').addClass('editor-edit-disabled').attr('disabled', true);
}
// 启用编辑
function ediEnable() {
	$('.editor-edit').removeClass('editor-edit-disabled').removeAttr('disabled');
}
// 跳转到新建用户页面
$("#btnAdd").click(function() {
	tabs.add({
		id : 'addOrUpdateUser',
		name : 'addOrUpdateUser',
		pid: tabs.getSelfId(window),
		title : '人员-新建',
		url : '../user/initPmsUserPage.html?user.userDeptId=' + currentNode.id,
		lock : false
	});
});
// 批量删除用户
$('#btnDelete').click(function() {
	showConfirm({
		icon: 'confirm',
		content: '您确认要删除选中项吗',
		ok: function() {
			var data = $('#staffGrid').getSelectedRows();
			for(var i = 0; i<data.datas.length; i++){
				var postJson = {};
				postJson['user.userId'] = data.datas[i].id;
				jQuery.ajax({
					type : 'POST',
					url : '../user/deletePmsUser.html',
					data : postJson,
					success : function(result) {
						searchUserList();
					}
				});
			}
			showConfirm({
				icon: 'succeed',
				content: '删除成功'
			});
		},
		cancel: function() {}
	});	
});

// 刷新人员表
function refresh() {
	$('#staffGrid').flexReload();
}

//是根节点返回1不是则返回0
function isRootTreeNode(node){
    var pNode = node.getParentNode();
    if(pNode){
        return 0;
    }else{
        return 1;
    }
}