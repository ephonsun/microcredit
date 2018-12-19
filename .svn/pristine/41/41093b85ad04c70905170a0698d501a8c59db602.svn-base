
//var fileTotal = 5, queueSizeLimit = fileTotal - $('.uploadifyQueueItem').length;
function setUpload(script,sizeLimit,queueSizeLimit) {
	var count = 0, sccCount = 0, strError = '';
	var filenames = '';
	var moreSizeName = [], //获取超过10M文件的名字
		erroTypeName = [], //获取格式错误文件的名字
		onSelectBool = false, //上传重复的时候不会走onSelect,onQueueFull函数会走onSelectOnce了，定义一个标示符
		queueFullBool = false;
	var uploadedRepeatFiles = [], //在编辑情况下，获取上传重复的文件名
		uploadedFilesName = []; //获取已上传文件的文件名
	$("input[name='fileInput']").uploadify({
		/*注意前面需要书写path的代码*/
		'uploader' : '../uploadify/uploadify.swf',
		'script' : script,
		'cancelImg' : '../uploadify/cancel.png',
		'queueID' : 'fileQueue', //和存放队列的DIV的id一致
		'fileDataName' : 'fileInput', //和以下input的name属性一致
		'auto' : true, //是否自动开始
		'multi' : true, //是否支持多文件上传
		//'hideButton'     : true, //隐藏浏览按钮的图片
		'buttonText' : escape('选择附件'),//浏览按钮上的文字
		'buttonImg' : '../uploadify/attach_select.png',//浏览按钮的图片的路径
		'width' : 70,//设置浏览按钮的宽度 ，默认值：110
		'height' : 24,//设置浏览按钮的高度 ，默认值：30
		'wmode' : 'transparent',//设置该项为transparent 可以使浏览按钮的flash背景文件透明，并且flash文件会被置为页面的最高层。 默认值：opaque
		'simUploadLimit' : 5, //一次同步上传的文件数目
		'sizeLimit' : sizeLimit, //设置单个文件大小限制 byte(目前最大不超过10M)
		'queueSizeLimit' : queueSizeLimit, //队列中同时存在的文件个数限制
		'filesReplaced' : false,//如果文件队列中已经存在A和B两个文件，再次选择文件时又选择了A和B，该属性值为2
		'onSelect':function(event, queueID, fileObj){ //加入队列时候上传重名的文件不会触发这个函数
			var point = fileObj.name.lastIndexOf(".");
			var type = fileObj.name.substr(point);
			//var typeArrs = uploadFileType.toLowerCase().split("|");
			/*if($.inArray(type.toLowerCase(), typeArrs) < 0){
				erroTypeName.push(fileObj.name);
				setTimeout("$('#fileInput').uploadifyCancel('" + queueID + "')", 2000);
			}else */if(fileObj.size > sizeLimit){
				moreSizeName.push(fileObj.name);
				setTimeout("$('#fileInput').uploadifyCancel('" + queueID + "')", 2000);
			}else{
				var repeatFile = $.inArray(fileObj.name, uploadedFilesName);
				if(repeatFile > -1){
					uploadedRepeatFiles.push(fileObj.name);
					setTimeout("$('#fileInput').uploadifyCancel('" + queueID + "')", 2000);
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
			fileDiv.setAttribute("class", "fileInputQueue");
			fm.appendChild(fileDiv);

			//文件名称
			var fileInput = document.createElement("input");
			fileInput.setAttribute("name", "fileNameTask");
			fileInput.setAttribute("value", fileObj.name);
			fileInput.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInput);
			//文件大小
			var fileInputSize = document.createElement("input");
			fileInputSize.setAttribute("name", "fileSize");
			fileInputSize.setAttribute("value", fileObj.size);
			fileInputSize.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputSize);

			var responseStr=response.split("||");
			//源文件名
			var fileInputOldName = document.createElement("input");
			fileInputOldName.setAttribute("name", "fileNameOld");
			fileInputOldName.setAttribute("value", responseStr[1]);
			fileInputOldName.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputOldName);
			//路径
			var fileInputPath = document.createElement("input");
			fileInputPath.setAttribute("name", "filePath");
			fileInputPath.setAttribute("value", responseStr[2]);
			fileInputPath.setAttribute("type", "hidden");
			fileDiv.appendChild(fileInputPath);

			sccCount = sccCount + 1;
			if (sccCount == count) {
				//submitMessage();
			}
			var json = eval("(" + response + ")"); //解析json格式数据
			filenames += json.filename + ",";
		},
		'onError' : function(event, queueID, fileObj) {
			/*
	        if (fileObj.size > 10240000) {
	            strError += '文件:' + fileObj.name + '大于10M，请重新选择！<br />';
	
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
				if(erroTypeName.length > 0 && onSelectBool == true){
					showConfirm({ icon:'warning', content:'文件：<br />' + erroTypeName.join('<br />') + '<br />格式错误，目前只支持bmp、jpg、jpeg、png图像文件格式上传！'});
					erroTypeName = []; //清空
				}else if(moreSizeName.length > 0 && onSelectBool == true){
					showConfirm({ icon:'warning', content:'文件：<br />' + moreSizeName.join('<br />') + '<br />大于'+parseInt(sizeLimit/1000/1024,10)+'M，请重新选择'});
					moreSizeName = []; //清空
					uploadedRepeatFiles = []; //清空
				}else if(uploadedRepeatFiles.length > 0 && onSelectBool == true){
					showConfirm({ icon:'warning', content:'文件：<br />' + uploadedRepeatFiles.join('<br />') + '<br />已存在，不允许重复上传'});
					uploadedRepeatFiles = []; //清空
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