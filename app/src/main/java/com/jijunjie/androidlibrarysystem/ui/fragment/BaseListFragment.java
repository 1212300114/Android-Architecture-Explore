package com.jijunjie.androidlibrarysystem.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * the book list fragment for all class
 * in this project i will try to use google's
 * design to construct .
 * Created by jijunjie on 16/1/23.
 */
public class BaseListFragment extends Fragment {
    // params  tags


    //views
    private RecyclerView recyclerView;


    //google suggest us to use set arguments to
    public BaseListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
