package banger.response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AppJsonResponse {
	
	public static String  success(){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_100.getCode());
		response.put("msg", CodeEnum.CODE_100.getMsg());
		return response.toString();
	}
	
	public static String success(String data){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_100.getCode());
		response.put("msg", CodeEnum.CODE_100.getMsg());
		response.put("data", data);
		return response.toString();
	}
	
	public static String success(JSONObject json){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_100.getCode());
		response.put("msg", CodeEnum.CODE_100.getMsg());
		response.put("data", json);
		String jsonString = response.toString();
		return jsonString;
	}

	public static String  success(JSONArray jsonArray){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_100.getCode());
		response.put("msg", CodeEnum.CODE_100.getMsg());
		response.put("data", jsonArray);
		return response.toString();
	}

	public static String  success(JSONArray jsonArray,Integer count){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_100.getCode());
		response.put("msg", CodeEnum.CODE_100.getMsg());
		response.put("data", jsonArray);
		response.put("count", count);
		return response.toString();
	}
	
	public static String fail(){
		JSONObject response=new JSONObject();
		response.put("code", CodeEnum.CODE_101.getCode());
		response.put("msg", CodeEnum.CODE_101.getMsg());
		return response.toString();
	}
	
	public static String fail(CodeEnum codeEnum){
		JSONObject response=new JSONObject();
		response.put("code", codeEnum.getCode());
		response.put("msg", codeEnum.getMsg());
		return response.toString();
	}
	
	
	public static String fail(CodeEnum codeEnum,JSONObject json){
		JSONObject response=new JSONObject();
		response.put("code", codeEnum.getCode());
		response.put("msg", codeEnum.getMsg());
		response.put("data", json);
		return response.toString();
	}
	
	public static String fail(CodeEnum codeEnum,String msg){
		JSONObject response=new JSONObject();
		response.put("code", codeEnum.getCode());
		response.put("msg", msg);
		return response.toString();
	}
	
	
}
