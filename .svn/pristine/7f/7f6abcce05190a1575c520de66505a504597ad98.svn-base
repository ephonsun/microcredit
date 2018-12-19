//小写
function getLower(str){
	var ss = str.split("_");
	var newStr="";
	for(var i=0;i<ss.length;i++){
		var s=ss[i];
		newStr+=s.charAt(0)+s.substr(1,s.length-1).toLowerCase();
	}
	return newStr;
}

//小写,不带前缀
function getLowerNoPre(str){
	var ss = str.split("_");
	var newStr="";
	for(var i=1;i<ss.length;i++){
		var s=ss[i];
		newStr+=s.charAt(0)+s.substr(1,s.length-1).toLowerCase();
	}
	return newStr;
}

//首字母小写
function getFirstLower(str){
	var ss = str.split("_");
	var newStr="";
	for(var i=0;i<ss.length;i++){
		var s=ss[i];
		if(i==0){
			newStr+=s.toLowerCase()
		}else{
			newStr+=s.charAt(0)+s.substr(1,s.length-1).toLowerCase();
		}
	}
	return newStr;
}

//首字母小写,不带前缀
function getFirstLowerNoPre(str){
	var ss = str.split("_");
	var newStr="";
	for(var i=1;i<ss.length;i++){
		var s=ss[i];
		if(i==1){
			newStr+=s.toLowerCase()
		}else{
			newStr+=s.charAt(0)+s.substr(1,s.length-1).toLowerCase();
		}
	}
	return newStr;
}

//得到值类型
function getValueType(type){
	switch(type){
		case "VARCHAR":
		case "VARCHAR2":
			return "String";
		case "DATE":
		case "TIMESTAMP":
			return "Date";
		case "INTEGER":
			return "Integer";
		case "DOUBLE":
			return "double";
		case "DECIMAL":
			return "BigDecimal";
	}
	return "";
}

//得到字段类型
function getFieldType(type){
	switch(type){
		case "VARCHAR":
		case "VARCHAR2":
			return "java.lang.String";
		case "DATE":
		case "TIMESTAMP":
			return "java.util.Date";
		case "INTEGER":
			return "java.lang.Integer";
		case "DOUBLE":
			return "java.lang.Double";
		case "DECIMAL":
			return "java.math.BigDecimal";
	}
	return "";
}

//生成表单控件
function getEditControl(type){
	switch(type){
		case "String":
			return "TextBox";
		case "Date":
			return "DateBox";
		case "Integer":
			return "NumSpin";
		case "DOUBLE":
			return "NumSpin";
		case "DECIMAL":
			return "NumSpin";
	}
	return "";
}

//得到默认控件
function getDefaultQueryControl(type){
	switch(type){
    	case "VARCHAR":
		case "VARCHAR2":
    		return "textbox";
    	case "DATE":
    	case "TIMESTAMP":
    		return "datespan";
    	case "INTEGER":
    		return "combobox";
    	case "DOUBLE":
    	case "DECIMAL":
    		return "numspin";
    }
    return "";
}

function getDefaultQueryType(type){
	switch(type){
    	case "VARCHAR":
		case "VARCHAR2":
    		return "equal";
    	case "INTEGER":
    		return "equal";
    }
    return "";
}

//得到控件列数
function getQueryControlCols(ctrl){
	switch(ctrl){
		case "timespan":
    	case "textspan":
    	case "datespan":
    	case "numspan":
    		return 2;
    	default:
    		return 1;
    }
}

//生成新增sql语句时,的空处理
function getNvlValue(type){
	if(type=="String"){
		return "''";
	}else if(type=="Integer"){
		return "0";
	}else{
		return "";
	}
}

//是否有日期类型
function hasDateType(table){
	for(var i=0;i<table.fields.length;i++){
		var field = table.fields[i];
		if(field.type=="DATE" || field.type=="TIMESTAMP")return true;
	}
	return false;
}

//是否有金额类型
function hasDecimalType(table){
	for(var i=0;i<table.fields.length;i++){
		var field = table.fields[i];
		if(field.type=="DECIMAL")return true;
	}
	return false;
}

//得到主要字段
function getPKField(table){
	for(var i=0;i<table.fields.length;i++){
		var field = table.fields[i];
		if(field.pk)return field;
	}
	return null;
}

//得到伪删除字段
function getIsDelField(table){
	for(var i=0;i<table.fields.length;i++){
		var field = table.fields[i];
		if(field.name.indexOf("ISDEL")>-1 || field.name.indexOf("IS_DEL")>-1)return field;
	}
	return null;
}

//得到除主键外的字段字符串
function getNoPKColsString(table){
	var colNames = "";
	for(var i=0;i<table.fields.length;i++){
		var field = table.fields[i];
		if(!field.pk){
			colNames+=(colNames!="")?","+getFirstLower(field.name):getFirstLower(field.name);
		}
	}
	return colNames;
}

//得到包的后缀名
function getPackage(namespace){
	if(namespace){
		var ns = namespace.split(".");
		var len = ns.length;
		if(len>0){
			return ns[len-1];
		}
	}
	return null;
}

//产生随机数字,首位不为0于9
function randomSerialNum(bit){
	var nums=["0","1","2","3","4","5","6","7","8","9"];              
	var num= "";  
	for(var i=0;i<bit;i++){
		if(i==0){
			var r=Math.floor(Math.random()*8)+1;
			num+=nums[r];
		}else{
			var r=Math.floor(Math.random()*10);
			num+=nums[r];
		}
	}
	return num;
}
