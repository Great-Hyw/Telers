<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';

const user = 'Alice';
const quote = '"专注是通往成功的桥梁。"';

const modes = [
  { id: 'pomodoro', label: '番茄钟', time: 25 * 60 },
  { id: 'shortBreak', label: '短休息', time: 5 * 60 },
  { id: 'longBreak', label: '长休息', time: 15 * 60 }
];

const currentMode = ref('pomodoro');
const timeLeft = ref(25 * 60);
const isRunning = ref(false);
const todayPomodoros = ref(4);
const totalPomodoros = ref(10);
const status = ref('专注中');

const tasks = ref([
  { id: 1, name: '完成产品设计稿', completed: true, pomodoros: 2 },
  { id: 2, name: '阅读《深度工作》', completed: false, pomodoros: 1 },
  { id: 3, name: '编写项目文档', completed: false, pomodoros: 1 },
  { id: 4, name: '学习新技能', completed: false, pomodoros: 0 }
]);

const focusStats = ref({
  todayPomodoros: 4,
  totalHours: 2.5,
  efficiency: 75
});

const focusHistory = ref([
  { date: '5/12', hours: 1 },
  { date: '5/13', hours: 1.5 },
  { date: '5/14', hours: 0.5 },
  { date: '5/15', hours: 2.5 },
  { date: '5/16', hours: 3 },
  { date: '5/17', hours: 3.5 },
  { date: '5/18', hours: 5.5 }
]);

let timer = null;

const currentModeData = computed(() => {
  return modes.find(m => m.id === currentMode.value);
});

const formattedTime = computed(() => {
  const minutes = Math.floor(timeLeft.value / 60);
  const seconds = timeLeft.value % 60;
  return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
});

const progress = computed(() => {
  return ((currentModeData.value.time - timeLeft.value) / currentModeData.value.time) * 100;
});

const circumference = 2 * Math.PI * 120;
const strokeDasharray = computed(() => {
  const dashLength = (progress.value / 100) * circumference;
  return `${dashLength} ${circumference}`;
});

const maxHours = computed(() => {
  return Math.max(...focusHistory.value.map(item => item.hours));
});

const startTimer = () => {
  if (isRunning.value) {
    clearInterval(timer);
    isRunning.value = false;
    status.value = '已暂停';
  } else {
    isRunning.value = true;
    status.value = '专注中';
    timer = setInterval(() => {
      if (timeLeft.value > 0) {
        timeLeft.value--;
      } else {
        clearInterval(timer);
        isRunning.value = false;
        if (currentMode.value === 'pomodoro') {
          todayPomodoros.value++;
        }
        status.value = '已完成';
      }
    }, 1000);
  }
};

const resetTimer = () => {
  clearInterval(timer);
  isRunning.value = false;
  timeLeft.value = currentModeData.value.time;
  status.value = '专注中';
};

const selectMode = (modeId) => {
  currentMode.value = modeId;
  resetTimer();
};

const toggleTask = (taskId) => {
  const task = tasks.value.find(t => t.id === taskId);
  if (task) {
    task.completed = !task.completed;
  }
};

onUnmounted(() => {
  if (timer) {
    clearInterval(timer);
  }
});
</script>

