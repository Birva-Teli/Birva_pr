package example.com.birva_pr.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.ImagePickerActivity;
import example.com.birva_pr.R;
import example.com.birva_pr.helpers.AppConstants;
import example.com.birva_pr.helpers.AppUtils;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;


    AlertDialog.Builder builder;
    @BindView(R.id.btnRetrofit)
    Button btnRetrofit;
    @BindView(R.id.btnNavigationDrawer)
    Button btnNavigationDrawer;
    @BindView(R.id.btnConnectivityStatus)
    Button btnConnectivityStatus;
    @BindView(R.id.btnimageViewer)
    Button btnimageViewer;
    @BindView(R.id.btnCropImage)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btnRegisterListView)
    Button btnRegisterListView;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.relative)
    RelativeLayout relative;
    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        builder = new AlertDialog.Builder(this);


    }

    @OnClick({R.id.btnRetrofit, R.id.btnNavigationDrawer, R.id.btnConnectivityStatus,
            R.id.btnRegisterListView, R.id.btnLogout, R.id.btnimageViewer,R.id.btnCropImage,R.id.btn6})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnRetrofit:
                showDialog("Description", "Data Showing Using Retrofit", 1);
                break;
            case R.id.btnNavigationDrawer:
                showDialog("Description", "Navigation Drawer", 2);
                break;
            case R.id.btnConnectivityStatus:
                showDialog("Description", "BroadCast Receiver Example", 3);
                break;
            case R.id.btnimageViewer:
                showDialog("Description", "Image Viewer using Glide, Matisse, and ViewPager", 4);
                break;
            case R.id.btnCropImage:
                showDialog("Description", "Crop Image", 5);
                break;
            case R.id.btn6:
                showDialog("Description","add View dynamically",6);
                break;
            case R.id.btnRegisterListView:
                i = new Intent(MainActivity.this, UsersListActivity.class);
                startActivity(i);
                break;
            case R.id.btnLogout:
                SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putBoolean(AppConstants.isLoggedIn, false);
                editor.commit();
                i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
    }

    private void showDialog(String title, String description, final int activityType) {

        //Setting message manually and performing action on button click
        builder.setMessage(description)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (activityType) {
                            case 1:
                                startActivity(new Intent(context, DisplayDataActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(context, MainFragmentActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(context, BroadcastReceiverExampleActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(context, ImagePickerActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(context, CropImageActivity.class));
                                break;
                            case 6:
                                startActivity(new Intent(context, ZoomRecyclerView.class));
                                break;
                        }

                        AppUtils.showLog("you choose yes action for alertbox");
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();
    }

}
