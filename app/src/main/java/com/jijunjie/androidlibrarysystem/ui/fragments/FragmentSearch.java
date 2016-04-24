package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.HistoryListAdapter;
import com.jijunjie.myandroidlib.utils.KeyBoardUtils;
import com.jijunjie.myandroidlib.utils.SharedPreferenceUtils;
import com.jijunjie.myandroidlib.view.ClearableEditText;

import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        record = (String) SharedPreferenceUtils.get(getActivity(), "record", "");
        list = new ArrayList<>();
        if (!TextUtils.isEmpty(record)) {
            historys = record.split(",");
            Log.e("record", record);
        }
        if (historys != null) {
            Collections.addAll(list, historys);
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
        adapter.setList(list);

        editText = (ClearableEditText) root.findViewById(R.id.etSearch);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("tag key", keyCode + "---" + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == MotionEvent.ACTION_UP) {
                    KeyBoardUtils.closeKeyboard((EditText) v, v.getContext());
                    String input = editText.getText().toString().trim();
                    Log.e("current input", "input = " + input);
                    recordHistory(input);
                    return true;
                }
                return false;
            }
        });
        return root;
    }

    private void recordHistory(String input) {
        if (!TextUtils.isEmpty(input)) {
            if (list.contains(input))
                return;
            if (TextUtils.isEmpty(record)) {
                record += input;
            } else {
                record += "," + input;
            }
            list.add(input);

            adapter.setList(list);
            SharedPreferenceUtils.put(getActivity(), "record", record);
        } else {
            Toast.makeText(getActivity(), "请输入书籍信息", Toast.LENGTH_SHORT).show();
        }
    }

}
