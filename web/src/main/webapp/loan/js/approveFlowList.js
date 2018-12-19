// 页面加载完成时...
$(function() {
	var flowId = $('#flowId').val();
	var btnAddName = 'btnAddProcess';//添加审批环节按钮ID

	var reviewModeMap = {'RANDOM_USER':'系统随机','SELECT_USER':'提交人员指定'};
	var reviewDataPowerMap = {'TEAM':'团队','ALL':'全部'};
	var isLimitControlMap = {'0':'否','1':'是'};

	//初始化 列表
	if(conditionInfo != ''){
		for(var i=0;i<conditionInfo.length;i++){
			initGridHtml(conditionInfo[i],i);
			var paramId = conditionInfo[i].paramId;
            var paramName = conditionInfo[i].paramName;
            var paramValue = conditionInfo[i].paramValue;
            var paramNo = i+1;
            var conditionId = conditionInfo[i].conditionId;
            var addButton = $('#btnAddProcess'+i);
            addButton.data('paramId',paramId);
            addButton.data('paramName',paramName);
            addButton.data('paramValue',paramValue);
            addButton.data('paramNo',paramNo);
            addButton.data('conditionId',conditionId);
            addButton.click(function() {
                var paramId = $(this).data('paramId');
                var paramName = $(this).data('paramName');
                var paramValue = $(this).data('paramValue');
                var paramNo = $(this).data('paramNo');
                var conditionId = $(this).data('conditionId');

				var params = 'flowId='+flowId+'&conditionId='+conditionId+'&paramId='+paramId+'&paramNo='+paramNo+'&paramName='+encodeURI(encodeURI(paramName))+'&paramValue='+paramValue;
				saveApproveProcess(params, "添加");
			});
			$('#approveFlowGrid'+i).flexigrid({
		        height: 200,
		        url: '../loanApproveFlow/queryApproveProcessListData.html',
		        dataType : 'json',
				params : {
					'flowId' : flowId, 'paramId' : paramId
				},
		        fields: [
		        	{ display: '审批环节', field: 'processName', width: 100 ,align: 'center'},
		        	{ display: '审批角色', field: 'revievRoleName', width: 150 ,align: 'center'},
		        	{ display: '数据权限', field: 'reviewDataPower', width: 150 ,align: 'center',data:reviewDataPowerMap},
		        	{ display: '审批人数', field: 'reviewCount', width: 100 ,align: 'center'},
		        	{ display: '分配方式', field: 'reviewMode', width: 150 ,align: 'center',data:reviewModeMap},
		        	{ display: '额度控制', field: 'isLimitControl', width: 100 ,align: 'center',data:isLimitControlMap}
		        ],
		        action: {
		        	display: '操作',
		        	width: 210,
		        	align: 'center',
		        	buttons: [
		                {
		                    display: '审批字段',
		                    onClick: function(tr, data){
		                    	saveApproveProcessField(data);
		                	}
		                },
		            	{
		            		display: '编辑',
		            		onClick: function(tr, data){
		            			var params = 'processId='+data.id+"&processName="+encodeURI(encodeURI(data.cols.processName));
		            			saveApproveProcess(params, '编辑');
							}
		        		},
		            	{
		            		display: '删除',
		            		onClick: function(tr, data){
		            			deleteApproveProcess(data,data.cols.paramId);
		            		}
		        		}
		            ]
		        },
                extendCell: {
                    'isLimitControl': function(value, row){
                    	var key = row.cols.isLimitControl+'';
                    	var txt = isLimitControlMap[key];
                    	if(row.cols.roleCount>1){
                            txt += ' ...';
                        }
                        return txt;
                    },
                    'reviewMode': function(value, row) {
                        var key = row.cols.reviewMode+'';
                        var txt = reviewModeMap[key];
                        if(row.cols.roleCount>1){
                            txt += ' ...';
                        }
                        return txt;
                    },
                    'reviewDataPower': function(value, row) {
                        var key = row.cols.reviewDataPower+'';
                        var txt = reviewDataPowerMap[key];
                        if(row.cols.roleCount>1){
                            txt += ' ...';
                        }
                        return txt;
                    },
                    'reviewCount': function(value, row) {
                        var txt = row.cols.reviewCount+'';
                        if(row.cols.roleCount>1){
                            txt += ' ...';
                        }
                        return txt;
                    },
                    'revievRoleName': function(value, row) {
                        var txt = row.cols.revievRoleName+'';
                        if(row.cols.roleCount>1){
                            txt += ' ...';
                        }
                        return txt;
                    }
                },
                usePage: false
		    });
		}
	}
	
	$(":radio").click(function() {
		var v = $(this).val();
		var classId = $('#classId').val();
		// ajax 切换生效流程 ....
		jQuery.ajax({
			url : '../loanApproveFlow/switchApproveFlowCondition.html',
			type : 'POST',
			dataType : 'json',
			data : {
				"classId" : classId,
				"isCondition" : v
			},
			sync : false,
			success : function(result) {
				if (!result.success) {
					showConfirm({
						icon : 'warning',
						content : '操作失败'
					});
				}
				reload();
			}
		});
	});

	$("#btnSetCondition").click(function() {
		var url = '../loanApproveFlow/getApproveConditionSavePage.html?flowId='+flowId +'&random='+Math.random()*10000;
		showDialog({
			id : 'approveConditionSave',
			title : '审批条件',
			url : url,
			width : 660,
			height : 215,
			tabId : tabs.getSelfId(window)
		});
	});

});

