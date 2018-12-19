/**
 * Author: dingwei 2013-03-18
 * Description: banger.autoComplete.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	$.fn.autoComplete = function(o){
		// 扩展默认参数
		o = $.extend({}, {
			id: 'uiAutoComplete',
			width: 500,
			height: 200,
			zIndex: 100,
			fields: null,
			rows: null,
			type: 'POST',
			async: false,
			cache: false,
			url: null,
			data: {},
			dataType: 'json',
			delay: 500,
			noDataTips: '无符合结果',
			failedTips: '请求失败，请刷新后重试',
			beforeOnSelect: null,
			onSelect: null
		}, o);
		
		// 公共函数库
		var global = {
			// 
			grid: null,
			// 创建选项列表
			getOptions: function(){
				var width = 0, colgroup = [], ths = [];
				for(var i = 0, fieldsLen = o.fields.length; i < fieldsLen; i++){
					var field = o.fields[i];
					// 计算表格总宽度（1为单元格右边框的宽度）
					width += parseInt(field.width) + 1;
					// 创建列格式化标签
					colgroup.push('<col style="width:' + field.width + 'px;" />');
					// 创建列头
					ths.push('<th>' + field.display + '</th>');
				}
				
				// 创建行
				var trs = [];
				for(var j = 0, rowsLen = o.rows.length; j < rowsLen; j++){
					var row = o.rows[j];
					trs.push('<tr>');
					for(var k = 0, colsLen = o.fields.length; k < colsLen; k++){
						var col = o.fields[k];
						trs.push('<td>' + row.cols[col.field] + '</td>');
					}
					trs.push('</tr>');
				}
				
				var list = [], pos = this.getPosition();
				list.push('<div id="' + o.id + '" class="ui-autocomplete" style="top:' + pos.top + 'px; left:' + pos.left + 'px; z-index:' + o.zIndex + '; width:' + o.width + 'px;">');
				list.push('<div class="ui-autocomplete-head">');
				list.push('<div class="ui-autocomplete-head-inner">');
				list.push('<table style="width:' + width + 'px;">');
				list.push('<colgroup>');
				list.push(colgroup.join(''));
				list.push('</colgroup>');
				list.push('<thead>');
				list.push('<tr>');
				list.push(ths.join(''));
				list.push('</tr>');
				list.push('</thead>');
				list.push('</table>');
				list.push('</div>');
				list.push('</div>');
				list.push('<div class="ui-autocomplete-body">');
				list.push('<div class="ui-autocomplete-body-inner" style="height:' + o.height + 'px;">');
				list.push('<table style="width:' + width + 'px;">');
				list.push('<colgroup>');
				list.push(colgroup.join(''));
				list.push('</colgroup>');
				list.push('<tbody>');
				list.push(trs.join(''));
				list.push('</tbody>');
				list.push('</table>');
				list.push('</div>');
				list.push('</div>');
				list.push('</div>');
				
				// 插入到文档流中
				$('body').append(list.join(''));
				
				// 
				this.grid = $('#' + o.id);
				
				// 
				this.addProp();
			},
			// 定位
			getPosition: function(){
				var pos = { top: 0, left: 0 };
				
				var os = this.inputWrapper.offset(), iptTop = os.top, iptLeft = os.left, iptHeight = this.inputWrapper.height() + 2, w = $(window).width(), h = $(document).height();
				
				// top
				if((iptTop + iptHeight + o.height + 27) < h){
					pos.top = iptTop + iptHeight - 1;
				}else{
					pos.top = iptTop - o.height - 28;
				}
				
				// left
				if((iptLeft + o.width + 2) < w){
					pos.left = iptLeft;
				}else{
					pos.left = w - (o.width + 2);
				}
				
				return pos;
			},
			// 注册事件
			addProp: function(){
				this.grid
				// tr.click
				.on('click', '.ui-autocomplete-body-inner tr', function(){
					var i = $(this).index(), data = o.rows[i], flag = true;
					
					// beforeOnSelect
					if(isFunction(o.beforeOnSelect)){
						flag = o.beforeOnSelect(data);
					}
					
					if(flag){
						// onSelect
						if(isFunction(o.onSelect)){
							o.onSelect(data);
						}
						
					   // 移除数据列表
                       global.grid.off().remove();
					}
				});
			},
			// 
			populate: function(){
				// 移除上一次请求的数据列表
				if(this.grid != null){
					this.grid.off().remove();
				}
				
				// 获取参数
				var data = isFunction(o.data) ? o.data() : o.data;
				
				// 参数集合中插入文本框关键字
				data['keywords'] = this.input.val();
				
				$.ajax({
					type: o.type,
					url: o.url,
					data: data,
					async: o.async,
					cache: o.cache,
					dataType: o.dataType,
					success: function(result){ // 成功
						if(result==null || result=='') return;
						// Array
						if(isArray(result)){
							o.rows = result;
						}
						// Json
						else if(isPlainObject(result)){
							o.fields = result.fields;
							o.rows = result.rows;
						}
						
						// 插入检索结果到文档流中
						global.getOptions();
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){ // 失败
						
					}
				});
			}
		};
		
		// 
		return this.each(function(){
			// 查找文本框
			if($(this).is('div')){
				global.inputWrapper = $(this);
			    global.input = $(this).find('.ui-text-text').eq(0);
			}else if($(this).is(':text')){
				global.inputWrapper = $(this).parent();
			    global.input = $(this);
			}
			
			// 定义时间戳及请求函数
			var timer = null, ajax = function(){
				global.populate();
			};
			
			global.input
			// 获得光标事件
			.focus(function(){
				
			})
			// 按键弹起事件
			.keyup(function(e){
				// 按键弹起时先清除上一个时间戳
				clearTimeout(timer);
				// 延迟进行服务器请求
				timer = setTimeout(ajax, o.delay);
			});
			
			$(document).click(function(){
				$('#' + o.id).off().remove();
			});
		});
	};
})(jQuery);
