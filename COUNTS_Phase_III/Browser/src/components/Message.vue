<template>
    <div>
      <div>
        <happy-scroll class="happy_scroll" color="rgba(51,51,51,0.2)" hide-vertical="false">
          <div v-for="item in messageList" :key="item">
            <div class="info" @click="showInfo(item.index)">
              <span style="position:absolute; font-size: 15px; top: 5px; font-weight: bold;left: 5px; text-align: left;width: 200px; overflow: hidden; text-overflow:ellipsis;white-space: nowrap;">{{item.topic}}</span>
              <p style="position: absolute; font-size: 12px; top: 18px;overflow: hidden; text-overflow:ellipsis;white-space: nowrap; width: 275px; left: 5px">{{item.content}}</p>
              <div v-show="item.unread" style="background-color: #ff732f; position: absolute;right: 6px; top: 10px; width: 6px;height: 6px;border-radius: 50%"></div>
            </div>
          </div>
        </happy-scroll>
      </div>
      <div style="color:#4CAF50;font-size: 30px;position: absolute; top: 60px;left: 310px">系统消息丨Message</div>
      <div style="position: absolute; width: 800px;top: 130px; left: 330px; height: 350px;">
        <span style="font-size: 24px">{{topic}}</span>
        <p style="position: absolute;top: 50px; text-align: justify;font-size: 16px; left: 100px">{{mainInfo}}</p>
       <!-- <el-button v-show="goProject" type="text" style="font-size: 16px">点此进入项目</el-button>-->
      </div>
    </div>
</template>

<script>
  export default {
    name: "message",
    data(){
      return{
        topic: '自动化审查',
        goProject:false,
        mainInfo: 'xxx客官：\n' +
        '   您有新的任务，请您接单！\n' +
        '   任务名： xxx\n' +
        '   标注类型：xxx\n' +
        '祝您标注愉快~',
        messageList:[
          {
            content:'xxx客官：\n' +
            '   您有新的任务，请您接单！\n' +
            '   任务名： xxx\n' +
            '   标注类型：xxx\n' +
            '祝您标注愉快~',
            index:1,
            topic:'a test',
            unread:true
          }
        ]
      }
    },
    created(){
      if(localStorage.getItem('username')!='visitor'){
        var _this = this
        var xmlhttp = new XMLHttpRequest()
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (JSON.parse(xmlhttp.responseText) != null) {
              var arrays = JSON.parse(xmlhttp.responseText)
              _this.messageList=[]
              for(var i=0;i<arrays.length;i++){
                _this.messageList.push({
                  index: arrays[i].id,
                  topic: arrays[i].title,
                  content: arrays[i].content,
                  unread:!arrays[i].read,
                })
              }
              console.log('red '+arrays[0].read)
            }
          }
        }
        let formData = new FormData()
        formData.append('username', localStorage.getItem('username'))
        xmlhttp.open('POST', 'http://localhost:8080/counts/message/getMessage', true)
        xmlhttp.send(formData)
      }
    },
    methods:{

      showInfo(i){
        this.topic = this.messageList[i].topic;
        this.mainInfo = this.messageList[i].content;
        this.messageList[i].unread = false;
        this.goProject = true;

        var _this = this
        var xmlhttp = new XMLHttpRequest()
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (JSON.parse(xmlhttp.responseText) != null) {
              console.log(JSON.parse(xmlhttp.responseText))
            }
          }
        }
        let formData = new FormData()
        formData.append('username', localStorage.getItem('username'))
        formData.append('id', i)
        xmlhttp.open('POST', 'http://localhost:8080/counts/message/updateMessage', true)
        xmlhttp.send(formData)
      }
    }
  }
</script>

<style scoped>
  .happy_scroll{
    width: 300px;
    left: 10px;
    position: absolute;
    top: 60px;
    height: 480px;
 //  bottom: 60px;

  }
  .info{
    position: relative; width: 280px; height: 60px;background-color: white; border-bottom: 1px solid #d1d1d1;
  }
  .info:hover{
    cursor: pointer;
    background-color: #dcdcdc;
  }
</style>
