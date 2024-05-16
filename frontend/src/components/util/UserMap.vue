<template>
    <div>
        <div :id="id" :style="{width: width, height: height}"></div>
    </div>
</template>

<script setup>
import { initMap } from "@/common/common";
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
    console.log(props.lat)
    const map = await initMap(id, props.lat, props.lng)
    console.log("MAP FIN")

    const position = new naver.maps.LatLng(props.lat, props.lng);
    let marker = new naver.maps.Marker({
        position: position,
        map: map
    });
    
    // update(position);
    const update = (coord) => emit("update-lat-lng", {id:props.id, lat:coord._lat, lng:coord._lng});
    update(position);

    if(isMarkable){
        naver.maps.Event.addListener(map, 'click', e => update(e.coord));
    }
}

onMounted(async () => draw())
watch(()=> [props.lat, props.lng], async () => { console.log("CHANGE"); await draw()})
</script>

<style scoped>

</style>
