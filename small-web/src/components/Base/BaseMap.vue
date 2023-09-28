<template>
  <div id="container" class="w-full h-full"></div>
</template>
<script setup>
import axios from 'axios';
const { proxy } = getCurrentInstance();
let { lbs_qq_key } = toRefs(proxy.$store.system.useSystemStore());
const props = defineProps({
  width: { type: String, default: '600px' },
  height: { type: String, default: '300px' },
  modelValue: { type: Object, default: null }, // 坐标位置数据
});
let isInit = ref(false);
// 最新位置信息
let geoObj = ref({
  lng: null, // 经度
  lat: null, // 纬度
  address: null, // 详细地址
  provinceCityAreaList: null, // 省市区 eg:['四川省', '成都市', '双流区']
});

watch(
  () => geoObj.value.lng, // 监听经纬度即可，如果监听整个对象，会导致locationToAddress中修改地址 -> 即多次无效回弹处理
  async (val) => {
    // 坐标转详细地址
    // console.log('111', val);
    await locationToAddress(new TMap.LatLng(geoObj.value.lat, geoObj.value.lng));
    // proxy.$emit('update:modelValue', geoObj.value);
    proxy.$emit('changeGeoData', geoObj.value);
  },
  {
    // immediate: true, // 初始化执行一次
    deep: true, // 深度监听
  },
);

defineExpose({ init });

// let address = ref('四川省成都市武侯区天府四街');
let map = null;
let marker = null;

onUpdated(() => {
  // 延时加载，保证地图js初始化完成
  setTimeout(() => initMap({ lat: props.modelValue.lat, lng: props.modelValue.lng }), 500);
});

// { lat: 30.56079, lng: 104.07483 }
// proxy.$refs.mapRef.init({ lng: form.longitude, lat: form.latitude });
function init(locationObj) {
  setTimeout(() => initMap(locationObj), 500);
}

async function initMap(locationObj) {
  if (isInit.value) {
    return;
  }
  isInit.value = true;

  initLocationObj(locationObj);

  var center = new TMap.LatLng(locationObj.lat, locationObj.lng);
  // 初始化地图 https://lbs.qq.com/webDemoCenter/glAPI/glMap/createMap
  map = new TMap.Map(document.getElementById('container'), {
    rotation: 0, // 设置地图旋转角度
    pitch: 30, // 设置俯仰角度（0~45）
    zoom: 13, // 设置地图缩放级别
    center: center, // 设置地图中心点坐标
  });

  // 初始化位置标点
  createMarker(new TMap.LatLng(locationObj.lat, locationObj.lng));

  // 监听点击事件添加marker
  map.on('click', (evt) => {
    removeMarker();
    createMarker(evt.latLng);
    geoObj.value.lng = evt.latLng.lng;
    geoObj.value.lat = evt.latLng.lat;
  });
}

async function initLocationObj(locationObj) {
  if (!locationObj || !locationObj.lat) {
    // 获取当前ip地址 https://www.ipify.org/
    let ipRes = await axios.get('http://api.ipify.org?format=json');
    let ip = ipRes.data.ip;

    // 通过ip获取定位  https://lbs.qq.com/service/webService/webServiceGuide/webServiceIp
    await axios
      .get(`https://apis.map.qq.com/ws/location/v1/ip?ip=${ip}&key=${lbs_qq_key.value}`)
      // .get(`https://apis.map.qq.com/ws/location/v1/ip?key=${lbs_qq_key.value}`)
      .then((res) => {
        alert('腾讯地图组件');
        console.log('111', res);
        locationObj = res.result.location;
      })
      .catch((err) => {
        console.log('通过ip获取定位异常：', err);
        locationObj = { lat: 30.56079, lng: 104.07483 };
      });
  }
}

// 创建marker事件
function createMarker(position) {
  if (!marker) {
    marker = new TMap.MultiMarker({
      id: 'marker-layer',
      map: map,
      // 自定义样式
      // styles: {
      //   marker: new TMap.MarkerStyle({
      //     width: 25,
      //     height: 35,
      //     anchor: { x: 16, y: 32 },
      //     src: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/markerDefault.png',
      //   }),
      // },
      geometries: [
        {
          id: 'demo',
          styleId: 'marker',
          position: position,
          properties: {
            title: 'marker',
          },
        },
      ],
    });
  }
}
// 移除marker事件
function removeMarker() {
  if (marker) {
    marker.setMap(null);
    marker = null;
  }
}

// 地址转坐标 https://lbs.qq.com/webDemoCenter/glAPI/glServiceLib/geocoderGetLocation
// proxy.$refs.mapRef.addressToLocation(form.address);
async function addressToLocation(addressStr) {
  if (!addressStr) {
    proxy.submitFail('请先填写地址!');
    return;
  }
  var geocoder = new TMap.service.Geocoder(); // 新建一个正逆地址解析类
  var markers = new TMap.MultiMarker({ map: map, geometries: [] });
  markers.setGeometries([]);
  // 将给定的地址转换为坐标位置
  let locationObj;
  await geocoder.getLocation({ address: addressStr }).then((result) => {
    // 拿到坐标数值
    locationObj = result.result.location;
    // console.log('结果', locationObj);
    markers.updateGeometries([
      {
        id: 'main',
        position: locationObj, // 将得到的坐标位置用点标记标注在地图上
      },
    ]);
    map.setCenter(locationObj);
  });
  geoObj.value.lng = locationObj.lng;
  geoObj.value.lat = locationObj.lat;
}

// 坐标转地址 https://lbs.qq.com/webDemoCenter/glAPI/glServiceLib/geocoderGetAddress
async function locationToAddress(location) {
  var geocoder = new TMap.service.Geocoder(); // 新建一个正逆地址解析类
  let res = await geocoder.getAddress({ location: location }); // 将给定的坐标位置转换为地址
  geoObj.value.address = res.result.address;
  let ac = res.result.address_component;
  geoObj.value.provinceCityAreaList = [ac.province, ac.city, ac.district];
}
</script>
<style lang="scss" scoped></style>
