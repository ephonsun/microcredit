jQuery.namespace("nff.graph.box");

(function($,δ,win){
	win.nff.graph.box=δ={
		/* 控件事件 */
		events:function(){
			this.onClick="";
			this.onMoving=[];			//称动事件
			this.onResizing=[];			//改变大小事件
		}
		/* 控件样式配置 */
		,styles:function(){
			this.frameStyle={"normal":"graph-box-frameStyle-normal","over":"graph-box-frameStyle-over","select":"graph-box-frameStyle-select"};		//图形外框样式
			this.textStyle="graph-box-textStyle";
		}
		,graph:function(){
			nff.ctrl.base.control.call(this);
			nff.ctrl.base.move.call(this);
			nff.ctrl.base.resize.call(this);
			this.events=new δ.events();
			this.styles=new δ.styles();
			this.width=100;
			this.height=100;
			this.text="";
			this.lock=false;
			this.id="";
			
			this.buildElement=function(){
				var f={css:{},attr:{}},t={css:{},attr:{}};
				with(this.styles){
					t.attr["class"]=textStyle;
					if(this.id)f.attr["id"]=this.id;
				}
				if(this.style)f.attr["style"]=this.style;
				this.frameObj=$("<div>").attr(f.attr).css(f.css);
				this.textObj=$("<span>").attr(t.attr).css(t.css);
				if(this.text)this.textObj.text(this.text);
				this.frameObj.append(this.textObj);
				this.setWidth(this.width);
				this.setHeight(this.height);
				this.setStyle("normal");
				this.frameObj.ctrl(this);
			};
			this.setHeight=function(height){
				var f={css:{}},t={css:{}};
				t.css["height"]=f.css["height"]=height;
				this.frameObj.css(f.css);
				this.textObj.css(t.css);
			};
			this.setStyle=function(state){
				switch(state){
					case "normal":
						$.dynamic.setToNormal(this.frameObj,this.styles.frameStyle);
						break;
					case "select":
						$.dynamic.setToSelect(this.frameObj,this.styles.frameStyle);
						break;
					case "over":
						$.dynamic.setToOver(this.frameObj,this.styles.frameStyle);
						break;
				}
			};
		}
		,construct:function(context){
			var graph = this.fn.create(context);
			return graph;
		}
		,fn:{
			create:function(context){
				var graph = new δ.graph();
				graph.ctrlSet=context.setting;
				this.setPropertys(graph);
				this.addEvents(graph);
				return graph;
			}
			,setPropertys:function(graph){		//设置控件属性
				if (graph.ctrlSet!=null){
					$.setPropertiesRecursive(graph,graph.ctrlSet,false);
					graph.buildElement();
				}
			}
			,addEvents:function(graph){
				$("body").bind({
					"mouseup":function(e){
						δ.fn.mouseUp(graph,e);
					}
					,"mousemove":function(e){
						δ.fn.mouseMove(graph,e);
					}
				});
				graph.frameObj.bind({
					"mouseover":function(e){
						δ.fn.mouseOver(graph,e)
					}
					,"mouseout":function(e){
						δ.fn.mouseOut(graph,e)
					}
					,"mousedown":function(e){
						δ.fn.mouseDown(graph,e)
					}
					,"click":function(e){
						δ.fn.boxClick(graph,e)
					}
				});
			}
			,mouseOver:function(graph,e){
				var elem=e.srcElement || e.target;
				graph.setStyle("over");
			}
			,mouseOut:function(graph,e){
				var elem=e.srcElement || e.target;
				graph.setStyle("normal");
			}
			,mouseDown:function(graph,e){
				var y = e.pageY;
				var x = e.pageX;
				if(!graph.lock){
					if($.mouse.getButton(e)==0){
						var cursor = graph.frameObj.resizeCursor(x,y,3);
						if(cursor && !graph.resize){
							if(!graph.resize)graph.beginResize(cursor,x,y);
						}
						
						if(!graph.resize && !graph.move)graph.beginMove(e.pageX,e.pageY);
					}
				}
			}
			,mouseMove:function(graph,e){
				var y = e.pageY;
				var x = e.pageX;
				
				if(!graph.resize){
					var cursor = graph.frameObj.resizeCursor(x,y,3);
					if(cursor){
						graph.frameObj.css({"cursor":cursor});
					}else graph.frameObj.css({"cursor":"default"});
				}
				
				if(graph.resize){
					graph.resizing(x,y);		//改变大小
					if(graph.events.onResizing)$.raise(graph.events.onResizing);
				}
				
				if(graph.move){
					graph.moving(x,y);			//拖动
					if(graph.events.onMoving)$.raise(graph.events.onMoving);
				}
			}
			,mouseUp:function(graph,e){
				if(graph.resize){
					graph.endResize();
				}
				
				if(graph.move){
					graph.endMove();
				}
			}
			,boxClick:function(graph,e){
				var elem=e.srcElement || e.target;
				graph.setStyle("select");
			}
		}
	}
})(jQuery,nff.graph.box,window);