package com.jijunjie.myandroidlib.view.BannerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jijunjie.myandroidlib.R;

/**
 * Created by jijunjie on 16/2/26.
 * The indicator gridView adapter
 */
public class BannerIndicatorAdapter extends BaseAdapter {

    private Context context;
    private int count = 0;
    private int current = 0;

    public BannerIndicatorAdapter(Context context) {
        this.context = context;
    }

    /**
     * set point and refresh
     *
     * @param count point count
     */
    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    /**
     * set current point
     *
     * @param current current position of the pager;
     */
    public void setCurrent(int current) {
        this.current = current;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.base_banner_point_item, null);
            viewHolder = new ViewHolder();
            viewHolder.pointView = (ImageView) convertView.findViewById(R.id.base_banner_point);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (current == position) {
            viewHolder.pointView.setImageResource(R.drawable.common_point_select);
        } else {
            viewHolder.pointView.setImageResource(R.drawable.common_point_nomal);
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView pointView;
    }
}
