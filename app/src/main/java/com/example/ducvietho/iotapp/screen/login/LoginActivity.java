package com.example.ducvietho.iotapp.screen.login;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.resource.remote.FloorDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.LoginDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.FloorRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.LoginRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.home.HomeContract;
import com.example.ducvietho.iotapp.screen.home.HomeFragment;
import com.example.ducvietho.iotapp.screen.home.HomePresenter;
import com.example.ducvietho.iotapp.screen.main.MainActivity;
import com.example.ducvietho.iotapp.util.Constant;
import com.example.ducvietho.iotapp.util.DialogLoading;
import com.example.ducvietho.iotapp.util.UserManager;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
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
    @BindView(R.id.cb_remember)
    CheckBox mCheckBox;
    private DialogLoading mLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/UTM Avo.ttf");
        mTextViewForget.setTypeface(tf);
        mTextViewMember.setTypeface(tf);
        mUserName.setTypeface(tf);
        mPass.setTypeface(tf);
        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/UTM Penumbra.ttf");
        mLogin.setTypeface(tf1);
        SharedPreferences preferences = getSharedPreferences(Constant.PREFS_ACCOUNT,MODE_PRIVATE);
        String user = preferences.getString(Constant.EXTRA_USER,null);
        String pass = preferences.getString(Constant.EXTRA_PASS,null);
        mUserName.setText(user);
        mPass.setText(pass);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPass.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        mLoading = new DialogLoading(LoginActivity.this);
        new UserManager(LoginActivity.this).checkUserLogin();
        SharedPreferences.Editor editorInternet = getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE).edit();
        editorInternet.putString(Constant.EXTRA_INTERNET,"http://118.70.223.182:50280");
        editorInternet.commit();
        SharedPreferences sharedPreferencesInternet = getSharedPreferences(Constant.PREFS_INTERNET,
                MODE_PRIVATE);
        String internet = sharedPreferencesInternet.getString(Constant.EXTRA_INTERNET,null);
        LoginDataRepository repository =  new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient
                .getInstance(internet)));
        final LoginContract.Presenter presenter = new LoginPresenter(repository,LoginActivity.this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mLoading.showDialog();
//                if(mCheckBox.isChecked()==true){
//                    SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_ACCOUNT,MODE_PRIVATE).edit();
//                    editor.putString(Constant.EXTRA_USER,mUserName.getText().toString());
//                    editor.putString(Constant.EXTRA_PASS,mPass.getText().toString());
//                    editor.commit();
//                }
//                LoginResponse loginResponse = new LoginResponse();
//                loginResponse.setStatus(200);
//                Login login = new Login();
//                login.setToken("avaoifaeiv2sfhfueifuf");
//                login.setName("DucViet");
//                new UserManager(LoginActivity.this).createUserLoginSession(login);
//                startActivity(new MainActivity().getIntent(LoginActivity.this));
                if(mUserName.getText().toString().equals("")||mPass.getText().toString().equals("")){
                    mLoading.dismissDialog();
                   Toast.makeText(LoginActivity.this,"Nhập Username và Pass",Toast.LENGTH_LONG).show();
                }
                else {
                    mLoading.showDialog();
                    presenter.loginUser(mUserName.getText().toString(),mPass.getText().toString());
                }

            }
        });
    }

    @Override
    public void loginSuccess(LoginResponse login) {
        if(login.getStatus()==200){
            if(mCheckBox.isChecked()==true){
                SharedPreferences.Editor editor = getSharedPreferences(Constant.PREFS_ACCOUNT,MODE_PRIVATE).edit();
                editor.putString(Constant.EXTRA_USER,mUserName.getText().toString());
                editor.putString(Constant.EXTRA_PASS,mPass.getText().toString());
                editor.commit();
            }

            startActivity(new MainActivity().getIntent(LoginActivity.this));
            new UserManager(LoginActivity.this).createUserLoginSession(login.getLogin());
        }
        else {
            Toast.makeText(LoginActivity.this,"Mật khẩu hoặc tài khoản không đúng !",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(LoginActivity.this,"Error:"+message,Toast.LENGTH_LONG).show();
    }
}
