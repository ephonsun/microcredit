//树型控件节点选中事件
function nodeChange(e){
	switch(e.data.type){
		case "page":
			showOnly("pagePart");
			break;
		case "forms":
			showOnly("formsPart");
			break;
		case "cell":
			showOnly("formsPart");
			break;
		case "grid":
			showOnly("gridPart");
			break;
		case "columm":
			showOnly("girdPart");
			break;
		case "option":
			showOnly("optionPart");
			break;
		case "buttons":
			showOnly("buttonPart");
			break;
		case "button":
			showOnly("buttonPart");
			break;
		case "codetables":
			showOnly("codeTablePart");
			break;
		case "treetables":
			showOnly("treeTablePart");
			break;
		case "querytables":
			showOnly("queryTablePart");
			break;
	}
}

function showOnly(id){
	$("#pagePart").hide();
	$("#formsPart").hide();
	$("#gridPart").hide();
	$("#buttonPart").hide();
	$("#optionPart").hide();
	$("#codeTablePart").hide();
	$("#treeTablePart").hide();
	$("#queryTablePart").hide();
	$("#"+id).show();
}

//保存页面基本信息
function savePageInfo(){
	//var postJson = $.encodeUrl($.json.toString(DS));
	var postJson = DS;
	$.ajax({
		type:"post",
		url:"../build/saveQueryPageInfo.html",
		data:postJson,
		contentType:"text/xml",
		success:function(result){
			if(result=="success"){
				alert("保存成功");
			}
		}
	});
}

var addColFlag = "";
function addColumn(e){
	addColFlag = e.id;
	$("#tableTreePanel").ctrl().show();
}

function addOperColumn(e){
	var obj = {"name":"","property":"","type":"","length":0,"title":"操作","format":"","width":140,"align":"center","table":""};
	DS.RecordColumnInfo.push(obj);
	$("#recordGrid").ctrl().dataBinding();
}

function addUserColumn(e){
	var obj = {"name":"","property":"","type":"","length":0,"title":"","format":"","width":140,"align":"center","table":""};
	DS.RecordColumnInfo.push(obj);
	$("#recordGrid").ctrl().dataBinding();
}

//选择表
function selectTable(e){
	if(e.data.type=="table"){
		$.post("../build/queryTableResultColumns.html?tableName="+e.data.name,{},function(result){
			if(result.charAt(0)=="["){
				var json = $.json.parse(result);
				$.json.attach("DV",{"Result_Info":json});
			}else{
				alert(result);
			}
		});
	}
}

//刷新查询结果
function refreshResult(){
	var sql = $("#sqlText").val();
	if(sql){
		$.post("../build/querySqlResultColumns.html?sqlString="+encodeURI(sql),{},function(result){
			if(result.charAt(0)=="["){
				var json = $.json.parse(result);
				$.json.attach("DS",{"Result_Info":json});
			}else{
				alert(result);
			}
		});
	}
}

//添加选择列
function addSelectColumn(){
	var data = $("#resultGrid").ctrl().getCheckedRowData();
	if(data.length>0){
		for(var i=0;i<data.length;i++){
			var name = data[i]["label"];
			var type = data[i]["typeName"];
			var length = data[i]["length"];
			var title = data[i]["comment"];
			var table = data[i]["tableName"];
			var property = getFirstLower(name);
			if(addColFlag=="queryColumnAdd"){
				var ctrl = getDefaultQueryControl(type);
				var cols = getQueryControlCols(ctrl);
				var query = getDefaultQueryType(type);
				var obj = {"name":name,"property":property,"type":type,"length":length,"title":title,"ctrl":ctrl,"cols":cols,"query":query,"table":table};
				if(!isExistColumn(DS.QueryColumnInfo,obj,"name"))DS.QueryColumnInfo.push(obj);
			}else if(addColFlag=="recordColumnAdd"){
				var valueType = getValueType(type);
				var format = (valueType=="Date")?"yyyy-MM-dd HH:mm:ss":"";
				var obj = {"name":name,"property":property,"type":type,"length":length,"title":title,"format":format,"width":100,"align":"center","hide":0,"table":table};
				if(!isExistColumn(DS.RecordColumnInfo,obj,"name"))DS.RecordColumnInfo.push(obj);
			}
		}
		if(addColFlag=="queryColumnAdd")$("#queryGrid").ctrl().dataBinding();
		else if(addColFlag=="recordColumnAdd")$("#recordGrid").ctrl().dataBinding();
	}
	addColFlag = "";
	$("#tableTreePanel").ctrl().hide();
	resetTableInfo();
}

