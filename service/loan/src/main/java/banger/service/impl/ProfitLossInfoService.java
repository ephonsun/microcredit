package banger.service.impl;

import banger.dao.intf.IProfitBizIncomeItemDao;
import banger.dao.intf.IProfitBizIncomeMonthDao;
import banger.dao.intf.IProfitConsumIncomeItemDao;
import banger.dao.intf.IProfitLossInfoDao;
import banger.domain.loan.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.ILoanCrossCheckNetProfitProvider;
import banger.moduleIntf.ILoanCrossCheckProvider;
import banger.moduleIntf.ILoanIncomeStatementProvider;
import banger.service.intf.ICrossCheckQuanyiquanService;
import banger.service.intf.ILoanAnalysislBusinessAndConsumService;
import banger.service.intf.IProfitLossInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * 损益情况信息表业务访问类
 */
@Repository
public class ProfitLossInfoService implements IProfitLossInfoService, ILoanIncomeStatementProvider {

	@Autowired
	private IProfitLossInfoDao profitLossInfoDao;
	@Autowired
	private IProfitBizIncomeItemDao iProfitBizIncomeItemDao;
	@Autowired
	private IProfitBizIncomeMonthDao iProfitBizIncomeMonthDao;
	@Autowired
	private IProfitConsumIncomeItemDao iProfitConsumIncomeItemDao;
	@Autowired
	private ILoanCrossCheckProvider iLoanCrossCheckProvider;
	@Autowired
	private ICrossCheckQuanyiquanService iCrossCheckQuanyiquanService;
	@Autowired
	private ILoanCrossCheckNetProfitProvider loanCrossCheckNetProfitProvider;
	@Autowired
	private ILoanAnalysislBusinessAndConsumService analysislBusinessAndConsumService;



	/**
	 * 分配贷款时,生成损益主表信息
	 *
	 * @param profitLossInfo
	 * @param loginUserId
	 */
	public void saveProfitLossInfo(LoanProfitLossInfo profitLossInfo, Integer loginUserId) {
		LoanProfitLossInfo loanProfitLossInfo = this.profitLossInfoDao.getProfitLossInfoByLoanId(profitLossInfo.getLoanId());
		if (loanProfitLossInfo == null) {
			Date now = DateUtil.getCurrentDate();
			//获取当前时间前一个月
			Date preNow = DateUtil.addMonth(now, -1);
			Integer endYear = DateUtil.getYear(preNow);
			Integer endMonth = DateUtil.getMonth(preNow);
			//获取当前时间前一年
			Date pre = DateUtil.addMonth(now, -12);
			Integer startYear = DateUtil.getYear(pre);
			Integer startMonth = DateUtil.getMonth(pre);
			profitLossInfo.setYearStart(startYear);
			profitLossInfo.setMonthStart(startMonth);
			profitLossInfo.setYearEnd(endYear);
			profitLossInfo.setMonthEnd(endMonth);
			profitLossInfo.setCreateUser(loginUserId);
			profitLossInfo.setCreateDate(now);
			profitLossInfo.setUpdateUser(loginUserId);
			profitLossInfo.setUpdateDate(now);
			this.profitLossInfoDao.insertProfitLossInfo(profitLossInfo);
			//根据贷款id处理三表数据,另存财务分析详情明细
			analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());
		}
	}

	/**
	 * 新增损益情况信息表
	 *
	 * @param profitLossInfo 实体对像
	 * @param loginUserId    登入用户Id
	 */
	public void insertProfitLossInfo(LoanProfitLossInfo profitLossInfo, Integer loginUserId) {
		profitLossInfo.setCreateUser(loginUserId);
		profitLossInfo.setCreateDate(DateUtil.getCurrentDate());
		profitLossInfo.setUpdateUser(loginUserId);
		profitLossInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.profitLossInfoDao.insertProfitLossInfo(profitLossInfo);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());
	}

	/**
	 * 修改损益情况信息表
	 *
	 * @param profitLossInfo 实体对像
	 * @param loginUserId    登入用户Id
	 */
	public void updateProfitLossInfo(LoanProfitLossInfo profitLossInfo, Integer loginUserId) {
		profitLossInfo.setUpdateUser(loginUserId);
		profitLossInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.profitLossInfoDao.updateProfitLossInfo(profitLossInfo);
		iLoanCrossCheckProvider.updateGroProAndNetProDeviation(profitLossInfo.getLoanId());
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());

	}

	/**
	 * 通过主键删除损益情况信息表
	 *
	 * @param id 主键Id
	 */
	public void deleteProfitLossInfoById(Integer id) {
		LoanProfitLossInfo profitLossInfo = profitLossInfoDao.getProfitLossInfoById(id);
		this.profitLossInfoDao.deleteProfitLossInfoById(id);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());
	}

	/**
	 * 通过主键得到损益情况信息表
	 *
	 * @param id 主键Id
	 */
	public LoanProfitLossInfo getProfitLossInfoById(Integer id) {
		return this.profitLossInfoDao.getProfitLossInfoById(id);
	}

	/**
	 * 通过贷款ID得到损益情况信息表d
	 *
	 * @param loanId 主键loanId
	 */
	public LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId) {
		return this.profitLossInfoDao.getProfitLossInfoByLoanId(loanId);
	}

	/**
	 * 查询损益情况信息表
	 *
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanProfitLossInfo> queryProfitLossInfoList(Map<String, Object> condition) {

		return this.profitLossInfoDao.queryProfitLossInfoList(condition);
	}

	/**
	 * 分页查询损益情况信息表
	 *
	 * @param condition 查询条件
	 * @param page      分页对像
	 * @return
	 */
	public IPageList<LoanProfitLossInfo> queryProfitLossInfoList(Map<String, Object> condition, IPageSize page) {
		return this.profitLossInfoDao.queryProfitLossInfoList(condition, page);
	}

