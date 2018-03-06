package com.example.ducvietho.iotapp.screen.group;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.util.OnClickItemGroup;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Group> mList;
    private OnLongClickItem<Group> mLongClickItem;
    private OnClickItemGroup mOnClickItemGroup;

    public GroupAdapter(List<Group> list, OnLongClickItem<Group> longClickItem, OnClickItemGroup onClickItemGroup) {
        mList = list;
        mLongClickItem = longClickItem;
        mOnClickItemGroup = onClickItemGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_group)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Group group) {
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/UTM Avo.ttf");
            mTextView.setText(group.getName());
            mTextView.setTypeface(font);
            if(group.getState()==0){
                mImageView.setImageResource(R.drawable.ic_ac_off);
                //Picasso.with(itemView.getContext()).load(R.drawable.ic_ac_off).resize(100,100).into(mImageView);
            }
            else {
                mImageView.setImageResource(R.drawable.ic_ac);
                //Picasso.with(itemView.getContext()).load(R.drawable.ic_ac).resize(100,100).into(mImageView);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickItemGroup.onClick(group,mImageView,mTextView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickItem.onLongClick(group);
                    return true;
                }
            });
        }
    }
}
