package banger.service.impl;

import banger.dao.intf.IPotentialCustomersDao;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.permission.SysTeamMember;
import banger.domain.product.ProdProduct;
import banger.domain.system.SysBasicConfig;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.component.dataimport.AbstractImportHandler;
import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.IRecordReader;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.dataimport.context.DefaultImportContext;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.IdCardUtil;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.importThred.ImportPotentailThread;
import banger.moduleIntf.IBasicConfigProvider;
import banger.moduleIntf.IPotentialCustomersProvider;
import banger.moduleIntf.IProductModule;
import banger.service.intf.IPotentialCustomersService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 潜在客户表业务访问类
 */
@Repository
public class PotentialCustomersService implements IPotentialCustomersService,IPotentialCustomersProvider {

	private static final Logger logger = LoggerFactory.getLogger(PotentialCustomersService.class);

//	private int batchNumber = 2000;
	private int batchNumber = 100;

	private Map<String, AbstractImportHandler> handlerMap;

	public PotentialCustomersService() {
		this.handlerMap = new HashMap<String, AbstractImportHandler>();
	}

	@Autowired
	private IPotentialCustomersDao potentialCustomersDao;

	@Autowired
	private IBasicConfigProvider basicConfigProvider;

	@Resource
	private IProgressBarManager progressBarManager;

	@Autowired
	private IProductModule productModule;

	public IProgressBarManager getProgressBarManager() {
		return progressBarManager;
	}

	public void setProgressBarManager(IProgressBarManager progressBarManager) {
		this.progressBarManager = progressBarManager;
	}

	/**
	 * 新增潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertPotentialCustomers(CustPotentialCustomers potentialCustomers,Integer loginUserId){
		potentialCustomers.setCreateUser(loginUserId);
		potentialCustomers.setCreateDate(DateUtil.getCurrentDate());
		potentialCustomers.setUpdateUser(loginUserId);
		potentialCustomers.setUpdateDate(DateUtil.getCurrentDate());
		potentialCustomers.setCardNumber(IdCardUtil.toUpperCase(potentialCustomers.getCardNumber()));
		this.potentialCustomersDao.insertPotentialCustomers(potentialCustomers);
	}

	/**
	 *修改潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updatePotentialCustomers(CustPotentialCustomers potentialCustomers,Integer loginUserId){
		potentialCustomers.setUpdateUser(loginUserId);
		potentialCustomers.setUpdateDate(DateUtil.getCurrentDate());
		if (potentialCustomers.getCardNumber() != null) {
			if(!potentialCustomers.getCardNumber().equals("")) {
				potentialCustomers.setCardNumber(IdCardUtil.toUpperCase(potentialCustomers.getCardNumber()));
			} else {
				potentialCustomers.setCardNumber(null);
			}
		}
		this.potentialCustomersDao.updatePotentialCustomers(potentialCustomers);
	}
	/**
	 *修改潜在客户表 意向时间可为null
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updatePotentialCustomersByDate(CustPotentialCustomers potentialCustomers,Integer loginUserId){
		potentialCustomers.setUpdateUser(loginUserId);
		potentialCustomers.setUpdateDate(DateUtil.getCurrentDate());
		potentialCustomers.setCardNumber(IdCardUtil.toUpperCase(potentialCustomers.getCardNumber()));
		this.potentialCustomersDao.updatePotentialCustomersByDate(potentialCustomers);
	}

	/**
	 * 通过主键删除潜在客户表
	 * @param id 主键Id
	 */
	public boolean deletePotentialCustomersById(Integer id){
		Integer rows = this.potentialCustomersDao.deletePotentialCustomersById(id);
		boolean delete = rows.intValue() == 0 ? false : true;
		return delete;
	}

	/**
	 * 通过主键得到潜在客户表
	 * @param id 主键Id
	 */
	public CustPotentialCustomers getPotentialCustomersById(Integer id){
		return this.potentialCustomersDao.getPotentialCustomersById(id);
	}

