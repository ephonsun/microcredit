/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-30
 * Description: banger.validator.js
 * Modified by:
 * Modified contents:
 **/
Namespace.register('banger');

// 输入校验
banger.validator = {
    // 校验规则库
    defaultRules: {},
    // 临时存储需要校验的标签集合，用于在保存或下一步等操作时进行全局校验
    verifyCache: [],
    // 添加单个校验规则
    addRule: function(rule){
        var bool = isPlainObject(rule);
        if(bool){
            var key = rule.name;
            delete rule.name;
            this.defaultRules[key] = rule;
        }
    },
    // 添加多个输入校验规则
    addRules: function(rules){
        var bool = isArray(rules);
        if(bool){
            var i = 0, len = rules.length;
            for(; i < len; i++){
                this.addRule(rules[i]);
            }
        }
    },
    //清空某个元素的所有全局规则 add by 林贤聪
    emptyGlobalRules: function(selector) {
        for(var i = 0; i < this.verifyCache.length; i++ ) {
            if ($(this.verifyCache[i].selector).get(0) == $(selector).get(0)) {
                this.verifyCache[i].rules = [];
            }
        }
    },
    //寻找所有的input框，然后假如有data-validator属性则添加验证。add by 林贤聪
    globalValidator: function(validators){
        var addValidators = validators || {};
        //添加自己的验证规则
        banger.validator.addRules(addValidators);
        var elements = $('[data-validator]');
        this.verify(elements);
    },
    //为每个元素禁用校验
    disableVerify: function(selectors){
        var index;
        if(isArray(selectors)) {
            for (index = 0; index<selectors.length; index++) {
                this.emptyGlobalRules(selectors[index]);
                $(selectors[index]).off('blur');
            }
        } else {
            this.emptyGlobalRules(selectors);
            $(selectors).off('blur');
        }
    },
    //为每个元素添加校验,自动找到元素的data-validator标签答应的校验
    //todo 可以提供rule规则参数
    verify: function(elements){
        elements = $(elements);
        for (var i = 0; i <= elements.length - 1; i++) {
            var validators = $(elements[i]).data('validator');
            var arrValidators = validators;
            if(isArray(validators)){//数组形式
                arrValidators = validators;
            } else if(isString(validators)) {//逗号分隔的字符串
                arrValidators = validators.split(',');
            }
            banger.verify($(elements[i]),arrValidators);
        }
    }
};

// 添加单个校验规则
var addVerifyRule = function(rule){
    banger.validator.addRule(rule);
};

// 添加多个校验规则
var addVerifyRules = function(rules){
    banger.validator.addRules(rules);
};


// 供开发人员调用，加载输入校验
banger.verify = function(){
    if(arguments.length > 1){
        var selector = arguments[0], rules = arguments[1];
        if(rules){
            // 加载输入校验
            this.verifyElements(selector, rules);
        }
    }else{
        // 在保存或下一步等操作时进行全局验证
        return this.verifyAllElems(arguments[0]);
    }
};

