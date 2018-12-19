$(function(){
	var Select = 'Select';
	var Decimal = 'Decimal';
	var Number = 'Number';
	var Float = 'Float';
	var numHeadHtml = "<td class='field' width='5%'><label>0&lt; </label></td>"; 
	var numEndHtml = "<td class='field' width='5%'><label> &lt;∞</label></td>";
	var numTextHtml1 = "<td width='20%'><div class='ui-text'><input type='text' maxlength='20' class='ui-text-text num-text' name='num' id='"
	var numTextHtml2 = "'/></div></td>";
	var numConnectHtml = "<td class='field' width='5%'><label> &lt;= </label></td>";
	var numNullHtml = "<td></td>";
	
	//渲染下拉框
    $('select').selectbox({});
	//初始化控件
    layout.initForms();
    
    $('#fieldId').change(function(){
    	var s = $(this).val();
    	var type = $('#'+s).val();
    	if(type.toLowerCase() == Select.toLowerCase()){
    		$('#count').html("");
    		$('#num').html("");
    		$('#nullDiv').show();
    	}else if(type.toLowerCase() == Decimal.toLowerCase() || type.toLowerCase() == Number.toLowerCase() || type.toLowerCase() == Float.toLowerCase()){
    		$('#nullDiv').hide();
    		$('#count').html("<td class='field' width='5%' colspan='1'><label class='ui-star'>数值分段：</label></td><td width='30%' colspan='3'><select id='countSelect' name='count'><option value='2' selected='selected'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select></td><td colspan='5'></td>");
    		$('select').selectbox({});
    	    $('#countSelect').change(function(){
    	    	createNumHtml($(this).val());
    	    	banger.verify('.num-text',[{name: 'required', tips: '不能为空'},{name:'posiNumber',tips:'必须为正数'},checkSortRule]);
    	    	$('.num-text').blur(function () {
                    removeOtherFailedClass();
                })
    	    });
    	    $("#countSelect").trigger("change"); //手动触发selectd的change事件 
    		//createNumHtml($('#countSelect').val());
    		//banger.verify('.num-text',[{name: 'required', tips: '不能为空'},{name:'posiNumber',tips:'必须为正数'},checkSortRule]);
    	}else{
    		return false;
    	}
    });
    
    //创建分段输入值
    function createNumHtml(c){
    	var html = numHeadHtml;
    	for(var i= 1;i<c;i++){
    		html += (numTextHtml1+i+numTextHtml2);
    		if(i+1 == c){
    			html += numEndHtml;
    		}else{
    			html += numConnectHtml;
    		}
    	}
    	var s = (5-c)*2;
    	for(var j=0;j<s;j++){
    		html += numNullHtml;
    	}
    	$('#num').html(html);
    }
    
	//校验 
	banger.verify('#fieldId',{name: 'required', tips: '请选择审批条件'});

	// 提交新增数据源
	$('#btnSave').click(function() {
		saveApproveCondition();
	});
	
	//关闭
	$('#btnCancel').click(function() {
		var dialog = getDialog('approveConditionSave');
		dialog.close();F
	});

});

//值必须从左到右递增
var checkSortRule = {
	name : 'checkSortRule',
	tips : '值必须从左到右递增,且不能相同',
	rule : function(obj) {
		var rule = this, bool = true;
		var id = obj.attr('id');
		if(parseInt(id)!=1){
			var t = $('#'+id).val();
			var u = $('#'+(id-1)).val();
			if(t && u){
				if(parseFloat(t)<=parseFloat(u)){
					bool = false;
				}
            }
		}
		return bool;
	}
}

function removeOtherFailedClass(){
	$('.num-text').each(function () {
		var elem = $(this);
        var flag = checkSortRule.rule(elem);
		if(flag){
			var title = elem.parent('div').attr("title");
			if(title==checkSortRule.tips){
                elem.parent('div').attr('title','');
                elem.parent('div').removeClass('ui-text-failed');
			}
		}else{
            var title = elem.parent('div').attr('title');
			if(title==null){
				elem.parent('div').attr('title',checkSortRule.tips);
				elem.parent('div').addClass('ui-text-failed');
            }
		}
    });
}

function saveApproveCondition(){
	if (!banger.verify('.ui-form-fields')) {
        return false;
    }
	var postJson = getPostFields();
	var nums = [];
	$('input[name=num]').each(function(){
		nums.push($(this).val());
    });
    postJson["nums"]= nums.join(",");
	jQuery.ajax({
		url : '../loanApproveFlow/saveApproveCondition.html',
		type : 'POST',
		dataType : 'json',
		data : postJson,
		sync : false,
		success : function(result) {
			if (result.success) {
				showConfirm({
					icon: 'succeed',
					content: result.cause
				});
				closeDialog();
			}else{
				showConfirm({
					icon : 'warning',
					content : '操作失败'
				});
			}
		}
	});
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('approveConditionSave');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

