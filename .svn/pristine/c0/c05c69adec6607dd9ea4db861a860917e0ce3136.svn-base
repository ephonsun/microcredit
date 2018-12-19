var buttonsArray = [
    {
        display: '编辑',
        onClick: function (tr,data) {
            var url = '../SysFormSettings/getFormSettingsUpdatePage.html?id='+data.id+'&random='+Math.random()*10000;
            showDialog({
                id: 'editFormSettings',
                title: '编辑表单设置',
                url: url,
                width: 660,
                height: 300,
                tabId: tabs.getSelfId(window)
            });
        }
    },
    {
        display: '删除',
        onClick: function (tr,data) {
            var url='../SysFormSettings/deleteFormSettings.html?id='+data.id+'&random='+Math.random()*10000;
            showConfirm({
                icon:'confirm',
                content:'您确定要彻底删除该项吗？删除后将无法恢复。',
                ok:function(){
                    jQuery.ajax({
                        url: url,
                        type:'POST',
                        sync: false,
                        dataType: 'json',
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
    $('#formSettings').flexigrid({
        height: 360,
        url: '../SysFormSettings/queryFormSettingsListData.html',
        dataType: 'json',
        //multiSelect: true,
        fields: [
            {display: '控制表单', field: 'controlFormName', width: 150, align: 'center'},
            {display: '控制项', field: 'controlItemName', width: 150, align: 'center'},
            {display: '控制值', field: 'controlValueName', width: 300, align: 'center'},
            {display: '隐藏表单', field: 'hiddenFormName', width: 150, align: 'center'}
        ],
        action: {
            display: '操作',
            width: 300,
            align: 'center',
            buttons: buttonsArray
        },
        usePage: true
    });

    //新建
    $("#btnAdd").click(function () {
        var url='../SysFormSettings/getFormSettingsInsertPage.html';
        showDialog({
            id: 'addFormSettings',
            title: '新增表单设置',
            url: url,
            width: 660,
            height: 300,
            tabId: tabs.getSelfId(window)
        });
    });


    //刷新列表
    $("#btnRefresh").click(function () {
        refresh();
    });
});

//刷新
function refresh() {
    $('#formSettings').flexReload();
}
