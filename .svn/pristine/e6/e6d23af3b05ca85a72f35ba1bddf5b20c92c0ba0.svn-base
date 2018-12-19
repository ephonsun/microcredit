// 页面加载完成时...
$(function(){
    //初始化控件
    layout.initForms();
	// 加载产品列表
	$('#productGrid').flexigrid({
        height: 280,
        url: '../prodProduct/queryProductListData.html',
        fields: [
        	{ display: '产品代码', field: 'productCode', width: 100 ,align: 'center'},
        	{ display: '产品名称', field: 'productName', width: 100 ,align: 'center'},
        	{ display: '产品类型', field: 'productTypeName', width: 100 ,align: 'center'},
        	{ display: '产品介绍', field: 'productInfo', width: 260 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 100,
        	align: 'center',
        	buttons: [
            	{
            		display: '选择',
            		onClick: function(tr, data){
            			selectProduct(data);
            		}
        		}
            ]
        },
        usePage: true,
        onComplete : function(data) {
		}
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
		searchProductList();
	});
});

//
$("#btnDelete").click(function () {
	selectProduct("");
});

//选择之后跳转到页面父页面
function selectProduct(data){
    var dialog = getDialog('productListPageForPotentialCust');
    var win = tabs.getIframeWindow(dialog.config.tabId);
	if(data == ""){
		$(win.document).find("#productCode").val("");
		$(win.document).find("#productName").val("");
	}else{
		$(win.document).find("#productCode").val(data.cols.productCode);
		$(win.document).find("#productName").html(data.cols.productName);
		$(win.document).find("#selectProductForCust").html("删除产品");
	}
    setTimeout(function() {
        dialog.close();
    }, 200);
}

function searchProductList() {
	var postJson = {};
	postJson['productCode'] = jQuery.trim($('#productCode').val());
	postJson['productName'] = jQuery.trim($('#productName').val());
	postJson['productType'] = jQuery.trim($('#productType').val());
	postJson['beginTime'] = jQuery.trim($('#txtStartDate').val());
	postJson['endTime'] = jQuery.trim($('#txtEndDate').val());
	$('#productGrid').flexSearch(postJson);
}
