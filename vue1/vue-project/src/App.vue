<script setup>
import { provide } from 'vue';
import { ref } from 'vue';

const isDarkMode = ref(false);

const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value;
  document.body.classList.toggle('dark-mode', isDarkMode.value);
};

provide('darkMode', isDarkMode);
provide('toggleDarkMode', toggleDarkMode);
</script>

<template>
<router-view />
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  min-height: 100vh;
  background-color: #f5f5f5;
  color: #333;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  transition: background-color 0.3s, color 0.3s;
}

body.dark-mode {
  background-color: #1a1a2e;
  color: #fff;
}
</style>