	/**
	 * 查询潜在客户表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustPotentialCustomers> queryPotentialCustomersList(Map<String,Object> condition){
		return this.potentialCustomersDao.queryPotentialCustomersList(condition);
	}

	/**
	 * 分页查询潜在客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustPotentialCustomerQuery> queryPotentialCustomersList(Map<String,Object> condition, IPageSize page){
		return this.potentialCustomersDao.queryPotentialCustomersList(condition,page);
	}

	/**
	 * @Author: yangdw
	 * @params: userId        用户Id
	 * @params: excelFilename 导入文件
	 * @params: columns       导入列和字段的对应关系
	 * @Description:开始导入潜在客户数据
	 * @Date: 15:38 2017/9/7
	 */

	public ProgressBar importExcel(String userId, String excelFilename, List<ColumnInfo> columns, Map<String, String> potentialMap) {
		Map<String, Object> cardTpyeMap = new HashedMap();
		AutoBaseField autoBaseField = new AutoFieldWrapper(0, "loanIdentifyType", "LOAN_IDENTIFY_TYPE", "证件类型", EnumFiledType.SELECT.fieldType, "", true, getDictOptions("CD_IDENTIFY_TYPE"), true, false);
		List<AutoBaseOption> options = autoBaseField.getOptions();
		for (AutoBaseOption option : options) {
			cardTpyeMap.put(option.getName(), option.getValue());
		}
		//意向产品map
		Map<String, Object> productMap = new HashedMap();
		//传递空map,方便获取所有的产品
		List<ProdProduct> prodProducts = productModule.queryProductList(productMap);
		for (ProdProduct prodProduct : prodProducts) {
			productMap.put(prodProduct.getProductCode(), prodProduct.getProductId());
		}

		IImportContext context = new DefaultImportContext(excelFilename);
		context.setColumns(columns);
		//2000条批量提交一次,利用开启线程插入数据库,需要校验电话号码和姓名,改为100提交,防止sql长度超过4000
		context.setBatch(batchNumber);

		PotentialImportHandler handler = new PotentialImportHandler(this, Integer.parseInt(userId),true,cardTpyeMap,potentialMap,productMap);
		handler.setContext(context);
		this.handlerMap.put(userId, handler);

		ProgressBar bar = progressBarManager.add("potentialCustImport_" + userId, userId);
		handler.setProgressBar(bar);
		handler.start();

		return bar;
	}

	/**
	 * 通过导入的Excel文件得到表头
	 *
	 * @param excelFilename
	 * @return
	 */
	public Map<String, Object> getImportExcelHead(String excelFilename) {
		Map<String, Object> map = new HashedMap();
		Map<String, String> potentialMap = new HashedMap();

		IImportContext context = new DefaultImportContext(excelFilename);
//		context.setMaxRow(1);
		PotentialImportHandler handler = new PotentialImportHandler(this, null,false, map, potentialMap, null);
		handler.setContext(context);
		handler.start();
		List<String> columns = handler.getHead();
		int total = handler.getTotal();
//		Map<String,Object> map = new HashedMap();
		map.put("columns", columns);
		map.put("total", total);
		return map;
	}

	@Override
	public void deletePotentialCustomersAll(Integer userId) {
		this.potentialCustomersDao.deletePotentialCustomersAll(userId);
	}
	/**
	 * 团队主管删除全部
	 * @param **/
	@Override
	public void deletePotentialCustomersAllByGroupId(Integer groupId) {
		this.potentialCustomersDao.deletePotentialCustomersAllByGroupId(groupId);
	}

	public IImportResult getImportResultByUserId(String userId) {
		if (this.handlerMap.containsKey(userId)) {
			return this.handlerMap.get(userId);
		}
		return null;
	}

	/**
	 * 推得到贷款业务类型
	 * @return
	 */
	private List<AutoBaseOption> getDictOptions(String dictName){
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", dictName);
		for(ICodeTable.IItem item : items){
			list.add(new AutoBaseOption(item.getValue(),item.getName()));
		}
		return list;
	}

