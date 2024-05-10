<template>
    <div>
        <div id="map" style="width:500px; height:500px"></div>
    </div>
</template>

<script setup>
import { onMounted } from "vue";
import * as common from "@/common/common.js";

const programs = [ // sample data
  {
    "id": 2,
    "mentorId": 1,
    "categoryId": 3,
    "title": "멘토 1의 글",
    "reservationStartDate": "2024-05-09T15:00:00.000+00:00",
    "reservationEndDate": "2024-05-09T15:00:00.000+00:00",
    "programStartDate": "2024-05-09T15:00:00.000+00:00",
    "programEndDate": "2024-05-09T15:00:00.000+00:00",
    "thumbnail": "string",
    "content": "string",
    "youtubeUrl": "string",
    "latitude": 37.5081110,
    "longitude": 127.037237,
    "createdAt": "2024-05-10T14:37:11.000+00:00",
    "times": [
      {
        "id": 1,
        "programId": 2,
        "sunday": true,
        "monday": false,
        "tuesday": true,
        "wednesday": false,
        "thursday": false,
        "friday": false,
        "saturday": false,
        "startTime": "12:00:00",
        "endTime": "14:00:00",
        "price": 3,
        "capacity": 5,
        "reserveNum": 0
      },
      {
        "id": 2,
        "programId": 2,
        "sunday": false,
        "monday": true,
        "tuesday": true,
        "wednesday": false,
        "thursday": false,
        "friday": false,
        "saturday": false,
        "startTime": "14:00:00",
        "endTime": "15:00:00",
        "price": 7,
        "capacity": 3,
        "reserveNum": 0
      }
    ],
    "mentorInfo": null
  },
  {
    "id": 3,
    "mentorId": 2,
    "categoryId": 3,
    "title": "멘토 2의 글",
    "reservationStartDate": "2024-05-09T15:00:00.000+00:00",
    "reservationEndDate": "2024-05-09T15:00:00.000+00:00",
    "programStartDate": "2024-05-09T15:00:00.000+00:00",
    "programEndDate": "2024-05-09T15:00:00.000+00:00",
    "thumbnail": "string",
    "content": "string",
    "youtubeUrl": "string",
    "latitude": 37.5004463,
    "longitude": 127.037236,
    "createdAt": "2024-05-10T14:37:11.000+00:00",
    "times": [
      {
        "id": 1,
        "programId": 2,
        "sunday": true,
        "monday": false,
        "tuesday": true,
        "wednesday": false,
        "thursday": false,
        "friday": false,
        "saturday": false,
        "startTime": "12:00:00",
        "endTime": "14:00:00",
        "price": 3,
        "capacity": 5,
        "reserveNum": 0
      },
      {
        "id": 2,
        "programId": 2,
        "sunday": false,
        "monday": true,
        "tuesday": true,
        "wednesday": false,
        "thursday": false,
        "friday": false,
        "saturday": false,
        "startTime": "14:00:00",
        "endTime": "15:00:00",
        "price": 7,
        "capacity": 3,
        "reserveNum": 0
      }
    ],
    "mentorInfo": null
  }
]
let programId = null;

onMounted(async ()=>{
    const mapDiv = document.getElementById("map");
    const map = await common.initMap(mapDiv);
    console.log("AWAIT FIN")
    
    for(const program of programs){
        console.log(program.latitude)
        const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(program.latitude, program.longitude),
            map: map,
        });
        naver.maps.Event.addListener(marker, 'click', clickEvent(program.id));
    }
})

function clickEvent(pId){
    return function(e) {
        programId = pId;
        console.log(programId)
    }
}

</script>

<style scoped>

</style>
