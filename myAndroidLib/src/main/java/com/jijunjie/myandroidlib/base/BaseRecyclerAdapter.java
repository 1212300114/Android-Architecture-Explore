package com.jijunjie.myandroidlib.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> the item model type
 * @author Gary Ji
 * @description the base recycler view adapter
 * @date 2016/6/8 0008.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected ArrayList<T> list;
    protected Context context;
    protected BaseOnItemEventListener<T> onItemEventListener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    /**
     * reset  list data
     * 1
     *
     * @param list the list data
     */

    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

    /**
     * to add more data for the list
     *
     * @param list the more list data
     */
    public void addMore(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * clear all list data of recycler view
     */
    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    /**
     * remove single item of the recycler view
     *
     * @param position the position
     */
    public void removeItem(int position) {
        if (position < list.size()) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ArrayList<T> getList() {
        return list;
    }

    /**
     * to get list item data model
     *
     * @param position
     */

    public T getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * the item click event callback
     *
     * @param <T> the item model
     */
    public interface BaseOnItemEventListener<T> {
        void onItemClick(View view, int position, T t);
    }

    /**
     * set event callback
     *
     * @param onItemEventListener event callback
     */

    public void setOnItemEventListener(BaseOnItemEventListener<T> onItemEventListener) {
        this.onItemEventListener = onItemEventListener;
    }
}