	private void exec(List<CustPotentialCustomers> list) throws InterruptedException{
		int count = 400;//一个线程处理400条数据
		int listSize = list.size();//数据集合大小
		int runSize = (listSize/count)+1; //开启的线程数,因为是2000条提交一次,最多创建5个线程
		List<CustPotentialCustomers> newlist = null;//存放每个线程的执行数据
		ExecutorService executor = Executors.newFixedThreadPool(runSize);//创建一个线程池，数量和开启线程的数量一样
		//创建两个个计数器
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(runSize);
		//循环创建线程
		for (int i = 0; i < runSize ; i++) {
			//计算每个线程执行的数据
			if((i+1)==runSize){
				int startIndex = (i*count);
				int endIndex = list.size();
				newlist= list.subList(startIndex, endIndex);
			}else{
				int startIndex = (i*count);
				int endIndex = (i+1)*count;
				newlist= list.subList(startIndex, endIndex);
			}
			//线程类
			ImportPotentailThread importThead = new ImportPotentailThread(newlist,begin,end,potentialCustomersDao);
			//这里执行线程的方式是调用线程池里的executor.execute(importThead)方法。
			executor.execute(importThead);
		}
		begin.countDown();
		end.await();

		//执行完关闭线程池
		executor.shutdown();
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:潜在客户导入处理类
	 * @Date: 13:54 2017/9/8
	 */
	class PotentialImportHandler extends AbstractImportHandler {
		private PotentialCustomersService potentialCustomersService;
		private List<CustPotentialCustomers> list;
		private List<String> contentStr;//去掉重复内容记录
		private boolean isInsert; //false为计算total
		private Integer loginUserId;
		private long optTime;
		private int total;
		private Map<String, Object> cardTypeMap; //存放证件类型的code和msg
		private Map<String, Object> productMap; //存放产品的code和id
		private Map<String, String> potentialMap; //存放归属类型的code和msg

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public PotentialImportHandler(PotentialCustomersService potentialCustomersService, Integer userId, boolean isInsert, Map<String, Object> cardTypeMap, Map<String, String> potentialMap, Map<String, Object> productMap) {
			this.potentialCustomersService = potentialCustomersService;
			this.list = new ArrayList<CustPotentialCustomers>();
			this.loginUserId = userId;
			this.isInsert = isInsert;
			if (isInsert == true) {
				this.cardTypeMap = cardTypeMap;
				this.potentialMap = potentialMap;
				this.productMap = productMap;
			}
			this.contentStr = new ArrayList<String>();
		}

		/**
		 * 逐行读取Excel数据
		 */
		@Override
		public void read(IRecordReader reader) {
			if (reader.rownum() > 0) {
				if(isInsert == true){
					List<ColumnInfo> columns = this.getContext().getColumns();
					CustPotentialCustomers potential = new CustPotentialCustomers();
					for (ColumnInfo column : columns) {
						//处理integer的字段类型,导入填写字段是汉字问题
						if (!StringUtil.isNullOrEmpty(column.getFieldName())) {
							String val = (String) reader.getValue(column.getIndex());
							if (val != null)
								//val = val.trim().length() > 30 ? val.trim().substring(0, 30) : val.trim();
								val = val.trim();
							//性别处理
							if("customerSex".equals(column.getFieldName()) && val!=null){
								if("男".equals(val)){
									potential.setCustomerSex("1");
								}else if("女".equals(val)){
									potential.setCustomerSex("0");
								}else if("".equals(val)){
									potential.setCustomerSex("");
								}else{
									//不允许通过字段信息
									potential.setCustomerSex("3");
								}
							}else if("age".equals(column.getFieldName()) && val!=null){
								//年龄处理
								if(val != null && !"".equals(val) && StringUtil.isNumber(val)){
									potential.setAge(Integer.valueOf(val));
								}else if(val != null && !"".equals(val) && !StringUtil.isNumber(val)){
									potential.setAge(-1);
								}else{
									potential.setAge(0);
								}
//							}else if("loanIntention".equals(column.getFieldName()) && val!=null){
//								//贷款意向处理
//								if("有".equals(val)){
//									potential.setLoanIntention(1);
//								}else if("无".equals(val) || "".equals(val)){
//									potential.setLoanIntention(0);
//								}else{
//									//不允许通过字段信息
//									potential.setLoanIntention(2);
//								}
							}else{
								ReflectorUtil.setPropertyValue(potential, column.getFieldName(), val);
							}
						}
					}
					if (this.validImportPotentialData(potential, reader) ) {
						list.add(potential);
						String content = "\r\n" + "客户姓名和联系方式已经存在";
						for(int i = 0; i < this.head.size(); i++){
							content = content + "," + (reader.getValue(i) == null ? "" : reader.getValue(i));
						}
						contentStr.add(content);
					}
					this.rowCount++;
				}else{
					this.total++;
				}
			}
			super.read(reader);                //读进度条,写入Excel头信息
		}

		/**
		 * 批量提交
		 */
		public void batchCommit(){
			try {

				if (this.list.size() > 0 && insertCount < list.size()) {
					List<CustPotentialCustomers> insertListBefore = new ArrayList<CustPotentialCustomers>();
					List<CustPotentialCustomers> insertList = new ArrayList<CustPotentialCustomers>();
					List<CustPotentialCustomers> repeatList = new ArrayList<CustPotentialCustomers>();
//					insertList.subList(insertCount, this.list.size());
					ArrayList<String> phoneArray = new ArrayList<String>();
					for (int i = insertCount; i < this.list.size(); i++) {
						CustPotentialCustomers potential = this.list.get(i);
						if (potential.getCardType() != null) {
							potential.setCardType((String) cardTypeMap.get(potential.getCardType()));
						}
						if (potential.getProductCode() != null) {
							potential.setLoanIntention(1);
						}else{
							potential.setLoanIntention(0);
							potential.setProductCode("");
						}
						if(!isNullOrEmpty(potential.getCardNumber())){
							potential.setCardNumber(potential.getCardNumber().toUpperCase());
						}
						if ("1".equals(potentialMap.get("type"))) {
							potential.setAttributionManager(Integer.valueOf(potentialMap.get("value")));
						}else if("2".equals(potentialMap.get("type"))){
							potential.setAttributionTeam(Integer.valueOf(potentialMap.get("value")));
						}
						//为了批量插入需要把null的属性赋值为""或者0
						isNullToEmpty(potential);
						potential.setCreateUser(loginUserId);
						potential.setUpdateUser(loginUserId);
						potential.setCreateDate(Calendar.getInstance().getTime());
						potential.setUpdateDate(Calendar.getInstance().getTime());
						//记录类型,0 导入记录,1 新建记录
						potential.setRecordType(0);
						potential.setCreateUserTeam(0);
						insertListBefore.add(potential);
						phoneArray.add(potential.getTelephoneNumber());
					}
					//配置平台对潜在客户导入的方式控制
					SysBasicConfig qzkhdr = basicConfigProvider.getSysBasicConfigByKey("qzkhdr");
					//1:允许重复导入,2不允许重复导入
					if(qzkhdr != null && qzkhdr.getConfigStatus() != null &&
							qzkhdr.getConfigStatus().equals("1")){
						phoneArray.clear();
					}
					//需要对导入数据去重复
					if (CollectionUtils.isNotEmpty(phoneArray)) {
						StringBuilder phoneSb = new StringBuilder();
						for (String str : phoneArray) {
							phoneSb.append("'"+str+"'" + ",");
						}
						String phoneStr = phoneSb.substring(0, phoneSb.length() - 1);
						List<CustPotentialCustomers> volidateList = potentialCustomersDao.getPotentialCustomersByPhones(phoneStr);
						//数据库校验去重复
						Set<String> validateSet = new HashSet<String>();
						//批量提交本身校验去重复
						Map<String, Integer> validateMap = new HashMap<String, Integer>();

						for (CustPotentialCustomers volidateCustomer : volidateList) {
							validateSet.add(volidateCustomer.getCustomerName() + "_" + volidateCustomer.getTelephoneNumber());
						}
						for (CustPotentialCustomers validateCustomer : insertListBefore) {
							String strKey = validateCustomer.getCustomerName() + "_" + validateCustomer.getTelephoneNumber();
							if (validateMap.get(strKey) != null) {
								validateMap.put(strKey, validateMap.get(strKey) + 1);
							}else{
								validateMap.put(strKey, 1);
							}
						}

						for (int i = 0; i < insertListBefore.size(); i++) {
							//过滤数据库重复记录
							boolean validateTp = validateSet.contains(insertListBefore.get(i).getCustomerName()+"_"+insertListBefore.get(i).getTelephoneNumber());
							if (validateTp){
								this.writeErrorFileByLine(contentStr.get(i), "", "客户姓名和联系方式已经存在");
								repeatList.add(insertListBefore.get(i));
							}else{
								//过滤自身重复
								String validateKey = insertListBefore.get(i).getCustomerName() + "_" + insertListBefore.get(i).getTelephoneNumber();
								Integer validateValue = validateMap.get(validateKey);
								if (validateValue != null && validateValue.intValue() > 1) {
									String str = contentStr.get(i).replace("客户姓名和联系方式已经存在", "Excel存在当前相同记录");
									this.writeErrorFileByLine(str, "", "Excel存在当前相同记录");
									repeatList.add(insertListBefore.get(i));
								}
							}
						}

						this.insertCount += insertListBefore.size();
						insertListBefore.removeAll(repeatList);
						contentStr.clear();

					}else{
						this.insertCount += insertListBefore.size();
					}
					insertList.addAll(insertListBefore);

//					long start = System.currentTimeMillis();
					if (insertList.size() > 0) {
						//开启多线程执行
//						exec(insertList);
						//单线程执行
						potentialCustomersDao.batchInsertPotential(insertList);

					}
//					long end = System.currentTimeMillis();
//					System.out.println("导入运行耗时间为：" + (end - start) + "-----------");
					/*this.insertCount += insertListBefore.size();*/
					this.successCount += insertList.size();
//					this.optTime += end - start;
//					System.out.println("单线程导入运行耗时间总计：" + optTime + "====================================");
				}
			} catch (Exception e) {
				e.printStackTrace();
				ProgressBar bar = progressBarManager.getProgressBarById("potentialCustImport_"+ loginUserId);
				if (bar != null) {
					int current = bar.getCurrent();
					logger.error("导入潜在客户Excel,第---" + current + "---数据报错");
				}
				logger.error("导入潜在客户执行插入报错 error:"+e.getMessage());
			}
		}

		/**
		 * @Author: yangdw
		 * @param potential
		 * @Description:
		 * @Date: 14:28 2017/9/27
		 */
		private void isNullToEmpty(CustPotentialCustomers potential) {
			potential.setLoanId(0);
			potential.setMarketingSuccess(0);
			if(StringUtil.isNullOrEmpty(potential.getCardType())) potential.setCardType("");
			if(StringUtil.isNullOrEmpty(potential.getCardNumber())) potential.setCardNumber("");
			if(StringUtil.isNullOrEmpty(potential.getCustomerSex())) potential.setCustomerSex("2");
			if(potential.getAge() == null) potential.setAge(0);
			if(StringUtil.isNullOrEmpty(potential.getAddress())) potential.setAddress("");
			if(potential.getLoanIntention() == null) potential.setLoanIntention(0);
			if(StringUtil.isNullOrEmpty(potential.getLoanUse())) potential.setLoanUse("");
			if(potential.getAttributionTeam() == null) potential.setAttributionTeam(0);
			if(potential.getAttributionManager() == null) potential.setAttributionManager(0);
			if(StringUtil.isNullOrEmpty(potential.getRemark())) potential.setRemark("");

		}

		/**
		 * @Author: yangdw
		 * @param potential
		 * @param reader
		 * @Description: 校验潜在客户信息是否合法
		 * @Date: 15:27 2017/9/26
		 */
		private boolean validImportPotentialData(CustPotentialCustomers potential, IRecordReader reader) {

			String error = "";
			boolean result = true;
			//String importResult = "成功";
			//客户名称（必填）	联系电话（必填）
			if (isNullOrEmpty(potential.getCustomerName())) {
				error = "客户名称不能为空";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(potential.getCustomerName().length() > 30){
				error = "客户名称不能超过30长度";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			/*}else if(isNullOrEmpty(potential.getTelephoneNumber()) ||
					!(isPhoneLegal(potential.getTelephoneNumber()) || isTelephone(potential.getTelephoneNumber()))){*/
			}else if(isNullOrEmpty(potential.getTelephoneNumber()) || !checkTelephone(potential.getTelephoneNumber())){
				error = "电话号码不符合规则";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getCardType()) && isNullOrEmpty((String) cardTypeMap.get(potential.getCardType()))){
				error = "证件类型不正确";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getCardNumber()) && !verForm(potential.getCardNumber())){
				error = "证件号码不正确";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getCustomerSex()) && "3".equals(potential.getCustomerSex())){
				error = "性别填写有误";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(potential.getAge() != null && potential.getAge() == -1){
				error = "年龄填写有误";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getAddress()) && potential.getAddress().length() > 100){
				error = "居住地址不能超过100个长度";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}/*else if(potential.getLoanIntention() != null && 2 == (potential.getLoanIntention())){
				error = "贷款意向请填写有无";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}*/
			else if(!isNullOrEmpty(potential.getProductCode()) && ((Integer) productMap.get(potential.getProductCode()) == null)){
				error = "产品编号不正确";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getLoanUse()) && potential.getLoanUse().length() > 50){
				error = "贷款用途不能超过50个长度";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}else if(!isNullOrEmpty(potential.getRemark()) && potential.getRemark().length() > 100){
				error = "备注不能超过100个长度";
				//importResult = "失败";
				this.writeErrorFileByLine(reader, "", error);
				result = false;
			}

