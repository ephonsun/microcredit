// 页面加载完成时...
$(function(){

	// 加载列表
	$('#intentGrid').flexigrid({
        height: 280,
        url: '../customer/queryIntentProdListData.html?id='+$("#id").val(),
        usePage: true,
        fields: [
        	{ display: '意向产品', field: 'picProductName', width: 150 ,align : 'center' },
        	{ display: '意向说明', field: 'picRemark', width: 200 ,align : 'center' },
        	{ display: '添加时间', field: 'picCreateDate', width: 100 ,align : 'center' },
        	{ display: '添加人员', field: 'picCreateUserName', width: 100 ,align : 'center' }
		],
        onComplete : function(data) {
			$('#lblStatisticsIntent').text(data.total);
		}
    });
});

