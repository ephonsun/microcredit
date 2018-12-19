$(function(){
    //初始化下拉单选框
    $('select').selectbox();

    $('#grid').flexigrid({
        height: 400,
        url: '../intoTemplatesInfo/queryIntoTemplatesInfoColunm.html',
        fields: [
            { display: '所属模块', field: 'tableName', width: 250 ,align: 'center'},
            { display: '评分项', field: 'fieldName', width: 250,align: 'center'},
            { display: '显示名称', field: 'fieldDisplay', width: 250 ,align: 'center'},
            { display: '是否必填', field: 'isMust', width: 250 ,align: 'center',data: {0: "否", 1: "是"}}
        ],
        action: {
            display: '操作',
            width: 220,
            align: 'center',
            buttons: [
                {
                    display: '上移',
                    onClick: function(tr, data){
                        move(data,'moveUp');
                    },
                    isDisabled : function(row, index, data) {
                        return index == 0;
                    }
                },
                {
                    display: '下移',
                    onClick: function(tr, data){
                        move(data,'moveDown');
                    },
                    isDisabled : function(row, index, data) {
                        return (++index == data.rows.length);
                    }
                },
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        getIntoTemplatesInfoSetPage(data.cols.templateId);
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要删除吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../intoTemplatesInfo/disable/'+ data.id +".html",
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '删除成功'
                                        });
                                        refresh();
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    }
                }

            ]
        },
        onComplete : function(data) {
        },
        usePage: true
    });

    //跳转设置页面
    $("#btnSet").click(function(){
        getIntoTemplatesInfoSetPage("");
    });

    function getIntoTemplatesInfoSetPage(templateId) {
        var url = '../intoTemplatesInfo/getIntoTemplatesInfoSetPage.html?templateId='+templateId;
        showDialog({
            id: 'getModelTemplateInfoSetPage',
            name: 'getModelTemplateInfoSetPage',
            pid: tabs.getSelfId(window),
            title : '设置进件信息',
            width : 800,
            height : 550,
            url : url,
            lock : true
        });
    }

    //移动
    function move(data,type) {
        jQuery.ajax({
            type : 'POST',
            url : '../intoTemplatesInfo/moveIntoTemplatesFieldOrderNo.html?templateId='+data.cols.templateId+ '&type='+type+'&fieldId='+data.id,
            data : {},
            success : function(result) {
                refresh();
            }
        });
    }
    //点击搜索
    $('#btnSearch').click(function(){
        var postJson = {};
        postJson['templateId'] = $('#intoTemplatesInfos').val();
        postJson['fieldName'] = $('#fieldName').val();
        $('#grid').flexSearch(postJson);
    });
    //清空搜索条件
    $("#btnClean").click(function(){
        toCleanForm('#form');
    });
    //点击刷新
    $('#btnRefresh').click(function(){
        refresh();
    });
    //刷新
    function refresh() {
        $('#grid').flexReload();
    }

})