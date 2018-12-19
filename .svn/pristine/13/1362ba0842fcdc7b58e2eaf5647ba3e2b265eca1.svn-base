/*
   作者：徐红军
   说明：ajax文件上传 
*/
var ajaxFileUpload = function(opts){
    return new ajaxFileUpload.prototype.init(opts);
};
ajaxFileUpload.prototype = {
    init:function(opts){
        var set = this.extend({
            url:'result.php',
            id:'fileId',
            callback:function(){}
        },opts || {});
        var _this = this;
        var id = +new Date();
        var form = this.createForm(id),frame = this.createIframe(id,set.url);
        var oldFile = document.getElementById(set.id);
        //校验文件类型，opts.fileType 不填的话则不校验
        if(set.fileType){
            var fileName=oldFile.value;
            var point = fileName.lastIndexOf(".");  
            var type = fileName.substr(point);
            var typeArrs = set.fileType.toLowerCase().split("|");
            if(jQuery.inArray(type.toLowerCase(), typeArrs) < 0){
                showConfirm({
                    icon: 'warning',
                    content: set.typeTips,
                    ok: function(){}
                });
                return;
            }
        }
        //校验文件大小 opts.fileSize 不填的话则不校验
        //在IE10以下版本浏览器没有权限获取到文件的大小，故暂时先不校验大小
//      if(set.fileSize){
//          if(oldFile.files[0].size > set.fileSize){
//              openMessageBox(set.sizeTips);
//              return;
//          }
//      }
        var $oldFile = $(oldFile);
        var $newFile = $oldFile.clone();
        var fileId = 'ajaxFileUploadFile'+id;
        $oldFile.attr('id',fileId);
        $oldFile.parent().append($newFile);
        form.appendChild(oldFile);
        form.setAttribute('target',frame.id);
        form.setAttribute('action',set.url);
        setTimeout(function(){
            form.submit();
            if(frame.attachEvent){
                frame.attachEvent('onload',function(){_this.uploadCallback(id,set.callback);});
            }else{
                frame.onload = function(){_this.uploadCallback(id,set.callback);}
            }
        },100);
    },
    /*
        创建iframe
    */
    createIframe:function(id,url){
        var frameId = 'ajaxFileUploadFrame'+id,iFrame;
        var IE = /msie ((\d+\.)+\d+)/i.test(navigator.userAgent) ? (document.documentMode ||  RegExp['\x241']) : false,
        url = url || 'javascript:false';
        if(IE && IE < 8){
            iFrame = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
            iFrame.src = url;
        }else{
            iFrame = document.createElement('iframe');
            this.attr(iFrame,{
                'id':frameId,
                'name':frameId
            });
        };
        iFrame.style.cssText = 'position:absolute; top:-9999px; left:-9999px';
        return document.body.appendChild(iFrame);
    },
    /*
            创建form
    */
    createForm:function(id){
        var formId = 'ajaxFileUploadForm'+id;
        var form = document.createElement('form');
        this.attr(form,{
            'action':'',
            'method':'POST',
            'name':formId,
            'id':formId,
            'enctype':'multipart/form-data',
            'encoding':'multipart/form-data'
        });
        form.style.cssText = 'position:absolute; top:-9999px; left:-9999px';
        return document.body.appendChild(form);  
    },
    /*
             获取iframe内容，执行回调函数，并移除生成的iframe和form 
    */
    uploadCallback:function(id,callback){
        var frame = document.getElementById('ajaxFileUploadFrame'+id),form = document.getElementById('ajaxFileUploadForm'+id);data = {};
        var db = document.body;
        try{
            if(frame.contentWindow){
                data.responseText = frame.contentWindow.document.body ? frame.contentWindow.document.body.innerHTML : null;
                data.responseXML = frame.contentWindow.document.XMLDocument ? frame.contentWindow.document.XMLDocument : frame.contentWindow.document;
            }else{
                data.responseText = frame.contentDocument.document.body ? frame.contentDocument.document.body.innerHTML : null;
                data.responseXML = frame.contentDocument.document.XMLDocument ? frame.contentDocument.document.XMLDocument : frame.contentDocument.document;
            }
            var text = data.responseText;
            var len = data.responseText.length;
            if(text && len>3){
            	if(text.substr(0,4).toLowerCase()=="<pre"){
                    var n = text.indexOf(">");
                    var m = text.lastIndexOf("<");
                    data.responseText=text.substr(n+1,m-n-1);
                }
            }
        }catch(e){};
        callback && callback.call(this,data);
        setTimeout(function(){
            db.removeChild(frame);
            db.removeChild(form);
        },100);
    },
    attr:function(el,attrs){
        for(var prop in attrs) el.setAttribute(prop,attrs[prop]);
        return el;
    },
    extend:function(target,source){
        for(var prop in source) target[prop] = source[prop];
        return target;
    }
};
ajaxFileUpload.prototype.init.prototype = ajaxFileUpload.prototype;