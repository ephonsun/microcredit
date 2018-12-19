$(function () {

    // 加载操作时间
    $('#applyStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'applyEndDate\')}'
    });
    $('#applyEndDate').datepicker({
        minDate: '#F{$dp.$D(\'applyStartDate\')}'
    });
    $('#investStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'investEndDate\')}'
    });
    $('#investEndDate').datepicker({
        minDate: '#F{$dp.$D(\'investStartDate\')}'
    });
    $('#approvalStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'approvalEndDate\')}'
    });
    $('#approvalEndDate').datepicker({
        minDate: '#F{$dp.$D(\'approvalStartDate\')}'
    });
    $('#loanStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'loanEndDate\')}'
    });
    $('#loanEndDate').datepicker({
        minDate: '#F{$dp.$D(\'loanStartDate\')}'
    });
    var r = $('#role').val();
    var roleId = $('#role').val();
    if (roleId == 3) {
        $('#hh').hide();
        $('#gg').hide();
    } else if(roleId == 2) {
        $('#gg').hide();
    }else{
        $('#hh').show();
        $('#gg').show();
    }

    //一级下拉选change事件
    $('#taskObj').change(
        function () {
            roleId = $(this).val();
            if (roleId == 3) {
                $('#hh').hide();
                $('#gg').hide();
            } else if (roleId == 2) {
                $('#hh').show();
                $('#gg').hide();
                $('#teamGroupId').val('');
                $('#userIdSelect').val('');
                $('select').selectbox({});
            } else if (roleId == 1) {
                $("#hh").show();
                $("#gg").show();
                $('#teamGroupId').val('');
                $('#userIdSelect').val('');
                $('select').selectbox({});
            }
        });
    //二级下拉选change事件
    $('#teamGroupId').change(
        function () {
            var groupId = $('#teamGroupId').val();
            $('#userIdSelect').val('')
            $('select').selectbox({});
            $.getJSON("../taskStat/queryMemberByTeamGroupId.html", {teamGroupId: groupId}, function (data) {
                $('#userIdSelect').empty();
                $('#userIdSelect').append("<option></option>");
                if($('#gp').val()==""){
                    $('#userIdSelect').append("<option value=" + $("#loginUserId").val() + ">" + $("#userName").val() + "</option>");
                }else{
                    for (var index in data.data) {
                        $('#userIdSelect').append("<option value=" + data.data[index].userId + ">" + data.data[index].userName + "</option>");
                    }}
            });
        });
    // 搜索按钮
    $('#btnSearch').click(function () {
        pd(roleId);
    });
    $('select').selectbox({});
    // 清空搜索条件
    $('#btnClean').click(function () {
        $('#btnExport').hide();
        toCleanForm('#form');
        $('#t1').show();
        $('#t2').hide();
        $('#t3').hide();
        $('#taskObj').val(r);
        roleId = r;
        if (roleId == 3) {
            $('#hh').hide();
            $('#gg').hide();
        } else if(roleId == 2) {
            $('#gg').hide();
        }else{
            $('#hh').show();
            $('#gg').show();
        }
        $('select').selectbox({});
    });

    //导出
    $('#btnExport').click(function () {
        var applyStartDate= jQuery.trim($('#applyStartDate').val());
        var applyEndDate = jQuery.trim($('#applyEndDate').val());
        var investStartDate = jQuery.trim($('#investStartDate').val());
        var investEndDate = jQuery.trim($('#investEndDate').val());
        var approvalStartDate = jQuery.trim($('#approvalStartDate').val());
        var approvalEndDate = jQuery.trim($('#approvalEndDate').val());
        var loanStartDate= jQuery.trim($('#loanStartDate').val());
        var loanEndDate = jQuery.trim($('#loanEndDate').val());
        var role = roleId;
        var userId="";
        var teamGroupId="";
        var groupPermit="";
        if(role==1){
            userId = jQuery.trim($('#userIdSelect').val());
        }
        if(role==2){
            teamGroupId = jQuery.trim($('#teamGroupId').val());
        }
        if(role==3){
            groupPermit = jQuery.trim($('#groupPermit').val());
        }
        window.location.href ="../queryReport/exportFile.html?"+ "applyStartDate="+applyStartDate+"&applyEndDate="+applyEndDate
        +"&investStartDate="+investStartDate+"&investEndDate="+investEndDate
        +"&approvalStartDate="+approvalStartDate+"&approvalEndDate"+approvalEndDate
        +"&loanStartDate="+loanStartDate+"&loanEndDate="+loanEndDate
        +"&role="+role+"&userId="+userId+"&teamGroupId="+teamGroupId+"&groupPermit="+groupPermit;


    });
});


