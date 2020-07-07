package com.mrwind.windbase.common.constant;

import java.util.HashMap;

/**
 * Created by michelshout on 2018/8/6.
 */
public enum WindForceProjectTypeEnum implements BaseEnum {
    DELIVERY(1,"delivery"),OPENAPI(2,"openapi"),EXPRESS(3,"express");

    private static HashMap<Integer, WindForceProjectTypeEnum> items;
    private static void putItem(WindForceProjectTypeEnum item){
        if(items == null){
            items = new HashMap<Integer, WindForceProjectTypeEnum>();
        }
        items.put(item.getCode(), item);
    }
    public static WindForceProjectTypeEnum valueOf(int code){
        if(items.containsKey(code)){
            return items.get(code);
        }
        throw new IllegalArgumentException("无效的WindForceProjectNameEnum.code" + code);
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

    private final int code;

    private final String desc;

    WindForceProjectTypeEnum(int code, String desc){
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
