$(function () {
    //关闭编辑
    $('#btnEditCancel').click(function() {
        var dialog = getDialog('editIntoInfo');
        dialog.close();
    });

    //保存编辑
    $("#btnEditSave").click(function () {
        updateIntoInfo();
    });
    // queryTableName($('#tableName1').val(),$('#controlItem1').val());

    // mutlDefaultValue();
});

//获取控制项
function queryTableName(thisval, selected){
    if(!selected){
        $("#ntableName, #nfieldColumn , #nfieldIsRequired").val("");
        $("#fieldColumn").empty();
    }
    var url = '../intoTemplatesInfo/getTableName.html';
    var tableName = thisval;
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType: 'json',
        async : false,
        data : {'tableName' : tableName},
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option value =" + list[i].cols.fieldColumn
                    +" "+"cloumn="+list[i].cols.fieldColumn ;
                if (selected && selected == list[i].cols.fieldColumn)
                    comment +=' selected ';
                comment+= " >" + list[i].cols.fieldColumnDisplay + "</option>";
            }
            $("#fieldColumn").empty().append(comment);
        }
    });
    // $("#controlFormName").val($("#controlForm option:selected").text());
    // //隐藏表单
    // // hiddenForm();
}

//获取控制值
// function queryControlValue() {
//     $("#controlItemColumn").val($("#controlItem option:selected").attr("cloumn"));
//     $("#ncontrolValue , #mutlDefaultValue ,#defaultValue,#controlValue").val("");
//     $("#controlValue").empty()
//
//     var url = '../SysFormSettings/getFormControlValue.html';
//     var controlItem = $("#controlItem").val();
//     jQuery.ajax({
//         type: 'POST',
//         url: url,
//         dataType: 'json',
//         async : false,
//         data : {'controlItem' : controlItem},
//         success : function(data) {
//             var comment="<option></option>";
//             var list = data.rows;
//             for(var i = 0;i<list.length;i++){
//                 comment += "<option value="+list[i].cols.dictValue+">"+list[i].cols.dictName+"</option>";
//             }
//             $("#controlValue").empty().append(comment);
//             mutlDefaultValue();
//         }
//     });
//     $("#controlItemName").val($("#controlItem option:selected").text());
// }

//保存表单设置
function  updateIntoInfo() {
    if (!banger.verify('#form')) {
        return false;
    }
    var url = '../intoTemplatesInfo/updateIntoInfo.html';
    // $("#hiddenFormName").val($("#hiddenForm option:selected").text());
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
                closeDialog('editIntoInfo');
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