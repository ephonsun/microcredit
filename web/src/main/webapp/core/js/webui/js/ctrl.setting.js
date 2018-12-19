/* 加载资源配置 tagName 标记名称 propName 属性名称 propValue 属性值（唯一） */
(function(δ){
	δ.addCtrl({tagName:"input",typeName:"textbox",construct:"nff.ctrl.textbox.construct"});
	δ.addCtrl({tagName:"input",typeName:"combobox",construct:"nff.ctrl.combobox.construct"});
	δ.addCtrl({tagName:"input",typeName:"datebox",construct:"nff.ctrl.datebox.construct"});
	δ.addCtrl({tagName:"input",typeName:"datespan",construct:"nff.ctrl.datespan.construct"});
	δ.addCtrl({tagName:"input",typeName:"numspin",construct:"nff.ctrl.numspin.construct"});
	δ.addCtrl({tagName:"input",typeName:"numspan",construct:"nff.ctrl.numspan.construct"});
	δ.addCtrl({tagName:"input",typeName:"listbox",construct:"nff.ctrl.listbox.construct"});
	δ.addCtrl({tagName:"input",typeName:"toolbox",construct:"nff.ctrl.toolbox.construct"});
	δ.addCtrl({tagName:"input",typeName:"gridbox",construct:"nff.ctrl.gridbox.construct"});
	δ.addCtrl({tagName:"input",typeName:"treebox",construct:"nff.ctrl.treebox.construct"});
	δ.addCtrl({tagName:"input",typeName:"checkbox",construct:"nff.ctrl.checkbox.construct"});
	δ.addCtrl({tagName:"input",typeName:"radio",construct:"nff.ctrl.radio.construct"});
	δ.addCtrl({tagName:"input",typeName:"button",construct:"nff.ctrl.button.construct"});
	δ.addCtrl({tagName:"input",typeName:"textarea",construct:"nff.ctrl.textarea.construct"});
	
	δ.addCtrl({tagName:"div",typeName:"pagesize",construct:"nff.ctrl.pagesize.construct"});
	δ.addCtrl({tagName:"div",typeName:"gridview",construct:"nff.ctrl.gridview.construct"
			  ,children:[{tagName:"div",typeName:"column"},{tagName:"div",typeName:"pagesize"}]});
	δ.addCtrl({tagName:"div",typeName:"forms",construct:"nff.ctrl.forms.construct",children:[{tagName:"div",typeName:"cell"}]});
	δ.addCtrl({tagName:"div",typeName:"toolbar",construct:"nff.ctrl.toolbar.construct"});
	δ.addCtrl({tagName:"div",typeName:"menu",construct:"nff.ctrl.menu.construct"});
	δ.addCtrl({tagName:"div",typeName:"tab",construct:"nff.ctrl.tab.construct",children:[{tagName:"div",typeName:"item"}]});
	δ.addCtrl({tagName:"div",typeName:"mousemenu",construct:"nff.ctrl.mousemenu.construct"});
	δ.addCtrl({tagName:"div",typeName:"dialog",construct:"nff.ctrl.dialog.construct",container:true});
	δ.addCtrl({tagName:"div",typeName:"window",construct:"nff.ctrl.window.construct",container:true});
	δ.addCtrl({tagName:"div",typeName:"panel",construct:"nff.ctrl.panel.construct",container:true});
	δ.addCtrl({tagName:"div",typeName:"wtab",construct:"nff.ctrl.window.fn.setupTab"
			  ,children:[{tagName:"div",typeName:"window"},{tagName:"div",typeName:"panel"}]});
	δ.addCtrl({tagName:"div",typeName:"calendar",construct:"nff.ctrl.calendar.construct"});
	δ.addCtrl({tagName:"div",typeName:"propertygrid",construct:"nff.ctrl.propertygrid.construct"});
})(nff.ctrl.plugins);

