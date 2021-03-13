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
            <el-button type="success" @click="handleSelect(scope.row)">选课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
    export default {
      name: "S_Course",
      data() {
        return {
          search: '',
          active_term: '选课未开始', //当前选课学期
          courses: [],
          selected_courses: []
        }
      },
      props: {
        username: String
      },
      methods: {
        getCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_GetCoursesServlet'
          ).then(function (data) {
            this.courses=data.data;
          });
        },
        getSelectedCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_GetSelectedCoursesServlet',{
            params: {
              username: this.username
            }
          }).then(function (data) {
            this.selected_courses=data.data;
          });
        },
        toCode: function(str) {     //上课时间转换为二进制数组，日期格式形如：二1-3 四1-3,5
          var array=new Array(65).fill(0);
          var split1=str.split(" ");
          for(var i=0;i<split1.length;i++)
          {
            var base;
            if(split1[i].charAt(0)=='一')
              base=-1;
            else if(split1[i].charAt(0)=='二')
              base=12;
            else if(split1[i].charAt(0)=='三')
              base=25;
            else if(split1[i].charAt(0)=='四')
              base=38;
            else if(split1[i].charAt(0)=='五')
              base=51;
            split1[i]=split1[i].slice(1);
            var split2=split1[i].split(",");
            for(var j=0;j<split2.length;j++)
            {
              var split3=split2[j].split("-");
              if(split3.length==1)
                array[base+parseInt(split3[0])]=1;
              else if(split3.length==2)
              {
                for(var k=parseInt(split3[0]);k<=parseInt(split3[1]);k++)
                  array[base+k]=1;
              }
            }
          }
          return array;
        },
        handleSelect: function(row) {
          var selected=new Array(65).fill(0); //已经选过的时间
          var current=this.toCode(row.time);
          for(var i=0;i<this.selected_courses.length;i++)
          {
            var array=this.toCode(this.selected_courses[i].time);
            for(var j=0;j<65;j++)
            {
              if(array[j]==1)
                selected[j]=1;
            }
          }
          var flag=1;
          for(var i=0;i<65;i++)
          {
            if(selected[i]==1 && current[i]==1)
            {
              flag=0;
              break;
            }
          }
          if(flag)
          {
            this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/S_SelectServlet', {
              params: {
                s_number: this.username,
                term: this.active_term,
                c_number: row.c_number,
                t_number: row.t_number
              }
            }).then(function(data) {
              if (data.data==1) {
                this.$notify({
                  title: '选课成功',
                  type: 'success',
                  position: 'bottom-left'
                });
                this.getSelectedCourses();
              }
              else
              {
                this.$notify({
                  title: '选课失败',
                  type: 'error',
                  position: 'bottom-left'
                });
                console.log(data.data);
              }
            })
          }
          else
          {
            this.$notify({
              title: '时间冲突',
              type: 'error',
              position: 'bottom-left'
            });
          }
        }
      },
      computed: {
        filtered: function() {
          if(!this.search){
            return this.courses;
          }
          var array = this.courses;
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
        this.getCourses();
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
