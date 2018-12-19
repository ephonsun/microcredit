/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-21
 * Description: banger.datepicker.js
 * Modified by:
 * Modified contents:
 **/
if(typeof WdatePicker == 'undefined'){

	document.write('<script type="text/javascript" src="../core/js/third/My97DatePicker/WdatePicker.js"><\/script>');

}

(function($){

	$.fn.datepicker = function(opts){

		opts = $.extend({}, {

			eventType: 'click',

			errDealMode: 1,

			qsEnabled: false,

			readOnly: true

		}, opts);

		return this.each(function(){

			var target = $(this), div, input, icon;

			if(target.is('div')){

				div = target;

				// 文本框
				input = $(':text', div);

				// 为文本框赋值id属性
				input.attr('id', div.attr('id') + '-text');

				// 图标
				icon = $('i.ui-text-date', div);

			}else if(target.is(':text')){

				div = target.parent();

				// 文本框
				input = target;

				// 图标
				icon = input.next('i.ui-text-date');

			}

			div.addClass('ui-datepicker');

			// 文本框触发日历控件
			input[opts.eventType](function(){

				// 隐藏或清除其他控件
				banger.controls.hide();

				WdatePicker(opts);

			});

			// 图标触发日历控件
			icon.click(function(){

				// 隐藏或清除其他控件
				banger.controls.hide();

				// 设置 el 参数，指定文本框用于存储日历值
				opts['el'] = input.attr('id');

				WdatePicker(opts);

			});

		});

	};

})(jQuery);
