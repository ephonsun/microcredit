var loanId=$("#loanId").val();
var loanClassId=$("#loanClassId").val();

function addNewAsset(columnName,itemName){
    var url='../assets/getAddAssetsPage.html?columnName='+columnName+'&loanClassId='+loanClassId+'&loanId='+loanId;
    showDialog({
        id: 'updateAssets',
        title: itemName,
        url: url,
        width: 660,
        height: 330,
        tabId: tabs.getSelfId(window)
    });
}


/*获取编辑界面*/
function updateAssets(columnName,id,itemName){
    var url='../assets/getUpdateAssetsPage.html?columnName='+columnName+'&id='+id+'&updordet=1';
    showDialog({
        id: 'updateAssets',
        title: itemName,
        url: url,
        width: 660,
        height: 330,
        tabId: tabs.getSelfId(window)
    });
}

/*获取详情界面*/
function detailAssets(columnName,id,itemName){
    var url='../assets/getUpdateAssetsPage.html?columnName='+columnName+'&id='+id+'&updordet=2';
    showDialog({
        id: 'detailAssets',
        title: itemName,
        url: url,
        width: 660,
        height: 330,
        tabId: tabs.getSelfId(window)
    });
}

/*移除资产负债调查项*/
function deleteAssets(columnName,id){
    showConfirm({
        icon:'confirm',
        content:'您确定要彻底删除该项吗？删除后将无法恢复。',
        ok:function(){
            jQuery.ajax({
                url: '../assets/deleteAssets.html',
                type:'POST',
                dataType:'json',
                data: {"columnName":columnName,"id":id,"loanId":loanId},
                sync: false,
                success: function(result){
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        reflushGrid(columnName);
                        reflushTitle(columnName);
                    }else {
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function() {}
    });
}



function reflushGrid(columnName) {
    jQuery.ajax({
        url: '../assets/reflushGrid.html',
        type:'POST',
        dataType:'html',
        data: {"columnName":columnName,"loanId":loanId,"loanClassId":loanClassId},
        sync: false,
        success: function(result){
         $('#'+columnName).html(result);
        }
    });
}

function reflushTitle(columnName) {
    jQuery.ajax({
        url: '../assets/reflushTitle.html',
        type:'POST',
        dataType:'html',
        data: {"columnName":columnName,"loanId":loanId},
        sync: false,
        success: function(result){
           if(columnName.indexOf("ASSETS_")==0){
               $("#assets").html(result);
           }else if(columnName.indexOf("DEBTS_")==0) {
               $("#debts").html(result);
           }
        }
    });
}
