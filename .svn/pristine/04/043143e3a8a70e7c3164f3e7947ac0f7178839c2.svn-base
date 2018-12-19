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

    $('#importPotential').bind("click",function(){
        importPotential();
    });
})
/*下载导入潜在客户模板*/
function downloadIndustryExcel(){
    window.location="../custPotentialCustomers/downloadExcelFile.html";
}
function failMessage(){
    var filename = $("#filename").val();
    if(filename){
        window.location="../custPotentialCustomers/downloadFailFile.html?filename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000;
    }
}
/*得到潜在客户导入结果信息*/
function getImportResult(){
    $("#mask").hide();
    window.clearInterval(t1);
    jQuery.ajax({
        type : 'post',
        url : '../custPotentialCustomers/getImportResult.html',
        data:{},
        success : function(text) {
            var json = $.parseJSON(text);
            $("#total").html(json.total);
            $("#successImport").html(json.success);
            // $("#updateMessage").html(json.update);
            $("#newMessage").html(json.success);
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
            url:"../custPotentialCustomers/doUpload.html?fullFilename="+encodeURI(encodeURI(filename))+"&random="+Math.random()*10000,
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
                    }else if(text == "文件不能超过5M"){
                        showConfirm({
                            icon: 'warning',
                            content: '文件不能超过5M!'
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

function importPotential(){
    //归属类型
    var potentialTyte;
    //归属值
    var potentialValue;
    var roleId = $("#taskObj").val();
    potentialTyte = roleId;
    if (roleId == 2) {
        //团队
        potentialValue = $('#teamGroupId').val();
        if(potentialValue == ""){
            showConfirm({
                icon:"warning",
                content:"归属团队不能为空"
            })
            return;
        };
    } else if (roleId == 1) {
        //客户经理
        potentialValue = $('#userIdSelect').val();
        if(potentialValue == ""){
            showConfirm({
                icon:"warning",
                content:"归属客户经理不能为空"
            })
            return;
        };
    }
    var leftColumns = [];
    var rightFields = [];
    $("select[name='leftColumns']").each(function(){
        leftColumns.push($(this).val());
    })
    $("select[name='rightFields']").each(function(index,element){
        if($(this).val()!=""){
            rightFields.push($(this).val());
        }else{
            rightFields.push(index);
        }
    })

    var str = rightFields.join(",");
    if(str.indexOf("customerName") < 0){
        showConfirm({
            icon: 'warning',
            content: '客户姓名（必填）为必填项！'
        });
        return;
    }
    if(str.indexOf("telephoneNumber") < 0){
        showConfirm({
            icon: 'warning',
            content: '联系电话（必填）为必填项！'
        });
        return;
    }
    if (isRepeat(rightFields)) {
        showConfirm({
            icon: 'warning',
            content: '不能有重复元素！'
        });
        return;
    }
    //执行完解除绑定click事件
    $('#importPotential').unbind('click');
    var postJson={};
    postJson['leftColumns'] = leftColumns.join(",");
    postJson['rightFields'] = rightFields.join(",");
    postJson['potentialTyte'] = potentialTyte;
    postJson['potentialValue'] = potentialValue;
    postJson['importTotal'] = $("#importTotal").val();
    var saveFilename = $("#saveFilename").val();
    jQuery.ajax({
        type : 'post',
        url : '../custPotentialCustomers/doImportPotentialCustomer.html?saveFilename='+encodeURI(encodeURI(saveFilename))+"&random="+Math.random()*10000,
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
        url : '../custPotentialCustomers/getImportProgress.html?random='+Math.random()*10000,
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
	jQuery.post("../custPotentialCustomers/getExcelHeadData.html?random="+Math.random()*1000000,{"saveFilename":filename},
        function(html){
			if(html=="error"){

			}else if(html=="warning"){
                showConfirm({
                    icon: 'warning',
                    content: '请检查对应关系或导入潜在客户模板列格式是否正确'
                })
            } else{
				judge(2);
				var elem = $(html);
			    $("#columnDiv").append(elem);
                teamLoad();
			}
        }
    );
}

function teamLoad(){
    var groupPermit = $('#groupPermit').val();
    $.getJSON("../taskStat/queryGroupListByGroupPermit.html", {groupPermit: groupPermit},
        function (data) {
            if(data!=null){
                for (var index in data.data) {
                    $('#teamGroupId').append("<option value=" + data.data[index].teamGroupId + ">" + data.data[index].groupName + "</option>");
                }
            }
        });
}

//取消
function setupClose(){
    var win = tabs.getIframeWindow("customerPotentialListPage");
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



