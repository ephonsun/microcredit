$(function () {
    $('select').selectbox({});
    banger.verify('#form',{name: 'required', tips: '进件信息设置'});
    banger.verify($('#form').find($('.ui-star')), [{name: 'required', tips: '进件信息设置'}]);
    banger.verify('#tableName',{name: 'required', tips: '所属模块必须填写'});
    banger.verify('#fieldColumn',{name: 'required', tips: '字段列名必须填写'});
    banger.verify('#fieldIsRequired',{name: 'required', tips: '是否必填必须填写'});

    //保存新建
    $('#btnSave').click(function() {
        saveIntoInFo();
    });

    //关闭新建
    $('#btnCancel').click(function() {
        var dialog = getDialog('addIntoInfo');
        dialog.close();
    });
    // if($!intoAutoTableColumn){
        queryTableName($("#tableName").val(),true);
    // }

});

//获取表
function queryTableName(pid,selected){
    if(selected){
    }else{
        $("#ntableName, #nfieldColumn , #nfieldIsRequired").val("");
        $("#fieldColumn").empty();
    }
    // mutlDefaultValue();
    if(pid == ''){
        return ;
    }
    var url = '../intoTemplatesInfo/getTableName.html';
    var tableName = $("#tableName").val();
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
                    comment +="<option value =" + list[i].cols.fieldColumn +" "+"cloumn="+list[i].cols.fieldColumn + " >" + list[i].cols.fieldColumnDisplay + "</option>";
            }
            $("#fieldColumn").empty().append(comment);
        }
    });
    // $("#controlFormName").val($("#controlForm option:selected").text());
    // //隐藏表单
    // hiddenForm();
}

// //获取字段
// function queryfieldColumn(pid,selected) {
//     // $("#controlItemColumn").val($("#controlItem option:selected").attr("cloumn"));
//     // $("#fieldIsRequired").empty();
//     if(pid == ''){
//         return ;
//     }
//     var url = '../intoTemplatesInfo/getFieldColumn.html';
//     var fieldColumn = $("#fieldColumn").val();
//     jQuery.ajax({
//         type: 'POST',
//         url: url,
//         dataType: 'json',
//         async : false,
//         data : {'fieldColumn' : fieldColumn},
//         success : function(data) {
//         }
//     });
// }

//保存表单设置
function  saveIntoInFo() {
    if (!banger.verify('#form')) {
        return false;
    }
    var url = '../intoTemplatesInfo/insertIntoInfo.html';
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
                closeDialog('addIntoInfo');
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