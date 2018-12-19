/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-12-30
 * Description: banger.biz.js
 * Modified by: 
 * Modified contents: 
**/
if(typeof banger == 'undefined' || typeof banger.dialog == 'undefined'){
    document.write('<script type="text/javascript" src="../core/js/banger/ui/tools/banger.dialog.js"><\/script>');
}

_writeCookieHours = function(key, value, hours){
	var expires = '';
	if(hours){
		var date = new Date();
		date.setTime(date.getTime() + (hours * 60 * 60 * 1000));
		expires = '; expires=' + date.toGMTString();
	}
	document.cookie = key + '=' + escape(value) + expires+";path=/";
};

var _requestUrl = window.location.href;

if (_requestUrl && _requestUrl.indexOf("?") != -1) {   
   var _paramstrs = _requestUrl.substr(1);   
   var _paramparts = _paramstrs.split("&");   
   for(var i = 0; i < _paramparts.length; i ++) {
	  if(_paramparts[i].split("=")[0]=="token"){
	     var _token = _paramparts[i].split("=")[1];
	     if(_token){
	    	 _writeCookieHours("token",_token,1);
	     }
	  }
   }
};

// 获取本地当前日期和星期
var getDateAndWeek = function(){
    var now = new Date();
    var year = now.getFullYear(), month = now.getMonth() + 1, date = now.getDate(), day = now.getDay();
    return year + '年' + month + '月' + date + '日  星期' + '日一二三四五六'.charAt(day);
};

// 产生一个Guid标识符
var getGuidChars = function(){
    var str = '';
    for(var i = 1; i <= 32; i++){
        var n = Math.floor(Math.random() * 16.0).toString(16);
        str += n;
        if((i == 8) || (i == 12) || (i == 16) || (i == 20)){
            str += '-';
        }
    }
    return str;
};

// 在指定范围里获得一个随机数
var getRandomByRange = function(min, max){
    return Math .floor(Math.random() * (max - min + 1)) + min;
};

// 生成一个指定长度且由字母数字组成的随机字符串
var getRandomAlphaNum = function(len){
    var str = '';
    for(; str.length < len; str += Math.random().toString(36).substr(2)){}
    return  str.substr(0, len);
}

// 获取url服务器地址
var getUrlServer = function(url){
    if(!url){
        url = document.location.href;
    }
    var n = url.indexOf('http://'), m = url.indexOf('/', 7);
    if(n != -1 && m != -1){
        return url.substr(7, m - 7);
    }
};
// 获取url参数集合
var getUrlParams = function(url){
    if(!url){
        url = document.location.href;
    }
    var s = url.indexOf('?'), str = '';
    if(s >- 1){
        str = url.substr(s + 1, url.length - s - 1);
    }
    var parts = str.split('&'), params = {};
    for(var i = 0, l = parts.length; i < l; i++){
        var part = parts[i], ps = part.split('='), key = ps[0], value = ps[1];
        params[key] = value;
    }
    return params;
};
// 解码地址参数（中文会encodeURI两次，所以对url参数进行解码）
var decodeUrlParams = function(params){
    if(!params){
        return null;
    }
    for(var key in params){
        if(params[key]){
            params[key] = decodeURI(params[key]);
        }
    }
    return params;
};

// 判断表单是否被修改
var formIsChanged = function(selector){
    if(!selector){
        return false;
    }
    var form = $(selector).eq(0), elem = null, isChanged = false;
    cycle: for(var i = 0, len = form.elements.length; i < len; i++){
        elem = form[i];
        switch(elem.type){
            case 'text':
            case 'password':
            case 'textarea':
            case 'file':
            case 'hidden':
                if(elem.defaultValue != elem.value){
                    isChanged = true;
                    break cycle;
                }
                break;
            case 'checkbox':
            case 'radio':
                if (elem.defaultChecked != elem.checked){
                    isChanged = true;
                    break cycle;
                }
                break;
            case 'select-one':
            case 'select-multiple':
                for(var n = 0, oLength = elem.options.length; n < oLength; n++){
                    if(elem.options[n].defaultSelected != elem.options[n].selected){
                        isChanged = true;
                        break cycle;
                    }
                }
                break;
        }
    }
    return isChanged;
};

// 下载
var download = function(url){
    if(!url){
        return false;
    }
    var isExisted = $('#downIframe').length > 0;
    if(!isExisted){
        $('body').append('<iframe id="downIframe" style="display:none; height:0; border:0;"></iframe>');
    }
    url = modifyUrl(url, 'random', Math.random());
    $('#downIframe').attr('src', url);
};

// 禁用按钮
var disableButtons = function(selector){
    $(selector).addClass('ui-button-disabled').attr('disabled', true);
};

