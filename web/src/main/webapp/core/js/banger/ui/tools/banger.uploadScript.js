
//var fileTotal = 5, queueSizeLimit = fileTotal - $('.uploadifyQueueItem').length;
function setUploadProdIntro(script,sizeLimit,queueSizeLimit) {
	var count = 0, sccCount = 0, strError = '';
	var filenames = '';
	var moreSizeName = [], //获取超过100M文件的名字
		onSelectBool = false, //上传重复的时候不会走onSelect,onQueueFull函数会走onSelectOnce了，定义一个标示符
		queueFullBool = false;
		uploadedRepeatFiles = [], //在编辑情况下，获取上传重复的文件名
		uploadedFilesName = []; //获取已上传文件的文件名
	$("input[name='prodIntroFileInput']").uploadify({
		/*注意前面需要书写path的代码*/
		'uploader' : '../uploadify/uploadify.swf',
		'script' : script,
		'cancelImg' : '../uploadify/cancel.png',
		'queueID' : 'prodIntroFileQueue', //和存放队列的DIV的id一致 
		'fileDataName' : 'prodIntroFileInput', //和以下input的name属性一致 
		'auto' : true, //是否自动开始 
		'multi' : true, //是否支持多文件上传 
		//'hideButton'     : true, //隐藏浏览按钮的图片
		'buttonText' : escape('选择附件'),//浏览按钮上的文字  
		'buttonImg' : '../uploadify/script_upload.png',//浏览按钮的图片的路径 
		'width' : 72,//设置浏览按钮的宽度 ，默认值：110
		'height' : 26,//设置浏览按钮的高度 ，默认值：30
		'wmode' : 'transparent',//设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。 默认值：opaque 
		'simUploadLimit' : 5, //一次同步上传的文件数目 
		'sizeLimit' : sizeLimit, //设置单个文件大小限制 byte(目前最大不超过100M)
		'queueSizeLimit' : queueSizeLimit, //队列中同时存在的文件个数限制 
		'filesReplaced' : false,//如果文件队列中已经存在A和B两个文件，再次选择文件时又选择了A和B，该属性值为2
		'onSelect':function(event, queueID, fileObj){ //加入队列时候上传重名的文件不会触发这个函数
			if(fileObj.size > 102400000){
				moreSizeName.push(fileObj.name);
				setTimeout("$('#prodIntroFileInput').uploadifyCancel('" + queueID + "')", 2000);
			}else{
				var repeatFile = $.inArray(fileObj.name, uploadedFilesName);
				if(repeatFile > -1){
					uploadedRepeatFiles.push(fileObj.name);
					setTimeout("$('#prodIntroFileInput').uploadifyCancel('" + queueID + "')", 2000);
				}
			}
			queueFullBool = false;
			onSelectBool = true;
		},
		'onComplete' : function(event, queueID, fileObj, response, data) {
			var fm = document.forms[0];
			/**生成隐藏字段**/
			var fileDiv = document.createElement("div");
			fileDiv.setAttribute("Id",queueID);
			fileDiv.setAttribute("class", "prodIntroFileInputQueue");
			fm.appendChild(fileDiv);
			
			//文件名称
			var fileInput = document.createElement("input");
			fileInput.setAttribute("name", "prodIntroFileNameTask");
			fileInput.setAttribute("value", fileObj.name);
			fileInput.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInput);
			//文件大小
			var fileInputSize = document.createElement("input");
			fileInputSize.setAttribute("name", "prodIntroFileSize");
			fileInputSize.setAttribute("value", fileObj.size);
			fileInputSize.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputSize);
			
			var responseStr=response.split("||");
			//源文件名
			var fileInputOldName = document.createElement("input");
			fileInputOldName.setAttribute("name", "prodIntroFileNameOld");
			fileInputOldName.setAttribute("value", responseStr[1]);
			fileInputOldName.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputOldName);
			//路径
			var fileInputPath = document.createElement("input");
			fileInputPath.setAttribute("name", "prodIntroFilePath");
			fileInputPath.setAttribute("value", responseStr[2]);
			fileInputPath.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputPath);
	
			sccCount = sccCount + 1;
			if (sccCount == count) {
				//appendFileOptArray(1);
			}
			var json = eval("(" + response + ")"); //解析json格式数据
			filenames += json.filename + ",";
		},
		'onError' : function(event, queueID, fileObj) {
			/*
	        if (fileObj.size > 102400000) {
	            strError += '文件:' + fileObj.name + '大于100M，请重新选择！<br />';
	
	            var oArt = openMessageBox();
	
	            if(oArt.dialog.list['KDf435']){
	                oArt.dialog.list['KDf435'].content(strError);
	            }else{
	                openMessageBox({id: 'KDf435', content: strError, ok: function(){
	                    strError = '';
	                }, close: function(){
	                    strError = '';
	                }});
	            }
	
	            setTimeout("$('#fileInput').uploadifyCancel('"
	                + queueID + "')", 3000);
	
	        } else {
	            banger.page.openMessageBox("文件:" + fileObj.name + "上传失败");
	        }
	        */
	    },
		'onCancel' : function(event, queueID, fileObj, data) {
			count = data.fileCount;
			//queueSizeLimit = fileTotal - $('.uploadifyQueueItem').length;	
		},
		'onSelectOnce':function(event, data){
			count = data.fileCount;
			if(queueFullBool == false){
				if(moreSizeName.length > 0 && onSelectBool == true){
					showConfirm({ icon:'warning', content:'文件：<br />' + moreSizeName.join('<br />') + '<br />大于'+parseInt(sizeLimit/1000/1024,10)+'M，请重新选择'});
					moreSizeName = []; //清空
					uploadedRepeatFiles = []; //清空
				}else{
					if(uploadedRepeatFiles.length > 0 && onSelectBool == true){
						showConfirm({ icon:'warning', content:'文件：<br />' + uploadedRepeatFiles.join('<br />') + '<br />已存在，不允许重复上传'});
						uploadedRepeatFiles = []; //清空
					}
				}								
			}
		},
		'onQueueFull':function(event, queueSizeLimit){ //当队列中文件数目大于设置的queueSizeLimit就会自动触发
			showConfirm({ icon:'warning', content:'一次最多能上传' + queueSizeLimit + '个文件！'});
			moreSizeName = []; //直接上传多余5个文件且含有超过10M的文件，需要把moreSizeName清空
			uploadedRepeatFiles = [];
			queueFullBool = true;
		}
	});
}

