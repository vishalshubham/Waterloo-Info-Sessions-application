package uwinfosessions.uwinfosessions.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uwinfosessions.uwinfosessions.activity.FragmentAll;
import uwinfosessions.uwinfosessions.activity.FragmentFavourite;
import uwinfosessions.uwinfosessions.activity.FragmentReminder;
import uwinfosessions.uwinfosessions.activity.FragmentToday;

/**
 * Created by Vishal on 07/08/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentAll();
            case 1:
                return new FragmentToday();
            case 2:
                return new FragmentFavourite();
            case 3:
                return new FragmentReminder();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
