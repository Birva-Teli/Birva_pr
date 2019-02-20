package example.com.birva_pr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.GetUserDetailsBean;
import example.com.birva_pr.R;
import example.com.birva_pr.RestClient;
import example.com.birva_pr.UserBean;
import example.com.birva_pr.helpers.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayDataActivity extends AppCompatActivity {

    @BindView(R.id.btnGetUserDetails)
    Button btnGetUserDetails;
    @BindView(R.id.tvUserDetails)
    TextView tvUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGetUserDetails)
    public void onViewClicked() {

        Call<GetUserDetailsBean> call = RestClient.getRestClient()
                .getUserDetails("bearer e8d01327-c0c4-47c3-a840-3544147d9ae0");
        call.enqueue(new Callback<GetUserDetailsBean>() {
            @Override
            public void onResponse(Call<GetUserDetailsBean> call, Response<GetUserDetailsBean> response) {

                UserBean userbean=new UserBean();
                userbean=response.body().getUserBean();
                tvUserDetails.setText(userbean.getCountryCode()+ " "+userbean.getEmail()+" "+userbean.getFirstName()+
                        " "+userbean.getGender()+" "+userbean.getLastName()+" "+userbean.getPhone()+
                        " "+userbean.getBirthDate()+" "+userbean.getEmailVerified()+" "+userbean.getId()+
                        " "+userbean.getLocation()+" "+userbean.getPhoneNumberVerified());
            }
            @Override
            public void onFailure(Call<GetUserDetailsBean> call, Throwable t) {

            }
        });

    }
}
