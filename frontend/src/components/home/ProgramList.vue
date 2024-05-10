<template>
    <div>
        <div v-for="program in store.programList" :key="program.id">
            {{ program.title }}
        </div>
    </div>
</template>

<script setup>
import { useProgramStore } from "@/stores/programStore";

const store = useProgramStore()
</script>

<style scoped>

</style>