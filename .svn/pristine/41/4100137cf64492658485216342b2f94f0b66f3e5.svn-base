$(function(){
    var ruleId="0";
    //初始化下拉单选框
    $('select').selectbox();
    ruleId=$("#ruleId").val();
    $('#trialRuleDetail').flexigrid({
        height: 300,
        url: '../trialRuleDetail/getTrialRuleDetailList.html?ruleId='+ruleId,
        fields: [
            { display: '所属模块', field: 'tableName', width: 100 ,align: 'center'},
            { display: '字段名', field: 'fieldName', width: 100 ,align: 'center'},
            { display: '通过项', field: 'optionValue', width: 300 ,align: 'center'}
        ],
        action: {
            display: '操作',
            width: 250,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '/trialRuleDetail/getTrialRuleDetailInsertPage.html?optionId='+data.id+"&ruleId="+$("#ruleId").val();
                        showDialog({
                            id: 'editTrialRuleDetail',
                            title : '编辑初审规则',
                            url : url,
                            width: 660,
                            height: 180,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        deletePotentialCustomer(data);
                    }
                }
            ]
        },
        onComplete : function(data) {
            $('#lblStatisticsSR').text(data.total);
        },
        usePage: true
    });
    //新增
    $("#btnAdd").click(function() {
        var url = '../trialRuleDetail/getTrialRuleDetailInsertPage.html?ruleId='+$("#ruleId").val();
        showDialog({
            id: 'trialRuleDetailHandle',
            title: '新建初审规则',
            url: url,
            width: 660,
            height: 180,
            tabId: tabs.getSelfId(window)
        });
    });

    function deletePotentialCustomer(data){
        showConfirm({
            icon:'confirm',
            content:'您确定要删这条记录吗？删除后将无法恢复。',
            ok:function(){
                jQuery.ajax({
                    type:'POST',
                    url:'../trialRuleDetail/deleTrialRuleDetail.html',
                    data:{"optionId":data.id},
                    success:function(text){
                        if ("SUCCESS" == text) {
                            showConfirm({
                                icon: 'succeed',
                                content: '删除成功'
                            });
                            refresh();
                        } else {
                            showConfirm({
                                icon: 'warning',
                                content: '删除失败'
                            });
                            refresh();
                        }
                    }
                });
            },
            cancel: function() {}
        });
    }

    //刷新
    function refresh() {
        var win = tabs.getIframeWindow("TrialRuleInfoList_" + $("#ruleId").val());
        win.location.reload(true);
    }
})