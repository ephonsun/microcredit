// 页面加载完成时...
$(function(){
    $("#btnAddLoanRepayPlanInfo").click(function(){
        addLoanRepayPlanInfo();
    })

    $("#btnRefresh").click(function(){
        refresh();
    });

    $('#btnSynPlans').click(function(){
        var loanId= $('#loanId').val();
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../loanRepayPlanInfo/syncRepaymentPlan.html',
            data: {loanId:loanId},
            async: false,
            success: function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'succeed',
                        content: result.cause
                    });
                }else{
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    });

    // 加载
    $('#loanRepayPlanInfo').flexigrid({
        height: 500,
        url: '../loanRepayPlanInfo/queryLoanRepayPlanInfo.html?loanId=' + $("#loanId").val()+"&showApply="+$("#showApply").val(),
        usePage: true,
        fields: [
            {display: '计划还款日期', field: 'loanRepayPlanDate', width: 100, align: 'center'},
            {display: '应还本金（元）', field: 'loanPrincipalAmountF', width: 100, align: 'center'},
            {display: '应还利息（元）', field: 'loanAccrualAmountF', width: 100, align: 'center'},
            //{display: '已还本金（元）', field: 'loanRepayAmountF', width: 100, align: 'center'},
            //{display: '已还利息（元）', field: 'loanRepayAccrualAmountF', width: 100, align: 'center'},
            {display: '状态', field: 'loanRepayState', width: 100, align: 'center', data: {0: "未还款", 1: "已还款"}},
            //{display: '还款日期', field: 'loanRepayDate', width: 100, align: 'center'},
            //{display: '是否逾期', field: 'loanIsOverdue', width: 100, align: 'center', data: {0: "否", 1: "是",3: ""}},
            {display: '提醒人', field: 'userName', width: 100, align: 'center'},
        ],
        action: {
            display: '操作',
            width: 100,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function (tr, data) {
                        updateLoanRepayPlanInfo(data);
                    },
                    isDisabled: function (data) {
                        // return (!collectionEdit || data.cols.sj || data.cols.loanCollectionState == "CollectionComplete" || data.cols.loanCollectionState == "" || $("#module").val() == 'all' );

                        if($("#showApply").val() == 1){
                            return true;
                        }else{
                            if(data.cols.autoRepay == true)
                                return (!data.cols.collection);
                            else
                                return !data.cols.autoRepay;
                        }

                    }
                }/*,
                 {
                 display: '删除',
                 onClick: function(tr, data){
                 deleteLoanRepayPlanInfo(data);
                 },
                 isDisabled: function(data){
                 return (!collectionDel || data.cols.sj || data.cols.createUser == 1 || data.cols.loanCollectionState == "CollectionComplete" || $("#repaymentMode").val() == "1" || $("#repaymentMode").val() == "2" || $("#module").val() == 'all' );
                 }
                 }*/
            ]
        },
        usePage: false,
        onComplete : function(data) {
            //$('#totalMoney').text('贷款余额：'+data.totalMoney);
        }
    });
});
//跳转编辑
function updateLoanRepayPlanInfo(data){
    var url = '../loanRepayPlanInfo/getUpdatePage.html?id='+data.id+'&loanId='+$("#loanId").val() +'&random='+Math.random()*10000;
    showDialog({
        id: 'updateLoanRepayPlanInfo',
        title: '编辑',
        url: url,
        width: 660,
        height: 340,
        tabId: tabs.getSelfId(window)
    });
}


//跳转新增
function addLoanRepayPlanInfo(){
    var url = '../loanRepayPlanInfo/getAddPage.html?loanId='+$("#loanId").val() +'&random='+Math.random()*10000;
    showDialog({
        id: 'addLoanRepayPlanInfo',
        title: '新增',
        url: url,
        width: 660,
        height: 230,
        tabId: tabs.getSelfId(window)
    });
}

function deleteLoanRepayPlanInfo(data){
    var url = '../loanRepayPlanInfo/deleteById.html?id='+data.id +'&random='+Math.random()*10000;
    showConfirm({
        icon:'confirm',
        content:'您确定要删除这条还款计划吗？删除后将无法恢复！',
        ok:function(){
            jQuery.ajax({
                type:'POST',
                url:url,
                data:{},
                success:function(text){
                    showConfirm({
                        icon: 'succeed',
                        content: '删除成功'
                    });
                    $('#loanRepayPlanInfo').flexReload();
                }
            });
        },
        cancel: function() {}
    });
}
function refresh(){
    $('#loanRepayPlanInfo').flexReload();
}