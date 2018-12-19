
$(function() {
    layout.initForms();
    getRoleUser();
});

// 保存新建并继续新建
$('#btnConfirm').click(function() {
    var userId = $('#userId').val();
    var loanId = $('#loanId').val();
    if(userId==''){
        showConfirm({
            icon: 'warning',
            content: '请选择签订对象!'
        });
        return false;
    }
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../contract/loanContractSubmit.html',
        data: {
            "userId": userId,
            "loanId":loanId
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
});

$('#btnCancel').click(function() {
    closeDia();
});

$('body').mousedown(function(event){
    if (!(event.target.id == "btnConfirm"||event.target.id == "btnCancel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
        hideMenu();
    }
});

function closeDia(){
    // 关闭弹出框
    var dialog = getDialog('contractSubmit');
    var win = tabs.getIframeWindow(dialog.config.tabId);
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
var loginUserId = $('#loginUserId').val();

//function getRoleUser(){
//    var treeObj = $('#treeSelect');
//    var groupId = $('#groupId').val();
//    jQuery.ajax({
//        type: 'post',
//        url:  '../user/getGroupSelectUser.html',
//        async:false,
//        data: {groupId:groupId},
//        success: function (data) {
//            $.fn.zTree.init(treeObj, setting, $.parseJSON(data));
//        }
//    });
//}

function getRoleUser(){
    var treeObj = $('#treeSelect');
    var groupId = $('#groupId').val();
    jQuery.ajax({
        type: 'post',
        url:  '../user/getGroupSelectSubUser.html',
        async:false,
        data: {groupId:groupId},
        success: function (data) {
            $.fn.zTree.init(treeObj, setting, $.parseJSON(data));
        }
    });
}

function showMenu() {
    var Obj = $("#selectDiv");
    var Offset = $("#selectDiv").offset();
    $("#menuContent").css({left:Offset.left + "px", top:Offset.top + Obj.outerHeight() + "px"}).slideDown("fast");
}

function hideMenu() {
    $("#menuContent").fadeOut("fast");
}

function onClick(e, treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check) return check;
    var zTree = $.fn.zTree.getZTreeObj("treeSelect"),
        nodes = zTree.getSelectedNodes();
    if(nodes.length>0){
        if(loginUserId == nodes[0].id){
            showConfirm({
                icon: 'warning',
                content: '签订人不能是自己'
            });
            return false;
        }
        $('#selectDiv').val(nodes[0].name);
        $('#userId').val(nodes[0].id);
        hideMenu();
    }
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('contractSubmit');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.closeApplyTab){
        win.closeApplyTab();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}