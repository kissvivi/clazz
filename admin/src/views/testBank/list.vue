<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('testBank:list')"
            @click.native.prevent="geTestBankList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('testBank:add')"
            @click.native.prevent="showAddTestBankDialog"
          >添加题目</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="testBankList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="#" align="center" prop="id" width="80">
      </el-table-column>
      <el-table-column label="题目" align="center" prop="title" />
      <el-table-column label="类型" align="center" >
      <template slot-scope="scope" >{{ getTypeName(scope.row.type) }}</template>
      </el-table-column>
      <el-table-column label="正确选项" align="center" prop="selectOk" />
      <el-table-column label="修改时间" align="center" prop="time">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.time) }}</template>
      </el-table-column>
      <el-table-column
        label="管理"
        align="center"
        v-if="hasPermission('testBank:detail') || hasPermission('testBank:update') || hasPermission('testBank:delete')"
      >
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            v-if="hasPermission('testBank:detail')"
            @click.native.prevent="showRoleDialog(scope.$index)"
          >查看</el-button>
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('testBank:update')"
            @click.native.prevent="showUpdateRoleDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('testBank:delete')"
            @click.native.prevent="removeTestBank(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.size"
      :total="total"
      :page-sizes="[9, 18, 36, 72]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>

    <!-- 添加题目开始 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 500px; margin-left:50px;"
        :model="tempTestBank"
        :rules="createRules"
        ref="tempTestBank"
      >
        <el-form-item label="题目" prop="title" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempTestBank.title"
          ></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type" required>
          <el-select v-model="tempTestBank.type" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="选项A" prop="selectA" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="textarea"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempTestBank.selectA"
          ></el-input>
        </el-form-item>
        <el-form-item label="选项B" prop="selectB" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="textarea"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempTestBank.selectB"
          ></el-input>
        </el-form-item>
        <el-form-item label="选项C" prop="selectC" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="textarea"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempTestBank.selectC"
          ></el-input>
        </el-form-item>
        <el-form-item label="选项D" prop="selectD" required>
          <el-input
            :disabled="dialogStatus === 'show'"
            type="textarea"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tempTestBank.selectD"
          ></el-input>
        </el-form-item>
        <el-form-item label="正确选项" prop="selectOk" required>

          <el-radio-group  v-model="tempTestBank.selectOk">
              <el-radio label="A">A</el-radio>
              <el-radio label="B">B</el-radio>
              <el-radio label="C">C</el-radio>
              <el-radio label="D">D</el-radio>
              <el-radio label="无">无</el-radio>

            </el-radio-group>

          <!-- <el-input
            :disabled="dialogStatus === 'show'"
            type="text"
            prefix-icon="el-icon-check"
            auto-complete="off"
            v-model="tempTestBank.selectOK"
          ></el-input> -->
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          v-if="dialogStatus === 'add'"
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addTestBank"
        >添加</el-button>
        <el-button
          v-if="dialogStatus === 'update'"
          type="primary"
          :loading="btnLoading"
          @click.native.prevent="updateRole"
        >更新</el-button>
      </div>
    </el-dialog>
    <!-- 添加题目结束 -->
  </div>
</template>
<script>
  import {
    list as getTestBankList,
    add as addTestBank,
    remove,
  } from '@/api/testBank'
import {
  listRoleWithPermission,
  listResourcePermission,
  add as addRole,
  update as updateRole,
} from '@/api/role'
import { unix2CurrentTime } from '@/utils'
import { mapGetters } from 'vuex'

