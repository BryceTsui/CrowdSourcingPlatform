package com.fx.service.impl;

import com.fx.bean.MissionMonthChart;
import com.fx.bean.UserLevelChart;
import com.fx.bean.UserLocationChart;
import com.fx.model.Mission;
import com.fx.model.User;
import com.fx.repository.MissionRepository;
import com.fx.repository.UserRepository;
import com.fx.repository.impl.MissionRepositoryImpl;
import com.fx.repository.impl.UserRepositoryImpl;
import com.fx.service.AnalysisService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/1.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    UserRepository userRepository = new UserRepositoryImpl();
    MissionRepository missionRepository = new MissionRepositoryImpl();
    /**
     * 获得不同等级的工作者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    public List<UserLevelChart> getWorkerLevelChart(){

        List<User> list = userRepository.findUserByType("Worker");

        List<UserLevelChart> levels = new ArrayList<UserLevelChart>();

        String[] names = new String[]{"大众用户","黄金用户","铂金用户","钻石用户","星耀用户"};

        for(int i=1;i<=5;i++){
            levels.add(new UserLevelChart(0,names[i-1]));
        }
        int max;
        for(int i=0;i<=list.size()-1;i++){
            for(int j=0;j<=levels.size()-1;j++){
                if(levels.get(j).getName().equals(names[list.get(i).getLevel()])){
                    levels.get(j).setValue(levels.get(j).getValue()+1);
                }
            }
        }
        return levels;
    }

    /**
     * 获得不同等级发布者数
     * @return 代表不同等级的用户数目。一般是从1开始，到n结束，目前还没确定n这个上限,注意0一般是没有用的
     */
    public List<UserLevelChart> getRequestorLevelChart(){

        List<User> list = userRepository.findUserByType("Requestor");

        List<UserLevelChart> levels = new ArrayList<UserLevelChart>();

        String[] names = new String[]{"大众用户","黄金用户","铂金用户","钻石用户","星耀用户"};

        for(int i=1;i<=5;i++){
            levels.add(new UserLevelChart(0,names[i-1]));
        }
        int max;
        for(int i=0;i<=list.size()-1;i++){
            for(int j=0;j<=levels.size()-1;j++){
                if(levels.get(j).getName().equals(names[list.get(i).getLevel()])){
                    levels.get(j).setValue(levels.get(j).getValue()+1);
                }
            }
        }
        return levels;
    }

    /**
     * 获得工作者的地理位置图表
     * @return
     */
    public UserLocationChart getWorkerLocationChart(){

        return null;
    }

    /**
     * 获得发布者的地理位置图表
     * @return
     */
    public UserLocationChart getRequestorLocationChart(){

        return null;
    }

    public int getWorkerNumber(){

        return userRepository.findUserByType("Worker").size();
    }

    public int getRequestorNumber(){

        return userRepository.findUserByType("Requestor").size();
    }

    @Override
    public MissionMonthChart getMissionMonthChart() {
        List<List<Mission>> missions = new ArrayList<>();
       List<Mission> missions1 =  missionRepository.findMissionByBeginAndEnd("2018-01-01","2018-01-31");
       List<Mission> missions2 =  missionRepository.findMissionByBeginAndEnd("2018-02-01","2018-02-28");
        List<Mission> missions3 =  missionRepository.findMissionByBeginAndEnd("2018-03-01","2018-03-31");
        List<Mission> missions4 =  missionRepository.findMissionByBeginAndEnd("2018-04-01","2018-04-30");
        List<Mission> missions5 =  missionRepository.findMissionByBeginAndEnd("2018-05-01","2018-05-31");
        List<Mission> missions6 =  missionRepository.findMissionByBeginAndEnd("2018-06-01","2018-06-30");

        missions.add(missions1);
        missions.add(missions2);
        missions.add(missions3);
        missions.add(missions4);
        missions.add(missions5);
        missions.add(missions6);

        //a1保存已完成数
         int[] a1 = new int[]{0,0,0,0,0,0};
        int[] a2 = new int[]{0,0,0,0,0,0};
        for(int i=0;i<=missions.size()-1;i++){
            for(int j=0;j<=missions.get(i).size()-1;j++){
                if(missions.get(i).get(j).getCurrentNumber()>=missions.get(i).get(j).getMaxNumber()){
                    a1[i]++;
                }
                else{
                    a2[i]++;
                }
            }
        }

        int[] a3 = new int[]{0,0,0,0,0,0};
        for(int i=0;i<=a3.length-1;i++){
            a3[i] = missions.get(i).size();
        }

        MissionMonthChart missionMonthChart = new MissionMonthChart(a1,a2,a3);


        return missionMonthChart;
    }
}
