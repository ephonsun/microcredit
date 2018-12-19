//////////////////////////////////////////////////////
/////   Author: Zhu Sheng Wei (Nov-9th 1980)       ///
////    Nff WebUI Javascript Control TreeBox	  ////
//////////////////////////////////////////////////////

(function($,Δ,δ){
	Δ.ctrl.treebox=δ={
		/* 控件样式配置 */
		styles:function(){
			this.frameStyle="treebox-frameStyle";			//外框样式
			this.nodeStyle="treebox-nodeStyle";				//节点样式
			this.nodeTextStyle={"normal":"treebox-node-textStyle-normal","over":"treebox-node-textStyle-over","select":"treebox-node-textStyle-select"};			//节点文本样式
			this.childrenStyle="treebox-childrenStyle";		//子节点外框样式
			this.iconStyle="treebox-iconStyle";				//节点图标样式
			this.checkStyle="treebox-checkStyle";			//节点选择框样式
			this.lineStyle="treebox-lineStyle";				//父节点背景线样式
		}
		/* 树型节点 */
		,node:function(ctrl,index,dataIndex,pNode){				//构造参数 ctrl控件对像 index节点索引 dataIndex数据索引 pNode父节点
			Δ.ctrl.base.drag.call(this);		//继承拖动方法
			this.frameObj=null;					//外框标签
			this.ctrl=ctrl;						//控件对像
			this.children=null;					//子节点对像集合
			this.childrenObj=null;				//子节点标签集合
			this.textObj=null;					//节点文本标签
			this.imageObj=null;					//节点图标标签
			this.checkObj=null;					//节点选择框标签
			this.index=index;					//节点索引
			this.dataIndex=dataIndex;			//节点数据索引
			this.parentNode=pNode;				//父节点对像
			this.isRoot=false;					//是否为根节点
			this.extended=false;				//节点是否展开
			
			/* 构选节点标签 */
			this.buildElement=function(){
				with(this.ctrl){
					var n={css:{},attr:{}},i={css:{},attr:{}},c={css:{},attr:{}};
					n.attr["class"]=styles.nodeStyle;
					i.attr["class"]=styles.iconStyle;
					c.attr["class"]=styles.checkStyle;
					n.attr["indexs"]=this.getIndexs().join(",");
					this.frameObj=$("<li>").attr(n.attr);
					var index =  this.getImageIndex();
					this.imageObj = Δ.widget.graph.getElement("treeBranch",images[index],"<input type=button >");
					this.imageObj.attr(i.attr);
					this.frameObj.append(this.imageObj);
					if(multiselect){
						this.checkObj=Δ.widget.graph.getElement("check","normal","<input type=button >");
						this.checkObj.attr(c.attr);
						this.frameObj.append(this.checkObj);
					}
					var text = datatree.getNodeText(this.getDataIndexs());
					this.textObj = $("<label>");
					this.textObj.text(text);
					this.frameObj.append(this.textObj);
					this.setNodeStyle("normal");
					var node = datatree.getNode(this.getDataIndexs());
					if(node){
						if(node.extended)this.extended=true;
					}
				}
			};
			/* 展开节点 当参数flag为真时,会同时展开子节点 */
			this.extend=function(flag){
				with(this.ctrl){
					var nodeChildren = datatree.getNodeChildren(this.getDataIndexs());
					if(this.size() && nodeChildren){
						if(this.children==null)this.children=[];
						if(this.childrenObj==null){
							var c={css:{},attr:{}};
							c.attr["class"]=styles.childrenStyle;
							if(this.getPosition()=="middle"){
								c.css["background"]="url('"+Δ.widget.graph.getImageUrl("skin/images/line_conn.gif")+"')";
								c.css["backgroundRepeat"]="repeat-y";
								c.css["backgroundPosition"]="0px 0px";
							}
							this.childrenObj = $("<ul>").attr(c.attr).css(c.css);
							this.frameObj.append(this.childrenObj);
						}
						
						for(var i=0;i<nodeChildren.length;i++){
							var childNode = new δ.node(this.ctrl,i,i,this);
							childNode.buildElement();
							this.children.push(childNode);
							this.childrenObj.append(childNode.frameObj);
							if(flag)childNode.extend(flag);
						}
						this.extended=true;
						this.resetImage();
					}
				}
			};
			/* 收拢子节点 */
			this.retract=function(){
				if(this.size() && this.children){
					for(var i=0;i<this.children.length;i++){
						var n=this.children[i];
						if(n && n.extended)n.retract();
					}
					this.childrenObj.remove();
					this.childrenObj=null;
					this.children=null;
					this.extended=false;
					this.resetImage();
				}
			};
			/* 得到子节点的个数 */
			this.size=function(){
				var nodeChildren = this.ctrl.datatree.getNodeChildren(this.getDataIndexs());
				var n = (nodeChildren)?nodeChildren.length:0;
				var m = (this.children)?this.children.length:0;
				return (n>m)?n:m;
			};
			/* 得到节点的值 */
			this.getValue=function(){
				var value = this.ctrl.datatree.getNodeValue(this.getDataIndexs());
				return value;
			};
			/* 得到节点的文本 */
			this.getText=function(){
				var text = this.ctrl.datatree.getNodeText(this.getDataIndexs());
				return text;
			};
			/* 得到节点的数据对像 */
			this.getNode=function(){
				var node = this.ctrl.datatree.getNode(this.getDataIndexs());
				return node;
			};
			/* 得到节点图标的索引 */
			this.getImageIndex=function(){
				var position = this.getPosition();
				var index = -1;
				switch(position){
					case "top":
						if(this.size())index=(this.extended)?11:7;
						else index=3;
						break;
					case "middle":
						if(this.size())index=(this.extended)?9:5;
						else index=1;
						break;
					case "bottom":
						if(this.size())index=(this.extended)?10:6;
						else index=2;
						break;
				}
				return index;
			};
			/* 当展开或收拢子节点时刷新图标 */
			this.resetImage=function(){
				var index =  this.getImageIndex();
				Δ.widget.graph.setElement("treeBranch",this.ctrl.images[index],this.imageObj);
			};
			/* 得到节点的全路径索引 */
			this.getIndexs=function(){
				var indexs = [];
				if(this.parentNode!=null){
					var pIndexs = this.parentNode.getIndexs();
					for(var i=0;i<pIndexs.length;i++){
						indexs.push(pIndexs[i]);
					}
				}
				indexs.push(this.index);
				return indexs;
			};
			/* 得到节点的全路径数据索引 */
			this.getDataIndexs=function(){
				var indexs = [];
				if(this.parentNode!=null){
					var pIndexs = this.parentNode.getDataIndexs();
					for(var i=0;i<pIndexs.length;i++){
						indexs.push(pIndexs[i]);
					}
				}
				indexs.push(this.dataIndex);
				return indexs;
			};
			/* 得到节点的位置 */
			this.getPosition=function(){
				if(this.parentNode){
					if(this.index<(this.parentNode.size()-1))return "middle";
					else return "bottom";
				}
				else{
					if(this.isRoot)return "top";
					else{
						if(this.index==0)return "top";
						else if(this.index==(this.ctrl.nodes.length-1))return "bottom";
						else return "middle";
					}
				}
			};
			/* 是否为叶子节点 */
			this.isLeaf=function(){
				var node = this.getNode();
				if(node){
					if(node.children && node.children.length)return false;
					else return true;
				}
				return false;
			};
			/* 节点是否选中 */
			this.isSelected=function(){
				return this.textObj.attr("class")==this.ctrl.styles.nodeTextStyle.select;
			};
			/* 选中节点 参数flag为真时表示选中，为非时表示取消选中 */
			this.setSelected=function(flag){
				with(this.ctrl){
					if(!multiselect){			//是否为多选,单选情况下，清除其他选中项
						for(var i=0;i<nodes.length;i++)nodes[i].setNodeStyle("normal",true);
					}
					if(flag)this.setNodeStyle("select");
					else this.setNodeStyle("normal");
					if(this.ctrl.onafternodeselected)Δ.raise(this.ctrl.onafternodeselected,{"ctrl":this.ctrl,"node":this,"value":this.getValue(),"text":this.getText(),"data":this.getNode()});
				}
			};
			/* 设置节点样式 */
			this.setNodeStyle=function(state,flag){
				with(this.ctrl){
					switch(state){
						case "normal":
							Δ.dynamic.setToNormal(this.textObj,styles.nodeTextStyle);
							if(this.checkObj)Δ.widget.graph.setElement("check","normal",this.checkObj);
							break;
						case "select":
							Δ.dynamic.setToSelect(this.textObj,styles.nodeTextStyle);
							if(this.checkObj)Δ.widget.graph.setElement("check","select",this.checkObj);
							break;
						case "over":
							Δ.dynamic.setToOver(this.textObj,styles.nodeTextStyle);
							if(this.checkObj)Δ.widget.graph.setElement("check","over",this.checkObj);
							break;
					}
				}
				if(flag && this.children){
					for(var i=0;i<this.children.length;i++){this.children[i].setNodeStyle(state,flag);}
				}
			};
		}
		/* 控件主体对像 */
		,control:function(){
			Δ.ctrl.base.control.call(this);			//从基类继承属性和方法
			this.datatree=null;						//数据访对像
			this.styles=new δ.styles();
			this.width="100%";
			this.height=-1;
			this.rowHeight=20;						//行高
			this.nodes=[];							
			this.multiselect=false;					//是否多选
			this.extendMode="branch";
			this.extendNode="all";					//展开所有节点
			this.images=["treeF","treeT","treeL","treeWhite","treeFplus","treeMplus","treeLplus","treeOplus","treeFminus","treeMminus","treeLminus","treeOminus"];
			this.nodedrag=false;					//节点是否可以拖动		
			this.onbeforenodedrag="";				//节点拖动前触发事件
			this.onafternodedrag="";				//节点拖动后触发事件
			this.onbeforenodeselected="";			//节点选中前触发事件
			this.onafternodeselected="";			//节点选点后触发事件
			
			/* 构造控件Dom标记 */
			this.buildElement=function(){
				var f={css:{},attr:{}};
				with(this.styles){
					f.attr["class"]=frameStyle;
					if(this.id)f.attr["id"]=this.id;
				}
				if(this.style)f.attr["style"]=this.style;
				this.frameObj=$("<ul>").attr(f.attr).css(f.css);
				this.frameObj.ctrl(this);
				this.setWidth(this.width);
				if(this.height>0)this.setHeight(this.height);
			};
			/* 清除节点及Dom标记 */
			this.clear=function(){
				this.frameObj.empty();
				this.nodes=[];
			};
			/* 绑定数据 */
			this.dataBinding=function(){
				this.clear();
				var tree = this.datatree.getTree();
				if(tree){
					if(tree.constructor==Array){
						for(var i=0;i<tree.length;i++){
							var node = new δ.node(this,i,i);
							node.buildElement();
							if(tree.length==1)node.isRoot=true;
							this.nodes.push(node);
							this.frameObj.append(node.frameObj);
						}
					}else{
						var node = new δ.node(this,0,0);
						node.isRoot=true;
						node.buildElement();
						if(this.extendNode!="none")node.extend(true);
						this.nodes.push(node);
						this.frameObj.append(node.frameObj);
					}
					if(this.extendNode=="first" && this.nodes[0].isRoot)this.nodes[0].extend(false);
					else{
						for(var i=0;i<this.nodes.length;i++)this.nodes[i].extend(true);
					}
				}
			};
			/* 得到节点索引得到节点 */
			this.getNodeByIndexs=function(indexs){
				var node = null,index=0;
				for(var i=0;i<indexs.length;i++){
					index = indexs[i];
					node = (i==0)?this.nodes[index]:node.children[index];
				}
				return node;
			};
			/* 得到选中节点的集合 */
			this.getSelectNodes=function(){
				var list = []
				for(var i=0;i<this.nodes.length;i++){
					var node = this.nodes[i];
					if(node.isSelected())list.push(node);
				}
				return list;
			};
			/* 得到选中节点的值，多个值用逗号分隔 */
			this.getValue=function(){
				var value = "";
				var fn = function(n,f){
					var v=(n.isSelected())?n.getValue():"";
					if(n.children){
						var cv="";
						for(var i=0;i<n.children.length;i++){
							cv=f(n.children[i],f);
							if(cv)v+=(v)?","+cv:cv;
						}
					}
					return v;
				};
				for(var i=0;i<this.nodes.length;i++){
					var val = fn(this.nodes[i],fn);
					if(val)value+=(value)?","+val:val;
				}
				return value;
			};
			/* 得到选中节点的名称，多个名称用逗号分隔 */
			this.getText=function(){
				var text = "";
				var fn = function(n,f){
					var v=(n.isSelected())?n.getText():"";
					if(n.children){
						var cv="";
						for(var i=0;i<n.children.length;i++){
							cv=f(n.children[i],f);
							if(cv)v+=(v)?","+cv:cv;
						}
					}
					return v;
				};
				for(var i=0;i<this.nodes.length;i++){
					var val = fn(this.nodes[i],fn);
					if(val)text+=(text)?","+val:val;
				}
				return text;
			};
			/* 查找节点的值是否相等并选中 */
			this.selectByValue=function(value){
				var node,val;
				if(this.datatree){
					var vs = (typeof value == "string")?value.split(","):[];
					var fn = function(n,vs,f){
						val = n.getValue();
						for(var j=0;j<vs.length;j++){
							if(vs[j]==val)n.setSelected(true);
						}
						if(n.children){
							for(var i=0;i<n.children.length;i++){
								f(n.children[i],vs,f);
							}
						}
					};
					for(var i=0;i<this.nodes.length;i++){
						fn(this.nodes[i],vs,fn);
					}
				}
			};
			/* 控件赋完初始值后做额外的初始化工作 */
			this.propertiesInitialized=function(){
				if(this.data)this.datatree = Δ.ctrl.dao.fn.createDataTree(this.data);			//创建数据操作类
			};
		}
		,construct:function(context){
			var ctrl = this.fn.create(context);
			return ctrl;
		}
		,fn:{
			create:function(context){
				var ctrl = new δ.control();
				ctrl.ctrlSet=context.setting;
				this.setPropertys(ctrl);
				this.addEvents(ctrl);
				ctrl.dataBinding();
				return ctrl;
			}
			,setPropertys:function(ctrl){		//设置控件属性
				if (ctrl.ctrlSet!=null){
					Δ.setPropertiesRecursive(ctrl,ctrl.ctrlSet,false);
					ctrl.buildElement();
					ctrl.styles.itemStyle=Δ.dynamic.assign(ctrl.styles.itemStyle);
				}
			}
			,addEvents:function(ctrl){
				$("body").bind({
					"mouseup":function(e){
						δ.fn.mouseUp(ctrl,e);
					}
					,"mousemove":function(e){
						δ.fn.mouseMove(ctrl,e);
					}
				});
				ctrl.frameObj.bind({
					"mouseover":function(e){
						δ.fn.mouseOver(ctrl,e)
					}
					,"mouseout":function(e){
						δ.fn.mouseOut(ctrl,e)
					}
					,"mousedown":function(e){
						δ.fn.mouseDown(ctrl,e)
					}
					,"click":function(e){
						δ.fn.boxClick(ctrl,e)
					}
				});
			}
			,getEventNode:function(ctrl,e){
				var elem=e.srcElement || e.target;
				var nodeElement = (elem.tagName=="LI")?$(elem):$(elem).parent("li");
				if(nodeElement.length){
					var indexs = nodeElement.attr("indexs").split(",");
					var node = ctrl.getNodeByIndexs(indexs);
					return node;
				}
			}
			,mouseOver:function(ctrl,e){
				var node = this.getEventNode(ctrl,e);
				if(node && !node.isSelected())node.setNodeStyle("over");
			}
			,mouseOut:function(ctrl,e){
				var node = this.getEventNode(ctrl,e);
				if(node && !node.isSelected())node.setNodeStyle("normal");
			}
			,mouseDown:function(ctrl,e){
				if(ctrl.nodedrag){
					if(Δ.mouse.getButton(e)==0 && ctrl.dragNode==null){
						var node = this.getEventNode(ctrl,e);
						if(node){
							ctrl.dragNode = node;
							node.beginDrag(e.pageX,e.pageY);
						}
					}
				}
			}
			,mouseUp:function(ctrl,e){
				var y = e.pageY;
				var x = e.pageX;
				var node = ctrl.dragNode;
				if(node && node.drag){
					ctrl.dragNode=null;
					node.endDrag();
					var elem = Δ.ctrl.base.fn.getMouseOverElement(x,y);
					if(ctrl.onafternodedrag)Δ.raise(ctrl.onafternodedrag,{"node":node.getNode(),"target":elem});
				}
			}
			,mouseMove:function(ctrl,e){
				var y = e.pageY;
				var x = e.pageX;
				var node = ctrl.dragNode;
				if(node && node.drag){
					node.draging(x,y);
				}
			}
			,boxClick:function(ctrl,e){
				var elem=e.srcElement || e.target;
				var node = this.getEventNode(ctrl,e);
				if(node){
					if(elem==node.imageObj[0]){
						if(node.extended)node.retract();
						else node.extend();
					}else{
						node.setSelected(!node.isSelected());
					}
				}
			}
		}
	}
})(jQuery,nff);