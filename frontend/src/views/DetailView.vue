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
                        <h4 class="m-3" id="programInfo">프로그램 정보</h4>
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
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useProgramStore } from "@/stores/programStore";
import { onMounted } from "vue";
import ProgramInfo from "@/components/detail/ProgramInfo.vue"
import ProgramContent from "@/components/detail/ProgramContent.vue"
import ProgramVideo from "@/components/detail/ProgramVideo.vue"
import ProgramMentor from "@/components/detail/ProgramMentor.vue"
import ProgramReview from "@/components/detail/ProgramReview.vue"

const store = useProgramStore()
const route = useRoute();

onMounted(() => {
    store.getProgram(route.params.id)
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
</script>

<style scoped>

</style>