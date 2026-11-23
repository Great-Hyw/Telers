<script setup>
import { ref, onMounted } from 'vue';
import { chataiapi, gethabitdapi, addhabitdapi, deletehabitsapi } from "@/api/loginapi";
const res=ref([]);


onMounted(
   async () => {
   const red = await gethabitdapi();
   res.value=red.data;
  }
)
console.log(res.value);

</script>
<template>
  <div class="table">
     <div class="my-habit-list" v-if="res.length > 0">
      <h1>我的习惯之</h1>
          <div v-for="habit in res" :key="habit.id" class="habit-item"
            :class="{ completed: habit.completed }">
            <div class="habit-checkbox" @click="toggleHabit(habit)">
              <span>{{ habit.completed ? "✓" : "" }}</span>
            </div>
            <div class="habit-info">
              <span class="habit-name">{{ habit.name }}</span>
              
            </div>
            <div>
              <el-button class="habit-delete" plain @click="addid(habit)">
                🗑️
              </el-button>

            </div>
          </div>
        </div>
    <div>
      <h1>
        我的任务之
      </h1>
    </div>
  </div>
</template>