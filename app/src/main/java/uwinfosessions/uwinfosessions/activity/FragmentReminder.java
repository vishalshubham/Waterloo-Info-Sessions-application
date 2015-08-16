package uwinfosessions.uwinfosessions.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.Database;
import uwinfosessions.uwinfosessions.adapter.InfoNode;
import uwinfosessions.uwinfosessions.adapter.InfoNodeAdapter;

/**
 * Created by Vishal on 07/08/2015.
 */
public class FragmentReminder extends Fragment {

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        setInfoNodeListner(view);
        return view;
    }

    public void setInfoNodeListner(View view){
        final ListView list_main = (ListView)view.findViewById(R.id.main_list);
        final InfoNodeAdapter infoNodeAdapter = new InfoNodeAdapter(getActivity());
        final Database db = new Database(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.wait);
        builder.setMessage(R.string.getting_data);
        final AlertDialog dialog = builder.create();
        dialog.show();

        new AsyncTask<Void, Void, Void>(){
            protected Void doInBackground(Void... params) {
                List<InfoNode> infoNodeList = db.getRemSession();
                infoNodeAdapter.setInfoNodes(infoNodeList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                TextView textNotify = (TextView)getActivity().findViewById(R.id.text_notify_rem);
                if(infoNodeAdapter.getCount()<=0){
                    textNotify.setText("Sorry! No reminders set for any session.");
                }
                else{
                    textNotify.setBackgroundColor(Color.parseColor("#DDDDDD"));
                }
                list_main.setAdapter(infoNodeAdapter);
                list_main.setItemsCanFocus(true);
                dialog.dismiss();
            }
        }.execute();

        list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Log.d(DEBUGTAG, view.toString());
                Intent i = new Intent(getActivity(), InfoSessionActivity.class);
                i.putExtra(WHOLE_LINE, infoNodeAdapter.getSessionLine(position));
                startActivity(i);
                Log.d(InfoSessionListActivity.DEBUGTAG, "Position: " + position + "; Value: " + infoNodeAdapter.getSessionLine(position) + ";");
            }
        });
    }
}
