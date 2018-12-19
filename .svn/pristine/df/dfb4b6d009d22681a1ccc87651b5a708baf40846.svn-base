$(function () {
//自定义表单，编辑的移除一条
    $(".head-reduce-icon-ready").click(function(){
        var id = $(this).parent().parent().attr('id');
        var  idVal= $(this).next().find('input').val();
        var old = deleteIds[id];
        if (old == '' || old == null || old == undefined) {
            deleteIds[id]=idVal;
        } else {
            deleteIds[id]=old + ',' + idVal;
        }
        $(this).parent().remove();
    });
    //自定义表单添加一条
    $('.head-add-icon').click(function() {
        var id = $(this).attr("tablename");
        var coId = "co_"+id;
        var clone = $("#"+coId).clone(true);

        clone.find(".head-add-icon").hide();
        clone.find(".head-reduce-icon").show();
        clone.find(".ui-line-dotted").show();
        clone.find(".head-reduce-icon").click(function(){
            $(this).parent().remove();
        });
        clone.show();
        $("#"+id).append(clone);
        $.getJSON('../loan/getNextLbizId.html',{'tableName': id},function (data) {
            if (data){
                clone.attr("id",data);
                clone.find("input[name='field.ID']").val(data);
            }
        });
        layout.initForms(clone);
        if(id=="LBIZ_TRUSTED_PAYMENY"){
            clone.find('[id=counterpartyBankName]').mousedown(function(){
                selectAccountBankLoan($(this));
            });
        }
    });

});