// 启用按钮
var enableButtons = function(selector){
    $(selector).removeClass('ui-button-disabled').removeAttr('disabled');
};

// 判断当前提交的字段是否在允许提交的字段集合中
var checkPostFields = function(field, filter){
    if(field){
        if(filter){
            var ns = [];
            // 字符串
            if(isString(filter)){
                ns = filter.split(',');
            }
            // 数组
            else if(isArray(filter)){
                ns = filter;
            }
            for(var i = 0, l = ns.length; i < l; i++){
                if(field.indexOf(ns[i]) > -1){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    return false;
};

// 获取提交数据 edited by 林贤聪 封装了pushField方法，封装了input情况
var getPostFields = function(filter){
    var fields = {};
    // 单行文本框
    $('input').each(function(){
        if($(this).is(':checkbox') || $(this).is(':radio')) {
            if($(this).is(':checked')) {
                pushField($(this));
            } else {
                pushFieldNull($(this));
            }
        } else {
            pushInput($(this));
        }
    }); 
    // 多行文本框
    $('textarea').each(function(){
        pushField($(this));
    });
    // 下拉单选框
    $('select').each(function(){
        pushField($(this));
    });
    function pushField($ele) {
        var field = $ele.attr('name');
        if(checkPostFields(field, filter)){
            fields[field] = $.trim($ele.val());
        }
    }
    function pushFieldNull($ele) {
        var field = $ele.attr('name');
        if(checkPostFields(field, filter)){
            fields[field] = '';
        }
    }
    function pushInput($ele) {
        var field = $ele.attr('name');
        if(checkPostFields(field, filter)){
            fields[field] = $.trim($ele.val());//fields[field] = charReplaceBiz($.trim($ele.val()));
        }
    }
    return fields;
};

// 获取提交数据 查找Dom对像内的字段值
var findPostFields = function(co){
    var fields = {};
    // 单行文本框
    co.find('input').each(function(){
        if($(this).is(':checkbox') || $(this).is(':radio')) {
            if($(this).is(':checked')) {
                pushField($(this));
            } else {
                pushFieldNull($(this))
            }
        } else {
            pushInput($(this));
        }
    });
    // 多行文本框
    co.find('textarea').each(function(){
        pushInput($(this));
    });
    // 下拉单选框
    co.find('select').each(function(){
        pushField($(this));
    });
    function pushField($ele) {
        var field = $ele.attr('name');
        fields[field] = $.trim($ele.val());
    }
    function pushFieldNull($ele) {
        var field = $ele.attr('name');
        fields[field] = '';
    }
    function pushInput($ele) {
        var field = $ele.attr('name');
        fields[field] = $.trim($ele.val());//fields[field] = charReplaceBiz($.trim($ele.val()));
    }
    return fields;
};


// 获取提交数据 edited by 林贤聪 封装了pushField方法，封装了input情况
var getPostFieldsByFilter = function(filter){
    var fields = {};
    // 单行文本框
    $(filter).find('input').each(function(){
        if($(this).is(':checkbox') || $(this).is(':radio')) {
            if($(this).is(':checked')) {
                pushField($(this));
            } else {
                pushFieldNull($(this))
            }
        } else {
            pushInput($(this));
        }
    });
    // 多行文本框
    $(filter).find('textarea').each(function(){
        pushField($(this));
    });
    // 下拉单选框
    $(filter).find('select').each(function(){
        pushField($(this));
    });
    function pushField($ele) {
        var field = $ele.attr('name');
        fields[field] = $.trim($ele.val());
    }
    function pushFieldNull($ele) {
        var field = $ele.attr('name');
        fields[field] = '';
    }
    function pushInput($ele) {
        var field = $ele.attr('name');
        fields[field] = $.trim($ele.val());//fields[field] = charReplaceBiz($.trim($ele.val()));
    }
    return fields;
};

// 获取提交数据 edited by 林贤聪 封装了pushField方法，封装了input情况
var getPostFieldsForTemplate = function(filter){
    var fields = {};
    $(filter).find('.template:visible').each(function () {
        var id = $(this).attr('id');
        var form = [];
        $(this).find('table').each(function (index) {
            form[index] = getPostFieldsByFilter(this);
        })
        fields[id] = form;

    })
    return fields;
};

// 特殊字符集
var charsetsBiz = {
    '`': '&#96;', '~': '&#126;', '!': '&#33;', '%': '&#37;', '^': '&#94;', '*': '&#42;',
    '(': '&#40;', ')': '&#41;', '=': '&#61;', '+': '&#43;', '\\': '&#92;', '[': '&#91;', ']': '&#93;',
    '{': '&#123;', '}': '&#125;', ';': '&#59;', '\'': '&#39;','\"': '&#34;', '<': '&#60;', '>': '&#62;',
    '/': '&#47;', '?': '&#63;'
};
// 替换特殊字符
var charReplaceBiz = function(value){
    var chars = value.split(''), i = 0, len = chars.length;
    for(; i < len; i++){
        var o = this.charsetsBiz[chars[i]];
        if(o){
            chars[i] = o;
        }
    }
    return chars.join('');
};

// 查看客户
var viewCustomer = function(o){
    // 以模态对话框方式查看客户时的默认参数
    var n1 = { id: 'viewCustomerByDialog', title: '查看客户', url: '', width: 900, height: 500, cancel: function(){} };
    // 以页卡方式查看客户时的默认参数
    var n2 = { id: 'viewCustomerByTab', pid: '', name:'viewCustomerByTab' + Math.random(), title: '查看客户', url: '' };
    // String
    if(isString(o)){
        o = modifyUrl(o, 'random', Math.random());
        n1['url'] = o;
        if(typeof showDialog == 'function'){
            showDialog(n1);
        }
    }
    // Object
    else if(isPlainObject(o)){
        // 以页卡方式查看
        if(o['type'] == 'tab'){
            for(var i in o){
                if(i == 'type'){
                    continue;
                }
                n2[i] = o[i];
            }
            if(typeof tabs == 'object'){
                tabs.add(n2);
            }
        }
        // 以模态对话框方式查看
        else if(o['type'] == 'dialog'){
            for(var i in o){
                if(i == 'type'){
                    continue;
                }
                n1[i] = o[i];
            }
            if(typeof showDialog == 'function'){
                showDialog(n1);
            }
        }
    }
};

// 拨号
var dail = function(number, customerId){
    number = $.trim(number);
    if(number){
        // 获取顶级窗口对象的拨号函数
        var fn = window.top.dial;
        if(isFunction(fn)){
            fn(number, customerId);
        }
    }
};

// 座谈
var talk = function(customerId){
    if(customerId){
        // 获取顶级窗口对象的座谈函数
        var fn = window.top.talk;
        if(isFunction(fn)){
            fn(customerId);
        }
    }
};

// 播放音频&音频播放器
var audioPlayer = function(title, file){
    mediaPlayer('audio', { title: title, file: file });
};

// 播放视频&视频播放器
var videoPlayer = function(title, file){
    mediaPlayer('video', { title: title, file: file });
};

// 初始化多行文本框可输入字符长度实时提示
function initMaxlengthTips(){
	var oArea = $(arguments[0]), oTips = $(arguments[1]), max = parseInt(oArea.attr('maxlength'), 10);
	
	function init(){
		var length = oArea.val().length;
		if(length <= max){
			oTips.html('您还可输入<label>' + (max - length) + '</label>字');
		}else{
			oTips.html('您已超出<label class="cf00">' + (length - max) + '</label>字');
		}
	}
	
	init();
	
	oArea.off('keyup.maxlength paste.maxlength').on('keydown.maxlength paste.maxlength', function(){
		setTimeout(function(){
			init();
		}, 50);
	});
}

// 加载侧栏菜单
Namespace.register('sideListMenu');
// 储存菜单数据
sideListMenu.data = null;
// 加载
sideListMenu.init = function(o){
    o = $.extend({}, {
        type: 'POST',
        url: null,
        cache: false,
        dataType: 'json',
        defaultIndex: 0,
        onListClick: null
    }, o);

    $.ajax({
        type: o.type,
        url: o.url,
        cache: o.cache,
        dataType: o.dataType,
        success: function(data){
            if(isArray(data)){
                // 储存菜单数据
                sideListMenu.data = data;

                // 添加HTML
                var list = [];
                for(var i = 0, len = data.length; i < len; i++){
                    var node = data[i], cls = o.defaultIndex == i ? 'on' : '';
                    list.push('<li class="' + cls + '"><span>' + node.display + '</span></li>');
                }
                $(o.id).html(list.join(''));

                // 注册事件
                $(o.id).off('.list').on('mouseover.list', 'li', function(){
                    $(this).addClass('hover');
                }).on('mouseout.list', 'li', function(){
                    $(this).removeClass('hover');
                }).on('click.list', 'li', function(){
                    $(this).addClass('on').siblings().removeClass('on');
                    // 触发自定义事件
                    if(isFunction(o.onListClick)){
                        var i = $(o.id).find('li').index(this);
                        o.onListClick(sideListMenu.data[i]);
                    }
                });
            }
        }
    });
};

//生产
var serverMapIp = "http://125.0.167.120:9999";
var appMapIp = "http://192.168.168.120:9999";

//测试
// var serverMapIp = "http://125.0.182.162:9999";
//var appMapIp = "http://192.168.168.182:9999";
// var appMapIp = "http://125.0.182.162:9999";



