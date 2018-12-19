$(function () {

    $('select').selectbox();
    //保存
    $("#btnSave").click(function () {
        saveCustImport();
    });

    //重置
    $("#resetImport").click(function () {
        resetCustImport();
    });
    
    //生成存储过程
    $("#genProcedure").click(function () {
        generateProcedure();
    });

    //生成模拟数据
    $("#generateData").click(function () {
        generateAnalogData();
    });

    //取消
    $("#btnCancel").click(function () {
        tabs.close(tabs.getSelfId(window));
    });


});

//保存客户自动导入
function saveCustImport() {
    var matchMode = $('#matchMode').val();
    jarr = getPageResult();
    $.ajax({
     url : '../custAutoImport/saveCustAutoImport.html?id=1&matchMode='+matchMode,
     type : 'POST',
     data : {"jsonArray":jarr},
     success : function () {
         showConfirm({
             icon: 'succeed',
             content: '操作成功'
         });
         }
    }) ;
}

//重置页面
function resetCustImport() {
    window.location.reload();
}

//获取页面的值
function getPageResult() {
    jsonArray = [];
    var i = 0;
    $("select[class='targetTab']").each(function(){
        var selectValue = $(this).val();      //目标表列名
        var selectFieldId;
        if("" != selectValue){
            selectFieldId = $(this).find("option[name = "+selectValue+"]").attr("fieldId");  //目标表列id
        }else{
            selectFieldId = 0;
        }
        var soucoldisply = $("#value_" + i).val();   //来源数据表列名描述
        var ordernum = $("#order_" + i).val();  //来源数据列序号
        var soucolname = $("#soucolname_"+i).val();  //来源数据表列名
        //是否覆盖
        if($("#isCover_" + i).is(':checked')) {
            var iscover = '1';
        }else{
            var iscover = '0';
        }
        //是否必填
        if($("#isReqWrite_" + i).is(':checked')) {
            var isreqwrite = '1';
        }else{
            var isreqwrite = '0';
        }
        //拼接json
        json = {
            "selectValue":selectValue,
            "selectFieldId":selectFieldId,
            "soucoldisply":soucoldisply,
            "ordernum":ordernum,
            "soucolname":soucolname,
            "iscover":iscover,
            "isreqwrite":isreqwrite
        };
        i = i + 1;
        jsonArray.push(json);
    });
    return JSON.stringify(jsonArray);
}

//生成存储过程
function generateProcedure() {
    $.ajax({
        url : '../custAutoImport/generateProcedure.html',
        type : 'POST',
        success : function () {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
        }
    }) ;
}


//生成模拟数据
function generateAnalogData() {
    var amount = $('#dataValue').val();
    $.ajax({
        url : '../custAutoImport/generateAnalogData.html?amount='+amount,
        type : 'POST',
        success : function () {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
        }
    }) ;
}