//加载经理
function loadGrid(role) {
    var postJson = getPostFields();
    postJson['applyStartDate'] = jQuery.trim($('#applyStartDate').val());
    postJson['applyEndDate'] = jQuery.trim($('#applyEndDate').val());
    postJson['investStartDate'] = jQuery.trim($('#investStartDate').val());
    postJson['investEndDate'] = jQuery.trim($('#investEndDate').val());
    postJson['approvalStartDate'] = jQuery.trim($('#approvalStartDate').val());
    postJson['approvalEndDate'] = jQuery.trim($('#approvalEndDate').val());
    postJson['loanStartDate'] = jQuery.trim($('#loanStartDate').val());
    postJson['loanEndDate'] = jQuery.trim($('#loanEndDate').val());
    postJson['role'] = role;
    if(role==1){
        postJson['userId'] = jQuery.trim($('#userIdSelect').val());
    }
    if(role==2){
        postJson['teamGroupId'] = jQuery.trim($('#teamGroupId').val());
    }
    if(role==3){
        postJson['groupPermit'] = jQuery.trim($('#groupPermit').val());
    }
    //$.post("../queryReport/getQueryReportData.html", postJson,
    //    function (data) {
    //        $("#queryGrid").html(data);
    //    }, "html")
    // 加载列表
    $('#grid').flexigrid({
        height: 200,
        url: '../queryReport/getQueryReportDataJson.html',
        usePage: true,
        params:postJson,
        //multiSelect:true,
        rowIdProperty: 'id',
        fields: [
            //{ display: '贷款阶段', field: 'loanStatus', width: 100 ,align : 'center' },
            { display: '放款日期', field: 'creditDate', width: 100 ,align : 'center' },
            { display: '是否居民', field: 'isLocal', width: 50 ,align : 'center' },
            { display: '客户姓名', field: 'customerName', width: 50 ,align : 'center' },
            { display: '决议金额', field: 'approvalMoney', width: 80 ,align : 'center' },
            { display: '合同金额', field: 'creditMoney', width: 80 ,align : 'center' },
            { display: '放款金额', field: 'iouAmount', width: 80 ,align : 'center' },
            { display: '贷款期限', field: 'loanLimit', width: 50 ,align : 'center' },
            { display: '利率', field: 'loanRatio', width: 50 ,align : 'center' },
            { display: '还款方式', field: 'repayWay', width: 150 ,align : 'center' },
            { display: '担保方式', field: 'guarantorWay', width: 100 ,align : 'center' },
            { display: '客户经理-申请', field: 'applyUserName', width: 100 ,align : 'center' },
            { display: '客户经理-调查', field: 'investigateUserName', width: 100 ,align : 'center' },
            { display: '产品名称', field: 'productName', width: 150 ,align : 'center' },
            { display: '贷款类型', field: 'loanType', width: 50 ,align : 'center' },
            { display: '新增/转贷', field: 'isNew', width: 100 ,align : 'center' },
            //{ display: '台账状态', field: 'loanAfterState', width: 50 ,align : 'center' },
            { display: '五级分类', field: 'fiveMarks', width: 50 ,align : 'center' },
            { display: '是否涉农', field: 'isAgriculture', width: 50 ,align : 'center' },
            { display: '业务品种', field: 'businessLineName', width: 100 ,align : 'center' },
            { display: '客户类型', field: 'customerLevel', width: 50 ,align : 'center' },
            { display: '所属行业', field: 'industryCode', width: 100 ,align : 'center' },
            { display: '经营范围', field: 'businessScope', width: 150 ,align : 'center' },
            { display: '投向名称', field: 'businessScope', width: 200 ,align : 'center' },
            { display: '是否转介', field: 'isReferred', width: 100 ,align : 'center' },
            { display: '转介人员', field: 'referredUser', width: 150 ,align : 'center' },
            { display: '转介支行', field: 'referredDept', width: 150 ,align : 'center' },
            { display: '合同号', field: 'loanContractNum', width: 150 ,align : 'center' },
            { display: '合同编码', field: 'contractCode', width: 280 ,align : 'center' },
            { display: '贷款账号', field: 'loanAccountNo', width: 120 ,align : 'center' },
            { display: '合同开始时间', field: 'loanContractBegin', width: 80 ,align : 'center' },
            { display: '合同结束时间', field: 'loanContractEnd', width: 80 ,align : 'center' },
            { display: '放款开始时间', field: 'loanRatioDate', width: 80 ,align : 'center' },
            { display: '放款结束时间', field: 'loanEndDate', width: 80 ,align : 'center' }
        ]
    });
    $('#grid').flexSearch(postJson);
}



//根据roleId判断加载统计表
function pd(roleId) {
    if (roleId == 1 && $("#userIdSelect").val() != "" && $('#teamGroupId').val() != "") {
        $('#btnExport').show();
        loadGrid(1);
    } else if (roleId == 1 && $("#teamGroupId").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择团队'
        });
    } else if (roleId == 1 && $("#userIdSelect").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择客户经理'
        });
    }
    if (roleId == 2 && $("#teamGroupId").val() != "") {
        $('#btnExport').show();
        loadGrid(2);
    } else if (roleId == 2 && $("#teamGroupId").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择团队'
        });
    }
    if (roleId == 3&&$('#gp').val()=="") {
        showConfirm({
            icon: 'warning',
            content: '没有权限，请添联系管理员添加权限'
        });
    }else if(roleId == 3){
        $('#btnExport').show();
        loadGrid(3);
    }
}


//根据权限加载团队列表
$(window).load(function () {
    $.getJSON("../taskStat/queryGroupListByGroupPermit.html", {groupPermit: $('#groupPermit').val()},
        function (data) {
            if(data!=null){
                for (var index in data.data) {
                    $('#teamGroupId').append("<option value=" + data.data[index].teamGroupId + ">" + data.data[index].groupName + "</option>");
                }
            }
        });
});