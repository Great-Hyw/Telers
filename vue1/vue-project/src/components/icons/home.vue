<script setup>

</script>
<template>
<div class="home1">
<h class="h">暂未开发</h>
</div>
</template>
<style scoped>

/* 1. 确保容器占满整个屏幕高度 */
.home1 {
  height: 100vh; /* vh 代表视口高度的百分比，100vh 就是全屏高度 */
  width: 100%;
  background-color: #f5f7fa; /* 可选：加个浅灰色背景，看起来更明显 */

  /* 2. 开启 Flex 布局 */
  display: flex;

  /* 3. 水平居中 (主轴) */
  justify-content: center;
  align-items: center;
}

/* 修正之前的选择器错误 */
.home1 h {
  font-size: 24px; /* 稍微调大一点字体 */
  color: #666;     /* 给个深灰色 */
}
</style>

