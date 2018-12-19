// 百度地图API功能
var map = new BMap.Map("allmap");
var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = $('#zoom').val();
var point = new BMap.Point(defaultj, defaultw);
map.centerAndZoom(point, zoom);
$(function () {

    if (locationw == '' || locationj == '') {
        showConfirm({
            icon: 'confirm',
            content: '该地址还未标记地图坐标'
        });
    } else {
        //创建小狐狸
        var pt = new BMap.Point(Number(locationj), Number(locationw));
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
});

function getAttr(){
    showConfirm({
        icon: 'confirm',
        content: '<p>客户姓名：显示客户信</p><p>身份证号：14154549653268</p><p>联系方式：14154549653268</p>'
    });
}