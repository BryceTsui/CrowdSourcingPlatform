package com.fx.repository;

import com.fx.util.ResultMessage;

/**
 * Description:
 * Created by Hanxinhu at 9:24 2018/3/21/021
 */
public interface WholeLabelRepository {
    /**
     * @param userID
     * @param missionName
     * @param fileName
     * @param wholeLabel
     * @return
     */
    public ResultMessage addWholeLabel(String userID, String missionName, String fileName, String wholeLabel);

    /**
     * @param userID
     * @param missionName
     * @param fileName
     * @param wholeLabel
     * @return
     */
    public ResultMessage updateWholeLabel(String userID, String missionName, String fileName, String wholeLabel);

    public String getWholeLabelByUserIDAndLocation(String userID, String missionName, String fileName);
}
