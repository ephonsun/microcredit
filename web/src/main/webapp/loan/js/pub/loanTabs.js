var deleteIds = {};
$(function(){
    //行业分类
    $("#print_a").click(function(){
        var loanId = $("#loanId").val();
        getPrintPage(loanId, 'threeTable');
    });
    $("#print_b").click(function(){
        var loanId = $("#loanId").val();
        getPrintPage(loanId, 'info');
    });

});
/***********************************************申请*****************************************************/
var potentialId="0";
var customerId="0";

//保存申请信息
function saveApplyLoan(id, opType) {

    if (opType == 'Allot') {
        if (!checkRequired ()) {
            showConfirm({
                icon: 'warning',
                content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
            });
            return false;
        }
    }

    buttonDisable('#btnSaveApplyContine');
    buttonDisable('#btnSaveApply');
    var bool = banger.verify("#baseForm");
    if (!bool) {
        return false;
    }
    if (!checkCustomer()) {
        return;
    }
    // if (!checkIdCards()) {
    //     return;
    // }
    var postJson = getPostFieldsForTemplate('#applyTemplate_applyapply');
    var postJsonString = JSON.stringify(postJson);
    var deleteIdJson = JSON.stringify(deleteIds);

    potentialId=$("#potentialId").val();
    //customerId=$('#id').val();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanApply/saveLoanApply.html?potentialId='+potentialId + '&random='+Math.random()*10000,
        data: {
            "json": postJsonString,
            "deleteIds":deleteIdJson,
            "processType": opType,
            "id": id,
            "loanTypeId": loanTypeId,
            "applyId": applyId,
            "customerId":customerId
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if (opType == 'Allot') {
                    closeApplyTab();
                } else {
                    if (null != result.id && "null" != result.id && "" != result.id) {
                        closeApplyTab(result.id, 'apply');
                    }
                }
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause,
                    ok: function() {
                        if (result.newSave && null != result.id && "null" != result.id && "" != result.id) {
                            var url = '../loan/getLoanTabs.html?module=apply&showApply=1&loanId=' + result.id+'&random='+Math.random()*10000;
                            tabs.add({
                                id :'updateCustomerapply1' +result.id,
                                pid:tabs.getSelfId(window),
                                name:'updateCustomerapply1' +result.id,
                                title: '申请列表-详情',
                                url : url,
                                lock:false
                            });
                            closeApplyTab();
                        }
                    }
                });

            }
        }
    });
}
//根据证件类型+证件号码获得编辑页面
function getCustomerInfoEditPage(){
    var customerName = $('#co_LBIZ_PERSONAL_INFO').find('#customerName').val();
    var identifyType = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
    var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
    if (customerName != '' && customerName != null && customerName != undefined
        && identifyType != '' && identifyType != null && identifyType != undefined
        && identifyNum != '' && identifyNum != null && identifyNum != undefined) {
        if(loanId == '') {
            if(document.getElementById("LBIZ_PERSONAL_INFO")){
                getCustomerInfo("LBIZ_PERSONAL_INFO",customerName, identifyType,identifyNum);
            }
            if(document.getElementById("LBIZ_FAMILY_INFO")){
                getCustomerInfo("LBIZ_FAMILY_INFO",customerName,identifyType,identifyNum);
            }
            if(document.getElementById("LBIZ_SPOUSE_INFO")){
                getCustomerInfo("LBIZ_SPOUSE_INFO",customerName,identifyType,identifyNum);
            }
        } else {
            if(document.getElementById("LBIZ_PERSONAL_INFO")){
                getCustomerInfo("LBIZ_PERSONAL_INFO",customerName, identifyType,identifyNum, true);
            }
        }
    }
}

function getCustomerInfo(tableName,customerName, identifyType,identifyNum, updateId){
    jQuery.ajax({
        type: 'post',
        url: '../customer/getCustomerInfoPage.html',
        dataType:'json',
        data: {"customerName" : customerName,"identifyType": identifyType, "identifyNum": identifyNum, "tableName": tableName},
        success: function (result) {
            if(result && result.success){
                customerId = result.customerId;
                $.each(result.data, function (index,row) {
                    $("#" + row.key).val(row.value);
                    if (row.type == 'Select') {
                        $("#" + row.key).selectbox();
                    } else if (row.type == 'Date') {
                        var str1 = row.value.substring(0, 10) ;
                        $("#" + row.key).val(str1.toString());
                        $("#" + row.key).datepicker()
                    }
                })
                //三要素回填客户信息,并且校验客户编码
                $("#id").val(customerId);
                hideAllCtrlForm();
            } else {
                customerId = '';
                if (updateId) {

                }
            }
        }
    });
}

function checkCustomer(){
    var bool = true;
    //证件号码规则
    var identifyId = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
    var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
    var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
    var reg2 = /^\w+$/;  //其他证件类型校验规则
    if(identifyId=="1"){
        if(reg.test(identifyNum) === false){
            showConfirm({
                icon: 'warning',
                content:  '证件号码不合法'
            });
            bool = false;
        }
    } else {
        if(reg2.test(identifyNum)==false) {
            showConfirm({
                icon: 'warning',
                content:  '证件号码不合法'
            });
            bool = false;
        }
    }
    return bool;
}

