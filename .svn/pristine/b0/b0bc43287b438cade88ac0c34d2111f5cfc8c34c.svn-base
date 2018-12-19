import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

/**
 * 这是连公网的例子，必须能上外网
 */
public class FaceTest {
	
	/**
	 * 
	 * @param args
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void main(String[] args)  throws HttpException, IOException {
		String httpUrl = "http://baihang.iplink.com.cn:5000";
		String feature1 = getFeature(httpUrl,"D:/代码/face++/pic/1.png");
		System.out.println(feature1);
		String feature2 = getFeature(httpUrl,"D:/代码/face++/pic/2.png");
		System.out.println(feature2);
		String result = compareFeature(httpUrl,feature1,feature2);
		System.out.println(result);
	}
	
	/**
	 * 抽feature
	 * @throws HttpException
	 * @throws IOException
	 */
	private static String getFeature(String httpUrl,String filename) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod method=new PostMethod(httpUrl+"/extract?mode=slow&watermark=0");
		FilePart p1 = new FilePart("img", new File(filename));
		Part[] parts = {p1};  
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		client.executeMethod(method);
		String result = method.getResponseBodyAsString();
		if(result!=null && result.length()>0){
			int n = result.indexOf("\"feature\":");
			int m = result.indexOf("\"rect\":");
			if(n>0 && m>0){
				String part = result.substring(n+10, m);
				int l = part.indexOf("\"");
				int k = part.lastIndexOf("\"");
				String feature = part.substring(l+1,k);
				return feature;
			}
		}
		return result;
	}
	
	/**
	 * 比较两张图片
	 * @throws HttpException
	 * @throws IOException
	 */
	private static String compareFeature(String httpUrl,String feature1,String feature2) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod method=new PostMethod(httpUrl+"/compare");
		StringPart p1 = new StringPart("feature1",feature1);
		StringPart p2 = new StringPart("feature2",feature2);
		Part[] parts = {p1,p2};
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		client.executeMethod(method);
		String result = method.getResponseBodyAsString();
		return result;
	}
	
}
