<template>
  <div>
    <div style="position: absolute; top: 57px; left: 0px; width: 100%; height: 140px;">
      <div style="position: absolute; left: 50%; width: 260px;margin-left: -180px">
        <div style="position: relative;">
          <!--
          <span style="position:absolute; top: 10px; color: #d81e06; left: 0px;">筛选</span>
          <img src="../assets/shaixuan.png" style="position: absolute;width:20px; top:12px; left: 35px;">
          <div>
            <span style="position: absolute;font-size: 14px; left: 100px; top: 12px;">标注类型:</span>
            <el-button type="text" style="position: absolute; left: 180px; top: 1px;">默认</el-button>
            <span style="position: absolute; left: 230px; top: 11px;">|</span>
            <el-button type="text" style="position: absolute; left: 250px; top: 1px;">整体描述</el-button>
            <span style="position: absolute; left: 320px; top: 11px;">|</span>
            <el-button type="text" style="position: absolute; left: 340px; top: 1px;">方框标注</el-button>
            <span style="position: absolute; left: 410px; top: 11px;">|</span>
            <el-button type="text" style="position: absolute; left: 430px; top: 1px;">区域标注</el-button>
            <span style="position: absolute; left: 500px; top: 11px;">|</span>
            <el-button type="text" style="position: absolute; left: 520px; top: 1px;">图像分类</el-button>
            <span style="position: absolute; left: 590px; top: 11px;">|</span>
            <el-button type="text" style="position: absolute; left: 610px; top: 1px;">属性标注</el-button>
          </div>
          <!--
          <div>
            <span style="position: absolute;font-size: 14px; left: 103px; top: 45px;">积 分</span>
            <el-input-number v-model="count" @change="handleChange" :min="5" :max="20" size="mini" style="position: absolute; left: 170px; top: 40px; "></el-input-number>
            <el-button type="text" style="position: absolute; left: 310px; top: 33px;">恢复默认</el-button>
          </div>
          <div>
            <span style="position: absolute;font-size: 14px; left:103px; top: 78px;">时 间</span>
            <el-date-picker @change="handleTimeChange" style="position: absolute; left: 170px; top: 76px;" size="mini" v-model="timepart" type="daterange" range-separator="-" start-placeholder="起始日期" end-placeholder="截止日期"></el-date-picker>
          </div>

          <div>
            <span style="position:absolute; top: 10px; color: #d81e06; left: 0px;">排序</span>
            <img src="../assets/sort.png" style="position: absolute;width:20px; top:12px; left: 35px;">
            <div >
              <el-button type="text" style="position: absolute; left: 101px; top: 1px;">综合</el-button>
              <span style="position: absolute; left: 150px; top: 10px;">|</span>
              <el-button type="text" style="position: absolute; left: 170px; top: 1px;">积分 <i v-bind:class="countIcon"></i></el-button>
              <span style="position: absolute; left: 220px; top: 10px;">|</span>
              <el-button type="text" style="position: absolute; left: 240px; top: 1px;">时间 <i v-bind:class="timeIcon"></i></el-button>
              </div>
          </div>
           -->
        </div>
      </div>
    </div>

    <div style="position: absolute; top: 80px; left: 4%;width: 92%;height:370px;">
      <el-carousel height="370px">
        <el-carousel-item>
          <img src="../assets/carouse1.png" style="width: 100%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../assets/casourse2.png" style="width: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="position: absolute; top:470px; width:100%;height: 70px;">
      <span style="font-size: 32px; color: black">— 标 / 注 / 项 / 目 —</span>
      <el-button type="text" icon="el-icon-refresh" style="position:absolute;font-size: 16px; margin-left: 10px;margin-top: 3px" v-bind:onclick="getRecommandMission">换一批</el-button>
    </div>

    <div style="position: absolute; top:560px; width:100%;">
      <div style="float: left;width:92%; margin-left: 4%;">
        <div v-for="item in projects" :key="item">
          <el-card class="el_card" :body-style="{padding:'0px'}">
            <div style="position: relative;">
              <img style="width: 98%" v-bind:src="item.cover" class="image" v-bind:id="item.missionname">
              <el-tag v-show="item.isContinue" style="position: absolute; left: 20px; top: 10px; color:white; " color="#4CAF50">进行中...</el-tag>
              <el-tag v-show="item.isEnd" style="position: absolute; left: 20px; top: 10px; color:white; " color="#E97506">已满员</el-tag>
              <el-tag v-show="item.isEnd2" style="position: absolute; left: 20px; top: 10px; color:white;width: 60px " color="#E97506">已截止</el-tag>
              <div style="position:absolute; top: 150px;bottom:5px; right:1%; left: 1%; background-color: white">
                <span style="font-size: 20px; color: #4CAF50; width:100%">{{item.missionname}}</span>
                <span class="time" style="position: absolute; left: 5px;top: 25px;font-size: 14px;">时间：{{item.dateStart}} - {{item.dateEnd}}</span>
                <span style="position: absolute; left: 5px;top: 47px;font-size: 14px;">类型：{{item.type}}</span>
                <span style="position: absolute; left: 140px;top: 47px;font-size: 14px;">积分：{{item.counts}}</span>
                <span style="position: absolute; left: 5px;top: 69px;font-size: 14px;">参与人数：{{item.worker_now_number}} / {{item.worker_total_number}}</span>
                <div style="position: absolute; left: 5px;top: 95px; width: 320px">
                  <div style="float: left">
                    <div style="float: left"  v-for="tag in item.tags">
                      <div class="itemn" style="float: left">
                        <span>{{tag}}</span>
                      </div>
                    </div>
                  </div>
                </div>
                <el-progress style="position: absolute; top: 72px; left:140px;width: 170px; color: black;font-size: 12px;" :percentage="item.percent" :stroke-width="16" :text-inside="true"></el-progress>
              </div>
              <div class="imgOnClick" >
                <img @click="handleProjectDetails(item.id)" src="../assets/img_1.png" style="width: 323px; position: absolute; left: -3px;top: 0px;height: 299px">
                <p style="color:white;position: absolute; top: 0px; left: 5%; width: 90%;text-align: justify; font-size: 14px">{{item.details}}</p>
                <el-button class="btn" style="position: absolute; left: 50px; bottom: 0px; color: white" type="text" @click="handleProjectDetails(item.id)">查看详情</el-button>
                <el-button class="btn2" v-bind:id="item.id" @click="joinProject(item.id)" style="display:block;position: absolute; right: 50px; bottom: 0px; color: white;" type="text" v-bind:disabled="item.isBan">参与项目</el-button>
              </div>
            </div>
          </el-card>
        </div>
      </div>
      <div v-show="!isRecommand" style="float: left; width: 100%;">
        <el-pagination @current-change="handleCurrentChange" background :current-page=1 :page-size="12"
                       layout="total, prev, pager, next, jumper" v-bind:total="project_total">
        </el-pagination>
      </div>
    </div>
    <div>
      <ul>
        <li><el-button class="myProject" type="text" @click="goMyProject">个人<br>中心</el-button></li>
        <li>
          <a href="">返回首页</a>
        </li>
        <li>
          <a href="javascript:window.scrollTo(0,0);">返回顶部</a>
        </li>
      </ul>
    </div>

  </div>

