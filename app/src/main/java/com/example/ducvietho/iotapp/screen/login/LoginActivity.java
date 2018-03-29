package com.example.ducvietho.iotapp.screen.login;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ducvietho.iotapp.util.UserManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    public static final int REQUEST_STORAGE = 112;
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
    private DialogLoading mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/UTM Avo.ttf");
        mTextViewForget.setTypeface(tf);
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
        SharedPreferences.Editor editorInternet = getSharedPreferences(Constant.PREFS_INTERNET, MODE_PRIVATE).edit();
        editorInternet.putString(Constant.EXTRA_INTERNET, "http://34.229.9.67:2021");
        editorInternet.commit();
        ImageRemoteDataResource imageDataRepository = (new ImageRemoteDataResource
                (IOTServiceClient.getInstance()));
        LoginDataRepository repository = new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient.getInstance()));
        final LoginContract.Presenter presenter = new LoginPresenter(imageDataRepository,repository, LoginActivity
                .this);
        new UserManager(LoginActivity.this).checkUserLogin();
        presenter.downloadImage();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading.showDialog();
                if (mUserName.getText().toString().equals("") || mPass.getText().toString().equals("")) {
                    mLoading.dismissDialog();
                    Toast.makeText(LoginActivity.this, "Nhập Username và Pass", Toast.LENGTH_LONG).show();
                } else {
                    presenter.loginUser(mUserName.getText().toString(), mPass.getText().toString());
                }

            }
        });
    }

    @Override
    public void loginSuccess(LoginResponse login) {
        if (login.getStatus() == 200) {
            if (mCheckBox.isChecked() == true) {
                SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_ACCOUNT, MODE_PRIVATE).edit();
                editor.putString(Constant.EXTRA_USER, mUserName.getText().toString());
                editor.putString(Constant.EXTRA_PASS, mPass.getText().toString());
                editor.commit();
            }

            startActivity(new MainActivity().getIntent(LoginActivity.this));
            new UserManager(LoginActivity.this).createUserLoginSession(login.getLogin());
        } else {
            Toast.makeText(LoginActivity.this, "Mật khẩu hoặc tài khoản không đúng !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(LoginActivity.this, "Error:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void downloadSuccess(List<Image> list) {
        requestPermission(list);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return;
            }

        }
    }
    private void requestPermission(List<Image> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        } else {
                for(int i = 0;i<list.size();i++){
                    imageDownload(list.get(i).getIconOff());
                    imageDownload(list.get(i).getIconOn());
                }
            }
        }

    private void imageDownload(String url){
        Picasso.with(LoginActivity.this)
                .load(url)
                .into(getTarget(url));
    }


    private  Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/iot/");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        String urlRe = url.replaceAll("/","");
                        File file = new File(Environment.getExternalStorageDirectory().toString() + "/iot/"+urlRe );
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
}