function setUploadRiskWarn(script,sizeLimit,queueSizeLimit) {
	var count = 0, sccCount = 0, strError = '';
	var filenames = '';
	var moreSizeName = [], //获取超过100M文件的名字
		onSelectBool = false, //上传重复的时候不会走onSelect,onQueueFull函数会走onSelectOnce了，定义一个标示符
		queueFullBool = false;
		uploadedRepeatFiles = [], //在编辑情况下，获取上传重复的文件名
		uploadedFilesName = []; //获取已上传文件的文件名
	$("input[name='riskWarnFileInput']").uploadify({
		/*注意前面需要书写path的代码*/
		'uploader' : '../uploadify/uploadify.swf',
		'script' : script,
		'cancelImg' : '../uploadify/cancel.png',
		'queueID' : 'riskWarnFileQueue', //和存放队列的DIV的id一致 
		'fileDataName' : 'riskWarnFileInput', //和以下input的name属性一致 
		'auto' : true, //是否自动开始 
		'multi' : true, //是否支持多文件上传 
		//'hideButton'     : true, //隐藏浏览按钮的图片
		'buttonText' : escape('上传话术'),//浏览按钮上的文字  
		'buttonImg' : '../uploadify/script_upload.png',//浏览按钮的图片的路径 
		'width' : 72,//设置浏览按钮的宽度 ，默认值：110
		'height' : 26,//设置浏览按钮的高度 ，默认值：30
		'wmode' : 'transparent',//设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。 默认值：opaque 
		'simUploadLimit' : 5, //一次同步上传的文件数目 
		'sizeLimit' : sizeLimit, //设置单个文件大小限制 byte(目前最大不超过100M)
		'queueSizeLimit' : queueSizeLimit, //队列中同时存在的文件个数限制 
		'filesReplaced' : false,//如果文件队列中已经存在A和B两个文件，再次选择文件时又选择了A和B，该属性值为2
		'onSelect':function(event, queueID, fileObj){ //加入队列时候上传重名的文件不会触发这个函数
			if(fileObj.size > 102400000){
				moreSizeName.push(fileObj.name);
				setTimeout("$('#riskWarnFileInput').uploadifyCancel('" + queueID + "')", 2000);
			}else{
				var repeatFile = $.inArray(fileObj.name, uploadedFilesName);
				if(repeatFile > -1){
					uploadedRepeatFiles.push(fileObj.name);
					setTimeout("$('#riskWarnFileInput').uploadifyCancel('" + queueID + "')", 2000);
				}
			}
			queueFullBool = false;
			onSelectBool = true;
		},
		'onComplete' : function(event, queueID, fileObj, response, data) {
			var fm = document.forms[0];
			/**生成隐藏字段**/
			var fileDiv = document.createElement("div");
			fileDiv.setAttribute("Id",queueID);
			fileDiv.setAttribute("class", "riskWarnFileInputQueue");
			fm.appendChild(fileDiv);
			
			//文件名称
			var fileInput = document.createElement("input");
			fileInput.setAttribute("name", "riskWarnFileNameTask");
			fileInput.setAttribute("value", fileObj.name);
			fileInput.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInput);
			//文件大小
			var fileInputSize = document.createElement("input");
			fileInputSize.setAttribute("name", "riskWarnFileSize");
			fileInputSize.setAttribute("value", fileObj.size);
			fileInputSize.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputSize);
			
			var responseStr=response.split("||");
			//源文件名
			var fileInputOldName = document.createElement("input");
			fileInputOldName.setAttribute("name", "riskWarnFileNameOld");
			fileInputOldName.setAttribute("value", responseStr[1]);
			fileInputOldName.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputOldName);
			//路径
			var fileInputPath = document.createElement("input");
			fileInputPath.setAttribute("name", "riskWarnFilePath");
			fileInputPath.setAttribute("value", responseStr[2]);
			fileInputPath.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputPath);
	
			sccCount = sccCount + 1;
			if (sccCount == count) {
				//appendFileOptArray(2);
			}
			var json = eval("(" + response + ")"); //解析json格式数据
			filenames += json.filename + ",";
		},
		'onError' : function(event, queueID, fileObj) {
			/*
	        if (fileObj.size > 102400000) {
	            strError += '文件:' + fileObj.name + '大于100M，请重新选择！<br />';
	
	            var oArt = openMessageBox();
	
	            if(oArt.dialog.list['KDf435']){
	                oArt.dialog.list['KDf435'].content(strError);
	            }else{
	                openMessageBox({id: 'KDf435', content: strError, ok: function(){
	                    strError = '';
	                }, close: function(){
	                    strError = '';
	                }});
	            }
	
	            setTimeout("$('#fileInput').uploadifyCancel('"
	                + queueID + "')", 3000);
	
	        } else {
	            banger.page.openMessageBox("文件:" + fileObj.name + "上传失败");
	        }
	        */
	    },
		'onCancel' : function(event, queueID, fileObj, data) {
			count = data.fileCount;
			//queueSizeLimit = fileTotal - $('.uploadifyQueueItem').length;	
		},
		'onSelectOnce':function(event, data){
			count = data.fileCount;
			if(queueFullBool == false){
				if(moreSizeName.length > 0 && onSelectBool == true){
					showConfirm({ icon:'warning', content:'文件：<br />' + moreSizeName.join('<br />') + '<br />大于'+parseInt(sizeLimit/1000/1024,10)+'M，请重新选择'});
					moreSizeName = []; //清空
					uploadedRepeatFiles = []; //清空
				}else{
					if(uploadedRepeatFiles.length > 0 && onSelectBool == true){
						showConfirm({ icon:'warning', content:'文件：<br />' + uploadedRepeatFiles.join('<br />') + '<br />已存在，不允许重复上传'});
						uploadedRepeatFiles = []; //清空
					}
				}								
			}
		},
		'onQueueFull':function(event, queueSizeLimit){ //当队列中文件数目大于设置的queueSizeLimit就会自动触发
			showConfirm({ icon:'warning', content:'一次最多能上传' + queueSizeLimit + '个文件！'});
			moreSizeName = []; //直接上传多余5个文件且含有超过10M的文件，需要把moreSizeName清空
			uploadedRepeatFiles = [];
			queueFullBool = true;
		}
	});
}