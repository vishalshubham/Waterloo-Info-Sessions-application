package uwinfosessions.uwinfosessions.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uwinfosessions.uwinfosessions.activity.InfoSessionActivity;
import uwinfosessions.uwinfosessions.activity.InfoSessionListActivity;
import uwinfosessions.uwinfosessions.activity.MapActivity;
import uwinfosessions.uwinfosessions.R;

/**
 * Created by Vishal on 21/07/2015.
 */
public class InfoNodeAdapter extends BaseAdapter implements ListAdapter {

    private List<InfoNode> infoNodes;
    private Context context;

    public InfoNodeAdapter(Context context, List<InfoNode> infoNodes){
        this.context = context;
        this.infoNodes = infoNodes;
    }

    public InfoNodeAdapter(Context context){
        this.context = context;
    }

    public List<InfoNode> getInfoNodes() {
        return infoNodes;
    }

    public void setInfoNodes(List<InfoNode> infoNodes) {
        this.infoNodes = infoNodes;
    }

    public String getSessionName(int position){
        return infoNodes.get(position).getSessionName();
    }
    @Override
    public int getCount() {
        //Log.d(InfoSessionListActivity.DEBUGTAG, "Size of the infoNodes:" + infoNodes.size());
        if(infoNodes!=null){
            return infoNodes.size();
        }
        else{
            return 0;
        }
        //return infoNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return infoNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return infoNodes.get(position).getSessionId();
    }

    public String getSessionLine(int position){
        return infoNodes.get(position).getSessionLine();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_info_node, null);

        final InfoNode infoNode = infoNodes.get(position);
        final String sessionName = infoNode.getSessionName();
        final String sessionDate = infoNode.getSessionDate();
        final String sessionTime = infoNode.getSessionTime();
        final String sessionLocation = infoNode.getSessionLocation();
        final String sessionFor = infoNode.getSessionFor();
        final String sessionLine = infoNode.getSessionLine();

        TextView sessionNameView = (TextView)view.findViewById(R.id.text_session_name);
        TextView sessionDateTimeView = (TextView)view.findViewById(R.id.text_session_date_time);
        TextView sessionLocationView = (TextView)view.findViewById(R.id.text_session_location);
        TextView sessionForView = (TextView)view.findViewById(R.id.text_session_for);

        ImageView imageFav = (ImageView)view.findViewById(R.id.ic_fav);
        ImageView imageInfo = (ImageView)view.findViewById(R.id.ic_info);
        ImageView imageReminder = (ImageView)view.findViewById(R.id.ic_reminder);
        ImageView imageLocation = (ImageView)view.findViewById(R.id.ic_location);
        ImageView imageShare = (ImageView)view.findViewById(R.id.ic_share);
        ImageView imageRightArrow = (ImageView)view.findViewById(R.id.ic_right_arrow);

        SharedPreferences favPrefs = context.getSharedPreferences(InfoSessionListActivity.FAV_SESSIONS, 0);
        String favString = favPrefs.getString(InfoSessionListActivity.FAV_SESSIONS, "");
        if(favString.indexOf(sessionName)!=-1){
            imageFav.setImageResource(R.drawable.ic_favsel);
        }
        else{
            Log.d(InfoSessionListActivity.DEBUGTAG, "-->" + favString.indexOf(sessionName));
            imageFav.setImageResource(R.drawable.ic_favunsel);
        }

        SharedPreferences remPrefs = context.getSharedPreferences(InfoSessionListActivity.REM_SESSIONS, 0);
        String remString = remPrefs.getString(InfoSessionListActivity.REM_SESSIONS, "");
        if(remString.indexOf(sessionName)!=-1){
            imageReminder.setImageResource(R.drawable.ic_reminder_sel);
        }
        else{
            Log.d(InfoSessionListActivity.DEBUGTAG, "-->" + favString.indexOf(sessionName) + sessionName);
            imageReminder.setImageResource(R.drawable.ic_reminder_unsel);
        }

        imageInfo.setImageResource(R.drawable.ic_info);
        imageLocation.setImageResource(R.drawable.ic_location);
        imageShare.setImageResource(R.drawable.ic_share);
        imageRightArrow.setImageResource(R.drawable.ic_right_arrow);

