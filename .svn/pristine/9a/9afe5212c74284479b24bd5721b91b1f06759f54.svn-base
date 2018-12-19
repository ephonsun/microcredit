<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 

<script type="text/javascript" src="jquery-1.4.2.min.js"></script> 
<script type="text/javascript" src="swfobject.js"></script> 
<script type="text/javascript" src="jquery.uploadify.v2.1.4.min.js"></script> 
<link href="uploadify.css" rel="stylesheet" type="text/css" /> 

<script type="text/javascript"> 
        $(document).ready(function() { 
        	var filenames="";
			var path = 20111122
            $("input[name='fileupload']").uploadify({ 
                /*注意前面需要书写path的代码*/ 
                'uploader'       : 'uploadify.swf', 
                'script'         : '/uploadFile.action', 
                'cancelImg'      : 'cancel.png', 
                'queueID'        : 'fileQueue', //和存放队列的DIV的id一致 
                'fileDataName'   : 'fileupload', //和以下input的name属性一致 
                'auto'           : true, //是否自动开始 
                'multi'          : true, //是否支持多文件上传 
                //'hideButton'     : true, //隐藏浏览按钮的图片
                'buttonText'     : escape('添加附件'),//浏览按钮上的文字  
             	'buttonImg'     : 'attach.png',//浏览按钮的图片的路径 
             	'width'          : 30,//设置浏览按钮的宽度 ，默认值：110
             	'height'         : 30,//设置浏览按钮的高度 ，默认值：30
             	'wmode'          : 'transparent',//设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。 默认值：opaque 
                'simUploadLimit' : 3, //一次同步上传的文件数目 
                'sizeLimit'      : 307200000, //设置单个文件大小限制 byte
                'queueSizeLimit' : 2, //队列中同时存在的文件个数限制 
                'filesReplaced'  : false,//如果文件队列中已经存在A和B两个文件，再次选择文件时又选择了A和B，该属性值为2
                //'fileDesc'       : '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
                //'fileExt'        : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式   
                onSelect:function (event, queueID, fileObj){
                	if(fileObj.size>307200){
                		return;
                	}
                },
	            onComplete: function (event, queueID, fileObj, response, data) { 
					$('<li></li>').appendTo('.files').text(response);
					$('<li></li>').appendTo('.files').text("上传控件ID："+event.currentTarget.id);
					$('<li></li>').appendTo('.files').text("文件名："+fileObj.name); 	
					var json=eval("(" + response + ")"); //解析json格式数据
					filenames+=json.filename+",";
					$('<li></li>').appendTo('.files').text("保存文件名："+json.filename); 
				},  
				onAllComplete: function (event, data) { 
					$('<li></li>').appendTo('.files').text("上传的所有文件个数:"+data.filesUploaded);
					$('<li></li>').appendTo('.files').text("出现错误的个数:"+data.errors); 	
					$('<li></li>').appendTo('.files').text("所有上传文件的总大小:"+data.allBytesLoaded+" kb"); 	
					$('<li></li>').appendTo('.files').text("平均上传速率:"+data.speed+" kb/s" ); 
					$('<li></li>').appendTo('.files').text("所有保存文件名："+filenames.substring(0,filenames.length-1)); 					
				}, 
				onError: function(event, queueID, fileObj) { 
		             alert("文件:" + fileObj.name + "上传失败"); 
			    }, 
			    onCancel: function(event, queueID, fileObj){ 
			         alert("取消了" + fileObj.name); 
			    } 
	        }); 
        }); 
        </script> 

<script type="text/javascript"> 
                  //必须的 
function uploadifyUpload(){ 
   $('#fileupload').uploadifyUpload(); 
} 
</script> 

</head>
<body>
<table>
<tr> 
<td>上传图片：</td> 
<td> 
<input type="file" name="fileupload" id="fileupload" /> 
<input type="file" name="fileupload" id="fileupload1" /> 
<input type="file" name="fileupload" id="fileupload2" /> 
<div id="fileQueue"></div> 
<p>  
<a href="javascript:;" onClick="javascript:uploadifyUpload()">开始上传</a>&nbsp; 
<a href="javascript:jQuery('#fileupload').uploadifyClearQueue()">取消所有上传</a> 
</p> 
<ol class=files></ol> 
</td> 
</tr> 
</table>
</body>
</html>
