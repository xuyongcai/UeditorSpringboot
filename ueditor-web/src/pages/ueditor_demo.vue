<template>
  <div id="app">
      <div>
        <UEditor :config=config ref="ueditor"></UEditor>
      </div>
  </div>
</template>

<script>
  import UEditor from '@/components/ueditor/ueditor.vue'
        

  export default{
      name: 'hello',
      components: {UEditor},
      data(){
        return {
          config: {
            //可以在此处定义工具栏的内容
            //适用于邮箱
            toolbars: [
             ['source', '|', 'fullscreen', 'undo', 'redo', '|', 'bold', 'italic', 'underline',
             '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist' , '|', 'fontfamily', 'fontsize', 'justifyleft', 'justifycenter', 'justifyright', '|', 'simpleupload', 'insertimage', 'emotion', '|', 'indent', 'blockquote', 'link', 'removeformat', 'formatmatch']
            ],
            //适用于评论
            // toolbars: [
            //  ['insertcode', 'bold', 'italic', '|', 'insertorderedlist', 'insertunorderedlist' , '|', 'simpleupload', 'emotion', 'link', '|', 'blockquote', 'undo', 'redo']
            // ],
            autoHeightEnabled: false,
            autoFloatEnabled: true,
            initialContent:'请输入内容',   //初始化编辑器的内容,也可以通过textarea/script给值，看官网例子
            autoClearinitialContent:true, //是否自动清除编辑器初始内容，注意：如果focus属性设置为true,这个也为真，那么编辑器一上来就会触发导致初始化的内容看不到了
            initialFrameWidth: null,
            initialFrameHeight: 450,
            BaseUrl: '',
            UEDITOR_HOME_URL: 'static/ueditor/'
          },
          addFormVisible: false
        }
      },
      methods: {
        //获取文档内容
        getContent: function(){
          let content = this.$refs.ueditor.getUEContent();
          console.log(content);
          alert(content);
        }
      }
  }

</script>
