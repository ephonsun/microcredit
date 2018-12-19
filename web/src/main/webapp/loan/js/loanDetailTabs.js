
$(function(){

	var loanId = $("#loanId").val();
	if(loanId==null||loanId==""||loanId==undefined){
		loanId = "";
	}


	$('select').selectbox();

	if(loanId!=""){
		iniTabs(loanId);
		$('#tabs').show();
		$("#loanTypeId").prop("disabled", true);
		$("#btnLoanType").hide();
	}else{
		$('#tabs').hide();
	}

	$('#btnLoanType').click(function() {
        var nloanType = $("#loanTypeId").val();
        if (nloanType == '') {
            showConfirm({
                icon: 'succeed',
                content: '请选择贷款类型'
            });
        	return false;
		}

		iniTabs(loanId);
		//refreshLoanTemplate($('#loanTypeId').selectbox({}).val(),"applyTemplate","Apply");
		$("#loanTypeId").prop("disabled", true);
		$("#btnLoanType").hide();
		$('#tabs').show();
	});

});

function iniTabs(loanId){

	var defaultShowPage = $("#defaultShowPage").val();
	if(defaultShowPage==null||defaultShowPage==""||defaultShowPage==undefined){
		defaultShowPage = 0;
	}

	var loanProcessTabs = $("#loanProcessTabs").val();
	if(loanProcessTabs==null||loanProcessTabs==undefined){
		loanProcessTabs = "";
	}
	var loanTypeId = $("#loanTypeId_hide").val();
	if(loanTypeId==null||loanTypeId==undefined){
		loanTypeId = 0;
	}

	var items = [];


	var refuse = {
		display: '拒绝信息',
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};
	var loan = {
		display: '放贷信息',
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};
	var approval = {
		display: '审批信息',
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};
	var investigate = {
		display: '调查信息',
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};
	var apply = {
		display: '申请信息',
		//url:'../loan/getLoanApplyTab.html?loanId='+loanId+"&flag="+$("#flag").val()+"&loanTypeId="+loanTypeId,
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};
	var loanFile = {
		display: '贷款资料',
		url:'../loanFile/getLoanFilePage.html?loanId='+$("#loanTypeId").val() + '&random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanFile.js");
        }
	};
	var historyLog = {
		display: '历史操作',
		url:'../loan/getLoanApplyTab.html?random='+Math.random()*10000,
		loadType: 'tab.click',
		loadReady: function (item, page) {
			includeJs("../loan/js/loanApplyTab.js");
		}
	};

	//INVESTIGATE("investigate","调查", new int[] {}),
	//APPROVAL("approval","审批", new int[] {}),
	//LOAN("loan","放款", new int[] {}),
	//REFUSE("refuse","拒绝", new int[] {}),
	if(loanId=='') {//
		items.push(apply);
        items.push(loanFile);

    }else{//
        if(isContains(loanProcessTabs,"refuse")){
			items.push(refuse);
		}
		if(isContains(loanProcessTabs,"loan")){
			items.push(loan);
		}
		if(isContains(loanProcessTabs,"approval")){
			items.push(approval);
		}
		if(isContains(loanProcessTabs,"investigate")){
			items.push(investigate);
		}
		items.push(apply);
		items.push(loanFile);
		items.push(historyLog);
    }

	$('#tabs').tabs({
		defaultShowPage: defaultShowPage,
		items: items
	});

 }


function isContains(str, substr) {
	return new RegExp(substr).test(str);
}

function refreshLoanTemplate(loanTypeId,divId,precType) {

	$('#'+divId).empty();

	var flag = $("#flag").val();
	var loanId = $("#loanId").val();
	var url = '../loan/getLoanTemplate.html?loanTypeId='+loanTypeId+"&flag="+flag+"&loanId="+loanId+"&precType="+precType;
	jQuery.ajax({
		type: 'post',
		url: url,
		async:false,
		data: { },
		success: function (data) {
			$('#'+divId).append(data);
			layout.initForms();
		}
	});

}