
var modeScoreField = {};

var reg = /^(-?\d{0,4})(\.\d{0,2})?$/;
var reg1 = /^(-?\d+)(\.\d{0,2})?$/;//数值
//根据表单模板查询评分项
$(function(){
    // //初始化页面的时候加载评分项(根据模板id)
    // if($("#templateId").val() != ""){
    //     queryModelScoreFieldByPid($("#templateId").val());
    // }

    //数值显示设置
    // $(".doubleToInt").each(function(){
    //     $(this).val(parseInt($(this).val()));
    // });
    //编辑状态需要显示字段
    var isEdit = $("#isEdit").val();
    if(isEdit == "yes"){
        $("#fieldCounts,#paramsTable").show();
        $("#fieldId,#templateId").attr("disabled","disabled");
        var fieldtype = $("#fieldId").find("option:selected").attr("fieldType");
        if(fieldtype == "Decimal" || fieldtype == "Number" || fieldtype == "Float" || fieldtype == "Ratio"){

        }else{
            $("#fieldCounts").hide();
        }
    }else{
        $("#fieldCounts,#paramsTable").hide();
    }
    banger.verify('#templateId',{name: 'required', tips: '表单模板必须选择'});
    banger.verify('#fieldId',{name: 'required', tips: '评分项必须选择'});
    banger.verify('.number',{name: 'required', tips: '数值必须填写'});
    $('select').selectbox();
    $("#btnSave").click(function(){
        saveScoreField();
    });

    $("#btnCancel").click(function(){
        var dialog = getDialog('saveModelScoreFieldPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    });
});
function queryModelScoreFieldByPid(pid) {
    $("#nfieldId").val("");
    $("#fieldCounts,#paramsTable").hide();
    if(pid == ""){
        $("#fieldId").empty().append("<option></option>");
        return;
    }

    // $("#paramTable").empty().hide();
    var url = '../modelScoreField/queryModelScoreFieldByPid.html';
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType:'json',
        async : false,
        data : {'templateId':pid},
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option fieldNumberUnit=" +"："+ list[i].cols.fieldNumberUnit + "   fieldType = " + list[i].cols.fieldType + " value =" + list[i].id +" >" + list[i].cols.fieldDisplay + "</option>";
            }
            $("#fieldId").empty().append(comment);
        }
    });
}
//根据不同的field显示不同的内容
function fieldOnchange(fieldId,fieldType) {
    //校验评分项唯一性
    if($("#fieldId").val()=="")
        return;
    var url = '../modelScoreField/checkModelScoreFieldRule.html';
    jQuery.ajax({
        type: 'post',
        url: url,
        dataType: 'json',
        async: false,
        data: {
            "modeId": $("#modeId").val(),
            "templateId":$("#templateId").val(),
            "fieldId":$("#fieldId").val(),
            "fieldName":$("#fieldId").find("option:selected").text()
        },
        success : function(result) {
            var bool = result.success;
            if(bool==true){
                initTable(fieldId, fieldType);
            }else{
                showConfirm({
                    icon: 'warning',
                    content: '已存在相同名称评分项，请修改别名！'
                });
                $("#fieldId").find("option:selected").removeAttr("selected");
                $("#option").attr("selected");
                $("#fieldCounts,#paramsTable").hide();
                return false;
            }
        }
    });

}
function initTable(fieldId,fieldType) {
    var fieldNumberUnit = $("#fieldId").find("option:selected").attr("fieldNumberUnit");
    if(fieldNumberUnit != "" && fieldNumberUnit != "：") {
        $("#paramsUnit").html("（单位" + fieldNumberUnit + "）");
    }else{
        $("#paramsUnit").html("");
    }
    fieldType = $("#fieldId").find("option:selected").attr("fieldType");
    $("#fieldCount").find("option:selected").removeAttr("selected");
    $("#nfieldCount").val("");
    if(fieldType == "Decimal" || fieldType == "Number" || fieldType == "Float" || fieldType == "Ratio") {
        //列出数值分段
        $("#fieldCounts").show();
        $("#paramsTable").hide();
        fieldCountOnchange($("#nfieldCount").val());
    }else {
        //列出固定值
        $("#paramTable").empty();
        $("#paramsTable").show();
        $("#fieldCounts").hide();
        if(fieldType == "YesNo"){
            var comment = '';
            comment =
                '<tr>' +
                '<td class="field" width="50%" align="center">'+
                '<input type="text" readonly class="" value="是">' +
                '<input type="hidden" class="" value="1">' +
                '</td>' +
                '<td class="field" width="50%" align="center">' +
                '<input type="text" value = 0 style="text-align: center"/>'+
                '</td>' +
                '</tr>'+
                '<tr>' +
                '<td class="field" width="50%" align="center">'+
                '<input type="text" readonly class="" value="否">' +
                '<input type="hidden" class="" value="0">' +
                '</td>' +
                '<td class="field" width="50%" align="center">' +
                '<input type="text" value = 0 style="text-align: center"/>'+
                '</td>' +
                '</tr>';
            $("#paramTable").append(comment);
        }else{
            var url = "../modelScoreField/getTemplateFieldCodeTable.html";
            jQuery.ajax({
                type : 'post',
                url : url,
                dataType:'json',
                async : false,
                data : {'fieldId':fieldId},
                success : function(data) {
                    if(data.length < 1){
                        showConfirm({
                            icon: 'warning',
                            content: '没有数值项'
                        });
                        return;
                    }
                    var comment = '';
                    for(var i=0;i<data.length;i++) {
                        comment +=
                            '<tr>' +
                            '<td class="field" width="50%" align="center">'+
                            '<input type="text" readonly class="" value='+ data[i].cols.name +'>' +
                            '<input type="hidden" class="" value='+ data[i].cols.value +'>' +
                            '</td>' +
                            '<td class="field" width="50%" align="center">' +
                            '<input type="text" value = 0 style="text-align: center"/>'+
                            '</td>' +
                            '</tr>';
                    }
                    $("#paramTable").append(comment);
                }
            });
        }
    }
}
//数值分段下拉框被改变
function fieldCountOnchange(value) {
    //没有选择显示选项条数
    if(value == "") {
        $("#paramsTable").hide();
        $("#paramTable").empty();
        return;
    }
    $("#paramTable").empty();
    $("#paramsTable").show();
    var comment =
        '<tr class="firstTr">'+
            '<td class="field" width="50%" align="center">'+
                '<input readonly style="width: 42%;" type="text" value="-∞（含）"/>'+
            '—'+
                '<input type="text" maxlength="9" style="width:42%;ime-mode:Disabled" class="number" value=""  onkeydown="displayResult()" onkeyup="displayNext(this,this.value)" />'+
            '</td>'+
            '<td class="field" width="50%" align="center">'+
                '<input type="text" class="" value = 0 />'+
            '</td>'+
        '</tr>'+
        '<tr>'+
            '<td class="field" width="50%" align="center">'+
                '<input readonly style="width: 42%; value=""/>'+
            '—'+
                '<input readonly type="text" style="width:42%; margin-left: 10px" value="+∞（含）"/>'+
            '</td>'+
            '<td class="field" width="50%" align="center">'+
                '<input type="text" class="" value = 0 />'+
            '</td>'+
        '</tr>';
    $("#paramTable").append(comment);
    var tr = "";
    for(var i =0;i < parseInt(value) - 2; i++) {
        tr +=
            '<tr>' +
                '<td class="field" width="50%" align="center">'+
                    '<input readonly style="width: 42%;border: " type="text" value=""/>'+
                    '—'+
                    '<input type="text" maxlength="9" style="width:42%; ime-mode:Disabled" class="number" value="" onkeydown="displayResult()" onkeyup="displayNext(this,this.value)" />'+
                '</td>'+
                '<td class="field" width="50%" align="center">'+
                    '<input type="text" class="" value = 0 />'+
                '</td>' +
            '</tr>';
    }
    $(".firstTr").after(tr);
    banger.verify('.number',{name: 'required', tips: '数值必须填写'});
}
function saveScoreField() {
    var fieldType = $("#fieldId").find("option:selected").attr("fieldType");
    if (!banger.verify('.validateTd')) {
        return false;
    }
    //没有数值项
    if($("#paramTable tr").length < 1) {
        showConfirm({
            icon: 'warning',
            content: '没有数值项'
        });
        return false;
    }

    if(!validata(fieldType)){
        return false;
    }
    save(fieldType);
}

