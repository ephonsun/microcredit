$(function () {
    var r = $('#role').val();
    var roleId = $('#role').val();
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth()+1;

    $('#startYear').val(year);
    $('#startYear1').val(year);
    $('#startYear2').val(year);
    $('#endYear').val(year);
    $('#endYear1').val(year);
    $('#endYear2').val(year);
    $('#startMonth').val('01');
    $('#endMonth').val(month);

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
        roleId1=roleId;
        if (_ptype == 3) {
            pd(roleId);
        }
        if (_ptype == 2) {
            pd1(roleId);
        }
        if (_ptype == 1) {
            pd2(roleId);
        }
    });
    $('select').selectbox({});
    // 清空搜索条件
    $('#btnClean').click(function () {
        toCleanForm('#form');
        $('#startYear').val(year);
        $('#startYear1').val(year);
        $('#startYear2').val(year);
        $('#endYear').val(year);
        $('#endYear1').val(year);
        $('#endYear2').val(year);
        $('#startMonth').val('01');
        $('#endMonth').val(month);
        $('#t1').show();
        $('#t2').hide();
        $('#t3').hide();
        $('#taskObj').val(r);
        roleId = r;
        _ptype = 3;
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
        oldPostJson=null;
    });

    //导出
    $('#btnExport').click(function () {
        if (_ptype == 3) {
            if (roleId == 1) {
                window.location.href = '../loanExport/exportByUserId.html?ttype=' + _ttype + "&userId=" + _userId + "&startYear=" + _startYear + "&endYear=" + _endYear +
                    "&startYear1=" + _startYear1 + "&endYear1=" + _endYear1 + "&startYear2=" + _startYear2 + "&endYear2=" + _endYear2 + "&startMonth=" + _startMonth +
                    "&endMonth=" + _endMonth + "&startMonth1=" + _startMonth1 + "&endMonth1=" + _endMonth1 + "&isLocalPerson="+_isLocalPerson + "&isHaveHouse="+_isHaveHouse+"&loanType="+_loanType
            }
            if (roleId == 2) {
                window.location.href = '../loanExport/exportByTeamGroupId.html?teamGroupId=' + _teamGroupId + "&ttype=" + _ttype + "&startYear=" + _startYear + "&endYear=" + _endYear +
                    "&startYear1=" + _startYear1 + "&endYear1=" + _endYear1 + "&startYear2=" + _startYear2 + "&endYear2=" + _endYear2 + "&startMonth=" + _startMonth +
                    "&endMonth=" + _endMonth + "&startMonth1=" + _startMonth1 + "&endMonth1=" + _endMonth1 + "&isLocalPerson="+_isLocalPerson + "&isHaveHouse="+_isHaveHouse+"&loanType="+_loanType
            }
            if (roleId == 3) {
                window.location.href = '../loanExport/exportCross.html?groupPermit=' + _groupPermit + "&ttype=" + _ttype + "&startYear=" + _startYear + "&endYear=" + _endYear +
                    "&startYear1=" + _startYear1 + "&endYear1=" + _endYear1 + "&startYear2=" + _startYear2 + "&endYear2=" + _endYear2 + "&startMonth=" + _startMonth +
                    "&endMonth=" + _endMonth + "&startMonth1=" + _startMonth1 + "&endMonth1=" + _endMonth1 + "&isLocalPerson="+_isLocalPerson + "&isHaveHouse="+_isHaveHouse+"&loanType="+_loanType
            }
        } else {
            chart = $('#container').highcharts();
            chart.exportChart({
                exportFormat: 'PNG'
            });
        }
    });

    //根据记录搜索后的oldPostJson进行图表类型切换，roleId1是记录搜索时的roleId
    $('input[name=ptype]').click(function () {
        _ptype = $('input[name=ptype]:checked').val();
        if(oldPostJson!=null) {
            //统计表
            if (_ptype == 3) {
                $("#taskGrid").html('');
                $("#container").html('').removeAttr('data-highcharts-chart');
                if (roleId1 == 1) {
                    $.post("../loanStat/getPersonListPage.html", oldPostJson,
                        function (data) {
                            $("#taskGrid").html(data);
                        }, "html")
                }
                if (roleId1 == 2) {
                    $.post("../loanStat/getGroupListPage.html", oldPostJson,
                        function (data) {
                            $("#taskGrid").html(data);
                        }, "html");
                }
                if (roleId1 == 3) {
                    $.post("../loanStat/getCrossListPage.html", oldPostJson,
                        function (data) {
                            $("#taskGrid").html(data);
                        }, "html");
                }
            }
            //柱状图
            if (_ptype == 2) {
                $("#taskGrid").html('');
                if (roleId1 == 1) {
                    $.getJSON("../loanStatPic/queryLoanStatPicListByUserId.html", oldPostJson, function (data) {
                        z(data);
                    });
                }
                if (roleId1 == 2) {
                    $.getJSON("../loanStatPic/queryLoanStatPicListByTeamGroupId.html", oldPostJson, function (data) {
                        z(data);
                    });
                }
                if (roleId1 == 3) {
                    $.getJSON("../loanStatPic/queryCrossPicList.html", oldPostJson, function (data) {
                        z(data);
                    });
                }
            }
            //折线图
            if (_ptype == 1) {
                $("#taskGrid").html('');
                if (roleId1 == 1) {
                    $.getJSON("../loanStatPic/queryLoanStatPicListByUserId.html", oldPostJson, function (data) {
                        zx(data);
                    });
                }
                if (roleId1 == 2) {
                    $.getJSON("../loanStatPic/queryLoanStatPicListByTeamGroupId.html", oldPostJson, function (data) {
                        zx(data);
                    });
                }
                if (roleId1 == 3) {
                    $.getJSON("../loanStatPic/queryCrossPicList.html", oldPostJson, function (data) {
                        zx(data);
                    });
                }
            }
        }
    });
});

