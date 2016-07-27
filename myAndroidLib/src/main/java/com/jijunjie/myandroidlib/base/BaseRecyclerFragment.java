package com.jijunjie.myandroidlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.jijunjie.myandroidlib.utils.LogUtils;
import com.lansen.oneforgem.R;

import butterknife.Bind;

/**
 * @author Gary Ji
 * @description the base class of fragment with a list view which can pull to refresh and load more with config
 * @date 2016/5/11 0011.
 */
public abstract class BaseRecyclerFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    public RecyclerView recyclerView;
    @Bind(R.id.pull_refresh)
    public PtrClassicFrameLayout ptrLayout;
    private boolean loadMoreEnable = false;
    protected int pageSize = 10;
    protected int pageNo = 1;

    @Override
    protected void initViews(View root) {
        initRecyclerView();
        initExtraViews();
        initPtrLayout();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("activity create");

    }

    /**
     * to init recycler view include set adapter and layout manager ,and set event callback of item
     */

    protected abstract void initRecyclerView();

    /**
     * to init views beside recycler view
     */
    protected abstract void initExtraViews();

    /**
     * to init ptr layout
     */
    protected void initPtrLayout() {
        ptrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();

            }
        });
        ptrLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadMoreData();
            }
        });
        ptrLayout.setLoadMoreEnable(loadMoreEnable);
        ptrLayout.setLoadingMinTime(1000);
        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ptrLayout != null)
                    ptrLayout.autoRefresh();
            }
        }, 150);
    }

    /**
     * config wether the list cab load more or not
     *
     * @param loadMoreEnable true to load more
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
    }

    /**
     * callback of refresh
     */
    protected abstract void refreshData();

    /**
     * callback of load more event
     */

    protected abstract void loadMoreData();
}
