package com.vacadmin.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vacadmin.R;
import com.vacadmin.adapter.FeedAdapter;
import com.vacadmin.bean.FeedItems;
import com.vacadmin.widget.fabMenu.FabSpeedDial;
import com.vacadmin.widget.fabMenu.SimpleMenuListenerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * declaration of variables of xml layouts
     */
    private SearchView searchView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbarHeader;

    @InjectView(R.id.fab_speed_dial)
    FabSpeedDial fab_speed_dial;

    @InjectView(R.id.rvFeed)
    RecyclerView rvFeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_home);

        /**|
         * inject views of xml layout to java code
         */
        ButterKnife.inject(this);


        /*********************** INITIALIZATION **********************************/
        initializeViews();

        /**
         * set up  recyclerview
         */
        setupFeed();

    }

    /**
     * initializing views of xml layout
     */
    private void initializeViews() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbarHeader = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarHeader);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbarHeader, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FabSpeedDial fabSpeedDial = ((FabSpeedDial) findViewById(R.id.fab_speed_dial));
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {

                setMenuItemVisibility(navigationMenu, R.id.action_call);
                setMenuItemVisibility(navigationMenu, R.id.action_text);
                setMenuItemVisibility(navigationMenu, R.id.action_email);

                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Toast.makeText(HomeActivity.this,"You clicked on "+menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                return super.onMenuItemSelected(menuItem);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void setMenuItemVisibility(NavigationMenu menu, int menuItemId) {
        menu.findItem(menuItemId).setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) myActionMenuItem.getActionView();
        }

        /**
         * customizing cursor
         */
        EditText editTextSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        try {
            Field searchViewField = TextView.class.getDeclaredField("mCursorDrawableRes");
            searchViewField.setAccessible(true);
            searchViewField.set(editTextSearch, R.drawable.custom_cursor);// set textCursorDrawable to null
        } catch (Exception e) {
            e.printStackTrace();
        }
        editTextSearch.setHintTextColor(Color.parseColor("#F9FBFB"));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this, "item to search - " + query, Toast.LENGTH_SHORT).show();

                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    myActionMenuItem.collapseActionView();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }


    private void setupFeed() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        rvFeed.setLayoutManager(linearLayoutManager);

        FeedAdapter feedAdapter = new FeedAdapter(this, getDataList());
        rvFeed.setAdapter(feedAdapter);
        rvFeed.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Toast.makeText(HomeActivity.this,"clicked",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
     }

    private ArrayList<FeedItems> getDataList() {

        ArrayList<FeedItems> dataList = new ArrayList<>();

        dataList.add(new FeedItems("Clarks Amer", "Opp. Fortis Hospital, Jaipur, Rajasthan", R.mipmap.img_hotel));
        dataList.add(new FeedItems("Red Fox", "Opp. MNIT College,Jaipur", R.mipmap.img_room));
        dataList.add(new FeedItems("District 9 Lounge", "Opposite Reliance Fresh, Malviya Nagar, Jaipur", R.mipmap.img_pub));
        dataList.add(new FeedItems("Octal", "6/78, Near Kardhani Shopping Center", R.mipmap.img_house));

        return dataList;
    }

}
