package com.fx.controller;

import com.fx.bean.*;
import com.fx.model.*;
import com.fx.service.LabelService;
import com.fx.service.impl.LabelServiceImpl;
import com.fx.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by thinkpad on 2018/3/17.
 */
@Controller
@RequestMapping("/counts/label")
@CrossOrigin
public class LabelController {

    @Autowired
    LabelService labelService;


    /**
     * 整体标注
     * @param location location 图片位置  location采用 ：任务名/图片名 的格式
     * @param wholabel 标注信息
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/wholelabel",
            params = {"userid","location","wholelabel"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"})
    public OptMessage WholeLabel(@RequestParam(value="userid") String userID,@RequestParam(value = "location") String location,@RequestParam(value = "wholelabel") String wholabel){
        OptMessage result = new OptMessage(true);
        ResultMessage resultMessage = labelService.addWholeLabel(userID, location, wholabel);
        result.setMessage(resultMessage.toString());
        return result;
    }



    /**
     * 按原来画点的方式保存，暂时不需要使用，如果是使用canvas的话请使用下面的方法
     * @param userID
     * @param location
     * @param labelList
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/locallabel",
            params = {"userid","location"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}

    )
    public OptMessage LocalLabel(@RequestParam(value = "userid") String userID,@RequestParam(value = "location") String location, @RequestBody List<LocalLabelBean> labelList){
        //System.out.println("Location:"+location);
        //System.out.println("labelList"+labelList.get(0).getLabel()+"   "+labelList.get(0).getDotList().get(0).getX());
        OptMessage result = new OptMessage(true);
        ResultMessage resultMessage = labelService.LocalLabel(userID,location, labelList);
        result.setMessage(resultMessage.toString());
        return result;
    }


    /**
     * 保存base64格式的图片，注意是jpg还是jpeg
     * @param localLabel
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/savecanvas",
            method = RequestMethod.POST
    )
    public ResultMessage SaveCanvas(@RequestBody LocalLabel localLabel){
        //  @RequestParam(value = "username") String userID,@RequestParam(value = "location") String location, @RequestParam(value = "imgstr") String imgStr
        //   userID 用户id
        // 图片位置 ,格式为:"任务名/图片名.jpg"
        // imgStr base64String

       // System.out.println("123");


        ResultMessage resultMessage = labelService.SaveCanvas(localLabel);



        return resultMessage;

    }

    /**
     * 更新一张图片的整体标注
     *
     * @param userID
     * @param location
     * @param wholeLabel
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(
            value = "/update/wholelabel",
            params = {"userid","location","wholelabel"},
            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    public OptMessage updateWholeLabel(@RequestParam(value = "userid") String userID,@RequestParam(value="location") String location,@RequestParam(value = "wholelabel") String wholeLabel){

        OptMessage result = new OptMessage(false);

        ResultMessage resultMessage = labelService.updateWholeLabel(userID, location, wholeLabel);
        if(resultMessage==ResultMessage.SUCCESS){
            result.setResult(true);
        }

        result.setMessage(resultMessage.toString());
        return result;
    }


    /**
     * 根据用户id 和 location查询一张图片的整体标注
     *
     * @param userID
     * @param location
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/getlabel/wholelabel",
            params = {"userid","location"},
            method = RequestMethod.GET,
            produces =  {"application/json; charset=UTF-8"}
    )
    public String getWholeLabelByUserIDAndLocation(@RequestParam(value = "userid") String userID,@RequestParam(value = "location") String location){

        return labelService.getWholeLabelByUserIDAndLocation(userID, location);
    }

    /**
     * 获得修改后的图片和标注
     * @param userName
     * @param missionid 任务名
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/getlocallabel",
            params = {"username","missionid"},
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}

    )
    public List<LocalLabel> getLocalLabel(@RequestParam(value = "username") String userName,@RequestParam(value = "missionid") String missionid){

       // System.out.println("1233");
        //System.out.println(missionid + " ))"+userName);
        File f = new File(LabelServiceImpl.canvasdir+userName+"/"+missionid);

        File imgpar = new File("../data/image/"+missionid);
        File[] imglist = imgpar.listFiles();

        //将未保存在canvas的imgae保存在canvas
        for(int i=0;i<=imglist.length-1;i++) {
            String midstr = imglist[i].getName().split("[.]")[0];
            File midfile = new File(LabelServiceImpl.canvasdir + userName + "/" + missionid + "/" + midstr);
            if (!midfile.exists()) {
                LocalLabel localLabel = new LocalLabel();
              //  localLabel.setOverallComment("");
               // localLabel.setOtherComments(null);
                localLabel.setUserName(userName);
                localLabel.setMissionID(missionid);
               // System.out.println(imglist[i].getName());
                InputStream in = null;
                byte[] data = null;
                try {
                    in = new FileInputStream(imglist[i]);
                    data = new byte[in.available()];
                    in.read(data);
                    in.close();
                    BASE64Encoder encoder = new BASE64Encoder();


                    String head = "data:image/"+imglist[i].getName().split("[.]")[1]+";base64,";
                   // System.out.println(head);
                    //不知道这个有没有data头
                    localLabel.setUrl(head+encoder.encode(data));
                    //System.out.println("fileName"+imglist[i].getName());
                    localLabel.setFileName(imglist[i].getName());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //System.out.println(localLabel.getFileName()+" "+localLabel.getMissionID()+" "+localLabel.getUserName());
                labelService.SaveCanvas(localLabel);
            }

        }





        List<LocalLabel> list = new ArrayList<LocalLabel>();

        /*
        if(!f.exists()){
            return null;
        }
        */

