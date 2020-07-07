package com.mrwind.windbase.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-11
 */

public class TeamType {

    /**
     * 普通分组
     */
    public static final int COMMON = 0;

    /**
     * 物流分组
     */
    public static final int STORE = 1;

    /**
     * 电商分组
     */
    public static final int RETAIL = 2;

    public static final List<Integer> ALL = Arrays.asList(COMMON, STORE, RETAIL);

}
