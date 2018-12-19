$(function () {
    $('select').selectbox({});
    banger.verify('#form',{name: 'required', tips: '表单关联设置'});
    banger.verify($('#form').find($('.ui-star')), [{name: 'required', tips: '表单关联设置'}]);
    banger.verify('#controlForm',{name: 'required', tips: '控制表单必须填写'});
    banger.verify('#controlItem',{name: 'required', tips: '控制项必须填写'});
    banger.verify('#mutlDefaultValue',{name: 'required', tips: '控制值必须填写'});
    banger.verify('#hiddenForm',{name: 'required', tips: '隐藏表单必须填写'});

    //关闭编辑
    $('#btnEditCancel').click(function() {
        var dialog = getDialog('editFormSettings');
        dialog.close();
    });

    //保存编辑
    $("#btnEditSave").click(function () {
        updateFormSettings();
    });
    queryControlItem($('#controlForm1').val(),$('#controlItem1').val());

    mutlDefaultValue();
});

//获取控制项
function queryControlItem(thisval, selected){
    if(!selected){
        $("#ncontrolItem, #ncontrolValue , #nhiddenForm, #mutlDefaultValue,#defaultValue,#controlValue").val("");
        $("#controlItem, #controlValue, #hiddenForm").empty();
        mutlDefaultValue();
    }
    var url = '../SysFormSettings/getFormControlItem.html';
    var controlForm = thisval;
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType: 'json',
        async : false,
        data : {'controlForm' : controlForm},
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option value =" + list[i].cols.fieldDictName
                    +" "+"cloumn="+list[i].cols.fieldColumn ;
                if (selected && selected == list[i].cols.fieldDictName)
                    comment +=' selected ';
                        comment+= " >" + list[i].cols.fieldColumnDisplay + "</option>";
            }
            $("#controlItem").empty().append(comment);
        }
    });
    $("#controlFormName").val($("#controlForm option:selected").text());
    //隐藏表单
    hiddenForm();
}

//获取控制值
function queryControlValue() {
    $("#controlItemColumn").val($("#controlItem option:selected").attr("cloumn"));
    $("#ncontrolValue , #mutlDefaultValue ,#defaultValue,#controlValue").val("");
    $("#controlValue").empty()

    var url = '../SysFormSettings/getFormControlValue.html';
    var controlItem = $("#controlItem").val();
    jQuery.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        async : false,
        data : {'controlItem' : controlItem},
        success : function(data) {
            var comment="<option></option>";
            var list = data.rows;
            for(var i = 0;i<list.length;i++){
                comment += "<option value="+list[i].cols.dictValue+">"+list[i].cols.dictName+"</option>";
            }
            $("#controlValue").empty().append(comment);
            mutlDefaultValue();
        }
    });
    $("#controlItemName").val($("#controlItem option:selected").text());
}

//隐藏表单
function hiddenForm() {
    var url = '../SysFormSettings/getHiddenForm.html';
    var controlForm = $("#controlForm").val();
    var hiddenForm = $("#hiddenForm").val();
    var hiddenFormName = $("#hiddenFormName").val();
    jQuery.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        async : false,
        data : {'controlForm' : controlForm},
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option value =" + list[i].cols.tableDbName ;
                if (hiddenForm && hiddenForm == list[i].cols.tableDbName)
                    comment +=' selected ';
                comment+=" >" + list[i].cols.tableDisplayName + "</option>";
            }
            $("#hiddenForm").empty().append(comment);
        }
    });
}


//多选下拉处理
function mutlDefaultValue() {
    var setting = {};
    var elem = $("#mutlDefaultValue");
    var selOption = $("#controlValue");

    if(selOption){
        setting["options"] = [];
        var selValues = "," + $('#defaultValue').val() + ",";
        $("#controlValue option").each(function () {
            var op = $(this);
            if(op.val()) {
                var checked = selValues.indexOf(","+op.val()+",") > -1 ? true : false;
                setting["options"].push({"text": op.text(), "value": op.val(), "checked": checked});
            }
        });
    }else{
        setting["options"] = eval("");
    }

    //解析多选下拉对应的选择值
    var selVal = $('#defaultValue').val();
    if (selVal) {
        var selValues = selVal.split(',');
        setting["selected"] = selValues;
        var curOpts = setting["options"];
        var selText = '';
        for (var i = 0; i < curOpts.length; i++) {
            for (var j = 0; j < selValues.length; j++) {
                if (curOpts[i].value == selValues[j]) {
                    selText = selText + curOpts[i].text + ',';
                }
            }
        }
        selText = selText.substring(0, selText.length - 1);
        elem.val(selText);
    }

    setting["onCheck"] = function (text, value) {
        $('#defaultValue').val(value);
    };
    $(elem).checkbox(setting);
};

//保存表单设置
function  updateFormSettings() {
    if (!banger.verify('#form')) {
        return false;
    }
    var id = $("#id").val();
    var url = '../SysFormSettings/updateFormSettings.html?id='+id;
    $("#hiddenFormName").val($("#hiddenForm option:selected").text());
    var postJson = getPostFields();
    jQuery.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        async: false,
        data: postJson,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog('editFormSettings');
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}


//关闭dialog
function closeDialog(monitorInfo){
    var dialog = getDialog(monitorInfo);
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}