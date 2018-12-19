$(function(){
	//渲染下拉框
    $('select').selectbox({});
    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });    
	initMaxlengthTips('#remark','#tips');
	//校验 
	banger.verify('#taskTitle',[{name: 'required', tips: '任务名称必须填写'},checkTaskTitle]);
	banger.verify('#txtStartDate',{name: 'required', tips: '任务期限开始日期必须填写'});
	banger.verify('#txtEndDate',{name: 'required', tips: '任务期限结束日期必须填写'});	
	banger.verify('#taskTarget',[{name: 'required', tips: '任务目标必须填写'},{name: 'integer', tips: '任务目标必须必须为整数'}]);
    //banger.verify('#taskMold',{name: 'required', tips: '任务类型必须填写'});
    banger.verify('#remark',{name: 'maxlength', tips: '内容过长'});

    // 提交新增数据源
	$('#btnSave').click(function() {
		saveTask('saveClose');
	});
	

	// 提交新建数据源数据并继续新建
	$('#btnContinue').click(function() {
		saveTask('saveContinue');
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var taskType = $("#taskType").val();
		var dialog = getDialog(taskType);
		// var dialog = getDialog('addTask');
		dialog.close();
	});

});

function saveTask(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    postJson['taskTitle'] = jQuery.trim($('#taskTitle').val());
    //postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
	//postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
	//var taskTypeMoney=$('#taskTypeMoney').val();
	//var taskTypeNum=$('#taskTypeNum').val();
	var taskType=1;
	if($('#taskTypeMoney').attr('checked')=='checked'){
		taskType=1;
	}else if($('#taskTypeNum').attr('checked')=='checked'){
		taskType=2;
	}
		
	postJson['taskType'] =taskType
	postJson['remark'] = jQuery.trim($('#remark').val());
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../task/insertTaskInfo.html',
        data : postJson,
        success : function(result) {
        	if(result.success){
        		showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if(opType == 'saveContinue'){
                	reset();
                }else{
                	closeDialog();
                }
        	}else{
        		showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
        	}
        }
    });
}

var checkTaskTitle = {
	name : 'checkTaskTitle',
	tips : '任务名称已存在，请重新输入',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		var url = '../task/checkTaskTitle.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			dataType:'json',
			async : false,
			data : data,
			success : function(result) {
				bool = result.success;
			}
		});
		return bool;
	}
};


//重置
function reset(){
	$("#form")[0].reset();
	$("#ntaskMold").val("贷款任务");
	$("#taskMoldShow").show();
	$("#targetUnit").html("元");
	initMaxlengthTips('#remark','#tips');
}

function showUnit(){
	if($('#taskTypeMoney').attr('checked')=='checked'){
		$('#targetUnit').html('元');
	}
	if($('#taskTypeNum').attr('checked')=='checked'){
		$('#targetUnit').html('笔');
	}
}
function taskMoldShow(taskMold) {
	if(taskMold == "0"){
		$("#taskMoldShow").show();
		if($('#taskTypeMoney').attr('checked')=='checked'){
			$('#targetUnit').html('元');
		}
		if($('#taskTypeNum').attr('checked')=='checked'){
			$('#targetUnit').html('笔');
		}
	}else if(taskMold == "1"){
		$("#taskMoldShow").hide();
		$('#targetUnit').html('个');
	}
}

//关闭dialog
function closeDialog(){
	var taskType = $("#taskType").val();
    var dialog = getDialog(taskType);
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.refreshTaskList();    
    setTimeout(function() {
        dialog.close();
    }, 0);
}