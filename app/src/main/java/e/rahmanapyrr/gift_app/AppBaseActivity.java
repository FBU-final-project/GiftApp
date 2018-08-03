package e.rahmanapyrr.gift_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import e.rahmanapyrr.gift_app.Calendar.CalendarActivity;
import e.rahmanapyrr.gift_app.Friends.AddFriends;
import e.rahmanapyrr.gift_app.Friends.CurrentUserFriends;

public abstract class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener
{
    private FrameLayout view_stub;
    private NavigationView navigation_view;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;

    public AppBaseActivity() {}

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.app_base_layout);
        view_stub = ((FrameLayout)findViewById(R.id.view_stub));
        navigation_view = ((NavigationView)findViewById(R.id.navigation_view));
        mDrawerLayout = ((DrawerLayout)findViewById(R.id.drawer_layout));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();
        for (int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
    }


    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    public void setContentView(int layoutResID)
    {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, -1);


            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    public void setContentView(View view)
    {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, -1);


            view_stub.addView(view, lp);
        }
    }

    public void setContentView(View view, ViewGroup.LayoutParams params)
    {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }



    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.navAddFriends:
                Intent i = new Intent(this, AddFriends.class);
                startActivity(i);
                finish();
                break;
            case R.id.navCalender:
                Intent o = new Intent(this, CalendarActivity.class);
                startActivity(o);
                finish();
                break;
            case R.id.navCurrentFriends:
                Intent p = new Intent(this, CurrentUserFriends.class);
                startActivity(p);
                finish();
                break;
        }

        return false;
    }
}