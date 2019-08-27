package com.fx.repository;

import com.fx.model.ClassificationLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 7:44 2018/4/10/010
 */
public interface ClassificationLabelRepository {
    /**
     * 添加分类标注 分类
     *
     * @param missionID
     * @param username
     * @param classificationLabel
     * @return
     */
    public ResultMessage addClassificationLabel(int missionID, String username, ClassificationLabel classificationLabel);

    /**
     * @param missionID
     * @param username
     * @param classificationLabel
     * @return
     */
    public ResultMessage updateClassificationLabel(int missionID, String username, ClassificationLabel classificationLabel);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public ResultMessage deleteClassificationLabel(int missionID, String username, String fileName);

    /**
     * @param missionID
     * @param username
     * @return
     */
    public List<ClassificationLabel> findClassificationLabelByMissionIDAndUsername(int missionID, String username);

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public ClassificationLabel findClassificationLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName);

}
