package com.fx.repository.impl;

import com.fx.model.CaptionClassificationResult;
import com.fx.repository.CapAndClaResultRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 20:58 2018/5/29/029
 */
public class CapAndClaResultRepositoryImpl implements CapAndClaResultRepository {
    private static final String prefix  = "../data/result";
    private static final String postfix = ".txt";
    Gson gson = new Gson();
    public CapAndClaResultRepositoryImpl(){
        File file = new File("../data");
        if (!file.exists())
            try{
            file.mkdir();
            }catch (Exception e){
            e.printStackTrace();
            }
        File file1 = new File(prefix);
        if (!file1.exists()) {
            try {
                file1.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 根据任务id得到 标注结果
     *
     * @param missionID 任务ID
     * @return
     */
    @Override
    public List<CaptionClassificationResult> getResultByID(int missionID) {
        String path = prefix + missionID + postfix;
        File file = new File(path);
        ArrayList<CaptionClassificationResult> results = new ArrayList<>();
        try{
          Scanner scanner = new Scanner(file);
          while (scanner.hasNextLine()){
              String s = scanner.nextLine();
              CaptionClassificationResult result = gson.fromJson(s,CaptionClassificationResult.class);
              results.add(result);
          }
      }catch (Exception e){
          e.printStackTrace();
          return results;
        }
     return results;
    }

    /**
     * 写入分类和整体描述的结果
     *
     * @param missionID
     * @param results
     * @return
     */
    @Override
    public ResultMessage addCapAndClaResult(int missionID, List<CaptionClassificationResult> results) {
        String path = prefix + missionID + postfix;
        File file = new File(path);
       try{
           ArrayList<String> strs = new ArrayList<>();
           for (int i = 0; i < results.size(); i++) {
               String str = gson.toJson(results.get(i));
               strs.add(str);
           }
           PrintWriter pw = new PrintWriter(file);
           for (int i = 0; i < strs.size(); i++) {
               pw.println(strs.get(i));
           }
           pw.close();
       }catch (Exception e){
           e.printStackTrace();
           return ResultMessage.FAILED;
       }
        return ResultMessage.SUCCESS;
    }
}
