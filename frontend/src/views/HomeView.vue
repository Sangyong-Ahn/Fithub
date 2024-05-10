<template>
  <div class="accordion mx-5 my-3" id="filter-accordion">
    <div class="accordion-item">
      <h2 class="accordion-header">
        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          검색 조건을 입력하세요
        </button>
      </h2>
      <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#filter-accordion">
        <div class="accordion-body">
          <SearchFilter />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import SearchFilter from '@/components/home/SearchFilter.vue';
</script>

<style scoped>
.accordion-button {
  display: inline-block;
  text-align: center;
}
.accordion-button::after {
  display: none;
}
.accordion-button:not(.collapsed), .accordion-button:focus {
  box-shadow: none;
  background-color: transparent
}
</style>
