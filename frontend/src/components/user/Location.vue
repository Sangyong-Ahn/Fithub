<template>
    <div>ddd
        <div id="map2" style="width:100%; height:500px"></div>
    </div>
</template>

<script setup>
import { initMap } from "@/common/common";
import { onMounted } from "vue";
import { useUserStore } from '@/stores/userStore';

const store = useUserStore()

onMounted(async ()=>{
    const mapDiv = document.getElementById("map2")
    const map = await initMap(mapDiv)

    const position = new naver.maps.LatLng(store.latitude, store.longitude);
    let marker = new naver.maps.Marker({
    position: position,
    map: map
    });

    naver.maps.Event.addListener(map, 'click', function(e) {
        marker.setPosition(e.coord);
        store.latitude = e.coord._lat;
        store.longitude = e.coord._lng;
    });
})

</script>

<style scoped>

</style>
