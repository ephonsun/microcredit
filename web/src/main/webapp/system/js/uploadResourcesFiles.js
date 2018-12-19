function checkFileType(id){
	var value=id.value
	if (value){
        $("#uploadSpan :text").val(value);
	}else{
        alert("请选择文件！");
    }
}	
/*页卡切换*/
function judge(n){
	var x='#page'+n
	$("#page1,#page2,#page3").hide();
	$(x).show();
}
/*点击上传*/
$("#nextUpload").click(function(){
	var url=$("#uploadSpan :text").val();
	if (url)upLoadFile();
	else alert("请选择文件！");
});
/*还原初始值*/
var html=$('#uploadSpan').html();
function resetFile(){
    $('#uploadSpan').html(html);
}


$(function(){//取消
	$('#uploadSelectCannel,#uploadingCannel,#setupCancel,#setupClose').click(function(){
	    setupClose();
	});
	
	//启用
	$('#setupConfirm').click(function(){
	    setupConfirm();
	});
})
//上传文件
function upLoadFile(){
    var filename = $("#file").val();
    if(filename!=""){
		judge(2);
        ajaxFileUpload({
            url:"../resource/doResourcesUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(a){
                var data = a.responseText;
                if(data=="error"){
                	alert("当前选择的资源文件有异常，请重新选择上传!");
                	window.location.reload();
                	return false;
                }else{
                    judge(3);
                }
            }
        });
    }
    else{
        alert("请先选择升级文件!");
    }
}

//取消
function setupClose(){
    var dialog = getDialog('openResourcesUploadPage');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    win.location.reload();
    setTimeout(function(){dialog.close();},0);
}

function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象
    var obj = ev.target || ev.srcElement; //获取事件源
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
    //判断
    if (flag2 || flag1) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;



