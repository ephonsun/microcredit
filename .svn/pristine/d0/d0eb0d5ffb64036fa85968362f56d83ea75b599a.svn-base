package banger.framework.web.layout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.sql.SqlMapException;
import banger.framework.util.StringUtil;
import banger.framework.web.LayoutException;
import banger.framework.xml.XmlDocument;
import banger.framework.xml.XmlNode;
import banger.framework.xml.XmlNodeType;

public class LayoutLoader implements ILayoutLoader {
	private Map<String,ILayout> layouts;			//页面布局对像
	
	public LayoutLoader(){
		this.layouts = new HashMap<String,ILayout>();
	}
	
	public ILayout load(String resource){
		if(!this.layouts.containsKey(resource)){
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(resource);
			if(stream==null)throw new LayoutException(StringUtil.format("找不到该资源文件 {0}",resource));
			XmlDocument xdoc = null;
			try{xdoc=new XmlDocument(stream);}
			catch(Exception e){throw new SqlMapException(StringUtil.format("读取资源文件 {0} 出错,不是有效的xml文件",resource),e);}
			
			for(XmlNode root : xdoc.getChildNodes()){
	        	if(root.getName().equalsIgnoreCase("banger")){
	        		List<XmlNode> nodes = new ArrayList<XmlNode>();
	        		for(XmlNode node : root.getChildNodes()){
	        			if(node.getNodeType() == XmlNodeType.Element){
	        				nodes.add(node);
	        			}
	        		}
	        		ILayout layout = new Layout(nodes);
	        		this.layouts.put(resource, layout);
	        	}
	        }
		}
		return this.layouts.get(resource);
	}
	
}
