package com.jijunjie.myandroidlib.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author some body for internet
 * @date unknown
 * @description 使用正则表达式进行表单验证
 */
public class RegexValidateUtils {

    static boolean flag = false;
    static String regex = "";

    private RegexValidateUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证非空
     *
     * @param notEmputy
     * @return
     */
    public static boolean checkNotEmputy(String notEmputy) {
        regex = "^\\s*$";
        return !check(notEmputy, regex);
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return check(email, regex);
    }

    /**
     * 验证手机号码
     * <p/>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147,181
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189、177
     *
     * @param cellphone the cell phone number
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1-3,5-9])|(17[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(telephone, regex);
    }

    /**
     * 验证传真号码
     *
     * @param fax
     * @return
     */
    public static boolean checkFax(String fax) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        return check(fax, regex);
    }

    /**
     * 验证QQ号码
     *
     * @param QQ
     * @return
     */
    public static boolean checkQQ(String QQ) {
        String regex = "^[1-9][0-9]{4,} $";
        return check(QQ, regex);
    }

    public static boolean checkNumber(String number) {
        String regex = "^\\d+$";
        return check(number, regex);
    }
}

