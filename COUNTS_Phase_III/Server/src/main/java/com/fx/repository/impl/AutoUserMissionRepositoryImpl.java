package com.fx.repository.impl;

import com.fx.model.AutoUserMission;
import com.fx.repository.AutoUserMissionRepository;
import com.fx.util.ResultMessage;
import com.google.gson.Gson;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * Created by Hanxinhu at 19:45 2018/6/12/012
 */
public class AutoUserMissionRepositoryImpl implements AutoUserMissionRepository {
    private static final String dirname = "../data/autoUserMission";
    Gson gson = new Gson();
    public AutoUserMissionRepositoryImpl(){
        File file = new File(dirname);
        if (!file.exists())
            file.mkdir();
    }
    @Override
    public ResultMessage addAutoUserMission(String username, AutoUserMission autoUserMission) {
        List<AutoUserMission> list = findAutoUserMissionByUsername(username);
        list.add(autoUserMission);
        writeAll(username,list);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateAutoUserMission(String username, AutoUserMission autoUserMission) {
        List<AutoUserMission> list = findAutoUserMissionByUsername(username);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getMissionId() == autoUserMission.getMissionId())
                list.set(i,autoUserMission);
        }
        writeAll(username,list);
        return ResultMessage.SUCCESS;
    }

    @Override
    public List<AutoUserMission> findAutoUserMissionByUsername(String username) {
        String filename = getFilename(username);
        File file = new File(filename);
        ArrayList<AutoUserMission> missions = new ArrayList<>();
        try {
            if (!file.exists())
                file.createNewFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                AutoUserMission a = gson.fromJson(s,AutoUserMission.class);
                missions.add(a);
                }
                scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return missions;
    }
    private String getFilename(String username){
        return dirname+"/"+username+".txt";
    }
    private void writeAll(String username,List<AutoUserMission> autoUserMissions){
        String filename = getFilename(username);
        File file = new File(filename);
        try{
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < autoUserMissions.size(); i++) {
                String s = gson.toJson(autoUserMissions.get(i));
                pw.println(s);
            }
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
