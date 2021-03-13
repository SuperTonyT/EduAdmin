<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="2.5">
          <h5>原密码：</h5>
        </el-col>
        <el-col :span="5">
          <el-input
            v-model="oldp"
            placeholder="请输入原密码">
          </el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="2.5">
          <h5>新密码：</h5>
        </el-col>
        <el-col :span="5">
          <el-input
            v-model="newp"
            placeholder="请输入新密码">
          </el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="1">
          <el-button type="primary" @click="handleChange">修改</el-button>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "A_ChangePassword",
      data() {
        return {
          oldp: '',
          newp: ''
        }
      },
      props: {
        username: String
      },
      methods: {
        handleChange() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/ChangePasswordServlet', {
            params: {
              username: this.username,
              oldp: this.oldp,
              newp: this.newp
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '修改成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.oldp='';
              this.newp='';
            }
            else if(data.data==0)
            {
              this.$notify({
                title: '原密码错误',
                type: 'error',
                position: 'bottom-left'
              });
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