var _userId;
var _startYear;
var _endYear;
var _startMonth;
var _endMonth;
var _startYear1;
var _endYear1;
var _startMonth1;
var _endMonth1;
var _startYear2;
var _endYear2;
var _ttype;
var _teamGroupId;
var _groupPermit;
var _ptype = $('input[name=ptype]:checked').val();


var oldPostJson;
var roleId1;

//加载经理
function loadGrid() {
    var postJson = getPostFields();
    _userId = postJson['userId'] = jQuery.trim($('#userIdSelect').val());
    _startYear = postJson['startYear'] = jQuery.trim($('#startYear').val());
    _endYear = postJson['endYear'] = jQuery.trim($('#endYear').val());
    _startMonth = postJson['startMonth'] = jQuery.trim($('#startMonth').val());
    _endMonth = postJson['endMonth'] = jQuery.trim($('#endMonth').val());
    _startYear1 = postJson['startYear1'] = jQuery.trim($('#startYear1').val());
    _endYear1 = postJson['endYear1'] = jQuery.trim($('#endYear1').val());
    _startMonth1 = postJson['startMonth1'] = jQuery.trim($('#startMonth1').val());
    _endMonth1 = postJson['endMonth1'] = jQuery.trim($('#endMonth1').val());
    _startYear2 = postJson['startYear2'] = jQuery.trim($('#startYear2').val());
    _endYear2 = postJson['endYear2'] = jQuery.trim($('#endYear2').val());
    _ttype = postJson['ttype'] = jQuery.trim($('input[name=ttype]:checked').val());
    _isLocalPerson = postJson['isLocalPerson'] = jQuery.trim($('#isLocalPerson').val());
    _isHaveHouse = postJson['isHaveHouse'] = jQuery.trim($('#isHaveHouse').val());
    _loanType = postJson['loanType'] = jQuery.trim($('#loanType').val());
    _userName = postJson['userName'] = jQuery.trim($('#userName').val());
    oldPostJson = postJson;

    $.post("../loanStat/getPersonListPage.html", postJson,
        function (data) {
            $("#taskGrid").html(data);
        }, "html")
}

