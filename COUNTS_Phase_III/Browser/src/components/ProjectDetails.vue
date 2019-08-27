<template>
  <div>
    <div style="position: fixed; top: 57px; left: 0px; width: 300px; height: 550px; border-bottom-left-radius: 1px; border-color: grey;" >
      <happy-scroll class="happy_scroll" color="rgba(51,51,51,0.2)" hide-vertical="false">
        <div v-for="item of imgList" :key="item">
          <img style="width: 100%;" :src="item">
        </div>
      </happy-scroll>
    </div>
    <div style="position: absolute; top: 57px; left:300px; width: 800px; height: 300px;">
      <div style="position: absolute; left: 20px;top: 3px;">
        <span style="color:#4CAF50;font-size: 32px;float: left; margin-right: 10px">{{projectInfo.name}} </span>
        <el-tag v-show="isContinue" style="float:left;color:white; margin-top: 4px" color="#4CAF50">进行中...</el-tag>
        <el-tag v-show="isEnd" style="float:left;color:white; margin-top: 4px" color="#E97506">已满员</el-tag>
        <el-tag v-show="isEnd2" style="float:left;color:white; margin-top: 4px" color="#E97506">已截止</el-tag>
      </div>
      <div style="position: absolute; left: 20px; top: 40px;">
        <span style="position:absolute;left:0px; font-size: 30px; font-weight: bold; margin: 10px;margin-left: 0px;margin-bottom: 30px; margin-top: 0px">“</span>
        <p style="position: absolute; left: 25px; width: 680px; top:00px; text-align: justify; font-size: 14px; color: #5b5b5b">{{projectInfo.details}}</p>
        <span style="position:absolute; font-size: 30px; font-weight: bold; left: 700px; top:50px;">”</span>
      </div>
      <div style="position: absolute;top: 120px;left: 30px;">时间：<span style="font-size: 15px">{{projectInfo.dateStart}} - {{projectInfo.dateEnd}}</span></div>
      <div style="position: absolute;top: 120px;left: 290px;">积分：<span style="color: #f8b62d;font-size: 17px;font-weight: bold">{{projectInfo.points}}</span></div>
      <div style="position: absolute;top: 120px;left: 400px;">类型：{{projectInfo.type}}</div>
      <div style="position: absolute;top: 150px;left: 30px;">参与人数：{{projectInfo.worker_now_number}} / {{projectInfo.worker_total_number}}</div>
      <div style="position: absolute;top: 150px;left: 220px;">图片数量：共 {{imgListLen}} 张</div>
    </div>
    <div style="position: absolute; top: 260px; left: 330px; height: 300px; width: 850px;">
      <el-carousel style="position: absolute; left: 0px; top: 0px; width: 870px" :interval="4000" type="card" height="300px">
        <el-carousel-item v-for="item in imgList" :key="item">
          <img style="width:500px" v-bind:src="item">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div>
      <ul>
        <li><el-button class="myProject" type="text" @click="goCenter">个人<br>中心</el-button></li>
        <li>
          <a v-bind:href="marketHref">众包市场</a>
        </li>
        <li>
          <a href="">返回首页</a>
        </li>
      </ul>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'counts-details',
    data(){
      return{
        isCanJoin: true,
        marketHref: '',
        isContinue:true,
        isEnd:false,
        isEnd2:false,
        imgList: [],
        imgListLen: 0,
        projectInfo:{
          name: '',
          id: '',
          details: '' ,
          dateStart:'',
          dateEnd: '',
          type:'',
          points: 0,
          worker_now_number: 0,
          worker_total_number:0,
          selects: []
        }
      }
    },
    created(){
      var username = localStorage.getItem('username');
      this.marketHref = '#/'+username+'/countsMarket'
      var missionID = localStorage.getItem('missionID');
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var mission = JSON.parse(xmlhttp.responseText)
          _this.projectInfo = {
            name: mission.missionName,
            id: mission.ID,
            details: mission.description,
            dateStart:mission.begin,
            dateEnd: mission.end,
            type:mission.type,
            points: mission.points,
            worker_now_number: mission.currentNumber,
            worker_total_number:mission.maxNumber,
            selects: []
          }
          if(_this.projectInfo.type=='Classification'||_this.projectInfo.type=='Attribute'){
            _this.projectInfo.selects = mission.selects;
            _this.projectInfo.details += ' (';
            if(_this.projectInfo.type=='Classification'){
              _this.projectInfo.details += '类别包括：';
            }else{
              _this.projectInfo.details += '属性包括：';
            }
            console.log(_this.projectInfo.selects)
            for(var i =0;i<_this.projectInfo.selects.length-1;i++){
              _this.projectInfo.details += _this.projectInfo.selects[i];
              _this.projectInfo.details += '、';
            }
            _this.projectInfo.details += _this.projectInfo.selects[_this.projectInfo.selects.length-1];
            _this.projectInfo.details += ')';
          }
          var time = _this.projectInfo.dateEnd.split('-');
          var end = new Date(time[0], parseInt(time[1])-1, parseInt(time[2])+1)
          var now = new Date()
          if(now <= end){
            _this.isEnd2=false;
            if(_this.projectInfo.worker_now_number<_this.projectInfo.worker_total_number) {
              _this.isContinue = true;
              _this.isEnd = false;
            }else{
              _this.isContinue = false;
              _this.isEnd = true;
            }
          }else{
            _this.isEnd2 = true;
            _this.isEnd = false;
            _this.isContinue = false;
          }
          _this.getAllImg()
          if(localStorage.getItem('identify')=='worker'){
            _this.isCanJoin = false;
          }
        }
      }
      let formData = new FormData()
      formData.append('id', ''+missionID)
      xmlhttp.open('POST', 'http://localhost:8080/counts/mission/findmission/id', true)
      xmlhttp.send(formData)
    },
    methods: {
      goCenter(){
        console.log('test    test')
        var path = '/' + localStorage.getItem('username')
        if (localStorage.getItem('identify') == 'worker') {
          this.$router.push({path: path + '/myProject'})
        } else if (localStorage.getItem('identify') == 'requester') {
          this.$router.push({path: path + '/requester'})
        } else if (localStorage.getItem('identify') == 'logout' || localStorage.getItem('username') == 'visitor') {
          this.openInfo("You haven't logged in")
        }
      },
      getAllImg(){
        var missionID = localStorage.getItem('missionID');
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            _this.imgList = JSON.parse(xmlhttp.responseText)
            _this.imgListLen = _this.imgList.length
          }
        }
        let formData = new FormData()
        formData.append('mission', ''+missionID)
        xmlhttp.open('POST', 'http://localhost:8080/counts/image/mission', true)
        xmlhttp.send(formData)
      }
    },
    openSucc: function (text) {
      this.$notify({
        title: '成功',
        message: text,
        type: 'success',
        duration: 2000,
        position: 'top-left'
      })
    },
    openInfo (text) {
      this.$notify({
        title: '消息',
        message: text,
        type: 'info',
        duration: 2000,
        position: 'top-left'
      })
    },

    joinProject () {
      var id = localStorage.getItem('missionID');
      if (localStorage.getItem('identify') == 'requester') {
        this.openInfo("Sorry, you can't join this project as a requester.")
        return;
      } else if (localStorage.getItem('identify') == 'logout') {
        this.openInfo("You haven't logged in. Please login first.")
        return;
      }
      var xmlhttp = new XMLHttpRequest()
      var _this = this
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          if (JSON.parse(xmlhttp.responseText).result == true) {
            _this.openSucc('操作成功！')
          }else{
            _this.openInfo('您已经加入该项目')
          }
        }
      }
      let formData = new FormData()
      var str = '' + id
      formData.append('missionid', str)
      var name = localStorage.getItem('userid')
      formData.append('userid',name)
      console.log(name)
      var path = localStorage.getItem('server')+'/counts/user/acceptmission'
      xmlhttp.open('POST', path, true)
      xmlhttp.send(formData)
    },

  }
</script>

<style scoped>
  .happy_scroll{
    position: absolute;
    top: 0px;
    left: 0px;
    width:100%;
  }
  ul{
    position: fixed;
    right: 0px;
    top: 230px;
    font-size: 14px;
    list-style-type: none;
    margin: 0;
    padding: 0px;
    width: 53px;
    background-color: #f1f1f1;
  }
  li{
    height: 60px;
  }
  li:hover{
  }
  li a {
    display: block;
    color: #000;
    padding: 8px;
    text-decoration: none;
  }
  li a:hover:not(.active){
    background-color:  #4CAF50;
    color: white;
  }
  li .myProject{
    line-height: 1.5;
    padding:8px;
    border-radius: 0;
    width: 53px;
    font-size: 14px;
    color: #000;
  }
  li button:hover:not(.active){
    background-color:  #4CAF50;
    color: white;
  }
</style>
