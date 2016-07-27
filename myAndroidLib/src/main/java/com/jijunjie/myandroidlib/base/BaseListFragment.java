package com.jijunjie.myandroidlib.base;

import android.view.View;
import android.widget.ListView;

import com.jijunjie.myandroidlib.R;

/**
 * @author Gary Ji
 * @description the base class of fragment with a list view
 * @date on 2016/5/11 0011.
 */
public abstract class BaseListFragment extends BaseFragment {
//    @Bind(R.id.listView)
    protected ListView listView;


    @Override
    protected void initViews(View root) {
        initExtraViews();
        listView = (ListView) root.findViewById(R.id.listView);
        initListView();
    }

    /**
     * to init views beside the list view
     */
    protected abstract void initExtraViews();

    /**
     * to init list view
     */
    protected abstract void initListView();

    /**
     * to get data from server
     */
    protected abstract void refreshData();
}
