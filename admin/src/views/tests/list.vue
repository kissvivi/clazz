<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="success" size="mini" icon="el-icon-refresh" v-if="hasPermission('tests:list')"
            @click.native.prevent="getTestsList">刷新</el-button>
          <!-- <el-button type="primary" size="mini" icon="el-icon-plus" v-if="hasPermission('tests:add')"
            @click.native.prevent="showAddRoleDialog">添加考试</el-button> -->
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="testsList" v-loading.body="listLoading" element-loading-text="loading" border fit
      highlight-current-row>
      <el-table-column label="考试编号" align="center" prop="code" />
      <el-table-column label="考试标题" align="center" prop="title" />
      <el-table-column :filters="[{ text: '试题', value: 101 }, { text: '问卷调查', value: 102 }]" :filter-method="filterType" label="类型" align="center" >
      <template slot-scope="scope" >{{ getTypeName(scope.row.type) }}</template>
      </el-table-column>
      <el-table-column label="题目集" align="center" prop="tbIds" />
      <el-table-column label="是否发布" align="center" prop="status">

        <template slot-scope="scope">

          <el-tag v-if ="scope.row.status === 1"
                  type="success"
                  disable-transitions>已发布</el-tag>

          <el-button v-if ="scope.row.status === 0"
          size="mini"
                  @click.native.prevent="showAddRoleDialog"
                  disable-transitions>未发布（点击发布）</el-button>
          <!-- <el-tag
                  :type="scope.row.status === 1 ? 'primary' : 'success'"
                  disable-transitions>{{scope.row.status === 1 ? '未发布' : '已发布'}}</el-tag> --></template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="time">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.time) }}</template>
      </el-table-column>
      <el-table-column label="管理" align="center" v-if="hasPermission('tests:detail') || hasPermission('tests:update') || hasPermission('tests:delete')">
        <template slot-scope="scope">
          <el-button type="info" size="mini" v-if="hasPermission('tests:detail')" @click.native.prevent="showRoleDialog(scope.$index)">查看</el-button>
          <el-button type="danger" size="mini" v-if="hasPermission('tests:delete')"
            @click.native.prevent="removeRole(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.page"
      :page-size="listQuery.size" :total="total" :page-sizes="[9, 18, 36, 72]" layout="total, sizes, prev, pager, next, jumper"></el-pagination>

    <!-- 添加考试开始 -->
    <!-- <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form status-icon class="small-space" label-position="left" label-width="100px" style="width: 800px; margin-left:50px;"
        :model="tempRole" :rules="createRules" ref="tempRole">
        <el-form-item label="角色名" prop="name" required>
          <el-input :disabled="dialogStatus === 'show'" type="text" prefix-icon="el-icon-edit" auto-complete="off"
            v-model="tempRole.name"></el-input>
        </el-form-item>

        <el-form-item label="题目选择" prop="name" required>
          <template>
            <el-transfer filterable :filter-method="filterMethod" filter-placeholder="请输入城市拼音" v-model="value" :data="data">
            </el-transfer>
          </template>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus === 'add'" type="success" :loading="btnLoading" @click.native.prevent="addRole">添加</el-button>
        <el-button v-if="dialogStatus === 'update'" type="primary" :loading="btnLoading" @click.native.prevent="updateRole">更新</el-button>
      </div>
    </el-dialog> -->
    <!-- 添加考试结束 -->


    <!-- 发布开始 -->
    <el-dialog title="发布考试/问卷" :visible.sync="dialogFormVisible">
      <el-form status-icon class="small-space" label-position="left" label-width="100px" style="width: 800px; margin-left:50px;"
        :model="tempClazz" ref="tempClazz">
<el-form-item label="班级选择" prop="name" required>
        <el-select v-model="tempClazz.id" placeholder="请选择班级">
            <el-option
              v-for="item in clazzList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button  :loading="btnLoading" @click.native.prevent="updateStatus">发布</el-button>
      </div>
    </el-dialog>
    <!-- 发布结束 -->


  </div>
