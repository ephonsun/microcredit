/*判断上传文件类型*/
function checkFileType(id){
	var value=id.value
	if (value){
		if(value.split(".")[value.split(".").length-1]!="zip"){
			alert('升级包文件格式必须是zip格式，您上传的文件类型为'+value.split(".")[value.split(".").length-1]+'，请重新上传')
			resetFile();
		}else {      
			//var localFilename = getFileValue(obj);
        	$("#uploadSpan :text").val(value);
       		//$("#localFilename").val(localFilename);
		}
	}else alert("请选择文件！");
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
	
	//启用升级包
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
            url:"../setup/doTransitUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(a){
                var data = a.responseText;
                if(data=="error"){
                	alert("当前选择的升级文件不正确,请选择正确的文件后重新升级!");
                	window.location.reload();
                	return false;
                }else{
                    judge(3);
	                var json = jQuery.parseJSON(data);
	                $("#serviceFilename").val(json.fullfilename);
	                $("#servicePath").val(json.path);
	                $("#infoMessage").empty();
                    $("#infoMessage").append("<dd> 文件名: "+json.filename+"</dd>");
                    $("#infoMessage").append("<dd> 保存路径: "+json.path+"</dd>");
                    $("#infoMessage").append("<dd> 版本: "+json.version+"</dd>");
                    $("#infoMessage").append("<dd> MD5: "+json.md5+"</dd>");
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
   setTimeout(function(){tabs.close(tabs.getSelfId(window))},0);
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



