<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('clazz:list')"
            @click.native.prevent="getClazzList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('clazz:add')"
            @click.native.prevent="showAddRoleDialog"
          >添加班级</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="clazzList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="#" align="center" prop="id" width="80">
      </el-table-column>
      <el-table-column label="班级名" align="center" prop="name" />
      <el-table-column label="课程名" align="center" prop="course">
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="time">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.time) }}</template>
      </el-table-column>
      <el-table-column
        label="管理"
        align="center"
        v-if="hasPermission('clazz:detail') || hasPermission('clazz:update') || hasPermission('clazz:delete')"
      >
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            @click.native.prevent="showQRDialog(scope.row.id)"
          >生成二维码</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('clazz:delete') && scope.row.name !== '超级管理员'"
            @click.native.prevent="removeClazz(scope.row.id)"
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

    <!-- 生成二维码开始 -->
    <el-dialog :title="textMap[dialogStatus]" width="480px" :visible.sync="dialogFormVisible">

      <div class="QRCode">
        <div class="demo-image__placeholder">
          <div class="block">
            <!-- <span class="demonstration">自定义</span> -->
            <el-image :src="src">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        </div>
    </el-dialog>
    <!-- 生成二维码结束 -->

    <!-- 添加班级开始 -->
    <el-dialog :title="textMap[dialogStatus]"  :visible.sync="dialogClazzFormVisible">


          <el-form
            status-icon
            class="small-space"
            label-position="left"
            label-width="100px"
            style="width: 500px; margin-left:50px;"
            :model="tempClazz"
            :rules="createRules"
            ref="tempClazz"
          >
            <el-form-item label="班级名" prop="name" required>
              <el-input
                :disabled="dialogStatus === 'show'"
                type="text"
                prefix-icon="el-icon-edit"
                auto-complete="off"
                v-model="tempClazz.name"
              ></el-input>
            </el-form-item>
            <el-form-item label="课程名" prop="course" required>
              <el-input
                :disabled="dialogStatus === 'show'"
                type="text"
                prefix-icon="el-icon-edit"
                auto-complete="off"
                v-model="tempClazz.course"
              ></el-input>
            </el-form-item>
          </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogClazzFormVisible = false">取消</el-button>
        <el-button
          v-if="dialogStatus === 'add'"
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addClazz"
        >添加</el-button>
        </div>
    </el-dialog>
    <!-- 生成二维码结束 -->
  </div>
</template>
<script>
import {
  list as getClazzList,
  getQRCode,
  add as addClazz,
  remove
} from '@/api/clazz'
import {
  add as addRole,
  update as updateRole,
} from '@/api/role'
import { unix2CurrentTime } from '@/utils'
import { mapGetters } from 'vuex'

