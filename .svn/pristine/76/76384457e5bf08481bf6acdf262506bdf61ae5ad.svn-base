// 获取当前window对象及其顶级window对象
var w = window, wt = w.top;

// 注册菜单命名空间
Namespace.register('banger.menu');
// 存储菜单数据
banger.menu.nodes = null;
// 加载菜单
banger.menu.init = function(json){
	// 存储菜单数据
	this.nodes = json;
	// 创建菜单html标签
	var html = [];
	$.each(json, function(i, main){
        var cls = i == 0 ? 'on' : '';
		if(main){
            html.push('<dt id="main' + main.id + '" class="' + cls + '">');
			html.push('<i>&nbsp;</i><span>' + main.display + '</span>');
			html.push('</dt>');
            html.push('<dd class="' + cls + '">');
			html.push('<ul>');
			if(main.sub){
				$.each(main.sub, function(j, sub){                   
                     html.push('<li id="sub' + sub.id + '" i="' + i + '" j="' + j + '" onclick="banger.menu.click(this);"><span>' + sub.display + '</span></li>');
				});
			}
			html.push('</ul>');
			html.push('</dd>');
		}
	});
	// 填充菜单
	$('#menu').append(html.join(''));
};
// 菜单点击事件
banger.menu.click = function(li){
    if(this.nodes){
        var obj = $(li), i = obj.attr('i'), j = obj.attr('j'), opts = this.nodes[i].sub[j];
        if(opts){
            tabs.add(opts);
        }
    }
};
//菜单重新加载
banger.menu.reload = function(){
    var that = this;
    var urlGetMenu = '../desktop/getSysMenuJson.html';
    var url = modifyUrl(urlGetMenu, 'random', Math.random());
    $.get(url,function(data) {
        // 存储菜单数据
        that.nodes = data;
        var json = data;
    //1.记住重新加载前的视图状态
        //展开菜单的id
        var openMenuId = $('#menu dt[class~="on"]').attr('id');
        openMenuId = openMenuId ? openMenuId.substr(4) : null;
        //选中的子菜单id
        var openSubMenuId = $('#menu li[class~="on"]').attr('id');
        openSubMenuId = openSubMenuId ? openSubMenuId.substr(3) : null;
    //2.将模型数据填充进去，边填充边恢复视图状态
        var $mainMenu = $('<dt><i>&nbsp;</i><span></span></dt>');
        var $subMenuBackbone = $('<dd><ul></ul></dd>');
        var $subMenu = $('<li onclick="banger.menu.click(this);"><span></span></li>');
        $('#menu').empty();
        for(var mainMenuIndex in json) {
            var mainMenu = json[mainMenuIndex];
            var $tmpMainMenu = $mainMenu.clone();
            $tmpMainMenu.attr('id', 'main'+mainMenu.id);
            $tmpMainMenu.find('span').text(mainMenu.display);
            var $tmpSubMenuBackbone = $subMenuBackbone.clone();            
            if(mainMenu.id == openMenuId) {
                $tmpMainMenu.addClass('on');
                $tmpSubMenuBackbone.addClass('on');
            }
            if(mainMenu.sub) {
                var subMenus = mainMenu.sub;
                for(var subMenuIndex in subMenus) {
                    var $tmpSubMenu = $subMenu.clone();
                    var subMenu = subMenus[subMenuIndex];

                    $tmpSubMenu.attr('i', mainMenuIndex);
                    $tmpSubMenu.attr('j', subMenuIndex);
                    $tmpSubMenu.attr('id', 'sub'+subMenu.id);
                    
                    $tmpSubMenu.find('span').text(subMenu.display);
                    if(subMenu.id == openSubMenuId) {
                        $tmpSubMenu.addClass('on');
                    }
                    $tmpSubMenuBackbone.find('ul').append($tmpSubMenu);
                }
            }
            $tmpMainMenu.after($tmpSubMenuBackbone);
            $('#menu').append($tmpMainMenu);
        }        
    }, 'json');
}

// 仿浏览器页卡对象
this.tabs = new Tabs();

var betweenSec = 0;
function openSceenWin(){
    var today = new Date();
    var nowHour = today.getHours();
    var nowMinute = today.getMinutes();
    var nowSecond = today.getSeconds();
    var mysec = (nowHour*3600)+(nowMinute*60)+nowSecond;

    if (mysec -  betweenSec > 3) {
        var account = $('#hidUserAccount').val();
        sp.openScreenWin("&autoLogin=true&account="+encodeURI(encodeURI(account)),function(args){
        });
        betweenSec = mysec;
    } else {
        return false;
    }
}

