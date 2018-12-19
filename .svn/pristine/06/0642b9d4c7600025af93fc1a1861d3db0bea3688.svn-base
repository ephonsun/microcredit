package banger.importThred;

/**
 * @Author: yangdw
 * @Description:潜在客户导入线程类
 * @Date: Created in 13:51 2017/9/27.
 */

import banger.dao.intf.IPotentialCustomersDao;
import banger.domain.customer.CustPotentialCustomers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: yangdw
 * @param
 * @Description:开启多线程，插入数据库
 * @Date: 16:17 2017/9/26
 */
public class ImportPotentailThread implements Runnable{

	private IPotentialCustomersDao potentialCustomersDao;
	private List<CustPotentialCustomers> list;
	private CountDownLatch begin;
	private CountDownLatch end;

	//创建个构造函数初始化 list,和其他用到的参数
	public ImportPotentailThread(List<CustPotentialCustomers> list,CountDownLatch begin,CountDownLatch end,IPotentialCustomersDao potentialCustomersDao){
		this.list = list;
		this.begin = begin;
		this.end = end;
		this.potentialCustomersDao = potentialCustomersDao;
	}

	@Override
	public void run() {
		try {
			//当处理的数据存在等待超时和出错会使线程一直处于等待状态
			potentialCustomersDao.batchInsertPotential(list);
//			for (int i = 0; i < list.size(); i++) {
//				potentialCustomersDao.insertPotentialCustomers(list.get(i));
////				System.out.println(Calendar.getInstance().getTime()+"------------1111111111111111111");
//			}
			//....
			//执行完让线程直接进入等待
			begin.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			//这里要主要了，当一个线程执行完计数要减一,不然这个线程会被一直挂起
			//调用end.countDown()，这个方法就是直接把计数器减一的
			end.countDown();
//			System.out.println(Calendar.getInstance().getTime()+"---------------222222222222222");
		}
	}
}
