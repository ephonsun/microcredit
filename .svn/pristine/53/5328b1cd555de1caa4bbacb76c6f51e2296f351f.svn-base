



function saveApproval(loanId, thisProcessId, last) {
    var postJson = getPostFieldsByFilter('#approvalBaseFormApproval');
    var postJsonString = JSON.stringify(postJson);
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanApproval/saveLoanApprovalInfo.html',
        data: {
            "loanId": loanId,
            "thisProcessId": thisProcessId,
            "json" : postJsonString,
            "last" : last
        },
        async: false,
        success: function (result) {
            if (result.success) {
                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                closeApplyTab();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

function refuseApply(loanId, module){
    var url = '../loan/gotoRefusePage.html?loanId='+loanId + '&module='+module + '&random='+Math.random()*10000;;
    showDialog({
        id: 'refuseLoanApply',
        title: '拒绝贷款申请',
        url: url,
        width: 500,
        height: 270,
        tabId: tabs.getSelfId(window)
    });
}

function approvalBack(loanId, module){
    var url = '../loan/backApplyInfo.html?loanId='+loanId + '&module=' + module + '&random='+Math.random()*10000;
    showDialog({
        id: 'backLoanApply',
        title: '审批驳回',
        url: url,
        width: 400,
        height: 150,
        tabId: tabs.getSelfId(window)
    });
}





function approvalPass(loanId){

    var bool = banger.verify("#approvalBaseFormApproval");
    if (!bool) {
        return false;
    }
    var repaymentMode, proposalAmount, proposalLimit, proposalRatio;
    repaymentMode = $("#approvalBaseFormApproval #repaymentMode").val();
    proposalAmount = $("#approvalBaseFormApproval #resultAmount").val();
    proposalLimit = $("#approvalBaseFormApproval #resultLimit").val();
    proposalRatio = $("#approvalBaseFormApproval #resultRatio").val();
    if (!repaymentMode) repaymentMode = '';
    if (!proposalAmount) proposalAmount = '';
    if (!proposalLimit) proposalLimit = '';
    if (!proposalRatio) proposalRatio = '';
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../loanApproval/saveLoanApprovalInfoBefore.html',
        data: {
            "loanId": loanId,
            "repaymentMode": repaymentMode,
            "proposalAmount": proposalAmount,
            "proposalLimit": proposalLimit,
            "proposalRatio": proposalRatio
        },
        async: false,
        success: function (result) {
            if (result.success) {
                    if (result.next) {
                    if (result.random) {
                        var postJsonString = JSON.stringify(result.reviews);
                        showConfirm({
                            icon: 'confirm',
                            content: '您确定要提交审批吗？',
                            ok: function () {
                                var thisProcessId = result.thisProcessId;
                                jQuery.ajax({
                                    type : 'post',
                                    dataType:'json',
                                    url : '../loanApproval/saveExamine.html',
                                    data : {"next":"1","loanId":loanId,"thisProcessId": thisProcessId, "processId" : result.processId,
                                        "flowId" : result.flowId, "paramId" : result.paramId, "reviews": postJsonString},
                                    success : function(result) {
                                        if(result.success){
                                            showConfirm({
                                                icon: 'succeed',
                                                content: result.cause
                                            });
                                            saveApproval(loanId, thisProcessId, '');
                                        }else{
                                            showConfirm({
                                                icon: 'warning',
                                                content: result.cause
                                            });
                                        }
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    } else {
                        var url = '../loanApproval/selectUserForward.html?next=1&loanId='+loanId+ '&thisProcessId=' + result.thisProcessId;
                        showDialog({
                            id: 'submitLoanApproval',
                            title: '提交审批',
                            url: url,
                            width: 400,
                            height: 300,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                } else {
                    saveApproval(loanId, result.thisProcessId, result.last);
                }
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
            }
        }
    });
}

//关闭页卡
function closeApplyTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refreshList){
        win.refreshList();
    }
    tabs.close(tabs.getSelfId(window));
}
