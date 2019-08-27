package com.fx.repository;

import com.fx.model.SegmentationLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 7:48 2018/4/10/010
 */
public interface SegmentationLabelRepository {
    /**
     * @param missionID
     * @param username
     * @param segmentationLabel
     * @return
     */
    public ResultMessage addSegmentationLabel(int missionID, String username, SegmentationLabel segmentationLabel);

    /**
     * @param missionID
     * @param username
     * @param segmentationLabel
     * @return
     */
    public ResultMessage updateSegmentationLabel(int missionID, String username, SegmentationLabel segmentationLabel);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public ResultMessage deleteSegmentationLabel(int missionID, String username, String fileName);

    /**
     * @param missionID
     * @param username
     * @return
     */
    public List<SegmentationLabel> findSegmentationLabelByMissionIDAndUsername(int missionID, String username);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public SegmentationLabel findSegmentationLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName);

}