function getSexAndAge() {
    var customerSex = $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').val();
    var customerAge = $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val();
    var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
    var identifyType = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
    var birthday = $('#co_LBIZ_PERSONAL_INFO').find('#customerBirthday').val();
    if (identifyType == '1') {
        if (identifyNum.length == 15) {
            //if (!customerSex) {
            var usex = identifyNum.substring(14, 15);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').selectbox({'value': sex });
            //}
            //if (!customerAge) {
            var age = 0;
            var date = new Date();
            var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
            var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
            var dateStr = date.getFullYear() + '' + month + '' + day;
            var yearn = dateStr.substring(0, 4);// 得到年份
            var yuen = dateStr.substring(4, 8);// 得到月份
            var year = "19" + identifyNum.substring(6, 8);// 得到年份
            var yue = identifyNum.substring(8, 12);// 得到月份
            if (yuen > yue) {
                age = yearn - year + 1;
            } else {
                age = yearn - year;
            }

            $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val(age);
            // }

            //生日
            var birthday="19"+identifyNum.substring(6, 8) + "-" + identifyNum.substring(8, 10) + "-" + identifyNum.substring(10, 12);
            $('#co_LBIZ_PERSONAL_INFO').find('#customerBirthday').val(birthday);
        } else if (identifyNum.length == 18){
            //if (!customerSex) {
            var usex = identifyNum.substring(16, 17);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').selectbox({'value': sex });
            // }
            //if (!customerAge) {
            var age = 0;
            var date = new Date();
            var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
            var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
            var dateStr = date.getFullYear() + '' + month + '' + day;
            var yearn = dateStr.substring(0, 4);// 得到年份
            var yuen = dateStr.substring(4, 8);// 得到月份
            var year = identifyNum.substring(6, 10);// 得到年份
            var yue = identifyNum.substring(10, 14);// 得到月份
            if (yuen > yue) {
                age = yearn - year + 1;
            } else {
                age = yearn - year;
            }
            $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val(age);

            //生日
            var birthday=identifyNum.substring(6, 10) + "-" + identifyNum.substring(10, 12) + "-" + identifyNum.substring(12, 14);
            $('#co_LBIZ_PERSONAL_INFO').find('#customerBirthday').val(birthday);
        }
    }

}


function applyInfoSign(loanId) {
    var url = '../loanAllot/gotoAllotSignPage.html?ids='+loanId+'&random='+Math.random()*10000;
    showDialog({
        id: 'loanAllotSign',
        title: '分配贷款',
        url: url,
        width: 330,
        height: 200,
        tabId: tabs.getSelfId(window)
    });
}


