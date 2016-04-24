package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.BeautyAdapter;
import com.jijunjie.androidlibrarysystem.helper.Constants;
import com.jijunjie.androidlibrarysystem.model.DataResults;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * Created by jijunjie on 16/3/25.
 * the beauty girls fragment show girl's photo in Staggered style
 */
public class FragmentBeauty extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private BeautyAdapter adapter;
    private MyHandler handler = new MyHandler(this);
    private int currentPage = 1;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createFragmentView(inflater, container);
    }

    private View createFragmentView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_beauty, container, false);
        ButterKnife.bind(this, root);
        adapter = new BeautyAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int[] lastVisibleItem = layoutManager.findLastVisibleItemPositions(null);
                int totalItem = layoutManager.getItemCount();
                Log.e("tag position", lastVisibleItem[1] + "total = " + totalItem);
                if (totalItem - lastVisibleItem[1] < 6) {
                    if (!isLoading) {
                        isLoading = true;
                        addMoreData();
                        Log.d("tag", "start load ");
                    }

                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccentDark,
                R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //需要手动调一次回调,延时调 ui 效果好一些
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentBeauty.this.onRefresh();
            }
        }, 1000);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    //auto add more data when scrolling  to end
    private void addMoreData() {
        currentPage++;
        Log.e("page tag", "page = " + currentPage);
        OkHttpUtils.get().url(Constants.beautyUrl + currentPage).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                isLoading = false;
                Toast.makeText(getActivity(), "error data get fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", response);
                DataResults dataResults = new Gson().fromJson(response, DataResults.class);
                isLoading = false;
                if (!dataResults.isError()) {
                    adapter.addMore(dataResults.getResults());

                    Log.e("tag", "finish load");
                } else {
                    Toast.makeText(getActivity(), "no more", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pullInData();
    }

    //pull in data when refresh
    private void pullInData() {
        currentPage = 1;
        OkHttpUtils.get().url(Constants.beautyUrl + currentPage).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("tag", "error" + call.toString() + e.toString());
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "error data get fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                DataResults dataResults = new Gson().fromJson(response, DataResults.class);
                adapter.setList(dataResults.getResults());
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    static class MyHandler extends Handler {
        private final WeakReference<FragmentBeauty> weakReference;

        public MyHandler(FragmentBeauty fragmentBeauty) {
            this.weakReference = new WeakReference<>(fragmentBeauty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
