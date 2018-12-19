
$(function(){

	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../sysImportHistory/queryImportHistoryListData.html',
        usePage: true,
		multiSelect:false,
		rowIdProperty: 'id',
		fields: [
        	{ display: '操作者', field: 'userName', width: 150 ,align : 'center' },
        	{ display: '导入模块', field: 'importModuleName', width: 150 ,align : 'center' },
			{ display: '导入进度', field: 'progressRate', width: 150 ,align : 'center' },
			{ display: '导入状态', field: 'progressName', width: 150 ,align : 'center' },
			{ display: '导入开始时间', field: 'createDate', width: 150 ,align : 'center' },
			{ display: '备注', field: 'remark', width: 150 ,align : 'center' },
		],
        // action: {},
        onComplete : function(data) {

			$('#lblStatistics').text(data.total);
		}
    });
});

//刷新
$('#btnRefresh').click(function(){
    refreshList();
});

// 刷新
function refreshList() {
	$('#grid').flexReload();
}