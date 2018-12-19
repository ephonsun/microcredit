package banger.framework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {
	
	private static String ip = "127.0.0.1";

	public static String getIp() {
		if ("127.0.0.1".equals(ip)) {
			ip = getLocalIP();
		}
		return ip;
	}

	/**
	 * 获得主机IP
	 * 
	 * @return String
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();

					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();

						//if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
						if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1 && ni.getName().indexOf("eth0")>-1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	
	public static String getMacAddress() {
		StringBuffer sb = new StringBuffer();
		try {
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia)
					.getHardwareAddress();

			// 下面代码是把mac地址拼装成String
			for (int i = 0; i < mac.length; i++) {
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}
	
	/**
	 * 判断Ip在范围内
	 * @param ip
	 * @param beginIp
	 * @param endIp
	 * @return
	 */
	public static boolean inIpRange(String ip,String beginIp,String endIp){
		Long p1 = getIpLongValue(ip);
		Long p2 = getIpLongValue(beginIp);
		Long p3 = getIpLongValue(endIp);
		if(p1>0 && p2>0 && p3>0 && ((p1>p2 && p1<p3) || (p1>p3 && p1<p2))){
			return true;
		}
		return false;
	}
	
	/**
	 * 得到Ip数字
	 * @param ip
	 * @return
	 */
	public static Long getIpLongValue(String ip){
		Long intVal = 0L;
		if(!StringUtil.isNullOrEmpty(ip)&&isIpv4(ip)){
			String part[] = ip.split("\\.");
			if(part.length==4){
				intVal+=Long.parseLong(part[0])*256*256*256;
				intVal+=Long.parseLong(part[1])*256*256;
				intVal+=Long.parseLong(part[2])*256;
				intVal+=Long.parseLong(part[3]);
			}
			if(intVal > Integer.MAX_VALUE){
				return intVal % Integer.MAX_VALUE;
			}
		}else if(!StringUtil.isNullOrEmpty(ip)&&!isIpv4(ip)){//查询时，如果IP地址不合法的时候，查询结果为空
			intVal=1L;
		}
		return intVal;
	}

	/**
	 * 验证IP地址是否合法
	 * @param ipAddress
	 * @return
	 */
	public static boolean isIpv4(String ipAddress) {
		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();

	}
}
