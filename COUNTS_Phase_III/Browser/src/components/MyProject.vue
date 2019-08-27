<template>
  <div>
    <div style="background-color: #E9E9E9; top: 57px; position: fixed; bottom: 0px; left: 0px; width: 100%"></div>
    <div style="position: absolute; left: 0px; top: 57px; background-color: #E9E9E9; width: 100%; height:900px">
      <div style="position: absolute; left: 50%; margin-left: -550px; top: 20px;">
        <div style="position: absolute; width: 5px; height:30px; background-color:#108cee; left:0px;"></div>
        <span style="position: absolute; left: 10px; width: 200px; font-size: 20px; text-align: left">个人信息</span>
      </div>
      <div style="position: absolute; top: 60px; height: 220px; width: 1100px; left: 50%; margin-left: -550px; background-color: white">
        <img :src="headImage" style="width: 150px; position: absolute; left: 20px; top: 15px;">
        <el-button icon="el-icon-edit-outline" type="text" style="position: absolute; left: 85px;top: 170px;font-size: 15px" @click="dialogVisible = true">修改头像</el-button>
        <span style="position: absolute; left: 210px; top: 25px; font-size: 28px;">{{personal_info.name}}</span>
        <div style="position: absolute; left: 210px; top: 70px; font-size: 18px; color: #282828">
          &nbsp<span id="id">{{personal_info.id}}</span>&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span id="identify">{{personal_info.identify}}</span>&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span id="username">{{personal_info.username}}</span>&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span id="email">{{personal_info.email}}</span>&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span id="phone">{{personal_info.phone}}</span>
        </div>
        <div style="position: absolute; left: 215px; top: 95px; font-size: 12px; color: grey">
          <p style="width: 800px; text-align: left">{{personal_info.info}}</p>
        </div>
        <div style="position: absolute; left: 210px; top: 155px;">
          &nbsp<span>积分&nbsp&nbsp<span style="color: #f8b62d;font-size: 16px;font-weight: bold">{{personal_info.points}}</span></span>&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span>等级&nbsp&nbspLv{{personal_info.level}}&nbsp<span style="font-size: 15px; color: #f8b62d; font-weight: bold">{{personal_info.level_d}}</span></span>
          &nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp
          <span>评分&nbsp&nbsp</span>
          <el-rate style="position: absolute; top: 2px; left: 290px; width: 200px" v-model="personal_info.rate" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
        </div>
      </div>
      <div style="position: absolute; top: 300px;width: 1100px; left: 50%; margin-left: -550px;">
        <el-menu :default-active="default_index" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="1" style="width: 250px">我的项目</el-menu-item>
          <el-menu-item index="2" style="width: 250px">我的数据</el-menu-item>
          <el-menu-item index="3" style="width: 250px">我的资料</el-menu-item>
        </el-menu>
      </div>
    </div>
    <div v-show="project_show" style="position: absolute; top: 400px;width: 100%; left: 0; top: 418px;">
      <div style="position: absolute; top: 0px;width: 1100px; left: 50%; margin-left: -550px; background-color: white;">
        <div style="position: absolute; top: 0px;left: 0; width: 100%; height: 370px; background-color: white"></div>
        <div>
          <el-cascader style="position: absolute; left: 30px; top: 10px;"
            expand-trigger="hover"
            :options="labelTypeOptions"
            v-model="selectLabelTypeOption"
            @change="handleLabelTypeChange">
          </el-cascader>
        </div>
        <div style="position: absolute; top: 50px; left: 0px; padding-left: 20px;background-color: white;">
          <div v-for="item in projectInfo" style="float: left" :key="item">
            <el-card v-show="item.show" class="el_card" :body-style="{padding:'0px'}">
              <el-tag v-show="item.isContinue" style="position: absolute; left: 20px; top: 10px; color:white; width: 70px " color="#4CAF50">进行中...</el-tag>
              <el-tag v-show="item.isEnd" style="position: absolute; left: 20px; top: 10px; color:white;width: 60px " color="#E97506">已截止</el-tag>
              <img v-bind:src="item.cover" class="image" v-bind:id="item.missionname">
              <div class="imgOnClick">
                <span>已标注：{{item.imgFinished}} 张</span>
                <span>未标注：{{item.imgToDo}} 张</span>
                <el-progress type="circle" v-bind:percentage="item.percent" color="#8e71c7"></el-progress>
              </div>
              <div style="height: 130px;position:absolute; top: 150px; width: 97%; left: 1.5%; background-color: white">
                <span style="font-size: 20px; color: #4CAF50">{{item.missionname}}</span>
                <span class="time" style="position: absolute; left: 5px;top: 30px;font-size: 14px; color:black">时间：{{item.dateStart}} - {{item.dateEnd}}</span>
                <span style="position: absolute; left: 5px;top: 52px;font-size: 14px;">类型：{{item.type}}</span>
                <span style="position: absolute; left: 140px;top: 52px;font-size: 14px;">积分：{{item.counts}}</span>
                <el-button style="position: absolute; left: 50px;top: 75px;font-size: 14px;" type="text" @click="handleProjectDetails(item.id)">项目详情</el-button>
                <el-button style="position: absolute; left: 150px;top: 75px;font-size: 14px;" type="text" class="card_button" @click="openProject(item)">进入项目</el-button>
              </div>
            </el-card>
          </div>
          <div v-for="item in autoProjects" style="float: left" :key="item">
            <el-card v-show="item.show" class="el_card" :body-style="{padding:'0px'}">
              <img v-bind:src="item.cover" class="image" v-bind:id="item.missionname">
              <div class="imgOnClick">
                <span>已标注：{{item.imgFinished}} 张</span>
                <span>未标注：{{item.imgToDo}} 张</span>
                <el-progress type="circle" v-bind:percentage="item.percent" color="#8e71c7"></el-progress>
              </div>
              <div style="height: 130px;position:absolute; top: 150px; width: 97%; left: 1.5%; background-color: white">
                <span style="font-size: 20px; color: #4CAF50">{{item.missionname}}</span>
                <span style="position: absolute;left:90px; top: 28px;font-size: 15px;color:green">自动化标注</span>
                <span style="position: absolute; left: 5px;top: 52px;font-size: 14px;">类型：{{item.type}}</span>
                <span style="position: absolute; left: 140px;top: 52px;font-size: 14px;">积分：{{item.counts}}</span>
                <el-button style="position: absolute; left: 150px;top: 75px;font-size: 14px;" type="text" class="card_button" @click="openProject(item)">进入项目</el-button>
              </div>
            </el-card>
          </div>
          <div v-show="hasProject" style="float: left; width: 100%;">
            <el-pagination @current-change="handleCurrentChange" background :current-page=1 :page-size="12"
                           layout="total, prev, pager, next, jumper" v-bind:total="project_total">
            </el-pagination>
          </div>
        </div>

      </div>

    </div>
    <div v-show="data_show" style="position: absolute; top: 400px;width: 100%; left: 0; top: 418px;">
      <div style="position: absolute; top: 0px;width: 1100px; left: 50%; margin-left: -550px; background-color: white; height:1000px">
        <div style="position: absolute; left: 5%; width: 90%; top: 10px;">
          <div style="position: relative;float:left; width: 400px; height: 250px;">
            <span style="position: absolute; left: 0px; top: 0px; text-align: left; font-size: 16px">项目完成情况</span>
            <div id="pie" style="position: absolute;left: 0px; top: 50px;width: 400px; height: 200px;"></div>
          </div>
          <div style="float:left; margin-left: 10px; width: 400px; height: 250px;">
            <div style="position: relative;">
              <span style="position: absolute; left: 0px; top: 0px; text-align: left; font-size: 16px">类型分布</span>
              <div id="radar" style="position: absolute;left: 100px; top: 50px;width: 400px; height: 200px;"></div>
            </div>
          </div>
          <div style="float:left; margin-top:10px; width: 400px; height: 250px;">
            <div style="position: relative;">
              <span style="position: absolute; left: 0px; top: 0px; text-align: left; font-size: 16px">排名信息</span>
              <span style="position: absolute; left: 100px; top: 30px; font-size: 15px">我的经验值排名：<span style="font-weight: bold; color: gold">{{myRanking}}</span></span>
              <span style="position: absolute; left: 260px; width: 300px; top: 30px; font-size: 15px; text-align: left">已超越 <span style="font-weight: bold; color: gold">{{myRankingPercent}}%</span> 的Worker用户</span>
              <span style="position: absolute; left: 100px; top: 60px; font-size: 15px; font-weight: bold; color: #ffca06">全网前十 英雄榜</span>
              <div style="position: absolute; left: 100px; top: 90px;">
                  <el-table :data="rankingTableData" >
                    <el-table-column label="排名" align="center" type="index" width="50"></el-table-column>
                    <el-table-column align="center" property="id" label="ID" width="150"></el-table-column>
                    <el-table-column align="center" property="name" label="昵称" width="150"></el-table-column>

                    <el-table-column style="color:gold; font-weight: bold" align="center" property="exp" label="经验值" width="150">
                      <template slot-scope="scope">
                        <span style="color:gold; font-weight: bold; font-size: 15px">{{scope.row.exp}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column align="center" property="area" label="地区" width="150"></el-table-column>
                  </el-table>
              </div>
              <img style="width: 25px; position: absolute; left: 112px; top: 150px;" src="../assets/rankFirst.png">
              <img style="width: 25px; position: absolute; left: 112px; top: 200px;" src="../assets/rankSecond.png">
              <img style="width: 25px; position: absolute; left: 112px; top: 250px;" src="../assets/rankThird.png">
            </div>

          </div>
          <div style="float:left; margin-top:10px; width: 400px; height: 250px; display: none">
            <div style="position: relative;">
              <span style="position: absolute; left: 0px; top: 0px; text-align: left; font-size: 16px">项目正确率</span>
              <div id="line" style="position: absolute;left: 0px; top: 50px;width: 400px; height: 200px;"></div>
            </div>
          </div>
          <div style="float:left; margin-left: 10px; width: 400px; height: 250px; display: none">
            <div style="position: relative;">
              <span style="position: absolute; left: 0px; top: 0px; text-align: left; font-size: 16px">群体排名</span>
              <div id="grade" style="position: absolute;left: 50px; top: 40px;width: 480px; height: 260px;"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="info_show" style="position: absolute; top: 400px;width: 100%; left: 0; top: 418px;">
      <div style="position: absolute; top: 0px;width: 1100px; left: 50%; margin-left: -550px; background-color: white; height: 550px">
        <div id="account_info" style="width: 100%; height: 171px; position: absolute; left: 0px; top: 0px;">
          <span style="font-size: 18px;position: absolute;left: 30px; top: 20px;">账号信息</span>
          <div style="position: absolute; left: 35px; top: 70px">
            编号：<span style="font-size: 16px;color: #767676">{{personal_info.id}}</span>
          </div>
          <div style="position: absolute; left: 400px; top: 70px">
            用户名：<span style="font-size: 16px;color: #767676">{{personal_info.username}}</span>
          </div>
          <div style="position: absolute; left: 765px; top: 70px">
            昵称：<span style="font-size: 16px;color: #767676">{{personal_info.name}}</span>
          </div>
          <div style="position: absolute; left: 35px; top:110px">
            身份：<span style="font-size: 16px;color: #767676">{{personal_info.identify}}</span>
          </div>
          <div style="width: 94%;height: 1px;background-color: #dadada;left: 3%; top: 170px;position: absolute;"></div>
          <el-button id="edit_name" style="position: absolute; top: 30px; right: 50px" icon="el-icon-edit-outline" type="text" @click="editNameDialogShow">修改昵称</el-button>
        </div>
        <div id="details_info" style="width: 100%; height: 181px; position: absolute; left: 0px; top: 175px;">
          <span style="font-size: 18px;position: absolute;left: 30px; top: 20px;">个人详情</span>
          <div style="position: absolute; left: 35px; top: 70px;">
            个人简述：
          </div>
          <p style="color: #767676;position: absolute;left: 120px; top: 70px;font-size:14px;width: 700px; text-align: left">{{personal_info.info}}</p>
          <div style="position: absolute; left: 35px; top: 120px">
            所在地：<span style="font-size: 16px;color: #767676">{{personal_info.province}} {{personal_info.city}}</span>
          </div>
          <el-button id="edit_details" style="position: absolute; top: 30px; right: 50px" icon="el-icon-edit-outline" type="text" @click="editDetailsDialogShow">修改</el-button>
          <div style="width: 94%;height: 1px;background-color: #dadada;left: 3%; top: 180px;position: absolute;"></div>
        </div>
        <div id="connect_info" style="width: 100%; height: 171px; position: absolute; left: 0px; top: 357px;">
          <span style="font-size: 18px;position: absolute;left: 30px; top: 20px;">联系方式</span>
          <div style="position: absolute; left: 35px; top: 70px">
            电子邮箱：<span style="font-size: 16px;color: #767676">{{personal_info.email}}</span>
          </div>
          <div style="position: absolute; left: 400px; top: 70px">
            手机号码：<span style="font-size: 16px;color: #767676">{{personal_info.phone}}</span>
          </div>
          <el-button id="edit_connect" style="position: absolute; top: 30px; right: 50px" icon="el-icon-edit-outline" type="text" @click="editConnectDialogShow">修改</el-button>
        </div>
      </div>
    </div>
    <div>
      <el-dialog title="修改头像" :visible.sync="dialogVisible" width="600px;">
        <div style="height: 350px">
          <div class="show-info">
            <div class="test">
              <vueCropper
                ref="cropper2"
                :img="example2.img"
                :outputSize="example2.size"
                :outputType="example2.outputType"
                :info="example2.info"
                :canScale="example2.canScale"
                :autoCrop="example2.autoCrop"
                :autoCropWidth="example2.autoCropWidth"
                :autoCropHeight="example2.autoCropHeight"
                :fixed="example2.fixed"
                :fixedNumber="example2.fixedNumber"
                @realTime="realTime"
              ></vueCropper>
            </div>
            <div style="position: absolute; left: 450px; top:100px; width: 100px; height: 100px">
              <div class="show-preview" :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden', 'margin': '5px'}">
                <div :style="previews.div">
                  <img :src="previews.url" :style="previews.img">
                </div>
              </div>
            </div>
            <label class="btn" for="upload2">选择照片</label>
            <input type="file" id="upload2" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg" @change="uploadImg($event, 2)">
            <button @click="saveHeadImg('base64')" class="btn">确定</button>
            <button class="btn" @click="cancel">取消</button>
          </div>
        </div>
      </el-dialog>
    </div>
    <div>
      <el-dialog title="修改昵称" :visible.sync="editNameVisible" style="width: 1200px;" >
        <el-input style="position: absolute; left: 100px; top: 70px; width: 300px;" v-model="newName"></el-input>
        <el-button type="primary" style="position: absolute; top: 70px; left: 440px; height: 38px;width: 80px" @click="editNameSure">确定</el-button>
        <div style="height: 40px"></div>
      </el-dialog>
      <el-dialog  title="修改详情" width="650px" :visible.sync="editDetailsVisible" >
        <span style="position: absolute; left:50px; top: 70px; width: 100px">个人简述</span>
        <el-input style="color:#5c5c5c; font-size:12px;position: absolute; left: 150px; width: 400px; top: 70px;" type="textarea" :rows="3" v-bind:placeholder="personal_info.info" v-model="newDetailsInfo"></el-input>
        <span style="position: absolute; left: 50px; top: 170px; width: 100px">所在地</span>
        <el-cascader style="position: absolute; left: 150px; top: 170px; width: 400px"
          size="small"
          :options="options"
          v-model="selectedOptions"
          @change="handleLocationChange">
        </el-cascader>
        <el-button type="primary" style="position: absolute; top: 220px; left: 470px; height: 30px;width: 80px" @click="editDetailsSure">确定</el-button>
        <div style="height: 170px">
        </div>
      </el-dialog>
      <el-dialog title="修改联系方式" width="650px" :visible.sync="editConnectVisible" >
        <el-form  style="position: absolute; left: 100px; width: 400px" :model="connectform"  status-icon :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm" size="small">
          <el-form-item label="电子邮箱" prop="newMail" required>
            <el-input  v-model="connectform.newMail" auto-complete="off" size="small" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="newPhone" :label-width="formLabelWidth" required>
            <el-input v-model="connectform.newPhone" auto-complete="off" size="small"></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" style="position: absolute; top: 180px; left: 420px; height: 30px;width: 80px" @click="editConnectSure">确认</el-button>
        <div style="height: 140px"></div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode } from 'element-china-area-data'
