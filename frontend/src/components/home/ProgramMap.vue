<template>
    <div>
        <div id="map" style="width:100%; height:800px"></div>
    </div>
</template>

<script setup>
import { useProgramStore } from '@/stores/programStore';
import { onMounted, watch } from "vue";
import * as common from "@/common/common.js";

const store = useProgramStore()
onMounted(()=>{
})

watch(async ()=> store.programList, async () => {
    const mapDiv = document.getElementById("map");
    const map = await common.initMap(mapDiv, store.programList[0].latitude, store.programList[0].longitude);
    console.log('watch processing')
    console.log(store.programList)
    for(const program of store.programList){
        const lowestPrice = common.getLowestPrice(program);

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
})

function clickEvent(program){
    return function(e) {
        store.programList = [program]
        store.isSpecified = true
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
