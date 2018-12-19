//根据字段类型隐藏选项列表
function setstate1(){
    $("#Optbl1 tbody tr:first a[nm='up']").attr('disabled', 'disabled');
    $("#Optbl1 tbody tr:first a[nm='up']").addClass('ui-link-disabled');

    $("#Optbl1 tbody tr:last a[nm='down']").attr('disabled', 'disabled');
    $("#Optbl1 tbody tr:last a[nm='down']").addClass('ui-link-disabled');

    $('#Optbl1 tbody tr').removeAttr('class');
    $('#Optbl1').dataTable();
}

function setstate2(){
    $("#Optbl2 tbody tr:first a[nm='up']").attr('disabled', 'disabled');
    $("#Optbl2 tbody tr:first a[nm='up']").addClass('ui-link-disabled');

    $("#Optbl2 tbody tr:last a[nm='down']").attr('disabled', 'disabled');
    $("#Optbl2 tbody tr:last a[nm='down']").addClass('ui-link-disabled');

    $('#Optbl2 tbody tr').removeAttr('class');
    $('#Optbl2').dataTable();
}

// 业务逻辑
$(function(){
	//初始化下拉框
	$('#fieldType').selectbox();

       // alert( $('#sd').val());

	//属性字段名称非空及中文数字下划线字母输入限制验证
    //banger.verify("#fieldName", [{ name: "required", tips: "“名称”必须填写！"},{name:"account", tips:"“名称”只能输入中文！",rule:/^[\u4e00-\u9fa5]+$/},fieldNameRepeatRule], null);
    //banger.verify("#fieldColumn", [{ name: "required", tips: "“列名”必须填写！"},{name:"account", tips:"“列名”只能英文加下划线加英文！",rule:/^(?!_)(?!.*?_$)[a-zA-Z]+([_][a-zA-Z]+)+$/},fieldNameRepeatRule2], null);


    //属性字段名称非空及输入验证
    //banger.verify("#Option1", [{name:"account", tips:"“名称”只能由中文、字母、数字和下划线组成！",rule:/^[a-zA-Z0-9-_\u4e00-\u9fa5]+$/}], null);
    //banger.verify("#Option2", [{name:"account", tips:"“名称”只能由中文、字母、数字和下划线组成！",rule:/^[a-zA-Z0-9-_\u4e00-\u9fa5]+$/}], null);

    //单位非空验证
    banger.verify("#fieldNumberUnit", [{ name: "required", tips: "“单位”必须填写！",rule:funMeasurement}], null);

	// 保存
	$('#btnSave').click(function() {
		//if (!banger.verify('.ui-form-fields')) {
		//return false;
		//}
		if(selectAndAccout()){
            //submit('saveClose');
            submit('saveContinue');
        }

	});

	// 保存并新建
	$('#btnSaveNew').click(function() {
		if (!banger.verify('.ui-form-fields')) {
		return false;
		}
        selectAndAccout();

        submit('saveContinue');
	});
//检验下拉类型选项
	function selectAndAccout() {
       var type = $('#fieldType').val();

        if (type==null || type ==''){

            showConfirm({
                icon: 'warning',
                content: "数据类型不能为空"
            });
            return false;
        }
        return true;
    }
    function  funMeasurement(){

        var bool = true;
        var type = $("#fieldType").selectbox().val();

        if(type == "Number" || type == "Date" || type == "Decimal"){
            if($("#fieldNumberUnit").val() == ""){
                bool = false;
            }else{
                bool = true;
            }
        }
        return bool;
    }
	// 取消
	$('#btnCancel').click(function() {
        //showCancelConfirmForEdit(function(){
            fn.cancel();
        //});
	});

    $("#fieldType").selectbox().change(function(){
        var val=$(this).val();
        switch(val){
            case "Text":
                $(".unit-item").hide();jQuery("#shortTextSelect").show();    $('select').selectbox({});break;
            case "TextArea":
                $(".unit-item").hide();jQuery("#longTextSelect").show();   $('select').selectbox({}); break;
            case "Number":
                $(".unit-item").hide(); jQuery("#unit").show();jQuery("#numberSelect").show(); $('select').selectbox({}); break;
            case "Float":
                $(".unit-item").hide(); jQuery("#unit").show();$('select').selectbox({}); break;
            case "Date":
                $(".unit-item").hide(); break;
            case "Decimal":
                $(".unit-item").hide();jQuery("#unit").show(); break;
            case "Ratio":
                $(".unit-item").hide();break;
            case "YesNo":
                $(".unit-item").hide(); break;
            case "Select":
                 $(".unit-item").hide();  break;
            case "MultipleSelect":
                $(".unit-item").hide();  break;
            default:
                $(".unit-item").hide(); break;
        }
    });

    setstate1();
    setstate2();

    $("#btnAdd1").click(function(){
        var bool = banger.verify('#boxOption1');
        if(!bool){
            return false;
        }
        $("#Optbl1 tbody tr:last a[nm='down']").removeAttr('disabled').removeClass('ui-link-disabled');
        Shared($("#Optbl1"), $("#Option1"));
        setstate1();
    });
    $("#btnAdd2").click(function(){
        var bool = banger.verify('#boxOption2');
        if(!bool){
            return false;
        }
        $("#Optbl2 tbody tr:last a[nm='down']").removeAttr('disabled').removeClass('ui-link-disabled');
        Shared($("#Optbl2"), $("#Option2"));
        setstate2();
    });

    function Shared(obj, objOption){
        var val = $.trim(objOption.val());
        if(val.length==0){
		    showConfirm({ icon: 'warning', content: '新选项值不能为空！' });
            return false;
        }
        if(obj.find("tbody").find("label[title='" + val + "']").length > 0){
        	showConfirm({ icon: 'warning', content: '已存在相同的选项！' });
            return false;
        }
        var tr = $("<tr style='height:28px;'><td><label title=\"" + val + "\">" + val + "</label></td><td>" + BuildHtml() + "</td></tr>");
        tr.appendTo(obj.find("tbody"));
        tr.find("a[nm='del']").bind("click", function(){
            tr.remove();
            setstate1();
            setstate2();
        });
        tr.find("a[nm='up']").bind("click", function(){
            if(tr.prevAll().length != 0){
                if(tr.prevAll().length == 1){
                    tr.prev().find("a[nm='up']").removeAttr('disabled').removeClass('ui-link-disabled');
                }
                if(tr.prevAll().length >= 1){
                    tr.find("a[nm='down']").removeAttr('disabled').removeClass('ui-link-disabled');
                }
                tr.prev().before(tr);
                setstate1();
                setstate2();
            }
        });
        tr.find("a[nm='down']").bind("click", function(){
            if(tr.nextAll().length != 0){
                if(tr.nextAll().length == 1){
                    tr.next().find("a[nm='down']").removeAttr('disabled').removeClass('ui-link-disabled');
                }
                if(tr.nextAll().length >= 1){
                    tr.find("a[nm='up']").removeAttr('disabled').removeClass('ui-link-disabled');
                }
                tr.before(tr.next());
                setstate1();
                setstate2();
            }
        });
        objOption.val("");
    }
    function BuildHtml(){
        var btn = "<div style='margin-left:110px;'>"
                +"<a href='#2' class='ui-link-button' nm='del'><div  style='display:inline;text-align:center;line-height:20px;float:left;margin-left:2px;'>删除</div></a> "
                + "<a href='#2' class='ui-link-button' nm='up'><div  style='display:inline;text-align:center;line-height:20px;float:left;margin-left:2px;'>上移</div></a> "
                + "<a href='#2' class='ui-link-button' nm='down'><div  style='display:inline;text-align:center;line-height:20px;float:left;margin-left:2px;'>下移</div></a>"
                +"</div>";
        return btn;
    }


   // layout.initSelect();
    banger.verify('#txtFieldIsDisplayActs',{ name: "required", tips: "必须填写！"});
    banger.verify('#txtFieldIsQueryActsIds',{ name: "required", tips: "必须填写！"});

    $("#insertFieldForm").click(
        function(){
            banger.verify('#insertFieldForm');
            //banger.verify('#fieldIsQueryActsBox');
        }
    );
    //$(".hover").click(function(){
    //    alert("hover");
    //});

    changeRadioBox('isDisplay','txtFieldIsDisplayActs','fieldIsDisplayActsBox','fieldIsDisplayActsIds');
    $("[name='isDisplay']").change(function() {
        changeRadioBox('isDisplay','txtFieldIsDisplayActs','fieldIsDisplayActsBox','fieldIsDisplayActsIds');
    });

    changeRadioBox('isQuery','txtFieldIsQueryActsIds','fieldIsQueryActsBox','fieldIsQueryActsIds');
    $("[name='isQuery']").change(function() {
        changeRadioBox('isQuery','txtFieldIsQueryActsIds','fieldIsQueryActsBox','fieldIsQueryActsIds');
    });
});

