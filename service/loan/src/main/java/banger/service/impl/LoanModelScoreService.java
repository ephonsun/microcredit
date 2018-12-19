package banger.service.impl;

import banger.domain.config.ModeScoreCustomField;
import banger.domain.config.ModeScoreFieldParams;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanScoreDetailInfo;
import banger.domain.loan.LoanType;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.OperationUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanModelScoreProvider;
import banger.moduleIntf.IScoreFieldParamsProvider;
import banger.moduleIntf.ITemplateFieldProvider;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.ILoanModelScoreService;
import banger.service.intf.IScoreDetailInfoService;
import banger.service.intf.ITypeService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @Description:计算评分模型得分实现类
 * @Date: Created in 15:48 2017/9/13.
 */
@Repository
public class LoanModelScoreService implements ILoanModelScoreService,ILoanModelScoreProvider {

	@Autowired
	private ITypeService typeService;
	@Autowired
	private IApplyInfoService applyInfoService;
	@Autowired
	private ITemplateFieldProvider iTemplateFieldProvider;
	@Autowired
	private IScoreFieldParamsProvider iScoreFieldParamsProvider;
	@Autowired
	private IScoreDetailInfoService scoreDetailInfoService;

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @param loanTypeId 贷款类型id
	 * @param loanProcessType 贷款过程类型 (Investigate)
	 * @Description:根据贷款id获取模板id
	 * @Date: 16:47 2017/9/13
	 */

	public BigDecimal countLoanModelScoreByModeId(Integer loanId, Integer loanTypeId, String loanProcessType) {
		//不是调查阶段不予计算评分项
		LoanScoreDetailInfo scoreDetailInfo = null;
		if (!LoanProcessTypeEnum.INVESTIGATE.type.equals(loanProcessType)) return null;
		if (loanId != null && loanTypeId != null) {
			LoanType loanType = typeService.getTypeById(loanTypeId);
			//校验贷款类型是否存在
			if (loanType != null && loanType.getModeId() != null) {
				BigDecimal score = null;
				Map<String, Object> map = new HashedMap();
				map.put("modeId", loanType.getModeId());
				//根据模型id查询评分项
				List<ModeScoreCustomField> modeList = iTemplateFieldProvider.getLoanModelScoreByModeId(loanType.getModeId());
				//根据模型id查询评分项参数表
				List<ModeScoreFieldParams> paramsList = iScoreFieldParamsProvider.queryScoreFieldParamsList(map);
				Map<String,List<ModeScoreCustomField>> itemMap = new HashMap<String,List<ModeScoreCustomField>>();
				Map<Integer,List<ModeScoreFieldParams>> paramsMap = new HashMap<Integer,List<ModeScoreFieldParams>>();

				modelScoreListToMap(modeList, itemMap);
				fieldParamsListToMap(paramsList,paramsMap);

				//删除当前贷款评分明细记录,重新计算插入
				scoreDetailInfoService.deleteScoreDetailInfoByLoanId(loanId);
				for (String tableName : itemMap.keySet()) {
					DataTable table = applyInfoService.getDataTableByLoanId(tableName, LoanProcessTypeEnum.INVESTIGATE.type, loanId);
					if (table.rowSize() > 0) {
						DataRow row = table.getRow(0);
						List<ModeScoreCustomField> itemList = itemMap.get(tableName);
						//同一张表中的所有字段循环处理
						for(ModeScoreCustomField item : itemList){
							Object fieldValue = row.get(item.getTalbeColumn());
							String fieldType = item.getFieldType();
							List<ModeScoreFieldParams> modeScoreFieldParamses = paramsMap.get(item.getFieldId());
							//保证字段类型和字段值不能为空
							if (fieldType != null && fieldValue != null) {
								if (fieldType.equalsIgnoreCase("YesNo") && "".equals(fieldValue)) {
									fieldValue = "0";
								}
								BigDecimal itemScore = getCurentCondtionParam(fieldType, fieldValue, modeScoreFieldParamses);
								score = OperationUtil.plus(score,itemScore);
								scoreDetailInfo = new LoanScoreDetailInfo();
								scoreDetailInfo.setLoanId(loanId);
								scoreDetailInfo.setFieldId(item.getFieldId());
								scoreDetailInfo.setFieldName(item.getFieldDisplay());
								scoreDetailInfo.setFieldColumn(item.getTalbeColumn());
								scoreDetailInfo.setFieldValue(String.valueOf(fieldValue));
								scoreDetailInfo.setFieldScore(itemScore);
								scoreDetailInfo.setFieldType(item.getFieldType());
								scoreDetailInfoService.insertScoreDetailInfo(scoreDetailInfo,null);
							}
						}
					}
				}
				return score;
			}
		}
		return null;
	}

