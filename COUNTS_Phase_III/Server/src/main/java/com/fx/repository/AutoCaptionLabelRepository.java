package com.fx.repository;

import com.fx.model.AutoCaptionLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:29 2018/6/12/012
 */
public interface AutoCaptionLabelRepository {
    public ResultMessage addAutoCaptionLabel(int autoMissionID, AutoCaptionLabel autoCaptionLabel);

    public ResultMessage updateAutoCaptionLabel(int autoMissionID, AutoCaptionLabel autoCaptionLabel);

    public List<AutoCaptionLabel> findAutoCaptionLabel(int autoMissionID);

    public AutoCaptionLabel findAutoCaptionLabelByMissionIDandFilename(int missionID,String filename);
}
