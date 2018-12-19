
var userId = "0";
var allotFlag = false;

// 页面加载完成时...
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
	$('select').selectbox();

	userId = $("#userId").val();
    allotFlag= $("#allotFlag").val();
	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../customer/queryBasicInfoListData.html?selectMy=' + selectMy,
        usePage: true,
		//multiSelect:true,
		rowIdProperty: 'id',
		fields: [
        	{ display: '客户信息', field: 'customerInfo', width: 300 ,align : 'center' },
            { display: '证件信息', field: 'cardInfo', width: 180 ,align : 'center' },
        	{ display: '客户类型', field: 'customerLevelName', width: 100 ,align : 'center' },
        	{ display: '归属人', field: 'belongUserName', width: 100 ,align : 'center' }
		],
        action: {
        	display: '操作',
        	width: 150,
            align : 'center',
        	buttons: [
                {
                    display: '分配',
                    onClick: function(tr, data){
                        allotCustomer(data);
                    },
                    isDisabled:function(tr,data){
                        if (tr.cols.belongUserId.toString()==userId||allotFlag=='true') {
                            return false;
                        }
                        return true;
                    }
                },
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
            			if(customerEdit){
							if (tr.cols.belongUserId.toString()==userId) {
								return false;
							}
                        }
						return true;
					}
        		},
				/*{
					display: '定位',
					onClick: function(tr, data){
						customerMap(data);
					}
				},*/
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteCustomer(data);
            		},
					isDisabled: function(tr, data){
                        if(customerDel) {
                            if (tr.cols.belongUserId.toString() == userId) {
                                return false;
                            }
                        }
						return true;
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

        if($('#selectFlag').is(':checked')) {
            selectFlag = 'on';
        }else{
            selectFlag = '';
        }
        setTimeout(function(){$("input[name='customer']").focus();},100);
        setTimeout(function(){$("input[name='customer']").blur();},100);
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
            selectFlag = 'on';
		}else{
            selectFlag = '';
		}
	});

});

function updateCustomer(data){
	tabs.add({
		id :'updateCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'updateCustomer'+data.id,
		title:'客户-编辑',
		url : '../customer/getCustomerTabs.html?id='+data.id,
		lock:false
	});
}

function viewCustomer(data){
	tabs.add({
		id :'viewCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'viewCustomer'+data.id,
		title:'客户-查看',
		url : '../customer/getCustomerTabs.html?id='+data.id+"&flag="+1,
		lock:false
	});
}
//分配客户
function allotCustomer(data){
    var url = '../customer/allotCustomerPage.html?ids='+data.id+'&random='+Math.random()*10000;
    showDialog({
        id: 'allotCustomerPage',
        title: '分配客户',
        url: url,
        width: 330,
        height: 200,
        tabId: tabs.getSelfId(window)
    });
}

function deleteCustomer(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除客户“'+ data.cols.customerName+ '”吗？删除后将无法恢复。',
        ok:function(){
            var postJson = {};
            postJson['id'] = data.id;
            jQuery.ajax({
                type:'POST',
                url:'../customer/deleteCustomer.html',
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
                            content: '该客户办理过贷款业务无法删除'
                        });
					}
                }
            });
        },
        cancel: function() {}
    });
}


//跳转到新建页面
$("#btnShowMap").click(function() {
    var title = '查看地图';
    var url = '../tagging/getCustomerMapSearch.html?random='+Math.random()*10000;
    showDialog({
        id: 'customerMaps',
        title: title,
        url: url,
        width: 920,
        height: 550,
        tabId: tabs.getSelfId(window)
    });
});

//跳转到新建页面
$("#btnAdd").click(function() {
    tabs.add({
        id : 'addOrUpdateCustomer',
        name : 'addOrUpdateCustomer',
        pid: tabs.getSelfId(window),
        title : '客户-新建',
        url : '../customer/getCustomerTabs.html',
        lock : false
    });
});

function searchList() {
	var postJson = {};
	postJson['customerLevel'] = $('#customerLevel').val();
	postJson['customer'] = $('#customer').val();
	postJson['selectFlag'] = selectFlag;
	postJson['belongUserName'] = $('#belongUserName').val();

	$('#grid').flexSearch(postJson);

}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}
