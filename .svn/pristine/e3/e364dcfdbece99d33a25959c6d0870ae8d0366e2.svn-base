var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
var j = defaultj, w =defaultw;
RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
$(function () {
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    setTraject();
});
function setTraject() {
    $.each(maps, function (index, value) {
        var pointMarker = new RPointMarker( value.lng, value.lat, "../core/imgs/icon/icon_user.png", -15, -30, 30, 30);
        //将标注添加入 rmap对象
        rmap.addMarker(pointMarker);
        pointMarker.runClickEvent=function(e){
            showConfirm({
                icon: 'confirm',
                content: value.userName
            });
        };
    });
}