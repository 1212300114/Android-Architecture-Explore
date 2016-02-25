package com.jijunjie.myandroidlib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jijunjie on 16/2/25.
 * the base fragment of the application
 */
public abstract class BaseFragment extends Fragment{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(toGetLayoutId(),null);
        bindSubViews(rootView);
        return rootView;
    }

    /**
     * Return the content layout id abstract method have to be implement in subclass
     * can't return nothing
     */
    @NonNull
    protected abstract int toGetLayoutId();

    /**
     * bind the subViews in subclass
     * @param view root view
     */

    @NonNull
    protected abstract void bindSubViews(View view);

}
