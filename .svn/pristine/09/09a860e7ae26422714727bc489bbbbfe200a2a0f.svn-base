package banger.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITypeTableFieldDao;
import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTypeTableField;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ITypeTableFieldService;

/**
 * 贷款类型表单字段配置业务访问类
 */
@Repository
public class TypeTableFieldService implements ITypeTableFieldService {

	@Autowired
	private ITypeTableFieldDao typeTableFieldDao;

	/**
	 * 新增贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTypeTableField(LoanTypeTableField typeTableField,Integer loginUserId){
		typeTableField.setCreateUser(loginUserId);
		typeTableField.setCreateDate(DateUtil.getCurrentDate());
		typeTableField.setUpdateUser(loginUserId);
		typeTableField.setUpdateDate(DateUtil.getCurrentDate());
		this.typeTableFieldDao.insertTypeTableField(typeTableField);
	}

	/**
	 *修改贷款类型表单字段配置
	 * @param typeTableField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTypeTableField(LoanTypeTableField typeTableField,Integer loginUserId){
		typeTableField.setUpdateUser(loginUserId);
		typeTableField.setUpdateDate(DateUtil.getCurrentDate());
		this.typeTableFieldDao.updateTypeTableField(typeTableField);
	}

	/**
	 * 通过主键删除贷款类型表单字段配置
	 * @param id 主键Id
	 */
	public void deleteTypeTableFieldById(Integer id){
		this.typeTableFieldDao.deleteTypeTableFieldById(id);
	}

	/**
	 * 通过主键得到贷款类型表单字段配置
	 * @param id 主键Id
	 */
	public LoanTypeTableField getTypeTableFieldById(Integer id){
		return this.typeTableFieldDao.getTypeTableFieldById(id);
	}

	/**
	 * 查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition){
		return this.typeTableFieldDao.queryTypeTableFieldList(condition);
	}

	/**
	 * 分页查询贷款类型表单字段配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanTypeTableField> queryTypeTableFieldList(Map<String,Object> condition,IPageSize page){
		return this.typeTableFieldDao.queryTypeTableFieldList(condition,page);
	}

	/**
	 * 根据条件联表查询自定义字段集合
	 * 
	 * @param condition
	 */
	public List<LoanFieldExtend> queryTypeTableFieldListByCondition(Map<String, Object> condition) {
		return this.typeTableFieldDao.queryTypeTableFieldListByCondition(condition);
	}

	/**
	 * 保存或修改
	 *  @param loginUserId
	 * @param tableId
	 * @param fieldId
	 * @param fieldIsRequired
	 * @param fieldIsCondition
	 * @param fieldWebIsShow
	 * @param fieldAppIsShow
	 * @param refIds
	 */
	public void saveOrUpdateTypeTableField(Integer loginUserId, Integer tableId, Integer typeId, String precType,
										   String[] fieldId,
										   String[] fieldIsRequired, String[] fieldIsCondition, String[] fieldWebIsShow, String[] fieldAppIsShow, String[] refIds) {

			//查询表中是否有数据
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("tableId", tableId);
			condition.put("typeId",typeId);
			condition.put("precType",precType);
			List<LoanFieldExtend> list = typeTableFieldDao.queryTypeTableFieldListData(condition);
			Set<Integer> idSet = new HashSet<Integer>();
			for (LoanFieldExtend field : list)
				idSet.add(field.getFieldId());

			for (int i = 0; i < fieldId.length; i++) {
				Integer fId = Integer.valueOf(fieldId[i]);
				if(!idSet.contains(fId)){
					LoanTypeTableField loanTypeTableField = new LoanTypeTableField();
					// 新增
					loanTypeTableField.setTableId(tableId);
					loanTypeTableField.setFieldId(fId);
					loanTypeTableField.setLoanPrecType(precType);
					loanTypeTableField.setLoanTypeId(typeId);
					loanTypeTableField.setFieldIsRequired(Integer.valueOf(fieldIsRequired[i]));
					if (Integer.valueOf(fieldIsCondition[i]) != 2) {
						loanTypeTableField.setFieldIsCondition(Integer.valueOf(fieldIsCondition[i]));
					}
					loanTypeTableField.setFieldWebIsShow(Integer.valueOf(fieldWebIsShow[i]));
					loanTypeTableField.setFieldAppIsShow(Integer.valueOf(fieldAppIsShow[i]));
					loanTypeTableField.setCreateUser(loginUserId);
					loanTypeTableField.setCreateDate(DateUtil.getCurrentDate());
					loanTypeTableField.setUpdateUser(loginUserId);
					loanTypeTableField.setUpdateDate(DateUtil.getCurrentDate());
					typeTableFieldDao.insertTypeTableField(loanTypeTableField);
				}else{
					if(i<refIds.length) {
						// 修改
						LoanTypeTableField lt = typeTableFieldDao.getTypeTableFieldById(Integer.valueOf(refIds[i]));
						if (Integer.valueOf(fieldIsCondition[i]) != 2) {
							lt.setFieldIsCondition(Integer.valueOf(fieldIsCondition[i]));
						}
						lt.setFieldIsRequired(Integer.valueOf(fieldIsRequired[i]));
						lt.setFieldWebIsShow(Integer.valueOf(fieldWebIsShow[i]));
						lt.setFieldAppIsShow(Integer.valueOf(fieldAppIsShow[i]));
						lt.setUpdateUser(loginUserId);
						lt.setUpdateDate(DateUtil.getCurrentDate());
						typeTableFieldDao.updateTypeTableField(lt);
					}
				}
			}

		}

}
