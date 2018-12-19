// 页面常量
var loanClassId;
//图表高宽
var chartWidth = 250;
var chartHeight = 250;
var percent = '40%';
// js入口
$(function () {
    // $("#isloaded").parent("div[class='ui-tabs-iframe']").removeAttr("isloaded");
    loanClassId = $("#loanClassId").val();
    setTimeout(function(){showPie();}, 100);
    // showPie();
});
//格式化金额
function toThousands(num) {
    return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}
// 加载饼图 getFinanceAnalysisByLoanId
var postJson = {};
postJson.loanId = $("#loanId").val();
postJson.loanClassId = $("#loanClassId").val();
function showPie(){
    $.getJSON("../loanAnalysis/getFinanceAnalysisByLoanId.html?random=" + Math.random(), postJson, function (datas) {
        if(datas == null){
            showPie()
        }else{
            pie(datas);
        }
    });

}

// 饼图
function pie(datas) {
    //资产分析
    assetAnalysisPie(datas);
    breakEvenAnalysisPie(datas);
    solvencyAnalysisPie(datas);
    // turnoverRatioAnalysisPie(data.turnoverRatioAnalysis);


}
//资产分析
function assetAnalysisPie(datas){
    var bool;//是否显示showInLegend
    var data; var data1; var color1; var color2;
    var assetAnalysis = datas.assetAnalysis;
    var loanAssetsInfo = datas.loanAssetsInfo;
    if(loanClassId == 1) {
        color1 = ['#d96294','#b65aca','#7561d6','#346fc8','#2ea1dd','#3abeca','#3aca99','#7fc937'];
        color2 = ['#f35858','#ff7f46','#ffa62a','#ffd200','#c6dd45'];
        bool = true;
        $("#assetAnalysis_left").before('<table id="assetAnalysis_table1" align="left"><table>');
        if(assetAnalysis.total==0 && assetAnalysis.flow==0) {
            data = [];
            $("#assetAnalysis_foot").hide();
            $("#solvencyAnalysis_grades,#solvency_biz").hide();
            $("#turnoverRatioAnalysis").find("div").hide();
            var comment = '<tr> <td></td><td></td> <td></td></tr>' +
                '<tr> <td></td><td></td> <td></td></tr>';
            $("#assetAnalysis_table1").append(comment);
        }else{
            data = [
                // ['总资产',   parseInt(assetAnalysis.total)],
                // ['其他',   parseInt(assetAnalysis.total - assetAnalysis.flow)],
                // ['流动资产', parseInt(assetAnalysis.flow)]
                ['现金', parseInt(loanAssetsInfo.assetsCashAmount)],
                ['存货', parseInt(loanAssetsInfo.assetsStockAmount)],
                ['应收账款', parseInt(loanAssetsInfo.assetsReceivableAmount)],
                ['预付账款', parseInt(loanAssetsInfo.assetsPaymentAmount)],
                ['其他经营资产', parseInt(loanAssetsInfo.assetsOperatingAmount)],
                ['其他非经营资产', parseInt(loanAssetsInfo.assetsNonOperatingAmount)],
                ['固定资产', parseInt(loanAssetsInfo.assetsFixedAmount)],
                ['其他资产', parseInt(loanAssetsInfo.assetsOtherAmount)]
            ];
            var comment = '<tr> <td></td><td>总资产</td> <td>'+ toThousands(parseInt(assetAnalysis.total))+'</td></tr>' +
                          '<tr> <td></td><td>流动资产</td> <td>'+ toThousands(parseInt(assetAnalysis.flow)) +'('+assetAnalysis.flowRatio+'%)</td></tr>'+
                          '<tr> <td></td><td>表外资产</td> <td>'+ toThousands(parseInt(loanAssetsInfo.offAssetsAmount))+'</td></tr>' +
                '<tr> <td><div class="cell" style="background-color:'+ color1[0]+'"></div></td><td>现金</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsCashAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[1]+'"></div></td><td>存货</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsStockAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[2]+'"></div></td><td>应收账款</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsReceivableAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[3]+'"></div></td><td>预付账款</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsPaymentAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[4]+'"></div></td><td>其他经营资产</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsOperatingAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[5]+'"></div></td><td>其他非经营资产</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsNonOperatingAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[6]+'"></div></td><td>固定资产</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsFixedAmount)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color1[7]+'"></div></td><td>其他资产</td> <td>'+ toThousands(parseInt(loanAssetsInfo.assetsOtherAmount)) +'</td></tr>';

            $("#assetAnalysis_table1").append(comment);
            $("#assetAnalysis_grade").html(assetAnalysis.assetLiabilityRatioGrade).addClass(assetAnalysis.assetLiabilityRatioGradeEn);
            $("#assetAnalysis_grade_exp").html(assetAnalysis.assetLiabilityRatioExp);
        }
        if(assetAnalysis.liabilities==0 && assetAnalysis.flowLiabilities==0) {
            data1 = [];
            var comment = '<tr> <td></td><td></td> <td></td></tr>' +
                '<tr> <td></td><td></td> <td></td></tr>';
            $("#assetAnalysis_table2").append(comment);
        }else{
            data1 = [
                // ['总负债',parseInt(assetAnalysis.liabilities)],
                // ['其他',parseInt(assetAnalysis.liabilities - assetAnalysis.flowLiabilities)],
                // ['流动负债',parseInt(assetAnalysis.flowLiabilities)]
                ['应付账款',parseInt(loanAssetsInfo.debtsPayableAmount)],
                ['预收账款',parseInt(loanAssetsInfo.debtsReceivedAmount)],
                ['短期负责',parseInt(loanAssetsInfo.debtsShortAmount)],
                ['长期负债',parseInt(loanAssetsInfo.debtsLongAmount)],
                ['其他负债',parseInt(loanAssetsInfo.debtsBizOtherAmount)]
            ];
            if(parseFloat(assetAnalysis.liabilities)>0){
                $("#assetAnalysis_foot").show();
                $("#solvencyAnalysis_grades,#solvency_biz").show();
                $("#turnoverRatioAnalysis").find("div").show();
            }
            var comment = '<tr> <td></td><td>总负债</td> <td>'+ toThousands(parseInt(assetAnalysis.liabilities)) + '</td></tr>' +
                          '<tr> <td></td><td>流动负债</td> <td>'+ toThousands(parseInt(assetAnalysis.flowLiabilities)) +'('+assetAnalysis.flowLiabilitiesRatio+'%)</td></tr>'+
                          '<tr> <td></td><td>表外负债</td> <td>'+ toThousands(parseInt(loanAssetsInfo.offDebtsAmount))+'</td></tr>' +
            '<tr> <td><div class="cell" style="background-color:'+ color2[0]+'"></div></td><td>应付账款</td> <td>'+ toThousands(parseInt(loanAssetsInfo.debtsPayableAmount)) +'</td></tr>'+
            '<tr> <td><div class="cell" style="background-color:'+ color2[1]+'"></div></td><td>预收账款</td> <td>'+ toThousands(parseInt(loanAssetsInfo.debtsReceivedAmount)) +'</td></tr>'+
            '<tr> <td><div class="cell" style="background-color:'+ color2[2]+'"></div></td><td>短期负责</td> <td>'+ toThousands(parseInt(loanAssetsInfo.debtsShortAmount)) +'</td></tr>'+
            '<tr> <td><div class="cell" style="background-color:'+ color2[3]+'"></div></td><td>长期负债</td> <td>'+ toThousands(parseInt(loanAssetsInfo.debtsLongAmount)) +'</td></tr>'+
            '<tr> <td><div class="cell" style="background-color:'+ color2[4]+'"></div></td><td>其他负债</td> <td>'+ toThousands(parseInt(loanAssetsInfo.debtsBizOtherAmount)) +'</td></tr>';

            $("#assetAnalysis_table2").append(comment);
        }
    }else if(loanClassId == 2) {
        color1 = ['#8585FE', '#85A3FE','#85C2C2', '#85C2A3', '#85C266', '#339900'];
        color2 = ['#FF6600', '#FF9900','#FFCC00', '#FFFF00'];
        bool = true;
        if(assetAnalysis.money == 0 && assetAnalysis.investmentAssets == 0 && assetAnalysis.externalClaims == 0 &&
            assetAnalysis.advanceCharge == 0 && assetAnalysis.fixedAssets == 0 && assetAnalysis.otherAssets == 0) {
            data = [];
            $("#assetAnalysis_foot").hide();
            $("#solvencyAnalysis_grades,#solvency_biz").hide();
            $("#turnoverRatioAnalysis").hide();
            var comment = '<tr> <td></td><td></td> <td></td></tr>' +
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>';
            $("#assetAnalysis_table1").append(comment);
        }else{
            data = [
                // ['总资产',assetAnalysis.total],
                ['现金',parseInt(assetAnalysis.money)],
                ['投资性资产',parseInt(assetAnalysis.investmentAssets)],
                ['对外债权',parseInt(assetAnalysis.externalClaims)],
                ['预付款',parseInt(assetAnalysis.advanceCharge)],
                ['固定资产',parseInt(assetAnalysis.fixedAssets)],
                ['其他资产',parseInt(assetAnalysis.otherAssets)]
            ];
            var comment = '<tr> <td></td><td>总资产</td> <td>'+ toThousands(parseInt(assetAnalysis.total)) + '</td></tr>' +
                          '<tr> <td><div class="cell" style="background-color:'+ color1[0]+'"></div></td><td>现金</td> <td>'+ toThousands(parseInt(assetAnalysis.money)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color1[1]+'"></div></td><td>投资性资产</td> <td>'+ toThousands(parseInt(assetAnalysis.investmentAssets)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color1[2]+'"></div></td><td>对外债权</td> <td>'+ toThousands(parseInt(assetAnalysis.externalClaims)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color1[3]+'"></div></td><td>预付款</td> <td>'+ toThousands(parseInt(assetAnalysis.advanceCharge)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color1[4]+'"></div></td><td>固定资产</td> <td>'+ toThousands(parseInt(assetAnalysis.fixedAssets)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color1[5]+'"></div></td><td>其他资产</td> <td>'+ toThousands(parseInt(assetAnalysis.otherAssets))+'</td></tr>';
            $("#assetAnalysis_table1").append(comment);
            $("#assetAnalysis_grade").html(assetAnalysis.assetLiabilityRatioGrade).addClass(assetAnalysis.assetLiabilityRatioGradeEn);
            $("#assetAnalysis_grade_exp").html(assetAnalysis.assetLiabilityRatioExp);
        }
        if(assetAnalysis.consumerLiabilities == 0 && assetAnalysis.personalLiabilities == 0 && assetAnalysis.investmentLiability == 0 &&
            assetAnalysis.otherLiability == 0){
            data1 = [];
            var comment = '<tr> <td></td><td></td> <td></td></tr>' +
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>'+
                '<tr> <td><div class="cell"></div></td><td></td> <td></td></tr>';
            $("#assetAnalysis_table2").append(comment);
        }else{
            data1 = [
                // ['总负债', assetAnalysis.liabilities],
                ['消费性负债', parseInt(assetAnalysis.consumerLiabilities)],
                ['自用性负债', parseInt(assetAnalysis.personalLiabilities)],
                ['投资性负债', parseInt(assetAnalysis.investmentLiability)],
                ['其他负债', parseInt(assetAnalysis.otherLiability)]
            ];
            if(parseFloat(assetAnalysis.liabilities)>0){
                $("#assetAnalysis_foot").show();
                $("#solvencyAnalysis_grades,#solvency_biz").show();
            }
            var comment = '<tr> <td></td><td>总负债</td> <td>'+  toThousands(parseInt(assetAnalysis.liabilities)) + '</td></tr>' +
                          '<tr> <td><div class="cell" style="background-color:'+ color2[0]+'"></div></td><td>消费性负债</td> <td>'+  toThousands(parseInt(assetAnalysis.consumerLiabilities)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color2[1]+'"></div></td><td>自用性负债</td> <td>'+  toThousands(parseInt(assetAnalysis.personalLiabilities)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color2[2]+'"></div></td><td>投资性负债</td> <td>'+  toThousands(parseInt(assetAnalysis.investmentLiability)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color2[3]+'"></div></td><td>其他负债</td> <td>'+  toThousands(parseInt(assetAnalysis.otherLiability)) +'</td></tr>';
            $("#assetAnalysis_table2").append(comment);
        }
    }
    $('#assetAnalysis_left').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            width: chartWidth,
            height: chartHeight
        },
        title: {
            // text: '资产分析总资产'
            text: null
        },
        colors: color1,
        tooltip: {
            headerFormat: '{series.name}<br>',
            formatter: function() {
                return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                    Highcharts.numberFormat(this.y, 0, ',') +' 元)';
            }
        },
        lang: {
            noData: "暂 无 数 据" //真正显示的文本
        },
        noData: {
            // Custom positioning/aligning options
            position: {    //相对于绘图区定位无数据标签的位置。 默认值：[object Object].
                // align: 'right',
                // verticalAlign: 'bottom'
            },
            // Custom svg attributes
            attr: {     //无数据标签中额外的SVG属性
                // 'stroke-width': 1,
                // stroke: '#cccccc'
            },
            // Custom css
            style: {    //对无数据标签的CSS样式。 默认值：[object Object].
                // fontWeight: 'bold',
                // fontSize: '15px',
                // color: '#202030'
            }
        },
        plotOptions: {
            pie: {
                innerSize:percent,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.y, 0, ',') +' 元';
                    }
                },
                showInLegend: false
            }
        },
        credits: {
            enabled: false     //不显示LOGO
        },
        exporting: { enabled: false },//隐藏导出图片
        series: [{
            type: 'pie',
            name: '资产分析',
            data: data
        }]
    });
    $('#assetAnalysis_right').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            width: chartWidth,
            height: chartHeight
        },
        colors: color2,
        title: {
            text: null
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            formatter: function() {
                return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                    Highcharts.numberFormat(this.y, 0, ',') +' 元)';
            }
        },
        lang: {
            noData: "暂 无 数 据" //真正显示的文本
        },
        plotOptions: {
            pie: {
                innerSize:percent,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.y, 0, ',') +' 元';
                    },
                },

                showInLegend: false
            }
        },
        credits: {
            enabled: false     //不显示LOGO
        },
        exporting: { enabled: false },//隐藏导出图片
        series:  [{
            type: 'pie',
            name: '资产负债',
            data: data1
        }]
    });
    if(parseFloat(assetAnalysis.actualInterest) < 0) {
        $("#actualInterest,#assetLiabilityRatio").css("color", "red");
    }
    $("#actualInterest").html(toThousands(parseInt(assetAnalysis.actualInterest)));
    $("#assetLiabilityRatio").html(assetAnalysis.assetLiabilityRatio + "%");
    if(data.length == 0 && data1.length == 0) {
        $("#assetAnalysis_table1,#assetAnalysis_table2").empty();
        $("#assetAnalysis_top").css("margin",0)
    }
}
//损益分析
function breakEvenAnalysisPie(datas) {
    var breakEvenAnalysis = datas.breakEvenAnalysis;
    var loanProfitLossInfo = datas.loanProfitLossInfo;
    var data;
    var color;
    if(loanClassId == 1) {
        if(breakEvenAnalysis.totalNetProfit == 0 && breakEvenAnalysis.variableCostExpenditure == 0 &&
            breakEvenAnalysis.monthlyAverageAvailable == 0){
            data = [];
            $("#breakEven_biz").hide();
            $("#breakEvenAnalysisTable_left").css("width", "50%");
        }else{
            data = [
                ['营业收入', parseInt(loanProfitLossInfo.businessIncomeAmount)],
                ['其他收入',   parseInt(loanProfitLossInfo.otherIncomeAmount)],
                ['固定成本支出', parseInt(loanProfitLossInfo.fixedCostDefrayAmount)],
                ['所得税支出', parseInt(loanProfitLossInfo.texDefrayAmount)],
                ['其他支出', parseInt(loanProfitLossInfo.otherDefrayAmount)]

            ];
            color = ['#5a90ea', '#43aeeb', '#f40767','#ffa524','#ffd738'];
            var comment = '<tr> <td><div class="cell" style="background-color:'+ color[0]+'"></div></td><td>营业收入</td> <td>'+ toThousands(parseInt(loanProfitLossInfo.businessIncomeAmount)) + '</td></tr>' +
                          '<tr> <td><div class="cell" style="background-color:'+ color[1]+'"></div></td><td>其他收入</td> <td>'+ toThousands(parseInt(loanProfitLossInfo.otherIncomeAmount)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color[2]+'"></div></td><td>固定成本支出</td> <td>'+ toThousands(parseInt(loanProfitLossInfo.fixedCostDefrayAmount)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color[3]+'"></div></td><td>所得税支出</td> <td>'+ toThousands(parseInt(loanProfitLossInfo.texDefrayAmount)) +'</td></tr>'+
                          '<tr> <td><div class="cell" style="background-color:'+ color[4]+'"></div></td><td>其他支出</td> <td>'+ toThousands(parseInt(loanProfitLossInfo.otherDefrayAmount)) +'</td></tr>';
            $("#breakEvenAnalysisTable_left").append(comment);

            var comment1 = '<tr> <td></td><td>总销售额</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.totalSales)) + '</td>' +
                            '<td></td><td>毛利率</td> <td>'+ parseInt(breakEvenAnalysis.grossProfitRate) +'%</td></tr>'+
                          '<tr> <td></td><td>总毛利润</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.grossProfit)) +'</td>'+
                            '<td></td><td>总净利润</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.totalNetProfit)) +'</td></tr>'+
                          '<tr> <td></td><td>可变成本支出</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.variableCostExpenditure)) +'</td>'+
                            '<td></td><td>月平均月可支</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.monthlyAverageAvailable)) +'</td></tr>';

            $("#breakEvenAnalysisTable_right").append(comment1);

            $("#netInterestRate_grade").html(breakEvenAnalysis.netInterestRateGrade).addClass(breakEvenAnalysis.netInterestRateGradeEn);
            $("#netInterestRate_grade_exp").html(breakEvenAnalysis.netInterestRateExp);
            $("#returnOnEquity_grade").html(breakEvenAnalysis.returnOnEquityGrade).addClass(breakEvenAnalysis.returnOnEquityGradeEn);
            $("#returnOnEquity_grade_exp").html(breakEvenAnalysis.returnOnEquityExp);

        }
        $("#netInterestRate").html(breakEvenAnalysis.netInterestRate + "%");
        if(parseFloat(datas.assetAnalysis.actualInterest) == 0) {
            $("#returnOnEquity").html("--");
            $("#returnOnEquity_grade,#returnOnEquity_grade_exp").hide();
        }else{
            $("#returnOnEquity").html(breakEvenAnalysis.returnOnEquity + "%");
        }
    }else if(loanClassId == 2) {
        if(breakEvenAnalysis.householdIncome == 0 && breakEvenAnalysis.otherIncome == 0 && breakEvenAnalysis.fixedExpenditure == 0 &&
            breakEvenAnalysis.individualIncomeTax == 0 && breakEvenAnalysis.otherExpenditure == 0) {
            data = [];
            $("#breakEven_con").hide();
        }else{
            data = [
                ['家庭收入', parseInt(breakEvenAnalysis.householdIncome)],
                ['其他收入', parseInt(breakEvenAnalysis.otherIncome)],
                ['固定支出', parseInt(breakEvenAnalysis.fixedExpenditure)],
                ['个人所得税', parseInt(breakEvenAnalysis.individualIncomeTax)],
                ['其他支出', parseInt(breakEvenAnalysis.otherExpenditure)]
            ];
            color = ['#8585FE', '#85A3FE', '#FF9900','#FFCC00', '#FFFF00'];
            var comment = '<tr> <td><div class="cell" style="background-color:'+ color[0]+'"></div></td><td>家庭收入</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.householdIncome)) + '</td></tr>' +
                '<tr> <td><div class="cell" style="background-color:'+ color[1]+'"></div></td><td>其他收入</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.otherIncome)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color[2]+'"></div></td><td>固定支出</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.fixedExpenditure)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color[3]+'"></div></td><td>个人所得税</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.individualIncomeTax)) +'</td></tr>'+
                '<tr> <td><div class="cell" style="background-color:'+ color[4]+'"></div></td><td>其他支出</td> <td>'+ toThousands(parseInt(breakEvenAnalysis.otherExpenditure)) +'</td></tr>';
            $("#breakEvenAnalysisTable").append(comment);
        }
        $("#grossIncome").html(toThousands(parseInt(breakEvenAnalysis.grossIncome)));
        $("#monthlyAverageAvailable").html(toThousands(parseInt(breakEvenAnalysis.monthlyAverageAvailable)));

    }
    $('#breakEvenAnalysisPie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            width: chartWidth,
            height: chartHeight
        },
        colors : color,
        title: {
            // text: '资产分析总负债'
            text: null
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            formatter: function() {
                return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                    Highcharts.numberFormat(this.y, 0, ',') +' 元)';
            }
        },
        lang: {
            noData: "暂 无 数 据" //真正显示的文本
        },
        plotOptions: {
            pie: {
                innerSize:percent,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    formatter: function() {
                        return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.y, 0, ',') +' 元';
                    },
                },
                showInLegend: false
            }
        },
        credits: {
            enabled: false     //不显示LOGO
        },
        exporting: { enabled: false },//隐藏导出图片
        series:  [{
            type: 'pie',
            name: '损益分析',
            data: data
        }]
    });
}
//偿还能力分析 data.solvencyAnalysis
function solvencyAnalysisPie(data) {
    if($("#loanClassId").val() == 1){
        $("#cashRatio").html(data.solvencyAnalysis.cashRatio == "" ? "--" : data.solvencyAnalysis.cashRatio+ "%");
        $("#flowRatio").html(data.solvencyAnalysis.flowRatio  == "" ? "--" : data.solvencyAnalysis.flowRatio+ "%");
        $("#salesRatio").html(data.solvencyAnalysis.salesRatio  == "" ? "--" : data.solvencyAnalysis.salesRatio+ "%");
        $("#quickRatio").html(data.solvencyAnalysis.quickRatio  == "" ? "--" : data.solvencyAnalysis.quickRatio+ "%");
        $("#solvencyAnalysis_grade").html(data.solvencyAnalysis.quickRatioGrade).addClass(data.solvencyAnalysis.quickRatioGradeEn);
        $("#solvencyAnalysis_grade_exp").html(data.solvencyAnalysis.quickRatioExp);
        turnoverRatioAnalysisPie(data.turnoverRatioAnalysis);
    }else if($("#loanClassId").val() == 2) {
        if(data.solvencyAnalysis.cashRatio == ""){
            $("#quickRatio").html("--");
        }else{
            $("#quickRatio").html(data.solvencyAnalysis.cashRatio + "%");
        }
    }
}
//周转率分析
function turnoverRatioAnalysisPie(turnoverRatioAnalysis) {
    $("#assetTurnover").html(turnoverRatioAnalysis.assetTurnover == "" ? "--" :  turnoverRatioAnalysis.assetTurnover);
    $("#accountsTeceivableTurnover").html(turnoverRatioAnalysis.accountsTeceivableTurnover == "" ? "--" : turnoverRatioAnalysis.accountsTeceivableTurnover);
    $("#inventoryTurnover").html(turnoverRatioAnalysis.inventoryTurnover == "" ? "--" : turnoverRatioAnalysis.inventoryTurnover);

    $("#assetTurnoverDays").html(turnoverRatioAnalysis.assetTurnoverDays == "" ? "--" : parseInt(turnoverRatioAnalysis.assetTurnoverDays));
    $("#daysSalesOutstanding").html(turnoverRatioAnalysis.daysSalesOutstanding == "" ? "--" : parseInt(turnoverRatioAnalysis.daysSalesOutstanding));
    $("#inventoryTurnoverDays").html(turnoverRatioAnalysis.inventoryTurnoverDays == "" ? "--" : parseInt(turnoverRatioAnalysis.inventoryTurnoverDays));

    $("#assetTurnover_grade").html(turnoverRatioAnalysis.assetTurnoverGrade).addClass(turnoverRatioAnalysis.assetTurnoverGradeEn);
    $("#assetTurnover_grade_exp").html(turnoverRatioAnalysis.assetTurnoverExp);
    $("#accountsTeceivableTurnover_grade").html(turnoverRatioAnalysis.accountsTeceivableTurnoverGrade).addClass(turnoverRatioAnalysis.accountsTeceivableTurnoverGradeEn);
    $("#accountsTeceivableTurnover_grade_exp").html(turnoverRatioAnalysis.accountsTeceivableTurnoverExp);
    $("#inventoryTurnover_grade").html(turnoverRatioAnalysis.inventoryTurnoverGrade).addClass(turnoverRatioAnalysis.inventoryTurnoverGradeEn);
    $("#inventoryTurnover_grade_exp").html(turnoverRatioAnalysis.inventoryTurnoverExp);

}