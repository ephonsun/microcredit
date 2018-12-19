$(function(){
    //浏览器不支持 placeholder 时才执行
    if (!('placeholder' in document.createElement('input'))) {
        $('[placeholder]').each(function () {
            var $tag = $(this); //当前 input
            var $copy = $tag.clone();   //当前 input 的复制
            if ($copy.val() == "") {
                $copy.css("color", "#999");
                $copy.val($copy.attr('placeholder'));
            }
            $copy.focus(function () {
                if (this.value == $copy.attr('placeholder')) {
                    this.value = '';
                    this.style.color = '#000';
                }
            });
            $copy.blur(function () {
                if (this.value=="") {
                    this.value = $copy.attr('placeholder');
                    $tag.val("");
                    this.style.color = '#a9a9a9';
                } else {
                    $tag.val(this.value);
                }
            });
            $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
        });
    }


	$('select').selectbox({});
    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });
	// 加载日程列表
	$('#marketCustomerGrid').flexigrid({
        height:200,
        //multiSelect: true,
        url: '../marketCustomer/queryMemberCustomerListData.html',
        fields: [
        	{ display: '客户信息', field: 'customerName', width: 150,align: 'center'},
        	{ display: '证件信息', field: 'cardNumber', width: 150 ,align: 'center'},
        	{ display: '意向金额(元)', field: 'loanMoneyStr', width: 100 ,align: 'center'},
        	{ display: '贷款用途', field: 'loanUse', width: 300,align: 'center'},
        	{ display: '分配时间', field: 'signDateStr', width: 120 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 100,
        	align: 'center',
        	buttons: [
				{
					display: '转申请',
					onClick: function(tr, data){
						doLoanApply(data);
					},
                    isDisabled: function(tr, data){
                        if(loanToApply) {
                            return false;
                        }
                        return true;
                    }
				},      
        	    {
            		display: '作废',
            		onClick: function(tr, data){
            			delCustomer(data);
            		},
                    isDisabled: function(tr, data){
                        if(loanPreApplyDel) {
                            return false;
                        }
                        return true;
                    }
        		}
            ]
        },
        extendCell: {
        	'customerName': function(value, row){
	   			var value = row.cols.customerName+"&nbsp;&nbsp;&nbsp;"+row.cols.sexCN+"&nbsp;&nbsp;&nbsp;"+row.cols.ageStr
	   			+"</br>"+row.cols.phoneNumber;
	   			return value;
	   	 	},
	   	 	'cardNumber': function(value, row){
	   			var value = row.cols.cardTypeCN+"</br>"+row.cols.cardNumber;
	   			return value;
	   	 	}
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
        setTimeout(function(){$("input[name='customerName']").focus();},100);
        setTimeout(function(){$("input[name='customerName']").blur();},100);
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
		searchList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshMarketCustomerGridList();
	});
});


function delCustomer(data){
	var url = '../marketCustomer/deleteCustMarketCustomer.html?marketCustomerId='+data.id;
	showConfirm({
        icon:'confirm',
        content:'您确定要作废吗？',
        ok:function(){
        	jQuery.ajax({
    			url: url,
    			type:'POST', 
    			dataType:'json',
                sync: false,
                success: function(result){
                	if(result.success){
                		showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                		refreshMarketCustomerGridList();
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

function doLoanApply(data){
    tabs.add({
        id : 'customeraddOrUpdateLoan' + data.id,
        name : 'customeraddOrUpdateLoan' + data.id,
        pid: tabs.getSelfId(window),
        title : '预申请-转申请',
        url : '../loan/getLoanTabs.html?module=apply&marketCustomerId='+data.id +'&random='+Math.random()*10000,
        lock : false
    });
}

function searchList() {
    var postJson = {};
    postJson['customerName'] = $('#customerName').val();
    postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
	$('#marketCustomerGrid').flexSearch(postJson);
}
// 刷新列表表
function refreshMarketCustomerGridList() {
	$('#marketCustomerGrid').flexReload();
}