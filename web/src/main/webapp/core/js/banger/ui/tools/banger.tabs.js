
// 仿浏览器页卡对象原型链
Namespace.register('cards');

// 记录分组页卡
cards.groups = {};

// 新增页卡
cards.add = function(opts){
    var id = opts.id;
    // 判断页卡是否存在
    if(this.isExisted(id)){
        // 激活页卡
        this.click(opts);
    }else{
        // 判断打开的页卡数量是否小于最大限定数
        var max = this.max;
        if((max != null) && (typeof max == 'number') && (this.getTotal() >= max)){
            alert('The number of tabs currently open too much!');
            return false;
        }
        // 创建新页卡
        this.create(opts);
    }
};

// 判断页卡是否存在
cards.isExisted = function(id){
    return $('#' + id).length > 0;
};

// 获取打开的页卡数量
cards.getTotal = function(){
    return this.head.find('li').length;
};

// 获取当前页面所对应的页卡
cards.getSelf = function(win){
    if(win && win.frameElement){
        var iframe = win.frameElement, id = $(iframe).attr('for'), obj = $('#' + id, this.head);
        if(obj.length > 0){
            return obj;
        }
    }
    return null;
};

// 获取当前页面所对应的页卡id属性
cards.getSelfId = function(win){
    if(win){
        var obj = this.getSelf(win);
        if(obj){
            return obj.attr('id');
        }
    }
    return '';
};

// 获取当前页面所对应的页卡pid属性
cards.getSelfPid = function(win){
    if(win){
        var obj = this.getSelf(win);
        if(obj){
            return obj.attr('pid');
        }
    }
    return '';
};

// 获取当前处于激活状态的页卡
cards.getActive = function(){
    var obj = $('li.' + this.onStyle, this.head);
    if(obj.legnth > 0){
        return obj;
    }
    return null;
};

// 获取当前处于激活状态的页卡 id属性
cards.getActiveId = function(){
    var obj = this.getActive();
    if(obj){
        return obj.attr('id');
    }
    return '';
};

// 获取当前处于激活状态的页卡 pid属性
cards.getActivePid = function(){
    var obj = this.getActive();
    if(obj){
        return obj.attr('pid');
    }
    return '';
};

// 获取指定页卡body内的iframe框架
cards.getIframe = function(id){
    if(!id){
        return null;
    }
    var iframe = this.body.find('#' + id + '-iframe');
    if(iframe.length > 0){
        return iframe.get(0);
    }
    return null;
};

// 获取指定页卡body内的iframe框架的contentWindow对象
cards.getIframeWindow = function(id){
    if(!id){
        return null;
    }
    var iframe = this.getIframe(id);
    if(iframe){
        return iframe.contentWindow;
    }
    return null;
};

// 创建新页卡
cards.create = function(opts){
    var cards = this;

    // 创建页卡头
    var head = $(cards.getHtml('head', opts));
    cards.head.append(head);
    // 重置页卡头容器的宽度
    cards.setHeadWidth();
    // 页卡头click事件
    head.click(function(){
        // 激活页卡
        cards.click(opts.id, opts.reload);
    });
    // 关闭按钮click事件
    $('#' + opts.id + '-close').click(function(e){
        // 关闭页卡
        cards.close(opts.id, opts.hasCloseConfirm, false);
        // 阻止事件冒泡
        e.stopPropagation();
    });

    // 创建页卡身躯
    var body = $(cards.getHtml('body', opts));
    cards.body.append(body);
    // Iframe框架load事件
    $('#' + opts.id + '-iframe').load(function(){
        // 重设iframe框架高度
        cards.setIframeHeight(this);
        // 在IE6浏览器下，避免iframe出现纵向滚动条时页面右侧溢出
        if(isIe6 && this.contentWindow.ie6IframeScroll){
            this.contentWindow.ie6IframeScroll();
        }
        // 滚动条置顶
        this.contentWindow.scrollTo(0, 0);
    });

    // 创建页卡收藏
    var fav = $(cards.getHtml('fav', opts));
    cards.fav.append(fav);
    // 页卡收藏click事件
    fav.click(function(){
        // 激活页卡
        cards.click(opts.id, opts.reload);
        // 隐藏收藏夹
        cards.fav.parent().css({ 'display': 'none' });
    });
    // 在IE6 浏览器下，加载hover效果
    if(isIe6){
        fav.hover(function(){
            fav.addClass('hover');
        }, function(){
            fav.removeClass('hover');
        });
    }

    // 页卡创建完成后通过模拟单击进行激活
    $('#' + opts.id).click();
};

