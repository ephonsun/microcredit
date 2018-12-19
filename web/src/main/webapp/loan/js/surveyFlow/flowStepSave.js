$(function(){
    banger.verify('.subStanceCla', {name : 'required'});
    banger.verify('#stepName', {name : 'maxlength'});
    banger.verify('#stepName', {name : 'required'});
    flushList();
});

$("#AddSubStance").click(function(){
    $("#subStanceTable").append('<tr>\n' +
        '                      <td style="width: 75%; text-align: center;"><div style="width: 100%;height: 30px;padding:0px"><input type="hidden" value="" name="subId"><input type="text" name="subStance" value=""  class="subStanceCla" style="width: 100%;height: 30px;line-height: 30px;display:block;border:none;text-align: center" required="true" maxlength="45"></div></td>\n' +
        '                      <td style="width: 25%; text-align: center;" class="tdoverflow"><div  mark="action">\n' +
        '                          <label class="ui-link mr5" mark="action" data="0" button="0" onclick="removeSub(this)">删除</label>\n' +
        '                          <label class="ui-link mr5" mark="action" data="0" button="1" onclick="upSub(this)">上移</label>\n' +
        '                          <label class="ui-link mr5" mark="action" data="0" button="2" onclick="downSub(this)">下移</label>\n' +
        '                      </div></td>\n' +
        '                   </tr>');

    flushList();
});

function addFlowStep(){
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var stepName = $("#stepName").val();
    var stepId = $("#stepId").val();
    var flowId = $("#flowId").val();

    var postJsonArr=[];
    $("#subStanceTable").find("tr").each(function(){
        var postJson={};
        var tdArr = $(this).children();
        var subId = tdArr.eq(0).find('input[name=subId]').val();
        var subStance= tdArr.eq(0).find('input[name=subStance]').val();
        var sortNo = tdArr.eq(0).parent()[0].rowIndex;
        postJson['subId'] = subId;
        postJson['subStance'] = subStance;
        postJson['sortNo'] = sortNo;
        postJsonArr.push(postJson);
    });
    var dataList = JSON.stringify(postJsonArr) ;
    jQuery.ajax({
        type: "post",
        data: {"dataList":dataList,"stepName":stepName},
        url: "../flowStep/insertFlowStep.html?stepId="+stepId+"&flowId="+flowId,
        dataType: "json",
        success: function(result){
            if(result.success){
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeDialog();
            }else{
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
                closeDialog();
            }
        }
    });
}

//上移
function upSub(obj){
    var $tr=$(obj).parents('tr');
    if ($tr.index() != 0) {
        $tr.fadeOut().fadeIn();
        $tr.prev().before($tr);
    }
    flushList();
};

//下移
function downSub(obj) {
    var len = $("#subStanceTable").find("tr").length;
    var $tr = $(obj).parents("tr");
    if ($tr.index() != len - 1) {
        $tr.fadeOut().fadeIn();
        $tr.next().after($tr);
    }
    flushList();
}

function removeSub(obj){
    var $tr = $(obj).parents("tr");
    var subId = $tr.children().eq(0).find('input[name=subId]').val();
    if(null == subId || subId == ""){
        $tr.remove();
        flushList();
    }else {
        jQuery.ajax({
            type: "post",
            url: "../LoanFlowStepContent/deleteFlowStepContent.html?subId=" + subId,
            dataType: "json",
            success: function (result) {
                if (result) {
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    })
                    $tr.remove();
                    flushList();
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    })
                }
            }

        })
    }
}


//取消
$('#btnClose').click(function () {
    var dialog = getDialog('editFlowStep');
    dialog.close();
});

//刷新列表（为了做上下移动的禁止效果）
function flushList() {
    var tlength = $("#subStanceTable").find("tr").length;
    $("#subStanceTable").find("tr").each(function () {
        var tdArr = $(this).children();
        if(tlength == 1) {
            var label1 = tdArr.eq(1).children().children().eq(1);
            var label2 = tdArr.eq(1).children().children().eq(2);
            label1.attr("class", "ui-link mr5 ui-link-disabled");
            label1.attr("disabled", "disabled");
            label2.attr("class", "ui-link mr5 ui-link-disabled");
            label2.attr("disabled", "disabled");
        }else if(tlength > 1){
            if (tdArr.eq(0).parent()[0].rowIndex == 0) {
                var label1 = tdArr.eq(1).children().children().eq(1);
                var label2 = tdArr.eq(1).children().children().eq(2);
                label1.attr("class","ui-link mr5 ui-link-disabled");
                label1.attr("disabled","disabled");
                label2.attr("class", "ui-link mr5");
                label2.removeAttr("disabled");
            }else if(tdArr.eq(0).parent()[0].rowIndex == tlength-1){
                var label1 = tdArr.eq(1).children().children().eq(1);
                var label2 = tdArr.eq(1).children().children().eq(2);
                label1.attr("class","ui-link mr5");
                label1.removeAttr("disabled");
                label2.attr("class","ui-link mr5 ui-link-disabled");
                label2.attr("disabled","disabled");
            }else {
                var label1 = tdArr.eq(1).children().children().eq(1);
                var label2 = tdArr.eq(1).children().children().eq(2);
                label1.attr("class", "ui-link mr5");
                label1.removeAttr("disabled");
                label2.attr("class", "ui-link mr5");
                label2.removeAttr("disabled");
            }

        }

    })
}

//关闭dialog
function closeDialog() {
    var dialog = getDialog('editFlowStep');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload(true);
    setTimeout(function () {
        dialog.close();
    }, 0);
}