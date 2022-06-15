<template>
  <el-menu
      style="width: 200px;"
      default-active="1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
    <el-sub-menu index="1">
      <template slot="title">系统管理</template>
      <el-menu-item index="1-4-1">选项1</el-menu-item>
    </el-sub-menu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      title: "系统管理"
    }
  }
}
</script>

<style scoped>

</style>