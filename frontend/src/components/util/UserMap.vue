<template>
    <div>
        <div :id="id" :style="{width: width, height: height, borderRadius: '5px'}"></div>
    </div>
</template>

<script setup>
import { initMap, mapParams } from "@/common/common";
import { onMounted, watch } from "vue";

const props = defineProps({
    id: String,
    lat: Number,
    lng: Number,
    width: String,
    height: String,
    isMarkable: String,
})

const emit = defineEmits(["update-lat-lng"])

const id=`${props.id}Map`;
const isMarkable = props.isMarkable || false;

const draw = async () => {
    const map = await initMap(id, props.lat, props.lng, mapParams.zoomIn)

    const position = new naver.maps.LatLng(props.lat, props.lng);
    let marker = new naver.maps.Marker({
        position: position,
        map: map
    });
    
    const update = (coord) => emit("update-lat-lng", {id:props.id, lat:coord._lat, lng:coord._lng});
    update(position);

    if(isMarkable){
        naver.maps.Event.addListener(map, 'click', e => update(e.coord));
    }
}

onMounted(async () => draw())
watch(()=> [props.lat, props.lng], async () => await draw())
</script>

<style scoped>

</style>
