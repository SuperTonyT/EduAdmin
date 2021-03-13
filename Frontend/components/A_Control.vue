<template>
  <el-container>
    <el-main>
    <el-row>
      <el-col :span="2.5">
        <h5>当前选课学期：</h5>
      </el-col>
      <el-col :span="5">
        <el-input
          v-model="course_term"
          placeholder="请输入选课学期"
          :disabled="course_switch">
        </el-input>
      </el-col>
      <el-col :span="1">
        <el-switch
          v-model="course_switch"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="course_change">
        </el-switch>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="2.5">
        <h5>当前登分学期：</h5>
      </el-col>
      <el-col :span="5">
        <el-select v-model="score_term" placeholder="请选择登分学期">
          <el-option
            v-for="item in terms"
            :key="item"
            :label="item"
            :value="item"
            :disabled="score_switch">
          </el-option>
         </el-select>
      </el-col>
      <el-col span="1">
        <el-switch
          v-model="score_switch"
          active-color="#13ce66"
          inactive-color="#ff4949"
          @change="score_change">
        </el-switch>
      </el-col>
    </el-row>
    </el-main>
  </el-container>
</template>

<script>
    export default {
        name: "A_Control",
      data() {
          return {
            course_term: '',
            course_switch: false,
            terms: '',
            score_term: '',
            score_switch: false
          }
      },
      methods: {
          course_change: function(callback){
            if(callback==false)
              this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/DeleteCourseStateServlet');
            else
              this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/SetCourseStateServlet', {
                params: {
                  term:this.course_term
                }
              })
          },
          score_change: function(callback){
            if(callback==false)
              this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/DeleteScoreStateServlet');
            else
              this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/SetScoreStateServlet', {
                params: {
                  term:this.score_term
                }
              })
          }
      },
      created() {
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetCourseStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.course_term=data.data;
            this.course_switch=true;
          }
        });
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetTermsServlet'
        ).then(function(data) {
          this.terms=data.data;
        });
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetScoreStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.score_term=data.data;
            this.score_switch=true;
          }
        });
      }
    }
</script>

<style scoped>

</style>
