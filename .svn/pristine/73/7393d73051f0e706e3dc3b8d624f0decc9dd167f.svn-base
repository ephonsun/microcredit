
var modeScoreField = {};
var optionParam11="";
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
    if(isEdit == 1){
        $("#fieldCounts").show();
        // $("#fieldId,#templateId").attr("disabled","disabled");
        // var fieldtype = $("#fieldId").find("option:selected").attr("fieldType");
        // if(fieldtype == "Decimal" || fieldtype == "Number"){
        //
        // }else{
        //     $("#fieldCounts").hide();
        // }
    }else{
        $("#fieldCounts").hide();
    }
    banger.verify('#templateId',{name: 'required', tips: '模块名称必须选择'});
    banger.verify('#fieldId',{name: 'required', tips: '字段名必须选择'});
    banger.verify('.number',{name: 'required', tips: '数值必须填写'});
    banger.verify('#optionParam1',{name: 'required', tips: '该项必须填写'});
    $('select').selectbox();

    $("#btnSave").click(function(){
        if($("#optionParam1").val()!="")
        {
            saveScoreField();
        }else {

        }
    });

    $("#btnCancel").click(function(){
        var dialog;
        if ($("#isedit").val()=="1")
        {
            dialog = getDialog('editTrialRuleDetail');
        }else {
            dialog = getDialog('trialRuleDetailHandle');
        }
        setTimeout(function() {
            dialog.close();
        }, 0);
    });
});
function queryModelScoreFieldByPid(pid) {
    if(pid == ""){
        $("#fieldId").empty().append("<option></option>");
        return;
    }

    // $("#paramTable").empty().hide();
    var url = '../trialRuleDetail/queryTrialRuleDetailByPid.html';
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType:'json',
        async : false,
        data : {'templateId':pid},
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            if(list.length>0)
            {
                for(var i = 0;i < list.length; i++){
                    comment +="<option fieldType = " + list[i].cols.fieldType + " value =" + list[i].id +" >" + list[i].cols.fieldName + "</option>";
                }

            }else{
                showConfirm({
                    icon: 'warning',
                    content: '该项无可配字段'
                });
                $("#isNumber").empty();
            }
            $("#fieldId").empty().append(comment);
        }
    });
}
var issave=1; //判断是否可以提交
//根据不同的field显示不同的内容
function fieldOnchange(fieldId,fieldType) {
    //校验评分项唯一性
    if($("#fieldId").val()=="")
        return;
    var url = '../trialRuleDetail/checkTrialRuleDetail.html';
    jQuery.ajax({
        type: 'post',
        url: url,
        dataType: 'json',
        async: false,
        data: {
            "optionId": $("#optionId").val(),
            "ruleId": $("#ruleId").val(),
            "templateId":$("#templateId").val(),
            "fieldId":$("#fieldId").val()
        },
        success : function(result) {
            var bool = result.success;
            if(bool==true){
                initTable(fieldId, fieldType);
                issave=1;
            }else{
                showConfirm({
                    icon: 'warning',
                    content: '该规则已经存在'
                });
                $("#isNumber").empty();
                issave=0;
                $("#fieldId").find("option:selected").removeAttr("selected");
                $("#option").attr("selected");

                return false;
            }
        }
    });

}
//如果是编辑  就先执行一次
var isEdit = $("#isEdit").val();
if(isEdit==1)
{
    var options = [];
    jQuery.ajax({
        type: 'post',
        url: "../trialRuleDetail/getFiledDictionary.html",
        dataType: 'json',
        async: false,
        data: {
            "fieldId": $("#fieldId").val()
        },
        success: function (result) {
            var bool = result.success;
            if (bool == true) {
                var ids=$("#optionParam1").val();
                var strs=ids.split(",");
                $.each(result.value, function (n, item) {
                    var option = {};
                    option['value'] = item.value;
                    option['text'] = item.text;
                    option['inputtext'] = item.text;
                    for(i=0;i<strs.length ;i++ )
                    {
                        if(strs[i]==item.value)
                        {
                            option["checked"]=true;
                        }
                    }
                    options.push(option);
                });

            } else {
                showConfirm({
                    icon: 'warning',
                    content: '未知错误'
                });
            }
        }
    });

    $('#rule').checkboxtext({
        options: options,
        onCheck: function (text, value) {
            var reviewData = {};
            var reviewUsers = [];
            for (var i = 0; i < value.length; i++) {
                var user = {};
                user["ruleId"] = value[i];
                user["ruleName"] = text[i];
                reviewUsers.push(user);
            }
            reviewData['users'] = reviewUsers;
            var str = "";
            reviewUsers[(reviewUsers.length - 1)]
            for (var index in reviewUsers) {
                str += "," + reviewUsers[index].ruleId
            }
            str = str.substr(1);
            $("#optionParam1").val(str);
        }
    });
}

