
package com.fx.controller;

import com.fx.bean.Image;
import com.fx.bean.MissionCompletion;
import com.fx.model.LocalLabel;
import com.fx.model.Mission;
import com.fx.model.User;
import com.fx.service.ImageService;
import com.fx.service.LabelService;
import com.fx.service.MissionService;
import com.fx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by thinkpad on 2018/3/17.
 */
@Controller
@RequestMapping("/counts/image")
@CrossOrigin
public class ImageController {
    //图片存放地址
    // public static final String imageURL = "E:\\大二下\\软工\\ImageTest\\";
    public static final String imageURL = "../data/image/";
    @Autowired
    ImageService imageService;

    @Autowired
    LabelService labelService;

    @Autowired
    MissionService missionService;
    @Autowired
    UserService userService;

    private final static String autoDirName = "../data/autoImage";

    public ImageController() {
        File file = new File(autoDirName);
        try {
            if (!file.exists())
                file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(
            value = "/uploadAutoClassificationImg"
    )
    @ResponseBody
    public void uploadAutoClassificationImg(int missionID, MultipartFile file[]) throws Exception {
        //xxx 不行 还是要坚持该下去
        for (MultipartFile f : file) {

            // System.out.println("missionname"+mission.getID());

            // String imgName = System.currentTimeMillis() + f.getOriginalFilename(); // 这里图片的名字用毫秒数+图片原来的名字拼接,迭代一暂时不考虑重名情况
            String imgName = f.getOriginalFilename();//这里图片还是以原名命名
            //上传文件
            imageService.uploadFileUtil(f.getBytes(), autoDirName + "/" + missionID + "/" + "allimage/", imgName);
        }
    }

    @RequestMapping
            (value = "/uploadAutoCaptionImg")
    @ResponseBody
    public void uploadAutoCaptionImg(int missionID, MultipartFile file[]) throws Exception {

        for (MultipartFile f : file) {


            String imgName = f.getOriginalFilename();//这里图片还是以原名命名
            System.out.println(imgName);
            imageService.uploadFileUtil(f.getBytes(), autoDirName + "/" + missionID + "/allimage/", imgName);
        }
    }

    @RequestMapping(
            value = "/uploadAutoDetectionImg"
    )
    @ResponseBody
    public void uploadAutoDetectionImg(int missionID, MultipartFile file[]) throws Exception {
        int size = file.length;
        int i = 0;

        for (MultipartFile f : file) {


            String imgName = f.getOriginalFilename();//这里图片还是以原名命名
            System.out.println(imgName);
            imageService.uploadFileUtil(f.getBytes(), autoDirName + "/" + missionID + "/allimage/", imgName);
        }

        for (MultipartFile f : file) {
            String imgName = f.getOriginalFilename();//这里图片还是以原名命名
            if (i <= size / 2)
                imageService.uploadFileUtil(f.getBytes(), autoDirName + "/" + missionID + "/images/test/", imgName);
            else
                imageService.uploadFileUtil(f.getBytes(), autoDirName + "/" + missionID + "/images/train/", imgName);
            i++;
        }
    }

    /**
     * 上传多张图片
     * 前端语法参考：http://blog.csdn.net/jtshongke/article/details/78516559?locationNum=7&fps=1
     * 可能会出现文件太大，无法上传，如果发生请联系后端修改文件大小限制
     *
     * @param file 要上传的文件集合
     * @throws Exception
     */
    @RequestMapping(
            value = "/uploadImg"
    )

    @ResponseBody
    public void uploadImg(int mission, MultipartFile file[]) throws Exception {
        //  System.out.println(mission.getID());
        System.out.println("得到的areaName:" + imageURL);
        //写给后端coder：file似乎不要用requestparam定义，不然会报错。。。

        //  missionService.addMission(mission);
        for (MultipartFile f : file) {

            // System.out.println("missionname"+mission.getID());

            // String imgName = System.currentTimeMillis() + f.getOriginalFilename(); // 这里图片的名字用毫秒数+图片原来的名字拼接,迭代一暂时不考虑重名情况
            String imgName = f.getOriginalFilename();//这里图片还是以原名命名
            //上传文件
            imageService.uploadFileUtil(f.getBytes(), imageURL + mission + "/", imgName);
        }

    }

    @RequestMapping(value = "/addmission",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public int addMission(@RequestBody Mission mission) {


        missionService.addMission(mission);
        return mission.getID();
    }

    private void addRequestorExp(String userName, int missionExp) {
        User user = userService.getUser(userName);
        user.setExp(user.getExp() + missionExp);
        userService.updateUserData(user);
    }


    /**
     * 获得原图的方法
     *
     * @param missionid 任务名 迭代1默认“mission”
     * @param imgname   图片名，包括.jpg
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "checkimg",
            method = RequestMethod.POST
    )
    public LocalLabel checkImg(String missionid, String imgname) {
        File file = new File(imageURL + missionid + "/" + imgname);

        if (file.exists()) {
            InputStream in = null;
            byte[] data = null;
            try {
                in = new FileInputStream(file);
                data = new byte[in.available()];
                in.read(data);
                in.close();
                LocalLabel localLabel = new LocalLabel();
                BASE64Encoder encoder = new BASE64Encoder();
                String url = encoder.encode(data);
                String head = "data:image/" + file.getName().split("[.]")[1] + ";base64,";

                localLabel.setUrl(head + url);
                // localLabel.setOtherComments(null);
                // localLabel.setOverallComment(null);
                localLabel.setMissionID(missionid);

                localLabel.setFileName(file.getName());


                return localLabel;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }


    /**
     * 根据任务名称返回对应图片集
     *
     * @param mission
     * @return 图片集
     */
    @RequestMapping(
            value = "/mission",
            params = {"mission"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<String> getMission(@RequestParam(value = "mission") String mission) {
        return imageService.getMission(mission);
    }

    @RequestMapping(
            value = "/originmission",
            params = {"missionid"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Image> getOriginPicture(@RequestParam(value = "missionid") String missionid) {

        return imageService.getOriginPicture(missionid);
    }
    /****************************************************************************8/
     /**下面是迭代2的新增方法**/


    /**
     * 判断一个用户的已经进行的任务的准确率
     *
     * @param missionid
     * @param userId
     * @return 负数表示没有该用户没有接这个任务，0.0<= n && n<=1.0表示准确率的小数表示
     */
    @RequestMapping(value = "/accuracy", params = {"missionid", "userid"})
    @ResponseBody
    public double judgeAccuracyRate(@RequestParam(value = "missionid") String missionid, @RequestParam(value = "userid") String userId) {

        return imageService.judgeAccuracyRate(missionid, userId);
    }


    /**
     * 返回用户所接的任务以及完成度
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "/completion", params = {"userid"})
    @ResponseBody
    public List<MissionCompletion> degreeOfCompletion(@RequestParam(value = "userid") String userid) {

        return imageService.degreeOfCompletion(userid);
    }


    @RequestMapping(
            value = "/get/trainimages",

            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Image> getTargetUserTrainImages(int missionid, String username) {
        return imageService.getTargetUserTrainImages(missionid, username);
    }

    @RequestMapping(
            value = "/get/testimages",

            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<Image> getTargetUserCheckImages(int missionid, String username) {
        return imageService.getTargetUserCheckImages(missionid, username);
    }

    @RequestMapping(
            value = "/get/firstautoimage",

            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public String getFirstAutoImages(int missionid, String username) {

        return imageService.getFirstAutoImages(missionid, username);
    }


}