function isExistColumn(source,obj,field){
	for(var i=0;i<source.length;i++){
		if(source[i][field]==obj[field])return true;
	}
	return false;
}

//生成Sql语句
function buildSql(){
	var pi = DS.PageInfo;
	var tableName = pi["tableName"];
	if(tableName){
		var sqlId = (pi["sqlId"]=="")?"query"+pi.pageId+"Data":pi["sqlId"]; //没有sqlId时，自动生成
		var sql = buildQuerySqlString(sqlId,DS.QueryColumnInfo,DS.RecordColumnInfo,tableName,DS.RelationInfo);
		pi["sqlString"]=sql;
		pi["sqlId"]=sqlId;
		$("#forms").ctrl().dataBinding();
	}
}

//生成操作项脚本
function buildOptionScript(){
	var type = $("#optionType").val();
	var method = $("#optionMethod").val();
	var script = buildQueryScriptString(type,method);
	$("#optionScript").val(script);
}

//生成按钮脚本
function buildButtonScript(){
	var type = $("#buttonType").val();
	var method = $("#buttonMethod").val();
	var script = buildQueryScriptString(type,method);
	$("#buttonScript").val(script);
}


var optionItemIndex = -1;	//操作项的游标
//暂存生成的脚本
function saveOptionScript(){
	if(optionItemIndex>=0){
		var type = $("#optionType").val();
		var method = $("#optionMethod").val();
		var script = $("#optionScript").val();
		var oi = DS.RecordOptionItems[optionItemIndex];
		oi["type"] = type;
		oi["method"] = method;
		oi["script"] = script;
	}
	$("#optionScriptPanel").ctrl().hide();
}

var buttonItemIndex = -1;
//保存按钮脚本
function saveButtonScript(){
	if(buttonItemIndex>=0){
		var type = $("#buttonType").val();
		var method = $("#buttonMethod").val();
		var script = $("#buttonScript").val();
		var oi = DS.ButtonItems[buttonItemIndex];
		oi["type"] = type;
		oi["method"] = method;
		oi["script"] = script;
	}
	$("#buttonScriptPanel").ctrl().hide();
}

//添加操作项
function addOptionItem(){
	var obj = {"id":"","tltle":"","script":"","type":"","method":""};
	DS.RecordOptionItems.push(obj);
	$("#optionItemGrid").ctrl().dataBinding();
}

//添加按钮项
function addButtonItem(){
	var obj = {"id":"","tltle":"","skin":"blur","script":"","type":"","method":""};
	DS.ButtonItems.push(obj);
	$("#buttonItemGrid").ctrl().dataBinding();
}

//添加关联表
function addTableRelation(){
	var tLen = DS.TableInfo.length;
	var rLen = DS.RelationInfo.length;
	if(rLen<tLen-1){
		
	}else{
		alert("没有可添加的关系表");
	}
}

//重设TableInfo数据
function resetTableInfo(){
	var ja = [],map={},col;
	for(var i=0;i<DS.RecordColumnInfo.length;i++){
		col = DS.RecordColumnInfo[i];
		if(!map[col.table]){
			map[col.table]=col.table;
			ja.push({"id":col.table,"name":col.table});
		}
	}
	$.json.attach("DV",{"TableInfo":ja});
}

//选择操作项
function optionItemSelect(e){
	var index = e.parent.row.dataIndex;
	var title = (e.row)?e.row["title"]:"";
	if(title)DS.RecordOptionItems[index]["title"] = title;
	$("#optionItemGrid").ctrl().dataBinding();
}

//选择按钮项
function buttonItemSelect(e){
	var index = e.parent.row.dataIndex;
	var title = (e.row)?e.row["title"]:"";
	if(title)DS.ButtonItems[index]["title"] = title;
	$("#buttonItemGrid").ctrl().dataBinding();
}

