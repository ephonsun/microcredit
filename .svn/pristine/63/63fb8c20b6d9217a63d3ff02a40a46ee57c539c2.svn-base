
// 页面加载完成时...
$(function(){
    // 加载列表
    $('#grid').flexigrid({
        height: 500,
        url: '../msg/getMsgList.html',
        usePage: true,
        rowIdProperty: 'id',
        fields: [
            { display: '手机号码', field: 'phone', width: 250,align : 'center' },
            { display: '验证码', field: 'checkCode', width: 250 ,align : 'center' },
            { display: '调用时间', field: 'addTime', width: 250 ,align : 'center' },
            { display: 'IP地址', field: 'ip', width: 350 ,align : 'center' }
        ],

        onComplete : function(data) {

            $('#lblStatistics').text(data.total);
        }
    });

    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');

        setTimeout(function(){$("input[name='userName']").focus();},100);
        setTimeout(function(){$("input[name='userName']").blur();},100);
    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchList();
    });
    //刷新
    $('#btnRefresh').click(function(){
        refreshList();
    });



});


function searchList() {

    var postJson = {};
    postJson['phone'] = $('#phone').val();
    postJson['addTime'] = $('#txtStartDate').val();
    postJson['addTimeEnd'] = $('#txtStartDate').val();

    $('#grid').flexSearch(postJson);

}
// 刷新人员表
function refreshList() {
    $('#grid').flexReload();
}
// 加载操作时间
$('#txtStartDate').datepicker({
    maxDate: '#F{$dp.$D(\'txtEndDate\')}'
});
$('#txtEndDate').datepicker({
    minDate: '#F{$dp.$D(\'txtStartDate\')}',
    maxDate:new Date()
});

