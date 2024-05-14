<template>
    <div class="m-3 p-3 border rounded-4 bg-white text-center">
        <iframe 
            width="80%" height="400" 
            :src="`https://www.youtube.com/embed/${videoUrl}`"
            title="YouTube video player" frameborder="0" 
            allow="accelerometer; autoplay; clipboard-write; 
            encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
        </iframe>
        
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';
import { computed } from 'vue';

const store = useProgramStore();
const videoUrl = computed(()=>store.program.youtubeUrl?store.program.youtubeUrl.split('v=')[1]:"")

</script>

<style scoped>
</style>
