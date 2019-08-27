<template>
  <el-container>
    <el-main>
      <div>
        <!--<img class="img_show" src="../assets/gakki.jpg">-->
        <el-carousel id="elcarousel" height="400px" autoplay="true"interval="3000" >
          <el-carousel-item v-for="item in imgList" :key="item.id">
            <img :src="item.imgUrl">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="loginDiv">
        <h2 style="position: absolute; top: 15px; left: 120px;">用户登陆</h2>
        <div id="userid">
          <el-input v-model="input_id"  prefix-icon="el-icon-ali-people" placeholder="请输入ID" style="width: 230px;"></el-input>
        </div>
        <div id="password">
          <el-input v-model="input_pass" type="password" prefix-icon="el-icon-ali-lock" placeholder="请输入密码" style="width: 230px;"></el-input>
        </div>
        <div style="position: absolute;left: 50%; margin-left: -115px; top: 225px;">
          <div v-show="isInputVerify" style="float: top; height: 60px">
            <div style="position: relative;margin-bottom: 15px; margin-top: -18px;">
              <el-input v-model="identifyVerify" style="float:left; width: 130px;" placeholder="请输入验证码"></el-input>
              <div style="margin-left:5px ;float: left; margin-top: 1px" class="code" @click="refreshCode">
                <s-identify :identifyCode="identifyCode" content-width="90" content-height="35"></s-identify>
              </div>
            </div>
            <span v-show="isBanShow" style="position: absolute;left: 5px; top: 39px; color: red;font-size: 11px">{{banDetails}}</span>
          </div>
          <el-button class="toHello" type="primary" @click="loginAndToUserPage">登陆</el-button>
          <el-button id="registerBtn" type="text" @click="handleRegister">还没账号？立刻注册</el-button>
        </div>

        <el-dialog title="新用户注册" :visible.sync="dialogFormVisible">
          <el-form :model="form"  status-icon :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm" size="small">
            <el-form-item label="用户名" prop="userName" :label-width="formLabelWidth" required>
              <el-input v-model="form.userName" auto-complete="off" size="small" ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass" required>
              <el-input type="password" v-model="form.pass" auto-complete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass" required>
              <el-input type="password" v-model="form.checkPass" auto-complete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="mailbox" :label-width="formLabelWidth" required>
              <el-input v-model="form.mailbox" auto-complete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phoneNumber" :label-width="formLabelWidth" required>
              <el-input v-model="form.phoneNumber" auto-complete="off" size="small"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="verification" :label-width="formLabelWidth" required>
              <div style="position: absolute; left: 0px; width: 100%">
                <el-input style="width: 78%" v-model="form.verification" auto-complete="off" size="small"></el-input>
                <el-button @click="sendVerification" style="width: 20%" type="primary" v-bind:disabled="isVerBtnShow">{{verBtnInfo}}</el-button>
              </div>
            </el-form-item>
            <el-form-item label="所在地" prop="location" :label-width="formLabelWidth">
              <el-cascader style="width: 100%"
                size="small"
                :options="options"
                v-model="selectedOptions"
                @change="handleLocationChange">
              </el-cascader>
            </el-form-item>
            <el-form-item label="身份" :label-width="formLabelWidth" required>
              <el-select style="width: 100%" v-model="form.identity" placeholder="请选择账户身份">
                <el-option label="发起者(Requester)" value="Requestor" size="small"></el-option>
                <el-option label="工作者(Worker)" value="Worker"/>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleRegister">确 定</el-button>
          </div>
        </el-dialog>
        <router-view></router-view>
      </div>
    </el-main>
  </el-container>

</template>

<script>
import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode }
  from 'element-china-area-data'
import App from '../App'
import ElContainer from 'element-ui/packages/container/src/main'
import con1 from '@/assets/main.png'
import con2 from '@/assets/data.png'
import con3 from '@/assets/bitStream.png'
import con4 from '@/assets/chart.png'
import SIdentify from "./identify";

