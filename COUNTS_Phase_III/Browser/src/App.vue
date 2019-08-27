<template>
  <div id="app">
    <div class="header">
      <img class="logoIcon" @click="goHome" src="./assets/logo.png">
      <el-button class="zhongbaosc" @click="goMarket" type="text" >众包市场</el-button>
      <el-button class="wodexm" @click="goMyProject" type="text">个人中心</el-button>
      <el-badge v-show="show" v-bind:value="unreadInfoNumber" class="item">
        <el-button @click="readMessage" class="message" type="text"><i class="el-icon-ali-xinxi-copy" style="font-size: 22px"></i></el-button>
      </el-badge>
      <el-button @click="readMessage" class="message2" type="text"><i class="el-icon-ali-xinxi-copy" style="font-size: 22px"></i></el-button>
      <el-button class="login_button" type="text" @click="login_button_action()">登陆</el-button>
      <el-dropdown @command="handleCommand" class="headDropDown">
        <img id="userImg" class="el-dropdown-link" v-bind:src="headImg">
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登陆</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <router-view/>
  </div>

</template>

<script>
  import ElContainer from 'element-ui/packages/container/src/main'
  import ElHeader from 'element-ui/packages/header/src/main'
  import store from './vuex/store'
  import HeadImg from '@/assets/userDefault.png'
  export default {
    components: {
      ElHeader,
      ElContainer
    },
    store,
    vuex: {
    },
    name: 'App',
    data: function () {
      return {
        show:false,
        sakura_show:true,
        headImg: HeadImg,
        unreadInfoNumber:0
      }
    },
    created () {
      console.log(
        '          _____                   _______                   _____                    _____             _____                            _____                                  \n' +
        '         /\\    \\                 /::\\    \\                 /\\    \\                  /\\    \\           /\\    \\                          /\\    \\                 ______          \n' +
        '        /::\\    \\               /::::\\    \\               /::\\____\\                /::\\____\\         /::\\    \\                        /::\\    \\               |::|   |         \n' +
        '       /::::\\    \\             /::::::\\    \\             /:::/    /               /::::|   |         \\:::\\    \\                      /::::\\    \\              |::|   |         \n' +
        '      /::::::\\    \\           /::::::::\\    \\           /:::/    /               /:::::|   |          \\:::\\    \\                    /::::::\\    \\             |::|   |         \n' +
        '     /:::/\\:::\\    \\         /:::/~~\\:::\\    \\         /:::/    /               /::::::|   |           \\:::\\    \\                  /:::/\\:::\\    \\            |::|   |         \n' +
        '    /:::/  \\:::\\    \\       /:::/    \\:::\\    \\       /:::/    /               /:::/|::|   |            \\:::\\    \\                /:::/__\\:::\\    \\           |::|   |         \n' +
        '   /:::/    \\:::\\    \\     /:::/    / \\:::\\    \\     /:::/    /               /:::/ |::|   |            /::::\\    \\              /::::\\   \\:::\\    \\          |::|   |         \n' +
        '  /:::/    / \\:::\\    \\   /:::/____/   \\:::\\____\\   /:::/    /      _____    /:::/  |::|   | _____     /::::::\\    \\            /::::::\\   \\:::\\    \\         |::|   |         \n' +
        ' /:::/    /   \\:::\\    \\ |:::|    |     |:::|    | /:::/____/      /\\    \\  /:::/   |::|   |/\\    \\   /:::/\\:::\\    \\          /:::/\\:::\\   \\:::\\    \\  ______|::|___|___ ____ \n' +
        '/:::/____/     \\:::\\____\\|:::|____|     |:::|    ||:::|    /      /::\\____\\/:: /    |::|   /::\\____\\ /:::/  \\:::\\____\\        /:::/  \\:::\\   \\:::\\____\\|:::::::::::::::::|    |\n' +
        '\\:::\\    \\      \\::/    / \\:::\\    \\   /:::/    / |:::|____\\     /:::/    /\\::/    /|::|  /:::/    //:::/    \\::/    /        \\::/    \\:::\\   \\::/    /|:::::::::::::::::|____|\n' +
        ' \\:::\\    \\      \\/____/   \\:::\\    \\ /:::/    /   \\:::\\    \\   /:::/    /  \\/____/ |::| /:::/    //:::/    / \\/____/          \\/____/ \\:::\\   \\/____/  ~~~~~~|::|~~~|~~~      \n' +
        '  \\:::\\    \\                \\:::\\    /:::/    /     \\:::\\    \\ /:::/    /           |::|/:::/    //:::/    /                            \\:::\\    \\            |::|   |         \n' +
        '   \\:::\\    \\                \\:::\\__/:::/    /       \\:::\\    /:::/    /            |::::::/    //:::/    /                              \\:::\\____\\           |::|   |         \n' +
        '    \\:::\\    \\                \\::::::::/    /         \\:::\\__/:::/    /             |:::::/    / \\::/    /                                \\::/    /           |::|   |         \n' +
        '     \\:::\\    \\                \\::::::/    /           \\::::::::/    /              |::::/    /   \\/____/                                  \\/____/            |::|   |         \n' +
        '      \\:::\\    \\                \\::::/    /             \\::::::/    /               /:::/    /                                                                |::|   |         \n' +
        '       \\:::\\____\\                \\::/____/               \\::::/    /               /:::/    /                                                                 |::|   |         \n' +
        '        \\::/    /                 ~~                      \\::/____/                \\::/    /                                                                  |::|___|         \n' +
        '         \\/____/                                           ~~                       \\/____/                                                                    ~~              \n' +
        '                                                                                                                                                                               '
      )
      console.log('   count_fx@163.com 欢迎您的来信')
      localStorage.setItem('server', 'http://localhost:8080')
      if (localStorage.getItem('username') != 'visitor' && localStorage.getItem('username') != 'undefined') {
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (JSON.parse(xmlhttp.responseText) != null) {
              var item = JSON.parse(xmlhttp.responseText)
              if (item.avatar != "") {
                _this.headImg = item.avatar
              }else{
                _this.headImg = HeadImg
              }
            }
          }
        }
        let formData = new FormData()
        formData.append('username', localStorage.getItem('username'))
        xmlhttp.open('POST', 'http://localhost:8080/counts/user/getuser', true)
        xmlhttp.send(formData)
        this.getMessageNumber();
      }
    },

    methods: {
      getMessageNumber(){
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (JSON.parse(xmlhttp.responseText) != null) {
              var arrays = JSON.parse(xmlhttp.responseText)
              console.log('message'+arrays)
              for(var i=0;i<arrays.length;i++){
                if(arrays[i].read==false){
                  _this.unreadInfoNumber++;
                }
              }
              if(_this.unreadInfoNumber!=0)
                _this.show=true
              else
                _this.show=false
              localStorage.setItem('unreadInfoNumber',''+_this.unreadInfoNumber)
            }
          }
        }
        let formData = new FormData()
        formData.append('username', localStorage.getItem('username'))
        xmlhttp.open('POST', 'http://localhost:8080/counts/message/getMessage', true)
        xmlhttp.send(formData)
      },
      readMessage(){
        var path = '/' + localStorage.getItem('username')
        var id = localStorage.getItem('identify')
        if (id == 'logout') {
          this.openInfo("You haven't logged in")
        }else{
          var p = '/'+localStorage.getItem('username')+'/message'
          this.$router.push({path: p})
        }
      },

      openSucc (text) {
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
      goHome () {
        if (localStorage.getItem('username') != '' && localStorage.getItem('username') != 'undefined' && localStorage.getItem('username') != 'visitor') {
          var path = '/' + localStorage.getItem('username') + '/home'
          this.$router.push({path: path})
        } else {
          this.$router.push({path: '/'})
        }
      },
      goMarket: function () {

        if (localStorage.getItem('identify') == 'logout') {
          this.openInfo("You haven't logged in")
          var path = '/visitor/countsMarket'
          this.$router.push({path: path})
        } else {
          //  console.log(JSON.parse(localStorage.getItem('identify')))
          var path = '/' + localStorage.getItem('username') + '/countsMarket'
          this.$router.push({path: path})
        }
      },
      sakuraShowOrNot:function(){
        this.sakura_show= !this.sakura_show
      },
      goMyProject: function () {

        var path = '/' + localStorage.getItem('username')
        var id = localStorage.getItem('identify')
        if (id == 'worker') {
          this.$router.push({path: path + '/myProject'})
        } else if (id == 'requester') {
          this.$router.push({path: path + '/requester'})
        } else if (id == 'logout') {
          this.openInfo("You haven't logged in")
          this.$router.push({path: '/login'})
        }
      },
      login_button_action () {
        if (localStorage.getItem('identify') == 'logout' ||
          localStorage.getItem('username') == 'undefined' ||
          localStorage.getItem('username') == 'visitor') {
          this.$router.push({path: '/login'})
        } else {
          this.openInfo('You have logged in')
        }
      },
      // 这里是头像下拉框的各个方法
      handleCommand (command) {
        if (command === 'logout') {
          if (localStorage.getItem('identify') == 'logout') {
            this.openInfo("You haven't logged in")
            this.$router.push({path: '/login'})
          } else {
            localStorage.setItem('identify', 'logout')
            localStorage.setItem('username', 'visitor')
            this.openSucc('Logout Successfully')
            this.$router.push({path: '/login'})
          }
        }
      }
    }
  }
</script>
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
  .logoIcon {
    width: 130px;
    position: absolute;
    top: 0px;
    left: 30px;
  }
  .zhongbaosc{
    position: absolute;
    top: 8px;
    left: 44%;
    font-size: 16px;
    color: white;
  }
  .wodexm{
    position: absolute;
    top: 8px;
    left: 52%;
    font-size: 16px;
    color: white;
  }
  .header{
    background-color: #24292E;
    position: absolute;
    width: 100%;
    height: 57px;
    top: 0px;
    left: 0px;
  }
  .headDropDown{
    position: absolute;
    right: 3%;
    top: 11px;
  }
  #userImg{
    width: 35px;
  }
  .login_button{
    position: absolute;
    top: 8px;
    right: 8%;
    margin-left: 100px;
    font-size: 14px;
    color: white;
  }
  .message{
    margin-left: 100px;
    color: white;
    margin-top: -10px;
  }
  .message2{
    position: absolute;
    top:17px;
    right: 13%;
    margin-left: 100px;
    color: white;
    margin-top: -10px;
  }
  .item {
    font-size: 10px;
    position: absolute;
    top:17px;
    right: 13%;

  }
</style>