/* 加载资源配置 */
(function(δ){
		  
	δ.addImage({name:"upTriangle4",src:"triangles.gif",region:{x:0,y:0,w:7,h:4}});
	δ.addImage({name:"downTriangle4",src:"triangles.gif",region:{x:0,y:-3,w:7,h:4}});
	δ.addImage({name:"leftTriangle4",src:"triangles.gif",region:{x:0,y:0,w:4,h:7}});
	δ.addImage({name:"rightTriangle4",src:"triangles.gif",region:{x:-4,y:0,w:4,h:7}});
	
	δ.addImage({name:"rightDownTriangle6",src:"triangles.gif",region:{x:0,y:-16,w:6,h:6},status:"black"});
	δ.addImage({name:"rightDownTriangle6",src:"triangles.gif",region:{x:-11,y:-16,w:6,h:6},status:"white"});
	
	δ.addImage({name:"rightHollowTriangle5",src:"triangles.gif",region:{x:-22,y:-7,w:5,h:9},status:"black"});
	δ.addImage({name:"rightHollowTriangle5",src:"triangles.gif",region:{x:-40,y:-7,w:5,h:9},status:"white"});
	
	δ.addImage({name:"upTriangle",src:"spiner_up2.png",region:{x:0,y:0,w:8,h:4},status:"up"});
	δ.addImage({name:"upTriangle",src:"spiner_up1.png",region:{x:0,y:0,w:8,h:4},status:"down"});
	δ.addImage({name:"downTriangle",src:"spiner_down2.png",region:{x:0,y:0,w:8,h:4},status:"up"});
	δ.addImage({name:"downTriangle",src:"spiner_down1.png",region:{x:0,y:0,w:8,h:4},status:"down"});
	
	δ.addImage({name:"drop",src:"drop-normal.gif",region:{x:0,y:0,w:22,h:22},status:"normal"});
	δ.addImage({name:"drop",src:"drop-over.gif",region:{x:0,y:0,w:22,h:22},status:"over"});
	δ.addImage({name:"prop",src:"prop.ico",region:{x:0,y:0,w:16,h:16},status:"normal"});
	δ.addImage({name:"prop",src:"prop.ico",region:{x:0,y:0,w:16,h:16},status:"over"});
	
	δ.addImage({name:"calendar",src:"calendar.gif",region:{x:0,y:0,w:22,h:22},status:"normal"});
	δ.addImage({name:"calendar",src:"calendar.gif",region:{x:-22,y:0,w:22,h:22},status:"over"});
	
	δ.addImage({name:"plusMinus",src:"tree.gif",region:{x:-76,y:-92,w:18,h:18},status:"plus"});
	δ.addImage({name:"plusMinus",src:"tree.gif",region:{x:-94,y:-92,w:18,h:18},status:"minus"});
	
	δ.addImage({name:"check",src:"tree.gif",region:{x:0,y:0,w:14,h:14},status:"normal"});
	δ.addImage({name:"check",src:"tree.gif",region:{x:-14,y:0,w:14,h:14},status:"select"});
	δ.addImage({name:"check",src:"tree.gif",region:{x:0,y:-14,w:14,h:14},status:"over"});
	δ.addImage({name:"radio",src:"tree.gif",region:{x:-28,y:0,w:14,h:14},status:"normal"});
	δ.addImage({name:"radio",src:"tree.gif",region:{x:-42,y:0,w:14,h:14},status:"select"});
	δ.addImage({name:"radio",src:"tree.gif",region:{x:-28,y:-14,w:14,h:14},status:"over"});
	
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-56,y:0,w:18,h:18},status:"treeF"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-56,y:-18,w:18,h:18},status:"treeT"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-56,y:-36,w:18,h:18},status:"treeL"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-56,y:-54,w:18,h:18},status:"treeWhite"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-74,y:0,w:18,h:18},status:"treeFplus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-74,y:-18,w:18,h:18},status:"treeMplus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-74,y:-36,w:18,h:18},status:"treeLplus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-74,y:-54,w:18,h:18},status:"treeOplus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-92,y:0,w:18,h:18},status:"treeFminus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-92,y:-18,w:18,h:18},status:"treeMminus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-92,y:-36,w:18,h:18},status:"treeLminus"});
	δ.addImage({name:"treeBranch",src:"tree.gif",region:{x:-92,y:-54,w:18,h:18},status:"treeOminus"});
	
	δ.addImage({name:"framebox",src:"design.gif",region:{x:0,y:0,w:6,h:6},status:"normal"});
	δ.addImage({name:"framebox",src:"design.gif",region:{x:0,y:-5,w:6,h:6},status:"active"});
	δ.addImage({name:"framebox",src:"design.gif",region:{x:-6,y:0,w:6,h:6},status:"lock"});
	
	δ.addImage({name:"upDock",src:"dock.gif",region:{x:-32,y:0,w:32,h:32}});
	δ.addImage({name:"downDock",src:"dock.gif",region:{x:-32,y:-64,w:32,h:32}});
	δ.addImage({name:"leftDock",src:"dock.gif",region:{x:0,y:-32,w:32,h:32}});
	δ.addImage({name:"rightDock",src:"dock.gif",region:{x:-64,y:-32,w:32,h:32}});
	δ.addImage({name:"upHalfDock",src:"dock.gif",region:{x:0,y:0,w:32,h:32}});
	δ.addImage({name:"downHalfDock",src:"dock.gif",region:{x:-64,y:0,w:32,h:32}});
	δ.addImage({name:"leftHalfDock",src:"dock.gif",region:{x:0,y:-64,w:32,h:32}});
	δ.addImage({name:"rightHalfDock",src:"dock.gif",region:{x:-64,y:-64,w:32,h:32}});
	δ.addImage({name:"centerDock",src:"dock.gif",region:{x:-32,y:-32,w:32,h:32}});
	
	δ.addImage({name:"toolbox",src:"toolbox.gif",region:{x:0,y:0,w:29,h:25}});
	δ.addImage({name:"toolbox",src:"toolbox.gif",region:{x:-29,y:0,w:29,h:25},status:"select"});
	
	δ.addImage({name:"logo",src:"logo2.gif",region:{x:0,y:0,w:32,h:32}});
	
	δ.addImage({name:"minWin",src:"window.gif",region:{x:-10,y:0,w:10,h:10},status:"normal"});
	δ.addImage({name:"maxWin",src:"window.gif",region:{x:-20,y:0,w:10,h:10},status:"normal"});
	δ.addImage({name:"revertWin",src:"window.gif",region:{x:0,y:0,w:10,h:10},status:"normal"});
	δ.addImage({name:"closeWin",src:"window.gif",region:{x:-30,y:0,w:10,h:10},status:"normal"});
	δ.addImage({name:"downWin",src:"window.gif",region:{x:-40,y:0,w:10,h:10},status:"normal"});
	
	δ.addImage({name:"minWin",src:"window.gif",region:{x:-10,y:-10,w:10,h:10},status:"over"});
	δ.addImage({name:"maxWin",src:"window.gif",region:{x:-20,y:-10,w:10,h:10},status:"over"});
	δ.addImage({name:"revertWin",src:"window.gif",region:{x:0,y:-10,w:10,h:10},status:"over"});
	δ.addImage({name:"closeWin",src:"window.gif",region:{x:-30,y:-10,w:10,h:10},status:"over"});
	δ.addImage({name:"downWin",src:"window.gif",region:{x:-40,y:-10,w:10,h:10},status:"over"});
	
	δ.addImage({name:"minWin",src:"window.gif",region:{x:-10,y:-20,w:10,h:10},status:"select"});
	δ.addImage({name:"maxWin",src:"window.gif",region:{x:-20,y:-20,w:10,h:10},status:"select"});
	δ.addImage({name:"revertWin",src:"window.gif",region:{x:0,y:-20,w:10,h:10},status:"select"});
	δ.addImage({name:"closeWin",src:"window.gif",region:{x:-30,y:-20,w:10,h:10},status:"select"});
	δ.addImage({name:"downWin",src:"window.gif",region:{x:-40,y:-20,w:10,h:10},status:"select"});
	
	δ.addImage({name:"tabFork",src:"tabs-icon.png",region:{x:0,y:0,w:17,h:17},status:"normal"});
	δ.addImage({name:"tabFork",src:"tabs-icon.png",region:{x:0,y:-17,w:17,h:17},status:"over"});
	
	δ.addImage({name:"tabLeft",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"normal"});
	δ.addImage({name:"tabLeft",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"over"});
	
	δ.addImage({name:"tabRight",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"normal"});
	δ.addImage({name:"tabRight",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"over"});
	
	δ.addImage({name:"tabDrop",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"normal"});
	δ.addImage({name:"tabDrop",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"over"});
	
	δ.addImage({name:"tabClose",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"normal"});
	δ.addImage({name:"tabClose",src:"tabs-icon.png",region:{x:0,y:0,w:16,h:16},status:"over"});
	
})(nff.widget.graph);

/* 异常处理代码配置，可以通文件中查询错语代码定位断言位置 */
(function(Δ,δ){
	δ.addError({code:"1109",message:"",callback:Δ.assert.show});
	δ.addError({code:"1110",message:"JSON数据格式错误",file:"dao.js",callback:Δ.assert.show});
	δ.addError({code:"1201",message:"渲染及控件标签错误",file:"ctrl.js",callback:Δ.assert.show});
})(nff,nff.assert);  