        sessionNameView.setText(sessionName);
        sessionDateTimeView.setText(sessionDate + ", " + sessionTime);
        sessionLocationView.setText(sessionLocation);
        sessionForView.setText(sessionFor);
        Log.d(InfoSessionListActivity.DEBUGTAG, "Setting text and icons");

        imageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(context);
                ImageView image = (ImageView) v;

                SharedPreferences prefs = context.getSharedPreferences(InfoSessionListActivity.FAV_SESSIONS, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String str = prefs.getString(InfoSessionListActivity.FAV_SESSIONS, "");
                String date = "|" + sessionName + ":" + sessionDate + ":" + sessionTime;
                if (str.indexOf(date) == -1) {
                    str = str + date;
                    image.setImageResource(R.drawable.ic_favsel);
                    db.storeFavSession(infoNode);
                    Toast.makeText(context, "ADDED: " + sessionName + " saved as your favourite session!", Toast.LENGTH_LONG).show();
                } else {
                    str = str.replace(date, "");
                    Log.d(InfoSessionListActivity.DEBUGTAG, "----" + str + "----");
                    image.setImageResource(R.drawable.ic_favunsel);
                    db.removeFavSession(infoNode);
                    Toast.makeText(context, "REMOVED: " + sessionName + " from your favourite list!", Toast.LENGTH_LONG).show();
                }
                editor.putString(InfoSessionListActivity.FAV_SESSIONS, str);
                Log.d(InfoSessionListActivity.DEBUGTAG, "-->" + str);
                editor.commit();

//                if (context instanceof FragmentActivity) {
                   /* // We can get the fragment manager
                    FragmentActivity activity = (FragmentActivity) context;
                    FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();*/
//                } else if (context instanceof Activity) {
                    /*Fragment frg = null;
                    frg = context.getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");*/
//                }

//                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();
            }
        });

        imageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InfoSessionActivity.class);
                i.putExtra(InfoSessionListActivity.WHOLE_LINE, sessionLine);
                context.startActivity(i);
                Log.d(InfoSessionListActivity.DEBUGTAG, "Clicked Info on " + sessionName);
            }
        });

        imageReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(context);
                ImageView image = (ImageView) v;

                SharedPreferences prefs = context.getSharedPreferences(InfoSessionListActivity.REM_SESSIONS, 0);
                SharedPreferences.Editor editor = prefs.edit();
                String str = prefs.getString(InfoSessionListActivity.REM_SESSIONS, "");
                String date = "|" + sessionName + ":" + sessionDate + ":" + sessionTime;
                if (str.indexOf(date) == -1) {
                    str = str + date;
                    image.setImageResource(R.drawable.ic_reminder_sel);
                    db.storeRemSession(infoNode);
                    Toast.makeText(context, "ADDED: Reminder for " + sessionName + " session!", Toast.LENGTH_LONG).show();

                } else {
                    str = str.replace(date, "");
                    image.setImageResource(R.drawable.ic_reminder_unsel);
                    db.removeRemSession(infoNode);
                    Toast.makeText(context, "REMOVED: Reminder for " + sessionName + " session!", Toast.LENGTH_LONG).show();
                }
                editor.putString(InfoSessionListActivity.REM_SESSIONS, str);
                Log.d(InfoSessionListActivity.DEBUGTAG, "-->" + str);
                editor.commit();
            }
        });

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapActivity.class);
                context.startActivity(i);
                Log.d(InfoSessionListActivity.DEBUGTAG, "Clicked Map on " + sessionName);
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareString = sessionName + " is visiting our campus on " + sessionDate + " between " + sessionTime + " at " + sessionLocation + ". Let's try to go to the session" + "\n \n \n" + "For more details, install UW Info App on your android mobile.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, sessionName + " coming here!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);
                context.startActivity(Intent.createChooser(sharingIntent, "Share " + sessionName + " via"));
                Log.d(InfoSessionListActivity.DEBUGTAG, "Clicked Share on " + sessionName);
            }
        });

        return view;
    }
}
