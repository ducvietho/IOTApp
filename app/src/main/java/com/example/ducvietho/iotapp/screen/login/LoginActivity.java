package com.example.ducvietho.iotapp.screen.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ducvietho.iotapp.R;
import com.example.ducvietho.iotapp.data.model.Login;
import com.example.ducvietho.iotapp.data.model.LoginResponse;
import com.example.ducvietho.iotapp.data.resource.remote.LoginDataRepository;
import com.example.ducvietho.iotapp.data.resource.remote.api.LoginRemoteDataResource;
import com.example.ducvietho.iotapp.data.resource.remote.api.service.IOTServiceClient;
import com.example.ducvietho.iotapp.screen.main.MainActivity;
import com.example.ducvietho.iotapp.util.DialogLoading;
import com.example.ducvietho.iotapp.util.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    @BindView(R.id.tv_login)
    TextView mLogin;
    @BindView(R.id.ed_user)
    EditText mUserName;
    @BindView(R.id.ed_pass)
    EditText mPass;
    private DialogLoading mLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        mLoading = new DialogLoading(LoginActivity.this);
        new UserManager(LoginActivity.this).checkUserLogin();
        LoginDataRepository repository =  new LoginDataRepository(new LoginRemoteDataResource(IOTServiceClient
                .getInstance()));
        final LoginContract.Presenter presenter = new LoginPresenter(repository,LoginActivity.this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoading.showDialog();
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setStatus(200);
                Login login = new Login();
                login.setToken("avaoifaeiv2sfhfueifuf");
                login.setName("DucViet");
                new UserManager(LoginActivity.this).createUserLoginSession(login);
                startActivity(new MainActivity().getIntent(LoginActivity.this));
//                if(mUserName.getText().equals("")||mPass.getText().equals("")){
//                   Toast.makeText(LoginActivity.this,"Nhập Username và Pass",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    mLoading.showDialog();
//                    presenter.loginUser(mUserName.getText().toString(),mPass.getText().toString());
//                }

            }
        });
    }

    @Override
    public void loginSuccess(LoginResponse login) {
        if(login.getStatus()==200){
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
