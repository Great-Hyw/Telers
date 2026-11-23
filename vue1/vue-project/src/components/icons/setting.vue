<script setup>
</script>
<template>
<div>
    <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    router="true"
    @select="handleSelect"
  >
    <el-menu-item index="/tomato">番茄钟设置</el-menu-item>
    <el-menu-item index="/mess" >通知设置</el-menu-item>
    <el-menu-item index="/homest">账号设置</el-menu-item>
  </el-menu></div>
  <div class="mmm"><router-view /></div>
</template>