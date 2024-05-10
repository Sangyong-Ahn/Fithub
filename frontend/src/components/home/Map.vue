<template>
    <div>
        <div id="map" style="width:500px; height:500px"></div>
    </div>
</template>

<script setup>
import { onMounted } from "vue";
import * as common from "@/common/common.js";

    onMounted(()=>{
        const mapDiv = document.getElementById("map");
        const map = common.initMap(mapDiv);
    })

</script>

<style scoped>

</style>