export default {
  components: {
    SIdentify,
    App,
    ElContainer
  },
  name: 'Login',
  data () {
    // 以下两个方法检验注册时密码是否一致
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (value.length < 6) {
          callback(new Error('密码不能少于6位'))
        }
        if (this.form.checkPass !== '') {
          this.$refs.form.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
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
      }else{
        callback(new Error('请输入手机号码'))
      }
    }
    var validateUserName = (rule, value, callback) => {
      if (value == 'visitor'){
        callback(new Error('该用户名不能用于注册'))
      }else{
        var reg = /^[0-9a-zA-Z_]{1,}$/
        if(reg.test(value)==false){
          callback(new Error("用户名必须是字母、数字或下划线的组合"))
        }
      }
    }
    var validateVerification = (rule, value, callback) => {
      if (value.length!=6){
        callback(new Error('请输入验证码'))
      }else{
        if(value != this.verification){
          callback(new Error('验证码不正确'))
        }
      }
    }
    return {
      banDetails: '',
      isBanShow:false,
      inputCounts: 0,
      isInputVerify: false,
      identifyVerify:'',
      identifyCodes: "1234567890",
      identifyCode: "",
      verification: '',
      isVerBtnShow: false,
      verBtnInfo: '发送',
      isPhoneCorrect:false,
      options: provinceAndCityData,
      selectedOptions: [],
      isCorrectUser: false,
      imgList: [{
        imgUrl: con1
      },
      {
        imgUrl: con2
      },
      {
        imgUrl: con3
      },
      {
        imgUrl: con4
      }],

      dialogFormVisible: false,
      form: {
        location: '',
        userName: '',
        identity: '',
        mailbox: '',
        pass: '',
        checkPass: '',
        phoneNumber: '',
        verification: ''
      },

      formLabelWidth: '100px',

      rules2: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        mailbox: [
          {validator: validateMail, trigger: 'blur'}
        ],
        phoneNumber: [
          {validator: validatePhone, trigger: 'blur'}
        ],
        userName: [
          {validator: validateUserName, trigger: 'blur'}
        ],
        verification: [
          {validator: validateVerification, trigger: 'blur'}
        ],
      }

    }
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    random_Num(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.random_Num(0, this.identifyCodes.length)
          ];
      }
    },
    sendVerification(){
      var ran = this.randomNum(100000, 999999)
      var phone = this.form.phoneNumber;
      console.log(phone)
      this.verification = ran;
      var templatePram =  '{"code":"'+ran+'"}'
      console.log(templatePram)
      console.log('send')
      const SMSClient = require('@alicloud/sms-sdk')
      const accessKeyId = 'LTAIFKY2agu6Sj1H'
      const secretAccessKey = '5h7x9bYHZ8MVXtYjWINOj1fJATwKpk'
      //初始化sms_client
      let smsClient = new SMSClient({accessKeyId, secretAccessKey})
      smsClient.sendSMS({
        PhoneNumbers: phone,
        SignName: '众包标注平台FX',
        TemplateCode: 'SMS_133265998',
        TemplateParam: templatePram
      }).then(function (res) {
        let {Code}=res
        if (Code === 'OK') {
          //处理返回参数
          console.log(res)
        }
      }, function (err) {
        console.log(err)
      })
      console.log('sended')
      this.resetTime()

    },
    resetTime(){
      var _this = this
      var timer=null;
      var t=60;
      var m=0;
      var s=0;
      m=Math.floor(t/60%60);
      m<10&&(m='0'+m);
      s=Math.floor(t%60);
      function countDown(){
        s--;
        s<10&&(s='0'+s);
        if(s.length>=3){
          s=59;
          m="0"+(Number(m)-1);
        }
        if(m.length>=3){
          m='00';
          s='00';
          clearInterval(timer);
        }

        if(parseInt(s)!=0){
          _this.verBtnInfo = s+'秒'
          _this.isVerBtnShow = true
        }else{
          _this.verBtnInfo = '重新发送'
          _this.isVerBtnShow = false
        }
      }
      timer=setInterval(countDown,1000);
    },
    randomNum(minNum,maxNum){
      switch(arguments.length){
        case 1:
          return parseInt(Math.random()*minNum+1,10);
          break;
        case 2:
          return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10);
          break;
        default:
          return 0;
          break;
      }
    },
    handleLocationChange (value) {
      if (value[0] == '110000' || value[0] == '120000' || value[0] == '310000' || value[0] == '500000') {
        this.form.location = CodeToText[value[0]]

      } else {
        this.form.location = CodeToText[value[1]]

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
    openWarn: function (text) {
      this.$notify({
        title: '警告',
        message: text,
        type: 'warning',
        duration: 2000,
        position: 'top-left'
      })
    },
    loginAndToUserPage () {
      if (this.input_id == 'admin') {
        var path = '/' + this.input_id + '/admin'
        this.$router.push({path: path})
      } else if (this.input_id == 'test') {
        var path = '/' + this.input_id + '/test'
        this.$router.push({path: path})
      } else {
        this.login()
      }
    },
    getAllMessage(){
      localStorage.setItem('unreadInfoNumber', 10);
    },

    login: function () {
      if(this.isInputVerify==true){
        if(this.identifyVerify==''){
          this.banDetails = '请输入验证码'
          this.isBanShow=true;
          return
        }else{
          if(this.identifyCode!=this.identifyVerify){
            this.banDetails = '验证码错误'
            return;
          }else{
            this.isBanShow = false;
          }
        }
      }
      var id = this.input_id
      var password = this.input_pass
      var _this = this
      if (id == undefined || id == null || new RegExp('^[ ]+$').test(id)) {
        this.$alert('请输入用户名', '错误', {
          confirmButtonText: '确定'
        })
        return
      }
      if (password == undefined || password == null || new RegExp('^[ ]+$').test(password)) {
        this.$alert('请输入密码', '错误', {
          confirmButtonText: '确定'
        })
        return
      }
      var xmlhttp = new XMLHttpRequest()
      xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          if (JSON.parse(xmlhttp.responseText).result == true || JSON.parse(xmlhttp.responseText).result.result == true) {
            _this.toUserPage()
            _this.getAllMessage()
          } else {
            _this.openWarn('Fail to log in')
            _this.inputCounts++;
            if(_this.inputCounts>=5){
              _this.isInputVerify = true;
            }
          }
        }
      }

      xmlhttp.open('POST', 'http://localhost:8080/counts/user/signin', true)
      xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
      var user = {
        username: id,
        password: password,
        email: '',
        phone: '',
        role: ''
      }
      xmlhttp.send(JSON.stringify(user))
    },

    toUserPage () {
      var id = this.input_id
      var _this = this
      var xmlhttp2 = new XMLHttpRequest()
      xmlhttp2.onreadystatechange = function () {
        if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200) {
          if (xmlhttp2.responseText == 'Requestor') {
            _this.openSucc('Login in Successfully.')
            var path = '/' + id + '/requester'
            _this.$router.push({path: path})
            localStorage.setItem('identify', 'requester')
            localStorage.setItem('username', id)
            window.location.reload()
          } else if (xmlhttp2.responseText == 'Worker') {
            _this.openSucc('Login in Successfully.')
            var path = '/' + id + '/myProject'
            _this.$router.push({path: path})
            localStorage.setItem('identify', 'worker')
            localStorage.setItem('username', id)
            window.location.reload()
          }
        }
      }
      xmlhttp2.open('POST', 'http://localhost:8080/counts/user/findusername', true)
      xmlhttp2.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=utf-8')
      xmlhttp2.send('username=' + id)
    },

    handleRegister: function () {
      var path = '/register'
      this.$router.push({path: path})
      /*
      var username = this.form.userName
      var password = this.form.pass
      var checkPassword = this.form.checkPass
      var mail = this.form.mailbox
      var phoneNo = this.form.phoneNumber
      var role = this.form.identity
      var city = this.form.location
      if (password != checkPassword) {
        this.$alert('密码不一致', '错误', {
          confirmButtonText: '确定'
        })
      } else {
        var xmlhttp = new XMLHttpRequest()
        var _this = this
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            // console.log(JSON.parse(xmlhttp.responseText).result);
            if (JSON.parse(xmlhttp.responseText).result == true) {
              _this.openSucc('注册成功！')
            } else {
              _this.openWarn('注册失败！')
            }
          }
        }
        var user = {
          username: username,
          password: password,
          email: mail,
          phone: phoneNo,
          role: role,
          name: username,
          info: '',
          level: 1,
          avatar: '',
          missions: [],
          points: 0, // 积分
          rate: 0, // 评分
          city: city
        }

        xmlhttp.open('POST', 'http://localhost:8080/counts/user/signup', true)
        xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        xmlhttp.send(JSON.stringify(user))
      }
      this.dialogFormVisible = false
      */
    }
  }
}
</script>

<style scoped>
  .code {
    width: 90px;
    height: 35px;
    border: 1px solid red;
  }

  .toHello {
    float: top;
    width: 230px; height: 40px;
    display: inherit;
    padding: 5px 10px;
    border: 1px solid #ccc;
  }

  a:hover {
    cursor: pointer;
    color: #fff;
    background-color: #138bec;
    border: 1px solid #138bec;
    text-underline: none;
  }

  .loginDiv {
    width: 350px;
    height: 400px;
    background-color: #e9e9e9;
    border: rgba(156, 162, 148, 0.75);
    border-width: 2px;
    position: absolute;
    left: 65%;
    top: 80px;
  }

  #userid {
    position: absolute;
    top: 100px;
    left: 50%;
    margin-left: -115px;
  }

  #password {
    position: absolute;
    top: 160px;
    left: 50%;
    margin-left: -115px;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  #elcarousel {
    width: 50%;
    position: absolute;
    top: 83px;
    left: 8%;
  }

  #registerBtn{
    display: inherit;
    float: top;
    margin-left: 20%;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #ffffff;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #ffffff;
  }
</style>
