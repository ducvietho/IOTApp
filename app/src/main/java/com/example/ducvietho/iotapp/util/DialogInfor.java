package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 2/28/2018.
 */

public class DialogInfor {
    @BindView(R.id.tv_info)
    TextView mInfo;
    @BindView(R.id.tv_house)
    TextView mHouse;
    @BindView(R.id.tv_name_house)
    TextView mNameHouse;
    @BindView(R.id.tv_mac)
    TextView mMac;
    @BindView(R.id.tv_mac_id)
    TextView mMacId;
    @BindView(R.id.tv_state)
    TextView mState;
    @BindView(R.id.tv_state_content)
    TextView mStateContent;
    @BindView(R.id.tv_version)
    TextView mVersion;
    @BindView(R.id.tv_version_content)
    TextView mVersionContent;
    @BindView(R.id.tv_website)
    TextView mWebsite;
    @BindView(R.id.tv_web)
    TextView mWeb;
    @BindView(R.id.tv_feedback)
    TextView mFeedback;
    @BindView(R.id.tv_gmail)
    TextView mGmail;
    @BindView(R.id.img_back)
    ImageView mView;
    @BindView(R.id.layout_back)
    RelativeLayout mLayout;
    private Context mContext;

    public DialogInfor(Context context) {
        mContext = context;
    }
    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_infor);
        ButterKnife.bind(this,dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM Penumbra.ttf");
        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM PenumbraBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM Avo.ttf");
        mInfo.setTypeface(tf1);
        mHouse.setTypeface(tf);
        mMac.setTypeface(tf);
        mState.setTypeface(tf);
        mVersion.setTypeface(tf);
        mWebsite.setTypeface(tf);
        mFeedback.setTypeface(tf);
        mNameHouse.setTypeface(tf2);
        mMacId.setTypeface(tf2);
        mStateContent.setTypeface(tf2);
        mWeb.setTypeface(tf2);
        mVersionContent.setTypeface(tf2);
        mGmail.setTypeface(tf2);
        SharedPreferences prefHouse = mContext.getSharedPreferences(Constant.PREFS_NAME_HOUSE, MODE_PRIVATE);
        String nameHouse = prefHouse.getString(Constant.EXTRA_NAME_HOUSE, null);
        mNameHouse.setText(nameHouse);
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