function changeRadioBox(idName, txtId, divId,inputId) {
    var val=$("input[name='"+idName+"']:checked").val();
    if (val == 1) {
        banger.enableVerify('#'+txtId);
        $("."+divId).show();
    }else if(val == 0){
        banger.disabledVerify('#'+txtId);
        $('#'+inputId).val('');
        $("."+divId).hide();
    }
}

var fn = {};

fn.saveClassField = function(){
	submit('saveClose');
}

fn.saveNewClassField = function(){
	submit('saveContinue');
}

fn.cancel = function(){
	// 关闭弹出框
	var dialog = getDialog('insertTemplateField');
	dialog.close();
}

function submit(code){

    var result = "";

    var fieldType = $("#fieldType").selectbox().val();

    if(fieldType != "Number" && fieldType != "Decimal" && fieldType != "Float"){
        $("#fieldNumberUnit").val("");
    }
    if(fieldType == "Ratio"){
        $("#fieldNumberUnit").val("%");
    }

    if(fieldType != "Number"){
        $("#numberSelects").val("");
    }

    var texts ="";
    if (fieldType == "Text"  ){
        texts=$("#shortText").selectbox().val();
    }
    if (fieldType == "TextArea"){
        texts=$("#longText").selectbox().val();
    }

    var fielConstraintRule ="";
    if (fieldType == "Number"  ){
        fielConstraintRule=$("#numberSelects").selectbox().val();
    }

     var  codeData = result;

     var  typeId = $("#typeId").val();
    var saveType = code;//
    var fieldColumnDisplay = $("#fieldName").val();
    //var fieldTypes = $("#fieldType").val();
    var fieldColumn = $("#fieldColumn").val();
    var fieldNumberUnit =$("#fieldNumberUnit").val();
    var fieldIsRequired =$("input[name='fieldIsRequired']:checked").val();
    var fieldWebIsShow =$("input[name='fieldWebIsShow']:checked").val();
    var fieldAppIsShow =$("input[name='fieldAppIsShow']:checked").val();
    var fieldisplay =$("input[name='fieldisplay']:checked").val();


    var fieldQuery ="";
       var data ={
           fielConstraintRule:fielConstraintRule,
           texts : texts,
           codeData  :codeData ,
           typeId  :typeId ,
           saveType  :saveType,
           fieldColumnDisplay   : fieldColumnDisplay ,
           fieldType    : fieldType ,
           fieldColumn    : fieldColumn ,
           fieldNumberUnit   :fieldNumberUnit,
           fieldIsRequired   : fieldIsRequired,
           fieldWebIsShow   : fieldWebIsShow,
           fieldAppIsShow   : fieldAppIsShow,
           fieldisplay:fieldisplay,
           fieldQuery:fieldQuery
       };
    //   alert(""+typeId+saveType+","+fieldColumnDisplay+","+fieldType+","+fieldColumn+","+fieldNumberUnit+","+fieldIsRequired+","+fieldWebIsShow+","+fieldAppIsShow)
    var url = '../tableColumn/insertTemplateField.html';
    var bool= false;
    jQuery.ajax({
        type: 'post',
        url: url,
        data:data,
        dataType:'json',
        async: false,
        success: function(data){
            bool = data.success;

             if(bool){
                addResult(code);
             }else{

                 showConfirm({//warning succeed
                     icon: 'warning',
                     content: "列名已存在"
                 });

             }

        }
    });
    
}

