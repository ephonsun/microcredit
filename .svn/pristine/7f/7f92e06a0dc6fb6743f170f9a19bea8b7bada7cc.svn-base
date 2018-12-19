/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-18
 * Description: banger.checkbox.js
 * Modified by:
 * Modified contents:
 **/
var uiCheckboxOptsId = 0;

(function($){
    $.fn.checkbox = function(o){
        o = $.extend({}, {
        	zIndex: 100,
            readonly: true,
            scrollRange: 10,
            method: 'POST',
            url: null,
            async: false,
            cache: false,
            data: {},
            dataType: 'json',
            selected: [],
            options: null,
            beforeOnCheck: null,
            onCheck: null
        }, o);
        
        var global = {
        	// 
        	getOptions: function(){
        		if(isArray(o.options) && o.options.length > 0){
        			var list = [], listId = 'uiCheckboxOpts' + uiCheckboxOptsId++;
                    var text = [];
	        		
	        		list.push('<div id="' + listId + '" class="ui-checkbox-box">');
	        		var height = (o.options.length > o.scrollRange) ? (24 * o.scrollRange + 'px') : 'auto';
	        		list.push('<div class="options" style="height:' + height + ';">');
	        		list.push('<ul>');
	        		for(var i = 0, len = o.options.length; i < len; i++){
	        			var opt = o.options[i], optId = listId + i;
                        var checkedAttr = (opt.checked)?"checked":"";
	        			// 
	        			if(isPlainObject(opt)){
	        				list.push('<li title="' + opt.text + '">');
	        				list.push('<input type="checkbox" id="' + optId + '" class="check" '+checkedAttr+' text="' + opt.text + '" value="' + opt.value + '" />');
	        				list.push('<label for="' + optId + '">' + opt.text + '</label>');
	        				list.push('</li>');
	        			}else{
	        				list.push('<li title="' + opt + '">');
	        				list.push('<input type="checkbox" id="' + optId + '" class="check" '+checkedAttr+' text="' + opt + '" value="' + opt + '" />');
	        				list.push('<label for="' + optId + '">' + opt + '</label>');
	        				list.push('</li>');
	        			}
                        if(opt.checked)text.push(opt.text);
	        		}
	        		list.push('');
	        		list.push('</ul>');
	        		list.push('</div>');
	        		list.push('</div>');

					// 插入到文档流中
					this.inputWrapper.append(list.join('')).css('zIndex', o.zIndex);

                    if(text.length>0)this.input.val(text.join(","));

					// 
					this.list = $('#' + listId);
					
					//
					this.initChecked();
					
					// 
					this.addProp();
        		}
        	},
        	// 
        	addProp: function(){
        		this.list.off()
        		// 
        		.on('click.div', function(e){
        			e.stopPropagation();
        		})
        		// 
        		.on('mouseover.li', 'li', function(){
        			$(this).addClass('hover');
        		})
        		// 
        		.on('mouseout.li', 'li', function(){
        			$(this).removeClass('hover');
        		})
        		// 
        		.on('click.input', ':checkbox', function(e){
                    // 赋值
                    global.assign();
                    // 组织事件冒泡
                    e.stopPropagation();
                });
        	},
        	// 
        	selected: o.selected,
        	// 根据checkbox的选中状态，给this.selected和文本框赋值 
        	assign: function(){
        		var text = [], value = [];
		        $(':checkbox:checked', this.list).each(function(i){
		        	text.push($(this).attr('text'));
		            value.push($(this).val());
		        });
		        // 文本框赋值
		       	this.input.val(text.join(','));
		       	this.input.blur();          //点击触发，失去焦点事件，非空校验用
		       	this.selected = value;
		        // 选项勾选事件回调函数
		        if(isFunction(o.onCheck)){
		            o.onCheck(text, value);
		        }
        	},
        	//根据selected属性来初始化checkbox和文本框 初始话文本框add by 林贤聪
        	initChecked: function(){
                var chks = $(':checkbox', this.list);
                var text = [];
                if(this.input.val()!=''&&this.selected.length > 0){
                    // 清除所有复选框的勾选状态
                    chks.removeAttr('checked');
                    // 根据文本框的值勾选对应的复选框
                    for(var i in this.selected){
                        var val = this.selected[i];
                        $(':checkbox[value="' + val + '"]', this.list).attr('checked', true);
                        for(var index = 0; index<o.options.length; index++) {
                            if(o.options[index].value == this.selected[i]) {
                                text.push(o.options[index].text);
                                break;
                            }
                        }
                    }
                    this.input.val(text.join(','));
                }else{
                    // 清除所有复选框的勾选状态
                    //chks.removeAttr('checked');
                }
		    },
        	// 
        	populate: function(){
        		// 移除上一次请求的选项列表
				if(this.list != null){
					this.list.off().remove();
				}
				
				if(o.url){
					// 获取参数
					var data = isFunction(o.data) ? o.data() : o.data;
					
					$.ajax({
						type: o.type,
						url: o.url,
						data: data,
						async: o.async,
						cache: o.cache,
						dataType: o.dataType,
						success: function(result){ // 成功
							// 
							o.options = result;
							// 插入选项到文档流中
							global.getOptions();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){ // 失败
							
						}
					});
				}else if(o.options){
					// 插入选项到文档流中
					this.getOptions();
				}else{
					var attr = this.inputWrapper.attr('options') || this.input.attr('options');
                    if(attr){
                        o.options = attr.split(',');
                        // 插入选项到文档流中
						this.getOptions();
                    }
				}
        	}
        };

        return this.each(function(){
        	// 查找文本框
			if($(this).is('div')){
				global.inputWrapper = $(this);
			    global.input = $(this).find('.ui-text-text').eq(0);
			}else if($(this).is(':text')){
				global.inputWrapper = $(this).parent();
			    global.input = $(this);
			}
			
			// 文本框容器添加样式类
			global.inputWrapper.addClass('ui-checkbox');
        	
            // 设置只读属性
            global.input.attr('readonly', o.readonly);

            //add by 林贤聪 初始化操作
        	// 填充选项
        	global.populate();
            banger.controls.hide();        	      	
            //根据selected属性初始化checkbox和文本框
            global.initChecked();

            // 
            global.inputWrapper.off().on('click', function(e){
            	// 隐藏其他组件
            	banger.controls.hide();
            	global.initChecked();
            	// 填充选项
            	global.populate();
            	// 组织事件冒泡
            	e.stopPropagation();
            });

            $(document).click(function(){
                // 点击页面其它区域隐藏或清除 ui组件
                banger.controls.hide();
            });
        });
    };

    $.fn.checkboxtext = function(o){
        o = $.extend({}, {
            zIndex: 100,
            readonly: true,
            scrollRange: 10,
            method: 'POST',
            url: null,
            async: false,
            cache: false,
            data: {},
            dataType: 'json',
            selected: [],
            options: null,
            beforeOnCheck: null,
            onCheck: null
        }, o);

        var global = {
            //
            getOptions: function(){
                if(isArray(o.options) && o.options.length > 0){
                    var list = [], listId = 'uiCheckboxOpts' + uiCheckboxOptsId++;
                    var text = [];

                    list.push('<div id="' + listId + '" class="ui-checkbox-box">');
                    var height = (o.options.length > o.scrollRange) ? (24 * o.scrollRange + 'px') : 'auto';
                    list.push('<div class="options" style="height:' + height + ';">');
                    list.push('<ul>');
                    for(var i = 0, len = o.options.length; i < len; i++){

                        var opt = o.options[i], optId = listId + i;
                        var checkedAttr = (opt.checked)?"checked":"";
                        //

                        if(isPlainObject(opt)){
                            list.push('<li title="' + opt.text + '">');
                            list.push('<input type="checkbox" id="' + optId + '" class="check" '+checkedAttr+' inputText="' + opt.inputtext +'" limitEnable="' + opt.limitEnable + '" text="' + opt.text + '" value="' + opt.value + '" />');
                            list.push('<label for="' + optId + '">' + opt.text + '</label>');
                            list.push('</li>');
                        }else{
                            list.push('<li title="' + opt + '">');
                            list.push('<input type="checkbox" id="' + optId + '" class="check" '+checkedAttr+'  inputText="' + opt.inputtext +'" limitEnable="' + opt.limitEnable+ '" text="' + opt + '" value="' + opt + '" />');
                            list.push('<label for="' + optId + '">' + opt + '</label>');
                            list.push('</li>');
                        }
                        if(opt.checked)text.push(opt.text);
                    }
                    list.push('');
                    list.push('</ul>');
                    list.push('</div>');
                    list.push('</div>');

                    // 插入到文档流中
                    this.inputWrapper.append(list.join('')).css('zIndex', o.zIndex);

                    if(text.length>0)this.input.val(text.join(","));

                    //
                    this.list = $('#' + listId);

                    //
                    this.initChecked();

                    //
                    this.addProp();
                }
            },
            //
            addProp: function(){
                this.list.off()
                //
                    .on('click.div', function(e){
                        e.stopPropagation();
                    })
                    //
                    .on('mouseover.li', 'li', function(){
                        $(this).addClass('hover');
                    })
                    //
                    .on('mouseout.li', 'li', function(){
                        $(this).removeClass('hover');
                    })
                    //
                    .on('click.input', ':checkbox', function(e){
                        // 赋值
                        global.assign();
                        // 组织事件冒泡
                        e.stopPropagation();
                    });
            },
            //
            selected: o.selected,
            // 根据checkbox的选中状态，给this.selected和文本框赋值
            assign: function(){
                var text = [], value = [], limitEnable = [];

                $(':checkbox:checked', this.list).each(function(i){
                    text.push($(this).attr('inputtext'));
                    limitEnable.push($(this).attr('limitEnable'));
                    value.push($(this).val());
                });
                // 文本框赋值
                this.input.val(text.join(','));
                this.selected = value;
                // 选项勾选事件回调函数
                if(isFunction(o.onCheck)){
                    o.onCheck(text, value, limitEnable);
                }
            },
            //根据selected属性来初始化checkbox和文本框 初始话文本框add by 林贤聪
            initChecked: function(){
                var chks = $(':checkbox', this.list);
                var text = [];
                if(this.input.val()!=''&&this.selected.length > 0){
                    // 清除所有复选框的勾选状态
                    chks.removeAttr('checked');
                    // 根据文本框的值勾选对应的复选框
                    for(var i in this.selected){
                        var val = this.selected[i];
                        $(':checkbox[value="' + val + '"]', this.list).attr('checked', true);
                        for(var index = 0; index<o.options.length; index++) {
                            if(o.options[index].value == this.selected[i]) {
                                text.push(o.options[index].inputtext);
                                break;
                            }
                        }
                    }
                    this.input.val(text.join(','));
                }else{
                    // 清除所有复选框的勾选状态
                    //chks.removeAttr('checked');
                }
            },
            //
            populate: function(){
                // 移除上一次请求的选项列表
                if(this.list != null){
                    this.list.off().remove();
                }

                if(o.url){
                    // 获取参数
                    var data = isFunction(o.data) ? o.data() : o.data;

                    $.ajax({
                        type: o.type,
                        url: o.url,
                        data: data,
                        async: o.async,
                        cache: o.cache,
                        dataType: o.dataType,
                        success: function(result){ // 成功
                            //
                            o.options = result;
                            // 插入选项到文档流中
                            global.getOptions();
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown){ // 失败

                        }
                    });
                }else if(o.options){
                    // 插入选项到文档流中
                    this.getOptions();
                }else{
                    var attr = this.inputWrapper.attr('options') || this.input.attr('options');
                    if(attr){
                        o.options = attr.split(',');
                        // 插入选项到文档流中
                        this.getOptions();
                    }
                }
            }
        };

        return this.each(function(){
            // 查找文本框
            if($(this).is('div')){
                global.inputWrapper = $(this);
                global.input = $(this).find('.ui-text-text').eq(0);
            }else if($(this).is(':text')){
                global.inputWrapper = $(this).parent();
                global.input = $(this);
            }

            // 文本框容器添加样式类
            global.inputWrapper.addClass('ui-checkbox');

            // 设置只读属性
            global.input.attr('readonly', o.readonly);

            //add by 林贤聪 初始化操作
            // 填充选项
            global.populate();
            banger.controls.hide();
            //根据selected属性初始化checkbox和文本框
            global.initChecked();

            //
            global.inputWrapper.off().on('click', function(e){
                // 隐藏其他组件
                banger.controls.hide();
                global.initChecked();
                // 填充选项
                global.populate();
                // 组织事件冒泡
                e.stopPropagation();
            });

            $(document).click(function(){
                // 点击页面其它区域隐藏或清除 ui组件
                banger.controls.hide();
            });
        });
    };
})(jQuery);
