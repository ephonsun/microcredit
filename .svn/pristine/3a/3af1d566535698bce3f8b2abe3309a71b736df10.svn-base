var t1;
$(function(){
    //取消
    $('#uploadSelectCannel,#uploadingCannel,#importCannel,#setupCancel,#setupClose').click(function(){
        setupClose();
    });
    /*点击上传*/
    $("#nextUpload").click(function(){
        var url=$("#uploadSpan :text").val();
        if (url)upLoadFile();
        else showConfirm({
            icon: 'warning',
            content: '请选择文件'
        });;
    });

    /*Excel模板下载*/
    $("#downloads").click(function(){
        downloadIndustryExcel();
    });
    /**下载失败信息*/
    $("#failMessageDiv").click(function(){
        failMessage();
    });

    $('#importIndustry').bind("click",function(){
        importIndustry();
        //执行完解除绑定click事件
        $('#importIndustry').unbind('click');
    });
})
/*下载导入行业指引模板*/
function downloadIndustryExcel(){
    window.location="../loanIndustryGuidelines/downloadExcelFile.html";
}
function failMessage(){
    var filename = $("#filename").val();
    if(filename){
        window.location="../loanIndustryGuidelines/downloadFailFile.html?filename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000;
    }
}
/*得到行业指引导入结果信息*/
function getImportResult(){
    $("#mask").hide();
    window.clearInterval(t1);
    jQuery.ajax({
        type : 'post',
        url : '../loanIndustryGuidelines/getImportResult.html',
        data:{},
        success : function(text) {
            var json = $.parseJSON(text);
            $("#total").html(json.total);
            $("#successImport").html(json.success);
            $("#updateMessage").html(json.update);
            $("#newMessage").html(json.insert);
            $("#importFailure").html(json.fail);
            var filename = $("#filename").val(json.filename);
            if(json.fail==0){
                $("#failMessageDiv").hide();
            }
        }
    });
}
/*判断上传文件类型*/
function checkFileType(id){
    var value=id.value;
    if (value){
        if(value.split(".")[value.split(".").length-1]!="xlsx"){
            showConfirm({
                icon: 'warning',
                content: '上传文件格式必须是.xlsx文件，您上传的文件类型为'+value.split(".")[value.split(".").length-1]+'，请重新上传'
            });
            resetFile();
        }else {
            //var localFilename = getFileValue(obj);
            $("#uploadSpan :text").val(value);
            //$("#localFilename").val(localFilename);
        }
    }else showConfirm({
        icon: 'warning',
        content: '请选择文件！'
    });
}
/*页卡切换*/
function judge(n){
    var step = '#step'+ n;
    $("#step1,#step2,#step3,#step4").removeClass("on");
    $(step).addClass("on");
    var x='#page'+n
    $("#page1,#page2,#page3,#page4").hide();
    $(x).show();
}

/*还原初始值*/
var html=$('#uploadSpan').html();
function resetFile(){
    $('#uploadSpan').html(html);
}

//上传文件
function upLoadFile(){
    var filename = $("#file").val();
    if(filename!=""){
        ajaxFileUpload({
            url:"../loanIndustryGuidelines/doUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(data){
                var text = data.responseText;
                if(html=="error"){
                    showConfirm({
                        icon: 'warning',
                        content: '当前选择的上传文件不正确,请选择正确的文件后重新上传!'
                    });
                    window.location.reload();
                    return false;
                }else{
                    if(text == "" || text == "error"){
                        showConfirm({
                            icon: 'warning',
                            content: '文件路径读取错误!'
                        });
                        return;
                    }else if(text == "文件不能超过2M"){
                        showConfirm({
                            icon: 'warning',
                            content: '文件不能超过2M!'
                        });
                        return;
                    }
                	loadExcelHead(text);
                	$("#saveFilename").val(text);
                }
            }
        });
    }
    else{
        showConfirm({
            icon: 'warning',
            content: '请先选择上传文件！'
        });
    }
}

function importIndustry(){
    var leftColumns = [];
    var rightFields = [];
    $("select[name='leftColumns']").each(function(){
        leftColumns.push($(this).val());
    })
    $("select[name='rightFields']").each(function(){
        rightFields.push($(this).val());
    })

    if (isRepeat(rightFields)) {
        showConfirm({
            icon: 'warning',
            content: '不能有重复元素！'
        });
        return;
    }
    var postJson={};
    postJson['leftColumns'] = leftColumns.join(",");
    postJson['rightFields'] = rightFields.join(",");
    postJson.importTotal = $("#importTotal").val();
    var saveFilename = $("#saveFilename").val();
    jQuery.ajax({
        type : 'post',
        url : '../loanIndustryGuidelines/doImportIndustryGuide.html?saveFilename='+encodeURI(encodeURI(saveFilename))+"&random="+Math.random()*10000,
        data:postJson,
        success : function(jo) {
            judge(4);
            getImportResult();
        }
    });
    t1 = window.setInterval("getImportProgress()",1000);
};

function getImportProgress(){
    jQuery.ajax({
        type : 'post',
        url : '../loanIndustryGuidelines/getImportProgress.html?random='+Math.random()*10000,
        dataType:"json",
        data:{
            "importTotal":$("#importTotal").val()
        },
        success : function(jo) {
            if(jo.current > 0) {
                $("#mask").css("height",$(document).height());
                $("#mask").css("width",$(document).width());
                $("#mask").show();
                var p = jo.rate;
                var bar = document.getElementById('progress-bar');
                bar.style.width = Math.floor(p * 100) + '%';
                bar.innerHTML = Math.floor(p * 100) + '%';
            }
        }
    });
}

/**
 * 判断数据元素中是否有重复元素
 * @param arr
 * @returns {boolean}
 */
function isRepeat(arr){
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]])
            return true;
        hash[arr[i]] = true;
    }
    return false;
}

function loadExcelHead(filename){
	jQuery.post("../loanIndustryGuidelines/getExcelHeadData.html?random="+Math.random()*1000000,{"saveFilename":filename},
        function(html){
			if(html=="error"){

			}else if(html=="warning"){
                showConfirm({
                    icon: 'warning',
                    content: '请检查对应关系或导入行业指引模板列格式是否正确'
                })
            } else{
				judge(2);
				var elem = $(html);
			    $("#columnDiv").append(elem);
			}
        }
    );
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



