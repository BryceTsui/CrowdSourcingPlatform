package com.fx.controller;

import com.fx.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.PropertySource;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.net.URLEncoder;

import java.util.List;


/**
 * Created by thinkpad on 2018/6/6.
 */
@Controller
@RequestMapping("/counts/ml")
@CrossOrigin
public class MLController {
    @Autowired
    MLService mlService;

    public void addMLMission(){

    }

    public void getUserMission(){

    }

    public void getTargetGoldUser(){

    }

    public void addLabel(){

    }

    public void startMakeLabel(){

    }

    public void getWrongLabel(){

    }

    public void updateLabel(){

    }

    /**

     * 文件下载相关代码

     *

     * @param request

     * @param response

     * @return

     */

    @RequestMapping("/download")

    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {

        //文件名

        String fileName = "caption 词云.png";

        if (fileName != null) {

            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录

            //String realPath = new File("").getAbsolutePath() + File.separator + filePath;

            System.out.println("123");
            String realPath = "E:\\大二下\\软工\\COUNTS_Phase_III\\data\\image\\2\\caption 词云.png";
            File file = new File(realPath);

            if (file.exists()) {
                System.out.println("come");

                FileInputStream fis = null;

                BufferedInputStream bis = null;

                try {

                    response.setContentType("application/force-download");

                    // 设置文件名 支持中文

                    fileName = URLEncoder.encode(fileName, "UTF-8");

                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

                    byte[] buffer = new byte[1024];

                    fis = new FileInputStream(file);

                    bis = new BufferedInputStream(fis);

                    OutputStream os = response.getOutputStream();

                    int i = bis.read(buffer);

                    while (i != -1) {

                        os.write(buffer, 0, i);

                        i = bis.read(buffer);

                    }

                    System.out.println("success");

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    if (bis != null) {

                        try {

                            bis.close();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    }

                    if (fis != null) {

                        try {

                            fis.close();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    }

                }

            }

        }

        return null;

    }

}
