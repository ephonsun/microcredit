package banger.moduleImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banger.domain.task.TskTaskInfoQuery;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.ITaskModule;
import banger.service.intf.ITaskInfoService;

@Service
public class TaskModuleImpl implements ITaskModule{

	@Autowired
	private ITaskInfoService taskInfoService;

	@Autowired
	private IPermissionModule permissionModule;
	
	@Override
	public List<TskTaskInfoQuery> queryTaskInfoListForApp(
			Map<String, Object> condition, IPageSize page) {

		return taskInfoService.queryTaskInfoListForApp(condition,page);
	}


}
