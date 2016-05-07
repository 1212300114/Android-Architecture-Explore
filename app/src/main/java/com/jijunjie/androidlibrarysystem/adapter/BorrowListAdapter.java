package com.jijunjie.androidlibrarysystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.androidlibrarysystem.model.BorrowRecord;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jijunjie on 16/5/6.
 */
public class BorrowListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> books;
    private ArrayList<BorrowRecord> records;

    public BorrowListAdapter(Context context) {
        this.context = context;
        this.records = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public void setBooks(List<Book> books, List<BorrowRecord> records) {
        this.books.clear();
        this.books.addAll(books);
        this.records.clear();
        this.records.addAll(records);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_borrow_reocrd, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBookName = (TextView) convertView.findViewById(R.id.tvBook);
            viewHolder.tvBorrowTime = (TextView) convertView.findViewById(R.id.tvBorrowTime);
            viewHolder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            viewHolder.ivBook = (ImageView) convertView.findViewById(R.id.ivBook);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DrawableUtils.displayNormalImgOnNet(viewHolder.ivBook, books.get(position)
                .getBookImage().getFileUrl(context));
        viewHolder.tvClass.setText(books.get(position).getClassName());
        viewHolder.tvBookName.setText(books.get(position).getBookName());
        viewHolder.tvBorrowTime.setText(records.get(position).getCreatedAt());
        return convertView;
    }

    class ViewHolder {
        TextView tvBookName, tvBorrowTime, tvClass;
        ImageView ivBook;
    }
}
