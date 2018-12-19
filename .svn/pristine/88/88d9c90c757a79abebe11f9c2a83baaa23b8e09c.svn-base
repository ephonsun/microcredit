var loanId=$("#loanId").val();
var columnName=$("#columnName").val();



$(function(){

    //关闭
    $('#btnCancel').click(function() {
        var dialog = getDialog('Maolilv');
        dialog.close();
    });
});


//编辑时间
function updateTime(loanId,createDate,createDateEnd){
    var url = '../loanInvestigate/getUpdateTimePage.html?loanId='+ loanId+'&createDate='+createDate+'&createDateEnd='+createDateEnd;
    showDialog({
        id: 'updateTime',
        title: '更改时间区间',
        url: url,
        width: 560,
        height: 250,
        tabId: tabs.getSelfId(window)
    });
}

//编辑时间(消费类)
function updateConTime(loanId,createDate,createDateEnd){
    var url = '../loanConInvestigate/getConUpdateTimePage.html?loanId='+loanId+'&createDate='+createDate+'&createDateEnd='+createDateEnd;
    showDialog({
        id: 'updateConTime',
        title: '更改时间区间',
        url: url,
        width: 560,
        height: 250,
        tabId: tabs.getSelfId(window)
    });
}


//查看加权毛利率详情
function showMaolilv(id) {
    var url = '../loanInvestigate/getMaolilvPage.html?id=' + id;
    showDialog({
        id: 'Maolilv',
        title: '加权毛利率详情',
        url: url,
        width: 660,
        height: 300,
        tabId: tabs.getSelfId(window)
    });
}
//编辑加权毛利率详情
function updateMaolilv(id){
    var url = '../loanInvestigate/getMaolilvUpdatePage.html?id=' + id;
    showDialog({
        id: 'updateMaolilv',
        title: '编辑加权毛利率',
        url: url,
        width: 660,
        height: 250,
        tabId: tabs.getSelfId(window)
    });
}

//新增加权毛利率详情
function addMaolilv(loanId){
    var url = '../loanInvestigate/getMaolilvAddPage.html?loanId=' +loanId;
    showDialog({
        id: 'addMaolilv',
        title: '新增加权毛利率',
        url: url,
        width: 660,
        height: 250,
        tabId: tabs.getSelfId(window)
    });
}

//新增明细详情
function addMingxi(columnName,loanId,createDate,createDateEnd){
    var url = '../loanInvestigate/getMingxiAddPage.html?columnName=' +columnName+'&loanId='+loanId+'&createDate='+createDate+'&createDateEnd='+createDateEnd;
    showDialog({
        id: 'addMingxi',
        title: '新增明细表',
        url: url,
        width: 660,
        height: 250,
        tabId: tabs.getSelfId(window)
    });
}


//明细显示(可编辑)
function showDetail(columnName,loanId,id) {
    var url = '../loanInvestigate/getDetailPage.html?columnName=' +columnName+'&loanId='+loanId+'&id=' + id+'&just=null';
    showDialog({
        id: 'showDetail',
        title: '明细界面',
        url: url,
        width: 660,
        height: 450,
        tabId: tabs.getSelfId(window)
    });
}

//明细显示(不可编辑)
function showJustDetail(columnName,loanId,id) {
    var url = '../loanInvestigate/getDetailPage.html?columnName=' +columnName+'&loanId='+loanId+'&id=' + id+'&just=just';
    showDialog({
        id: 'showJustDetail',
        title: '明细详情界面',
        url: url,
        width: 660,
        height: 450,
        tabId: tabs.getSelfId(window)
    });
}


//删除加权毛利率详情
function deleteMaolilv(loanId,id) {
    showConfirm({
        icon: 'confirm',
        content: '确定删除？',
        ok:
            function(){
                jQuery.ajax({
                    url: '../loanInvestigate/deleteMaolilv.html?id=' + id,
                    type:'POST',
                    dataType:'json',
                    sync: false,
                    success: function(result){
                        if(result.success){
                            showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });
                            reflushMao(loanId);
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


//删除营业收入
function deleteBsIncome(columnName,loanId,id) {
    showConfirm({
        icon: 'confirm',
        content: '确定删除？',
        ok:
            function(){
                jQuery.ajax({
                    url: '../loanInvestigate/deleteBsIncome.html?id=' + id,
                    type:'POST',
                    dataType:'json',
                    sync: false,
                    success: function(result){
                        if(result.success){
                            showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });

                            reflushGrid(loanId,columnName);
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




//新增明细详情(消费类)
function addConMingxi(columnName,loanId,createDate,createDateEnd){
    var url = '../loanConInvestigate/getConMingxiAddPage.html?columnName=' + columnName+'&loanId='+loanId+'&createDate='+createDate+'&createDateEnd='+createDateEnd;
    showDialog({
        id: 'addConMingxi',
        title: '新增明细表',
        url: url,
        width: 660,
        height: 300,
        tabId: tabs.getSelfId(window)
    });
}


//明细显示(消费类)
function showConDetail(id,createDate,createDateEnd) {
    var url = '../loanConInvestigate/getConDetailPage.html?id=' + id+'&createDate='+createDate+'&createDateEnd='+createDateEnd+'&just=null';
    showDialog({
        id: 'showConDetail',
        title: '编辑',
        url: url,
        width: 660,
        height: 300,
        tabId: tabs.getSelfId(window)
    });
}


//删除营业收入(消费类)
function deleteConBsIncome(columnName,loanId,id) {
    showConfirm({
        icon: 'confirm',
        content: '确定删除？',
        ok:
            function(){
                jQuery.ajax({
                    url: '../loanConInvestigate/deleteConBsIncome.html?id=' + id,
                    type:'POST',
                    dataType:'json',
                    sync: false,
                    success: function(result){
                        if(result.success){
                            showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });
                            reflushGrid(loanId,columnName);

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

//显示营业收入及其他（消费类）
function showConBsIncome(createDate,createDateEnd,id) {
    var url = '../loanConInvestigate/getConDetailPage.html?createDate=' + createDate+'&createDateEnd='+createDateEnd+'&id='+ id+'&just=just';
    showDialog({
        id: 'showJustConDetail',
        title: '详情',
        url: url,
        width: 660,
        height: 300,
        tabId: tabs.getSelfId(window)
    });
}


function reflushGrid(loanId,columnName) {
    jQuery.ajax({
        url: '../loanInvestigate/reflushGrid.html',
        type:'POST',
        dataType:'html',
        data: {"columnName":columnName,"loanId":loanId},
        sync: false,
        success: function(result){
                $('#'+columnName).html(result);
        }
    });
}
//毛利率即时刷新
function reflushMao(loanId) {
    jQuery.ajax({
        url: '../loanInvestigate/reflushMao.html',
        type:'POST',
        dataType:'html',
        data: {"loanId":loanId},
        sync: false,
        success: function(result){
            $('#Maolilv').html(result);
        }
    });
}


//关闭dialog
function closeDialog(){
    var dialog = getDialog('addMingxi');
    var win = tabs.getIframeWindow(dialog.config.tabId);
    /* /!*if(win && win.reflushGrid){
     win.reflushGrid(loanId,columnName);
     }*!/*/
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}


/*// 刷新毛利率表
 function refreshMaolilvList() {
 $('#showMaolilv-form').flexReload();
 }

 // 刷新毛利率表
 function refreshBsIncomeList() {
 $('#BsIncome-form').flexReload();
 }*/
