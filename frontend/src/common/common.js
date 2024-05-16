export const mapParams = {
  lat: 37.5004462,
  lng: 127.037236,
  zoomOut: 12,
  zoomIn: 16,
}

export async function initMap(mapDiv, lat, lng, zoom=mapParams.zoomOut){
  return new Promise((resolve)=>{
    if(typeof naver !== 'undefined'){
      const mapOptions = {
        center: new naver.maps.LatLng(lat, lng),
        zoom,
      };

      resolve(new naver.maps.Map(mapDiv, mapOptions))
      return;
    };

    const mapScript = document.createElement("script");
    const clientId = import.meta.env.VITE_NAVER_API_KEY;
    mapScript.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}`;
    mapScript.async = true;
    mapScript.defer = true;
    document.head.appendChild(mapScript);
  
    mapScript.onload = () => {
    
      const mapOptions = {
        center: new naver.maps.LatLng(lat, lng),
        zoom: 14
      };

      resolve(new naver.maps.Map(mapDiv, mapOptions));
    }
  })
}

export const getLowestPrice = (program) => program.times.reduce((prev, cur) => prev < cur.price ? prev : cur.price, program.times[0].price)