// 创建html标签
cards.getHtml = function(type, o){
    var html = [];
    switch(type){
        case 'head':
            var pid = '', name = '', lockClass = ' unlock', btnClose = '<b id="' + o.id + '-close" class="tabs-item-close"></b>', bool = o.lock
            if(o.pid){
                pid = ' pid="' + o.pid + '"';
            }
            if(o.name){
                name = ' name="' + o.name + '"';
            }
            if(bool){
                lockClass = ' lock';
                btnClose = '';
            }
            html.push('<li id="' + o.id + '"' + pid + name + ' class="tabs-item' + lockClass + '">');
            html.push('<h3 id="' + o.id + '-title" class="tabs-item-title" title="' + o.title + '">' + o.title + '</h3>');
            html.push(btnClose);
            html.push('</li>');
            break;
        case 'body':
            // 地址添加随机参数
            var url = this.modifyUrl(o.url, 'random', Math.random());
            html.push('<div id="' + o.id + '-body" class="tabs-body-child">');
            html.push('<iframe id="' + o.id + '-iframe" for="' + o.id + '" frameborder="0" src="' + url + '"></iframe>');
            html.push('</div>');
            break;
        case 'fav':
            html.push('<li id="' + o.id + '-fav" for="' + o.id + '"><h3 title="' + o.title + '">' + o.title + '</h3></li>');
            break;
    }
    return html.join('');
};

// 激活页卡
cards.click = function(){
    var args = arguments, len = args.length;
    if(len == 2){ // id、reload
        var id = args[0], reload = args[1], head = $('#' + id);
        if(head.length > 0){
            // 切换页卡头样式
            head.addClass(this.onStyle).siblings().removeClass(this.onStyle);

            if((head.position().left + this.head.position().left) < 0){
                this.move(3, 10, 20, id);
            }
            else if((this.head.parent().width() - (head.position().left + this.head.position().left)) < head.width()){
                this.move(4, 10, 20, id);
            }

            // 显示页卡躯体
            this.showBody(id, reload);
        }
    }else if(len == 1){ // options
        var opts = args[0], head = $('#' + opts.id);
        if(head.length > 0){
            if(head.attr('name') == opts.name){
                this.click(opts.id, opts.reload);
            }else{
                // 切换页卡头样式
                head.addClass(this.onStyle).siblings().removeClass(this.onStyle);
                // 更改页卡头属性
                head.attr('name', opts.name).find('h3').attr('title', opts.title).html(opts.title);
                // 更改收藏夹属性
                $('#' + opts.id + '-fav').find('h3').attr('title', opts.title).html(opts.title);

                if((head.position().left + this.head.position().left) < 0){
                    this.move(3, 10, 20, opts.id);
                }
                else if((this.head.parent().width() - (head.position().left + this.head.position().left)) < head.width()){
                    this.move(4, 10, 20, opts.id);
                }

                // 显示页卡躯体
                this.showBody(opts);
            }
        }
    }
};

// 显示页卡躯体
cards.showBody = function(){
    var args = arguments, len = args.length;
    if(len == 2){ // id、reload
        var id = args[0], reload = args[1], body = $('#' + id + '-body');
        if(body.length > 0){
            // 切换页卡躯体显隐
            body.css({ 'display': 'block' }).siblings().css({ 'display': 'none' });
            // 是否重载（刷新）
            if(reload){
                this.refresh(id);
            }
        }
    }else if(len == 1){ // options
        var opts = args[0], body = $('#' + opts.id + '-body');
        if(body.length > 0){
            // 切换页卡躯体显隐
            body.css({ 'display': 'block' }).siblings().css({ 'display': 'none' });
            this.refresh(opts.id, opts.url);
        }
    }
};

