$(function () {

    $('select').selectbox();

    //校验分类
    banger.verify('#ownerName', [
        {name: 'required', tips: '子类名称必须填写'},
        {name: 'specialAccount', tips: '子类名称必须由中英文、数字或下划线组成'},
        checkOwnerNameRule
    ]);
    //校验子类
    banger.verify('#className', [
        {name: 'required', tips: '子类名称必须填写'},
        {name: 'specialAccount', tips: '子类名称必须由中英文、数字或下划线组成'},
        checkClassNameRule
    ]);
    //校验子类
    banger.verify('#editName', [
        {name: 'required', tips: '子类名称必须填写'},
        {name: 'specialAccount', tips: '子类名称必须由中英文、数字或下划线组成'},
        checkEditNameRule
    ]);

    banger.verify('#isActived',{name: 'required', tips: '选择是否禁用'});


    $('#btnSave').click(function () {
        saveLoanTypeRelTable();
    });

    //关闭子类添加弹窗
    $('#btnCancelClass').click(function () {
        $("#saveSearchBox").addClass("hide");
        $("#className").val("");
        $("#classNameLaber").removeClass("ui-text-failed").removeAttr("title");
    });
    //关闭子类编辑弹窗
    $('#btnEditCancel').click(function () {
        $("#editBox").addClass("hide");
        $("#editName").val("");
        $("#editNameLaber").removeClass("ui-text-failed").removeAttr("title");
    });

    $('#loanInfoAddedClassList').flexigrid({
        dataType: 'json',
        autoLoad: false,
        url: false,
        fields: [
            {display: '子类名', field: 'className', width: 400, align: 'center'},
        ],
        action: {
            display: '操作',
            width: 400,
            align: 'center',
            buttons: [
                {
                    display: '编辑',
                    onClick: function (tr, data) {

                        //数组坐标
                        dataId = data.id.substring(4)
                        //需要编辑的名字
                        for (var i = 0; i < classNames['rows'].length; i++) {
                            if (classNames['rows'][i]['id'] == dataId) {
                                editName = classNames['rows'][i]['cols']['className'];
                            }
                        }
                        $("#editName").val(editName);

                        var _minusHeight = $('#editBox').height() + $("#btnNew").height(),
                            _offsetTop = $("#btnNew").offset().top,
                            _offsetLeft = $("#btnNew").offset().left,
                            _diffHeight = _offsetTop - _minusHeight;

                        if (_offsetTop < _minusHeight) {
                            $('#editBox').css({
                                'top': _minusHeight + 4 + 'px',
                                'left': _offsetLeft + 370 + 'px'
                            }).removeClass('hide');
                        } else {
                            $('#editBox').css({
                                'top': _diffHeight + 'px',
                                'left': _offsetLeft + 370 + 'px'
                            }).removeClass('hide');
                        }
                    }
                },
                {
                    display: '删除',
                    onClick: function (tr, data) {
                        //有bug 取消确认弹窗
                        for (var i = 0; i < classNames['rows'].length; i++) {
                            var classNameId = classNames['rows'][i]['id'];
                            //dataId = flex1 flex2..
                            var dataId = data.id.substring(4);
                            if (classNameId == dataId) {
                                classNames['rows'].splice(i, 1);
                            }
                        }
                        refresh();
                    }
                }
            ]
        },
        usePage: false
    });

});

//保存子类
$('#btnSaveClass').click(function () {
    var bool = true
    if (!banger.verify('#saveSearchBox')) {
        bool = false;
        return bool;
    }
    if (bool) {
        id = classNames['rows'].length;
        if (!id) {
            id = 1;
        } else {
            id = id + 1;
        }
        classNames['rows'].push({id: id, cols: {className: $("#className").val()}});
        $('#btnCancelClass').click();
        $("#loanInfoAddedClassList").flexAddData(classNames);
    }
});
//编辑子类
$("#btnEditClass").click(function () {
    var bool = true
    if (!banger.verify('#editBox')) {
        bool = false;
        return bool;
    }
    if (bool) {
        //需要编辑的名字
        for (var i = 0; i < classNames['rows'].length; i++) {
            if (classNames['rows'][i]['id'] == dataId) {
                classNames['rows'][i]['cols']['className'] = $("#editName").val();
            }
        }
        $('#btnEditCancel').click();
        refresh();
    }
});

//弹出子类
$('#btnNew').on('click', function (e) {
    var minusHeight = $('#saveSearchBox').height() + $(this).height(),
        offsetTop = $(this).offset().top,
        offsetLeft = $(this).offset().left,
        diffHeight = offsetTop - minusHeight;

    if (offsetTop < minusHeight) {
        $('#saveSearchBox').css({'top': minusHeight + 4 + 'px', 'left': offsetLeft + 370 + 'px'}).removeClass('hide');
    } else {
        $('#saveSearchBox').css({'top': diffHeight + 'px', 'left': offsetLeft + 370 + 'px'}).removeClass('hide');
    }

    // var searchTitleName = $('#oldName').val();
    // $('#resultName').val(searchTitleName);
    // e.stopPropagation();
});

var checkClassNameRule = {
    name: 'checkClassNameRule',
    tips: '子类名字已经存在，请重新输入',
    rule: function () {
        var rule = this, bool = true;
        for (var i = 0; i < classNames['rows'].length; i++) {
            var className = classNames['rows'][i]['cols']['className'];
            if (className == $("#className").val()) {
                bool = false;
            }
        }
        return bool;
    }
};

var checkEditNameRule = {
    name: 'checkEditNameRule',
    tips: '子类名字已经存在，请重新输入',
    rule: function () {
        var rule = this, bool = true;
        for (var i = 0; i < classNames['rows'].length; i++) {
            var _editName = classNames['rows'][i]['cols']['className'];
            if (_editName == $("#editName").val() && _editName != editName) {
                bool = false;
            }
        }
        return bool;
    }
};
var checkOwnerNameRule = {
    name: 'checkOwnerNameRule',
    tips: '分类名字已经存在，请重新输入',
    rule: function () {
        var rule = this, bool = true, data = {"ownerName":$("#ownerName").val()};
        var url = '../loanInfoAddedOwner/checkName.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            async: false,
            data: data,
            success: function (result) {
                bool = result.success;
            }
        });
        return bool;
    }
};

//重新注入数据
function refresh() {
    $("#loanInfoAddedClassList").flexAddData(classNames);
}
//保存分类及其子类
$("#btnSaveAll").click(function () {
    if (!banger.verify('#form1')) {
        return false;
    }
    if(classNames['rows'].length == 0){
        showConfirm({
            icon: 'warning',
            content: '请先添加子类'
        });
        return;
    }
    var classNameStr = "";
    for (var i = 0; i < classNames['rows'].length; i++) {
        classNameStr += classNames['rows'][i]['cols']['className'] + ",";
    }
    classNameStr = classNameStr.substring(0,classNameStr.length-1)
    jQuery.ajax({
        type: 'post',
        url: '../loanInfoAddedOwner/saveAddLoanInfoAddedOwner.html',
        data: {
            "ownerName": $("#ownerName").val(),
            "classNames": classNameStr,
            "isActived":$("#isActived").val()
        },
        success: function () {
            showConfirm({
                icon: 'succeed',
                content: '保存成功'
            });
            tabs.refresh("loanInfoAddedOwner");
            $("#btnCancelAll").click();
        }
    })
})
$("#btnCancelAll").click(function () {
    tabs.close(tabs.getSelfId(window))
})