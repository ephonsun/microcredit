var locationw = $('#locationw').val();
var locationj = $('#locationj').val();
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var userId = $('#userId').val();
var forApp = $('#forApp').val();
var zoom = parseInt($('#zoom').val());
// var zoom = 3;

if(forApp=="true"){
    RMapConstant.MapRoot = appMapIp+"/PathUrl.ashx?path=/";
}else{
    RMapConstant.MapRoot = serverMapIp+"/PathUrl.ashx?path=/";
}
var rmap = new RMap(document.getElementById("allmap"), defaultj, defaultw, zoom, $('#allmap').width(), $('#allmap').height());
var markers = [];
$(function () {
    //显示地图
    rmap.show();
    rmap.setCenter(defaultj, defaultw);
    //浏览器不支持 placeholder 时才执行
    if (!('placeholder' in document.createElement('input'))) {
        $('[placeholder]').each(function () {
            var $tag = $(this); //当前 input
            var $copy = $tag.clone();   //当前 input 的复制
            if ($copy.val() == "") {
                $copy.css("color", "#999");
                $copy.val($copy.attr('placeholder'));
            }
            $copy.focus(function () {
                if (this.value == $copy.attr('placeholder')) {
                    this.value = '';
                    this.style.color = '#000';
                }
            });
            $copy.blur(function () {
                if (this.value=="") {
                    this.value = $copy.attr('placeholder');
                    $tag.val("");
                    this.style.color = '#a9a9a9';
                } else {
                    $tag.val(this.value);
                }
            });
            $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
        });
    }
    $('#btnClean').click(function() {
        toCleanForm('#form');
        setTimeout(function(){$("input[name='customer']").focus();},100);
        setTimeout(function(){$("input[name='customer']").blur();},100);
    });
    //回车事件
    $("input").keydown(function(event){
        if(event.keyCode ==13){
            $("#btnSearch").trigger("click");
            return false;
        }
    });
    $('#btnSearch').click(function () {
        rmap.removeAllMarker();
        rmap.removeAllGraphics();
        var customer = $('#customer').val();
        jQuery.ajax({
            type : 'post',
            url : '../tagging/getSearchResult.html',
            async : false,
            dataType: "html",
            data : {'customer':customer, 'userId':userId},
            success : function(html) {
                $('#search-result').html(html);
            }
        });
    });
    $('#btnClose').click(function() {
        var dialog = getDialog('customerMaps');
        dialog.close();
    });
    rmap.removeAllMarker();
    rmap.removeAllGraphics();
    var customer = $('#customer').val();
    jQuery.ajax({
        type : 'post',
        url : '../tagging/getSearchResult.html',
        async : false,
        dataType: "html",
        data : {'customer':customer, 'userId':userId},
        success : function(html) {
            $('#search-result').html(html);
        }
    });
});
function setTraject(points) {

    rmap.removeAllMarker();
    var i = 0;
    $.each(points, function (index, value) {
        var point = {};
        point.Cx = value.lng;
        point.Cy = value.lat;
        if (i == 0) {
            i = 1;
            rmap.setCenter(value.lng, value.lat);
        }
        showLabel(value.lng, value.lat, value.customerName);
        // var pointMarker = new RPointMarker( value.lng, value.lat, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
        // //将标注添加入 rmap对象
        // rmap.addMarker(pointMarker);
        // pointMarker.runClickEvent=function(e){
        //     showLabel(value.lng, value.lat, value.customerName);
        // };
    });
}
function customerClick(j,w, info){
    $.each(markers, function (index, value) {
        if (j == value.Cx && w == value.Cy) {
            rmap.setCenter(j, w);
            value.showLabel();
        } else {
            value.hideLabel();
        }
    });
    if (window.closeResult)
        closeResult();
    // rmap.removeAllMarker();
    // $.each(points, function (index, value) {
    //     var point = {};
    //     point.Cx = value.lng;
    //     point.Cy = value.lat;
    //     if (j == value.lng) {
    //         rmap.setCenter(value.lng, value.lat);
    //         showLabel(value.lng, value.lat, info);
    //     } else{
    //         var pointMarker = new RPointMarker( value.lng, value.lat, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
    //         //将标注添加入 rmap对象
    //         rmap.addMarker(pointMarker);
    //         pointMarker.runClickEvent=function(e){
    //             showLabel(value.lng, value.lat, info);
    //         };
    //     }
    // });
}

function showLabel(j, w, info) {
    // var pointMarkern = new RLabelMarker( j, w, info, "../core/imgs/icon/customer.png", -15, -30, 30, 30);
    var wnd = {"detaX":-4,"detaY":-65,"html":'<div style="color:#525252;font-size:14px;border:0;height:63px;padding:0 8px;white-space: pre;min-width: 70px;' +
    'text-align:center;line-height:45px;background:url(../core/imgs/icon/map-msg.png);cursor:pointer;background-size:100% 100%;">'+info+'</div>'};
    var pointMarker = new RSelfMarker(j, w, wnd, '../core/imgs/icon/customer.png', -15, -30, 30, 30);
    pointMarker.runClickEvent=function(e){
        if (pointMarker.isShowTitle)
            pointMarker.hideLabel();
        else pointMarker.showLabel();
    };
    rmap.addMarker(pointMarker);
    //pointMarkern.showLabel();
    markers.push(pointMarker);

}