package com.fx.HttpRequestor.api;

import com.fx.HttpRequestor.domain.ApiResult;
import com.fx.bean.MLWholeLabel;
import com.fx.model.AutoCaptionLabel;

import java.util.Map;

/**
 * Created by thinkpad on 2018/6/11.
 */
public class Connecter {
    public AutoCaptionLabel predictCaptionLabel(String filename,String Base64Image) {

        String url = "https://api.ai.qq.com/fcgi-bin/vision/vision_imgtotext";

        BaseApiService baseApiService = new BaseApiService();
        Map<String, String> params = baseApiService.buildCommonParam();

        params.put("image", Base64Image);

        try {
            ApiResult<Map> apiResult = baseApiService.postApi(url, params);
            System.out.println(apiResult);
            AutoCaptionLabel autoCaptionLabel = new AutoCaptionLabel();

            autoCaptionLabel.setCaption((String)apiResult.getData().get("text"));
            autoCaptionLabel.setFileName(filename);
            return autoCaptionLabel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
