package banger.common.constant;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: wumh
 * Date: 14-1-21
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class PageConst {
	
	public final static String INSERT_OPERATE = "insert";
	public final static String UPDATE_OPERATE = "update";
	
    //按钮文本常量
    public final String BTN_RESET = "重置";
    public final String BTN_SEARCH = "搜索";
    public final String BTN_REFRESH = "刷新";
    public final String BTN_SAVE = "保存";
    public final String BTN_SAVE_AND_UPLOAD = "保存并上传视频";
    public final String BTN_SAVE_CONTINUE = "保存并新建";
    public final String BTN_CANCEL = "取消";
    public final String BTN_NEW = "新建";
    public final String BTN_DELETE = "删除";
    public final String BTN_SURE = "确定";
    public final String BTN_CLOSE = "关闭";
    public final String BTN_ADD_FIELD = "添加字段";
    public final String BTN_ADD = "添加";
    public final String BTN_SUBMIT = "提交";
    public final String BTN_SElECT_FILE = "选择附件";
    public final String BTN_SElECT_SCRIPT = "选择标准话术";
    public final String BTN_UPLOAD_SCRIPT = "上传话术";
    public final String BTN_CHOOSE_PRODUCT = "选择产品";
    public final String BTN_REMOVE_PRODUCT = "移除产品";
    public final String BTN_PRINT = "打印";
    public final String BTN_UPDATE = "修改";
    public final String BTN_AUTO_IMPORT = "自动导入";
    public final String BTN_IMPORT = "导入";
    public final String BTN_SET_CONDITION = "设置条件";
    public final String BTN_DELETE_ALL="全部删除";
    public final String BTN_REFUSE="拒绝";
    public final String BTN_ALLOT="分配";

    public String getBTN_ALLOT() {
        return BTN_ALLOT;
    }

    public String getBTN_REFUSE() {
        return BTN_REFUSE;
    }

    public String getBTN_DELETE_ALL() {
        return BTN_DELETE_ALL;
    }
    public final String BTN_UPGRADE = "升级";

    public String getBTN_UPGRADE() {
        return BTN_UPGRADE;
    }

    public String getBTN_IMPORT() {
        return BTN_IMPORT;
    }

    public String getBTN_ADD() {
        return BTN_ADD;
    }

    public String getBTN_ADD_FIELD() {
        return BTN_ADD_FIELD;
    }

    public String getBTN_AUTO_IMPORT() {
        return BTN_AUTO_IMPORT;
    }

    public String getBTN_CANCEL() {
        return BTN_CANCEL;
    }

    public String getBTN_CHOOSE_PRODUCT() {
        return BTN_CHOOSE_PRODUCT;
    }

    public String getBTN_CLOSE() {
        return BTN_CLOSE;
    }

    public String getBTN_DELETE() {
        return BTN_DELETE;
    }

    public String getBTN_NEW() {
        return BTN_NEW;
    }

    public String getBTN_PRINT() {
        return BTN_PRINT;
    }

    public String getBTN_REFRESH() {
        return BTN_REFRESH;
    }

    public String getBTN_REMOVE_PRODUCT() {
        return BTN_REMOVE_PRODUCT;
    }

    public String getBTN_RESET() {
        return BTN_RESET;
    }

    public String getBTN_SAVE() {
        return BTN_SAVE;
    }

    public String getBTN_SAVE_AND_UPLOAD() {
        return BTN_SAVE_AND_UPLOAD;
    }

    public String getBTN_SAVE_CONTINUE() {
        return BTN_SAVE_CONTINUE;
    }

    public String getBTN_SEARCH() {
        return BTN_SEARCH;
    }

    public String getBTN_SElECT_FILE() {
        return BTN_SElECT_FILE;
    }

    public String getBTN_SElECT_SCRIPT() {
        return BTN_SElECT_SCRIPT;
    }

    public String getBTN_SUBMIT() {
        return BTN_SUBMIT;
    }

    public String getBTN_SURE() {
        return BTN_SURE;
    }

    public String getBTN_UPDATE() {
        return BTN_UPDATE;
    }

    public String getBTN_UPLOAD_SCRIPT() {
        return BTN_UPLOAD_SCRIPT;
    }

    public static String getInsertOperate() {
        return INSERT_OPERATE;
    }

    public static String getUpdateOperate() {
        return UPDATE_OPERATE;
    }

    public String getBTN_SET_CONDITION() {
		return BTN_SET_CONDITION;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("BTN_ADD", BTN_ADD)
                .append("BTN_RESET", BTN_RESET)
                .append("BTN_SEARCH", BTN_SEARCH)
                .append("BTN_REFRESH", BTN_REFRESH)
                .append("BTN_SAVE", BTN_SAVE)
                .append("BTN_SAVE_AND_UPLOAD", BTN_SAVE_AND_UPLOAD)
                .append("BTN_SAVE_CONTINUE", BTN_SAVE_CONTINUE)
                .append("BTN_CANCEL", BTN_CANCEL)
                .append("BTN_NEW", BTN_NEW)
                .append("BTN_DELETE", BTN_DELETE)
                .append("BTN_SURE", BTN_SURE)
                .append("BTN_CLOSE", BTN_CLOSE)
                .append("BTN_ADD_FIELD", BTN_ADD_FIELD)
                .append("BTN_SUBMIT", BTN_SUBMIT)
                .append("BTN_SElECT_FILE", BTN_SElECT_FILE)
                .append("BTN_SElECT_SCRIPT", BTN_SElECT_SCRIPT)
                .append("BTN_UPLOAD_SCRIPT", BTN_UPLOAD_SCRIPT)
                .append("BTN_CHOOSE_PRODUCT", BTN_CHOOSE_PRODUCT)
                .append("BTN_REMOVE_PRODUCT", BTN_REMOVE_PRODUCT)
                .append("BTN_PRINT", BTN_PRINT)
                .append("BTN_UPDATE", BTN_UPDATE)
                .append("BTN_AUTO_IMPORT", BTN_AUTO_IMPORT)
                .append("BTN_IMPORT", BTN_IMPORT)
                .append("BTN_SET_CONDITION", BTN_SET_CONDITION)
                .append("BTN_UPGRADE", BTN_UPGRADE)
                .append("BTN_DELETE_ALL", BTN_DELETE_ALL)
                .toString();
    }
}
