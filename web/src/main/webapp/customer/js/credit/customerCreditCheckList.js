$(function(){
	$('select').selectbox({});

	// 加载日程列表
	$('#creditCustGrid').flexigrid({
        height: 280,
        url: '../creditCheck/queryCustomerCreditCheckListData.html?checkStatus='+$("#checkStatus").val(),
        fields: [
        	{ display: '客户信息', field: 'customerName', width: 50 ,align: 'center'},
        	{ display: '申请人', field: 'userName', width: 50 ,align: 'center'},
        	{ display: '申请提交时间', field: 'applyTime', width: 120 ,align: 'center'},
        	{ display: '完成情况', field: 'checkStatus', width: 50 ,align: 'center',data:{1:"未完成",2:"已完成",3:"已拒绝"}},
        	{ display: '调查提交时间', field: 'checkDate', width: 120 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 100,
        	align: 'center',
        	buttons: [
        	    {
            		display: '详情',
            		onClick: function(tr, data){
            			detail(data);
            		}
        		}
            ]
        },
        rowIdProperty: 'id',
        usePage: true,
        onComplete : function(data) {
			$('#lblStatistics').text(data.total);
		}
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
		searchList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshList();
	});
});


function detail(data){
	var url = '../creditCheck/getCustomerCreditCheckUpdatePage.html?customerCreditCheckId='+data.id;
	tabs.add({
        id: 'customerCreditCheck'+data.id,
        name: 'customerCreditCheck',
        pid: tabs.getSelfId(window),
        title : '客户征信调查',
        url : url,
        lock : false
    });
}


function searchList() {
	var postJson =getPostFields();
	
	$('#creditCustGrid').flexSearch(postJson);
}
// 刷新列表表
function refreshList() {
	$('#creditCustGrid').flexReload();
}