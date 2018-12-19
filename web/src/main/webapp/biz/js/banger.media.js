/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-08-29
 * Description: banger.media.js
 * Modified by: 
 * Modified contents: 
**/
Namespace.register('banger.media');

// 创建音频或视频播放器标记
banger.media.getContent = function(type, opts){
	
	var doc = [];
	
	if(type == 'audio'){ // 音频
		
		doc.push('<div>');
		
		doc.push('<object id="' + opts.id + '" width="' + opts.width + '" height="' + opts.height + '" align="middle" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95">');
		
		doc.push('<param name="AutoStart" value="-1" />');
		
		doc.push('<param name="Filename" value="'+ opts.file +'" />');
		
		doc.push('<param name="PlayCount" value="1" />');
		
		doc.push('<param name="Volume" value="0" />');
		
		doc.push('</object>');
		
		doc.push('</div>');
		
	}else if(type == 'video'){ // 视频
		doc.push('<div>');
		
		doc.push('<object id="' + opts.id + '" width="' + opts.width + '" height="' + opts.height +  '" align="middle" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">');
		
		doc.push('<param name="AutoStart" value="-1" />');
		
		doc.push('<param name="url" value="'+ opts.file +'" />');
		
		doc.push('<param name="PlayCount" value="1" />');

        doc.push('<param name="windowlessVideo" value="0" />');

		doc.push('<param name="Volume" value="60" />');
		
		doc.push('<param name="EnableContextMenu" value="-1">');
		
		doc.push('</object>');
		
		doc.push('</div>');		
		
		
	}
	
	return doc.join('');
	
};

// 弹出音频播放器
banger.media.audio = {
	
	play: function(opts){
		
		opts = $.extend({}, {
			
			id: '', // 
			
			title: '播放音频', // 标题
			
			file: '', // 音频文件
			
			width: 300, // 宽度
			
			height: 60, // 高度
			
			padding: 0, // 内补丁
			
			resize: false, // 是否可拖曳改变尺寸
			
			lock: true,
			
			opacity: 400,
			
			content: '', // 内容
			
			follow: null, // 设置跟随元素
			
			close: function(){
				var audio = window.top.document.getElementById(opts.id);
				if(audio){
				
					audio.Stop();
					
					audio.style.display = 'none';
					jQuery.ajax({
						url:'../product/deleteTempFile.html?fileName='+opts.file
					});
				}
				
			} // 对话框关闭事件处理函数
			
		}, opts);
		
		// 如果 id为空，则返回
		if(opts.id == ''){
			
			return false;
			
		}
		
		// 查找第三方对象
		var oArt = w.top.jQuery;

		if(oArt){
			
			var id = 'art-' + opts.id, oAudio = oArt.dialog.list[id], isExsited = false, doc = banger.media.getContent('audio', opts);
			
			for(var i in oAudio){
				
				isExsited = true;
				
				break;
				
			}
			
			if(isExsited){
				
				oAudio.content(doc).follow(opts.follow);
				
			}else{
				
				oArt.dialog({
				
					id: id,
					
					title: opts.title,
					
					padding: opts.padding,
					
					resize: opts.resize,
					
					lock: opts.lock,
					
					opacity: opts.opacity,
					
					content: doc,
					
					follow: opts.follow,
					
					close: opts.close
					
				});
				
			}
			
		}
		
	}
	
};

function playAudio(opts){
	
	banger.media.audio.play(opts);
	
}

// 弹出视频播放器
banger.media.video = {
	
	play: function(opts){
opts = $.extend({}, {
			
			id: '', // 
			
			title: '播放视频', // 标题
			
			file: '', // 音频文件

			width: 800, // 宽度

			height: 550, // 高度
			
			padding: 10, // 内补丁
			
			resize: true, // 是否可拖曳改变尺寸
			
			lock: true,
			
			opacity: 400,
			
			content: '', // 内容
			
			follow: null, // 设置跟随元素
			
			close: function(dc){
				var video = window.top.document.getElementById(opts.id);
				
				if(video){
				
					video.Controls.Stop();
					
					//.style.display = 'none';
					
					jQuery.ajax({
						url:'../product/deleteTempFile.html?fileName='+opts.file
					});
					if(video.style.display != 'none'){
						video.style.display = 'none';
						setTimeout(function(){
							dc.close();
						},10);
						return false;
					}
				}
				
			} // 对话框关闭事件处理函数
			
		}, opts);
		// 如果 id为空，则返回
		if(opts.id == ''){
			
			return false;
			
		}
		
		// 查找第三方对象
		var oArt = w.top.jQuery;

		if(oArt){
			
			var id = 'art-' + opts.id, oVideo = oArt.dialog.list[id], isExsited = false, doc = banger.media.getContent('video', opts);
			
			for(var i in oVideo){
				
				isExsited = true;
				
				break;
				
			}
			
			if(isExsited){

                oVideo.content(doc).follow(opts.follow);
				
			}else{
				
				oArt.dialog({
				
					id: id,
					
					title: opts.title,
					
					padding: opts.padding,
					
					resize: opts.resize,
					
					lock: opts.lock,
					
					opacity: opts.opacity,
					
					content: doc,
					
					follow: opts.follow,
					
					close: opts.close

				});
				
			}
			
		}
		
	}
	
};

function playVideo(opts){
	banger.media.video.play(opts);
}
