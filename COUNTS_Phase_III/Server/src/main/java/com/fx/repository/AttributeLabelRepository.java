package com.fx.repository;

import com.fx.model.AttributeLabel;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 7:42 2018/4/10/010
 */
public interface AttributeLabelRepository {
    /**
     * 添加一个属性标注
     *
     * @param missionID
     * @param username
     * @param attributeLabel
     * @return
     */
    public ResultMessage addAttributeLabel(int missionID, String username, AttributeLabel attributeLabel);

    /**
     * 更新属性标注
     *
     * @param missionID
     * @param username
     * @param attributeLabel
     * @return
     */
    public ResultMessage updateAttributeLabel(int missionID, String username, AttributeLabel attributeLabel);

    /**
     * 删除属性标注
     *
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    public ResultMessage deleteAttributeLabel(int missionID, String username, String fileName);

    /**
     * 根据任务id 用户的username 返回所有的标注结果
     *
     * @param missionID
     * @param username
     * @return
     */
    public List<AttributeLabel> findAttributeLabelByMissionIDAndUsername(int missionID, String username);

    /**
     * 根据任务id 用户名username 标注的文件名 返回特定的标注结果
     *
     * @param missionID
     * @param username
     * @param filename
     * @return
     */
    public AttributeLabel findAttributeLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String filename);
}
