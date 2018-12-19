
$(function(){
    $('select').selectbox();
    banger.verify('#form',{name: 'required', tips: '行业等级必选选择'});
    banger.verify($('#form').find($('.ui-text-text')),
        [{name: 'required', tips: '行业等级必选选择'}]);
    $("#btnSave").click(function(){
        saveIndustryGuide();
    });

    $("#btnCancel").click(function(){
        var dialog = getDialog('getIndustryGuidelinesPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    });
    $("#btnClean").click(function(){
        var loanId = $("#loanId").val();
        var loanProcess = $("#loanProcess").val();
        if("apply" == loanProcess) {
            if(loanId == ""){
                var win = tabs.getIframeWindow("addOrUpdateLoan");
                win.document.getElementById("businessCatalog").value = "";
                win.document.getElementById("display_businessCatalog").value = "";
            }else{
                var win = tabs.getIframeWindow("updateCustomerapply"+$("#loanId").val());
                win.document.getElementById("businessCatalog").value = "";
                win.document.getElementById("display_businessCatalog").value = "";
            }
        }else if("investigate" == loanProcess){
            var win = tabs.getIframeWindow("updateCustomerinvestigate"+$("#loanId").val());
            win.document.getElementById("businessCatalog").value = "";
            win.document.getElementById("display_businessCatalog").value = "";
        }
        var dialog = getDialog('getIndustryGuidelinesPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    });
    //编辑状态
    var gradeThird = $("#gradeThird1").val();
    if(gradeThird != ""){
        queryGradeSecond($("#gradeFirst1").val(),true);
        queryGradeThird($("#gradeSecond1").val(),true);
     }

});
function queryGradeSecond(pid,selected) {
    $("#ngradeSecond,#ngradeThird").val("");
    $("#gradeSecond,#gradeThird").empty();
    if(pid == ""){
        return;
    }
    var url = '../loanIndustryGuidelines/getIndustryGuidelineByGroup.html';
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType:'json',
        async : false,
        data : {
                'industryGrade':'grade_second',
                'industryParGrade':'grade_first',
                'industryParName':$("#gradeFirst").val()
                },
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option value =" + list[i].cols.gradeSecond +" >" + list[i].cols.gradeSecond + "</option>";
            }
            $("#gradeSecond").empty().append(comment);
            if(selected == true){
                $("#gradeSecond").find("option[value = '"+$("#gradeSecond1").val()+"']").attr("selected","selected");
                $("#ngradeSecond").val($("#gradeSecond1").val());
            }
        }
    });
}

function queryGradeThird(pid,selected) {
    $("#ngradeThird").val("");
    $("#gradeThird").empty();
    if(pid == ""){
        return;
    }
    var url = '../loanIndustryGuidelines/getIndustryGuidelineByGroup.html';
    jQuery.ajax({
        type : 'post',
        url : url,
        dataType:'json',
        async : false,
        data : {
            'industryGrade':'grade_third',
            'industryParGrade':'grade_second',
            'industryParName':$("#gradeSecond").val()
        },
        success : function(data) {
            var comment = "<option></option>";
            var list = data.rows;
            for(var i = 0;i < list.length; i++){
                comment +="<option value =" + list[i].cols.gradeThird +" >" + list[i].cols.gradeThird + "</option>";
            }
            $("#gradeThird").empty().append(comment);
            if(selected == true){
                $("#gradeThird").find("option[value = '"+$("#gradeThird1").val()+"']").attr("selected","selected");
                $("#ngradeThird").val($("#gradeThird1").val());
            }
        }
    });
}

function saveIndustryGuide() {
    if (!banger.verify('#form')) {
        return false;
    }
    // var displayIndustry = $("#gradeThird").val();
    var displayIndustry = $("#gradeFirst").val()+ "_"+$("#gradeSecond").val()+"_"+ $("#gradeThird").val();
    var industry = $("#gradeFirst").val()+ ","+$("#gradeSecond").val()+","+ $("#gradeThird").val();
    var loanId = $("#loanId").val();
    var loanProcess = $("#loanProcess").val();
    if("apply" == loanProcess) {
        if(loanId == ""){
            var win = tabs.getIframeWindow("addOrUpdateLoan");
            win.document.getElementById("businessCatalog").value = industry;
            win.document.getElementById("display_businessCatalog").value = displayIndustry;
        }else{
            var win = tabs.getIframeWindow("updateCustomerapply"+$("#loanId").val());
            win.document.getElementById("businessCatalog").value = industry;
            win.document.getElementById("display_businessCatalog").value = displayIndustry;
        }
    }else if("investigate" == loanProcess){
        var win = tabs.getIframeWindow("updateCustomerinvestigate"+$("#loanId").val());
        win.document.getElementById("businessCatalog").value = industry;
        win.document.getElementById("display_businessCatalog").value = displayIndustry;
    }
    var dialog = getDialog('getIndustryGuidelinesPage');
    setTimeout(function() {
        dialog.close();
    }, 0);
}