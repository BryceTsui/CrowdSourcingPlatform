package com.fx.repository.impl;

import com.fx.model.SegmentationLabel;
import com.fx.repository.SegmentationLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 10:47 2018/4/10/010
 */
public class SegmentationLabelRepositoryImpl implements SegmentationLabelRepository {
    RepositoryHelper<SegmentationLabel> helper;

    public SegmentationLabelRepositoryImpl() {
        helper = new RepositoryHelper<>(SegmentationLabel.class);
    }

    /**
     * @param missionID
     * @param username
     * @param segmentationLabel
     * @return
     */
    @Override
    public ResultMessage addSegmentationLabel(int missionID, String username, SegmentationLabel segmentationLabel) {
        List<SegmentationLabel> labels = helper.getAllLabels(missionID, username);
        for (int i = 0; i < labels.size(); i++) {
            SegmentationLabel s = labels.get(i);
            if (s.getFileName().equals(segmentationLabel.getFileName())){
                s.setDots(segmentationLabel.getDots());
                helper.printAllLabels(missionID,username,labels);
                return ResultMessage.EXIST;
            }
        }
            labels.add(segmentationLabel);
        
        helper.printAllLabels(missionID, username, labels);
        return ResultMessage.SUCCESS;
    }

    /**
     * @param missionID
     * @param username
     * @param segmentationLabel
     * @return
     */
    @Override
    public ResultMessage updateSegmentationLabel(int missionID, String username, SegmentationLabel segmentationLabel) {
        List<SegmentationLabel> labels = helper.getAllLabels(missionID, username);
        for (SegmentationLabel label : labels
                ) {
            if (label.getFileName().equals(segmentationLabel.getFileName())) {
                label.setDots(segmentationLabel.getDots());
                helper.printAllLabels(missionID, username, labels);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public ResultMessage deleteSegmentationLabel(int missionID, String username, String fileName) {
        List<SegmentationLabel> labels = helper.getAllLabels(missionID, username);
        for (SegmentationLabel label : labels
                ) {
            if (label.getFileName().equals(fileName)) {
                labels.remove(label);
                helper.printAllLabels(missionID, username, labels);
                return ResultMessage.SUCCESS;
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    /**
     * @param missionID
     * @param username
     * @return
     */
    @Override
    public List<SegmentationLabel> findSegmentationLabelByMissionIDAndUsername(int missionID, String username) {
        return helper.getAllLabels(missionID, username);
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public SegmentationLabel findSegmentationLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName) {
        List<SegmentationLabel> labels = helper.getAllLabels(missionID, username);
        SegmentationLabel label = null;
        for (SegmentationLabel l : labels
                ) {
            if (l.getFileName().equals(fileName))
                label = l;
        }
        return label;
    }
}
