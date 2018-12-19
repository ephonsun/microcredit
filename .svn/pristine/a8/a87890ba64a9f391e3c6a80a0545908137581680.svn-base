package banger.controller;

import banger.common.BaseController;
import banger.domain.permission.PmsUserPassExpiration;
import banger.service.impl.UserPassExpirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 密码过期开关
 */

@Controller
@RequestMapping("/passExpiration")
public class UserPassExpirationController extends BaseController {

    @Autowired
    UserPassExpirationService userPassExpirationService;

    @RequestMapping("/queryPassExpiration")
    public String queryPassExpiration(){
        //Map<String,Object> condition = new HashMap<String, Object>();
        PmsUserPassExpiration pmsUserPassExpiration = userPassExpirationService.getUserPassExpiration();
        setAttribute("isActived",pmsUserPassExpiration.getIsActived());
        setAttribute("expirationDay",pmsUserPassExpiration.getExpirationDay());
        setAttribute("expirationId",pmsUserPassExpiration.getExpirationId());
        return "permission/vm/passExpiration";
    }

    /**
     * 更新密码过期
     */
    @RequestMapping("/updatePassExpiration")
    public void updatePassExpiration(){

        Integer loginUserId = getLoginInfo().getUserId();

        PmsUserPassExpiration pmsUserPassExpiration = new PmsUserPassExpiration();
        pmsUserPassExpiration.setExpirationId(Integer.valueOf(getParameter("mmgq_id")));
        pmsUserPassExpiration.setIsActived(Integer.valueOf(getParameter("mmgq_kg")));
        pmsUserPassExpiration.setExpirationDay(Integer.valueOf(getParameter("mmgq_day")));

        userPassExpirationService.updateUserPassExpiration(pmsUserPassExpiration,loginUserId);

    }

}

