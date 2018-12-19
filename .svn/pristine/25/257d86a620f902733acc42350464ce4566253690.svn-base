
$(function() {
    initTree();
});


$('#btnSelectSure').click(function() {
    var ywtx = $('#ywtx').val();
    var ywtxbm = $('#ywtxbm').val();
    var prdName = $('#prdName').val();
    var subCode = $('#subCode').val();

    closeDia(ywtx,ywtxbm,prdName,subCode);
});

$('#btnCloseSelect').click(function() {
    closeDia();
});


function closeDia(ywtx,ywtxbm,prdName,subCode){
    // 关闭弹出框
    var dialog = getDialog('selectCrmPrdType');
    if (ywtx&&ywtxbm&&prdName&&subCode) {
        dialog.businessLineName = ywtx;
        dialog.businessLine = ywtxbm;
        dialog.mainProTypeName = prdName;
        dialog.mainProType = subCode;
    }
    dialog.close();
}

var setting = {
    view: {
        dblClickExpand: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: onClick
    }
};


function initTree(){
    var treeObj = $('#treeSelect');
    jQuery.ajax({
        type: 'post',
        url:  '../contract/queryCrmPrdTypeJson.html',
        async:false,
        data: {},
        success: function (data) {
            $.fn.zTree.init(treeObj, setting, $.parseJSON(data));
        }
    });
}

function onClick(e, treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check) return check;
    var zTree = $.fn.zTree.getZTreeObj("treeSelect"),
        nodes = zTree.getSelectedNodes();
    if(nodes.length>0){
        var fullname = getFullName(nodes[0]);
        $('#selectVal').val(fullname);
        $('#ywtx').val(nodes[0].ywtx);
        $('#ywtxbm').val(nodes[0].pId);
        $('#prdName').val(nodes[0].name);
        $('#subCode').val(nodes[0].id);
    }
}

//获取子节点，所有父节点的name的拼接字符串
function getFullName(treeObj){
    if(treeObj==null)return "";
    var fullname = treeObj.name;
    var pNode = treeObj.getParentNode();
    if(pNode!=null){
        fullname = getFullName(pNode) +">"+ fullname;
    }
    return fullname;
}

