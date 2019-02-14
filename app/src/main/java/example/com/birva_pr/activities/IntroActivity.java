package example.com.birva_pr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.R;
import example.com.birva_pr.helpers.AppConstants;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.btnSkip)
    Button btnSkip;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSkip, R.id.btnDone})
    public void onViewClicked(View view)

    {
        switch (view.getId()) {
            case R.id.btnSkip:
                executeButton();
                break;
            case R.id.btnDone:
                executeButton();
                break;
        }

    }

    private void executeButton() {
        SharedPreferences sharedpreferences= getSharedPreferences(AppConstants.MyPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.putBoolean(AppConstants.isIntroSeen,true);
        editor.commit();
        Intent i1=new Intent(IntroActivity.this, LoginActivity.class);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
    }
}
