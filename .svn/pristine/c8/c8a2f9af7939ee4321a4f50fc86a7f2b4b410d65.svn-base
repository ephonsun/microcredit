$(function(){

    //初始化下拉单选框
    $('select').selectbox();

    $('#gridServiceRecord').flexigrid({
        height: 300,
        url: '../tableTemplate/queryTableInfoListData.html',
        /*multiSelect: true,*/
        fields: [
                 
            { display: '模块名称', field: 'tableDisplayName', width: 300 ,align: 'center'},
            { display: '状态', field: 'isActived', width: 100 ,align: 'center',data:{0:"禁用",1:"启用"}},
        ],
        action: {
            display: '操作',
            width: 250,
            align: 'center',
            buttons: [
                {
                    display: '启用',
                    onClick: function(tr, data){
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要启用吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../tableTemplate/enable/' + data.id +".html",
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '启用成功'
                                        });
                                        refresh();
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    },
                    isDisabled: function(data){
                        return data.cols.isActived == 1;
                    }
                },
                {
                    display: '禁用',
                    onClick: function(tr, data){
                        showConfirm({
                            icon: 'confirm',
                            content: '您确认要禁用吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../tableTemplate/disable/' + data.id +".html",
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '禁用成功'
                                        });
                                        refresh();
                                    }
                                });
                            },
                            cancel: function () {
                            }
                        });
                    },
                    isDisabled: function(data){
                        var arrDis = ["LBIZ_LOAN_APPLY_INFO","LBIZ_PERSONAL_INFO","LBIZ_FAMILY_INFO","LBIZ_SPOUSE_INFO","LBIZ_SURVEY_RESULT","LBIZ_APPROVAL_RESOLUTION","LBIZ_LOAN_GRANT"];
                        if(data.cols.isActived == 0){
                           return true;
                        }
                        if(arrDis.toString().indexOf(data.cols.tableDbName) > -1){
                            return true;
                        }
                        return false;
                    }
                },
                {
                    display: '编辑',
                    onClick: function(tr, data){
                        var url = '../tableTemplate/getTableInfoUpdatePage.html?tableId='+data.id;
                        showDialog({
                            id: 'editTableTemplate',
                            title : '编辑表单模版',
                            url : url,
                            width : 330,
                            height : 137,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '字段',
                    onClick: function(tr, data){
                        var url = '../tableColumn/getTableColumnListPage.html?tableId='+data.id;
                        tabs.add({
                            id: 'tableColumnList_'+data.id,
                            name: 'tableColumnList',
                            pid: tabs.getSelfId(window),
                            title : '表单模版字段列表',
                            url : url,
                            lock : false
                        });
                    }
                }
            ]
        },
        onComplete : function(data) {
            $('#lblStatisticsSR').text(data.total);
        },
        usePage: true
    });


    //点击同频表结构
    $('#btnTableSync').click(function(){
    	jQuery.ajax({
            type: 'POST',
            url: '../tableColumn/syncCustTableToLoadTable.html',
            success: function (result) {
                showConfirm({
                    icon: 'succeed',
                    content: '同频数据成功'
                });
            }
        });
    });
    
    //点击同频数据
    $('#btnRefreshData').click(function(){
    	jQuery.ajax({
            type: 'POST',
            url: '../tableColumn/refreshTableToField.html',
            success: function (result) {
                showConfirm({
                    icon: 'succeed',
                    content: '刷新数据成功'
                });
            }
        });
    });

    //点击导出数据
    $('#btnExportData').click(function(){
        window.location= '../tableColumn/exportTableFieldData.html'

    });
    
    //点击刷新
    $('#btnRefresh').click(function(){
        refresh();
    });

    //刷新
    function refresh() {
        $('#gridServiceRecord').flexReload();
    }
})