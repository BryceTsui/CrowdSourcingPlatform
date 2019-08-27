package com.fx.repository.impl;

import com.fx.model.ClassificationLabel;
import com.fx.repository.ClassificationLabelRepository;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 10:44 2018/4/10/010
 */
public class ClassificationLabelRepositoryImpl implements ClassificationLabelRepository {
    RepositoryHelper<ClassificationLabel> helper;

    public ClassificationLabelRepositoryImpl() {
        helper = new RepositoryHelper<>(ClassificationLabel.class);
    }

    /**
     * 添加分类标注 分类
     *
     * @param missionID
     * @param username
     * @param classificationLabel
     * @return
     */
    @Override
    public ResultMessage addClassificationLabel(int missionID, String username, ClassificationLabel classificationLabel) {
        List<ClassificationLabel> labels = helper.getAllLabels(missionID, username);
        for (int i = 0; i < labels.size(); i++) {
            ClassificationLabel c = labels.get(i);
            if (c.getFileName().equals(classificationLabel.getFileName())) {
                c.setSelect(classificationLabel.getSelect());
                helper.printAllLabels(missionID, username, labels);
                return ResultMessage.EXIST;
            }

        }
            labels.add(classificationLabel);

        boolean success = helper.printAllLabels(missionID, username, labels);
        if (success)
            return ResultMessage.SUCCESS;
        else
            return ResultMessage.FAILED;
    }

    /**
     * @param missionID
     * @param username
     * @param classificationLabel
     * @return
     */
    @Override
    public ResultMessage updateClassificationLabel(int missionID, String username, ClassificationLabel classificationLabel) {
        List<ClassificationLabel> labels = helper.getAllLabels(missionID, username);
        for (int i = 0; i < labels.size(); i++) {
            if (classificationLabel.getFileName().equals(labels.get(i).getFileName())) {
                labels.get(i).setSelect(classificationLabel.getSelect());
            }
        }
        helper.printAllLabels(missionID, username, labels);
        return ResultMessage.SUCCESS;
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public ResultMessage deleteClassificationLabel(int missionID, String username, String fileName) {
        List<ClassificationLabel> labels = helper.getAllLabels(missionID, username);
        for (ClassificationLabel l : labels) {
            if (l.getFileName().equals(fileName))
                labels.remove(l);
        }
        return ResultMessage.SUCCESS;
    }

    /**
     * @param missionID
     * @param username
     * @return
     */
    @Override
    public List<ClassificationLabel> findClassificationLabelByMissionIDAndUsername(int missionID, String username) {

        return helper.getAllLabels(missionID, username);
    }

    /**
     * @param missionID
     * @param username
     * @param fileName
     * @return
     */
    @Override
    public ClassificationLabel findClassificationLabelByMissionIDAndUsernameAndFileName(int missionID, String username, String fileName) {
        List<ClassificationLabel> labels = helper.getAllLabels(missionID, username);
        ClassificationLabel label = null;
        for (ClassificationLabel c : labels
                ) {
            if (c.getFileName().equals(fileName))
                label = c;
        }
        return label;
    }
}
