$(function(){
    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });


    $('select').selectbox({});


    // 加载系统日志数据网格
    $('#logGrid').flexigrid({
        height: 300,
        url: '../systemLog/queryLogByCondition.html',
       // params : postJson,
        dataType: 'json',
        fields: [
            { display: '操作时间', field: 'opeventDate', width: 150, align: 'left' },
            { display: '操作名称', field: 'opeventModule', width: 100, align: 'left' },
            { display: '操作内容', field: 'opeventAction', width: 180, align: 'left' },
            { display: '操作者账号', field: 'opeventUserAccount', width: 180, align: 'left' },
            { display: '操作者姓名', field: 'opeventUserName', width: 100, align: 'left' },
            { display: '操作IP', field: 'opeventIp', width: 120, align: 'left' }
        ],
        rowIdProperty: 'opeventId',
        usePage: true, // 使用分页
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });

    //查询
    $('#btnSearch').click(function(){
        //$("#logGridShow").removeClass("hide");
        var postJson = {};
        _logDateBegin = postJson['logDateBegin'] = jQuery.trim($('#txtStartDate').val());
        _opeventModule = postJson['opeventModule'] = jQuery.trim($('#opeventModule').val());
        _txtBelongTo = postJson['txtBelongTo'] = jQuery.trim($('#txtBelongTo').val());
        _logDateEnd = postJson['logDateEnd'] = jQuery.trim($('#txtEndDate').val());
        $('#logGrid').flexSearch(postJson);
    });

    //
    $('#btnRefresh').click(function(){
        $('#logGrid').flexReload();
    });

    //
    $('#btnClean').click(function(){
        toCleanForm('#form');
        $('input[name="opAction"]').val('');
        $('input[name="opUserName"]').val('');
    });


});
//导出日志
function  exportLog(){
    var num = $("#lblStatistics").text() - 0;
    if(num > 10000){
        showConfirm({
            icon: 'warning',
            content: '导出日志不能超过1000条！'
        });
        return;
    }
    window.location.href = '../systemLog/exportLog.html?logDateBegin='+_logDateBegin+"&opeventModule="+encodeURI(_opeventModule)+"&txtBelongTo="+encodeURI(_txtBelongTo)+"&logDateEnd="+_logDateEnd
}
