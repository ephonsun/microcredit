$(function(){

    // 初始化下拉单选框
    layout.initForms();
    //$('select').selectbox();

    //banger.verify('#customerName', {name : 'required', tips : '姓名必须填写'});
    //banger.verify('#customerSex', {name : 'required', tips : '性别必须填写'});
    //banger.verify('#identifyType', {name : 'required', tips : '证件类型必须填写'});
    //banger.verify('#identifyNum', [{name : 'required',	tips : '证件号码必须填写'}]);
    //banger.verify('#phoneNumber', {name : 'required', tips : '联系电话必须填写'});
    //banger.verify('#customerLevel', {name : 'required', tips : '客户类型必须填写'});

    // 出生日期
//        $('#beginLiveTime').datepicker();

// 提交新增数据源
    $('#btnSaveBase').click(function() {
        saveCustomer('saveClose');
    });


    // 提交新建数据源数据并继续新建
    $('#btnSaveContine').click(function() {
        saveCustomer('saveContinue');
    });

        var customerId=$('#id').val();
    $('#toApplyLoan').click(function () {
        tabs.add({
            id : 'toApplyLoan',
            name : 'toApplyLoan',
            pid: tabs.getSelfId(window),
            title : '客户申请贷款',
            url : '../loan/getLoanTabs.html?module='+'apply'+'&customerId='+customerId+'&random='+Math.random()*10000,
            lock : false
        });
    });

    //关闭
    $('#btnCancelBase').click(function() {

        showCancelConfirmForEdit(function(){
            closeTab();
        })

    });
    $('#identifyNum').change(function () {
        getSexAndAge();
    });
    $('#identifyType').change(function () {
        getSexAndAge();
    });
    //配偶姓名证件号
    $('#spouseIdentifyType').selectbox().change(function () {
        getSpouseSexAndAge();
    });
    $('#spouseIdentifyNum').change(function () {
        getSpouseSexAndAge();
    });
});

//用户帐号重复性校验
var customerRepeatRule = {
    name : 'repeated',
    tips : '',
    rule : function() {
        var rule = this, bool = true, data = getPostFields();
        var url = '../customer/checkCustomerIsRepeat.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            async : false,
            data : data,
            success : function(result) {
                if (result) {
                    result = jQuery.parseJSON(result);
                    rule.tips = result.value;
                    bool = result.success;
                } else{
                    bool = true;
                }
            }
        });
        return bool;
    }
};

//证件号码合法性校验
var identifyNumValidRule = {
    name : 'repeated',
    tips : '证件号码不合法',
    rule : function() {
        var rule = this, bool = true;
        var  identifyId  =  $("#identifyType").val();
        var  identifyNum =  $("#identifyNum").val();
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
    var customerAge = $('#customerAge').val();
    var identifyNum = $('#identifyNum').val();
    var identifyType = $('#identifyType').val();
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

            $('#customerAge').val(age);
            //}

            //生日
            var birthday="19"+identifyNum.substring(6, 8) + "-" + identifyNum.substring(8, 10) + "-" + identifyNum.substring(10, 12);
            $('#customerBirthday').val(birthday);
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

            $('#customerAge').val(age);
            //}
            //生日
            var birthday=identifyNum.substring(6, 10) + "-" + identifyNum.substring(10, 12) + "-" + identifyNum.substring(12, 14);
            $('#customerBirthday').val(birthday);
        }
    }
}
function checkCustomer(){

    var bool = true;

    //证件号码规则
    var  identifyId  =  $("#identifyType").val();
    var  identifyNum =  $("#identifyNum").val();
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


    if(bool){
        //客户重复性校验
        var data = getPostFields();
        var url = '../customer/checkCustomerIsRepeat.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            async : false,
            data : data,
            success : function(result) {
                if (result) {
                    result = jQuery.parseJSON(result);
                    showConfirm({
                        icon: 'warning',
                        content:  result.value
                    });
                    bool = false;
                }
            }
        });
    }


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
    if(!checkCustomer()){
        return;
    }

    var postJson = getPostFields();
    eval("var postJsonString = '"+JSON.stringify(postJson)+"';");

    jQuery.ajax({
        type : 'post',
        dataType:'json',
        url : '../customer/saveCustomerInfo.html',
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
$('#btnHandOver').click(function () {
    var url = '../customerHandOver/getCusHandOverUpdatePage.html?ids='+$("#id").val()+"&belongId="+$("#belongId").val();
    showDialog({
        id: 'customerHandOverUpdate',
        title: '移交客户',
        url: url,
        width: 400,
        height: 200,
        tabId: tabs.getSelfId(window)
    });
});
///*********处理表单显隐**********************/
//2017-12-08确认不做联动校验
function hideAllCtrlForm() {
    var hideMap = {};
    $("select[hideflag='true']").each(function () {
        var hideitem = $(this).attr("hideitem");
        var hidevalue = $(this).attr("hidevalue");
        var selValue = $(this).val();
        if(hideitem && hidevalue && selValue) {
            var hidevalues = hidevalue.split(",");
            for(var i = 0; i < hidevalues.length;i++) {
                if(selValue == hidevalues[i])
                    hideMap[hideitem] = true;
            }
        }
    })
    $("div.template").each(function () {
        var templateName = $(this).attr("name");
        if(hideMap[templateName]){
            $(this).hide();
        }else if($(this).is(":hidden")){
            $(this).show();
            //如果全局渲染selectbox导致页面太卡了
            $(this).find('select').selectbox();
        }

    })
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