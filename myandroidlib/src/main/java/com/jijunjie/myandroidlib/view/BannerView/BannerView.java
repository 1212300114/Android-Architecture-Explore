package com.jijunjie.myandroidlib.view.BannerView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jijunjie.myandroidlib.R;
import com.jijunjie.myandroidlib.utils.ScreenUtils;

import java.util.ArrayList;


/**
 * Created by jijunjie on 16/2/26.
 * custom banner view it's an combination view
 */
public class BannerView extends LinearLayout implements BannerPageAdapter.onItemClickListener, ViewPager.OnPageChangeListener {

    private int defaultPointWidth = ScreenUtils.dp2px(getContext(), 20);
    private boolean loopEnabled = false;
    //data model of banner
    private ArrayList<BaseBannerEntity> bannerEntities;
    //subviews in the banner
    private TextView bannerTitle;
    private ChildViewPager bannerViewPager;
    private BannerPageAdapter pageAdapter;
    private GridView bannerIndicator;
    private BannerIndicatorAdapter indicatorAdapter;
    //callback of click
    private onBannerClickListener onBannerClickListener;
    private Handler mHandler;

    /**
     * Instantiates a new Banner view.
     *
     * @param context the context
     */
    public BannerView(Context context) {
        super(context);
        initModel();
        initView();
    }

    /**
     * Instantiates a new Banner view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initModel();
        initView();
    }

    /**
     * Instantiates a new Banner view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initModel();
        initView();
    }

    /**
     * Instantiates a new Banner view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initModel();
        initView();

    }

    /**
     * to init the adapter
     */
    private void initModel() {
        pageAdapter = new BannerPageAdapter(getContext());
        pageAdapter.setOnItemClickListener(this);
        indicatorAdapter = new BannerIndicatorAdapter(getContext());
    }

    /**
     * to init subviews of the banner;
     */
    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.base_banner_layout, null);
        this.addView(rootView);
        bannerTitle = (TextView) rootView.findViewById(R.id.bannerTitle);
        bannerViewPager = (ChildViewPager) rootView.findViewById(R.id.bannerPager);
        bannerViewPager.setAdapter(pageAdapter);
        bannerViewPager.addOnPageChangeListener(this);
        bannerIndicator = (GridView) rootView.findViewById(R.id.bannerIndicator);
        bannerIndicator.setAdapter(indicatorAdapter);
        bannerTitle = (TextView) rootView.findViewById(R.id.bannerTitle);
    }


    @Override
    public void click(View view, BaseBannerEntity entity, int position) {
        if (this.onBannerClickListener != null) {
            if (loopEnabled) {
                position -= 1;
            }
            this.onBannerClickListener.click(entity, position);
        }
    }

    /**
     * to set the click listener of the banner;
     *
     * @param onBannerClickListener callback
     */
    public void setOnBannerClickListener(BannerView.onBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }


    /**
     * callback of click
     */
    public interface onBannerClickListener {
        /**
         * Click.
         *
         * @param entity   the entity
         * @param position the position
         */
        void click(BaseBannerEntity entity, int position);

    }

    /**
     * set data and
     *
     * @param bannerEntities the data for banner
     * @param loopEnabled    the banner can scroll without bounds
     */
    public void setBannerEntitiesAndLoopEnable(final ArrayList<BaseBannerEntity> bannerEntities, boolean loopEnabled) {
        this.bannerEntities = bannerEntities;
        this.loopEnabled = loopEnabled;
        // set data to view pager adapter
        pageAdapter.setDataWithLoopEnabled(bannerEntities, loopEnabled);
        if (loopEnabled) {
            // move to the second  because this can show what the data look like
            //  data before change  [ a , b , c ]  data after add c [ a , b , c ] a
            bannerViewPager.setCurrentItem(1);
        }
        // set count to indicator adapter
        indicatorAdapter.setCount(bannerEntities.size());
        // set column number
        bannerIndicator.setNumColumns(bannerEntities.size());
        // set new width to the gridView
        bannerIndicator.getLayoutParams().width = defaultPointWidth * bannerEntities.size();
        // request layout toto make new layout
        bannerIndicator.requestLayout();
        bannerTitle.setText(bannerEntities.get(0).getTitle());
    }


    /**
     * Sets auto play.
     */
    public void setAutoPlay() {
        if (loopEnabled) {
//            currentItem = 1;
        }
        postDelayed(playTask, 2000);
    }

    /**
     * The Current item.
     */
    int currentItem = 0;
    /**
     * thread for auto play
     */
    private Runnable playTask = new Runnable() {
        @Override
        public void run() {
            currentItem++;
            currentItem = currentItem % bannerEntities.size();
            Log.e("current item = ", "current = " + currentItem);
            if (loopEnabled) {

                bannerViewPager.setCurrentItem(currentItem + 1);
            } else {
                bannerViewPager.setCurrentItem(currentItem);
            }
            postDelayed(playTask, 2000);
        }
    };

    /**
     * The Current position.
     */
    int currentPosition = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.e("pageScroll", "current position" + position + " position offset = " + position
//                + " offset pixels = " + positionOffsetPixels);

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (loopEnabled) {
            position = generateCorrectPosition(position);
//            bannerViewPager.setCurrentItem(position);
        }
        bannerTitle.setText(bannerEntities.get(position).getTitle());
        indicatorAdapter.setCurrent(position);
        Log.e("pageSelected", "selected position" + position);
    }

    private int generateCorrectPosition(int position) {
        if (position == bannerEntities.size() + 1) {
//            bannerViewPager.setCurrentItem(1, false);
            position = 0;
        } else if (position == 0) {
//            bannerViewPager.setCurrentItem(bannerEntities.size(), false);
            position = bannerEntities.size() - 1;
        } else {
            position = position - 1;
        }
        return position;
    }

    // do scroll inside here
    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e("pageChange", "current state = " + state + "currentPosition" + currentPosition);
        if (state == ViewPager.SCROLL_STATE_IDLE && loopEnabled) {
            //when it finish animation move to the correct position
            if (currentPosition == 0) {
                bannerViewPager.setCurrentItem(bannerEntities.size(), false);
            } else if (currentPosition == bannerEntities.size() + 1) {
                bannerViewPager.setCurrentItem(1, false);
            }
        }
    }
}
