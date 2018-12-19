package banger.service.intf;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import banger.domain.task.TskTaskStatQuery;


public interface ITaskStatService {

	List<TskTaskStatQuery> queryTaskStatList(Map<String, Object> condition);

	List<TskTaskStatQuery> queryPersonList(Map<String, Object> condition);

	List<TskTaskStatQuery> queryGroupList();

	List<TskTaskStatQuery> queryMemberByTeamGroupId(String teamGroupId);

	List<TskTaskStatQuery> queryGroupListByGroupPermit(String groupPermit);

	List<TskTaskStatQuery> queryCrossTeamList(String groupPermit,Date startDate,Date endDate);

	List<TskTaskStatQuery> queryLoanTasksList(Map<String,Object> condition);

	List<TskTaskStatQuery> queryLoanTasksListForTeam(Map<String,Object> condition);

	File createCSVFile(List<String> head, List<List<String>> dataList,
					   String outPutPath, String filename) throws IOException;
}
