package com.example.ducvietho.iotapp.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.screen.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 3/1/2018.
 */

public class DialogSetting {
    @BindView(R.id.tv_setting)
    TextView mSetting;
    @BindView(R.id.tv_house)
    TextView mHouse;
    @BindView(R.id.ed_house)
    EditText mEdHouse;
    @BindView(R.id.tv_lan)
    TextView mLan;
    @BindView(R.id.ed_lan)
    EditText mEdLan;
    @BindView(R.id.tv_internet)
    TextView mInternet;
    @BindView(R.id.ed_internet)
    EditText mEdInternet;
    @BindView(R.id.tv_domain)
    TextView mDomain;
    @BindView(R.id.ed_domain)
    EditText mEdDomain;
    @BindView(R.id.tv_complete)
    TextView mComplete;
    @BindView(R.id.img_back)
    ImageView mView;
    @BindView(R.id.layout_back)
    RelativeLayout mLayout;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_edit)
    TextView mEdit;
    @BindView(R.id.layput_edit)
    RelativeLayout mLayoutEdit;
    private Context mContext;

    public DialogSetting(Context context) {
        mContext = context;
    }
    public void showDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting);
        ButterKnife.bind(this,dialog);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM Penumbra.ttf");
        Typeface tf1 = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM PenumbraBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),"fonts/UTM Avo.ttf");
        mSetting.setTypeface(tf1);
        mComplete.setTypeface(tf);
        mHouse.setTypeface(tf);
        mLan.setTypeface(tf);
        mInternet.setTypeface(tf);
        mDomain.setTypeface(tf);
        mEdDomain.setTypeface(tf);
        mEdHouse.setTypeface(tf);
        mEdInternet.setTypeface(tf);
        mEdLan.setTypeface(tf);
        mName.setTypeface(tf);
        mEdit.setTypeface(tf);
        SharedPreferences prefHouse = mContext.getSharedPreferences(Constant.PREFS_NAME_HOUSE, MODE_PRIVATE);
        String nameHouse = prefHouse.getString(Constant.EXTRA_NAME_HOUSE, null);
        mEdHouse.setText(nameHouse);
        SharedPreferences prefLan = mContext.getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
        String lan = prefLan.getString(Constant.EXTRA_LAN, null);
        mEdLan.setText(lan);
        SharedPreferences prefInternet = mContext.getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = prefInternet.getString(Constant.EXTRA_INTERNET, null);
        mEdInternet.setText(internet);
        SharedPreferences prefDomain = mContext.getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
        String domain = prefDomain.getString(Constant.EXTRA_DOMAIN, null);
        mEdDomain.setText(domain);
        UserManager userManager = new UserManager(mContext);
        mName.setText(userManager.getUserDetail().getName());
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferences.Editor editorHouse = mContext.getSharedPreferences(Constant.PREFS_NAME_HOUSE,
                        MODE_PRIVATE).edit();
                editorHouse.putString(Constant.EXTRA_NAME_HOUSE,mEdHouse.getText().toString());
                editorHouse.commit();
                SharedPreferences.Editor editorLan = mContext.getSharedPreferences(Constant.PREFS_LAN,
                        MODE_PRIVATE).edit();
                editorLan.putString(Constant.EXTRA_LAN,mEdLan.getText().toString());
                editorLan.commit();
                SharedPreferences.Editor editorInternet = mContext.getSharedPreferences(Constant.PREFS_INTERNET,
                        MODE_PRIVATE).edit();
                editorInternet.putString(Constant.EXTRA_INTERNET,mEdInternet.getText().toString());
                editorInternet.commit();
                SharedPreferences.Editor editorDomain = mContext.getSharedPreferences(Constant.PREFS_DOMAIN,
                        MODE_PRIVATE).edit();
                editorDomain.putString(Constant.EXTRA_DOMAIN,mEdDomain.getText().toString());
                editorDomain.commit();
                Intent i = mContext.getPackageManager()
                        .getLaunchIntentForPackage( mContext.getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(i);
            }
        });
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
