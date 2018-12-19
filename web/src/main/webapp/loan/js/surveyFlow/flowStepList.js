var buttonsArray = [
    {
        display: '编辑',
        onClick: function (tr, data) {
            var url = '../flowStep/getFlowStepUpdatePage.html?id='+data.id+'&random='+Math.random()*10000;
            showDialog({
                id: 'editFlowStep',
                title: '编辑流程步骤',
                url: url,
                width: 660,
                height: 330,
                tabId: tabs.getSelfId(window)
            });
        }
    },
    {
        display: '删除',
        onClick: function (tr, data) {
            var size = $("#flowStepList tr").length;
            if(size <= 1){
                showConfirm({
                    icon:"warning",
                    content:"已经是最后一个了，不能删除！"
                })
            }else{
            showConfirm({
                icon:'confirm',
                content:'您确定要彻底删除该项吗？删除后将无法恢复。',
                ok:function(){
                    jQuery.ajax({
                        url: '../flowStep/deleteFlowStep.html?id='+data.id+'&random='+Math.random()*10000,
                        type:'POST',
                        sync: false,
                        dataType:'json',
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
    },
    {
        display: '上移',
        onClick: function (tr, data) {
            move(data,"moveUp");
        },
        isDisabled : function(row, index, data) {
            return index == 0;
        }
    },
    {
        display: '下移',
        onClick: function (tr, data) {
           move(data,"moveDown");

        },
        isDisabled : function(row, index, data) {
            return (++index == data.rows.length);
        }
    }


];

$(function () {
    var flowId = $("#flowId").val();
    $('#flowStepList').flexigrid({
        height: 300,
        url: '../flowStep/queryFlowStepList.html?flowId='+flowId+'&random='+Math.random()*10000,
        dataType: 'json',
        //multiSelect: true,
        fields: [
            {display: '步骤名称', field: 'stepName', width: 300, align: 'center'},
        ],
        action: {
            display: '操作',
            width: 300,
            align: 'center',
            buttons: buttonsArray
        },
        usePage: true
    });

    //新增
    $("#btnAdd").click(function () {
        var url = '../flowStep/getFlowStepInsertPage.html?flowId='+flowId+'&random='+Math.random()*10000;
        showDialog({
            id: 'editFlowStep',
            title: '添加步骤',
            url: url,
            width: 660,
            height: 330,
            tabId: tabs.getSelfId(window)
        });
    });

});

function move(data,type){
    jQuery.ajax({
        type : 'POST',
        url : '../flowStep/moveStepSortNo.html',
        data : {
            "stepId":data.id,
            "flowId":data.cols.flowId,
            "type":type
        },
        success : function(result) {
            refresh();
        }
    });
}

//
function refresh() {
    $('#flowStepList').flexReload();
}