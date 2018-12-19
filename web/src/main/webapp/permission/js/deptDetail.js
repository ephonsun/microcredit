//被选中的核实事项
var itemIds = $('#itemIds').val();

$(function() {
	initTreeSelectBox();
	// 获取多选下拉选项
	moreSelectBox();
	initMaxlengthTips('#txtRemark','#tips');
    banger.verify('#txtRemark',{ name: 'maxlength', tips: '“机构备注”内容过长' });
    
	// 非空校验
	banger.verify('#deptName', {
		name : 'required',
		tips : '“机构名称”必须填写'
	});
    banger.verify('#deptCode', [{
		name : 'required',
		tips : '“机构编号”必须填写'
	},deptCodeRepeatRule]);
	banger.verify('#txtBelongTo', [{
		name : 'required',
		tips : '“归属机构”必须填写'
	},belongAllow]);
});
function initTreeSelectBox(){
	//加载归属机构下拉树形选择框
	$('#txtBelongTo').treeselectbox({
		treeId : 'belongToTree',
		nodes : deptJson,
		onTextClick : function(treeId) {
			// 根据文本框内的值初始化选中节点
			var value = $.trim($('#deptParentId').val());
			if (value) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var node = zTree.getNodeByParam('id', value, null);
				zTree.selectNode(node);
			}
		},
		onClick : function(e, treeId, node, flag) {
            $('#deptParentId').val(node.id);
            $('#txtBelongTo').val(node.name).blur();
		}
	});
}

// 保存新增and修改
$("#btnSave").click(function() {
	if (banger.verify('#deptTab')) {
		var postJson = getPostFields();
		postJson['itemIds'] = itemIds; 
		jQuery.ajax({
			type : "post",
			url : "../deptManager/addUpdateDept.html?",
			data : postJson,
			success : function(html) {
				showConfirm({
					icon: 'succeed',
		 		    content: '操作成功'
				});
				// 关闭弹出框
				var dialog = getDialog('opendeptDetail');
				var win = tabs.getIframeWindow(dialog.config.tabId);
				if(win&&win.refreshDeptTree){
					if($('#deptId').val()=='0'){
						win.refreshDeptTree('1');
					}else{
						win.refreshDeptTree($('#deptParentId').val());
					}
				}
				dialog.close();
		 	}
		});
	}
});

// 保存新建并继续新建
$('#btnContinue').click(function() {
	if (banger.verify('#form')) {
		var postJson = getPostFields();
		jQuery.ajax({
			type : "post",
			url : "../deptManager/addUpdateDept.html?",
			data : postJson,
			success : function(html) {
				showConfirm({
		 			icon: 'succeed',
		 			content: '新建成功'
		 		});
				initTreeSelectBox();
				var dialog = getDialog('opendeptDetail');
				var win = tabs.getIframeWindow(dialog.config.tabId);
				if(win&&win.refreshDeptTree){
					win.refreshDeptTree($('#hidBelongTo').val());
				}
                toCleanForm('#form');
			}
		});
	}
});

//获取多选下拉选项
function moreSelectBox(){
	var deptId = jQuery.trim($('#deptId').val());
	$('#txtArea').checkbox({
		url: '../deptManager/getManagementArea.html?deptId=' + deptId,
        selected: areaIds.split(','),
		//勾选后处理函数
		onCheck: function(text, value) {
			itemIds = value.join(',');
		},
		scrollRange:5
	});
}

$('#btnCancel').click(function() {
	// 关闭弹出框
	var dialog = getDialog('opendeptDetail');
	dialog.close();
});

// 机构编号同名校验
var deptCodeRepeatRule = {
	name : 'deptCodeRepeatRule',
	tips : '已存在相同的机构编号',
	rule : function() {
		var rule = this, bool = true, data = {};
		if ($('#deptCode').val())
			data['deptCode'] = $('#deptCode').val();
		if ($('#deptId'))
			data['deptId'] = $('#deptId').val();
		var url = '../deptManager/checkDeptCodeIsExist.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
				//alert('result='+result);
				if (result) {
                    result = jQuery.parseJSON(result);
                    rule.tips = result.value;
                    bool = result.success;
                } else{
                    bool = true;
                }
			}
		});
		//alert(bool);
		return bool;
	}
};

//同层机构名称重复校验
var deptNameRepeatRule = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = {};
		if ($('#deptName').val())
			data['dept.deptName'] = $('#deptName').val();
		if ($('#deptId'))
			data['dept.deptId'] = $('#deptId').val();
		if ($('#deptParentId'))
			data['dept.deptParentId'] = $('#deptParentId').val();
		var url = '../deptManager/checkDeptNameIsExist.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
				//alert('result='+result);
				if (result) {
                    result = jQuery.parseJSON(result);
                    rule.tips = result.value;
                    bool = result.success;
                } else{
                    bool = true;
                }
			}
		});
		//alert(bool);
		return bool;
	}
};

//归属机构是否为自己校验
var belongAllow = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true;
		//alert('aaa');
		var deptId = $('#deptId').val();
		var parentId = $('#deptParentId').val();
		var belongName = $('#txtBelongTo').val();
		if(deptId == parentId){
			bool = false;
			this.tips = '归属机构'+belongName+'为当前机构的子机构，请重新选择';
		}
		//alert(bool);
		return bool;
	}
};

