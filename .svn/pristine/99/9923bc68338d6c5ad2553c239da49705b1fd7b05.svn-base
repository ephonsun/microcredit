// 百度地图API功能
var map = new BMap.Map("allmap",{minZoom:6});
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = $('#zoom').val();
var point = new BMap.Point(defaultj, defaultw);
map.centerAndZoom(point, zoom);
var trajectory = [];
var prevPoint = {};
var s = 1; //比例尺倍数、系数
var scaleControl = [20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000];
$(function () {
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



    setTraject();

    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});// 左上角，添加比尺
    map.addControl(top_left_control);//添加比例尺
    map.enableScrollWheelZoom(true)   ;     //开启鼠标滚轮缩放
    map.addEventListener("zoomend",function(){
        zoom = map.getZoom();
        setTraject();
    });
    $('#btnClose').click(function() {
        var dialog = getDialog('trajectoryMap');
        dialog.close();
    });

});
var first = true;
function setTraject() {
    map.clearOverlays();
    trajectory = [];
    $.each(points, function (index, value) {
        if (index == 0) {
            prevPoint = value;
            trajectory.push(new BMap.Point(value.lng, value.lat));
            var pt = new BMap.Point(value.lng, value.lat);
            var myIcon = new BMap.Icon("../core/imgs/icon/qi.png", new BMap.Size(30,55));
            var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
            marker.addEventListener("click",function () {
                showConfirm({
                    icon: 'confirm',
                    content: value.time
                });
            });
            map.addOverlay(marker);              // 将标注添加到地图中
            if (first) {
                map.centerAndZoom(pt, zoom);
                first = false;
            }
        } else {
            var limit = scaleControl[19 - zoom] * s;
            var l = map.getDistance(new BMap.Point(prevPoint.lng, prevPoint.lat), new BMap.Point(value.lng, value.lat));
            if (l > limit) {
                prevPoint = value;
                var pt = new BMap.Point(value.lng, value.lat);
                var myIcon = new BMap.Icon("../core/imgs/icon/point.png", new BMap.Size(30,55));
                var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
                marker.addEventListener("mouseover",function () {
                    var label = new BMap.Label(value.time,{offset:new BMap.Size(0,-20)});
                    this.setLabel(label);
                });
                marker.addEventListener("mouseout",function () {
                    var label = this.getLabel();
                    label.remove();
                });
                map.addOverlay(marker);              // 将标注添加到地图中
                trajectory.push(new BMap.Point(value.lng, value.lat));
            }
        }
    });
    if (trajectory.length > 0 ) {
        var polyline = new BMap.Polyline(trajectory, {strokeColor:"red", strokeWeight:3, strokeOpacity:0.5});   //创建折线
        map.addOverlay(polyline);
    } else {
        showConfirm({
            icon: 'confirm',
            content: '没有查到相关数据'
        });
    }
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