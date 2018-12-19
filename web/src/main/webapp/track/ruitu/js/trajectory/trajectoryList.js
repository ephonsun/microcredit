// 页面加载完成时...
$(function(){
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


    // 加载列表
    $('#taskGrid').flexigrid({
        height: 500,
        url: '../trajectory/queryManage.html',
        usePage: true,
        //multiSelect:true,
        rowIdProperty: 'id',
        fields: [{ display: '客户经理', field: 'userName', width: 240 ,align : 'center' },
            { display: '团队名称', field: 'teamName', width: 400 ,align : 'center' }],
        action: {
            display: '操作',
            width: 100,
            align : 'center',
            buttons: [{ display: '查看轨迹', onClick: function(tr, data){ viewTrackInfo(data, '查看轨迹'); }}]
        },
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });
    $('select').selectbox();
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');
        setTimeout(function(){$("input[name='customer']").focus();},100);
        setTimeout(function(){$("input[name='customer']").blur();},100);
        //$('#customer').blur();

    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchList();
    });
    //刷新
    $('#btnRefresh').click(function(){
        refreshList();
    });
});


//列表详情/编辑
function viewTrackInfo(data, title){
    var url = '../trajectory/getTrajectoryPage.html?id='+data.id + '&random='+Math.random()*10000;
    showDialog({
        id: 'trajectoryMap',
        title: title,
        url: url,
        width: 920,
        height: 550,
        tabId: tabs.getSelfId(window)
    });
}

//列表搜索
function searchList() {
    var postJson = {};
    postJson['teamId'] = jQuery.trim($('#teamId').val());
    postJson['memberUser'] = $('#memberUser').val();
    $('#taskGrid').flexSearch(postJson);
}
// 刷新人员表
function refreshList() {
    $('#taskGrid').flexReload();
}
