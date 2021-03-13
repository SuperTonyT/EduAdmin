<template>
  <div id="app">
    <el-form ref="loginForm" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号">
        <el-input type="text" v-model="username"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="login">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
        name: "Login",
      data() {
          return {
            username: '',
            password: ''
          }
      },
      methods: {
        login: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/LoginServlet', {
            params: {
              username: this.username,
              password: this.password
            }
          }).then(function (data) {
            if (data.data==0) {
              alert("用户名或密码错误");
            }
            else if(data.data.identity==1)
            {
              this.$router.push({name:"Admin", params:{username:this.username}});
            }
            else if(data.data.identity==2)
            {
              this.$router.push({name:"Teacher", params:{username:this.username}});
            }
            else if(data.data.identity==3)
            {
              this.$router.push({name:"Student", params:{username:this.username}});
            }
          });
        }
      }
    }
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }
</style>
