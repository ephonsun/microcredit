//重置查询条件
function clearQuery(){
	$.json.attach("DS",{"Query":{}});
}

var oldQuery = {};

//查询页面数据
function queryList(){
	oldQuery=$.json.copy(DS.Query);
	gotoPage();
}

//查询数据

function gotoPage(){
	var postJson = {"Query":oldQuery,"PageSize":DS.PageSize};
	var postData = $.json.toString(postJson);
	$.ajax({
		type:"post",
		url:"../build/getQueryListData.html?pageId="+pageId,
		data:postData,
		contentType:"text/xml",
		success:function(data){
			var json = $.json.parse(data);
      		$.json.attach("DV",{"RecordList":json.RecordList});				//控件和数据绑定,只要数据源发生变化,绑定的控件自动刷新
      		$.json.attach("DS",{"PageSize":json.PageSize});
      		$("#recordCount").text(DS.PageSize.count);
		}
	});
}