//	public LoanProfitLossInfo queryLoanProfitLossInfoByIds(Integer loanId, Integer loanClassId) {
//		return  this.profitLossInfoDao.queryLoanProfitLossInfoByIds(loanId, loanClassId);
//	}

	/**
	 * @Author: yangdw
	 * @params: * @param null
	 * @Description: 修改收入和支出, 主表要重新被修改
	 * @Date: 12:52 2017/8/24
	 */
	@Override
	public Boolean updateLoanProfitLossInfoByOpt(Integer loanId, Integer loanClassId, Integer loginUserId) {
		Map<String, Object> map = new HashedMap();
		map.put("loanId", loanId);
		map.put("loanClassId", loanClassId);

		LoanProfitLossInfo loanProfitLossInfo = new LoanProfitLossInfo();
		LoanProfitLossInfo profitLossInfo = profitLossInfoDao.getProfitLossInfoByLoanId(loanId);

		//收入统计
		BigDecimal plus1 = new BigDecimal("0.00");
		BigDecimal plus2 = new BigDecimal("0.00");
		//支出统计
		BigDecimal plus3 = new BigDecimal("0.00");
		BigDecimal plus4 = new BigDecimal("0.00");
		BigDecimal plus5 = new BigDecimal("0.00");
		//经营类贷款
		if (loanClassId == 1) {
			List<LoanProfitBizIncomeItem> loanProfitBizIncomeItems = iProfitBizIncomeItemDao.queryProfitBizIncomeItemList(map);
			for (LoanProfitBizIncomeItem item : loanProfitBizIncomeItems) {
				if ("BUSINESS_INCOME_AMOUNT".equals(item.getColumnName())) {
					//经营收入
					plus1 = OperationUtil.plus(plus1, item.getTotalAmount());
				} else if ("OTHER_INCOME_AMOUNT".equals(item.getColumnName())) {
					//其他收入
					plus2 = OperationUtil.plus(plus2, item.getTotalAmount());
				} else if ("FIXED_COST_DEFRAY_AMOUNT".equals(item.getColumnName())) {
					//固定成本支出
					plus3 = OperationUtil.plus(plus3, item.getTotalAmount());
				} else if ("TEX_DEFRAY_AMOUNT".equals(item.getColumnName())) {
					//所得税支出
					plus4 = OperationUtil.plus(plus4, item.getTotalAmount());
				} else if ("OTHER_DEFRAY_AMOUNT".equals(item.getColumnName())) {
					//其他支出
					plus5 = OperationUtil.plus(plus5, item.getTotalAmount());
				}
			}
			loanProfitLossInfo.setBusinessIncomeAmount(plus1);
			loanProfitLossInfo.setOtherIncomeAmount(plus2);
			loanProfitLossInfo.setFixedCostDefrayAmount(plus3);
			loanProfitLossInfo.setTexDefrayAmount(plus4);
			loanProfitLossInfo.setOtherDefrayAmount(plus5);
		} else if (loanClassId == 2) {
			List<LoanProfitConsumIncomeItem> loanProfitConsumIncomeItems = iProfitConsumIncomeItemDao.queryProfitConsumIncomeItemList(map);
			for (LoanProfitConsumIncomeItem item : loanProfitConsumIncomeItems) {
				if ("HOME_INCOME_AMOUNT".equals(item.getColumnName())) {
					//家庭收入
					plus1 = OperationUtil.plus(plus1, item.getTotalAmount());
				} else if ("OTHER_INCOME_AMOUNT".equals(item.getColumnName())) {
					//其他收入
					plus2 = OperationUtil.plus(plus2, item.getTotalAmount());
				} else if ("FIXED_DEFRAY_AMOUNT".equals(item.getColumnName())) {
					//固定支出
					plus3 = OperationUtil.plus(plus3, item.getTotalAmount());
				} else if ("TEX_PERSONAL_AMOUNT".equals(item.getColumnName())) {
					//个人所得所支出
					plus4 = OperationUtil.plus(plus4, item.getTotalAmount());
				} else if ("OTHER_DEFRAY_AMOUNT".equals(item.getColumnName())) {
					//其他支出
					plus5 = OperationUtil.plus(plus5, item.getTotalAmount());
				}
			}
			loanProfitLossInfo.setHomeIncomeAmount(plus1);
			loanProfitLossInfo.setOtherIncomeAmount(plus2);
			loanProfitLossInfo.setFixedDefrayAmount(plus3);
			loanProfitLossInfo.setTexPersonalAmount(plus4);
			loanProfitLossInfo.setOtherDefrayAmount(plus5);
		}
		loanProfitLossInfo.setId(profitLossInfo.getId());
		loanProfitLossInfo.setUpdateUser(loginUserId);
		loanProfitLossInfo.setUpdateDate(DateUtil.getCurrentDate());

		profitLossInfoDao.updateProfitLossInfo(loanProfitLossInfo);
		//更新主表经营收入之后,需要更新交叉检验表的销售额检验和权益检验


		if (loanClassId == 1) {
			LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(loanId);
			if(lccq==null){//如果权益表字段为空 先新增字段
				lccq=new LoanCrossCheckQuanyiquan();
				lccq.setLoanClassId(1);
				lccq.setLoanId(loanId);
				iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,loginUserId);
			}
			iLoanCrossCheckProvider.updateSaleDev(loanId);
			iLoanCrossCheckProvider.updateQuanyiquanDev(loanId);
			//计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
			loanCrossCheckNetProfitProvider.updateNetProDev(loanId);
		}
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());
		return true;
	}

	/**
	 * @Author: yangdw
	 * @params: * @param null
	 * @Description:更改月份的支出和收入,修改收入和支出明细表
	 * @Date: 17:58 2017/8/25
	 */
	@Override
	public Boolean updateLoanProfitBizIncomeItemByOpt(Integer loanId, Integer month, Integer loginUserId) {
		Map<String, Object> map = new HashedMap();
		map.put("loanId", loanId);
		List<LoanProfitBizIncomeItem> loanProfitBizIncomeItems = iProfitBizIncomeItemDao.queryProfitBizIncomeItemList(map);
		for (LoanProfitBizIncomeItem item : loanProfitBizIncomeItems) {
			BigDecimal plus = new BigDecimal("0.00");
			map.clear();
			map.put("incomeId", item.getId());
			List<LoanProfitBizIncomeMonth> loanProfitBizIncomeMonths = iProfitBizIncomeMonthDao.queryProfitBizIncomeMonthList(map);
			for (LoanProfitBizIncomeMonth monthItem : loanProfitBizIncomeMonths) {
				//重新计算总金额(收入表)
				plus = OperationUtil.plus(plus, monthItem.getMonthAmount());
			}
			item.setAverageAmount(OperationUtil.divide(BigDecimal.valueOf(month), 2, plus));
			item.setUpdateDate(new Date());
			item.setUpdateUser(loginUserId);
			item.setTotalAmount(plus);
			iProfitBizIncomeItemDao.updateProfitBizIncomeItem(item);
		}
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(loanId);
		return true;
	}

	/**
	 * 通过贷款ID得到损益信息对像
	 *
	 * @param loanId
	 * @return
	 */
	public LoanProfitLossInfo getLoanProfitLossInfoByLoanId(Integer loanId) {
		return this.profitLossInfoDao.getProfitLossInfoByLoanId(loanId);
	}



	/*
	public void updateProfitLossInfoByLoanId(Map<String, Object> condition) {
		this.profitLossInfoDao.updateProfitLossInfoByLoanId(condition);
	}


	/**
	 *修改损益情况信息表
	 * @param profitLossInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProfitLossInfoByLoanId(LoanProfitLossInfo profitLossInfo, Integer loginUserId) {
		profitLossInfo.setUpdateUser(loginUserId);
		profitLossInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.profitLossInfoDao.updateProfitLossInfoByLoanId(profitLossInfo);
		//根据贷款id处理三表数据,另存财务分析详情明细
		analysislBusinessAndConsumService.saveAnalysislBusinessOrConsum(profitLossInfo.getLoanId());
	}

	/**
	 * 更新月份区间
	 */
	public Boolean updateInterval(String startYearMonth, String endYearMonth, String loanId, String loanClassId,String userId) {
		String startYear = startYearMonth.substring(0, 4);
		String startMonth = startYearMonth.substring(5);
		String endYear = endYearMonth.substring(0, 4);
		String endMonth = endYearMonth.substring(5);
		if (Integer.parseInt(loanClassId) == 1) {
			//存原时间段年月数组
			List oldTimeArr = new ArrayList();  //原来时间段
			List<LoanProfitBizIncomeMonth> list = iProfitBizIncomeMonthDao.queryYearAndMonthByLoanId(Integer.parseInt(loanId));
			for (LoanProfitBizIncomeMonth li : list) {
				oldTimeArr.add(li.getYearVal().toString() + li.getMonthVal().toString());
			}
			//存新时间段年月数组
			List<String> newTimeArr = getList(startYear, endYear, startMonth, endMonth);
			List<String> temp = new ArrayList<String>();
			temp.addAll(newTimeArr);
			temp.addAll(oldTimeArr);
			temp.removeAll(oldTimeArr);
			//该新增的年月
			List<String> pluArr = new ArrayList();
			//该删除的年月
			List<String> dleArr = new ArrayList();
			for (String string : temp) {
				pluArr.add(string);
			}
			temp.clear();
			temp.addAll(newTimeArr);
			temp.addAll(oldTimeArr);
			temp.removeAll(newTimeArr);
			for (String s : temp) {
				dleArr.add(s);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanId", Integer.parseInt(loanId));
			List<LoanProfitBizIncomeItem> biz = iProfitBizIncomeItemDao.queryProfitBizIncomeItemList(map);
			//更新月份明细表
			if (biz != null) {
				for (int i = 0; i < biz.size(); i++) {
					for (int j = 0; j < pluArr.size(); j++) {
						LoanProfitBizIncomeMonth lp = new LoanProfitBizIncomeMonth();
						lp.setIncomeId(biz.get(i).getId());
						lp.setYearVal(Integer.parseInt(pluArr.get(j).substring(0, 4)));
						lp.setMonthVal(Integer.parseInt(pluArr.get(j).substring(4)));
						//lp.setMonthAmount(BigDecimal.valueOf(0));
						//添加新增的月份
						iProfitBizIncomeMonthDao.insertProfitBizIncomeMonth(lp);
					}
					for (int k = 0; k < dleArr.size(); k++) {
						//删除多余的月份
						iProfitBizIncomeMonthDao.deleteProfitBizIncomeMonthByIncomeIdAndYM(biz.get(i).getId(),
								Integer.parseInt(dleArr.get(k).substring(0, 4)), Integer.parseInt(dleArr.get(k).substring(4)));
					}
				}
				//更新收入支出明细表
				updateLoanProfitBizIncomeItemByOpt(Integer.parseInt(loanId), newTimeArr.size(), Integer.parseInt(userId));
				//更新主表
				updateLoanProfitLossInfoByOpt(Integer.parseInt(loanId), 1, Integer.parseInt(userId));

			}
		}
		LoanProfitLossInfo loanProfitLossInfo = new LoanProfitLossInfo();
		loanProfitLossInfo.setLoanId(Integer.parseInt(loanId));
		loanProfitLossInfo.setYearStart(Integer.parseInt(startYear));
		loanProfitLossInfo.setMonthStart(Integer.parseInt(startMonth));
		loanProfitLossInfo.setYearEnd(Integer.parseInt(endYear));
		loanProfitLossInfo.setMonthEnd(Integer.parseInt(endMonth));
		//更新主表时间
		updateProfitLossInfoByLoanId(loanProfitLossInfo, Integer.parseInt(userId));
		return true;
	}

	private List getList(String startYear,String endYear,String startMonth,String endMonth){
		List<String> arr=new ArrayList();
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(startYear), Integer.parseInt(startMonth) - 2, 1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt(endYear), Integer.parseInt(endMonth) - 2, 1);
		int result = 0;
		int i = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 1;
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		result = Math.abs(month + i);

		for (int j = 0; j < result; j++) {
			c1.add(Calendar.MONTH, 1);
			arr.add(DateUtil.format(c1.getTime(), "yyyyM").toString());
		}
			return arr;
	}
}