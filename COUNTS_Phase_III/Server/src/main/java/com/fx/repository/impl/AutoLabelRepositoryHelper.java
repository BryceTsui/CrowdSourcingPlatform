package com.fx.repository.impl;

import com.fx.model.AutoCaptionLabel;
import com.fx.model.AutoClassificationLabel;
import com.fx.model.AutoDetectionLabel;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 20:56 2018/6/12/012
 */
public class AutoLabelRepositoryHelper<T> {
    Type t;
    Gson gson = new Gson();
    private static final String dir = "../data/AutoImage/";

    public AutoLabelRepositoryHelper(Type t) {
        this.t = t;
    }

    public ResultMessage addAutoLabel(int missionID, T t) {
        List<T> ts = findAutoLabelByMissionID(missionID);
        ts.add(t);
        writeLabels(missionID,ts);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage updateAutoLabel(int missionID, T t) {
        List<T> labels = findAutoLabelByMissionID(missionID);
        for (int i = 0; i < labels.size() ; i++) {
            if(t.equals(AutoClassificationLabel.class)){
                AutoClassificationLabel a = (AutoClassificationLabel) labels.get(i);
                AutoClassificationLabel b = (AutoClassificationLabel) t;
                if (a.getFileName().equals(b.getFileName())){
                    labels.set(i,t);
                    writeLabels(missionID,labels);
                    return ResultMessage.SUCCESS;
                }
            }
            else if(t.equals(AutoDetectionLabel.class)){
                AutoDetectionLabel a = (AutoDetectionLabel) labels.get(i);
                AutoDetectionLabel b = (AutoDetectionLabel) t;
                if (a.getFileName().equals(b.getFileName())){
                    labels.set(i,t);
                    writeLabels(missionID,labels);
                    return ResultMessage.SUCCESS;

                }
            }else if(t.equals(AutoCaptionLabel.class)){
                AutoCaptionLabel a = (AutoCaptionLabel) labels.get(i);
                AutoCaptionLabel b = (AutoCaptionLabel) t;
                if (a.getFileName().equals(b.getFileName())){
                    labels.set(i,t);
                    writeLabels(missionID,labels);
                    return ResultMessage.SUCCESS;

                }
            }
        }
        return ResultMessage.NOT_EXIST;
    }

    public List<T> findAutoLabelByMissionID(int missionID) {
        String filename = dir + missionID + "/label.txt";
        File file = new File(filename);
        ArrayList<T> list = new ArrayList<>();
        try {
            if (!file.exists())
                file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                T temple = gson.fromJson(s, t);
                list.add(temple);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public T findAutoLabelByMissionIDandFilename(int missionID, String filename) {
        List<T> labels = findAutoLabelByMissionID(missionID);
        for (T label:labels
             ) {
           if(t.equals(AutoClassificationLabel.class)){
               AutoClassificationLabel a = (AutoClassificationLabel) label;
                if (filename.equals(a.getFileName()))
                    return label;
           }
           else if(t.equals(AutoDetectionLabel.class)){
               AutoDetectionLabel a = (AutoDetectionLabel) label;
               if (filename.equals(a.getFileName()))
                   return label;
           }else if(t.equals(AutoCaptionLabel.class)){
               AutoCaptionLabel a = (AutoCaptionLabel) label;
               if (filename.equals(a.getFileName()))
                   return label;
           }
        }
        return null;
    }

    private void writeLabels(int missionID, List<T> ts) {
        String filename = dir + missionID + "/label.txt";
        File file = new File(filename);
        try {
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < ts.size(); i++) {
                pw.println(gson.toJson(ts.get(i)));
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
