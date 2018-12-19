package banger.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.controller.LoanUtil;
import banger.dao.intf.*;
import banger.domain.loan.*;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IAssetsInfoProvider;
import banger.service.intf.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import javax.annotation.Resource;

/**
 * 资产负债表信息表业务访问类
 */
@Repository
public class AssetsInfoService implements IAssetsInfoService, IAssetsInfoProvider {

	@Autowired
	private IAssetsInfoDao assetsInfoDao;

	@Autowired
	private IAssetsFixedItemService assetsFixedItemService;

	@Autowired
	private IAssetsDebtsItemService assetsDebtsItemService;

	@Autowired
	private IAssetsAmountItemService assetsAmountItemService;

	@Autowired
	private IAssetsAccountItemService assetsAccountItemService;

	@Autowired
	private IAssetsStockItemService assetsStockItemService;

	@Autowired
	private IAssetsAccountItemDao assetsAccountItemDao;

	@Autowired
	private IAssetsAmountItemDao assetsAmountItemDao;

	@Autowired
	private IAssetsDebtsItemDao assetsDebtsItemDao;

	@Autowired
	private IAssetsFixedItemDao assetsFixedItemDao;

	@Autowired
	private IAssetsStockItemDao assetsStockItemDao;

	@Autowired
	private ICrossCheckQuanyiquanDao crossCheckQuanyiquanDao;

	@Resource
	private ILoanAnalysislBusinessAndConsumService analysislBusinessAndConsumService;

	/**
	 * 分配贷款时,生成资产主表信息
	 * @param assetsInfo
	 * @param loginUserId
	 */
	public void saveAssetsInfo(LoanAssetsInfo assetsInfo,Integer loginUserId){
		LoanAssetsInfo loanAssetsInfo = assetsInfoDao.getAssetsInfoByLoanId(assetsInfo.getLoanId());
		if(loanAssetsInfo==null){
			assetsInfo.setCreateUser(loginUserId);
			assetsInfo.setCreateDate(DateUtil.getCurrentDate());
			assetsInfo.setUpdateUser(loginUserId);
			assetsInfo.setUpdateDate(DateUtil.getCurrentDate());
			this.assetsInfoDao.insertAssetsInfo(assetsInfo);
			//根据贷款id处理三表数据,另存财务分析详情明细
			analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(assetsInfo.getLoanId());
		}
	}

	/**
	 * 新增资产负债表信息表
	 * @param assetsInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAssetsInfo(LoanAssetsInfo assetsInfo,Integer loginUserId){
		assetsInfo.setCreateUser(loginUserId);
		assetsInfo.setCreateDate(DateUtil.getCurrentDate());
		assetsInfo.setUpdateUser(loginUserId);
		assetsInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsInfoDao.insertAssetsInfo(assetsInfo);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(assetsInfo.getLoanId());
	}

	/**
	 *修改资产负债表信息表
	 * @param assetsInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAssetsInfo(LoanAssetsInfo assetsInfo,Integer loginUserId){
		if(null == assetsInfo.getOffAssetsAmount()){
			BigDecimal offAssetsAmount = new BigDecimal(0);
			assetsInfo.setOffAssetsAmount(offAssetsAmount);
		}
		if (null == assetsInfo.getOffDebtsAmount()) {
			BigDecimal offDebtsAmount = new BigDecimal(0);
			assetsInfo.setOffDebtsAmount(offDebtsAmount);
		}
		assetsInfo.setUpdateUser(loginUserId);
		assetsInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.assetsInfoDao.updateAssetsInfo(assetsInfo);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(assetsInfo.getLoanId());
	}

	/**
	 * 通过主键删除资产负债表信息表
	 * @param id 主键Id
	 */
	public void deleteAssetsInfoById(Integer id){
		LoanAssetsInfo assetsInfo = assetsInfoDao.getAssetsInfoById(id);
		this.assetsInfoDao.deleteAssetsInfoById(id);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(assetsInfo.getLoanId());
	}

