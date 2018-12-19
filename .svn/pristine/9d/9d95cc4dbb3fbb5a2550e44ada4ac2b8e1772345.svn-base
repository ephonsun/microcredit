/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-12-04
 * Description: banger.abnormal.js
 * Modified by:
 * Modified contents:
 **/
Namespace.register('banger');

// 登录超时&异地登陆&帐号被删除&帐号被禁用&角色变换等操作异常
banger.abnormal = {
    // 缓存当前ajax请求
    getLastRequest: function(o){
        return function(){
            $.ajax(o);
        };
    },
    //
    branch: function(args1, args2, args3, lastRequest, callback){
        var _this = this, result = '';

        if(typeof args1 == 'string'){
            result = args1;
        }else if(args1 != null && typeof args1 == 'object' && typeof args1.responseText != 'undefined'){
            result = args1.responseText;
        }

        switch(result){
            // 登录超时
            case 'timeout':
                _this.timeout(lastRequest);
                break;
            // 异地登陆
            case 'kicked':
                _this.kicked();
                break;
            // 帐号被禁用
            case 'disabled':
                _this.disabled();
                break;
            // 帐号被删除
            case 'deleted':
                _this.deleted();
                break;
            // 角色变换
            case 'rolechang':
                _this.rolechang();
                break;
            case 'repeatsubmit':				//重复提交
            	_this.repeatsubmit();
                break;
            case 'unsafe':
            	_this.unsafe();
            	callback(args1, args2, args3);
                break;
            default:
            	if(result.indexOf('错误详情') > -1){
                    _this.error(result);
                }else{
                    callback(args1, args2, args3);
                }
                break;
        }
    },
    // 包装ajax请求
    repack: function(o){
        var _this = this;

        // 如果未设置请求失败回调函数，则手动添加
        if(!o.error){
            o.error = function(){};
        }

        // 缓存请求完成&成功和请求失败回调函数
        var successCallback = o.complete || o.success, errorCallback = o.error;

        if(successCallback || errorCallback){
            // 缓存当前ajax请求
            var lastRequest = _this.getLastRequest(o);

            // 包装请求完成&成功回调函数
            var fn1 = function(args1, args2, args3){
                _this.branch(args1, args2, args3, lastRequest, successCallback);
            };

            // 包装请求失败回调函数
            var fn2 = function(args1, args2, args3){
                _this.branch(args1, args2, args3, lastRequest, errorCallback);
            };

            // 重新设置完成回调函数
            if(o.complete){
                o.complete = fn1;
            }
            // 重新设置成功回调函数
            if(o.success){
                o.success = fn1;
            }
            // 重新设置失败回调函数
            if(o.error){
                o.error = fn2;
            }
        }
    },
    // 根据id查找dom元素
    getElemById: function(id){
        return window.top.document.getElementById(id);
    },
    // 清除字符串两端空格
    trim: function(val){
        return val == null ? '' : val.toString().replace(/^\s+/, '').replace(/\s+$/, '');
    },
    // 组装HTML
    getContent: function(type){
        var html = [];
        switch(type){
            // 登录超时
            case 'timeout':
            	var loginAccount = $("#loginAccount").val();
                html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>系统闲置时间过长，请重新登录！</label></div>');
                html.push('<div class="dialog-abnormal-form">');
                html.push('<div id="lblErrorMessage" class="dialog-abnormal-error"></div>');
                html.push('<div class="dialog-abnormal-field">');
                html.push('<label class="label">帐&nbsp; &nbsp; 号</label>');
                if(loginAccount){
                	html.push('<input type="text" name="txtLoginAccount" id="txtLoginAccount" class="text" onkeydown="banger.abnormal.keydown(event);" readonly="true" value="'+loginAccount+'" />');
        		}else{
        			html.push('<input type="text" name="txtLoginAccount" id="txtLoginAccount" class="text" onkeydown="banger.abnormal.keydown(event);" />');
        		}
                html.push('</div>');
                html.push('<div class="dialog-abnormal-field">');
                html.push('<label class="label">密&nbsp; &nbsp; 码</label>');
                html.push('<input type="password" name="txtLoginPassword" id="txtLoginPassword" class="text" onpaste="javascript:return false;" oncopy="javascript:return false;" onkeydown="banger.abnormal.keydown(event);" />');
                html.push('</div>');
                html.push('<div class="dialog-abnormal-field" id = "checkCode" style="display:none;">');
                html.push('<label class="label">验证码</label>');
                html.push('<input type="hidden" id="needRandNum" name="needRandNum" value=""/>');
                html.push('<input type="text" id="randNum" name="randNum" style="widht:80px;height:30px;"/>');
                html.push('<img id="check" onclick="banger.abnormal.changeValidateCode(this)"/>');
                html.push('<a href="#" onclick="banger.abnormal.changeValidateCode()">换一张</a></li>');
                html.push('</div>');
                html.push('</div>');
                html.push('</div>');
                break;
            // 异地登录
            case 'kicked':
                html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>您的帐户在其它地方登录，被强制下线! </label></div>');
                html.push('</div>');
                break;
            // 帐号被禁用
            case 'disabled':
                html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>您的帐号被停用，被强制下线!</label></div>');
                html.push('</div>');
                break;
            // 帐号被删除
            case 'deleted':
                html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>您的帐号被删除，被强制下线! </label></div>');
                html.push('</div>');
                break;
            // 角色变换
            case 'rolechang':
                html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>您的帐号权限发生变化，请重新登录! </label></div>');
                html.push('</div>');
                break;
            case 'nopermit':
            	html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>没有权限访问，操作被拦截! </label></div>');
                html.push('</div>');
            	break;
            case 'unsafe':
            	html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>包含不安全字符，操作被拦截! </label></div>');
                html.push('</div>');
            	break;
            case 'repeatsubmit':
            	html.push('<div class="dialog-abnormal">');
                html.push('<div class="dialog-abnormal-tips"><label>数据重复提交，请闭关后重新打页面! </label></div>');
                html.push('</div>');
            	break;
            default: break;
        }
        return html.join('');
    },
    //
    keydown: function(e){
        // 如果按下的是 enter键，则模拟触发消息对话框的登录事件
        if(e.keyCode == 13){
            jQuery('input.d-state-highlight').click();
        }
    },
    // 登录
    login: function(lastRequest){
        var _this = this;

        // 获取错误提示层
        var oErrorMsg = _this.getElemById('lblErrorMessage');
        // 获取帐号
        var oAccount = _this.getElemById('txtLoginAccount'), txtAccount = _this.trim(oAccount.value);
        // 获取密码
        var oPassword = _this.getElemById('txtLoginPassword'), txtPassword = _this.trim(oPassword.value);

        var	randNum = jQuery.trim(jQuery('#randNum').val());

        var	needRandNum = jQuery.trim(jQuery('#needRandNum').val());

        //
        var  flag = true;
        if(!txtAccount || !txtPassword){
            oErrorMsg.innerHTML = '帐号或密码不能为空！';
            flag = false;
        }
        // 判断验证码是否为空
        if(needRandNum == 'needRandNum' && randNum ==''){
            oErrorMsg.innerHTML = '验证码不能为空！';
            flag = false;
        }
        if(flag){
            // 清空错误提示层
            oErrorMsg.innerHTML = '';
            txtPassword = $.md5(txtPassword);
            var loginUrl = ('su'==txtAccount)?'../login/doBackLogin.html':'../login/doLogin.html';
            $.ajax({
                type: 'POST',
                async: false,
                url: loginUrl,
                data: { account: txtAccount, password: txtPassword , checkCode: randNum, needRandNum: needRandNum},
                dataType: 'text',
                success: function(result){
                    
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

                    // 登录成功
                    if(localeResult == 'success'){
                        var oUserAccount = _this.getElemById('hidUserAccount'), txtOldAccount = oUserAccount.value;
                        
                        _this.saveToken(token);

                        // 新帐号登录
                        if(txtAccount != txtOldAccount){
                            window.top.location.href = '../desktop/getMainPage.html';
                        }
                        // 旧帐号登录
                        else{
                            if(isFunction(lastRequest)){
                                lastRequest();
                            }else if(isWindow(lastRequest)){
                                lastRequest.location.href = lastRequest.location.href;
                            }
                        }
                    }else{
                        if(localeResult=="needrandnum" || localeResult.indexOf("请输入验证码")>-1){
                            jQuery('#needRandNum').val("needRandNum");
                            if(localeResult=="needrandnum"){
                            	localeResult = "用户名不存在或密码错误！";
                            }
                            $("#checkCode").show();
                            var obj = document.getElementById('check');
                    		var currentTime= new Date().getTime();
                            obj.src = "../picture/randomPictureAction.html?d=" + currentTime;
                        }
                        oErrorMsg.innerHTML = localeResult;
                        flag = false;
                    }
                }
            });
        }
        return flag;
    },
    // 返回到登录界面
    back: function(){
    	var txtAccount = $("#loginAccount").val();
    	var backUrl = ('su'==txtAccount)?'../login/getBackLoginPage.html':'../login/getLoginPage.html';
        window.top.location.href = backUrl;
    },
    // 返回到登录界面
    nothing: function(){
        //什么都不做
    },
    // 登录超时
    timeout: function(lastRequest){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('timeout'),
            okValue: '登录',
            ok: function(){
                return _this.login(lastRequest);
            },
            cancelValue: '取消',
            cancel: _this.back
        });
        $('#txtLoginAccount').focus();
    },
    //没有操作权限
    nopermit: function(){
    	var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('nopermit'),
            cancel: _this.nothing,
            cancelValue: '关闭'
        });
    },
    // 异地登陆
    kicked: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('kicked'),
            cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 帐号被禁用
    disabled: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('disabled'),
            cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 帐号被删除
    deleted: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('deleted'),
            cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 角色转换
    rolechang: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('rolechang'),
            cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 不安全字符
    unsafe: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('unsafe'),
            //cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 重复提交
    repeatsubmit: function(){
        var _this = this;
        showDialog({
            id: 'abnormalDialog',
            content: _this.getContent('repeatsubmit'),
            //cancel: _this.back,
            cancelValue: '关闭'
        });
    },
    // 系统出错
    error: function(error){
        showConfirm({ icon: 'error', content: '系统出错，请重试！' ,"error":error});
    },
    saveToken: function(token){
    	if(token){
    		this.writeCookieHours('token', token, 1);
    	}
    },
    writeCookieHours: function(key, value, hours){
    	var expires = '';
    	if(hours){
    		var date = new Date();
    		date.setTime(date.getTime() + (hours * 60 * 60 * 1000));
    		expires = '; expires=' + date.toGMTString();
    	}
    	document.cookie = key + '=' + escape(value) + expires+";path=/";
    },
    changeValidateCode:function(obj) {
    	if(obj){
    		var currentTime= new Date().getTime();
            obj.src = "../picture/randomPictureAction.html?d=" + currentTime;
    	}else{
    		var obj = document.getElementById('check');
    		var currentTime= new Date().getTime();
            obj.src = "../picture/randomPictureAction.html?d=" + currentTime;
    	}
    }
};