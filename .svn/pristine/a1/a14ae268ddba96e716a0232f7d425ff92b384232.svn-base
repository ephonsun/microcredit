Namespace.register('banger.bizEx');

//打开新页卡显示用户信息
function showPerCustomerInfoTab(customerType, isAdd, customerId) {
    var isAddFlag = isAdd? 1:2;
    var params = {
        lock: false,
        hasCloseConfirm: true,
        pid: tabs.getSelfId(window)
    };
    var nameFlag = isAddFlag +''+ customerType ;
    switch (nameFlag) {
        case '11':
            params.id = 'addCoCustomer';
            params.name = 'addCoCustomer';
            params.title = '企业客户-新建';
            params.url = '../customer/getCustomerInitPage.html?customerType=1';
            break;
        case '12':
            params.id = 'addPerCustomer';
            params.name = 'addPerCustomer';
            params.title = '个人客户-新建';
            params.url = '../customer/getCustomerInitPage.html?customerType=2';
            break;
        case '21':
            params.id = 'coCustomerDetail'+customerId;
            params.name = 'coCustomerDetail'+customerId;
            params.title = '客户-详情';
            params.url = '../customer/getCustomerInitPage.html?customerId='+customerId;
            break;
        case '22':
            params.id = 'perCustomerDetail'+customerId;
            params.name = 'perCustomerDetail'+customerId;
            params.title = '客户-详情';
            params.url = '../customer/getCustomerInitPage.html?customerId='+customerId;
            break;
    }
    tabs.add(params);
}
//保存弹出框窗口包含的表单，向服务端发送请求
banger.bizEx.saveDialogForm = function(isSave,o) {
    o = $.extend({},{
        frmSelector: '',//表单选择器
        condition: function(){return true;},//定义需要执行的额外条件
        filter: '',//表单后台名,
        url: '',//请求url
        type: 'POST',//请求类型,
        dialogName: '', //artdialog的id
        dataExt: {}, //额外的数据
        refresh: '', //父窗体的某个表格的jquery选择器,
        onSuccess: function(){}, //操作成功后，关闭窗口之前传入的函数
        onSuccessScope: null //表示成功后执行的回调函数的作用域
    },o);
    var flag = true;
    flag = banger.verify(o.frmSelector);
    if( o.frmSelector != '' || $(o.frmSelector).length == 0 ) {
        flag = banger.verify(o.frmSelector);
    }
    if( o.condition && isFunction(o.condition) ) {
        flag = o.condition() && flag;    
    }         
    if(flag) {
        var formData = getPostFields(o.filter);
        var postJson = $.extend({}, formData, o.dataExt);
        jQuery.ajax({
            type: o.type,
            url: o.url,
            data: postJson,
            success: function (result) {
                if (result == 'success') {
                    showConfirm({
                        icon: 'succeed',
                        content: '操作成功'
                    });
                    //父窗体处理
                    if( o.refresh ) {
                        if( window.parent ) {
                            var tabs = window.parent.tabs;
                            var tabId = getDialog(o.dialogName).config.tabId;
                            var win = tabs.getIframeWindow(tabId);
                            if( win && o.refresh){
                                $(win.document).find(o.refresh).flexReload();
                            }
                        }
                    }
                    if(isFunction(o.onSuccess)) {
                        if(o.onSuccessScope)
                        o.onSuccess.call(o.onSuccessScope);
                    }                                        
                    //dialog页面的处理
                    if (isSave == 'save') {
                        var dialog = getDialog(o.dialogName);
                        dialog.close();
                    } else {
                        if(!o.reloadUrl) {
                            window.location.reload();
                        } else {
                            window.location.href = o.reloadUrl;
                        }
                    }
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result,
                        ok: function(){}
                    });
                }
            },
            error: function(){
                showConfirm({
                    icon: 'error',
                    content: '网络错误,请稍后重试',
                    ok: function(){}
                });
            }
        });                
    }
};
//假如输入用户名，那么从服务器拉用户信息的数据,然后显示一个表格显示用户数据
banger.bizEx.ajaxGetCustomerInfo = function($gridCusNameQuery,o){
    o = $.extend({},{
        url: '', //服务器啦用户信息的地址
        dataType: 'json',//表示服务器返回类型
        height: 'auto',//显示的表格高度
        fieldType: 2,//默认是个人客户
        listeners: [],//元素格式：event为jquery的事件,handle表示额外要执行代码（默认会根据selector设置向服务端发送的数据）。{selector: '#customerName', param: 'customerName', event: 'change', handler: function(){}}
                      //Note:假如在handle函数中改变了option的值，那么必须要返回option
        params: {},//向服务端传输的数据
        onRowClick: function(){}//点击数据行事件
    },o);
        
   if(isArray(o.listeners)) {
        //初始化向服务端发送的数据
        for(var l in o.listeners) {
            var listener = o.listeners[l];
            o.params[listener.param] = $(listener.selector).val();
        }
        $gridCusNameQuery.data('option', o);
        for(l in o.listeners) {
            listener = o.listeners[l];
            //为每个元素绑定一个param
            $(listener.selector).data('param', listener.param);
            //输入框绑定键盘事件，发送请求
            if(listener.event === 'main') {
                $(listener.selector).data('customerInfoStId','');
                $(listener.selector).keyup(function(){
                    var $this = $(this);
                    var customerInfoStId = $this.data('customerInfoStId');
                    clearTimeout(customerInfoStId);
                    customerInfoStId = setTimeout(function(){
                        if($.trim($this.val()).length) {
                            $gridCusNameQuery.data('option').params[$this.data('param')] = $.trim($this.val());
                            showGrid($gridCusNameQuery, $gridCusNameQuery.data('option'));
                        } else {
                            $gridCusNameQuery.hide();
                        }
                    }, 500);
                    $this.data('customerInfoStId', customerInfoStId);
                });
            } else {
                $(listener.selector).on(listener.event, function(){
                    if(isFunction(listener.handler)) {
                        var option = listener.handler.call(this, $gridCusNameQuery.data('option'));
                        $gridCusNameQuery.data('option',option);
                    }
                    if(mainEleValNotNull(option)) {
                        $gridCusNameQuery.data('option').params[$(this).data('param')] = $.trim($(this).val());
                        showGrid($gridCusNameQuery, $gridCusNameQuery.data('option'));
                    } else {
                        $gridCusNameQuery.hide();
                    }
                });
            }
        }

    }
    //输入客户名的输入框不为空
    function mainEleValNotNull(obj){
        for(var l in obj.listeners) {
            var listener = obj.listeners[l];
            if('main' === listener.event) {
                return $.trim($(listener.selector).val()) ? true: false;
            }
        }
    }
    //显示表格
    var showGrid = function($gridCusNameQuery,o){
        var style = {
            'z-index':2, 
            left: '0px', 
            top: '25px',
            position: 'absolute',
            display: 'none'
        };
        $gridCusNameQuery.css(style);
        var perCustomerInfoFields = [
            { display: '客户',  field: 'customerName',width: 80 ,align:'left' },
            { display: '身份证',  field: 'customerCode', width: 150 ,align:'left' },
            { display: '联系电话',  field: 'phoneNum', width: 80 ,align:'left' }
        ];
        var coCustomerInfoFields = [
            { display: '客户',  field: 'customerName',width: 80 ,align:'left' },
            { display: '组织机构代码证',  field: 'customerCode', width: 120 ,align:'left' },
            { display: '联系人',  field: 'defaultLinkman',width: 80 ,align:'left' },
            { display: '联系电话',  field: 'phoneNum', width: 80 ,align:'left' }      
        ];
        var gridFields = o.fieldType == 1? coCustomerInfoFields : perCustomerInfoFields;
        o.params.customerType = o.fieldType == 1? 1: 2;
        $gridCusNameQuery.html('<table></table>');
        $gridCusNameQuery.find('table').flexigrid({
            url: o.url,
            dataType: o.dataType,
            height: o.height,
            width: o.width,
            params: o.params,
            fields: gridFields,
            onRowClick: function(ele,data){
                if( o && o.onRowClick ) {
                    o.onRowClick(ele,data);
                }
            },
            onComplete: function(data) {
                if( !data.rows.length ) {
                    $gridCusNameQuery.hide();
                } else {
                    $gridCusNameQuery.show();
                }
            },
            extendCell: {
            	customerName:function(name, row){
				 var customerType = row.cols.customerType, sex = row.cols.sex;
	                if(customerType==2) {
						if(sex==1)
							name = name+'（男）';
						else if(sex==2) 
							name = name+'（女）';
	                } 
	                
            	 return name;
            	}
            },
            onError: function(){
                $gridCusNameQuery.hide();
            }
        });
        $('body').click(function(){
            $gridCusNameQuery.hide();
        });
    };    
};

