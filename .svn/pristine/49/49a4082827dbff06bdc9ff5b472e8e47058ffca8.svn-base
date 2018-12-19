var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
$(function () {

    var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    var toolManager = new RToolManager(rmap);
    toolManager.addZoomDirectBar(0,0);
    if (locationw == '' || locationj == '') {
        showConfirm({
            icon: 'confirm',
            content: '该地址还未标记地图坐标'
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
            if (showLabel)
                pointMarker.showLabel();
        },500);
    }
    $('#btnClose').click(function() {
        var dialog = getDialog('loanTaggingMap');
        dialog.close();
    });
});


function delta(lat, lon) {
    var json={};
    var a = 6378245.0; // a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
    var ee = 0.00669342162296594323; // ee: 椭球的偏心率。
    var dLat = this.functionLat(lon - 105.0, lat - 35.0);
    var dLon = this.functionLon(lon - 105.0, lat - 35.0);
    var radLat = lat / 180.0 * this.PI;
    var magic = Math.sin(radLat);
    magic = 1 - ee * magic * magic;
    var sqrtMagic = Math.sqrt(magic);
    dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
    dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
    json['dLat']=lat-dLat;
    json['dLon']=lon-dLon;
    return json;
}
function functionLat(x,y) {
    var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
    ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
    return ret;
}
function functionLon(x,y) {
    var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
    ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
    ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
    ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
    return ret;
}
function getAddress(lng, lat) {
    var json =delta(lat,lng);
    address='';
    $.ajax({
        type: 'POST',
        async: false,
        url: '../tagging/getAddressByLngLat.html',
        data:{'lng': json.dLon, 'lat': json.dLat},    //请求参数
        dataType: 'text',
        success: function(data){
            if(data){
                address = data;
            }
        }
    });
}