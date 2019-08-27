package com.fx.service;

import com.fx.bean.*;
import com.fx.model.*;
import com.fx.util.ResultMessage;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by thinkpad on 2018/3/17.
 */
public interface LabelService {
    /**
     * 对一张图片整体标注
     * @param userID 用户ID location 图片位置 wholeLabel 图片标注 location采用 ：任务名/图片名 的格式
     * @return 结果
     */
    public ResultMessage addWholeLabel(String userID, String location, String wholeLabel);

    /**
     * 更新一张图片的整体标注
     *
     * @param userID
     * @param location
     * @param wholeLabel
     * @return 是否成功
     */
    public ResultMessage updateWholeLabel(String userID, String location, String wholeLabel);

    /**
     * 根据用户id 和 location查询一张图片的标注
     *
     * @param userID
     * @param location
     * @return
     */
    public String getWholeLabelByUserIDAndLocation(String userID, String location);

    /**
     * 添加局部标注
     * @param userID 用户ID location 图片位置 采用任务名/图片名 的格式
     * @param labelList bean列表
     * @return
     */
    public ResultMessage LocalLabel(String userID,String location, List<LocalLabelBean> labelList);



    /**
     * 保存base64格式的图片
     * @param localLabel
     * @return
     */
    public ResultMessage SaveCanvas(LocalLabel localLabel);


    /**************************************/

    /**
     * 添加属性标注
     * @param userName
     * @param attributeLabel
     * @return
     */
    public ResultMessage addAttributeLabel(int missionID,String userName,  AttributeLabel attributeLabel);

    /**
     * 添加整体标注
     * @param userName
     * @param captionLabel
     * @return
     */
    public ResultMessage addCaptionLabel(int missionID,String userName,  CaptionLabel captionLabel);

    /**
     * 添加分类标注
     * @param userName
     * @param classificationLabel
     * @return
     */
    public ResultMessage addClassificationLabel(int missionID,String userName,  ClassificationLabel classificationLabel);


    /**
     * 添加矩形标注
     * @param userName
     * @param detectionLabel
     * @return
     */
    public ResultMessage addDetectionLabel(int missionID,String userName,  DetectionLabel detectionLabel);

    /**
     * 添加描边轮廓标注
     * @param userName
     * @param segmentationLabel
     * @return
     */
    public ResultMessage addSegmentationLabel(int missionID,String userName,SegmentationLabel segmentationLabel);


    /**
     * 查找属性标注
     * @param missionID
     * @param userName
     * @return
     */
    public List<AttributeLabel> getAttributeLabel(int missionID,String userName);

    /**
     * 添加整体标注
     * @param userName
     * @param missionID
     * @return
     */
    public List<CaptionLabel> getCaptionLabel(int missionID,String userName);

    /**
     * 添加分类标注
     * @param userName
     * @param missionID
     * @return
     */
    public List<ClassificationLabel> getClassificationLabel(int missionID,String userName);


    /**
     * 添加矩形标注
     * @param userName
     * @param missionID
     * @return
     */
    public List<DetectionLabel> getDetectionLabel(int missionID,String userName);

    /**
     * 添加描边轮廓标注
     * @param userName
     * @param missionID
     * @return
     */
    public List<SegmentationLabel> getSegmentationLabel(int missionID,String userName);
    public ResultMessage addOne(int missionID,String userName,String FileName);



    public ResultMessage addAutoClassificationLabel(AutoClassificationLabelBean autoClassificationLabelBean);


    public ResultMessage addAutoCaptionLabel( AutoCaptionLabelBean autoCaptionLabelBean);


    public ResultMessage addAutoDetectionLabel( AutoDetectionLabelBean autoDetectionLabelBean);

    public ResultMessage updateAutoClassificationLabel(AutoClassificationLabelBean autoClassificationLabelBean);


    public ResultMessage updateAutoCaptionLabel( AutoCaptionLabelBean autoCaptionLabelBean);


    public ResultMessage updateAutoDetectionLabel( AutoDetectionLabelBean autoDetectionLabelBean);

    public List<AutoCaptionLabel> getAutoCaptionLabel(String missionid,String username);
    public List<AutoClassificationLabel> getAutoClassificationLabel(String missionid,String username);

    public List<AutoDetectionLabel> getAutoDetectionLabel(String missionid,String username);
}
