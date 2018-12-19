// 页面加载完成时...
$(function(){
    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });
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
        	{ display: '产品介绍', field: 'productInfo', width: 300 ,align: 'center'},
        	{ display: '创建时间', field: 'productCreateDate', width: 100 ,align: 'center'},
        	{ display: '更新时间', field: 'productUpdateDate', width: 100 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 200,
        	align: 'center',
        	buttons: [
        	    {
            		display: '编辑',
            		onClick: function(tr, data){
            			updateProduct(data);
            		}
                    ,isDisabled: function(){
                        return !productSaleEdit;
                    }
        		},
                {
                    display: '详情',
                    onClick: function(tr, data){
                    	queryProduct(data);
                    }
                },
                {
                    display: '潜在客户',
                    onClick: function(tr, data){
                    	queryIntentCustomerList(data);
                    }
                    ,isDisabled: function(){
                        return !intentionalCustomer;
                    }
                },
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteProduct(data);
            		}
                    ,isDisabled: function(){
                        return !productSaleDel;
                    }
        		}
            ]
        },
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
		searchProductList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshProductList();
	});
});

function queryIntentCustomerList(data){
	tabs.add({
		id :'queryIntentCustomerList'+data.id,
		pid:tabs.getSelfId(window),
		name:'queryIntentCustomerList',
		title:data.cols.productName+'-潜在客户',
		url:'../prodIntentCustomer/getIntentCustomerListPage.html?productId='+data.id,
		lock:false
	});
}

function deleteProduct(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要彻底删除产品“'+data.cols.productName+'”及关联客户潜在信息吗？删除后将无法恢复。',
        ok:function(){
        	jQuery.ajax({
    			url: '../prodProduct/deleteProduct.html',
    			type:'POST', 
    			dataType:'json',
                data: {"productId": data.id},
                sync: false,
                success: function(result){
                	if(result.success){
                		showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        refreshProductList();
                	}else {
                		showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
					}
                }
    		});
        },
        cancel: function() {}
	});
}

function queryProduct(data){
	var url = '../prodProduct/getProductDetailPage.html?productId='+data.id;
    showDialog({
        id: 'productHandle',
        title: '产品详情',
        url: url,
        width: 700,
        height: 350,
        tabId: tabs.getSelfId(window)
    });
}

function updateProduct(data){
	var url = '../prodProduct/getProductUpdatePage.html?productId='+data.id;
    showDialog({
        id: 'productHandle',
        title: '编辑产品',
        url: url,
        width: 660,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
}

$("#btnAdd").click(function() {
	var url = '../prodProduct/getProductInsertPage.html';
    showDialog({
        id: 'productHandle',
        title: '新建产品',
        url: url,
        width: 660,
        height: 330,
        tabId: tabs.getSelfId(window)
    });
});

function searchProductList() {
	var postJson = {};
	postJson['productCode'] = jQuery.trim($('#productCode').val());
	postJson['productName'] = jQuery.trim($('#productName').val());
	postJson['productType'] = jQuery.trim($('#productType').val());
	postJson['beginTime'] = jQuery.trim($('#txtStartDate').val());
	postJson['endTime'] = jQuery.trim($('#txtEndDate').val());
	$('#productGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshProductList() {
	$('#productGrid').flexReload();
}