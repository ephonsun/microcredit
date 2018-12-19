package banger.util;

import com.sun.jndi.toolkit.url.UrlUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HttpUtils { // 创建HttpClient对象

    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://180.201.156.166:8080/trial/";

    /**
     * * @param url 发送请求的URL * @return 服务器响应字符串 * @throws Exception
     */
    public static String getRequest(final String url) throws Exception {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 创建HttpGet对象。
                HttpGet get = new HttpGet(url);
                // 发送GET请求
                HttpResponse httpResponse = httpClient.execute(get); // 如果服务器成功地返回响应
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    // 获取服务器响应字符串
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    return result;
                }
                return null;
            }
        });
        new Thread(task).start();
        return task.get();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        try {

            JSONObject jo = new JSONObject();

//            jo.put("operType", UrlUtil.encode(operType,"UTF-8"));
//            jo.put("operName", UrlUtil.encode(operName,"UTF-8"));
//            jo.put("operRole", UrlUtil.encode(operRole,"UTF-8"));
//            jo.put("amount", UrlUtil.encode(amount,"UTF-8"));
//            jo.put("customerName", UrlUtil.encode(customerName,"UTF-8"));
//            jo.put("customerMobile", UrlUtil.encode(customerMobile,"UTF-8"));
//            jo.put("superName", UrlUtil.encode(superName,"UTF-8"));
//            jo.put("superRole", UrlUtil.encode(superRole,"UTF-8"));
            jo.put("operType", "Allot");
            jo.put("operName", "陈志斌");
            jo.put("operRole", "客户经理");
            jo.put("amount", "1000");
            jo.put("customerName", "小燕子");
            jo.put("customerMobile", "18088888888");
            jo.put("superName", "陈诚");
            jo.put("superRole", "团队主管");
            jo.put("time", LoanMsgUtil.getTime());

            map.put("msg", UrlUtil.encode(jo.toString(),"UTF-8"));
            String  b = null;
            b = postRequest("http://localhost:80/api/v1/getLoanMsg.html", map);
            System.out.println("-------------------"+new String(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // /** * @param url 发送请求的URL * @param params 请求参数 * @return 服务器响应字符串 * @throws Exception */
    public static String postRequest(final String url, final Map<String, String> rawParams) throws Exception {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 创建HttpPost对象。
                HttpPost post = new HttpPost(url);
                // 如果传递参数个数比较多的话可以对传递的参数进行封装
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : rawParams.keySet()) {
                    // 封装请求参数
                    params.add(new BasicNameValuePair(key, rawParams.get(key)));
                }
                // 设置请求参数
                post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                // 发送POST请求
                HttpResponse httpResponse = httpClient.execute(post);
                // 如果服务器成功地返回响应
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    // 获取服务器响应字符串
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    return result;
                }
                return null;
            }
        });
        new Thread(task).start();
        return task.get();
    }
}
