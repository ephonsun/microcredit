// var deleteIds = {};
// $(function(){
//     //行业分类
//     $("#print_a").click(function(){
//         var loanId = $("#loanId").val();
//         getPrintPage(loanId, 'threeTable');
//     });
//     $("#print_b").click(function(){
//         var loanId = $("#loanId").val();
//         getPrintPage(loanId, 'info');
//     });
// });
// /***********************************************申请*****************************************************/
// var potentialId="0";
// var cusId="0";
//
// //保存申请信息
// function saveApplyLoan(id, opType) {
//
//     if (opType == 'Allot') {
//         if (!checkRequired ()) {
//             showConfirm({
//                 icon: 'warning',
//                 content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
//             });
//             return false;
//         }
//     }
//
//     buttonDisable('#btnSaveApplyContine');
//     buttonDisable('#btnSaveApply');
//     var bool = banger.verify("#baseForm");
//     if (!bool) {
//         return false;
//     }
//     if (!checkCustomer()) {
//         return;
//     }
//     // if (!checkIdCards()) {
//     //     return;
//     // }
//     var postJson = getPostFieldsForTemplate('#applyTemplate_applyapply');
//     var postJsonString = JSON.stringify(postJson);
//     var deleteIdJson = JSON.stringify(deleteIds);
//
//     potentialId=$("#potentialId").val();
//     cusId=$('#id').val();
//     jQuery.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: '../loanApply/saveLoanIntoApply.html?potentialId='+potentialId+'&cusId='+cusId+'&random='+Math.random()*10000,
//         data: {
//             "json": postJsonString,
//             "deleteIds":deleteIdJson,
//             "processType": opType,
//             "id": id,
//             "loanTypeId": loanTypeId,
//             "applyId": applyId,
//             "customerId":customerId
//         },
//         async: false,
//         success: function (result) {
//             if (result.success) {
//                 showConfirm({
//                     icon: 'succeed',
//                     content: result.cause
//                 });
//                 if (opType == 'Allot') {
//                     closeApplyTab();
//                 } else {
//                     if (null != result.id && "null" != result.id && "" != result.id) {
//                         closeApplyTab(result.id, 'apply');
//                     }
//                 }
//             } else {
//                 showConfirm({
//                     icon: 'warning',
//                     content: result.cause,
//                     ok: function() {
//                         if (result.newSave && null != result.id && "null" != result.id && "" != result.id) {
//                             var url = '../loan/getLoanTabs.html?module=apply&showApply=1&loanId=' + result.id+'&random='+Math.random()*10000;
//                             tabs.add({
//                                 id :'updateCustomerapply1' +result.id,
//                                 pid:tabs.getSelfId(window),
//                                 name:'updateCustomerapply1' +result.id,
//                                 title: '申请列表-详情',
//                                 url : url,
//                                 lock:false
//                             });
//                             closeApplyTab();
//                         }
//                     }
//                 });
//
//             }
//         }
//     });
// }
// //根据证件类型+证件号码获得编辑页面
// function getCustomerInfoEditPage(){
//     if(loanId == '' && customerId == '') {
//         var customerName = $('#co_LBIZ_PERSONAL_INFO').find('#customerName').val();
//         var identifyType = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
//         var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
//         if (customerName != '' && customerName != null && customerName != undefined
//             && identifyType != '' && identifyType != null && identifyType != undefined
//             && identifyNum != '' && identifyNum != null && identifyNum != undefined) {
//             if(document.getElementById("LBIZ_PERSONAL_INFO")){
//                 getCustomerInfo("LBIZ_PERSONAL_INFO",customerName, identifyType,identifyNum);
//             }
//             if(document.getElementById("LBIZ_FAMILY_INFO")){
//                 getCustomerInfo("LBIZ_FAMILY_INFO",customerName,identifyType,identifyNum);
//             }
//             if(document.getElementById("LBIZ_SPOUSE_INFO")){
//                 getCustomerInfo("LBIZ_SPOUSE_INFO",customerName,identifyType,identifyNum);
//             }
//         }
//     }
// }
//
// function getCustomerInfo(tableName,customerName, identifyType,identifyNum){
//     jQuery.ajax({
//         type: 'post',
//         url: '../customer/getCustomerInfoPage.html',
//         dataType:'json',
//         data: {"customerName" : customerName,"identifyType": identifyType, "identifyNum": identifyNum, "tableName": tableName},
//         success: function (result) {
//             if(result && result.success){
//                 customerId = result.customerId;
//                 $.each(result.data, function (index,row) {
//
//                     $("#" + row.key).val(row.value);
//                     if (row.type == 'Select') {
//                         $("#" + row.key).selectbox();
//                     } else if (row.type == 'Date') {
//                         var str1 = row.value.substring(0, 10) ;
//                         $("#" + row.key).val(str1.toString());
//                         $("#" + row.key).datepicker()
//                     }
//                 })
//             }
//         }
//     });
// }
// //
// // function checkIdCards(){
// //     var bool = true;
// //     var  identifyId  =  $("#spouseIdentifyType").val();
// //     var  identifyNum =  $("#spouseIdentifyNum").val();
// //     var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
// //     var reg2 = /^\w+$/;  //其他证件类型校验规则
// //     if(identifyId=="01"){
// //         if(identifyNum && reg.test(identifyNum) === false){
// //             showConfirm({
// //                 icon: 'warning',
// //                 content:  '配偶信息的证件号码不合法'
// //             });
// //             bool = false;
// //         }
// //     } else {
// //         if (identifyNum && reg2.test(identifyNum) == false) {
// //             showConfirm({
// //                 icon: 'warning',
// //                 content: '证件号码不合法'
// //             });
// //             bool = false;
// //         }
// //     }
// //     $("#LBIZ_JOINT_BORROWER input[name='field.idCard']").each(function () {
// //         if (bool) {
// //             var  identifyNum =  $(this).val();
// //             var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
// //             if(identifyNum && reg.test(identifyNum) === false){
// //
// //                 showConfirm({
// //                     icon: 'warning',
// //                     content:  '共同借款人的证件号码不合法'
// //                 });
// //                 bool = false;
// //             }
// //         }
// //     });
// //     $("#LBIZ_LOAN_GUARANTEE input[name='field.idCard']").each(function () {
// //         if (bool) {
// //             var  identifyNum =  $(this).val();
// //             var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
// //             if(identifyNum && reg.test(identifyNum) === false){
// //
// //                 showConfirm({
// //                     icon: 'warning',
// //                     content:  '担保人的证件号码不合法'
// //                 });
// //                 bool = false;
// //             }
// //         }
// //     });
// //
// //     return bool;
// // }
// function checkCustomer(){
//     var bool = true;
//     //证件号码规则
//     var identifyId = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
//     var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
//     var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
//     var reg2 = /^\w+$/;  //其他证件类型校验规则
//     if(identifyId=="01"){
//         if(reg.test(identifyNum) === false){
//             showConfirm({
//                 icon: 'warning',
//                 content:  '证件号码不合法'
//             });
//             bool = false;
//         }
//     } else {
//         if(reg2.test(identifyNum)==false) {
//             showConfirm({
//                 icon: 'warning',
//                 content:  '证件号码不合法'
//             });
//             bool = false;
//         }
//     }
//     return bool;
// }
//
// function getSexAndAge() {
//     var customerSex = $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').val();
//     var customerAge = $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val();
//     var identifyNum = $('#co_LBIZ_PERSONAL_INFO').find('#identifyNum').val();
//     var identifyType = $('#co_LBIZ_PERSONAL_INFO').find('#identifyType').val();
//     if (identifyType == '01') {
//         if (identifyNum.length == 15) {
//             //if (!customerSex) {
//             var usex = identifyNum.substring(14, 15);// 用户的性别
//             var sex = '';
//             if (usex % 2 == 0) {
//                 sex = '0';
//             } else if (usex % 2 == 1) {
//                 sex = '1';
//             }
//             $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').selectbox({'value': sex });
//             //}
//             //if (!customerAge) {
//             var age = 0;
//             var date = new Date();
//             var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
//             var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
//             var dateStr = date.getFullYear() + '' + month + '' + day;
//             var yearn = dateStr.substring(0, 4);// 得到年份
//             var yuen = dateStr.substring(4, 8);// 得到月份
//             var year = "19" + identifyNum.substring(6, 8);// 得到年份
//             var yue = identifyNum.substring(8, 12);// 得到月份
//             if (yuen > yue) {
//                 age = yearn - year + 1;
//             } else {
//                 age = yearn - year;
//             }
//
//             $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val(age);
//             // }
//         } else if (identifyNum.length == 18){
//             //if (!customerSex) {
//             var usex = identifyNum.substring(16, 17);// 用户的性别
//             var sex = '';
//             if (usex % 2 == 0) {
//                 sex = '0';
//             } else if (usex % 2 == 1) {
//                 sex = '1';
//             }
//             $('#co_LBIZ_PERSONAL_INFO').find('#customerSex').selectbox({'value': sex });
//             // }
//             //if (!customerAge) {
//             var age = 0;
//             var date = new Date();
//             var month = date.getMonth() + 1 < 10 ? '0' + ( date.getMonth() +1 ) : (date.getMonth() + 1);
//             var day = date.getDate() + 1 < 10 ? '0' + ( date.getDate() +1 ) : (date.getDate() + 1);
//             var dateStr = date.getFullYear() + '' + month + '' + day;
//             var yearn = dateStr.substring(0, 4);// 得到年份
//             var yuen = dateStr.substring(4, 8);// 得到月份
//             var year = identifyNum.substring(6, 10);// 得到年份
//             var yue = identifyNum.substring(10, 14);// 得到月份
//             if (yuen > yue) {
//                 age = yearn - year + 1;
//             } else {
//                 age = yearn - year;
//             }
//             $('#co_LBIZ_PERSONAL_INFO').find('#customerAge').val(age);
//         }
//     }
//
// }
//
//
// function applyInfoSign(loanId) {
//     var url = '../loanAllot/gotoAllotSignPage.html?ids='+loanId+'&random='+Math.random()*10000;
//     showDialog({
//         id: 'loanAllotSign',
//         title: '分配贷款',
//         url: url,
//         width: 330,
//         height: 200,
//         tabId: tabs.getSelfId(window)
//     });
// }
//
//
// // 调查提交审批
// function investigateSubmit(id) {
//     if (!checkRequired ()) {
//         showConfirm({
//             icon: 'warning',
//             content: '还有必填项未填写，请先进行编辑，将必填项填写完整'
//         });
//         return false;
//     }
//
//     jQuery.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: '../loanInvestigate/saveApproveInfo.html',
//         data: {"loanId": id },
//         async: false,
//         success: function (result) {
//             if (result.success) {
//                 if (result.random) {
//                     var postJsonString = JSON.stringify(result.reviews);
//                     showConfirm({
//                         icon: 'confirm',
//                         content: '您确定要提交审批吗？',
//                         ok: function () {
//                             jQuery.ajax({
//                                 type : 'post',
//                                 dataType:'json',
//                                 url : '../loanApproval/saveExamine.html',
//                                 data : {"loanId":id, "processId" : result.processId, "flowId" : result.flowId, "paramId" : result.paramId, "reviews": postJsonString},
//                                 success : function(result) {
//                                     if(result.success){
//                                         showConfirm({
//                                             icon: 'succeed',
//                                             content: result.cause
//                                         });
//                                         closeApplyTab();
//                                     }else{
//                                         showConfirm({
//                                             icon: 'warning',
//                                             content: result.cause
//                                         });
//                                     }
//                                 }
//                             });
//                         },
//                         cancel: function () {
//                         }
//                     });
//                 } else {
//                     var url = '../loanApproval/selectUserForward.html?loanId='+id ;
//                     showDialog({
//                         id: 'submitLoanApproval',
//                         title: '提交审批',
//                         url: url,
//                         width: 400,
//                         height: 300,
//                         tabId: tabs.getSelfId(window)
//                     });
//                 }
//             } else {
//                 showConfirm({
//                     icon: 'warning',
//                     content: result.cause
//                 });
//             }
//         }
//     });
// }
// // 调查提交保存
// function investigateSave(id) {
//     var bool = banger.verify("#baseForm");
//     if (!bool) {
//         return false;
//     }
//     if (!checkCustomer()) {
//         return;
//     }
//     // if (!checkIdCards()) {
//     //     return;
//     // }
//
//     var postJson = getPostFieldsForTemplate('#applyTemplate_investigateinvestigate');
//     var postJsonString = JSON.stringify(postJson);
//     var deleteIdJson = JSON.stringify(deleteIds);
//     // var postJson = getPostFieldsForTemplate('#applyTemplate_investigateinvestigate');
//     // eval("var postJsonString = '" + JSON.stringify(postJson) + "';");
//     // eval("var deleteIdJson = '" + JSON.stringify(deleteIds) + "';");
//     jQuery.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: '../loanInvestigate/saveLoanApply.html',
//         data: {
//             "json": postJsonString,
//             "deleteIds":deleteIdJson,
//             "id": id,
//             "loanTypeId": loanTypeId
//         },
//         async: false,
//         success: function (result) {
//             if (result.success) {
//                 showConfirm({
//                     icon: 'succeed',
//                     content: result.cause
//                 });
//                 if (null != result.value && "null" != result.value && "" != result.value) {
//                     // var url = '../loan/getLoanTabs.html?module=investigate&showApply=1&loanId=' + result.value +'&random='+Math.random()*10000;
//                     // tabs.add({
//                     //     id :'updateCustomerinvestigate1' +result.value,
//                     //     pid:tabs.getSelfId(window),
//                     //     name:'updateCustomerinvestigate1' +result.value,
//                     //     title: '调查列表-详情',
//                     //     url : url,
//                     //     lock:false
//                     // });
//                     closeApplyTab(result.value, 'investigate');
//                 } else {
//                     showConfirm({
//                         icon: 'warning',
//                         content: result.cause
//                     });
//                 }
//             }
//         }
//     });
// }
//
// //判断必填项是否完整
// function checkRequired () {
//     var bool = true;
//     $("#baseForm").find("label.ui-star").each(function () {
//         var isnull = $(this).attr("isnull");
//         if (isnull == 'true')
//             bool = false;
//     });
//     return bool;
// }
// //编辑跳转
// function editApply(loanId, module) {
//     closeApplyTab(loanId, module, true);
//     // var url = '../loan/getLoanTabs.html?module=investigate&showApply=1&loanId=' + result.value +'&random='+Math.random()*10000;
//     // var title = 'apply' == module ? '申请' : '调查';
//     // var url = '../loan/getLoanTabs.html?module='+module+'&loanId=' + loanId+'&random='+Math.random()*10000;
//     // tabs.add({
//     //     id :'updateCustomer' +module +loanId,
//     //     pid:tabs.getSelfId(window),
//     //     name:'updateCustomer' +module +loanId,
//     //     title: title + '列表-编辑',
//     //     url : url,
//     //     lock:false
//     // });
// }
//
//
// //关闭页卡
// function closeApplyTab(id, module, edit) {
//     var win = tabs.getIframeWindow(tabs.getSelfPid(window));
//     if (id) {
//         if (edit) {
//             if( win && win.editEdit){
//                 win.editEdit(id,module);
//             }
//         } else {
//             if( win && win.editShow){
//                 win.editShow(id,module);
//             }
//         }
//     }
//     if( win && win.refreshList){
//         win.refreshList();
//     }
//     if( win && win.refreshMarketCustomerGridList){
//         win.refreshMarketCustomerGridList();
//     }
//     tabs.close(tabs.getSelfId(window));
// }
// function refuseApply(loanId, module){
//     var url = '../loan/gotoRefusePage.html?loanId='+loanId+'&module='+module+'&random='+Math.random()*10000;
//     showDialog({
//         id: 'refuseLoanApply',
//         title: '拒绝贷款申请',
//         url: url,
//         width: 500,
//         height: 270,
//         tabId: tabs.getSelfId(window)
//     });
// }
// function backApplyInfo(loanId, module){
//     var url = '../loan/backApplyInfo.html?loanId='+loanId + '&module=' + module+'&random='+Math.random()*10000;
//     showDialog({
//         id: 'backLoanApply',
//         title: '回退贷款信息',
//         url: url,
//         width: 330,
//         height: 145,
//         tabId: tabs.getSelfId(window)
//     });
// }
// function showModel(){
//     var url='../modelManagement/getShowPage.html?projectId=1';
//     showDialog({
//         id: 'pageShowHandle',
//         title: '参考利率',
//         url: url,
//         width: 317,
//         height: 'auto',
//         tabId: tabs.getSelfId(window)
//     });
// }
// function showPlanInfo(module, fieldFlag) {
//     var repaymentMode, proposalAmount, proposalLimit, proposalRatio, date='',ratioDate='';
//     // var bool = banger.verify("#baseForm");
//     // if (!bool) return;
//     if (fieldFlag == '0') {
//         if ('clear' == module || 'investigate' == module || module == 'approval' ) {
//             repaymentMode = $("#LBIZ_SURVEY_RESULT #repaymentMode").val();
//             proposalAmount = $("#LBIZ_SURVEY_RESULT #proposalAmount").val();
//             proposalLimit = $("#LBIZ_SURVEY_RESULT #proposalLimit").val();
//             proposalRatio = $("#LBIZ_SURVEY_RESULT #proposalRatio").val();
//         } else {
//             repaymentMode = $("#LBIZ_LOAN_GRANT #loanBackMode").val();
//             proposalAmount = $("#LBIZ_LOAN_GRANT #loanAmount").val();
//             proposalLimit = $("#LBIZ_LOAN_GRANT #loanLimit").val();
//             proposalRatio = $("#LBIZ_LOAN_GRANT #loanRatio").val();
//             ratioDate = $("#LBIZ_LOAN_GRANT #loanRatioDate").val();
//             date = $("#LBIZ_LOAN_GRANT #loanBackDate").val();
//         }
//     } else {
//         if ('clear' == module || 'investigate' == module || 'approval' == module ) {
//             repaymentMode = $("#repay-repaymentMode").text();
//             proposalAmount = $("#repay-proposalAmount").text();
//             proposalLimit = $("#repay-proposalLimit").text();
//             proposalRatio = $("#repay-proposalRatio").text();
//         } else {
//             repaymentMode = $("#repay-loanBackMode").text();
//             proposalAmount = $("#repay-loanAmount").text();
//             proposalLimit = $("#repay-loanLimit").text();
//             proposalRatio = $("#repay-loanRatio").text();
//             ratioDate = $("#repay-ratioDate").text();
//             date = $("#repay-loanBackDate").text();
//         }
//     }
//     if (proposalAmount && proposalLimit && repaymentMode) {
//         if (repaymentMode == '等额本金' || repaymentMode == '1') repaymentMode = '1';
//         else if (repaymentMode == '等额本息' || repaymentMode == '2') repaymentMode = '2';
//         else if (repaymentMode == '按月还息到期还本' || repaymentMode == '3') repaymentMode = '3';
//         // else if (repaymentMode == '一次性还本付息' || repaymentMode == '4') repaymentMode = '4';
//         else repaymentMode = '5';
//
//         if ('clear' != module && 'investigate' != module && module != 'approval' ) {
//             if (!date || !proposalRatio || !ratioDate) {
//                 showConfirm({
//                     icon: 'warning',
//                     content: '信息不全，请补全后再试！'
//                 });
//                 return false;
//             }
//         }
//         if (repaymentMode == '1' || repaymentMode == '2') {
//             if (!proposalRatio && repaymentMode == '1' && ('clear' == module || 'investigate' == module || module == 'approval') ) {
//                 proposalRatio = "0.1";
//             }
//             if (proposalRatio) {
//                 openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module,fieldFlag,ratioDate, date);
//             } else {
//                 showConfirm({
//                     icon: 'warning',
//                     content: '信息不全，请补全后再试！'
//                 });
//             }
//         } else {
//             openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module,fieldFlag,ratioDate, date);
//         }
//     } else {
//         showConfirm({
//             icon: 'warning',
//             content: '信息不全，请补全后再试！'
//         });
//     }
// }
// function openPlanPage(repaymentMode, proposalAmount, proposalLimit, proposalRatio,module, fieldFlag,ratioDate, date) {
//     var loanId = $('#loanId').val();
//     var url='../loanRepayPlanInfo/getPlanPage.html?loanId=' + loanId + '&repaymentMode=' + repaymentMode + '&proposalAmount=' + proposalAmount  + '&proposalLimit=' + proposalLimit
//         + '&proposalRatio=' + proposalRatio + '&fieldFlag=' + fieldFlag + '&module=' + module + '&ratioDate=' + ratioDate + '&date=' + date;
//     showDialog({
//         id: 'showPlanPage',
//         title: '还款计划',
//         url: url,
//         width: 700,
//         height: 500,
//         tabId: tabs.getSelfId(window),
//     });
// }
//
// //放款保存
// function loanMoneySave(id){
//     var postJson = getPostFieldsForTemplate('#applyTemplate_loanloan');
//     var postJsonString = JSON.stringify(postJson);
//     jQuery.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: '../loanLoanMoney/loanMoneySave.html',
//         data: {
//             "json": postJsonString,
//             "id": id
//         },
//         async: false,
//         success: function (result) {
//             if (result.success) {
//                 showConfirm({
//                     icon: 'succeed',
//                     content: result.cause
//                 });
//                 var win = tabs.getIframeWindow("updateCustomerloan"+id);
//                 if(win)
//                     win.location.reload(true);
//                 // closeApplyTab();
//             } else {
//                 showConfirm({
//                     icon: 'warning',
//                     content: result.cause
//                 });
//             }
//         }
//     });
// }
// // 放款
// function loanMoney(id) {
//     var bool = banger.verify("#baseForm");
//     if (!bool) {
//         return false;
//     }
//     var postJson = getPostFieldsForTemplate('#applyTemplate_loanloan');
//     var postJsonString = JSON.stringify(postJson);
//     jQuery.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: '../loanLoanMoney/saveLoanApply.html',
//         data: {
//             "json": postJsonString,
//             "id": id
//         },
//         async: false,
//         success: function (result) {
//             if (result.success) {
//                 showConfirm({
//                     icon: 'succeed',
//                     content: result.cause
//                 });
//                 closeApplyTab();
//             } else {
//                 showConfirm({
//                     icon: 'warning',
//                     content: result.cause
//                 });
//             }
//         }
//     });
// }
//
// // 导出合同
// function exportContract(loanId,loanTypeId) {
//     //var bool = banger.verify("#baseForm");
//     //if (!bool) {
//     //    return false;
//     //}
//     var url = '../contract/getExportContractPage.html?loanId=' + loanId +"&loanTypeId="+ loanTypeId +'&random='+Math.random()*10000;
//     showDialog({
//         id: 'exportContractPage',
//         title: '导出合同',
//         url: url,
//         width: 400,
//         height: 400,
//         tabId: tabs.getSelfId(window)
//     });
// }
//
//
// var dom = null;
// function showMap(e, id, columnName, tableName, isShow, lbizId) {
//     dom = $(e).parent().prev();
//     var title = isShow && isShow == '1' ? '查看定位' : '标记定位';
//     var url = '../tagging/getTaggingMap.html?loanId=' + id + '&isShow='  + isShow + '&columnName='+ columnName + '&tableName='+ tableName + '&lbizId='+ lbizId + '&random='+Math.random()*10000;
//     showDialog({
//         id: 'loanTaggingMap',
//         title: title,
//         url: url,
//         width: 920,
//         height: 550,
//         tabId: tabs.getSelfId(window)
//     });
// }
// function setAddressValue(address) {
//     var oldVal = $(dom).find('input').val();
//     if (!oldVal){
//         $(dom).find('input').val(address);
//     }
// }
//
// function getPrintPage(loanId, type){
//     var url = '../loanLoanMoney/printLoan.html?type='+type+'&loanId=' +loanId;
//     var title = type == 'info' ? '打印调查报告' : '打印三表信息';
//     showDialog({
//         id: 'getPrintPage' + type,
//         title: title,
//         url: url,
//         width: 800,
//         height: 600,
//         tabId: tabs.getSelfId(window)
//     });
// }
//
//
//
//
