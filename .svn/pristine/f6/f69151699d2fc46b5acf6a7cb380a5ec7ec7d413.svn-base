// 业务逻辑
$(function(){
    $('select').selectbox();
	//业务类型非空中文下划线数字输入限制验证,业务类型同名校验
	banger.verify('#txtClassName', [{name : 'required',	tips : '业务类型必须填写'}, {name:"account", tips:"“业务类型”只能由中文、字母、数字和下划线组成！",rule:/^[a-zA-Z0-9-_\u4e00-\u9fa5]+$/},updateBusinessTypeNameRepeatRule]);

	// 提交新增业务类型的数据
	$('#btnSave').click(function() {
		saveBusinessType();
	});
		
	// 关闭页卡
	$('#btnCancel').click(function() {
		var dialog = getDialog('editBusinessType');
		dialog.close();
	});
	
});

function saveBusinessType(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    postJson['typeId'] = $("#typeId").val();
    jQuery.ajax({
        type : 'post',
        url : '../product/updateBusinessType.html',
        data : postJson,
        success : function(data) {

            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
    		var dialog = getDialog('editBusinessType');
    	    var win = tabs.getIframeWindow(dialog.config.tabId);
    	    //win.refresh();
    	    win.location.reload(true);
	       // banger.menu.reload();
	        setTimeout(function(){dialog.close();},0);
	    
	        //刷新产品管理菜单 

        }
    });
}

//业务类型编辑同名校验
var updateBusinessTypeNameRepeatRule = {
	name : 'repeated',
	tips : '',
	rule : function() {
		var rule = this, bool = true, data = getPostFields();
		data['typeId'] = $("#typeId").val();
		var url = '../product/checkUpdateBusinessTypeNameIsRepeat.html';
		jQuery.ajax({
			type : 'post',
			url : url,
			async : false,
			data : data,
			success : function(result) {
				if (result) {
					result =  eval("("+result+")");
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
//function closeTab() {
//    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
//    if( win && win.refresh){
//        win.refresh();
//    }
//	tabs.close(tabs.getSelfId(window));
//}