// 调查提交审批
function investigateSubmit(id) {
    if (!checkRequired ()) {
        showConfirm({
            icon: 'warning',
            content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
        });
        return false;
    }

    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanInvestigate/saveApproveInfo.html',
        data: {"loanId": id },
        async: false,
        success: function (result) {
            if (result.success) {
                if (result.random) {
                    var postJsonString = JSON.stringify(result.reviews);
                    showConfirm({
                        icon: 'confirm',
                        content: '您确定要提交审批吗？',
                        ok: function () {
                            jQuery.ajax({
                                type : 'post',
                                dataType:'json',
                                url : '../loanApproval/saveExamine.html',
                                data : {"loanId":id, "processId" : result.processId, "flowId" : result.flowId, "paramId" : result.paramId, "reviews": postJsonString},
                                success : function(result) {
                                    if(result.success){
                                        showConfirm({
                                            icon: 'succeed',
                                            content: result.cause
                                        });
                                        closeApplyTab();
                                    }else{
                                        showConfirm({
                                            icon: 'warning',
                                            content: result.cause
                                        });
                                    }
                                }
                            });
                        },
                        cancel: function () {
                        }
                    });
                } else {
                    var url = '../loanApproval/selectUserForward.html?loanId='+id ;
                    showDialog({
                        id: 'submitLoanApproval',
                        title: '提交审批',
                        url: url,
                        width: 400,
                        height: 300,
                        tabId: tabs.getSelfId(window)
                    });
                }
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
// 调查提交保存
function investigateSave(id) {
    var bool = banger.verify("#baseForm");
    if (!bool) {
        return false;
    }
    if (!checkCustomer()) {
        return;
    }
    //如果是担保方式必须要有担保人
    var assureMeans2 = $('#LBIZ_SURVEY_RESULT #assureMeans2').val();
    var assureMeans3 = $('#LBIZ_SURVEY_RESULT #assureMeans3').val();
    if(assureMeans2=='02'||assureMeans3=='02'){
        var isGuarantor = $('#isGuarantor').val();
        if(isGuarantor!='01'){
                showConfirm({
                    icon: 'warning',
                    content: '担保方式为担保时，必须添加担保人信息!'
                });
                return false;
        }
    }
    var guaranteeMode = $('#LBIZ_SURVEY_RESULT #guaranteeMode').val();
    if(guaranteeMode=='03'){
        var isCollateral = $('#isCollateral').val();
        if(isCollateral!='01'){
            showConfirm({
                icon: 'warning',
                content: '担保方式为抵押时，必须添抵质押物!'
            });
            return false;
        }
        var arr = $('#LBIZ_PLEDGE_ITEM #pledgeAmount');
        var value = parseFloat(0);
        for(var i=0;i<arr.length;i++){
            value += parseFloat($(arr[i]).val());
        }
        if($('#LBIZ_SURVEY_RESULT #proposalAmount').val()>parseFloat(value)){
            showConfirm({
                icon: 'warning',
                content: '抵质押金额总额不能小于建议金额'
            });
            return false;
        }
        //借款人
        var loanName = $("input[name='field.customerName']").val();
        var arr2 = $('#LBIZ_PLEDGE_ITEM #pledgeOwner');
        for(var i=0;i<arr2.length;i++){
            if(arr2[i]&&loanName!=$(arr2[i]).val()){
                //借款人做了抵押人 判断是否添加为担保人 不能添加为担保人
                //抵押人一定是担保人（去除借款人的情况）
                //担保人
                var arr3 = $('#LBIZ_LOAN_GUARANTEE #fullName');
                var flag = true;
                for(var j = 0 ;j<arr3.length;j++){
                    if(arr3[j]&&$(arr2[i]).val()==$(arr3[j]).val()){
                        flag = false;
                    }
                }
                if(flag){
                    showConfirm({
                        icon: 'warning',
                        content: '抵押人必须添加为担保人，请在担保人添加后保存！'
                    });
                    return false;
                }
            }
        }
    }
    var postJson = getPostFieldsForTemplate('#applyTemplate_investigateinvestigate');
    var postJsonString = JSON.stringify(postJson);
    var deleteIdJson = JSON.stringify(deleteIds);
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanInvestigate/saveLoanApply.html',
        data: {
            "json": postJsonString,
            "deleteIds":deleteIdJson,
            "id": id,
            "loanTypeId": loanTypeId
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if (null != result.value && "null" != result.value && "" != result.value) {
                    closeApplyTab(result.value, 'investigate');
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        }
    });
}

//判断必填项是否完整
function checkRequired () {
    var bool = true;
    $("#baseForm").find("label:visible.ui-star").each(function () {
        var isnull = $(this).attr("isnull");
        if (isnull == 'true'){
            bool = false;
            $(this).addClass("ui-color-red");
        }
    });
    return bool;
}
//编辑跳转
function editApply(loanId, module) {
    closeApplyTab(loanId, module, true);
}

function closeInvestigate() {
    closeApplyTab();
}

//关闭页卡
function closeApplyTab(id, module, edit) {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if (id) {
        if (edit) {
            if( win && win.editEdit){
                win.editEdit(id,module);
            }
        } else {
            if( win && win.editShow){
                win.editShow(id,module);
            }
        }
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    if( win && win.refreshMarketCustomerGridList){
        win.refreshMarketCustomerGridList();
    }
    tabs.close(tabs.getSelfId(window));
}
function refuseApply(loanId, module){
    var url = '../loan/gotoRefusePage.html?loanId='+loanId+'&module='+module+'&random='+Math.random()*10000;
    showDialog({
        id: 'refuseLoanApply',
        title: '拒绝贷款申请',
        url: url,
        width: 500,
        height: 270,
        tabId: tabs.getSelfId(window)
    });
}
function backApplyInfo(loanId, module){
    var url = '../loan/backApplyInfo.html?loanId='+loanId + '&module=' + module+'&random='+Math.random()*10000;
    showDialog({
        id: 'backLoanApply',
        title: '回退贷款信息',
        url: url,
        width: 330,
        height: 145,
        tabId: tabs.getSelfId(window)
    });
}
function showModel(){
    var url='../modelManagement/getShowPage.html?projectId=1';
    showDialog({
        id: 'pageShowHandle',
        title: '参考利率',
        url: url,
        width: 317,
        height: 'auto',
        tabId: tabs.getSelfId(window)
    });
}
function showPlanInfo(module, fieldFlag) {
    var repaymentMode, proposalAmount, proposalLimit, proposalRatio, date='',ratioDate='',loanEndDate='';
    // var bool = banger.verify("#baseForm");
    // if (!bool) return;
    if (fieldFlag == '0') {
        if ('clearApproval' == module || module == 'approval' ) {
            repaymentMode = $("#approvalBaseFormApproval #repaymentMode").val();
            proposalAmount = $("#approvalBaseFormApproval #resultAmount").val();
            proposalLimit = $("#approvalBaseFormApproval #resultLimit").val();
            proposalRatio = $("#approvalBaseFormApproval #resultRatio").val();
        } else if ('clear' == module || 'investigate' == module) {
            repaymentMode = $("#LBIZ_SURVEY_RESULT #repaymentMode").val();
            proposalAmount = $("#LBIZ_SURVEY_RESULT #proposalAmount").val();
            proposalLimit = $("#LBIZ_SURVEY_RESULT #proposalLimit").val();
            proposalRatio = $("#LBIZ_SURVEY_RESULT #proposalRatio").val();
        } else {
            repaymentMode = $("#LBIZ_LOAN_GRANT #loanBackMode").val();
            proposalAmount = $("#LBIZ_LOAN_GRANT #iouAmount").val();
            proposalLimit = $("#LBIZ_LOAN_GRANT #loanLimit").val();
            proposalRatio = $("#LBIZ_LOAN_GRANT #loanRatio").val();
            ratioDate = $("#LBIZ_LOAN_GRANT #loanRatioDate").val();
            loanEndDate = $("#LBIZ_LOAN_GRANT #loanEndDate").val();
            date = $("#LBIZ_LOAN_GRANT #loanBackDate").val();
        }
    } else {
        if ('clear' == module || 'investigate' == module) {
            repaymentMode = $("#repay-repaymentMode").text();
            proposalAmount = $("#repay-proposalAmount").text();
            proposalLimit = $("#repay-proposalLimit").text();
            proposalRatio = $("#repay-proposalRatio").text();
        } else {
            repaymentMode = $("#repay-loanBackMode").text();
            proposalAmount = $("#repay-loanAmount").text();
            proposalLimit = $("#repay-loanLimit").text();
            proposalRatio = $("#repay-loanRatio").text();
            ratioDate = $("#repay-ratioDate").text();
            loanEndDate = $("#repay-loanEndDate").text();
            date = $("#repay-loanBackDate").text();
        }
    }
    if ('clearApproval' == module || module == 'approval' ) {
        if (!repaymentMode) repaymentMode = '';
        if (!proposalAmount) proposalAmount = '';
        if (!proposalLimit) proposalLimit = '';
        if (!proposalRatio) proposalRatio = '';
        openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module,fieldFlag,ratioDate,loanEndDate, date);
    } else {
        if (proposalAmount && proposalLimit && repaymentMode) {
            if (repaymentMode == '等额本金' || repaymentMode == '1') repaymentMode = '1';
            else if (repaymentMode == '等额本息' || repaymentMode == '2') repaymentMode = '2';
            else if (repaymentMode == '按月还息到期还本' || repaymentMode == '3') repaymentMode = '3';
            else if (repaymentMode == '一次性还本付息' || repaymentMode == '4') repaymentMode = '4';
            else repaymentMode = '5';

            if ('clear' != module && 'investigate' != module && module != 'approval' ) {
                if (!date || !proposalRatio || !ratioDate) {
                    showConfirm({
                        icon: 'warning',
                        content: '信息不全，请补全后再试！'
                    });
                    return false;
                }
            }
            if (repaymentMode == '1' || repaymentMode == '2') {
                if (!proposalRatio && repaymentMode == '1' && ('clear' == module || 'investigate' == module || module == 'approval') ) {
                    proposalRatio = "0.1";
                }
                if (proposalRatio) {
                    openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module,fieldFlag,ratioDate,loanEndDate, date);
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: '信息不全，请补全后再试！'
                    });
                }
            } else {
                openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module,fieldFlag,ratioDate,loanEndDate, date);
            }
        } else {
            showConfirm({
                icon: 'warning',
                content: '信息不全，请补全后再试！'
            });
        }
    }

}
function openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module, fieldFlag,ratioDate,loanEndDate, date) {
    var loanId = $('#loanId').val();
    var url='../loanRepayPlanInfo/getPlanPage.html?loanId=' + loanId + '&repaymentMode=' + repaymentMode + '&proposalAmount=' + proposalAmount  + '&proposalLimit=' + proposalLimit
        + '&proposalRatio=' + proposalRatio + '&fieldFlag=' + fieldFlag + '&module=' + module + '&ratioDate=' + ratioDate  + '&loanEndDate=' + loanEndDate + '&date=' + date;
    showDialog({
        id: 'showPlanPage',
        title: '还款计划',
        url: url,
        width: 700,
        height: 500,
        tabId: tabs.getSelfId(window)
    });
}
//合同保存
function contractSave(id,loanTypeId){
    if(loanTypeId==''){
        showConfirm({
            icon: 'warning',
            content: '请确定选择合同类型'
        });
        return false;
    }

    var bool = banger.verify("#baseForm");
    if (!bool) {
        return false;
    }
    var accountNum = $("#LOAN_CONTRACT #accountNum").val();
    if(accountNum){
        if(accountNum.substr(0, 3)!='800'){
            showConfirm({
                icon: 'warning',
                content: '还款账号应已800开头,请核对是否正确!'
            });
            return false;
        }
    }
    var cusId = $('#queryCusId').val();

    var postJson = getPostFieldsForTemplate('#applyTemplate_contractcontract');
    var postJsonString = JSON.stringify(postJson);
    var loanContractBegin =$('#loanContractBegin').val();
    var loanContractEnd = $('#loanContractEnd').val();
    //还款帐号 还款账户开户行 入账账号
    var accountNum = $('#accountNum').val();
    var accountBank = $('#accountBank').val();
    var enterAccount = $('#enterAccount').val();
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../contract/loanContractSave.html',
        data: {
            "json": postJsonString,
            "id": id,
            "loanTypeId":loanTypeId,
            "cusId":cusId,
            "loanContractBegin":loanContractBegin,
            "loanContractEnd":loanContractEnd,
            "accountNum":accountNum,
            "accountBank":accountBank,
            "enterAccount":enterAccount
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                var win = tabs.getIframeWindow("updateCustomercontract"+id);
                if(win)
                    //win.location.reload(true);
                    closeApplyTab(result.value, 'contract',false);
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
//放款保存
function loanMoneySave(id){
    var flag = true;
    var postJson = getPostFieldsForTemplate('#applyTemplate_loanloan');
    var postJsonString = JSON.stringify(postJson);
    var deleteIdJson = JSON.stringify(deleteIds);
    //放款金额校验
    flag = checkIouAmount();
    if(flag){
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../loanLoanMoney/loanMoneySave.html',
            data: {
                "deleteIds":deleteIdJson,
                "json": postJsonString,
                "id": id
            },
            async: false,
            success: function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                    var win = tabs.getIframeWindow("updateCustomerloan"+id);
                    if(win)
                        win.location.reload(true);
                    // closeApplyTab();
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    }
}
//放款校验
function checkIouAmount(){
    var loanAmount = document.getElementById("loanAmount").value;
    var iouAmount = document.getElementById("iouAmount").value;
    //放款金额不能大于合同可用余额
    if(parseFloat(loanAmount)<parseFloat(iouAmount)){
        showConfirm({
            icon: 'warning',
            content: '放款金额不能大于合同可用余额'
        });
        return false;
    }

    //放款利率不能小于决议利率
    var loanRatio = $('#LBIZ_LOAN_GRANT #loanRatio').val();
    var approvalRatio = $('#approvalRatio').val();
    if(parseFloat(loanRatio)<parseFloat(approvalRatio)){
        showConfirm({
            icon: 'warning',
            content: '放款利率不能小于决议利率'
        });
        return false;
    }
    return true;
}
//合同提交
function contractSubmit(loanId){
    if (!checkRequired ()) {
        showConfirm({
            icon: 'warning',
            content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
        });
        return false;
    }
    //加遮罩层
    var url = '../contract/getContractCheckDia.html?loanId='+loanId;
    showDialog({
        id: 'contractCheckDia'+loanId,
        title: '合同提交',
        url: url,
        width: 350,
        height: 200,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            //success = data.success;
            if (data.success) {
                //跳出签订人登录页面
                var url = '../contract/getContractSubmitPage.html?loanId='+loanId+'&random='+Math.random()*10000;
                showDialog({
                    id: 'contractSubmit',
                    title: '签订分配',
                    url: url,
                    width: 350,
                    height: 200,
                    tabId: tabs.getSelfId(window)
                });
            }
        }
    });


    //核对客户号信息
    //jQuery.ajax({
    //    type: 'post',
    //    dataType: 'json',
    //    url: '../contract/checkCusInfo.html',
    //    data: {
    //        "loanId": loanId
    //    },
    //    async: false,
    //    success: function (result) {
    //
    //    }
    //});




}
//合同驳回
function contractGiveBack(id){
    var url = '../contract/contractGiveBack.html?id='+id+'&random='+Math.random()*10000;
    showConfirm({
        icon: 'confirm',
        content: '您确定要驳回吗？',
        ok: function () {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : url,
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        closeApplyTab();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });

}
//合同注销
function contractCancel(id,contCancelShow){
    var url = '../contract/contractCancel.html?id='+id+'&random='+Math.random()*10000;
    var content = '合同撤销该笔贷款会退到调查阶段，确定要合同撤销吗？';
    if(contCancelShow=='true'){
        content = '确定要注销合同？';
    }
    showConfirm({
        icon: 'confirm',
        content: content,
        ok: function () {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : url,
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        closeApplyTab();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });

}
//合同注销后申请贷款
function contractLoanApply(id){
    var url = '../contract/contractLoanApply.html?id='+id+'&random='+Math.random()*10000;
    showConfirm({
        icon: 'confirm',
        content: '您确定要重新申请贷款吗?',
        ok: function () {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : url,
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        closeApplyTab();
                        var url  = '../loan/getLoanTabs.html?module=investigate&loanId='+result.value+'&showApply=&random='+Math.random()*10000;
                        tabs.add({
                            id :'contractLoanApply' +id,
                            pid:tabs.getSelfId(window),
                            name:'contractLoanApply' +id,
                            title: '调查列表-详情',
                            url : url,
                            lock:false
                        });
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });

}
//签订提交
function contractSignSubmit(loanId){
    //var url = '../contract/contractSignSubmit.html?loanId='+id+'&random='+Math.random()*10000;
    showConfirm({
        icon: 'confirm',
        content: '您确定要签订合同吗?',
        ok: function () {
            //跳出loading页面
            var url = '../contract/getContractSignCheckPage.html?loanId='+loanId;
            showDialog({
                id: 'contractSignCheckPage'+loanId,
                title: '合同签订',
                url: url,
                width: 350,
                height: 200,
                tabId: tabs.getSelfId(window),
                pid:tabs.getSelfPid(window)
            });
        },
        cancel: function () {
        }
    });
}
//签订退回
function contractSignBack(id){
    var url = '../contract/contractSignBack.html?loanId='+id+'&random='+Math.random()*10000;
    showConfirm({
        icon: 'confirm',
        content: '您确定要退回签订吗?',
        ok: function () {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : url,
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        closeApplyTab();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });
}

//选择开户行
function selectAccountBank(){
    var url = '../contract/selectAccountBank.html?random='+Math.random()*10000;
    showDialog({
        id: 'selectAccountBank',
        title: '开户行选择页面',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            $('#accountBank').val(data.coffer);
            $('#bankNo').val(data.bankNo);
        }
    });
}
function selectAccountBankLoan(_this){
    var url = '../contract/selectAccountBank.html?random='+Math.random()*10000;
    showDialog({
        id: 'selectAccountBank',
        title: '开户行选择页面',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            var parent = $(_this).parents('table');
            parent.find("#counterpartyBankName").val(data.coffer);
            parent.find("#counterpartyBankNumber").val(data.bankNo);
            // $('#accountBank').val(data.coffer);
            // $('#bankNo').val(data.bankNo);
        }
    });
}
//贷款投向
function selectLoanOrientation(){
    var url = '../contract/selectLoanOrientation.html?random='+Math.random()*10000;
    showDialog({
        id: 'selectLoanOrientation',
        title: '贷款投向选择页面',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            $('#loanOrientation').val(data.orientationId);
            $('#orientationName').val(data.orientationName);
        }
    });
}

// 放款
function loanMoney(id) {
    var postJson = getPostFieldsForTemplate('#applyTemplate_loanloan');
    var postJsonString = JSON.stringify(postJson);
    if (!checkRequired ()) {
        showConfirm({
            icon: 'warning',
            content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
        });
        return false;
    }
    //判断抵质押物 有没有补录


    var sumAmount=$('#LBIZ_TRUSTED_PAYMENY #paymentAmount');
    var iouAmount=$('#iouAmount').val();
    var loanMode = $('#loanMode').val();
    var sum=0;
    for(var i=0;i<sumAmount.length;i++){
        sum += parseFloat(sumAmount.get(i).defaultValue);
    }
    if(iouAmount!=sum && "02"==loanMode){
        showConfirm({
            icon: 'warning',
            content: '授权支付累加金额需要等于放款金额'
        });
        return false;
    }

    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanLoanMoney/saveLoanApply.html',
        data: {
            "json": postJsonString,
            "id": id
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                var win = tabs.getIframeWindow("updateCustomerloan"+id);
                if(win)
                    win.location.reload(true);
                closeApplyTab();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
//放款驳回
function loanGiveBack(data, module){
    var url = '../loan/loanGiveBack.html?loanId='+data + '&module=' + module+'&random='+Math.random()*10000;
    showConfirm({
        icon: 'confirm',
        content: '您确定要驳回吗？',
        ok: function () {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : url,
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        closeApplyTab();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });
}


//授权确认
function authorizationConfirm(loanId){
    showConfirm({
        icon: 'confirm',
        content: '您确定要确认授权吗?',
        ok: function () {
            //跳出loading页面
            var url = '../loanLoanMoney/getAuthorizationCheckPage.html?loanId='+loanId;
            showDialog({
                id: 'authorizationCheckPage'+loanId,
                title: '授权确认同步',
                url: url,
                width: 350,
                height: 200,
                tabId: tabs.getSelfId(window),
                pid:tabs.getSelfPid(window)
            });
            // closeApplyTab();
        },
        cancel: function () {
        }
    });
}

//授权取消
function authorizationCancel(loanId){
    showConfirm({
        icon: 'confirm',
        content: '您确定要授权取消吗?',
        ok: function () {
            //跳出loading页面
            var url = '../loanLoanMoney/getAuthorizationCancelCheckPage.html?loanId='+loanId;
            showDialog({
                id: 'authorizationCancelCheckPage'+loanId,
                title: '授权取消同步',
                url: url,
                width: 350,
                height: 200,
                tabId: tabs.getSelfId(window),
                pid:tabs.getSelfPid(window)
            });
            // closeApplyTab();
        },
        cancel: function () {
        }
    });
}
//更改受托支付同步状态
function trustedPaymentInfoSync(loanId){
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanLoanMoney/trustedPaymentInfoSync.html',
        data: {
            "loanId": loanId
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                //closeApplyTab();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

//授款阶段 退回
function authorizationBack(id){
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanLoanMoney/authorizationBack.html',
        data: {
            "id": id
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeApplyTab();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
//根据卡号/存款帐号查询客户信息- 99QRY00400
function queryCustomerInfo(data){
    var counterpartyAccount = $(data).parent().find("#counterpartyAccount").val();
    var isBankAccount = $(data).parents('table').find('#isBankAccount').val();
    if(counterpartyAccount != ''&& isBankAccount == "01"){
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../loanLoanMoney/checkCustomerInfoData.html',
            data: {
                accNo:counterpartyAccount,
                loanId:loanId
            },
            async: false,
            success: function (result) {
                if(result.success== true){
                    if(result.cus_name != ''){$(data).parents('table').find('#counterpartyName').val(result.cus_name)};
                    if (result.brch_name != '') {$(data).parents('table').find('#counterpartyBankName').val(result.brch_name)};
                    if (result.branch != '') {$(data).parents('table').find('#counterpartyBankNumber').val(result.branch)};
                }else {
                    if(result.code == "-1"){
                        showConfirm({
                            icon: 'warning',
                            content: result.msg
                        });
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: "未查到对应账号的信息"
                        });
                    }
                }
                }
        });
    }
}


// 导出合同
function exportContract(loanId,loanTypeId) {
    //var bool = banger.verify("#baseForm");
    //if (!bool) {
    //    return false;
    //}
    var url = '../contract/getExportContractPage.html?loanId=' + loanId +"&loanTypeId="+ loanTypeId +'&random='+Math.random()*10000;
    showDialog({
        id: 'exportContractPage',
        title: '导出合同',
        url: url,
        width: 400,
        height: 400,
        tabId: tabs.getSelfId(window)
    });
}


var dom = null;
function showMap(e, id, columnName, tableName, isShow) {
    dom = $(e).parent().prev();
    var lbizId = 0;
    var templateType = $(e).parents('.template').find(".templateType").val();
    if (templateType && templateType == 'list')
        lbizId = $(e).parents('table').find("input[name='field.ID']").val();
    var title = isShow && isShow == '1' ? '查看定位' : '标记定位';
    var url = '../tagging/getTaggingMap.html?loanId=' + id + '&isShow='  + isShow + '&columnName='+ columnName + '&tableName='+ tableName + '&lbizId='+ lbizId + '&random='+Math.random()*10000;
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

function getPrintPage(loanId, type){
    var url = '../loanLoanMoney/printLoan.html?type='+type+'&loanId=' +loanId;
    var title = type == 'info' ? '打印调查报告' : '打印三表信息';
    showDialog({
        id: 'getPrintPage' + type,
        title: title,
        url: url,
        width: 800,
        height: 600,
        tabId: tabs.getSelfId(window)
    });
}
function getSpouseSexAndAge() {
    var customerSex = $('#LBIZ_SPOUSE_INFO').find('#spouseSex').val();
    var customerAge = $('#LBIZ_SPOUSE_INFO').find('#spouseAge').val();
    var identifyNum = $('#LBIZ_SPOUSE_INFO').find('#spouseIdentifyNum').val();
    var identifyType = $('#LBIZ_SPOUSE_INFO').find('#spouseIdentifyType').val();
    //var birthday = $('#LBIZ_SPOUSE_INFO').find('#customerBirthday').val();
    if (identifyType == '1') {
        if (identifyNum.length == 15) {
            //if (!customerSex) {
            var usex = identifyNum.substring(14, 15);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#LBIZ_SPOUSE_INFO').find('#spouseSex').selectbox({'value': sex });
            //}
            //if (!customerAge) {
            var age = 0;
            var date = new Date();
            var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
            var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
            var dateStr = date.getFullYear() + '' + month + '' + day;
            var yearn = dateStr.substring(0, 4);// 得到年份
            var yuen = dateStr.substring(4, 8);// 得到月份
            var year = "19" + identifyNum.substring(6, 8);// 得到年份
            var yue = identifyNum.substring(8, 12);// 得到月份
            if (yuen > yue) {
                age = yearn - year + 1;
            } else {
                age = yearn - year;
            }

            $('#LBIZ_SPOUSE_INFO').find('#spouseAge').val(age);
            // }

            //生日
            //var birthday="19"+identifyNum.substring(6, 8) + "-" + identifyNum.substring(8, 10) + "-" + identifyNum.substring(10, 12);
            //$('#LBIZ_SPOUSE_INFO').find('#customerBirthday').val(birthday);
        } else if (identifyNum.length == 18){
            //if (!customerSex) {
            var usex = identifyNum.substring(16, 17);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#LBIZ_SPOUSE_INFO').find('#spouseSex').selectbox({'value': sex });
            // }
            //if (!customerAge) {
            var age = 0;
            var date = new Date();
            var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
            var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
            var dateStr = date.getFullYear() + '' + month + '' + day;
            var yearn = dateStr.substring(0, 4);// 得到年份
            var yuen = dateStr.substring(4, 8);// 得到月份
            var year = identifyNum.substring(6, 10);// 得到年份
            var yue = identifyNum.substring(10, 14);// 得到月份
            if (yuen > yue) {
                age = yearn - year + 1;
            } else {
                age = yearn - year;
            }
            $('#LBIZ_SPOUSE_INFO').find('#spouseAge').val(age);

            //生日
            //var birthday=identifyNum.substring(6, 10) + "-" + identifyNum.substring(10, 12) + "-" + identifyNum.substring(12, 14);
            //$('#LBIZ_SPOUSE_INFO').find('#customerBirthday').val(birthday);
        }
    }

}



//入库/出库
function operationCollateral(id,type) {
    var content = "";
    if (type==1){
        content = '您确定要进行入库操作吗?';
    }else if (type==2){
        content = '您确定要进行出库操作吗?';
    }
    showConfirm({
        icon:'confirm',
        content:content,
        ok:function(){
            jQuery.ajax({
                url: '../contract/operationCollateral.html',
                type:'POST',
                dataType:'json',
                data: {"id": id,"type":type},
                sync: false,
                success: function(result){
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
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


//补录信息抵质押物信息
function pledgPage(id){
    var url = '../loan/getPledgEidtPage.html?id='+id+'&random='+Math.random()*10000;
    showDialog({
        id: 'getPledgEidtPage',
        title: '补录信息',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window)
    });
}

//添加共有人信息
function queryCommPeoInfoList(id,loanId,type){
    var url = '../loan/queryCommPeoInfoList.html?id='+id+'&loanId='+loanId+'&precType='+type+'&random='+Math.random()*10000;
    showDialog({
        id: 'queryCommPeoInfoListPage',
        title: '添加共有人信息列表',
        url: url,
        width: 800,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window)
    });
}

function changePlegeJe(data) {
    var currentValue,pledgeAmount,pledgeRate;
    $($("input",data.parentElement.parentElement.parentElement.children)).each(function (i,e) {
        if (e.id=="currentValue"){
            currentValue = e;
        }else  if (e.id=="pledgeRate"){
            pledgeRate=e;
        }else  if (e.id=="pledgeAmount"){
            pledgeAmount=e;
        }
    });

    if (data.id=="currentValue"){
        //估值发生变化  修改 抵押金额
        $(pledgeAmount).val(currentValue.value*pledgeRate.value/100);
    }else if (data.id=="pledgeRate"){
        //抵押率发生变化  修改 抵押金额
        $(pledgeAmount).val(currentValue.value*pledgeRate.value/100);
    }else if (data.id=="pledgeAmount"){
        //抵押金额编号 抵押率变化
        $(pledgeRate).val((pledgeAmount.value*100/currentValue.value).toFixed(2));
    }
}

//取产品分类 产品类型
function checkBizAndPrdType(dom) {
    var url = '../contract/selectBizAndPrdType.html?random='+Math.random()*10000;
    showDialog({
        id: 'selectBizAndPrdType',
        title: '取产品分类',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            $('#bizType').val(data.bizType);
            $('#bizTypeName').val(data.bizTypeName);
            $('#prdUserdfName').val(data.prdUserdfName);
            $('#prdName').val(data.prdName);
            $('#prdPk').val(data.prdPk);
            $('#accountClass').val(data.accountClass);
            $('#accountClassName').val(data.accountClassName);
            $('#bizTypeDetail').val(data.bizTypeDetail);
        }
    });
}

//取业务条线
function checkCrmPrdType(dom) {
    var url = '../contract/selectCrmPrdType.html?random='+Math.random()*10000;
    showDialog({
        id: 'selectCrmPrdType',
        title: '取业务条线分类',
        url: url,
        width: 500,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(data){
            $('#businessLine').val(data.businessLine);
            $('#businessLineName').val(data.businessLineName);
            $('#mainProType').val(data.mainProType);
            $('#mainProTypeName').val(data.mainProTypeName);
        }
    });
}

function calculateDate(o1,o2,o3){

    var value = 0;
    if(o1&&o2&&o3){
        var year1 = parseInt(o1.substring(0, 4));
        var month1 = parseInt(o1.substring(5, 7));
        var day1 = parseInt(o1.substring(8, 10));
        var year2 = parseInt(o2.substring(0, 4));
        var month2 = parseInt(o2.substring(5, 7));
        var day2 = parseInt(o2.substring(8, 10));
        var sum = 0;
        if(day1<=21){
            sum=sum+1;
        }
        if(o3=='2'&&day1<=21){
            //次月还款
            sum=sum-1;
        }
        if(day2>21){
            sum=sum+1;
        }
        sum = sum + (year2-year1)*12+(month2-month1);
        $('#LBIZ_LOAN_GRANT #loanLimit').val(sum);
    }
}