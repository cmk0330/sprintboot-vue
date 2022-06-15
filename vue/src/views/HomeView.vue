<template>
  <div style="padding: 8px">
    <div style="margin: 8px">
      <el-button type="primary" @click="addUser">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%;"></el-input>
      <el-button type="primary" style="margin-left: 16px" @click="loadUser">搜索</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="id"
          label="id"
          width="180"
          sortable>
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="nickName"
          label="昵称"
          width="180">
      </el-table-column>
      <el-table-column
          prop="age"
          label="年龄"
          width="180">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别"
          width="180">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template #default="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="text" size="mini" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 16px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" style="width: 80%" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>


import request from "@/urils/request";

export default {
  name: "Home",

  data() {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      dialogVisible: false,
      search: "",
      total: 60,
      currentPage: 1,
      pageSize: 10,
      form: {},
    }
  },
  mounted() {
    this.loadUser()
  },
  methods: {
    loadUser() {
      request.get("/user", {
        params: {
          pageIndex: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log('loadUser:', res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    addUser() {
      this.dialogVisible = true
      this.form = {}
    },

    handleDelete(id) {
      console.log('id', id)
      request.delete("/user/" + id).then(res => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "删除完成"
          })
          this.loadUser()
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    saveUser() {
      if (this.form.id) {// 更新
        request.put("/user", this.form).then(res => {
          console.log('user', res)
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "更新完成"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      } else { // 新增
        request.post("/user", this.form).then(res => {
          console.log('user', res)
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "新增完成"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
      this.loadUser()
      this.dialogVisible = false
    },
    handleClick(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleSizeChange(pageSize) { // 改变每页数量
      this.pageSize = pageSize
      this.loadUser()
    },
    handleCurrentChange(pageIndex) { // 改变当前页码
      this.currentPage = pageIndex
      this.loadUser()
    }
  }
}
</script>