function operQueryClick(e){
	switch(e.id){
		case "up":
			if(DS.QueryColumnInfo.up(e.index))e.ctrl.dataBinding();
			break;
		case "down":
			if(DS.QueryColumnInfo.down(e.index))e.ctrl.dataBinding();
			break;
		case "del":
			if(DS.QueryColumnInfo.remove(e.index))e.ctrl.dataBinding();
			break;
		case "data":
			eidtCtrlData(e.index);
			break;
	}
}

function openRecordClilk(e){
	switch(e.id){
		case "up":
			if(DS.RecordColumnInfo.up(e.index))e.ctrl.dataBinding();
			break;
		case "down":
			if(DS.RecordColumnInfo.down(e.index))e.ctrl.dataBinding();
			break;
		case "del":
			if(DS.RecordColumnInfo.remove(e.index))e.ctrl.dataBinding();
			break;
	}
}

function operItemClick(e){
	switch(e.id){
		case "up":
			if(DS.RecordOptionItems.up(e.index))e.ctrl.dataBinding();
			break;
		case "down":
			if(DS.RecordOptionItems.down(e.index))e.ctrl.dataBinding();
			break;
		case "del":
			if(DS.RecordOptionItems.remove(e.index))e.ctrl.dataBinding();
			break;
		case "script":
			editOptionItemScript(e.index);
			break;
	}
}

function buttonItemClick(e){
	switch(e.id){
		case "up":
			if(DS.ButtonItems.up(e.index))e.ctrl.dataBinding();
			break;
		case "down":
			if(DS.ButtonItems.down(e.index))e.ctrl.dataBinding();
			break;
		case "del":
			if(DS.ButtonItems.remove(e.index))e.ctrl.dataBinding();
			break;
		case "script":
			editButtonItemScript(e.index);
			break;
	}
}

function editOptionItemScript(index){
	optionItemIndex = index;
	var oi = DS.RecordOptionItems[optionItemIndex];
	var	type = oi["type"];
	var	method = oi["method"];
	var script = oi["script"];
	$("#optionScriptPanel").ctrl().show();
	if(type)$("#optionType").val(type);
	if(method)$("#optionMethod").val(method);
	if(script)$("#optionScript").val(script);
}

function editButtonItemScript(index){
	buttonItemIndex = index;
	var oi = DS.ButtonItems[buttonItemIndex];
	var	type = oi["type"];
	var	method = oi["method"];
	var script = oi["script"];
	var html = oi["html"];
	$("#buttonScriptPanel").ctrl().show();
	if(type)$("#buttonType").val(type);
	if(method)$("#buttonMethod").val(method);
	if(script)$("#buttonScript").val(script);
}

function optionTypeSelect(e){
	var val = e.value;
	$("#optionMethod").val(val);
}

function buttonTypeSelect(e){
	var val = e.value;
	$("#buttonMethod").val(val);
}

function addCodeTable(e){
	var obj = {"id":"","name":"","type":"sql","sql":"","items":[]};
	DS.CodeTables.push(obj);
	$("#codeTableGrid").ctrl().dataBinding();
}

function codeTableClick(e){
	switch(e.id){
		case "up":
			if(DS.CodeTables.up(e.index))e.ctrl.dataBinding();
			break;
		case "down":
			if(DS.CodeTables.down(e.index))e.ctrl.dataBinding();
			break;
		case "del":
			if(DS.CodeTables.remove(e.index))e.ctrl.dataBinding();
			break;
		case "edit":
			editCodeTableItem(e.index);
			break;
	}
}

var codeTableIndex = -1;	//代码表的索引
function editCodeTableItem(index){
	var type = DS.CodeTables[index].type;
	if(type=="fixed"){
		codeTableIndex = index;
		var items = DS.CodeTables[codeTableIndex].items;
		$("#codeTableItemGrid").ctrl().dataBinding(items);
		$("#codeTableItemPanel").ctrl().show();
	}else{
		
	}
}

function addCodeTableItem(){
	var items = DS.CodeTables[codeTableIndex].items;
	var obj = {"value":"","text":""};
	items.push(obj);
	$("#codeTableItemGrid").ctrl().dataBinding(items);
}

function saveCodeTableItem(){
	$("#codeTableItemPanel").ctrl().hide();
}

var ctrlDataIndex = -1;
function eidtCtrlData(index){
	ctrlDataIndex = index;
	$("#dataSourcePanel").ctrl().show();
}