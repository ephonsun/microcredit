package banger.framework.web.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.sql.SqlMapException;
import banger.framework.util.StringUtil;
import banger.framework.web.LayoutException;
import banger.framework.xml.XmlNode;
import banger.framework.xml.XmlNodeType;

/**
 * 布局对像
 */
public class Layout implements ILayout {
	private Map<String,Forms> formsMap;
	
	public Layout(List<XmlNode> nodes){
		this.formsMap = new HashMap<String,Forms>();
		this.loadForms(nodes);
	}
	
	private void loadForms(List<XmlNode> nodes){
		for(XmlNode node : nodes){
			if(node.getName().equalsIgnoreCase("Forms")){
				Forms forms = this.buildForms(node);
				if(!this.formsMap.containsKey(forms.getId()))this.formsMap.put(forms.getId(), forms);
				else throw new LayoutException(StringUtil.format("Forms配置Id[{0}]重复", forms.getId()));
			}
		}
	}
	
	/**
	 * 构造表单配置对像
	 * @param node
	 * @return
	 */
	private Forms buildForms(XmlNode node){
		Forms forms = new Forms();
		forms.setNode(node);
		
		if(!node.getAttributes().containsKey("id")) throw new SqlMapException("forms节点 id属性未定义");
		String id = node.getAttributes().get("id").getValue();
		forms.setId(id);
		
		if(node.getAttributes().containsKey("columns")){
			forms.setColumns(Integer.parseInt(node.getAttributes().get("columns").getValue()));
		}
		
		if(node.getAttributes().containsKey("name")){
			forms.setName(node.getAttributes().get("name").getValue());
		}
		
		if(node.getAttributes().containsKey("readonly")){
			forms.setReadonly("true".equalsIgnoreCase(node.getAttributes().get("readonly").getValue()));
		}
		
		if(node.getAttributes().containsKey("ratio")){
			forms.setRatio(Double.parseDouble(node.getAttributes().get("ratio").getValue()));
		}
		
		if(node.getAttributes().containsKey("base")){
			String base = node.getAttributes().get("base").getValue();
			if(this.formsMap.containsKey(base)){
				Forms baseForms = this.formsMap.get(base);
				for(XmlNode n : baseForms.getNode().getChildNodes()){
					if(n.getNodeType()==XmlNodeType.Element){
						if(n.getName().equalsIgnoreCase("field")){
							Field field = this.buildField(n,forms);
							forms.addField(field);
						}
					}
				}
			}
		}
		
		for(XmlNode n : node.getChildNodes()){
			if(n.getNodeType()==XmlNodeType.Element){
				if(n.getName().equalsIgnoreCase("field")){
					Field field = this.buildField(n,forms);
					if(StringUtil.isNullOrEmpty(field.getName()) || !forms.containsKey(field.getName())){
						forms.addField(field);
					}
				}
			}
		}
		
		forms.fillRows();
		return forms;
	}
	
