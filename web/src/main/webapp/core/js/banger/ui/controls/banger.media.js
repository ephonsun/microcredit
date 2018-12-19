/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-12-06
 * Description: banger.media.js
 * Modified by:
 * Modified contents:
 **/
if(typeof banger == 'undefined' || typeof banger.dialog == 'undefined'){
    document.write('<script type="text/javascript" src="../core/js/banger/ui/tools/banger.dialog.js"><\/script>');
}

Namespace.register('banger.media');

// 扩展播放器参数
banger.media.fn = {
    // 扩展音频播放器参数
    extendAudioOpts: function(o){
        return $.extend({ id: 'audioPlayer', title: '', width: 300, height: 60, lock: false, zIndex: 3000, content: '', file: null }, o);
    },
    // 扩展视频播放器参数
    extendVideoOpts: function(o){
        return $.extend({ id: 'videoPlayer', title: '', width: 640, height: 440, lock: false, zIndex: 3000, content: '', file: null }, o);
    }
};

// 创建HTML标签
banger.media.getContent = function(type, o){
    var doc = [];
    // 音频播放器HTML标签
    if(type == 'audio'){
        doc.push('<div>');
        doc.push('<object width="' + o.width + '" height="' + o.height + '" align="middle" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95">');
        doc.push('<param name="AutoStart" value="-1" />');
        doc.push('<param name="Filename" value="' + o.file + '" />');
        doc.push('<param name="PlayCount" value="1" />');
        doc.push('<param name="Volume" value="60" />');
        doc.push('</object>');
        doc.push('</div>');
    }
    // 视频播放器HTML标签
    else if(type == 'video'){
        doc.push('<div>');
        doc.push('<object width="' + o.width + '" height="' + o.height + '" align="middle" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95" id="vv">');
        doc.push('<param name="AutoStart" value="-1" />');
        doc.push('<param name="Filename" value="' + o.file + '" />');
        doc.push('<param name="PlayCount" value="1" />');
        doc.push('<param name="Volume" value="60" />');
        doc.push('</object>');
        doc.push('</div>');
    }
    return doc.join('');
};

// 媒体播放器
banger.media.player = function(type, o){
    // 音频
    if(type == 'audio'){
        o = this.fn.extendAudioOpts(o);
        if(o.id == ''){
            return false;
        }
        if(typeof showDialog == 'function' && typeof getDialog == 'function'){
            var d = getDialog(o.id), doc = this.getContent(type, o);
            if(!d){
                showDialog({
                    id: o.id,
                    title: o.title,
                    lock: o.lock,
                    zIndex: o.zIndex,
                    content: doc,
                    close: function(dg){
                        var ocx = dg.dom.content.find('object')[0];
                        if(ocx){
                            try{
                                ocx.stop();
                            }catch(e){

                            }
                        }
                    }
                });
            }else{
                d.title(o.title).content(doc);
            }
        }
    }
    // 视频
    else if(type == 'video'){
        o = this.fn.extendVideoOpts(o);
        if(o.id == ''){
            return false;
        }
        if(typeof showDialog == 'function' && typeof getDialog == 'function'){
            var d = getDialog(o.id), doc = this.getContent(type, o);
            if(!d){
                showDialog({
                    id: o.id,
                    title: o.title,
                    lock: o.lock,
                    zIndex: o.zIndex,
                    content: doc,
                    close: function(dg){
                        var ocx = dg.dom.content.find('object')[0];
                        if(ocx){
                            try{
                                ocx.stop();
                            }catch(e){

                            }
                        }
                    }
                });
            }else{
                d.title(o.title).content(doc);
            }
        }
    }
};

// 
var mediaPlayer = function(){
    var args = arguments, len = args.length, type = 'audio', o = null;
    if(len == 1){
        o = args[0];
    }else if(len == 2){
        type = args[0];
        o = args[1];
    }
    banger.media.player(type, o);
};