			//this.writeLineReportFile(reader, importResult, "", error);
			return result;
		}

		//		<------------------身份证格式的正则校验----------------->
		private boolean verForm(String num) {
//			String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
			String reg = "^[0-9A-Za-z]{1,30}$";
			if (!num.matches(reg)) {
				return false;
			}
			return true;
		}
		private boolean isNullOrEmpty(String str) {
			if (str == null || str.equals("") || "null".equals(str)) {
				return true;
			} else {
				return str.trim().length() == 0;
			}
		}

		//校验手机号
		private boolean isPhoneLegal(String str) {
			String regExp = "^1[0-9]{10}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(str);
			return m.matches();
		}

		//校验电话号码
		private boolean isTelephone(String str) {
//			String regExp = "^([0-9]|[-])+$";
//			Pattern p = Pattern.compile(regExp);
//			Matcher m = p.matcher(str);
//			return m.matches();

			Pattern p1 = null, p2 = null;
			Matcher m = null;
			boolean b = false;
			p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
			p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
			if (str.length() > 9) {
				m = p1.matcher(str);
				b = m.matches();
			} else {
				m = p2.matcher(str);
				b = m.matches();
			}
			return b;
		}

		private boolean checkTelephone(String str) {
			if(str.length() > 20)
				return false;
//			String regExp = "^[\\-*#0-9\\s]$";
			Matcher matcher  = Pattern.compile("^([- *#0-9]+)$").matcher(str);
			return matcher.find();
		}

		private boolean isIncludeSpecialLetter(String str){
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.find();
		}
	}
	@Override
	public Integer getNewId() {
		return potentialCustomersDao.getNewId();
	}
	/**
	 * 检验客户唯一性
	 *
	 *
	 * @return
	 */
	@Override
	public boolean isUniqueCustomer(String s, String customerName, String cardType, String cardNumber) {
		return potentialCustomersDao.isUniquePointCustome(s,customerName,cardType,cardNumber);
	}

	@Override
	public CustPotentialCustomerQuery getPotentialCustomersQueryById(Integer id) {
		return potentialCustomersDao.getPotentialCustomersQueryById(id);
	}

	@Override
	public SysTeamMember getTeamIdByUserId(Integer userId) {
		return potentialCustomersDao.getTeamIdByUserId(userId);
	}

	@Override
	public void updatePotentialCustomersByProductCode(String productCode) {
		potentialCustomersDao.updatePotentialCustomersByProductCode(productCode);
	}
}
