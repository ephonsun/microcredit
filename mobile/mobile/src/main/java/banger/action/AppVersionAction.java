package banger.action;

import banger.common.BaseController;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.util.StringEncoderHelper;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 安卓版本升级接口
 *
 * @author zhusw
 */
public class AppVersionAction extends BaseController {
    private static final long serialVersionUID = 432982355669235797L;

    private static final String VRESIONS_STR = "BHSL";

    /**
     * 版本升级接口
     *
     * @return
     */
    public void compareAPKVersion(HttpServletRequest request) {
        try {
            String formatParam = StringEncoderHelper.getFormatUtf8String(this.getParameter("param"));
            log.info("compareAPKVersion request: " + formatParam);

            // 解析json参数
            String deviceId = "", localIp = "", version = "", needUpgrade = "false", serverVersion = "", upgradeUrl = "";
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(formatParam);

                if (!StringUtil.isNullOrEmpty(jsonObject.getString("deviceId"))) {
                    deviceId = jsonObject.getString("deviceId");
                }

                if (!StringUtil.isNullOrEmpty(jsonObject.getString("localIp"))) {
                    localIp = jsonObject.getString("localIp");
                }

                if (!StringUtil.isNullOrEmpty(jsonObject.getString("version"))) {
                    version = jsonObject.getString("version");
                }

            } catch (JSONException e) {
                log.info("解析产品分类查询接口compareAPKVersion参数 : " + e);
            }

            HttpServletRequest req = request;
            String path = FileUtil.contact(this.getRootPath(), "apk");
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String[] exts = {"apk"};
            Collection<File> files = FileUtils.listFiles(dir, exts, false);
            if (files.size() > 0) {
                ArrayList<File> list = new ArrayList<File>();
                list.addAll(files);
                File apkFile = list.get(0);
                serverVersion = apkFile.getName();
                serverVersion = serverVersion.replace(".apk", "");
                serverVersion = serverVersion.substring(4, serverVersion.length());//去掉文件名的"BSHL"字符串
                String[] serverVersionArray = serverVersion.split("\\.");
                String[] versionArray = version.split("\\.");
                if (serverVersionArray.length >= 3 && versionArray.length >= 3) {
                    Integer serverVersion1 = Integer.valueOf(serverVersionArray[0]);
                    Integer serverVersion2 = Integer.valueOf(serverVersionArray[1]);
                    Integer serverVersion3 = Integer.valueOf(serverVersionArray[2]);

                    Integer version1 = Integer.valueOf(versionArray[0]);
                    Integer version2 = Integer.valueOf(versionArray[1]);
                    Integer version3 = Integer.valueOf(versionArray[2]);

                    if (serverVersion1 > version1) {
                        needUpgrade = "true";
                        upgradeUrl = "apk/" + VRESIONS_STR+serverVersion + ".apk";
                    } else {
                        if (serverVersion2 > version2) {
                            needUpgrade = "true";
                            upgradeUrl = "apk/" +VRESIONS_STR+serverVersion + ".apk";
                        } else {
                            if (serverVersion3 > version3) {
                                needUpgrade = "need";
                                upgradeUrl = "apk/" +VRESIONS_STR+serverVersion + ".apk";
                            }
                        }
                    }
                }
            }

            //升级信息
            String remark = FileUtils.readFileToString(new File(path + File.separator + "remark.txt"),"UTF-8");

            JSONObject json = new JSONObject();
            json.put("needUpgrade", needUpgrade);
            json.put("upgradeUrl", upgradeUrl);
            json.put("version", serverVersion);
            json.put("remark", remark);
            json.put("result", 0);
            json.put("error", "");
            log.info("compareAPKVersion response: " + json.toString());
            renderText(json.toString());
        } catch (Exception e) {
            log.error("compareAPKVersion error: ", e);
            JSONObject baseJson = new JSONObject();
            baseJson.put("result", 1);
            baseJson.put("error", e.getMessage());
            renderText(baseJson.toString());
        }
    }

    /**
     * 设备状态上传接口
     */
    public void uploadDeviceState() {

    }


    /**
     * APK升级接口
     */
    public void upgradeAPK() {

    }


}
