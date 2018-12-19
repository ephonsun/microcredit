$(function () {
    //自定义表单，编辑的移除一条
    $(".head-reduce-icon-ready").click(function(){
        var id = $(this).parent().parent().attr('id');
        var  idVal= $(this).next().find('input').val();
        var old = deleteIds[id];
        if (old == '' || old == null || old == undefined) {
            deleteIds[id]=idVal;
        } else {
            deleteIds[id]=old + ',' + idVal;
        }
        $(this).parent().remove();
    });
    //自定义表单添加一条
    $('.head-add-icon').click(function() {
        var id = $(this).attr("tablename");
        var coId = "co_"+id;
        var clone = $("#"+coId).clone(true);
        //clone.find(".test").show();
        clone.find(".head-add-icon").hide();
        clone.find(".head-reduce-icon").show();
        clone.find(".ui-line-dotted").show();
        clone.find(".head-reduce-icon").click(function(){
            $(this).parent().remove();
        });
        clone.show();
        $("#"+id).append(clone);
        $.getJSON('../loan/getNextLbizId.html',{'tableName': id},function (data) {
            if (data){
                clone.attr("id",data);
                clone.find("input[name='field.ID']").val(data);
            }
        });
        layout.initForms(clone);
        checkRequired1();
    });

    $('#identifyType').selectbox().change(function () {
        getSexAndAge();
         getCustomerInfoEditPage();
    });
    $('#identifyNum').change(function () {
        getSexAndAge();
        getCustomerInfoEditPage();
    });
    //配偶姓名证件号
    $('#spouseIdentifyType').selectbox().change(function () {
        getSpouseSexAndAge();
        //getCustomerInfoEditPage();
    });
    $('#spouseIdentifyNum').change(function () {
        getSpouseSexAndAge();
        //getCustomerInfoEditPage();
    });
    $('#customerName').change(function () {
        getCustomerInfoEditPage();
    });
    //抵押人
    $('[id=pledgeOwner]').change(function(){
        var parent = $(this).parents('table');
        //借款人
        var loanName = $("input[name='field.customerName']").val();
        if(loanName == $(this).val()){
            parent.find('#idNum').val($("input[name='field.identifyNum']").val());
            parent.find('#mortgagePhone').val($("input[name='field.phoneNumber']").val());
            parent.find('#deliveryAddress').val($("input[name='field.liveAddress']").val());
            return false;
        }
        //担保人
        var arr = $('[id=fullName]');
        for(var i = 0 ;i<arr.length;i++){
            var node = $(arr[i]);
            var _parent = node.parents('table');
            if($(this).val()==node.val()){
                parent.find('#idNum').val(_parent.find('#idCard').val());
                parent.find('#mortgagePhone').val(_parent.find('#telNum').val());
                parent.find('#deliveryAddress').val(_parent.find('#liveAddress').val());
                break;
            }
        }
    });
    //行业分类
    $("#display_businessCatalog").click(function(){
        getIndustry();
    })
    $("#display_businessCatalog").next(".ui-text-icon").click(function(){
        getIndustry();
    })

    $('#loanOrientation').attr('readonly',true);
    $('#orientationName').attr('readonly',true);
    $('#loanOrientation').mousedown(function(){
        selectLoanOrientation(loanId);
    });

    banger.verify('.ui-form-fields');
    checkRequired1();
});
function checkRequired1 () {
    $("#baseForm").find("label:visible.ui-star").each(function () {
        var isnull = $(this).attr("isnull");
        if (isnull == 'true'){
            var divObj = $(this).parent().next().find("div");
            var inputObj = divObj.find("input");
            var selectObj = divObj.find("select");
            if(''==inputObj.val()||''==selectObj.val()){
                divObj.addClass('ui-text-failed');
            }
            inputObj.blur(function(){
                if(''!=$(this).val()&&null!=$(this).val()){
                    divObj.removeClass('ui-text-failed');
                }else{
                    divObj.addClass('ui-text-failed');
                }
            });
            selectObj.change(function(){
                if(''!=$(this).val()&&null!=$(this).val()){
                    divObj.removeClass('ui-text-failed');
                }else{
                    divObj.addClass('ui-text-failed');
                }
            });
        }
    });
}

//校验必填项
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
function getIndustry(){
    var url = '../loanIndustryGuidelines/getIndustryGuidelinesPage.html?loanId='+loanId+ "&loanProcess=investigate&businessCatalog="+encodeURI(encodeURI($("#businessCatalog").val()));
    showDialog({
        id: 'getIndustryGuidelinesPage',
        name: 'getIndustryGuidelinesPage',
        pid: tabs.getSelfId(window),
        title : '所属行业',
        width : 400,
        height : 220,
        url : url
    });
}
function addGuaSpouseInfo(_this){
    var fullName = $("#spouseName")=="undefined"?"":$("#spouseName").val();
    var idCard = $("#spouseIdentifyNum")=="undefined"?"":$("#spouseIdentifyNum").val();
    var telNum = $("#spousePhoneNum")=="undefined"?"":$("#spousePhoneNum").val();
    var identifyType = $("#spouseIdentifyType")=="undefined"?"":$("#spouseIdentifyType").val();
    var type = $("#nspouseIdentifyType")=="undefined"?"":$("#nspouseIdentifyType").val();
    var businessAddrss = $("#spouseCompanyAddress")=="undefined"?"":$("#spouseCompanyAddress").val();
    var parent = $(_this).parent();
    var relationship = "10011";
    parent.find("#fullName").val(fullName);
    parent.find("#idCard").val(idCard);
    parent.find("#telNum").val(telNum);
    //parent.find("#relationship").val(relationship);
    parent.find("#relationship").selectbox({'value': relationship });
    parent.find("#identifyType").val(identifyType);
    parent.find("#nidentifyType").val(type);
    parent.find("#businessAddrss").val(businessAddrss);
    $(".head-add-info-icon").hide();
}


