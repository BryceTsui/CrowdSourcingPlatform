package com.fx.service.impl;

import com.aliyun.oss.OSSClient;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * Description: 将本机文件转存到aliyun 已备访问
 * Created by Hanxinhu at 14:09 2018/6/16/016
 */
public class OSSHelper {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static final String accessKeyId = "LTAIFKY2agu6Sj1H";
    private static final String accessKeySecret = "5h7x9bYHZ8MVXtYjWINOj1fJATwKpk";
    private static final String bucketName = "counts";

    public String getUrl(String objectName){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
// 关闭Client。
        ossClient.shutdown();
        return url.toString();
    }

    /**
     *
     * @param file 要上传的文件
     * @param key 标志上传文件的唯一ID
     */
    public void upload(File file,String key){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
// 上传文件。

        ossClient.putObject(bucketName, key, file);
// 关闭OSSClient。
        ossClient.shutdown();
    }
}
