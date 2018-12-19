(function($){
	var factory = {};

	// 初始化select标签选项
	factory.initOptions = function(os, o){
        var url = '';
        if(o.keyWord) {
            if(o.url.indexOf('?')>0) {
                url = o.url+'&'+o.keyWordParam+'='+o.keyWord;
            } else {
                url = o.url+'?'+o.keyWordParam+'='+o.keyWord;
            }
        } else {
            url = o.url;
        }
		$.ajax({
			url: url,
			type: o.type,
			async: o.async,
			cache: o.cache,
			data: o.data,
			dataType: o.dataType,
			success: function(arr){
				// 填充选项
				var options = [];
                if(o.filterValue && isFunction(o.filterValue)) {
                    arr = o.filterValue(arr);
                }
				for(var i = 0, len = arr.length; i < len; i++){
					var option = arr[i], value, display;
					if(isPlainObject(option)){
						value = option.value;
						display = option.display;
					}else{
						value = option;
						display = option;
					}
					options.push('<option value="' + value + '">' + display + '</option>');
				}			
				
				$(os).html(options.join(''));					
				// 改造select标签
				factory.reform(os, o);

			},
			error: function(data){
				// alert(1);
			}
		});
	};
	
	// 改造select标签
	factory.reform = function(target, o){
		var os = $(target);

		if( o.generator && isFunction(o.generator)) {
			o.generator(os);
		}
        if(o.addBlankOption && (!o.keyWord || $.trim(o.keyWord) == '')) {
            if(os.children().eq(0).val() !== '') {
                os.prepend('<option value=""></option>');
                target.selectedIndex = 0;
                os.change();
            }
        }
		//假如存在value值，那么就默认选中
		if(o.value && o.value !== '') {
			var selectedIndex = os.find('option[value="'+o.value+'"]').index();
			// 根据当前点击项的索引更新原始下拉单选框选项
			target.selectedIndex = selectedIndex;
			// 触发原始下拉单选框选项事件
			os.change();			
		}   
        //已创建模拟标签        
		if(os.data('ns')){
            //删除原先的模拟标签
            $(os.data('ns')).remove();
        }  		  	        
		//调用用户的onChange函数
		if(o.onChange && isFunction(o.onChange)){
			os.change(function(){
				o.onChange($(this).find('option:selected').val());
			});
		}
        var ns = this.getHtml(os, o);
        ns = $(ns);        
        // 在原始select标签之前插入模拟标签
        if(!os.data('ns')){
            os.before(ns).wrap('<div style="display:none;"></div>');
        } else {
            os.parent().before(ns);
        }         
        os.data('ns', ns); // 创建一个ns供调用
        if(os.find('option').length !=0 && !os.find('div.options').is(':visible') && o.keyWord && $.trim(o.keyWord)!==''){
            // 下拉
            factory.dropdown(os.data('ns'), target, o);					
        }
        if(!o.keyWord || $.trim(o.keyWord) == '') {
            var i = target.selectedIndex, text = os.find('option').eq(i).text();
            // 初始化赋值
            os.data('ns').find(':text').val(text);
        } else {
            os.data('ns').find(':text').val(o.keyWord);
        }
        // 按键按下
        if(o.onKeyDown && isFunction(o.onKeyDown)){
            ns.find(':text').keydown(function(e){
                return o.onKeyDown(e);
            });
        }
        // 按键弹起
        if(o.ajaxSearch){
            ns.find(':text').keyup(function(e){
                var delay = o.delay || 500;
                clearTimeout(o.getDataEvent);
                o.getDataEvent = setTimeout(function(){	
                    os.selectbox(
                        $.extend({},o,{
                            keyWord: $(e.target).val()
                        })
                    );
                },delay);
            });
        };

        // 显示或者隐藏下拉列表
        ns.click(function(e){
            // 被禁用时返回不处理
            if(os.attr('disabled')){
                return false;
            }

            // 让文本框获得光标
            $(this).find(':text').focus();
            // 列表若已显示则隐藏
            if($(this).find('div.options').is(':visible')){
                factory.clean($(this));
            }
            // 反之则显示
            else{
                // 清除或隐藏其他组件
                banger.controls.hide();
                // 下拉时事件处理函数
                if(o.onDropdown && isFunction(o.onDropdown)){
                    o.onDropdown(target);
                }

                if(os.find('option').length != 0){
                    factory.dropdown($(this), target, o);
                }
            }

            // 阻止事件冒泡
            e.stopPropagation();
        });

        // 点击页面其他区域时清空选项
        $(document).click(function(e){
            factory.clean(ns);
        }); 
	};
	
	// 创建模拟标签
	factory.getHtml = function(os, o){
		var ns = [], nid = '', css = '', style = ' style=', disabled = '', readonly = '', maxLength = '', value = '';
		// 每次重新渲染的时候，保留input，因为input可能已经绑定了一些事件。add by 林贤聪
		var $text = $('<input type="text" class="ui-text-text" />');
		if(os.data('ns')) {
			$text = os.data('ns').find(':text').clone(true);
		}		
		// id
		if(os.attr('id')){
			var id = os.attr('id');
			// nid = ' id="n' + id + '"';
			$text.attr('id', 'n'+id);
		}
		// class
		if(os.attr('class')){
			css = ' ' + os.attr('class');
		}
		// style
		if(os.attr('style')){
			style += os.attr('style');
		}
		// disabled
		if(os.attr('disabled')){
			// disabled = ' disabled="' + os.attr('disabled') + '"';
			css += ' ui-text-disabled';
			$text.attr('disabled', os.attr('disabled'));
		}
		// readonly
		if(o.readonly){
			// readonly = ' readonly="readonly"';
			$text.attr('readonly', 'readonly');
		}
		// maxLength
		if(o.maxLength){
			// maxLength = ' maxLength="' + o.maxLength + '"';
			$text.attr('maxLength', o.maxLength);
		}
		// value
		value = os.find('option:selected').text();
		$text.val(value);
		ns.push('<div class="ui-text ui-selectbox' + css + '"' + style +'>');
		ns.push('<i class="ui-text-icon ui-text-select"></i>');
		ns.push('<div class="options"></div>');
		ns.push('</div>');
		
		return $(ns.join('')).prepend($text);
	};
	
	// 下拉
	factory.dropdown = function(ns, target, o){
		var oOptions = $(target).find('option'), n1 = oOptions.length, n2 = o.scrollRange, nOptionsBox = ns.find('div.options'), nOptionsHtml = [];
		
		// 设置选项列表容器高度
		nOptionsBox.height(n1 > n2 ? (22 * n2) : 'auto');
		// 创建选项
		oOptions.each(function(){
			var oOption = $(this), text = oOption.text(), disabled = oOption.attr('disabled');
			nOptionsHtml.push('<li title="' + text + '"' + (disabled ? ' disabled="disabled"' : '') + '><label>' + text + '</label></li>');
		});
		
		nOptionsBox.html('<ul>' + nOptionsHtml.join('') + '</ul>');
		var nOptions = ns.find('li');
		
		nOptions.click(function(e){
			if($(this).attr('disabled')){
				return false;
			}
			
			var i = nOptions.index(this);
			
			if(o.onSelect && isFunction(o.onSelect)){
				if(!o.onSelect(target, oOptions[i])){
					factory.clean(ns);
					return false;
				}
			}
			
			// 根据当前点击项的索引更新原始下拉单选框选项
			target.selectedIndex = i;
			
			// 触发原始下拉单选框选项事件
			$(target).change();
			
			// 文本框赋值，自动获取及失去光标，用于触发验证
			var value = $(target).find('option:eq(' + i + ')').text();
			
			ns.find(':text').val(value).focus().blur();

			factory.clean(ns);

			//清除keyword的值
			if (o.keyWord) {
				o.keyWord = null; 	
			};
			
			e.stopPropagation();
		}).mouseover(function(){
			if($(this).attr('disabled')){
				return false;
			}
			
			$(this).addClass('hover').siblings().removeClass('hover');
		});
		ns.find('li:eq(' + target.selectedIndex + ')').addClass('hover');
		ns.css('z-index', 100).find('div.options').css('display', 'block');
	};
	
	// 清空及隐藏选项
	factory.clean = function(ns){
		if(ns.length != 0){
			ns.css('z-index', '').find('div.options').html('').css('display', 'none');
		}
	};
	$.fn.selectbox = function(o){
		o = $.extend({}, {
		    readonly: true,
		    maxLength: null,
			scrollRange: 10,
			url: '',
			type: 'POST',
			async: false,
			cache: false,
			data: null,
			dataType: 'json',
			onKeyDown: null,
			onDropdown: null,
			onSelect: null,
			onKeyUp: null,
			getDataEvent:null,
			keyWord: null,
			keyWordParam: 'k',
            addBlankOption: false,
            selectedIndex: '',//初始化选中的值
            filterValue: null, // function 用于筛选服务端的传过来的数组,返回筛选后的数组
            generator: function(){} //产生option的函数，优先级次于ajax获得数据
		}, o);
		
		return this.each(function(){
			if(o.url){
				// 初始化select标签选项
				factory.initOptions(this, o);
			}else{
				// 改造select标签
				factory.reform(this, o);
			}
		});
	};
})(jQuery);