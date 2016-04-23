package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.FavourListAdapter;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.myandroidlib.utils.ScreenUtils;
import com.jijunjie.myandroidlib.view.BannerView.BannerView;
import com.jijunjie.myandroidlib.view.BannerView.BaseBannerEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * the Search fragment show some book in favourite
 * Created by jijunjie on 16/3/2.
 */
public class FragmentFavor extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private BannerView banner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lvBooks;
    private FavourListAdapter adapter;
    private Handler handler = new MyHandler(this);

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

    @Override
    public void onResume() {
        super.onResume();
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
                FragmentFavor.this.onRefresh();
            }
        }, 1000);
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
        View rootView = inflater.inflate(R.layout.fragment_favor, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccentDark,
                R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.startNestedScroll(View.SCROLL_AXIS_VERTICAL);
        swipeRefreshLayout.setOnRefreshListener(this);


        lvBooks = (ListView) rootView.findViewById(R.id.lvBooks);
        lvBooks.setVisibility(View.GONE);
        adapter = new FavourListAdapter(getActivity());

        banner = (BannerView) inflater.inflate(R.layout.banner_layout, lvBooks, false);
        banner.getLayoutParams().height = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.6);
        banner.invalidate();
        lvBooks.addHeaderView(banner);
        lvBooks.setAdapter(adapter);
        lvBooks.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    queryData(false);
                Log.e("scroll tag", firstVisibleItem + "---" + visibleItemCount + "---" + totalItemCount);
                if (firstVisibleItem + visibleItemCount > totalItemCount - 4 && hasMore && !isLoading) {

                    queryData(false);
                }
            }
        });
        return rootView;
    }

    /**
     * query data from cloud database
     */

    int pageCount = 10;
    int pageIndex = 0;
    boolean hasMore = true;
    boolean isLoading = false;

    private void queryData(final boolean isRefresh) {
        final BmobQuery<Book> bookQuery = new BmobQuery<>();
        bookQuery.setLimit(pageCount);
        if (isRefresh) {
            pageIndex = 0;
            hasMore = true;
        } else {
            if (!isLoading) {
                pageIndex++;
            }
            isLoading = true;
        }
        bookQuery.order("createdAt");
        bookQuery.setSkip(pageIndex * pageCount);
        Log.e("scroll tag", "page index = " + pageIndex);
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = getActivity() != null && bookQuery.hasCachedResult(getActivity(), Book.class);
        if (isCache) {
            bookQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
            // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        } else {
            bookQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
            // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        bookQuery.findObjects(getActivity(), new FindListener<Book>() {
            @Override
            public void onSuccess(List<Book> list) {
                Log.e("success", "success and size = " + list.size() + "   and page index = " + pageIndex);
                ArrayList<BaseBannerEntity> entities = new ArrayList<>();
                if (list.size() == 0) {
                    hasMore = false;
                    Toast.makeText(getActivity(), "没有更多啦！", Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
                for (Book book : list) {
                    String json = new Gson().toJson(book);
                    Log.e("book json String", json);
                    if (book.getClassName().equals("教育")) {
                        BaseBannerEntity entity = new BaseBannerEntity();
                        entity.setImgUrl(book.getBookImage().getFileUrl(getActivity()));
                        entity.setTitle(book.getBookName());
                        entities.add(entity);
                    }
                }
                Log.e("success", list.size() + "" + list.getClass().toString() + "------" + entities.size());
                lvBooks.setVisibility(View.VISIBLE);
                if (isRefresh) {
                    if (entities.size() != 0)
                        banner.setBannerEntitiesAndLoopEnable(entities, true);
                    adapter.setBooks((ArrayList<Book>) list);
                } else {
                    adapter.addMore((ArrayList<Book>) list);
                }
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
        queryData(true);
    }

    static class MyHandler extends Handler {
        private final WeakReference<FragmentFavor> weakReference;

        public MyHandler(FragmentFavor fragmentFavor) {
            this.weakReference = new WeakReference<>(fragmentFavor);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
