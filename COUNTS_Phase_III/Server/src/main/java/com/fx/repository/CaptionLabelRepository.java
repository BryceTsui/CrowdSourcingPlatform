package com.fx.repository;

import com.fx.model.CaptionLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 7:42 2018/4/10/010
 */
public interface CaptionLabelRepository {
    /**
     * 添加整体标注  描述图片内容
     *
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    public ResultMessage addCaptionLabel(int missionID, String username, CaptionLabel captionLabel);

    /**
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    public ResultMessage updateCaptionLabel(int missionID, String username, CaptionLabel captionLabel);

    /**
     * @param missionID
     * @param username
     * @param captionLabel
     * @return
     */
    public ResultMessage deleteCaptionLabel(int missionID, String username, CaptionLabel captionLabel);

    /**
     * @param missionID
     * @param username
     * @return
     */
    public List<CaptionLabel> findCaptionLabelByMissionIDAndUsername(int missionID, String username);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public CaptionLabel findCaptionLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName);

}
