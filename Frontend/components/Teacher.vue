<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <h5>{{this.username}}</h5>
      <el-menu
        class="el-menu-vertical-demo"
        router>
        <el-menu-item index="/t_course">
          <i class="el-icon-menu"></i>
          <span slot="title">课程</span>
        </el-menu-item>
        <el-menu-item index="/t_changepassword">
          <i class="el-icon-setting"></i>
          <span slot="title">修改密码</span>
        </el-menu-item>
      </el-menu>
      <el-button type="danger" @click="handleLogout">退出</el-button>
    </el-aside>
    <el-main>
      <router-view :username="username"></router-view>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "Teacher",
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

</style>
