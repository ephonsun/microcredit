
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
        height: 500,
        url: '../intoCustApplyInfo/queryIntoCustApplyInfoList.html',
        usePage: true,
		multiSelect:true,
		rowIdProperty: 'id',
		fields: [ //custName,cardType,idCard,custPhone,useType,applyAmount
        	{ display: '客户姓名', field: 'custName', width: 120 ,align : 'center' },
            { display: '证件类型', field: 'cardType', width: 120 ,align : 'center' },
            { display: '证件号码', field: 'idCard', width: 200 ,align : 'center' },
        	{ display: '联系电话', field: 'custPhone', width: 120 ,align : 'center' },

            { display: '贷款用途', field: 'loanUserOptionName', width: 120 ,align : 'center' },
            { display: '贷款需求', field: 'applyAmount', width: 120 ,align : 'center' }
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
                // {
            		// display: '编辑',
            		// onClick: function(tr, data){
            		// 	updateCustomer(data);
            		// },
					// isDisabled: function(tr, data){
            		// 	if(customerEdit){
            		// 	    if(tr.cols.attributionManager.toString()=='0')
                 //            {
                 //                if ( tr.cols.attributionTeam.toString() ==userTeamId) {
                 //                    return false;
                 //                }
                 //            }else if ( tr.cols.attributionManager.toString()==userId){
                 //                return false;
                 //            }
                //
                 //        }
					// 	return true;
					// }
        		// },
				/*{
					display: '定位',
					onClick: function(tr, data){
						customerMap(data);
					}
				},*/
                // {
            		// display: '删除',
            		// onClick: function(tr, data){
            		// 	//deletePotentialCustomer(data);
            		// }
					// // ,isDisabled: function(tr, data){
                 //     //    if(customerDel) {
                 //     //        if(tr.cols.attributionManager.toString()=='0')
                 //     //        {
                 //     //            if ( tr.cols.attributionTeam.toString() ==userTeamId) {
                 //     //                return false;
                 //     //            }
                 //     //        }else if ( tr.cols.attributionManager.toString()==userId){
                 //     //            return false;
                 //     //        }
                 //     //    }
					// // 	return true;
					// // }
        		// }
            ]
        },
        onComplete : function(data) {

			$('#lblStatistics').text(data.total);
		}
    });

	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');

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



});

$('#btnSign').click(function () {
    var selectObj = $('#grid').selectedRows();

    var ids="";
    for(var i = 0; i<selectObj.length;i++){
        var k = selectObj[i].length-1;
        var id = str=selectObj[i][k].RowId.replace("flex","");
        ids = ids +id+","
    }
    if(ids==""){
        showConfirm({
            icon: 'warning',
            content: "请选择要分配的记录!"
        });
        return;
    }
    ids = ids.substring(0,ids.length-1);
    var url = '../intoCustApplyInfo/gotoGroupSignPage.html?applyIds='+ids;
    showDialog({
        id: 'customerGroupSign',
        title: '分配',
        url: url,
        width: 330,
        height: 210,
        tabId: tabs.getSelfId(window)
    });
});



function viewCustomer(data){

	tabs.add({
		id :'viewCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'viewCustomer'+data.id,
		title:'进件客户-查看',
		url : '../intoCustApplyInfo/getIntoCustApplyInfoPage.html?id='+data.id,
		lock:false
	});
}




function searchList() {

	var postJson = {};
	postJson['custName'] = $('#custName').val();
	postJson['idCard'] = $('#idCard').val();
	postJson['custPhone'] = $('#custPhone').val();
    postJson['loanUserOption'] = $('#loanUserOption').val();

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
