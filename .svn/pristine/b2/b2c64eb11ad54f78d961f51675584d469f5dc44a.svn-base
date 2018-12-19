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
	$('#customerGrid').flexigrid({
        height: 280,
        url: '../customerBlack/queryCustomerBlackApproveListData.html',
        params:{'approveStatus':$('#approveStatus').val(),'createUser':$('#createUser').val()},
        fields: [
        	{ display: '客户信息', field: 'customerName', width: 150 ,align: 'center'},
        	{ display: '证件信息', field: 'cardNo', width: 200 ,align: 'center'},
        	{ display: '申请人', field: 'userName', width: 150 ,align: 'center'},
        	{ display: '申请时间', field: 'createDate', width: 100 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 130,
        	align: 'center',
        	buttons: [
        	    /*{
            		display: '详情',
            		onClick: function(tr, data){
            			viewCustomer(data);
            		},
                    isDisabled: function(tr, data){
                    	
                    	return false;
                    }
        		},*/
        		{
            		display: '移出黑名单',
            		onClick: function(tr, data){
            			doDel(data);
            		},
                    isDisabled: function(tr, data){
                    	
                    	return false;
                    }
        		}
            ]
        },
        extendCell: {
        	'cardNo': function(value, row){
	   			var value = row.cols.cardTypeCN+"&nbsp;&nbsp;&nbsp;"+row.cols.cardNo;
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
		refreshList();
	});
});


function doDel(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要把客户“'+data.cols.customerName+'”移出黑名单吗？',
        ok:function(){
        	jQuery.ajax({
    			url: '../customerBlack/deleteCustomerBlack.html',
    			type:'POST', 
    			dataType:'json',
                data: {"customerBlackId": data.id},
                sync: false,
                success: function(result){
                	if(result.success){
                		showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                		refreshList();
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

function viewCustomer(data){
	jQuery.ajax({
		url: '../customerBlack/findCustomer.html',
		type:'POST', 
		dataType:'json',
        data: {"customerName": data.cols.customerName,"cardType": data.cols.cardType,"cardNo": data.cols.cardNo},
        sync: false,
        success: function(result){
        	if(result==0){
        		showConfirm({
                    icon: 'warning',
                    content: "系统中不存在此客户信息"
                });
        	}else{
        		tabs.add({
        			id :'viewCustomer'+data.id,
        			pid:tabs.getSelfId(window),
        			name:'viewCustomer',
        			title:'客户-查看',
        			url : '../customer/getCustomerTabs.html?id='+result+"&flag="+1,
        			lock:false
        		});
        	}
        }
	});
	

}

function searchList() {
    var postJson = {};
    postJson['customerName'] = $('#customerName').val();
    postJson['userName'] = $('#userName').val();
    postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
	$('#customerGrid').flexSearch(postJson);
}
// 刷新列表表
function refreshList() {
	$('#customerGrid').flexReload();
}