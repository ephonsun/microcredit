
// 加载有限期
$('#endDate').datepicker({
    maxDate: '#F{$dp.$D(\'endDate\')}'
});
var endDate=$('input[name="validDate"]').val();
// 业务逻辑
$(function(){
	// 主管机构树
	var zHeadTree;
	// 加载归属机构下拉树形选择框
	$('#txtBelongTo').treeselectbox({
		treeId: 'belongToTree',
		loadType: 'window.load',
		url: '../deptManager/refreshTree.html',
        async: false,
		onComplete: function(treeId){
			var id = $.trim($('#hidBelongTo').val());
			if(id){
				var oTree = $.fn.zTree.getZTreeObj(treeId), node = oTree.getNodeByParam('id', id, null);
                oTree.selectNode(node, true);
				$('#txtBelongTo').val(node.name);
			}
		},
		onClick: function(e, treeId, node, flag) {
			$('#hidBelongTo').val(node.id);
			$('#txtBelongTo').val(node.name).blur();
            $(".ui-treeselectbox-box").hide();
		}
	});

    //初始化产品列表
    $('#roleprodManager').checkbox({
        options:cdProdClassJson,
        onCheck: function(text, value){
            $("#_roleprodManager").val(value);
        }
    });

	// 非空校验
	banger.verify('#txtName', [{name : 'required', tips : '姓名必须填写'}]);
	banger.verify('#txtBelongTo', {name : 'required', tips : '归属机构必须填写'});
	banger.verify('#txtAccount', [{name : 'required',	tips : '用户名必须填写'},specialLetter, accountRepeatRule]);
    banger.verify(".role", { name: 'checked[1,null]', tips: '必须分配一个角色' });
    initMaxlengthTips('#txtRemark','#tips');
    banger.verify('#txtRemark',{ name: 'maxlength', tips: '“人员备注”内容过长' });
	// 提交新增和修改用户的数据
	$('#btnSave').click(function() {
        saveUser('saveClose');
	});

	// 提交新建人员数据并继续新建
	$('#btnContinue').click(function() {
		saveUser('saveContinue');
	});

	// 关闭页卡
	$('#btnCancel').click(function() {
		closeTab();
	});
    initRoleDept();
});


function saveUser(opType){
    if(isShowAddMune=="false"){
        showConfirm({
            icon: 'warning',
            content: "该用户角色没有新建用户操作权限！"
        });
        return false;
    }
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    //获取角色ID
    var roleIds = acquireRoleId();

    if (roleIds == "") {
        showConfirm({
            icon: 'warning',
            content: "请选择用户角色！"
        });
        return false;
    }

    postJson['roleIds'] = roleIds;
    //获取部门ID
    var roleDeptIds = acquireRoleDeptId();
   //获得人员状态
    var start = $("#start").val();
    postJson['start'] = start;
    var stop = $("#stop").val();
    postJson['stop'] = stop;
//    postJson['roleDeptIds'] = roleDeptIds;
    //获取产品ID
    var userProductClassIds =$('#_roleprodManager').val();

    var hasRole4 = false;
    var roleIdsArray = roleIds.split(",");
    for(var i=0;i<roleIdsArray.length;i++){
        var roleId = roleIdsArray[i];
        if (roleId == "4") {
            hasRole4 = true;
        }
    }
    if(hasRole4 && userProductClassIds==""){
        showConfirm({
            content: "请选择产品类型"
        })
        return false;
    }
    if (!hasRole4) {
        //如果没勾选角色4，情况产品类型权限
        userProductClassIds = "";
    }
    postJson['userProductClassIds'] = userProductClassIds;
    //获取人员备注
    var txtRemark = $('#txtRemark').val();
    jQuery.ajax({
        type : 'post',
        url : '../user/updatePmsUser.html',
        data : postJson,

        success : function(json) {
        	var result = jQuery.parseJSON(json);
        	if(result.success){
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
        	}else{
        		showConfirm({
                    icon: 'warning',
                    content: result.cause,
                    ok: function(){}
                });
        	}
            
        }
    });
}
//获取用户角色Id
function acquireRoleId(){
	//var chks = $('.roles').find('input[type=checkbox]');
	var chks = $('.roles').find('input[class="ui-check role"]');
	var str = "";
	chks.each(function(){
		if($(this).is(':checked')){
			if(str){
				str = str +","+ $(this).val();
			}else{
				str=$(this).val();
			}
		}
	});
	return str;
}
//获取用户机构Id
function acquireDeptId(){
    var chks = $('.ui-text-text').find('input[type=checkbox]');
    var str = "";
    chks.each(function(){
        if($(this).is(':checked')){
            if(str){
                str = str +","+ $(this).val();
            }else{
                str=$(this).val();
            }
        }
    });
    return str;
}
//用户名同名校验
var accountRepeatRule = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../user/checkUserAccountIsRepeat.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
				if (result=="noExist") {
                    bool = true;
				} else{
                    result = eval("("+result+")");
                    rule.tips = result.value;
                    bool = result.success;
                }
			}
		});
		return bool;
	}
};

