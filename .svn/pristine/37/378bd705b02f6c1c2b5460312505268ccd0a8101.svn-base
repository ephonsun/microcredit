$(function(){
    var ruleId="0";
    //初始化下拉单选框
    $('select').selectbox();

    $('#trialRuleinfo').flexigrid({
        height: 300,
        url: '../trialRuleInfo/getTrialRuleInfoList.html',
        fields: [
            { display: '规则名称', field: 'ruleName', width: 300 ,align: 'center'},
        ],
        action: {
            display: '操作',
            width: 250,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '../trialRuleInfo/getTrialRuleInfoInsertPage.html?ruleId='+data.id;
                        showDialog({
                            id: 'editTrialRuleInfo',
                            title : '编辑初审规则名称',
                            url : url,
                            width: 330,
                            height: 120,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '配置',
                    onClick: function(tr, data){
                        var url = '../trialRuleDetail/getTrialRuleDetailListPage.html?ruleId='+data.id;
                        tabs.add({
                            id: 'TrialRuleInfoList_'+data.id,
                            name: 'tableColumnList',
                            pid: tabs.getSelfId(window),
                            title : '表单模版字段列表',
                            url : url,
                            lock : false
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
        var url = '../trialRuleInfo/getTrialRuleInfoInsertPage.html';
        showDialog({
            id: 'trialRuleInfoHandle',
            title: '新建初审规则',
            url: url,
            width: 330,
            height: 120,
            tabId: tabs.getSelfId(window)
        });
    });
    //点击刷新
    $('#btnRefresh').click(function(){
        refresh();
    });

    //刷新
    function refresh() {
        $('#trialRuleinfo').flexReload();
    }
    function deletePotentialCustomer(data){
        showConfirm({
            icon:'confirm',
            content:'您确定要删除初审规则“'+ data.cols.ruleName+ '”吗？删除后将无法恢复。',
            ok:function(){
                jQuery.ajax({
                    type:'POST',
                    url:'../trialRuleInfo/deleterialRuleInfo.html',
                    data:{"ruleId":data.id},
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
})