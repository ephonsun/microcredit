package banger.service.intf;

import java.util.List;
import java.util.Map;
import banger.domain.loan.LoanFlowStepItem;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 流程步骤明细表业务访问接口
 */
public interface IFlowStepItemService {

	/**
	 * 新增流程步骤明细表
	 * @param flowStepItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertFlowStepItem(LoanFlowStepItem flowStepItem, Integer loginUserId);

	/**
	 *修改流程步骤明细表
	 * @param flowStepItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateFlowStepItem(LoanFlowStepItem flowStepItem, Integer loginUserId);

	/**
	 * 通过主键删除流程步骤明细表
	 * @param id 主键Id
	 */
	void deleteFlowStepItemById(Integer id);

	/**
	 * 通过主键得到流程步骤明细表
	 * @param id 主键Id
	 */
	LoanFlowStepItem getFlowStepItemById(Integer id);

	/**
	 * 查询流程步骤明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition);

	/**
	 * 分页查询流程步骤明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition, IPageSize page);

	/**
	 * 判断还是添加中转方法
	 * @param subObj json对象
	 * @param stepId 步骤id
	 */
	void updaOrAddSub(JSONObject subObj,Integer stepId,Integer loginUserId) throws Exception;

	/**
	 * 获取排序号
	 * @return
	 */
	Integer getMaxSortNum();


	/**
	 * 上移下移
	 */
	void moveStepSortNo(Integer stepId,String type,Integer flowId);
}
