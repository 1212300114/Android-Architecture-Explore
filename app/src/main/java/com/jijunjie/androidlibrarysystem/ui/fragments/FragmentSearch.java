package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.HistoryListAdapter;
import com.jijunjie.myandroidlib.utils.KeyBoardUtils;
import com.jijunjie.myandroidlib.view.ClearableEditText;

import java.util.ArrayList;

/**
 * Created by jijunjie on 16/3/18.
 */
public class FragmentSearch extends Fragment {

    private TabLayout tabLayout;
    private String[] titles = new String[]{"搜书名", "搜作者"};
    private ListView lvHistory;
    private HistoryListAdapter adapter;
    private ClearableEditText editText;
    private String[] historys;
    private String record;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            record = sharedPreferences.getString("history", "");
            historys = record.split(",");
            Log.e("tag", "onCreate: " + record);
        }
        list = new ArrayList<>();
        if (historys != null) {
            for (String s : historys) {
                Log.e("history tag", s);
                list.add(s);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createFragmentView(inflater);
    }

    private View createFragmentView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.fragment_search, null);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
        lvHistory = (ListView) root.findViewById(R.id.lvHistory);
        View headerView = inflater.inflate(R.layout.history_header, lvHistory, false);
        lvHistory.addHeaderView(headerView);
        adapter = new HistoryListAdapter(getActivity());
        lvHistory.setAdapter(adapter);

//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            String history = "c++" + i;
//            list.add(history);
//        }
//        adapter.setList(list);

        editText = (ClearableEditText) root.findViewById(R.id.etSearch);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("tag key", keyCode + "---" + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtils.closeKeyboard((EditText) v, v.getContext());
                    recordHistory(((EditText) v).getText().toString());
                    return true;
                }
                return false;
            }
        });
        return root;
    }

    private void recordHistory(String input) {
        if (TextUtils.isEmpty(input)) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!TextUtils.isEmpty(record)) {
            record += ",";
            record += input;
        } else {
            record += input;
        }
        editor.putString("history", record);
        editor.apply();
    }

}
