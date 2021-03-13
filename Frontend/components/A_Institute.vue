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
            label="院系号"
            width="100">
          </el-table-column>
          <el-table-column
            prop="name"
            label="名称"
            width="150">
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
            width="300">
          </el-table-column>
          <el-table-column
            prop="t_num"
            label="联系电话"
            width="150">
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

    <el-dialog title="新增学院" :visible.sync="dialogVisible">
      <el-form :model="form">
        <el-form-item label="院系号" :label-width="formLabelWidth">
          <el-input v-model="form.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="form.t_num" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改学院信息" :visible.sync="dialogVisible2">
      <el-form :model="form2">
        <el-form-item label="院系号" :label-width="formLabelWidth">
          <el-input v-model="form2.number" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="form2.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form2.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="form2.t_num" autocomplete="off"></el-input>
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
      name: "A_Institute",
      data() {
        return {
          search: '',
          formLabelWidth: '100px',
          dialogVisible: false,
          dialogVisible2: false,
          institutes: '',
          form: {
            number: '',
            name: '',
            address: '',
            t_num: ''
          },
          form2: {
            number: '',
            name: '',
            address: '',
            t_num: ''
          }
        }
      },
      methods: {
        getInstitutes: function () {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_GetInstitutesServlet'
          ).then(function (data) {
            this.institutes=data.data;
          });
        },
        handleDelete(row) {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_DeleteInstituteServlet', {
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
              this.getInstitutes();
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
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_AddInstituteServlet', {
            params: {
              number: this.form.number,
              name: this.form.name,
              address: this.form.address,
              t_num: this.form.t_num
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '添加成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getInstitutes();
              this.dialogVisible=false;
              this.form.number='';
              this.form.name='';
              this.form.address='';
              this.form.t_num='';
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
          this.form2.address=row.address;
          this.form2.t_num=row.t_num;
          this.dialogVisible2=true;
        },
        handleUpdate2() {
          this.$http.get('http://localhost:8080/EducationalAdministration_war_exploded/A_UpdateInstituteServlet', {
            params: {
              number: this.form2.number,
              name: this.form2.name,
              address: this.form2.address,
              t_num: this.form2.t_num
            }
          }).then(function(data) {
            if (data.data==1) {
              this.$notify({
                title: '更新成功',
                type: 'success',
                position: 'bottom-left'
              });
              this.getInstitutes();
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
            return this.institutes;
          }
          var array = this.institutes;
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
        this.getInstitutes();
      }
    }
</script>

<style scoped>

</style>
