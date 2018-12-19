package banger.framework.component.constraint;

import banger.framework.reader.Reader;

public class NotEmptyConstraint implements IConstraint {
	private String errorMessage;
	private String propertyName;

	public String getErrorMessage(){
		return errorMessage;
	}

	public NotEmptyConstraint(String propertyName,String errorMessage){
		this.errorMessage = errorMessage;
		this.propertyName = propertyName;
	}

	@Override
	public boolean verify(Object data) {
		Object val = Reader.objectReader().getValue(data, this.propertyName);
		if(val!=null){
			if(val instanceof String)return !"".equals(val);
			else return true;
		}
		return false;
	}

	@Override
	public String getErroMessage() {
		return errorMessage;
	}
}
