var buttonsArray = [
    {
        display: '启用',
        onClick: function (tr, data) {
            showConfirm({
                icon: 'confirm',
                content: '您确认要启用吗',
                ok: function () {
                    jQuery.ajax({
                        type: 'POST',
                        url: '../loanType/enable/' + data.id + ".html",
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
        isDisabled: function (data) {
            return data.cols.isActived == 1;
        }
    },
    {
        display: '禁用',
        onClick: function (tr, data) {
            $.post('../loan/checkLoanType.html', {'id':data.id},function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'warning',
                        content: '该贷款类型已经使用，不能禁用'
                    });
                } else {
                    showConfirm({
                        icon: 'confirm',
                        content: '您确认要禁用吗',
                        ok: function () {
                            jQuery.ajax({
                                type: 'POST',
                                url: '../loanType/disable/' + data.id + ".html",
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

                }
            },'json');
        },
        isDisabled: function (data) {
            return data.cols.isActived == 0;
        }
    },
    {
        display: '编辑',
        onClick: function (tr, data) {
            var url = '../loanType/getContractTypeUpdatePage.html?typeId=' + data.id +'&random='+Math.random()*10000;
            showDialog({
                id: 'LoanTypeHandle',
                title: '编辑合同类型',
                url: url,
                width: 330,
                height: 200,
                tabId: tabs.getSelfId(window)
            });
        }
    },
    {
        display: '表单配置',
        onClick: function (tr, data) {
            var url = '../loanTypeRelTable/getqueryAutoTableInfoListPage.html?typeId=' + data.id +"&precType=Contract"+'&random='+Math.random()*10000;
            tabs.add({
                id: 'tableColumnRelList'+data.id,
                name: 'tableColumnRelList',
                pid: tabs.getSelfId(window),
                title: '编辑表单',
                url: url,
                lock: false
            });
        }
    }
];

$(function () {

    var userId = parseInt($("#userId").val());
    var filterButtons = [];
    var buttonFilter = {"启用":true,"禁用":true,"调查报告":true,"合同":true};     //过滤操作权限

    for(var i=0;i<buttonsArray.length;i++){
        if(buttonFilter[buttonsArray[i].display])
            filterButtons.push(buttonsArray[i]);
    }

    $('#loanTypeList').flexigrid({
        height: 300,
        url: '../loanType/queryLoanTypeList.html?type=contract',
        dataType: 'json',
        //multiSelect: true,
        fields: [
            {display: '合同类型', field: 'loanTypeName', width: 300, align: 'center'},
            {display: '状态', field: 'isActived', width: 100, align: 'center', data: {0: "禁用", 1: "启用"}},
        ],
        action: {
            display: '操作',
            width: 300,
            align: 'center',
            buttons: (userId>0)?filterButtons:buttonsArray
        },
        usePage: false
    });

    //新增
    $("#btnAdd").click(function () {
        var url = '../loanType/getLoanContractTypeInsertPage.html?random='+Math.random()*10000;
        showDialog({
            id: 'LoanTypeHandle',
            title: '新建合同类型',
            url: url,
            width: 330,
            height: 200,
            tabId: tabs.getSelfId(window)
        });
    });

});
// 刷新人员表
function refresh() {
    $('#loanTypeList').flexReload();
}