	/**
	 * 通过主键得到资产负债表信息表
	 * @param id 主键Id
	 */
	public LoanAssetsInfo getAssetsInfoById(Integer id){
		return this.assetsInfoDao.getAssetsInfoById(id);
	}

	/**
	 * 查询资产负债表信息表
	 * @param condition 查询条件
	 * @return
	 */
	@Override
	public List<LoanAssetsInfo> queryAssetsInfoList(Map<String,Object> condition){
		return this.assetsInfoDao.queryAssetsInfoList(condition);
	}


	public LoanAssetsInfo queryOneAssetsInfoList(Map<String,Object> condition){
		List<LoanAssetsInfo> assetsInfos = queryAssetsInfoList(condition);
		if (CollectionUtils.isNotEmpty(assetsInfos))
			return assetsInfos.get(0);
		return new LoanAssetsInfo();
	}

	/**
	 * 分页查询资产负债表信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanAssetsInfo> queryAssetsInfoList(Map<String,Object> condition,IPageSize page){
		return this.assetsInfoDao.queryAssetsInfoList(condition,page);
	}

	/**
	 * 通过贷款Id得到资产负债表信息表
	 * @param loanId 贷款Id
	 */
	public LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId){
		return this.assetsInfoDao.getAssetsInfoByLoanId(loanId);
	}

	/**
	 * 通过贷款Id得到资产负债主界面
	 * @param loanId 贷款Id
	 */
	public Map<String,Object> getAssetsMain(Integer loanId){
		String [] arrayItem= LoanUtil.LOAN_ASSETS_ITEM.split(",");
		Map<String,Object> assetsMap=new HashMap<String,Object>();
		for(int i=0;i<arrayItem.length;i++) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("loanId", loanId);
			condition.put("columnName", arrayItem[i]);
			this.getAssetsItem(condition,arrayItem[i],assetsMap);
		}
		return assetsMap;
	}

	/**
	 * 获得主界面的单个子项
	 * @param loanId
	 * @param columnName
	 * @return
	 */
	public Map<String,Object> getReflushAssets(Integer loanId,String columnName){
		Map<String,Object> assetsMap=new HashMap<String,Object>();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanId",loanId);
		condition.put("columnName",columnName);
		this.getAssetsItem(condition,columnName,assetsMap);
		return assetsMap;
	}


	private void getAssetsItem(Map<String,Object> condition,String columnName,Map<String,Object> assetsMap){
		if(LoanUtil.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
			List<LoanAssetsAccountItem> assetsAccount=assetsAccountItemService.queryAssetsAccountItemList(condition);
			if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_RECEIVABLE_AMOUNT")){//应收账款
				assetsMap.put("ASSETS_RECEIVABLE_AMOUNT",assetsAccount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_PAYMENT_AMOUNT")){//预付账款
				assetsMap.put("ASSETS_PAYMENT_AMOUNT",assetsAccount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_PAYABLE_AMOUNT")){ //应付账款
				assetsMap.put("DEBTS_PAYABLE_AMOUNT",assetsAccount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_RECEIVED_AMOUNT")){//预收账款
				assetsMap.put("DEBTS_RECEIVED_AMOUNT",assetsAccount);
			}
		}else if(LoanUtil.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
			List<LoanAssetsDebtsItem> assetsDebts=assetsDebtsItemService.queryAssetsDebtsItemList(condition);
			if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_SHORT_AMOUNT")){ //短期负债
				assetsMap.put("DEBTS_SHORT_AMOUNT",assetsDebts);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_LONG_AMOUNT")){ //长期负债
				assetsMap.put("DEBTS_LONG_AMOUNT",assetsDebts);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_INVEST_AMOUNT")){//投资性负债
				assetsMap.put("DEBTS_INVEST_AMOUNT",assetsDebts);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_SELF_USER_AMOUNT")){//自用性负债
				assetsMap.put("DEBTS_SELF_USER_AMOUNT",assetsDebts);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_CONSUME_AMOUNT")){//消费性负债
				assetsMap.put("DEBTS_CONSUME_AMOUNT",assetsDebts);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_OTHER_AMOUNT")){//消费类其他负债
				assetsMap.put("DEBTS_OTHER_AMOUNT",assetsDebts);
			}

		}else if(LoanUtil.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){
			List<LoanAssetsAmountItem> assetsAmount=assetsAmountItemService.queryAssetsAmountItemList(condition);
			if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_CASH_AMOUNT" )){ //现金
				assetsMap.put("ASSETS_CASH_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_OPERATING_AMOUNT")){ //其他经营资产
				assetsMap.put("ASSETS_OPERATING_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_NON_OPERATING_AMOUNT")){//其他非经营资产
				assetsMap.put("ASSETS_NON_OPERATING_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_OTHER_AMOUNT")){//其他资产
				assetsMap.put("ASSETS_OTHER_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_INVEST_AMOUNT")){//投资性资产
				assetsMap.put("ASSETS_INVEST_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_EXTERNAL_CLAIMS")){//对外债权
				assetsMap.put("ASSETS_EXTERNAL_CLAIMS",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_ADVANCE_PAYMENT_AMOUNT")){//预付款
				assetsMap.put("ASSETS_ADVANCE_PAYMENT_AMOUNT",assetsAmount);
			}else if(StringUtil.isNotEmpty(columnName) && columnName.equals("DEBTS_BIZ_OTHER_AMOUNT")){//经营类其他负债
				assetsMap.put("DEBTS_BIZ_OTHER_AMOUNT",assetsAmount);
			}
		}else if(LoanUtil.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){ //存货
			List<LoanAssetsStockItem> assetsStock=assetsStockItemService.queryAssetsStockItemList(condition);
			if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_STOCK_AMOUNT")){
				assetsMap.put("ASSETS_STOCK_AMOUNT",assetsStock);
			}
		}else if(LoanUtil.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
			List<LoanAssetsFixedItem> assetsStock=assetsFixedItemService.queryAssetsFixedItemList(condition);
			if(StringUtil.isNotEmpty(columnName) && columnName.equals("ASSETS_FIXED_AMOUNT")){ //固定资产
				assetsMap.put("ASSETS_FIXED_AMOUNT",assetsStock);
			}
		}
	}

	/**
	 * 查询资产负债单个项目详情
	 * @param columnName 项目列名
	 * @param id 子项主键Id
	 * @param i
	 */
	public Object getAssetsInfoEntity(String columnName, Integer id, String tableName, int i){
		Map<String,Object> condition=new HashMap<String,Object>();
		if(tableName.equals("LOAN_ASSETS_ACCOUNT_ITEM")){
			LoanAssetsAccountItem assetsAccountItem = this.assetsAccountItemDao.getAssetsAccountItemById(id);
			if (i != 1) {
				assetsAccountItem.setRemark(assetsAccountItem.getRemark().replace("\r\n","<br>"));
				assetsAccountItem.setRemark(assetsAccountItem.getRemark().replace("\n","<br>"));
			}
			return (LoanAssetsAccountItem) assetsAccountItem;
		}else if(tableName.equals("LOAN_ASSETS_DEBTS_ITEM")){
			LoanAssetsDebtsItem assetsDebtsItem = this.assetsDebtsItemDao.getAssetsDebtsItemById(id);
			if (i != 1) {
				assetsDebtsItem.setRemark(assetsDebtsItem.getRemark().replace("\r\n","<br>"));
				assetsDebtsItem.setRemark(assetsDebtsItem.getRemark().replace("\n","<br>"));
			}
			return (LoanAssetsDebtsItem)assetsDebtsItem;
		}else if(tableName.equals("LOAN_ASSETS_AMOUNT_ITEM")){
			LoanAssetsAmountItem assetsAmountItem = this.assetsAmountItemDao.getAssetsAmountItemById(id);
			if (i != 1) {
				assetsAmountItem.setRemark(assetsAmountItem.getRemark().replace("\r\n","<br>"));
				assetsAmountItem.setRemark(assetsAmountItem.getRemark().replace("\n","<br>"));
			}
			return (LoanAssetsAmountItem)assetsAmountItem;
		}else if(tableName.equals("LOAN_ASSETS_STOCK_ITEM")){
			LoanAssetsStockItem assetsStockItem = this.assetsStockItemDao.getAssetsStockItemById(id);
			if (i != 1) {
				assetsStockItem.setRemark(assetsStockItem.getRemark().replace("\r\n","<br>"));
				assetsStockItem.setRemark(assetsStockItem.getRemark().replace("\n","<br>"));
			}
			return (LoanAssetsStockItem)assetsStockItem;
		}else if(tableName.equals("LOAN_ASSETS_FIXED_ITEM")){
			LoanAssetsFixedItem assetsFixedItem = this.assetsFixedItemDao.getAssetsFixedItemById(id);
			if (i != 1) {
				assetsFixedItem.setItemRemark(assetsFixedItem.getItemRemark().replace("\r\n","<br>"));
				assetsFixedItem.setItemRemark(assetsFixedItem.getItemRemark().replace("\n","<br>"));
			}
			return (LoanAssetsFixedItem)assetsFixedItem;
		}
		return null;
	}

	/**
	 * 删除资产负债单个项目
	 * @param tableName 表名
	 * @param id 子项主键Id
	 */
	public BigDecimal deleteAssets (String tableName,Integer id) {
		if(tableName.equals("LOAN_ASSETS_ACCOUNT_ITEM")){
			BigDecimal amount=assetsAccountItemDao.getAssetsAccountItemById(id).getAmount();
			this.assetsAccountItemDao.deleteAssetsAccountItemById(id);
			return amount;
		}else if(tableName.equals("LOAN_ASSETS_DEBTS_ITEM")){
			BigDecimal amount=assetsDebtsItemDao.getAssetsDebtsItemById(id).getBalanceAmount();
			this.assetsDebtsItemDao.deleteAssetsDebtsItemById(id);
			return amount;
		}else if(tableName.equals("LOAN_ASSETS_AMOUNT_ITEM")){
			BigDecimal amount=assetsAmountItemDao.getAssetsAmountItemById(id).getAmount();
			this.assetsAmountItemDao.deleteAssetsAmountItemById(id);
			return amount;
		}else if(tableName.equals("LOAN_ASSETS_STOCK_ITEM")){
			BigDecimal amount=assetsStockItemDao.getAssetsStockItemById(id).getAmount();
			this.assetsStockItemDao.deleteAssetsStockItemById(id);
			return amount;
		}else if(tableName.equals("LOAN_ASSETS_FIXED_ITEM")){
			BigDecimal amount=assetsFixedItemDao.getAssetsFixedItemById(id).getAmount();
			this.assetsFixedItemDao.deleteAssetsFixedItemById(id);
			return amount;
		}
		return new BigDecimal(0);
	}

	public void reFlushAssetsInfo(){

	}

	/**
	 * 更新assetsInfo表信息(用在子表的添加和修改时)
	 * @param loanId
	 * @param amount
	 * @param columnName
	 */
	public void updateAssetsAmount(Integer loanId,BigDecimal amount, String columnName,Integer userId) {
		LoanAssetsInfo assetsInfo = assetsInfoDao.getAssetsInfoByLoanId(loanId);
		if(StringUtils.isNotEmpty(columnName) && null != assetsInfo){
			String propertyName = defineVar(columnName);
			Object propertyValue = getValueByProperty(assetsInfo, propertyName);
			BigDecimal currAmount = null!=propertyValue?new BigDecimal(propertyValue.toString()):new BigDecimal(0);
			setValueByProperty(assetsInfo, propertyName,currAmount.add(amount));
			if(columnName.startsWith("ASSETS_")){
				BigDecimal totalAmount = assetsInfo.getAssetsTotalAmount();
				if(null==totalAmount){
					totalAmount = new BigDecimal(0);
				}
				assetsInfo.setAssetsTotalAmount(totalAmount.add(amount));
			}else if(columnName.startsWith("DEBTS_")){
				BigDecimal totalAmount = assetsInfo.getDebtsTotalAmount();
				if(null==totalAmount){
					totalAmount = new BigDecimal(0);
				}
				assetsInfo.setDebtsTotalAmount(totalAmount.add(amount));
			}
			assetsInfo.setUpdateUser(userId);
			assetsInfo.setUpdateDate(new Date());
			assetsInfoDao.updateAssetsInfo(assetsInfo);
			//交叉检验---权益检验
			LoanCrossCheckQuanyiquan loanCrossCheck = crossCheckQuanyiquanDao.getCrossCheckQuanyiquanByLoanId(loanId);

			BigDecimal assetsTotalAmount = assetsInfo.getAssetsTotalAmount();
			if(null==assetsTotalAmount) assetsTotalAmount = new BigDecimal(0);
			BigDecimal debtsTotalAmount = assetsInfo.getDebtsTotalAmount();
			if(null==debtsTotalAmount) debtsTotalAmount = new BigDecimal(0);

			//实际权益
			BigDecimal actualRight = assetsTotalAmount.subtract(debtsTotalAmount);

			if(null==loanCrossCheck){
				loanCrossCheck = new LoanCrossCheckQuanyiquan();
				loanCrossCheck.setLoanId(loanId);
				loanCrossCheck.setActualRight(actualRight);
				loanCrossCheck.setCreateDate(new Date());
				loanCrossCheck.setCreateUser(userId);
				crossCheckQuanyiquanDao.insertCrossCheckQuanyiquan(loanCrossCheck);
			}else{
				loanCrossCheck.setActualRight(actualRight);
				//应有权益
				if(null==loanCrossCheck.getDeservedRight()){
					loanCrossCheck.setDeservedRight(new BigDecimal(0));
					//偏差率
					loanCrossCheck.setDeviationRatio(new BigDecimal(0));
				}else{
					//差别
					loanCrossCheck.setDeviation(loanCrossCheck.getActualRight().subtract(loanCrossCheck.getDeservedRight()));
					//偏差率
					if(loanCrossCheck.getDeservedRight().compareTo(BigDecimal.ZERO)!=0){
						loanCrossCheck.setDeviationRatio(loanCrossCheck.getDeviation().divide(loanCrossCheck.getDeservedRight(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
					}else{
						loanCrossCheck.setDeviationRatio(null);
					}
				}
				loanCrossCheck.setUpdateDate(new Date());
				loanCrossCheck.setUpdateUser(userId);
				crossCheckQuanyiquanDao.updateCrossCheckQuanyiquan(loanCrossCheck);
			}
			//根据贷款id处理三表数据,另存财务分析详情明细
			analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(assetsInfo.getLoanId());
		}

	}

	private  String defineVar(String str){
		try{
			String retStr = "";
			String[] ss  = str.split("_");
			for (int i = 0; i < ss.length; i++) {
				char[] ch = ss[i].toLowerCase().toCharArray();
				if(i!=0&&ch[0] >= 'a' && ch[0] <= 'z'){
					ch[0] = (char)(ch[0] - 32);
				}
				retStr+=new String(ch);
			}
			return retStr;
		}catch (Exception e){
			return null;
		}
	}

	private  void setValueByProperty(Object o, String propertyName,  Object propertyValue) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
			Method method = pd.getWriteMethod();
			method.invoke(o, propertyValue);
		}catch (Exception e){
		}
	}

	private  Object getValueByProperty(Object o, String propertyName) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
			Method method = pd.getReadMethod();
			return method.invoke(o);
		}catch (Exception e){
			return null;
		}
	}

}
