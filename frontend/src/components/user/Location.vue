<template>
    <div>
        <div id="userMap" style="width:100%; height: 300px;"></div>
    </div>
</template>

<script setup>
import { initMap, defaultLatLng } from "@/common/common";
import { onMounted, watch } from "vue";

const props = defineProps({
    lat: Number,
    lng: Number,
})
const emit = defineEmits(["update-location"])

onMounted(async () => {
    const map = await initMap("userMap", props.lat, props.lng)

    const position = new naver.maps.LatLng(props.lat, props.lng);
    let marker = new naver.maps.Marker({
        position: position,
        map: map
    });
    
    update(position);

    naver.maps.Event.addListener(map, 'click', e => update(e.coord));

    function update(coord){
        console.log(coord)
        marker.setPosition(coord);
        // map.setCenter(coord);
        // TODO: 화면을 한 번이라도 resize 하기 전까지 중앙 위치가 잘못 잡히는 문제
        emit("update-lat-lng", {lat:coord._lat, lng:coord._lng});

    }
})

</script>

<style scoped>

</style>
