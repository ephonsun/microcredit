// 页面加载完成时...
$(function(){

    $('#btnAdd').click(function() {
        addLoanInfoAddedOwner();
    });

    $('#loanInfoAddedOwnerList').flexigrid({
        // width:  'auto',
        height: 500,
        url: '../loanInfoAddedOwner/queryLoanInfoAddedOwnerList.html',
        fields: [
            { display: '分类名称', field: 'ownerName', width: 500 ,align: 'center'}
        ],
        action: {
            display: '操作',
            width: 500,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function (tr, data) {
                        var url = '../loanInfoAddedOwner/getLoanInfoAddedOwnerUpdatePage.html?ownerId=' + data.id +'&random='+Math.random()*10000;
                        tabs.add({
                            id: 'editLoanInfoAddedOwner',
                            name: 'editLoanInfoAddedOwner',
                            pid: tabs.getSelfId(window),
                            title : '编辑分类',
                            url : url,
                            lock : false
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
                                        url: '../loanInfoAddedOwner/enable.html?ownerId='+ data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '启用成功'
                                        });
                                        $('#loanInfoAddedOwnerList').flexReload();
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
                                    url: '../loanInfoAddedOwner/disable.html?ownerId='+ data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '禁用成功'
                                        });
                                        $('#loanInfoAddedOwnerList').flexReload();
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
                    display: '删除',
                    onClick: function (tr, data) {
                        showConfirm({
                            icon: 'warning',
                            content: '您确认要删除吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../loanInfoAddedOwner/delete.html?ownerId=' + data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '删除成功'
                                        });
                                        $('#loanInfoAddedOwnerList').flexReload();
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
        usePage: false
    });
});

function addLoanInfoAddedOwner(){
    var url = '../loanInfoAddedOwner/getAddLoanInfoAddedOwnerPage.html?random='+Math.random()*10000;
    tabs.add({
        id: 'addLoanInfoAddedOwner',
        name: 'addLoanInfoAddedOwner',
        pid: tabs.getSelfId(window),
        title : '新增分类',
        url : url,
        lock : false
    });
}
//移动
function move(data,type) {
    jQuery.ajax({
        type : 'POST',
        url : '../loanInfoAddedOwner/moveOwnerOrderNo.html',
        data : {
            "ownerId":data.id,
            "type":type
        },
        success : function(result) {
            reloadList();
        }
    });
}

function reloadList(){
    $('#loanInfoAddedOwnerList').flexReload();
}