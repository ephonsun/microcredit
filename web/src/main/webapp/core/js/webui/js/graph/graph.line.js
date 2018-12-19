jQuery.namespace("nff.graph.line");

(function($,δ,win){
	win.nff.graph.line=δ={
		/* 控件事件 */
		events:function(){
			this.onClick="";
		}
		/* 控件样式配置 */
		,styles:function(){
			this.frameStyle="graph-line-frameStyle";
			this.lineStyle={"normal":"graph-lineStyle-normal","over":"graph-lineStyle-over","select":"graph-lineStyle-select"};		//线外框样式
			this.triangleStyle="graph-line-triangleStyle";
			this.textStyle={"vertical":"graph-line-textStyle-vertical","horizon":"graph-line-textStyle-horizon"};
		}
		,point:function(graph){
			this.graph=graph;
			this.frameObj=null;
			this.target=null;
			this.toward="";
			this.mark="triangle";		//triangle or box
			this.icon=null;
			
			this.buildElement=function(){
				this.frameObj = $("<div>");
				if(this.mark=="triangle"){
					this.icon=new nff.widget.icon("pointTriangle",this.graph.oppo[this.toward]);
					this.icon.buildElement();
					this.icon.frameObj.attr("class",this.graph.styles.triangleStyle);
					if(this.toward=="up" || this.toward=="down"){
						this.icon.frameObj.css({"left":-3,"bottom":3});
					}else{
						this.icon.frameObj.css({"top":-3});
					}
					this.frameObj=this.icon.frameObj;
				}
			};
			this.getPosition=function(){
				if(this.target){
					var w = this.target.width();
					var h = this.target.height();
					var l = this.target.offset().left;
					var t = this.target.offset().top;
					var pos = {"up":{"x":l+w/2,"y":t},"down":{"x":l+w/2,"y":t+h},"left":{"x":l,"y":t+h/2},"right":{"x":l+w,"y":t+h/2}};
					return pos[this.toward];
				}
				return null;
			};
		}
		,line:function(graph){
			this.graph=graph;
			this.frameObj=null;
			this.textObj=null;
			this.point=null;
			this.text="";
			this.oppo=null;			//记录线的另一端点位置
			
			this.buildElement=function(){
				this.frameObj=$("<div>");
				this.setStyle("normal");
				
				if(this.text){
					this.textObj = $("<span>");
					this.textObj.text(this.text);
					this.frameObj.append(this.textObj);
				}
				
				if(this.point){
					this.point.buildElement();
					this.frameObj.append(this.point.frameObj);
				}
				
				this.setShape();
			};
			this.setShape=function(){			//设置线的形状和位置
				var pp = this.graph.getPointPosition();
				var h,w;
				if(this.point){
					var pos = this.point.getPosition();
					var toward = this.point.toward;
					if(toward == "up" || toward == "down"){			//垂直方向
						if(pp.type=="oppo"){
							h = Math.abs(pp.dis.y/2);
						}else if(pp.type=="side"){
							if(pos.y<pp.mid.y)h = Math.abs(pp.dis.y)+this.graph.extension;
							else h = this.graph.extension;
						}else{
							h = Math.abs(pp.dis.y);
						}
						this.frameObj.width(this.graph.border);
						this.frameObj.height(h);
						if(toward=="up"){
							this.frameObj.css({"left":pos.x,"top":pos.y-h});
							this.oppo={"x":pos.x,"y":pos.y-h};
						}else{
							this.frameObj.css({"left":pos.x,"top":pos.y});
							this.oppo={"x":pos.x,"y":pos.y+h};
						}
					}else if(toward == "left" || toward == "right"){		//水平方向
						if(pp.type=="oppo"){
							w = Math.abs(pp.dis.x/2);
						}else if(pp.type=="side"){
							if(pos.x<pp.mid.x)w = Math.abs(pp.dis.x)+this.graph.extension;
							else w = this.graph.extension;
						}else{
							w = Math.abs(pp.dis.x);
						}
						this.frameObj.height(this.graph.border);
						this.frameObj.width(w);
						if(toward=="left"){
							this.frameObj.css({"top":pos.y,"left":pos.x-w});
							this.oppo={"x":pos.x,"y":pos.y};
						}else{
							this.frameObj.css({"top":pos.y,"left":pos.x});
							this.oppo={"x":pos.x+w,"y":pos.y};
						}
					}
				}else{
					var fp = this.graph.formLine.oppo;
					var tp = this.graph.toLine.oppo;
					if(fp.y==tp.y){			//水平线
						w = Math.abs(fp.x-tp.x);
						this.frameObj.css({"top":fp.y,"left":(fp.x>tp.x)?tp.x:fp.x});
						this.frameObj.height(this.graph.border);
						this.frameObj.width(w);
						if(this.textObj)this.textObj.attr("class",this.graph.styles.textStyle.vertical);
					}else if(fp.x==tp.x){			//垂直线
						h = Math.abs(fp.y-tp.y);
						this.frameObj.css({"top":(fp.y>tp.y)?tp.y:fp.y,"left":fp.x});
						this.frameObj.width(this.graph.border);
						this.frameObj.height(h);
						if(this.textObj)this.textObj.attr("class",this.graph.styles.textStyle.horizon);
					}
				}
			};
			this.setStyle=function(state){
				with(this.graph){
					switch(state){
						case "normal":
							$.dynamic.setToNormal(this.frameObj,styles.lineStyle);
							break;
						case "select":
							$.dynamic.setToSelect(this.frameObj,styles.lineStyle);
							break;
						case "over":
							$.dynamic.setToOver(this.frameObj,styles.lineStyle);
							break;
					}
				}
			};
		}
		,graph:function(){
			nff.ctrl.base.control.call(this);
			this.events=new δ.events();
			this.styles=new δ.styles();
			this.frameObj=null;
			this.text="";
			this.formLine=null;
			this.toLine=null;
			this.joinLine=null;
			this.formPoint=null;
			this.toPoint=null;
			this.border="1px";		//线的粗细
			this.extension=40;		//侧线延伸段
			this.form={"id":"","toward":""};
			this.to={"id":"","toward":""};
			this.oppo={"up":"down","down":"up","left":"right","right":"left"};
			
			this.buildElement=function(){
				var f={css:{},attr:{}};
				with(this.styles){
					f.attr["class"]=frameStyle;
				}
				if(this.style)f.attr["style"]=this.style;
				this.frameObj=$("<div>").attr(f.attr).css(f.css);
				
				if(this.form.id){
					var point = new δ.point(this);
					point.toward = this.form.toward;
					point.target = $("#"+this.form.id);
					point.mark = "box";
					this.formLine = new δ.line(this);
					this.formLine.point = this.formPoint = point;
				}
				if(this.to.id){
					var point = new δ.point(this);
					point.toward = this.to.toward;
					point.target = $("#"+this.to.id);
					point.mark = "triangle";
					this.toLine = new δ.line(this);
					this.toLine.point = this.toPoint = point;
				}
				
				this.joinLine = new δ.line(this);
				this.joinLine.text = this.text;
				
				this.formLine.buildElement();
				this.toLine.buildElement();
				this.joinLine.buildElement();
				
				this.frameObj.append(this.formLine.frameObj);
				this.frameObj.append(this.toLine.frameObj);
				this.frameObj.append(this.joinLine.frameObj);
			};
			this.resetShape=function(){				//重新绘制线的形状和位置
				this.formLine.setShape();
				this.toLine.setShape();
				this.joinLine.setShape();
			};
			this.getPointPosition=function(){			//得到线的两端位置
				var fp = this.formPoint.getPosition();
				var tp = this.toPoint.getPosition();
				var type = "";
				var ft = this.formPoint.toward;
				var tt = this.toPoint.toward;
				if(ft==tt)type = "side";	//同侧
				else if(ft==this.oppo[tt])type = "oppo";	//对面
				else type = "oblique";		//斜对面
				var dx = fp.x-tp.x;
				var dy = fp.y-tp.y;
				var mx = (dx>0)?fp.x-dx/2:tp.x+dx/2;
				var my = (dy>0)?fp.y-dy/2:tp.y+dy/2;
				return {"type":type,"dis":{"x":dx,"y":dy},"mid":{"x":mx,"y":my},"fp":fp,"tp":tp};
			};
			this.toHtml=function(){
				
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
				this.addRelationEvent(graph);
				return graph;
			}
			,setPropertys:function(graph){		//设置控件属性
				if (graph.ctrlSet!=null){
					$.setPropertiesRecursive(graph,graph.ctrlSet,false);
					graph.buildElement();
				}
			}
			,addEvents:function(graph){
				graph.frameObj.bind({
					"mouseover":function(e){
						δ.fn.mouseOver(graph,e)
					}
					,"mouseout":function(e){
						δ.fn.mouseOut(graph,e)
					}
					,"click":function(e){
						δ.fn.boxClick(graph,e)
					}
				});
			}
			,addRelationEvent:function(graph){			//添加关联事件
				var formCtrl = graph.formPoint.target.ctrl();
				formCtrl.events.onMoving.push(function(){
					graph.resetShape();
				});
				formCtrl.events.onResizing.push(function(){
					graph.resetShape();
				});
				var toCtrl = graph.toPoint.target.ctrl();
				toCtrl.events.onMoving.push(function(){
					graph.resetShape();
				});
				toCtrl.events.onResizing.push(function(){
					graph.resetShape();
				});
			}
			,mouseOver:function(graph,e){
				var elem=e.srcElement || e.target;
			}
			,mouseOut:function(graph,e){
				var elem=e.srcElement || e.target;
			}
			,boxClick:function(graph,e){
				var elem=e.srcElement || e.target;
			}
		}
	}
})(jQuery,nff.graph.line,window);