
// 页面加载完成时...
$(function(){

    $('#btnUpdate').click(function() {
        updateTrustedPayment();
    });

	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../loan/queryTrustedPaymentListData.html',
        usePage: true,
		multiSelect:true,
		rowIdProperty: 'id',
		fields: [
        	{ display: '客户姓名', field: 'loanName', width: 50 ,align : 'center' },
        	{ display: '证件号码', field: 'loanIdentifyNum', width: 120 ,align : 'center' },
            { display: '是否本行账号', field: 'isBankAccount', width: 60 ,align : 'center' },
            { display: '交易对手账号', field: 'counterpartyAccount', width: 120 ,align : 'center' },
            { display: '交易对手卡号', field: 'counterpartyCardNumber', width: 120 ,align : 'center' },
        	{ display: '交易对手名称', field: 'counterpartyName', width: 160 ,align : 'center' },
            { display: '支付金额', field: 'paymentAmount', width: 70 ,align : 'center' },
            { display: '支付状态', field: 'paymentStatus', width: 60 ,align : 'center' }
		],
        action: {
        	display: '操作',
        	width: 100,
            align : 'center',
        	buttons: [
            	{
            		display: '编辑',
            		onClick: function(tr, data){
            			updateTrusedPayment(data);
            		},
					isDisabled: function(tr, data){
            			if ("支付成功"==tr.cols.paymentStatus) {
                            return true;
                        }
                        return false;
					}
        		}
            ]
        },
        preProcessData:function (data) {
            for (var i=0;i<data.rows.length;i++){
                if (data.rows[i].cols.isBankAccount=="01") {
                    data.rows[i].cols.isBankAccount="是本行";
                }else if (data.rows[i].cols.isBankAccount=="02"){
                    data.rows[i].cols.isBankAccount="不是本行";
                }
                if (data.rows[i].cols.paymentStatus==""||data.rows[i].cols.paymentStatus=="0"){
                    data.rows[i].cols.paymentStatus="待同步";
                }else if (data.rows[i].cols.paymentStatus=='1'){
                    data.rows[i].cols.paymentStatus="已同步";
                }else if (data.rows[i].cols.paymentStatus=='2'){
                    data.rows[i].cols.paymentStatus="支付成功";
                }else if (data.rows[i].cols.paymentStatus=='3'){
                    data.rows[i].cols.paymentStatus="支付失败";
                }
            }
            return data;
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

function updateTrusedPayment(data){
	tabs.add({
		id :'updateTrusedPayment'+data.id,
		pid:tabs.getSelfId(window),
		name:'updateTrusedPayment',
		title:'受托支付编辑-编辑',
		url : '../loan/getTrusedPayment.html?id='+data.id,
		lock:false
	});
}



function searchList() {
	var postJson = {};
	postJson['customer'] = $('#customer').val();
	$('#grid').flexSearch(postJson);

}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}

function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    tabs.close(tabs.getSelfId(window));
}

//编辑保存
function updateTrustedPayment(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../loan/updateTrustedPayment.html',
        data : {
            id:$("#id").val(),
            loanId:$("#loanId").val(),
            isBankAccount:$("#isBankAccount").val(),
            counterpartyAccount:$("#counterpartyAccount").val(),
            counterpartyCardNumber:$("#counterpartyCardNumber").val(),
            counterpartyName:$("#counterpartyName").val(),
            counterpartyBankName:$("#counterpartyBankName").val(),
            counterpartyBankNumber:$("#counterpartyBankNumber").val()
        },
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeTab()
            }else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }

        }
    });
}

