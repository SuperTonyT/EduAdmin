<template>
  <div id="app2">
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <h5>{{this.username}}</h5>
        <el-menu
          class="el-menu-vertical-demo"
          router>
          <el-menu-item index="/a_control">
            <i class="el-icon-menu"></i>
            <span slot="title">选课登分控制</span>
          </el-menu-item>
          <el-menu-item index="/a_student">
            <i class="el-icon-document"></i>
            <span slot="title">学生表</span>
          </el-menu-item>
          <el-menu-item index="/a_teacher">
            <i class="el-icon-document"></i>
            <span slot="title">教师表</span>
          </el-menu-item>
          <el-menu-item index="/a_institute">
            <i class="el-icon-document"></i>
            <span slot="title">学院表</span>
          </el-menu-item>
          <el-menu-item index="/a_course">
            <i class="el-icon-document"></i>
            <span slot="title">课程表</span>
          </el-menu-item>
          <el-menu-item index="/a_activecourse">
            <i class="el-icon-document"></i>
            <span slot="title">开课表</span>
          </el-menu-item>
          <el-menu-item index="/a_changepassword">
            <i class="el-icon-setting"></i>
            <span slot="title">修改密码</span>
          </el-menu-item>
        </el-menu>
        <el-button type="danger" @click="handleLogout">退出</el-button>
      </el-aside>

      <el-container>
        <el-main>
          <router-view :username="username"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Admin",
    data() {
      return {
        username: ''
      }
    },
    methods: {
      handleLogout() {
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/LogoutServlet'
        ).then(function(data) {
          if (data.data==1) {
            this.$router.push("/login");
          }
        })
      }
    },
    created: function() {
      this.username=this.$route.params.username;
    }
  }
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
</style>
