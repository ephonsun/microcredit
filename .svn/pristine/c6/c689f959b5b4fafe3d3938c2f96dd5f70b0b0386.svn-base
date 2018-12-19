var dataArray;


$(function() {
    // 加载操作时间
    $('#initRightDate').datepicker({
        dateFmt: 'yyyy-MM-dd',
        maxDate:new Date()
    });
   //不能输入非数字
    banger.verify($('#crossPage').find($('.ui-text-text').not($('#initRightDate'))),
        [{name: 'number', tips: '不能为非数字'}]);
    //金钱的输入不能小于0
    banger.verify($('#crossPage').find($("[flag$='yuan']")),[{ name: 'nonNegaDecimal', tips: '必须为正数'}]);

    //关闭
    $('#btnCancel').click(function () {
        closeTab();
    });

    //保存
    $('#btnSave123').click(function() {
        if (!banger.verify($('#crossPage'))) {
            return false;
        }

        saveCrossCheck();
    });

});

function saveCrossCheck() {

    getPostData();
    // data:{"param":dataArray}
    var loanId = $('#loanId').val();
    jQuery.ajax({
        type: 'post',
        dataType:'json',
        url: '../crossCheck/saveCrossCheck.html',
        data: {"param": dataArray, "loanId": loanId},
        success: function (result) {
            if (result.success) {

                showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
                reload();
            } else {
                showConfirm({
                    icon: 'warning',
                    content: result.cause
                });

            }

        }

    });

}
//json取值
function getPostData(){
    var params = [];
    $(".tableParam").each(function(){

        var param = {};
        $(this).find("input").each(function(){
            var  name = $(this).attr("name");
            if(name == "") {
                return;
            }

            param[name] = $(this).val();
        });
        $(this).find(".ui-textarea-text").each(function () {
            var  name = $(this).attr("name");
            param[name]=$(this).val();
        });
         params.push(param);
    })
    dataArray = JSON.stringify(params);
}


//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
    tabs.close(tabs.getSelfId(window));
}

function reload() {
    var win = tabs.getIframeWindow("updateCustomerinvestigate"+$("#loanId").val());
    win.location.reload(true);
}
