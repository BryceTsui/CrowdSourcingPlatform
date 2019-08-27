package com.fx.repository.impl;

import com.fx.util.DataConst;
import com.fx.util.ResultMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 9:35 2018/3/21/021
 */
public class WholeLabelRepositoryImpl implements com.fx.repository.WholeLabelRepository {
    /**
     * @param userID
     * @param missionName
     * @param fileName
     * @param wholeLabel
     * @return
     */
    @Override
    public ResultMessage updateWholeLabel(String userID, String missionName, String fileName, String wholeLabel) {
        String name = DataConst.FILE_PATH + userID + "_" + missionName + "_" + fileName + ".txt";
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(wholeLabel);
            printWriter.close();
            fileWriter.close();
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }

    @Override
    public String getWholeLabelByUserIDAndLocation(String userID, String missionName, String fileName) {
        String name = DataConst.FILE_PATH + userID + "_" + missionName + "_" + fileName + ".txt";
        File file = new File(name);
        if (!file.exists()) {
            return "";
        }
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            String s = scanner.nextLine();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * @param userID
     * @param missionName
     * @param fileName
     * @param wholeLabel
     * @return
     */
    @Override
    public ResultMessage addWholeLabel(String userID, String missionName, String fileName, String wholeLabel) {
        String name = DataConst.FILE_PATH + userID + "_" + missionName + "_" + fileName + ".txt";
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(wholeLabel);
            printWriter.close();
            fileWriter.close();
            return ResultMessage.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }
}
