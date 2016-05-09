package com.jijunjie.androidlibrarysystem.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.myandroidlib.utils.ScreenUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jijunjie on 16/5/8.
 */
public class FavorSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> classNames;
    private int[] imageRes = {R.drawable.icon_book_first, R.drawable.icon_book_second,
            R.drawable.icon_book_third, R.drawable.icon_book_forth, R.drawable.icon_book_fifth};
    private Context context;
    private ArrayList<Boolean> states;

    public FavorSelectAdapter(Context context) {
        this.context = context;
        this.classNames = new ArrayList<>();
        this.states = new ArrayList<>();
    }

    public void setClassNames(ArrayList<String> classNames) {
        this.classNames = classNames;
        for (String ignored : classNames) {
            states.add(false);
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedClassNames() {
        ArrayList<String> selectedNames = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i)) {
                selectedNames.add(classNames.get(i));
            }
        }
        return selectedNames;
    }

    public void setSelectPosition(int index) {
        this.states.set(index, true);
        notifyItemChanged(index);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_favor_select_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        int margin = ScreenUtils.dp2px(context, 10);
        layoutParams.setMargins(margin, margin, margin, margin);
        return new FavorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        FavorViewHolder viewHolder = (FavorViewHolder) holder;
        final Boolean state = states.get(pos);
        if (state) {
            viewHolder.tvClass.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.itemView.setBackgroundResource(R.drawable.selected_item_background);
        } else {
            viewHolder.tvClass.setTextColor(context.getResources().getColor(android.R.color.primary_text_light));
            viewHolder.itemView.setBackgroundResource(R.drawable.gray_stroke_background);
        }
        viewHolder.ivBook.setImageResource(imageRes[pos % 5]);
        viewHolder.tvClass.setText(classNames.get(pos));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                states.set(pos, !state);
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classNames.size();
    }

    class FavorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivIcon)
        ImageView ivBook;
        @Bind(R.id.tvClass)
        TextView tvClass;

        public FavorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
