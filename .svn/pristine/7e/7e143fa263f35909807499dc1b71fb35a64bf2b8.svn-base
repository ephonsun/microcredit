$(function(){
    $('#grid').flexigrid({
        height: 400,
        url: '../intoTemplatesInfo/queryIntoInfoList.html',
        fields: [
            { display: '所属模块', field: 'tableModelName', width: 250 ,align: 'center'},
            { display: '字段', field: 'fieldName', width: 250 ,align: 'center'},
            { display: '是否必填', field: 'fieldIsRequired', width: 150 ,align: 'center',data:{0:"否",1:"是"}}
        ],
        action: {
            display: '操作',
            width: 350,
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
                    onClick: function (tr,data) {
                        var url = '../intoTemplatesInfo/getIntoInfoPage.html?fieldId='+data.id+'&random='+Math.random()*10000;
                        showDialog({
                            id: 'addIntoInfo',
                            title: '编辑进件信息',
                            url: url,
                            width: 660,
                            height: 300,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '启用',
                    onClick: function (tr, data) {
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要启用吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../intoTemplatesInfo/enable.html?fieldId='+ data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '启用成功'
                                        });
                                        $('#grid').flexReload();
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    },
                    isDisabled: function (data) {
                        return data.cols.isActived == 1;
                    }
                },
                {
                    display: '禁用',
                    onClick: function (tr, data) {
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要禁用吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../intoTemplatesInfo/disable.html?fieldId='+ data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '禁用成功'
                                        });
                                        $('#grid').flexReload();
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    },
                    isDisabled: function (data) {
                        return data.cols.isActived == 0;
                    }
                },
            ]
        },
        onComplete : function(data) {
        },
        usePage: false
    });

    //跳转新增页面
    $("#btnNew").click(function(){
        var url = '../intoTemplatesInfo/getIntoInfoPage.html';
        showDialog({
            id: 'addIntoInfo',
            title: '新增进件信息',
            url: url,
            width: 660,
            height: 300,
            tabId: tabs.getSelfId(window)
        });
    });
    //点击刷新
    $('#btnRefresh').click(function(){
        refresh();
    });

    //刷新
    function refresh() {
        $('#grid').flexReload();
    }

    //移动
    function move(data,type) {
        jQuery.ajax({
            type : 'POST',
            url : '../intoTemplatesInfo/moveIntoInfoFieldId.html',
            data : {
                "fieldId":data.id,
                "type":type
            },
            success : function(result) {
                refresh();
            }
        });
    }
})