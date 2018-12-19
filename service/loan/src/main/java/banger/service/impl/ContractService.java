package banger.service.impl;

import banger.dao.intf.IContractExportRecordDao;
import banger.dao.intf.IContractRelateItemDao;
import banger.dao.intf.IContractTemplateFileDao;
import banger.dao.intf.IContractTemplateTypeDao;
import banger.domain.loan.contract.LoanContractExportRecord;
import banger.domain.loan.contract.LoanContractRelateItem;
import banger.domain.loan.contract.LoanContractTemplateFile;
import banger.domain.loan.contract.LoanContractTemplateType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IContractService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * 业务访问类
 */
@Repository
public class ContractService implements IContractService {

	@Autowired
	private IContractExportRecordDao contractExportRecordDao;

	/**
	 * 新增
	 * @param contractExportRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertContractExportRecord(LoanContractExportRecord contractExportRecord,Integer loginUserId){
		this.contractExportRecordDao.insertContractExportRecord(contractExportRecord);
	}

	/**
	 *修改
	 * @param contractExportRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateContractExportRecord(LoanContractExportRecord contractExportRecord,Integer loginUserId){
		this.contractExportRecordDao.updateContractExportRecord(contractExportRecord);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteContractExportRecordById(Integer id){
		this.contractExportRecordDao.deleteContractExportRecordById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public LoanContractExportRecord getContractExportRecordById(Integer id){
		return this.contractExportRecordDao.getContractExportRecordById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition){
		return this.contractExportRecordDao.queryContractExportRecordList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition,IPageSize page){
		return this.contractExportRecordDao.queryContractExportRecordList(condition,page);
	}


	@Autowired
	private IContractRelateItemDao contractRelateItemDao;

	/**
	 * 新增
	 * @param contractRelateItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertContractRelateItem(LoanContractRelateItem contractRelateItem,Integer loginUserId){
		contractRelateItem.setCreateUser(loginUserId);
		contractRelateItem.setCreateDate(DateUtil.getCurrentDate());
		this.contractRelateItemDao.insertContractRelateItem(contractRelateItem);
	}

	/**
	 *修改
	 * @param contractRelateItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateContractRelateItem(LoanContractRelateItem contractRelateItem,Integer loginUserId){
		this.contractRelateItemDao.updateContractRelateItem(contractRelateItem);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteContractRelateItemById(Integer id){
		this.contractRelateItemDao.deleteContractRelateItemById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public LoanContractRelateItem getContractRelateItemById(Integer id){
		return this.contractRelateItemDao.getContractRelateItemById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition){
		return this.contractRelateItemDao.queryContractRelateItemList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition,IPageSize page){
		return this.contractRelateItemDao.queryContractRelateItemList(condition,page);
	}

	@Autowired
	private IContractTemplateFileDao contractTemplateFileDao;

	/**
	 * 新增
	 * @param contractTemplateFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertContractTemplateFile(LoanContractTemplateFile contractTemplateFile,Integer loginUserId){
		contractTemplateFile.setCreateUser(loginUserId);
		contractTemplateFile.setCreateDate(DateUtil.getCurrentDate());
		contractTemplateFile.setUpdateUser(loginUserId);
		contractTemplateFile.setUpdateDate(DateUtil.getCurrentDate());
		this.contractTemplateFileDao.insertContractTemplateFile(contractTemplateFile);
	}

	/**
	 *修改
	 * @param contractTemplateFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateContractTemplateFile(LoanContractTemplateFile contractTemplateFile,Integer loginUserId){
		contractTemplateFile.setUpdateUser(loginUserId);
		contractTemplateFile.setUpdateDate(DateUtil.getCurrentDate());
		this.contractTemplateFileDao.updateContractTemplateFile(contractTemplateFile);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteContractTemplateFileById(Integer id){
		this.contractTemplateFileDao.deleteContractTemplateFileById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public LoanContractTemplateFile getContractTemplateFileById(Integer id){
		return this.contractTemplateFileDao.getContractTemplateFileById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition){
		return this.contractTemplateFileDao.queryContractTemplateFileList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition,IPageSize page){
		return this.contractTemplateFileDao.queryContractTemplateFileList(condition,page);
	}

	@Autowired
	private IContractTemplateTypeDao contractTemplateTypeDao;

	/**
	 * 新增
	 * @param contractTemplateType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertContractTemplateType(LoanContractTemplateType contractTemplateType,Integer loginUserId){
		contractTemplateType.setCreateUser(loginUserId);
		contractTemplateType.setCreateDate(DateUtil.getCurrentDate());
		contractTemplateType.setUpdateUser(loginUserId);
		contractTemplateType.setUpdateDate(DateUtil.getCurrentDate());
		this.contractTemplateTypeDao.insertContractTemplateType(contractTemplateType);
	}

	/**
	 *修改
	 * @param contractTemplateType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateContractTemplateType(LoanContractTemplateType contractTemplateType,Integer loginUserId){
		contractTemplateType.setUpdateUser(loginUserId);
		contractTemplateType.setUpdateDate(DateUtil.getCurrentDate());
		this.contractTemplateTypeDao.updateContractTemplateType(contractTemplateType);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteContractTemplateTypeById(Integer id){
		this.contractTemplateTypeDao.deleteContractTemplateTypeById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public LoanContractTemplateType getContractTemplateTypeById(Integer id){
		return this.contractTemplateTypeDao.getContractTemplateTypeById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition){
		return this.contractTemplateTypeDao.queryContractTemplateTypeList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition,IPageSize page){
		return this.contractTemplateTypeDao.queryContractTemplateTypeList(condition,page);
	}

	@Override
	public List<LoanContractTemplateFile> queryTemplateFileByTypeId(Integer typeId) {
		return this.contractTemplateFileDao.queryTemplateFileByTypeId(typeId);
	}

	@Override
	public String getTemplateSelectFile(String loanTypeId) {

		Map<String,Object> condition = new HashMap<String,Object>();

		List<LoanContractRelateItem> relateItems = null;
		if(StringUtils.isNotBlank(loanTypeId)&&StringUtils.isNumeric(loanTypeId)){
			condition.put("loanTypeId",loanTypeId);
			relateItems = this.contractRelateItemDao.queryContractRelateItemList(condition);
		}

		Set<Integer> templateFileIds = new HashSet<Integer>();
		if(!CollectionUtils.isEmpty(relateItems)){
			for (LoanContractRelateItem relateItem : relateItems) {
				templateFileIds.add(relateItem.getTemplateFileId());
			}
		}


		//查询模版类型
		condition.clear();
		List<LoanContractTemplateType>  templateTypeList = contractTemplateTypeDao.queryContractTemplateTypeList(condition);

		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(templateTypeList)){
			for(int i=0;i<templateTypeList.size();i++){
				LoanContractTemplateType templateType = templateTypeList.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", "T_"+templateType.getId());
				jo.put("pId", 0);
				jo.put("name", templateType.getTypeName());
				jo.put("icon", "../core/imgs/icon/dir14.png");
				jo.put("open", true);
				jo.put("nocheck", true);
				ja.add(jo);
			}
		}



		//查询模版文件
		condition.clear();
		List<LoanContractTemplateFile>  templateFileList = contractTemplateFileDao.queryContractTemplateFileList(condition);

		if(CollectionUtils.isNotEmpty(templateFileList)){
			for (int i = 0; i < templateFileList.size(); i++) {
				LoanContractTemplateFile templateFile = templateFileList.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", templateFile.getId());
				jo.put("pId", "T_"+templateFile.getTemplateTypeId());
				jo.put("name", templateFile.getOldFileName());
				jo.put("icon", "../core/imgs/icon/file14.png");
				if(templateFileIds.contains(templateFile.getId())){
					jo.put("checked", true);
				}
				ja.add(jo);
			}
		}

//		if(funcSet.contains(func.getFuncId()))jo.put("checked", true);


//		if(rootCount==1){
//			((JSONObject)ja.get(0)).put("open", true);
//		}
		return ja.toString();
	}


	@Override
	public String getExportTemplateFile(String loanTypeId) {

		Map<String,Object> condition = new HashMap<String,Object>();
		Set<Integer> templateTypeIds = new HashSet<Integer>();
		JSONArray ja = new JSONArray();
		List<LoanContractTemplateFile> templateFileList = null;

		//查询模版文件
		if(StringUtils.isNotBlank(loanTypeId)&&StringUtils.isNumeric(loanTypeId)){
			condition.put("loanTypeId",loanTypeId);
			templateFileList = contractTemplateFileDao.queryTemplateFilesByLoanTypeId(condition);
		}

		if(!CollectionUtils.isEmpty(templateFileList)){
			for (LoanContractTemplateFile templateFile : templateFileList) {
				templateTypeIds.add(templateFile.getTemplateTypeId());
				JSONObject jo = new JSONObject();
				jo.put("id", templateFile.getId());
				jo.put("pId", "T_"+templateFile.getTemplateTypeId());
				jo.put("name", templateFile.getOldFileName());
				jo.put("icon", "../core/imgs/icon/file14.png");
				ja.add(jo);
			}

			//查询模版类型
			condition.clear();
			List<LoanContractTemplateType>  templateTypeList = contractTemplateTypeDao.queryContractTemplateTypeList(condition);

			if(CollectionUtils.isNotEmpty(templateTypeList)){
				for(int i=0;i<templateTypeList.size();i++){
					LoanContractTemplateType templateType = templateTypeList.get(i);
					if(templateTypeIds.contains(templateType.getId())){
						JSONObject jo = new JSONObject();
						jo.put("id", "T_"+templateType.getId());
						jo.put("pId", 0);
						jo.put("name", templateType.getTypeName());
						jo.put("icon", "../core/imgs/icon/dir14.png");
						jo.put("open", true);
//						jo.put("nocheck", true);
						ja.add(jo);
					}
				}
			}

		}

		return ja.toString();

	}

	@Override
	public void deleteContractRelateItemByLoanTypeId(Integer loanTypeId) {
		this.contractRelateItemDao.deleteContractRelateItemByLoanTypeId(loanTypeId);
	}


}
