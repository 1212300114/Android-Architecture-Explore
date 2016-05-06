package com.jijunjie.androidlibrarysystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jijunjie.androidlibrarysystem.R;

import java.util.ArrayList;

/**
 * Created by jijunjie on 16/3/25.
 */
public class HistoryListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;


    public HistoryListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setList(ArrayList<String> list) {
        if (list == null)
            return;
        this.list.clear();
        for (int i = list.size() - 1; i >= 0; i--) {
            this.list.add(list.get(i));
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addOne(String item) {
        this.list.add(item);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvHistory = (TextView) convertView.findViewById(R.id.tvHistory);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvHistory.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvHistory;
    }


}
