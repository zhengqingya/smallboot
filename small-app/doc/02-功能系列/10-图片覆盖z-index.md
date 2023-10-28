# 图片覆盖z-index

单独给图片设置`z-index`是无效的，需要给图片的父级设置定位加`z-index` eg: `position: relative; z-index: 1`
然后再在外面套一级使用flex布局来让想要的内容覆盖到图片之上...

```
<view class="flex-column">

  <!-- 图片渐变： style="mask-image: linear-gradient(to bottom, black 80%, transparent 100%)" -->
  <view style="position: relative; z-index: 1">
    <u-swiper
      v-if="imgList && imgList.length > 0"
      height="160"
      :list="imgList.map((item) => item.url)"
      :indicator="true"
      indicatorMode="line"
      circular />
    <!-- <swiper>
      <swiper-item
        v-if="imgList && imgList.length > 0"
        v-for="(item, index) in imgList.map((item) => item.url)"
        :key="index">
        <view class="testp">
          <image class="test" mode="aspectFill" style="" :src="item" />
        </view>
      </swiper-item>
    </swiper> -->
  </view>
  
  <!-- 将这层放到上面的图片之上 -->
  <view style="padding: 0rpx 0rpx; z-index: 999; margin-top: -100rpx">
    xxx
  </view>
  
</view>
```