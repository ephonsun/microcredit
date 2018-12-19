// 页面加载完成时...
$(function(){
    //浏览器不支持 placeholder 时才执行
    if (!('placeholder' in document.createElement('input'))) {
        $('[placeholder]').each(function () {
            var $tag = $(this); //当前 input
            var $copy = $tag.clone();   //当前 input 的复制
            if ($copy.val() == "") {
                $copy.css("color", "#999");
                $copy.val($copy.attr('placeholder'));
            }
            $copy.focus(function () {
                if (this.value == $copy.attr('placeholder')) {
                    this.value = '';
                    this.style.color = '#000';
                }
            });
            $copy.blur(function () {
                if (this.value=="") {
                    this.value = $copy.attr('placeholder');
                    $tag.val("");
                    this.style.color = '#a9a9a9';
                } else {
                    $tag.val(this.value);
                }
            });
            $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
        });
    }

    banger.verify('#loanStartTime', {name : 'posiInteger', tips: '必须为正整数'})
    banger.verify('#loanEndTime', {name : 'posiInteger', tips: '必须为正整数'})
    // 加载操作时间
	$('#txtStartDate').datepicker({
		maxDate: '#F{$dp.$D(\'txtEndDate\')}'
	});
	$('#txtEndDate').datepicker({
		minDate: '#F{$dp.$D(\'txtStartDate\')}'
	});
    // 加载操作时间
    $('#auditStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'auditEndDate\')}'
    });
    $('#auditEndDate').datepicker({
        minDate: '#F{$dp.$D(\'auditStartDate\')}'
    });
    // 加载操作时间
    $('#loanContractStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'loanContractStartDate\')}'
    });
    $('#loanContractEndDate').datepicker({
        minDate: '#F{$dp.$D(\'loanContractEndDate\')}'
    });
    // 加载操作时间
    $('#loanStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'loanStartDate\')}'
    });
    $('#loanEndDate').datepicker({
        minDate: '#F{$dp.$D(\'loanEndDate\')}'
    });
    // 加载操作时间
    $('#loanContractEndStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'loanContractEndStartDate\')}'
    });
    $('#loanContractEndEndDate').datepicker({
        minDate: '#F{$dp.$D(\'loanContractEndEndDate\')}'
    });
    // 加载操作时间
    $('#auditEndStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'auditEndStartDate\')}'
    });
    $('#auditEndEndDate').datepicker({
        minDate: '#F{$dp.$D(\'auditEndEndDate\')}'
    });


	$('select').selectbox();
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');
        setTimeout(function(){$("input[name='customer']").focus();},100);
        setTimeout(function(){$("input[name='customer']").blur();},100);
        //$('#customer').blur();

    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchList();
    });
    //刷新
    $('#btnRefresh').click(function(){
        refreshList();
    });

    //同步贷款账户信息
    $('#btnRefreshLoanAccount').click(function(){
        refreshLoanAccount();
    });
});

/***********************************************申请列表*****************************************************/
//跳转到新建页面
$("#addApplyInfoBtn").click(function() {
	tabs.add({
		id : 'addOrUpdateLoan',
		name : 'addOrUpdateLoan',
		pid: tabs.getSelfId(window),
		title : '贷款-新建',
		url : '../loan/getLoanTabs.html?module='+$(this).attr('module')+'&random='+Math.random()*10000,
		lock : false
	});
});
/***********************************************申请列表*****************************************************/
/***********************************************分配列表*****************************************************/
//跳转到新建页面
$("#applyInfoSignBtn").click(function() {
    var selectObj = $('#grid').selectedRows();
    var ids="";
    for(var i = 0; i<selectObj.length;i++){
        var k = selectObj[i].length-1;
        var id = selectObj[i][k].RowId.replace("flex","");
        ids = ids +id+","
    }
    if(ids==""){
        showConfirm({
            icon: 'warning',
            content: "请选择要分配的记录!"
        });
        return;
    }
    ids = ids.substring(0,ids.length-1);
   applyInfoSign(ids);
});
function applyInfoSign(ids){
    var url = '../loanAllot/gotoAllotSignPage.html?ids='+ids+'&random='+Math.random()*10000;
    showDialog({
        id: 'loanAllotSign',
        title: '分配贷款',
        url: url,
        width: 330,
        height: 200,
        tabId: tabs.getSelfId(window)
    });
}
/***********************************************分配列表*****************************************************/
/***********************************************公共方法*****************************************************/

//列表详情/编辑
function viewShowApplyInfo(module,data, title, showApply){
    // var title = "1" == showApply ? "贷款信息-详情" : "贷款信息-编辑";
    // title = title;// + '[' +  data.cols.loanName + "]";
    tabs.add({
        id :'updateCustomer' + module +  showApply +data.id,
        pid:tabs.getSelfId(window),
        name:'updateCustomer' + module +  showApply +data.id,
        title: title,
        url : '../loan/getLoanTabs.html?module='+module + '&loanId='+data.id + '&showApply='+showApply+'&random='+Math.random()*10000,
        lock:false
    });
}
function editShow(id, module) {
    var title = 'apply' == module ? '申请' : '调查';
    var url =  '../loan/getLoanTabs.html?module='+ module+'&showApply=1&loanId=' + id+'&random='+Math.random()*10000;
    tabs.add({
        id :'updateCustomer'+module+'1' +id,
        pid:tabs.getSelfId(window),
        name:'updateCustomer'+module+'1' +id,
        title: title + '列表-详情',
        url : url,
        lock:false
    });
}
function editEdit(id, module) {
    var title = 'apply' == module ? '申请' : '调查';
    var url =  '../loan/getLoanTabs.html?module='+ module+'&loanId=' + id+'&random='+Math.random()*10000;
    tabs.add({
        id :'updateCustomer'+module +id,
        pid:tabs.getSelfId(window),
        name:'updateCustomer'+module+id,
        title: title + '列表-编辑',
        url : url,
        lock:false
    });
}
//列表详情
function showApplyInfo(module,data, title){
    viewShowApplyInfo(module,data,title, "1");
}

