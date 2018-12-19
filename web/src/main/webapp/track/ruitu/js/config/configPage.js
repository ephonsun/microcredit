var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = parseInt($('#zoom').val());
var j = defaultj, w =defaultw;
RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
var moveIndex = 0;
$(function () {
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    var toolManager = new RToolManager(rmap);
    toolManager.addZoomDirectBar(50,50);
    var pointMarker = new RPointMarker( j, w, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
    //将标注添加入 rmap对象
    rmap.addMarker(pointMarker);
    rmap.addEventListener(RMapEvent.Moving, function (e) {
        moveIndex++;
    });
    rmap.addEventListener(RMapEvent.MouseClickEvent, function (e) {
        if (moveIndex < 2) {
            rmap.removeAllMarker();
            j = e.MouseCx;
            w = e.MouseCy;
            var pointMarker = new RPointMarker( j, w, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
            //将标注添加入 rmap对象
            rmap.addMarker(pointMarker);
        }
        moveIndex = 0;
    } );
    $('#btnClose').click(function() {
        closeApplyTab();
    });
    $('#btnSubmit').click(function() {
        saveConfig()
    });
});

function saveConfig() {
    zoom = rmap.getLevel();
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