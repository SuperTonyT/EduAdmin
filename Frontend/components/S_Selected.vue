<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="5">
          <el-input v-model="search" placeholder="筛选" autosize></el-input>
        </el-col>
        <el-col :span="5">
          <h5>当前选课学期：{{this.active_term}}</h5>
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
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="danger" @click="handleDelete(scope.row)">退课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "S_Selected",
      data() {
        return {
          search: '',
          active_term: '选课未开始',
          selected_courses: []
        }
      },
      props: {
        username: String
      },
      methods: {
        getSelectedCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_GetSelectedCoursesServlet',{
            params: {
              username: this.username
            }
          }).then(function (data) {
            this.selected_courses=data.data;
          });
        },
        handleDelete: function(row) {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_DeleteSelectedCourseServlet', {
            params: {
              s_number: this.username,
              c_number: row.c_number
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '退课成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getSelectedCourses();
            }
            else
            {
              this.$notify({
                title: '退课失败',
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
          if(!this.search){
            return this.selected_courses;
          }
          var array = this.selected_courses;
          var string = this.search;
          string = string.trim().toLowerCase();
          array = array.filter(function(item){
            if((item.c_number.toLowerCase().indexOf(string) !== -1) || (item.c_name.toLowerCase().indexOf(string) !== -1)
              || (item.t_number.toLowerCase().indexOf(string) !== -1) || (item.t_name.toLowerCase().indexOf(string) !== -1)
              || (item.time.toLowerCase().indexOf(string) !== -1))
            {
              return item;
            }
          })
          return array;
        }
      },
      created: function() {
        this.getSelectedCourses();
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetCourseStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.active_term=data.data;
          }
        });
      }
    }
</script>

<style scoped>

</style>
