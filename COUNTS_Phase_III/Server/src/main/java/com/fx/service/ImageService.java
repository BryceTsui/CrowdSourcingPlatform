package com.fx.service;

import com.fx.bean.Image;
import com.fx.bean.MissionCompletion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/17.
 */
public interface ImageService {

    /**
     * 根据任务名称返回对应图片集
     * @param mission
     * @return 图片集
     */
    public List<String> getMission(String mission);

    /**
     * 上传文件的似有方法，前段无需使用
     * @param file：文件的字节
     * @param imgPath：文件的路径
     * @param imgName：文件的名字
     * @throws Exception
     */
    public void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception;


    public List<Image> getOriginPicture(String missionid);
    /**************下面为迭代2新增方法**********/

    /**
     * 判断一个用户的已经进行的任务的准确率
     *
     * @param missionname
     * @param userId
     * @return 负数表示没有该用户没有接这个任务，0.0<= n && n>=1.0表示准确率的小数表示
     */
    public double judgeAccuracyRate(String missionname,String userId);


    /**
     * 返回用户所接的任务以及完成度
     *
     * @param userid
     * @return
     */
    public List<MissionCompletion> degreeOfCompletion( String userid);


    public List<Image> getTargetUserTrainImages(int missionid, String username);

    public List<Image> getTargetUserCheckImages(int missionid, String username);

    public String getFirstAutoImages(int missionid, String username);

}
