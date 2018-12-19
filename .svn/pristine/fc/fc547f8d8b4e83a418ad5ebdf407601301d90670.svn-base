var userAccountLengthLimit = $('#userAccountLengthLimit').val();
var pageFlag =   $('#pageFlag_hid').val();
// 业务逻辑
$(function(){
	//加载归属机构下拉树形选择框
	// 主管机构树
	var zHeadTree;
	//判断是否存在机构树
	isDeptTreeExist();
	//判断是否存在角色
	if ($("#roleFlag_hid").val() == 1) {
		showConfirm({
			icon: 'warning',
			content: "无法新建用户,请先新建角色",
			ok: function () {
				closeTab();
			},
			cancel: function() {
				closeTab();
			}
		});

	}
     if(pageFlag!=1){
	$('#userDeptId').treeselectbox({
		panelWidth : '100%',
		treeId: 'userDeptIdTree',
		loadType: 'window.load',
		url: '../deptManager/refreshTree.html',
        async: false,
		onComplete: function(treeId){
			var id = $.trim($('#userDeptId_hid').val());
			if(id){
				var oTree = $.fn.zTree.getZTreeObj(treeId), node = oTree.getNodeByParam('id', id, null);
                oTree.selectNode(node, true);
				$('#userDeptId').val(node.name);
			}
		},
		onClick: function(e, treeId, node) {
			$('#userDeptId_hid').val(node.id);
			$('#userDeptId').val(node.name).blur();
			 banger.controls.hide(); 
		}
	});
     }

	// 非空校验
	banger.verify('#userName', {name : 'required', tips : '用户姓名必须填写'});
	banger.verify('#userDeptId', {name : 'required', tips : '归属机构必须选择'});
	banger.verify('#userAccount', [{name : 'required',	tips : '用户账号必须填写'}, accountRepeatRule, accountLengthLimit]);
    banger.verify(".role", { name: 'checked[1,null]', tips: '必须分配一个角色' });
    banger.verify("#userPhoneNumber", { name: 'telephone', tips: '联系方式格式不正确' });
    initMaxlengthTips('#userRemark','#tips');
    banger.verify('#userRemark',{ name: 'maxlength', tips: '“用户备注”内容过长' });
	banger.verify('#userPassAmount',[{name:'money',tips:"金额格式不正确，格式为[10位].[2位]"}]);

	// 提交新增和修改用户的数据
	$('#btnSave').click(function() {
        saveUser('saveClose');
	});

	// 提交新建人员数据并继续新建
	$('#btnContinue').click(function() {
		saveUser('saveContinue');
	});

	// 关闭页卡
	$('#btnCancel').click(function() {
		showCancelConfirmForEdit(function(){
			closeTab();
		})
	});
	initGroupAllTreeEdit(true,"dueGroupToTree","txtDueGroup","userGroupPermit", $("#txtDueGroup").val(),false);

});

function showDeptTree(){

	
}
function  isDeptTreeExist(){
    jQuery.ajax({
        type : 'post',
        async : false,
        url : '../deptManager/refreshTree.html',
        success : function(json) {
        	var result = jQuery.parseJSON(json);
        	if(result.length==0){
        			showConfirm({
                    icon: 'warning',
                    content: "无法新建用户,请先新建机构",
                    ok: function(){
                        closeTab();
                    }
                });
        	}

        }
    });
}
function saveUser(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    var roleIds = getRoleIdsChecked();
    postJson['roleIds'] = roleIds;

    jQuery.ajax({
        type : 'post',
        url : '../user/updatePmsUser.html',
        data : postJson,

        success : function(json) {
        	var result = jQuery.parseJSON(json);
        	if(result.success){
        		showConfirm({
                    icon: 'succeed',
                    content: '操作成功'
                });
                if(opType == 'saveContinue')
                {
                    tabs.refresh(tabs.getSelfId(window));
                }else{
                    closeTab();
                }
        	}else{
        		showConfirm({
                    icon: 'warning',
                    content: result.cause,
                    ok: function(){}
                });
        	}
            
        }
    });
}

//获取角色复选框Ids
function getRoleIdsChecked(){
	var roles = $('.roles').find('input[class="ui-check role"]');
	var checkRoles = $('.roles').find('input[class="ui-check checkRole"]');
	var str = "";
	roles.each(function(){
		if($(this).is(':checked')){
			if(str){
				str = str +","+ $(this).val();
			}else{
				str=$(this).val();
			}
		}
	});
	checkRoles.each(function(){
		if($(this).is(':checked')){
			if(str){
				str = str +","+ $(this).val();
			}else{
				str=$(this).val();
			}
		}
	});
	return str;
}

//用户帐号重复性校验
var accountRepeatRule = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../user/checkUserAccountIsRepeat.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
				if (result) {
					result = jQuery.parseJSON(result);
					rule.tips = result.value;
					bool = result.success;
				} else{
					bool = true;
                }
			}
		});
		return bool;
	}
};

