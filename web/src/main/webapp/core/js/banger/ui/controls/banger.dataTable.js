/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-05-09
 * Description: banger.dataTable.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	
	//工厂
	var factory = {
		
		striped: function(trs, opts){
			
			if(opts.striped){
				
				//奇数行
				trs.filter(':odd').addClass(opts.odd);
				
				//偶数行
				trs.filter(':even').addClass(opts.even);
				
			}
			
		}
		
	};
	
	$.fn.dataTable = function(opts){
		
		opts = $.extend({}, {
			
			striped: true,
			
			odd: 'odd',
			
			even: 'even',
			
			hover: 'hover',
			
			on: 'on',
			
			deleteEvent: null,
			
			upEvent: null,
			
			downEvent: null,
			
			editEvent: null,
			
			viewEvent: null
			
		}, opts);
		
		return this.each(function(){
			
			var table = this, trs = $('tbody>tr:visible', table);
			
			//隔行换色
			factory.striped(trs, opts);
			
			//ie6下鼠标划过效果
			if($.browser.msie && $.browser.version < 7.0){
				
				trs.hover(function(){
					
					if(!$(this).hasClass(opts.on)){
						
						$(this).addClass(opts.hover);
						
					}
					
				}, function(){
					
					$(this).removeClass(opts.hover);
					
				});
				
			}
			
			var global = $('.check-all:enabled', table), single = $('.check-row:enabled', table);
			
			//全选
			global.click(function(){
				
				var flag = this.checked;
				
				single.attr('checked', flag).parents('tr')[flag ? 'addClass' : 'removeClass'](opts.on);
				
			});
			
			//单选
			single.click(function(){
				
				var flag = this.checked;
				
				$(this).parents('tr')[flag ? 'addClass' : 'removeClass'](opts.on);
				
				var checked = single.filter(':checked');
				
				global.attr('checked', single.length === checked.length);
				
			});
			
		});
		
	};
	
})(jQuery);
