package com.fx.service.impl;

import com.fx.bean.AutoMissionPresentation;
import com.fx.bean.MissionPresentation;
import com.fx.controller.ImageController;
import com.fx.model.*;
import com.fx.repository.*;
import com.fx.repository.impl.*;
import com.fx.service.MissionService;
import com.fx.util.ResultMessage;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2018/4/10.
 */
@Service
public class MissionServiceImpl implements MissionService {
    MissionRepository missionRepository;
    AutoMissionRepository autoMissionRepository;
    AcceptMissionRepository acceptMissionRepository;
    UserRepository userRepository;
    AutoUserMissionRepository autoUserMissionRepository;
    MessageRepository messageRepository;
    @Override
    public ResultMessage addAcceptedMission(String username, int id, int recommendType) {
        Mission mission = findMissionByID(id);
        if (mission.getCurrentNumber() > mission.getMaxNumber())
            return ResultMessage.FALSE;
        AcceptedMission acceptedMission = new AcceptedMission(username, mission, recommendType);

        ResultMessage message = acceptMissionRepository.addAcceptMission(acceptedMission);
        if (message == ResultMessage.SUCCESS) {
            mission.setCurrentNumber(mission.getCurrentNumber() + 1);
            missionRepository.updateMission(mission);
            return ResultMessage.SUCCESS;
        } else
            return message;
    }


    @Override
    public ResultMessage updateAcceptedMission(AcceptedMission acceptedMission) {
        return acceptMissionRepository.updateAcceptMission(acceptedMission);
    }

    @Override
    public List<AcceptedMission> findAcceptedMissionByUsername(String username) {
        return acceptMissionRepository.findAcceptMissionByUsername(username);
    }

    public MissionServiceImpl() {
        missionRepository = new MissionRepositoryImpl();
        autoMissionRepository = new AutoMissionRepositoryImpl();
        acceptMissionRepository = new AcceptMissionRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        autoMissionRepository = new AutoMissionRepositoryImpl();
        autoUserMissionRepository = new AutoUserMissionRepositoryImpl();
        missionRepository = new MissionRepositoryImpl();
        messageRepository = new MessageRepositoryImpl();
    }

    /**
     * 获得所有任务，不区分发布者，工作者，是否结束等，其中MissionPresentation包含一个任务用于展示需要的基本信息
     *
     * @return
     */
    public List<Mission> getAllMission(int i) {
        List<Mission> missions = new ArrayList<>();
        List<Mission> results = missionRepository.getAllMission();
        for (int j = i; j <= i + 11 && j < results.size(); j++) {
            missions.add(results.get(j));
        }
        return missions;
    }

    /**
     * 获得某个发布者的所有任务，其中MissionPresentation包含一个任务用于展示需要的基本信息
     *
     * @param userName
     * @return
     */
    public List<MissionPresentation> getMissionByWorker(String userName) {
        return null;
    }

    /**
     * 获得一个工作者的所有任务信息，其中MissionPresentation包含一个任务用于展示需要的基本信息
     *
     * @param userName
     * @return
     */
    public List<MissionPresentation> getMissionByRequestor(String userName) {
        return null;
    }

    /**
     * 获得一个用户的所有任务数，如果userID == "" 则返回所有任务,不限用户
     *
     * @param userName
     * @return
     */

    public int countWholeMissions(String userName) {
        return 0;
    }

    /**
     * 获得一个用户的已经完成所有任务数，如果userID == "" 则返回所有已经完成的任务,不限用户
     *
     * @param userName
     * @return
     */

    public int countFinishedMissions(String userName) {
        return 0;
    }

    /**
     * 获得一个用户的未完成所有任务数，如果userID == "" 则返回所有未完成的任务,不限用户
     *
     * @param userName
     * @return
     */

    public int countUnfinishedMissions(String userName) {
        return 0;
    }

    /**
     * 添加Mission
     *
     * @param mission
     * @return
     */
    public ResultMessage addMission(Mission mission) {

        String id = mission.getRequestorID();
        UserRepository userRepository = new UserRepositoryImpl();

        User requestor = userRepository.findUserByUsername(id);

        requestor.setExp(requestor.getExp() + mission.getPoints());
        requestor.setPoints(requestor.getPoints() - mission.getPoints());
        userRepository.updateUser(requestor);

        return missionRepository.addMission(mission);
    }

