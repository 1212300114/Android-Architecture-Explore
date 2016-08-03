package com.jijunjie.myandroidlib.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * @author Gary Ji
 * @date on 16/2/25.
 * @description the base fragment  to config butter knife and okHttp
 */
public abstract class BaseFragment extends Fragment {


    public BaseFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getArgAndConfig();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getRootLayoutResId(), container, false);
        ButterKnife.bind(this, rootView);
        initViews(rootView);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroyView();
    }

    /**
     * to config the fragment and get argument inside on create
     */

    protected abstract void getArgAndConfig();

    /**
     * Return the content layout id abstract method have to be implement in subclass
     * can't return nothing
     */

    @NonNull
    @LayoutRes
    protected abstract int getRootLayoutResId();

    /**
     * to init views need to be set up in code
     */

    protected abstract void initViews(View root);



    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