<template>
  <div class="pomodoro-container">
    <header class="header">
      <div class="greeting">
        <h1>你好, {{ user }} 👋</h1>
        <p>{{ quote }}</p>
      </div>
      <div class="header-actions">
        <button class="action-btn">🎵</button>
        <button class="action-btn">🔔</button>
        <button class="custom-btn">⚙️ 自定义</button>
      </div>
    </header>

    <div class="mode-tabs">
      <button
        v-for="mode in modes"
        :key="mode.id"
        :class="['mode-tab', { active: currentMode === mode.id }]"
        @click="selectMode(mode.id)"
      >
        {{ mode.label }}
      </button>
    </div>

    <div class="main-content">
      <div class="timer-section">
        <div class="timer-container">
          <svg class="timer-ring" viewBox="0 0 260 260">
            <circle
              class="timer-bg"
              cx="130"
              cy="130"
              r="120"
              fill="none"
              stroke="#ffe5e5"
              stroke-width="8"
            />
            <circle
              class="timer-progress"
              cx="130"
              cy="130"
              r="120"
              fill="none"
              stroke="#ff6b6b"
              stroke-width="8"
              stroke-linecap="round"
              :stroke-dasharray="strokeDasharray"
              transform="rotate(-90 130 130)"
            />
            <circle
              class="timer-dot"
              :cx="130 + 120 * Math.cos((progress * 2 * Math.PI) / 100 - Math.PI / 2)"
              :cy="130 + 120 * Math.sin((progress * 2 * Math.PI) / 100 - Math.PI / 2)"
              r="8"
              fill="#ff6b6b"
            />
          </svg>
          <div class="timer-content">
            <span class="tomato-icon">🍅</span>
            <div class="time-display">{{ formattedTime }}</div>
            <span class="status">{{ status }}</span>
          </div>
        </div>

        <div class="timer-controls">
          <button class="start-btn" @click="startTimer">
            {{ isRunning ? '暂停' : '开始' }}
          </button>
          <button class="reset-btn" @click="resetTimer">🔄</button>
        </div>

        <div class="pomodoro-count">
          <span>今日番茄钟</span>
          <span class="count">{{ todayPomodoros }} / {{ totalPomodoros }}</span>
        </div>
      </div>

      <div class="sidebar">
        <div class="task-panel">
          <div class="panel-header">
            <h3>今日任务</h3>
            <button class="add-task-btn">+ 添加任务</button>
          </div>
          <div class="task-list">
            <div
              v-for="task in tasks"
              :key="task.id"
              :class="['task-item', { completed: task.completed }]"
              @click="toggleTask(task.id)"
            >
              <div :class="['checkbox', { checked: task.completed }]">
                <span v-if="task.completed">✓</span>
              </div>
              <span class="task-name">{{ task.name }}</span>
              <span class="task-pomodoros">🍅 {{ task.pomodoros }}</span>
            </div>
          </div>
        </div>

        <div class="stats-panel">
          <h3>专注数据</h3>
          <div class="stats-grid">
            <div class="stat-item">
              <div class="stat-value">{{ focusStats.todayPomodoros }}</div>
              <div class="stat-label">今日番茄钟</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ focusStats.totalHours }}</div>
              <div class="stat-label">今日专注时长(h)</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">{{ focusStats.efficiency }}%</div>
              <div class="stat-label">今日专注率</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="history-panel">
      <div class="panel-header">
        <h3>专注记录</h3>
        <select class="time-select">
          <option>近7天</option>
          <option>近30天</option>
          <option>近一年</option>
        </select>
      </div>
      <div class="chart-container">
        <div class="chart-y-axis">
          <span>6h</span>
          <span>4h</span>
          <span>2h</span>
          <span>0min</span>
        </div>
        <div class="chart-bars">
          <div
            v-for="item in focusHistory"
            :key="item.date"
            class="bar-wrapper"
          >
            <div
              class="bar"
              :style="{ height: `${(item.hours / maxHours) * 100}%` }"
            >
              <span v-if="item.hours === maxHours" class="bar-tooltip">
                {{ item.hours }}小时
              </span>
            </div>
            <span class="bar-label">{{ item.date }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.pomodoro-container {
  min-height: 100vh;
  background: linear-gradient(180deg, #fff5f5 0%, #ffffff 100%);
  padding: 40px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 30px;
}

.greeting h1 {
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.greeting p {
  color: #666;
  margin: 8px 0 0;
  font-size: 14px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.action-btn {
  width: 40px;
  height: 40px;
  border: none;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  font-size: 18px;
  cursor: pointer;
  transition: all 0.2s;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.12);
}

.custom-btn {
  padding: 10px 20px;
  border: 2px solid #ff6b6b;
  border-radius: 20px;
  background: transparent;
  color: #ff6b6b;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.custom-btn:hover {
  background: #ff6b6b;
  color: white;
}

.mode-tabs {
  display: flex;
  gap: 12px;
  margin-bottom: 40px;
}

.mode-tab {
  padding: 12px 32px;
  border: none;
  border-radius: 25px;
  background: #fff;
  color: #666;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  transition: all 0.2s;
}

.mode-tab:hover {
  transform: translateY(-1px);
}

.mode-tab.active {
  background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(255,107,107,0.3);
}

.main-content {
  display: flex;
  gap: 40px;
  margin-bottom: 40px;
}

.timer-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.timer-container {
  position: relative;
  width: 300px;
  height: 300px;
  margin-bottom: 30px;
}

.timer-ring {
  width: 100%;
  height: 100%;
}

.timer-progress {
  transition: stroke-dasharray 0.3s ease;
}

.timer-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.tomato-icon {
  font-size: 48px;
  display: block;
  margin-bottom: 10px;
}

.time-display {
  font-size: 56px;
  font-weight: 700;
  color: #333;
  font-family: 'SF Mono', Monaco, monospace;
}

.status {
  display: block;
  margin-top: 8px;
  color: #ff6b6b;
  font-size: 14px;
}

.timer-controls {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
}

.start-btn {
  padding: 14px 50px;
  border: none;
  border-radius: 30px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ff8e8e 100%);
  color: white;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  box-shadow: 0 4px 15px rgba(255,107,107,0.3);
  transition: all 0.2s;
}

.start-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(255,107,107,0.4);
}

.reset-btn {
  width: 48px;
  height: 48px;
  border: none;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  font-size: 18px;
  cursor: pointer;
  transition: all 0.2s;
}

.reset-btn:hover {
  transform: rotate(180deg);
  transition: transform 0.5s;
}

.pomodoro-count {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #666;
  font-size: 14px;
}

.pomodoro-count .count {
  font-weight: 600;
  color: #ff6b6b;
}

.sidebar {
  width: 320px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.task-panel,
.stats-panel {
  background: #fff;
  border-radius: 20px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.panel-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.add-task-btn {
  font-size: 13px;
  color: #ff6b6b;
  background: none;
  border: none;
  cursor: pointer;
  font-weight: 500;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 12px;
  background: #fafafa;
  cursor: pointer;
  transition: all 0.2s;
}

.task-item:hover {
  background: #f0f0f0;
}

.task-item.completed {
  opacity: 0.6;
}

.task-item.completed .task-name {
  text-decoration: line-through;
  color: #999;
}

.checkbox {
  width: 22px;
  height: 22px;
  border: 2px solid #ddd;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #fff;
  transition: all 0.2s;
}

.checkbox.checked {
  background: linear-gradient(135deg, #67c23a 0%, #85ce61 100%);
  border-color: #67c23a;
}

.task-name {
  flex: 1;
  font-size: 14px;
  color: #333;
}

.task-pomodoros {
  font-size: 12px;
  color: #ff6b6b;
}

.stats-panel h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0 0 20px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #ff6b6b;
}

.stat-label {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.history-panel {
  background: #fff;
  border-radius: 20px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

.time-select {
  padding: 6px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 13px;
  cursor: pointer;
  background: #fff;
}

.chart-container {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.chart-y-axis {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 180px;
  font-size: 12px;
  color: #999;
  padding-right: 10px;
}

.chart-bars {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  height: 180px;
  padding-top: 20px;
  border-top: 2px solid #f0f0f0;
}

.bar-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  width: 12%;
}

.bar {
  width: 100%;
  max-width: 40px;
  background: linear-gradient(180deg, #ff6b6b 0%, #ffaaa5 100%);
  border-radius: 8px 8px 0 0;
  position: relative;
  transition: height 0.5s ease;
}

.bar-tooltip {
  position: absolute;
  top: -35px;
  left: 50%;
  transform: translateX(-50%);
  background: #333;
  color: #fff;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 12px;
  white-space: nowrap;
}

.bar-label {
  font-size: 12px;
  color: #666;
}

@media (max-width: 768px) {
  .pomodoro-container {
    padding: 20px;
  }
  
  .header {
    flex-direction: column;
    gap: 20px;
  }
  
  .main-content {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
  
  .timer-container {
    width: 250px;
    height: 250px;
  }
  
  .time-display {
    font-size: 48px;
  }
}
</style>
