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
        mapTypeId: naver.maps.MapTypeId.HYBRID,
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
        zoom: 14,
        mapTypeId: naver.maps.MapTypeId.HYBRID,
      };

      resolve(new naver.maps.Map(mapDiv, mapOptions));
    }
  })
}

export const getLowestPrice = (program) => program.times.reduce((prev, cur) => prev < cur.price ? prev : cur.price, program.times[0].price)

export const getDateString = (afterDay=0) => {
  const date = new Date();
  date.setDate(date.getDate() + afterDay);
  
  const yyyy = date.getFullYear(); // 년도
  let mm = '' + (date.getMonth() + 1);  // 월
  let dd = '' + date.getDate();  // 날짜

  if(mm.length==1) mm = '0' + mm;
  if(dd.length==1) dd = '0' + dd;
  
  return `${yyyy}-${mm}-${dd}`
}
