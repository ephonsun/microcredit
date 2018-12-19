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

	// 加载角色列表
	$('#grid').flexigrid({
        height: 500,
        url: '../sysMobileInfo/queryMobileInfoListData.html',
        usePage: true,
        fields: [
        	{ display: '序列号', field: 'serialNo', width: 100 ,align : 'center' },
        	{ display: '品牌', field: 'mobileBrand', width: 100 ,align : 'center' },
        	{ display: '型号', field: 'mobileModel', width: 100 ,align : 'center' },
        	{ display: '系统版本号', field: 'systemVersion', width: 100 ,align : 'center' },
        	{ display: '使用人员', field: 'userName', width: 100 ,align : 'center'},
        	{ display: '最近登录时间', field: 'lastLoginTime', width: 150 ,align : 'center'}
		],
        action: {
            display: '操作',
            width: 120,
            align: 'center',
            buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){
                        seeDetails(data);
                    }
                },
				{
					display: '登录记录',
					onClick: function(tr, data){
					loginRecord(data);
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
});

function seeDetails(data){
    var url = '../sysMobileInfo/getMobileInfoDetailPage.html?serialNo='+data.id;
    showDialog({
        id: 'mobileDetail',
        title: '设备详情',
        url: url,
        width: 650,
        height: 300,
        tabId: tabs.getSelfId(window)
    });
}

function loginRecord(data){
    // var url = '../sysMobileInfo/getloginRecordPage.html?serialNo='+data.id;
    tabs.add({
        id :'loginRecord',
        pid:tabs.getSelfId(window),
        name:'loginRecord',
		title:"登录列表",
        url : '../sysMobileInfo/getloginRecordPage.html?serialNo='+data.id,
        lock:false
    });
}
function searchList() {
	var postJson = {};
	postJson['serialNo'] = $('#serialNo').val();
	postJson['mobileBrand'] = $('#mobileBrand').val();
	postJson['mobileModel'] = $('#mobileModel').val();
	postJson['systemVersion'] = $('#systemVersion').val();
	postJson['userName'] = $('#userName').val();
	postJson['txtStartDate'] = $('#txtStartDate').val();
	postJson['txtEndDate'] = $('#txtEndDate').val();
	$('#grid').flexSearch(postJson);
}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}