	/**
	 * 构造字段配置对像
	 * @param node
	 * @return
	 */
	private Field buildField(XmlNode node,Object parent){
		
		Field field = new Field();
		
		XmlNode nameNode = this.getChildNode(node,"name");
		if(nameNode!=null){
			String name = this.getNodeText(nameNode);
			if(!StringUtil.isNullOrEmpty(name))field.setName(name);
			
			if(parent instanceof Forms){
				Forms forms = (Forms)parent;
				if(forms.containsKey(name)){
					field = forms.getFieldByName(name);
				}
			}
		}
		
		XmlNode displayNode = this.getChildNode(node,"display");
		if(displayNode!=null){
			String display = this.getNodeText(displayNode);
			if(!StringUtil.isNullOrEmpty(display))field.setDisplay(display);
		}
		
		XmlNode cdNode = this.getChildNode(node,"codetable");
		if(cdNode!=null){
			String codetable = this.getNodeText(cdNode);
			if(!StringUtil.isNullOrEmpty(codetable)){
				String str = codetable.trim();
				int n = str.indexOf("(");
				int m = str.indexOf(")");
				if(n>-1 && m>n){
					field.setCodeTable(str.substring(0, n));
					field.setCodeTableParams(str.substring(n+1,m));
				}else{
					field.setCodeTable(str);
				}
			}
		}
		
		XmlNode measNode = this.getChildNode(node,"measure");
		if(measNode!=null){
			String measure = this.getNodeText(measNode);
			if(!StringUtil.isNullOrEmpty(measure))field.setMeasure(measure);
		}
		
		XmlNode hourNode = this.getChildNode(node,"hour");
		if(hourNode!=null){
			String hour = this.getNodeText(hourNode);
			if("false".equalsIgnoreCase(hour))field.setHour(false);
		}
		
		XmlNode minuteNode = this.getChildNode(node,"minute");
		if(minuteNode!=null){
			String minute = this.getNodeText(minuteNode);
			if("false".equalsIgnoreCase(minute))field.setMulti(false);
		}
		
		XmlNode secondNode = this.getChildNode(node,"second");
		if(secondNode!=null){
			String second = this.getNodeText(secondNode);
			if("false".equalsIgnoreCase(second))field.setSecond(false);
		}
		
		XmlNode multiNode = this.getChildNode(node,"multi");
		if(multiNode!=null){
			String multi = this.getNodeText(multiNode);
			if("true".equalsIgnoreCase(multi))field.setMulti(true);
		}
		
		XmlNode columnNode = this.getChildNode(node,"column");
		if(columnNode!=null){
			String column = this.getNodeText(columnNode);
			if(!StringUtil.isNullOrEmpty(column))field.setColumn(column);
		}
		
		XmlNode ctrlNode = this.getChildNode(node,"control");
		if(ctrlNode!=null){
			String control = this.getNodeText(ctrlNode);
			if(!StringUtil.isNullOrEmpty(control))field.setControl(control);
		}
		
		XmlNode colsNode = this.getChildNode(node,"cols");
		if(colsNode!=null){
			String cols = this.getNodeText(colsNode);
			if(!StringUtil.isNullOrEmpty(cols))field.setCols(Integer.parseInt(cols));
		}
		
		if(parent instanceof Forms){
			Forms forms = (Forms)parent;
			if(field.getCols()<0)field.setCols(forms.getColumns());
		}
		
		XmlNode rowsNode = this.getChildNode(node,"rows");
		if(rowsNode!=null){
			String rows = this.getNodeText(rowsNode);
			if(!StringUtil.isNullOrEmpty(rows))field.setCols(Integer.parseInt(rows));
		}
		
		XmlNode queryNode = this.getChildNode(node,"query");
		if(queryNode!=null){
			String query = this.getNodeText(queryNode);
			if(!StringUtil.isNullOrEmpty(query))field.setQuery(query);
		}
		
		XmlNode typeNode = this.getChildNode(node,"type");
		if(typeNode!=null){
			String type = this.getNodeText(typeNode);
			if(!StringUtil.isNullOrEmpty(type))field.setValueType(type);
		}
		
		XmlNode nullableNode = this.getChildNode(node,"nullable");
		if(nullableNode!=null){
			String nullable = this.getNodeText(nullableNode);
			if("false".equalsIgnoreCase(nullable))field.setNullable(false);
		}
		
		XmlNode mergeNode = this.getChildNode(node,"merge");
		if(mergeNode!=null){
			String merge = this.getNodeText(mergeNode);
			if(!StringUtil.isNullOrEmpty(merge))field.setMerge(merge);
		}
		
		XmlNode htmlNode = this.getChildNode(node,"html");
		if(htmlNode!=null){
			String html = this.getNodeText(htmlNode);
			if(!StringUtil.isNullOrEmpty(html))field.setHtml(html);
		}
		
		XmlNode readonlyNode = this.getChildNode(node,"readonly");
		if(readonlyNode!=null){
			String readonly = this.getNodeText(readonlyNode);
			
			if("false".equalsIgnoreCase(readonly)){
				field.setReadonly(false);
			}else if("true".equalsIgnoreCase(readonly)){
				field.setReadonly(true);
			}
		}else{
			if(parent instanceof Forms){
				Forms forms = (Forms)parent;
				field.setReadonly(forms.isReadonly());
			}
		}
		
		XmlNode validNode = this.getChildNode(node,"valid");
		if(validNode!=null){
			String valid = this.getNodeText(validNode);
			if(!StringUtil.isNullOrEmpty(valid))field.setValid(valid);
		}
		
		return field;
	}
	
	private String getNodeText(XmlNode node){
        for(XmlNode n : node.getChildNodes()){
            if (n.getNodeType() == XmlNodeType.Text) return n.getValue();
            else if(n.getNodeType() == XmlNodeType.CDATA) return n.getValue();
        }
        return "";
    }
	
	private XmlNode getChildNode(XmlNode node, String name){
        for(XmlNode n : node.getChildNodes()){
            if (n.getName().equalsIgnoreCase(name)){
                return n;
            }
        }
        return null;
    }
	
	
	@Override
	public Forms getForms(String name) {
		if(this.formsMap.containsKey(name))return this.formsMap.get(name);
		return null;
	}

}
