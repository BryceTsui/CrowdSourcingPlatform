<template>
  <el-container>
    <div id="menubar" style="position: absolute; left: 35%; top:57px ;height:100px" >
      <el-tabs type="border-card" style="width: 500px" v-model="activeName">
        <el-tab-pane class="el-tab-pane" name='1'>
          <span slot="label"><i class="el-icon-info"></i> 图片信息</span>
          <div style="height: 10px"></div>
          <el-row>
            <span>点击按钮或双击图片查看当前图片标注信息</span>
            <el-tooltip  content="标注信息" placement="bottom">
              <el-button type="info" icon="el-icon-info" size="mini" round  @click="selectDialog"></el-button>
            </el-tooltip>
          </el-row>
        </el-tab-pane>
        <el-tab-pane class="el-tab-pane" name='2' :disabled="correctJudge">
          <span slot="label"><i class="el-icon-success"></i> 正确判断</span>
          <span>当前标注结果是否正确？</span>
          <div style="height: 5px"></div>
          <el-row>
            <el-button type="success" icon="el-icon-check" size="small" @click="nextImg">正确，下一张</el-button>
            <el-button type="info" icon="el-icon-close" size="small" @click="handleTabClick">有问题，重新标注</el-button>
          </el-row>
        </el-tab-pane>
        <el-tab-pane class="el-tab-pane" name='3'>
          <span slot="label"><i class="el-icon-edit-outline"></i> 标注工具</span>
          <el-row>
            <el-tooltip  content="上一张" placement="bottom-start">
              <el-button icon="el-icon-caret-left" circle @click="prevImg"></el-button>
            </el-tooltip>
            <el-tooltip  content="添加标注信息" placement="bottom">
              <el-button type="primary" icon="el-icon-edit" circle @click="selectDialog"></el-button>
            </el-tooltip>
            <el-tooltip  content="保存图片并提交" placement="bottom">
              <el-button type="success" icon="el-icon-upload" circle @click="commitImg" ></el-button>
            </el-tooltip>
            <el-tooltip  content="加载任务数据" placement="bottom-start">
              <el-button type="info" icon="el-icon-download" circle @click="downloadSource" ></el-button>
            </el-tooltip>
            <el-tooltip  content="重新标注当前图片" placement="bottom-end">
              <el-button type="danger" icon="el-icon-refresh" circle @click="reLabelImg"></el-button>
            </el-tooltip>
            <el-tooltip  content="下一张" placement="bottom">
              <el-button icon="el-icon-caret-right" circle @click="nextImg"></el-button>
            </el-tooltip>
          </el-row>
        </el-tab-pane>
        <el-tab-pane class="el-tab-pane" name='4'>
          <span slot="label"><i class="el-icon-time"></i> 项目进度</span>
          <div style="height: 10px"></div>
          <el-progress :text-inside="true" :stroke-width="18" :percentage="(labeled/imgList.length)*100"></el-progress>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="work_place">
      <div class="block" >
        <canvas  id="myCanvas" v-model="canvas" @click="drawShapes" @dblclick="selectDialog"  width="900px" height="500px"></canvas>
      </div>
    </div>
    <div style="position: fixed; top: 0px; left: 0px; width: 18%; height: 680px; z-index:-10000;background-color: #e9e9e9;border: 1px solid rgba(156, 162, 148, 0.75);border-top: none" >
      <div style="position: absolute; left: 50px; height: 70px; top: 47px ">
        <h3 id="title">图片预览</h3>
      </div>

      <happy-scroll class="happy_scroll" color="rgba(51,51,51,0.2)" hide-vertical="false" style="top: 110px;">
        <div v-for="(item,index) of imgList" :key="item">
          <div class="upload_warp_img_div_top">
            <div class="upload_warp_img_div_text" style="font-size: 12px;">
              {{item.filename}}
            </div>
          </div>
          <img style="width: 100%; height: auto;" :src="item.url">
        </div>
      </happy-scroll>
    </div>


    <div style="display: none">
      <el-button @click="dialogVisible=true"></el-button>
      <el-dialog :visible.sync="dialogVisible" width="30%"></el-dialog>
    </div>
    <!--classification类型标注弹出框-->
    <el-dialog title="下列描述中最符合图片的是？" :visible.sync="classificationDialogVisible">
      <el-select v-model="classificationValue" >
        <el-option
          v-for="item in classificationOptions"
          :key="item.classificationValue"
          :label="item.label"
          :value="item.classificationValue">
        </el-option>
      </el-select>
      <div>
        <el-table
          :data="classificationInfoList"
          style="width: 100%">
          <el-table-column
            prop="fileName"
            label="图片名"
            width="380">
          </el-table-column>
          <el-table-column
            prop="select"
            label="选择"
            width="180">
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button  icon="el-icon-close" @click="classificationDialogVisible = false">取消</el-button>
        <el-button type="primary"icon="el-icon-circle-plus-outline" @click="saveClassificationInfo">确定</el-button>
      </div>
    </el-dialog>
    <!--classification类型标注弹出框结束-->
    <!--caption标注弹出框-->
    <el-dialog title="整体标注" :visible.sync="captionDialogVisible">
      <el-form :model="form">
        <el-form-item label="整体标注内容" :label-width="formLabelWidth">
          <el-input v-model="captionDialogInfo" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="captionDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCaptionInfo">确 定</el-button>
      </div>
    </el-dialog>
    <!--caption标注弹出框结束-->
    <!--attribute标注弹出框-->
    <el-dialog title="属性标注" :visible.sync="attributeDialogVisible">
      <el-table
        :data="attributeTableData"
        style="width: 100%"
        border="true"
        stripe="true"
      >
        <el-table-column
          label="属性"
          width="250">
          <template slot-scope="scope">
            <span>{{ scope.row.attributeValue }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="内容"
          width="330">
          <template slot-scope="scope">
            <el-input v-model="scope.row.label" placeholder="请输入内容"></el-input>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="attributeDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveAttributeInfo">确 定</el-button>
      </div>
    </el-dialog>
    <!--attribute弹出框结束-->

    <!--双击图片弹出已提交标注文本开始-->
    <el-dialog title="已提交标注列表" :visible.sync="committedCommentsVisible">
      <el-table
        :data="committedTableData"
        style="width: 100%"
        stripe="true"
        border="true">
        <el-table-column
          label="序号"
          width="120"
          prop="index">
        </el-table-column>
        <el-table-column
          label="类型"
          width="120"
          prop="commentType">
        </el-table-column>
        <el-table-column
          label="标注内容"
          width="320"
          prop="message">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button  icon="el-icon-close" @click="committedCommentsVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--双击文本弹出已提交标注文本结束-->
    <!--评分对话框开始-->
    <el-dialog title="评价项目" :visible.sync="ratingDialogVisible">
      <div class="block">
        <span class="demonstration">对这个项目感兴趣吗？</span>
        <div style="height: 10px"></div>
        <el-rate
          v-model="projectRate"
          :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
        </el-rate>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button  icon="el-icon-close" @click="ratingDialogVisible = true">取消</el-button>
        <el-button type="primary"icon="el-icon-circle-plus-outline" @click="commitRating">确定</el-button>
      </div>
    </el-dialog>
    <!--评分对话框结束-->
  </el-container>
</template>

<script>
  import ElMain from 'element-ui/packages/main/src/main'
  import ElAside from 'element-ui/packages/aside/src/main'


  export default {
    components: {
      ElAside,
      ElMain
    },
    name: 'Worker',
    data () {
      return {
        imgList: [{
          url: '',
          filename: ''
        }],

        colorList: ['#ff0000', '#FFA500', '#FFEB0D', '#69FF66', '#007FFF', '#BCB4FF'],
        colorPos: 0,
        imgPos: 0,

        captionInfoList: [
          /* {
          fileName:"",
          caption:""
        }
        */], // 存储当前数据集的全部整体标注内容
        classificationInfoList: [
          {
            fileName: '',
            select: ''
          }
        ], // 存储当前数据集每张图片的分类结果
        detectionInfoList: [
          /* {
            fileName:"",
            dots:[]
          } */], // 存储当前数据集每张图片的方框标注，每个元素都是一个List


        tooltip: '收起',
        btnText: 'el-icon-arrow-right',
        isShow: true,
        toolbar_info: {
          down: false,
          dx: 0,
          dy: 0,
          sx: 0,
          sy: 0
        },
        missionType: '', // 当前任务类型
        missionID: '', // 当前任务ID号
        classificationDialogVisible: false, // classification类型标注时的弹出框
        classificationOptions: [
          /* { // classification类型标注的选项列表
          classificationValue: '选项1',
          label: '黄金糕'
        }, {
          classificationValue: '选项2',
          label: '双皮奶'
        }
         */],
        classificationValue: '', // classificationOptions的选择结果(号码)
        classificationLabel: '', // classificationOptions的选择结果(文本)
        captionDialogVisible: false, // caption弹出框是否可见
        captionDialogInfo: '', // caption标注的内容
        attributeDialogVisible: false, // attribute弹出框是否可见
        attributeTableData: [
          /*
          {
            attributeValue: '',
            label: ''
          }
          */
        ],
        pointList: [],// 存储点集

        labeled: 0, //已标注数量
        ratingDialogVisible: false, //评分弹出框是否可见
        projectRate: null,
        activeName: '1', //选项卡选择哪一张
        acceptMission: null, //存储项目评分，已完成数量等信息
        autoMission: null, //自动化项目的信息
        status: '',
        correctJudge: false,
        currentImgHeight: 0,
        //ratingAccess: true,

      }
    },
    created () {
      this.missionType = localStorage.getItem('missionType')
      this.missionID = localStorage.getItem('missionID')
      this.getAutoMission()
      this.downloadSource()
    },
    watch: {
      imgList: {
        handler (oldValue, newValue) {
          this.draw()
        },
        deep: true
      }
    },
    directives: {
      drag (el, bindings) {
        el.onmousedown = function (e) {
          var disx = e.pageX - el.offsetLeft
          var disy = e.pageY - el.offsetTop
          document.onmousemove = function (e) {
            el.style.left = e.pageX - disx + 'px'
            el.style.top = e.pageY - disy + 'px'
          }
          document.onmouseup = function () {
            document.onmousemove = document.onmouseup = null
          }
        }
      }
    },
    methods: {
      // 以下方法用于处理classfication类型标注/caption类型标注/Arribute类型标注，用于实验后期可以删除
      saveClassificationInfo () {
        // console.log(JSON.stringify(classificationInfoList))

        var _this = this
        for (var i = 0; i < _this.classificationInfoList.length; i++) {
          if (_this.classificationInfoList[i].fileName == _this.imgList[_this.imgPos].filename) {
            _this.classificationInfoList[i].select = _this.classificationValue
            console.log(_this.classificationInfoList[i].select)
          }
        }

        this.classificationDialogVisible = false
      },
      saveCaptionInfo () {
        var _this = this
        for (var i = 0; i < _this.captionInfoList.length; i++) {
          if (_this.captionInfoList[i].fileName === _this.imgList[_this.imgPos].filename) {
            _this.captionInfoList[i].caption = _this.captionDialogInfo
          }
        }
        // console.log(this.captionDialogInfo);
        this.captionDialogVisible = false
      },
      saveAttributeInfo () {
        // console.log("close")
        /*
        for (var i = 0; i < this.attributeTableData.length; i++) {
          console.log(this.attributeTableData[i].label)
        }
        */
        this.attributeDialogVisible = false
      },
      // 选择弹出框类型
      selectDialog () {
        // console.log(this.missionType)
        switch (this.missionType) {
          case 'Caption': {
            this.captionDialogVisible = true
            break
          }
          case 'Classification': {
            this.classificationDialogVisible = true
            break
          }
          case 'Attribute': {
            this.attributeDialogVisible = true
            break
          }
          case 'Detection': {
            var _this = this
            _this.$notify({
              title: '标注信息',
              message: '当前是方框标注',
              type: 'info',
              duration: 2000,
              position: 'top-left'
            })
            break
          }
          case 'Segmentation': {
            var _this = this
            _this.$notify({
              title: '标注信息',
              message: '当前是区域标注',
              type: 'info',
              duration: 2000,
              position: 'top-left'
            })
            break
          }
          default: {}
        }
      },
      showToolBar () {
        console.log(123)
        this.isShow = !this.isShow
        if (this.isShow) {
          this.tooltip = '收起'
          this.btnText = 'el-icon-arrow-left'
        } else {
          this.tooltip = '展开'
          this.btnText = 'el-icon-arrow-right'
        }
      },
      // 重新标注当前图片
      reLabelImg () {
        this.tableData = []

        var _this = this
        if (this.missionType == 'Caption') {
          // 重设caption标注
          _this.captionDialogInfo = ''
          for (var i = 0; i < _this.captionInfoList.length; i++) {
            if (_this.captionInfoList[i].fileName === _this.imgList[_this.imgPos].filename) {
              _this.captionInfoList[i].caption = _this.captionDialogInfo
            }
          }
          _this.commitAndStay()
        }
        if (this.missionType == 'Classification') {
          // 重设classification标注
          _this.classificationValue = 0
          for (var i = 0; i < _this.classificationInfoList.length; i++) {
            if (_this.classificationInfoList[i].fileName === _this.imgList[_this.imgPos].filename) {
              _this.classificationInfoList[i].select = _this.classificationValue
              // console.log("relabel")
            }
          }
          _this.commitAndStay()
        }
        if (this.missionType == 'Detection') {
          this.colorPos = 0
          // _this.imgDataClear();
          var xmlhttp = new XMLHttpRequest()
          var _this = this
          xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
              var newImg = JSON.parse(xmlhttp.responseText)
              _this.imgList[_this.imgPos].url = newImg.url
              _this.draw()
            }
          }
          let formData3 = new FormData()
          formData3.append('missionid', localStorage.getItem('missionID'))
          formData3.append('imgname', this.imgList[this.imgPos].filename)
          // console.log("12345678");
          xmlhttp.open('POST', 'http://localhost:8080/counts/image/checkimg', true)
          xmlhttp.send(formData3)
        }
      },
      // 下载数据集
      downloadSource () {
        var xmlhttp1 = new XMLHttpRequest()
        var xmlhttp = new XMLHttpRequest()
        var xmlhttp0 = new XMLHttpRequest()
        var _this = this
        console.log(_this.status)
        if (this.status=='Train') {
          xmlhttp1.onreadystatechange = function () {
            if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
              // 加入imgList
              _this.imgPos = 0
              _this.imgList = []
              var dataSet = JSON.parse(xmlhttp1.responseText)
              //console.log(dataSet)
              for (var i = 0; i < dataSet.length; i++) {
                var dataurl = dataSet[i].base64
                var name = dataSet[i].fileName
                _this.imgList.push({url: dataurl, filename: name})
              }
              //console.log(_this.imgList)
              //console.log("0000")
            }
          }
          let formData = new FormData()
          formData.append('missionid', localStorage.getItem('missionID'))
          formData.append('username', localStorage.getItem('username'))
          console.log(localStorage.getItem('missionID'))
          xmlhttp1.open('POST', 'http://localhost:8080/counts/image/get/trainimages', false)
          xmlhttp1.send(formData)
        } else {
          xmlhttp1.onreadystatechange = function () {
            if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
              // 加入imgList
              _this.imgPos = 0
              _this.imgList = []
              var dataSet = JSON.parse(xmlhttp1.responseText)
              for (var i = 0; i < dataSet.length; i++) {
                var dataurl = dataSet[i].base64
                var name = dataSet[i].fileName
                _this.imgList.push({url: dataurl, filename: name})
              }
            }
          }
          let formData3 = new FormData()
          formData3.append('username', localStorage.getItem('username'))
          formData3.append('missionid', localStorage.getItem('missionID'))
          //console.log(localStorage.getItem('missionID'))
          xmlhttp1.open('POST', 'http://localhost:8080/counts/image/get/testimages', false)
          xmlhttp1.send(formData3)
        }

        // 判断任务类型下载任务数据
        switch (this.missionType) {
          case 'Caption': {
            // 下载整体标注结果集
            xmlhttp.onreadystatechange = function () {
              if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                //console.log(JSON.parse(xmlhttp.responseText))
                //console.log(_this.missionID)
                // 下载所有的整体标注结果
               var captionResult = JSON.parse(xmlhttp.responseText)
                for(var i=0;i<_this.imgList.length;i++){
                 _this.captionInfoList.push({
                   fileName: _this.imgList[i].filename,
                   caption: "",
                 })
                  for(var j=0;j<captionResult.length;j++){
                    //console.log(_this.captionInfoList[i].fileName+ " "+captionResult[j].fileName)
                   if(_this.captionInfoList[i].fileName==captionResult[j].fileName){
                     _this.captionInfoList[i].caption = captionResult[j].caption
                   }
                  }
                }
                _this.setCurrentImgCaptionInfo()
              }
            }
            let formDataCap = new FormData()
            formDataCap.append('userid', localStorage.getItem('username'))
            formDataCap.append('missionid', localStorage.getItem('missionID'))
            xmlhttp.open('POST', 'http://localhost:8080/counts/label/get/autocaptionlabel', false)
            xmlhttp.send(formDataCap)
            break
          }
          case 'Classification': {
            //准备分类标注选项
            console.log(_this.autoMission.types)
                var selects = _this.autoMission.types
                for (var i = 0; i < selects.length; i++) {
                  _this.classificationOptions.push({
                    classificationValue: i + 1,
                    label: selects[i]
                  })
                }
            // 下载分类标注结果
            xmlhttp0.onreadystatechange = function () {
              if (xmlhttp0.readyState == 4 && xmlhttp0.status == 200) {
                // 加入下载结果
                var classificationResult = JSON.parse(xmlhttp0.responseText)
                console.log(classificationResult)
                console.log(_this.imgList.length)
                for(var i=0;i<_this.imgList.length;i++){
                  _this.classificationInfoList.push({
                    fileName: _this.imgList[i].filename,
                    select: 0
                  })
                  for(var j=0;j<classificationResult.length;j++){
                    if(_this.classificationInfoList[i].fileName==classificationResult[j].fileName){
                      _this.classificationInfoList[i].select = classificationResult[j].select
                    }
                  }
                }
                //_this.classificationInfoList = JSON.parse(xmlhttp0.responseText)
                //console.log('999' + _this.classificationInfoList)
                _this.setCurrentImgClassificationValue()
              }
            }
            let formDataClaRes = new FormData()
            formDataClaRes.append('missionid', localStorage.getItem('missionID'))
            formDataClaRes.append('username', localStorage.getItem('username'))
            xmlhttp0.open('POST', 'http://localhost:8080/counts/label/get/autoclassificationlabel', true)
            xmlhttp0.send(formDataClaRes)
            break
          }
          case 'Detection': {
            // 下载方框标注结果并解析
            xmlhttp0.onreadystatechange = function () {
              if (xmlhttp0.readyState == 4 && xmlhttp0.status == 200) {
                // 加入下载结果
                var detectionResult = JSON.parse(xmlhttp0.responseText)
                for(var i=0;i<_this.imgList.length;i++){
                  _this.detectionInfoList.push({
                    fileName: _this.imgList[i].filename,
                    dots: [],
                  })
                  for(var j=0;j<detectionResult.length;j++){
                    if(_this.detectionInfoList[i].fileName==detectionResult[j].fileName){
                      _this.detectionInfoList[i].dots = detectionResult[j].dots
                    }
                  }
                }
                _this.setCurrentImgDetectionInfo()
              }
            }
            let formDataDetRes = new FormData()
            formDataDetRes.append('missionid', localStorage.getItem('missionID'))
            formDataDetRes.append('username', localStorage.getItem('username'))
            xmlhttp0.open('POST', 'http://localhost:8080/counts/label/get/autodetectionlabel', true)
            xmlhttp0.send(formDataDetRes)
            break
          }

          // default: {}
        }
      },
      // 以下三个方法用于设置当前图片的标注结果
      setCurrentImgCaptionInfo () {
        this.captionDialogInfo = ''
        console.log(this.captionInfoList)
        // console.log(_this.imgList[_this.imgPos].filename)
        var _this = this
        for (var i = 0; i < _this.captionInfoList.length; i++) {
          if (_this.captionInfoList[i].fileName == _this.imgList[_this.imgPos].filename) {
            _this.captionDialogInfo = _this.captionInfoList[i].caption
          }
        }
      },
      setCurrentImgClassificationValue () {

        var _this = this
        for (var i = 0; i < _this.classificationInfoList.length; i++) {
          if (_this.classificationInfoList[i].fileName === _this.imgList[_this.imgPos].filename) {
            _this.classificationValue = _this.classificationInfoList[i].select
          }
        }

        // console.log(_this.classificationInfoList)
      },
      setCurrentImgInfo () {
        // console.log(localStorage.getItem("missionType"))
        switch (localStorage.getItem('missionType')) {
          case 'Caption': {
            //console.log('uuuuu')
            this.setCurrentImgCaptionInfo()
            break
          }
          case 'Classification': {
            this.setCurrentImgClassificationValue()
            break
          }
          case 'Detection': {
            this.setCurrentImgDetectionInfo()
            break
          }
        }
      },
      setCurrentImgDetectionInfo(){
        var _this = this
        for (var i = 0; i < _this.detectionInfoList.length; i++) {
          if (_this.detectionInfoList[i].fileName === _this.imgList[_this.imgPos].filename) {
            //把点画上去
            var canvas = document.getElementById('myCanvas')
            var ctx = canvas.getContext('2d')
            for(var j=0;j<_this.detectionInfoList[i].dots.length-1;j = j+2){
              var x1 = _this.detectionInfoList[i].dots[j].x*900
              var y1 = _this.detectionInfoList[i].dots[j].y*_this.currentImgHeight
              var x2 = _this.detectionInfoList[i].dots[j+1].x*900
              var y2 = _this.detectionInfoList[i].dots[j+1].y*_this.currentImgHeight
              ctx.lineWidth = 2
              ctx.strokeStyle = 'rgba(52, 136, 255, 1)'
              ctx.fillStyle = 'rgba(52, 136, 255, 0.5)'
              ctx.fill()
              ctx.strokeRect(x1, y1, x2 - x1, y2 - y1)
              ctx.closePath()
            }
          }
        }
      },

      // 提交图片
      commitImg () {
        this.commitAndStay()
        //this.updateLabeled(this.labeled);
        this.nextImg()

      },
      //提交图片但不切换到下一张
      commitAndStay(){
        var xmlhttp = new XMLHttpRequest()
        var xmlhttp1 = new XMLHttpRequest()
        var _this = this
        var userName = localStorage.getItem('username')
        var missionID = localStorage.getItem('missionID')

        if (this.missionType == 'Detection') {
          // 准备数据
          var c = document.getElementById('myCanvas')
          var dataURL = c.toDataURL()
          var _this = this
          //var filename = _this.imgList[_this.imgPos].filename
          var autoDetectionLabel = {
            fileName: _this.imgList[_this.imgPos].filename,
            dots: _this.pointList
          }
          var data = {
            username: userName,
            missionid: missionID,
            kind: _this.status,
            autoDetection: autoDetectionLabel
          }
          // 替换img图片
          this.imgList[this.imgPos].url = dataURL
          // 发送数据

          xmlhttp1.onreadystatechange = function () {
            if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
              console.log(JSON.parse(xmlhttp1.responseText))
              if (JSON.parse(xmlhttp1.responseText) == 'SUCCESS') {
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
                _this.labeled++;
              }
              else if(JSON.parse(xmlhttp1.responseText) == 'EXIST'){
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
              }
              else{
                _this.$notify({
                  title: '提交失败',
                  message: '图片上传失败，请重试！',
                  type: 'warning',
                  duration: 2000,
                  position: 'top-left'
                })
              }
            }
          }
          xmlhttp1.open('POST', 'http://localhost:8080/counts/label/auto/add/detectionlabel', false)
          xmlhttp1.setRequestHeader('Content-type', 'application/json; charset=utf-8')
          xmlhttp1.send(JSON.stringify(data))
        }
        if (this.missionType == 'Caption') {
          console.log("caption")
          var autoCaptionLabel = {
            fileName: _this.imgList[_this.imgPos].filename,
            caption: _this.captionDialogInfo
          }
          var autoCaptionLabelBean = {
            username: userName,
            missionid: missionID,
            kind: _this.status,
            autoCaptionLabel: autoCaptionLabel
          }
          xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
              console.log(JSON.parse(xmlhttp.responseText))
              if (JSON.parse(xmlhttp.responseText) == 'SUCCESS') {
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
                _this.labeled++;
              }
              else if(JSON.parse(xmlhttp.responseText) == 'EXIST'){
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
              }
              else{
                _this.$notify({
                  title: '提交失败',
                  message: '图片上传失败，请重试！',
                  type: 'warning',
                  duration: 2000,
                  position: 'top-left'
                })
              }
            }
          }
          xmlhttp.open('POST', 'http://localhost:8080/counts/label/auto/add/captionlabel', false)
          xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
          xmlhttp.send(JSON.stringify(autoCaptionLabelBean))
        }
        if (this.missionType == 'Classification') {
          var autoClassificationLabel = {
            fileName: _this.imgList[_this.imgPos].filename,
            select: _this.classificationValue
          }
          //console.log(',,' + JSON.stringify(classificationLabel))
          var data = {
            username: userName,
            missionid: missionID,
            kind: _this.status,
            autoClassificationLabel: autoClassificationLabel
          }
          xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
              console.log(JSON.parse(xmlhttp.responseText))
              if (JSON.parse(xmlhttp.responseText) == 'SUCCESS') {
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
                _this.labeled++;
                console.log(_this.labeled)
              }
              else if(JSON.parse(xmlhttp.responseText) == 'EXIST'){
                _this.$notify({
                  title: '提交成功',
                  message: '图片已上传！',
                  type: 'success',
                  duration: 2000,
                  position: 'top-left'
                })
                console.log(_this.labeled)
              }
              else{
                _this.$notify({
                  title: '提交失败',
                  message: '图片上传失败，请重试！',
                  type: 'warning',
                  duration: 2000,
                  position: 'top-left'
                })
              }
            }
          }
          xmlhttp.open('POST', 'http://localhost:8080/counts/label/auto/add/classificationlabel', false)
          xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
          xmlhttp.send(JSON.stringify(data))
        }
      },

      // 获取图片location属性
      getLocation () {
        var location = this.missionID + this.imgList[this.imgPos].filename
        return location
      },

      // 以下方法用于绘画
      drawShapes () {
        // 判断标注类型并开始作画
        if (this.missionType == 'Detection') {
          this.drawRec()
        }
        if (this.missionType == 'Segmentation') {
          this.drawBrokenLine()
        } else {}
      },
      // 以下方法用于加载图片到画布上
      draw () {
        var _this = this
        var cvs = document.getElementById('myCanvas')
        var imgObj = new Image() // 创建image对象
        var img = this.imgList[this.imgPos].url
        imgObj.src = img
        // 待图片加载完后，将其显示在canvas上
        imgObj.onload = function () {
          var ctx = cvs.getContext('2d')
          // 压缩图片
          var rate = 900 / imgObj.width
          cvs.height = imgObj.height * rate
          _this.currentImgHeight = cvs.height
          // ctx.drawImage(this, 0, 0);//this即是imgObj,保持图片的原始大小：470*480
          ctx.drawImage(this, 0, 0, 900, cvs.height)// 改变图片的大小到900*500
          // 设置当前图片标注
        }

        _this.setCurrentImgInfo()
      },
      // 以下两个方法分别用于画折线和矩形
      drawBrokenLine () {
        var canvas = document.getElementById('myCanvas')
        var ctx = canvas.getContext('2d')
        var _this = this
        if (this.missionType == 'Segmentation') {
          canvas.onmousedown = function (ev) {
            var x = ev.offsetX
            var y = ev.offsetY
            _this.pointList.push({x: x, y: y})
            console.log(_this.pointList)
            ctx.beginPath()
            ctx.moveTo(x, y)
            canvas.onmousemove = function (ev) {
              var targetX = ev.offsetX
              var targetY = ev.offsetY
              _this.pointList.push({x: targetX, y: targetY})
              console.log(_this.pointList)
              ctx.lineWidth = 1
              ctx.strokeStyle = 'rgba(52, 136, 255, 1)'
              ctx.lineTo(targetX, targetY)
              ctx.stroke()
            }
            canvas.onmouseup = function (ev) {
              canvas.onmousemove = null
              canvas.onmouseup = null
              ctx.lineWidth = 2
              ctx.strokeStyle = 'rgba(52, 136, 255, 1)'
              ctx.lineTo(x, y)
              ctx.stroke()
              ctx.fillStyle = 'rgba(52, 136, 255, 0.5)'
              ctx.fill()
              // ctx.closePath();
            }
          }
        }
      },
      drawRec () {
        // var pointList = new Array()
        var pos = 0
        // var flag = this.checkList.indexOf('矩形画框')
        var canvas = document.getElementById('myCanvas')
        var ctx = canvas.getContext('2d')
        var x2
        var y2
        var _this = this
        if (this.missionType == 'Detection') {
          var lineColor = this.colorList[this.colorPos]
          canvas.onmousedown = function (ev) {
            var x1 = ev.offsetX
            var y1 = ev.offsetY
            //console.log(x1)
            ctx.beginPath()
            ctx.moveTo(x1, y1)
            _this.pointList.push({x: x1/900, y: y1/_this.currentImgHeight})
            //console.log(_this.pointList)
            // pointList[pos] = '(' + x1 + ',' + y1 + '),'
            pos = pos + 1

            canvas.onmousemove = function (ev) {
              x2 = ev.offsetX
              y2 = ev.offsetY
              // pointList[pos] = '(' + x2 + ',' + y2 + '),'
              pos = pos + 1
              ctx.strokeStyle = '#000000'
              ctx.rect(x1, y1, x2 - x1, y2 - y1)
              // ctx.strokeRect(x1,y1,x2-x1,y2-y1);
              // ctx.save();
            }
            canvas.onmouseup = function (ev) {
              ctx.lineWidth = 2
              ctx.strokeStyle = 'rgba(52, 136, 255, 1)'
              ctx.fillStyle = 'rgba(52, 136, 255, 0.5)'
              ctx.fill()
              _this.pointList.push({x: x2/900, y: y2/_this.currentImgHeight})
              //console.log(_this.pointList)

              ctx.strokeRect(x1, y1, x2 - x1, y2 - y1)
              c.onmousemove = null
              c.onmouseup = null
              ctx.closePath()
            }
          }
        }
      },
      // 以下方法用于清空数据缓存
      imgDataClear () {
        var c = document.getElementById('myCanvas')
        c.click()
        var cxt = c.getContent('2d')
        cxt.clearRect(0, 0, 800, 5000)
      },
      // 以下三个方法用来切换图片
      prevImg () {
        // console.log(this.imgPos);
        if (this.imgPos > 0 && this.imgList.length != 0) {
          this.imgPos--
          this.draw()
        }
        else{
          this.$notify({
            title: '第一张',
            message: '已经是第一张了',
            type: 'warning',
            duration: 2000,
            position: 'bottom-left'
          })
        }
        this.imgDataClear()
      },
      nextImg () {
        // console.log(this.imgPos);
        if (this.imgPos < this.imgList.length - 1) {
          this.imgPos++
          this.draw()
        }
        else{
          this.$notify({
            title: '最后一张',
            message: '已经是最后一张了',
            type: 'warning',
            duration: 2000,
            position: 'bottom-left'
          })
        }
        this.imgDataClear()
      },
      getAcceptMission(){
        if(localStorage.getItem('username')!="visitor"){
          var _this = this;
          var xmlhttp = new XMLHttpRequest()
          xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
              var acceptMission = JSON.parse(xmlhttp.responseText)
              _this.acceptMission = acceptMission;
              _this.missionType = _this.acceptMission.type;
              _this.labeled = _this.acceptMission.finished;
              //console.log(acceptMission.id)
              //console.log(_this.labeled)
              _this.projectRate = _this.acceptMission.score;
              _this.missionID = _this.acceptMission.id;
            }
          }
          let formData = new FormData()
          formData.append('username', localStorage.getItem('username'))
          formData.append('missionID', localStorage.getItem('missionID'))
          console.log(localStorage.getItem('missionID'))
          xmlhttp.open('POST', 'http://localhost:8080/counts/mission/getAcceptedMissionByUsernameMissionID', true)
          xmlhttp.send(formData)
        }
      },
      updateLabeled(labeled){
        console.log("labeled")
        var _this = this;
        _this.acceptMission.finished = labeled;
        //console.log(this.labeled)
        console.log(_this.acceptMission.finished)
        var _this = this;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            if (JSON.parse(xmlhttp.responseText) == 'SUCCESS') {
              console.log("labeled更新成功")
            }
          }
        }
        xmlhttp.open('POST', 'http://localhost:8080/counts/mission//updateAcceptedMission', true)
        xmlhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8')
        xmlhttp.send(JSON.stringify(_this.acceptMission))

      },
      getAutoMission(){
        if(localStorage.getItem('uesrname')!='visitor'){
          var _this = this;
          var xmlhttp3 = new XMLHttpRequest()
          xmlhttp3.onreadystatechange = function () {
            if (xmlhttp3.readyState == 4 && xmlhttp3.status == 200) {
              var autoMission = JSON.parse(xmlhttp3.responseText)
              _this.autoMission = autoMission;
              //console.log(autoMission)
              _this.missionType = _this.autoMission.type;
              _this.labeled = _this.autoMission.finished;
              _this.missionID = _this.autoMission.id;
              _this.status  = _this.autoMission.status
              //console.log(_this.status)
              //console.log("123456")
              if(_this.status=='Train'){
                _this.correctJudge = true;
              }

            }
          }
          let formData = new FormData()
          formData.append('username', localStorage.getItem('username'))
          formData.append('missionid', localStorage.getItem('missionID'))
          //console.log(localStorage.getItem('missionID'))
          xmlhttp3.open('POST', 'http://localhost:8080/counts/mission/getAutoMission/signalworker', false)
          xmlhttp3.send(formData)
        }
      },


    }
  }
