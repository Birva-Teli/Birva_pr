package example.com.birva_pr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.R;
import example.com.birva_pr.database.AppDatabase;
import example.com.birva_pr.database.UserDetailsBean;
import example.com.birva_pr.helpers.AppConstants;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editText_Username)
    TextInputEditText editTextUsername;
    @BindView(R.id.input_Username)
    TextInputLayout inputUsername;
    @BindView(R.id.editText_email)
    TextInputEditText editTextEmail;
    @BindView(R.id.input_Email)
    TextInputLayout inputEmail;
    @BindView(R.id.editText_password)
    TextInputEditText editTextPassword;
    @BindView(R.id.input_password)
    TextInputLayout inputPassword;
    @BindView(R.id.editText_confirmPassword)
    TextInputEditText editTextConfirmPassword;
    @BindView(R.id.input_confirmPassword)
    TextInputLayout inputConfirmPassword;
    @BindView(R.id.gender_radioBtn)
    TextView genderRadioBtn;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.radioButton_layout)
    LinearLayout radioButtonLayout;
    @BindView(R.id.editText_MobileNo)
    TextInputEditText editTextMobileNo;
    @BindView(R.id.input_MobileNo)
    TextInputLayout inputMobileNo;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.linearLayoutResister)
    LinearLayout linearLayoutResister;

    TextInputEditText etUpdateName,etUpdateEmail,etUpdatePass,etUpdateMob;


    private AppDatabase appDatabase;

    UserDetailsBean userDetailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        appDatabase = AppDatabase.getDatabase(this);
        userDetailsBean=new UserDetailsBean();
        userDetailsBean = getIntent().getParcelableExtra(AppConstants.USER_DETAIL_BEAN);

        if (userDetailsBean != null) {
            editTextUsername.setText(userDetailsBean.getName());
            editTextEmail.setText(userDetailsBean.getEmail());
            editTextPassword.setText(userDetailsBean.getPassword());
            editTextMobileNo.setText(userDetailsBean.getMobNo());
            editTextConfirmPassword.setText(userDetailsBean.getPassword());
            switch (userDetailsBean.getGender())
            {
                case AppConstants.GENDER_MALE:
                    radioMale.setChecked(true);
                    break;
                case AppConstants.GENDER_FEMALE:

                    radioFemale.setChecked(true);
                    break;
            }

        }
    }

    @OnClick(R.id.registerButton)
    public void onViewClicked() {

        String email = editTextEmail.getText().toString().trim();
        String strPwd = editTextPassword.getText().toString().trim();
        String strConfirmPwd = editTextConfirmPassword.getText().toString().trim();
        //validations

        if (!funValidateInput()) {
            return;
        }
        if (appDatabase.userDao().isUserExist(editTextEmail.getText().toString())) {
            inputEmail.setError(getString(R.string.err_msg_email_exist));
            return;
        }
        if (userDetailsBean != null)
            doUpdateUser();
        else
            doRegister();
    }

    private void doRegister() {
        UserDetailsBean userBean = new UserDetailsBean();
        userBean.setName(editTextUsername.getText().toString().trim());
        userBean.setEmail(editTextEmail.getText().toString().trim());
        userBean.setPassword(editTextPassword.getText().toString().trim());
        userBean.setMobNo(editTextMobileNo.getText().toString().trim());
        switch (rgGender.getCheckedRadioButtonId()) {
            case -1:
                Toast.makeText(this,
                        "Please select Gender", Toast.LENGTH_SHORT).show();
                return;
            case R.id.radioFemale:
                userBean.setGender(AppConstants.GENDER_FEMALE);
                break;
            case R.id.radioMale:
                userBean.setGender(AppConstants.GENDER_MALE);
                break;

        }
        appDatabase.userDao().insert(userBean);
        String strPassEmail = editTextEmail.getText().toString().trim();
        Intent i = new Intent();
        i.putExtra(AppConstants.EMAIL_RESULT_KEY, strPassEmail);
        setResult(RESULT_OK, i);
        finish();
        Toast.makeText(this,
                "Data Added Successfully", Toast.LENGTH_SHORT).show();
    }

    private void doUpdateUser() {
        userDetailsBean.setName(editTextUsername.getText().toString().trim());
        userDetailsBean.setEmail(editTextEmail.getText().toString().trim());
        userDetailsBean.setPassword(editTextPassword.getText().toString().trim());
        userDetailsBean.setMobNo(editTextMobileNo.getText().toString().trim());

        switch (rgGender.getCheckedRadioButtonId()) {
            case -1:
                Toast.makeText(this,
                        "Please select Gender", Toast.LENGTH_SHORT).show();
                return;
            case R.id.radioFemale:
                userDetailsBean.setGender(AppConstants.GENDER_FEMALE);
                break;
            case R.id.radioMale:
                userDetailsBean.setGender(AppConstants.GENDER_MALE);
                break;
        }
        appDatabase.userDao().update(userDetailsBean);
        Intent i = new Intent();
        i.putExtra(AppConstants.USER_DETAIL_BEAN, userDetailsBean);
        setResult(RESULT_OK, i);
        finish();
    }


    private boolean funValidateInput(){
        String email = editTextEmail.getText().toString().trim();
        String strPwd = editTextPassword.getText().toString().trim();
        String strConfirmPwd = editTextConfirmPassword.getText().toString().trim();
        if(!isEmpty())
            return false;
        if(!validateEmail(email))
            return false;
        if(!validatePassword(strPwd)) {
            inputPassword.setError("Password Must be Alpha numeric & one capital latter required!");
            return false;
        }
        if(!strPwd.equals(strConfirmPwd)) {
            editTextPassword.setText("");
            editTextConfirmPassword.setText("");
            inputPassword.setError("Password doesn't match!");
            return false;
        }
        return true;
    }


    public boolean isEmpty() {
        if (TextUtils.isEmpty(editTextUsername.getText().toString().trim()) ||
                TextUtils.isEmpty(editTextEmail.getText().toString().trim()) ||
                TextUtils.isEmpty(editTextPassword.getText().toString().trim()) ||
                TextUtils.isEmpty(editTextMobileNo.getText().toString().trim()) ||
                TextUtils.isEmpty(editTextConfirmPassword.getText().toString().trim()) )
        {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(email).matches();
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public boolean validatePassword(String pwd){

        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.{8,}).+$", pwd);
    }

}
