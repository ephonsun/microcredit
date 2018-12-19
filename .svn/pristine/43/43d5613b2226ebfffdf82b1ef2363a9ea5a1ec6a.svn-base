// JavaScript Document

try{
	if(!document.getElementById('file-button')){
		var input = document.createElement('input');
		
		input.setAttribute('type', 'button');
		input.setAttribute('id', 'file-button');
		input.setAttribute('value', 'file button');
		
		input.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0,finishOpacity=100)';
		input.style.opacity = '0';
		
		window.onload = function(){
			document.body.appendChild(input);
		};
	}
}
catch(e){ alert('Failed to create the element!'); }

var file = {};
file.firefox = function(element){
	try{
        netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
    } 
    catch(e){
        alert('无法访问本地文件，由于浏览器安全设置。为了克服这一点，请按照下列步骤操作：(1)在地址栏输入"about:config"；(2)右键点击并选择 New->Boolean；(3)输入"signed.applets.codebase_principal_support"（不含引号）作为一个新的首选项的名称；(4)点击OK并试图重新加载文件！');
        return;
    }
    var fileName = element.value; //这一步就能得到客户端完整路径。下面的是否判断的太复杂，还有下面得到ie的也很复杂。
    var file = Components.classes['@mozilla.org/file/local;1'].createInstance(Components.interfaces.nsILocalFile);
    try{
        // Back slashes for windows
        file.initWithPath( fileName.replace(/\//g, '\\\\') );
    }
    catch(e){
        if(e.result != Components.results.NS_ERROR_FILE_UNRECOGNIZED_PATH) throw e;
        alert("File '" + fileName + "' cannot be loaded: relative paths are not allowed. Please provide an absolute path to this file.");
        return;
    }
    if (file.exists() == false) {
        alert("File '" + fileName + "' not found.");
        return;
    }
	
	return file.path;
};
file.val = function(element){
	var n = {}, u = navigator.userAgent.toLowerCase(), s;
	
	(s = u.match(/msie ([\d.]+)/)) ? n.ie = s[1] : 
	(s = u.match(/firefox\/([\d.]+)/)) ? n.firefox = s[1] : 
	(s = u.match(/chrome\/([\d.]+)/)) ? n.chrome = s[1] : 
	(s = u.match(/opera.([\d.]+)/)) ? n.opera = s[1] : 
	(s = u.match(/version\/([\d.]+).*safari/)) ? n.safari = s[1] : 0;
	
	var path = '';
	
	if(n.ie <= 6.0){
		path = element.value;
	}
	else if(n.ie >= 7.0){
		element.select();
		document.getElementById('file-button').focus();
		
		path = document.selection.createRange().text;
		document.selection.empty();
	}
	else if(n.firefox){
		path = this.firefox(element);
	}
	else if(n.chrome){
		path = element.value;
	}
	
	return path;
};