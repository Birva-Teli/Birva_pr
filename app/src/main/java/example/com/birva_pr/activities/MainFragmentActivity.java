package example.com.birva_pr.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import example.com.birva_pr.BarkhaFragment;
import example.com.birva_pr.MainFragment;
import example.com.birva_pr.MiteshFragment;
import example.com.birva_pr.PranavFragment;
import example.com.birva_pr.R;
import example.com.birva_pr.SmeetFragment;

public class MainFragmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationview=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationview=(NavigationView)findViewById(R.id.navigation_view);
        navigationview.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id) {
            case R.id.birva:
                replaceFragment(new MainFragment());

                break;

            case R.id.barkha:

                replaceFragment(new BarkhaFragment());

                break;

            case R.id.mitesh:

                replaceFragment(new MiteshFragment());

                break;

            case R.id.smeet:

                replaceFragment(new SmeetFragment());

                break;

            case R.id.Pranav:

                replaceFragment(new PranavFragment());
                break;
        }
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    private void replaceFragment(Fragment fragment)
    {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
