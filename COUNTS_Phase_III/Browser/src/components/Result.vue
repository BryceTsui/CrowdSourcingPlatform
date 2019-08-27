<template>
  <div>
    <div style="position: absolute; top: 57px;left:0px;width: 100%; height: 100px">
      <div style="position: absolute; left: 50px;top: 3px;">
        <span style="color:#ff8432;font-size: 32px;float: left; margin-right: 10px">分析结果 </span>
        <span style="color: grey;font-size: 32px;float: left; margin-right: 10px">丨 </span>
        <span style="color:#4CAF50;font-size: 32px;float: left; margin-right: 10px">{{projectInfo.name}} </span>
      </div>
      <div style="position: absolute; left: 50px; top: 40px;">
        <p style="position: absolute; left: 10px; width: 70%; top:0px; text-align: justify; font-size: 16px; color: #747474">{{projectInfo.details}}</p>
      </div>
      <div style="position: absolute;top: 85px;left: 53px;font-size: 20px;color: #4CAF50">{{projectInfo.type}}</div>
    </div>
    <div style="position: absolute;top: 182px;left: 53px;font-size: 14px">
      移动鼠标至图片上查看分析结果
    </div>
    <div style="position: absolute; top: 200px; background-color: white;width: 99%;left: 1%">
      <div style="float: left">
        <div v-for="(item,index) in imgList" :key="(item,index)">
          <el-card class="el_card" :body-style="{padding:'0px'}">
            <div style="position: relative;">
              <img style="width: 100%" :src="item.url">
              <span >{{item.filename}}</span>
            </div>
            <div class="imgOnClick">
              <div class="background"></div>
              <div style="position: absolute;top: 0px;left:0;right:0;bottom: 0">
                <!--用下面这个div作图-->
                <div :id="index"  style="position: absolute;left: 5%; top: 5%; right: 5%; bottom: 5%; width:340px; height: 200px;"></div>
              </div>
            </div>
          </el-card>
        </div>
      </div>
      <div style="width: 100%;height: 40px;float: left">
        <span style="font-size: 15px">已拉到底了，</span>
        <el-button type="text" style="font-size: 15px" @click="goMyProject">点此返回个人中心</el-button>
      </div>
    </div>

  </div>
</template>

<script>
  import echarts from 'echarts'
  import con1 from '@/assets/gakki.jpg'
  import con2 from '@/assets/dog.jpg'
  require('echarts-wordcloud');
  export default {
    name: "result",
    data(){
      return{
        captionWordInfos: [
          {
            name: "蓝天",
            value: 10000,
          },
          {
            name: "女人",
            value: 6181,
          },
          {
            name: "小狗",
            value: 6861,
          },
          {
            name: "美丽",
            value: 4055,
          },
          {
            name: "绿树",
            value: 2467,
          },
          {
            name: "阳光",
            value: 2244,
          },
          {
            name: "微笑",
            value: 1898,

          },
          {
            name: "年轻",
            value: 1484,
          },
        ],
        projectInfo:{
          name: '一个测试的项目',
          details: '',
          type: 'Caption',
        },
        imgList:[
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },
          {
            url:con1,
            filename:"gakki.jpg"
          },

        ]
      }
    },
    mounted(){
      console.log(localStorage.getItem('mt'))
      if(localStorage.getItem('mt')=='0'){
        for(var i=0;i<this.imgList.length;i++){
          this.imgList[i].url = con2
          this.imgList[i].filename = 'dog.jpg'
        }
        this.drawPieCharts()
      }else{
        this.drawStringCloud()
      }
     // this.drawStringCloud()this.drawStringCloud()
    },
    methods:{
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

      drawPieCharts(){
        for(var i=0;i<this.imgList.length;i++) {
          var container = document.getElementById(i);
          var myChart = echarts.init(container);

          // 指定图表的配置项和数据
          var option = {
            tooltip: {
              trigger: 'item',
              formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
              orient: 'vertical',
              x: 'left',
              data: ['狗', '猫', '兔子', '鹦鹉']
            },
            series: [
              {
                name: '动物分类',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                  normal: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: '30',
                      fontWeight: 'bold'
                    }
                  }
                },
                labelLine: {
                  normal: {
                    show: false
                  }
                },
                data: [
                  {value: 3000, name: '狗'},
                  {value: 310, name: '猫'},
                  {value: 134, name: '兔子'},
                  {value: 65, name: '鹦鹉'},
                ]
              }
            ]
          };

          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        }
      },

      drawStringCloud(){
        for(var i=0;i<this.imgList.length;i++) {
          var container = document.getElementById(i);
          var myChart = echarts.init(container);
          var option = {
            title: {
              text: '关键词云',
            },
            tooltip: {
              show: true
            },
            series: [{
              name: 'Google Trends',
              type: 'wordCloud',
              size: ['80%', '80%'],
              textRotation: [0, 45, 90, -45],
              textPadding: 0,
              autoSize: {
                enable: true,
                minSize: 14
              },
              textStyle: {
                normal: {
                  fontFamily: 'sans-serif',
                  fontWeight: 'bold',
                  // Color can be a callback function or a color string
                  color: function () {
                    // Random color
                    return 'rgb(' + [
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160)
                    ].join(',') + ')';
                  }
                },
                emphasis: {
                  shadowBlur: 13,
                  shadowColor: '#999999'
                }
              },
              data: this.captionWordInfos
            }]
          }
          myChart.setOption(option);
        }
      },
      createRandomItemStyle() {
        return {
          normal: {
            color: 'rgb(' + [
              Math.round(Math.random() * 160),
              Math.round(Math.random() * 160),
              Math.round(Math.random() * 160)
            ].join(',') + ')'
          }
        };
      }
    }
  }
</script>

<style scoped>
  .imgOnClick {
    display: none;
  }

  .background{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background-color: white;
    opacity:0.6;
  }
  .el_card:hover .imgOnClick{
    display:block;
  }

  .el_card{
    border-radius: 0;
    margin: 0.8%;
    padding: 5px;
    padding-bottom: 2px;
    width: 30.2%;
    float: left;
    position: relative;
  }

</style>
