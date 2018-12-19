var buttonsArray = [
    {
        display: '编辑',
        onClick: function (tr, data) {
            var url = '../SurveyFlow/getSurveyFlowUpdatePage.html?id='+data.id+'&random='+Math.random()*10000;
            showDialog({
                id: 'editSurveyFlow',
                title: '编辑调查流程',
                url: url,
                width: 330,
                height: 100,
                tabId: tabs.getSelfId(window)
            });
        }
    },
    {
        display: '配置',
        onClick: function (tr, data) {
            var url = '../SurveyFlow/getFlowStepList.html?id=' + data.id +'&random='+Math.random()*10000;
            tabs.add({
                id: 'flowStepList' + data.id,
                name: 'flowStepList',
                pid: tabs.getSelfId(window),
                title: '流程配置',
                url: url,
                lock: false
            });
        }
    },

    {
        display: '删除',
        onClick: function (tr, data) {
            showConfirm({
                icon:'confirm',
                content:'您确定要彻底删除该项吗？删除后将无法恢复。',
                ok:function(){
                    jQuery.ajax({
                        url: '../SurveyFlow/deleteSurveyFlowInfo.html?id='+data.id,
                        type:'POST',
                        sync: false,
                        dataType:'json',
                        success: function(result){
                            if(result.success){
                                showConfirm({
                                    icon: 'succeed',
                                    content: result.cause
                                });
                                refresh();
                            }else {
                                showConfirm({
                                    icon: 'warning',
                                    content: result.cause
                                });
                            }
                        }
                    });
                },
                cancel: function() {}
            });
        }
    }
];

$(function () {
    $('#surveyFlowList').flexigrid({
        height: 300,
        url: '../SurveyFlow/querySurveyFlowInfoListData.html',
        dataType: 'json',
        //multiSelect: true,
        fields: [
            {display: '名称', field: 'flowName', width: 300, align: 'center'},
        ],
        action: {
            display: '操作',
            width: 300,
            align: 'center',
            buttons: buttonsArray
        },
        usePage: true
    });

    //新增
    $("#btnAdd").click(function () {
        var url = '../SurveyFlow/getSurveyFlowInsertPage.html?random='+Math.random()*10000;
        showDialog({
            id: 'editSurveyFlow',
            title: '新增调查流程',
            url: url,
            width: 330,
            height: 100,
            tabId: tabs.getSelfId(window)
        });
    });

});
// 刷新人员表
function refresh() {
    $('#surveyFlowList').flexReload();
}