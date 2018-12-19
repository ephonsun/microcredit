$(function(){

    $("#btnBack").click(function(){
        window.location.href = '../industryGuidelines/getIndustryGuidelinesGloblePage.html'
    });


    $('#industryGuidelinesGlobleTitle').flexigrid({
        height: 'auto',
        width:442,
        colResizable: false,
        url: '../industryGuidelines/globlePageTwo.html?dictColId='+$("#dictColId").val(),
        dataType: 'json',
        fields: [
            { display: '标题', field: 'title', width: 200, align: 'center' },
        ],
        action: {
            display: '操作',
            width: 200,
            align: 'center',
            buttons: [
                {
                    display: '详情',
                    onClick: function(tr, data){
                        pageThree(data)
                    }
                }
            ]
        }
    });
});
function pageThree(data){
    window.location.href = '../industryGuidelines/getPageThree.html?id='+data.id;
}