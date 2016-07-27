package com.jijunjie.myandroidlib.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary Ji
 * @description the base list /grid view adapter of the applicaition
 * @date 2016/6/8 0008.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected ArrayList<T> list;
    protected Context context;

    public BaseListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    /**
     * set list data model
     *
     * @param list the data model
     */

    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    public void addMore(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * get current list data
     *
     * @return current list data
     */
    public ArrayList<T> getList() {
        return list;
    }

    /**
     * clear list data
     */

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
