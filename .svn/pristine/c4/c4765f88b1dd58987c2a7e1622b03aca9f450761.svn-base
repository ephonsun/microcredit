var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
RMapConstant.MapRoot = appMapIp+"/PathUrl.ashx?path=/";
var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
$(function () {
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    // var toolManager = new RToolManager(rmap);
    // toolManager.addZoomDirectBar(0,0);
    if (locationw == '' || locationj == '') {
        showConfirm({
            icon: 'confirm',
            content: '该地址还未标记地图坐标'
        });
    } else {
        //新建一个标注
        var pointMarker = new RPointMarker( locationj, locationw, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
        //将标注添加入 rmap对象
        rmap.addMarker(pointMarker);
    }
    $('#btnClose').click(function() {
        var dialog = getDialog('loanTaggingMap');
        dialog.close();
    });
});
