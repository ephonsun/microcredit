// 页面加载完成时...
$(function(){

    // 加载角色列表
    var serialNo = $("#serialNo").val();
    $('#grid').flexigrid({
        height: 500,
        url: '../sysMobileInfo/queryloginRecordList.html?serialNo='+serialNo,
        usePage: true,
        fields: [
            { display: '登录时间', field: 'loginDate', width: 150 ,align : 'center'},
            { display: '用户', field: 'userName', width: 100 ,align : 'center'},
            { display: '经纬度', field: 'latitude', width: 100 ,align : 'center' },
            { display: 'IP', field: 'loginIp', width: 100 ,align : 'center' }
        ],
        extendCell: {
            'latitude': function(value, row){
                var value = '<a onclick=seeMap(this)>'+row.cols.loginLongitude+" "+row.cols.loginLatitude+'</a>';
                return value;
            }
        },
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');
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

function seeMap (obj)
{
    var loginLongitude=obj.text;
    var strs= new Array(); //定义一数组
    strs=loginLongitude.split(" "); //字符分割
    for (i=0;i<strs.length ;i++ )
    {
        strs[i]
    }
    var title = '查看地图';
    var url = '../tagging/taggingLngLatShow.html?lng='+strs[0]+'&lat='+strs[1]+'&random='+Math.random()*10000;
    showDialog({
        id: 'taggingLngLatShow',
        title: title,
        url: url,
        width: 920,
        height: 550,
        tabId: tabs.getSelfId(window)
    });
}
// 刷新人员表
function refreshList() {
    $('#grid').flexReload();
}