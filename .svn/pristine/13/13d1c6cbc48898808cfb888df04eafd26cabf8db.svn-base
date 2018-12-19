var potentialId = "0";
$(function(){
    var productName = $("#productName").html();
    if(productName == ""){
        $("#selectProductForCust").html("选择产品");
    }else{
        $("#selectProductForCust").html("删除产品");
    }

    // 初始化下拉单选框
    layout.initForms();
    $('select').selectbox();

    banger.verify('#customerName', {name : 'required', tips : '姓名必须填写'});
    //banger.verify('#customerSex', {name : 'required', tips : '性别必须填写'});
    //banger.verify('#identifyType', {name : 'required', tips : '证件类型必须填写'});
    //banger.verify('#identifyNum', [{name : 'required',	tips : '证件号码必须填写'}]);
    banger.verify('#phoneNumber', {name : 'required', tips : '联系电话必须填写'});
    //banger.verify('#customerLevel', {name : 'required', tips : '客户类型必须填写'});

    // 出生日期
//        $('#beginLiveTime').datepicker();


// 提交新增数据源
    $('#btnSaveBase').click(function() {
        var bool = banger.verify('#baseForm');
        if(!bool){
            return false;
        }
        var url = '../custPotentialCustomers/checkCustomerIsRepeatByNameAndPhone.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            async: false,
            data: {
                "customerName": $("#customerName").val(),
                "telephoneNumber": $("#telephoneNumber").val(),
                "id": $("#id").val()
            },
            success: function (result) {
                if (result) {
                    result = jQuery.parseJSON(result);
                    if(result.success){
                        saveCustomer('save');
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.value
                        });
                        return;
                    }
                } else {
                }
            }
        });
    });
    potentialId=$("#potentialId").val();
    $('#toApplyLoan').click(function () {
        tabs.add({
            id : 'addOrUpdateLoan'+potentialId,
            name : 'addOrUpdateLoan'+potentialId,
            pid: tabs.getSelfId(window),
            title : '贷款-新建',
            url : '../loan/getLoanTabs.html?module=apply'+'&potentialId='+potentialId+'&random='+Math.random()*10000,
            lock : false
        });
    });

    // 提交新建数据源数据并继续新建
    $('#btnSaveContine').click(function() {
        var bool = banger.verify('#baseForm');
        if(!bool){
            return false;
        }
        var url = '../custPotentialCustomers/checkCustomerIsRepeatByNameAndPhone.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            async: false,
            data: {
                "customerName": $("#customerName").val(),
                "telephoneNumber": $("#telephoneNumber").val(),
                "id": $("#id").val()
            },
            success: function (result) {
                if (result) {
                    result = jQuery.parseJSON(result);
                    if(result.success){
                        saveCustomer('saveContinue');
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.value
                        });
                        return;
                    }
                } else {

                }
            }
        });
    });

    //关闭
    $('#btnCancelBase').click(function() {

        showCancelConfirmForEdit(function(){
            closeTab();
        })

    });
    $('#cardNumber').change(function () {
        getSexAndAge();
    });
    $('#cardType').change(function () {

        getSexAndAge();
    });
});

//证件号码合法性校验
var identifyNumValidRule = {
    name : 'repeated',
    tips : '证件号码不合法',
    rule : function() {
        var rule = this, bool = true;
        var  identifyId  =  $("#cardType").val();
        var  identifyNum =  $("#cardNumber").val();
        var reg =/^\w{15}$|^\w{18}$/;   //身份证校验规则
        var reg2 = /^\w+$/;  //其他证件类型校验规则
        if(identifyId=="1"){
            if(reg.test(identifyNum) === false)  return  false;
        } else {
            if(reg2.test(identifyNum)==false) return  false;
        }
        return bool;
    }
};

