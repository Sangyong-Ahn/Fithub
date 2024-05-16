<template>
    <div>
        <div id="map" style="width:100%; height:800px"></div>
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';
import { useUserStore } from '@/stores/userStore';
import { watch } from "vue";
import { initMap, mapParams, getLowestPrice } from "@/common/common.js";

const programStore = useProgramStore()
const userStore = useUserStore()

watch(()=> programStore.programList, async () => update())
watch(()=> userStore.loginUser, async () => update())

async function update() {
    console.log(userStore.loginUser)
    const mapDiv = document.getElementById("map");

    let lat = userStore.loginUser?.latitude || mapParams.lat;
    let lng = userStore.loginUser?.longitude || mapParams.lng;
    let zoom = mapParams.zoomOut;
    
    if(programStore.isSpecified && programStore.programList.length > 0){
        lat = programStore.programList[0]?.latitude;
        lng = programStore.programList[0]?.longitude;
        zoom = mapParams.zoomIn;
    }
    const map = await initMap(mapDiv, lat, lng, zoom);

    for(const program of programStore.programList){
        const lowestPrice = getLowestPrice(program);
        const markerStyle = `
        background-color: white;
        border: 1px solid gray;
        border-radius: 5px;
        padding: 5px;
        `
        const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(program.latitude, program.longitude),
            map: map,
            icon:{
                content: `
                <div class="marker">${lowestPrice.toLocaleString()}~</div>
                <div class="marker-pin"></div>
                `,
                size: new naver.maps.Size(32, 32),
                anchor: new naver.maps.Point(32, 32),
            }
        });
        
        naver.maps.Event.addListener(marker, 'click', clickEvent(program));
    }
}

function clickEvent(program){
    return function(e) {
        programStore.programList = [program]
        programStore.isSpecified = true
    }
}

</script>

<style>
.marker {
    background-color: white;
    border: 1px solid gray;
    border-radius: 5px;
    padding: 5px;
    z-index: -1;
}
.marker-pin {
    position: absolute;
    bottom: -5px;
    left: calc(50% - 6px);
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    border-top: 6px solid gray;
    
}
.marker-pin::before {
    content: "";
    position: absolute;
    bottom: 1.5px;
    left: -6px;
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    border-top: 6px solid white;
}
</style>
