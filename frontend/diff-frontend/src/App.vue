<template>
  <div class="container">
    <h1>Diff Checker</h1>

    <div class="controls">
      <select v-model="mode">
        <option value="WORD">Word</option>
        <option value="CHARACTER">Character</option>
        <option value="LINE">Line</option>
      </select>

      <label>
        <input type="checkbox" v-model="caseSensitive" />
        Case Sensitive
      </label>

      <button @click="compare">Compare</button>
    </div>

    <div class="editors">
      <textarea v-model="text1" placeholder="Original text"></textarea>
      <textarea v-model="text2" placeholder="Compared text"></textarea>
    </div>

    <div class="result">
      <span v-for="(chunk, index) in chunks" :key="index" :class="chunk.type">
        {{ chunk.value }}{{ mode === 'WORD' ? ' ' : '' }}
      </span>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from "axios"
import { ref } from "vue"

interface DiffChunk {
  value: string
  type: "SAME" | "ADDED" | "REMOVED"
}

const text1 = ref("")
const text2 = ref("")
const mode = ref("WORD")
const caseSensitive = ref(false)
const chunks = ref<DiffChunk[]>([])

const compare = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/diff", {
      text1: text1.value,
      text2: text2.value,
      mode: mode.value,
      caseSensitive: caseSensitive.value
    })

    chunks.value = response.data.chunks
  } catch (error) {
    alert("Backend not running or CORS issue")
    console.error(error)
  }
}
</script>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  background: #0f172a;
}

.container {
  padding: 40px;
  color: white;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

.controls {
  display: flex;
  gap: 20px;
  justify-content: center;
  margin-bottom: 20px;
}

textarea {
  width: 48%;
  height: 250px;
  padding: 15px;
  background: #1e293b;
  border: 1px solid #334155;
  color: white;
  border-radius: 8px;
}

.editors {
  display: flex;
  gap: 20px;
  justify-content: center;
}

button {
  padding: 8px 16px;
  border-radius: 20px;
  border: none;
  background: #10b981;
  color: white;
  cursor: pointer;
}

.result {
  margin-top: 30px;
  background: #1e293b;
  padding: 20px;
  border-radius: 10px;
  min-height: 100px;
}

.SAME {
  color: white;
}

.ADDED {
  background: rgba(16, 185, 129, 0.5);
  padding: 2px 4px;
  border-radius: 4px;
}

.REMOVED {
  background: rgba(239, 68, 68, 0.5);
  padding: 2px 4px;
  border-radius: 4px;
}
</style>