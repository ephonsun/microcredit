
$(function() {
	refreshSelectList();
	var userName = "";
	var groupId = $("#groupId").val();
	if(null==groupId||""==groupId||0==groupId){
		userName = "userName";
	}else{
		var userIds = $("#userIds").val().split(",");
		for(var i=0;i<userIds.length;i++){
			userIdMap.put(parseInt( userIds[i]), userIds[i]);
		}
	}
    function refreshSelectList(){
        var userName = $("#userName").val();
        getRoleUser(userName,$("#userSelectTree"), selectSetting,"");
    }
    // //change事件在ie下失效的处理
    // $('#userName').bind('change',function(){
    //     //处理内容（change事件处理）
    //     var userName = $("#userName").val();
    //     getRoleUser(userName,$("#userSelectTree"), selectSetting,"");
    // }).live('change',function(){
    //     //处理内容（动态生成的select的事件处理）
    //     var userName = $("#userName").val();
    //     getRoleUser(userName,$("#userSelectTree"), selectSetting,"");
    // }).live('click',function(){
    //     if($.data(this,'events')==null||$.data(this,'events').change==undefined){
    //         //处理内容（click事件处理，如果没有click事件，也可以不写）
    //     }
    // });

    $('#userName').bind('keyup',function(){
        //处理内容（change事件处理）
        var userName = $("#userName").val();
        getRoleUser(userName,$("#userSelectTree"), selectSetting,"");
    })



    getRoleUser(userName,$("#userTree"), listSetting,groupId);
	//$.fn.zTree.init($("#userSelectTree"), selectSetting, zNodes);
	//$.fn.zTree.init($("#userTree"), listSetting, zNodes_tree);
	banger.verify('#groupName', {
		name : 'required',
		tips : '“团队名称”必须填写'
	});

	//banger.verify('#userTree', userTreeRule);

});

function HashMap(){
	this.map = {};
}
HashMap.prototype = {
	put : function(key , value){
		this.map[key] = value;
	},
	get : function(key){
		if(this.map.hasOwnProperty(key)){
			return this.map[key];
		}
		return null;
	},
	remove : function(key){
		if(this.map.hasOwnProperty(key)){
			return delete this.map[key];
		}
		return false;
	},
	removeAll : function(){
		this.map = {};
	},
	keySet : function(){
		var _keys = [];
		for(var i in this.map){
			_keys.push(i);
		}
		return _keys;
	}
};
HashMap.prototype.constructor = HashMap;

// 保存新增and修改
$("#btnSave").click(function() {
	save("btnSave");
});

function save(flag){
	if (banger.verify('#groupTab')) {

		var zTree = $.fn.zTree.getZTreeObj("userTree");
		var addNode = zTree.getNodeByParam("id",$("#leaderRoleId").val());
		//||addNode.children.length!=1
		// if(!addNode.children){
		// 	showConfirm({
		// 		icon: 'warning',
		// 		content: "请选择团队主管！"
		// 	});
		// 	//alert("有且需要1名团队主管");
		// 	return;
		// }

		var postJson = {};
		postJson['groupName'] = $("#groupName").val();
		postJson['groupId'] = $("#groupId").val();
		postJson['selectUserList'] = selectUserList();
		jQuery.ajax({
			type : "post",
			url : "../group/addUpdateGroup.html?",
			data : postJson,
			async : false,
			success : function(result) {
                result = JSON.parse(result);
				if(result.success != true){
					showConfirm({
						icon: 'warning',
						content: result.cause
					});
				}else{
					showConfirm({
						icon: 'succeed',
						content: '保存成功'
					});

					if("btnContinue"==flag){
						// $("#groupName").val("");
						// $("#groupId").val("");
						// $("#userName").val("");
                        // $("#userNum").html("").text(0);
						// userIdMap = new HashMap();
						// refreshSelectList();
						// getRoleUser("userName",$("#userTree"), listSetting,"");
                        window.location.href="../group/getAddSysTeamGroupPage.html";
					}else{
						var dialog = getDialog('addOrUpdateGroup');
						var win = tabs.getIframeWindow(dialog.config.tabId);
						win.refreshGroupList();
						dialog.close();
					}
				}
			}
		});
	}
}

