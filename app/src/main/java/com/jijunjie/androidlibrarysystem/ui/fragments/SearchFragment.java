package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.FavourListAdapter;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.myandroidlib.utils.ScreenUtils;
import com.jijunjie.myandroidlib.view.BannerView.BannerView;
import com.jijunjie.myandroidlib.view.BannerView.BaseBannerEntity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * the Search fragment show some book in favourite
 * Created by jijunjie on 16/3/2.
 */
public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private BannerView banner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvBooks;
    private FavourListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragment log", "create");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d("fragment log", "attach");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragment log", "create view");
        return createFragmentView(inflater);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("fragment log", "activity create");
        super.onActivityCreated(savedInstanceState);

    }


    private View createFragmentView(LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.fragment_search, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccentDark,
                R.color.colorPrimary, R.color.colorPrimaryDark);
        //需要post 才会执行动画
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.startNestedScroll(View.SCROLL_AXIS_VERTICAL);
        swipeRefreshLayout.setOnRefreshListener(this);

        //需要手动调一次回调
        this.onRefresh();

        lvBooks = (ListView) rootView.findViewById(R.id.lvBooks);
        lvBooks.setVisibility(View.GONE);
        adapter = new FavourListAdapter(getActivity());
        lvBooks.setAdapter(adapter);
        banner = (BannerView) inflater.inflate(R.layout.banner_layout, lvBooks, false);
        banner.getLayoutParams().height = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.6);
        banner.invalidate();
        lvBooks.addHeaderView(banner);
        return rootView;
    }

    /**
     * query data from cloud database
     */
    private void queryData() {
        final BmobQuery<Book> bookQuery = new BmobQuery<>();
        bookQuery.findObjects(getActivity(), new FindListener<Book>() {
            @Override
            public void onSuccess(List<Book> list) {
                Log.d("success", "success");
                ArrayList<BaseBannerEntity> entities = new ArrayList<BaseBannerEntity>();
                for (Book book : list) {
                    String json = new Gson().toJson(book);
                    Log.e("book json String", json);
                    if (book.getClassName().equals("管理")) {
                        BaseBannerEntity entity = new BaseBannerEntity();
                        entity.setImgUrl(book.getBookImage().getFileUrl(getActivity()));
                        entity.setTitle(book.getBookName());
                        entities.add(entity);
                    }
                }
                lvBooks.setVisibility(View.VISIBLE);
                banner.setBannerEntitiesAndLoopEnable(entities, true);
                adapter.setBooks((ArrayList<Book>) list);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s) {
                Log.d("error", "error = " + s);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.d("fragment log", "fragment destory");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("fragment log", "save instance");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("fragment log", "view restore");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.d("fragment log", "destroy view");
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d("fragment log", "hidden change");
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onRefresh() {
        queryData();
    }
}
