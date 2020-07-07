package com.mrwind.windbase.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/6
 */

public class JsonUtil {

    public static <T> List<T> parse(List<JSONObject> objects, Class<T> cls) {
        return JSONArray.parseArray(JSON.toJSONString(objects), cls);
    }

}
