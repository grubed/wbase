package com.mrwind.windbase.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

import javax.ws.rs.core.MediaType;
import java.util.Map;


/**
 * 通用的 Http 请求
 *
 * @author hanjie
 * @date 2018/5/18
 */

public class HttpMethod {

    /**
     * GET
     */
    public static JSONObject get(String url) {
        return get(url, null);
    }

    /**
     * GET
     */
    public static JSONObject get(String url, Map<String, Object> headers) {
        System.out.println("GET : " + url);
        Client client = new Client();
        WebResource webResource = client.resource(url);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON);
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        ClientResponse clientResponse = builder.get(ClientResponse.class);
        if (clientResponse.getStatus() == 200) {
            String result = clientResponse.getEntity(String.class);
            return JSON.parseObject(result);
        }
        JSONObject result = new JSONObject();
        result.put("errCode", clientResponse.getStatus());
        result.put("errMsg", clientResponse.getEntity(String.class));
        return result;
    }

    /**
     * POST
     */
    public static JSONObject post(String url, String parameter) {
        return post(url, parameter, null);
    }

    public static JSONObject post(String url, String parameter, Map<String, Object> headers) {
        return post(url, parameter, headers, null);
    }

    /**
     * POST
     */
    public static JSONObject post(String url, String parameter, Map<String, Object> headers, ClientFilter filter) {
        System.out.println("POST : " + url);
        Client client = new Client();
        if (filter != null) {
            client.addFilter(filter);
        }
        WebResource webResource = client.resource(url);
        webResource.setProperty("Content-Type", org.springframework.http.MediaType.APPLICATION_JSON_UTF8);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON);
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        ClientResponse clientResponse = builder.post(ClientResponse.class, parameter);
        if (clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            String result = clientResponse.getEntity(String.class);
            return JSON.parseObject(result);
        }
        JSONObject result = new JSONObject();
        result.put("errCode", clientResponse.getStatus());
        result.put("errMsg", clientResponse.getEntity(String.class));
        return result;
    }


    /**
     * POST
     */
    public static JSONObject put(String url, Map<String,Object> parameter, Map<String, Object> headers) {
        System.out.println("POST : " + url);
        Client client = new Client();
        WebResource webResource = client.resource(url);
        WebResource.Builder builder = webResource.type(MediaType.APPLICATION_JSON);
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        ClientResponse clientResponse = builder.put(ClientResponse.class, parameter);
        if (clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            String result = clientResponse.getEntity(String.class);
            return JSON.parseObject(result);
        }
        JSONObject result = new JSONObject();
        result.put("errCode", clientResponse.getStatus());
        result.put("errMsg", clientResponse.getEntity(String.class));
        return result;
    }



    /**
     * DELETE
     */
    public static JSONObject delete(String url) {
        System.out.println("DELETE : " + url);
        Client client = new Client();
        WebResource webResource = client.resource(url);
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        if (clientResponse.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
            String result = clientResponse.getEntity(String.class);
            return JSON.parseObject(result);
        }
        JSONObject result = new JSONObject();
        result.put("errCode", clientResponse.getStatus());
        result.put("errMsg", clientResponse.getEntity(String.class));
        return result;
    }

}
