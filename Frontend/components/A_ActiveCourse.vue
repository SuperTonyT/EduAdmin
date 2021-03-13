<template>
  <div>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="5">
            <el-input v-model="search" placeholder="筛选" autosize></el-input>
          </el-col>
          <el-col :span="5">
            <el-select v-model="selected_term" placeholder="请选择学期">
              <el-option
                v-for="item in terms"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
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
            prop="t_number"
            label="工号"
            width="150">
          </el-table-column>
          <el-table-column
            prop="time"
            label="上课时间"
            width="150">
          </el-table-column>
          <el-table-column
            label="操作"
            width="150">
            <template slot-scope="scope">
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)" :disabled="selected_term!=active_term"></el-button>
              <el-button type="primary" icon="el-icon-edit" circle @click="handleUpdate1(scope.row)"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

    <el-dialog title="修改上课时间" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="form.term" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="课号" :label-width="formLabelWidth">
          <el-input v-model="form.c_number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="工号" :label-width="formLabelWidth">
          <el-input v-model="form.t_number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="上课时间" :label-width="formLabelWidth">
          <el-input v-model="form.time" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdate2">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "A_ActiveCourse",
      data() {
        return {
          terms: '',
          active_term: '',
          selected_term: '',
          search: '',
          formLabelWidth: '100px',
          dialogVisible: false,
          courses: [], //不定义为数组filter会报错
          form: {
            term: '',
            c_number: '',
            t_number: '',
            time: ''
          }
        }
      },
      methods: {
        getActiveCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetActiveCoursesServlet'
          ).then(function (data) {
            this.courses=data.data;
          });
        },
        handleDelete(row) {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_DeleteActiveCourseServlet', {
            params: {
              term: row.term,
              c_number: row.c_number,
              t_number: row.t_number
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '删除成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getActiveCourses();
            }
            else
            {
              this.$notify({
                title: '删除失败',
                type: 'error',
                position: 'bottom-left'
              });
              console.log(data.data);
            }
          })
        },
        handleUpdate1(row) {
          this.form.term=row.term;
          this.form.c_number=row.c_number;
          this.form.t_number=row.t_number;
          this.form.time=row.time;
          this.dialogVisible=true;
        },
        handleUpdate2() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_UpdateActiveCourseServlet', {
            params: {
              term: this.form.term,
              c_number: this.form.c_number,
              t_number: this.form.t_number,
              time: this.form.time
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '更新成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getActiveCourses();
              this.dialogVisible=false;
            }
            else
            {
              this.$notify({
                title: '更新失败',
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
          })
          if(!this.search){
            return array;
          }
          string = this.search;
          string = string.trim().toLowerCase();
          array = array.filter(function(item){
            if((item.c_number.toLowerCase().indexOf(string) !== -1) || (item.t_number.toLowerCase().indexOf(string) !== -1)){
              return item;
            }
          })
          return array;
        }
      },
      created: function() {
        this.getActiveCourses();
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetTermsServlet'
        ).then(function(data) {
          this.terms=data.data;
        });
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetCourseStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.active_term=data.data;
          }
        });
        setTimeout(() => {
          this.selected_term=this.terms[this.terms.length-1];
        }, 1); //确保数据初始化完成
      }
    }
</script>

<style scoped>

</style>
