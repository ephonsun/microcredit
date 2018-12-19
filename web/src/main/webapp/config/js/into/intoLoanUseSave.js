$(function(){
    $('select').selectbox({});
    //校验
    banger.verify('#useSelect',[
        {name: 'required', tips: '贷款类型必须填写'},
        checkLoanUseNameRule]);


    $('#btnSave').click(function() {
        saveLoanUse();
    });
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('intoLoanUseSavePage');
        dialog.close();
    });

    $("#btnContinue").click(function(){
        saveContinue();
    });


});
function saveLoanUse(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../intoLoanUse/addIntoLoanUse.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
        }
    });
}

//贷款类型名字合法性校验  (长度校验 重复性校验)
var checkLoanUseNameRule = {
    name : 'checkLoanUseNameRule',
    tips : '贷款用途已经存在！',
    rule : function() {
        var rule = this, bool = true, data = getPostFields();
        var url = '../intoLoanUse/checkLoanUseNameRule.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            dataType:'json',
            async : false,
            data : data,
            success : function(result) {
                bool = result.success;
            }
        });
        return bool;
    }
};
//关闭dialog
function closeDialog(){
    var dialog = getDialog('intoLoanUseSavePage');
    var win = tabs.getIframeWindow('loanUse');
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

function saveContinue(){

    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../intoLoanUse/addIntoLoanUse.html',
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            var dialog = getDialog('intoLoanUseSavePage');
            var win = tabs.getIframeWindow('loanUse');
            win.location.reload(true);
            $("#form")[0].reset();
        }
    });
}
$(window).load(function() {

    var options = [];
    $.getJSON("../intoLoanUse/getTrialRules.html",
        function (data) {
            for (var index in data.data) {
                var option = {};
                option['value'] = data.data[index].ruleId;
                option['text'] = data.data[index].ruleName;
                option['inputtext'] =data.data[index].ruleName;
                options.push(option);
            }
        })

    $('#rule').checkboxtext({
        options: options,
        onCheck: function(text, value){
            var reviewData = {};
            var reviewUsers = [];
            for (var i = 0; i< value.length; i++) {
                var user = {};
                user["ruleId"] = value[i];
                user["ruleName"] = text[i];
                reviewUsers.push(user);
            }
            reviewData['users'] = reviewUsers;
            var str="";
            reviewUsers[(reviewUsers.length-1)]
            for(var index in reviewUsers ){
                str+=","+reviewUsers[index].ruleId
            }
            str=str.substr(1);
            $("#ruleId").val(str);
        }
    });
});