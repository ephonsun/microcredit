/* 加载资源配置 */
(function(δ){
	δ.addCtrl({tagName:"gbox",ctrlName:"gbox",construct:"nff.graph.box.construct"});
	δ.addCtrl({tagName:"gline",ctrlName:"gline",attrs:{"to":true,"form":true},construct:"nff.graph.line.construct"});
})(nff.ctrl.plugins);

/* 加载资源配置 */
(function(δ){
	δ.addImage({name:"pointTriangle",src:"triangles.gif",region:{x:0,y:0,w:7,h:4},status:"up"});
	δ.addImage({name:"pointTriangle",src:"triangles.gif",region:{x:0,y:-3,w:7,h:4},status:"down"});
	δ.addImage({name:"pointTriangle",src:"triangles.gif",region:{x:0,y:0,w:4,h:7},status:"left"});
	δ.addImage({name:"pointTriangle",src:"triangles.gif",region:{x:-4,y:0,w:4,h:7},status:"right"});
})(nff.widget.graph);