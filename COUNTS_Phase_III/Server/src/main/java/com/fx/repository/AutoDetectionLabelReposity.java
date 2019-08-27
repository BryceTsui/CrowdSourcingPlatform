package com.fx.repository;

import com.fx.model.AutoDetectionLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 19:30 2018/6/12/012
 */
public interface AutoDetectionLabelReposity {

    public ResultMessage addAutoDetectionLabel(int missionID, AutoDetectionLabel autoDetectionLabel);

    public ResultMessage updateAutoDetectionLabel(int missionID, AutoDetectionLabel autoDetectionLabel);

    public List<AutoDetectionLabel> findAutoDetectionLabel(int missionID);

    public AutoDetectionLabel findAutoDetectionLabelBymissionIDAndfilename(int missionID,String filename);
}
