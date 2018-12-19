// 页面加载完成时...
$(function(){
	var dataDictId = $('#dataDictId').val();
	$('#dataDictColGrid').flexigrid({
		//width:  500,
        height: 500,
        url: '../sysDataDictCol/queryDataDictColListData.html?'+encodeURI('dataDictId='+dataDictId),
        fields: [
        	//{ display: '编码', field: 'dictValue', width: 300 ,align: 'center'},
        	{ display: '选项', field: 'dictName', width: 300 ,align: 'center'},
        	{ display: '编码', field: 'dictCode', width: 300 ,align: 'center'}
        ],
        action: {
        	display: '操作',
        	width: 300,
        	align: 'center',
        	buttons: [
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
                    display: '编辑',
                    onClick: function(tr, data){
                    	updateDataDict(data);
                    }
                },
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			delDataDictCol(data);
            		}
                    ,isDisabled: function(data){
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
	
	//移动
	function move(data,type) {
		jQuery.ajax({
			type : 'POST',
			url : '../sysDataDictCol/moveDictOrderNo.html?dictColId='+ data.id+ '&type='+type+ '&dataDictId='+dataDictId,
			data : {},
			success : function(result) {
				reloadDataDictColGrid();
			}
		});
	}
    
	//删除字典值
	function delDataDictCol(data){
		showConfirm({
            icon:'confirm',
            content:'您确定要删除字典值“'+ data.cols.dictName+ '”吗?',
            ok:function(){
            	jQuery.ajax({
        			url: '../sysDataDictCol/deleteDataDictCol.html',
        			type:'POST', 
        			dataType:'json',
                    data: {"dictColId": data.id},
                    sync: false,
                    success: function(result){
                    	if(result.success){
                    		showConfirm({
                                icon: 'succeed',
                                content: result.cause
                            });
                            reloadDataDictColGrid();
                    	}else {
                    		showConfirm({
                                icon: 'warning',
                                content: "操作失败！"
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
		var dataDictId = $('#dataDictId').val();
		var dictCnName = $('#dictCnName').val();
	    var url = '../sysDataDictCol/getDataDictColInsertPage.html?'+encodeURI('dataDictId='+dataDictId);
	    showDialog({
	        id: 'dataDictColHandle',
	        title: '新建选项',
	        url: url,
	        width: 330,
	        height: 170,
	        tabId: tabs.getSelfId(window)
	    });
	}); 
	
    //编辑数据源
    function updateDataDict(data){
    	var dataDictId = $('#dataDictId').val();
    	var dictCnName = $('#dictCnName').val();
    	var url = '../sysDataDictCol/getDataDictColUpdatePage.html?'+encodeURI('dataDictId='+dataDictId+'&dictColId='+data.id);
	    showDialog({
	        id: 'dataDictColHandle',
	        title: '编辑选项',
	        url: url,
	        width: 330,
	        height: 170,
	        tabId: tabs.getSelfId(window)
	    });
    }
	
	//刷新
	$('#btnRefresh').click(function(){
		$('#dataDictColGrid').flexReload();
	});
	
	function reloadDataDictColGrid(){
		$('#dataDictColGrid').flexReload();
	}
	
	function searchDataDictColGrid(){
		$('#dataDictColGrid').flexSearch();
	}
});