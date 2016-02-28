# Android-Architecture-Explore

尝试些一个android lib  包含工具类 自定义view 基础组件  侦听一些系统广播的Receiver等 东西
# 以下为轮播图的截图  
轮播图可以支持无线滑动 可以支持自动滚动 只需要简单的配置
  bannerView = (BannerView) findViewById(R.id.bannerView);
  //为轮播图设置数据并且 它是否为无线轮播
  bannerView.setBannerEntitiesAndLoopEnable(entities, false);
  bannerView.setOnBannerClickListener(new BannerView.onBannerClickListener() {
            @Override
            public void click(BaseBannerEntity entity, int position) {
                Log.e("click event", "click at position = " + position);
            }
        });
  //设置是否可自动轮播--- 可以传入轮播间隔
  bannerView.setAutoPlay();
  以下为效果图
![](https://github.com/1212300114/Android-Architecture-Explore/raw/master/screenshot/AutoPlayBannerAndroid.gif) 
