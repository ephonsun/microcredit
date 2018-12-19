var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
var flag = $('#flag').val();
var j = defaultj, w =defaultw;
var rmap;
var trajectory = [];
var prevPoint = {};
var address = '';
var offsetLng='';
var offsetLat='';
var s = 10; //基础距离（米）
var PI = 3.14159265358979324;
var X_PI = 3.14159265358979324 * 3000.0 / 180.0;
// var scaleControl = [50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000];
$(function () {
    if(flag=="1"){
        RMapConstant.MapRoot = appMapIp+"/PathUrl.ashx?path=/";
    }else{
        RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
    }
    rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    var toolManager = new RToolManager(rmap);
    toolManager.addZoomDirectBar(0,0);

    //给初始日期赋值
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth()+1<10?"0" +(nowDate.getMonth()+1):nowDate.getMonth()+1;
    var day = nowDate.getDate()<10?"0" +(nowDate.getDate()):nowDate.getDate();
    var dateStr = year + "-" + month + "-" + day;
    $("#date").val(dateStr);
    //banger.verify('#date' , {name : 'required', tips : '日期必须 选择'});
    // 加载操作时间
    $('#date').datepicker({
        dateFmt: 'yyyy-MM-dd',
        onpicked: function (dq) {
            $("#date-tips").hide();
        },
        oncleared:function () {
            $("#date-tips").show();
        }
    });
    $('#txtStartDate').datepicker({
        dateFmt: 'HH:mm'
    });
    $('#txtEndDate').datepicker({
        dateFmt: 'HH:mm'
    });
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#mapSearchForm');
        $("#date").val(dateStr);
    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchCustomer();
    });
    rmap.addEventListener(RMapEvent.LevelChanged, function (e) {
        zoom = rmap.getLevel();
        setTraject();
    });
    setTraject();
    $('#btnClose').click(function() {
        var dialog = getDialog('trajectoryMap');
        dialog.close();
    });
});
var first = true;
function setTraject() {
    rmap.removeAllMarker();
    rmap.removeAllGraphics();
    trajectory = [];
    $.each(points, function (index, value) {
        if (index == 0) {
            prevPoint = value;
            var point = {};
            point.Cx = value.lng;
            point.Cy = value.lat;
            // offsetLng = value.lng;
            // offsetLat = value.lat;
            // tasnform(offsetLng,offsetLat);
            trajectory.push(point);
            var pointMarker = new RPointMarker( value.lng, value.lat, "../core/imgs/icon/qi.png", -15, -30, 30, 30);
            //将标注添加入 rmap对象
            rmap.addMarker(pointMarker);
            pointMarker.runClickEvent=function(e){
                debugger;
                getAddress(value.lng, value.lat);
                showConfirm({
                    icon: 'confirm',
                    content: value.time+'<br>'+address
                });
            };
            if (first) {
                rmap.setCenter(value.lng, value.lat);
                first = false;
            }
        } else if(index != points.length-1 ){
            // var limit = scaleControl[13 - zoom] * s;
            var l = getFlatternDistance(prevPoint.lng, prevPoint.lat, value.lng, value.lat);
            if (l > s) {
                // offsetLng = value.lng;
                // offsetLat = value.lat;
                // tasnform(offsetLng,offsetLat);
                prevPoint = value;
                var pointMarker = new RPointMarker( value.lng, value.lat, "../core/imgs/icon/pointNew.png", -15, -30, 30, 30);
                //将标注添加入 rmap对象
                rmap.addMarker(pointMarker);
                pointMarker.runClickEvent=function(e){
                    getAddress(value.lng, value.lat);
                    showConfirm({
                        icon: 'confirm',
                        content: value.time+'<br>'+address
                    });
                };
                var point = {};
                point.Cx = value.lng;
                point.Cy = value.lat;
                trajectory.push(point);
            }
        }else{
            var l = getFlatternDistance(prevPoint.lng, prevPoint.lat, value.lng, value.lat);
                offsetLng = value.lng;
                offsetLat = value.lat;
                tasnform(offsetLng,offsetLat);
                prevPoint = value;
                var pointMarker = new RPointMarker( offsetLng, offsetLat, "../core/imgs/icon/lastPoint.png", -15, -30, 30, 30);
                //将标注添加入 rmap对象
                rmap.addMarker(pointMarker);
                pointMarker.runClickEvent=function(e){
                    getAddress(value.lng, value.lat);
                    showConfirm({
                        icon: 'confirm',
                        content: value.time+'<br>'+address
                    });
                };
                var point = {};
                point.Cx = value.lng;
                point.Cy = value.lat;
                trajectory.push(point);
        }
    })
    if (trajectory.length > 0 ) {
        // var rLine = new RLine(trajectory, 3, "#ff0000", 0.8);
        // rmap.addGraphics(rLine);
        // var polyline = new BMap.Polyline(trajectory, {strokeColor:"red", strokeWeight:3, strokeOpacity:0.5});   //创建折线
        // map.addOverlay(polyline);
        rmap.refresh();
    } else {
        showConfirm({
            icon: 'confirm',
            content: '没有查到相关数据'
        });
    }
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
function searchCustomer(){
    first = true;
    var bool = banger.verify("#mapSearchForm");
    if (!bool) {
        return false;
    }
    var postJson = getPostFieldsByFilter("#mapSearchForm");
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../trajectory/getTrajectoryData.html',
        data: postJson,
        async: false,
        success: function (result) {
            if (result.success) {
                points = result.data;
                setTraject();
            } else {
                showConfirm({
                    icon: 'confirm',
                    content: '没有查到相关数据'
                });
            }
        }
    });
}
function tasnform(lng,lat) {
    offsetLng='';
    offsetLat='';
    $.ajax({
        type: 'POST',
        async: false,
        url: '../tagging/tasnform.html',
        data:{'lng': lng, 'lat': lat},    //请求参数
        dataType: 'text',
        success: function(data){
            if(data){
                var latAndLng = new Array();
                latAndLng = data.split(",");
                offsetLng = latAndLng[1];
                offsetLat = latAndLng[0];
            }
        }
    });
}
var EARTH_RADIUS = 6378137.0;    //单位M
var PI = Math.PI;
function getRad(d){
    return d*PI/180.0;
}
/**
 * approx distance between two points on earth ellipsoid
 * @param {Object} lat1
 * @param {Object} lng1
 * @param {Object} lat2
 * @param {Object} lng2
 */
function getFlatternDistance(lng1,lat1,lng2,lat2){
    var f = getRad((lat1 + lat2)/2);
    var g = getRad((lat1 - lat2)/2);
    var l = getRad((lng1 - lng2)/2);
    var sg = Math.sin(g);
    var sl = Math.sin(l);
    var sf = Math.sin(f);
    var s,c,w,r,d,h1,h2;
    var a = EARTH_RADIUS;
    var fl = 1/298.257;
    sg = sg*sg;
    sl = sl*sl;
    sf = sf*sf;
    s = sg*(1-sl) + (1-sf)*sl;
    c = (1-sg)*(1-sl) + sf*sl;
    w = Math.atan(Math.sqrt(s/c));
    r = Math.sqrt(s*c)/w;
    d = 2*w*a;
    h1 = (3*r -1)/2/c;
    h2 = (3*r +1)/2/s;
    return d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
}

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