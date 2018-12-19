/**
 * Author: dingwei 2013-05-19
 * Description: banger.treegrid.js
 * Modified by: 
 * Modified contents: 
**/
if(typeof TableTree4JTableTree4J === 'undefined'){
	document.write('<script type="text/javascript" src="../core/js/third/TableTree4J/TableTree4J.js"><\/script>');
}

var getRandomAlphaNum = function(len){
    var str = '';
    for(; str.length < len; str += Math.random().toString(36).substr(2)){}
    return  str.substr(0, len);
};

(function($){
	$.fn.treegrid = function(o){
		// 扩展默认参数
		o = $.extend({}, {
			extendCell: null
		}, o);
		
		// 公共函数库
		var g = {
			
		};
		
		// 
		return this.each(function(){

            var rd = 'rd' + getRandomAlphaNum(10);

            window[rd] = new TableTree4J(rd, '../core/js/third/TableTree4J/');
            window[rd].tableDesc = '<table id="' + (this.id + '-table') + '">';
            window[rd].config.rootNodeBtn = false;
            window[rd].config.booleanInitOpenAll = true;
			$('#' + this.id).addClass('ui-treegrid');
			
			var headerDataList = [], widthList = [];
			
			for(var i = 0, len = o.fields.length; i < len; i++){
				var field = o.fields[i];
				headerDataList.push(field.display);
				widthList.push(field.width);
			}

            window[rd].config.highLightRowClassName = 'ui-treegrid-tr-hover';

            window[rd].setHeader(headerDataList, -1, widthList, true, 'ui-treegrid-head', '', '', '', '');

            window[rd].gridHeaderColStyleArray = new Array('', '', '', '');
            window[rd].gridDataCloStyleArray = new Array('', '', '', '');
			
			for(var j = 0, len = o.rows.length; j < len; j++){
				var row = o.rows[j];
				
				if(isFunction(o.extendCell)){
					row = o.extendCell(row);
				}
				
                window[rd].addGirdNode(row.cols, row.id, row.pId, row.bool, row.order);
			}

            window[rd].printTableTreeToElement(this.id);
			
		});
	};
})(jQuery);






























