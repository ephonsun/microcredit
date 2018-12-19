// 页面加载完成时
$(function(){
    // 设置#body高度
    var fixBodyHeight = function(){
        $('#body').height($(w).height() - 60);
    };
    fixBodyHeight();
    $(w).resize(function(){
        fixBodyHeight();
    });

    // 子菜单鼠标移入移出效果
    var cls = 'hover';
    $('#body-side').on('mouseover', 'li', function(){
        $(this).addClass(cls);
    }).on('mouseout', 'li', function(){
        $(this).removeClass(cls);
    });
});
