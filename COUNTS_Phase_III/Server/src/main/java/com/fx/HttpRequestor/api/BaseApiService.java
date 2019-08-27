package com.fx.HttpRequestor.api;


import com.fx.HttpRequestor.domain.ApiResult;
import com.fx.HttpRequestor.util.AuthSingature;
import com.fx.HttpRequestor.util.HttpUtil;
import com.fx.HttpRequestor.util.RandomUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.google.gson.reflect.TypeToken;


public class BaseApiService {

    protected static String AppID = "";

    protected static String AppKey = "3lxgwi65bhVhMlWF";

    @PostConstruct
    void init() throws IOException {
        Properties prop = new Properties();

        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

        //AppID   = prop.getProperty("txai.appid");
        //AppKey = prop.getProperty("txai.appkey");

    }

    /**
     * 构建公共参数（不包含签名参数sign）
     *
     * @return 返回公共参数（不包含签名参数sign）
     */
    protected Map<String, String> buildCommonParam() {

        Map<String, String> params = new HashMap<String, String>();

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

        params.put("app_id", "1106937204");
        params.put("time_stamp", timeStamp);
        params.put("nonce_str", RandomUtil.generateRandomString(16));
        params.put("session_id", "1509333186");
        return params;
    }

    protected <T> ApiResult<T> postApi(String url, Map<String, String> params) throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(url)) {
            return null;
        }

        if (params == null || params.size() <= 3) {
            return null;
        } else {
            //如果sign参数不存在，那就签名并且加上sign参数
            if (params.get("sign") == null) {
                String sign = AuthSingature.sign(AppKey, params);
                params.put("sign", sign);
            }
        }

        String jsonStr = HttpUtil.post(url, params);

        Gson gson = new Gson();
        Type type = new TypeToken<ApiResult<T>>() {
        }.getType();

        return gson.fromJson(jsonStr, type);

    }


}
