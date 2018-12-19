// 页面加载完成时...
$(function(){
	$('#intentCustomerGrid').flexigrid({
        height: 280,
        url: '../custPotentialCustomers/queryPotentialCustomersListData.html',
        usePage: true,
        multiSelect:true,
        rowIdProperty: 'id',
        params:{"productId":$("#picProductId").val()},
        fields: [
            { display: '客户姓名', field: 'customerName', width: 100 ,align : 'center' },
            { display: '证件类型', field: 'identifyTypeName', width: 100 ,align : 'center' },
            { display: '证件号码', field: 'cardNumberX', width: 180 ,align : 'center' },
            { display: '联系电话', field: 'telephoneNumberX', width: 100 ,align : 'center' },
            { display: '意向产品', field: 'productName', width: 100 ,align : 'center' },
            { display: '营销成功', field: 'marketingSuccess', width: 100 ,align : 'center', data: {0: "否", 1: "是"}},
            { display: '意向时间', field: 'intentionDate', width: 100 ,align : 'center' },
            { display: '新建时间', field: 'createDate', width: 100 ,align : 'center' },
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
                        return !customerEdit;
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        deletePotentialCustomer(data);
                    },
                    isDisabled: function(tr, data){
                        return !customerDel;
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
		searchIntentCustomerList();
	});
	//刷新
	$('#btnRefresh').click(function(){
        refreshList();
	});
});




function updateCustomer(data){
    tabs.add({
        id :'potentialCustomerUpdate'+data.id,
        pid:tabs.getSelfId(window),
        name:'potentialCustomerUpdate'+data.id,
        title:'潜在客户-编辑',
        url : '../custPotentialCustomers/getPotentialCustomersTabs.html?id='+data.id,
        lock:false
    });
}

function viewCustomer(data){
    tabs.add({
        id :'potentialCustomerView'+data.id,
        pid:tabs.getSelfId(window),
        name:'potentialCustomerView'+data.id,
        title:'潜在客户-查看',
        url : '../custPotentialCustomers/getPotentialCustomersTabs.html?id='+data.id+"&isShow=1",
        lock:false
    });
}


function deletePotentialCustomer(data){
    showConfirm({
        icon:'confirm',
        content:'您确定要删除潜在客户“'+ data.cols.customerName+ '”吗？删除后将无法恢复。',
        ok:function(){
            var postJson = {};
            postJson['ids'] = data.id;
            jQuery.ajax({
                type:'POST',
                url:'../custPotentialCustomers/deletePotentialCustomers.html',
                data:postJson,
                success:function(text){
                    if ("SUCCESS" == text) {
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                        refreshList();
                    } else {
                        showConfirm({
                            icon: 'warning',
                            content: '删除失败'
                        });
                        refreshList();
                    }
                }
            });
        },
        cancel: function() {}
    });
}

//意向客户跳转到新建潜在客户页面
$("#btnAdd").click(function() {
    tabs.add({
        id : 'addPotentialCustomer',
        name : 'addPotentialCustomer',
        pid: tabs.getSelfId(window),
        title : '潜在客户-新建',
        url : '../custPotentialCustomers/getPotentialCustomersTabs.html?productCode='+encodeURI(encodeURI(prodJson.productCode)),
        lock : false
    });
});

//之前意向客户跳转页面,2017-11-28需求确认合并到潜在客户
// $("#btnAdd").click(function() {
// 	var url = '../prodIntentCustomer/getIntentCustomerInsertPage.html?productId='+prodJson.productId;
//     showDialog({
//         id: 'intentCustomerHandle',
//         title: '新建意向客户',
//         url: url,
//         width: 700,
//         height: 320,
//         tabId: tabs.getSelfId(window)
//     });
// });

function searchIntentCustomerList() {
	var postJson = {};
	postJson['customerInfo'] = jQuery.trim($('#customerInfo').val());
	postJson['picProductName'] = jQuery.trim($('#picProductName').val());
	postJson['picRemark'] = jQuery.trim($('#picRemark').val());
	postJson['picCreateUserName'] = jQuery.trim($('#picCreateUserName').val());
	postJson['myIntentCustomer'] = $("#myIntentCustomer").is(':checked');
	$('#intentCustomerGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshList() {
	$('#intentCustomerGrid').flexReload();
}
