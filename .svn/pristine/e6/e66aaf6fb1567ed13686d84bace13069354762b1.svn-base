$(function () {
//给初始日期赋值
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
    var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
    var dateStr = year + "-" + month + "-" + "01";
    var dateStr1 = year + "-" + month + "-" + day;
    $("#txtStartDate").val(dateStr);
    $("#txtEndDate").val(dateStr1);
    var r = $('#role').val();
    var roleId = $('#role').val();

     if(roleId == 2) {
        $('#gg').hide();
    }else{
        $('#hh').show();
        $('#gg').show();
    }


    $('#taskObj').change(
        function () {
            roleId = $(this).val();
            if (roleId == 2) {
                $('#hh').show();
                $('#gg').hide();
                $('#teamGroupId1').val('');
                $('#userIdSelect1').val('');
                $('#teamGroupId').val('');
                $('#userIdSelect').val('');
                getUser();
            } else if (roleId == 1) {
                $("#hh").show();
                $('#gg').show();
                $('#teamGroupId1').val('');
                $('#userIdSelect1').val('');
                $('#teamGroupId').val('');
                $('#userIdSelect').val('');
                getUser();
            }
        });

    $('select').selectbox({});

    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });

    // 清空搜索条件
    $('#btnClean').click(function () {
        toCleanForm('#form');
        if(r==1){
            $("#taskObj").val(1);
        }else{
            $("#taskObj").val(2);
        }
        // $('#taskObj').val(r);
        roleId = r;
        if(roleId==2){
            $('#gg').hide();
        }else{
            $('#hh').show();
            $('#gg').show();
        }
        $('#taskObj').selectbox({});
        $("#txtStartDate").val(dateStr);
        $("#txtEndDate").val(dateStr1);
    });

    $('#btnSearch').click(function () {
      if (roleId == 1 && $("#userIdSelect").val() != "" && $('#teamGroupId').val() != "") {
            $('#btnExport').show();
            loadGrid2();
        } else if (roleId == 1 && $("#teamGroupId").val() == "") {
            showConfirm({
                icon: 'warning',
                content: '请选择团队'
            });
            $('#btnExport').hide();
        } else if (roleId == 1 && $("#userIdSelect").val() == "") {
            showConfirm({
                icon: 'warning',
                content: '请选择客户经理'
            });
            $('#btnExport').hide();
        }

        if (roleId == 2&&$('#gp').val()=="") {
            showConfirm({
                icon: 'warning',
                content: '没有权限，请添联系管理员添加权限'
            });
        }else if (roleId == 2 && $("#teamGroupId").val() != "") {

            $('#btnExport').show();
            loadGrid();
        } else if (roleId == 2 && $("#teamGroupId").val() == "") {
            showConfirm({
                icon: 'warning',
                content: '请选择团队'
            });
            $('#btnExport').hide();
        }
    });

    //导出报表
    $('#btnExport').click(function () {

        if (roleId == 1) {
            window.location.href = '../taskStat/exportReportx.html?userId=' + _userId + "&startDate=" + _startDate + "&endDate=" + _endDate
        } else if (roleId == 2) {
            window.location.href = '../taskStat/exportReportx2.html?teamGroupId=' + _teamGroupId + "&startDate=" + _startDate + "&endDate=" + _endDate
        }
        // else if (roleId == 3) {
        //     window.location.href = '../taskStat/exportReport1.html?startDate=' + _startDate1 + "&endDate=" + _endDate1 + "&groupPermit=" + _groupPermit
        // }
    });

    $(window).load(function() {
        var groupPermit = $('#groupPermit').val();
        var options = [];
        $.getJSON("../taskStat/queryGroupListByGroupPermit.html", {groupPermit: groupPermit},
            function (data) {
                for (var index in data.data) {
                    var option = {};
                    option['value'] = data.data[index].teamGroupId;
                    option['text'] = data.data[index].groupName;
                    option['inputtext'] =data.data[index].groupName;
                    options.push(option);
                }
            })

        $('#teamGroupId1').checkboxtext({
            options: options,
            onCheck: function(text, value){
                $('#userIdSelect1').val('');
                $('#userIdSelect').val('');
                var reviewData = {};
                var reviewUsers = [];
                for (var i = 0; i< value.length; i++) {
                    var user = {};
                    user["teamGroupId"] = value[i];
                    user["groupName"] = text[i];
                    reviewUsers.push(user);
                }
                reviewData['users'] = reviewUsers;
                var str="";
                reviewUsers[(reviewUsers.length-1)]
                for(var index in reviewUsers ){
                    str+=","+reviewUsers[index].teamGroupId
                }
                str=str.substr(1);

                $("#teamGroupId").val(str);
                if(roleId==1){
                    getUser()

                }

            }
        });
    });

    function getUser(){
        var teamGroupId=$('#teamGroupId').val();
        // 2017-12-12团队没有选择不应该查询成员
        if(!teamGroupId)
            return;
        var options = [];
        $.getJSON("../taskStat/queryMemberByTeamGroupId.html",{teamGroupId:teamGroupId},function(data){

            if($('#gp').val()==""){
                var option = {};
                option['value'] = $("#loginUserId").val();
                option['text'] = $("#userName").val();
                option['inputtext'] =$("#userName").val();
                options.push(option);

            }else{
                for (var index in data.data) {
                    var option = {};
                    option['value'] = data.data[index].userId;
                    option['text'] = data.data[index].userName+"---"+data.data[index].groupName;
                    option['inputtext'] =data.data[index].userName;
                    options.push(option);
                }
            }
        });
        $('#userIdSelect1').checkboxtext({
            options: options,
            onCheck: function(text, value){
                var reviewData = {};
                var reviewUsers = [];
                for (var i = 0; i< value.length; i++) {
                    var user = {};
                    user["userId"] = value[i];
                    user["userName"] = text[i];
                    reviewUsers.push(user);
                }
                reviewData['member'] = reviewUsers;
                var str="";
                for(var index in reviewUsers ){
                    str+=","+reviewUsers[index].userId
                }
                str=str.substr(1);
                $("#userIdSelect").val(str);
            }
        });
    }
});


//记录搜索后的关键字
var _userId;
var _teamGroupId;
var _startDate;
var _endDate;
//加载团队列表
function loadGrid() {
    var postJson = getPostFields();
    _teamGroupId = postJson['teamGroupId'] = jQuery.trim($('#teamGroupId').val());
    _startDate = postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    _endDate = postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
    $.post("../taskStat/getGroupListPage.html", postJson,
        function (data) {
            $("#taskGrid").html(data);
        }, "html")

}
//加载客户经理列表
function loadGrid2() {
    var postJson = getPostFields();
    _userId = postJson['userId'] = jQuery.trim($('#userIdSelect').val());
    _startDate = postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    _endDate = postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
    $.post("../taskStat/getPersonListPage.html", postJson,
        function (data) {
            $("#taskGrid").html(data);
        }, "html")

}



