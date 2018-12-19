// $(function(){
//
//     $('select').selectbox();
//
//     var loanTypeId =  $('#loanTypeId').selectbox({}).val();//$("#loanTypeId_hide").val();
//     //if(null!=loanTypeId&&""!=loanTypeId&&undefined!=loanTypeId){}
//     refreshLoanTemplate(loanTypeId,"applyTemplate","Apply");
//
//
//     //$('#loanTypeId').selectbox({}).val(loanTypeId).selectbox().change(
//     //    function() {
//     //        var value = $(this).val();
//     //        refreshLoanTemplate(value)
//     //    }
//     //);
//
// // 提交新增数据源
//     $('#btnSaveApply').click(function() {
//         saveApplyLoan('saveClose');
//     });
//
//
//     // 提交新建数据源数据并继续新建
//     $('#btnSaveApplyContine').click(function() {
//         saveApplyLoan('saveContinue');
//     });
//
//     //关闭
//     $('#btnCancelApply').click(function() {
//         showCancelConfirmForEdit(function(){
//             closeApplyTab();
//         })
//
//     });
//
//     $('#identifyType').selectbox().change(function () {
//         getCustomerInfoEditPage();
//     });
//
//
//     $('#identifyNum').change(function () {
//         getCustomerInfoEditPage();
//     });
//
//
// });
//
//
// //根据证件类型+证件号码获得编辑页面
// function getCustomerInfoEditPage(){
//     var identifyType = $('#identifyType').val();
//     var identifyNum = $('#identifyNum').val();
//     if(document.getElementById("LBIZ_PERSONAL_INFO")){
//         getCustomerInfo("LBIZ_PERSONAL_INFO",identifyType,identifyNum);
//     }
//     if(document.getElementById("LBIZ_FAMILY_INFO")){
//         getCustomerInfo("LBIZ_FAMILY_INFO",identifyType,identifyNum);
//     }
//     if(document.getElementById("LBIZ_SPOUSE_INFO")){
//         getCustomerInfo("LBIZ_SPOUSE_INFO",identifyType,identifyNum);
//     }
// }
//
// function getCustomerInfo(tableName,identifyType,identifyNum){
//     jQuery.ajax({
//         type: 'post',
//         url: '../customer/getCustomerInfoPage.html?',
//         data: {"identifyType": identifyType, "identifyNum": identifyNum, "tableName": tableName},
//         success: function (data) {
//             if(data != ''){
//                 $('#'+tableName).html(data);
//             }
//         }
//     });
// }
//
// function checkCustomer(){
//
//     var bool = true;
//
//     //证件号码规则
//     var  identifyId  =  $("#identifyType").val();
//     var  identifyNum =  $("#identifyNum").val();
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
//
//
//     //if(bool){
//     //    //客户重复性校验
//     //    var data = getPostFields();
//     //    var url = '../customer/checkCustomerIsRepeat.html';
//     //    jQuery.ajax({
//     //        type : 'post',
//     //        url : url,
//     //        async : false,
//     //        data : data,
//     //        success : function(result) {
//     //            if (result) {
//     //                result = jQuery.parseJSON(result);
//     //                showConfirm({
//     //                    icon: 'warning',
//     //                    content:  result.value
//     //                });
//     //                bool = false;
//     //            }
//     //        }
//     //    });
//     //}
//
//     return bool;
// }
//
//
// //关闭页卡
// function closeApplyTab() {
//     var win = tabs.getIframeWindow(tabs.getSelfPid(window));
//     if( win && win.refresh){
//         win.refresh();
//     }
//     tabs.close(tabs.getSelfId(window));
// }
//
// function saveApplyLoan(opType){
//     var bool=banger.verify("#baseForm");
//     if(!bool){
//         return false;
//     }
//     if(!checkCustomer()){
//         return;
//     }
//
//     var postJson = getPostFields();
//     eval("var postJsonString = '"+JSON.stringify(postJson)+"';");
//
//     jQuery.ajax({
//         type : 'post',
//         dataType:'json',
//         url : '../loan/saveLoanApply.html',
//         data : {"json":postJsonString,"id":$("#loanId").val()},
//         //data : postJson,
//         async: false ,
//         success : function(result) {
//             if(result.success){
//                 showConfirm({
//                     icon: 'succeed',
//                     content: result.cause
//                 });
//                 if(opType == 'saveContinue'){
//                     $("#tabs")[0].reset();
//                 }else{
//                     if(null!=result.value&&"null"!=result.value&&""!=result.value){
//                         location.href= '../customer/getCustomerTabs.html?id='+result.value;
//                     }
//                 }
//             }else{
//                 showConfirm({
//                     icon: 'warning',
//                     content: result.cause
//                 });
//             }
//         }
//     });
// }
//
//
