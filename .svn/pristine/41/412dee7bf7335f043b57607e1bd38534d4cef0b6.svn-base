$(function(){
    $('select').selectbox({});
    //校验
    banger.verify('#useSelect',[
        {name: 'required', tips: '贷款类型必须填写'},
        checkLoanUseNameRule]);


    $('#btnUpdate').click(function() {
        updateLoanUse();
    });
    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('intoLoanUseUpdate');
        dialog.close();
    });

});
function updateLoanUse(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        //dataType:'json',
        url : '../intoLoanUse/updateIntoLoanUse.html',
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

//重置
function reset(){
    $("#form")[0].reset();
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
    var dialog = getDialog('intoLoanUseUpdate');
    var win = tabs.getIframeWindow('loanUse');
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}

$(window).load(function() {
    var options = [];
    $.getJSON("../intoLoanUse/getTrialRules.html",
        function (data) {
        //获取ruleId字符串
        var r=$('#ruleId').val();
        //拆分成数组
        var rd=r.split(",");
            for (var index in data.data) {
                var option = {};
                option['value'] = data.data[index].ruleId;
                option['text'] = data.data[index].ruleName;
                option['inputtext'] =data.data[index].ruleName;
                //存在则编辑选中
                for(var d in rd){
                    if(rd[d]==data.data[index].ruleId){
                        option['checked']=true;
                    }
                }

                options.push(option);
            }
        })

    $('#rule').checkboxtext({
        options: options,
        onCheck: function(text, value){
            //debugger
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

function edit(data){

}