$(function(){

    $('select').selectbox({});

    // 加载系统日志数据网格
    $('#industryGuidelinesGrid').flexigrid({
        height: 300,
        url: '../industryGuidelines/queryIndustryGuidelines.html',
        dataType: 'json',
        fields: [
            { display: '行业分类', field: 'dictName', width: 200, align: 'center' },
            { display: '标题', field: 'title', width: 250, align: 'center' },
            { display: '创建者', field: 'builder', width: 200, align: 'center' },
        ],
        action: {
            display: '操作',
            width: 200,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        editIndustryGuidelines(data);
                    }
                },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        deleteIndustryGuidelines(data);
                    }
                }
            ]
        },
        rowIdProperty: 'id',
        usePage: true, // 使用分页
        onComplete : function(data) {

        }
    });

    //查询
    $('#btnSearch').click(function(){
        var postJson = {};
        postJson['dictName'] = jQuery.trim($("#dictNameSelect").find("option:selected").text());
        postJson['title'] = jQuery.trim($('#title').val());
        postJson['builder'] = jQuery.trim($('#builder').val());
        $('#industryGuidelinesGrid').flexSearch(postJson);
    });

    //重置
    $('#btnClean').click(function(){
        toCleanForm('#form');
    });

});

function addIndustryGuidelines(){
    var url = '../industryGuidelines/getAddPage.html'
    showDialog({
        id: 'addIndustryGuidelines',
        title: '新增',
        url: url,
        width: 600,
        height: 410,
        tabId: tabs.getSelfId(window)
    });
}

function editIndustryGuidelines(data){
    var url = '../industryGuidelines/getEditPage.html?id='+data.id
    showDialog({
        id: 'editIndustryGuidelines',
        title: '编辑',
        url: url,
        width: 600,
        height: 410,
        tabId: tabs.getSelfId(window)
    });
}

function deleteIndustryGuidelines(data){
    var url = '../industryGuidelines/deleteIndustryGuidelines.html?id='+data.id
    showConfirm({
        icon:'confirm',
        content:'您确定要删除行业分类“'+ data.cols.dictName+ '”吗?',
        ok:function(){
            jQuery.ajax({
                type : 'post',
                url : url,
                success : function(data) {
                    showConfirm({
                        icon: 'succeed',
                        content: '删除成功'
                    });
                    $('#industryGuidelinesGrid').flexReload();
                }
            });
        },
        cancel: function() {}
    });
}