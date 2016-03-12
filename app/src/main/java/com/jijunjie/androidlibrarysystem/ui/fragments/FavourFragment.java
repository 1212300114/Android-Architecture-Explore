package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jijunjie.androidlibrarysystem.R;

/**
 * to present a list view for user with user's favourite books
 * Created by jijunjie on 16/3/12.
 */
public class FavourFragment extends Fragment {

    private ListView lvFavour;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createFragmentView(inflater);
    }

    private View createFragmentView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.fragment_favour, null);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        lvFavour = (ListView) root.findViewById(R.id.lvFavour);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
