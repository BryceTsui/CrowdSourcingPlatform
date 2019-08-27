package com.fx.repository.impl;

import com.fx.bean.LocalLabelBean;
import com.fx.repository.LocalLabelRepository;
import com.fx.util.DataConst;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Description:
 * Created by Hanxinhu at 9:34 2018/3/21/021
 */
public class LocalLabelRepositoryImpl implements LocalLabelRepository {
    Gson gson = new Gson();

    /**
     * 添加局部标记
     *
     * @param userID
     * @param missionName
     * @param fileName
     * @param labelList
     */
    @Override
    public ResultMessage addLocalLabel(String userID, String missionName, String fileName, List<LocalLabelBean> labelList) {
        File file = new File(DataConst.FILE_PATH + userID + "_" + missionName + "_" + fileName + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            String json = gson.toJson(labelList);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(json);
            printWriter.close();
            fileWriter.close();
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }


    }
}