// 页面加载完成时
$(function(){
	// 加载菜单
	banger.menu.init(menuJson);

    $('#body-side')
    // 侧边栏展开与收缩
    .on('click', 'b', function(){
        var width = $('#body-side').outerWidth(), cls = 'menu-toggle-on';
        if($(this).hasClass(cls)){
            $(this).removeClass(cls);
            $('#body-side').css('left', 0);
            $('#body-main').css('margin-left', width + 10);
        }else{
            $(this).addClass(cls);
            $('#body-side').css('left', -width);
            $('#body-main').css('margin-left', 0);
        }
    })
    // 主菜单展开与收缩
    .on('click', 'dt', function(){
        var dt = $(this), dd = dt.next(), cls = 'on';
        if(dd.is(':visible')){
            dt.removeClass(cls);
            dd.removeClass(cls);
        }else{
            dt.addClass(cls).siblings('dt').removeClass(cls);
            dd.addClass(cls).siblings('dd').removeClass(cls);
        }
    })
    // 子菜单点击变色
    .on('click', 'li', function(){
        var cls = 'on';
        $('#menu li').removeClass(cls);
        $(this).addClass(cls);
    });

    // 自动创建工作台页卡
    tabs.add({ id: 'desk', name:'desk', title: '工作台', url: workBenchUrl, lock: true });

    // 设置相关区高度
    var fixPanelSize = function(){
        var height = $(w).height();
        // 设置菜单区高度
        $('#menu-body').height(height - 95);
        // 设置页卡body区高度
        $('#tabs-body').height(height - 105).find('iframe').height(height - 105);
    };
    fixPanelSize();
    $(w).resize(function(){
        fixPanelSize();
    });
    
	$("#call").click(function(){
    	var telNum = $("#telNum").val();
		dial(telNum);
    });
    
    $("#hup").click(function(){
    	hangup();
    });

    // 退出
	$('#btnLogout').click(function(){
		isDoLogout();
	});
    
    
    //role
	var ur = $('ul.ul-role')
	$('#role').click(function(){
		$('#rolelist').show();
	}).hover(
		function(){
			if($('li', ur).length == 0) return false;
			$(this).addClass('hover');
			$('#rolelist').show();
			ur.show().css({
				'top': $(this).offset().top - 9,
				'left': $(this).offset().left
			});
		},
		function(){
			$(this).removeClass('hover');
			ur.hide();
		}
	);

    kickTimerHandler = setInterval(kickAccount,2000);       //踢人

});

function downRes() {
    var url = '../desktop/getResourcesPage.html';
    tabs.add({
        id: 'getResourcesPage',
        name: 'returnResourcesPage',
        pid: tabs.getSelfId(window),
        title : '资源下载',
        url : url,
        lock : false
    });
}

function isDoLogout(){
    $.ajax({
        type: 'POST',
        async:false,
        data: { userId: $('#userId').val()},
        url: '../login/doBackLogout.html',
        success: function(result){  
        	clearAllCookie();
            window.location.href = '../login/getBackLoginPage.html?exitflag=yes';
        }
    });
}

function clearAllCookie(){
    var expires = '';
    var date = new Date();
    expires = '; expires=' + date.toGMTString();
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if(keys){
        for(var i = keys.length; i--;){
        	document.cookie = keys[i] + '=null'+ expires+";path=/";
        }
    }
}

function weChatMagnify(e){
    evt = e|| window.event;
    var pointX = evt.pageX;//这里可以得到鼠标Y坐标
    var pointY=evt.pageY;
    if(evt.pageX){
        pointX=evt.pageX;
        pointY=evt.pageY;
    } else {
        pointX=evt.clientX+document.body.scrollLeft -document.body.clientLeft;
        pointY=evt.clientY+document.body.scrollTop-document.body.clientTop;
    }
    $("#floatWeChatDiv").css('left',pointX-290);
    $("#floatWeChatDiv").css('top',pointY+30);
    $("#floatWeChatDiv").show();
}

function weChatHidden(){
   $("#floatWeChatDiv").hide();
}

var outting = false;
var kickTimerHandler = -1;			//踢人计时器的句柄

function kickAccount(){				//同一个浏览器，帐号踢人
    var account = readCookie("account");
    var loginAccount = $("#loginAccount").val()+"";
    if(account){
    	var b = new Base64();
        var e = b.encode(loginAccount.toUpperCase());
        loginAccount = e;
        if(account!=loginAccount && !outting){
            outting = true;
            window.location.href = '../login/getBackLoginPage.html?exitflag=yes';
        }
    }
}

function readCookie(name){
    var cookieValue = "";
    var search = name + "=";
    if(document.cookie.length > 0)
    {
        offset = document.cookie.indexOf(search);
        if(offset != -1)
        {
            offset += search.length;
            end = document.cookie.indexOf(";",offset);
            if(end == -1) end = document.cookie.length;
            cookieValue = unescape(document.cookie.substring(offset,end));
        }
    }
    return cookieValue;
}