</script>

<style scoped>

  .toolBar {
    position: absolute;
    right: 0px;
    top: 57px;
    width: 15%;
    height: 580px;
    background-color: #e9e9e9;
    border: 1px solid rgba(156, 162, 148, 0.75);
  }

  .work_place {
    position: absolute;
    top: 240px;
    width: 60%;
    left: 20.5%;

  }

  .carousel img {
    width: 90%;
    position: absolute;
    top: 0px;
    left: 5%;
  }

  .labelType {
    position: absolute;
    top: 0px;
    left: 5%;
    width: 90%;
    height: 200px;

  }

  .labelType .labelType_text {
    position: absolute;
    top: 20px;
    left: 10px;
  }

  .labelType .labelType_opera {
    position: absolute;
    top: 50px;
    left: 5px;
    width: 150px;

  }

  #table_btn{
    position: absolute;
    top:200px;
    left: 20px;
  }

  .changeImg{
    position: absolute;
    top: 335px;
    left: 5%;
    height: 90px;
    width: 90%;

  }

  .changeImg .changeImg_text {
    position: absolute;
    top: 20px;
    left: 10px;
  }

  .changeImg .changeImg_opera{
    position: absolute;
    top: 50px;
    left: 10px;
  }

  .operationToImg {
    position: absolute;
    top: 440px;
    left: 5%;
    height: 90px;
    width: 90%;

  }

  .operationToImg .operationToImg_opera{
    position: absolute;
    top: 50px;
    left: 10px;
  }

  .otherOperation {
    position: absolute;
    top: 40%;
    left: 5%;
    width: 90%;
    height: 90px;

  }

  .otherOperation .otherOperation_text {
    position: absolute;
    top: 20px;
    left: 10px;
  }

  .operationToImg .operationToImg_text {
    position: absolute;
    top: 20px;
    left: 10px;
  }

  .otherOperation .otherOperation_opera {
    position: absolute;
    top: 50px;
    left: 10px;
  }

  .happy_scroll{
    position: absolute;
    top: 30px;
    left: 15%;
    width:70%;
    height: 90%;
  }

  .upload_warp_img_div_del {
    position: absolute;
    top: 0px;
    width: 20px;
    left: 0px;
  }

  .upload_warp_img_div_del img {
    width: 20px;
  }


  #title{
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    color: #6699cc;
  }

  .el-tab-pane{
    height: 55px;
  }



</style>