</template>
<script>
  import {
    list as getTestsList,
    remove
  } from '@/api/tests'
  import {
    list as getClazzList,
  } from '@/api/clazz'
  import {
    listRoleWithPermission,
    listResourcePermission,
    add as addRole,
    update as updateRole,
  } from '@/api/role'
  import {
    unix2CurrentTime
  } from '@/utils'
  import {
    mapGetters
  } from 'vuex'

  export default {
    created() {
      if (this.hasPermission('tests:update')) {
        this.getPermissionList()
      }
      if (this.hasPermission('tests:list')) {
        this.getTestsList()
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

      const generateData = _ => {
        const data = [];
        const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都','上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
        const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu','shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
        cities.forEach((city, index) => {
          data.push({
            label: city,
            key: index,
            pinyin: pinyin[index]
          });
        });
        return data;
      };

      return {


        data: generateData(),
        value: [],
        filterMethod(query, item) {
          return item.pinyin.indexOf(query) > -1;
        },

        clazzList:[],
        testsList: [],
        permissionList: [],
        listLoading: false,
        total: 0,
        listQuery: {
          page: 1,
          size: 9
        },
        listClazzQuery: {
          page: 1,
          size: 999
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          update: '修改角色',
          add: '添加角色'
        },
        btnLoading: false,
        temptests: {
          id: '',
          name: '',
          permissionIdList: []
        },
        tempClazz:{},
        createRules: {
          name: [{
            required: true,
            trigger: 'blur',
            validator: validateRoleName
          }]
        }
      }
    },
    computed: {
      ...mapGetters(['roleName'])
    },
    methods: {
      unix2CurrentTime,
      /**
       * 获取列表
       */
      getTestsList() {
        this.listLoading = true
        getTestsList(this.listQuery).then(response => {
          this.testsList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载列表失败')
        })
      },
    /**
     * 获取班级列表
     */
    getClazzList() {
      this.listLoading = true
      getClazzList(this.listClazzQuery).then(response => {
        this.clazzList = response.data.list
        //this.total = response.data.total
        this.listLoading = false
      }).catch(res => {
        this.$message.error('加载班级列表失败')
      })
    },
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
       * 发布题目更新班级与状态
       */
      updateStatus(){
        this.$message.error('更新班级与状态')
      },
      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.listQuery.page = 1
        this.listQuery.size = size
        this.getTestsList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.getTestsList()
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
       * 显示新增角色对话框
       */
      showAddRoleDialog() {
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tempRole.name = ''
        this.tempRole.id = ''
        this.tempRole.permissionIdList = []
      },
      /**
       * 显示更新角色的对话框
       * @param index 角色下标
       */
      showUpdateRoleDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        const role = this.testsList[index]
        this.tempRole.name = role.name
        this.tempRole.id = role.id
        this.tempRole.permissionIdList = []
        for (let i = 0; i < role.resourceList.length; i++) {
          const handleList = role.resourceList[i].handleList
          for (let j = 0; j < handleList.length; j++) {
            const handle = handleList[j]
            this.tempRole.permissionIdList.push(handle.id)
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
        const role = this.testsList[index]
        this.tempRole.name = role.name
        this.tempRole.id = role.id
        this.tempRole.permissionIdList = []
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
            this.tempRole.permissionIdList.push(handle.id)
          }
        }
      },
      /**
       * 添加新角色
       */
      addRole() {
        this.$refs.tempRole.validate(valid => {
          if (
            valid &&
            this.isRoleNameUnique(this.tempRole.id, this.tempRole.name)
          ) {
            this.btnLoading = true
            addRole(this.tempRole).then(() => {
              this.$message.success('添加成功')
              this.getTestsList()
              this.dialogFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('添加角色失败')
            })
          } else {
            console.log('表单无效')
          }
        })
      },
      /**
       * 修改角色
       */
      updateRole() {
        this.$refs.tempRole.validate(valid => {
          if (
            valid &&
            this.isRoleNameUnique(this.tempRole.id, this.tempRole.name)
          ) {
            this.btnLoading = true
            updateRole(this.tempRole).then(() => {
              this.$message.success('更新成功')
              this.getTestsList()
              this.dialogFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('更新角色失败')
            })
          } else {
            console.log('表单无效')
          }
        })
      },
      /**
       * 校验角色名是否已经存在
       * @param id 角色id
       * @param name 角色名
       * @returns {boolean}
       */
      isRoleNameUnique(id, name) {
        for (let i = 0; i < this.testsList.length; i++) {
          if (this.testsList[i].id !== id && this.testsList[i].name === name) {
            this.$message.error('角色名已存在')
            return false
          }
        }
        return true
      },
      /**
       * 移除考试
       * @param index 角色下标
       * @returns {boolean}
       */
      removeRole(index) {
        this.$confirm('删除该考试/问卷？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const roleId = this.testsList[index].id
          remove(roleId).then(() => {
            this.$message.success('删除成功')
            this.getTestsList()
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
<style>

.el-transfer-panel {
    border: 1px solid #EBEEF5;
    border-radius: 4px;
    overflow: hidden;
    background: #FFFFFF;
    display: inline-block;
    vertical-align: middle;
    width: 280px;
    height: 460px;
    max-height: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    position: relative;
}

  .el-transfer-panel__list.is-filterable{
    height: 320px;
  }
</style>
