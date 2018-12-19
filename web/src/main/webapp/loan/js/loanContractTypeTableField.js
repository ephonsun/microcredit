$(function(){
	$('#btnSave').click(function() {
		saveLoanTypeTableField();
	});
    $('#loanTypeTableFieldList').flexigrid({
        height: 300,
        url: '../LoanTypeTableField/queryLoanTypeTableFieldList.html?tableId='+$("#tableId").val()+"&typeId="+$("#typeId").val()+"&precType="+"contract",
        dataType : 'json',
        //multiSelect: true,
        fields: [
            { display: '字段名称', field: 'fieldColumnDisplay', width: 300 ,align: 'center'},
            { display: '字段类型', field: 'fieldType', width: 200 ,align: 'center',data:cdFiledType},
            { display: '是否必填', field: 'fieldIsRequired', width: 100 ,align: 'center'},
            { display: '审批条件', field: 'fieldIsCondition', width: 100 ,align: 'center'},
            { display: 'web端显示', field: 'fieldWebIsShow', width: 100 ,align: 'center'},
            { display: 'APP显示', field: 'fieldAppIsShow', width: 100 ,align: 'center'},
        ],
        extendCell: {
    		'fieldIsRequired': function(value,row){
    			var _required = '';
    		 if(row.cols.fieldIsRequired){
    		 	//判断是否是必填
    		 	if(row.cols.isFixed == 1){
                    _required = '<input class="fieldIsRequired" type = \'checkbox\' checked=\'checked\' disabled />'+'<input class="fieldId" type="hidden" value='+row.cols.fieldId+' />';
                }else if(row.cols.fieldIsCondition){
                    _required = '<input class="fieldIsRequired" type = \'checkbox\' checked=\'checked\' disabled />'+'<input class="fieldId" type="hidden" value='+row.cols.fieldId+' />';
                 }else {
                    _required = '<input class="fieldIsRequired" type = \'checkbox\' checked=\'checked\'  />'+'<input class="fieldId" type="hidden" value='+row.cols.fieldId+' />';
                }
             }else{
                 //判断是否是必填
                 if(row.cols.isFixed == 1) {
                     _required = '<input class="fieldIsRequired" type = \'checkbox\' disabled />' + '<input type="hidden" class="fieldId" value=' + row.cols.fieldId + ' />';
                 }else{
                     _required = '<input class="fieldIsRequired" type = \'checkbox\'  />' + '<input type="hidden" class="fieldId" value=' + row.cols.fieldId + ' />';
                 }
             }
    		 return _required;
    		},
    		'fieldIsCondition': function(value,row){
    			var _condition='';
    			if(row.cols.fieldType == "Number" || row.cols.fieldType == "Select" || row.cols.fieldType == "Decimal" || row.cols.fieldType == "Float"){
    				if(row.cols.fieldIsCondition){
                        // if(row.cols.isFixed == 1) {
                        //     _condition = '<input class="fieldIsCondition"  type = \'checkbox\' checked=\'checked\' disabled />'
                        // }else{
                            _condition = '<input class="fieldIsCondition"  type = \'checkbox\' checked=\'checked\'  />'
                        // }
    				}else{
                        // if(row.cols.isFixed == 1) {
                        //     _condition = '<input class="fieldIsCondition"  type = \'checkbox\' disabled />'
                        // }else{
                            _condition = '<input class="fieldIsCondition"  type = \'checkbox\'  />'
                        // }
    				}
    			}else{
    				_condition = '<input class="fieldIsCondition" type = \'checkbox\' style="display:none"  />';
    			}
    			if(!($("#precTypeName").val() == "调查表")){
    			    _condition='<input class="fieldIsCondition" type = \'checkbox\' style="display:none"  />';
    			}
    			return _condition;
    		},
		    'fieldWebIsShow': function(value,row){
				var _web='';
				 if(row.cols.fieldWebIsShow){
                     if(row.cols.isFixed == 1) {
                         _web = '<input class="fieldWebIsShow" type = \'checkbox\' checked=\'checked\' disabled />'+'<input class="refIds" type="hidden" value="'+row.cols.refId+'" />';
                     }else if(row.cols.fieldIsRequired){
                         _web = '<input class="fieldWebIsShow" type = \'checkbox\' checked=\'checked\' disabled />'+'<input class="refIds" type="hidden" value="'+row.cols.refId+'" />';
                     }else{
                         _web = '<input class="fieldWebIsShow" type = \'checkbox\' checked=\'checked\'  />'+'<input class="refIds" type="hidden" value="'+row.cols.refId+'" />';
                     }
		         }else{
                     if(row.cols.isFixed == 1) {
                         _web = '<input class="fieldWebIsShow" type = \'checkbox\' disabled />'+'<input class="refIds" type="hidden" value="'+row.cols.refId+'" />';
                     }else{
                         _web = '<input class="fieldWebIsShow" type = \'checkbox\'  />'+'<input class="refIds" type="hidden" value="'+row.cols.refId+'" />';
                     }
		         }
				 return _web;
		    },
		    'fieldAppIsShow': function(value,row){
				var _app='';
				 if(row.cols.fieldAppIsShow){
                     if(row.cols.isFixed == 1) {
                         _app = '<input class="fieldAppIsShow" type = \'checkbox\' checked=\'checked\' disabled />';
                     }else if(row.cols.fieldIsRequired){
                         _app = '<input class="fieldAppIsShow" type = \'checkbox\' checked=\'checked\' disabled />';
                     }else{
                         _app = '<input class="fieldAppIsShow" type = \'checkbox\' checked=\'checked\'  />';
                     }
		         }else{
                     if(row.cols.isFixed == 1) {
                         _app = '<input class="fieldAppIsShow" type = \'checkbox\' disabled />';
                     }else{
                         _app = '<input class="fieldAppIsShow" type = \'checkbox\'  />';

                     }
		         }
				 return _app;
		    }
		    		
        	},
        usePage: false
});
    //联动 首先判断是否事调查表如果是调查表以审批按钮为主 当点击必选的时候同时勾选web端和app端的选项
    // if(!($("#precTypeName").val() == "调查表")){
    //     $(".fieldIsRequired").live('click',function () {
    //         if($(this).attr('checked')){
    //             $(this).parent().parent().next().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled","disabled");
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("disabled","disabled");
    //         }else{
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled",false);
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("disabled",false);
    //         }
    //     })
    // }else{
    //     $(".fieldIsCondition").live('click',function () {
    //         if($(this).attr('checked')){
    //             $(this).parent().parent().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().children().children(':first').attr("disabled","disabled");
    //             $(this).parent().parent().next().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled","disabled");
    //             $(this).parent().parent().prev().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().prev().children().children(':first').attr("disabled","disabled");
    //         }else{
    //             $(this).parent().parent().next().children().children(':first').attr("disabled",false);
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled",false);
    //             $(this).parent().parent().prev().children().children(':first').attr("disabled",false);
    //         }
    //     })

    //     $(".fieldIsRequired").live('click',function () {
    //         if($(this).attr('checked')){
    //             $(this).parent().parent().next().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled","disabled");
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("checked","checked");
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("disabled","disabled");
    //         }else{
    //             $(this).parent().parent().next().next().children().children(':first').attr("disabled",false);
    //             $(this).parent().parent().next().next().next().children().children(':first').attr("disabled",false);
    //         }
    //     })
    // }



    function saveLoanTypeTableField(){
        debugger;
    	var postJson = {};
        var fieldIsRequired = [];
        var fieldIsCondition = [];
        var fieldWebIsShow = [];
        var fieldAppIsShow = [];
        var fieldIds = [];
        var refids = [];
        $(".fieldIsRequired").each(function(){
        	fieldIsRequired.push($(this).attr("checked")?1:0);
        	var ids = $(this).next().val();
        	fieldIds.push(ids);
        });
        $(".fieldIsCondition").each(function(){
        	if($(this).attr("style")){
        		fieldIsCondition.push(2);
        	}else{
        	fieldIsCondition.push($(this).attr("checked")?1:0);
        	}
        });
        $(".fieldWebIsShow").each(function(){
        	fieldWebIsShow.push($(this).attr("checked")?1:0);
            refids.push($(this).next().val());
        });
        $(".fieldAppIsShow").each(function(){
            fieldAppIsShow.push($(this).attr("checked")?1:0);
        });
			postJson["fieldIsRequired"]= fieldIsRequired.join(",");
        postJson["fieldIsCondition"]= fieldIsCondition.join(",");
        postJson["fieldWebIsShow"]= fieldWebIsShow.join(",");
        postJson["fieldAppIsShow"]= fieldAppIsShow.join(",");
        postJson["fieldIds"]= fieldIds.join(",");
        postJson["refids"]= refids.join(",");

        var postJsonStr = JSON.stringify(postJson);
        jQuery.ajax({
            type : 'post',
            url : '../LoanTypeTableField/saveOrUpdateLoanTypeTableField.html',
            data : {
            	"tableId" : $("#tableId").val(),
				"precType" : encodeURI($("#precType").val()),
				"typeId": $("#typeId").val(),
				"json":postJsonStr
				},
			success : function(result) {
				showConfirm({
					icon: 'succeed',
					content: '操作成功'
				});
                $('#loanTypeTableFieldList').flexReload();
	        }
        });
    }
});
//刷新
function reloadloanTypeTableFieldListGrid(){
	$('#loanTypeTableFieldList').flexReload();
}
