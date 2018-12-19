/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-12-03
 * Description: banger.dialog.js
 * Modified by:
 * Modified contents:
 **/
if(typeof banger == 'undefined' || typeof banger.dialog == 'undefined'){
    document.write('<link type="text/css" rel="stylesheet" href="../core/js/third/artDialog/dialog.css" \/>');
    document.write('<script type="text/javascript" src="../core/js/third/artDialog/jquery.artDialog.min.js"><\/script>');
}

// 注册模态&消息对话框命名空间
Namespace.register('banger.dialog');

// 扩展模态&消息对话框参数
banger.dialog.fn = {
    // 扩展模态对话框参数
    extendDialogOpts: function(o){
        var n = { lock: true };
        switch(typeof o){
            case 'string':
                n['content'] = '<div class="d-content-dialog">' + o + '</div>';
                break;
            case 'object':
                for(var i in o){
                    n[i] = o[i];
                }
                if(n['url']){
                    n['url'] = modifyUrl(n['url'], 'random', Math.random());
                    n['content'] = '<iframe frameborder="0" style="width:' + n['width'] + 'px; height:' + n['height'] + 'px;" src="' + n['url'] + '"></iframe>';
                }
                break;
            default: break;
        }
        return n;
    },
    // 扩展消息对话框参数
    extendConfirmOpts: function(o){
        var n = { icon: 'warning', lock: true, ok: function(){} };
        switch(typeof o){
            case 'string':
                n['content'] = '<div class="d-content-warning"><span>' + o + '</span></div>';
                break;
            case 'object':
                for(var i in o){
                    n[i] = o[i];
                }
                var type = n['icon'], text = n['content'] ,error = n['error'];
                var html = '<div class="d-content-' + type + '"><span>' + text + '</span></div>';
                n['content'] = html;
                if(type == 'succeed'){
                    n['lock'] = false;
                    n['time'] = 1000;
                    n['ok'] = false;
                }else if(type == 'error'){
                	n['lock'] = false;
                	n['cannelValue'] = '122';
                	n['okValue'] = '关闭';
                    n['ok'] = function(){
                        
                    };
                    n['cancelValue'] = '日志';
                    n['cancel'] = function(){
                    	var html = "<textarea style='width:700px;height:500px;' >"+error+"</textarea>";
                    	banger.dialog.showDialog(html);
                    	//return alert(error);
                    };
                    //n['time'] = 1000;
                    //n['ok'] = false;
                }
                break;
            default: break;
        }
        return n;
    }
};

// 打开模态对话框
banger.dialog.showDialog = function(o){
    var w = window, q = w.top.jQuery;
    if(q && q.dialog && o){
        var d = q.dialog;
        o = this.fn.extendDialogOpts(o);
        return d(o);
    }
    return null;
};

var showDialog = function(o){
    return banger.dialog.showDialog(o);
};

// 打开消息对话框
banger.dialog.showConfirm = function(o){
    var w = window, q = w.top.jQuery;
    if(q && q.dialog && o){
        var d = q.dialog;
        o = this.fn.extendConfirmOpts(o);
        return d(o);
    }
    return null;
};
var showConfirm = function(o){
    return banger.dialog.showConfirm(o);
};

// 获取当前模态&消息对话框
banger.dialog.getDialog = function(id){
    var w = window, q = w.top.jQuery;
    if(q && q.dialog && id){
        var d = q.dialog;
        return d.get(id);
    }
    return null;
};
var getDialog = function(id){
    return banger.dialog.getDialog(id);
};

// 获取当前模态&消息对话框内嵌iframe标签
banger.dialog.getDialogIframe = function(id){
    var d = this.getDialog(id);
    if(d){
        var content = d.dom.content;
        return content.find('iframe').get(0);
    }
    return null;
};
var getDialogIframe = function(id){
    return banger.dialog.getDialogIframe(id);
};

// 获取当前模态&消息对话框内嵌iframe标签window对象
banger.dialog.getDialogWindow = function(id){
    var iframe = this.getDialogIframe(id);
    if(iframe){
        return iframe.contentWindow;
    }
    return null;
};
var getDialogWindow = function(id){
    return banger.dialog.getDialogWindow(id);
};

/* 更新或保存取消时都可以调用此方法,t为取消时执行的方法 */
var showCancelConfirmForEdit = function (f,ueEditorValue) {
    if (banger.isFormChanged(ueEditorValue)) {
        var mo = {
            icon: 'confirm',
            content: '数据已更新是否确定取消保存？',
            ok: f,
            cancel: function () {
            }
        };
        return banger.dialog.showConfirm(mo);
    } else {
        f();
    }
};
