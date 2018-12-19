var users = [];
var overLimit = [];
var limitEn = [];
$(function(){
    //渲染下拉框


    $.each(reviews, function (index, row) {
        overLimit[index] = false;
        limitEn[index] = true;
        if ('SELECT_USER' == row.loanExamineType) {
            var options = [];
            $.each(row.users, function (index2, row2) {
                var option = {};
                option['value'] = row2.userId;
                option['text'] = "<div style='float: left'>" + row2.userName + "</div><div style='float: right; margin-right: 10px;' >额度：" + row2.limitAmount + "</div> ";
                option['inputtext'] = row2.userName;
                option['limitEnable'] = row2.limitEnable;
                options.push(option);
            });
            var id = index +1;
            id = '#selectUsers' + id;
            banger.verify(id , {name : 'required', tips : '审批人员必须选择'});
            $(id).checkboxtext({
                options: options,
                onCheck: function(text, value, limitEnable){
                    var l = false;
                    $.each(limitEnable, function (index, le) {
                        if (le == 'true'){
                            l = true;
                        }
                    });
                    limitEn[index] = l;
                    if (value.length != row.reviewCount) {
                        overLimit[index] = true;
                    } else {
                        overLimit[index] = false;
                        var reviewData = {};
                        var reviewUsers = [];
                        for (var i = 0; i< value.length; i++) {
                            var user = {};
                            user["userId"] = value[i];
                            user["userName"] = text[i];
                            reviewUsers.push(user);
                        }
                        reviewData['users'] = reviewUsers;
                        users[index] = reviewData;
                    }
                    banger.verify("#form");
                }
            });
        } else {
            users[index] = row;
        }
    })


    $('#btnConfirm').click(function() {
        saveSign();
    });


    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('submitLoanApproval');
        dialog.close();
    });

});



function saveSign(){
    var bool = banger.verify("#form");
    if (!bool) {
        return false;
    }
    var limit = false;
    $.each(overLimit, function (index, value) {
        if (value){
            limit = true;
        }
    });
    var limite = false;
    $.each(limitEn, function (index, value) {
        if (!value) {
            limite = true;
        }
    });

    if (limit) {
        showConfirm({
            icon: 'warning',
            content: '选择人数不对'
        });
        return false;
    } else if (limite) {
        showConfirm({
            icon: 'warning',
            content: '必须选择一个符合限额条件的审核人员'
        });
        return false;
    } else {
        var reviewsJson =JSON.stringify(users);
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../loanApproval/saveExamine.html',
            data: {
                "loanId": $("#loanId").val(),
                "processId": $("#processId").val(),
                "thisProcessId": $("#thisProcessId").val(),
                "flowId": $("#flowId").val(),
                "paramId": $("#paramId").val(),
                "reviews": reviewsJson,
                "postJsonString": $("#postJsonString").val(),
                // "approvalOpinion": $("#approvalOpinion").val(),
                // "loanMode": $("#loanMode").val(),
                // "repaymentMode": $("#repaymentMode").val(),
                "next": $("#next").val()
                // "resultAmount": $("#resultAmount").val(),
                // "resultLimit": $("#resultLimit").val(),
                // "resultRatio": $("#resultRatio").val(),
                // "repaymentDate": $("#repaymentDate").val()
            },
            success: function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                    closeDialog();
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    }
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('submitLoanApproval');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.saveApproval){
        win.saveApproval($("#loanId").val(), $("#thisProcessId").val(), '');
    }
    if( win && win.closeInvestigate){
        win.closeInvestigate();
    }
    if( win && win.refreshList){
        win.refreshList();
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}