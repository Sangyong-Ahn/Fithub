<template>
    <div class="border mb-3 p-2 d-flex justify-content-around" style="height:100px; align-items:center;">
        <div>
            <input type="radio" class="btn-check" name="sortOptions" id="newest" value="newest" autocomplete="off" v-model="selectedSort" @change="sortPrograms">
            <label class="btn btn-outline-secondary rounded-5 mx-2" for="newest">최신순</label>

            <input type="radio" class="btn-check" name="sortOptions" id="lowestPrice" value="lowestPrice" autocomplete="off" v-model="selectedSort" @change="sortPrograms">
            <label class="btn btn-outline-secondary rounded-5 mx-2" for="lowestPrice">가격 낮은순</label>

            <input type="radio" class="btn-check" name="sortOptions" id="highestRating" value="highestRating" autocomplete="off" v-model="selectedSort" @change="sortPrograms">
            <label class="btn btn-outline-secondary rounded-5 mx-2" for="highestRating">별점 높은순</label>
        </div>
    </div>
    <div class="border scrollbar">
        <div v-if="store.isSpecified">
            <button class="btn btn-white d-flex align-items-center mt-2" @click="goBack">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M14 18L8 12L14 6" stroke="#3D454D" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>                
            </button>
        </div>
        <div v-if="store.programList && store.programList.length">
            <RouterLink
                v-for="program in store.programList"
                :key="program.id"
                :to="`/program/${program.id}`"
                class="text-decoration-none"
                >
                <ProgramCard :program="program" />
            </RouterLink>
        </div>
        <div v-else class="text-center mt-5 fs-4">
            조건에 맞는 결과가 없습니다.
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { RouterLink } from "vue-router";
import { useProgramStore } from "@/stores/programStore";
import ProgramCard from "@/components/home/ProgramCard.vue"
import { getLowestPrice } from '@/common/common.js';

const store = useProgramStore()
const selectedSort = ref('newest');

const sortPrograms = () => {
    switch (selectedSort.value) {
        case 'newest':
            store.programList.sort((a, b) => new Date(b.createAt) - new Date(a.createAt));
            break;
        case 'lowestPrice':
            store.programList.sort((a, b) => getLowestPrice(a) - getLowestPrice(b));
            break;
        case 'highestRating':
            store.programList.sort((a, b) => b.mentorInfo.reviewAvgScore - a.mentorInfo.reviewAvgScore);
            break;
        default:
            break;
    }
};

const goBack = function() {
    store.programList = store.originalProgramList
    store.isSpecified = false;
}

</script>

<style scoped>

.scrollbar { 
  width: 100%;
  height: 685px;
  overflow-y: scroll; /*  */
}

/* 스크롤바의 폭 너비 */
.scrollbar::-webkit-scrollbar {
    width: 11px;  
}

.scrollbar::-webkit-scrollbar-thumb {
    background: rgb(171, 231, 6); /* 스크롤바 색상 */
    border-radius: 10px; /* 스크롤바 둥근 테두리 */
}

.scrollbar::-webkit-scrollbar-track {
    background: rgba(171, 231, 6, .1);  /*스크롤바 뒷 배경 색상*/
}

</style>