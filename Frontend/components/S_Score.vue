<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="5">
          <el-input v-model="search" placeholder="筛选" autosize></el-input>
        </el-col>
        <el-col :span="5">
          <el-select v-model="selected_term" placeholder="请选择学期" @input="GetCredit">
            <el-option
              v-for="item in terms"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <h5>平均绩点：{{this.credit}}</h5>
        </el-col>
      </el-row>
      <el-table
        :data="filtered"
        border
        style="width: 100%">
        <el-table-column
          prop="c_number"
          label="课号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="c_name"
          label="课名"
          width="150">
        </el-table-column>
        <el-table-column
          prop="t_number"
          label="教师号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="t_name"
          label="教师名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="time"
          label="上课时间"
          width="200">
        </el-table-column>
        <el-table-column
          prop="assignment_score"
          label="平时成绩"
          width="100">
        </el-table-column>
        <el-table-column
          prop="test_score"
          label="考试成绩"
          width="100">
        </el-table-column>
        <el-table-column
          prop="score"
          label="总评成绩"
          width="100">
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "S_Score",
      data() {
        return {
          scores: [],
          search: '',
          terms: [],
          selected_term: '',
          credit: ''
        }
      },
      props: {
        username: String
      },
      methods: {
        GetCredit: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_GetCreditServlet',{
            params: {
              s_number: this.username,
              term: this.selected_term
            }
          }).then(function (data) {
            this.credit=data.data;
          });
        }
      },
      computed: {
        filtered: function() {
          var array = this.scores;
          var string=this.selected_term;
          array = array.filter(function(item){
            if((item.term.indexOf(string) !== -1)){
              return item;
            }
          })
          if(!this.search){
            return array;
          }
          string = this.search;
          string = string.trim().toLowerCase();
          array = array.filter(function(item){
            if((item.c_number.toLowerCase().indexOf(string) !== -1) || (item.c_name.toLowerCase().indexOf(string) !== -1)){
              return item;
            }
          })
          return array;
        }
      },
      created() {
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_GetScoresServlet',{
          params: {
            username: this.username
          }
        }).then(function (data) {
          this.scores=data.data;
          for(var i=0;i<data.data.length;i++)
            this.terms.push(data.data[i].term);
        });
        setTimeout(() => {
          for(var i=0;i<this.terms.length;i++)  //去重
          {
            if(this.terms.indexOf(this.terms[i])!=i)
              this.terms.splice(i,i);
          }
        }, 1);
        setTimeout(() => {
          this.selected_term=this.terms[this.terms.length-1];
        }, 1);
        setTimeout(() => {
          this.GetCredit();
        }, 1);
      }
    }
</script>

<style scoped>

</style>
