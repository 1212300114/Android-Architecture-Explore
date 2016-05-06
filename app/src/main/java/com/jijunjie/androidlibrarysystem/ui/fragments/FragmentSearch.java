package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.HistoryListAdapter;
import com.jijunjie.androidlibrarysystem.ui.activity.SearchResultActivity;
import com.jijunjie.myandroidlib.utils.KeyBoardUtils;
import com.jijunjie.myandroidlib.utils.SharedPreferenceUtils;
import com.jijunjie.myandroidlib.view.ClearableEditText;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by jijunjie on 16/3/18.
 * the search scene
 */
public class FragmentSearch extends Fragment implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    /**
     * class args
     */
    private static final String TAG = "searchTag";

    /**
     * views
     */

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    private String[] titles = new String[]{"搜书名", "搜作者"};
    @Bind(R.id.lvHistory)
    ListView lvHistory;
    @Bind(R.id.etSearch)
    ClearableEditText editText;
    // view inside the header
    TextView tvClear;

    private HistoryListAdapter adapter;
    /**
     * datas
     */
    private String[] historys;
    private String record;
    private ArrayList<String> list;
    private boolean isBookName = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        record = (String) SharedPreferenceUtils.get(getActivity(), "record", "");
        Log.d(TAG, "onCreate:  the record = " + record);
        list = new ArrayList<>();
        if (!TextUtils.isEmpty(record)) {
            historys = record.split(",");
            Log.e("record", historys.length + "");
        }
        if (historys != null) {
            Collections.addAll(list, historys);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return createFragmentView(inflater, container);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    /**
     * the click event
     *
     * @param v the clicked view
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvClear) {
            this.list.clear();
            record = "";
            SharedPreferenceUtils.put(getActivity(), "record", "");
            adapter.clearData();
        }

    }

    @OnItemClick(R.id.lvHistory)
    public void clickHistory(AdapterView<?> parent, View item, int position, long id) {
        if (position > 0) {
            Log.e("position", "" + adapter.getItem(position - 1));
        }
    }

    private View createFragmentView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);
        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        View headerView = inflater.inflate(R.layout.history_header, lvHistory, false);

        tvClear = (TextView) headerView.findViewById(R.id.tvClear);
        tvClear.setOnClickListener(this);

        lvHistory.addHeaderView(headerView);
        adapter = new HistoryListAdapter(getActivity());
        lvHistory.setAdapter(adapter);
        adapter.setList(list);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    editText.setText((String) adapter.getItem(position - 1));
                    storeHistory((String) adapter.getItem(position - 1));
                }
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("tag key", keyCode + "---" + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == MotionEvent.ACTION_UP) {
                    KeyBoardUtils.closeKeyboard((EditText) v, v.getContext());
                    String input = editText.getText().toString().trim();
                    Log.e("current input", "input = " + input);
                    storeHistory(input);
                    return true;
                }
                return false;
            }
        });

        tabLayout.setOnTabSelectedListener(this);
        return root;
    }


    private void storeHistory(String input) {
        if (!TextUtils.isEmpty(input)) {
            handleSearch(input);
            if (list.contains(input)) {
                int position = list.indexOf(input);
                if (position > -1) {
                    String firstObject = list.get(0);
                    list.set(0, input);
                    list.set(position, firstObject);
                } else {
                    throw new IllegalArgumentException("the position lose");
                }
                return;
            }
            if (TextUtils.isEmpty(record)) {
                record += input;
            } else {
                record += "," + input;
            }
            Log.e("record", "changed record = " + record);

            list.add(input);

            adapter.setList(list);
            SharedPreferenceUtils.put(getActivity(), "record", record);
        } else {
            Toast.makeText(getActivity(), "请输入书籍信息", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSearch(String input) {

        startActivity(new Intent(getActivity(), SearchResultActivity.class)
                .putExtra("input", input)
                .putExtra("isBookName", isBookName));
    }

    /**
     * callback methods of tab select change
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        isBookName = tab.getPosition() == 0;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
