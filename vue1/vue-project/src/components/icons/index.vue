<script setup>
import { ref, computed, inject, onMounted } from "vue";
import "@/assets/index.css";
import { useRouter } from "vue-router";
import aichat from "./ai.vue";
import { ElMessage } from "element-plus";
import { chataiapi, gethabitdapi, addhabitdapi, deletehabitsapi } from "@/api/loginapi";
import { jwtDecode } from "jwt-decode";

const router = useRouter();
const newHabitData = ref({
  id: Date.now(),
  name: "",
  category: "",
  frequency: "",
  completed: false,
  completions: ""
});

const habits = ref([]);
const newHabit = ref({
  name: "",
  category: "health",
  frequency: "everyday",
});
const activeFilter = ref("all");
const icon = ref("🌙");
const isDarkMode = inject("darkMode", ref(false));
const toggleDarkMode = inject("toggleDarkMode", () => { });
const filteredHabits = computed(() => {
  if (activeFilter.value === "all") {
    return habits.value;
  }
  return habits.value.filter((h) => h.category === activeFilter.value);
});
const totalHabits = computed(() => habits.value.length);
const completedToday = computed(
  () => habits.value.filter((h) => h.completed).length,
);
const currentStreak = computed(() => {
  let streak = 0;
  const today = new Date();
  for (let i = 0; i < 7; i++) {
    const date = new Date(today);
    date.setDate(date.getDate() - i);
    const dateStr = date.toISOString().split("T")[0];
    const completed = habits.value.some((h) =>
      h.completions?.includes(dateStr),
    );
    if (completed) {
      streak++;
    } else if (i > 0) {
      break;
    }
  }
  return streak;
});
const completionRate = computed(() => {
  if (habits.value.length === 0) return 0;
  return Math.round((completedToday.value / habits.value.length) * 100);
});
const addHabit = async () => {
  if (!newHabit.value.name.trim()) return;

  newHabitData.value = {
    id: Date.now(),
    name: newHabit.value.name,
    category: newHabit.value.category,
    frequency: newHabit.value.frequency,
    completed: false,
    completions: [],
  }
  try {
    const result = await addhabitdapi(newHabitData.value);
    if (result.code == 1) {
      ElMessage.success("添加成功");
      const re = await gethabitdapi();
      habits.value = re.data;

    }
  } catch (error) {
    ElMessage.error("添加失败");
    console.error("添加习惯失败:", error);
  }

  newHabit.value = { name: "", category: "health", frequency: "everyday" };
};
const toggleHabit = (habit) => {
  habit.completed = !habit.completed;
  const today = new Date().toISOString().split("T")[0];
  if (!habit.completions) habit.completions = [];
  if (habit.completed) {
    if (!habit.completions.includes(today)) {
      habit.completions.push(today);
    }
  } else {
    const index = habit.completions.indexOf(today);
    if (index > -1) {
      habit.completions.splice(index, 1);
    }
  }
};
const deleteHabit = async (habitId) => {
  dialogVisible.value = false;
  const result = await deletehabitsapi({
    id: habitId
  });
  if (result.code == 1) {
    ElMessage("删除成功");
    const re = await gethabitdapi();
    habits.value = re.data;
  }
  else {
    ElMessage("删除失败" + result.mes);
  }

};
const setFilter = (filter) => {
  activeFilter.value = filter;
};
const toggleTheme = () => {
  toggleDarkMode();
  icon.value = isDarkMode.value ? "☀️" : "🌙";
};
const getCategoryLabel = (category) => {
  const labels = { health: "健康", study: "学习", social: "社交" };
  return labels[category] || category;
};
const getCategoryColor = (category) => {
  const colors = {
    health: "#4CAF50",
    study: "#2196F3",
    social: "#FF9800",
  };
  return colors[category] || "#9E9E9E";
};
const getWeekDays = () => {
  const days = ["SU", "MO", "TU", "WE", "TH", "FR", "SA"];
  const result = [];
  const today = new Date();
  for (let i = 6; i >= 0; i--) {
    const date = new Date(today);
    date.setDate(date.getDate() - i);
    result.push({
      label: days[date.getDay()],
      date: date.toISOString().split("T")[0],
      isToday: i === 0,
      completed: habits.value.some((h) =>
        h.completions?.includes(date.toISOString().split("T")[0]),
      ),
    });
  }
  return result;
};