import vueCropper from 'vue-cropper'
import echarts from 'echarts';
require('echarts/theme/shine');
export default {
  name: 'MyProject',
  components: {
    vueCropper
  },
  data () {
    var validateMail = (rule, value, callback) => {
      var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/
      if (reg.test(value) == false) {
        callback(new Error('邮箱格式不正确'))
      }
    }
    var validatePhone = (rule, value, callback) => {
      if (value.length != 0) {
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/
        if (reg.test(value) == false) {
          callback(new Error('手机号码格式不正确'))
        }
      }
    }
    return {
      autoProjects:[
        {
          id: '1',
          missionname: 'auto test',
          charts_id: '' + '1' + '_charts',
          imgFinished: 4,
          imgToDo: 7,
          type: 'Caption',
          cover: '',
          percent: 0,
          annotationType: 1,
          status:'train',
          show: true
        }
      ],
      selectLabelTypeOption:[],
      labelTypeOptions:[
        {
          value:'all',
          label:'全部项目'
        },
        {
          value: 'man',
          label:'人工标注项目'
        },
        {
          value:'auto',
          label:'自动化项目',
        }
      ],
      isManLabel:true,
      isAutoLabel:true,
      rankingTableData: [

      ],
      myRanking: 0,
      myRankingPercent: 0,
      hasProject:false,
      user: {},
      connectform: {
        newMail: '',
        newPhone: ''
      },
      options: provinceAndCityData,
      selectedOptions: [],
      newName: '',
      newDetailsInfo: '',
      newProvince: '',
      newCity: '',
      detailDialog_Details: '',
      detailDialog_Locations: '',
      editDetailsVisible: false,
      editConnectVisible: false,
      editNameVisible: false,
      user_name_myProject: this.$store.state.user_name,
      headImage: '',
      model: false,
      modelSrc: '',
      crap: false,
      previews: {},
      dialogVisible: false,
      option: {
        img: '',
        size: 1,
        full: false,
        outputType: 'png',
        canMove: true,
        fixedBox: false,
        original: false,
        canMoveBox: true
      },
      example2: {
        img: 'http://localhost:8080/static/img/userDefault.d70d303.png',
        info: true,
        size: 1,
        outputType: 'jpeg',
        canScale: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 150,
        autoCropHeight: 125,
        fixed: true,
        fixedNumber: [1, 1]
      },
      cropper_info: {
        img: 'https://o90cnn3g2.qnssl.com/0C3ABE8D05322EAC3120DDB11F9D1F72.png',
        autoCrop: true,
        autoCropWidth: 200,
        autoCropHeight: 200,
        fixedBox: true
      },

      params: {
        token: '123456798',
        name: 'avatar'
      },
      headers: {
        smail: '*_~'
      },
      imgDataUrl: '',
      uploadShow: true,
      project_show: true,
      data_show: false,
      info_show: false,
      default_index: '1',
      project_info: {},
      projectInfo: [
        {
          show:true,
          id: '01',
          missionname: 'miss',
          dateStart: '2011-1-2',
          dateEnd:'2016-3-2',
          charts_id: '1_charts',
          imgFinished: 17,
          imgToDo: 3,
          type: 'Caption',
          cover: '',
          annotationType: 0,
          counts: 5,
          percent:0,
          isEnd:false,
          isContinue:true
        },
      ],
      project_total: 0,
      info: {
        id: '',
        username: '',
        name: '',
        identify: '',
        email: '',
        phone: '',
        info: '',
        level: 0,
        level_d: '',
        points: 0, // 积分
        rate: 0, // 评分
        province: '',
        city: ''
      },
      personal_info: {
        id: '',
        username: '',
        name: '',
        identify: '',
        email: '',
        phone: '',
        info: '',
        level: 0,
        level_d: '',
        points: 0, // 积分
        rate: 0, // 评分
        province: '',
        city: ''
      },
      myData: {
        max: 20,
        finished: 36,
        unfinished: 14,
        caption: 10,
        detection: 16,
        segmentation: 6,
        attribute: 12,
        classification: 6,
        rate: [0,0,0,0,0]// 方、区、整、分、属
      },
      switchValue: {
        caption: true,
        detection: true,
        segmentation: true,
        attribute: true,
        classification: true,
        finished: true,
        unfinished: true
      },
      rules2: {
        newMail: [
          {validator: validateMail, trigger: 'blur'}
        ],
        newPhone: [
          {validator: validatePhone, trigger: 'blur'}
        ]
      }

    }
  },
  mounted () {
    var name = localStorage.getItem('username')
    var identify = localStorage.getItem('identify')

    if (identify == 'worker' && name != 'visitor' && name != 'undefined') {
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          if (JSON.parse(xmlhttp.responseText) != null) {
            var item = JSON.parse(xmlhttp.responseText)
            _this.user = item
            var info2 = {
              id: item.id,
              username: item.username,
              name: item.name,
              identify: item.role,
              email: item.email,
              phone: item.phone,
              info: item.info,
              level: item.level,
              level_d: '',
              points: item.points, // 积分
              rate: item.rate, // 评分
              province: item.province,
              city: item.city,
              avatar: item.avatar
            }

            _this.info = info2
            _this.personal_info = info2
            _this.newDetailsInfo = _this.info.info
            _this.newName = _this.info.name
            _this.connectform.newMail = _this.info.email
            _this.connectform.newPhone = _this.info.phone
            _this.headImage = info2.avatar
            _this.example2.img = info2.avatar
            _this.projectInfo = []
            _this.getAllProject()
            _this.getAllAutoProject()
          }
        }
      }
      let formData = new FormData()
      formData.append('username', localStorage.getItem('username'))
      xmlhttp.open('POST', 'http://localhost:8080/counts/user/getuser', true)
      xmlhttp.send(formData)
    }
  },

  methods: {
    getAllAutoProject(){
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          if (JSON.parse(xmlhttp.responseText) != null) {
            var arrays = JSON.parse(xmlhttp.responseText);
            console.log('automissions'+arrays)
            var cap = 0, seg = 0, dec = 0, attr = 0, cla = 0, fin = 0, unfin = 0
            for(var i =0;i<arrays.length;i++){
              _this.autoProjects.push(
                {
                  id: arrays[i].id,
                  missionname: arrays[i].missionName,
                  charts_id: '' + arrays[i].id + '_charts',
                  imgFinished: arrays[i].finished,
                  imgToDo: (arrays[i].size - arrays[i].finished),
                  type: arrays[i].type,
                  cover: '',
                  percent: 0,
                  annotationType: 1,
                  show: true
                }
              )
              if (arrays[i].finished == arrays[i].sum) {
                fin++
              } else {
                unfin++
              }
              var type = arrays[i].type
              if (type == 'Classification') {
                cla++
              } else if (type == 'Detection') {
                dec++
              } else if (type == 'Segmentation') {
                seg++
              } else if (type == 'Caption') {
                cap++
              } else if (type == 'Attribute') {
                attr++
              }
            }
            var L = [cla, dec, seg, cap, attr];
            var m = _this.max(L)
            _this.myData.max = ((parseInt('' + m / 10) + 1) * 10>_this.myData.max)?(parseInt('' + m / 10) + 1) * 10:_this.myData.max;
            _this.project_total += arrays.length;
            _this.myData.attribute += attr
            _this.myData.caption += cap
            _this.myData.classification += cla
            _this.myData.segmentation += seg
            _this.myData.detection += dec
            _this.myData.finished += fin
            _this.myData.unfinished += unfin

            for (var i = 0; i < _this.autoProjects.length; i++) {
              _this.autoProjects[i].percent = (_this.autoProjects[i].imgFinished / (_this.autoProjects[i].imgToDo + _this.autoProjects[i].imgFinished) * 100).toFixed(2)
              _this.getAutoFirstImage(_this.autoProjects[i].id, i);
            }

            _this.drawPieCharts()
            _this.drawRadarCharts()
            _this.drawLineCharts()
            _this.drawGradeCharts()
            _this.getMyRankInfo()
            _this.getRankingInfo()
          }
        }
      }
      let formData = new FormData()
      formData.append('username', localStorage.getItem('username'))
      xmlhttp.open('POST', 'http://localhost:8080/counts/mission/getAutoMission/worker', true)
      xmlhttp.send(formData)
    },

    getAllProject(){
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          if (JSON.parse(xmlhttp.responseText) != null) {
            var arrays = JSON.parse(xmlhttp.responseText)
            var cap = 0, seg = 0, dec = 0, attr = 0, cla = 0, fin = 0, unfin = 0
            if (arrays.length != 0) {
              _this.hasProject = true
            }
            for (var i = 0; i < arrays.length; i++) {
              _this.projectInfo.push({
                id: arrays[i].id,
                missionname: arrays[i].missionName,
                dateStart: arrays[i].begin,
                dateEnd: arrays[i].end,
                charts_id: '' + arrays[i].id + '_charts',
                imgFinished: arrays[i].finished,
                imgToDo: (arrays[i].sum - arrays[i].finished),
                type: arrays[i].type,
                cover: '',
                percent: 0,
                isEnd: false,
                isContinue: true,
                annotationType: 0,
                show: true
              })
              if (arrays[i].finished == arrays[i].sum) {
                fin++
              } else {
                unfin++
              }
              var type = arrays[i].type
              if (type == 'Classification') {
                cla++
              } else if (type == 'Detection') {
                dec++
              } else if (type == 'Segmentation') {
                seg++
              } else if (type == 'Caption') {
                cap++
              } else if (type == 'Attribute') {
                attr++
              }
            }
            var L = [cla, dec, seg, cap, attr];
            var m = _this.max(L)
            _this.myData.max = (parseInt('' + m / 10) + 1) * 10;
            _this.project_total += arrays.length;
            _this.myData.attribute += attr
            _this.myData.caption += cap
            _this.myData.classification += cla
            _this.myData.segmentation += seg
            _this.myData.detection += dec
            _this.myData.finished += fin
            _this.myData.unfinished += unfin

            for (var i = 0; i < _this.projectInfo.length; i++) {
              var time = _this.projectInfo[i].dateEnd.split('-');
              var end = new Date(time[0], parseInt(time[1]) - 1, time[2])
              var now = new Date()
              if (now < end) {
                _this.projectInfo[i].isEnd = false;
                _this.projectInfo[i].isContinue = true;
              } else {
                _this.projectInfo[i].isEnd = true;
                _this.projectInfo[i].isContinue = false;
              }
              _this.projectInfo[i].percent = (_this.projectInfo[i].imgFinished / (_this.projectInfo[i].imgToDo + _this.projectInfo[i].imgFinished) * 100).toFixed(2)

              _this.getCoverImg(_this.projectInfo[i].id, i);
            }

            _this.drawPieCharts()
            _this.drawRadarCharts()
            _this.drawLineCharts()
            _this.drawGradeCharts()
            _this.getMyRankInfo()
            _this.getRankingInfo()
          }
        }
      }
      let formData = new FormData()
      formData.append('username', localStorage.getItem('username'))
      xmlhttp.open('POST', 'http://localhost:8080/counts/mission/getAcceptedMission', true)
      xmlhttp.send(formData)
    },
    handleLabelTypeChange(val){
      if(val[0]=='man'){
        this.selectManLabel()
      }else if(val[0]=='auto'){
        this.selectAutoLabel()
      }else{
        for(var i=0;i<this.projectInfo.length;i++){
            this.projectInfo[i].show=true;
        }
        for(var i=0;i<this.autoProjects.length;i++){
          this.autoProjects[i].show=true;
        }
      }
    },
    selectManLabel(){
      for(var i=0;i<this.projectInfo.length;i++){
          this.projectInfo[i].show=true;
      }
      for(var i=0;i<this.autoProjects.length;i++){
        this.autoProjects[i].show=false;
      }
    },
    selectAutoLabel(){
      for(var i=0;i<this.projectInfo.length;i++){
        this.projectInfo[i].show=false;
      }
      for(var i=0;i<this.autoProjects.length;i++){
        this.autoProjects[i].show=true;
      }
    },

    getRankingInfo(){
      var _this = this
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var arrays = JSON.parse(xmlhttp.responseText)
          for(var i=0;i<arrays.length;i++){
            var user = arrays[i];
            var col = {
              id: user.id,
              name: user.name,
              exp: user.exp,
              area: user.city
            }
            if(user.province!=null&&user.province!=''){
              col.area = user.province+ '/' + col.area
            }
            _this.rankingTableData.push(col)
          }
        }
      }
      let formData = new FormData()
      formData.append('type', 'Worker')
      var path = localStorage.getItem('server')+'/counts/user/getuserrank'
      xmlhttp.open('POST',path, true)
      xmlhttp.send(formData)
    },
    getMyRankInfo(){
      var _this = this
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var res = xmlhttp.responseText;
          _this.myRanking = res;
          _this.getTotalNumber()
        }
      }
      let formData = new FormData()
      var str = localStorage.getItem('username');
      formData.append('username', str)
      formData.append('type', 'Worker')
      var path = localStorage.getItem('server')+'/counts/user/getcurrentuserrank'
      xmlhttp.open('POST',path, true)
      xmlhttp.send(formData)
    },
    getTotalNumber(){
      var _this = this
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var res = xmlhttp.responseText;
          _this.myRankingPercent = ((res-_this.myRanking)/res*100).toFixed(2)

        }
      }
      var path = localStorage.getItem('server')+'/counts/analysis/getnumber/worker'
      xmlhttp.open('GET',path, true)
      xmlhttp.send(null)
    },

    max(L){
      var m = L[0]
      for(var i =0;i<L.length;i++){
        if(L[i]>m){
          m = L[i]
        }
      }
      return m;
    },
    getAutoFirstImage(missionid,i){
      var cover = ''
      var _this = this
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          cover = xmlhttp.responseText
          _this.autoProjects[i].cover = cover
        }
      }
      let formData = new FormData()
      var str = '' + missionid
      formData.append('missionid', str)
      formData.append('username',localStorage.getItem('username'))
      var path = localStorage.getItem('server')+'/counts/image/get/firstautoimage'
      xmlhttp.open('POST',path, true)
      xmlhttp.send(formData)
    },
    getCoverImg (missionid, i) {
      var cover = ''
      var _this = this
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          cover = xmlhttp.responseText
          _this.projectInfo[i].cover = cover
        }
      }
      let formData = new FormData()
      var str = '' + missionid
      formData.append('missionid', str)
      var path = localStorage.getItem('server')+'/counts/mission/get/firstimage'
      xmlhttp.open('POST',path, true)
      xmlhttp.send(formData)
    },
    handleLocationChange (value) {
      if (value[0] == '110000' || value[0] == '120000' || value[0] == '310000' || value[0] == '500000') {
        this.newProvince = CodeToText[value[0]]
        this.newCity = CodeToText[value[0]]
      } else {
        this.newProvince = CodeToText[value[0]]
        this.newCity = CodeToText[value[1]]

      }
    },
    editDetailsSure () {
      if (this.newDetailsInfo != '') {
        this.personal_info.info = this.newDetailsInfo
      } else {
        this.personal_info = '未填写个人简述'
      }
      if (this.newCity != '') {
        this.personal_info.province = this.newProvince
        this.personal_info.city = this.newCity

        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (JSON.parse(xmlhttp.responseText).result == true || JSON.parse(xmlhttp.responseText).result.result == true) {
            _this.openSucc('Edit name successfully.')
          }
        }
        xmlhttp.open('POST', 'http://localhost:8080/counts/user/update/userdata', true)
        xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        var user = this.user
        user.city = this.newCity
        user.info = this.newDetailsInfo
        xmlhttp.send(JSON.stringify(user))
      }
      this.editDetailsVisible = false
    },

    editDetailsDialogShow () {
      this.editDetailsVisible = true
    },
    editNameSure () {
      if (this.newName != '') {
        this.personal_info.name = this.newName
        this.editNameVisible = false
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (JSON.parse(xmlhttp.responseText).result == true || JSON.parse(xmlhttp.responseText).result.result == true) {
            _this.openSucc('Edit name successfully.')
          }
        }
        xmlhttp.open('POST', 'http://localhost:8080/counts/user/update/userdata', true)
        xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        var user = this.user
        user.name = this.newName
        xmlhttp.send(JSON.stringify(user))
      }
    },
    handleProjectDetails (id) {
      localStorage.setItem('missionID', id)
      var path = '#/' + localStorage.getItem('username') + '/worker/' + id + '/projectDetails'
      window.open(path)
    },
    editNameDialogShow () {
      this.editNameVisible = true
    },

    editConnectSure () {
      this.personal_info.email = this.connectform.newMail
      this.personal_info.phone = this.connectform.newPhone
      this.editConnectVisible = false
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (JSON.parse(xmlhttp.responseText).result == true || JSON.parse(xmlhttp.responseText).result.result == true) {
          _this.openSucc('Edit name successfully.')
        }
      }
      xmlhttp.open('POST', 'http://localhost:8080/counts/user/update/userdata', true)
      xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
      var user = this.user
      user.email = this.connectform.newMail
      user.phone = this.connectform.newPhone
      xmlhttp.send(JSON.stringify(user))
    },

    editConnectDialogShow () {
      this.editConnectVisible = true
    },

    cancel () {
      this.dialogVisible = false
    },
    // 实时预览函数
    realTime (data) {
      this.previews = data
    },

    saveHeadImg (type) {
      this.$refs.cropper2.getCropData((data) => {
        this.headImage = data
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (JSON.parse(xmlhttp.responseText).result == true || JSON.parse(xmlhttp.responseText).result.result == true) {
            _this.openSucc('Edit name successfully.')
          }
        }
        xmlhttp.open('POST', 'http://localhost:8080/counts/user/update/userdata', true)
        xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        var user = this.user
        user.avatar = this.headImage
        xmlhttp.send(JSON.stringify(user))
      })

      this.dialogVisible = false
    },
    uploadImg (e, num) {
      // 上传图片
      // this.option.img
      var file = e.target.files[0]
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
        return false
      }
      var reader = new FileReader()
      reader.onload = (e) => {
        let data
        if (typeof e.target.result === 'object') {
          data = window.URL.createObjectURL(new Blob([e.target.result]))
        } else {
          data = e.target.result
        }
        if (num === 1) {
          this.option.img = data
        } else if (num === 2) {
          this.example2.img = data
        }
      }
      reader.readAsArrayBuffer(file)
    },
    imgLoad (msg) {    },
    allCancel () {
      this.switchValue.caption = false
      this.switchValue.detection = false
      this.switchValue.segmentation = false
      this.switchValue.attribute = false
      this.switchValue.classification = false
      this.switchValue.finished = false
      this.switchValue.unfinished = false
    },
    allSelected () {
      this.switchValue.caption = true
      this.switchValue.detection = true
      this.switchValue.segmentation = true
      this.switchValue.attribute = true
      this.switchValue.classification = true
      this.switchValue.finished = true
      this.switchValue.unfinished = true
    },
    handleSelect (key, keyPath) {
      if (key == 1) {
        this.project_show = true
        this.data_show = false
        this.info_show = false
      } else if (key == 2) {
        this.project_show = false
        this.data_show = true
        this.info_show = false
      } else if (key == 3) {
        this.project_show = false
        this.data_show = false
        this.info_show = true
      }
    },
    openProject (item) {
      localStorage.setItem('missionType', item.type)
      localStorage.setItem('missionID', item.id)
      if(item.annotationType==1){
        localStorage.setItem('isAutoLabel', '1')
        var path = '/' + localStorage.getItem('username')
        this.$router.push({path: path + '/autoLabel'})
      }else{
        localStorage.setItem('isAutoLabel', '0')
        var path = '/' + localStorage.getItem('username')
        this.$router.push({path: path + '/worker'})
      }
      window.location.reload()
    },
    drawPieCharts () {
      let myChart = echarts.init(document.getElementById('pie'))
      myChart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: ['已完成', '未完成']
        },
        toolbox: {
          show : true,
          x:360,
          feature : {
            saveAsImage : {
              show : true,
              name: '项目完成情况',
              title : '保存图片',
            },
          }
        },
        color: [ '#248ce4', '#e97506'],
        series: [{
          name: '完成情况',
          type: 'pie',
          radius: '60%',
          center: ['50%', '60%'],
          data: [
            {value: this.myData.finished, name: '已完成'},
            {value: this.myData.unfinished, name: '未完成'}
          ]
        }
        ]
      })
    },
    drawGradeCharts () {
      let myChart = echarts.init(document.getElementById('grade'), 'shine')
      myChart.setOption({
        tooltip : {
          trigger: 'axis',
          formatter: function (params) {
            var res = params[0].name+'排名';
            for (var i = 0, l = params.length; i < l; i++) {
              res += '<br/>' +params[i].seriesName+ ' 第 ' + (-params[i].value)+ ' 名';
            }
            return res;
          }
        },
        legend: {
          data:['按积分','按正确率','按项目数']
        },
        toolbox: {
          show : true,
          x:415,
          feature : {
            saveAsImage : {
              show : true,
              name: '群体排名',
              title : '保存图片',
            },
          }
        },
        xAxis : [
          {
            axisLine: {onZero: false},
            type : 'category',
            boundaryGap : false,
            data : ['一月','二月','三月','四月','五月','六月','七月','八月','九月'
              ,'十月','十一月','十二月']
          }
        ],
        yAxis : [
          {
            type : 'value',
            axisLabel : {
              formatter: function(v){
                return - v;
              }
            }
          }
        ],
        series : [
          {
            name:'按积分',
            type:'line',
            stack: '总量',
            data:[-10,-932,-901,-934, -1290, -1330,-932,-901,-934, -1290, -1330,-1320]
          },
          {
            name:'按正确率',
            type:'line',
            stack: '总量',
            data:[-130,-532,-301,-534, -890, -1630,-132,-401,-934, -1290, -1330,-1320]
          },
          {
            name:'按项目数',
            type:'line',
            stack: '总量',
            data:[-460,-532,-301,-534, -890, -1630,-132,-401,-934, -1290, -1330,-1320]
          }
        ]
      })
    },
    drawRadarCharts () {
      let myChart = echarts.init(document.getElementById('radar'))
      myChart.setOption({
        tooltip: {
          trigger: 'item'
        },
        color: '#248CE4',
        lineStyle: {
          color: '#220de4'
        },
        toolbox: {
          show : true,
          x:360,
          feature : {
            saveAsImage : {
              show : true,
              name: '类型分布',
              title : '保存图片',
            },
          }
        },
        polar: [
          {
            indicator: [
              {text: '方框标注', max: this.myData.max},
              {text: '区域标注', max: this.myData.max},
              {text: '整体描述', max: this.myData.max},
              {text: '图像分类', max: this.myData.max},
              {text: '属性标注', max: this.myData.max}
            ],
            radius: '70%',
            center: ['50%', '55%']
          }
        ],
        series: [
          {
            name: '',
            type: 'radar',
            itemStyle: {
              normal: {
                areaStyle: {
                  type: 'default'
                }
              }
            },
            data: [
              {
                value: [this.myData.detection, this.myData.segmentation, this.myData.caption,
                  this.myData.classification, this.myData.attribute],
                name: '类型分布'

              }
            ]
          }
        ]
      })
    },
    drawLineCharts () {
      let myChart = echarts.init(document.getElementById('line'))
      myChart.setOption({
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          show : true,
          x:360,
          feature : {
            saveAsImage : {
              show : true,
              name: '项目正确率',
              title : '保存图片',

            },
          }
        },
        polar: [
          {
            indicator: [
              {text: '方框标注', max: 100},
              {text: '区域标注', max: 100},
              {text: '整体描述', max: 100},
              {text: '图像分类', max: 100},
              {text: '属性标注', max: 100}
            ],
            radius: '70%',
            center: ['50%', '55%']
          }
        ],
        color: '#33d319',
        series: [
          {
            name: '',
            type: 'radar',
            itemStyle: {
              normal: {
                areaStyle: {
                  type: 'default'
                }
              }
            },
            data: [
              {
                value: [this.myData.rate[0], this.myData.rate[1], this.myData.rate[2],
                  this.myData.rate[3], this.myData.rate[4]],
                name: '正确率'
              }
            ]
          }
        ]
      })
    }
  }
}

