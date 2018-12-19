
var userId = "0";

// 页面加载完成时...
$(function(){

	// 加载操作时间
	$('#txtStartDate').datepicker({
		maxDate: '#F{$dp.$D(\'txtEndDate\')}'
	});
	$('#txtEndDate').datepicker({
		minDate: '#F{$dp.$D(\'txtStartDate\')}'
	});
	$('select').selectbox();

	userId = $("#userId").val();

	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../loan/queryApplyInfoListData.html',
        usePage: true,
		multiSelect:true,
		rowIdProperty: 'id',
		fields: [
        	{ display: '客户信息', field: 'customerInfo', width: 300 ,align : 'center' },
        	{ display: '客户类型', field: 'customerLevelName', width: 100 ,align : 'center' },
        	{ display: '归属人', field: 'belongUserName', width: 100 ,align : 'center' }
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
                },
            	{
            		display: '编辑',
            		onClick: function(tr, data){
            			updateCustomer(data);
            		},
					isDisabled: function(tr, data){
						if (tr.cols.belongUserId.toString()==userId) {
							return false;
						} else {
							return true;
						}
					}
        		},
				{
					display: '定位',
					onClick: function(tr, data){
						//queryRole(data);
					}
				},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteCustomer(data);
            		},
					isDisabled: function(tr, data){
						if (tr.cols.belongUserId.toString()==userId) {
							return false;
						} else {
							return true;
						}
					}
        		}
            ]
        },
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

	$("#selectFlag").change(function(){
		if($('#selectFlag').is(':checked')) {
		}else{
		}
	});

});

function updateCustomer(data){
	tabs.add({
		id :'updateCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'updateCustomer',
		title:'客户-编辑',
		url : '../customer/getCustomerTabs.html?id='+data.id,
		lock:false
	});
}

function viewCustomer(data){
	tabs.add({
		id :'viewCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'viewCustomer',
		title:'客户-查看',
		url : '../customer/getCustomerTabs.html?id='+data.id+"&flag="+1,
		lock:false
	});
}

function deleteCustomer(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除客户“'+ data.cols.customerName+ '”吗？删除后将无法恢复。',
        ok:function(){
            var postJson = {};
            postJson['id'] = data.id;
            jQuery.ajax({
                type:'POST',
                url:'../customer/deleteCustomer.html',
                data:postJson,
                success:function(text){
                    showConfirm({
                        icon: 'succeed',
                        content: '删除成功'
                    });
					refreshList();
                }
            });
        },
        cancel: function() {}
    });
}

//跳转到新建页面
$("#btnAdd").click(function() {
	tabs.add({
		id : 'addOrUpdateLoan',
		name : 'addOrUpdateLoan',
		pid: tabs.getSelfId(window),
		title : '贷款-新建',
		url : '../loan/getLoanTabs.html?random='+Math.random()*10000,
		lock : false
	});
});

function searchList() {
	var postJson = {};
	postJson['customerLevel'] = $('#customerLevel').val();
	postJson['customer'] = $('#customer').val();
	postJson['selectFlag'] = $('#selectFlag').val();
	postJson['belongUserName'] = $('#belongUserName').val();

	$('#grid').flexSearch(postJson);

}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}

////跳转到新建页面
//$("#btnView").click(function() {
//	tabs.add({
//		id : 'viewschedule',
//		name : 'viewschedule',
//		pid: tabs.getSelfId(window),
//		title : '客户-日程',
//		url : '../schedule/getCustScheduleTabPage.html?customerId=1',
//		lock : false
//	});
//});