const dialogVisible = ref(false)
const showai = ref(false);

const dialogVisible1 = ref(false);
const ai = async () => {
  dialogVisible1.value = true;
  showai.value = true;
  console.log("传的值为" + JSON.stringify(habits.value));
  const response = await chataiapi(habits.value);
  console.log(response);
  provide("aiResponse", response.data);
};

const closeAI = () => {

  showai.value = false;
  dialogVisible1.value = false;
};

const habitIds = ref();

const addid = (habit) => {
  habitIds.value = habit.id;
  dialogVisible.value = true;
}

const deleteid = () => {
  deleteHabit(habitIds.value);
}




//加载习惯数据
onMounted(
  async () => {
    const result = await gethabitdapi();
    habits.value = result.data;
    console.log("习惯数据为" + JSON.stringify(habits.value));
  }
)
const activeIndex = ref('1')
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}




</script>

<template>
  <el-dialog v-model="dialogVisible1" draggable :show-close="false" :fullscreen="true">
    <aichat v-if="showai" @close="closeAI" />
  </el-dialog>




  <div class="container">
    <div class="head1">
      <div>
        <header>
          <h1 class="container-title">🌱习惯追踪</h1>
          <p class="container-text">记录和管理您的习惯，实现更高效的生活</p>
        </header>
      </div>
      <div class="theme">


     
        <button @click="ai" class="ai" id="ai">
          <span class="ai-icon">🤖</span>AI建议
        </button> 
      </div>
    </div>

    <section id="stats">
      <div class="stats-item">
        <h2 class="icon">📅</h2>
        <div class="stats-content">
          <h3>{{ totalHabits }}</h3>
          <span>总习惯</span>
        </div>
      </div>
      <div class="stats-item">
        <h2 class="icon">✅</h2>
        <div class="stats-content">
          <h3>{{ completedToday }}</h3>
          <span>完成量</span>
        </div>
      </div>
      <div class="stats-item">
        <h2 class="icon">🔥</h2>
        <div class="stats-content">
          <h3>{{ currentStreak }}</h3>
          <span>当前连胜</span>
        </div>
      </div>
      <div class="stats-item">
        <h2 class="icon">📊</h2>
        <div class="stats-content">
          <h3>{{ completionRate }}%</h3>
          <span>完成率</span>
        </div>
      </div>
    </section>

    <main>
      <section class="add-habit">
        <div class="add-habit-content1">
          <header>➕添加习惯</header>
        </div>
        <form class="add-habit-form" @submit.prevent="addHabit">
          <div class="form-item">
            <label for="iterm1">习惯名称:</label>
            <input v-model="newHabit.name" name="habitName" class="form-select" id="iterm1" placeholder="例如:每天阅读30分钟"
              required autocomplete="off" />
          </div>
          <div class="form-item">
            <label for="iterm2">习惯描述:</label>
            <select v-model="newHabit.category" name="category" class="form-select" id="iterm2" required>
              <option value="health">健康</option>
              <option value="study">学习</option>
              <option value="social">社交</option>
            </select>
          </div>
          <div class="form-item">
            <label for="iterm3">频率</label>
            <select v-model="newHabit.frequency" name="frequency" class="form-select" id="iterm3" required>
              <option value="everyday">每天</option>
              <option value="everyweek">每周</option>
            </select>
          </div>
          <div class="form-btn" id="form-btn">
            <button type="submit" id="addHabitBtn">添加习惯</button>
          </div>
        </form>
      </section>

      <section class="my-habit">
        <div class="my-habit-content1">
          <h3>🗒️我的习惯</h3>
          <div class="my-habit-btn">
            <button class="filter-btn" :class="{ active: activeFilter === 'all' }" @click="setFilter('all')">
              所有习惯
            </button>
            <button class="filter-btn" :class="{ active: activeFilter === 'health' }" @click="setFilter('health')">
              健康
            </button>
            <button class="filter-btn" :class="{ active: activeFilter === 'study' }" @click="setFilter('study')">
              学习
            </button>
            <button class="filter-btn" :class="{ active: activeFilter === 'social' }" @click="setFilter('social')">
              社交
            </button>
          </div>
        </div>
        <div class="my-habit-list" v-if="filteredHabits.length > 0">
          <div v-for="habit in filteredHabits" :key="habit.id" class="habit-item"
            :class="{ completed: habit.completed }">
            <div class="habit-checkbox" @click="toggleHabit(habit)">
              <span>{{ habit.completed ? "✓" : "" }}</span>
            </div>
            <div class="habit-info">
              <span class="habit-name">{{ habit.name }}</span>
              <span class="habit-category" :style="{ backgroundColor: getCategoryColor(habit.category) }">{{
                getCategoryLabel(habit.category) }}</span>
            </div>
            <div>
              <el-button class="habit-delete" plain @click="addid(habit)">
                🗑️
              </el-button>

            </div>
          </div>
        </div>
        <div class="my-habit-content2" v-else>
          <h6>🌳</h6>
          <p class="text2">还没有习惯</p>
          <p class="text3">请添加一些习惯开始，开始你的成长之旅吧！</p>
        </div>
      </section>
    </main>

    <section id="visualization">
      <header style="font-size: 12px">📊数据可视化</header>
      <div class="visualization-content">
        <div class="visualization-item">
          <h1>七天完成热力图</h1>
          <div class="week-buttons">
            <button v-for="day in getWeekDays()" :key="day.date" class="button"
              :class="{ completed: day.completed, today: day.isToday }">
              {{ day.label }}
            </button>
          </div>
        </div>
        <div class="visualization-item">
          <h1>本周进度</h1>
          <div class="visualization-content-contanier">
            <div class="progress-ring-container" v-if="habits.length > 0">
              <svg class="progress-ring" width="100" height="100">
                <defs>
                  <linearGradient id="progressGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#667eea" />
                    <stop offset="100%" stop-color="#764ba2" />
                  </linearGradient>
                </defs>
                <circle class="progress-ring-background" cx="50" cy="50" r="40" fill="none" stroke="#e0e0e0"
                  stroke-width="8" />
                <circle class="progress-ring-progress" cx="50" cy="50" r="40" fill="none"
                  stroke="url(#progressGradient)" stroke-width="8" stroke-linecap="round"
                  :stroke-dasharray="`${completionRate * 2.51} 251`" transform="rotate(-90 50 50)" />
                <text class="progress-ring-text" x="50" y="55" text-anchor="middle" font-size="16" font-weight="bold"
                  fill="#333">
                  {{ completionRate }}%
                </text>
              </svg>
            </div>
            <div class="no-data-container" v-else>
              <div class="no-data-text">暂无数据</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <el-dialog v-model="dialogVisible" title="确认删除？" width="500" draggable>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消删除</el-button>
          <el-button type="primary" @click.stop="deleteid">
            确定删除
          </el-button>
        </div>z
      </template>
    </el-dialog>
  </div>
    <footer >
      <div>
        <span>泷</span><br>
        <span>已老实的码农😭</span><br>
        <span>有疑问？联系7508999193@qq.com</span>
      </div>
      <span>💪 坚持就是胜利，记录每一天的习惯</span>
    </footer>
</template>

<style scoped></style>
