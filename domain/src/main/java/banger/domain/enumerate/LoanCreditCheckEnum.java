package banger.domain.enumerate;

import banger.framework.util.StringUtil;

/**
 * 证信调查图片类型
 * Created by zhusw on 2017/8/11.
 */
public enum LoanCreditCheckEnum {

//    ID_CARD_FRONT("IdCardFront", "身份证正面","身份证正面照片"),
//    ID_CARD_BACK("IdCardBack", "身份证反面","身份证反面照片"),
    //2017-12-05需求组确认改变改变名称
    ID_CARD_FRONT("IdCardFront", "证件正面","证件正面照片"),
    ID_CARD_BACK("IdCardBack", "证件背面","证件背面照片"),
    USER_AUTHOR_BOOK("UserAuthorBook", "授权书","用户授权书"),
    ;

    public final String type;					//类型
    public final String typeName;				//类型名称
    public final String display;

    LoanCreditCheckEnum(String type, String typeName, String display) {
        this.type = type;
        this.typeName = typeName;
        this.display = display;
    }

    public static String getNameByType(String type){
        for(LoanCreditCheckEnum checkType : LoanCreditCheckEnum.values()){
            if(checkType.type.equalsIgnoreCase(type)){
                return checkType.typeName;
            }
        }
        return "";
    }

}
