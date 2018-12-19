$(function(){
	      
	initMaxlengthTips('#checkRemark','#tips');
	//校验 
	banger.verify('#checkRemark',[{ name: 'maxlength', tips: '备注内容过长' }]);

	//上传附件初始化(单个文件最大10M，队列中同时最多存在5个文件，文件类型为图片)
	//var uploadFileType = '.jpg|.bmp|.png|.jpeg';
    setUpload('../creditCheck/uploadCreditFile.html',1024*1024*50,10);
	
	// 提交新增数据源
	$('#btnSave').click(function() {
		save('save');
	});
    // 提交新增数据源
	$('#btnRefuse').click(function() {
        var checkRemark = $("#checkRemark").val();
        if(checkRemark == ""){
            showConfirm({
                icon:"warning",
                content:"拒绝征信调查需要填写备注！"
            })
            return;
        }
		showConfirm({
		    icon:"warning",
            content:"确认要拒绝吗？",
            ok: function () {
                refuse();

            },
            cancel: function () {
            }
        })
	});

    //拒绝征信调查
    function refuse(){
        save('refuse');
    }
	// 提交新建数据源数据并继续新建
	$('#btnSubmit').click(function() {
        if(checkSingleFile()){
            showConfirm({
                icon: 'confirm',
                content: '您确定要提交征信调查资料吗？',
                ok: function () {
                    save('submit');
                },
                cancel: function () {
                }
            });
        }
	});
	
	//关闭
	$('#btnCancel').click(function() {	    
		 tabs.close(tabs.getSelfId(window));	 
	});

});

function checkSingleFile() {
    var fileName = [];
    $(".fileName[addedFlag='true']").each(function(){
        fileName.push($(this).val());
    });
    $("#fileQueue").find(".uploadifyQueueItem").each(function(){
        fileName.push($(this).val());
    });
    if(fileName.length==0){
        showConfirm({
            icon: 'warning',
            content: '至少提交一个附件'
        });
        return false;
    }/*else if(fileName.length>1){
        showConfirm({
            icon: 'warning',
            content: '最多只能提交一个附件'
        });
        return false;
    }*/
    //文件上传中提示
    if(!checkUploadComplete()){
        showConfirm({
            icon: 'warning',
            content: '请等待文件上传完成'
        });
        return false;
    }
    return true;
}

function checkUploadComplete() {
    var success = true;
    $(".percentage").each(function () {
        var test = $(this).text();
        if(test != "" && test.indexOf("100%") < 0 ) {
            success = false;
            return success;
        }
    });
    return success ;
}

function save(opType){
    if (!banger.verify('.ui-form-fields')) {
        return false;
    }
    //文件上传中提示
    if(!checkUploadComplete()){
        showConfirm({
            icon: 'warning',
            content: '请等待文件上传完成'
        });
        return false;
    }
    var postJson = getPostFields();
    
	postJson['checkRemark'] = jQuery.trim($('#checkRemark').val());
	postJson["opType"]= opType;
	
    var fileQueueIdNew = [];
    var fileNameTask = [];
    var fileSize =[];
    var fileNameOld = [];
    var filePath = [];
    var fullFilenameDel = [];
    
    //需要删除的话术选项集合
	postJson["fullFilenameDel"]= getFileOptArrayForDel();
    
    $('input[name=fileNameTask]').each(function(){
        fileNameTask.push($(this).val());
    });
    $('input[name=fileNameOld]').each(function(){
        fileNameOld.push($(this).val());
    });
    $('input[name=fileSize]').each(function(){
        fileSize.push($(this).val());
    });
    $('input[name=filePath]').each(function(){
        filePath.push($(this).val());
    });

    var fileNameTaskStr= fileNameTask.join(",");
    var fileNameOldStr= fileNameOld.join(",");
    var fileSizeStr= fileSize.join(",");
    var saveFilenameStr= filePath.join(",");
	
    postJson["fileNameTask"]= fileNameTask.join(",");
    postJson["fileNameOld"]= fileNameOld.join(",");
    postJson["fileSize"]= fileSize.join(",");
    postJson["saveFilename"]= filePath.join(",");
	
    jQuery.ajax({
        type: "post",
        url: "../creditCheck/updateCustomerCreditCheck.html",
        data: postJson,
        success: function(result){
            result = JSON.parse(result);
            if(result.success){
            	showConfirm({
                    icon: 'succeed',
                    content: result.cause
                });
            	closeTab();
			}else{
				showConfirm({
                    icon: 'warning',
                    content: result.cause
                });
			}
        }
    });
}

//删除附件
function delAttachFile(picId, index, tips) {
    showConfirm({
        icon: 'confirm',
        content: tips,
        ok: function () {
            jQuery.post('../creditCheck/delCreditCheckFile.html', {'fileId': picId}, function (data) {
                if (data == 'success') {
                    $("#" + index).closest("tr").remove();
                }else{
                	showConfirm({
                        icon: 'warning',
                        content: data
                    });
                }
            });
        },
        cancel: function () {
        }
    });
}

//下载附件
function downLoadFile(fileId){
     location.href='../creditCheck/downloadCreditFile.html?fileId='+fileId;        
}

//获得需要删除的选项集合
function getFileOptArrayForDel(){
	var fullFilenameDel = [];		//需要删除附件全路径
	
	if($('div#fileQueue .uploadifyQueueItem').length != $('input[name=fileNameTask]').length){//已上传附件队列需要删除
		var fileQueueIdNew = [];
		$('div#fileQueue .uploadifyQueueItem').each(function(){
	        fileQueueIdNew.push($(this).attr('id'));
	    });
	    
	    $('.fileInputQueue').each(function(){
	    	if($.inArray('fileInput' + $(this).attr('id'), fileQueueIdNew) < 0){
	    		fullFilenameDel.push($(this).find('input[name=filePath]').val() + '/' + $(this).find('input[name=fileNameOld]').val());
	    		//清除页面话术选项
	    		$(this).remove();
	    	}
	    });
		
	}
	return fullFilenameDel.join(",");
}

//回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    win.refreshList();
	tabs.close(tabs.getSelfId(window));
}

function lookPhoto(fileName,filePath){
    var url = "../creditCheck/showImageDiv.html?fileName="+encodeURI(encodeURI(fileName))+"&filePath="+encodeURI(encodeURI(filePath));
    showDialog({
        id: 'vPhotoDiv',
        title : '申请资料',
        url : url,
        width : 960,
        height : 580
    });
}