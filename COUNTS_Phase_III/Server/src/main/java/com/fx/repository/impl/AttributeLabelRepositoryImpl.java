package com.fx.repository.impl;

import com.fx.model.AttributeLabel;
import com.fx.repository.AttributeLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 10:43 2018/4/10/010
 */
public class AttributeLabelRepositoryImpl implements AttributeLabelRepository {
    RepositoryHelper<AttributeLabel> helper;

    public AttributeLabelRepositoryImpl() {
        helper = new RepositoryHelper<>(AttributeLabel.class);
    }

    /**
     * 添加一个属性标注
     *
     * @param missionID
     * @param username
     * @param attributeLabel
     * @return
     */
    @Override
    public ResultMessage addAttributeLabel(int missionID, String username, AttributeLabel attributeLabel) {
        List<AttributeLabel> labels = helper.getAllLabels(missionID, username);
        boolean exist = false;
        for (int i = 0; i < labels.size() ; i++) {
            AttributeLabel label = labels.get(i);
            if (label.getFileName().equals(attributeLabel.getFileName())) {
                label.setAttributes(attributeLabel.getAttributes());
                helper.printAllLabels(missionID,username,labels);
                return ResultMessage.EXIST;
            }
        }
            labels.add(attributeLabel);

        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * 更新属性标注
     *
     * @param missionID
     * @param username
     * @param attributeLabel
     * @return
     */
    @Override
    public ResultMessage updateAttributeLabel(int missionID, String username, AttributeLabel attributeLabel) {
        List<AttributeLabel> labels = helper.getAllLabels(missionID, username);
        boolean exist = false;
        for (AttributeLabel label :
                labels) {
            if (label.getFileName().equals(attributeLabel.getFileName())) {
                exist = true;
                label.setAttributes(attributeLabel.getAttributes());
                break;
            }
        }
        if (!exist)
            return ResultMessage.FAILED;
        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * 删除属性标注
     *
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public ResultMessage deleteAttributeLabel(int missionID, String username, String fileName) {
        List<AttributeLabel> labels = helper.getAllLabels(missionID, username);
        boolean success = false;
        for (AttributeLabel label :
                labels) {
            if (label.getFileName().equals(fileName)) {
                labels.remove(label);
                success = true;

            }
        }
        if (success) {
            helper.printAllLabels(missionID, username, labels);
            return ResultMessage.SUCCESS;
        } else
            return ResultMessage.NOT_EXIST;
    }

    /**
     * 根据任务id 用户的username 返回所有的标注结果
     *
     * @param missionID
     * @param username
     * @return
     */
    @Override
    public List<AttributeLabel> findAttributeLabelByMissionIDAndUsername(int missionID, String username) {
        return helper.getAllLabels(missionID, username);
    }

    /**
     * 根据任务id 用户名username 标注的文件名 返回特定的标注结果
     *
     * @param missionID
     * @param username
     * @param filename
     * @return
     */
    @Override
    public AttributeLabel findAttributeLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String filename) {
        List<AttributeLabel> attributeLabels = helper.getAllLabels(missionID, username);
        AttributeLabel attributeLabel = null;
        for (AttributeLabel a : attributeLabels
                ) {
            if (a.getFileName().equals(filename))
                attributeLabel = a;
        }
        return attributeLabel;
    }


}
