package banger.framework.component.constraint;

public interface IConstraint {
	
	boolean verify(Object data);
	
	String getErroMessage();
	
}
