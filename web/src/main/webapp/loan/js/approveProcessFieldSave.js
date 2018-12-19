$(function(){
	//审批字段js
	//保存
	$('#btnSave').click(function() {
		saveApproveProcessField();
	});
	//取消
	$('#btnCancel').click(function() {
		var dialog = getDialog('approveProcessField');
		dialog.close();
	});
	

	
    $('#approveProcessFieldList').flexigrid({
        height: 198,
        url: '../approveProcessField/queryApproveProcessField.html?processId='+$("#processId").val(),
        dataType : 'json',
        //multiSelect: true,
        fields: [
            { display: '字段名称', field: 'fieldName', width: 150 ,align: 'center'},
            { display: '是否必填', field: 'fieldIsRequired', width: 150 ,align: 'center'},
            { display: 'web端显示', field: 'fieldWebIsShow', width: 120 ,align: 'center'},
            { display: 'APP显示', field: 'fieldAppIsShow', width: 117 ,align: 'center'},
        ],
        extendCell: {
    		'fieldIsRequired': function(value,row){
				 if(row.cols.fieldIsRequired){
					 _required = '<input class="fieldIsRequired" type = \'checkbox\' checked=\'checked\' />';
				 }else{
					 _required = '<input class="fieldIsRequired" type = \'checkbox\' />';
				 }
				 var id = row.id ? row.id : 0;
					_required += '<input class="fieldId" type="hidden" value="'+row.cols.fieldId+'" /><input type="hidden" class="id" value="'+id+'" />';
				 return _required;
    		},
		    'fieldWebIsShow': function(value,row){
				var _web='';
				 if(row.cols.fieldWebIsShow){
					 _web = '<input class="fieldWebIsShow" type = \'checkbox\' checked=\'checked\' ></input>';
		         }else{                   
		        	 _web = '<input class="fieldWebIsShow" type = \'checkbox\' ></input>';
		         }
				 return _web;
		    },
		    'fieldAppIsShow': function(value,row){
				var _app='';
				 if(row.cols.fieldAppIsShow){
					 _app = '<input class="fieldAppIsShow" type = \'checkbox\' checked=\'checked\' ></input>';
		         }else{
		        	 _app = '<input class="fieldAppIsShow" type = \'checkbox\' ></input>';
		         }
				 return _app;
		    }

        	},
        usePage: false
    });
    function saveApproveProcessField(){
    	var postJson = [];
    	$("#approveProcessFieldList tr").each(function () {
			var json = {};
			json.id = $(this).find('.id').val();
            json.fieldId = $(this).find('.fieldId').val();
            json.fieldIsRequired = $(this).find('.fieldIsRequired').attr("checked")?1:0;
            json.fieldWebIsShow = $(this).find('.fieldWebIsShow').attr("checked")?1:0;
            json.fieldAppIsShow = $(this).find('.fieldAppIsShow').attr("checked")?1:0;
			postJson.push(json);
        });
        postJson = JSON.stringify(postJson); 
        jQuery.ajax({
            type : 'post',
            dataType:'json',
            url : '../approveProcessField/saveApproveProcessField.html',
            data : {
				"processId" : $("#processId").val(),
				"typeId": $("#typeId").val(),
				"paramId":$("#paramId").val(),
				"json":postJson
				},
				success : function() {
					showConfirm({
						icon: 'succeed',
						content: '操作成功'
					});
					_close();
		        }
        });
    }
});
//关闭dialog
function _close(){
	var dialog = getDialog('approveProcessField');
	var win = tabs.getIframeWindow(dialog.config.tabId);
	win.location.reload(true);
	setTimeout(function() {
		dialog.close();
	}, 0);
}	
