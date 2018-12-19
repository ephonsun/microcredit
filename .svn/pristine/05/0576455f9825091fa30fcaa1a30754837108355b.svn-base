$(function(){
    $('#grid').flexigrid({
        height: 400,
        url: '../intoLoanUse/queryIntoLoanUseList.html',
        fields: [
            { display: '选项', field: 'useSelect', width: 250 ,align: 'center'},
            { display: '贷款类型', field: 'loanTypeName', width: 250 ,align: 'center'},
            { display: '初审规则', field: 'ruleName', width: 250 ,align: 'center'}
        ],
        action: {
            display: '操作',
            width: 350,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '../intoLoanUse/getIntoLoanUseUpdatePage.html?useId='+data.id;
                        showDialog({
                            id: 'intoLoanUseUpdate',
                            title : '编辑贷款用途',
                            url : url,
                            width : 660,
                            height : 150,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        var url = '../intoLoanUse/deleteIntoLoanUse.html?useId='+data.id;
                        showConfirm({
                            icon:'confirm',
                            content:'您确定要删除这条用途吗？删除后将无法恢复！',
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
                                        $('#grid').flexReload();
                                    }
                                });
                            },
                            cancel: function() {}
                        });
                    }
                }
            ]
        },
        onComplete : function(data) {
        },
        usePage: false
    });

    //跳转新增页面
    $("#btnNew").click(function(){
        var url = '../intoLoanUse/getIntoLoanUseSavePage.html';
        showDialog({
            id: 'intoLoanUseSavePage',
            name: 'intoLoanUseSavePage',
            pid: tabs.getSelfId(window),
            title : '新增贷款用途',
            width : 660,
            height : 150,
            url : url,
            lock : true
        });
    });
})