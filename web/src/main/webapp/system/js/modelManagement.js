// 页面加载完成时...
$(function(){
	$('#modelManagementList').flexigrid({
        height: 500,
        url: '../modelManagement/queryModelManagementList.html',
        fields: [
        	{ display: '编号', field: 'modelOrderNum', width: 200 ,align: 'center'},
        	{ display: '模型名称', field: 'modelName', width: 150 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 200,
        	align: 'center',
        	buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                    	updateModelManagement(data);
                    }
                },
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteModel(data);
            		}
        		},
        		// {
            		// display: '试一试',
            		// onClick: function(tr, data){
            		// 	test(data);
            		// }
        		// }
            ]
        },
        usePage: false
    });
	
	//新增模型
	$("#btnAdd").click(function() {
	    var url = '../modelManagement/getModelManagementInsertPage.html';
	    tabs.add({
            id: 'newModelHandle',
            name: 'newModelHandle',
            pid: tabs.getSelfId(window),
            title : '新增模型',
            url : url,
            lock : false
        });
	}); 
	
	//模型匹配
	$("#btnMatch").click(function() {
		var url = '../modelMatch/getModelMatchProjectPage.html';
		 tabs.add({
	            id: 'modelMatchHandle',
	            name: 'modelMatchHandle',
	            pid: tabs.getSelfId(window),
	            title : '模型匹配',
	            url : url,
	            lock : false
	        });
	}); 
	
	//删除模型
	function deleteModel(data){
		var url = '../modelManagement/deleteModel.html?modelId='+data.id;
		 showConfirm({
		        icon: 'confirm',
		        content: '您确认要删除吗',
		        ok: function () {
		            jQuery.ajax({
		                type: 'POST',
		                url: url,
		                success: function (result) {
		                    showConfirm({
		                        icon: 'succeed',
		                        content: '删除成功'
		                        });
		                    $('#modelManagementList').flexReload();
		                    }
		                });
		            },
		        cancel: function () {}
		        });
		};
	   //编辑模型 
	   function updateModelManagement(data){
	    	var url = '../modelManagement/getUpdateModelManagementPage.html?'+'modelId='+data.id+'&modelName='+encodeURI(data.cols.modelName);
		    tabs.add({
	            id: 'updateModelHandle',
	            name: 'updateModelHandle',
	            pid: tabs.getSelfId(window),
	            title : '编辑模型',
	            url : url,
	            lock : false
	        });
	   }
	   //占坑
		// function test(data){
	   // 		var url='../modelManagement/getShowPage.html?projectName=参考利率';
        //     showDialog({
        //         id: 'pageShowHandle',
        //         title: '参考利率',
        //         url: url,
        //         width: 317,
        //         height: 'auto',
        //         tabId: tabs.getSelfId(window)
        //     });
		// }
	
});