package example.com.birva_pr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.birva_pr.OnOptionClickListener;
import example.com.birva_pr.R;
import example.com.birva_pr.adapter.Myadapter;
import example.com.birva_pr.beans.UserDetailsBean;
import example.com.birva_pr.database.AppDatabase;
import example.com.birva_pr.helpers.AppConstants;

public class UsersListActivity extends AppCompatActivity {
    ArrayList<UserDetailsBean> users;
    AppDatabase appDatabase;
    SharedPreferences sharedpreferences;
    Myadapter myadapter;
    Context context = UsersListActivity.this;
    int position = -1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        users = new ArrayList<UserDetailsBean>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = AppDatabase.getDatabase(this);

        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String EmailKey = sharedpreferences.getString(AppConstants.EmailKey, " ");

        users.addAll(appDatabase.userDao().getUsersList(EmailKey));
        myadapter = new Myadapter(users, this);
        recyclerView.setAdapter(myadapter);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        MenuItem searchItem = menu.findItem(R.id.searchUsers);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Here...");

        Field searchField = null;
        try {
            searchField = SearchView.class.getDeclaredField("mCloseButton");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        searchField.setAccessible(true);
        ImageView closeBtn = null;
        try {
            closeBtn = (ImageView) searchField.get(searchView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        closeBtn.setImageResource(R.drawable.cancel_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myadapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

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