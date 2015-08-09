package uwinfosessions.uwinfosessions.adapter;

import android.content.Context;
import android.content.Intent;
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
import uwinfosessions.uwinfosessions.activity.MainActivity;
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
        return infoNodes.size();
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

        imageFav.setImageResource(R.drawable.ic_favunsel);
        imageInfo.setImageResource(R.drawable.ic_info);
        imageReminder.setImageResource(R.drawable.ic_reminder_unsel);
        imageLocation.setImageResource(R.drawable.ic_location);
        imageShare.setImageResource(R.drawable.ic_share);
        imageRightArrow.setImageResource(R.drawable.ic_right_arrow);

        sessionNameView.setText(sessionName);
        sessionDateTimeView.setText(sessionDate + ", " + sessionTime);
        sessionLocationView.setText(sessionLocation);
        sessionForView.setText(sessionFor);
        Log.d(MainActivity.DEBUGTAG, "Setting text and icons");

        imageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(context);
                ImageView image = (ImageView) v;
                image.setImageResource(R.drawable.ic_favsel);
                db.storeFavSession(infoNode);
                Toast toast = Toast.makeText(context, sessionName + " saved as your favourite session!", Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        imageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InfoSessionActivity.class);
                i.putExtra(MainActivity.WHOLE_LINE, sessionLine);
                context.startActivity(i);
                Log.d(MainActivity.DEBUGTAG, "Clicked Info on " + sessionName);
            }
        });

        imageReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) v;
                image.setImageResource(R.drawable.ic_reminder_sel);
                Toast toast = Toast.makeText(context, "Reminder set for " + sessionName + " session!", Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapActivity.class);
                context.startActivity(i);
                Log.d(MainActivity.DEBUGTAG, "Clicked Map on " + sessionName);
            }
        });

        /*imageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InfoSessionActivity.class);
                i.putExtra(MainActivity.WHOLE_LINE, sessionLine);
                context.startActivity(i);
                Log.d(MainActivity.DEBUGTAG, "Clicked Right Arrow on " + sessionName);
            }
        });*/

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareString = sessionName + " is visiting our campus on " + sessionDate + " between " + sessionTime + " at " + sessionLocation + ". Let's try to go to the session" + "\n \n \n" + "For more details, install UW Info App on your android mobile.";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, sessionName + " coming here!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareString);
                context.startActivity(Intent.createChooser(sharingIntent, "Share " + sessionName + " via"));
                Log.d(MainActivity.DEBUGTAG, "Clicked Share on " + sessionName);
            }
        });

        return view;
    }
}