export default {
  created() {
    if (this.hasPermission('testBank:update')) {
      this.getPermissionList()
    }
    if (this.hasPermission('testBank:list')) {
      this.geTestBankList()
    }
  },
  data() {
    /**
     * 验证角色名
     * @param rule 规则
     * @param value 角色名
     * @param callback 回调
     */
    const validateRoleName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('角色名不能为空'))
      } else {
        callback()
      }
    }
    return {

      options: [{
                value: 101,
                label: '试题'
              }, {
                value: 102,
                label: '问卷调查'
              }],
      testBankList: [],
      permissionList: [],
      listLoading: false,
      total: 0,
      listQuery: {
        page: 1,
        size: 9
      },
      dialogStatus: 'add',
      dialogFormVisible: false,
      textMap: {
        add: '添加题目'
      },
      btnLoading: false,
      tempTestBank: {
        id: '',
        name: '',
        selectOk:'A',
      },
      createRules: {
        name: [{ required: true, trigger: 'blur', validator: validateRoleName }]
      }
    }
  },
  computed: {
    ...mapGetters(['roleName'])
  },
  methods: {
    unix2CurrentTime,


    /**
     * 根据code 得到类型
     * @param {Object} type
     */
    getTypeName(type){
      if(type == 101)
      return '试题'
      else
      return '问卷调查'
    },
    /**
     * 获取题库列表
     */
    geTestBankList() {
      this.listLoading = true
      getTestBankList(this.listQuery).then(response => {
        this.testBankList = response.data.list
        this.total = response.data.total
        this.listLoading = false
      }).catch(res => {
        this.$message.error('加载题库列表失败')
      })
    },
    /**
     * 改变每页数量
     * @param size 页大小
     */
    handleSizeChange(size) {
      this.listQuery.page = 1
      this.listQuery.size = size
      this.geTestBankList()
    },
    /**
     * 改变页码
     * @param page 页号
     */
    handleCurrentChange(page) {
      this.listQuery.page = page
      this.geTestBankList()
    },
    /**
     * 表格序号
     * @param index 数据下标
     * @returns 表格序号
     */
    getTableIndex(index) {
      return (this.listQuery.page - 1) * this.listQuery.size + index + 1
    },
    /**
     * 显示新增题目对话框
     */
    showAddTestBankDialog() {
      this.dialogFormVisible = true
      this.dialogStatus = 'add'
      // this.tempTestBank.name = ''
      // this.tempTestBank.id = ''
    },
    /**
     * 显示更新角色的对话框
     * @param index 角色下标
     */
    showUpdateRoleDialog(index) {
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
      const role = this.testBankList[index]
      this.tempTestBank.name = role.name
      this.tempTestBank.id = role.id
      this.tempTestBank.permissionIdList = []
      for (let i = 0; i < role.resourceList.length; i++) {
        const handleList = role.resourceList[i].handleList
        for (let j = 0; j < handleList.length; j++) {
          const handle = handleList[j]
          this.tempTestBank.permissionIdList.push(handle.id)
        }
      }
    },
    /**
     * 显示角色权限的对话框
     * @param index 角色下标
     */
    showRoleDialog(index) {
      this.dialogFormVisible = true
      this.dialogStatus = 'show'
      const role = this.testBankList[index]
      this.tempTestBank.name = role.name
      this.tempTestBank.id = role.id
      this.tempTestBank.permissionIdList = []
      let resourceList = []
      if (role.name === '超级管理员') {
        resourceList = this.permissionList
      } else {
        resourceList = role.resourceList
      }
      for (let i = 0; i < resourceList.length; i++) {
        const handleList = resourceList[i].handleList
        for (let j = 0; j < handleList.length; j++) {
          const handle = handleList[j]
          this.tempTestBank.permissionIdList.push(handle.id)
        }
      }
    },
    /**
     * 添加题目
     */
    addTestBank() {

      //this.$message.success('添加成功' +this.tempTestBank.type )

      this.$refs.tempTestBank.validate(valid => {
        // if (
        //   valid &&
        //   this.isRoleNameUnique(this.tempTestBank.id, this.tempTestBank.name)
        // ) {
          this.btnLoading = true
          addTestBank(this.tempTestBank).then(() => {
            this.$message.success('添加成功')
            this.geTestBankList()
            this.dialogFormVisible = false
            this.btnLoading = false
          }).catch(res => {
            this.$message.error('添加角色失败')
          })
        // } else {
        //   console.log('表单无效')
        // }
      })
    },
    // /**
    //  * 修改角色
    //  */
    // updateRole() {
    //   this.$refs.tempTestBank.validate(valid => {
    //     if (
    //       valid &&
    //       this.isRoleNameUnique(this.tempTestBank.id, this.tempTestBank.name)
    //     ) {
    //       this.btnLoading = true
    //       updateRole(this.tempTestBank).then(() => {
    //         this.$message.success('更新成功')
    //         this.geTestBankList()
    //         this.dialogFormVisible = false
    //         this.btnLoading = false
    //       }).catch(res => {
    //         this.$message.error('更新角色失败')
    //       })
    //     } else {
    //       console.log('表单无效')
    //     }
    //   })
    // },
    /**
     * 校验角色名是否已经存在
     * @param id 角色id
     * @param name 角色名
     * @returns {boolean}
     */
    isRoleNameUnique(id, name) {
      for (let i = 0; i < this.testBankList.length; i++) {
        if (this.testBankList[i].id !== id && this.testBankList[i].name === name) {
          this.$message.error('题目名已存在')
          return false
        }
      }
      return true
    },
    /**
     * 移除题目
     * @param index 角色下标
     * @returns {boolean}
     */
    removeTestBank(id) {
      this.$confirm('删除该题目？', '警告', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        const tbId = id
        remove(tbId).then(() => {
          this.$message.success('删除成功')
          this.geTestBankList()
        }).catch(() => {
          this.$message.error('删除失败')
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /**
     * 判断角色菜单内的权限是否一个都没选
     * @param index 下标
     * @returns {boolean}
     */
    isMenuNone(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempTestBank.permissionIdList.indexOf(handleList[i].id) > -1) {
          return false
        }
      }
      return true
    },
    /**
     * 判断角色菜单内的权限是否全选了
     * @param index 下标
     * @returns {boolean}
     */
    isMenuAll(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        if (this.tempTestBank.permissionIdList.indexOf(handleList[i].id) < 0) {
          return false
        }
      }
      return true
    },
    /**
     * 根据菜单状态check所有checkbox
     * @param index 下标
     */
    checkAll(index) {
      if (this.isMenuAll(index)) {
        // 如果已经全选了,则全部取消
        this.cancelAll(index)
      } else {
        // 如果尚未全选,则全选
        this.selectAll(index)
      }
    },
    /**
     * checkbox全部选中
     * @param index 下标
     */
    selectAll(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        this.addUnique(handleList[i].id, this.tempTestBank.permissionIdList)
      }
    },
    /**
     * checkbox全部取消选中
     * @param index 下标
     */
    cancelAll(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        const idIndex = this.tempTestBank.permissionIdList.indexOf(handleList[i].id)
        if (idIndex > -1) {
          this.tempTestBank.permissionIdList.splice(idIndex, 1)
        }
      }
    },
    /**
     * 本方法会在勾选状态改变之后触发
     * @param item 选项
     * @param index 对应下标
     */
    handleChecked(item, index) {
      if (this.tempTestBank.permissionIdList.indexOf(item.id) > -1) {
        // 选中事件
        // 如果之前未勾选本权限,现在勾选完之后,tempTestBank里就会包含本id
        // 那么就要将必选的权限勾上
        this.makePermissionChecked(index)
      } else {
        // 取消选中事件
        this.cancelAll(index)
      }
    },
    /**
     * 将角色菜单必选的权限勾上（这里并没有做必选的数据库字段）
     * @param index 权限对应下标
     */
    makePermissionChecked(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        this.addUnique(handleList[i].id, this.tempTestBank.permissionIdList)
      }
    },
    /**
     * 数组内防重复地添加元素
     * @param val 值
     * @param arr 数组
     */
    addUnique(val, arr) {
      const _index = arr.indexOf(val)
      if (_index < 0) {
        arr.push(val)
      }
    }
  }
}
</script>
