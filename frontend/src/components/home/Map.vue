<template>
    <div>
        <div id="map" style="width:100%; height:800px"></div>
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';
import { onMounted } from "vue";
import * as common from "@/common/common.js";

const store = useProgramStore()

onMounted(async ()=>{
    const mapDiv = document.getElementById("map");
    const map = await common.initMap(mapDiv);

    if(!store.programList.length){
        alert("조건에 해당하는 프로그램이 없습니다.") // TODO: remove
    }

    for(const program of store.programList){
        const lowestPrice = common.getLowestPrice(program);
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
                content: `<div style="${markerStyle}">${lowestPrice.toLocaleString()}~</div>`,
                size: new naver.maps.Size(32, 32),
                anchor: new naver.maps.Point(16, 16),
            }
        });
        
        naver.maps.Event.addListener(marker, 'click', clickEvent(program));
    }
})

function clickEvent(program){
    return function(e) {
        store.programList.value = [program]
    }
}

</script>

<style scoped>
</style>
