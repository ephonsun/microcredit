$(function(){
    $('#grid').flexigrid({
        height: 400,
        url: '../modelScoreInfo/queryModelScoreInfoList.html',
        fields: [
            { display: '模型名称', field: 'modeName', width: 500 ,align: 'center'},
        ],
        action: {
            display: '操作',
            width: 500,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '../modelScoreInfo/getModelScoreInfoUpdatePage.html?modeId='+data.id;
                        showDialog({
                            id: 'modelScoreInfoUpdate',
                            title : '编辑评分模型',
                            url : url,
                            width : 330,
                            height : 100,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '设置评分项',
                    onClick: function(tr, data){
                        var url = '../modelScoreField/getModelScoreFieldPage.html?modeId='+data.id;
                        tabs.add({
                            id: 'modelScoreField'+data.id,
                            name: 'modelScoreField',
                            pid: tabs.getSelfId(window),
                            title : '设置评分项',
                            url : url,
                            lock : false
                        });
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        var url = '../modelScoreInfo/deleteModelScoreInfo.html?modeId='+data.id;
                        showConfirm({
                            icon:'confirm',
                            content:'您确定要删除这条评分吗？删除后将无法恢复！',
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
        var url = '../modelScoreInfo/getModelScoreInfoSavePage.html';
        showDialog({
            id: 'modelScoreInfoSavePage',
            name: 'modelScoreInfoSavePage',
            pid: tabs.getSelfId(window),
            title : '新增评分模型',
            width : 330,
            height : 100,
            url : url,
            lock : true
        });
    });
})