package banger.moduleIntf;

import banger.domain.permission.PmsMenu;
import banger.domain.permission.PmsRoleMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by XUElw on 2017/9/20.
 */
public interface IPremissionMenu {

    /**
     * 根据角色获取菜单
     * @param roleIds
     * @return
     */
    List<PmsMenu> getMenuByRoleIds(String roleIds);

}
