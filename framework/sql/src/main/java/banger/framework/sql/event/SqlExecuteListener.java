package banger.framework.sql.event;

import java.util.EventListener;

public interface SqlExecuteListener extends EventListener {
	void onSqlExecute();
}
