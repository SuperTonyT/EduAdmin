<template>
  <div>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="5">
          <el-input v-model="search" placeholder="筛选" autosize></el-input>
        </el-col>
        <el-col :span="5">
          <el-select v-model="selected_term" placeholder="请选择学期" @input="termChanged">
            <el-option
              v-for="item in terms"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-select v-model="selected_course" placeholder="请选择课程" @input="courseChanged">
            <el-option
              v-for="item in term_courses"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <h5>上课时间：{{this.time}}</h5>
        </el-col>
        <el-col :span="4">
          <h5>平均分：{{this.avg_score}}</h5>
        </el-col>
      </el-row>
      <el-table
        :data="filtered"
        border
        style="width: 100%">
        <el-table-column
          prop="s_number"
          label="学号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="s_name"
          label="姓名"
          width="100">
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
        <el-table-column
          label="操作"
          width="60">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="handleEdit1(scope.row)" :disabled="active_term!=selected_term"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>

  <el-dialog title="登记成绩" :visible.sync="dialogVisible">
    <el-form :model="form">
      <el-form-item label="学期" :label-width="formLabelWidth">
        <el-input v-model="form.term" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="课名" :label-width="formLabelWidth">
        <el-input v-model="form.c_name" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="课号" :label-width="formLabelWidth">
        <el-input v-model="form.c_number" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-input v-model="form.s_name" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="学号" :label-width="formLabelWidth">
        <el-input v-model="form.s_number" autocomplete="off" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="平时成绩" :label-width="formLabelWidth">
        <el-input v-model="form.assignment_score" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="考试成绩" :label-width="formLabelWidth">
        <el-input v-model="form.test_score" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleEdit2">确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "T_Course",
      data() {
        return {
          courses: [],
          search: '',
          terms: [],
          selected_term: '',
          term_courses: [], //当前学期课程
          selected_course: '',
          active_term: '', //允许登分学期
          time: '',
          avg_score: '',
          dialogVisible: false,
          formLabelWidth: '100px',
          form: {
            term: '',
            c_name: '',
            c_number: '',
            s_name:'',
            s_number:'',
            assignment_score: '',
            test_score: ''
          }
        }
      },
      props: {
        username: String
      },
      methods: {
        getCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/T_GetCoursesServlet',{
            params: {
              t_number: this.username
            }
          }).then(function (data) {
            this.courses=data.data;
          });
        },
        courseChanged: function () {
          var sum=0;
          var count=0;
          for(var i=0;i<this.courses.length;i++)
          {
            if(this.courses[i].term==this.selected_term && this.courses[i].c_name==this.selected_course)
            {
              sum += this.courses[i].score;
              count++;
              this.time=this.courses[i].time;
            }
          }
          this.avg_score=sum/count;
          this.avg_score=this.avg_score.toFixed(2);
        },
        termChanged: function () {
          var array=new Array();
          for(var i=0;i<this.courses.length;i++)
          {
            if(this.courses[i].term==this.selected_term && array.indexOf(this.courses[i].c_name)==-1)
              array.push(this.courses[i].c_name);
          }
          this.term_courses=array;
          this.selected_course=this.term_courses[0];
          this.courseChanged();
        },
        handleEdit1(row) {
          this.form.term=row.term;
          this.form.c_name=row.c_name;
          this.form.c_number=row.c_number;
          this.form.s_name=row.s_name;
          this.form.s_number=row.s_number;
          this.form.assignment_score=row.assignment_score;
          this.form.test_score=row.test_score;
          this.dialogVisible=true;
        },
        handleEdit2: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/T_EditServlet', {
            params: {
              assignment_score: this.form.assignment_score,
              test_score: this.form.test_score,
              s_number: this.form.s_number,
              term: this.form.term,
              c_number: this.form.c_number
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '登记成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getCourses();
              setTimeout(() => {
                this.courseChanged();
              }, 50);
              this.dialogVisible=false;
            }
            else
            {
              this.$notify({
                title: '登记失败',
                type: 'error',
                position: 'bottom-left'
              });
              console.log(data.data);
            }
          })
        }
      },
      computed: {
        filtered: function() {
          var array = this.courses;
          var string=this.selected_term;
          array = array.filter(function(item){
            if((item.term.indexOf(string) !== -1)){
              return item;
            }
          });
          string=this.selected_course;
          array = array.filter(function(item){
            if((item.c_name.indexOf(string) !== -1)){
              return item;
            }
          });
          if(!this.search){
            return array;
          }
          string = this.search;
          string = string.trim().toLowerCase();
          array = array.filter(function(item){
            if((item.s_number.toLowerCase().indexOf(string) !== -1) || (item.s_name.toLowerCase().indexOf(string) !== -1)){
              return item;
            }
          })
          return array;
        }
      },
      created: function () {
        this.getCourses();
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetScoreStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.active_term=data.data;
          }
        });
        setTimeout(() => {  //保证courses数据初始化完成
          for(var i=0;i<this.courses.length;i++)  //去重
          {
            if(this.terms.indexOf(this.courses[i].term)==-1)
              this.terms.push(this.courses[i].term);
          }
        }, 1);
        setTimeout(() => {
          this.selected_term=this.terms[this.terms.length-1];
        }, 1);
        setTimeout(() => {
          this.termChanged();
        }, 1);
      }
    }
</script>

<style scoped>

</style>
