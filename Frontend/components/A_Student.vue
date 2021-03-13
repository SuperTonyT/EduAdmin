<template>
  <div>
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="5">
            <el-input v-model="search" placeholder="筛选" autosize></el-input>
          </el-col>
          <el-col :span="1">
            <el-button type="primary" @click="dialogVisible1=true">新增</el-button>
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
            label="学号"
            width="150">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="120">
          </el-table-column>
          <el-table-column
            prop="sex"
            label="性别"
            width="50">
          </el-table-column>
          <el-table-column
            prop="birthday"
            label="出生日期"
            width="150">
          </el-table-column>
          <el-table-column
            prop="birthplace"
            label="籍贯"
            width="150">
          </el-table-column>
          <el-table-column
            prop="t_num"
            label="手机号码"
            width="150">
          </el-table-column>
          <el-table-column
            prop="institute"
            label="院系号"
            width="100">
          </el-table-column>
          <el-table-column
            label="操作"
            width="150">
            <template slot-scope="scope">
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
              <el-button type="primary" icon="el-icon-edit" circle @click="handleUpdate1(scope.row)"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

    <el-dialog title="新增学生" :visible.sync="dialogVisible1">
      <el-form :model="form">
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="form.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" :label-width="formLabelWidth">
          <el-input v-model="form.birthday" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="籍贯" :label-width="formLabelWidth">
          <el-input v-model="form.birthplace" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" :label-width="formLabelWidth">
          <el-input v-model="form.t_num" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="院系号" :label-width="formLabelWidth">
          <el-input v-model="form.institute" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改学生信息" :visible.sync="dialogVisible2">
      <el-form :model="form2">
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="form2.number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form2.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-input v-model="form2.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" :label-width="formLabelWidth">
          <el-input v-model="form2.birthday" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="籍贯" :label-width="formLabelWidth">
          <el-input v-model="form2.birthplace" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" :label-width="formLabelWidth">
          <el-input v-model="form2.t_num" autocomplete="off"></el-input>
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
  </div>
</template>

<script>
    export default {
      name: "A_Student",
      data() {
        return {
          search: '',
          formLabelWidth: '100px',
          dialogVisible1: false,
          dialogVisible2: false,
          students: '',
          form: {
            number: '',
            name: '',
            sex: '',
            birthday: '',
            birthplace: '',
            t_num: '',
            institute: ''
          },
          form2: {
            number: '',
            name: '',
            sex: '',
            birthday: '',
            birthplace: '',
            t_num: '',
            institute: ''
          }
        }
      },
      methods: {
        getStudents: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetStudentsServlet'
          ).then(function (data) {
            this.students=data.data;
          });
        },
        handleDelete(row) {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_DeleteStudentServlet', {
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
              this.getStudents();
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
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_AddStudentServlet', {
            params: {
              number: this.form.number,
              name: this.form.name,
              sex: this.form.sex,
              birthday: this.form.birthday,
              birthplace: this.form.birthplace,
              t_num: this.form.t_num,
              institute: this.form.institute
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '添加成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getStudents();
              this.dialogVisible1=false;
              this.form.number='';
              this.form.name='';
              this.form.sex='';
              this.form.birthday='';
              this.form.birthplace='';
              this.form.t_num='';
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
          this.form2.sex=row.sex;
          this.form2.birthday=row.birthday;
          this.form2.birthplace=row.birthplace;
          this.form2.t_num=row.t_num;
          this.form2.institute=row.institute;
          this.dialogVisible2=true;
        },
        handleUpdate2() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_UpdateStudentServlet', {
            params: {
              number: this.form2.number,
              name: this.form2.name,
              sex: this.form2.sex,
              birthday: this.form2.birthday,
              birthplace: this.form2.birthplace,
              t_num: this.form2.t_num,
              institute: this.form2.institute
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '更新成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getStudents();
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
        }
      },
      computed: {
        filtered: function() {
          if(!this.search){
            return this.students;
          }
          var array = this.students;
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
        this.getStudents();
        var a=new Array(5).fill(0);
        a[0]="四1-3";
        if(a[0].charAt(0)=='四')
          console.log(a);
        var b="四1-3";
        console.log(b.slice(1));
        console.log(b.charAt(0));
      }
    }
</script>

<style scoped>

</style>
