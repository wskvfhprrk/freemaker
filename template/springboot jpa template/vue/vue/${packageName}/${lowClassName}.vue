<template>
  <div class="app-container">
    <div class="filter-container">
<#list table.columns as column>
    <#if column.isKey==false>
      <#if column.isImportedKey == false>
      <el-input v-model="listQuery.${column.javaBeanName}" placeholder="${column.columnComment}" style="width: 200px" clearable class="filter-item" @keyup.enter.native="handleFilter" />
      <#else>
      <el-select v-model="listQuery.${column.javaBeanName}" placeholder="${column.columnComment}" clearable style="width: 200px" class="filter-item">
        <!-- todo 根据需要修改item -->
        <el-option v-for="item in ${column.javaBeanName}Options" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </#if></#if></#list>
      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"> 查询</el-button>
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"> 新增 </el-button>
    </div>
    <el-table :data="list" border>
      <#list table.columns as column>
      <#if column.isKey==true>
      <el-table-column label="${column.columnComment}" prop="${column.javaBeanName}" align="center" width="80" />
      <#elseif column.isImportedKey == true>
      <el-table-column label="${column.columnComment}" prop="${column.javaBeanName}" align="center" :formatter="${column.javaBeanName}Optionsformatter"/>
      <#else>
      <el-table-column label="${column.columnComment}" prop="${column.javaBeanName}" align="center" />
    </#if></#list>
      <el-table-column label="操作"  align="center">
        <template slot-scope="{row}" >
          <el-button type="primary" size="mini" @click="handleUpdate(row)"> 修改</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)"> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="200px" style="width: 400px; margin-left: 50px" >
<#list table.columns as column>
  <#if column.isKey==true>
        <el-form-item v-show="dialogStatus === 'update'" label="${column.columnComment}" >
          <el-input v-model="temp.${column.javaBeanName}" disabled placeholder="请输入${column.columnComment}" />
        </el-form-item>
  <#elseif column.columnJavaType?index_of("Integer")!=-1 && column.isImportedKey == false>
          <el-form-item label="${column.columnComment}" <#if column.isNullable==0> prop="${column.javaBeanName}"</#if>>
          <el-input v-model="temp.${column.javaBeanName}" type="number" placeholder="请输入${column.columnComment}" />
        </el-form-item>
  <#elseif column.columnJavaType?index_of("Integer")!=-1 && column.isImportedKey == true>
        <el-form-item label="${column.columnComment}" <#if column.isNullable==0> prop="${column.javaBeanName}"</#if>>
          <el-select v-model="temp.${column.javaBeanName}" placeholder="请选择${column.columnComment}" clearable style="width: 200px" class="filter-item">
          <el-option v-for="item in ${column.javaBeanName}Options" :key="item.id" :label="item" :value="item.id" />
          </el-select>
        </el-form-item>
  <#elseif column.isImportedKey == true>
          <el-form-item label="${column.columnComment}" <#if column.isNullable==0> prop="${column.javaBeanName}"</#if>>
            <el-select v-model="temp.${column.javaBeanName}" placeholder="请选择${column.columnComment}" clearable style="width: 200px" class="filter-item">
            <!-- todo 根据需要修改item -->
            <el-option v-for="item in ${column.javaBeanName}Options" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
  <#else>
        <el-form-item label="${column.columnComment}" <#if column.isNullable==0> prop="${column.javaBeanName}"</#if>>
          <el-input v-model="temp.${column.javaBeanName}"  placeholder="请输入${column.columnComment}" />
        </el-form-item>
  </#if>
</#list>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()"> 确认 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { create${className}, delete${className}, fetchList, update${className} <#list table.columns as column><#if column.isImportedKey == true>,${column.javaBeanName}Options</#if></#list>} from '@/api/${lowClassName}'
import Pagination from '@/components/Pagination' // 基于el分页的二级包

export default {
  name: 'ComplexTable',
  components: { Pagination },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
<#list table.columns as column>
<#if column.isKey==false>
      ${column.javaBeanName}: undefined,
</#if></#list>
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      // 格式化数据
      // calendarNoImeiOptions,
      // automaticOptions,
      sortOptions: [
        { label: 'ID 顺序排列', key: '+id' },
        { label: 'ID 倒序排列', key: '-id' }
      ],
<#list table.columns as column>
<#if column.isImportedKey == true>
      ${column.javaBeanName}Options:[],
</#if></#list>
      showReviewer: false,
      temp: {
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新增'
      },
      rules: {
        <#list table.columns as column>
        <#if column.isKey==false && column.isNullable==0>
        ${column.javaBeanName}: [{ required: true, message: '${column.columnComment}是必填', trigger: 'blur' }],
      </#if></#list>
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    <#list table.columns as column>
    <#if column.isImportedKey == true>
    this.get${column.javaBeanName?cap_first}Options()
    </#if></#list>
  },
  methods: {
    // 格式化表格数据
    Ynformater(row, column) {
      if (row.noImei) {
        return '带的'
      } else {
        return '不带'
      }
    },
    // 获取列表数据
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then((response) => {
        this.list = response.data.items
        this.total = response.data.total
      })
    },
<#list table.columns as column>
<#if column.isImportedKey == true>
    get${column.javaBeanName?cap_first}Options(){
      ${column.javaBeanName}Options().then((response)=>{
        this.${column.javaBeanName}Options=response.data
      })
    },
    ${column.javaBeanName}Optionsformatter(row,column){
      for (let index = 0; index < this.${column.javaBeanName}Options.length; index++) {
        const element = this.${column.javaBeanName}Options[index];
        if(row.${column.javaBeanName} === element.id){
          // todo 根据需要修改
          return element.name
        }
      }
    },
</#if></#list>
handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 排序改变
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    // 重置表单数据
    resetTemp() {
      this.temp = {
      }
    },
    // 新建
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          create${className}(this.temp).then(() => {
            this.dialogFormVisible = false
            this.$notify({
                  title: 'Success',
                  message: '添加成功！',
                  type: 'success',
                  duration: 2000
                },
                this.getList()
            )
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          update${className}(tempData).then(() => {
            const index = this.list.findIndex((v) => v.id === this.temp.id)
            this.list.splice(index, 1, this.temp)
            this.dialogFormVisible = false
            this.$notify({
                  title: 'Success',
                  message: '修改成功！',
                  type: 'success',
                  duration: 2000
                },
                this.getList()
            )
          })
        }
      })
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delete(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    delete(row) {
      delete${className}(row.id).then(() => {
        this.$notify({
              title: 'Success',
              message: '删除成功！',
              type: 'success',
              duration: 2000
            },
            this.getList()
        )
      })
    }
  }
}
</script>
