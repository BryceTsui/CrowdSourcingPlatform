package com.fx.repository.impl;

import com.fx.model.AutoDetectionLabel;
import com.fx.repository.AutoDetectionLabelReposity;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:48 2018/6/12/012
 */
public class AutoDetectionLabelRepositoryImpl implements AutoDetectionLabelReposity {
    AutoLabelRepositoryHelper helper = new AutoLabelRepositoryHelper(AutoDetectionLabel.class);
    @Override
    public ResultMessage addAutoDetectionLabel(int missionID, AutoDetectionLabel autoDetectionLabel) {
        return helper.addAutoLabel(missionID,autoDetectionLabel);
    }

    @Override
    public ResultMessage updateAutoDetectionLabel(int missionID, AutoDetectionLabel autoDetectionLabel) {
        return helper.updateAutoLabel(missionID,autoDetectionLabel);
    }

    @Override
    public List<AutoDetectionLabel> findAutoDetectionLabel(int missionID) {
        return helper.findAutoLabelByMissionID(missionID);
    }

    @Override
    public AutoDetectionLabel findAutoDetectionLabelBymissionIDAndfilename(int missionID, String filename) {
        return(AutoDetectionLabel) helper.findAutoLabelByMissionIDandFilename(missionID,filename);
    }
}
