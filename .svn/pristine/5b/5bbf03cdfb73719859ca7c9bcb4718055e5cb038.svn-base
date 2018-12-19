/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-30
 * Description: banger.autotips.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	
	$.fn.autotips = function(opts){
		
		opts = $.extend({}, {
			
			tips: '',
			
			color: '#7d7d7d'
			
		}, opts);
		
		return this.each(function(){
		    
			var target = $(this), input;
			
			if(target.is('div')){
			    
			    input = target.find(':text:first');
			    
			}else if(target.is(':text')){
			    
			    input = target;
			    
			}
			
			input.attr({
				
				'autotips': opts.tips,
				
				'color': opts.color
				
			});
			
			var val = $.trim(input.val());
			
			if(val == ''){
			    
				input.css('color', opts.color).val(opts.tips);
				
			}
			
			input.focus(function(){
			    
			    if($(this).val() == opts.tips){
			        
			        $(this).css('color', '').val('');
			        
			    }
			    
			    this.select();
			    
			}).blur(function(){
			
				if($(this).val() == ''){
				    
					$(this).css('color', opts.color).val(opts.tips);
					
				}
				
			});
			
		});
		
	};
	
})(jQuery);
