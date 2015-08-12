package uwinfosessions.uwinfosessions.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.TabsPagerAdapter;

public class InfoSessionListActivity extends FragmentActivity implements ActionBar.TabListener/*, ViewTreeObserver.OnScrollChangedListener */{

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";
    public ViewPager viewPager;
    public TabsPagerAdapter tabAdapter;
    public ActionBar actionBar;
    public float actionBarHeight;


    DrawerLayout drawerLayout;
    ListView drawerListView;
    ActionBarDrawerToggle drawerToggle;
    String title="";

    private String[] tabs = { "All", "Today", "Favourite", "Reminder" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_session_list);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerListView = (ListView)findViewById(R.id.drawer_list);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle("Info Sessions");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.menu_title);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_info_session_list_item_drawer, getResources().getStringArray(R.array.string_menu_options));
        drawerListView.setAdapter(adapter);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String[] menu_options = getResources().getStringArray(R.array.string_menu_options);
                switch(position){
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(InfoSessionListActivity.this);
                        builder.setTitle(R.string.select_calendar);
                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = inflater.inflate(R.layout.layout_alert_calendar, null);
                        builder.setView(v);

                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        //builder.setMessage(R.string.getting_data);
                        //final AlertDialog dialog = builder.create();
                        builder.show();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }

            }
        });

        viewPager = (ViewPager)findViewById(R.id.pager);
        tabAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        actionBar = getActionBar();

        /*viewPager.getViewTreeObserver().addOnScrollChangedListener(this);*/

        viewPager.setAdapter(tabAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name : tabs){
            ActionBar.Tab tab = actionBar.newTab().setText(tab_name).setTabListener(this);
            //tab.setIcon(R.drawable.ic_favunsel);
            actionBar.addTab(tab);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_session_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerListView);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /*@Override
    public void onScrollChanged() {
        float y = viewPager.findViewById(R.id.main_list).getScrollY();
        Log.d(DEBUGTAG,"1----------------------------" + actionBarHeight + " y=" + y);
        if (y >= actionBarHeight && actionBar.isShowing()) {
            Log.d(DEBUGTAG,"2----------------------------" + actionBarHeight);
            actionBar.hide();
        } else if ( y<0 && !actionBar.isShowing()) {
            Log.d(DEBUGTAG,"3----------------------------" + actionBarHeight);
            actionBar.show();
        }
    }*/
}
