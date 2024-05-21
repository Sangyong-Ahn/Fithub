<template>
    <div class="m-3 p-5 border rounded-4 bg-white">
        <span v-html="formattedContent"></span>
    </div>
</template>

<script setup>
import { computed } from 'vue';
import { useProgramStore } from '@/stores/programStore';

const store = useProgramStore();

const formattedContent = computed(() => {
    if (store.program && store.program.content) {
        return store.program.content.replace(new RegExp('\r?\n', 'g'), '<br />');
    }
    return '';
});
</script>

<style scoped>
div {
    min-height: 400px;
}
</style>
