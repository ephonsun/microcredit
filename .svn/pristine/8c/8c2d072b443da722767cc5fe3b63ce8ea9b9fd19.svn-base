function buildQuerySqlString(sqlId,cells,columns,table,relations){
	var code="",ctrl="",field="",property="",type="",query="";
	code+="<sql id=\""+sqlId+"\" >\r\n";
	code+="\tSELECT * FROM "+table+" \r\n";
	code+="\t<where>\r\n";
	for(var i=0;i<cells.length;i++){
		cell = cells[i];
		ctrl = cell.ctrl;
		field = cell.name;
		property = getFirstLower(field);
		query = cell.query;
		type = cell.type;
		switch(ctrl){
			case "textbox":
				if(query=="equal"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" = '["+property+"]' </clause>\r\n";
				}else if(query=="like"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" like '%["+property+"]%' </clause>\r\n";
				}else if(query=="leftlike"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" like '["+property+"]%' </clause>\r\n";
				}else if(query=="rightlike"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" like '%["+property+"]' </clause>\r\n";
				}
				break;
			case "combobox":
			case "combotree":
			case "combotable":
				code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" in ["+property+"] </clause>\r\n";
				break;
			case "combobox1":
			case "combotree1":
			case "combotable1":
				if(type=="VARCHAR2" || type=="VARCHAR"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" in (<method name=\"quote\" >["+property+"]</method>) </clause>\r\n";
				}else if(type=="INTEGER"){
					code+="\t\t<clause prepend=\"and\" assert=\"!isNullOrEmpty(["+property+"])\" > "+field+" in (["+property+"]) </clause>\r\n";
				}
				break;
			case "numspan":
				code+="\t\t<clause parent=\"NumberSpan\" params=\""+field+","+property+","+property+"End\" ></clause>\r\n";
				break;
			case "datespan":
				code+="\t\t<clause parent=\"DateSpan\" params=\""+field+","+property+","+property+"End\" ></clause>\r\n";
				break;
			case "timespan":
				code+="\t\t<clause parent=\"DateTimeSpan\" params=\""+field+","+property+","+property+"End\" ></clause>\r\n";
				break;
		}
	}
	code+="\t</where>\r\n";
	code+="</sql>";
	return code;
}

function buildQueryScriptString(type,method){
	var script = "";
	switch(type){
		case "openPageInTab":
			script += "//在页卡中打开新的页面\r\n";
			script += "function "+method+"(e){\r\n";
			script += "\tvar data = e.data;\t\t\t//行对像，可通过data['字段名']取要想要的参数\r\n";
			script += "\tvar url = '';\t\t\t//打开页面的地址\r\n";
			script += "\tvar page = {'id':'','title':'','url':url,'pid':tabs.getSelfId(window),'lock':false};\r\n";
			script += "\ttabs.add(page);\r\n";
			script += "}\r\n";
			break;
		case "openPageInDialog":
			script += "//在对话框中打开新的页面\r\n";
			script += "function "+method+"(e){\r\n";
			script += "\tvar data = e.data;\t\t\t//行对像，可通过data['字段名']取要想要的参数\r\n";
			script += "}\r\n";
			break;
	}
	return script;
}