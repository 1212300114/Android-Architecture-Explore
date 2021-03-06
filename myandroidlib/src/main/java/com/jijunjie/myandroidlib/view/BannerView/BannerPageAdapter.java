package com.jijunjie.myandroidlib.view.BannerView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jijunjie.myandroidlib.R;
import com.jijunjie.myandroidlib.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by jijunjie on 16/2/26.
 * the adapter of the banner viewpager;
 */
public class BannerPageAdapter extends PagerAdapter {
    private int itemID = R.layout.base_banner_item;
    private ArrayList<BaseBannerEntity> bannerEntities;
    //i like to pass context so that i can who who is call this adapter and communicate with it
    private Context context;
    //call back of click event;
    private onItemClickListener onItemClickListener;

    public BannerPageAdapter(Context context) {
        this.bannerEntities = new ArrayList<>();
        this.context = context;
    }

    /**
     * to set the data and refresh the viewpager
     *
     * @param subViewsData the data pass in
     * @param loopEnabled  control the data should be loop or not
     */

    public void setDataWithLoopEnabled(ArrayList<BaseBannerEntity> subViewsData, boolean loopEnabled) {
        if (subViewsData == null) {
            throw new NullPointerException("hey guy what the fuck are you doing with a null data for me !");
        }
        if (loopEnabled) {
            if (subViewsData.size() > 1) {
                // make the new length
                for (int i = 0; i < subViewsData.size() + 2; i++) {
                    if (i == 0) {
                        //add the last data at the first of new list
                        this.bannerEntities.add(subViewsData.get(subViewsData.size() - 1));
                    } else if (i == subViewsData.size() + 1) {
                        //add the first data at the last of new list
                        this.bannerEntities.add(subViewsData.get(0));
                    } else {
                        this.bannerEntities.add(subViewsData.get(i - 1));
                    }
                }
            } else {
                //if only one don't do that thing
                this.bannerEntities = subViewsData;
            }

        } else {
            this.bannerEntities = subViewsData;
        }
        notifyDataSetChanged();
    }

    /**
     * To use custom item layout if needed;
     *
     * @param itemID new item resource id
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bannerEntities.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView itemView = (ImageView) LayoutInflater.from(context).inflate(itemID, container, false);
        itemView.setImageResource(R.drawable.common_images_normal);
        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.base_banner_item) {
                        BannerPageAdapter.this
                                .onItemClickListener.click(v, bannerEntities.get(position), position);
                    }
                }
            });
        }
        container.addView(itemView);

        BaseBannerEntity currentData = bannerEntities.get(position);
        if (!TextUtils.isEmpty(currentData.getImgUrl())) {
            ImageUtils.displayNormalImgOnNet(itemView, currentData.getImgUrl());
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    /**
     * to set click listener
     *
     * @param onItemClickListener callback
     */
    public void setOnItemClickListener(BannerPageAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void click(View view, BaseBannerEntity entity, int position);
    }
}
