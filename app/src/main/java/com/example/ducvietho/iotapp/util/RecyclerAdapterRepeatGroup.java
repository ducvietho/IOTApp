package com.example.ducvietho.iotapp.util;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.DayRepeat;
import com.example.ducvietho.iotapp.data.model.Group;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 3/5/2018.
 */

public class RecyclerAdapterRepeatGroup extends RecyclerView.Adapter<RecyclerAdapterRepeatGroup.ViewHolder> {
    private List<DayRepeat> mList;
    private SaveDayRepeat<Group> mDayRepeat;
    private Group object;

    public RecyclerAdapterRepeatGroup(List<DayRepeat> list, SaveDayRepeat<Group> dayRepeat, Group object) {
        mList = list;
        mDayRepeat = dayRepeat;
        this.object = object;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repeat, parent, false);
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
        @BindView(R.id.tv_day)
        TextView mDay;
        @BindView(R.id.cb_repeat)
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final DayRepeat dayRepeat) {
            Typeface tf = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/UTM Penumbra.ttf");
            mDay.setTypeface(tf);
            mDay.setText(dayRepeat.getDay());
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mDayRepeat.saveDayRepeat(object,dayRepeat.getId());
                }
            });
        }
    }
}