function getSexAndAge() {
    var customerSex = $('#customerSex').val();
    var customerAge = $('#age').val();
    var identifyNum = $('#cardNumber').val();
    var identifyType = $('#cardType').val();
    if (identifyType == '1') {
        if (identifyNum && identifyNum.length == 15) {
            //if (!customerSex) {
            var usex = identifyNum.substring(14, 15);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#customerSex').selectbox({'value': sex });
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
            $('#age').val(age);
            //}
        } else if (identifyNum && identifyNum.length == 18){
            //if (!customerSex) {
            var usex = identifyNum.substring(16, 17);// 用户的性别
            var sex = '';
            if (usex % 2 == 0) {
                sex = '2';
            } else if (usex % 2 == 1) {
                sex = '1';
            }
            $('#customerSex').selectbox({'value': sex });
            //}
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

            $('#age').val(age);
            //}

        }
    }
}
function checkCustomer(){

    var bool = true;

    //证件号码规则
    var  identifyId  =  $("#cardType").val();
    var  identifyNum =  $("#cardNumber").val();
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


    // if(bool){
    //     //客户重复性校验
    //     var data = getPostFields();
    //     var url = '../custPotentialCustomers/checkCustomerIsRepeat.html';
    //     jQuery.ajax({
    //         type : 'post',
    //         url : url,
    //         async : false,
    //         data : data,
    //         success : function(result) {
    //             if (result) {
    //                 result = jQuery.parseJSON(result);
    //                 showConfirm({
    //                     icon: 'warning',
    //                     content:  result.value
    //                 });
    //                 bool = false;
    //             }
    //         }
    //     });
    // }


    return bool;
}

//关闭页卡
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refreshList){
        win.refreshList();
    }
    tabs.close(tabs.getSelfId(window));
}

function saveCustomer(opType){
    var bool=banger.verify("#baseForm");
    if(!bool){
        return false;
    }

    if( $("#cardType").val()!="" && $("#cardNumber").val()!="")
    {
        if(!checkCustomer()){
            return false;
        }
    }

    var phonenumber =/^[-*#0-9\s]+$/;
    var ispass = phonenumber.test($("#telephoneNumber").val());
    if(!ispass)
    {
        showConfirm({
            icon: 'warning',
            content:  '联系方式不正确'
        });
        return "";
    }

    var postJson = getPostFields();
    var postJsonString = JSON.stringify(postJson);

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../custPotentialCustomers/updatePotentialCustomers.html',
        data : {"json":postJsonString,"id":$("#id").val()},
        //data : postJson,
        async: false ,
        success : function(result) {
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                if(opType == 'saveContinue'){
                    tabs.refresh(tabs.getSelfId(window));
                }else{
                    closeTab();
                   /* if(null!=result.value&&"null"!=result.value&&""!=result.value){
                        location.href= '../customer/getCustomerTabs.html?id='+result.value;
                    }*/
                }
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}
function editShow(id, module) {
    var title = 'apply' == module ? '申请' : '调查';
    var url =  '../loan/getLoanTabs.html?module='+ module+'&showApply=1&loanId=' + id+'&random='+Math.random()*10000;
    tabs.add({
        id :'updateCustomer'+module+'1' +id,
        pid:tabs.getSelfId(window),
        name:'updateCustomer'+module+'1' +id,
        title: title + '列表-详情',
        url : url,
        lock:false
    });
}

function editEdit(id, module) {
    var title = 'apply' == module ? '申请' : '调查';
    var url =  '../loan/getLoanTabs.html?module='+ module+'&loanId=' + id+'&random='+Math.random()*10000;
    tabs.add({
        id :'updateCustomer'+module +id,
        pid:tabs.getSelfId(window),
        name:'updateCustomer'+module+id,
        title: title + '列表-编辑',
        url : url,
        lock:false
    });
}

$('#litStartDate').datepicker({
    minDate: new Date()
});

//获取意向产品
function selectProductForCust(){
    var productName = $("#productName").html();
    if(productName == ""){
        var url = '../prodProduct/getProductListPageForPotentialCust.html';
        showDialog({
            id: 'productListPageForPotentialCust',
            title: '意向产品选择',
            url: url,
            width: 821,
            height: 350,
            tabId: tabs.getSelfId(window)
        });
    }else{
        $("#productCode").val("");
        $("#productName").html("");
        $("#selectProductForCust").html("选择产品");
    }

}

function queryProduct(id){
    var url = '../prodProduct/getProductDetailPage.html?productId='+id;
    showDialog({
        id: 'productHandle',
        title: '产品详情',
        url: url,
        width: 700,
        height: 350,
        tabId: tabs.getSelfId(window)
    });
}
