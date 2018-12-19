var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
var address;
$(function () {
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    var toolManager = new RToolManager(rmap);
    toolManager.addZoomDirectBar(0,0);
    if (locationw == '' || locationj == '') {
        showConfirm({
            icon: 'confirm',
            content: '参数错误'
        });
    } else {
        getAddress(locationj, locationw);
        setTimeout(function () {
            var showLabel = false;
            if (address)
                showLabel = true;
            else address = '';
            var pointMarker = new RLabelMarker( locationj, locationw, address, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
            //将标注添加入 rmap对象
            rmap.addMarker(pointMarker);
            rmap.setCenter(locationj, locationw);
            if (showLabel)
                pointMarker.showLabel();
        },500);
    }
    $('#btnClose').click(function() {
        var dialog = getDialog('taggingLngLatShow');
        dialog.close();
    });
});
function getAddress(lng, lat) {
    address='';
    $.ajax({
        type: 'POST',
        async: false,
        url: '../tagging/getAddressByLngLat.html',
        data:{'lng': lng, 'lat': lat},    //请求参数
        dataType: 'text',
        success: function(data){
            if(data){
              address = data;
            }
        }
    });
}