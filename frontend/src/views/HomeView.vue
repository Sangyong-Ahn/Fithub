<template>
  <div class="container">

    <div class="row">
      <div class="col-2"></div>
      <div class="col-8">
        <div class="accordion-item border pb-1 mb-4" :class="{'collapsed': collapsed}">
          <h2 class="accordion-header">
            <button id="toggle-btn" class="accordion-button text-center" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne" @click="toggle">
              <img src="@/assets/common/filter.png" id="filter-clicker" class="m-1">
            </button>
          </h2>
          <div id="collapseOne" class="accordion-collapse collapse show mb-3" data-bs-parent="#filter-accordion">
            <div class="accordion-body px-4">
              <SearchFilter
              @collapse="collapseFilter"
               />
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
import { computed, onMounted, ref } from "vue";

const store = useProgramStore()
onMounted(() => {
    store.getProgramList()
})

const collapsed = ref(false)

const toggle = () => {
  const toggleBtn = document.getElementById('toggle-btn');
  const isExpanded = toggleBtn.getAttribute('aria-expanded');
  collapsed.value = isExpanded=='false'
}

const collapseFilter = () => {
  collapsed.value = true;
}

</script>

<style scoped>
.accordion-item {
  background-color: #040404;
  border-radius: 5px;
}
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

#filter-clicker {
  height: 30px;
}

.collapsed {
  border-color: lightgreen !important;
}

</style>
