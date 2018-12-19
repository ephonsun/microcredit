$(function(){	
	//关闭
	$('#btnCancel').click(function() {	    
		 tabs.close(tabs.getSelfId(window));	 
	});

});


//下载附件
function downLoadFile(fileId){
     location.href='../creditCheck/downloadCreditFile.html?fileId='+fileId;        
}

function lookPhoto(fileName,filePath){
    var url = "../creditCheck/showImageDiv.html?fileName="+encodeURI(encodeURI(fileName))+"&filePath="+encodeURI(encodeURI(filePath));
    showDialog({
        id: 'vPhotoDiv',
        title : '申请资料',
        url : url,
        width : 960,
        height : 500
    });
}