//用户帐号长度限制
var accountLengthLimit = {
	name : 'lengthLimit',
	tips : '用户账号必须为'+userAccountLengthLimit+'位',
	rule : function() {
        if(userAccountLengthLimit != "" || userAccountLengthLimit != 0){
            var account = $('#userAccount').val();
            if(account.length != userAccountLengthLimit){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
        return true;
	}
};

// 回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
}


//初始化所有机构树,用于编辑
// chkEnable false  单选
// chkEnable true   多选复选框，勾选父机构不选下属机构
// disCheckIds      禁止选择Ids
function initGroupAllTreeEdit(chkEnable,treeId,txtId,valueId,checkIds,disCheckIds){
	var json;
	var bool = true;
	// 请求获取机构树
	jQuery.ajax({
		type : 'POST',
		url : '../group/initGroupTree.html',
		async : false,
		data : {},
		success : function(text) {
			if(text == "[]"){
				bool = false;
                $("#txtDueGroup").attr("disabled",true);
                $("#userGroupPermit_").removeClass();
				return;
			}
			json = jQuery.parseJSON(text);
		}
	});
	if(bool) {
        //加载归属机构下拉树形选择框
        $('#' + txtId).treeselectbox({
            treeId: treeId,
            nodes: json,
            loadType: 'window.load',
            panelWidth: '100%',
            checkEnable: chkEnable,
            checkReaction: {"Y": "s", "N": "s"},
            onClick: function (e, treeId, node, flag) {
                $('#' + valueId).val(node.id);
                $('#' + txtId).val(node.name).blur();
                banger.controls.hide();
            },
            beforeOnCheck: function (treeId, node) {
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                var nodes = new Array();
                nodes = getAllChildrenNodes(node, nodes);
                var bool = node.checked;
                if (bool && typeof nodes != 'undefined') {
                    for (var i = 0; i < nodes.length; i++) {
                        zTree.setChkDisabled(nodes[i], !bool);
                    }
                }
            },
            onCheck: function (e, treeId, node) {
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                var nodes = new Array();
                nodes = getAllChildrenNodes(node, nodes);
                var bool = node.checked;
                if (bool && typeof nodes != 'undefined') {
                    for (var i = 0; i < nodes.length; i++) {
                        zTree.setChkDisabled(nodes[i], bool);
                    }
                }
                if (disCheckIds) {
                    var disIds = disCheckIds.split(',');
                    for (var i = 0; i < disIds.length; i++) {
                        var node = zTree.getNodeByParam('id', disIds[i], null);
                        if (node) {
                            var nodes = new Array();
                            nodes = getAllChildrenNodes(node, nodes);
                            for (var j = 0; j < nodes.length; j++) {
                                zTree.setChkDisabled(nodes[j], true);
                            }
                            zTree.setChkDisabled(node, true);
                        }
                    }
                }
                var vid = '', d = zTree.getCheckedNodes(treeId, 'D'), v = '';
                jQuery.each(d, function (i) {
                    if (!d[i].chkDisabled) {
                        v += d[i].name + ',';
                        vid += d[i].id + ',';
                    }
                });
                if (v.length > 0) {
                    v = v.substring(0, v.length - 1);
                }
                if (vid.length > 0) {
                    vid = vid.substring(0, vid.length - 1);
                }
                $('#' + txtId).val(v).focus().blur();
                $('#' + valueId).val(vid);
            },
            onComplete: function (treeId) {
                var names = [];
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                if (chkEnable) {
                    if (disCheckIds) {
                        var disIds = disCheckIds.split(',');
                        for (var i = 0; i < disIds.length; i++) {
                            var node = zTree.getNodeByParam('id', disIds[i], null);
                            if (node) {
                                var nodes = new Array();
                                nodes = getAllChildrenNodes(node, nodes);
                                for (var j = 0; j < nodes.length; j++) {
                                    zTree.setChkDisabled(nodes[j], true);
                                }
                                zTree.setChkDisabled(node, true);
                            }
                        }
                    }
                    if (checkIds) {
                        var ids = checkIds.split(',');

                        for (var i = 0; i < ids.length; i++) {
                            var node = zTree.getNodeByParam('id', ids[i], null);
                            var nodes = new Array();
                            nodes = getAllChildrenNodes(node, nodes);
                            for (var j = 0; j < nodes.length; j++) {
                                zTree.setChkDisabled(nodes[j], true);
                            }
                        }
                        var idsNew = [];
                        for (var i = 0; i < ids.length; i++) {
                            var node = zTree.getNodeByParam('id', ids[i], null);
                            if (node) {
                                zTree.setChkDisabled(node, false);
                                zTree.selectNode(node);
                                zTree.checkNode(node);
                                names.push(node.name);
                                idsNew.push(ids[i]);
                            }
                        }
                        $('#' + txtId).val(names.join(','));
                        $('#' + valueId).val(idsNew);
                    }
                }
            }
        });
    }
}