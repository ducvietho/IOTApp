package com.example.ducvietho.iotapp.screen.floor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.util.OnCLickItem;
import com.example.ducvietho.iotapp.util.OnLongClickItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 2/3/2018.
 */

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private List<Equipment> mList;
    private OnCLickItem<Equipment> mOnCLick;
    private OnLongClickItem<Equipment> mLongClick;

    public EquipmentAdapter(List<Equipment> list, OnCLickItem<Equipment> onCLick, OnLongClickItem<Equipment> longClick) {
        mList = list;
        mOnCLick = onCLick;
        mLongClick = longClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equip, parent, false);
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
        @BindView(R.id.img_equip)
        ImageView mImageView;
        @BindView(R.id.tv_equip)
        TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Equipment equipment) {
            mName.setText(equipment.getName());
            if (equipment.getState() == 0) {
                Picasso.with(itemView.getContext()).load(equipment.getIconOff()).resize(300,300).into
                        (mImageView);
            } else {
                Picasso.with(itemView.getContext()).load(equipment.getIconOn()).resize(300,300).into(mImageView);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnCLick.onClick(equipment);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClick.onLongClick(equipment);
                    return true;
                }
            });
        }
    }
}
