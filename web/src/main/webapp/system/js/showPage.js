// 页面加载完成时...
$(function(){
    $('#showPageList').flexigrid({
        height: 405,
        width: 'auto',
        url: '../modelManagement/queryShowPage.html?projectId='+$("#projectId").val(),
        fields: [
            {display: $("#projectName").val(), field: 'configKey', width: 118, align: 'center'},
            {display: '参考值', field: 'configValue', width: 118, align: 'center'}
        ],
        usePage: false }
    )}
    );