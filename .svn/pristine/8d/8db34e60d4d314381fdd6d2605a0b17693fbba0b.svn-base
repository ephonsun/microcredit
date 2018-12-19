
//加载产品关联话术选项
//videoTypeProd录像环节:1产品介绍，2风险提示
//scriptOptFlag话术选项标志:0：编辑 1：查看
function loadProductScriptOpt (videoTypeProd,scriptOptFlag){
	var postJson = {};
    postJson['videoTypeProd'] = videoTypeProd;
    postJson['scriptOptFlag'] = scriptOptFlag;
    postJson['isProductScript'] = 1;
    postJson['productId'] = $('#productId').val();
    jQuery.ajax({
        type : 'post',
        url : '../product/onScriptOptChange.html',
        data : postJson,
        success : function(html) {
        	if(scriptOptFlag==0){//编辑
        		appendOpt(html,"",videoTypeProd,1);
        	}else{//查看
	        	//页面插入话术选项
	        	if(videoTypeProd == 1){//产品介绍
			        jQuery('#productScriptOptTableL').html(html);
			    }else if(videoTypeProd == 2){//风险提示
			        jQuery('#productScriptOptTableR').html(html);
			    }
        	}
        }
    });
}

//编辑话术选项
function editOpt(scriptId,scriptName,videoTypeProd,isProductScript){
	var scriptOptMode = "edit";
	var url = "../product/getScriptOptPage.html?scriptOptMode="+scriptOptMode+"&videoTypeProd="+videoTypeProd+"&isProductScript="+isProductScript;
	if(parseInt(isProductScript)!=1){
		url = url+"&scriptId="+scriptId;
	}else{
		url = url+"&ppsId="+scriptId;
	}
	showDialog({
        id: 'scriptOpt',
        title : scriptName,
        url : url,
        width : 720,
        height : 480,
        tabId: tabs.getSelfId(window)
    });
}

//编辑话术选项
/*function editOpt(scriptId,scriptName,scriptContent,videoTypeProd,isProductScript){
	//alert('edit');
	var scriptContentNew;		//新话术内容
	if(isProdIntro(videoTypeProd)){//产品介绍
		if(isProductScript == 0){//标准话术
			scriptContentNew = $('input#prodIntro'+scriptId).val();
		}else if(isProductScript == 1){//产品关联话术
			scriptContentNew = $('input#prodIntroInit'+scriptId).val();		
		}
	}else{//风险提示
		if(isProductScript == 0){//标准话术
			scriptContentNew = $('input#riskWarn'+scriptId).val();
		}else if(isProductScript == 1){//产品关联话术
			scriptContentNew = $('input#riskWarnInit'+scriptId).val();		
		}
	}
	
	//alert(scriptContentNew);
	
	//如果话术选项编辑后不为空则更新选项的话术内容，为空则还原选项的话术内容
	if(scriptContentNew!=''){
		scriptContent = scriptContentNew;
	}
	var url ="";
	if(isProductScript == 0){//标准话术
		url = '../product/getScriptOptEditPage.html?scriptInfoOpt.scriptId='+scriptId+'&scriptInfoOpt.scriptName='+
		encodeURI(encodeURI(scriptName))+'&scriptInfoOpt.scriptContent='+encodeURI(encodeURI(scriptContent))+'&videoTypeProd='+videoTypeProd+'&isProductScript=0';
	}else if(isProductScript == 1){//产品关联话术
		url = '../product/getScriptOptEditPage.html?productScriptOpt.ppsId='+scriptId+'&productScriptOpt.scriptName='+
		encodeURI(encodeURI(scriptName))+'&productScriptOpt.scriptContent='+encodeURI(encodeURI(scriptContent))+'&videoTypeProd='+videoTypeProd+'&isProductScript=1';
	}
	showDialog({
		id: 'editScriptOpt',
		title : '编辑话术选项',
		url : url,
		width : 480,
		height : 320,
		tabId: tabs.getSelfId(window)
	});
}*/
	
//查看话术
function viewOpt(scriptId,scriptName,isProductScript){
	var scriptOptMode = "view";
	var url = "../product/getScriptOptPage.html?scriptOptMode="+scriptOptMode+"&isProductScript="+isProductScript;

	if(parseInt(isProductScript)!=1){
		url = url+"&scriptId="+scriptId;
	}else{
		url = url+"&ppsId="+scriptId;
	}

	url += "&random=" + +Math.random()*100000;

	showDialog({
        id: 'scriptOpt',
        title : scriptName,
        url : url,
        width : 680,
        height : 420
    });
}

//下载话术附件
function downScriptFile(scriptId,filePath,fileName,isProductScript){
	var saveFilename = filePath + "/" + fileName;
	jQuery.ajax({
        type: "post",
        url: "../product/isFileExist.html?saveFilename="+encodeURI(encodeURI(saveFilename)),
        success: function(result){
            if(result == 'continue'){
				location.href="../product/downScriptFile.html?isProductScript="+isProductScript+"&scriptId="+scriptId;
			}else{
				showConfirm({
					icon: 'error',
					content: '文件不存在!'
				});
				return false;
			}
        }
    });
}

//播放话术附件
function playScriptRecord(obj,scriptId,filePath,fileName,fileType){
	var saveFilename = filePath + "/" + fileName;
	var separator = filePath.substring(filePath.length-2,filePath.length-1);
	jQuery.ajax({
	    type: "post",
	    url: "../product/isFileExist.html?saveFilename="+encodeURI(encodeURI(saveFilename)),
	    success: function(result){
	        if(result == 'continue'){
	        	jQuery.post('../product/openRecord.html', { 'scriptId':scriptId, 'saveFilename': saveFilename,'fileType': fileType }, function(result){
	        		//alert("播放功能正在修复，请改用下载");
	        		if(fileType == 'video'){//播放视频
	                	banger.media.video.play({ id: 'scriptVideo', file: '../temp_file/Videos/'+result, follow: obj });
	        		}else if(fileType == 'audio'){//播放音频
	        			banger.media.audio.play({ id: 'scriptAudio', file: '../temp_file/Audios/'+result, follow: obj });
	        		}
            	});
			}else{
				showConfirm({
					icon: 'error',
					content: '文件不存在!'
				});
				return false;
			}
	    }
    });
}