function validata(fieldType){
    var bool = true;
    $("#paramTable tr").each(function (){
        if(fieldType == "Decimal" || fieldType == "Number" || fieldType == "Float" || fieldType == "Ratio") {
            var preValue = $(this).children().eq(0).children().eq(1).val();
            var nextValue = $(this).next().children().eq(0).children().eq(1).val();
            if(preValue == "+∞（含）"){
                return;
            }else{
                if(!reg1.test(preValue)){
                    showConfirm({
                        icon: 'warning',
                        content: '选项区间不正确'
                    });
                    $(this).children().eq(0).children().eq(1).css("color", "#f00").attr('title',"数值不能为空且最多为两位小数,必须为依次增大");
                    bool = false;
                    return false;
                }

            }
            if(nextValue == "+∞（含）"){
                if(!reg1.test(preValue)){
                    showConfirm({
                        icon: 'warning',
                        content: '选项区间不正确'
                    });
                    $(this).children().eq(0).children().eq(1).css("color", "#f00").attr('title',"数值不能为空且最多为两位小数,必须为依次增大");
                    bool = false;
                    return false;
                }
            }else{
                if(!reg1.test(nextValue)){
                    $(this).next().children().eq(0).children().eq(1).css("color", "#f00").attr('title',"数值不能为空且最多为两位小数,必须为依次增大");
                    bool = false;
                    return false;
                }
            }
            if(nextValue != "+∞（含）"){
                if(!(parseFloat(preValue) < parseFloat(nextValue)) || preValue == "" || nextValue == ""){
                    $(this).children().eq(0).children().eq(1).css("color", "#f00").attr('title',"数值不能为空且最多为两位小数,必须为依次增大");
                    $(this).next().children().eq(0).children().eq(1).css("color", "#f00").attr('title',"数值不能为空且最多为两位小数,必须为依次增大");
                    showConfirm({
                        icon: 'warning',
                        content: '数值项不正确'
                    });
                    bool = false;
                    return false;
                }
            }
        }
        var value = $(this).children().eq(1).children().val();
        if(!reg.test(value)){
            $(this).children().eq(1).children().css("color", "#f00");
            $(this).children().eq(1).children().attr('title',"数值不能为空、只能为4位且最多为两位小数");
            showConfirm({
                icon: 'warning',
                content: '分值不正确'
            });
            bool = false;
            return false;
        }
    })
    return bool;
}

