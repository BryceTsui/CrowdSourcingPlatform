package com.fx.repository;

import com.fx.model.AutoClassificationLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:29 2018/6/12/012
 */
public interface AutoClassificationLabelRepository {

    public ResultMessage addAutoClassificationLabel(int autoMissionID, AutoClassificationLabel autoClassificationLabel);

    public ResultMessage updateAutoClassificationLabel(int autoMissionID, AutoClassificationLabel autoClassificationLabel);

    public List<AutoClassificationLabel> findAutoClassificationLabel(int autoMissionID);

    public AutoClassificationLabel findAutoClassificationLabelByMissionIDandFilename(int missionID,String filename);
}
