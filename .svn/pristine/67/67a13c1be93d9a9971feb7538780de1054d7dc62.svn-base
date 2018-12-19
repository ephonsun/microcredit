// 页面加载完成时...
$(function(){
    //浏览器不支持 placeholder 时才执行
    if (!('placeholder' in document.createElement('input'))) {
        $('[placeholder]').each(function () {
            var $tag = $(this); //当前 input
            var $copy = $tag.clone();   //当前 input 的复制
            if ($copy.val() == "") {
                $copy.css("color", "#999");
                $copy.val($copy.attr('placeholder'));
            }
            $copy.focus(function () {
                if (this.value == $copy.attr('placeholder')) {
                    this.value = '';
                    this.style.color = '#000';
                }
            });
            $copy.blur(function () {
                if (this.value=="") {
                    this.value = $copy.attr('placeholder');
                    $tag.val("");
                    this.style.color = '#a9a9a9';
                } else {
                    $tag.val(this.value);
                }
            });
            $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
        });
    }

    // 加载操作时间
    $('#txtStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'txtEndDate\')}'
    });
    $('#txtEndDate').datepicker({
        minDate: '#F{$dp.$D(\'txtStartDate\')}'
    });
    // 加载操作时间
    $('#auditStartDate').datepicker({
        maxDate: '#F{$dp.$D(\'auditEndDate\')}'
    });
    $('#auditEndDate').datepicker({
        minDate: '#F{$dp.$D(\'auditStartDate\')}'
    });
    $('select').selectbox();
    // 清空搜索条件
    $('#btnClean').click(function() {
        toCleanForm('#form');
    });
    // 搜索按钮
    $('#btnSearch').click(function() {
        searchList();
        setTimeout(function(){$("input[name='customer']").focus();},100);
        setTimeout(function(){$("input[name='customer']").blur();},100);
    });
    //刷新
    $('#btnRefresh').click(function(){
        refreshList();
    });

    // 加载
    $('#grid').flexigrid({
        height: 280,
        url: '../loanAuditManage/queryLoanInfoListData.html?module=All',
        usePage: true,
        fields: [
            { display: '客户信息', field: 'customerInfo', width: 140 ,align : 'center' },
            { display: '证件信息', field: 'cardInfo', width: 180 ,align : 'center' },
            { display: '贷款类型', field: 'loanTypeName', width: 60 ,align : 'center' },
            { display: '贷款阶段', field: 'loanProcessTypeName', width: 60 ,align : 'center' },
            { display: '贷款状态', field: 'loanAfterStateName', width: 60 ,align : 'center' },
            { display: '申请人员', field: 'applyUserName', width: 60 ,align : 'center' },
            { display: '调查人员', field: 'investigateUserName', width: 60 ,align : 'center' },
            { display: '所属团队', field: 'teamName', width: 100 ,align : 'center' },
            { display: '创建时间', field: 'createDate', width: 80 ,align : 'center' },
            { display: '提交审批时间', field: 'loanInvestigationDate', width: 80 ,align : 'center' },
            { display: '审计状态', field: 'loanAuditState', width: 80 ,align : 'center' ,data:{'0':'待审计','1':'审计通过','2':'审计不通过'}},
            { display: '审计员', field: 'loanAuditors', width: 80 ,align : 'center' }
        ],
        action: {
            display: '操作',
            width: 200,
            align: 'center',
            buttons: [
                {
                    display: '详情',
                    onClick: function(tr, data){
                        getDetailPage(data);
                    }
                },
            ]
        },
        usePage: true
     });




});
//列表详情
function getDetailPage(data){
    var loanId =  data.id;
    if(!loanId){
        loanId = 0;
    }
    tabs.add({
        id: 'loanAuditDetail'+data.id,
        pid:tabs.getSelfId(window),
        name:'loanAuditDetail'+data.id,
        title: ' 审计信息',
        url : '../loan/getLoanTabs.html?module=audit&loanId='+data.id + '&showApply=1' +'&random='+Math.random()*10000,
        lock:false
    });
    // tabs.add({
    //     id: 'loanAuditDetail'+data.id,
    //     name: 'loanAuditDetail',
    //     pid: tabs.getSelfId(window),
    //     title : ' 审计信息',
    //     url : "../loanAuditManage/getDetailPage.html?loanId="+loanId,
    //     lock : false
    // });
}
//列表搜索
function searchList() {
    var postJson = {};
    postJson['loanTypeId'] = $('#loanTypeId').val();
    postJson['customer'] = $('#customer').val();
    postJson['startDate'] = jQuery.trim($('#txtStartDate').val());
    postJson['endDate'] = jQuery.trim($('#txtEndDate').val());
    postJson['processType'] = jQuery.trim($('#processType').val());
    postJson['teamId'] = jQuery.trim($('#teamId').val());

    postJson['afterState'] = $('#afterState').val();

    postJson['montiorState'] = $('#montiorState').val();

    postJson['memberUserId'] = $('#memberUserId').val();
    postJson['memberUser'] = $('#memberUser').val();

    postJson['auditStartDate'] = jQuery.trim($('#auditStartDate').val());
    postJson['auditEndDate'] = jQuery.trim($('#auditEndDate').val());
    postJson['loanAuditState'] = $('#loanAuditState').val();


    $('#grid').flexSearch(postJson);
}
//初始化列表，根据列名和按钮自定义渲染
function initGrid(fields, buttons, module){
    // 加载列表
    $('#grid').flexigrid({
        height: 280,
        url: '../loan/queryLoanInfoListData.html?module=' + module,
        usePage: true,
        multiSelect:true,
        rowIdProperty: 'id',
        fields: fields,
        action: {
            display: '操作',
            width: 150,
            align : 'center',
            buttons: buttons
        },
        onComplete : function(data) {
            $('#lblStatistics').text(data.total);
        }
    });
}