<template>
  <div>
    <a href="/static/balabala.csv" v-bind:download="downloadFileName">download data</a>
  </div>
</template>

<script>
  export default {
    name: "test",
    data(){
      return{
        downloadFileName:'test csv'
      }
    },
    methods:{
      downloadData(){

      }
    }
  }
</script>

<style scoped>

</style>