// 保存新建并继续新建
$('#btnContinue').click(function() {
	save("btnContinue");
});

$('#btnCancel').click(function() {
	closeDia();
});

function closeDia(){
	// 关闭弹出框
	var dialog = getDialog('addOrUpdateGroup');
	var win = tabs.getIframeWindow(dialog.config.tabId);
	win.refreshGroupList();
	dialog.close();
}

var userIdMap = new HashMap();

var selectSetting = {
	view : {
		showIcon : false,
		expandSpeed : ''
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback: {
		onDblClick: selectUser
	}
};


var listSetting = {
	view : {
		showIcon : false,
		expandSpeed : ''
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback: {
		onDblClick: removeUser
	}
};

function selectUser(){
	var userId = moveUser("userTree","userSelectTree");
	if(userId!=0){
		countNum(1);
		userIdMap.put(userId, userId);
	}
}

function removeUser(){
	var userId = moveUser("userSelectTree","userTree");
	if(userId!=0){
		countNum(-1);
		userIdMap.remove(userId);
	}
}

function countNum(i){
	$("#userNum").text(parseInt($("#userNum").text())+i);
}



function moveUser(addTree,removeTree){

	var removeZTree = $.fn.zTree.getZTreeObj(removeTree);
	var removeNodes = removeZTree.getSelectedNodes();
	var	selectTreeNode = removeNodes[0];

	if(null==selectTreeNode.pId||selectTreeNode.pId==0){
		return 0;
	}

	var addZTree = $.fn.zTree.getZTreeObj(addTree);
	var addNode = addZTree.getNodeByParam("id",selectTreeNode.pId );
	addZTree.addNodes(addNode, {id:selectTreeNode.id, pId:selectTreeNode.pId, isParent:false, name:selectTreeNode.name});

	removeZTree.removeNode(selectTreeNode);
	return selectTreeNode.id;
}



function selectUserList(){
	var userList = "";
	var keys = userIdMap.keySet();
	for(var i= 0;i< keys.length ;i++){
		if(userList!=""){
			userList += ",";;
		}
		userList+=userIdMap.get(keys[i]);
	}
//userIdMap.map.forEach(function (item, key, mapObj) {
	//	if(userList!=""){
	//		userList += ",";;
	//	}
	//	userList+=item.toString();
	//});
	return userList;

}

function getRoleUser(userName,treeObj,setting,groupId){
	var userList = selectUserList();

	jQuery.ajax({
		type: 'post',
		url:  '../user/getGroupSelectUser.html',
		async:false,
		data: {userName:userName,exUserIds:userList,groupId:groupId},
		success: function (data) {
			$.fn.zTree.init(treeObj, setting, $.parseJSON(data));
		}
	});
}
//
//var zNodes =[
//	{ id:1, pId:0, name:"随意拖拽 1"},
//	{ id:11, pId:1, name:"随意拖拽 1-1"},
//	{ id:13, pId:0, name:"禁止拖拽 1-3"},
//	{ id:131, pId:13, name:"禁止拖拽 1-3-1"},
//	{ id:132, pId:13, name:"禁止拖拽 1-3-2"},
//	{ id:132, pId:13, name:"禁止拖拽 1-3-3"},
//	{ id:2, pId:0, name:"禁止子节点移走 2"},
//	{ id:21, pId:2, name:"我不想成为父节点 2-1"},
//	{ id:22, pId:2, name:"我不要成为根节点 2-2"},
//	{ id:23, pId:2, name:"拖拽试试看 2-3"},
//	{ id:3, pId:0, name:"禁止子节点排序/增加 3"},
//	{ id:31, pId:3, name:"随意拖拽 3-1"},
//	{ id:32, pId:3, name:"随意拖拽 3-2"},
//	{ id:33, pId:3, name:"随意拖拽 3-3"}
//];
//var zNodes_tree =[
//	{ id:1, pId:0, name:"随意拖拽 1"},
//	{ id:13, pId:0, name:"禁止拖拽 1-3"},
//	{ id:2, pId:0, name:"禁止子节点移走 2"},
//	{ id:3, pId:0, name:"禁止子节点排序/增加 3"},
//];
//

// Production steps of ECMA-262, Edition 5, 15.4.4.19
// Reference: http://es5.github.com/#x15.4.4.19

