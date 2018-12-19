var check = true;

$(function(){

	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('assignTask');
	    var win = tabs.getIframeWindow(dialog.config.tabId);
	    dialog.close();
	});
	$('#btnSave').click(function() {
        setTimeout(saveGroupTaskAssign(),500);
	});
	
});

function saveGroupTaskAssign(){

    if (!check){
        return;
    }

    var postJson = getPostFields();
    var total=0;
    var taskTarget=parseInt($('#taskTarget').val(),10);
    $("input[name=groupAssignTarget]").each(function(){
    	if($(this).val()==''){
    		showConfirm({
                icon: 'succeed',
                content: '任务目标不能为空'
            });
    		return;
    	}
    	var num=parseInt($(this).val(),10);
		total=total+num;
    });
	//如果超出了目标，需要提示
	if(total>taskTarget){
		showConfirm({
            icon: 'warning',
            content: '分配数量超过任务总数，请确认'
        });
		return;
	}
	var teamGroupIdArr=[];
	var groupTaskAssignIdArr=[];
	var groupAssignTargetArr=[];
	$("input[name=teamGroupId]").each(function(){
		teamGroupIdArr.push($(this).val());
    });
	$("input[name=groupTaskAssignId]").each(function(){
		groupTaskAssignIdArr.push($(this).val());
    });
	$("input[name=groupAssignTarget]").each(function(){
		groupAssignTargetArr.push($(this).val());
    });
	
	postJson["teamGroupIdArr"]= teamGroupIdArr.join(",");
	postJson["groupTaskAssignIdArr"]= groupTaskAssignIdArr.join(",");
	postJson["groupAssignTargetArr"]= groupAssignTargetArr.join(",");
	
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../groupTask/saveGroupTaskAssign.html',
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
                    content: result.cause
                });
        	}
        }
    });
}

function assignNumChange(e,oldValue,newValue,teamAssign){
    if(oldValue===newValue){
        return;
    }
    if(teamAssign>0 && newValue<teamAssign){
        check = false;
        showConfirm({
            icon: 'warning',
            content: '该团队已经分配贷款任务，再次编辑不能小于'+teamAssign
        });
        return;
    }
	if(newValue==''){
        newValue = 0;
        $(e).val(0);
        // showConfirm({
        //    icon: 'warning',
        //    content: '任务目标不能为空'
        // });
        // return;
	}
	//输入校验，只能输入数字
	var reg =  /^[0-9]*$/;
    if(reg.test(newValue) == false){
        check = false;
    	showConfirm({
            icon: 'warning',
            content: '任务目标只能输入数字'
        }); 
        return;
    }

    //循环输入框中的值，计算剩余
    var total=0;
    var taskTarget=parseInt($('#taskTarget').val(),10);
    $("input[name=groupAssignTarget]").each(function(){
        var num=parseInt($(this).val(),10);
        total=total+num;
    });
    var left=taskTarget-total;
    $('#unSingedNum').html(left);
	
	if(oldValue==newValue){
        check = true;
        return;
	}



	//如果超出了目标，需要提示
	if(total>taskTarget){
        check = false;
		showConfirm({
            icon: 'warning',
            content: '分配数量超过任务总数，请确认'
        });
		return;
	}
	

	/*if(left==0){
		showConfirm({
            icon: 'warning',
            content: '剩余数量为0，请保存'
        });
	}*/
    check = true;
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('assignTask');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshGroupList();    
    setTimeout(function() {
        dialog.close();
    }, 0);
}