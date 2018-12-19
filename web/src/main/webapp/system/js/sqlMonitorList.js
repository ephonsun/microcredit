// 页面加载完成时...
$(function(){
	
	$('#sqlCountGrid').flexigrid({
        height: 312,
        url: '../monitor/getSqlMoniterListData.html',
        fields: [
        	{ display: 'SQLID', field: 'sqlId', width: 200 },
        	{ display: '平均执行时间', field: 'averageTime', width: 80 },
        	{ display: '总共执行次数', field: 'exceCount', width: 80 },
        	{ display: '总共执行时间', field: 'totalTime', width: 80 },
        	{ display: '最大执行时间', field: 'maxTime', width: 80 },
        	{ display: '最小执行时间', field: 'minTime', width: 80 },
        	{ display: '最后执行时间', field: 'lastTime', width: 80 }
        ],
        action: {
        	display: '操作',
        	width: 100
        },
        usePage: true,
        onComplete : function(data) {
			$('#lblSqlTotol').text(data.total);
		}
    });
	//刷新
	$('#btnRefresh').click(function(){
		$('#sqlCountGrid').flexReload();
	});
});

// 刷新人员表
function refresh() {
	$('#sqlCountGrid').flexReload();
}

//导出Excel
$("#btnExport").click(function() {	
	jQuery.ajax({
		type : 'POST',
		url : '../monitor/exportSqlMonitorData.html',
		data : {},
		success : function(result) {
			if(result){
				document.location.href = '../monitor/downloadExportExcelFile.html?filename=' + result + '&random=' + Math.random();
			}
		}
	});
	
});