// 页面加载完成时...
$(function(){
	// 加载列表
	$('#creditGrid').flexigrid({
        height: 280,
        url: '../customer/queryCreditListData.html?id='+$("#id").val(),
        usePage: true,
        fields: [
        	{ display: '创建时间', field: 'createDate', width: 100 ,align : 'center' },
        	{ display: '申请金额(元)', field: 'loanApplyAmountFormat', width: 100 ,align : 'center' },
        	{ display: '贷款阶段', field: 'loanProcessTypeName', width: 100 ,align : 'center' },
        	{ display: '贷款类型', field: 'loanTypeName', width: 100 ,align : 'center' },
        	{ display: '申请人员', field: 'applyUserName', width: 100 ,align : 'center' },
        	{ display: '调查人员', field: 'investigateUserName', width: 100 ,align : 'center' },
		],
        action: {
            display: '操作',
            width: 150,
            align : 'center',
            buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){

                        viewCustomer(data);
                    }
                }

            ]
        },
        onComplete : function(data) {
			$('#lblStatisticsCredit').text(data.total);
		}
    });
});

function viewCustomer(data) {
   var  id = data.id;

    tabs.add({
        id :'updateCustomerall1' +id,
        pid:tabs.getSelfId(window),
        name:'updateCustomerall1' +id,
        title: '列表-详情',
        url : '../loan/getLoanTabs.html?module=all&loanId='+id + '&showApply=1&random='+Math.random()*10000,
        lock:false
    });
}