// 关闭页卡
cards.close = function(id, hasCloseConfirm, isRefreshParent){
    var head = $('#' + id);
    // 如页卡不存在或被锁定，则返回不处理
    if(head.length == 0 || head.hasClass('lock')){
        return false;
    }
    // 判断关闭页卡时是否需要进行确认对话
    if(hasCloseConfirm){
        var iframe = $('#' + id + '-iframe').get(0);
        if(iframe){
            var w = iframe.contentWindow, fn = w.confirmBeforeClose;
            if(fn && typeof fn == 'function'){
                var bool = fn();
                if(!bool){
                    // 激活页卡
                    this.click(id, false);
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    var body = $('#' + id + '-body'), fav = $('#' + id + '-fav');
    // 如该页卡处于激活状态，则第一步判断其是否存在父页卡，存在则激活父页卡；如父页卡不存在，则查找该页卡的
    // 前一项，存在则激活前一项；如父页卡及前一项都不存在，最后查找该页卡的后一项，存在则激活后一项
    if(head.hasClass(this.onStyle)){
        var oParent = $('#' + head.attr('pid')), oPrev = head.prev(), oNext = head.next();
        if(oParent.length > 0){
            this.click(oParent.attr('id'), isRefreshParent);
        }else if(oPrev.length > 0){
            this.click(oPrev.attr('id'), false);
        }else if(oNext.length > 0){
            this.click(oNext.attr('id'), false);
        }
    }
    // 移除页卡
    head.remove();
    body.remove();
    fav.remove();
    // 重置页卡头容器的宽度
    this.setHeadWidth();
    //对页卡部分做判断位移
    this.move(2, 10, 10);
};

// 关闭所有页卡
cards.closeAll = function(){
    // 指针
    var cards = this;
    // 遍历所有未锁定的页卡，触发其中关闭按钮的单击事件
    $('li.unlock', cards.head).each(function(){
        $(this).find('b').click();
    });
    // 若所有未锁定的页卡关闭后还存在锁定的页卡且都不处于激活状态，则激活剩余的第一个页卡
    if(cards.head.find('li').length > 0 && $('.' + this.onStyle, cards.head).length == 0){
        cards.click($('li:first', cards.head).attr('id'), false);
    }
    // 页卡头定位到最左侧
    cards.head.css('left', 0);
};

// 刷新页卡
cards.refresh = function(id, url){
    var iframe = $('#' + id + '-iframe');
    if(iframe.length > 0){
        url = url || iframe.attr('src'), rd = Math.random();
        // 变更随机参数，防止缓存
        url = this.modifyUrl(url, 'random', rd);
        // 重新设置src地址
        iframe.attr('src', url);
    }
};

// 设置页卡标题
cards.setTitle = function(id, text){
    var title = $('#' + id + '-title'), fav = $('#' + id + '-fav');
    if(title.length > 0){
        // 更改页卡头
        title.html(text).attr('title', text);
    }
    if(fav.length > 0){
        // 更改页卡收藏
        fav.find('h3').html(text).attr('title', text);
    }
};

// 设置页卡头容器的宽度
cards.setHeadWidth = function(){
    var oLis = this.head.find('li'), unit = oLis.eq(0).outerWidth(true), len = oLis.length;
    this.head.width(unit * len);
};

// 设置iframe框架的高度（其高度不能随内部文档高度自适应）
cards.setIframeHeight = function(iframe){
    try{
        // 根据 iframe框架内部文档的高度设置自身高度
        iframe.style.height = $(window).height() - 105 + 'px';
    }catch(e){  }
};

// 更改地址参数
cards.modifyUrl = function(url, key, value){
    var rgx = new RegExp('(\\\?|&)' + key + '=([^&]+)(&|$)', 'i'), match = url.match(rgx);
    if(value){
        if(match){
            return url.replace(rgx, function($0, $1, $2){
                return ($0.replace($2, value));
            });
        }else{
            if(url.indexOf('?') == -1){
                return (url + '?' + key + '=' + value);
            }else{
                return (url + '&' + key + '=' + value);
            }
        }
    }else{
        if(match){
            return match[2];
        }else{
            return '';
        }
    }
}

// 开始页卡头滑动动画
cards.move = function(n, interval, speed, id){
    var s = this;

    switch(n){
        case 0:
            clearInterval(s.timer);
            s.timer = setInterval(
                function(){
                    var sp = s.head.position().left;
                    if(s.head.position().left >= 0){
                        clearInterval(s.timer);
                    }
                    else{
                        if(Math.abs(sp) < speed){
                            s.head.css({ left: s.head.position().left + Math.abs(sp) });
                        }
                        else{
                            s.head.css({ left: s.head.position().left + speed });
                        }
                    }
                },
                interval);
            break;
        case 1:
            clearInterval(s.timer);
            s.timer = setInterval(
                function(){
                    var sp = (s.head.width() + s.head.position().left) - s.head.parent().width();
                    if((s.head.width() + s.head.position().left) <= s.head.parent().width()){
                        clearInterval(s.timer);
                    }
                    else{
                        if(sp < speed){
                            s.head.css({ left: s.head.position().left - Math.abs(sp) });
                        }
                        else{
                            s.head.css({ left: s.head.position().left - speed });
                        }
                    }
                },
                interval);
            break;
        case 2:
            clearInterval(s.timer);
            s.timer = setInterval(
                function(){
                    var sp = s.head.position().left;
                    if((s.head.position().left >= 0) || ((s.head.width() + s.head.position().left) >= s.head.parent().width())){
                        clearInterval(s.timer);
                    }
                    else{
                        if(Math.abs(sp) < speed){
                            s.head.css({ left: s.head.position().left + Math.abs(sp) });
                        }
                        else{
                            s.head.css({ left: s.head.position().left + speed });
                        }
                    }
                },
                interval);
            break;
        case 3:
            clearInterval(s.timer);
            s.timer = setInterval(
                function(){
                    var sp = $("#" + id).position().left + s.head.position().left;
                    if(sp >= 0){
                        clearInterval(s.timer);
                    }
                    else{
                        if(Math.abs(sp) < speed){
                            s.head.css({ left: s.head.position().left + Math.abs(sp) });
                        }
                        else{
                            s.head.css({ left: s.head.position().left + speed });
                        }
                    }
                },
                interval);
            break;
        case 4:
            clearInterval(s.timer);
            s.timer = setInterval(
                function(){
                    var sp = $("#" + id).width() - (s.head.parent().width() - ($("#" + id).position().left + s.head.position().left));
                    if((s.head.parent().width() - ($("#" + id).position().left + s.head.position().left)) >= $("#" + id).width()){
                        clearInterval(s.timer);
                    }
                    else{
                        if(sp < speed){
                            s.head.css({ left: s.head.position().left - Math.abs(sp) });
                        }
                        else{
                            s.head.css({ left: s.head.position().left - speed });
                        }
                    }
                },
                interval);
            break;
    }
};

// 加载页卡滑动、打开或关闭收藏夹以及关闭所有等事件处理
cards.addEventListener = function(){
    // 指针
    var cards = this;
    // 在IE6 浏览器下，加载hover效果
    if(isIe6){
        cards.head.on('mouseover.item', 'li', function(){
            $(this).addClass('hover');
        }).on('mouseout.item', 'li', function(){
                $(this).removeClass('hover');
            }).on('mouseover.close', 'b', function(){
                $(this).addClass('hover');
            }).on('mouseout.close', 'b', function(){
                $(this).removeClass('hover');
            });
    }
    // 向右滑动
    cards.btnScrollRight.mousedown(function(){
        // 开始页卡头滑动动画
        cards.move(0, 10, 20);
    }).mouseup(function(){
        // 停止页卡头滑动动画
        clearInterval(cards.timer);
    });
    // 向左滑动
    cards.btnScrollLeft.mousedown(function(){
        // 开始页卡头滑动动画
        cards.move(1, 10, 20);
    }).mouseup(function(){
        // 停止页卡头滑动动画
        clearInterval(cards.timer);
    });

    // 打开或隐藏收藏夹
    var time = null, fn = function(){
        cards.fav.parent().css({ 'display': 'none' });
    };
    cards.btnOpenFav.click(function(){ // 单击时，如已显示则隐藏，反之已隐藏则显示
        var style = cards.fav.parent().is(':visible') ? { 'display': 'none' } : { 'display': 'block' };
        cards.fav.parent().css(style);
    }).hover(function(){ // 鼠标移入按钮时清除隐藏收藏夹的时间戳
            clearTimeout(time);
        }, function(){ // 鼠标移出按钮时设置隐藏收藏夹的时间戳
            time = setTimeout(fn, 1000);
        });
    cards.fav.hover(function(){ // 鼠标移入收藏夹时清除隐藏收藏夹的时间戳
        clearTimeout(time);
    }, function(){
        time = setTimeout(fn, 1000); // 鼠标移出收藏夹时设置隐藏收藏夹的时间戳
    });
    // 关闭所有
    cards.btnCloseAll.click(function(){
        cards.closeAll();
    });
    /*
     // 窗口尺寸改变
     $(window).resize(function(){
     // 开始页卡头滑动动画
     cards.doAnimation(null, 'resize');
     });
     */
    return cards;
};

// 仿浏览器页卡构造函数
var Tabs = function(o){
    o = $.extend({
        max: null,
        headId: 'tabs-head',
        bodyId: 'tabs-body',
        favId: 'tabs-fav',
        btnScrollRightId: 'tabs-scroll-right',
        btnScrollLeftId: 'tabs-scroll-left',
        btnOpenFavId: 'tabs-open-fav',
        btnCloseAllId: 'tabs-close-all',
        onStyle: 'on'
    }, o);

    this.max = o.max;
    this.head = $('#' + o.headId);
    this.body = $('#' + o.bodyId);
    this.fav = $('#' + o.favId);
    this.btnScrollLeft = $('#' + o.btnScrollLeftId);
    this.btnScrollRight = $('#' + o.btnScrollRightId);
    this.btnOpenFav = $('#' + o.btnOpenFavId);
    this.btnCloseAll = $('#' + o.btnCloseAllId);
    this.onStyle = o.onStyle;
    this.addEventListener();
};

// 继承
Tabs.prototype = cards;
