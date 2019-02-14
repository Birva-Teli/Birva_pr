package example.com.birva_pr.activities;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import example.com.birva_pr.InternetConnectedFragment;
import example.com.birva_pr.NoInternetConnectivityFragment;
import example.com.birva_pr.R;
import example.com.birva_pr.helpers.BroadCastReceiver;

public class BroadcastReceiverExampleActivity extends AppCompatActivity implements BroadCastReceiver.ConnectivityReceiverListener{

    BroadCastReceiver broadCastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver_example);
        broadCastReceiver = new BroadCastReceiver();
        broadCastReceiver.addListener(this);
        this.registerReceiver(broadCastReceiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

       /* InternetConnectedFragment internetConnectedFragment = new InternetConnectedFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flConnectivityContainer, internetConnectedFragment);
        fragmentTransaction.commit();*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadCastReceiver.removeListener(this);
        this.unregisterReceiver(broadCastReceiver);
    }

    @Override
    public void message(String strConnectionMessage)
    {
        TextView tvMsg=(TextView)findViewById(R.id.tvShowMsg);
        tvMsg.setText(strConnectionMessage);
/*
       if(strConnectionMessage==1 || strConnectionMessage==2)
       {
           InternetConnectedFragment internetConnectedFragment = new InternetConnectedFragment();
           android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
           fragmentTransaction.replace(R.id.flConnectivityContainer, internetConnectedFragment);
           fragmentTransaction.commit();
       }
       else {
           NoInternetConnectivityFragment noInternetConnectivityFragment = new NoInternetConnectivityFragment();
           android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
           fragmentTransaction.replace(R.id.flConnectivityContainer, noInternetConnectivityFragment);
           fragmentTransaction.commit();
       }*/

    }

}
