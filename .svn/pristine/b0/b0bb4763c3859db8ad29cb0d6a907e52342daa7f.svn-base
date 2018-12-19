$(function() {

    $('#importReport').click(function() {
        importReport();
    });
    //刷新
    $('#btnRefresh').click(function(){
        reloadList();
    });
    $('#gridReport').flexigrid({
        height: 400,
        url: '../modeConfigFile/queryConfigFiles.html',
        dataType: 'json',
        fields: [
            {display: '模板名称', field: 'modeName', width: 400, align: 'center'}
        ],
        action: {
            display: '操作',
            width: 100,
            align: 'center',
            buttons: [{ display: '编辑', onClick: function(tr, data){  importReport(data.id); }},
                { display: '删除', onClick: function(tr, data){  deleteConfigFiles(data.id); }}]
        },
        usePage: false
    });
});

function reloadList() {
    $('#gridReport').flexReload();
}
function deleteConfigFiles(id) {
    showConfirm({
        icon: 'warning',
        content: "是否删除模板数据",
        ok: function() {
            jQuery.ajax({
                type : 'post',
                dataType:'json',
                url : '../modeConfigFile/deleteConfieFile.html',
                data : {"id": id},
                success : function(result) {
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        reloadList();
                    }else{
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function () {
        }
    });
}
function importReport(id) {
    var title = id ? '调查报告-模板编辑' : '调查报告-导入调查报告';
    tabs.add({
        id : 'importReport' + id,
        name : 'importReport' + id,
        pid: tabs.getSelfId(window),
        title : title,
        url : '../modeConfigFile/importReport.html?id=' + id + '&random='+Math.random()*10000,
        lock : false
    });
}