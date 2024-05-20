<template>
  <div class="container">

    <div class="row">
      <div class="col-2"></div>
      <div class="col-8">
        <div class="accordion-item border rounded-3 p-2 pb-4 mb-4" id="condition-area">
          <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
              검색 필터
            </button>
          </h2>
          <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#filter-accordion">
            <div class="accordion-body px-3">
              <SearchFilter />
            </div>
          </div>
        </div>
      </div>
      <div class="col-2"></div>
    </div>


    <div class="row mb-5">
      <div class="col-4 ps-0" >
        <ProgramList />
      </div>
      <div class="col-8 p-0">
        <ProgramMap />
      </div>
    </div>
  </div>
</template>

<script setup>
import SearchFilter from '@/components/home/SearchFilter.vue';
import ProgramList from '@/components/home/ProgramList.vue';
import ProgramMap from '@/components/home/ProgramMap.vue';

import { useProgramStore } from "@/stores/programStore";
import { onMounted } from "vue";

const store = useProgramStore()
onMounted(() => {
    store.getProgramList()
})

</script>

<style scoped>
.accordion-button {
  display: inline-block;
  text-align: center;
  font-size: 15px;
  font-weight: bold;
}
.accordion-button::after {
  display: none;
}
.accordion-button:not(.collapsed), .accordion-button:focus {
  box-shadow: none;
  background-color: transparent
}

#condition-area {
  background-color: black;
  border-radius: 5px;
}
</style>
