/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :数据服务端校验
 * Author     :zhushengwei
 * Create Date:2013-3-28
 */
package banger.common.valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banger.framework.util.StringUtil;

public class ValidEngine {
	private static Map<String,List<ValidRule>> validRulesCache;			//校验解析集合
	
	static{
		validRulesCache = new ConcurrentHashMap<String,List<ValidRule>>();
	}
	
	/**
	 * 校验数据
	 * @param expression 校验调用表达式
	 * @param action Action对像
	 * @return
	 */
	public static ValidResult valids(String expression,Object action){
		List<ValidRule> rules = getRules(expression);
		ValidResult result = new ValidResult();
		if(rules!=null){
			for(ValidRule rule : rules){
				ValidResultItem item = rule.valid(action);
				if(item!=null){
					result.addItem(item);
				}
			}
		}
		return result;
	}
	
	/**
	 * 得到校验规则集合
	 * @param expression
	 * @return
	 */
	private static List<ValidRule> getRules(String expression){
		if(!validRulesCache.containsKey(expression)){
			List<ValidRule> rules = parserExpression(expression);
			validRulesCache.put(expression, rules);
		}
		return validRulesCache.get(expression);
	}
	
	/**
	 * 解析校验规则表达式
	 * @param expression
	 * @return
	 */
	private static List<ValidRule> parserExpression(String expression){
		try{
			String[] parts = expression.split("\\;");
			List<ValidRule> rules = new ArrayList<ValidRule>();
			for(String part : parts){
				if(!StringUtil.isNullOrEmpty(part)){
					ValidRule rule = new ValidRule();
					rule.setExpression(part);
					String str = part.trim();
					int n = str.indexOf("(");
					int m = str.indexOf(")");
					if(n<0 || m<0)throw new ValidException(StringUtil.format("解析校验规则表达式出错 {0}",expression));
					String prePart = str.substring(0,n);
					int l = prePart.indexOf(".");
					if(l>0){
						rule.setTargetName(prePart.substring(0,l).trim());
						rule.setMethodName(prePart.substring(l+1).trim());
					}else{
						rule.setMethodName(prePart.trim());
					}
					String paramPart = str.substring(n+1,m);
					String[] paramNames = paramPart.split("\\,");
					rule.setParamNames(paramNames);
					rules.add(rule);
				}
			}
			return rules;
		}
		catch(Exception e){
			throw new ValidException(StringUtil.format("解析校验规则表达式出错 {0}",expression),e);
		}
	}
}
