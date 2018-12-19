$(function () {
    // initImgDiv();
    var options = {
        'title': '',
        'headToolbar':['maximize','close'],
        'footToolbar':[
            'zoomIn',
            'zoomOut',
            'prev',
            'next',
            'rotateLeft',
            'rotateRight'
        ],
        'fixedContent': false
    };
    $('[data-magnify=galleryLoanFile]').magnify(options);
});
var loanId=$('#_loanId').val();

// function initImgDiv() {
//     var total = $("#total").val();
//     var i = 0;
//     for (; i < total; i++) {
//         fileId = $("#fileId_" + i).val();
//         loanId = $("#loanId").val();
//         var url = '../loanFile/previewImage.html?fileId=' + fileId +'&loanId='+loanId;
//         var imgStr = "<img width='100%' height='80%' " +
//             "src=" + url + "/>";
//         $('#imagePreview_' + i).prepend(imgStr);
//     }
// }


$('select').selectbox({});

//一级下拉选
$(window).load(function () {
    $.getJSON("../loanFile/getOwnerName.html",{"loanId": loanId},
        function (data) {
            if(data!=null){
                for (var index in data.data) {
                    $('#fileType').append("<option value=" + data.data[index].ownerId + ">" + data.data[index].ownerName + "</option>");
                }
            }
        });
});

//二级下拉选联动
$('#fileType').change(function () {
    var ownerId=$(this).val()
    //debugger
    $.getJSON("../loanFile/getClassNameByOwnerId.html", {"ownerId": ownerId,"loanId": loanId}, function (data) {
        $('#subClass').val('')
        $('select').selectbox({});
        $('#subClass').empty();
        $('#subClass').append("<option></option>");
        for (var index in data.data) {
            $('#subClass').append("<option value=" + data.data[index].classId + ">" + data.data[index].className + "</option>");
        }
    });
});

//照片浏览重置
$('#btnClean').click(function () {
    toCleanForm('#form');
});
//照片浏览搜索
$('#btnSearch').click(function () {
$('#imglist').html('');
$('#taskGrid').html('');
    searchImg();

});

function searchImg(){
    var ownerId=$('#fileType').val();
    var classId=$('#subClass').val();
    $.post("../loanFile/getSearchList.html", {"loanId":loanId,"ownerId":ownerId,"classId":classId},
        function (data) {
            $("#taskGrid").html(data);
        }, "html")
}

function  orginalImg(id,className) {
    var imgUrl = '../loanFile/getOriginalImage.html?id=' + id+'&random='+Math.random()*10000;;

    // showDialog({
    //     id: 'orginalImg',1
    //     title: '照片',
    //     url: imgUrl,
    //     width: 800,
    //     height: 600,
    //     tabId: tabs.getSelfId(window)
    // });
    var r= window.open();
    r.document.write("<title>"+className+"</title>"+"<img src="+imgUrl+"/>");
}