	/**
	 * @Author: yangdw
	 * @param modeList
	 * @Description:评分项中相同的tableName放在一起
	 * @Date: 15:23 2017/9/14
	 */
	private void modelScoreListToMap(List<ModeScoreCustomField> modeList,Map<String,List<ModeScoreCustomField>> itemMap){
		for (ModeScoreCustomField item : modeList) {
			if (itemMap.containsKey(item.getTableName())) {
				itemMap.get(item.getTableName()).add(item);
			}else{
				List<ModeScoreCustomField> modeList1 = new ArrayList<ModeScoreCustomField>();
				modeList1.add(item);
				itemMap.put(item.getTableName(), modeList1);
			}
		}

	}

	/**
	 * @Author: yangdw
	 * @param paramsList 评分模型评分项参数集合
	 * @param paramsMap field作为key
	 * @Description:
	 * @Date: 16:57 2017/9/14
	 */
	private void fieldParamsListToMap(List<ModeScoreFieldParams> paramsList, Map<Integer, List<ModeScoreFieldParams>> paramsMap) {
			for (ModeScoreFieldParams item : paramsList) {
			if (paramsMap.containsKey(item.getFieldId())) {
				paramsMap.get(item.getFieldId()).add(item);
			}else{
				List<ModeScoreFieldParams> modeList1 = new ArrayList<ModeScoreFieldParams>();
				modeList1.add(item);
				paramsMap.put(item.getFieldId(), modeList1);
			}
		}
	}

	/**
	 * @Author: yangdw
	 * @param fieldType 字段类型
	 * @param fieldValue 字段值
	 * @param paramList 评分项集合
	 * @Description:取出对应评分的得分
	 * @Date: 9:26 2017/9/18
	 */
	private BigDecimal getCurentCondtionParam(String fieldType, Object fieldValue, List<ModeScoreFieldParams> paramList){
		if (fieldType.equalsIgnoreCase("Select")) {
			String val = (String) fieldValue;
			for (ModeScoreFieldParams param : paramList) {
				if (val.equals(param.getOptionParam2())) {
					return param.getParamScore();
				}
			}
		} else if (fieldType.equalsIgnoreCase("YesNo")) {
			String val = (String) fieldValue;
			if ("".equals(val)) {
				val = "0";
			}
			for (ModeScoreFieldParams param : paramList) {
				if (val.equals(param.getOptionParam2())) {
					return param.getParamScore();
				}
			}
		} else if (fieldType.equalsIgnoreCase("MultipleSelect")) {
			String val = (String) fieldValue;
			String[] valArray = val.split(",");
			BigDecimal paramScore = null;
			Map<String, BigDecimal> paramMap = new HashedMap();
			for (ModeScoreFieldParams param : paramList) {
				paramMap.put(param.getOptionParam2(), param.getParamScore());
			}
			for (int i = 0; i < valArray.length ; i++) {
				String varStr = valArray[i];
				paramScore = OperationUtil.plus(2, paramScore, paramMap.get(varStr));
			}
			return paramScore;
		} else {
			if (fieldType.equalsIgnoreCase("Number") || fieldType.equalsIgnoreCase("Decimal")
					|| fieldType.equalsIgnoreCase("Float") || fieldType.equalsIgnoreCase("Ratio")) {
//			Integer val = (Integer)fieldValue;
				Double val = Double.parseDouble(fieldValue.toString());
				for (int i = 0; i < paramList.size(); i++) {
					ModeScoreFieldParams param = paramList.get(i);
					if (i == 0) {
						//单独创建对象,防止-∞和+∞
						Double p2 = (StringUtil.isNotEmpty(param.getOptionParam2())) ? Double.parseDouble(param.getOptionParam2()) : null;
						if (p2 != null && val.doubleValue() < p2.doubleValue()) {       //最小值
							return param.getParamScore();
						}
					} else if (i > 0 && i < paramList.size() - 1) {
						Double p1 = (StringUtil.isNotEmpty(param.getOptionParam1())) ? Double.parseDouble(param.getOptionParam1()) : null;
						Double p2 = (StringUtil.isNotEmpty(param.getOptionParam2())) ? Double.parseDouble(param.getOptionParam2()) : null;
						if (val.doubleValue() >= p1.doubleValue() && val.doubleValue() < p2.doubleValue()) {
							return param.getParamScore();
						}
					} else if (i == paramList.size() - 1) {
						Double p1 = (StringUtil.isNotEmpty(param.getOptionParam1())) ? Double.parseDouble(param.getOptionParam1()) : null;
						if (p1 != null && val.doubleValue() >= p1.doubleValue()) {       //最大值
							return param.getParamScore();
						}
					}
				}
			}
		}
		return null;
	}
}