function initGridHtml(info,index){
	var paramId = info.paramId;
	var paramName = info.paramName;
	var paramValue = info.paramValue;
    var html="</br></br>";
    html+="<div class='ui-layout-action'>";
    html+="<span class='ui-button-text fl' style='margin-left:10px;margin-right:10px' >"+paramName+"</span>";
    html+="<span id='btnAddProcess"+index+"' class='ui-button fl' ><label class='ui-button-text' >添加</label></span>"
	html+="</div>";
	html+="<table id='approveFlowGrid"+index+"' ></table>";
	$('#approveFlowDiv').append(html);
}

function saveApproveProcess(params, name) {
	var url = '../loanApproveFlow/getApproveProcessSavePage.html?' + params +'&random='+Math.random()*10000;
	showDialog({
		id : 'approveProcessSave',
		title : name + '审批环节',
		url : url,
		width : 660,
		height : 215,
		tabId : tabs.getSelfId(window)
	});
}

function deleteApproveProcess(data,paramId){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除审批环节“'+ data.cols.processName+ '”吗?',
        ok:function(){
        	jQuery.ajax({
    			url: '../loanApproveFlow/deleteApproveProcess.html',
    			type:'POST', 
    			dataType:'json',
                data: {"processId": data.id,"flowId":data.cols.flowId},
                sync: false,
                success: function(result){
                	if(result.success){
                		showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        //reloadApproveFlowGrid(paramId);
                        reload();
                	}else {
                		showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
					}
                }
    		});
        },
        cancel: function() {}
	});
}

//审批字段
function saveApproveProcessField(data){
	var url = '../approveProcessField/getSaveApproveProcessField.html?flowId='+$('#flowId').val()+'&processId='+data.id+'&typeId='+$("#classId").val();
	showDialog({
		id : 'approveProcessField',
		title : '选择审批字段',
		url : url,
		width : 660,
		height : 338,
		tabId : tabs.getSelfId(window)
	});
}

function reloadApproveFlowGrid(paramId){
	$('#approveFlowGrid'+paramId).flexReload();
}

function searchApproveFlowGrid(paramId){
	$('#approveFlowGrid'+paramId).flexSearch();
}

// 刷新页面
function reload() {
	window.location.reload(true);
}