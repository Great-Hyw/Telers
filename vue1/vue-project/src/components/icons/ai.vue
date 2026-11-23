<script setup>
import { ref, inject, nextTick, onMounted } from "vue";
import { ElMessage } from "element-plus";
import "@/assets/ai.css";

const isDarkMode = inject("darkMode", ref(false));
const messages = ref([]);
const userInput = ref("");
const isLoading = ref(false);
const chatContainer = ref(null);

// 预设的 AI 建议选项
const aiSuggestions = ref([
  "如何培养新习惯？",
  "如何保持习惯的连续性？",
  "如何设定合理的目标？",
  "如何克服拖延症？",
]);

// 模拟 AI 响应
const aiResponses = {
  "如何培养新习惯？": "培养新习惯的关键是：\n1. 从小目标开始，选择一个简单易行的习惯\n2. 设定明确的触发点（时间、地点、情境）\n3. 保持一致性，每天在同一时间执行\n4. 建立奖励机制，完成后给自己小奖励\n5. 记录进度，使用习惯追踪工具可视化你的进步",
  "如何保持习惯的连续性？": "保持习惯连续性的方法：\n1. 设定提醒，使用手机或日历提醒\n2. 找到习惯的乐趣，选择你真正感兴趣的事情\n3. 建立问责机制，告诉朋友或加入社群\n4. 允许偶尔的失误，不要因为一次失败就放弃\n5. 专注于过程而非结果，享受习惯本身",
  "如何设定合理的目标？": "设定合理目标的 SMART 原则：\n1. Specific（具体）：目标要清晰明确\n2. Measurable（可衡量）：要有量化标准\n3. Achievable（可实现）：目标要切合实际\n4. Relevant（相关）：目标要与你的价值观相关\n5. Time-bound（有时限）：设定明确的截止日期",
  "如何克服拖延症？": "克服拖延症的实用策略：\n1. 5分钟法则：告诉自己只做5分钟\n2. 分解任务：将大任务拆分成小步骤\n3. 消除干扰：远离手机、社交媒体等\n4. 设定优先级：先做重要紧急的事情\n5. 奖励自己：完成任务后给予适当奖励",
  "default": "很高兴为您提供建议！您可以选择以下话题：\n• 如何培养新习惯？\n• 如何保持习惯的连续性？\n• 如何设定合理的目标？\n• 如何克服拖延症？\n\n或者直接告诉我您的具体问题，我会尽力帮助您！",
};

// 添加消息到聊天记录
const addMessage = (content, isUser = true) => {
  messages.value.push({
    id: Date.now(),
    content,
    isUser,
    timestamp: new Date(),
  });
  scrollToBottom();
};

// 滚动到底部
const scrollToBottom = async () => {
  await nextTick();
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
};

// 处理用户输入
const handleSend = async () => {
  const message = userInput.value.trim();
  if (!message || isLoading.value) return;

  // 添加用户消息
  addMessage(message, true);
  userInput.value = "";
  isLoading.value = true;

  // 模拟 AI 响应延迟
  setTimeout(() => {
    const response = aiResponses[message] || aiResponses["default"];
    addMessage(response, false);
    isLoading.value = false;
  }, 1000);
};

// 处理建议点击
const handleSuggestionClick = (suggestion) => {
  userInput.value = suggestion;
  handleSend();
};

// 格式化消息内容（支持换行）
const formatMessage = (content) => {
  return content.replace(/\n/g, "<br>");
};

// 组件挂载时添加欢迎消息
onMounted(() => {
  addMessage("您好！我是您的习惯追踪 AI 助手。我可以为您提供关于习惯养成的建议和指导。请问有什么可以帮助您的？", false);
});
</script>

<template>
  <div class="ai-chat-container" :class="{ 'dark-mode': isDarkMode }">
    <div class="chat-header">
      <div class="header-content">
        <div class="ai-avatar">
          <span class="ai-icon">🤖</span>
        </div>
        <div class="header-info">
          <h2 class="header-title">AI 习惯助手</h2>
          <p class="header-status">在线</p>
        </div>
      </div>
      <button class="close-btn" @click="$emit('close')">
        <span>✕</span>
      </button>
    </div>

    <div class="chat-messages" ref="chatContainer">
      <div
        v-for="message in messages"
        :key="message.id"
        class="message-item"
        :class="{ 'user-message': message.isUser, 'ai-message': !message.isUser }"
      >
        <div class="message-avatar" v-if="!message.isUser">
          <span class="ai-icon">🤖</span>
        </div>
        <div class="messxage-content">
          <div
            class="message-text"
            :class="{ 'user-text': message.isUser, 'ai-text': !message.isUser }"
            v-html="formatMessage(message.content)"
          ></div>
          <div class="message-time">
            {{ message.timestamp.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' }) }}
          </div>
        </div>
        <div class="message-avatar" v-if="message.isUser">
          <span class="user-icon">👤</span>
        </div>
      </div>

      <!-- 加载动画 -->
      <div v-if="isLoading" class="message-item ai-message">
        <div class="message-avatar">
          <span class="ai-icon">🤖</span>
        </div>
        <div class="message-content">
          <div class="typing-indicator">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>
    </div>

    <!-- AI 建议选项 -->
    <div class="ai-suggestions" v-if="messages.length <= 2">
      <div class="suggestions-title">💡 常见问题</div>
      <div class="suggestions-list">
        <button
          v-for="(suggestion, index) in aiSuggestions"
          :key="index"
          class="suggestion-btn"
          @click="handleSuggestionClick(suggestion)"
        >
          {{ suggestion }}
        </button>
      </div>
    </div>

    <div class="chat-input-container">
      <div class="input-wrapper">
        <textarea
          v-model="userInput"
          class="chat-input"
          placeholder="输入您的问题..."
          @keydown.enter.prevent="handleSend"
          rows="1"
          ref="inputRef"
        ></textarea>
        <button
          class="send-btn"
          @click="handleSend"
          :disabled="!userInput.trim() || isLoading"
        >
          <span v-if="!isLoading">发送</span>
          <span v-else>...</span>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>