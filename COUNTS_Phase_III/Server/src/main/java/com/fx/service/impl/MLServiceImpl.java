package com.fx.service.impl;

import com.fx.HttpRequestor.api.Connecter;
import com.fx.machinelearning.PBServer;
import com.fx.model.*;
import com.fx.repository.*;
import com.fx.repository.impl.*;
import com.fx.service.MLService;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/6/6.
 */
@Service
public class MLServiceImpl implements MLService {

    PBServer pbServer = new PBServer();
    AutoUserMissionRepository autoUserMissionRepository = new AutoUserMissionRepositoryImpl();
    AutoMissionRepository autoMissionRepository = new AutoMissionRepositoryImpl();
    AutoCaptionLabelRepository autoCaptionLabelRepository = new AutoCaptionLabelRepositoryImpl();
    AutoDetectionLabelReposity autoDetectionLabelReposity = new AutoDetectionLabelRepositoryImpl();
    AutoClassificationLabelRepository autoClassificationLabelRepository = new AutoClassificationLabelRepositoryImpl();

    Connecter connecter = new Connecter();

    @Override
    public void predictCaptionLabel(String username,int missionid) {
        List<AutoUserMission> autoUserMission = autoUserMissionRepository.findAutoUserMissionByUsername(username);
        AutoMission autoMission = autoMissionRepository.findAutoMissionByID(missionid);

        String path = "../data/autoImage/" + missionid + "/allImage";

        for (int j = 0; j <= autoUserMission.size() - 1; j++) {
            if (autoUserMission.get(j).getMissionId() == missionid) {

                int start = autoUserMission.get(j).getTestStart();
                int end = autoUserMission.get(j).getTestEnd();

                File file = new File(path);
                //System.out.println(file.getAbsolutePath());
                if (!file.exists())
                    System.out.println("路径有毛病:"+path);

                File[] files = file.listFiles();

                //List<String> lists = new ArrayList<>();

                for (int i = start; i <= end; i++) {
                    InputStream in = null;
                    byte[] data = null;
                    try {
                        in = new FileInputStream(files[i]);
                        data = new byte[in.available()];
                        in.read(data);
                        in.close();
                        //LocalLabel localLabel = new LocalLabel();
                        BASE64Encoder encoder = new BASE64Encoder();
                        String url = encoder.encode(data);
                        //String head = "data:image/" + files[i].getName().split("[.]")[1] + ";base64,";
                        AutoCaptionLabel mid  = connecter.predictCaptionLabel(files[i].getName(),url);
                        autoCaptionLabelRepository.addAutoCaptionLabel(missionid,mid);
                        // System.out.println(head+url);
                        //lists.add(head+url);


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }




    public void predictCalssificationLabel(String username,int missionid){
        List<AutoUserMission> autoUserMission = autoUserMissionRepository.findAutoUserMissionByUsername(username);
        AutoMission autoMission = autoMissionRepository.findAutoMissionByID(missionid);

        String path = "../data/autoImage/" + missionid + "/allImage";

        for (int j = 0; j <= autoUserMission.size() - 1; j++) {
            if (autoUserMission.get(j).getMissionId() == missionid) {

                int start = autoUserMission.get(j).getTestStart();
                int end = autoUserMission.get(j).getTestEnd();

                File file = new File(path);
                //System.out.println(file.getAbsolutePath());
                if (!file.exists())
                    System.out.println("路径有毛病:"+path);

                File[] files = file.listFiles();

                //List<String> lists = new ArrayList<>();

                for (int i = start; i <= end; i++) {

                        String[] labels = new String[autoMission.getTypes().size()];
                        for(int k=0;k<=labels.length-1;k++){
                            labels[k] = autoMission.getTypes().get(i);
                        }
                        AutoClassificationLabel mid  = pbServer.predictClassificationLabel(files[i].getName(),"../data/autoImage/"+missionid+"/allimage","../data/tensorflow/pbmodel",labels,"output_graph.pb");
                        autoClassificationLabelRepository.addAutoClassificationLabel(missionid,mid);
                        // System.out.println(head+url);
                        //lists.add(head+url);


                }
            }
        }

    }

    public void predictDetectionLabel(String username,int missionid){
        List<AutoUserMission> autoUserMission = autoUserMissionRepository.findAutoUserMissionByUsername(username);
        AutoMission autoMission = autoMissionRepository.findAutoMissionByID(missionid);

        String path = "../data/autoImage/" + missionid + "/allImage";

        for (int j = 0; j <= autoUserMission.size() - 1; j++) {
            if (autoUserMission.get(j).getMissionId() == missionid) {

                int start = autoUserMission.get(j).getTestStart();
                int end = autoUserMission.get(j).getTestEnd();

                File file = new File(path);
                //System.out.println(file.getAbsolutePath());
                if (!file.exists())
                    System.out.println("路径有毛病:"+path);

                File[] files = file.listFiles();

                //List<String> lists = new ArrayList<>();

                for (int i = start; i <= end; i++) {
                    InputStream in = null;
                    byte[] data = null;
                    try {
                        in = new FileInputStream(files[i]);
                        data = new byte[in.available()];
                        in.read(data);
                        in.close();
                        //LocalLabel localLabel = new LocalLabel();
                        BASE64Encoder encoder = new BASE64Encoder();
                        String url = encoder.encode(data);
                        //String head = "data:image/" + files[i].getName().split("[.]")[1] + ";base64,";
                        AutoDetectionLabel mid  = pbServer.predicObjectDetectionLabel(files[i].getName(),"../data/autoImage/"+missionid+"/allimage","../data/tensorflow/odmodel");
                        autoDetectionLabelReposity.addAutoDetectionLabel(missionid,mid);
                        // System.out.println(head+url);
                        //lists.add(head+url);


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }
}