//联系方式输入步骤控制
$('input:text[data-type="linker"]').blur(function () {
    var reg = /\S/;
    var len = $('input:text[data-type="linker"]').index($(this));
    var $radio = $(':radio[data-type="linker"]');
    //手机号码验证处理
    if ($(this).data('validator') == 'mobile') {
        var mobileReg = /^1[0-9]{10}$/;
        if ($.trim($(this).val()) == '' || mobileReg.test($(this).val())) {
            $(this).parent().removeAttr('title').removeClass('ui-text-failed');
        } else {
            $(this).parent().attr('title', '手机号码填写错误').addClass('ui-text-failed');
        }
    }
    //联系人逻辑
    if (reg.test($(this).val())) {
        $('input:text[data-type="linker"]').each(function () {
            if($.trim($(this).val()) == '') {
                $(this).parent().removeAttr('title').removeClass('ui-text-failed');
            }
        });
        $radio.eq(len).removeAttr('disabled');
        $('input[class*=' + $(this).data('son') + ']').removeAttr('disabled').parent().removeClass('ui-text-disabled');
        if ($(':radio[data-type="linker"]:checked').length != 1) {
            $radio.eq(len).attr('checked', 'checked');
        } else if ($radio.eq(len).prop('checked')) {
            $(':radio[data-type="linker"]:enabled').eq(1).attr('checked', 'checked');
        }
    } else {
        $('input[class*=' + $(this).data('son') + ']').attr('disabled', true)
        .parent().addClass('ui-text-disabled').removeAttr('title').removeClass('ui-text-failed');
        $radio.eq(len).attr('disabled', 'disabled');
        $radio.eq(len).removeAttr('checked');
        if ($radio.filter(':checked').length == 0) {
            $radio.filter(':enabled').eq(0).attr('checked', 'checked');
        }
    }
});
$('input:text[data-type="linker"]').blur();
banger.bizEx.isExistLinker = function(){
    var reg = /\S/;
    var bool = false;
    $('input:text[data-type="linker"]').each(function () {
        if (reg.test($(this).val())) {
            bool = true;
        }
    });
    if (!bool) {
        $('input:text[data-type="linker"]').each(function () {
            $(this).parent().attr('title', '请至少填写一种联系方式').addClass('ui-text-failed');
        })
    }
    return bool;    
};
//客户归属联动
banger.bizEx.initCustomerBelong = function(sltBelongValue){
    //初始化机构树和用户树(客户归属联动)
    initDeptTreeSelectBox();
    initDeptUserTreeSelectBox();
    $('#selBelongTo').change(function() {
        changeBelongToType(this.value);
    });
    $('#selBelongTo').selectbox({
        value: sltBelongValue
    });
};
//客户归属方式改变
function changeBelongToType(val){
    $('#belongTo').val(val);
    switch(val){
        case 'brAll':
            $('#brAll').removeClass('hide');
            banger.validator.disableVerify(['#brUserText','#brDeptText']);
            $('#brUserText,#brDeptText, #hidDepts,#hidUsers').val('');
            $('#brMine,#brDept,#brUser').addClass('hide');
            break;
        case 'brMine':
            $('#brMine').removeClass('hide');
            banger.validator.disableVerify(['#brUserText','#brDeptText']);
            $('#brUserText,#brDeptText, #hidDepts,#hidUsers').val('');
            $('#brAll,#brDept,#brUser').addClass('hide');
            break;
        case 'brUser':
            $('#brUser').removeClass('hide');
            banger.validator.verify('#brUserText');
            banger.validator.disableVerify('#brDeptText');
            $('#brDeptText, #hidDepts').val('');
            $('#brAll,#brDept,#brMine').addClass('hide');
            break;
        case 'brDept':
            $('#brDept').removeClass('hide');
            banger.validator.verify('#brDeptText');
            banger.validator.disableVerify('#brUserText');
            $('#brUserText,#hidUsers').val('');
            $('#brAll,#brMine,#brUser').addClass('hide');
            break;
        default:
            $('#brAll').removeClass('hide');
            banger.validator.disableVerify(['#brUserText','#brDeptText']);
            $('#brUserText,#brDeptText,#hidUsers,#hidDepts').val('');
            $('#brMine,#brDept,#brUser').addClass('hide');
            break;
    }

}
//初始话机构树下拉框
function initDeptTreeSelectBox(){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../deptManager/initDeptTree.html',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载归属机构下拉树形选择框
    $('#brDeptText').treeselectbox({
        treeId : 'deptTree',
        nodes : json,
        checkEnable: false,
        checkReaction: { "Y" : "", "N" : "" },
		onTextClick: function(treeId){
			//根据文本框内的值初始化选中节点
			var value = $.trim($('#brDeptText').val());
			
			if(value != ''){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var node = zTree.getNodeByParam('name', value, null);
				zTree.selectNode(node, false);
			}
		},
        onClick: function(e, treeId, node, flag){
            //赋值
            $('#brDeptText').val(node.name);
            $('#hidDepts').val(node.id);
            $('#brDeptText').blur();
        }

    });
}
//初始化用户树下拉框
function initDeptUserTreeSelectBox(){
    var json;
    // 请求获取机构树
    jQuery.ajax({
        type : 'POST',
        url : '../deptManager/initDeptUserTree.html',
        async : false,
        data : {},
        success : function(text) {
            json = jQuery.parseJSON(text);
        }
    });
    //加载归属机构下拉树形选择框
    $('#brUserText').treeselectbox({
        treeId : 'deptUserTree',
        nodes : json,
        checkEnable: false,
        onTextClick: function(treeId){
			//根据文本框内的值初始化选中节点
			var value = $.trim($('#brUserText').val());
			
			if(value != ''){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var node = zTree.getNodeByParam('name', value, null);
				zTree.selectNode(node, false);
			}
		},
        onClick: function(e, treeId, node, flag){
            if(node.flag=='U'){ //用户
                //赋值
                $('#brUserText').val(node.name);
                $('#brUserText').blur();
                $('#hidUsers').val(node.id.split('-')[1]);
                $('#hidUserDepts').val(node.pId.split('-')[1]);
                $('#brUserText').blur();
            } else {
                $('#brUserText, #hidUsers').val('');
                $('#brUserText').blur();
            }
        }
    });
}

banger.bizEx.customerDataInfo=function(id,customerNo,customerType){
	tabs.add({
        id: 'infoDetail'+id,
        name: 'infoDetail'+id,
        pid: tabs.getSelfId(window),
        title : '客户详情',
        url : '../customer/getCustomerInitPage.html?customerType='+customerType+'&customerId='+id+'&customerNo='+customerNo,
        lock : false,
        hasCloseConfirm : false
    });

}

