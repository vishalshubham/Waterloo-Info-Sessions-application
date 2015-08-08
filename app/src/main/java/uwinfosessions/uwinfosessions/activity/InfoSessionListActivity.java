package uwinfosessions.uwinfosessions.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.TabsPagerAdapter;

public class InfoSessionListActivity extends FragmentActivity implements ActionBar.TabListener/*, ViewTreeObserver.OnScrollChangedListener */{

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";
    public ViewPager viewPager;
    public TabsPagerAdapter tabAdapter;
    public ActionBar actionBar;
    public float actionBarHeight;

    private String[] tabs = { "All", "Today", "Favourite", "Reminder" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        actionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();*/
        setContentView(R.layout.activity_info_session_list);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_session_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
