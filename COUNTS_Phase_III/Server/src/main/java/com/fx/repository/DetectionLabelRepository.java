package com.fx.repository;

import com.fx.model.DetectionLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 7:45 2018/4/10/010
 */
public interface DetectionLabelRepository {
    /**
     * @param missionID
     * @param username
     * @param detectionLabel
     * @return
     */
    public ResultMessage addDetectionLabel(int missionID, String username, DetectionLabel detectionLabel);

    /**
     * @param missionID
     * @param username
     * @param detectionLabel
     * @return
     */
    public ResultMessage updateDetectionLabel(int missionID, String username, DetectionLabel detectionLabel);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public ResultMessage deleteDetectionLabel(int missionID, String username, String fileName);

    /**
     * @param missionID
     * @param username
     * @return
     */
    public List<DetectionLabel> findDetectionLabelByMissionIDAndUsername(int missionID, String username);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public DetectionLabel findDetectionLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName);
}