//加载团队
function loadGrid1() {
    var postJson = getPostFields();
    _teamGroupId = postJson['teamGroupId'] = jQuery.trim($('#teamGroupId').val());
    _startYear = postJson['startYear'] = jQuery.trim($('#startYear').val());
    _endYear = postJson['endYear'] = jQuery.trim($('#endYear').val());
    _startMonth = postJson['startMonth'] = jQuery.trim($('#startMonth').val());
    _endMonth = postJson['endMonth'] = jQuery.trim($('#endMonth').val());
    _startYear1 = postJson['startYear1'] = jQuery.trim($('#startYear1').val());
    _endYear1 = postJson['endYear1'] = jQuery.trim($('#endYear1').val());
    _startMonth1 = postJson['startMonth1'] = jQuery.trim($('#startMonth1').val());
    _endMonth1 = postJson['endMonth1'] = jQuery.trim($('#endMonth1').val());
    _startYear2 = postJson['startYear2'] = jQuery.trim($('#startYear2').val());
    _endYear2 = postJson['endYear2'] = jQuery.trim($('#endYear2').val());
    _ttype = postJson['ttype'] = jQuery.trim($('input[name=ttype]:checked').val());
    _isLocalPerson = postJson['isLocalPerson'] = jQuery.trim($('#isLocalPerson').val());
    _isHaveHouse = postJson['isHaveHouse'] = jQuery.trim($('#isHaveHouse').val());
    _loanType = postJson['loanType'] = jQuery.trim($('#loanType').val());
    _userName = postJson['userName'] = jQuery.trim($('#userName').val());

    oldPostJson = postJson;

    $.post("../loanStat/getGroupListPage.html", postJson,
        function (data) {
            $("#taskGrid").html(data);
        }, "html");
}

//加载跨团队
function loadGrid2() {
    var postJson = getPostFields();
    _groupPermit = postJson['groupPermit'] = jQuery.trim($('#groupPermit').val());
    _startYear = postJson['startYear'] = jQuery.trim($('#startYear').val());
    _endYear = postJson['endYear'] = jQuery.trim($('#endYear').val());
    _startMonth = postJson['startMonth'] = jQuery.trim($('#startMonth').val());
    _endMonth = postJson['endMonth'] = jQuery.trim($('#endMonth').val());
    _startYear1 = postJson['startYear1'] = jQuery.trim($('#startYear1').val());
    _endYear1 = postJson['endYear1'] = jQuery.trim($('#endYear1').val());
    _startMonth1 = postJson['startMonth1'] = jQuery.trim($('#startMonth1').val());
    _endMonth1 = postJson['endMonth1'] = jQuery.trim($('#endMonth1').val());
    _startYear2 = postJson['startYear2'] = jQuery.trim($('#startYear2').val());
    _endYear2 = postJson['endYear2'] = jQuery.trim($('#endYear2').val());
    _ttype = postJson['ttype'] = jQuery.trim($('input[name=ttype]:checked').val());
    _isLocalPerson = postJson['isLocalPerson'] = jQuery.trim($('#isLocalPerson').val());
    _isHaveHouse = postJson['isHaveHouse'] = jQuery.trim($('#isHaveHouse').val());
    _loanType = postJson['loanType'] = jQuery.trim($('#loanType').val());
    _userName = postJson['userName'] = jQuery.trim($('#userName').val());

    oldPostJson = postJson;

    $.post("../loanStat/getCrossListPage.html", postJson,
        function (data) {
            $("#taskGrid").html(data);
        }, "html");
}

//获取客户经理柱状图数据
function zhu() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryLoanStatPicListByUserId.html", postJson, function (data) {
        z(data);
    });
    oldPostJson = postJson;
}
//获取团队柱状图数据
function zhu1() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryLoanStatPicListByTeamGroupId.html", postJson, function (data) {
        z(data);
    });
    oldPostJson = postJson;
}

//获取跨团队柱状图数据
function zhu2() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryCrossPicList.html", postJson, function (data) {
        z(data);
    });
    oldPostJson = postJson;
}
//柱状图
function z(data) {
    $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '贷款量统计柱状图'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: data.a,
            title: {
                text: '统计时间'
            },
            gridLineColor: '#5E7F7A',
            gridLineWidth: 1,
            gridLineDashStyle: 'longdash',
            crosshair: true
        },
        yAxis: {
            floor: 0,
            ceiling: 100,
            title: {
                text: '数量 (笔)'
            },
            gridLineColor: '#5E7F7A',
            gridLineWidth: 1,
            gridLineDashStyle: 'longdash'
        }, credits: {
            text: '',
            href: ''
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.0f}笔</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        exporting: {enabled: false},
        series: data.b
    });
}

