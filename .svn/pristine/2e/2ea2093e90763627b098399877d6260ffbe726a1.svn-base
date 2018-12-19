// 百度地图API功能
var map = new BMap.Map("allmap");
var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = $('#zoom').val();
var point = new BMap.Point(defaultj, defaultw);
map.centerAndZoom(point, zoom);
var local = new BMap.LocalSearch(map, {
    renderOptions:{map: map}
});
var address = '';
var j,w;
$(function () {
    map.addEventListener("click", showInfo);
    if (locationw != '' && locationj != '') {
        j = locationj;
        w = locationw;
        var pt = new BMap.Point(locationj, locationw);
        var gc = new BMap.Geocoder();
        var myIcon = new BMap.Icon("../core/imgs/icon/customer.png", new BMap.Size(30,55));
        //获取地址的数据地址
        gc.getLocation(pt, function(rs){
            var addComp = rs.addressComponents;
            address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
            var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
            var label = new BMap.Label(address,{offset:new BMap.Size(0,-20)});
            marker.setLabel(label);
            map.addOverlay(marker);              // 将标注添加到地图中
        });
    }
    map.enableScrollWheelZoom(true)   ;     //开启鼠标滚轮缩放
    $('#btnClose').click(function() {
        var dialog = getDialog('loanTaggingMap');
        dialog.close();
    });
    $('#btnSubmit').click(function() {
        saveTagging()
    });
    $('#btnClean').click(function() {
        toCleanForm('#form');
        setTimeout(function(){$("input[name='address']").focus();},100);
        setTimeout(function(){$("input[name='address']").blur();},100);
    });
    $('#btnSearch').click(function () {
        var address = $('#address').val();
       local.search(address);
    });
    //回车事件
    $("input").keydown(function(event){
        if(event.keyCode ==13){
            var address = $('#address').val();
            local.search(address);
            return false;
        }
    });
});

function saveTagging() {
    if (j && w) {
        jQuery.ajax({
            type: 'post',
            dataType: 'json',
            url: '../tagging/saveTaggingMap.html',
            data: {
                "tagId" : $('#tagId').val(),
                "customerId" : $('#customerId').val(),
                "loanId" : $('#loanId').val(),
                "columnName" : $('#columnName').val(),
                "tableName" : $('#tableName').val(),
                "lbizId" : $('#lbizId').val(),
                "tagLongitude" : j,
                "tagLatitude" :	w
            },
            async: false,
            success: function (result) {
                if (result.success) {

                    showConfirm({
                        icon: 'succeed',
                        content: '标注成功'
                    });
                    closeDialog(address);
                } else {
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                }
            }
        });
    } else {
        showConfirm({
            icon: 'confirm',
            content: '还没有标注地图上的点'
        });
    }
}

function showInfo(e){
    map.clearOverlays();
    j = e.point.lng;
    w = e.point.lat;

    var pt = new BMap.Point(j, w);
    var myIcon = new BMap.Icon("../core/imgs/icon/customer.png", new BMap.Size(30,55));
    var gc = new BMap.Geocoder();
    //获取地址的数据地址
    gc.getLocation(pt, function(rs){
        var addComp = rs.addressComponents;
        address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
        var marker = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
        var label = new BMap.Label(address,{offset:new BMap.Size(0,-20)});
        marker.setLabel(label);
        map.addOverlay(marker);              // 将标注添加到地图中
    });
}


//关闭dialog
function closeDialog(address){
    var dialog = getDialog('loanTaggingMap');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    if( win && win.setAddressValue){
        win.setAddressValue(address);
    }
    setTimeout(function() {
        dialog.close();
    }, 0);
}