var specialLetter = {
    name : 'specialLetter',
    tips : '输入含有特殊字符，请重新输入',
    rule : function() {
        var userAccount = $('#txtAccount').val();
        var regex = new RegExp("[@/'\"#$%&;?!！《》】【:;'“”^*]+");
        if(regex.test(userAccount))
        {
            return false;
        }else{
            return true;
        }
    }
}

//用户名长度限制
var accountLenthLimit = {
	name : 'lengthLimit',
	tips : '用户名必须为7位员工号',
	rule : function() {
		var account = $('#txtAccount').val();
		if(account.length!=7){
			return false;
		}else{
			return true;
		}
	}	
}

// 用户编码相同校验
var codeRepeatRule = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../user/checkUserCodeIsRepeat.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
                if (result) {
                    result = eval("("+result+")");
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

// 回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
}

//初始化角色对应的机构
function checkRoleDept(roleId,flag,checkIds,roleType){
    if((roleType==2)||(roleType==3)){
        initDeptTree(true,'deptTree_'+roleId,'brDeptText_'+roleId,'hidRoleDepts_'+roleId,checkIds);
    }else if(roleType==4){
        if(pdtClassIds && pdtClassIds!=''){
            $("#_roleprodManager").val(pdtClassIds);
        }
        //初始化角色产品列表
        $('#roleprodManager').checkbox({
            options:cdProdClassJson,
            onCheck: function(text, value){
                $("#_roleprodManager").val(value);
            }
        });
        $('#prodManager').show();
     }else if(roleType==5){
          //banger.disabledVerify("#brDeptText_"+roleId);
           $('#roletype45').hide();
        }
    if(flag==true){
         if((roleType==2)||(roleType==3)){
             banger.enableVerify("#brDeptText_"+roleId);
             banger.verify("#brDeptText_"+roleId, {name : 'required', tips : '“负责机构”必须填写'});
             $('#brDept_'+roleId).show();
         }else if(roleType == 4){
             banger.enableVerify("#prodClass");
             banger.verify("#prodClass", {name : 'required', tips : '“负责机构”必须填写'});
            $('#prodManager').show();
         }

    }else{
        //$('#hidRoleDepts_'+roleId).val("");
        $('#brDept_'+roleId).hide();
        $('#roletype45').hide();
        $('#prodManager').hide();
        banger.disabledVerify("#brDeptText_"+roleId);
    }
}
//选择或取消角色复选框
function checkRoleDeptClone(roleId,flag,checkIds,roleType){
    if((roleType==2)||(roleType==3)){
        initDeptTree(true,'deptTree_'+roleId,'brDeptText_'+roleId,'hidRoleDepts_'+roleId,checkIds);
    }else if(roleType==4){
        if(pdtClassIds && pdtClassIds!=''){
            $("#_roleprodManager").val(pdtClassIds);
        }
        //初始化角色产品列表
        $('#roleprodManager').checkbox({
            options:cdProdClassJson,
            onCheck: function(text, value){
                $("#_roleprodManager").val(value);
            }
        });
        $('#prodManager').show();
     }else if(roleType==5){
          //banger.disabledVerify("#brDeptText_"+roleId);
           $('#roletype45').hide();
        }
    if(flag==true){
         if((roleType==2)||(roleType==3)){
             banger.enableVerify("#brDeptText_"+roleId);
             banger.verify("#brDeptText_"+roleId, {name : 'required', tips : '“负责机构”必须填写'});
             $('#brDept_'+roleId).show();
         }else if(roleType == 4){
             banger.enableVerify("#prodClass");
             banger.verify("#prodClass", {name : 'required', tips : '“负责机构”必须填写'});
            $('#prodManager').show();
         }

    }else{
        //$('#hidRoleDepts_'+roleId).val("");
        if((roleType==2)||(roleType==3)){
            $('#brDept_'+roleId).hide();
        } else if(roleType == 4){
            $('#prodManager').hide();
        }else if(roleType == 5){
            $('#roletype45').hide();
        }
        banger.disabledVerify("#brDeptText_"+roleId);
    }
}
//获取用户角色对应的机构Id
function acquireRoleDeptId(){
    var chks = $('.roles').find('input[type=checkbox]');
    var str = "";
    chks.each(function(){
        if($(this).is(':checked')){
            var rId = $(this).val();
            if($('#hidRoleDepts_'+rId).val()){
                if(str){
                    str = str +";"+ rId+"_"+$('#hidRoleDepts_'+rId).val();
                }else{
                    str=rId+"_"+$('#hidRoleDepts_'+rId).val();
                }
            }
        }
    });
    return str;
}

function initRoleDept(){
    var chks = $('.roles').find('input[type=checkbox]');
    var str = "";
    chks.each(function(){
        if($(this).is(':checked')){
            var rId = $(this).val();
            checkRoleDept(rId,true,$(this).attr('deptIds'),$(this).attr('roleType'))
        }
    });
}
