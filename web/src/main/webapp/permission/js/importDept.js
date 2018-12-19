$(function(){
    //取消
    $('#uploadSelectCannel,#uploadingCannel,#importCannel,#setupCancel,#setupClose').click(function(){
        setupClose();
    });

    /*点击上传*/
    $("#nextUpload").click(function(){
        var url=$("#uploadSpan :text").val();
        if (url)upLoadFile();
        else alert("请选择文件！");
    });

    /*Excel模板下载*/
    $("#downloads").click(function(){
        downloadDeptExcel();
    });
    /**下载失败信息*/
    $("#failMessageDiv").click(function(){
        failMessage();
    });

    $('#importDept').bind("click",function(){
        importDept();
        //执行完解除绑定click事件
        $('#importDept').unbind('click');
    });
})
/*下载导入机构模板*/
function downloadDeptExcel(){
    window.location="../dept/downloadExcelFile.html";
}
function failMessage(){
    var filename = $("#filename").val();
    if(filename){
        window.location="../dept/downloadFailFile.html?filename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000;
    }
}
/*得到机构导入结果信息*/
function getImportResult(){
    jQuery.ajax({
        type : 'post',
        url : '../dept/getImportResult.html',
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
        if(value.split(".")[value.split(".").length-1]!="xls"){
            showConfirm({
                icon: 'warning',
                content: '上传文件格式必须是.xls文件，您上传的文件类型为'+value.split(".")[value.split(".").length-1]+'，请重新上传'
            });
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
            url:"../dept/doUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
            id:'file',
            callback:function(data){
                var text = data.responseText;
                if(html=="error"){
                    alert("当前选择的上传文件不正确,请选择正确的文件后重新上传!");
                    window.location.reload();
                    return false;
                }else{
                	loadExcelHead(text);
                	$("#saveFilename").val(text);
                }
            }
        });
    }
    else{
        alert("请先选择上传文件!");
    }
}

function importDept(){
    var leftColumns = [];
    var rightFields = [];
    $("select[name='leftColumns']").each(function(){
        leftColumns.push($(this).val());
    })
    $("select[name='rightFields']").each(function(){
        rightFields.push($(this).val());
    })

    if (isRepeat(rightFields)) {
        alert('不能有重复元素！');
        return;
    }
    var postJson={};
    postJson['leftColumns'] = leftColumns.join(",");
    postJson['rightFields'] = rightFields.join(",");
    var saveFilename = $("#saveFilename").val();
    jQuery.ajax({
        type : 'post',
        url : '../dept/doImportDept.html?saveFilename='+encodeURI(encodeURI(saveFilename)),
        data:postJson,
        success : function(jo) {
          /*  showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });*/
            judge(4);
            getImportResult();
        }
    });
};

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
	jQuery.post("../dept/getExcelHeadData.html?random="+Math.random()*1000000,{"saveFilename":filename},
        function(html){
			if(html=="error"){

			}else if(html=="warning"){
                showConfirm({
                    icon: 'warning',
                    content: '请检查对应关系或导入部门模板列格式是否正确'
                })
            }else{
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