export default {
  created() {
    if (this.hasPermission('clazz:update')) {
      this.getPermissionList()
    }
    if (this.hasPermission('clazz:list')) {
      this.getClazzList()
    }
  },
  data() {
    /**
     * 验证班级
     * @param rule 规则
     * @param value 班级名
     * @param callback 回调
     */
    const validateRoleName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('班级名不能为空'))
      } else {
        callback()
      }
    }
    return {
      src: 'http://localhost:8080/QRImage/1619958783025.png',
      clazzList: [],
      permissionList: [],
      listLoading: false,
      total: 0,
      listQuery: {
        page: 1,
        size: 9
      },
      params:{
        clazzId:1,
      },
      dialogStatus: 'add',
      dialogFormVisible: false,
      dialogClazzFormVisible:false,
      textMap: {
        add: '添加班级',
        qr: '生成二维码'
      },
      btnLoading: false,
      tempClazz: {
        name: '',
        course:''
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
     * 获取班级列表
     */
    getClazzList() {
      this.listLoading = true
      getClazzList(this.listQuery).then(response => {
        this.clazzList = response.data.list
        this.total = response.data.total
        this.listLoading = false
      }).catch(res => {
        this.$message.error('加载班级列表失败')
      })
    },
    /**
     * 改变每页数量
     * @param size 页大小
     */
    handleSizeChange(size) {
      this.listQuery.page = 1
      this.listQuery.size = size
      this.getClazzList()
    },
    /**
     * 改变页码
     * @param page 页号
     */
    handleCurrentChange(page) {
      this.listQuery.page = page
      this.getClazzList()
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
     * 显示添加班级对话框
     */
    showAddRoleDialog() {
      this.dialogClazzFormVisible = true
      this.dialogStatus = 'add'
      this.tempClazz.name = ''
      this.tempClazz.course = ''
    },
    /**
     * 显示更新角色的对话框
     * @param index 角色下标
     */
    // showUpdateRoleDialog(index) {
    //   this.dialogFormVisible = true
    //   this.dialogStatus = 'update'
    //   const role = this.roleList[index]
    //   this.tempRole.name = role.name
    //   this.tempRole.id = role.id
    //   this.tempRole.permissionIdList = []
    //   for (let i = 0; i < role.resourceList.length; i++) {
    //     const handleList = role.resourceList[i].handleList
    //     for (let j = 0; j < handleList.length; j++) {
    //       const handle = handleList[j]
    //       this.tempRole.permissionIdList.push(handle.id)
    //     }
    //   }
    // },
    /**
     * 生成二维码
     * @param id 班级id
     */
    showQRDialog(id) {
      //this.$message.success("ttttttt"+id)
      this.dialogStatus = 'qr'
      this.params.clazzId = id
      getQRCode(this.params).then(res=>{
        this.src = "http://localhost:8080/QRImage/"+res.data.url
      }).catch(res => {
        this.$message.error('生成二维码失败')
      })
      this.dialogFormVisible = true
      this.dialogStatus = 'show'

    },
    /**
     * 添加新班级
     */
    addClazz() {
      this.$refs.tempClazz.validate(valid => {
        if (
          valid &&
          this.isClazzNameUnique(this.tempClazz.id, this.tempClazz.name)
        ) {
          this.btnLoading = true
          addClazz(this.tempClazz).then(() => {
            this.$message.success('添加成功')
            this.getClazzList()
            this.dialogClazzFormVisible = false
            this.btnLoading = false
          }).catch(res => {
            this.$message.error('添加班级失败')
          })
        } else {
          console.log('表单无效')
        }
      })
    },
    /**
     * 修改角色
     */
    // updateRole() {
    //   this.$refs.tempRole.validate(valid => {
    //     if (
    //       valid &&
    //       this.isRoleNameUnique(this.tempRole.id, this.tempRole.name)
    //     ) {
    //       this.btnLoading = true
    //       updateRole(this.tempRole).then(() => {
    //         this.$message.success('更新成功')
    //         this.getClazzList()
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
     * 校验班级名是否已经存在
     * @param id 班级id
     * @param name 班级名
     * @returns {boolean}
     */
    isClazzNameUnique(id, name) {
      for (let i = 0; i < this.clazzList.length; i++) {
        if (this.clazzList[i].id !== id && this.clazzList[i].name === name) {
          this.$message.error('角色名已存在')
          return false
        }
      }
      return true
    },
    /**
     * 移除班级
     * @returns {boolean}
     */
    removeClazz(id) {
      this.$confirm('删除该班级？', '警告', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        const clazzId = id
        remove(clazzId).then(() => {
          this.$message.success('删除成功')
          this.getClazzList()
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
        if (this.tempRole.permissionIdList.indexOf(handleList[i].id) > -1) {
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
        if (this.tempRole.permissionIdList.indexOf(handleList[i].id) < 0) {
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
        this.addUnique(handleList[i].id, this.tempRole.permissionIdList)
      }
    },
    /**
     * checkbox全部取消选中
     * @param index 下标
     */
    cancelAll(index) {
      const handleList = this.permissionList[index].handleList
      for (let i = 0; i < handleList.length; i++) {
        const idIndex = this.tempRole.permissionIdList.indexOf(handleList[i].id)
        if (idIndex > -1) {
          this.tempRole.permissionIdList.splice(idIndex, 1)
        }
      }
    },
    /**
     * 本方法会在勾选状态改变之后触发
     * @param item 选项
     * @param index 对应下标
     */
    handleChecked(item, index) {
      if (this.tempRole.permissionIdList.indexOf(item.id) > -1) {
        // 选中事件
        // 如果之前未勾选本权限,现在勾选完之后,tempRole里就会包含本id
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
        this.addUnique(handleList[i].id, this.tempRole.permissionIdList)
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

<style rel="stylesheet/scss" lang="scss" scoped>
.QRCode{
  text-align: center;
  //margin-top: 38px;
  margin-left: 38px;
  margin-bottom: 20px;
  width: 360px;
   height: 300px;

}
</style>
