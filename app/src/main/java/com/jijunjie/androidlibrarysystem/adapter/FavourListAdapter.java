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
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import java.util.ArrayList;

/**
 * Created by jijunjie on 16/3/12.
 * the adapter for favour list to create subviews for list view
 */
public class FavourListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Book> books;

    public FavourListAdapter(Context context) {
        this.context = context;
        this.books = new ArrayList<>();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books.clear();
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    public void addMore(ArrayList<Book> books) {
        this.books.addAll(books);
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
        //initialize the view holder
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favour_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBook = (TextView) convertView.findViewById(R.id.tvBook);
            viewHolder.ivBook = (ImageView) convertView.findViewById(R.id.ivBook);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            viewHolder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // get data to view through view holder

        viewHolder.tvBook.setText(books.get(position).getBookName());
        viewHolder.tvClass.setText(books.get(position).getClassName());
        viewHolder.tvAuthor.setText(books.get(position).getBookAuthor());
        DrawableUtils.displayNormalImgOnNet(viewHolder.ivBook, books.get(position).getBookImage().getFileUrl(context));
        // set click event here
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvBook, tvAuthor, tvClass;
        ImageView ivBook;
    }
}
