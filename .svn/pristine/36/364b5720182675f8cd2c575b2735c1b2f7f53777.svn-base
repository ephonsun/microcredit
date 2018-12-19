
$(function() {
    initTree();
});


$('#btnSelectSure').click(function() {
    var subCode = $('#subCode').val();
    var subName = $('#subName').val();
    var subOrder = $('#subOrder').val();
    var cmiCode = $('#cmiCode').val();
    var cmiName = $('#cmiName').val();
    var prdName = $('#prdName').val();
    var prdPk = $('#prdPk').val();
    var parentCode = $('#parentCode').val();
    closeDia(subCode,subName,subOrder,cmiCode,cmiName,prdName,prdPk,parentCode);
});

$('#btnCloseSelect').click(function() {
    closeDia();
});


function closeDia(subCode,subName,subOrder,cmiCode,cmiName,prdName,prdPk,parentCode){
    // 关闭弹出框
    var dialog = getDialog('selectBizAndPrdType');
    if (subCode&&subName&&subOrder&&prdName&&prdPk,parentCode) {
        dialog.bizType = subCode;
        dialog.bizTypeName = subName;
        dialog.prdPk = prdPk;
        dialog.bizTypeDetail = subOrder;
        dialog.accountClass = cmiCode;
        dialog.accountClassName = cmiName;
        dialog.prdName = prdName;
        //取二级
        var str = subOrder.split(">")
        dialog.prdUserdfName = str[1];
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
        url:  '../contract/getBizAndPrdTypeJson.html',
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
        var str = fullname.split("--");
        var subName =nodes[0].name;
        var names = subName.split("--");
        $('#selectVal').val(fullname);
        $('#subCode').val(nodes[0].id);
        $('#subName').val(names[0]);
        $('#parentCode').val(nodes[0].pId);
        $('#subOrder').val(str[0].replace("业务品种分类>",""));
        $('#cmiCode').val(nodes[0].cmiCode);
        $('#cmiName').val(nodes[0].cmiName);
        $('#prdName').val(nodes[0].prdNam);
        $('#prdPk').val(nodes[0].prdPk);
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

