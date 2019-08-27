package com.fx.repository;

import com.fx.bean.LocalLabelBean;
import com.fx.util.ResultMessage;

import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 9:25 2018/3/21/021
 */
public interface LocalLabelRepository {
    /**
     * 添加局部标记
     *
     * @param userID
     * @param missionName
     * @param fileName
     * @param labelList
     */
    public ResultMessage addLocalLabel(String userID, String missionName, String fileName, List<LocalLabelBean> labelList);
}