        File[] dirlist = f.listFiles();

        for(int i=0;i<=dirlist.length-1;i++){

            LocalLabel l = new LocalLabel();
            //System.out.println(dirlist[i].getPath());
            File pic =new File(dirlist[i].getPath()).listFiles()[0];
           // File whole = new File(dirlist[i].getPath()+"/label/overall.txt");
           // File local = new File(dirlist[i].getPath()+"/label/other.txt");


/*
            InputStream in = null;
            byte[] data =null;
            try{
                in = new FileInputStream(img);
                data = new byte[in.available()];
                in.read(data);
                in.close();
                BASE64Encoder encoder = new BASE64Encoder();
*/

                String gp = pic.getParentFile().getParentFile().getName();
                //没设置表头

            File ori = new File("../data/image/"+missionid);
            File[] orilist = ori.listFiles();
            String type="";
            for(int j=0;j<=orilist.length-1;j++){
                if(orilist[j].getName().split("[.]")[0].equals(gp)){
                    type = orilist[j].getName().split("[.]")[1];
                }
            }



                l.setMissionID(missionid);
           // System.out.println(l.getLocation());
                l.setUserName(userName);
                /**
                 * 从文件中读取数据
                 */


                try{
                    /*
                    Scanner scanner = new Scanner(whole);
                    String wholeLabel  = "";
                    if (scanner.hasNextLine())
                     wholeLabel = scanner.nextLine();
                  //  l.setOverallComment(wholeLabel);
                    scanner.close();

                   Scanner scanner1 = new Scanner(local);
                    List<String> localLabels = new ArrayList<>();
                    while(scanner1.hasNextLine()){
                        String s = scanner1.nextLine();
                        localLabels.add(s);
                    }
                   // l.setOtherComments(localLabels);
                    scanner1.close();
                    */
                    Scanner scanner2 = new Scanner(pic);
                    StringBuilder base64 = new StringBuilder();


                    while (scanner2.hasNextLine())
                    base64.append(scanner2.nextLine());

                    l.setUrl(base64.toString());
                    String img = ImageController.imageURL+missionid;
                    File imgfa = new File(img);
                    File[] imgarr =imgfa.listFiles();

                    for(int k=0;k<=imgarr.length-1;k++){
                        String name = imgarr[k].getName().split("[.]")[0];
                        if(name.equals(dirlist[i].getName())){
                            l.setFileName(imgarr[k].getName());
                        }
                    }
                    //System.out.println(base64);
                    list.add(l);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

       // System.out.println(list.get(0).getLocation());
        return list;
    }


    /**
     * 添加属性标注
     * @param
     * @param attributeLabel
     * @return
     */
    @RequestMapping(
            value = "/add/attributelabel",

            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addAttributeLabel(@RequestBody AttributeLabelBean attributeLabel) {

        //System.out.println(attributeLabel.getAttributeLabel().getFileName());
        ResultMessage resultMessage = labelService.addAttributeLabel(attributeLabel.getMissionID(),attributeLabel.getUserName(), attributeLabel.getAttributeLabel());

        return resultMessage;

    }

    /**
     * 添加整体标注
     * @param
     * @param captionLabel
     * @return
     */
    @RequestMapping(
            value = "/add/captionlabel",

            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addCaptionLabel(@RequestBody CaptionLabelBean captionLabel) {

        //System.out.println("9999");
        ResultMessage resultMessage = labelService.addCaptionLabel(captionLabel.getMissionID(),captionLabel.getUserName(), captionLabel.getCaptionLabel());

        return resultMessage;

    }

    /**
     * 添加分类标注
     * @param
     * @param classificationLabel
     * @return
     */
    @RequestMapping(
            value = "/add/classificationlabel",

            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addClassificationLabel(@RequestBody ClassificationLabelBean classificationLabel) {

        ResultMessage resultMessage = labelService.addClassificationLabel(classificationLabel.getMissionID(),classificationLabel.getUserName(),classificationLabel.getClassificationLabel());

        return resultMessage;

    }

    /**
     * 添加矩形标注
     * @param userName
     * @param detectionLabel
     * @return
     */
    @RequestMapping(
            value = "/add/detectionlabel",
            params = {"missionid","username","detectionlabel"},
            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addDetectionLabel(@RequestParam(value = "missionid") int missionID, @RequestParam(value = "username") String userName, @RequestParam(value = "detectionlabel") @RequestBody DetectionLabel detectionLabel) {


        ResultMessage resultMessage = labelService.addDetectionLabel(missionID,userName, detectionLabel);

        return resultMessage;

    }

    /**
     * 添加轮廓（描边）标注
     * @param userName
     * @param segmentationLabel
     * @return
     */
    @RequestMapping(
            value = "/add/segmentationlabel",
            params = {"missionid","username","segmentationlabel"},
            method = RequestMethod.POST,
            produces =  {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addSegmentationLabel(@RequestParam(value = "missionid") int missionID, @RequestParam(value = "username") String userName, @RequestBody @RequestParam(value = "segmentationlabel") SegmentationLabel segmentationLabel) {
        ResultMessage resultMessage = labelService.addSegmentationLabel(missionID,userName, segmentationLabel);

        return resultMessage;
    }


    @RequestMapping(
            value = "/get/attributelabel",
            params = {"missionid","userid"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AttributeLabel> getAttributeLabel(@RequestParam(value = "missionid") int missionID,@RequestParam(value = "userid") String usernid){

        return labelService.getAttributeLabel(missionID, usernid);
    }

    @RequestMapping(
            value = "/get/captionlabel",
            params = {"missionid","userid"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<CaptionLabel> getCaptionLabel(@RequestParam(value = "missionid") int missionID,@RequestParam(value = "userid") String usernid){

        return labelService.getCaptionLabel(missionID, usernid);
    }

    @RequestMapping(
            value = "/get/classificationlabel",
            params = {"missionid","userid"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<ClassificationLabel> getClassificationLabel(@RequestParam(value = "missionid") int missionID,@RequestParam(value = "userid") String usernid){

        return labelService.getClassificationLabel(missionID, usernid);
    }

    @RequestMapping(
            value = "/get/detectionlabel",
            params = {"missionid","userid"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<DetectionLabel> getDetectionLabel(@RequestParam(value = "missionid") int missionID,@RequestParam(value = "userid") String usernid){

        return labelService.getDetectionLabel(missionID, usernid);
    }

    @RequestMapping(
            value = "/get/segmentationlabel",
            params = {"missionid","userid"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<SegmentationLabel> getSegmentationLabel(@RequestParam(value = "missionid") int missionID,@RequestParam(value = "userid") String usernid){

        return labelService.getSegmentationLabel(missionID, usernid);
    }

    @RequestMapping(
            value = "/auto/add/classificationlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addAutoClassificationLabel(@RequestBody AutoClassificationLabelBean autoClassificationLabelBean){
        return labelService.addAutoClassificationLabel(autoClassificationLabelBean);

    }

    @RequestMapping(
            value = "/auto/add/captionlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addAutoCaptionLabel(@RequestBody AutoCaptionLabelBean autoCaptionLabelBean){
        System.out.println("234");
        return  labelService.addAutoCaptionLabel(autoCaptionLabelBean);

    }

    @RequestMapping(
            value = "/auto/add/detectionlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public ResultMessage addAutoDetectionLabel(@RequestBody AutoDetectionLabelBean autoDetectionLabelBean){
        System.out.println(autoDetectionLabelBean==null);
        return labelService.addAutoDetectionLabel(autoDetectionLabelBean);

    }


    @RequestMapping(
            value = "/auto/update/classificationlabel",
            params = {"autoClassificationLabelBean"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage updateAutoClassificationLabel(@RequestParam(value = "autoClassificationLabelBean") AutoClassificationLabelBean autoClassificationLabelBean){
        ResultMessage resultMessage = labelService.updateAutoClassificationLabel(autoClassificationLabelBean);
        OptMessage result = new OptMessage(false);

        if(ResultMessage.SUCCESS == resultMessage){
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    @RequestMapping(
            value = "/auto/update/captionlabel",
            params = {"autoCaptionLabelBean"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage updateAutoCaptionLabel(@RequestParam(value="autoCaptionLabelBean") AutoCaptionLabelBean autoCaptionLabelBean){
        ResultMessage resultMessage = labelService.updateAutoCaptionLabel(autoCaptionLabelBean);
        OptMessage result = new OptMessage(false);

        if(ResultMessage.SUCCESS == resultMessage){
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    @RequestMapping(
            value = "/auto/update/detectionlabel",
            params = {"autoDetectionLabelBean"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public OptMessage updateAutoDetectionLabel(@RequestParam(value="autoDetectionLabelBean") AutoDetectionLabelBean autoDetectionLabelBean){

        ResultMessage resultMessage = labelService.updateAutoDetectionLabel(autoDetectionLabelBean);
        OptMessage result = new OptMessage(false);

        if(ResultMessage.SUCCESS == resultMessage){
            result.setResult(true);
        }
        result.setMessage(resultMessage.toString());
        return result;
    }

    @RequestMapping(
            value = "/get/autocaptionlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AutoCaptionLabel> getAutoCaptionLabel(String missionid,String username){
        System.out.println( labelService.getAutoCaptionLabel(missionid, username));
        return labelService.getAutoCaptionLabel(missionid, username);
    }

    @RequestMapping(
            value = "/get/autoclassificationlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AutoClassificationLabel> getAutoClassificationLabel(String missionid,String username){

        return labelService.getAutoClassificationLabel(missionid, username);
    }

    @RequestMapping(
            value = "/get/autodetectionlabel",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<AutoDetectionLabel> getAutoDetectionLabel(String missionid,String username){

        return labelService.getAutoDetectionLabel(missionid,username);
    }






}
