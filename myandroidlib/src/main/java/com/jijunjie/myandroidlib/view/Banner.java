package com.jijunjie.myandroidlib.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.demo.news.adapters.DotsGridAdapter;
import com.example.demo.news.adapters.MyPagerAdapter;
import com.example.demo.news.databeans.ColumnEntity;
import com.example.demo.news.utils.ImageLoaderInition;

import net.xinhuamm.d0403.R;

import java.util.ArrayList;
import java.util.List;


public class Banner extends FrameLayout implements OnClickListener {
    //轮播图的内容 偷了 一个开源项目里的
    private List<ColumnEntity.DataEntity.BannerEntity> entities;
    private List<View> views;
    private Context context;
    private MyViewPager vp;
    private boolean isAutoPlay;
    private int currentItem;
    private int delayTime;
    private Handler handler = new Handler();
    private OnItemClickListener mItemClickListener;
    private TextView tvTitle;
    private DotsGridAdapter dotsGridAdapter;

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.entities = new ArrayList<>();
        initView();
        //初始化图片加载器,传递context和轮播图数据
    }

    private void initView() {
        views = new ArrayList<>();
        delayTime = 2000;
        //初始化了点的内容和轮播图图片的内容
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context) {
        this(context, null);
    }

    public void setTopEntities(List<ColumnEntity.DataEntity.BannerEntity> topEntities) {
        this.entities = topEntities;
        reset();
    }

    //应该是刷新的时候调用它来重新加载数据
    private void reset() {
        views.clear();//清楚数据
        initUI();
    }

    private void initUI() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.banner_layout, this, true);
        vp = (MyViewPager) view.findViewById(R.id.vp);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        //初始化轮播图表示点的父view并且清楚所有的 点
        int len = entities.size();//根据轮播图内容数据条数来动态的添加点
        GridView gridView = (GridView) view.findViewById(R.id.dots);
        if (null != entities) {
            dotsGridAdapter = new DotsGridAdapter(entities, context);
            dotsGridAdapter.setEntities(entities);
            gridView.setAdapter(dotsGridAdapter);
            gridView.setNumColumns(entities.size());
            gridView.getLayoutParams().width = entities.size() * 30;
            gridView.requestLayout();
        }
        for (int i = 0; i <= len + 1; i++) {
            View fm = LayoutInflater.from(context).inflate(
                    R.layout.item_image, null);//初始化轮播图内容图片以及标题
            ImageView iv = (ImageView) fm.findViewById(R.id.iv_title);
            iv.setScaleType(ScaleType.CENTER_CROP);
            //iv.setBackgroundResource(R.drawable.loading1);
            if (i == 0) {
                ImageLoaderInition.imageLoader.displayImage(entities.get(len - 1).getImage(), iv, ImageLoaderInition.options);
                //吧第一张图设置成最后一条数据
            } else if (i == len + 1) {
                ImageLoaderInition.imageLoader.displayImage(entities.get(0).getImage(), iv, ImageLoaderInition.options);
                // 吧最后一张图设置成第一条数据
            } else {
                ImageLoaderInition.imageLoader.displayImage(entities.get(i - 1).getImage(), iv, ImageLoaderInition.options);
            }
            fm.setOnClickListener(this);
            views.add(fm);
        }
        tvTitle.setText(entities.get(0).getTitle());
        vp.setAdapter(new MyPagerAdapter(views));
        vp.setFocusable(true);
        vp.setCurrentItem(1);
        //设置了viewpager的适配器和当前的页面
        currentItem = 1;
        vp.setOnPageChangeListener(new MyOnPageChangeListener());//添加事件侦听
//        startPlay();
    }

    private void startPlay() {
        isAutoPlay = true;//设置自动轮播
        handler.postDelayed(task, 3000);
    }


    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (entities.size() + 1) + 1;
                if (currentItem == 1) {
                    vp.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    vp.setCurrentItem(currentItem);
                    handler.postDelayed(task, 5000);
                }
            } else {
                handler.postDelayed(task, 5000);
            }//自动轮播的线程设置
        }
    };

    class MyOnPageChangeListener implements OnPageChangeListener {
        private int current = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(entities.size(),false);
                    } else if (vp.getCurrentItem() == entities.size() + 1) {
                        vp.setCurrentItem(1,false);
                    }
                    currentItem = vp.getCurrentItem();
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0) {
                tvTitle.setText(entities.get(entities.size() - 1).getTitle());
            } else if (arg0 == views.size() - 1) {
                tvTitle.setText(entities.get(0).getTitle());
            } else {
                tvTitle.setText(entities.get(arg0 - 1).getTitle());
            }
            if (arg0 == 0) {
                current = entities.size() - 1;
            } else if (arg0 == views.size() - 1) {
                current = 0;
            } else {
                current = arg0 - 1;
            }
            dotsGridAdapter.setCurrent(current);

        }

    }

    //点击事件的侦听设置方法以及listener的接口设置
    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void click(View v, ColumnEntity.DataEntity.BannerEntity entity);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            ColumnEntity.DataEntity.BannerEntity entity = entities.get(vp.getCurrentItem() - 1);
            mItemClickListener.click(v, entity);
        }
    }
}
