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
        this.books = books;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favour_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvBook = (TextView) convertView.findViewById(R.id.tvBook);
            viewHolder.ivBook = (ImageView) convertView.findViewById(R.id.ivBook);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            viewHolder.tvPress = (TextView) convertView.findViewById(R.id.tvPress);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //get data to view through view holder

        viewHolder.tvBook.setText(books.get(position).getBookName());
        viewHolder.tvPress.setText(books.get(position).getBookPress());
        viewHolder.tvAuthor.setText(books.get(position).getBookAuthor());
        DrawableUtils.displayNormalImgOnNet(viewHolder.ivBook, books.get(position).getBookImage().getFileUrl(context));

        return convertView;
    }

    class ViewHolder {
        TextView tvBook, tvAuthor, tvPress;
        ImageView ivBook;
    }
}