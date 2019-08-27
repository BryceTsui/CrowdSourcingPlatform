package com.fx.repository.impl;

import com.fx.model.AutoCaptionLabel;
import com.fx.repository.AutoCaptionLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:46 2018/6/12/012
 */
public class AutoCaptionLabelRepositoryImpl implements AutoCaptionLabelRepository {
    AutoLabelRepositoryHelper helper = new AutoLabelRepositoryHelper(AutoCaptionLabel.class);
    @Override
    public ResultMessage addAutoCaptionLabel(int autoMissionID, AutoCaptionLabel autoCaptionLabel) {
        return helper.addAutoLabel(autoMissionID,autoCaptionLabel);
    }

    @Override
    public ResultMessage updateAutoCaptionLabel(int autoMissionID, AutoCaptionLabel autoCaptionLabel) {
        return helper.updateAutoLabel(autoMissionID,autoCaptionLabel);
    }

    @Override
    public List<AutoCaptionLabel> findAutoCaptionLabel(int autoMissionID) {
        return helper.findAutoLabelByMissionID(autoMissionID);
    }

    @Override
    public AutoCaptionLabel findAutoCaptionLabelByMissionIDandFilename(int missionID, String filename) {
        return (AutoCaptionLabel)helper.findAutoLabelByMissionIDandFilename(missionID,filename);
    }
}
