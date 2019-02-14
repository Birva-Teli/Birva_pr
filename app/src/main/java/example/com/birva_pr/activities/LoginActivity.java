package example.com.birva_pr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.R;
import example.com.birva_pr.database.AppDatabase;
import example.com.birva_pr.database.UserDao;
import example.com.birva_pr.helpers.AppConstants;

import static example.com.birva_pr.helpers.AppConstants.REGISTER_ACTIVITY_REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvRegister)
    TextView tvregister;

    private UserDao userDao;
    private AppDatabase appDatabase;

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.input_layout_pass)
    TextInputLayout inputLayoutPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        appDatabase = AppDatabase.getDatabase(this);

    }

    public void sharedPrefRun() {
        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String stremail = etEmail.getText().toString().trim();
        editor.putString(AppConstants.EmailKey,stremail);
        editor.putBoolean(AppConstants.isLoggedIn, true);
        editor.commit();
    }

/*    private boolean submitForm(String name, String password) {
        if(name.equals(etName.getText().toString().trim()))
        {

        }
    }*/

  /*  private boolean validateName(String name) {
        if (!name.equals(strName)) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(etName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword(String password) {
        if (!password.equals(strPass)) {
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(etPassword);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }*/

    /*private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }*/


    @OnClick({R.id.btnLogin, R.id.tvRegister})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                String strEmail=etEmail.getText().toString().trim();
                String strPwd=etPassword.getText().toString().trim();

                if(appDatabase.userDao().isUserExist(strEmail))
                {
                    if(appDatabase.userDao().getUserPassword(strEmail).equals(strPwd))
                    {
                        sharedPrefRun();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Toast.makeText(this,
                                "Login successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(this, "This email is not registered", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tvRegister:
                Intent i1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(i1, REGISTER_ACTIVITY_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode==REGISTER_ACTIVITY_REQUEST_CODE){
                String returnString = null;
                if (data != null) {
                    returnString = data.getStringExtra(AppConstants.EMAIL_RESULT_KEY);
                }
                etEmail.setText(returnString);
                etPassword.requestFocus();
            }

        }
    }
}

