package uwinfosessions.uwinfosessions.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.TabsPagerAdapter;

public class InfoSessionListActivity extends FragmentActivity implements ActionBar.TabListener/*, ViewTreeObserver.OnScrollChangedListener */{

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";
    public static String FAV_SESSIONS = "FAV_SESSIONS";
    public static String REM_SESSIONS = "REM_SESSIONS";
    public static String CURR_DATE = "CURR_DATE";
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
                SharedPreferences prefs = getSharedPreferences(CURR_DATE, 0);
                String curr_date = prefs.getString(CURR_DATE, "");
                getActionBar().setTitle("Info Sessions : " + curr_date);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.menu_title);
            }
        };

        SharedPreferences prefs = getSharedPreferences(CURR_DATE, 0);
        String curr_date = prefs.getString(CURR_DATE, "");
        getActionBar().setTitle("Info Sessions : " + curr_date);

        drawerLayout.setDrawerListener(drawerToggle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_info_session_list_item_drawer, getResources().getStringArray(R.array.string_menu_options));
        drawerListView.setAdapter(adapter);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.pager);
        tabAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        actionBar = getActionBar();

        viewPager.setAdapter(tabAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name : tabs){
            ActionBar.Tab tab = actionBar.newTab().setText(tab_name).setTabListener(this);
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



        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Calendar option
                        ContextThemeWrapper ctw = new ContextThemeWrapper( InfoSessionListActivity.this, R.style.style_ic_pressed );
                        AlertDialog.Builder builder = new AlertDialog.Builder(InfoSessionListActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        builder.setTitle(R.string.select_calendar);
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = inflater.inflate(R.layout.layout_alert_calendar, null);
                        builder.setView(v);
                        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner_calendar);

                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = getSharedPreferences(InfoSessionListActivity.CURR_DATE, 0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString(InfoSessionListActivity.CURR_DATE, spinner.getSelectedItem().toString());
                                editor.commit();
                                Intent i = new Intent(InfoSessionListActivity.this, InfoSessionListActivity.class);
                                startActivity(i);
                                InfoSessionListActivity.this.finish();
                                Toast.makeText(InfoSessionListActivity.this, "Your calendar settings changed to : " + spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        //dialog.setIcon(R.drawable.ic_calendar_grey);
                        TextView textView = new TextView(InfoSessionListActivity.this);
                        textView.setBackgroundColor(Color.parseColor("#FFBB33"));
                        textView.setText(R.string.select_calendar);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(24);
                        textView.setPadding(10, 50, 10, 50);
                        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_white, 0, 0, 0);
                        dialog.setCustomTitle(textView);
                        dialog.show();


                        //AlertDialog.Builder builder= new AlertDialog.Builder( ctw );

                        Button b1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        b1.setBackgroundColor(Color.parseColor("#FFBB33"));
                        b1.setTextColor(Color.WHITE);
                        b1.setTypeface(Typeface.DEFAULT_BOLD);

                        Button b2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        b2.setBackgroundColor(Color.parseColor("#FFBB33"));
                        b2.setTextColor(Color.WHITE);
                        b2.setTypeface(Typeface.DEFAULT_BOLD);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InfoSessionListActivity.this, R.array.string_calendar, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

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
}
