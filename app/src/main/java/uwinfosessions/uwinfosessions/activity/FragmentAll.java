package uwinfosessions.uwinfosessions.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uwinfosessions.uwinfosessions.R;

/**
 * Created by Vishal on 07/08/2015.
 */
public class FragmentAll extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);
    }
}