function save(fieldType){
    getDatas(fieldType);
    var url = "../modelScoreField/saveModelScoreField.html";
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType:'json',
        async : false,
        data : modeScoreField,
        success : function(data) {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
            var dialog = getDialog('saveModelScoreFieldPage');
            setTimeout(function() {
                dialog.close();
            }, 0);
        }
    });
}
//关闭dialog,并刷新list
function closeDialog(){
    var dialog = getDialog('saveModelScoreFieldPage');
    var win = tabs.getIframeWindow("modelScoreField"+$("#modeId").val());
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0)
};
function getDatas(fieldType){
    modeScoreField.optionId = $("#optionId").val();
    modeScoreField.modeId = $("#modeId").val();
    modeScoreField.templateId = $("#templateId").val();
    modeScoreField.fieldId = $("#fieldId").val();
    modeScoreField.fieldName = $("#fieldId").find("option:selected").text()
    var params = [];
    $("#paramTable tr").each(function (){
        var param = {};
        if(fieldType == "Decimal" || fieldType == "Number" || fieldType == "Float") {
            var firValue = $(this).children().eq(0).children().eq(0).val();
            var secValue = $(this).children().eq(0).children().eq(1).val();
            param.optionParam1 = firValue;
            param.optionParam2 = secValue;
        }else{
            param.optionParam1 = $(this).children().eq(0).children().eq(0).val();
            param.optionParam2 = $(this).children().eq(0).children().eq(1).val();
        }
        var value = $(this).children().eq(1).children().val();
        param.paramScore = value;
        params.push(param);
    })
    modeScoreField.params = JSON.stringify(params);
}
function displayResult(){
    if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
        if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)||(event.keyCode == 109) ||(event.keyCode == 189)
            || (event.keyCode == 110) || (event.keyCode == 190)))
            event.returnValue=false;
}
function displayNext(e,value) {
    // $(e).parent("tr").next("tr td input:nth-child(1)").val(value);
    $(e).parent().parent().next().children().children().eq(0).val(value);
}