// 加载输入校验
banger.verifyElements = function(selector, rules){
    // 解析验证规则
    var tempRules = [], cache = {};
    // 清空上次的校验规则
    this.validator.emptyGlobalRules(selector);
    // 规则被指定为string类型
    if(isString(rules)){
        var rs = rules.split(' '), len = rs.length;
        for(var i = 0; i < len; i++){
            var single = this.resolveStringRules(rs[i]);
            // 储存局部检验规则
            tempRules.push(single);
            // 储存全局检验规则
            this.spliceGlobalRules(cache, selector, single);
        }
    }
    // 规则被指定为object类型
    else if(isPlainObject(rules)){
        var single = this.resolveObjectRules(rules);
        // 储存局部检验规则
        tempRules.push(single);
        // 储存全局检验规则
        this.spliceGlobalRules(cache, selector, single);
    }
    // 规则被指定为array类型
    else if(isArray(rules)){
        var len = rules.length;
        for(var i = 0; i < len; i++){
            var single;
            // 规则被指定为string类型
            if(isString(rules[i])){
                single = this.resolveStringRules(rules[i]);
            }
            // 规则被指定为object类型
            else if(isPlainObject(rules[i])){
                single = this.resolveObjectRules(rules[i]);
            }
            // 储存局部检验规则
            tempRules.push(single);
            // 储存全局检验规则
            this.spliceGlobalRules(cache, selector, single);
        }
    }
    this.validator.verifyCache.push(cache);

    // 加载验证事件
    var obj = $(selector);
    // 单行或者多行文本框
    if(obj.is(':text') || obj.is(':password') || obj.is('textarea')){
        obj.blur(function(){
            var len = tempRules.length;
            for(var i = 0; i < len; i++){
                var bool = verifyText(this, tempRules[i]);
                if(!bool){
                    return false;
                }
            }
        });
    }
    // 复选框
    else if(obj.is(':checkbox')){
        obj.click(function(){
            var len = tempRules.length;
            for(var i = 0; i < len; i++){
                var bool = verifyCheck(obj, tempRules[i]);
                if(!bool){
                    break;
                }
            }
        });
    }
    // 单选框
    else if(obj.is(':radio')){
        obj.click(function(){
            var len = tempRules.length;
            for(var i = 0; i < len; i++){
                var bool = verifyCheck(obj, tempRules[i]);
                if(!bool){
                    break;
                }
            }
        });
    }
    else if(obj.is('select')){ //add by 林贤聪 依赖selectbox.js
        if(obj.data('ns')) {
            obj.data('ns').find(':text').blur(function(){
                var len = tempRules.length;
                for(var i = 0; i < len; i++){
                    var bool = verifyText(this, tempRules[i]);
                    if(!bool){
                        return false;
                    }
                }
            });            
            //当select改变的时候就让文本框改变一次
            obj.change(function(){
                $(this).data('ns').find(':text').blur();
            });            
        }
    }
};

