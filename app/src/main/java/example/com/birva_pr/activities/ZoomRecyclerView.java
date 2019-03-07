package example.com.birva_pr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.otaliastudios.zoom.ZoomLayout;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.birva_pr.OnOptionClickListener;
import example.com.birva_pr.R;
import example.com.birva_pr.adapter.Myadapter;
import example.com.birva_pr.beans.UserDetailsBean;
import example.com.birva_pr.database.AppDatabase;
import example.com.birva_pr.helpers.AppConstants;

public class ZoomRecyclerView extends AppCompatActivity {
    ArrayList<UserDetailsBean> users;
    AppDatabase appDatabase;
    SharedPreferences sharedpreferences;
    Myadapter myadapter;
    Context context = ZoomRecyclerView.this;
    int position = -1;

    @BindView(R.id.rvZoom)
    RecyclerView rvZoom;
    @BindView(R.id.zoomLayout)
    ZoomLayout zoomLayout;
    ScaleGestureDetector scaleGestureDetector;
    Float scale = 1f;
    Matrix matrix=new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_recycler_view);
        ButterKnife.bind(this);
        scaleGestureDetector=new ScaleGestureDetector(this,new ScaleListener());
        users = new ArrayList<UserDetailsBean>();
        rvZoom.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = AppDatabase.getDatabase(this);

        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String EmailKey = sharedpreferences.getString(AppConstants.EmailKey, " ");

        users.addAll(appDatabase.userDao().getUsersList(EmailKey));
        myadapter = new Myadapter(users, this);
        rvZoom.setAdapter(myadapter);

        myadapter.setOnOptionClickListener(new OnOptionClickListener() {
            @Override
            public void onOptionClick(final int Position, final UserDetailsBean userDetailsBean, View view) {
                position = Position;
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.recyclerview_list_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.update:

                                Intent i = new Intent(context, RegisterActivity.class);
                                i.putExtra(AppConstants.USER_DETAIL_BEAN, userDetailsBean);
                                startActivityForResult(i, AppConstants.REQUEST_CODE_UPDATE_USER);
                                break;

                            case R.id.delete:

                                appDatabase.userDao().deleteUser(userDetailsBean);
                                users.remove(position);
                                myadapter.notifyDataSetChanged();
                                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale= scale*detector.getScaleFactor();
            scale= Math.max(0.1f,Math.min(scale,5f));
            matrix.setScale(scale,scale);
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.REQUEST_CODE_UPDATE_USER) {
                if (data != null) {
                    UserDetailsBean obj = data.getParcelableExtra(AppConstants.USER_DETAIL_BEAN);
                    myadapter.addUser(obj, false, position);
                }
            }
        }
    }

}
