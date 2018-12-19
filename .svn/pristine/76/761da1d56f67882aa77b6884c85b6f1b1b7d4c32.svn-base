$(function(){

    $('#tableInfolist').flexigrid({
        height: 300,
        url: '../loanTypeRelTable/queryAutoTableInfoList.html?precType='+$("#precType").val()+'&typeId='+$("#typeId").val(),
        dataType : 'json',
        //multiSelect: true,
        fields: [
            { display: '自定义信息表', field: 'tableDisplayName', width: 300 ,align: 'center'},
            { display: '状态', field: 'isActivedthis', width: 100 ,align: 'center',data:{0:"禁用",1:"启用"}},
        ],
        action: {
        	display: '操作',
        	width: 200,
        	align: 'center',
        	buttons: [
        	   {
			display: '启用',
			onClick: function(tr, data){
			    showConfirm({
			        icon: 'confirm',
			        content: '您确认要启用吗',
			        ok: function () {
			            jQuery.ajax({
			                type: 'POST',
			                url: '../loanTypeRelTable/enable/' + data.id +".html",
			                success: function (result) {
			                    showConfirm({
			                        icon: 'succeed',
			                        content: '启用成功'
			                        });
			                    	refresh();
			                    }
			                });
			            },
			        cancel: function () {
			            }
			        });
			    },
			        isDisabled: function(data){
					if(data.cols.isActived == 0){
						return true;
					}else{
						return data.cols.isActivedthis == 1;
					}
			    }
			},
			{
			    display: '禁用',
			onClick: function(tr, data){
			    showConfirm({
			        icon: 'confirm',
			        content: '您确认要禁用吗',
			        ok: function () {
			            jQuery.ajax({
			                type: 'POST',
			                url: '../loanTypeRelTable/disable/' + data.id +".html",
			                success: function (result) {
			                    showConfirm({
			                        icon: 'succeed',
			                        content: '禁用成功'
			                        });
			                        refresh();
			                    }
			                });
			            },
			            cancel: function () {
			            }
			        });
			    },
			    isDisabled: function(data){
					if(data.cols.isActived == 0){
						return true;
					}
			    	//是否包含必填，如果包含禁用按钮置灰
			        return data.cols.isActivedthis == 0 || ($("#fixedTableIds").val().indexOf(data.cols.tableId) != -1);
			    }
			},
			{
			    display: '配置',
			    onClick: function(tr, data){
                    var url = '../LoanTypeTableField/getLoanTypeTableFieldPage.html?tableId='+data.cols.tableId+'&tableDisplayName='+ encodeURI(data.cols.tableDisplayName)+'&loanType='+ encodeURI($("#loanType").val())+'&precTypeName='+ encodeURI($("#precTypeName").val())+'&typeId='+$("#typeId").val()+'&precType='+ encodeURI($("#precType").val());
                    tabs.add({
                        id: 'LoanTypeTableFieldList'+data.id,
                        name: 'LoanTypeTableFieldList',
                        pid: tabs.getSelfId(window),
                        title : '贷款类型表单字段配置',
                        url : url,
                        lock : false
                    });
                }
			},
			{
                display: '上移',
                onClick: function(tr, data){
                	move(data,'moveUp');
                },
        		isDisabled : function(row, index, data) {
					return index == 0;
				}
            },
            {
                display: '下移',
                onClick: function(tr, data){
                	move(data,'moveDown');
                },
                isDisabled : function(row, index, data) {
					return (++index == data.rows.length);
				}
            },
                {
                    display: '删除',
                    onClick: function(tr, data){
                        deleteLoanTypeField(data);
                    },
                    isDisabled: function(data){
                        //是否包含必填，如果包含禁用按钮置灰
                        return data.cols.isActivedthis == 0 || ($("#fixedTableIds").val().indexOf(data.cols.tableId) != -1);
                    }
                }
            ]
        },
        usePage: false
});
  //移动
	function move(data,type) {
		jQuery.ajax({
			type : 'POST',
			url : '../loanTypeRelTable/moveTableInfoOrderNo.html?typeId='+ data.cols.loanTypeId+ '&type='+type+ '&precType='+data.cols.loanPrecType+'&tableId='+data.cols.tableId,
			data : {},
			success : function(result) {
				reloadTableInfoListGrid();
			}
		});
	}
	//刷新
	function reloadTableInfoListGrid(){
		$('#tableInfolist').flexReload();
	}
	//新增
	 $("#btnAdd").click(function() {
	    	var url = '../loanTypeRelTable/getLoanTypeRelTableInsertPage.html?precType='+$("#precType").val()+'&typeId='+$("#typeId").val() +'&random='+Math.random()*10000;
	        showDialog({
	            id: 'LoanTypeRelTableHandle',
	            title: '新建',
	            url: url,
	            width: 330,
	            height: 200,
	            tabId: tabs.getSelfId(window)
	        });
	    });
	// 刷新人员表
	 function refresh() {
	 	$('#tableInfolist').flexReload();
	 }

	 function deleteLoanTypeField(data){
         showConfirm({
             icon:'confirm',
             content:'您确定要删除“'+data.cols.tableDisplayName+'”吗？',
             ok:function(){
                 jQuery.ajax({
                     url: '../loanTypeRelTable/deleteLoanType.html',
                     type:'POST',
                     dataType:'json',
                     data: {"id": data.id},
                     sync: false,
                     success: function(result){
                         reloadTableInfoListGrid();
                     }
                 });
             },
             cancel: function() {}
         });
	 }
});