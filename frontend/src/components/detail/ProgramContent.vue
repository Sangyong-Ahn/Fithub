<template>
    <div class="m-3 p-5 border rounded-4 bg-white">
        <span v-html="content"></span>
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';

const store = useProgramStore();
const content = store.program.content.replace(new RegExp('\r?\n','g'), '<br />')
</script>

<style scoped>
div {
    min-height: 400px
}
</style>