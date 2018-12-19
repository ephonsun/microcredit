
var produtFieldLayout = {
	"initForms":function(){
		produtFieldLayout.initSelect();
		produtFieldLayout.initDate();
		//produtFieldLayout.initTime();
		produtFieldLayout.initValid();
	},
	"initDate":function(){		//初始化日期
		$("input[dateFlag$='true']").each(function(){
			var elem = $(this);
			var id = elem.attr("id");
			$('#'+id).datepicker();
		});
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
	"initSelect":function(){
		$('select').selectbox();
		
		$("input[multiselect$='true']").each(function(){
			var setting = {};
			var elem = $(this);
			var options = elem.attr("options");
			var inputId = elem.attr("inputId");
			setting["options"] = eval(options);
			
			//解析多选下拉对应的选择值
			var selVal = $('#'+inputId).val();
			if(selVal){
				var selValues = selVal.split(',');
				setting["selected"] = selValues;
				var curOpts = eval(options);
				var selText = '';
				for(var i=0;i<curOpts.length;i++){
					for(var j=0;j<selValues.length;j++){
						if(curOpts[i].value==selValues[j]){
							selText = selText + curOpts[i].text+',';
						}
					}
				}
				selText = selText.substring(0,selText.length-1);
				elem.val(selText);
			}
			
			setting["onCheck"] = function(text, value){
				$('#'+inputId).val(value);
			};
			$(this).checkbox(setting);
		});
	},
	"initValid":function(){
		$("input[valid]").each(function(){
			var elem = $(this);
			var id = elem.attr("id");
			var inputId = elem.attr("inputId");
			var valid = elem.attr("valid");
			var multiselect = elem.attr("multiselect");
			if(valid == 'productCodeRules'){
				banger.verify('#'+id,[{name : 'required',	tips : '必须填写'}, productCodeRepeatRule]);
			}else if(id != null){
				banger.verify('#'+id,valid);
			}else if(multiselect){
				banger.verify('#mutl'+id,valid);
			}else{
				banger.verify('#'+inputId,valid);
			}
		});
	}
};