//根据类型 找到字段类型 展示不同的框
function initTable(fieldId,fieldType) {
    fieldType = $("#fieldId").find("option:selected").attr("fieldType");
    $("#fieldCount").find("option:selected").removeAttr("selected");
    $("#nfieldCount").val("");
    $("#fieldCounts").show();
    $("#isNumber").empty();
    var comment = "";
    if (fieldType == "Number") {
        comment = " <input style=\"width: 32%;\" valid=\"posiNumber\"  class=\"number\" type=\" text\" id=\"optionParam1\" name=\"optionParam1\"maxlength= \"9\"/>"
            + "—"
            + "<input type=\"text\"  valid=\"required\"  style=\"width:32%;\" class=\"number\" maxlength=\"9\" id=\"optionParam2\" name=\"optionParam2\"/>";
        $("#isNumber").append(comment);
    } else {
         comment = " <div class=\"ui-text\">"
       +" <input type=\"hidden\" id=\"optionParam1\"  name=\"optionParam1\"/>"
          +"  <input type=\"text\" id=\"rule\" class=\"ui-text-text\"  />"
            +"<i class=\"ui-text-icon ui-text-select \"></i>"
            +"</div>";
        $("#isNumber").append(comment);
        var options = [];
        jQuery.ajax({
            type: 'post',
            url: "../trialRuleDetail/getFiledDictionary.html",
            dataType: 'json',
            async: false,
            data: {
                "fieldId": $("#fieldId").val()
            },
            success: function (result) {
                var bool = result.success;
                if (bool == true) {
                    $.each(result.value, function (n, item) {
                        var option = {};
                        option['value'] = item.value;
                        option['text'] = item.text;
                        option['inputtext'] = item.text;
                        options.push(option);
                    });

                } else {
                    showConfirm({
                        icon: 'warning',
                        content: '未知错误'
                    });
                }
            }
        });

        $('#rule').checkboxtext({
            options: options,
            onCheck: function (text, value) {
                var reviewData = {};
                var reviewUsers = [];
                for (var i = 0; i < value.length; i++) {
                    var user = {};
                    user["ruleId"] = value[i];
                    user["ruleName"] = text[i];
                    reviewUsers.push(user);
                }
                reviewData['users'] = reviewUsers;
                var str = "";
                reviewUsers[(reviewUsers.length - 1)]
                for (var index in reviewUsers) {
                    str += "," + reviewUsers[index].ruleId
                }
                str = str.substr(1);
                $("#optionParam1").val(str);
            }
        });
    }
}


    function saveScoreField() {
        var fieldType = $("#fieldId").find("option:selected").attr("fieldType");
        if (!banger.verify('#optionParam1')) {
            return false;
        }
        //判断 如果是数字的话 就不用判断了  但是 如果不是数字 就要判断 是否选择
        if (fieldType != "Number") {
            if(issave==1)
            {
                if ($("#optionParam1").val() == "") {
                    showConfirm({
                        icon: 'warning',
                        content: '请选择通过选项'
                    });
                    return false;
                }else {
                    issave=1;
                }
            }

        }else if( $("#optionParam1").val() != ""&& $("#optionParam2").val() != ""){
            var re = /^[0-9]+$/ ;
            if(re.test($("#optionParam1").val())&& re.test($("#optionParam2").val()))
            {
                if($("#optionParam1").val()>=$("#optionParam2").val())
                {
                    showConfirm({
                        icon: 'warning',
                        content: '请输入正确区间'
                    });
                    issave=0;
                }else {
                    issave=1;
                }
            }else {
                showConfirm({
                    icon: 'warning',
                    content: '请输入数字'
                });
                issave=0;

            }

        }else{
            issave=0;
            showConfirm({
                icon: 'warning',
                content: '请输入数值区间'
            });
        }
        if(issave==1)
        {
            save(fieldType);
        }

    }

//保存
    function save(fieldType) {
        getDatas(fieldType);
        var url = "../trialRuleDetail/getTrialRuleDetailInsert.html";
        jQuery.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            async: false,
            data: {
                "optionParam1": $("#optionParam1").val(),
                "optionParam2": $("#optionParam2").val(),
                "optionId": $("#optionId").val(),
                "ruleId": $("#ruleId").val(),
                "templateId": $("#templateId").val(),
                "fieldId": $("#fieldId").val()
            },
            success: function (data) {
                showConfirm({
                    icon: 'succeed',
                    content: '操作成功'
                });
                closeDialog();
                var dialog;
                if ($("#isedit").val()=="1")
                {
                     dialog = getDialog('editTrialRuleDetail');
                }else {
                    dialog = getDialog('trialRuleDetailHandle');
                }

                setTimeout(function () {
                    dialog.close();
                }, 0);
            }
        });
    }

//关闭dialog,并刷新list
    function closeDialog() {
        var dialog;
        if ($("#isedit").val()=="1")
        {
            dialog = getDialog('editTrialRuleDetail');
        }else {
            dialog = getDialog('trialRuleDetailHandle');
        }
        var win = tabs.getIframeWindow("TrialRuleInfoList_" + $("#ruleId").val());
        win.location.reload(true);
        setTimeout(function () {
            dialog.close();
        }, 0)
    };
    function getDatas(fieldType) {
        modeScoreField.optionId = $("#optionId").val();
        modeScoreField.modeId = $("#modeId").val();
        modeScoreField.templateId = $("#templateId").val();
        modeScoreField.fieldId = $("#fieldId").val();
        var params = [];
        $("#paramTable tr").each(function () {
            var param = {};
            if (fieldType == "Decimal" || fieldType == "Number") {
                var firValue = $(this).children().eq(0).children().eq(0).val();
                var secValue = $(this).children().eq(0).children().eq(1).val();
                param.optionParam1 = firValue;
                param.optionParam2 = secValue;
            } else {
                param.optionParam1 = $(this).children().eq(0).children().eq(0).val();
                param.optionParam2 = $(this).children().eq(0).children().eq(1).val();
            }
            var value = $(this).children().eq(1).children().val();
            param.paramScore = value;
            params.push(param);
        })
        modeScoreField.params = JSON.stringify(params);
    }

    function displayResult() {
        if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39))
            if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode == 109) || (event.keyCode == 189)
                || (event.keyCode == 110) || (event.keyCode == 190)))
                event.returnValue = false;
    }

    function displayNext(e, value) {
        // $(e).parent("tr").next("tr td input:nth-child(1)").val(value);
        $(e).parent().parent().next().children().children().eq(0).val(value);
    }