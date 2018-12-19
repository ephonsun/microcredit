// 关闭当前页卡
function closeThisTab(){
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refreshList){
        win.refreshList();
    }
    tabs.close(tabs.getSelfId(window));
}

$(function(){

	var defaultShowPage = $("#defaultShowPage").val();
	if(defaultShowPage==null||defaultShowPage==""||defaultShowPage==undefined){
		defaultShowPage = 0;
	}
	var id = $("#id").val();
	if(id==null||id==""||id==undefined){
		id = "0";
	}

	 if(id=='0') {//
		$('#tabs').tabs({
			defaultShowPage: defaultShowPage,
			items: [
				{
					display: '基本信息',
					url:'../customer/getCustomerInsertPage.html',
					loadType: 'tab.click',
					loadReady: function (item, page) {
						includeJs("../customer/js/customer/customerInsert.js");
					}
				}
			]
		});
	}else{//
		$('#tabs').tabs({
			defaultShowPage: defaultShowPage,
			items: [
				{
					display: '基本信息',
					url:'../customer/getCustomerInsertPage.html?id='+id+"&flag="+$("#flag").val()+"&isHandOver="+$("#isHandOver").val(),
					loadType: 'tab.click',
					loadReady: function (item, page) {
						includeJs("../customer/js/customer/customerInsert.js");
					}
				},
				{
					display: '日程安排',
					url : '../schedule/getCustScheduleTabPage.html?customerId='+id+"&flag="+$("#flag").val(),
					loadType: 'tab.click',
					loadReady: function (item, page) {
					}
				},
				{
					display: '贷款信息',
					url : '../customer/getCustomerLoanListPage.html?id='+id,
					loadType: 'tab.click',
					loadReady: function (item, page) {
						includeJs("../customer/js/customer/customerLoanList.js");
					}
				}/*,
				{
					display: '客户意向',
					url : '../customer/getIntentProdListPage.html?id='+id,
					loadType: 'tab.click',
					loadReady: function (item, page) {
						includeJs("../customer/js/intentProd/intentProdList.js");
					}
				}*//*,
				{
					display: '资料信息',
					url : '../customer/getCreditFilePage.html?id='+id,
					loadType: 'tab.click',
					loadReady: function (item, page) {
						includeJs("../customer/js/creditFileList.js");
					}
				}*/
			]
		});
	}
    // 取消
    $('#btnCancel').click(function(){
        closeThisTab();
    });

});

var dom = null;
function showMap(e,id ,columnName, tableName, isShow) {
	dom = $(e).parent().prev();
    var title = isShow && isShow == '1' ? '查看定位' : '标记定位';
    var url = '../tagging/getTaggingMap.html?customerId=' + id + '&isShow='  + isShow + '&columnName='+ columnName + '&tableName='+ tableName + '&random='+Math.random()*10000;
    showDialog({
        id: 'loanTaggingMap',
        title: title,
        url: url,
        width: 920,
        height: 550,
        tabId: tabs.getSelfId(window)
    });
}

function setAddressValue(address) {
	var oldVal = $(dom).find('input').val();
	if (!oldVal){
        $(dom).find('input').val(address);
	}
    $(dom).next().find('.address-icon').attr('src', '../../core/imgs/icon/address-blue.png');
}