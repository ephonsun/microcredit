// 页面加载完成时...
$(function(){
	$('#dataDictGrid').flexigrid({
		//width:  500,
        height: 500,
        url: '../sysDataDict/queryDataDictListData.html',
        fields: [
        	{ display: '名称', field: 'dictCnName', width: 200 ,align: 'center'},
        	{ display: '业务平台显示', field: 'sysFlag', width: 150 ,align: 'center',data:{'0':'是','1':'否'}}
        	//,{ display: '编码', field: 'dictEnName', width: 300 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 200,
        	align: 'center',
        	buttons: [
                {
                    display: '编辑',
                    onClick: function(tr, data){
                    	updateDataDict(data);
                    }
                },
            	{
            		display: '字典',
            		onClick: function(tr, data){
            			openDataDictColPage(data);
            		}
        		},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			delDataDict(data);
            		},
                    isDisabled: function(data){
                        if(data.cols.isFixed == 1){
                            return true;
                        }
                        return false;
                    }
        		}
            ]
        },
        usePage: false
    });
	
    //查看数据源 数据字典
    function openDataDictColPage(data){
    	tabs.add({
			id :'openDataDictCol'+data.id,
			pid:tabs.getSelfId(window),
			name:'openDataDictCol',
			title:'数据源/'+data.cols.dictCnName,
			url:'../sysDataDictCol/getDataDictColListPage.html?'+encodeURI('dataDictId='+data.id+'&dictCnName='+data.cols.dictCnName),
			lock:false
		});
    }
    
	//删除数据源
	function delDataDict(data){
		showConfirm({
            icon:'confirm',
            content:'您确定要删除数据源“'+ data.cols.dictCnName+ '”吗?',
            ok:function(){
            	jQuery.ajax({
        			url: '../sysDataDict/deleteDataDict.html',
        			type:'POST', 
        			dataType:'json',
                    data: {"dataDictId": data.id},
                    sync: false,
                    success: function(result){
                    	if(result.success){
                    		showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });
                    		reloadDataDictGrid();
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
	
	//弹出新建框
	$("#btnAdd").click(function() {
	    var url = '../sysDataDict/getDataDictInsertPage.html';
	    showDialog({
	        id: 'dataDictHandle',
	        title: '新建数据源',
	        url: url,
	        width: 330,
	        height: 168,
	        tabId: tabs.getSelfId(window)
	    });
	}); 
	
    //编辑数据源
    function updateDataDict(data){
    	var url = '../sysDataDict/getDataDictUpdatePage.html?'+encodeURI('dataDictId='+data.id+'&dictCnName='+data.cols.dictCnName);
	    showDialog({
	        id: 'dataDictHandle',
	        title: '编辑数据源',
	        url: url,
	        width: 330,
	        height: 168,
	        tabId: tabs.getSelfId(window)
	    });
    }
	
	//刷新
	$('#btnRefresh').click(function(){
		$('#dataDictGrid').flexReload();
	});
	
	function reloadDataDictGrid(){
		$('#dataDictGrid').flexReload();
	}
	
	function searchDataDictGrid(){
		$('#dataDictGrid').flexSearch();
	}
});