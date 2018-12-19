$(function(){
    //初始化下拉单选框
    $('select').selectbox();

    $('#grid').flexigrid({
        height: 400,
        url: '../modelTemplateInfo/queryModelTemplateInfoColunm.html',
        fields: [
            { display: '所属模块', field: 'tableName', width: 300 ,align: 'center'},
            { display: '评分项', field: 'fieldName', width: 300 ,align: 'center'},
            { display: '显示名称', field: 'fieldDisplay', width: 300 ,align: 'center'}
        ],
        onComplete : function(data) {
        },
        usePage: true
    });

    //跳转设置页面
    $("#btnSet").click(function(){
        var url = '../modelTemplateInfo/getModelTemplateInfoSetPage.html';
        showDialog({
            id: 'getModelTemplateInfoSetPage',
            name: 'getModelTemplateInfoSetPage',
            pid: tabs.getSelfId(window),
            title : '设置评分项',
            width : 800,
            height : 550,
            url : url,
            lock : true
        });
    });


    //点击刷新
    $('#btnSearch').click(function(){
        var postJson = {};
        postJson['templateId'] = $('#modeTemplateInfos').val();
        postJson['fieldName'] = $('#fieldName').val();
        $('#grid').flexSearch(postJson);
    });

    $("#btnClean").click(function(){
        toCleanForm('#form');
    });
    //刷新
    function refresh() {
        $('#gridServiceRecord').flexReload();
    }
})