//列表编辑
function viewApplyInfo(module,data, title){
    viewShowApplyInfo(module,data,title, "");
}


//回退申请信息
function backApplyInfo(data, module){
    var url = '../loan/backApplyInfo.html?loanId='+data.id + '&module=' + module+'&random='+Math.random()*10000;
    showDialog({
        id: 'backLoanApply',
        title: '回退贷款信息',
        url: url,
        width: 330,
        height: 145,
        tabId: tabs.getSelfId(window)
    });
}
//列表搜索
function searchList() {
	var postJson = {};
    postJson['loanTypeId'] = $('#loanTypeId').val();
    postJson['customer'] = $('#customer').val();
    postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
    postJson['processType'] = jQuery.trim($('#processType').val());
    postJson['teamId'] = jQuery.trim($('#teamId').val());

    postJson['afterState'] = $('#afterState').val();

    postJson['montiorState'] = $('#montiorState').val();
    postJson['collectionState'] = $('#collectionState').val();
    postJson['firstMonitorState'] = $('#firstMonitorState').val();


    postJson['memberUserId'] = $('#memberUserId').val();
    postJson['memberUser'] = $('#memberUser').val();

    postJson['auditStartDate'] = jQuery.trim($('#auditStartDate').val());
    postJson['auditEndDate'] = jQuery.trim($('#auditEndDate').val());

    postJson['loanContractStartDate'] = jQuery.trim($('#loanContractStartDate').val());
    postJson['loanContractEndDate'] = jQuery.trim($('#loanContractEndDate').val());
    postJson['benchMode'] = "";

    postJson['loanStartDate'] = jQuery.trim($('#loanStartDate').val());
    postJson['loanEndDate'] = jQuery.trim($('#loanEndDate').val());

    postJson['loanContractEndStartDate'] = jQuery.trim($('#loanContractEndStartDate').val());
    postJson['loanContractEndEndDate'] = jQuery.trim($('#loanContractEndEndDate').val());

    postJson['auditEndStartDate'] = jQuery.trim($('#auditEndStartDate').val());
    postJson['auditEndEndDate'] = jQuery.trim($('#auditEndEndDate').val());

    postJson['loanStartTime'] = jQuery.trim($('#loanStartTime').val());
    postJson['loanEndTime'] = jQuery.trim($('#loanEndTime').val());

    postJson['isNew'] = jQuery.trim($('#isNew').val());
    postJson['productType'] = jQuery.trim($('#productType').val());
    postJson['isExistAccountNo'] = jQuery.trim($('#isExistAccountNo').val());
    postJson['isOverdue'] = jQuery.trim($('#isOverdue').val());
    postJson['isEnough'] = jQuery.trim($('#isEnough').val());

    //postJson['contractSubmitStartDate'] = jQuery.trim($('#contractSubmitStartDate').val());
    //postJson['contractSubmitEndDate'] = jQuery.trim($('#contractSubmitEndDate').val());


    var rule=/^[0-9]*[1-9][0-9]*$/;
    var loanStartTime=jQuery.trim($('#loanStartTime').val());
    var loanEndTime=jQuery.trim($('#loanEndTime').val());
    if(""!=loanStartTime || ""!=loanEndTime){
        if(rule.test(loanStartTime)&&rule.test(loanEndTime)){
                $('#grid').flexSearch(postJson);
        }else if(rule.test(loanStartTime)&&""==loanEndTime){
            $('#grid').flexSearch(postJson);
        }else if(""==loanStartTime&&rule.test(loanEndTime)){
            $('#grid').flexSearch(postJson);
        }
    }else if(""==loanStartTime && ""==loanEndTime){
        $('#grid').flexSearch(postJson);
    }

}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}
//初始化列表，根据列名和按钮自定义渲染
function initGrid(fields, buttons, module, width){
    // 加载列表
    $('#grid').flexigrid({
        height: 280,

        url: '../loan/queryLoanInfoListData.html?module=' + module,
        usePage: true,
        // params: {'montiorState': $('#montiorState').val(), 'collectionState': $('#collectionState').val()},
        //multiSelect:true,
        rowIdProperty: 'id',
        fields: fields,
        //工作台跳转参数
        params:{"montiorState":$("#montiorState").val(),"benchMode": $("#benchMode").val(),"collectionState":$("#collectionState").val()
            ,"collectionType":$("#collectionType").val()},
        action: {
            display: '操作',
            width: width,
            align : 'center',
            buttons: buttons
        },
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });
}


// 刷新人员表
function refreshLoanAccount() {
    jQuery.ajax({
        type : 'post',
        url : '../loan/refreshLoanAccount.html',
        data : "",
        success : function(json) {
            var result = jQuery.parseJSON(json);
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: '已发起贷款账户同步请求<br/>请耐心等待数据同步完成！'
                });
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

function refreshLoanAccountTab(loanId){
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loan/refreshLoanAccountTab.html',
        data : {"loanId":loanId},
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: '完成请求！'
                });
                refreshList();
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

//新建合同
//function addContractPage(module,data, title){
//    //window.location.href="../loan/getLoanListPage.html?module=contract";
//    tabs.add({
//        id :'updateCustomer' + module +data.id,
//        pid:tabs.getSelfId(window),
//        name:'updateCustomer' + module +data.id,
//        title : title,
//        url : '../loan/getLoanlistPage.html?module='+module+'&contract=contract'+'&random='+Math.random()*10000,
//        lock : false
//    });
//}
