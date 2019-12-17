package cn.ekgc.itrip.util;

public class UserUtil {
    //Email地址正则表达式
    private static final String emailRegEx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //手机号码正则表达式
    private static final String CellphoneREgEx ="^1[0-9]{10}$";
    /**
     * <b>校验User的userCode格式是否正确</b>
     * @param userCode
     * @return
     */
    public static boolean checkUserCodePattern(String userCode){
        //判断是否为null
        if (userCode != null && !"".equals(userCode.trim())){
        //使用正则匹配
            if(userCode.matches(emailRegEx)){
                return true;
            } else if(userCode.matches(CellphoneREgEx)){
                return true;
            }
        }
        return false;
    }
}
