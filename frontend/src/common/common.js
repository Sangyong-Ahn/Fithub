export function initMap(mapDiv){
  const mapScript = document.createElement("script");
  const clientId = import.meta.env.VITE_NAVER_API_KEY;
  mapScript.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${clientId}`;
  mapScript.async = true;
  mapScript.defer = true;
  document.head.appendChild(mapScript);

  mapScript.onload = () => {
    return new naver.maps.Map(mapDiv);
  }
}
