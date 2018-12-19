$(function() {
    //渲染下拉框
    //$('from select').selectbox({});

    var co = $("#coDiv");

    if (co) {
        //初始化控件
        layout.initForms(co);
        initVerify(co);

        co.find('.head-reduce-icon').click(function () {
            var tableId = $(this).attr("table_id");
            var p = $(this).parent();
            var table = p.parent().find('#'+tableId);
            removeVerify(table);
            table.remove();
            p.remove();
        });
    }

	// 提交新增数据源
	$('#btnSave').click(function() {
		saveAppoveProcess();
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('approveProcessSave');
		dialog.close();
	});

    $('.head-add-icon').click(function() {
        var clone = $("#cloneDiv").clone();
        clone.attr("id","");
        co.append(clone);
        clone.show();
        layout.initForms(clone);
        initVerify(clone);
        
        clone.find('.head-reduce-icon').click(function () {
            removeVerify(clone);
            clone.remove();
        })
    });

});

//审批角色重复校验
function roleNoRepeat() {
    var co = $("#coDiv");
    var ids = [];
    co.find(".ui-form-fields").each(function () {
        var roleElem = $(this).find("#revievRoleId");
        if(roleElem && roleElem.val()){
            ids.push(roleElem.val());
        }
    });

    var roleObj = {};
    for(var i=0;i<ids.length;i++){
        if(roleObj[ids[i]]){
            return false;
        }else{
            roleObj[ids[i]]=true;
        }
    }
    return true;
}

addVerifyRules([{name:'flowRole',tips: '审批角色不能重复',rule:roleNoRepeat}]);

function initVerify(co){

    //校验
    // banger.verify(co.find('#revievRoleId'), [{name: 'required', tips: '请选择审批角色'},{name: 'flowRole', tips: '审批角色不能重复'}]);
    banger.verify(co.find('#reviewDataPower'), {name: 'required', tips: '请选择数据权限'});
    banger.verify(co.find('#reviewCount'), [{name: 'required', tips: '审批人数必须填写'}, {
        name: 'posiInteger',
        tips: '审批人数必须为非零正整数'
    }]);
    banger.verify(co.find('#reviewMode'), {name: 'required', tips: '请选择分配方式'});

}

function removeVerify(co) {
    // banger.shieldedVerify(co.find('#revievRoleId'));
    banger.shieldedVerify(co.find('#reviewDataPower'));
    banger.shieldedVerify(co.find('#reviewCount'));
    banger.shieldedVerify(co.find('#reviewMode'));
}

function saveAppoveProcess(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }

    //校验审批人
    var co = $("#coDiv");
    var ids = [];
    co.find(".ui-form-fields").each(function () {
        var roleElem = $(this).find("#revievRoleId");
        if(roleElem && roleElem.val()){
            ids.push(roleElem.val());
        }
    });
    var roleObj = {};
    for(var i=0;i<ids.length;i++){
        if(roleObj[ids[i]]){
            showConfirm({
                icon: 'warning',
                content: '审批角色不能重复选择'
            });
            return;
        }else{
            roleObj[ids[i]]=true
        }
    }


    var flowId = $("#flowId").val();
    var paramId = $("#paramId").val();
    var orderNo = $("#orderNo").val();
    var processId = $("#processId").val();
    var conditionId = $("#conditionId").val();
    var paramName = $("#paramName").val();
    var paramValue = $("#paramValue").val();
    var paramNo = $("#paramNo").val();

    var postJsonArray = [];
    var co = $("#coDiv");
    co.find(".ui-form-fields").each(function () {
        postField = findPostFields($(this));
        postJsonArray.push(postField);
    });

    var postJson = {"flowId":flowId,"paramId":paramId,"paramNo":paramNo,"paramName":paramName,"paramValue":paramValue,"conditionId":conditionId,"orderNo":orderNo,"processId":processId,"postJsonString":JSON.stringify(postJsonArray)};

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loanApproveFlow/saveApproveProcess.html',
        data : postJson,
        success : function(result) {
        	if(result.success){
        		showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
        	}else{
        		showConfirm({
                    icon: 'warning',
                    content: '操作失败'
                });
        	}
        }
    });
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('approveProcessSave');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

//名字校验
// var checkRoleRule = {
//     name: 'checkRoleRule',
//     tips: '审批角色不能重复',
//     rule: function () {
//         var co = $("#coDiv");
//         var ids = [];
//         co.find(".ui-form-fields").each(function () {
//             var roleElem = $(this).find("#revievRoleId");
//             if(roleElem && roleElem.val()){
//                 ids.push(roleElem.val());
//             }
//         });
//
//         // var roleObj = {};
//         // for(var i=0;i<ids.length;i++){
//         //     if(roleObj[ids[i]]){8
//         //         return false;
//         //     }else{
//         //         roleObj[ids[i]]=true;
//         //     }
//         // }
//         if(ids.length > 0){
//             return false;
//         }
//         return true;
//     }
// };
