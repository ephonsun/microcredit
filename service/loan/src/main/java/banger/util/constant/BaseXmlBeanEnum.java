package banger.util.constant;

import org.apache.commons.lang.enums.Enum;

import java.util.Map;

public class BaseXmlBeanEnum extends Enum {

	private static final long serialVersionUID = 6582579870155714449L;

//	发送报文 字段，标记，长度，是否必填，select转换，默认值
//	接收报文 字段，标记

	private String elementName ;
	private String elementColumnName ;
	private String elementLength ;
	private String elementNotNull ;
	private Map<String,String> elementOptions ;
	private String elementDefaultValue ;


	public BaseXmlBeanEnum(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String,String> elementOptions, String elementDefaultValue) {
		super(elementName);
		this.elementName = elementName;
		this.elementColumnName = elementColumnName;
		this.elementLength = elementLength;
		this.elementNotNull = elementNotNull;
		this.elementOptions = elementOptions;
		this.elementDefaultValue = elementDefaultValue;
	}


	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementColumnName() {
		return elementColumnName;
	}

	public void setElementColumnName(String elementColumnName) {
		this.elementColumnName = elementColumnName;
	}

	public String getElementLength() {
		return elementLength;
	}

	public void setElementLength(String elementLength) {
		this.elementLength = elementLength;
	}

	public String getElementNotNull() {
		return elementNotNull;
	}

	public void setElementNotNull(String elementNotNull) {
		this.elementNotNull = elementNotNull;
	}

	public Map<String, String> getElementOptions() {
		return elementOptions;
	}

	public void setElementOptions(Map<String, String> elementOptions) {
		this.elementOptions = elementOptions;
	}

	public String getElementDefaultValue() {
		return elementDefaultValue;
	}

	public void setElementDefaultValue(String elementDefaultValue) {
		this.elementDefaultValue = elementDefaultValue;
	}

}
