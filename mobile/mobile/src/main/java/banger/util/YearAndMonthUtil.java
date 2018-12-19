package banger.util;

import banger.framework.util.DateUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: yangdw
 * @Description:
 * @Date: Created in 17:31 2017/8/25.
 */
public class YearAndMonthUtil {

	@Test
	public void test(){
		String str = "2016-06-2018-01";
		this.yearAndMonth(str);
	}
	public void yearAndMonth(String str){
		String[] split = str.split("-");

		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, 1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt(split[02]), Integer.parseInt(split[3]) - 1, 1);
		int result = 0;
		int i = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		//月份个数
		result = Math.abs(month + i);
		for (int j = 0; j < result; j++) {
			if (j == 0) {
				c1.add(Calendar.MONTH, 0);
				String s = DateUtil.format(c1.getTime(), "yyyy") + "年" + DateUtil.format(c1.getTime(), "MM") + "月";
				System.out.println(s);
			} else {
				c1.add(Calendar.MONTH, 1);
				String s = DateUtil.format(c1.getTime(), "yyyy") + "年" + DateUtil.format(c1.getTime(), "MM") + "月";
				System.out.println(s);
			}
		}
	}
	private static String format(Date date, String format){
		if (date == null)
			return "";
		return new SimpleDateFormat(format).format(date);
	}
}
