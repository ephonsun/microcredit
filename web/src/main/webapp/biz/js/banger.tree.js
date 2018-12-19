function initAreaTreeSelectBox(chkEnable,treeId,txtId,valueId,checkIds,treeType,urlSuffix){
    var json;
    //请求机构人员片区树
    var url = '../deptManager/initDeptUserAreaTree.html';
    if(treeType){
        url = url + '?treeType='+treeType;
    }
    
    if(urlSuffix){
    	if(treeType)
    		url = url +'&&'+urlSuffix;
    	else 
    		url = url + '?'+urlSuffix;
    }
    
    jQuery.ajax({
        type : 'POST',
        url : url,
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载机构人员片区树
    $('#'+txtId).treeselectbox({
        treeId : treeId,
        nodes : json,
        popupWidth:popupWidth,
        loadType: 'window.load',
        checkEnable: chkEnable,
        onTextClick : function(treeId) {
            if(!$('#'+txtId).val() && chkEnable){
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                zTree.checkAllNodes(false); // 取消所有选中状态
            }
            if (!chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', 'a-'+checkIds, null);
                if(node){
                    zTree.selectNode(node);
                }
            }
        },
        onCheck: function(e, treeId, node){
            var result = getCheckedData(treeId, 'A');
            $('#'+txtId).val(result.names);
            $('#'+valueId).val(result.ids);
        },
        onClick : function(e, treeId, node, flag) {
            if(!chkEnable & node.flag == 'A'){
                $('#'+txtId).val(node.name).blur();
                $('#'+valueId).val(node.id.split('-')[1]);
            }
        },
        onComplete: function(treeId){
            var names=[];
            if (checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                if(chkEnable) {
                    var ids = checkIds.split(',');
                    var i = 0;
                    for (; i < ids.length; i++) {
                        var node = zTree.getNodeByParam('id', 'a-' + ids[i], null);
                        if (node) {
                            zTree.selectNode(node)
                            zTree.checkNode(node);
                            names.push(node.name);
                        }
                    }
                    $('#' + txtId).val(names.join(','));
                }else{
                    var node = zTree.getNodeByParam('id', 'a-'+checkIds, null);
                    if(node){
                        zTree.selectNode(node);
                    }
                    $('#' + txtId).val(node.name);
                }
            }
        }
    });
}

function getAllChildrenNodes(node,result){
    if (node.isParent) {
        var childrenNodes = node.children;
        if (childrenNodes) {
            for (var i = 0; i < childrenNodes.length; i++) {
                result.push(childrenNodes[i]);
                result = getAllChildrenNodes(childrenNodes[i],result);
            }
        }
    }
    return result;
}

function initDeptUserTree(chkEnable,treeId,txtId,valueId,checkIds){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../deptManager/initDeptUserTree.html',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载归属机构下拉树形选择框
    $('#'+txtId).treeselectbox(  
    		{
        treeId : treeId,
        nodes : json,
        panelWidth : '400px',
        loadType: 'window.load',
        checkEnable: chkEnable,
        onTextClick : function(treeId) {
            if(!$('#'+txtId).val()){
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                zTree.checkAllNodes(false); // 取消所有选中状态
            }
            if (!chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', 'u-'+checkIds, null);
                if(node){
                    zTree.selectNode(node);
                }
            }
        },
        onClick : function(e, treeId, node, flag) {
            if(!chkEnable & node.flag == 'U'){
                $('#'+valueId).val(node.id.split('-')[1]);
                $('#'+txtId).val(node.name).blur();
            }
        },
        onCheck: function(e, treeId, node){
            var result = getCheckedData(treeId, 'U');
            $('#'+valueId).val(result.ids);
            $('#'+txtId).val(result.names);
        },
        onComplete: function(treeId){
            var names=[];
            if (chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var ids = checkIds.split(',');
                var i=0;
                for (; i<ids.length; i++)
                {
                    var node = zTree.getNodeByParam('id', 'u-'+ids[i], null);
                    if(node){
                        zTree.selectNode(node)
                        zTree.checkNode(node);
                        names.push(node.name);
                    }
                }
                $('#'+txtId).val(names.join(','));
            }
        }
    });
}

function initDeptTree(chkEnable,treeId,txtId,valueId,checkIds){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../deptManager/initDeptTree.html',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载归属机构下拉树形选择框
    $('#'+txtId).treeselectbox({
        treeId : treeId,
        nodes : json,
        loadType: 'window.load',
        checkEnable: chkEnable,
        checkReaction: { "Y": "s", "N": "s" },
        onTextClick : function(treeId) {
            if(!$('#'+txtId).val()){
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                zTree.checkAllNodes(false); // 取消所有选中状态
            }
            if (!chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', checkIds, null);
                if(node){
                    zTree.selectNode(node);
                }
            }
        },
        onCheck: function(e, treeId, node){
            var result = getCheckedData(treeId, 'D');
            $('#'+valueId).val(result.ids);
            $('#'+txtId).val(result.names).blur();
        },
        onComplete: function(treeId){
            var names=[];
            if (chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var ids = checkIds.split(',');
                var i=0;
                for (; i<ids.length; i++)
                {
                    var node = zTree.getNodeByParam('id', ids[i], null);
                    if(node){
                        zTree.selectNode(node)
                        zTree.checkNode(node);
                        names.push(node.name);
                    }
                }
                $('#'+txtId).val(names.join(','));
                $('#'+valueId).val(ids);
            }
        }
    });
}


//客户多选条件下拉框
function initJobTree(chkEnable,treeId,txtId,valueId,checkIds){
    var json;
    //获取客户基础数据树（职业）
    jQuery.ajax({
        type : 'POST',
        url : '../cusLookupItem/initCustomerJobTree.html',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });

    //加载客户职业下拉树形选择框
    $('#'+txtId).treeselectbox({
        treeId : treeId,
        nodes : json,
        loadType: 'window.load',
        checkEnable: chkEnable,
        onTextClick : function(treeId) {
            if(!$('#'+txtId).val()){
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                zTree.checkAllNodes(false); // 取消所有选中状态
            }
            if (!chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', checkIds, null);
                if(node){
                    zTree.selectNode(node);
                }
            }
        },
        onCheck : function(e, treeId, node) {
            var result= getCheckedData(treeId, '');
            $('#'+txtId).val(result.names);
            $('#'+valueId).val(result.ids);
        },
        onComplete: function(treeId){
            var names=[];
            if (chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var ids = checkIds.split(',');
                var i=0;
                for (; i<ids.length; i++)
                {
                    var node = zTree.getNodeByParam('id', ids[i], null);
                    if(node){
                        zTree.selectNode(node)
                        zTree.checkNode(node);
                        names.push(node.name);
                    }
                }
                $('#'+txtId).val(names.join(','));
            }
        }
    });
}
function initDeptBranchUserTree(chkEnable,treeId,txtId,valueId,checkIds){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../deptManager/initDeptUserTree.html?flag=1',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载归属机构下拉树形选择框
    $('#'+txtId).treeselectbox({
        treeId : treeId,
        nodes : json,
        loadType: 'window.load',
        checkEnable: chkEnable,
        checkReaction: { "Y": "s", "N": "s" },
        onTextClick : function(treeId) {
            if(!$('#'+txtId).val()){
                var zTree = jQuery.fn.zTree.getZTreeObj(treeId);
                zTree.checkAllNodes(false); // 取消所有选中状态
            }
            if (!chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.getNodeByParam('id', checkIds, null);
                if(node){
                    zTree.selectNode(node);
                }
            }
        },
        onCheck: function(e, treeId, node){
            var result = getCheckedData(treeId, 'D');
            $('#'+valueId).val(result.ids);
            $('#'+txtId).val(result.names).blur();
        },
        onComplete: function(treeId){
            var names=[];
            if (chkEnable && checkIds) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var ids = checkIds.split(',');
                var i=0;
                for (; i<ids.length; i++)
                {
                    var node = zTree.getNodeByParam('id', ids[i], null);
                    if(node){
                        zTree.selectNode(node)
                        zTree.checkNode(node);
                        names.push(node.name);
                    }
                }
                $('#'+txtId).val(names.join(','));
                $('#'+valueId).val(ids);
            }
        }
    });
}

function getCheckedData(treeId, flag){
    var oTree = jQuery.fn.zTree.getZTreeObj(treeId), nodes = oTree.getCheckedNodes(true), uIds = [], uNames = [], i = 0, len = nodes.length;
    for(; i < len; i++){
        var node = nodes[i], id = node.id, name = node.name;
        if(flag == 'U' || flag == 'A' || flag == 'P' || flag == 'SL'){
            if(node.flag == flag){
                uIds.push(id.split('-')[1]);
                uNames.push(name);
            }
        }else{
            uIds.push(id);
            uNames.push(name);
        }
    }
    return { 'ids': uIds.join(','), 'names': uNames.join(',') };
}