    /**
     * 更新Mission的信息
     *
     * @param mission
     * @return
     */
    public ResultMessage updateMission(Mission mission) {

        return missionRepository.updateMission(mission);
    }

    /**
     * 根据id删除mission
     *
     * @param id
     * @return
     */
    public ResultMessage deleteMission(int id) {

        return missionRepository.deleteMission(id);
    }

    /**
     * 根据ID查找对应的任务
     *
     * @param id
     * @return
     */
    public Mission findMissionByID(int id) {
        return missionRepository.findMissionByID(id);
    }

    /**
     * 根据起始日期以及结束日期选择
     *
     * @param start
     * @param end
     * @return
     */
    public List<Mission> findMissionByBeginAndEnd(String start, String end) {

        return missionRepository.findMissionByBeginAndEnd(start, end);
    }

    /**
     * 根据任务类型查找
     *
     * @param type
     * @return
     */
    public List<Mission> findMissionByType(String type) {
        return missionRepository.findMissionByType(type);
    }

    /**
     * 根据发布者的id(实际为username)查找发布的任务
     *
     * @param requestorID
     * @return
     */
    public List<Mission> findMissionByRequestorID(String requestorID) {
        return missionRepository.findMissionByRequestorID(requestorID);
    }

    public String getFirstImage(int missionid) {
        String url = ImageController.imageURL;
        url = url + missionid;
        File file = new File(url);
        File first;
        if (file.listFiles().length != 0) {
            first = file.listFiles()[0];
        } else {
            return null;
        }

        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(first);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();


        String head = "data:image/" + first.getName().split("[.]")[1] + ";base64,";

        String result = head + encoder.encode(data);
        return result;
    }

    /**
     * 根据发布者的用户名查询 所有的自动化标注
     *
     * @param username
     * @return
     */
    @Override
    public List<AutoMission> getAutoMissionByRequestorID(String username) {
        return autoMissionRepository.findAutoMissionByRequestorID(username);
    }

    @Override
    public ResultMessage addAutoMission(AutoMission autoMission) {


        ResultMessage message = autoMissionRepository.addAutoMission(autoMission);
        int id = autoMission.getId();

        int numOfpicture = autoMission.getSize();
        int extraUser = numOfpicture/100;

        int numOfUser = 2+extraUser;

        List<User> users =  userRepository.findUserByType("Worker");



        users = quickSort(users,0,users.size()-1);

        if(users.size()<numOfUser){
            System.out.println("系统中已有用户无法满足分配需求，已经为你指派最多用户");
            numOfUser= users.size();
        }
        //添加该任务指派的User
        for(int i=0;i<=numOfUser-1;i++){
            AutoUserMission mid = new AutoUserMission();
            mid.setFinishTest(false);
            mid.setFinishTrain(false);
            mid.setMissionId(autoMission.getId());
            mid.setTrainStart((numOfpicture*i)/(2*numOfUser) );
            mid.setTrainEnd((numOfpicture*(i+1))/(2*numOfUser)  -1);
            mid.setTestStart((numOfpicture*(numOfUser+i))/(2*numOfUser));
            mid.setTestEnd((numOfpicture*(numOfUser+i+1))/(2*numOfUser)-1);


            //首先添加Message,但目前没有通知第二阶段
            Message message1 = new Message(users.get(i).getUsername(),autoMission.getId(),autoMission.getType());
            messageRepository.addMessage(users.get(i).getUsername(),message1);
            //System.out.println(numOfpicture);

            /**
             * 还空缺一个添加autousermission的方法
             */
            autoUserMissionRepository.addAutoUserMission(users.get(i).getUsername(),mid);

        }




        switch (autoMission.getType()) {
            case "Classification":
                mkdirsForAutoClassification(id,autoMission.getTypes());
                break;
            case "Caption":
                mkdirsForAutoCaption(id);

                break;
            case "Detection":
                mkdirsForAutoDetection(id);
                break;
        }

        return message;

    }

    @Override
    public AutoMission findAutoMissionByID(int id) {
        return autoMissionRepository.findAutoMissionByID(id);
    }

    public void mkdirsForAutoCaption(int id) {
        String dirname = "../data/autoImage/" + id;
        File dir = new File(dirname);
        if (!dir.exists())
            dir.mkdir();
        String allimage= dirname+"/allimage";
        File all = new File(allimage);
        if(!all.exists())
            all.mkdir();
    }

