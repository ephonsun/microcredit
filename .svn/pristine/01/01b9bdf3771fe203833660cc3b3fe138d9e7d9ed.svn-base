function checkstr(str){
	if(str >= 48 && str <= 57){ //数字
		return 1;
	}else if(str >= 65 && str <= 90){ //大写字母
		return 2;
	}else if(str >= 97 && str <= 122){ //小写字母
		return 3;
	}else{ //特殊字符
		return 4;
	};
};

function checkl(string){

	var n = false, s = false, t = false, score = 0;

	if (string.length < 6){
		score = 1;
	}else{
		for(i = 0; i < string.length; i++){

			var asc = checkstr(string.charCodeAt(i));

			if(asc == 1 && n == false){
				score += 1; n = true;
			}else if((asc == 2 || asc == 3) && s == false){
				score += 1; s = true;
			}else if(asc == 4 && t == false){
				score += 1; t = true;
			}
		};
	};
	return score;
};

function checklevel(psw){
	var color = '#ededed', color_l = '#ff0000', color_m = '#ff9900', color_h = '#33cc00';

	var lcor = null, mcor = null, hcor = null;

	if(psw == null || psw == ''){
		lcor = color;
		mcor = color;
		hcor = color;
	}else{
		var thelev = checkl(psw);
		switch(thelev){
			case 1:
				lcor = color_l;
				hcor = mcor = color;
				break;
			case 2:
				lcor = mcor = color_m;
				hcor = color;
				break;
			case 3:
				lcor = mcor = hcor = color_h;
				break;
			default:
				lcor = mcor = hcor = color;
				break;
		}
		$('#passwordStr').val(thelev);
	};

	$('#strengthWeak').css('background-color', lcor);
	$('#strengthMedium').css('background-color', mcor);
	$('#strengthForced').css('background-color', hcor);
};

//密码确认
function confirmPwd(){
	//验证是否通过
	var pwdNew = $.trim($('#txtNewPassword').val()),
		pwdConfirm = $.trim($('#txtConfirmPassword').val()),
		confirmBool = true;

	if(!pwdNew){
		$('#txtNewPassword').parent().addClass('ui-verify-failed');
		$('#warningNewPwd').text('新密码不能为空！');
		return false;
	}else{
		if(pwdNew.length < 6){
			$('#txtNewPassword').parent().addClass('ui-verify-failed');
			$('#warningNewPwd').text('新密码长度不能小于6位！');
			return false;
		}else if(pwdNew.match(/[\u4e00-\u9fa5]/)){
			$('#txtNewPassword').parent().addClass('ui-verify-failed');
			$('#warningNewPwd').text('新密码不允许全角字符与汉字！');
			return false;
		}else{
			$('#txtNewPassword').parent().removeClass('ui-verify-failed');
			$('#warningNewPwd').text('6～16个字符，区分大小写！');
			confirmBool = true;
		};
	};

	if(!pwdConfirm){
		$('#txtConfirmPassword').parent().addClass('ui-verify-failed');
		$('#warningConfirmPwd').text('确认密码不能为空！');
		return false;
	}else{
		if(pwdNew != pwdConfirm){
			$('#txtConfirmPassword').parent().addClass('ui-verify-failed');
			$('#warningConfirmPwd').text('两次输入的新密码不一致，请重新输入！');
			return false;
		}else{
			confirmBool = true;
		};
	};

	if(confirmBool == true && verifyBool == true){
		var b = new Base64();
        var oldPassword = $('input[name="oldPassword"]').val();
        var password = $('input[name="password"]').val();
        var passwordStr = $('input[name="passwordStr"]').val();
        var postJson = {};
        postJson.oldPassword = $.md5(oldPassword);
        postJson.password = $.md5(password);
        postJson.passwordStr = passwordStr;
		jQuery.ajax({
			type: 'POST',
			url: '../user/changeUserPassword.html',
			data: postJson,
			success:function(text){
				if(text && text == 'errorPasswd'){
					$('#txtOldPassword').parent().addClass('ui-verify-failed');
					$('#warningOldPwd').text('原始密码错误,请重新输入！');
				}else{
					showConfirm({
						icon: 'succeed',
						content: '密码修改成功'
					});
					//loadLoginPage();
                    gotoMainPage();
				}
			}
		});
	}
}

