/**
 * jQuery Cookie Plugin v1.4.0
 * https://github.com/carhartl/jquery-cookie
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 **/
(function(factory){
    if(typeof define === 'function' && define.amd){
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    }else{
        // Browser globals.
        factory(jQuery);
    }
}(function($){
    var pluses = /\+/g;

    function encode(s){
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s){
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value){
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s){
        if (s.indexOf('"') === 0){
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try{
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
        }catch(e){
            return;
        }

        try{
            // If we can't parse the cookie, ignore it, it's unusable.
            return config.json ? JSON.parse(s) : s;
        }catch(e){}
    }

    function read(s, converter){
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function(key, value, options){
        // Write
        if(value !== undefined && !$.isFunction(value)){
            options = $.extend({}, config.defaults, options);

            if(typeof options.expires === 'number'){
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // Read
        var result = key ? undefined : {};

        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
        var cookies = document.cookie ? document.cookie.split('; ') : [];

        for(var i = 0, l = cookies.length; i < l; i++){
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = parts.join('=');

            if(key && key === name){
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if(!key && (cookie = read(cookie)) !== undefined){
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options){
        if($.cookie(key) !== undefined){
            // Must not alter options, thus extending a fresh object...
            $.cookie(key, '', $.extend({}, options, { expires: -1 }));
            return true;
        }
        return false;
    };
}));

/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-11-04
 * Description: banger.flexigrid.js
 * Modified by:
 * Modified contents:
 **/
(function($){
    // jQuery 1.9 support, browser object has been removed in 1.9.
    var browser = $.browser;
    if(!browser){
        function userAgentMatch(ua){
            ua = ua.toLowerCase();
            var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf('compatible') < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];
            return {
                browser: match[1] || '',
                version: match[2] || '0'
            };
        };
        var matched = userAgentMatch(navigator.userAgent);
        browser = {};
        if(matched.browser){
            browser[matched.browser] = true;
            browser.version = matched.version;
        }
        // Chrome is Webkit, but Webkit is also Safari.
        if(browser.chrome){
            browser.webkit = true;
        }else if(browser.webkit){
            browser.safari = true;
        }
    }

    /**
     * Start code from jQuery ui
     *
     * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
     * Dual licensed under the MIT or GPL Version 2 licenses.
     * http://jquery.org/license
     *
     * http://docs.jquery.com/UI
     */
    if(typeof $.support.selectstart != 'function'){
        $.support.selectstart = 'onselectstart' in document.createElement('div');
    }
    if(typeof $.fn.disableSelection != 'function'){
        $.fn.disableSelection = function(){
            return this.bind(($.support.selectstart ? 'selectstart' : 'mousedown') + '.ui-disableSelection', function(e){
                e.preventDefault();
            });
        };
    }

    // DOM
    function K(){
        var a = arguments, selector = a[0], doc = a[1];
        if(this == window){
            return new K(selector, doc);
        }
        if(selector.nodeName){
            this.node = selector;
        }else{
            var chr = selector.charAt(0);
            if(chr == '#'){
                selector = selector.substr(1);
                this.node = document.getElementById(selector);
            }else if(chr == '.'){
                var nodes = (doc || document).getElementsByTagName('*'), len = nodes.length;
                selector = selector.substr(1);
                for(var i = 0; i < len; i++){
                    var node = nodes[i];
                    if(K(node).hasClass(selector)){
                        if(!this.node){
                            this.node = [node];
                        }else{
                            this.node.push(node);
                        }
                    }
                }
            }else{
                this.node = (doc || document).getElementsByTagName(selector);
            }
        }
    };
    // 创建元素
    K.create = function(selector){
        return document.createElement(selector);
    };
    // 去除左右空格
    K.prototype.trim = function(str){
        return str == null ? '' : str.toString().replace(/^\s+/, '').replace(/\s+$/, '');
    };
    // 获取或设置元素innerHTML
    K.prototype.html = function(html){
        if(html !== undefined){
            this.node.innerHTML= html;
            return this;
        }
        return this.node.innerHTML;
    };
    // 获取或设置元素的style样式属性
    K.prototype.style = function(key, value){
        var a = arguments, len = a.length, key = a[0], value = a[1];
        if(len == 1){
            var type = typeof key;
            if(type == 'string'){
                if(this.node.style[key]){
                    return this.node.style[key];
                }else if(this.node.currentStyle){
                    return this.node.currentStyle[key];
                }else if(document.defaultView && document.defaultView.getComputedStyle){
                    style = key.replace(/([A-Z])/g, '-$1').toLowerCase();
                    return document.defaultView.getComputedStyle(this.node, null).getPropertyValue(key);
                }
                return null;
            }else if(type == 'object'){
                for(var i in key){
                    this.node.style[i] = key[i];
                }
                return this;
            }
        }else{
            this.node.style[key] = value;
            return this;
        }
    };
    // 获取或设置元素的标签属性
    K.prototype.attr = function(){
        var a = arguments, key = a[0], value = a[1];
        if(a.length == 2){
            this.node.setAttribute(key, value);
            return this;
        }else if(a.length == 1){
            var type = typeof key;
            if(type == 'string'){
                return this.node.getAttribute(key);
            }else if(type == 'object'){
                for(var i in key){
                    this.node.setAttribute(i, key[i]);
                }
                return this;
            }
        }
    };
    // 移除元素属性
    K.prototype.removeAttr = function(attrs){
        var keys = attrs.split(' '), i = 0, len = keys.length;
        for(; i < len; i++){
            this.node.removeAttribute(keys[i]);
        }
        return this;
    };
    // 获取父节点
    K.prototype.parent = function(){
        return (this.node && this.node.parentNode) ? this.node.parentNode : null;
    };
    // 获取同辈元素
    K.prototype.siblings = function(){
        var nodes = [], p = this.node.parentNode, child;
        if(p){
            child = p.childNodes;
            if(child && child.length && child.length > 0){
                for(var i = 0, l = child.length; i < l; i++){
                    var node = child[i];
                    if(node.nodeType === 1 && node !== this.node){
                        nodes.push(node);
                    }
                }
            }
        }
        return nodes;
    };
    // 获取第一个子节点
    K.prototype.firstChild = function(){
        var first = null;
        if(!this.node || !this.node.firstChild){
            return first;
        }
        first = this.node.firstChild;
        while(first && first.nodeType !== 1){
            first = first.nextSibling;
        }
        return first;
    };
    // 查找最后一个子节点
    K.prototype.lastChild = function(){
        var last = null;
        if(!this.node || !this.node.lastChild){
            return last;
        }
        last = this.node.lastChild;
        while(last && last.nodeType !== 1){
            last = last.previousSibling;
        }
        return last;
    };
    // 添加子节点
    K.prototype.append = function(node){
        if(typeof node == 'string'){
            this.node.innerHTML += node;
        }else{
            this.node.appendChild(node);
        }
        return this;
    };
    // 前置新节点
    K.prototype.prepend = function(node){
        if(typeof node == 'string'){
            this.node.innerHTML = node + this.node.innerHTML;
        }else{
            this.node.insertBefore(node, this.firstChild());
        }
        return this;
    };
    // 移除节点
    K.prototype.remove = function(){
        var parent = this.parent();
        parent.removeChild(this.node);
    };
    // 清空子节点
    K.prototype.empty = function(){
        while(this.node.firstChild){
            this.node.removeChild(this.node.firstChild);
        }
        return this;
    };
    // 将一个新节点添加到指定节点之前
    K.prototype.before = function(node){
        var parent = this.parent();
        if(parent){
            if(node && node.nodeName){
                var first = D(parent).firstChild();
                parent.insertBefore(node, first);
            }
        }
        return this;
    };
    // 判断元素是否含有指定的类名
    K.prototype.hasClass = function(value){
        if(this.node && this.node.nodeType === 1 && (' ' + this.node.className + ' ').replace(/[\n\t\r]/g, ' ').indexOf(' ' + value + ' ') > - 1){
            return true;
        }
        return false;
    };
    // 添加样式类
    K.prototype.addClass = function(value){
        if(value && typeof value === 'string'){
            var cs = value.split(/\s+/);
            if(this.node && this.node.nodeType === 1){
                if(!this.node.className && cs.length === 1){
                    this.node.className = value;
                }else{
                    var dc = ' ' + this.node.className + ' ';
                    for(var i = 0, len = cs.length; i < len; i++){
                        if(!~dc.indexOf(' ' + cs[i] + ' ')){
                            dc += cs[i] + ' ';
                        }
                    }
                    this.node.className = this.trim(dc);
                }
            }
        }
        return this;
    };
    // 移除类名
    K.prototype.removeClass = function(value){
        if((value && typeof value === 'string') || value === undefined){
            var cs = (value || '').split(/\s+/);
            if(this.node && this.node.nodeType === 1 && this.node.className){
                if(value){
                    var dc = (' ' + this.node.className + ' ').replace(/[\n\t\r]/g, ' ');
                    for(var i = 0, len = cs.length; i < len; i++ ){
                        dc = dc.replace(' ' + cs[i] + ' ', ' ');
                    }
                    this.node.className = this.trim(dc);
                }else{
                    this.node.className = '';
                }
            }
        }
        return this;
    };
    // 切换类名
    K.prototype.toggleClass = function(value){
        if(this.hasClass(value)){
            this.removeClass(value);
        }else{
            this.addClass(value);
        }
    };

    // 判断是否为ie6浏览器
    var isIe6 = !-[1,] && !window.XMLHttpRequest;

    //
    var wp = window.location.pathname;
    //
    var globalSpace = wp.slice(0, wp.indexOf('.')) + '/';

    // 获取变量类型
    var getType = function(obj){
        return Object.prototype.toString.call(obj);
    };

    // 主函数
    $.addFlex = function(t, o){

        if(t.flexigrid){
            return false;
        }

        var tid = t.id;

        if(!tid){
            return t;
        }

        // 扩展自定义配置
        o = $.extend({
            height: 200, // 高度
            width: 'auto', // 宽度
            resizable: false, // 是否允许调整表格尺寸
            heightResizable: true, // 是否允许调整表格高度
            widthResizable: true, // 是否允许调整表格宽度
            striped: true, // 是否应用隔行换色效果
            nowrap: true, // 单元格内文本是否不换行
            multiSelect: false, // 是否启用多选
            colResizable: true, // 是否允许调整列宽
            colMovable: true, // 是否允许拖动列，即调整列顺序
            colMinWidth: 30, // 最小列宽
            addTitleToCell: true, //
            data: null, // 数据集合
            autoLoad: true, // 自动加载
            url: false, // 请求地址
            method: 'POST', // 请求方式
            params: null, // 请求参数
            dataType: 'json', // 数据类型
            loadingTips: '数据加载中', // 加载数据时的文本提示
            noDataTips: '未搜索到匹配项',
            total: 0, // 共多少条数据
            page: 1, // 当前页码
            pages: 1, // 共多少页
            rowIdProperty: 'id', // 行id属性
            usePage: false, // 是否使用分页
            useRp: true, // 是否使用每页显示多少条数据的条件选项
            rpOptions: [25, 50, 100], // 每页显示多少条数据的条件选项
            defaultRp: 25, // 默认每页显示多少条数据
            preProcessData: null, // 预处理数据
            onSortChange: null, // 排序事件处理函数
            onError: null, // 请求出错时的处理函数
            onComplete: null, // 请求完成时的处理函数
            isDisabled: function () {}, //是否启动某个操作按钮
            isShow: function () {}, //是否显示某个操作按钮
            isRowSpan: false,//是否合并单元格(存在页面显示Bug)
            cellSpanNum: 0,//合并几列
            onRowDblclick: null, // 行双击事件
            extendGridClass: function(g){ // 扩展g公共类
                return g;
            },
            //
            extendCell: {
                all: null
            },
            extendRow: function (row) {
            }, //可以扩展行，返回整个行的内部html结构。如'<td>测试</td>',假如没有扩展，返回false add by 林贤聪
            //
            factory: {
                extendDataCol: function(o, field, cell, data){
                    var fn = o.extendCell[field];
                    if(fn && typeof fn === 'function'){
                        return fn(cell, data);
                    }
                    return cell;
                }
            }
        }, o);

        // 创建g公共类
        var g = {
            //
            data: null,
            //
            dgwidth: 5,
            dgheight: 0,
            // 设置相关标签高度
            fixHeight: function(){
                var h = { box: $(this.box).outerHeight(), head: $(this.head).outerHeight(), body: $(this.body).outerHeight() };

                // 设置数据加载遮罩层的高度
                $(this.load).css({ 'margin-top': -o.height, 'height': o.height, 'line-height': o.height + 'px' });

                // 设置调节列宽的标签高度
                g.dgheight = h.head + h.body;
                $(this.drag).find('div').each(function(){
                    $(this).css({ 'width': g.dgwidth, 'height': g.dgheight });
                });
            },
            // 设置调节列宽的标签位置
            setDragerPosition: function(){
                this.fixHeight();

                var top = $(this.head).position().top, ths = $(this.head).find('th:visible'), scrollLeft = $(this.head).scrollLeft(), offsetLeft = -scrollLeft, cellPadding = this.cellPadding;
                offsetLeft -= Math.ceil(g.dgwidth / 2);
                $(this.drag).css('top', top).find('div').css('display', 'none');
                ths.each(function(i){
                    var cellWidth = $(this).find('div').width();
                    cellWidth = cellWidth + cellPadding + offsetLeft;
                    if(isNaN(cellWidth)){
                        cellWidth = 0;
                    }
                    $(g.drag).find('div').eq(i).css({ 'left': cellWidth, 'display': 'block' });
                    offsetLeft = cellWidth;
                });
            },
            //
            addCellProp: function(){
                var ths = K('th', this.head).node, trs = K('tr', this.body).node, trsLen = trs.length;
                for(var i = 0; i < trsLen; i++){
                    var tr = trs[i], tds = K('td', tr).node, tdsLen = tds.length;
                    // 隔行换色
                    if(o.striped && i % 2){
                        K(tr).addClass('odd');
                    }
                    for(var j = 0; j < tdsLen; j++){
                        var th = ths[j], thInner = K('div', th).node[0], td = tds[j], tdInner = K.create('div');
                        if(th){
                            if(o.sortField && K(th).attr('sort') == o.sortField){
                                K(td).addClass('sort');
                            }
                            if(K(th).attr('hide')){
                                K(td).style('display', 'none');
                            }
                            K(tdInner).style({ 'width': K(thInner).style('width'), 'textAlign': K(th).attr('align') });
                        }
                        // 超过部分折行
                        if(o.nowrap == false){
                            K(tdInner).style('white-space', 'normal');
                        }
                        K(tdInner).html(K(td).html());
                        K(td).removeAttr('width').empty().append(tdInner);
                        if(K(td).attr('mark') == 'action'){
                            K(tdInner).attr('mark', 'action');
                        }
                        g.addTitleToCell(tdInner);
                    }
                }
            },
            // 添加单元格title属性
            addTitleToCell: function(div){
                if(o.addTitleToCell && $(div).attr('mark') != 'action'){
                    $(div).attr('title', $(div).text());
                }
            },
            //
            addGridProp: function(){
                //
                if(o.multiSelect){
                    $('table', g.head).off('click.select').on('click.select', 'input[mark="check"]', function(e){
                        var flag = $(this).attr('checked') ? true : false, checkboxs = $(g.body).find(':checkbox[mark="check"]:enabled');
                        checkboxs.attr('checked', flag).parents('tr')[flag ? 'addClass' : 'removeClass']('selected');
                        //
                        if(o.onAllSelect && typeof o.onAllSelect == 'function'){
                            var datas = [], checkeds = checkboxs.filter(':checked');
                            for(var i = 0, len = checkeds.length; i < len; i++){
                                if(g.data && g.data.rows){
                                    var checked = checkeds.eq(i), idx = checked.attr('data'), data = g.data.rows[idx];
                                    datas.push(data);
                                }
                            }
                            o.onAllSelect(flag, checkeds, datas);
                        }
                    });
                }


                $('table', g.body).unbind().on('click', 'tr', function(e){
                    // 单选
                    if(o.multiSelect == false){
                        $(this).addClass('selected').siblings().removeClass('selected');
                    }
                    // 自定义单击事件
                    if(o.onRowClick && typeof o.onRowClick == 'function'){
                        var i = $(this).attr('data'), data = null;
                        if(g.data && g.data.rows){
                            data = g.data.rows[i];
                        }
                        o.onRowClick(this, data);
                    }
                }).on('dblclick', 'tr', function(e){
                    // 单选
                    if(o.multiSelect == false){
                        $(this).addClass('selected').siblings().removeClass('selected');
                    }
                    // 自定义双击事件
                    if(o.onRowDblclick && typeof o.onRowDblclick == 'function'){
                        var i = $(this).attr('data'), data = null;
                        if(g.data && g.data.rows){
                            data = g.data.rows[i];
                        }
                        o.onRowDblclick(this, data);
                    }
                }).on('click', 'input[mark="check"]', function(e){
                    var flag = $(this).attr('checked') ? true : false;
                    $(this).parents('tr')[flag ? 'addClass' : 'removeClass']('selected');

                    var checkboxs = $(g.body).find(':checkbox:enabled'), checkeds = checkboxs.filter(':checked');
                    $(g.head).find(':checkbox[mark="check"]').attr('checked', checkboxs.length == checkeds.length);

                    // 自定义勾选事件
                    if(o.onRowSelect && typeof o.onRowSelect == 'function'){
                        var data = null;
                        if(g.data && g.data.rows){
                            var idx = $(this).attr('data');
                            data = g.data.rows[idx];
                        }
                        o.onRowSelect(flag, $(this), data);
                    }
                    // 阻止事件继续冒泡
                    e.stopPropagation();
                }).on('click', 'label[mark="action"][disabled!="disabled"]', function(e){
                    var i = $(this).attr('button'), j = $(this).attr('data');
                    if(o.action && o.action.buttons){
                        var fn = o.action.buttons[i].onClick;
                        fn(null, g.data.rows[j]);
                    }
                    // 阻止事件继续冒泡
                    e.stopPropagation();
                });

                /*
                 $('table', g.body).unbind().on('click', 'tr', function(e){
                 // 获取事件源对象
                 var tr = this, elem = e.target || e.srcElement;

                 var $elem = $(elem), mark = $elem.attr('mark');

                 switch(mark){
                 // 操作按钮
                 case 'action':
                 var di = $elem.attr('data'), bi = $elem.attr('button');
                 if($elem.hasClass('ui-link-disabled')){
                 return false;
                 }
                 if(o.action && o.action.buttons){
                 var btn = o.action.buttons[bi];
                 if(btn && btn.onClick && typeof btn.onClick == 'function'){
                 btn.onClick(g.data.rows[di], tr);
                 }
                 }
                 return;
                 break;
                 // 复选按钮
                 case 'check':

                 return;
                 break;
                 default: break;
                 }
                 });
                 */

                if(isIe6){
                    $('tbody tr', g.body).hover(function(e){
                        K(this).addClass('hover');
                    }, function(){
                        K(this).removeClass('hover');
                    });
                }
            },
            // 去除列头内全选按钮的选中状态
            cleanChecked: function(){
                $(':checkbox[mark="check"]', g.head).attr('checked', false);
            },
            // 横向滚动
            scroll: function(){
                this.head.scrollLeft = this.bodyInner.scrollLeft;
                this.setDragerPosition();
            },
            // 搜索
            doSearch: function(params){
                o.params = $.extend(o.params, params);
                o.newPage = 1;
                this.populate();
            },
            // 排序
            changeSort: function(th){
                // 如果正在加载则返回不处理
                if(this.isLoading){
                    return false;
                }
                var $th = $(th);
                if($th.attr('sort') == o.sortField){
                    o.sortType = (o.sortType == 'asc') ? 'desc' : 'asc';
                }
                o.sortField = $th.attr('sort');
                $th.addClass('sort').siblings().removeClass('sort');
                $('.asc', this.head).removeClass('asc');
                $('.desc', this.head).removeClass('desc');
                $('div', th).addClass(o.sortType);
                if(o.onSortChange && typeof o.onSortChange == 'function'){ // 自定义排序
                    o.onSortChange(o.sortField, o.sortType);
                }else{
                    this.populate();
                }
            },
            // 分页
            changePage: function(type){
                // 如果正在加载则返回不处理
                $(".ui-flexigrid-body-inner").scrollTop(0);
                if(this.isLoading){
                    return false;
                }
                switch(type){
                    case 'first': // 首页
                        o.newPage = 1;
                        break;
                    case 'prev': // 上一页
                        if(o.page > 1){
                            o.newPage = parseInt(o.page, 10) - 1;
                        }
                        break;
                    case 'next': // 下一页
                        if(o.page < o.pages){
                            o.newPage = parseInt(o.page, 10) + 1;
                        }
                        break;
                    case 'last': //尾页
                        o.newPage = o.pages;
                        break;
                    case 'jump':
                        var num = parseInt($('.page', this.page).val(), 10);
                        if(isNaN(num)){
                            num = 1;
                        }
                        if(num < 1){
                            num = 1;
                        }else if(num > o.pages){
                            num = o.pages;
                        }
                        $('.page', this.page).val(num);
                        o.newPage = num;
                        break;
                }
                //保存当前页Checked选中状态
                o.CheckedData= o.CheckedData||{};
                o.CheckedData[o.page]=[];
                g.Checked=g.Checked||{};
                g.Checked[o.page]=g.Checked[o.page]||{};
                $('tr',this.body).each(function(index, element) {
                    // g.data.rows[index].id
                    g.Checked[o.page][index]=$('input:checkbox',this).prop("checked");
                    $('input:checkbox',this).prop("checked")&&o.CheckedData[o.page].push(g.data.rows[index]);
                });

                //
                if(o.onPageChange && typeof o.onPageChange == 'function'){
                    o.onPageChange(o.newPage);
                }else{
                    this.populate();
                }
            },
            // 填充数据
            populate: function(){
                //
                if(o.multiSelect){
                    this.cleanChecked();
                }
                // 若当前正在加载数据或url地址未定义则返回不处理
                if(this.isLoading || !o.url){
                    return false;
                }
                // 标注当前正在加载数据
                this.isLoading = true;
                // 添加数据加载遮罩层
                $(this.load).html('<span class="load">' + o.loadingTips + ' </span>');
                $(this.body).after(this.load);
                //
                if(!o.newPage){
                    o.newPage = 1;
                }
                //
                if(o.page > o.pages){
                    o.page = o.pages;
                }
                // 定义请求参数
                var params = $.extend({ 'pageNum': o.newPage, 'pageSize': o.defaultRp, 'sortfield': o.sortField, 'sorttype': o.sortType }, o.params);

                var url = typeof o.url === 'function' ? o.url() : o.url;

                // 发起请求
                $.ajax({
                    type: o.method,
                    url: url,
                    data: params,
                    dataType: o.dataType,
                    success: function(data){ // 成功
                        g.addData(data);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown){ // 失败
                        /*
                         try{
                         if(o.onError && typeof o.onError == 'function'){
                         o.onError(XMLHttpRequest, textStatus, errorThrown);
                         }
                         }catch(e){  }
                         */
                    	g.isLoading = false;
                    }
                });
            },
            // 解析数据
            addData: function(data){
                //
                if(!data){
                    if(o.onComplete && typeof o.onComplete == 'function'){
                        o.onComplete(data);
                    }
                    return false;
                }
                // 预处理数据
                if(o.preProcessData && typeof o.preProcessData == 'function'){
                    data = o.preProcessData(data);
                }
                //
                data = $.extend({ total: 0, page: 0, rows: [] }, data);
                //
                this.data = data;
                // 共多少条数据
                o.total = data.total;
                // 当前页码
                o.page = data.page;
                // 计算共多少页
                o.pages = Math.ceil(o.total / o.defaultRp);
                // 如果总数为零
                if(o.total == 0){
                    K(t).empty();
                    K(g.load).html('<span class="no">' + o.noDataTips + '</span>');
                    o.page = 1;
                    o.pages = 1;
                    this.buildPager();
                    if(o.onComplete && typeof o.onComplete == 'function'){
                        o.onComplete(data);
                    }
                    // 指示未在加载
                    this.isLoading = false;
                    return false;
                }
                //
                this.buildPager();
                //
                if (data.rows) {
                    // 创建tbody
                    var tbody = K.create('tbody'), rows = data.rows, rowsLen = rows.length, cols = K('th', g.head).node, colsLen = cols.length;
                    var preRowId = -1;// 前一行主键
                    for (var i = 0; i < rowsLen; i++) {
                        var row = rows[i];
                        // 遍历创建行
                        if (row && row.cols) {
                            var tr = K.create('tr'), id = row[o.rowIdProperty];
                            //
                            K(tr).attr('data', i);
                            // 添加行id属性
                            if (id) {
                                K(tr).attr({ 'id': 'flex' + id });
                            }
                            // 添加行name属性
                            if (row.name) {
                                K(tr).attr('name', row.name);
                            }
                            // 添加行class属性
                            if (row.style) {
                                K(tr).addClass(row.style);
                            }
                            //用户自定义这一行的结构 add by 林贤聪
                            var extendRow;
                            if (o.extendRow && isFunction(o.extendRow) && (extendRow = o.extendRow(row, i))) {
                                K(tr).html(extendRow);
                            } else {
                                if (o.isRowSpan) {
                                    // 下一行主键，如果当前行是最后一行，此值为空
                                    var nextRowId = -1;
                                    var num = i + 1;
                                    if (num < rowsLen) {
                                        var nextRow = rows[i + 1];
                                        nextRowId = nextRow[o.rowIdProperty];
                                    }
                                    // 遍历创建列
                                    for (var j = 0; j < colsLen; j++) {
                                        var col = cols[j], td = K.create('td');
                                        if (preRowId == id && id != nextRowId && j < o.cellSpanNum) {
                                            continue;
                                        }
                                        // 设置单元格文本对齐方式
                                        K(td).attr('align', K(col).attr('align'));
                                        // 填充数据列
                                        if (K(col).attr('axis')) {
                                            var idx = K(col).attr('axis'), field = o.fields[idx].field, cell = null;
                                            // Array数组格式
                                            if (isArray(row.cols)) {
                                                cell = row.cols[idx];
                                            }
                                            // Json对象格式
                                            else if (isPlainObject(row.cols)) {
                                                // 当前行主键与前一行主键不同，但是与下一行主键相同，就合并单元格
                                                cell = row.cols[o.fields[idx].field];
                                            }
                                            // 代码表 值转文本
                                            if(o.fields[idx].data){
                                                datakey = cell + '';
                                                if(o.fields[idx].data[datakey]!=undefined){
                                                    cell = o.fields[idx].data[datakey];
                                                }
                                            }
                                            cell = o.factory.extendDataCol(o, field, cell, row);
                                            K(td).html(cell);
                                            if (preRowId != id && id == nextRowId && idx < o.cellSpanNum) {
                                                K(td).attr('rowspan', 2);
                                            }
                                            K(tr).append(td);
                                        }
                                        // 填充复选列
                                        else if (K(col).attr('col') == 'check') {
                                            var checked = '', disabled = '';
                                            if (row.checked === true) {
                                                checked = ' checked="checked"';
                                            }
                                            if (row.checkable === false) {
                                                disabled = ' disabled="disabled"';
                                            }
                                            K(td).html('<input type="checkbox"' + checked + disabled + ' class="ui-flexigrid-checkbox" mark="check" data="' + i + '" />');
                                            if (preRowId != id && id == nextRowId) {
                                                K(td).attr('rowspan', 2);
                                            }
                                            K(tr).append(td);
                                        }
                                        // 填充操作列
                                        else if (K(col).attr('col') == 'action' && o.action && o.action.buttons) {
                                            K(td).attr('mark', 'action');
                                            var btns = o.action.buttons, btnLen = btns.length;
                                            for (var k = 0; k < btnLen; k++) {
                                                var btn = btns[k];
                                                if (btn) {
                                                    var label = K.create('label');
                                                    K(label).addClass('ui-link mr5').attr({ 'mark': 'action', 'data': i, 'button': k }).html(btn.display);
                                                    if (btn.isDisabled && btn.isDisabled(row, i, data)) {
                                                        K(label).addClass('ui-link-disabled').attr('disabled', 'disabled');
                                                    }
                                                    if (btn.isShow && !btn.isShow(row)) { //add by 林贤聪
                                                        K(label).addClass('hide');
                                                    }
                                                    g.addTitleToCell(label);
                                                    K(td).append(label);
                                                }
                                            }
                                        }
                                        K(tr).append(td);
                                    }
                                } else {
                                    // 遍历创建列
                                    for (var j = 0; j < colsLen; j++) {
                                        var col = cols[j], td = K.create('td');
                                        // 设置单元格文本对齐方式
                                        K(td).attr('align', K(col).attr('align'));
                                        // 填充数据列
                                        if (K(col).attr('axis')) {
                                            var idx = K(col).attr('axis'), field = o.fields[idx].field, cell = null;
                                            // Array数组格式
                                            if (isArray(row.cols)) {
                                                cell = row.cols[idx];
                                            }
                                            // Json对象格式
                                            else if (isPlainObject(row.cols)) {
                                                cell = row.cols[o.fields[idx].field];
                                            }
                                            // 代码表 值转文本
                                            if(o.fields[idx].data){
                                                datakey = cell + '';
                                                if(o.fields[idx].data[datakey]!=undefined){
                                                    cell = o.fields[idx].data[datakey];
                                                }
                                            }
                                            cell = o.factory.extendDataCol(o, field, cell, row);
                                            K(td).html(cell);
                                        }
                                        // 填充复选列
                                        else if (K(col).attr('col') == 'check') {
                                            var checked = '', disabled = '';
                                            if (row.checked === true) {
                                                checked = ' checked="checked"';
                                            }
                                            if (row.checkable === false) {
                                                disabled = ' disabled="disabled"';
                                            }
                                            K(td).html('<input type="checkbox"' + checked + disabled + ' class="ui-flexigrid-checkbox" mark="check" data="' + i + '" />');
                                        }
                                        // 填充操作列
                                        else if (K(col).attr('col') == 'action' && o.action && o.action.buttons) {
                                            K(td).attr('mark', 'action');
                                            var btns = o.action.buttons, btnLen = btns.length;
                                            for (var k = 0; k < btnLen; k++) {
                                                var btn = btns[k];
                                                if (btn) {
                                                    var label = K.create('label');
                                                    K(label).addClass('ui-link mr5').attr({ 'mark': 'action', 'data': i, 'button': k }).html(btn.display);
                                                    if (btn.isDisabled && btn.isDisabled(row, i, data)) {
                                                        K(label).addClass('ui-link-disabled').attr('disabled', 'disabled');
                                                    }
                                                    if (btn.isShow && !btn.isShow(row)) { //add by 林贤聪
                                                        K(label).addClass('hide');
                                                    }
                                                    g.addTitleToCell(label);
                                                    K(td).append(label);
                                                }
                                            }
                                        }
                                        K(tr).append(td);
                                    }

                                }
                            }
                            K(tbody).append(tr);
                        }
                        preRowId = id;
                    }
                }
                K(t).empty().append(tbody);

                this.addCellProp();
                this.addGridProp();

                this.scroll();

                // 移除数据加载遮罩层
                K(g.load).remove();
                // 触发完成时的回调函数
                if(o.onComplete){
                    o.onComplete(data);
                }
                // 指示未在加载
                this.isLoading = false;
            },
            // 重新构建分页栏
            buildPager: function(){
                // 修改当前页码
                $('.page', this.page).val(o.page);
                // 修改总页数

                $('.pages', this.page).text(o.pages);
            },
            // 拖动开始
            dragStart: function(e, obj, type){
                if(type == 'colResize' && o.colResizable == true){
                    var index = $('div', this.drag).index(obj), width = $('th:visible div:eq(' + index + ')', this.head).width();
                    // 设置线条样式
                    $(obj).addClass('drag').siblings().css('display', 'none');
                    $(obj).prev().addClass('drag').css('display', 'block');
                    // 记录坐标等相关数据
                    this.colResizeData = {
                        startX: e.pageX,
                        index: index,
                        left: parseInt(obj.style.left, 10),
                        width: width
                    };
                    // 设置body区鼠标样式
                    $('body').css('cursor', 'col-resize');
                }
                // 禁止浏览器默认的文本选中功能
                $('body').noSelect();
            },
            // 拖动时
            dragMove: function(e){
                if(this.colResizeData){
                    var index = this.colResizeData.index;
                    var diff = e.pageX - this.colResizeData.startX;
                    var nLeft = this.colResizeData.left + diff;
                    var nw = this.colResizeData.width + diff;
                    if(nw > o.colMinWidth){
                        $('div:eq(' + index + ')', this.drag).css('left', nLeft);
                        this.colResizeData.nw = nw;
                    }
                }
            },
            // 拖动结束
            dragEnd: function(){
                if(this.colResizeData){
                    var index = this.colResizeData.index, nw = this.colResizeData.nw;
                    $('th:visible div:eq(' + index + ')', this.head).width(nw);
                    $('tr', this.body).each(function(){
                        $('td:visible div:eq(' + index + ')', this).width(nw);
                    });
                    $('.drag', this.drag).removeClass('drag');
                    $('div:eq(' + index + ')', this.drag).siblings().css('display', 'block');

                    this.scroll();
                    this.fixHeight();

                    this.colResizeData = false;

                    // 缓存列宽
                    if($.cookie){
                        var th = $('th:visible:eq(' + index + ')', this.head), colName = th.attr('name');
                        if(colName){
                            $.cookie(globalSpace + tid + '/colwidths/' + colName, nw);
                        }
                    }
                }
                // 恢复body区鼠标样式
                $('body').css('cursor', 'default');
                // 恢复浏览器默认的文本选中功能
                $('body').noSelect(false);
            }
        };

        // 扩展g公共类
        g = o.extendGridClass(g);

        // 重置表格属性
        $(t).attr({ 'border': 0, 'cellPadding': 0, 'cellSpacing': 0 }).removeAttr('width');

        g.box = K.create('div'); // 创建全局容器
        g.head = K.create('div'); // 创建表头容器
        g.body = K.create('div'); // 创建表容器
        g.drag = K.create('div'); // 创建列拖动按钮的容器
        g.load = K.create('div'); // 创建数据加载时的遮罩层
        g.page = K.create('div'); // 创建分页容器

        // 填充原始表格的表头
        if(o.fields){
            var thead = [];
            thead.push('<thead><tr>');
            // 创建复选列列头
            if(o.multiSelect){
                var wSelectCol = 20;
                if($.cookie){
                    var src = globalSpace + tid + '/colwidths/select';
                    if($.cookie(src) != undefined){
                        wSelectCol = $.cookie(src);
                    }
                }
                thead.push('<th width="' + wSelectCol + '" align="center" name="select" col="check"><input type="checkbox" class="ui-flexigrid-checkbox" mark="check" /></th>');
            }
//============================================= =============调整操作列顺序============================================================
            // 创建操作列列头
            if(o.action){
                var col = o.action;
                if($.cookie){
                    var src = globalSpace + tid + '/colwidths/action';
                    if($.cookie(src) != undefined){
                        col.width = $.cookie(src);
                    }
                }
                thead.push('<th width="' + (col.width || 100) + '" align="' + (col.align || 'left') + '" name="action" col="action">' + col.display + '</th>');
            }
            // 创建数据列列头
            var fields = o.fields, len = fields.length;
            for(var i = 0; i < len; i++){
                var col = fields[i];
                if(col){
                    if($.cookie){
                        var src = globalSpace + tid + '/colwidths/' + col.field;
                        if($.cookie(src) != undefined){
                            col.width = $.cookie(src);
                        }
                    }
                    var sort = '';
                    if(col.sortable && col.field){
                        sort = ' sort="' + col.field + '"';
                    }
                    thead.push('<th width="' + (col.width || 100) + '" align="' + (col.align || 'left') + '" name="' + col.field + '" col="field" axis="' + i + '"' + sort + '>' + col.display + '</th>');
                }
            }
//======================================================================================================================
            thead.push('</tr></thead>');
            $(t).prepend(thead.join(''));
        }

        // 创建全局容器
        $(g.box).addClass('ui-flexigrid' + (browser.msie ? ' ui-flexigrid-ie' : '')).css('width', o.width);
        $(t).before(g.box);
        $(g.box).append(t);

        // 创建表头
        $(g.head).addClass('ui-flexigrid-head clearfix').html('<div class="ui-flexigrid-head-inner"><table border="0" cellpadding="0" cellspacing="0"></table></div>');
        $(t).before(g.head);
        var thead = $('thead', t);
        if(thead.length > 0){
            $(g.head).find('table').append(thead);
        }
        var ths = $(g.head).find('th'), len = ths.length;
        for(var i = 0; i < len; i++){
            var th = ths[i], sort = $(th).attr('sort'), div = K.create('div');
            if(sort){
                // 如果当前列为默认排序的列，则添加排序样式
                if(sort == o.sortField){
                    $(th).addClass('sort');
                    $(div).addClass(o.sortType);
                }
                // click event
                $(th).click(function(){
                    //
                    if(!$(this).hasClass('hover')){
                        return false;
                    }
                    // 排序
                    g.changeSort(this);
                });
            }
            // 设置宽度
            if(!$(th).attr('width')){
                $(th).attr('width', 100);
            }
            // 设置文本对齐方式
            if(!$(th).attr('align')){
                $(th).attr('align', 'left');
            }
            //
            if(!o.fields){
                $(th).attr('axis', i);
            }
            // 隐藏该列
            if($(th).attr('hide')){
                $(th).style('display', 'none');
            }
            $(div).css({ 'width': $(th).attr('width'), 'textAlign': $(th).attr('align') }).html($(th).html());
            $(th).empty().removeAttr('width').append(div).mousedown(function(e){
                // g.dragStart('colMovable', e, this);
            }).hover(function(e){
                //
                if(!g.colresize && !$(this).hasClass('move') && !g.colCopy){
                    $(this).addClass('hover');
                }
                //
                if($(this).attr('sort') != o.sortField && !g.colCopy && !g.colresize && $(this).attr('sort')){
                    $('div', this).addClass(o.sortType);
                }else if($(this).attr('sort') == o.sortField && !g.colCopy && !g.colresize && $(this).attr('sort')){
                    var cls = (o.sortType == 'asc') ? 'desc' : 'asc';
                    $('div', this).removeClass(o.sortType).addClass(cls);
                }
            }, function(e){
                $(this).removeClass('hover');
                //
                var thSort = $(this).attr('sort');
                if(thSort != o.sortField){
                    $('div', this).removeClass(o.sortType);
                }else{
                    var cls = o.sortType == 'asc' ? 'desc' : 'asc';
                    $('div', this).addClass(o.sortType).removeClass(cls);
                }
            });
        }

        // 创建表
        g.bodyInner = K.create('div');
        K(g.bodyInner).addClass('ui-flexigrid-body-inner').style('height', o.height + 'px').append(t);
        g.addCellProp();
        g.addGridProp();
        K(g.body).addClass('ui-flexigrid-body').append(g.bodyInner);
        K(g.box).append(g.body);
        $(g.bodyInner).scroll(function(){
            g.scroll();
        });

        // 创建调整列宽的标签
        if(o.colResizable){
            var ths = $('thead tr:first th', g.head)
            if(ths.length > 0){
                var th = ths.get(0);
                g.cellPadding = 0;
                g.cellPadding += (isNaN(parseInt($('div', th).css('borderLeftWidth'), 10)) ? 0 : parseInt($('div', th).css('borderLeftWidth'), 10));
                g.cellPadding += (isNaN(parseInt($('div', th).css('borderRightWidth'), 10)) ? 0 : parseInt($('div', th).css('borderRightWidth'), 10));
                g.cellPadding += (isNaN(parseInt($('div', th).css('paddingLeft'), 10)) ? 0 : parseInt($('div', th).css('paddingLeft'), 10));
                g.cellPadding += (isNaN(parseInt($('div', th).css('paddingRight'), 10)) ? 0 : parseInt($('div', th).css('paddingRight'), 10));
                g.cellPadding += (isNaN(parseInt($(th).css('borderLeftWidth'), 10)) ? 0 : parseInt($(th).css('borderLeftWidth'), 10));
                g.cellPadding += (isNaN(parseInt($(th).css('borderRightWidth'), 10)) ? 0 : parseInt($(th).css('borderRightWidth'), 10));
                g.cellPadding += (isNaN(parseInt($(th).css('paddingLeft'), 10)) ? 0 : parseInt($(th).css('paddingLeft'), 10));
                g.cellPadding += (isNaN(parseInt($(th).css('paddingRight'), 10)) ? 0 : parseInt($(th).css('paddingRight'), 10));
                ths.each(function(){
                    var dg = K.create('div');
                    $(dg).mousedown(function(e){
                        g.dragStart(e, this, 'colResize');
                    }).dblclick(function(e){
                        // g.autoResizeColumn(this);
                    });
                    if(isIe6){
                        $(dg).hover(function(){
                            $(this).addClass('hover');
                        }, function(){
                            $(this).removeClass('hover');
                        });
                    }
                    $(g.drag).append(dg);
                });
                $(g.drag).addClass('ui-flexigrid-drag');
                $(g.box).append(g.drag);
            }
        }

        // 创建数据加载遮罩层
        var bt = g.body.offsetTop, bw = $(g.body).width(), bh = $(g.body).height();
        $(g.load).addClass('ui-flexigrid-load').html('<span class="load">' + o.loadingTips + ' </span>');

        // 创建分页栏
        if(o.usePage){
            var html = [];
            html.push('<div class="ui-flexigrid-page-inner clearfix">');
            html.push('<div class="group"><div class="button first"><span>首页</span></div><div class="button prev"><span>上一页</span></div><div class="button next"><span>下一页</span></div><div class="button last"><span>尾页</span></div></div>');
            html.push('<div class="separator"></div>');
            html.push('<div class="group"><div class="current"><label class="word">第</label><input type="text" class="page" value="1" /><label class="word">页</label></div><div class="total"><label class="word">共</label><label class="word pages">1</label><label class="word">页</label></div></div>');
            html.push('<div class="separator"></div>');
            // 是否输出每页多少条选项
            if(o.useRp){
                var opts = [], selected = '', i = 0, len = o.rpOptions.length;
                for(; i < len; i++){
                    var opt = o.rpOptions[i];
                    selected = (opt == o.defaultRp) ? ' selected="selected"' : '';
                    opts.push('<option value="'  + opt + '"' + selected + '>' + opt + '</option>');
                }
                html.push('<div class="group"><select name="rp" class="rp">' + opts.join('') + '</select></div>');
            }
            html.push('</div>');
            $(g.page).addClass('ui-flexigrid-page').html(html.join(''));
            // 首页
            $('.first', g.page).click(function(){
                g.changePage('first');
            });
            // 上一页
            $('.prev', g.page).click(function(){
                g.changePage('prev');
            });
            // 下一页
            $('.next', g.page).click(function(){
                g.changePage('next');
            });
            // 尾页
            $('.last', g.page).click(function(){
                g.changePage('last');
            });
            // 跳转
            $('.page', g.page).keydown(function(e){
                if(e.keyCode == 13){
                    g.changePage('jump');
                }
            });
            // 每页显示多少条
            if(o.useRp){
                $('select', g.page).change(function(){
                    if(o.onRpChange){
                        o.onRpChange($(this).val());
                    }else{
                        o.newPage = 1;
                        o.defaultRp = $(this).val();
                        g.populate();
                    }
                });
            }
            $(g.box).append(g.page);
        }

        // 设置相关标签高度
        g.fixHeight();
        g.setDragerPosition();

        // Add document events
        $(document).mousemove(function(e){
            g.dragMove(e);
        }).mouseup(function(e){
            g.dragEnd();
        }).hover(function(){

        }, function(){
            g.dragEnd();
        });

        if(o.url && o.autoLoad){
            g.populate();
        }

        t.o = o;
        t.grid = g;
        t.flexigrid = 1;

        return t;
    };

    // 用于判断文档是否已完成加载
    var docIsReady = false;

    //
    $(function(){

        docIsReady = true;

    });

    // 入口
    $.fn.flexigrid = function(o){

        return this.each(function(){

            var table = this;

            if(!docIsReady){

                $(function(){

                    $.addFlex(table, o);

                });

            }else{

                $.addFlex(table, o);

            }

        });

    };

    // 搜索
    $.fn.flexSearch = function(params){
        return this.each(function(){
            if(this.o && this.grid){
                this.o.CheckedData={};
                this.grid.Checked={};
                this.grid.doSearch(params);
            }
        });
    };

    // 刷新
    $.fn.flexReload = function(opts){
        return this.each(function(){
            if(this.o && this.grid){
                this.o.CheckedData={};
                this.grid.Checked={};
                this.grid.populate();
            }
        });
    };

    //
    $.fn.setDragerPostion = function(opts){
        return this.each(function(){
            if(this.o && this.grid){
                this.grid.setDragerPosition();
            }
        });
    };

    //
    $.fn.flexOptions = function(opts){
        return this.each(function(){
            if(this.o){
                $.extend(this.o, opts);
            }
        });
    };

    //
    $.fn.flexToggleCol = function(cid, visible){

        return this.each(function(){

            if(this.grid){

                this.grid.toggleCol(cid, visible);

            }

        });

    };

    // Function to add data to grid
    $.fn.flexAddData = function(data){
        return this.each(function(){
            if(this.grid){
                this.grid.addData(data);
            }
        });
    };

    // 是否可以选中grid中的文本
    $.fn.noSelect = function(o){
        var prevent = (o === null) ? true : o;
        if(prevent){
            return this.each(function(){
                if(browser.msie || browser.safari){
                    $(this).bind('selectstart', function(){
                        return false;
                    });
                }else if(browser.mozilla){
                    $(this).css('MozUserSelect', 'none');
                    $('body').trigger('focus');
                }else if(browser.opera){
                    $(this).bind('mousedown', function(){
                        return false;
                    });
                }else{
                    $(this).attr('unselectable', 'on');
                }
            });
        }else{
            return this.each(function(){
                if(browser.msie || browser.safari){
                    $(this).unbind('selectstart');
                }else if(browser.mozilla){
                    $(this).css('MozUserSelect', 'inherit');
                }else if(browser.opera){
                    $(this).unbind('mousedown');
                }else{
                    $(this).removeAttr('unselectable', 'on');
                }
            });
        }
    };

    // function to get the selected rows of grid
    $.fn.getSelectedRows = function(){
        var json = { rows: [], datas: [] };
        this.each(function(){
            var trs = $(this).find('tr.selected'), g = this.grid;
            trs.each(function(){
                //json.rows.push(this);
                if(g){
                    var idx = $(this).attr('data'), data = g.data.rows[idx];
                    json.datas.push(data);
                }
            });
            for (var i in this.o.CheckedData){
                if(i!=this.o.page){
                    for (var j in this.o.CheckedData[i]){
                        json.datas.push(this.o.CheckedData[i][j])
                    }
                }
            }
        });
        return json;
    };

    // Returns the selected rows as an array, taken and adapted from http://stackoverflow.com/questions/11868404/flexigrid-get-selected-row-columns-values
    $.fn.selectedRows = function(o){
        var arReturn = [], arRow = [], selector = $(this.selector + ' .selected');
        $(selector).each(function(i, row){
            arRow = [];
            var idr = $(row).data('id');
            var trId = row.id;
            $.each(row.cells, function(c, cell){
                var col = cell.abbr;
                var val = cell.firstChild.innerHTML;
                if (val == '&nbsp;') val = '';      // Trim the content
                var idx = cell.cellIndex;

                arRow.push({
                    Column: col,        // Column identifier
                    Value: val,         // Column value
                    CellIndex: idx,     // Cell index
                    RowIdentifier: idr  // Identifier of this row element
                });
            });
            arRow.push({
                RowId: trId  // Identifier of this row element
            });
            arReturn.push(arRow);
        });
        return arReturn;
    };

    // 禁止文本选中
    $.fn.noSelect = function(flag){
        flag = (flag === undefined) ? true : flag;

        if(flag){
            return this.each(function(){
                if(browser.msie || browser.safari){
                    $(this).bind('selectstart', function(){
                        return false;
                    });
                }
                else if(browser.mozilla){
                    $(this).css('MozUserSelect', 'none');
                    $('body').trigger('focus');
                }
                else if(browser.opera){
                    $(this).bind('mousedown', function(){
                        return false;
                    });
                }
                else{
                    $(this).attr('unselectable', 'on');
                }
            });
        }else{
            return this.each(function(){
                if(browser.msie || browser.safari){
                    $(this).unbind('selectstart');
                }
                else if(browser.mozilla){
                    $(this).css('MozUserSelect', 'inherit');
                }
                else if(browser.opera){
                    $(this).unbind('mousedown');
                }
                else{
                    $(this).removeAttr('unselectable', 'on');
                }
            });
        }
    };

})(jQuery);