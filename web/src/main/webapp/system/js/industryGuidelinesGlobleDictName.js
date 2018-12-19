$(function(){
    // 加载系统日志数据网格
    $('#industryGuidelinesGlobleDictName').flexigrid({
        height: 'auto',
        width:442,
        colResizable: false,
        url: '../industryGuidelines/globlePageOne.html',
        dataType: 'json',
        fields: [
            { display: '行业分类', field: 'dictName', width: 200, align: 'center' },
        ],
        action: {
            display: '操作',
            width: 200,
            align: 'center',
            buttons: [
                {
                    display: '详情',
                    onClick: function(tr, data){
                        pageTwo(data);
                    }
                }
            ]
        }
    });
});
function pageTwo(data){
    window.location.href = '../industryGuidelines/getPageTwo.html?dictColId='+data.id;
}