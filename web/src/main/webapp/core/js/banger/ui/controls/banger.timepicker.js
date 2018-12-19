/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-21
 * Description: banger.timepicker.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	
	// 工厂
	var shared = {};
	
	// 创建指针
	shared.getHtml = function(opts){
		
		var needles = [], splitor = '<span>:</span>';
		
		needles.push('<tbody><tr><td>');
		
		if(opts.hour){
			
			// id
			var hid = opts.hour.id ? ' id="' + opts.hour.id + '"' : '';
			
			// name
			var hn = opts.hour.name ? ' name="' + opts.hour.name + '"' : '';
			
			// value
			var hv = opts.hour.value ? opts.hour.value : '00';
			
			// 时钟
			needles.push('<input type="text" tabindex="1" maxlength="2"' + hid + hn + ' class="ui-timepicker-hour" value="' + hv + '" onpaste="javascript:return false;" pointer="hour" />');
			
			// 如果时钟后输出分钟，则输出一个冒号分隔符
			if(opts.min){
				
				needles.push(splitor);
				
			}
			
		}
		
		if(opts.min){
			
			// id
			var mid = opts.min.id ? ' id="' + opts.min.id + '"' : '';
			
			// name
			var mn = opts.min.name ? ' name="' + opts.min.name + '"' : '';
			
			// value
			var mv = opts.min.value ? opts.min.value : '00';
			
			needles.push('<input type="text" tabindex="1" maxlength="2"' + mid + mn + ' class="ui-timepicker-min" value="' + mv + '" onpaste="javascript:return false;" pointer="min" />');
			
			// 如果分钟后输出秒钟，则输出一个冒号分隔符
			if(opts.sec){
				
				needles.push(splitor);
				
			}
			
		}
		
		if(opts.sec){
			
			// id
			var sid = opts.sec.id ? ' id="' + opts.sec.id + '"' : '';
			
			// name
			var sn = opts.sec.name ? ' name="' + opts.sec.name + '"' : '';
			
			// value
			var sv = opts.sec.value ? opts.sec.value : '00';
			
			// 秒钟
			needles.push('<input type="text" tabindex="1" maxlength="2"' + sid + sn + ' class="ui-timepicker-sec" value="' + sv + '" onpaste="javascript:return false;" pointer="sec" />');
			
		}
		
		needles.push('</td><td><a href="#2" tabindex="1" class="ui-timepicker-button ui-timepicker-plus" onclick="javascript:return false;"></a><a href="#2" tabindex="1" class="ui-timepicker-button ui-timepicker-minus" onclick="javascript:return false;"></a></td></tbody>');
		
		return needles.join('');
		
	};
	
	// 屏蔽按键，只能输入数字、Tab键、上下方向键
	shared.shield = function(e){
		
		var code = e.keyCode;
		
		return e.shiftKey || ((code < 48 || code > 57 && code < 96 || code > 105) && code != 8 && code != 9 && code != 37 && code != 38 && code != 39 && code != 40 && code != 46);
		
	};
	
	// 值转换为十进制数值
	shared.toInt = function(value){
		
		return parseInt(value, 10);
		
	};
	
	// 按键按下时
	shared.keydown = function(type, target, code){
		
		var num = null;
		
		switch(type){
			
			case 'hour':
				
				num = 23;
				
				break;
				
			case 'min':
				
				num = 59;
				
				break;
				
			case 'sec':
				
				num = 59;
				
				break;
				
			default: break;
			
		}
		
		var val = this.toInt(target.value);
		
		if(code == 38){ // 向上方向键控制加
			
			target.value = val < num ? (++val < 10 ? '0' + val : val) : '00';
			
		}else if(code == 40){ // 向下方向键控制减
			
			target.value = val < 1 ? num : (--val < 10 ? '0' + val : val);
			
		}
		
	}
	
	// 按键弹起时
	shared.keyup = function(type, target){
		
		var num = null;
		
		switch(type){
			
			case 'hour':
				
				num = 23;
				
				break;
				
			case 'min':
				
				num = 59;
				
				break;
				
			case 'sec':
				
				num = 59;
				
				break;
			
			default: break;
			
		}
		
		if(this.toInt(target.value) > num){
					
			target.value = num;
			
		}
		
	};
	
	// 返回时间
	shared.getResult = function(target){
		
		var result = {};
		
		$(':text', target).each(function(){
			
			result[$(this).attr('pointer')] = $(this).val();
			
		});
		
		return result;
		
	};
	
	$.fn.timepicker = function(opts){
		
		opts = $.extend({}, {
			
			hour: true,
			
			min: true,
			
			sec: true,
			
			focusStyle: 'ui-timepicker-focus',
			
			onpicked: null
			
		}, opts);
		
		return this.each(function(){
			
			var target = this;
			
			// 创建指针
			$(target).html(shared.getHtml(opts)).attr({'cellpadding': 0, 'cellspacing': 0});
			
			$(':text', target).focus(function(){ // 获得光标
				
				var style = opts.focusStyle;
				
				$(this).addClass(style).siblings().removeClass(style);
				
			}).blur(function(){ // 失去光标
				
				var len = this.value.length;
				
				if(len == 0){ // 值等于空时
					
					this.value = '00';
					
				}else if(len == 1){ // 值小于10时
					
					this.value = '0' + this.value;
					
				}
				
				// 触发回调函数
				if(isFunction(opts.onpicked)){
					
					var result = shared.getResult(target);
					
					var str = [];
					
					for(var i in result){
						
						str.push(result[i]);
						
					}
					
					opts.onpicked(str.join(':'), result);
					
				}
				
			}).eq(0).addClass(opts.focusStyle); // 默认第一个指针处于高亮状态
			
			// 时
			$('.ui-timepicker-hour', target).keydown(function(e){
				
				// 按键控制
				if(shared.shield(e)){
					
					return false;
					
				}
				
				// 设置时
				shared.keydown('hour', this, e.keyCode);
				
			}).keyup(function(){
				
				shared.keyup('hour', this);
				
			});
			
			// 分
			$('.ui-timepicker-min', target).keydown(function(e){
				
				// 按键控制
				if(shared.shield(e)){
					
					return false;
					
				}
				
				// 设置分
				shared.keydown('min', this, e.keyCode);
				
			}).keyup(function(){
				
				shared.keyup('min', this);
				
			});
			
			// 秒
			$('.ui-timepicker-sec', target).keydown(function(e){
				
				// 按键控制
				if(shared.shield(e)){
					
					return false;
					
				}
				
				// 设置秒
				shared.keydown('sec', this, e.keyCode);
				
			}).keyup(function(){
				
				shared.keyup('sec', this);
				
			});
			
			// 加
			$('.ui-timepicker-plus', target).click(function(){
				
				var input = $('.ui-timepicker-focus', target);
				
				if(input.length === 0){
					
					return false;
					
				}else{
					
					var v = shared.toInt(input.val());
					
					if(input.hasClass('ui-timepicker-hour')){
						
						input.val(v < 23 ? (++v < 10 ? '0' + v : v) : '00');
						
					}else{
						
						input.val(v < 59 ? (++v < 10 ? '0' + v : v) : '00');
						
					}
					
				}
				
				// 更改值后触发失去光标事件，以触发onpicked回调函数
				$('.ui-timepicker-focus', target).blur();
				
			});
			
			// 减
			$('.ui-timepicker-minus', target).click(function(){
				
				var input = $('.ui-timepicker-focus', target);
				
				if(input.length === 0){
					
					return false;
					
				}else{
					
					var v = shared.toInt(input.val());
					
					if(input.hasClass('ui-timepicker-hour')){
						
						input.val(v < 1 ? 23 : (--v < 10 ? '0' + v : v));
						
					}else{
						
						input.val(v < 1 ? 59 : (--v < 10 ? '0' + v : v));
						
					}
					
				}
				
				// 更改值后触发失去光标事件，以触发onpicked回调函数
				$('.ui-timepicker-focus', target).blur();
				
			});
			
		});
		
	};
	
})(jQuery);
