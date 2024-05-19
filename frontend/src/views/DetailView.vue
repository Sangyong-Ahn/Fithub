<template>
    <div class="container">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10">
                <nav id="program-navbar" class="navbar bg-white p-3 mb-4 justify-content-around border-bottom">
                    <div class="col-2 text-center">
                        <a class="nav nav-link text-body-secondary" href="#programInfo">프로그램 정보</a>
                    </div>
                    <div class="col-2 text-center">
                        <a class="nav nav-link text-body-secondary" href="#programContent">프로그램 소개</a>
                    </div>
                    <div class="col-2 text-center">
                        <a class="nav nav-link text-body-secondary" href="#programVideo">참고 영상</a>
                    </div>
                    <div class="col-2 text-center">
                        <a class="nav nav-link text-body-secondary" href="#mentorInfo">멘토 정보</a>
                    </div>
                    <div class="col-2 text-center">
                        <a class="nav nav-link text-body-secondary" href="#review">후기</a>
                    </div>
                </nav>
                <div data-bs-spy="scroll" data-bs-target="#program-navbar" data-bs-root-margin="0px 0px -40%" data-bs-smooth-scroll="true" class="scrollspy-example p-3 rounded-2" tabindex="0">
                    <div class="border mb-5 rounded-4 bg-light">
                        <div class="d-flex justify-content-between align-items-center">
                            <h4 class="mx-3 mt-3" id="programInfo">프로그램 정보</h4>
                            <button v-if="showDeleteButton" class="btn btn-outline-danger mx-3 mt-3" @click="deleteProgram">삭제하기</button>
                        </div>
                        <ProgramInfo />
                    </div>
                    <div class="border mb-5 rounded-4 bg-light">
                        <h4 class="m-3" id="programContent">프로그램 소개</h4>
                        <ProgramContent />
                    </div>
                    <div class="border mb-5 rounded-4 bg-light">
                        <h4 class="m-3" id="programVideo">참고 영상</h4>
                        <ProgramVideo />
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="border mb-5 rounded-4 bg-light w-75">
                            <h4 class="m-3" id="mentorInfo">멘토 정보</h4>
                            <ProgramMentor />
                        </div>
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="border mb-5 rounded-4 bg-light w-75">
                            <h4 class="m-3" id="review">후기</h4>
                            <ProgramReview />
                        </div>
                    </div>
                    <div class="d-flex justify-content-center">
                        <div class="border mb-5 rounded-4 bg-light w-75 .flex-fill">
                            <h4 class="m-3" id="review">위치</h4>
                                <div class="d-flex flex-fill m-3 p-3 border rounded-4 bg-white align-items-center">
                                    <div class="flex-fill" id="map-container">
                                        <!-- TODO: 동기 처리 -->
                                        <UserMap
                                            :id="'program'"
                                            :lat="programStore.program.latitude"
                                            :lng="programStore.program.longitude"
                                            :width= "'100%'"
                                            :height="'300px'"
                                        />
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useProgramStore } from "@/stores/programStore";
import { useChatStore } from "@/stores/chatStore";
import { useUserStore } from "@/stores/userStore";
import { computed, onMounted, watch } from "vue";
import ProgramInfo from "@/components/detail/ProgramInfo.vue"
import ProgramContent from "@/components/detail/ProgramContent.vue"
import ProgramVideo from "@/components/detail/ProgramVideo.vue"
import ProgramMentor from "@/components/detail/ProgramMentor.vue"
import ProgramReview from "@/components/detail/ProgramReview.vue"
import UserMap from "@/components/util/UserMap.vue"

const programStore = useProgramStore()
const chatStore = useChatStore()
const userStore = useUserStore()
const route = useRoute();

const deleteProgram = function () {
    programStore.deleteProgram(route.params.id)
}

// 현재 날짜와 비교하여 삭제 버튼 표시 여부 결정
const showDeleteButton = computed(() => {
    if(userStore.loginUser !== null && userStore.isMentor && userStore.loginUser.id === programStore.program.mentorId) {
        const now = new Date();
        const reservationStartDate = new Date(programStore.program.reservationStartDate);
        return now < reservationStartDate;
    }
    
    return false;
});

onMounted(async () => {
    await programStore.getProgram(route.params.id)
})

onMounted(() => {
    const navbar = document.getElementById('program-navbar');

    window.addEventListener('scroll', () => {
        if (window.scrollY > navbar.offsetTop) {
            navbar.classList.add('fixed-top');
        } else {
            navbar.classList.remove('fixed-top');
        }
    });
})

watch(() => programStore.program, (newVal, oldVal) => {
    if (newVal && userStore.loginUser && userStore.isUser) {
        chatStore.getChatList(programStore.program.mentorId, userStore.loginUser.id);
    }
});
</script>

<style scoped>

</style>