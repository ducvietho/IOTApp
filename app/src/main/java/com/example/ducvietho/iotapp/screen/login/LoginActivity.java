package com.example.ducvietho.iotapp.screen.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Image;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.resource.remote.LoginDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.ImageRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.LoginRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.main.MainActivity;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogLoading;
import com.example.ducvietho.iotapp.util.DialogSetting;
import com.example.ducvietho.iotapp.util.OnChoseImage;
import com.example.ducvietho.iotapp.util.UserManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, OnChoseImage {
    public static final int REQUEST_STORAGE = 112;
    public static final int PICK_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 101;
    @BindView(R.id.tv_login)
    TextView mLogin;
    @BindView(R.id.ed_user)
    EditText mUserName;
    @BindView(R.id.ed_pass)
    EditText mPass;
    @BindView(R.id.tv_member)
    TextView mTextViewMember;
    @BindView(R.id.tv_forget)
    TextView mTextViewForget;
    @BindView(R.id.img_show)
    ImageView mImageView;
    @BindView(R.id.img_not)
    ImageView mImageViewNo;
    @BindView(R.id.cb_remember)
    CheckBox mCheckBox;
    @BindView(R.id.tv_copy)
    TextView mCopy;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    private DialogLoading mLoading;
    ImageRemoteDataResource imageDataRepository;
    LoginDataRepository repository;
    UserManager mUserManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        mUserManager = new UserManager(LoginActivity.this);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/UTM Avo.ttf");
        mTextViewForget.setTypeface(tf);
        mCopy.setTypeface(tf);
        mTextViewMember.setTypeface(tf);
        mUserName.setTypeface(tf);
        mPass.setTypeface(tf);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/UTM Penumbra.ttf");
        mLogin.setTypeface(tf1);
        SharedPreferences preferences = getSharedPreferences(Constant.PREFS_ACCOUNT, MODE_PRIVATE);
        String user = preferences.getString(Constant.EXTRA_USER, null);
        String pass = preferences.getString(Constant.EXTRA_PASS, null);
        mUserName.setText(user);
        mPass.setText(pass);
        mImageViewNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPass.setTransformationMethod(null);
                mImageViewNo.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mImageView.setVisibility(View.GONE);
                mImageViewNo.setVisibility(View.VISIBLE);
            }
        });
        mLoading = new DialogLoading(LoginActivity.this);
        new UserManager(LoginActivity.this).checkUserLogin();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesLan = getSharedPreferences(Constant.PREFS_LAN, MODE_PRIVATE);
                SharedPreferences sharedPreferencesInternet = getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
                SharedPreferences sharedPreferencesDomain = getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
                SharedPreferences preferencesPort = getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
                String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB, "");
                String lan = Constant.HTTP + sharedPreferencesLan.getString(Constant.EXTRA_LAN, "") + ":" + port;
                lan = lan.replaceAll(" ", "");

                mLoading.showDialog();
                Log.i("Text", mUserName.getText().toString());
                if (mUserName.getText().toString().equals("") || mPass.getText().toString().equals("")) {
                    mLoading.dismissDialog();
                    Toast.makeText(LoginActivity.this, "Nhập Username và Pass", Toast.LENGTH_LONG).show();
                } else {
                    if (sharedPreferencesLan.getString(Constant.EXTRA_LAN, "").equals("") && sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "").equals("") && sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, "").equals("")) {
                        Toast.makeText(LoginActivity.this, "Xin hãy cài đặt ip, internet, domain !", Toast.LENGTH_LONG).show();
                    } else {
                        if (sharedPreferencesLan.getString(Constant.EXTRA_LAN, "").equals("")) {

                            String internet = Constant.HTTP + sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "") + ":" + port;
                            internet = internet.replaceAll(" ", "");
                            if (sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "").equals("")) {
                                String domain = Constant.HTTP + sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, "") + ":" + port;
                                domain = domain.replaceAll(" ", "");
                                repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance(domain)));
                                final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                                        .this);
                                presenter.loginUserDomain(mUserName.getText().toString(), mPass.getText().toString());
                            } else {
                                repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance(internet)));
                                final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                                        .this);
                                presenter.loginUserInternet(mUserName.getText().toString(), mPass.getText().toString());
                            }

                        } else {
                            imageDataRepository = (new ImageRemoteDataResource(IOTServiceClient.getInstance(lan)));
                            repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance(lan)));
                            final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                                    .this);
                            presenter.downloadImageLan();
                            presenter.loginUserLan(mUserName.getText().toString(), mPass.getText().toString());
                        }

                    }

                }

            }
        });
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogSetting(LoginActivity.this, LoginActivity.this).showDialog();
            }
        });
        mTextViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse("http://superfastserver.ddns.net:8081/volumn/email-reset-pass"));
                startActivity(viewIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String path = selectedImage.toString();
            SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_IMAGE, MODE_PRIVATE).edit();
            editor.putString(Constant.EXTRA_IMAGE, path);
            editor.commit();
            new DialogSetting(LoginActivity.this, LoginActivity.this).showDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_ACCOUNT, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void loginSuccess(LoginResponse login) {
        if (login.getStatus() == 200) {

            new UserManager(LoginActivity.this).createUserLoginSession(login.getLogin(), mCheckBox.isChecked());

            SharedPreferences.Editor editor = getSharedPreferences(Constant.PRE_MAC, Context.MODE_PRIVATE).edit();
            editor.putString(Constant.EXTRA_MAC, login.getLogin().getMac());
            editor.commit();

            startActivity(new MainActivity().getIntent(LoginActivity.this));

        } else {
            mLoading.dismissDialog();
            Toast.makeText(LoginActivity.this, "Mật khẩu hoặc tài khoản không đúng !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void loginFailureLan(String username, String pass) {
        IOTServiceClient.clear();
        SharedPreferences preferencesPort = getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB, "");
        SharedPreferences sharedPreferencesInternet = getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = Constant.HTTP + sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "") + ":" + port;
        internet = internet.replaceAll(" ", "");
        if (sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "").replace(" ", "").equals("")) {
            loginFailureInternet(username, pass);
        } else {
            repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance(internet)));
            final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                    .this);
            presenter.loginUserInternet(username, pass);
        }

    }

    @Override
    public void loginFailureInternet(String username, String pass) {
        IOTServiceClient.clear();
        SharedPreferences preferencesPort = getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB, "");
        SharedPreferences sharedPreferencesDomain = getSharedPreferences(Constant.PREFS_DOMAIN, MODE_PRIVATE);
        String domain = Constant.HTTP + sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, "") + ":" + port;
        domain = domain.replaceAll(" ", "");
        if (sharedPreferencesDomain.getString(Constant.EXTRA_DOMAIN, "").replace(" ", "").equals("")) {
            loginFailure("");
        } else {
            repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance(domain)));
            final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                    .this);
            presenter.loginUserDomain(username, pass);
        }

    }

    @Override
    public void loginFailure(String message) {
        mLoading.dismissDialog();
        Toast.makeText(LoginActivity.this, "Lỗi kết nối !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void downloadSuccess(List<Image> list) {
        requestPermission(list);
    }

    @Override
    public void downloadFailLan() {
        SharedPreferences preferencesPort = getSharedPreferences(Constant.PREFS_PORT_WEB, MODE_PRIVATE);
        String port = preferencesPort.getString(Constant.EXTRA_PORT_WEB, "");
        SharedPreferences sharedPreferencesInternet = getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE);
        String internet = Constant.HTTP + sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET, "") + ":" + port;
        imageDataRepository = (new ImageRemoteDataResource(IOTServiceClient.getInstance(internet)));
        final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository, repository, LoginActivity
                .this);
        presenter.downloadImageInternet();
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            } else {
                // User refused to grant permission.
            }
        }
    }

    private void requestPermission(List<Image> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        } else {
            for (int i = 0; i < list.size(); i++) {
                imageDownload(list.get(i).getIconOff());
                imageDownload(list.get(i).getIconOn());
            }
        }
    }

    private void imageDownload(String url) {
        Picasso.with(LoginActivity.this).load(url).into(getTarget(url));
    }


    private Target getTarget(final String url) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        File folder = new File(getCacheDir() + "/iot/");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        String urlRe = url.replaceAll("/", "");
                        File file = new File(getCacheDir() + "/iot/" + urlRe);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) LoginActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onChoseImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

}
