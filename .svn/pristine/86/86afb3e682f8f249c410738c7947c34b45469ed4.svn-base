var isExist= true ;
$(function() {
    initTreeSelectBox();

    initMaxlengthTips('#txtRemark','#tips');
    banger.verify('#txtRemark',{ name: 'maxlength', tips: '“机构备注”内容过长' });

    // 非空校验
    banger.verify('#deptName', [{
        name : 'required',
        tips : '“机构名称”必须填写'
    },deptNameRepeatRule]);
    banger.verify('#deptCode', [{
        name : 'required',
        tips : '“机构编号”必须填写'
    },{ name: 'account', tips: '机构编号必须由数字、字母或下划线组成!'},deptCodeRepeatRule]);
    banger.verify('#txtBelongTo', [{
        name : 'required',
        tips : '“归属机构”必须填写'
    },belongAllow]);
});
function initTreeSelectBox(){
    //加载归属机构下拉树形选择框
    $('#txtBelongTo').treeselectbox({
        treeId : 'belongToTree',
        panelWidth : '400px',
        nodes : deptJson,
        loadType: 'window.load',
        checkEnable: false,
        onTextClick : function(treeId) {
            // 根据文本框内的值初始化选中节点
            var value = $.trim($('#deptParentId').val());
            if (value) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', value, null);
                zTree.selectNode(node);
            }
        },
        beforeOnClick : function(treeId, node, flag){
            $('#deptParentId').val(node.id);
        },
        onClick : function(e, treeId, node, flag) {
            isExist = depIsExsit(node.id);
            $('#deptParentId').val(node.id);
            $('#txtBelongTo').val(node.name).blur();
            $(".ui-treeselectbox-box").hide();
        },
        onComplete: function(treeId){
            var checkId = $('#deptParentId').val();
            if (checkId) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', checkId, null);
                if(node){
                    zTree.selectNode(node)
                    $('#txtBelongTo').val(node.name);
                }
            }
        }
    });
}

    // 关闭页卡
    $('#btnCancel').click(function() {
        closeTab();
    });

// 提交新增和修改用户的数据
$('#btnSave').click(function() {
    saveDept('saveClose');
});

// 提交新建人员数据并继续新建
$('#btnContinue').click(function() {
    saveDept('saveContinue');
});

function saveDept(opType){
    if(!isExist){
        showConfirm({
            content: '用户归属机构已被删除，数据保存失败'
        });
        return false;
    }
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    //获取人员备注
    var txtRemark = $('#txtRemark').val();
    jQuery.ajax({
        type : 'post',
        url : '../dept/updatePmsDept.html',
        data : postJson,
        success : function(html) {
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
        }
    });
}

function depIsExsit(id){
    var  bool = false, data = {};
    data['deptId'] = id;
    var url = '../dept/checkDeptIsExist.html';
    jQuery.ajax({
        type : 'post',
        url : url,
        async : false,
        data : data,
        success : function(result) {
            if (result) {
                result = jQuery.parseJSON(result);
                bool = result.success;
            } else{
                bool = false;
            }
        }
    });
    return bool;
}
// 机构编号同名校验
var deptCodeRepeatRule = {
	name : 'repeated',
	tips : '',
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
		return bool;
	}
};

//归属机构是否为自己校验
var belongAllow = {
    name : 'repeated',
    tips : '',
    rule : function() {
        var rule = this, bool = true;
        var deptId = $('#deptId').val();
        var deptParentId = $('#deptParentId').val();
        var url = '../deptManager/checkBelongDept.html?deptId='+deptId+"&belongToDeptId="+deptParentId;
        jQuery.ajax({
            type : 'post',
            url : url,
            async : false,
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

//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
    tabs.close(tabs.getSelfId(window));
}
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
        return bool;
    }
};
