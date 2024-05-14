<template>
    <div class="m-3 p-3 border rounded-4 bg-white">
        {{ store.program.content }}
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';

const store = useProgramStore();
</script>

<style scoped>
div {
    height: 400px
}
</style>