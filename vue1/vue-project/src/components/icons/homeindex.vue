<script setup>
import {
  ArrowLeft,
  ArrowRight,
  House,
  Notification,
  Operation,
    Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { jwtDecode } from "jwt-decode";
import { ArrowDown } from '@element-plus/icons-vue'


const router = useRouter();
const home = () => {
  router.push('/userhome');
}

const username = ref();
const getusername = () => {
  const token = localStorage.getItem("token");
  if (token) {
    try {
      username.value = jwtDecode(token);
      console.log(username.value.username);
    } catch (error) {
      console.error("解析token失败:", error);
    }
  }

}
onMounted(() => {
  getusername();
}
)
const handlecommand = (command) => {
  switch (command) {
    case "/login":
      router.push("/login");
      localStorage.clear();
      break;

  }
}

</script>
<template>
  <div class="homebody">
    <div class="potoclock-page">
      <div class="menu">
        <div class="menu-top">
          <h1 style="font-size:1.5rem; text-align: center;">🍅potoclock</h1>
        </div>
        <el-menu  :default-active="$route.path" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
          @close="handleClose" active-text-color="#f48080" router="true">

          <el-menu-item index="/working">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>⏰专注时间</template>
          </el-menu-item>
          <el-menu-item index="/data">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>📈统计数据</template>
          </el-menu-item>

          <el-menu-item index="/things">
            <el-icon><icon-menu /></el-icon>
            <template #title>📅任务管理</template>
          </el-menu-item>

          <el-menu-item index="/great">
            <el-icon>
              <document />
            </el-icon>
            <template #title>📊成就</template>
          </el-menu-item>
          <el-menu-item index="/music">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>🎵音乐播放</template>
          </el-menu-item>

          <el-menu-item index="/setting">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>⚙️设置</template>
          </el-menu-item>

          <el-menu-item index="/index">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>🌱习惯追踪</template>
          </el-menu-item>
        </el-menu>

        <div class="name">
          <el-dropdown @command="handlecommand " style=" width: 100%;">
            <el-button class="usename-button">
              <span>🤵🏼</span>
              hello,{{ username?.username }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="/home">个人中心</el-dropdown-item>
                <el-dropdown-item command="/login">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      <div class="show">
        <router-view />
      </div>
    </div>

  </div>

</template>





<style scoped>
.sz {
  margin: 5vh 5vh;

}

.homebody {

  height: 100vh;
  background-color: rgba(176, 13, 13, 0.183);

}

.potoclock-page {
  display: flex;
  height: 100%;
  width: 100%;
  padding: 10px;
  flex: 1;
  overflow: hidden;
  border-radius: 1rem 0rem 0rem 1rem;
  background-color: rgba(190, 182, 182, 0.183);
}

.menu {
  display: flex;
  flex-direction: column;
  height: 100%;
  box-shadow: 1px 1px 5px rgba(241, 212, 212, 0.89);
  background-color: white;
  border-radius: 1rem 0rem 0rem 1rem;
  border-right: solid 1px var(--el-menu-border-color);
  min-width: 20vh;
}


.el-menu {
  border-right: 0;
  border-radius: 1rem 0rem 0rem 1rem;
  flex-shrink: 0;
  flex: 1;

}

.menu-top {
  margin-top: 10px;
  height: 50px;
  width: 100%;
}

.usename-button {
  background-color: rgba(250, 235, 215, 0.432);
  color: #f7cece;
  width: 90%;
  height: 50px;
  border-radius: 1rem;
  box-shadow: 1px 1px 1px #f7cece;
}

.name {
  height: 90px;
  width: 100%;
  text-align: center;
  padding: 10px;
  backdrop-filter: blur(10px);

}

.show {
  box-shadow: 1px 5px 5px rgba(190, 182, 182, 0.183);
  border-radius: 0 1rem 1rem 0rem;
  background-color: white;
  flex-grow: 1;
  max-height: 100vh;
  overflow: auto;
}
</style>