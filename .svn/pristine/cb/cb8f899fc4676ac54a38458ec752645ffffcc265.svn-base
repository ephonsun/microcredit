/*判断上传文件类型*/
function checkFileType(id){
	var value=id.value
	if (value){
		if(value.split(".")[value.split(".").length-1]!="apk"){
            showConfirm({
                icon: 'warning',
                content:'升级包文件格式必须是apk格式，您上传的文件类型为'+value.split(".")[value.split(".").length-1]+'，请重新上传'
            });
            resetFile();
		}else {
        	$("#uploadSpan :text").val(value);
            //获取上传apk的升级版本号和显示版本号
            getAppUploadFileVersion(value);
		}
	}else  showConfirm({
        icon:"warning",
        content:"请选择文件！"
    });
}
//获取上传apk的升级版本号和显示版本号
function getAppUploadFileVersion(filename){
    if(filename!=""){
        ajaxFileUpload({
            url:"../sysAppVersion/getAppUploadFileVersion.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(result){
                result = JSON.parse(result.responseText);
                if(result.success != true){
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                    window.location.reload();
                    return false;
                }else{
                    $("#apkVersionShow").val(result.apkVersionShow);
                    $("#apkVersionUpd").val(result.apkVersionUpd);
                }
            }
        });
    }
    else{
        showConfirm({
            icon:"warning",
            content:"请先选择升级文件！"
        });
    }
}
/*页卡切换*/
function judge(n){
	var x='#page'+n
	$("#page1,#page2,#page3").hide();
	$(x).show();
    $("#step1,#step2,#step3").removeClass("on");
    $("#step" + n).addClass("on");
}
/*点击上传*/
$("#nextUpload").click(function(){
	var url=$("#uploadSpan :text").val();
	if (url)upLoadFile();
	else showConfirm({
        icon:"warning",
        content:"请选择文件！"
    });
});
/*还原初始值*/
var html=$('#uploadSpan').html();
function resetFile(){
    $('#uploadSpan').html(html);
}

var apkVersion;
$(function(){//取消
	$('#uploadSelectCannel,#uploadingCannel,#setupCancel,#setupClose').click(function(){
	    setupClose();
	});

    initMaxlengthTips('#updateContent','#tips');
	
	//启用升级包
	$('#setupConfirm').click(function(){
	    setupConfirm();
	});
    $("select").selectbox();
    banger.verify('#apkVersionShow', {name : 'required', tips: '显示版本号必填'});
    banger.verify('#apkVersionUpd',[
        {name: 'required', tips: '升级版本号必须填写'},
        {name: 'posiInteger', tips: '升级版本号必须为正整数'},
        checkApkVersionUpdRule]);
    banger.verify('#updateContent', {name : 'required', tips: '升级内容不能为空'});
})
//上传文件
function upLoadFile(){
    // var filename = $("#file").val();
    var filename = $("#uploadSpan :text").val();;
    var updateContent = $("#updateContent").val();
    if(updateContent.length>300){
        showConfirm({
            content: '字数不能大于300'
        });
        return false;
    }
    if (!banger.verify('#baseForm')) {
        showConfirm({
            content: '请完善升级的版本信息'
        });
        return false;
    }
    if(filename!=""){
		judge(2);
        getApkVersionInfo();
        ajaxFileUpload({
            url:"../sysAppVersion/doAppUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(result){
                result = JSON.parse(result.responseText);
                if(result.success != true){
                    showConfirm({
                        icon: 'warning',
                        content: result.cause
                    });
                	window.location.reload();
                	return false;
                }else{
                    addAppVersion(result);
                }
            }
        });
    }
    else{
        showConfirm({
            icon:"warning",
            content:"请先选择升级文件！"
        });
    }
}
function addAppVersion(result){
    apkVersion.apkName = result.fileName;
    apkVersion.apkUrl = result.fileUrl;
    $.ajax({
        url : '../sysAppVersion/insertAppVersion.html',
        type : 'POST',
        data : apkVersion,
        success : function () {
            judge(3);
        }
    }) ;
}
function getApkVersionInfo(){
    apkVersion = {};
    apkVersion.isUpdate = $("#isUpdate").val();
    apkVersion.apkVersionShow = $("#apkVersionShow").val();
    apkVersion.apkVersionUpd = $("#apkVersionUpd").val();
    apkVersion.updateContent = $("#updateContent").val();
}
var checkApkVersionUpdRule = {
    name : 'checkApkVersionUpdRule',
    tips : '升级版本号必须大于上个版本号！',
    rule : function() {
        var rule = this, bool = false;
        var lastVersionUpd = $("#lastVersionUpd").val();
        var versionUpd = $("#apkVersionUpd").val();
        if(parseFloat(versionUpd) > parseFloat(lastVersionUpd)) {
            bool = true;
        }
        return bool;
    }
};

//取消
function setupClose(){
    var win = tabs.getIframeWindow("appUpdate");
    if(win)
        win.location.reload(true);
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



