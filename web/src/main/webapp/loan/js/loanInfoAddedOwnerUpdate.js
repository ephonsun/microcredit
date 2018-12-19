$(function(){

    $('select').selectbox();
    //校验
    banger.verify('#ownerName',[
        {name: 'required', tips: '分类名称必须填写'},
        {name: 'specialAccount', tips: '分类名称必须由中英文、数字或下划线组成'},
        checkOwnerNameRule
    ]);
    $('#btnSaveAll').click(function() {
        updateLoanInfoAddedOwner();
    });


    $('#btnNew').click(function() {
        addClass();
    });
    //关闭
    $('#btnCancelAll').click(function() {
        tabs.close(tabs.getSelfId(window));
    });

    $('#loanInfoClasses').flexigrid({
        height: 500,
        // dataType: 'json',
        url: '../loanInfoAddedOwner/queryLoanOwnerClass.html?ownerId='+$("#ownerId").val(),
        fields: [
            {display: '子类名', field: 'className', width: 400, align: 'center'},
        ],
        action: {
            display: '操作',
            width: 400,
            align: 'center',
            buttons: [
                {
                    display: '上移',
                    onClick: function(tr, data){
                        move(data,'moveUp');
                    },
                    isDisabled : function(tr, index, data) {
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
                    onClick: function (tr, data) {
                        var url = '../loanInfoAddedClass/getClassUpdatePage.html?classId='+data.id +'&random='+Math.random()*10000;
                        showDialog({
                            id: 'loanInfoAddedClassUpdatePage',
                            title: '编辑子类',
                            url: url,
                            width: 330,
                            height: 100,
                            tabId: tabs.getSelfId(window)
                        });
                    }
                },
                {
                    display: '删除',
                    onClick: function (tr, data) {
                        if($("#loanInfoClasses tr").length < 2){
                            showConfirm({
                                icon: 'warning',
                                content: '不能删除最后一个！'});
                            return;
                        }
                        showConfirm({
                            icon: 'warning',
                            content: '您确认要删除吗',
                            ok: function () {
                                jQuery.ajax({
                                    type: 'POST',
                                    url: '../loanInfoAddedClass/deleteClass.html?classId=' + data.id,
                                    success: function (result) {
                                        showConfirm({
                                            icon: 'succeed',
                                            content: '删除成功'
                                        });
                                        $('#loanInfoClasses').flexReload();
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
//编辑
function updateLoanInfoAddedOwner(){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    var postJson = getPostFields();
    jQuery.ajax({
        type : 'post',
        url : '../loanInfoAddedOwner/updateLoanInfoAddedOwner.html?isActived='+$("#isActived").val(),
        data : postJson,
        success : function() {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
        }
    });
}


//名字校验
var checkOwnerNameRule = {
    name : 'checkOwnerNameRule',
    tips : '分类名字已经存在，请重新输入',
    rule : function() {
        var rule = this, bool = true, data = getPostFields();
        var url = '../loanInfoAddedOwner/checkNameEdit.html';
        jQuery.ajax({
            type : 'post',
            url : url,
            dataType:'json',
            async : false,
            data : data,
            success : function(result) {
                bool = result.success;
            }
        });
        return bool;
    }
};

//移动
function move(data,type) {
    jQuery.ajax({
        type : 'POST',
        url : '../loanInfoAddedClass/moveClassOrderNo.html',
        data : {
            "classId": data.id,
            "type":type,
            "ownerId":$("#ownerId").val()
        },
        success : function(result) {
            $('#loanInfoClasses').flexReload();
        }
    });
}

//关闭dialog
function closeDialog(){
    // var dialog = getDialog('editLoanInfoAddedOwner');
    var win = tabs.getIframeWindow(tabs.getSelfId(window));
    var win1 = tabs.getIframeWindow("loanInfoAddedOwner");
    win1.location.reload(true);
    setTimeout(function() {
        tabs.close(tabs.getSelfId(window));
    }, 0);
}
//跳转新增子类
function addClass(){
    var url = '../loanInfoAddedClass/getLoanInfoAddedClassAddPage.html?ownerId='+$("#ownerId").val() +'&random='+Math.random()*10000;
    showDialog({
        id: 'loanInfoAddedClassPage',
        title: '新建子类',
        url: url,
        width: 330,
        height: 100,
        tabId: tabs.getSelfId(window)
    });
}
