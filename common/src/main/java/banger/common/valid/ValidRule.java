package banger.common.valid;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import banger.common.BaseController;
import banger.framework.reflector.PropertyInfo;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;

public class ValidRule {
	private String targetName;					//校验实现方法的对像
	private String methodName;						//校验实现方法名称
	private String[] paramNames;					//校验数据参数名
	private String expression;							//校验规则表达式

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParamNames() {
		return paramNames;
	}

	public void setParamNames(String[] paramNames) {
		this.paramNames = paramNames;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public ValidRule(){
		this.paramNames=new String[0];
	}

	/**
	 * 数据校验
	 * @param action
	 * @return
	 */
	public ValidResultItem valid(Object action){
		ValidResultItem item = null;
		try{
			if(StringUtil.isNullOrEmpty(this.methodName))
				throw new ValidException(StringUtil.format("执行数据校验表达式格式不正确 表达式:{0} 函数:{1}",expression,methodName));
			Object target = this.getMethodTarget(action);
			Method method = this.getMethodByName(target,methodName,this.paramNames.length);
			Object[] args = this.getMethodParameters(action, paramNames);
			Object[] ps = getChangeTypeParameter(args,method.getParameterTypes());
			Object result = method.invoke(target,ps);
			if(result!=null){
				item = new ValidResultItem();
	            item.setResult(result);
	            item.setType(StringUtil.isNullOrEmpty(this.targetName)?this.methodName:this.targetName+"."+this.methodName);
			}
			
		}
		catch(ValidException e){
			throw e;
		}
		catch(Exception e){
			throw new ValidException(StringUtil.format("执行数据校验时出错 表达式:{0} 函数:{1}",expression,methodName),e);
		}
		return item;
	}
	
	/**
	 * 得到校验函数的实现对像
	 * @param action
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private Object getMethodTarget(Object action) throws IllegalArgumentException, IllegalAccessException{
		if(!StringUtil.isNullOrEmpty(this.targetName)){
			PropertyInfo pi = ReflectorUtil.getProperty(action.getClass(), this.targetName);
			if(pi.getField()!=null)return pi.getField().get(action);
			return ReflectorUtil.getPropertyValue(action,this.targetName);
		}
		return action;
	}
	
	/**
	 * 对值类型参数进行参数转换
	 * @param args
	 * @param clazzs
	 * @return
	 */
	private Object[] getChangeTypeParameter(Object[] args,Class<?>[] clazzes){
		Object[] newArgs = args;
		for(int i=0;i<args.length;i++){
			Object arg = args[i];
			Class<?> clazz = clazzes[i];
			if(arg!=null && TypeUtil.isValueType(arg.getClass())){
				if(!arg.getClass().equals(clazz)){
					newArgs[i] = TypeUtil.changeType(arg,clazz);
				}
			}
		}
		return newArgs;
	}
	
	/**
	 * 通过名称
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	private Method getMethodByName(Object target,String methodName,int paramSize){
		Class<?> clazz = target.getClass();
		Method[] methods = clazz.getMethods();
		Method current = null;
		for(Method method : methods){
			if(method.getName().equalsIgnoreCase(methodName)){
				if(method.getParameterTypes().length!=paramSize)
					throw new ValidException(StringUtil.format("执行数据校验出错,函数参数和传入参数不一致 表达式:{0} 函数:{1}",expression,methodName));
				current = method;
			}
		}
		if(current==null)
			throw new ValidException(StringUtil.format("执行数据校验出错,找不到指定函数 表达式:{0} 函数:{1}",expression,methodName));
		return current;
	}
	
	/**
	 * 得到参数的值的数组
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	private Object[] getMethodParameters(Object action,String[] paramNames){
		Object[] args = new Object[paramNames.length];
		for(int i=0;i<paramNames.length;i++){
			args[i] = getMethodParameter(action,paramNames[i]);
		}
		return args;
	}
	
	/**
	 * 得到参数的值
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	private Object getMethodParameter(Object action,String paramName){
		String[] parts = paramName.trim().split("\\.");
		Object value = action;
		for(int i=0;i<parts.length;i++){
			String part = parts[i];
			value = this.getPropertyValue(value,part);
		}
		return value;
	}
	
	/**
	 * 得到参数的值
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	private Object getPropertyValue(Object obj,String propertyName){
		if(obj!=null){
			Object propertyValue = null;
			if(obj instanceof BaseController){
				BaseController action = (BaseController)obj;
				propertyValue = action.getRequest().getParameter(propertyName);
				if(propertyValue==null)propertyValue = ReflectorUtil.getPropertyValue(obj,propertyName);
			}else if(obj instanceof Map){
				if(((Map<?,?>) obj).containsKey(propertyName)){
					propertyValue = ((Map<?,?>) obj).get(propertyName);
				}
			}
			else if(obj instanceof List){
				
			}
			else if(TypeUtil.isValueType(obj.getClass())){
				
			}else{
				propertyValue = ReflectorUtil.getPropertyValue(obj,propertyName);
			}
			return propertyValue;
		}
		else throw new ValidException(StringUtil.format("获取参数出错,指定参数为空值 表达式:{0} 函数:{1}",expression,methodName));
	}
}
