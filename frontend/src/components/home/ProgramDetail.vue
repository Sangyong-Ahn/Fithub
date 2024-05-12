<template>
    <div>
        {{ store.program.title }}
    </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useProgramStore } from "@/stores/programStore";
import { onMounted } from "vue";

const store = useProgramStore()

const route = useRoute();
const router = useRouter();

onMounted(() => {
    store.getProgram(route.params.id)
})
</script>

<style scoped>

</style>