//请求后的回调函数
function addResult(msg){
    if(msg == "saveClose"){
        var dialog = getDialog('insertTemplateField');
		var win = tabs.getIframeWindow(dialog.config.tabId);

        win.location.reload(true);
		setTimeout(function(){dialog.close();},0);
    }
    else if(msg == "saveContinue"){
    	toResetForm('#insertFieldForm');
    	$("#Optbl1 tbody").find("tr").each(function(i){
           $(this).remove();
        });
    	$("#Optbl2 tbody").find("tr").each(function(i){
    		$(this).remove();
        });
    	var dialog = getDialog('insertTemplateField');
    	var win = tabs.getIframeWindow(dialog.config.tabId);
		win.refresh();
    }
    else{
    	showConfirm({ icon: 'warning', content: msg });
    }
}



var fieldNameRepeatRule = {
    name: 'checkfieldColumnNameRule',
    tips: '与已有数据重复',
    rule: function () {
        var rule = this, bool = true, data = {
            tableId : $("#typeId").val(),
            fieldColumnDisplay : $("#fieldName").val(),
            fieldColumn : null
        };
        var url = '../tableColumn/checkfieldColumnNameByTableId.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            data: data,
            dataType:'json',
            async: false,
            success: function (result) {
                bool = result.success;
                if(!bool){
                }
            }
        });
        return bool;
    }
};
var fieldNameRepeatRule2 = {
    name: 'checkfieldColumnNameRule',
    tips: '与已有数据重复',
    rule: function () {
        var rule = this, bool = true, data = {
            tableId : $("#typeId").val(),
            fieldColumnDisplay :null,
            fieldColumn : $("#fieldColumn").val()

        };
        var url = '../tableColumn/checkfieldColumnNameByTableId.html';
        jQuery.ajax({
            type: 'post',
            url: url,
            data: data,
            dataType:'json',
            async: false,
            success: function (result) {
                bool = result.success;
                if(!bool){
                }
            }
        });
        return bool;
    }
};