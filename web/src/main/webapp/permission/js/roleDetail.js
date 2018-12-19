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
       saveRole('saveContinue');
    });
    
    $('#btnCancel').click(function() {
    	closeTab();
    });
    
});



//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
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
