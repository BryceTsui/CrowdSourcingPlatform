package com.fx.repository.impl;

import com.fx.model.DetectionLabel;
import com.fx.repository.DetectionLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 10:45 2018/4/10/010
 */
public class DetectionLabelRepositoryImpl implements DetectionLabelRepository {
    RepositoryHelper<DetectionLabel> helper;

    public DetectionLabelRepositoryImpl() {
        helper = new RepositoryHelper<>(DetectionLabel.class);
    }

    /**
     * @param missionID
     * @param username
     * @param detectionLabel
     * @return
     */
    @Override
    public ResultMessage addDetectionLabel(int missionID, String username, DetectionLabel detectionLabel) {
        boolean isExisted = false;
        List<DetectionLabel> labels = helper.getAllLabels(missionID, username);
        for (DetectionLabel l : labels) {
            if (l.getFileName().equals(detectionLabel.getFileName())) {
                l.setRectangles(detectionLabel.getRectangles());
                helper.printAllLabels(missionID, username, labels);
                return ResultMessage.EXIST;
            }
        }
            labels.add(detectionLabel);

        helper.printAllLabels(missionID, username, labels);
        return ResultMessage.SUCCESS;

    }

    /**
     * @param missionID
     * @param username
     * @param detectionLabel
     * @return
     */
    @Override
    public ResultMessage updateDetectionLabel(int missionID, String username, DetectionLabel detectionLabel) {

        List<DetectionLabel> labels = helper.getAllLabels(missionID, username);
        for (DetectionLabel l : labels) {
            if (l.getFileName().equals(detectionLabel.getFileName())) {
                l.setRectangles(detectionLabel.getRectangles());
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
    public ResultMessage deleteDetectionLabel(int missionID, String username, String fileName) {
        List<DetectionLabel> labels = helper.getAllLabels(missionID, username);
        for (DetectionLabel l : labels) {
            if (l.getFileName().equals(fileName)) {
                labels.remove(l);
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
    public List<DetectionLabel> findDetectionLabelByMissionIDAndUsername(int missionID, String username) {
        return helper.getAllLabels(missionID, username);
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public DetectionLabel findDetectionLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName) {
        List<DetectionLabel> labels = helper.getAllLabels(missionID, username);
        DetectionLabel detectionLabel = null;
        for (DetectionLabel la : labels) {
            if (la.getFileName().equals(fileName))
                detectionLabel = la;
        }
        return detectionLabel;
    }
}
