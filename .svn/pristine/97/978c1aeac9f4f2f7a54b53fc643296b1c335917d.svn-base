$(function() {
	var curSize="",curTime="";
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        var chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'spline',
                animation: Highcharts.svg,
                // don't animate in old IE               
                marginRight: 10,
                events: {
                    load: function() {}
                }
            },
            title: {
                text: '文件上传流量'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 100
            },
            yAxis: [{  
			    min: 0,//纵轴的最小值  
                title: {
                    text: '流量 kb/s'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            }],
            tooltip: {
                formatter: function() {
                    return '<b style="text-align:center;font-weight:bold;">' + Highcharts.numberFormat(this.y, 2) + ' kb/s</b><br/>' + Highcharts.dateFormat('%H:%M:%S', this.x);
                }
            },
            legend: {
                enabled: false
            },
			credits: {          
				enabled:false
			}, 
            exporting: {
                enabled: false
            },
			//
            series: [{
                name: '实时流量',
                data: (function() {                              
                    var data = [],
                    time = (new Date()).getTime(),
                    i;
                    for (i = -19; i <= 0; i++) {
                        data.push({
                            x: +time + i * 1000,
                            y: null
                        });
                    }
                    return data;
                })()
            }]
        }); // set up the updating of the chart each second                     
        var series = chart.series[0];

		setInterval(function() {
			jQuery.ajax({
				type : "post",
				url : '../monitor/getCurrentUploadSpeed.html?time='+curTime+'&'+'size='+curSize,
				dataType:'json' ,
				success : function(data) {
					var curSpeed=data.speed
					curTime=data.time
					curSize=data.size
					series.addPoint([(new Date()).getTime(),curSpeed], true, true);
	    		}
	        });
        },
        2000);
});