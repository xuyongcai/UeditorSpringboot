<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item style="margin: 0 0 0 10px;" label="标题：" prop="title">
        <el-input maxlength="200" v-model="form.title" placeholder="请输入标题" style="width: 300px;"></el-input>
      </el-form-item>
      <el-form-item style="margin: 0;" prop="license_pic">
        <el-upload
          v-loading="uploading"
          action="http://localhost:8081/upload/upload"
          :show-file-list="false"
          :on-success="handleImageSuccess"
          :before-upload="beforeImageUpload">
          name="file"
          <div v-if="imageUrl" class="upload-image">
            <img v-if="!isImageFun(imageUrl)" :src="imageUrl">
            <div v-else style="border:1px solid #ccc;text-align: center;line-height: 150px;">
              PDF文件
            </div>
            <div class="upload-layer">重新上传</div>
          </div>
          <div v-else>
            <el-button icon="el-icon-link" size="small">添加附件</el-button>
            <div slot="tip" class="el-upload__tip" style="display: inline-block;margin-left: 5px;">（最大3G）</div>
          </div>
        </el-upload>
        <ul class="attachment-list">
          <li v-for="(item,index) in form.attachmentList">{{item.originalName}}</li>
        </ul>
      </el-form-item>

    </el-form>
      
    <script id="editor" type="text/plain" ></script>
  </div>
</template>

<script>
  import '../../../static/ueditor/ueditor.config.js'
  import '../../../static/ueditor/ueditor.all.js'
  import '../../../static/ueditor/lang/zh-cn/zh-cn.js'
  import '../../../static/ueditor/jquery-2.2.3.min.js'

  export default {
    name: "UEditor",
    props: {
      id: {
          type: String
      },
      config: {
          type: Object
      }
    },
    data() {
      return {
        editor: null,
        uploading: false,
        imageUrl: '',//图片地址
        rules: {
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
            { max: 200, message: '不能超过200个文字', trigger: 'blur' }
          ]
        },
        form: {
          id: '',
          title: '',
          recId: '',
          attachmentList: [],
          content: '',
        }
      }
    },
    create(){

    },
    mounted() {
      //初始化UE
      const _this = this;
      this.editor = UE.delEditor("editor");
      this.editor = UE.getEditor('editor',this.config);

        // console.log(44)
      console.log(this.editor)
    },
    destoryed() {
      this.editor.destory();
    },
    computed: {
      mixedConfig () {
        return Object.assign({}, this.defaultConfig, this.config);
      }
    },
    methods:{
      getUEContent: function(){
       return this.editor.getContent();
      },
      getContentTxt: function(){
        return this.editor.getContentTxt();
      },
      getAttachmentList: function(){
        return this.attachmentList;
      },
      upload(e) {
        var actionUrl = this.editor.getActionUrl(this.getOpt('fileActionName'))
        var fileMaxSize = this.getOpt('fileMaxSize')
        var acceptExtensions = (this.getOpt('fileAllowFiles') || []).join('').replace(/\./g, ',').replace(/^[,]/, '');;
                
        console.log(uploader)


      },
      //图片信息
      handleImageSuccess(res, file) {
        this.uploading = false

        var attachment = {}
        attachment.originalName = res.originalName
        attachment.actualName = res.actualName
        attachment.url = res.url
        attachment.size = res.size

        this.form.attachmentList.push(attachment)

        // console.log(this.form.attachmentList)
      },
      //上传图片前
      beforeImageUpload(file) {
        this.uploading = true
        // 上传大小限制，单位B，这里限制3GB
        const sizeFlag = file.size < 3221225472;
        if (!sizeFlag) {
          this.$message.error('上传文件大小不能超过 3G!')
          this.uploading = false
        }
        return sizeFlag
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
.attachment-list li {
  color: #42b983;
}
.attachment-icon{
    height: 20px !important;
    width: 20px !important;
    display: inline-block;
    vertical-align: middle;
    background-image: url(../../../static/ueditor/themes/default/images/icons.png);
    background-position: -620px -40px;
}
.btn-body{
  color: #0F6099!important;
  text-decoration: none;
}
</style>
