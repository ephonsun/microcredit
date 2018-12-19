
var userId = "0";
var userTeamId="0";
var selectIsMy='0';
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
    userTeamId = $("#userTeamId").val();
	// 加载列表
	$('#grid').flexigrid({
        height: 280,
        url: '../custPotentialCustomers/queryPotentialCustomersListData.html',
        usePage: true,
		multiSelect:true,
		rowIdProperty: 'id',
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
            			if(customerEdit){
            			    if(tr.cols.attributionManager.toString()=='0')
                            {
                                if ( tr.cols.attributionTeam.toString() ==userTeamId) {
                                    return false;
                                }
                            }else if ( tr.cols.attributionManager.toString()==userId){
                                return false;
                            }

                        }
						return true;
					}
        		},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deletePotentialCustomer(data);
            		},
					isDisabled: function(tr, data){
                        if(customerDel) {
                            if(tr.cols.attributionManager.toString()=='0')
                            {
                                if ( tr.cols.attributionTeam.toString() ==userTeamId) {
                                    return false;
                                }
                            }else if ( tr.cols.attributionManager.toString()==userId){
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

    //潜在客户分配
    $("#btnAllot").click(function () {
        var data = $('#grid').getSelectedRows();
        if(data.datas.length == 0) {
            showConfirm({
                icon: "warning",
                content: "至少选择一个潜在客户"
            });
            return;
        }
        var ids = "";
        for(var i = 0; i<data.datas.length; i++){
            if (i == 0){
                ids = data.datas[i].id;
            }else{
                ids = ids + ','+  data.datas[i].id;
            }
        }
        var url = '../custPotentialCustomers/allotPotentialaCustPage.html?ids=' + ids;
        showDialog({
            id : 'allotPotentialaCust',
            title : '分配潜在客户',
            url : url,
            width : 500,
            height : 215,
            tabId : tabs.getSelfId(window)
        });
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
        $("#userName").attr('placeholder','姓名、电话或证件号码');
        if($('#selectFlag').is(':checked')) {
            selectIsMy = '1';
        }else{
            selectIsMy = '0';
        }
        setTimeout(function(){$("input[name='userName']").focus();},100);
        setTimeout(function(){$("input[name='userName']").blur();},100);
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
            selectIsMy = '1';
        }else{
            selectIsMy = '0';
        }
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
                            content: '记录删除失败，权限不足'
                        });
                        refreshList();
					}
                }
            });
        },
        cancel: function() {}
    });
}

//批量删除页面
$("#btnDel").click(function() {
    //2018年1月5日添加 是否选择客户校验，避免空删除操作
    var data = $("#grid").getSelectedRows();
    if(data.datas.length == 0){
        showConfirm({
            icon: 'warning',
            content: '至少选择一个客户'
        });
        return ;
    }
    showConfirm({
        icon: 'confirm',
        content: '您确认要删除选中项吗',
        ok: function() {
            var data = $('#grid').getSelectedRows();
            var ids = "";
            for(var i = 0; i<data.datas.length; i++){

                if (i == 0)
                ids = data.datas[i].id;
                else
                    ids = ids + ','+  data.datas[i].id;
            }
            jQuery.ajax({
                type : 'POST',
                url : '../custPotentialCustomers/deletePotentialCustomers.html',
                data : {'ids':ids},
                success : function(result) {
                    if ("SUCCESS" == result) {
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                        refreshList();
                    }else {
                        showConfirm({
                            icon: 'warning',
                            content: '记录删除失败，权限不足'

                        });

                    }

                }
            });
        },
        cancel: function() {}
    });
});
//全部删除页面
$("#btnDelAll").click(function() {
    showConfirm({
        icon: 'confirm',
        content: '您确认要删除您权限下所有记录吗？',
        ok: function() {
                var postJson = {};
                jQuery.ajax({
                    type : 'POST',
                    url : '../custPotentialCustomers/deletePotentialCustomersAll.html',
                    data : postJson,
                    success : function(result) {
                        searchList();
                    }
                });
            showConfirm({
                icon: 'succeed',
                content: '删除成功'
            });
        },
        cancel: function() {}
    });
});


//跳转到新建页面
$("#btnAdd").click(function() {
    tabs.add({
        id : 'addOrUpdateCustomer',
        name : 'addOrUpdateCustomer',
        pid: tabs.getSelfId(window),
        title : '潜在客户-新建',
        url : '../custPotentialCustomers/getPotentialCustomersTabs.html',
        lock : false
    });
});
//导入潜在客户
$("#btnImport").click(function() {
    //校验之前任务是否完成
    jQuery.ajax({
        type : 'POST',
        url : '../sysImportHistory/getImportHistoryIsComplated.html',
        data : {'progressCode':"potentialCustImport_"},
        success : function(result) {
            if ("SUCCESS" == result) {
                tabs.add({
                    id : 'importCustomer',
                    name : 'importCustomer',
                    pid: tabs.getSelfId(window),
                    title : '潜在客户-导入',
                    url : '../custPotentialCustomers/getPotentialCustPage.html',
                    lock : false
                });
            }else if(("error" == result)){
                showConfirm({
                    icon: 'warning',
                    content: '请等待当前模块导入完成,或在导入历史列表中查看导入进度'

                });

            }else{
                showConfirm({
                    icon: 'warning',
                    content: result

                });
            }

        }
    });
});

function searchList() {
    if($('#selectFlag').is(':checked')) {
        selectIsMy = '1';
    }else{
        selectIsMy = '0';
    }
	var postJson = {};
	postJson['userName'] = $('#userName').val();
	postJson['attributionManagerName'] = $('#attributionManagerName').val();
	postJson['selectMy'] = selectIsMy;
	postJson['loanIntention'] = $('#loanIntention').val();
    postJson['marketSuccess'] = $('#marketSuccess').val();
    postJson['isRemark'] = $('#isRemark').val();
    postJson['createDate'] = $('#txtStartDate').val();
    postJson['createDateEnd'] = $('#txtEndDate').val();
    postJson['intentionDate'] = $('#litStartDate').val();
    postJson['intentionDateEnd'] = $('#litEndDate').val();

	$('#grid').flexSearch(postJson);

}
// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}
// 加载操作时间
$('#txtStartDate').datepicker({
    maxDate: '#F{$dp.$D(\'txtEndDate\')}'
});
$('#txtEndDate').datepicker({
    minDate: '#F{$dp.$D(\'txtStartDate\')}',
    maxDate:new Date()
});
$('#litStartDate').datepicker({
    maxDate: '#F{$dp.$D(\'litEndDate\')}'
});
$('#litEndDate').datepicker({
    minDate: '#F{$dp.$D(\'litStartDate\')}',
    maxDate:new Date()
});
