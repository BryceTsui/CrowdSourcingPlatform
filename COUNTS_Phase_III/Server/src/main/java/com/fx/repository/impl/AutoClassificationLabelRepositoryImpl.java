package com.fx.repository.impl;

import com.fx.model.AutoClassificationLabel;
import com.fx.repository.AutoClassificationLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:49 2018/6/12/012
 */
public class AutoClassificationLabelRepositoryImpl implements AutoClassificationLabelRepository {
    AutoLabelRepositoryHelper helper = new AutoLabelRepositoryHelper(AutoClassificationLabel.class);

    @Override
    public ResultMessage addAutoClassificationLabel(int autoMissionID, AutoClassificationLabel autoClassificationLabel) {
        return helper.addAutoLabel(autoMissionID, autoClassificationLabel);
    }

    @Override
    public ResultMessage updateAutoClassificationLabel(int autoMissionID, AutoClassificationLabel autoClassificationLabel) {
        return helper.updateAutoLabel(autoMissionID, autoClassificationLabel);

    }

    @Override
    public List<AutoClassificationLabel> findAutoClassificationLabel(int autoMissionID) {
        return helper.findAutoLabelByMissionID(autoMissionID);
    }

    @Override
    public AutoClassificationLabel findAutoClassificationLabelByMissionIDandFilename(int missionID, String filename) {
        return(AutoClassificationLabel) helper.findAutoLabelByMissionIDandFilename(missionID,filename);
    }
}
