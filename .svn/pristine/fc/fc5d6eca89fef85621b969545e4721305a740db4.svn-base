package banger.service.intf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAssetsInfo;

/**
 * 资产负债表信息表业务访问接口
 */
public interface IAssetsInfoService {

	/**
	 * 分配贷款时,生成资产主表信息
	 * @param assetsInfo
	 * @param loginUserId
	 */
	void saveAssetsInfo(LoanAssetsInfo assetsInfo,Integer loginUserId);

	/**
	 * 新增资产负债表信息表
	 * @param assetsInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAssetsInfo(LoanAssetsInfo assetsInfo, Integer loginUserId);

	/**
	 *修改资产负债表信息表
	 * @param assetsInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAssetsInfo(LoanAssetsInfo assetsInfo, Integer loginUserId);

	/**
	 * 通过主键删除资产负债表信息表
	 * @param ID 主键Id
	 */
	void deleteAssetsInfoById(Integer id);

	/**
	 * 通过主键得到资产负债表信息表
	 * @param ID 主键Id
	 */
	LoanAssetsInfo getAssetsInfoById(Integer id);

	/**
	 * 查询资产负债表信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAssetsInfo> queryAssetsInfoList(Map<String, Object> condition);

	/**
	 * 分页查询资产负债表信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAssetsInfo> queryAssetsInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 通过贷款Id得到资产负债表信息表
	 * @param loanId 贷款Id
	 */
	LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId);

	/**
	 * 通过贷款Id得到资产负债主界面
	 * @param loanId 贷款Id
	 */
	Map<String,Object> getAssetsMain(Integer loanId);

	/**
	 * 查询资产负债单个项目详情
	 * @param conlumeName 项目列名
	 * @param id 贷款Id
	 * @param i
	 */
	Object getAssetsInfoEntity(String conlumeName, Integer id, String tableName, int i);

	/**
	 * 删除资产负债单个项目,并返回原有的金额
	 * @param tableName 表名
	 * @param id 子项主键Id
	 */
	BigDecimal deleteAssets (String tableName,Integer id);

	/**
	 * 获得主界面的单个子项
	 * @param loanId
	 * @param columnName
	 * @return
	 */
	Map<String,Object> getReflushAssets(Integer loanId,String columnName);

	/**
	 * 更新assetsInfo表信息(用在子表的添加和修改时)
	 * @param loanId
	 * @param amount (当前值减去之前值，取差值)
	 * @param columnName
	 */
	void updateAssetsAmount(Integer loanId, BigDecimal amount, String columnName,Integer userId);
}
