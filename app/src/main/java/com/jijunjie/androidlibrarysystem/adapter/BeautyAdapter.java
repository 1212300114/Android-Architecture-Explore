package com.jijunjie.androidlibrarysystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.Results;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 16/2/16 23:05
 */
public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.GirlyViewHolder> {

    private Context context;
    private List<Results> list = new ArrayList<>();

    public void setList(List<Results> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<Results> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public BeautyAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public GirlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GirlyViewHolder holder = new GirlyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_girly, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(GirlyViewHolder holder, final int position) {
//        Glide.with(context)
//                .load(list.get(position).getUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.imageView);
        DrawableUtils.displayNormalImgOnNet(holder.imageView, list.get(position).getUrl());
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, ImageActivity.class);
//                intent.putExtra("url",list.get(position).getUrl());
//                intent.putExtra("desc",list.get(position).getDesc());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class GirlyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GirlyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_girl);
        }
    }

}