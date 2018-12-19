$(function(){

    $('#loanProcessTypeList').flexigrid({
        height: 300,
        url: '../loanProcessType/queryLoanProcessTypeList.html',
        dataType : 'json',
        //multiSelect: true,
        fields: [
            { display: '表单名称', field: 'precTypeName', width: 300 ,align: 'center'},
        ],
        action: {
            	display: '操作',
            	width: 200,
            	align: 'center',
            	buttons: [
				{
				    display: '编辑',
				    onClick: function(tr, data){
				    	var url = '../loanTypeRelTable/getqueryAutoTableInfoListPage.html?precType='+data.cols.precType+'&typeId='+$("#typeId").val()+'&precTypeName='+encodeURI(data.cols.precTypeName)+'&LoanType='+encodeURI($("#LoanType").val()) +'&random='+Math.random()*10000;
				        tabs.add({
				            id: 'tableColumnRelList'+$("#typeId").val()+data.cols.precType,
				            name: 'tableColumnRelList',
				            pid: tabs.getSelfId(window),
				            title : '编辑表单',
				            url : url,
				            lock : false
				        });
				    }
				},
                ]
            },
            usePage: false
    });
});