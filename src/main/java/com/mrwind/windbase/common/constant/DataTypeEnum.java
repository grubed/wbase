package com.mrwind.windbase.common.constant;

import java.util.HashMap;

/**
 * Created by michelshout on 2018/7/25.
 */
public enum DataTypeEnum implements BaseEnum {
    //数据类型，多个以逗号分隔。数据项：user-获取用户基本信息，userrole-获取用户标签(本参数必须要获取组织)，userextention-获取人员附加业务字段
    //rootteam-获取根部门团队，warehouseteam-获取所在仓库团队(和获取客服团队互斥)，clientserverteam-获取客服团队(和获取所在仓库团队互斥)，parentteam-获取父团队
    //teamrole-获取团队标签（本参数必须要获取组织），teamgps-获取团队经纬度坐标（本参数必须要获取组织）,teamextention-获取团队附加业务字段
    USER(1,"user"),USERROLE(2,"userrole"),USEREXTENTION(3,"userextention"),
    ROOTTEAM(4,"rootteam"),WAREHOUSETEAM(5,"store"),CLIENTSERVERTEAM(6,"service"),PARENTTEAM(7,"parentteam"),
    TEAMROLE(8,"teamrole"),TEAMGPS(9,"teamgps"),TEAMEXTENTION(10,"teamextention"),ROOTTEAMUSERRELATION(11,"rootteamuserrelation");

    private static HashMap<Integer, DataTypeEnum> items;
    private static void putItem(DataTypeEnum item){
        if(items == null){
            items = new HashMap<Integer, DataTypeEnum>();
        }
        items.put(item.getCode(), item);
    }
    public static DataTypeEnum valueOf(int code){
        if(items.containsKey(code)){
            return items.get(code);
        }
        throw new IllegalArgumentException("无效的DataTypeEnum.code" + code);
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

    DataTypeEnum(int code, String desc){
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
