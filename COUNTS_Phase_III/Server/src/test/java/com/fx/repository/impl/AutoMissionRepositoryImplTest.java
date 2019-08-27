package com.fx.repository.impl;

import com.fx.model.AutoMission;
import com.fx.repository.AutoMissionRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * Created by Hanxinhu at 8:48 2018/6/10/010
 */
public class AutoMissionRepositoryImplTest {
    AutoMissionRepository autoMissionRepository = new AutoMissionRepositoryImpl();
    @Test
    public void getAllAutoMissions() {
    }

    @Test
    public void findAutoMissionByID() {
    }

    @Test
    public void findAutoMissionByRequestorID() {
        List<AutoMission> autoMissions = autoMissionRepository.findAutoMissionByRequestorID("hxh");
        System.out.println(autoMissions.size());
    }

    @Test
    public void findAutoMissionByType() {
    }

    @Test
    public void addAutoMission() {
        AutoMission autoMission = new AutoMission();
        autoMission.setBeginTime("2018-6-10 8:55:00");
        autoMission.setFinished(false);
        autoMission.setType("Classification");
        autoMission.setPoints(30);
        autoMission.setRequestorID("hxh");
        autoMissionRepository.addAutoMission(autoMission);
    }

    @Test
    public void deleteAutoMission() {
    }
}