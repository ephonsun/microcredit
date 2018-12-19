// 百度地图API功能
var map = new BMap.Map("allmap");
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = $('#zoom').val();
var point = new BMap.Point(defaultj, defaultw);
map.centerAndZoom(point, zoom);
var address = '';
var j = defaultj, w =defaultw;
$(function () {
    map.addEventListener("click", showInfo);
        var pt = new BMap.Point(defaultj, defaultw);
        var myIcon = new BMap.Icon("../core/imgs/icon/customer.png", new BMap.Size(30,55));
        var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
        map.addOverlay(marker);              // 将标注添加到地图中
    map.enableScrollWheelZoom(true)   ;     //开启鼠标滚轮缩放
    $('#btnClose').click(function() {
        closeApplyTab();
    });
    $('#btnSubmit').click(function() {
        saveConfig()
    });
});
function showInfo(e){
    map.clearOverlays();
    j = e.point.lng;
    w = e.point.lat;

    var pt = new BMap.Point(j, w);
    var myIcon = new BMap.Icon("../core/imgs/icon/customer.png", new BMap.Size(30,55));
    var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
    map.addOverlay(marker);              // 将标注添加到地图中
}


function saveConfig() {
    zoom = map.getZoom();
    if (j && w) {
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../mapConfig/updateConfig.html',
            data: {
                "zoom":zoom,
                "lng" : j,
                "lat" :	w
            },
            async: false,
            success: function (result) {
                if (result.success) {
                    showConfirm({
                        icon: 'succeed',
                        content: '保存成功'
                    });
                    window.location.reload();
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: '保存失败'
                    });
                }
            }
        });
    } else {
        showConfirm({
            icon: 'confirm',
            content: '地图上的点还未变化'
        });
    }
}

//关闭页卡
function closeApplyTab() {
    tabs.close(tabs.getSelfId(window));
}