</script>

<style scoped>
  .imgOnClick {
    width: 100%;
    height: 60%;
    filter: alpha(Opacity=50);
    -moz-opacity: 0.6;
    opacity: 0.6;
    z-index: 100;
    background-color: #ffffff;
    display: none;
    position: absolute;
    left: 0px;
    top:5px;
  }

  .el_card{
    margin: 10px;
    padding: 5px;
    width: 260px;
    height:245px;
    float: left;
    position: relative;
  }
  .el_card:hover .imgOnClick{
    display:block;
  }
  .el_card:hover{

  }
  .time{
    left: 10px;
    font-size: 14px;
    color: #999;
  }
  .card_button{
    padding: 0px;
    float: right;
  }
  .image{
    width: 100%;
    display: block;
  }
  * {
    margin: 0;
    padding: 0;
  }

  .btn {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #fff;
    border: 1px solid #c0ccda;
    color: #1f2d3d;
    text-align: center;
    box-sizing: border-box;
    outline: none;
    margin:20px 10px 0px 0px;
    padding: 9px 15px;
    font-size: 14px;
    border-radius: 4px;
    color: #fff;
    background-color: #50bfff;
    border-color: #50bfff;
    transition: all .2s ease;
    text-decoration: none;
    user-select: none;
  }

  .show-info {
    margin-bottom: 50px;
  }

  .test {
    height: 300px;
    width: 400px;
  }

  .model img {
    display: block;
    margin: auto;
    max-width: 80%;
    user-select: none;
    background-position: 0px 0px, 10px 10px;
    background-size: 20px 20px;
    background-image: linear-gradient(45deg, #eee 25%, transparent 25%, transparent 75%, #eee 75%, #eee 100%),linear-gradient(45deg, #eee 25%, white 25%, white 75%, #eee 75%, #eee 100%);
  }

  @keyframes slide {
    0%  {
      background-position: 0 0;
    }
    100% {
      background-position: -100% 0;
    }
  }

  #edit_name{
    display: none;
  }

  #account_info:hover #edit_name{
    display: block;
  }
  #edit_details{
    display: none;
  }

  #details_info:hover #edit_details{
    display: block;
  }
  #edit_connect{
    display: none;
  }

  #connect_info:hover #edit_connect{
    display: block;
  }
  .labelType{
    width:100px;
    padding-top: 3px;
    height: 24px;
    border-radius: 15px;
    color: white;
    background-color: #2ea2ff;
    font-size: 13px;
    cursor: pointer;
  }
  .labelType:hover{
    color:white;
    cursor: pointer;
  }
</style>
