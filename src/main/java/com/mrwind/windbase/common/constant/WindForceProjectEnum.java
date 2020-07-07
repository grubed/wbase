package com.mrwind.windbase.common.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by michelshout on 2018/8/6.
 */
public enum WindForceProjectEnum implements BaseEnum {
    //1-沙特利雅得,2-沙特吉达,3-中国杭州
    RIYADH(1,"riyadh"),JEDDAH(2,"jeddah"),HANGZHOU(3,"hangzhou"),ICELAND(4,"iceland"),TAIWAN(5,"taiwan");

    private static HashMap<Integer, WindForceProjectEnum> items;
    private static void putItem(WindForceProjectEnum item){
        if(items == null){
            items = new HashMap<Integer, WindForceProjectEnum>();
        }
        items.put(item.getCode(), item);
    }
    public static WindForceProjectEnum valueOf(int code){
        if(items.containsKey(code)){
            return items.get(code);
        }
        throw new IllegalArgumentException("无效的WindForceProjectEnum.code" + code);
    }
    public static String valueOfString(int code){
        if(items.containsKey(code)){
            return items.get(code).getDesc();
        }
        return "unknow";
    }
    public static boolean hasEnumCode(int code){
        return items.containsKey(code);
    }

    public static boolean hasEnumDesc(String desc){
        if(items==null || items.isEmpty() || StringUtils.isBlank(desc)){
            return false;
        }
        for(WindForceProjectEnum windForceProjectEnum : items.values()){
            if(windForceProjectEnum.getDesc().equals(desc)){
                return true;
            }
        }
        return false;
    }

    private final int code;

    private final String desc;

    WindForceProjectEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
        putItem(this);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString(){
        return String.format("%s(%d, %s)", this.name(), code, desc);
    }
}
