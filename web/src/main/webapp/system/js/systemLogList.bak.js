$(function(){
    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });
    $('select').selectbox();
    // 加载系统日志数据网格
    $('#logGrid').flexigrid({
        height: 300,
        url: '../systemLog/getSystemLogList.html',
        dataType: 'json',
        fields: [
            { display: '操作时间', field: 'opeventDate', width: 150, align: 'left' },
            { display: '操作对象', field: 'opeventModule', width: 100, align: 'left' },
            { display: '操作信息', field: 'opeventAction', width: 180, align: 'left' },
            { display: '操作者账号', field: 'userAccount', width: 180, align: 'left' },
            { display: '操作者姓名', field: 'userName', width: 100, align: 'left' },
            { display: '操作IP', field: 'opeventIp', width: 120, align: 'left' }
        ],
        rowIdProperty: 'id',
        usePage: true, // 使用分页
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });

    //
    $('#btnSearch').click(function(){
        var txtBelongTo = $("#txtBelongTo").val();
        var txtBelongToArray = txtBelongTo.split(",");
        if (txtBelongToArray.length > 100) {
            showConfirm({
                icon: 'warning',
                content: '操作者选择过多，不能大于100个！'
            });
            return;
        }
        var postFields = getPostFields();
        $('#logGrid').flexSearch(postFields);
    });

    //
    $('#btnRefresh').click(function(){
        var txtBelongTo = $("#txtBelongTo").val();
        var txtBelongToArray = txtBelongTo.split(",");
        if (txtBelongToArray.length > 100) {
            showConfirm({
                icon: 'warning',
                content: '操作者选择过多，不能大于100个！'
            });
            return;
        }
        $('#logGrid').flexReload();
    });

    //
    $('#btnClean').click(function(){
        toCleanForm('#form');
        $('input[name="opAction"]').val('');
        $('input[name="opUserName"]').val('');
    });

    //
    $('#deleteLog').click(function(){
        showConfirm({
            icon: 'confirm',
            content: '您确认要清空所有日志吗',
            ok: function() {
                jQuery.ajax({
                    type: 'post',
                    url: '../systemLog/deleteAllSystemLog.html',
                    data: {},
                    success: function(text){
                        $('#logGrid').flexReload();
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                    }
                });
            },
            cancel: function() {}
        });
    });
    initDeptUserTree(true, 'belongToTree', 'txtBelongTo', 'hidBelongTo', $.trim($('#hidBelongTo').val()));
    initOpeventModuleTree(true, 'opeventModuleToTree', 'txtOpModule', 'hidOpModule', $.trim($('#hidOpModule').val()));
});
function  exportLog(){
    var opAction = $('input[name="opAction"]').val();
    var logDateEnd = $('input[name="logDateEnd"]').val();
    var logDateBegin = $('input[name="logDateBegin"]').val();
    var opUserName = $('input[name="opUserName"]').val();
    window.location="../systemLog/exportLog.html?opUserName=" + encodeURI(encodeURI(opUserName)) +
        "&opAction="+ encodeURI(encodeURI(opAction)) + "&logDateBegin="+logDateBegin+"&logDateEnd="+logDateEnd;
}

function initOpeventModuleTree(chkEnable,treeId,txtId,valueId,checkIds){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../systemLog/getOpeventModuleTree.html',
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
            var result = getCheckedData(treeId, 'SL');
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