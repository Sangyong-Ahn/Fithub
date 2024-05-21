<template>
    <div class="w-100 mb-5">
        <div class="d-flex m-3 p-3 border rounded-4 bg-white align-items-center">
            <div class="fs-1 mx-3">{{ averageRating.toFixed(1) }}</div>
            <div>
                <div class="stars">
                    <span v-for="star in 5" :key="star" :class="{ 'filled': star <= averageRating }">&#9733;</span>
                </div>
                <div>
                    {{ latestReviews.length }}개의 후기
                </div>
            </div>
        </div>
        <div class="m-3 p-3 border rounded-4 bg-white text-center">
            <div v-if="latestReviews.length > 0">
                <div class="border rounded-4 mb-3 bg-light" v-for="review in displayedReviews" :key="review.id">
                    <div class="d-flex justify-content-between">
                        <div class="d-flex align-items-center">
                            <img class="border rounded-5 m-3 mb-0" src="@/assets/common/thumbnail-demo.jpg" style="width:45px; height: 45px; object-fit: cover;">
                            <div class="fw-bold">{{ review.userInfo.name }}</div>
                            <div class="fw-bold text-secondary mx-3">{{ formatDate(review.createdAt) }}</div>
                        </div>
                        <div class="d-flex align-items-center me-5">
                            <div class="stars">
                                <span v-for="star in 5" :key="star" :class="{ 'filled': star <= review.score }">&#9733;</span>
                            </div>
                        </div>
                    </div>
                    <div class="border rounded-4 text-start p-3 mx-5 mb-3" style="word-break: break-all;">
                        {{ review.content }}
                    </div>
                </div>
                <button class="btn btn-outline-secondary" @click="toggleShowAll">
                    {{ showAll ? '접기' : '전체 보기' }}
                </button>
            </div>
            <div v-else>
                후기가 없습니다.
            </div>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';
import { computed, ref } from 'vue';

const store = useUserStore();
const showAll = ref(false);

const latestReviews = computed(() => {
    if (store.loginUser && store.loginUser.reviews) {
        return store.loginUser.reviews;
    } else {
        return [];
    }
});

const averageRating = computed(() => {
    if (store.loginUser) {
        return store.loginUser.reviewAvgScore;
    } else {
        return 0;
    }
});

const displayedReviews = computed(() => {
    return showAll.value ? latestReviews.value : latestReviews.value.slice(0, 5);
});

const toggleShowAll = () => {
    showAll.value = !showAll.value;
};

const formatDate = (isoString) => {
    const date = new Date(isoString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}.${month}.${day}`;
};
</script>

<style scoped>
.stars {
    color: gray;
}

.stars span {
    font-size: 15px;
}

.stars .filled {
    color: gold;
}
</style>