// 解析字符串形式的规则配置
banger.resolveStringRules = function(rule){
    var defaultRules = this.validator.defaultRules, key, format;
    if(rule.indexOf('[') == -1){
        key = rule;
    }else{
        var rs = rule.split(/(?=\[)/g);
        key = rs[0];
        format = rs[1];
    }
    var single = cloneObject(defaultRules[key]);
    single.name = key;
    if(format){
        single.format = format.replace(/(^\[*)|(\]*$)/g, '') .split(',');
    }
    return single;
};

// 解析对象形式的规则配置
banger.resolveObjectRules = function(rule){
    var defaultRules = this.validator.defaultRules, key, format;
    if(rule.name.indexOf('[') == -1){
        key = rule.name;
    }else{
        var rs = rule.name.split(/(?=\[)/g);
        key = rs[0];
        format = rs[1];
    }
    var single = cloneObject(defaultRules[key]);
    single.name = key;
    if(format){
        single.format = format.replace(/(^\[*)|(\]*$)/g, '') .split(',');
    }
    for(var n in rule){
        if(n != 'name'){
            single[n] = rule[n];
        }
    }
    return single;
};

// 统计全局校验规则
banger.spliceGlobalRules = function(cache, selector, single){
    if(cache.selector && cache.selector == selector){
        cache.rules.push(single);
    }else{
        cache.selector = selector;
        cache.rules = [single];
    }
};

// 在保存或下一步等操作时进行全局验证
banger.verifyAllElems = function(selector){
    var form = $(selector ? selector : 'body'),
        total = this.validator.verifyCache.length,
        flagPass = true;
    for(var index = 0; index < total; index++){

        var json = this.validator.verifyCache[index];
        var obj = $(json.selector, form);
        //var obj = $(json.selector);
        //隐藏表单不做校验
        var display = $(obj).parents(".template").css("display");
        if(display == "none")
            continue;
        var isExisted = obj.length > 0;
        if(form.selector != json.selector && form.find(json.selector).length == 0)
            continue;
        if(isExisted){
            if(obj.is(':text') || obj.is(':password') || obj.is('textarea')){ // 单行或多行文本框
                obj.each(function(){
                    var rules = json.rules, len = rules.length;
                    for(var i = 0; i < len; i++){
                        flagPass = verifyText(this, rules[i]) && flagPass;
                        if(!flagPass){
                            break;
                        }
                    }
                });
            }else if(obj.is(':checkbox')){ // 复选框
                var rules = json.rules, len = rules.length;
                for(var j = 0; j < len; j++){
                    flagPass = verifyCheck(obj, rules[j]) && flagPass;
                    if(!flagPass){
                        break;
                    }
                }
            }else if(obj.is(':radio')){ // 单选框
                var rules = json.rules, len = rules.length;
                for(var k = 0; k < len; k++){
                    flagPass = verifyCheck(obj, rules[k]) && flagPass;
                    if(!flagPass){
                        break;
                    }
                }
            }else if(obj.is('select')) {//下拉框 add by 林贤聪
                obj.each(function(){
                    var rules = json.rules, len = rules.length;
                    if($(this).data('ns')) {
                        var that = $(this).data('ns').find(':text');
                        for(var i = 0; i < len; i++){
                            flagPass = verifyText(that, rules[i]) && flagPass;
                            if(!flagPass){
                                break;
                            }
                        }                        
                    }
                });                
            }
        }
    }
    return flagPass;
};

// 校验值
var verifyText = function(obj, json){
    var flag, obj = jQuery(obj);
    // if (flag) {
        // 如果当前标签被设置为屏蔽，则返回true，不验证
        if(obj.attr('isShielded')){
            obj.parent().removeClass('ui-text-failed');
            return true;
        }
        // 正则校验
        if(isRegex(json.rule)){
            var val = jQuery.trim(obj.val());
            // 值为空
            if(val == ''){
                flag = true;
                if(json.name === 'required'){
                    flag = json.rule.test(val);
                }
            }
            // 值非空
            else{
                flag = json.rule.test(val);
            }
        }
        // 函数校验
        else if(isFunction(json.rule)){
            flag = json.rule(obj);
        }
        var panel = obj.parent();
        if(flag){
            // 移除错误提示及错误样式
            panel.removeAttr('title').removeClass('ui-text-failed');
        }else{
            // 添加错误提示及错误样式
            panel.attr('title', json.tips).addClass('ui-text-failed');
        }
        return flag;
    // }

};

// 校验勾选
var verifyCheck = function(obj, json){
    var flag;
    // 正则校验
    if(isRegex(json.rule)){

    }
    // 函数校验
    else if(isFunction(json.rule)){
        flag = json.rule(obj);
    }
    if(flag){
        // 移除错误提示及错误样式
        obj.removeAttr('title').removeClass('ui-chkrdo-failed');
    }else{
        // 添加错误提示及错误样式
        obj.attr('title', json.tips).addClass('ui-chkrdo-failed');
    }
    return flag;
};

var _maxLengthRule = function(obj){
    var max = $(obj).attr('maxlength');
    if (max) {
        var text = $(obj).val();
        if (text) {
            return text.length <= max;
        }
        return true;
    }
    return true;
}

// 定义输入校验规则
var verifyRules = [
    { name: 'maxlength', tips: '内容过长', rule: _maxLengthRule},
    { name: 'required', tips: '必须填写', rule: /.+/ },
    { name: 'repeated', tips: '与已有数据重复', rule: function(){ return true; } },
    { name: 'number', tips: '必须为数值', rule: /^(-?\d+)(\.\d+)?$/ },
    { name: 'money', tips: '金额格式不正确，整数10位 小数6位',rule: /^(([1-9]{1}((\d){0,9}))|([0]{1}))(\.(\d){1,2})?$/},
    { name: 'posiNumber', tips: '必须为正数', rule: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/ },
    { name: 'negaNumber', tips: '必须为负数', rule: /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/ },
    //{ name: 'integer', tips: '必须为整数', rule: /^-?[1-9]\d*$/ },
    { name: 'integer', tips: '必须为整数', rule: /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/ },
    { name: 'ratioDecimal', tips: '必须为浮点数 整数10位 小数6位', rule: /^(([1-9]{1}((\d){0,9}))|([0]{1}))(\.(\d){1,6})?$/},
    { name: 'posiInteger', tips: '必须为正整数', rule: /^[0-9]*[1-9][0-9]*$/ },
    { name: 'positiveInteger', tips: '必须为正整数', rule: /^[0-9]*[1-9][0-9]*$/ },
    { name: 'negaInteger', tips: '必须为负整数', rule: /^-[0-9]*[1-9][0-9]*$/ },
    { name: 'nonNegaInteger', tips: '必须为非负整数', rule: /^\d+$/ },
    { name: 'nonPosiInteger', tips: '必须为非正整数', rule: /^((-\d+)|(0+))$/ },
    { name: 'decimal', tips: '必须为浮点数', rule: /^(-?\d+)(\.\d+)?$/ },
    { name: 'float', tips: '必须为浮点数 整数10位 小数2位',rule: /^(([1-9]{1}((\d){0,9}))|([0]{1}))(\.(\d){1,2})?$/},
    { name: 'posiZeroDecimal', tips: '必须为浮点数(包含0)', rule: /^(\d+)(\.\d+)?$/ },
    { name: 'posiDecimal', tips: '必须为正浮点数', rule: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/ },
    { name: 'negaDecimal', tips: '必须为负浮点数', rule: /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/ },
    { name: 'nonNegaDecimal', tips: '必须为非负数值', rule: /^\d+(\.\d+)?$/ },
    { name: 'nonPosiDecimal', tips: '必须为非正浮点数', rule: /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/ },
    { name: 'date', tips: '日期格式不正确', rule: /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/ },
    { name: 'email', tips: '邮箱格式不正确', rule: /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/ },
    { name: 'mobile', tips: '手机号码填写错误', rule: /^1[0-9]{10}$/ },
    { name: 'areaCode', tips: '本地区号格式不正确', rule: function(){} },
    /*{ name: 'telephone', tips: '电话号码填写错误', rule: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/ },*/
    { name: 'telephone', tips: '电话号码格式不正确', rule: /^([0-9]|[-|*|#| |/])+$/ },
    { name: 'cardNum', tips: '身份证号码格式不正确', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var identifyType = $(obj).parents('.ui-form-fields').find('#identifyType').val();
        if (identifyType && identifyType != '1') return true;
        var spouseIdentifyType = $(obj).parents('.ui-form-fields').find('#spouseIdentifyType').val();
        if (spouseIdentifyType && spouseIdentifyType != '1') return true;
        var idCardType = $(obj).parents('.ui-form-fields').find('#idCardType').val();
        if (idCardType && idCardType != '1') return true;
        var length = val.length;
        if (length == 15) {
            var regx =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
            return regx.test(val);
        } else if (length == 18) {
            var regx = /^(\d{6})(19|20)(\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\d{3})(\d|X|x)?$/;
            return regx.test(val);
        } else {
            return false;
        }
    } },
    { name: 'cardNumber', tips: '身份证号码格式不正确', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var identifyType = $(obj).parent().parent().prev().prev().find('#cardType').val();
        if (identifyType && identifyType != '1') return true;
        var length = val.length;
        if (length == 15) {
            var regx =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
            return regx.test(val);
        } else if (length == 18) {
            var regx = /^(\d{6})(19|20)(\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\d{3})(\d|X|x)?$/;
            return regx.test(val);
        } else {
            return false;
        }
    } },
    { name: 'proposalAmount', tips: '建议金额只能小于等于申请金额', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var loanApplyAmount = $('#loanApplyAmount').val();
        if (parseFloat(val) <= parseFloat(loanApplyAmount)) {
            return true;
        } else {
            return false;
        }
    } },
    { name: 'approvalAmount', tips: '决议金额只能小于等于申请金额', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var loanId = $('#loanId').val();
        var flag = false;
        $.ajax({
            type: 'get',
            url: '../loan/checkLoanMoney.html',
            data: {'loanId': loanId, 'money' : val, 'type': 'approval'},
            async: false,
            dataType: 'text',
            success: function(data){ // 成功
                flag = data == 'true';
            },
            error: function(){ // 失败
                flag =  false;
            }
        });
        return flag;
    } },
    { name: 'loanAmount', tips: '放款金额只能小于等于决议金额', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var loanId = $('#loanId').val();
        var flag = false;
        $.ajax({
            type: 'get',
            url: '../loan/checkLoanMoney.html',
            data: {'loanId': loanId, 'money' : val, 'type': 'loan'},
            async: false,
            dataType: 'text',
            success: function(data){ // 成功
                flag = data == 'true';
            },
            error: function(){ // 失败
                flag =  false;
            }
        });
        return flag;
    } },
    { name: 'qq', tips: 'QQ号码填写错误', rule: /^[1-9][0-9]{4,}$/ },
    { name: 'idCard', tips: '身份证只允许输入15位或者18位数字或字母', rule: /^\w{15}$|^\w{18}$/ },
    { name: 'idBank', tips: '银行卡号只允许输入16位17位或19位、21位数字三种',   rule: /^\d{16}$|^\d{17}$|^\d{19}$|^\d{21}$/ },
    { name: 'zipCode', tips: '邮政编码填写错误', rule: /^[1-9]\d{5}$/ },
    { name: 'url', tips: 'URL地址格式不正确', rule: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/ },
    { name: 'ip4', tips: 'IP地址填写错误', rule: /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/ },
    { name: 'rar', tips: '不是有效的压缩文件', rule: /(.*)\.(rar|zip|7zip|tgz)$/ },
    { name: 'picture', tips: '图片格式不正确', rule: /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/ },
    { name: 'account', tips: '用户名必须由数字、字母或下划线组成', rule: /^\w+$/ },
    { name: 'specialAccount', tips: '用户名必须由中英文、数字或下划线组成', rule: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/ },
    { name: 'specialAccountKuohao', tips: '用户名必须由中英文、数字、下划线或括号组成', rule: /^[a-zA-Z0-9_\u4e00-\u9fa5]+|\(\s*\)+$/ },
    { name: 'customerNum1', tips: '客户编码只能由英文字母和数字组成', rule: /^[a-zA-Z0-9]+$/ },
    { name: 'customerNum2', tips: '客户编码重复,请重新输入', rule: function (obj) {
        var val = $(obj).val();
        if (!val) return true;
        var customerId = $('#id').val();
        var loanId = $('#loanId').val();
        var flag = false;
        $.ajax({
            type: 'get',
            url: '../loan/checkCustomerNum.html',
            data: {'customerId': customerId, 'customerNum' : val,'loanId' : loanId},
            async: false,
            dataType: 'text',
            success: function(data){ // 成功
                flag = data == 'true';
            },
            error: function(){ // 失败
                flag =  false;
            }
        });
        return flag;
    } },
    { name: 'specialLetter', tips: '不允许输入非法字符', rule: /[`~\\\\!@#\$%\^&\*\(\)_\+<>\?:\"{},\.\/;'\[\\]]/ },
    { name: 'letter', tips: '仅允许输入字母', rule: /^[A-Za-z]+$/ },
    { name: 'upperLetter', tips: '仅允许输入大写字母', rule: /^[A-Z]+$/ },
    { name: 'lowerLetter', tips: '仅允许输入小写字母', rule: /^[a-z]+$/ },
    { name: 'chinese', tips: '仅允许输入中文', rule: /^[\u4E00-\u9FA5\uF900-\uFA2D]+/ },
    { name: 'color', tips: '不是有效的颜色值', rule: /^[a-fA-F0-9]{6}$/ },
    { name: 'ascii', tips: '仅允许填写Acsii字符', rule: /^[\x00-\xFF]+$/ },
    { name: 'dayOfMonth', tips: '仅允许填写1-28', rule: /^(([1-9])|(1\d)|(2[0-8]))$/ },
    {
        name: 'checked',
        tips: '勾选错误',
        rule: function(obj){
            var flag = true, min = this.format[0], max = this.format[1];
            if(obj.is(':checkbox') || obj.is(':radio')){
                var total = obj.filter(':checked').length;
                if(min && total < parseInt(min)){
                    flag = false;
                }
                if(max && total > parseInt(max)){
                    flag = false;
                }
                return flag;
            }
        }
    }
];

// 向默认规则库中插入输入验证规则
addVerifyRules(verifyRules);




// 屏蔽校验
banger.shieldedVerify = function(selector){
    if(!selector){
        return false;
    }
    // 添加屏蔽属性
    $(selector).each(function(){
        $(this).attr('isShielded', 1);
    });
    // 移除校验失败后所追加的提示及样式
    this.deleteVerifyStyle(selector);
};

// 禁用校验
banger.disabledVerify = function(selector){
    this.shieldedVerify(selector);
};

// 取消屏蔽
banger.deleteShielded = function(selector){
    if(!selector){
        return false;
    }
    // 删除屏蔽属性
    $(selector).each(function(){
        $(this).removeAttr('isShielded');
    });
};

// 启用校验
banger.enableVerify = function(selector){
    this.deleteShielded(selector);
};

// 移除校验失败后所添加的提示及样式
banger.deleteVerifyStyle = function(selector){
    if(!selector){
        return false;
    }
    // 移除样式
    $(selector).each(function(){
        var oThis = $(this);
        // 单行文本框&多行文本框
        if(oThis.is(':text') || oThis.is(':password') || oThis.is('textarea')){
            var oParent = oThis.parent();
            oParent.removeAttr('title').removeClass('ui-text-failed');
        }
        // 复选按钮&单选按钮
        else if(oThis.is(':checkbox') || oThis.is('radio')){
            oThis.removeAttr('title').removeClass('ui-chkrdo-failed');
        }
    });
};


//给某个元素删除全局验证数组里面的某个验证
banger.deleteVerify = function(selector, ruleName) {
    for(var i = 0; i < this.validator.verifyCache.length; i++ ) {
        if($(this.validator.verifyCache[i].selector).get(0) == $(selector).get(0)) {
            for(var j = 0; j< this.validator.verifyCache[i].rules.length; j++ ) {
                if(this.validator.verifyCache[i].rules[j].name === ruleName) {
                    this.validator.verifyCache[i].rules.splice(j, 1);
                    break;
                }
            }
        }
    }
};
//验证表单属性是否更改,对UEeditor特殊处理,传入默认值
banger.isFormChanged=function (UeEditorValue) {
    var form = document.forms[0];
    if (form == null || form == undefined)
        return false;
    for (var i = 0; i < form.elements.length; i++) {
        var element = form.elements[i];
        if (element == null) element == "";
        if(element.type=="hidden")continue;
        var selectObjs = form.getElementsByTagName("SELECT");
        for (var i = 0; i < selectObjs.length; i++) {
            if ((selectObjs[i].name == "") || (eval("/(^|,)" + selectObjs[i].name + "(,|$)/g").test(element))) continue;
            for (var j = 1; j < selectObjs[i].length; j++) {
                if (selectObjs[i].options[j].defaultSelected != selectObjs[i].options[j].selected) return true;
            }
        }
        var inputObjs = form.getElementsByTagName("INPUT");
        for (var i = 0; i < inputObjs.length; i++) {
            var inputObj = inputObjs[i];
            if (!inputObj.className.match("ui-check")&&/*!inputObj.getAttribute("multiselect")&&*/((inputObjs[i].name == "") || (eval("/(^|,)" + inputObjs[i].name + "(,|$)/g").test(element)))) continue;
            if ((inputObjs[i].type.toUpperCase() == "TEXT") && (inputObjs[i].defaultValue != inputObjs[i].value)) return true;
            else if (((inputObjs[i].type.toUpperCase() == "RADIO") || (inputObjs[i].type.toUpperCase() == "CHECKBOX")) && (inputObjs[i].defaultChecked != inputObjs[i].checked)) return true;
        }
        var textareaObjs = form.getElementsByTagName("TEXTAREA");
        for (var i = 0; i < textareaObjs.length; i++) {
            if ((textareaObjs[i].name == "") || (eval("/(^|,)" + textareaObjs[i].name + "(,|$)/g").test(element))) continue;
            if (UeEditorValue==undefined&&textareaObjs[i].defaultValue!= textareaObjs[i].value) return true;
            if (UeEditorValue!=undefined&&UeEditorValue!= textareaObjs[i].value) return true;
        }
        return false;
    }
}