</template>

<script>
  export default {
    name: 'CountsMarket',
    data () {
      return {
        isRecommand:false,
        recommandType:0,
        project_total: 0,
        count: 1,
        timepart: '',
        countIcon: 'el-icon-sort-down',
        timeIcon: 'el-icon-sort-down',
        hotIcon: 'el-icon-sort-down',
        project_info: {
          cover: 'http://localhost:8080/static/img/home_back.00f1c22.png',
          missionname: 'mission',
          counts: 5,
          details: '根据标注需求，对图像中的目标物体进行画框，比如图像中的车辆、车牌、行人、道路、建筑、船只、文字、人体部位等画框并打上对应标签，以跟ImageNet同样的XML格式输出数据。',
          dateStart: '2018/04/12',
          dateEnd: '2018/09/27',
          worker_total_number: 30,
          worker_now_number: 16,
          percent: (16 / 30 * 100).toFixed(2),
          type: '方框标注',
          id: '001002',
          isContinue:true,
          isEnd: false,
          isBan:true,
        },
        projects:[]
      }
    },
    created () {
      if(localStorage.getItem('username') == 'visitor'){
        this.getAllMissionLogOut()
      }else{
        this.getRecommandMission()
      }
    },
    methods: {
      getRecommandMission(){
        this.isRecommand=true;
        this.projects= []
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            console.log(JSON.parse(xmlhttp.responseText))
            _this.recommandType = JSON.parse(xmlhttp.responseText).type
            var arrays = JSON.parse(xmlhttp.responseText).missions
            _this.project_total = arrays.length
            for (var i = 0; i < arrays.length; i++) {
              var e = arrays[i]
              var info = {
                cover: '',
                missionname: e.missionName,
                counts: e.points,
                details: e.description,
                dateStart: e.begin,
                dateEnd: e.end,
                worker_total_number: e.maxNumber,
                worker_now_number: e.currentNumber,
                percent: (e.currentNumber / e.maxNumber * 100).toFixed(2),
                type: e.type,
                id: e.id,
                isContinue:true,
                isEnd:false,
                isEnd2: false,
                tags:e.tags
              }
              _this.projects.push(info)
            }
            for(var i=0;i<_this.projects.length;i++){
              var time = _this.projects[i].dateEnd.split('-');
              var end = new Date(time[0], parseInt(time[1])-1, parseInt(time[2])+1)
              var now = new Date()
              if(now <= end){
                _this.projects[i].isEnd2=false;
                if(_this.projects[i].worker_now_number<_this.projects[i].worker_total_number) {
                  _this.projects[i].isContinue = true;
                  _this.projects[i].isEnd = false;
                }else{
                  _this.projects[i].isContinue = false;
                  _this.projects[i].isEnd = true;
                }
              }else{
                _this.projects[i].isEnd2 = true;
                _this.projects[i].isEnd = false;
                _this.projects[i].isContinue = false;
              }
              _this.projects[i].isBan = !_this.projects[i].isContinue;
              if(localStorage.getItem('identify')=='requester'){
                _this.projects[i].isBan = true;
              }
              _this.getCoverImg(_this.projects[i].id, i);
            }
          }
        }
        let formData = new FormData()
        console.log(localStorage.getItem('username'))
        formData.append('username',localStorage.getItem('username'))
        xmlhttp.open('POST', 'http://localhost:8080/counts/recommend/mission', true)
        xmlhttp.send(formData)

      },
      getAllMissionLogOut(){
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            var arrays = JSON.parse(xmlhttp.responseText)
            _this.project_total = arrays.length
            for (var i = 0; i < arrays.length; i++) {
              var e = arrays[i]
              var info = {
                cover: '',
                missionname: e.missionName,
                counts: e.points,
                details: e.description,
                dateStart: e.begin,
                dateEnd: e.end,
                worker_total_number: e.maxNumber,
                worker_now_number: e.currentNumber,
                percent: (e.currentNumber / e.maxNumber * 100).toFixed(2),
                type: e.type,
                id: e.id,
                isContinue:true,
                isEnd:false,
                isEnd2: false,
                tags:e.tags
              }

              _this.projects.push(info)
            }
            for(var i=0;i<_this.projects.length;i++){
              var time = _this.projects[i].dateEnd.split('-');
              var end = new Date(time[0], parseInt(time[1])-1, parseInt(time[2])+1)
              var now = new Date()
              if(now <= end){
                _this.projects[i].isEnd2=false;
                if(_this.projects[i].worker_now_number<_this.projects[i].worker_total_number) {
                  _this.projects[i].isContinue = true;
                  _this.projects[i].isEnd = false;
                }else{
                  _this.projects[i].isContinue = false;
                  _this.projects[i].isEnd = true;
                }
              }else{
                _this.projects[i].isEnd2 = true;
                _this.projects[i].isEnd = false;
                _this.projects[i].isContinue = false;
              }
              _this.projects[i].isBan = !_this.projects[i].isContinue;
              if(localStorage.getItem('identify')=='requester'){
                _this.projects[i].isBan = true;
              }
              _this.getCoverImg(_this.projects[i].id, i);
            }
          }
        }
        let formData = new FormData()
        formData.append('i', '1')
        xmlhttp.open('POST', 'http://localhost:8080/counts/mission/getmission', true)
        xmlhttp.send(formData)
      },
      getCoverImg (missionid, i) {
        var cover = ''
        var _this = this
        var xmlhttp = new XMLHttpRequest()
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            cover = xmlhttp.responseText
            _this.projects[i].cover = cover
          }
        }
        let formData = new FormData()
        var str = '' + missionid
        formData.append('missionid', str)
        var path = localStorage.getItem('server')+'/counts/mission/get/firstimage'
        xmlhttp.open('POST',path, true)
        xmlhttp.send(formData)
      },

      handleCurrentChange (val) {
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            var arrays = JSON.parse(xmlhttp.responseText)
            _this.project_total = arrays.length
            for (var i = 0; i < arrays.length; i++) {
              var e = arrays[i]
              var info = {
                cover: '',
                missionname: e.missionName,
                counts: e.points,
                details: e.description,
                dateStart: e.begin,
                dateEnd: e.end,
                worker_total_number: e.maxNumber,
                worker_now_number: e.currentNumber,
                percent: (e.currentNumber / e.maxNumber * 100).toFixed(2),
                type: e.type,
                id: e.id,
                isContinue:true,
                isEnd:false,
                isEnd2: false,
                tags:e.tags
              }

              _this.projects.push(info)
            }
            for(var i=0;i<_this.projects.length;i++){
              var time = _this.projects[i].dateEnd.split('-');
              var end = new Date(time[0], parseInt(time[1])-1, parseInt(time[2])+1)
              var now = new Date()
              if(now <= end){
                _this.projects[i].isEnd2=false;
                if(_this.projects[i].worker_now_number<_this.projects[i].worker_total_number) {
                  _this.projects[i].isContinue = true;
                  _this.projects[i].isEnd = false;
                }else{
                  _this.projects[i].isContinue = false;
                  _this.projects[i].isEnd = true;
                }
              }else{
                _this.projects[i].isEnd2 = true;
                _this.projects[i].isEnd = false;
                _this.projects[i].isContinue = false;
              }
              _this.projects[i].isBan = !_this.projects[i].isContinue;
              if(localStorage.getItem('identify')=='requester'){
                _this.projects[i].isBan = true;
              }
              _this.getCoverImg(_this.projects[i].id, i);
            }
          }
        }
        let formData = new FormData()
        formData.append('i', ''+val)
        xmlhttp.open('POST', 'http://localhost:8080/counts/mission/getmission', true)
        xmlhttp.send(formData)
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
      goMyProject () {

        var path = '/' + localStorage.getItem('username')
        if (localStorage.getItem('identify') == 'worker') {
          this.$router.push({path: path + '/myProject'})
        } else if (localStorage.getItem('identify') == 'requester') {
          this.$router.push({path: path + '/requester'})
        } else if (localStorage.getItem('identify') == 'logout' || localStorage.getItem('username') == 'visitor') {
          this.openInfo("You haven't logged in")
        }
        window.location.reload()
      },
      joinProject (id) {
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
            console.log('return info')
            if (JSON.parse(xmlhttp.responseText).result == true) {
              _this.openSucc('操作成功！')
            }else{
              _this.openInfo('您已经加入该项目');
              for(var i=0;i<_this.projectInfo.length;i++){
                if(_this.projectInfo[i].id==id){
                  _this.openProject(_this.projectInfo[i]);
                  break;
                }
              }
            }
          }
        }
        let formData = new FormData()
        var str = '' + id
        var name = localStorage.getItem('username')
        formData.append('username',name)
        formData.append('missionID', str)
        formData.append('recommendType', ''+this.recommandType);
        var path = localStorage.getItem('server')+'/counts/mission/addAcceptMission'
        xmlhttp.open('POST', path, true)
        xmlhttp.send(formData)
      },
      openProject (item) {
        localStorage.setItem('missionType', item.type)
        localStorage.setItem('missionID', item.id)
        var path = '/' + localStorage.getItem('username')
        this.$router.push({path: path + '/worker'})
        window.location.reload()
      },
      handleProjectDetails (id) {
        localStorage.setItem('missionID', id)
        var path = '#/' + localStorage.getItem('username') + '/countsMarket/' + id + '/projectDetails'
        window.open(path)
      },

      handleChange (value) {

      },
      handleTimeChange () {

      }

    }
  }
</script>

<style scoped>
  .imgOnClick {
    width: 96%;
    height:270px;
    display: none;
    position: absolute;
    left: 2%;
    top: 0px;
  }

  .el_card:hover .imgOnClick{
    display:block;
  }

  .el_card{
    border-radius: 0;
    margin: 15px;
    padding: 5px;
    width: 330px;
    height: 300px;
    float: left;
    position: relative;
  }
  .itemn{
    font-size: 13px;
    color: #ff8432;
    margin: 2px;
    height: 18px;
    border: 1px solid #ff8432;
    border-radius: 15px;
    float: left;
    padding-left: 8px;
    padding-right: 8px;
    padding-top:1px;
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
  .btn:hover{
    text-decoration: underline;
  }
  .btn2:hover{
    text-decoration: underline;
  }
</style>
