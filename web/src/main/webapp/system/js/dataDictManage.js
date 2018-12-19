var dataDictNodes;
var oTree;
// 根节点数据
var nodes;
// 当前节点数据
var currentNode;

var dataDictConfig = {
	callback : {
		onClick : treeOnclick
	},
	view : {
		showIcon : false,
		expandSpeed : ''
	},
	data : {
		simpleData : {
			enable : true
		}
	}
};

// 选择第一个节点
function initNodeSelect() {
	oTree = jQuery.fn.zTree.getZTreeObj('ulDataDicts');
	nodes = oTree.getNodes();
	// 如果节点数量>零
	if (nodes.length > 0) {
		currentNode = nodes[0];
		// 选中第一个节点
		oTree.selectNode(currentNode);
		// 点击节点
		//treeOnclick(event, currentNode.id, currentNode);
	}
}
// 点击节点
function treeOnclick(event, treeId, node) {
	currentNode = node;
	searchDataDictColList();
}

//获取显示机构树
function showDataDictList(deptJson) {
	dataDictNodes = jQuery.parseJSON(dataDictTreeJson);
	$.fn.zTree.init($('#ulDataDicts'), dataDictConfig, dataDictNodes);
}

// 页面加载完成时...
$(function() {
	if(dataDictTreeJson!=''){
		// 获取显示机构树
		showDataDictList(dataDictTreeJson);
		//选中第一个节点
		initNodeSelect();
	}
	if(currentNode!=undefined){
		$('#dataDictColGrid').flexigrid({
			height : 500,
			url: '../sysDataDictCol/queryDataDictColListData.html',
			dataType : 'json',
			params : {
				'dataDictId' : currentNode.id 
			},
			fields: [
			     { display: '选项', field: 'dictName', width: 300 ,align: 'center'}
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
	}
});

//移动
function move(data,type) {
	jQuery.ajax({
		type : 'POST',
		url : '../sysDataDictCol/moveDictOrderNo.html?dictColId='+ data.id+ '&type='+type+ '&dataDictId='+currentNode.id,
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
    var url = '../sysDataDictCol/getDataDictColInsertPage.html?'+encodeURI('dataDictId='+currentNode.id+'&dictCnName='+currentNode.name);
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
	var url = '../sysDataDictCol/getDataDictColUpdatePage.html?isShow=false&&'+encodeURI('dataDictId='+currentNode.id+'&dictColId='+data.id+'&dictCnName='+currentNode.name+'&dictName='+data.cols.dictName);
    showDialog({
        id: 'dataDictColHandle',
        title: '编辑选项',
        url: url,
        width: 330,
        height: 136,
        tabId: tabs.getSelfId(window)
    });
}

// 回刷父页
function closeTab() {
    var win = tabs.getIframeWindow(tabs.getSelfPid(window));
    if( win && win.refresh){
        win.refresh();
    }
	tabs.close(tabs.getSelfId(window));
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

function searchDataDictColList() {
	var postJson = getPostFields();
	postJson['dataDictId'] = currentNode.id;
	$('#dataDictColGrid').flexSearch(postJson);
}
