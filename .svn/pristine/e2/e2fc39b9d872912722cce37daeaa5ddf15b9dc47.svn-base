var layout = {
	"initForms":function(co){
		if(co){
			layout.initSelect(co);
			layout.initDate(co);
			//layout.initTime();
            setTimeout(function(){layout.initValid(co);},300);
        }else{
            layout.initSelect();
            layout.initDate();
            setTimeout(function(){layout.initValid();},300);
		}
	},
	"initDate":function(co){		//初始化日期
		if(co){
            $(co).find("input[dateFlag$='true']").each(function(){
                var minDateId = $(this).attr('mindateid');
                var maxDateId = $(this).attr('maxdateid');
                if (maxDateId && minDateId) {
                    $(this).datepicker({
                        minDate: '#F{$dp.$D(\''+minDateId+'\')}',
                        maxDate: '#F{$dp.$D(\''+maxDateId+'\')}'
                    });
                } else if (maxDateId) {
                    $(this).datepicker({
                        maxDate: '#F{$dp.$D(\''+maxDateId+'\')}'
                    });
                } else if (minDateId) {
                    $(this).datepicker({
                        minDate: '#F{$dp.$D(\''+minDateId+'\')}'
                    });
                } else {
                    $(this).datepicker();
                }
            });
		}else{
			$("input[dateFlag$='true']").each(function(){
                var minDateId = $(this).attr('minDateId');
                var maxDateId = $(this).attr('maxDateId');
                if (maxDateId && minDateId) {
                    $(this).datepicker({
                        minDate: '#F{$dp.$D(\''+minDateId+'\')}',
                        maxDate: '#F{$dp.$D(\''+maxDateId+'\')}'
                    });
                } else if (maxDateId) {
                    $(this).datepicker({
                        maxDate: '#F{$dp.$D(\''+maxDateId+'\')}'
                    });
                } else if (minDateId) {
                    $(this).datepicker({
                        minDate: '#F{$dp.$D(\''+minDateId+'\')}'
                    });
                } else {
                    $(this).datepicker();
                }
            });
        }
	},
	"initTime":function(){		//初始化时间
		$("table[timeFlag$='true']").each(function(){
			var setting = {};
			var elem = $(this);
			var hour = elem.attr("hour");
			var minute = elem.attr("minute");
			var second = elem.attr("second");
			var time = elem.attr("value").split(":");
			var inputId = elem.attr("inputId");

			if(hour=="false")setting["hour"]=false;
			else setting["hour"]={"value":parseInt(time[0])};

			if(minute=="false")setting["min"]=false;
			else setting["min"]={"value":parseInt(time[1])};

			if(second=="false")setting["sec"]=false;
			else setting["sec"]={"value":parseInt(time[2])};

			setting["onpicked"] = function(result,t){
				var h = (t.hour)?t.hour:"00";
				var m = (t.min)?t.min:"00";
				var s = (t.sec)?t.sec:"00";
				var val = h+":"+m+":"+s;
				$('#'+inputId).val(val);
			};

			elem.timepicker(setting);

		});
	},
	"timeToInt":function(time){
		if(time){
			var times = time.split(':');
			var h = times[0] ? Number(times[0]) : 0;
			var m = times[1] ? Number(times[1]) : 0;
			var s = times[2] ? Number(times[2]) : 0;
			var result = h*3600 + m*60 + s;
			return result;
		}
		return 0;
	},
	"initSelect":function(co){
		if(co){
            $(co).find('select').each(function (){
                if($(this).is(":visible"))
                    $(this).selectbox();
            });

            $(co).find("input[multiselect$='true']").each(function () {
                var setting = {};
                var elem = $(this);
                var options = elem.attr("options");
                var inputId = elem.attr("inputId");

                var selOption = $("#"+options);
                if(selOption){
                    setting["options"] = [];
                    $("#"+options+" option").each(function () {
                        var op = $(this);
                        var checked = op.attr("selected")?true:false;
                        if(op.val()){
                            setting["options"].push({"text":op.text(),"value":op.val(),"checked":checked});
                        }
                    });
                    selOption.remove();
                }else{
                    setting["options"] = eval("("+options+")");
                }

                //解析多选下拉对应的选择值
                var selVal = $('#' + inputId).val();
                if (selVal) {
                    var selValues = selVal.split(',');
                    setting["selected"] = selValues;
                    var curOpts = setting["options"];
                    var selText = '';
                    for (var i = 0; i < curOpts.length; i++) {
                        for (var j = 0; j < selValues.length; j++) {
                            if (curOpts[i].value == selValues[j]) {
                                selText = selText + curOpts[i].text + ',';
                            }
                        }
                    }
                    selText = selText.substring(0, selText.length - 1);
                    elem.val(selText);
                }

                setting["onCheck"] = function (text, value) {
                    $('#' + inputId).val(value);
                };
                $(this).checkbox(setting);
            });

		}else {
            $('form select').each(function (){
                if($(this).is(":visible"))
                    $(this).selectbox();
            });

            $("input[multiselect$='true']").each(function () {
                var setting = {};
                var elem = $(this);
                var options = elem.attr("options");
                var inputId = elem.attr("inputId");

                var selOption = $("#"+options);
                if(selOption){
                    setting["options"] = [];
                    $("#"+options+" option").each(function () {
                        var op = $(this);
                        var checked = op.attr("selected")?true:false;
                        if(op.val()) {
                            setting["options"].push({"text": op.text(), "value": op.val(), "checked": checked});
                        }
                    });
                    selOption.remove();
                }else{
                    setting["options"] = eval("("+options+")");
                }

                //解析多选下拉对应的选择值
                var selVal = $('#' + inputId).val();
                if (selVal) {
                    var selValues = selVal.split(',');
                    setting["selected"] = selValues;
                    var curOpts = setting["options"];
                    var selText = '';
                    for (var i = 0; i < curOpts.length; i++) {
                        for (var j = 0; j < selValues.length; j++) {
                            if (curOpts[i].value == selValues[j]) {
                                selText = selText + curOpts[i].text + ',';
                            }
                        }
                    }
                    selText = selText.substring(0, selText.length - 1);
                    elem.val(selText);
                }

                setting["onCheck"] = function (text, value) {
                    $('#' + inputId).val(value);
                };

                $(this).checkbox(setting);
            });
        }
	},
	"initValid":function(co){
		if(co){
            $(co).find("input[valid]").each(function () {
                var elem = $(this);
                var id = elem.attr("id");
                var inputId = elem.attr("inputId");
                var valid = elem.attr("valid");
                var multiselect = elem.attr("multiselect");
                if (id != null) {
                    //banger.verify('#' + id, valid);
                    banger.verify(elem, valid);
                } else if (multiselect) {
                    banger.verify('#mutl' + id, valid);
                } else {
                    //banger.verify('#' + inputId, valid);
                    banger.verify(elem, valid);
                }
            });
            $(co).find("select[valid]").each(function () {
                var elem = $(this);
                var valid = elem.attr("valid");
                banger.verify(elem, valid);
            });
            $(co).find("textarea[valid]").each(function () {
                var elem = $(this);
                var valid = elem.attr("valid");
                banger.verify(elem, valid);
            });
		}else{
            $("input[valid]").each(function () {
                var elem = $(this);
                var id = elem.attr("id");
                var inputId = elem.attr("inputId");
                var valid = elem.attr("valid");
                var multiselect = elem.attr("multiselect");
                if (id != null) {
                    //banger.verify('#' + id, valid);
                    banger.verify(elem, valid);
                } else if (multiselect) {
                    banger.verify('#mutl' + id, valid);
                } else {
                    //banger.verify('#' + inputId, valid);
                    banger.verify(elem, valid);
                }
            });
            $("select[valid]").each(function () {
                var elem = $(this);
                var valid = elem.attr("valid");
                banger.verify(elem, valid);
            });
            $("textarea[valid]").each(function () {
                var elem = $(this);
                var valid = elem.attr("valid");
                banger.verify(elem, valid);
            });
        }
	}
}