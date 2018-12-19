package banger.service.intf;

import banger.domain.permission.ILoginInfo;

/**
 *
 * Created by zhusw on 2017/8/10.
 */
public interface IBackLoginService {

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    String login(String account,String password);


    /**
     * 退出
     * @param loginInfo
     * @return
     */
    String logout(ILoginInfo loginInfo);

}
