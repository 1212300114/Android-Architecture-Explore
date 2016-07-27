package com.jijunjie.myandroidlib.base;

import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.jijunjie.myandroidlib.R;


/**
 * @author Gary Ji
 * @description the fragment with a pull refresh enabled list view
 * to config if load more enable use {@code setLoadMoreEnable()} at {@code getArgAndConfig} method
 * @date 2016/5/11 0011.
 */
public abstract class BasePtrListFragment extends BaseListFragment {
//    @Bind(R.id.pull_refresh)  butter knife need final var so can't use in lib
    protected PtrClassicFrameLayout ptrLayout;
    private boolean loadMoreEnable = false;
    protected int pageSize = 10;
    protected int pageNo = 1;

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        ptrLayout = (PtrClassicFrameLayout) root.findViewById(R.id.pull_refresh);
        initPtrLayout();
    }

    /**
     * init ptr layout
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
        if (ptrLayout != null) ptrLayout.setLoadMoreEnable(loadMoreEnable);
    }

    /**
     * call back of load more event
     */

    protected abstract void loadMoreData();
}
