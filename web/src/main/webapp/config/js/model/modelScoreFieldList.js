$(function(){
    $('#grid').flexigrid({
        height: 400,
        url: '../modelScoreField/queryModelScoreInfoFieldList.html?modeId='+$("#modeId").val(),
        fields: [
            { display: '表单模块', field: 'tableNameCn', width: 300 ,align: 'center'},
            // { display: '评分项', field: 'fieldName', width: 300 ,align: 'center'}
            { display: '评分项名称', field: 'fieldDisplay', width: 300 ,align: 'center'}
        ],
        action: {
            display: '操作',
            width: 300,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '../modelScoreField/saveModelScoreFieldPage.html?modeId='+$("#modeId").val()+'&&optionId='+data.id;
                        showDialog({
                            id: 'saveModelScoreFieldPage',
                            name: 'saveModelScoreFieldPage',
                            title : '编辑评分项',
                            url : url,
                            width : 600,
                            height : 300,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        var url = '../modelScoreField/deleteModelScoreInfoField.html?optionId='+data.id;
                        showConfirm({
                            icon:'confirm',
                            content:'您确定要删除这条评分项吗？删除后将无法恢复！',
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

    //新增评分项
    $("#btnNew").click(function(){
        var url = '../modelScoreField/saveModelScoreFieldPage.html?modeId='+$("#modeId").val()+"&&optionId";
        showDialog({
            id: 'saveModelScoreFieldPage',
            name: 'saveModelScoreFieldPage',
            pid: tabs.getSelfId(window),
            title : '新增评分项',
            width : 600,
            height : 300,
            url : url
        });
    });
    //跳转复制页
    $("#btnCopy").click(function(){
        var url = '../modelScoreField/getCopyModelScoreInfoPage.html?modeId='+$("#modeId").val();
        showDialog({
            id: 'modelScoreInfoCopyPage',
            name: 'modelScoreInfoCopyPage',
            pid: tabs.getSelfId(window),
            title : '选择模型',
            width : 330,
            height : 300,
            url : url,
            lock : true
        });
    });
})