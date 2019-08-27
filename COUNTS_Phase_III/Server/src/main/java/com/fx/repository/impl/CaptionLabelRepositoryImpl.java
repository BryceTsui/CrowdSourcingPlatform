package com.fx.repository.impl;

import com.fx.model.CaptionLabel;
import com.fx.repository.CaptionLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 10:44 2018/4/10/010
 */
public class CaptionLabelRepositoryImpl implements CaptionLabelRepository {
    RepositoryHelper<CaptionLabel> helper;

    public CaptionLabelRepositoryImpl() {
        helper = new RepositoryHelper<>(CaptionLabel.class);
    }

    /**
     * 添加整体标注  描述图片内容
     *
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    @Override
    public ResultMessage addCaptionLabel(int missionID, String username, CaptionLabel captionLabel) {
        List<CaptionLabel> labels = helper.getAllLabels(missionID, username);
        for (int i = 0; i < labels.size(); i++) {
            CaptionLabel c = labels.get(i);
            if (c.getFileName().equals(captionLabel.getFileName())) {
                c.setCaption(captionLabel.getCaption());
                helper.printAllLabels(missionID,username,labels);
                return ResultMessage.EXIST;
            }
        }
            labels.add(captionLabel);


        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    @Override
    public ResultMessage updateCaptionLabel(int missionID, String username, CaptionLabel captionLabel) {
        List<CaptionLabel> labels = helper.getAllLabels(missionID, username);
        for (CaptionLabel c : labels
                ) {
            if (c.getFileName().equals(captionLabel.getFileName()))
                c.setCaption(captionLabel.getCaption());
        }
        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    @Override
    public ResultMessage deleteCaptionLabel(int missionID, String username, CaptionLabel captionLabel) {
        List<CaptionLabel> labels = helper.getAllLabels(missionID, username);
        for (CaptionLabel c : labels
                ) {
            if (c.getFileName().equals(captionLabel.getFileName()))
                labels.remove(c);
        }
        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * @param missionID
     * @param username
     * @return
     */
    @Override
    public List<CaptionLabel> findCaptionLabelByMissionIDAndUsername(int missionID, String username) {
        return helper.getAllLabels(missionID, username);
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public CaptionLabel findCaptionLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName) {
        List<CaptionLabel> labels = helper.getAllLabels(missionID, username);
        CaptionLabel caption = null;
        for (CaptionLabel c : labels
                ) {
            if (c.getFileName().equals(fileName))
                caption = c;
        }
        return caption;
    }


}
