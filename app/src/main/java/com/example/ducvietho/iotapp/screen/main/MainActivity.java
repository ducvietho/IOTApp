package com.example.ducvietho.iotapp.screen.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.screen.home.HomeFragment;
import com.example.ducvietho.iotapp.util.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.frame_layout)
    FrameLayout mLayout;
    private boolean doubleBackToExitPressedOnce = false;

    public Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpNavDrawer();
        startFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
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
            mToolbar.setTitle(getResources().getString(R.string.home));
            setSupportActionBar(mToolbar);

        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_menu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
        View header = mNavigationView.inflateHeaderView(R.layout.header_navigation);
        TextView textView = header.findViewById(R.id.tv_name);
        UserManager userManager = new UserManager(MainActivity.this);
        textView.setText(userManager.getUserDetail().getName());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_config:
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_config);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                        window.setAttributes(wlp);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                        return true;
                    case R.id.nav_logout:
                        new UserManager(MainActivity.this).logoutUser();
                        return true;
                    default:
                        return true;
                }

            }
        });

    }
    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
