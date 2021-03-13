<template>
  <el-container>
    <el-header height="30px">
      <el-menu class="el-menu-demo" mode="horizontal" router>
        <el-menu-item index="/s_course">课程表</el-menu-item>
        <el-menu-item index="/s_selected">已选课程</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view :username="username"></router-view>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "S_Choose",
      props: {
        username: String
      },
    }
</script>

<style scoped>

</style>
