const defaultLatLng = {
  lat: 37.5004462,
  lng: 127.037236,
}

export async function initMap(mapDiv, lat=defaultLatLng.lat, lng=defaultLatLng.lng){
  return new Promise((resolve)=>{
    const mapScript = document.createElement("script");
    const clientId = import.meta.env.VITE_NAVER_API_KEY;
    mapScript.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}`;
    mapScript.async = true;
    mapScript.defer = true;
    document.head.appendChild(mapScript);
  
    mapScript.onload = () => {
      console.log("LOAD")
      const mapOptions = {
          center: new naver.maps.LatLng(lat, lng),
          zoom: 14
      };
      resolve(new naver.maps.Map(mapDiv, mapOptions));
    }
  })
}
