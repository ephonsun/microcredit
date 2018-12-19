
$(function() {
    initTree();
});

// 保存新建并继续新建
$('#btnSelectSure').click(function() {
    var orientationName = $('#orientationName').val();
    var orientationId = $('#orientationId').val();
    closeDia(orientationName,orientationId);
});

$('#btnCloseSelect').click(function() {
    closeDia();
});


function closeDia(orientationName,orientationId){
    // 关闭弹出框
    var dialog = getDialog('selectLoanOrientation');
    if(orientationName && orientationId){
        dialog.orientationName=orientationName;
        dialog.orientationId=orientationId;
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
        url:  '../contract/getLoanOrientationJson.html',
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
        $('#orientationId').val(nodes[0].id);
        $('#orientationName').val(fullname);
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

