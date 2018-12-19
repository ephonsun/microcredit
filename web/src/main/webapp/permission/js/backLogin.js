
Namespace.register('banger.login');

banger = {
	login: {
		submit: function(){
			
		}
	}
}



// 提交
banger.login.submit = function(){
	var v1 = jQuery.trim(jQuery('#txtAccount').val());
	var	v2 = jQuery.trim(jQuery('#txtPassword').val());
	var	v3 = jQuery.trim(jQuery('#randNum').val());
	var	v4 = jQuery.trim(jQuery('#needRandNum').val());
	var bool = this.isEmpty(v1, v2, v3, v4);
	v2 = $.md5(v2);
	if(bool){
		this.ajaxCheck({ account: v1, password: v2 , checkCode: v3, needRandNum: v4});
	}
};

// 判断用户名和密码是否为空
banger.login.isEmpty = function(args1, args2, args3, args4){
	
	// 判断用户名是否为空
	if(args1 == ''){
		// 输出错误提示
		jQuery('#lblError').html('用户名不能为空！');
		return false;
	}else{
		// 清空错误提示
		jQuery('#lblError').html('');
	}
	
	// 判断密码是否为空
	if(args2 == ''){
		// 输出错误提示
		jQuery('#lblError').html('密码不能为空！');
		return false;
	}else{
		// 清空错误提示
		jQuery('#lblError').html('');
	}

	// 判断密码是否为空
	if(args4 == 'needRandNum' && args3 ==''){
		// 输出错误提示
		jQuery('#lblError').html('验证码不能为空！');
		return false;
	}else{
		// 清空错误提示
		jQuery('#lblError').html('');
	}
	return true;
};

// 异步校验登录情况
banger.login.ajaxCheck = function(data){
	var _this = this;
	jQuery.ajax({
		type: 'POST',
		url: '../login/doBackLogin.html',
		data: data,
		dataType: 'text',
		success: function(result){
			_this.go(result);
		}
	});
};

// 根据返回结果处理不同业务逻辑
banger.login.go = function(result){
	var _this = this;
	
	var localeResult = "";
	var token = "";
	if(isString(result)){
		if(result.charAt(0)=='{'){
			var resultJson = jQuery.parseJSON(result);
			localeResult = resultJson.result.toLocaleLowerCase();
			token = resultJson.token;
		}else{
			localeResult = result.toLocaleLowerCase();
		}
	}
	
	switch(localeResult){
		case 'passwordreset':
			jQuery('#lblError').html(''); // 清空失败提示
			_this.saveLogged();
			_this.saveToken(token);
			window.location.href = '../desktop/getMainPage.html';
			break;
		case 'success':
			jQuery('#lblError').html(''); // 清空失败提示
			_this.saveLogged();
			_this.saveToken(token);
			window.location.href = '../desktop/getMainPage.html';
			break;
		case 'timeout':
			jQuery('#lblError').html(''); // 清空失败提示
			_this.saveLogged();
			window.location.href = '../login/getBackLoginPage.html';
			break;
		case 'firstlogin':
			jQuery('#lblError').html(''); // 清空失败提示
			_this.saveLogged();
			_this.saveToken(token);
			window.location.href = '../login/getModifyDefaultPws.html';
			break;
		case 'needrandnum':
			jQuery('#needRandNum').val(result);
			jQuery('#lblError').html("用户名不存在或密码错误！"); // 输出失败提示
			var obj = document.getElementById('check');
			$("#checkCode").show();
			changeValidateCode(obj);
			break;
		default: 
			if(result.indexOf("请输入验证码")>-1){
				jQuery('#needRandNum').val('needrandnum');
				jQuery('#lblError').html("请输入验证码！"); // 输出失败提示
				var obj = document.getElementById('check');
				$("#checkCode").show();
				changeValidateCode(obj);
			}else{
				jQuery('#lblError').html(result); // 输出失败提示
			}
			break;
	}
};

//保存已登录的用户，用于踢人
banger.login.saveLogged = function(){
	var account = jQuery('#txtAccount').val();
    var b = new Base64();
    var e = b.encode(account.toUpperCase());
	account = e;
	this.writeCookieHours('account', account, 24);
};

//保存已登录的token，用于踢人
banger.login.saveToken = function(token){
	if(token){
		this.writeCookieHours('token', token, 1);
	}
};

// 写入cookie
banger.login.writeCookieDays = function(key, value, days){
	var expires = '';
	if(days){
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		expires = '; expires=' + date.toGMTString();
	}
	document.cookie = key + '=' + escape(value) + expires+";path=/";
};

//写入cookie
banger.login.writeCookieHours = function(key, value, hours){
	var expires = '';
	if(hours){
		var date = new Date();
		date.setTime(date.getTime() + (hours * 60 * 60 * 1000));
		expires = '; expires=' + date.toGMTString();
	}
	document.cookie = key + '=' + escape(value) + expires+";path=/";
};

// 页面加载完成时
jQuery(function(){
	// 登录
	jQuery('#btnLogin').click(function(){
		banger.login.submit();
	});
	// 用户名和密码文本框Enter键按下
	jQuery('#txtAccount, #txtPassword').keydown(function(e){
		if(e.keyCode == 13){
			banger.login.submit();
		}
	});
	// 密码不允许输入空格
	$('#txtPassword').keydown(function(e){
		/*var code = e.keyCode;
		if(code == 32){
			return false;
		}*/
	});
});
