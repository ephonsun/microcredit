package banger.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.util.DateUtil;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ILoanFieldExtendDao;
import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTypeTableField;
import banger.framework.dao.EntityDao;

@Repository
public class LoanFieldExtendDao extends EntityDao<LoanTypeTableField> implements ILoanFieldExtendDao {

	/**
	 * 得到贷款显示字段
	 * @param loanTypeId 贷款类型ID
	 * @param precType	贷款过程
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String precType){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precType", precType);
		List<LoanFieldExtend> list = (List<LoanFieldExtend>)this.queryEntities("getLoanFieldList", condition);
		return list;
	}

	/**
	 * 得到贷款显示字段
	 * @param loanTypeId 贷款类型ID
	 * @param precTypes	贷款过程
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String[] precTypes){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precTypes", precTypes);
		List<LoanFieldExtend> list = (List<LoanFieldExtend>)this.queryEntities("getLoanFieldList", condition);
		return list;
	}
	
	/**
	 * 得到贷款显示字段
	 * @param loanTypeId 贷款类型ID
	 * @param precType	贷款过程
	 * @param tableId	模板ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String precType,Integer tableId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precType", precType);
		condition.put("tableId", tableId);
		List<LoanFieldExtend> list = (List<LoanFieldExtend>)this.queryEntities("getLoanFieldList", condition);
		//时间类型默认值设置 0代表昨天，1代表今天，2代表明天 指定日期就存入指定日期
		for(LoanFieldExtend loanFieldExtend : list){
			if("Date".equals(loanFieldExtend.getFieldType())){
				if("0".equals(loanFieldExtend.getDefaultValue())){
					Date date = DateUtil.addDay(new Date(), -1);
					loanFieldExtend.setDefaultValue(DateUtil.format(date,DateUtil.DEFAULT_DATE_FORMAT));
				}else if("1".equals(loanFieldExtend.getDefaultValue())){
					loanFieldExtend.setDefaultValue(DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
				}else if("2".equals(loanFieldExtend.getDefaultValue())){
					Date date = DateUtil.addDay(new Date(), 1);
					loanFieldExtend.setDefaultValue(DateUtil.format(date,DateUtil.DEFAULT_DATE_FORMAT));
				}
			}
		}
		return list;
	}

	/**
	 * 得到贷款审批显示字段
	 * @param processId
	 * @param tableId
	 * @return
	 */
	public List<LoanFieldExtend> getTemplateFieldListByProcessId(Integer processId,Integer tableId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("processId", processId);
		condition.put("tableId", tableId);
		List<LoanFieldExtend> list = (List<LoanFieldExtend>)this.queryEntities("getTemplateFieldListByProcessId", condition);
		return list;
	}

	/**
	 * 得到贷款审批显示字段
	 * @param tableId
	 * @return
	 */
	public List<LoanFieldExtend> getLoanFieldListByTableId(Integer tableId){
		List<LoanFieldExtend> list = (List<LoanFieldExtend>)this.queryEntities("getLoanFieldListByTableId", tableId);
		return list;
	}


}
