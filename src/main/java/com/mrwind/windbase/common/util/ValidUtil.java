package com.mrwind.windbase.common.util;

import com.mrwind.windbase.common.constant.CountryCodeConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author hanjie
 */

public class ValidUtil {

    private static List<Integer> SUPPORT_COUNTRY_CODE;

    static {
        SUPPORT_COUNTRY_CODE = new ArrayList<>();
        SUPPORT_COUNTRY_CODE.add(CountryCodeConstant.CHINA);
        SUPPORT_COUNTRY_CODE.add(CountryCodeConstant.SAUDI);
    }

    /**
     * 校验支持的国家码
     *
     * @param countryCode 国际码
     * @return result
     */
    public static boolean checkCountryCode(int countryCode) {
        return SUPPORT_COUNTRY_CODE.contains(countryCode);
    }

    /**
     * 校验中国手机号码
     *
     * @param tel 手机号码
     * @return result
     */
    public static boolean checkTel(int countryCode, String tel) {
        if (!checkCountryCode(countryCode)) {
            return false;
        }
        switch (countryCode) {
            case CountryCodeConstant.CHINA:
                if (tel.length() == 11 && tel.startsWith("1")) {
                    return true;
                }
//                String regex = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
//                return Pattern.matches(regex, tel);
            case CountryCodeConstant.SAUDI:
                return tel.length() == 9;
            default:
                return false;
        }

    }

    /**
     * 根据手机号返回国家码
     *
     * @param tel 手机号码
     * @return result
     */
    public static int getCountryCode(String tel) {

        if (tel.length() == 11 && tel.startsWith("1")) {
            return CountryCodeConstant.CHINA;
        }
        return CountryCodeConstant.SAUDI;
    }

}
