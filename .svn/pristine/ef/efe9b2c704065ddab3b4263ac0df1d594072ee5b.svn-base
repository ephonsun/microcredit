// 百度地图API功能
var map = new BMap.Map("allmap");
var defaultw = $('#defaultw').val();
var defaultj = $('#defaultj').val();
var zoom = $('#zoom').val();
var userId = $('#userId').val();
var point = new BMap.Point(defaultj, defaultw);
map.centerAndZoom(point, zoom);
var markets = [];
var icon = new BMap.Icon("../core/imgs/icon/customer.png", new BMap.Size(30,30));
$(function () {
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
        //$('#customer').blur();

    });

    // 百度地图API功能
    map.enableScrollWheelZoom(true)   ;     //开启鼠标滚轮缩放
    //回车事件
    $("input").keydown(function(event){
        if(event.keyCode ==13){
            $("#btnSearch").trigger("click");
            return false;
        }
    });
    $('#btnSearch').click(function () {
        map.clearOverlays();
        var customer = $('#customer').val();
        jQuery.ajax({
            type : 'post',
            url : '../tagging/getSearchResult.html',
            async : false,
            dataType: "html",
            data : {'customer':customer,'userId':userId},
            success : function(html) {
                $('#search-result').html(html);
            }
        });
    });
    $('#btnClose').click(function() {
        var dialog = getDialog('customerMaps');
        dialog.close();
    });
});
function setTraject(points) {
    //markets = [];
    map.clearOverlays();
    var i = 0;
    $.each(points, function (index, value) {
        var point = new BMap.Point(value.lng, value.lat);
        if (i == 0) {
            i = 1;
            map.centerAndZoom(point, zoom);
        }
        var myIcon = new BMap.Icon("../core/imgs/icon/search-list.png", new BMap.Size(30,55));
        var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
        marker.addEventListener("click",function () {
            customerClick(value.lng,value.lat,value.customerName, true);
        });
        //map.getMarkers()
        map.addOverlay(marker);              // 将标注添加到地图中
    });
}
function customerClick(j,w, info, noremove){
    var allOverlay = map.getOverlays();
    var point = new BMap.Point(j, w);
    var myIcon = new BMap.Icon("../core/imgs/icon/search-list.png", new BMap.Size(30,55));
    if (allOverlay) {
        $(allOverlay).each(function (index,value) {
            if (value && value.getLabel) {
                var label = value.getLabel();
                if (label) {
                    if (!point.equals(value.getPosition())) {
                        label.remove();
                    }
                } else {
                    if (point.equals(value.getPosition())) {
                        var label = new BMap.Label(info,{offset:new BMap.Size(-8,-65)});
                        label.setStyle({                                   //给label设置样式，任意的CSS都是可以的
                            color:"#525252",                   //颜色
                            fontSize:"14px",               //字号
                            border:"0",                    //边
                            height:"63px",                //高度
                            padding: '0 8px',         //宽
                            textAlign:"center",            //文字水平居中显示
                            lineHeight:"45px",            //行高，文字垂直居中显示
                            background:"url(../core/imgs/icon/map-msg.png)",    //背景图片，这是房产标注的关键！
                            cursor:"pointer",
                            backgroundSize:'100% 100%'
                        });
                        value.setLabel(label)
                    }
                    if (window.closeResult)
                        closeResult();
                }
            }
        });
    }


    // var label = value.getLabel();
    // if (label)
    //     label.remove();
    // marker.setLabel(label);
    // map.addOverlay(marker);              // 将标注添加到地图中
    // var thiszoom = map.getZoom();
    if (!noremove)
        map.centerAndZoom(point, zoom);
}