    public void mkdirsForAutoDetection(int id) {
        String dirname = "../data/autoImage/" + id;
        File dir = new File(dirname);
        if (!dir.exists())
            dir.mkdir();
        File data = new File(dirname + "/data");
        data.mkdir();
        File images = new File(dirname + "/images");
        images.mkdir();
        File imagesTest = new File(dirname + "/images/test");
        imagesTest.mkdir();
        File imagesTrain = new File(dirname + "/images/train");
        imagesTrain.mkdir();
        File training = new File(dirname + "/training");
        training.mkdir();
    }


    @Override
    public AcceptedMission findAcceptedMissionByUsernameAndMissionID(String username, int missionID) {
       List<AcceptedMission> missions = acceptMissionRepository.findAcceptMissionByUsername(username);
        for (int i = 0; i < missions.size(); i++) {
            AcceptedMission acceptedMission = missions.get(i);
            if (acceptedMission.getId() == missionID)
                return acceptedMission;
        }
       return null;
    }

    public void mkdirsForAutoClassification(int id, List<String> types) {
        String dirname = "../data/autoImage/" + id;
        File dir = new File(dirname);
        if (!dir.exists())
            dir.mkdir();
        File labels = new File(dirname + "/labels.txt");
        try {
            if (!labels.exists())
                labels.createNewFile();
            PrintWriter pw = new PrintWriter(labels);
            for (int i = 0; i < types.size(); i++) {
                pw.println(types.get(i));
            }
            pw.close();
            String images = dirname + "/images";
            File imagesDir = new File(images);
            if (!imagesDir.exists())
                imagesDir.mkdir();
            String trainImage = images + "/trainimage";
            File trainImageDir = new File(trainImage);
            if (!trainImageDir.exists())
                trainImageDir.mkdir();
            File labels_dir = new File(dirname + "/image_labels_dir");
            if (!labels_dir.exists())
                labels_dir.mkdir();
//

            File allimages = new File(dirname + "/allimage");
            if (!allimages.exists())
                allimages.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> quickSort(List<User> a,int start,int end) {
        int base = a.get(end).getExp();
        //start一旦等于end，就说明左右两个指针合并到了同一位置，可以结束此轮循环。
        while (start < end) {
            while (start < end && a.get(start).getExp() <= base)
                //从左边开始遍历，如果比基准值小，就继续向右走
                start++;
            //上面的while循环结束时，就说明当前的a[start]的值比基准值大，应与基准值进行交换
            if (start < end) {
                //交换
                User temp = a.get(start);
                a.set(start, a.get(end));
                a.set(end, temp);
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
                end--;
            }
            while (start < end && a.get(end).getExp() >= base)
                //从右边开始遍历，如果比基准值大，就继续向左走
                end--;
            //上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
            if (start < end) {
                //交换
                User temp = a.get(start);
                a.set(start, a.get(end));
                a.set(end, temp);
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
                start++;
            }

        }
        return a;
    }

    public List<AutoMissionPresentation> getAutoMissionByWorkerID(String username){

        List<AutoUserMission> autoUserMissions = autoUserMissionRepository.findAutoUserMissionByUsername(username);

        List<AutoMissionPresentation> autoMissions = new ArrayList<>();
        for(int i=0;i<=autoUserMissions.size()-1;i++){
           AutoMission autoMission = autoMissionRepository.findAutoMissionByID(autoUserMissions.get(i).getMissionId());
           AutoMissionPresentation mid = new AutoMissionPresentation();
           mid.setContent(autoMission.getDescription());
           mid.setId(autoMission.getId());
           mid.setType(autoMission.getType());
           mid.setTypes(autoMission.getTypes());
            System.out.println(mid.getType());
           mid.setMissionName(autoMission.getMissionName());
           if(!autoUserMissions.get(i).isFinishTrain()){
               mid.setStatus("Train");
               mid.setSize(autoUserMissions.get(i).getTrainEnd()-autoUserMissions.get(i).getTrainStart()+1);
           }
           else if(autoUserMissions.get(i).isFinishTrain()&&!autoUserMissions.get(i).isFinishTest()){
               mid.setStatus("Test");
               mid.setSize(autoUserMissions.get(i).getTestEnd()-autoUserMissions.get(i).getTestStart()+1);

           }

           autoMissions.add(mid);


            //if(autoUserMissions.get(i).get)
        }
        return autoMissions;

    }

    public AutoMission getAutoMission(int missionid){
        return autoMissionRepository.findAutoMissionByID(missionid);
    }

}
