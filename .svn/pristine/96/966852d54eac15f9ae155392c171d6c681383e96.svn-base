package banger.framework.web.dojo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonTools {
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		ArrayList list = new ArrayList();
		Iterator it = jsonArr.iterator();

		while (it.hasNext()) {
			JSONObject json2 = (JSONObject) it.next();
			list.add(parseJSON2Map(json2.toString()));
		}

		return list;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		HashMap map = new HashMap();
		JSONObject json = JSONObject.fromObject(jsonStr);
		Iterator arg3 = json.keySet().iterator();

		while (true) {
			while (arg3.hasNext()) {
				Object k = arg3.next();
				Object v = json.get(k);
				if (v instanceof JSONArray) {
					ArrayList list = new ArrayList();
					Iterator it = ((JSONArray) v).iterator();

					while (it.hasNext()) {
						JSONObject json2 = (JSONObject) it.next();
						list.add(parseJSON2Map(json2.toString()));
					}

					map.put(k.toString(), list);
				} else {
					if(v instanceof String){
						String str = (String)v;
						map.put(k.toString(), cleanXSS(str));
					}else{
						map.put(k.toString(), v);
					}
				}
			}

			return map;
		}
	}
	
	private static String cleanXSS(String value) {
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
////        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
////        value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
//        value = value.replaceAll("'", "&#39;");
////		if(!isValidDate(value)){
////			value = value.replaceAll("-", "&macr;");
////		}
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");
        return value;
    }

	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			InputStream e = (new URL(url)).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(e));
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return parseJSON2List(sb.toString());
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	public static Map<String, Object> getMapByUrl(String url) {
		try {
			InputStream e = (new URL(url)).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(e));
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return parseJSON2Map(sb.toString());
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	private static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

}