//获取客户经理折线图数据
function zhe() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryLoanStatPicListByUserId.html", postJson, function (data) {
        zx(data);
    });
    oldPostJson = postJson;
}
//获取团队折线图数据
function zhe1() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryLoanStatPicListByTeamGroupId.html", postJson, function (data) {
        zx(data);
    });
    oldPostJson = postJson;
}

//获取跨团队折线图数据
function zhe2() {
    var postJson = getPostFields();
    $.getJSON("../loanStatPic/queryCrossPicList.html", postJson, function (data) {
        zx(data);
    });
    oldPostJson = postJson;
}
//折线图加载
function zx(data) {
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '贷款量统计曲线图',
            x: -20
        },
        subtitle: {
            text: '',
            x: -20
        },
        xAxis: {
            categories: data.a,
            title: {
                text: '统计时间'
            },
            gridLineColor: '#5E7F7A',
            gridLineWidth: 1,
            gridLineDashStyle: 'longdash'
        },
        yAxis: {
            floor: 0,
            ceiling: 100,
            title: {
                text: '数量 (笔)'
            },
            gridLineColor: '#5E7F7A',
            gridLineWidth: 1,
            gridLineDashStyle: 'longdash',
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        credits: {
            text: '',
            href: ''
        },
        tooltip: {
            valueSuffix: '笔'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        exporting: {enabled: false},
        series: data.b
    });

}
//根据roleId判断加载统计表
function pd(roleId) {
    if (roleId == 1 && $("#userIdSelect").val() != "" && $('#teamGroupId').val() != "") {
        $('#btnExport').show();
        loadGrid();
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
    if (roleId == 2 && $("#teamGroupId").val() != "") {

        $('#btnExport').show();
        loadGrid1();
    } else if (roleId == 2 && $("#teamGroupId").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择团队'
        });
        $('#btnExport').hide();
    }
    if (roleId == 3&&$('#gp').val()=="") {
        showConfirm({
            icon: 'warning',
            content: '没有权限，请添联系管理员添加权限'
        });
    }else if(roleId == 3){
        $('#btnExport').show();
        loadGrid2();
    }
}
//根据roleId判断加载柱状图
function pd1(roleId) {
    if (roleId == 1 && $("#userIdSelect").val() != "" && $('#teamGroupId').val() != "") {
        $('#btnExport').show();
        zhu();
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
    if (roleId == 2 && $("#teamGroupId").val() != "") {

        $('#btnExport').show();
        zhu1();
    } else if (roleId == 2 && $("#teamGroupId").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择团队'
        });
        $('#btnExport').hide();
    }
    //主管没有权限时,搜索对象为空，点搜索时候提示信息
    if (roleId == 3&&$('#gp').val()=="") {
        showConfirm({
            icon: 'warning',
            content: '没有权限，请添联系管理员添加权限'
        });

    }else if(roleId==3){
        $('#btnExport').show();
        zhu2();
    }
}
//根据roleId判断加载折线图
function pd2(roleId) {
    if (roleId == 1 && $("#userIdSelect").val() != "" && $('#teamGroupId').val() != "") {
        $('#btnExport').show();
        zhe();
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
    if (roleId == 2 && $("#teamGroupId").val() != "") {

        $('#btnExport').show();
        zhe1();
    } else if (roleId == 2 && $("#teamGroupId").val() == "") {
        showConfirm({
            icon: 'warning',
            content: '请选择团队'
        });
        $('#btnExport').hide();
    }
    if (roleId == 3&&$('#gp').val()=="") {
        showConfirm({
            icon: 'warning',
            content: '没有权限，请添联系管理员添加权限'
        });

    }else if(roleId == 3){
        $('#btnExport').show();
        zhe2();
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