var verifyBool = true;		//密码确认前校验
var confirmBool = true;		//密码确认后校验
$(function(){
	//当前密码
	$('#txtOldPassword').keydown(function(e){
		/*var code = e.keyCode;
		 if(code == 32){
		 return false;
		 }*/
	}).on('blur', function(){
		var pwd = $.trim(this.value);
		if(!pwd){
			$(this).parent().addClass('ui-verify-failed');
			$('#warningOldPwd').text('当前密码必须填写！');
			verifyBool = false;
		}else{
			$(this).parent().removeClass('ui-verify-failed');
			$('#warningOldPwd').text('');
			verifyBool = true;
		};
	});

	//新密码
	$('#txtNewPassword').keydown(function(e){
		/*var code = e.keyCode;
		 if(code == 32){
		 return false;
		 }*/
	}).on('blur', function(){
		var pwd = $.trim(this.value);
		if(!pwd){
			$(this).parent().addClass('ui-verify-failed');
			$('#warningNewPwd').text('新密码不能为空！');
			verifyBool = false;
		}else{
			$(this).parent().removeClass('ui-verify-failed');
			$('#warningNewPwd').text('6～16个字符，区分大小写！');
			if(pwd.length < 6){
				$(this).parent().addClass('ui-verify-failed');
				$('#warningNewPwd').text('新密码长度不能小于6位！');
				verifyBool = false;
			}else if(pwd.match(/[^\x00-\xff]/ig)){
				$('#txtNewPassword').parent().addClass('ui-verify-failed');
				$('#warningNewPwd').text('新密码不允许全角字符与汉字！');
				return false;
			}else{
				$(this).parent().removeClass('ui-verify-failed');
				$('#warningNewPwd').text('6～16个字符，区分大小写！');
				verifyBool = true;
			};
		};
	});

	//确认密码
	$('#txtConfirmPassword').keydown(function(e){
		/*var code = e.keyCode;
		 if(code == 32){
		 return false;
		 }*/
	}).on('blur', function(){
		var pwd = $.trim(this.value);
		var newPwd = $.trim($('#txtNewPassword').val());
		if(!pwd){
			$(this).parent().addClass('ui-verify-failed');
			$('#warningConfirmPwd').text('确认密码不能为空！');
			verifyBool = false;
		}else{
			$(this).parent().addClass('ui-verify-failed');
			$('#warningConfirmPwd').text('');
			if(pwd != newPwd){
				$(this).parent().addClass('ui-verify-failed');
				$('#warningConfirmPwd').text('两次输入的新密码不一致，请重新输入！');
				verifyBool = false;
			}else{
				$(this).parent().removeClass('ui-verify-failed');
				$('#warningConfirmPwd').text('');
				verifyBool = true;
			};
		};
	});

	//密码强度
	$('#txtNewPassword').change(function(){
		checklevel(this.value);
	});

	// 修改
	$('#btnUpdate').click(function(){
        //如果密码强度大于1往下执行，否则提示密码强度太低，请使用数字字母字符任意两种组合
        var pwdNew = $.trim($('#txtNewPassword').val());
        if(checkl(pwdNew)>1)
        {
            confirmPwd();
        }else{
            showConfirm({
                icon: 'succeed',
                content: "密码强度太低，请使用数字字母字符任意两种组合"
            });
        }
	});

});

function loadLoginPage(){
	$.ajax({
		type: 'POST',
		data: { userId: $('#userId').val() },
		url: '../login/doLogout.html',
		success: function(data, textStatus){
			window.location.href = '../login/getLoginPage.html?exitflag=yes';
		}
	});
}

function gotoMainPage() {
    window.location.href = '../desktop/getMainPage.html';
}
