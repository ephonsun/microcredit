// 树构造函数
function RoleTree(nodes){
    this.config = {
        check: {
            enable: true
        },
        view: {
            showIcon: false,
            expandSpeed: ''
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    this.nodes = nodes;
}
var ulMenu = new RoleTree(menuzNodes), // 菜单权限
	ulAction = new RoleTree(funczNodes); // 操作权限

// 角色名同名校验
var roleRepeatRule = {
    name : 'repeated',
    tips : '',
    rule : function() {
        var rule = this, bool = true, data = getPostFields();
        var url = '../role/checkRoleIsRepeat.html';
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

$(function(){
    // 选项卡
    $('#tabs').tabs({
        defaultShowPage: 0
    });
    
    // 加载菜单权限
    jQuery.fn.zTree.init($('#ulMenu'), ulMenu.config, ulMenu.nodes);

    // 加载操作权限
    jQuery.fn.zTree.init($('#ulAction'), ulAction.config, ulAction.nodes);

    
    initMaxlengthTips('#roleRemark','#tips');
   // banger.verify('#roleRemark',{ name: 'maxlength', tips: '“角色描述”内容过长' });
    
    // 非空校验
    banger.verify('#roleName', [{name : 'required',	tips : '“角色名称”必须填写'},roleRepeatRule]);
    
    //保存
    $('#btnSave').click(function() {
        var content = $("#roleRemark").val();
        if(content.length>200){
            showConfirm({
                content: '字数不能大于200'
            });
            return false;
        }
        saveRole('saveClose');
    });

    $('#btnContinue').click(function(){
        var content = $("#roleRemark").val();
        if(content.length>200){
            showConfirm({
                content: '字数不能大于200'
            });
            return false;
        }
       saveRole('saveContinue');
    });
    
    $('#btnCancel').click(function() {
    	closeTab();
    });
    
});


function saveRole(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var menuTree = jQuery.fn.zTree.getZTreeObj('ulMenu'), menuNodes = menuTree.getCheckedNodes(true), menuIds = [];
    var funcTree = jQuery.fn.zTree.getZTreeObj('ulAction'), funcNodes = funcTree.getCheckedNodes(true), funcIds = [];
    if(menuNodes==""){
        showConfirm('请选择相应的菜单权限');
    }else{
        var j = 0,f=0;
        for(var i = 0; i <menuNodes.length; i++){
            menuIds[j++] = menuNodes[i].id;
        }
        for(var n = 0; n <funcNodes.length; n++){
            funcIds[f++] = funcNodes[n].id;
        }
        var mIds = menuIds.toString();
        var fIds = funcIds.toString();
        var dIds = getCheckedDataPermits();
        var postJson = getPostFields();
        postJson["menuIds"] = mIds;
        postJson["funcIds"] = fIds;
        postJson["permitIds"] = dIds;
        jQuery.ajax({
            type : 'post',
            url : '../role/addOrUpdateRole.html',
            data : postJson,
            success : function(html) {
                showConfirm({
                    icon: 'succeed',
                    content: '操作成功'
                });

                if(opType == 'saveContinue'){
                    tabs.refresh(tabs.getSelfId(window));
                }else{
                    closeTab();

                }
            }
        });
    }
}
//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }


    win.location.reload(true);
	tabs.close(tabs.getSelfId(window));
}


function getCheckedDataPermits(){
    var checkIds = [],i = 0;
    $('.dataPermit').each(function(){
        if(this.checked){
            checkIds[i++] = this.id;
        }
    });
    return checkIds.toString();
}
