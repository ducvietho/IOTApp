package com.example.ducvietho.iotapp.screen.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.screen.group.GroupFragment;
import com.example.ducvietho.iotapp.screen.home.HomeFragment;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.CustomTypefaceSpan;
import com.example.ducvietho.iotapp.util.DialogInfor;
import com.example.ducvietho.iotapp.util.DialogSetting;
import com.example.ducvietho.iotapp.util.OnChoseImage;
import com.example.ducvietho.iotapp.util.UserManager;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnChoseImage,View.OnClickListener {
    public static final int PICK_IMAGE = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.frame_layout)
    FrameLayout mLayout;
    @BindView(R.id.linearlayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.tv_home)
    TextView mHome;
    @BindView(R.id.tv_scene)
    TextView mScene;
    @BindView(R.id.bt_group)
    RelativeLayout mGroup;
    @BindView(R.id.img_scene)
    ImageView mImgScene;
    @BindView(R.id.img_floor)
    ImageView mImgFloor;
    @BindView(R.id.layout_floor)
    RelativeLayout layoutFloor;
    @BindView(R.id.layout_group)
    RelativeLayout layoutGroup;

    DialogSetting mSetting;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isDrawerOpened;
    private MaterialMenuDrawable materialMenu;

    public Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/UTM PenumbraBold.ttf");
        mHome.setTypeface(tf1);
        SharedPreferences prefHouse = getSharedPreferences(Constant.PREFS_NAME_HOUSE, MODE_PRIVATE);
        String nameHouse = prefHouse.getString(Constant.EXTRA_NAME_HOUSE, null);
        if (nameHouse != null && !nameHouse.matches("")) {
            mHome.setText(nameHouse);
        } else {
            mHome.setText("my home");
        }
        mScene.setTypeface(tf1);
        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        setUpToolbar();
        setUpNavDrawer();
        startFragment(new HomeFragment());
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            applyFontToMenuItem(mi);
        }
        layoutFloor.setOnClickListener(this);
        layoutGroup.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_group:
                mImgScene.setVisibility(View.VISIBLE);
                mImgFloor.setVisibility(View.GONE);
                startFragment(new GroupFragment());
                break;
            case R.id.layout_floor:
                mImgFloor.setVisibility(View.VISIBLE);
                mImgScene.setVisibility(View.GONE);
                startFragment(new HomeFragment());
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String path = selectedImage.getPath();
            SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_IMAGE, MODE_PRIVATE).edit();
            editor.putString(Constant.EXTRA_IMAGE, path);
            editor.commit();
            new DialogSetting(MainActivity.this, MainActivity.this).showDialog();

        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
                return;
            }
            doubleBackToExitPressedOnce = true;
            String toast = "Press again to exit";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }

    private void setUpToolbar() {

        if (mToolbar != null) {
            mToolbar.setNavigationIcon(materialMenu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void setUpNavDrawer() {
        View header = mNavigationView.inflateHeaderView(R.layout.header_navigation);
        TextView textView = header.findViewById(R.id.tv_name);
        CircleImageView imageView = header.findViewById(R.id.img_avatar);
        SharedPreferences preferencesImage = getSharedPreferences(Constant.PREFS_IMAGE, MODE_PRIVATE);
        String path = preferencesImage.getString(Constant.EXTRA_IMAGE, null);
        if (path != null) {
            Picasso.with(MainActivity.this).load(new File(path)).placeholder(R.drawable.ic_user_placeholder).into(imageView);
        }

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/UTM Penumbra.ttf");
        textView.setTypeface(font);
        UserManager userManager = new UserManager(MainActivity.this);
        textView.setText(userManager.getUserDetail().getName());
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW, isDrawerOpened ? 2 - slideOffset : slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_config:
                        new DialogSetting(MainActivity.this, MainActivity.this).showDialog();
                        return true;
                    case R.id.nav_logout:
                        new UserManager(MainActivity.this).logoutUser();

                        return true;
                    case R.id.nav_infor:
                        new DialogInfor(MainActivity.this).showDialog();
                        return true;
                    default:
                        return false;

                }

            }
        });

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/UTM Penumbra.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment, "nextFragment");
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onChoseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}
