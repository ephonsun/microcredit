package banger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysModelConfig;
import banger.service.intf.IModelConfigService;

@Controller
@RequestMapping("/modelConfig")
public class ModelConfigController extends BaseController {

    private static final long serialVersionUID = 1L;
    @Autowired
    private IModelConfigService modelConfigService;

    /**
     * 查询模型配置
     *
     * @param modelId
     */
    @RequestMapping("/queryModelConfig")
    public void queryModelConfig(@RequestParam("modelId") Integer modelId) {
        Map<String, Object> condition = new HashMap<String, Object>();

        condition.put("modelId", modelId);
        List<SysModelConfig> modelConfigList = modelConfigService.queryModelConfigList(condition);
        renderText(JsonUtil.toJson(modelConfigList, "configId", "modelId,modelName,configKey,configValue").toString());
    }
}
