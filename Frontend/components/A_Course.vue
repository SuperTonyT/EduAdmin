<template>
  <div>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="5">
            <el-input v-model="search" placeholder="筛选" autosize></el-input>
          </el-col>
          <el-col :span="1">
            <el-button type="primary" @click="dialogVisible=true">新增</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table
          :data="filtered"
          border
          style="width: 100%">
          <el-table-column
            prop="number"
            label="课号"
            width="150">
          </el-table-column>
          <el-table-column
            prop="name"
            label="课名"
            width="150">
          </el-table-column>
          <el-table-column
            prop="credit"
            label="学分"
            width="50">
          </el-table-column>
          <el-table-column
            prop="ratio"
            label="考试成绩占比"
            width="120">
          </el-table-column>
          <el-table-column
            prop="institute"
            label="院系号"
            width="100">
          </el-table-column>
          <el-table-column
            label="操作"
            width="220">
            <template slot-scope="scope">
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
              <el-button type="primary" icon="el-icon-edit" circle @click="handleUpdate1(scope.row)"></el-button>
              <el-button type="success" :disabled="flag" @click="handleOpen1(scope.row)" round>开课</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

    <el-dialog title="新增课程" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="课号" :label-width="formLabelWidth">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth">
          <el-input v-model="form.credit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试成绩占比" :label-width="formLabelWidth">
          <el-input v-model="form.ratio" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="院系号" :label-width="formLabelWidth">
          <el-input v-model="form.institute" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改课程信息" :visible.sync="dialogVisible2">
      <el-form :model="form2">
        <el-form-item label="课号" :label-width="formLabelWidth">
          <el-input v-model="form2.number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="课名" :label-width="formLabelWidth">
          <el-input v-model="form2.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分" :label-width="formLabelWidth">
          <el-input v-model="form2.credit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试成绩占比" :label-width="formLabelWidth">
          <el-input v-model="form2.ratio" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="院系号" :label-width="formLabelWidth">
          <el-input v-model="form2.institute" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdate2">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="开课" :visible.sync="dialogVisible3">
      <el-form :model="form3">
        <el-form-item label="学期" :label-width="formLabelWidth">
          <el-input v-model="form3.term" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="课号" :label-width="formLabelWidth">
          <el-input v-model="form3.c_number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="工号" :label-width="formLabelWidth">
          <el-input v-model="form3.t_number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上课时间" :label-width="formLabelWidth">
          <el-input v-model="form3.time" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible3 = false">取 消</el-button>
        <el-button type="primary" @click="handleOpen2">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "A_Course",
      data() {
        return {
          search: '',
          formLabelWidth: '100px',
          flag: true, //判断是否禁用开课按钮，即选课是否开启（false代表选课开启）
          dialogVisible: false,
          dialogVisible2: false,
          dialogVisible3: false,
          courses: '',
          form: {
            number: '',
            name: '',
            credit: '',
            ratio: '',
            institute: ''
          },
          form2: {
            number: '',
            name: '',
            credit: '',
            ratio: '',
            institute: ''
          },
          form3: {
            term: '',
            c_number: '',
            t_number: '',
            time: ''
          }
        }
      },
      methods: {
        getCourses: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetCoursesServlet'
          ).then(function (data) {
            this.courses=data.data;
          });
        },
        handleDelete(row) {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_DeleteCourseServlet', {
            params: {
              number: row.number
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '删除成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getCourses();
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
        handleAdd() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_AddCourseServlet', {
            params: {
              number: this.form.number,
              name: this.form.name,
              credit: this.form.credit,
              ratio: this.form.ratio,
              institute: this.form.institute
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '添加成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getCourses();
              this.dialogVisible=false;
              this.form.number='';
              this.form.name='';
              this.form.credit='';
              this.form.ratio='';
              this.form.institute='';
            }
            else
            {
              this.$notify({
                title: '添加失败',
                type: 'error',
                position: 'bottom-left'
              });
              console.log(data.data);
            }
          })
        },
        handleUpdate1(row) {
          this.form2.number=row.number;
          this.form2.name=row.name;
          this.form2.credit=row.credit;
          this.form2.ratio=row.ratio;
          this.form2.institute=row.institute;
          this.dialogVisible2=true;
        },
        handleUpdate2() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_UpdateCourseServlet', {
            params: {
              number: this.form2.number,
              name: this.form2.name,
              credit: this.form2.credit,
              ratio: this.form2.ratio,
              institute: this.form2.institute
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '更新成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getCourses();
              this.dialogVisible2=false;
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
        },
        handleOpen1(row) {
          this.dialogVisible3=true;
          this.form3.c_number=row.number;
        },
        handleOpen2() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_AddActiveCourseServlet', {
            params: {
              term: this.form3.term,
              c_number: this.form3.c_number,
              t_number: this.form3.t_number,
              time: this.form3.time
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '开课成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.dialogVisible3=false;
              this.form3.t_number='';
              this.form3.time='';
            }
            else
            {
              this.$notify({
                title: '开课失败',
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
            return this.courses;
          }
          var array = this.courses;
          var string = this.search;
          string = string.trim().toLowerCase();
          array = array.filter(function(item){
            if((item.number.toLowerCase().indexOf(string) !== -1) || (item.name.toLowerCase().indexOf(string) !== -1)){
              return item;
            }
          })
          return array;
        }
      },
      created: function() {
        this.getCourses();
        this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/GetCourseStateServlet'
        ).then(function(data) {
          if (data.data!=0) {
            data.data=data.data.slice(1,-1); //去掉冒号
            this.form3.term=data.data;
            this.flag=false;
          }
        });
      }
    }
</script>

<style scoped>

</style>
