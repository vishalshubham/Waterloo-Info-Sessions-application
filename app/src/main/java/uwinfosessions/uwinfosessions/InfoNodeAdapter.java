package uwinfosessions.uwinfosessions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

        InfoNode infoNode = infoNodes.get(position);
        final String sessionName = infoNode.getSessionName();
        String sessionDate = infoNode.getSessionDate();
        String sessionTime = infoNode.getSessionTime();
        String sessionLocation = infoNode.getSessionLocation();
        String sessionFor = infoNode.getSessionFor();

        TextView sessionNameView = (TextView)view.findViewById(R.id.text_session_name);
        TextView sessionDateTimeView = (TextView)view.findViewById(R.id.text_session_date_time);
        TextView sessionLocationView = (TextView)view.findViewById(R.id.text_session_location);
        TextView sessionForView = (TextView)view.findViewById(R.id.text_session_for);

        ImageView imageFav = (ImageView)view.findViewById(R.id.ic_fav);
        ImageView imageInfo = (ImageView)view.findViewById(R.id.ic_info);
        ImageView imageReminder = (ImageView)view.findViewById(R.id.ic_reminder);
        ImageView imageLocation = (ImageView)view.findViewById(R.id.ic_location);
        ImageView imageShare = (ImageView)view.findViewById(R.id.ic_share);

        imageFav.setImageResource(R.drawable.ic_favunsel);
        imageInfo.setImageResource(R.drawable.ic_info);
        imageReminder.setImageResource(R.drawable.ic_reminder);
        imageLocation.setImageResource(R.drawable.ic_location);
        imageShare.setImageResource(R.drawable.ic_share);

        sessionNameView.setText(sessionName);
        sessionDateTimeView.setText(sessionDate + ", " + sessionTime);
        sessionLocationView.setText(sessionLocation);
        sessionForView.setText(sessionFor);
        Log.d(MainActivity.DEBUGTAG, "Setting text and icons");

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ImageView iv = (ImageView)v;
                iv.setBackgroundColor(Color.parseColor("#FFBB33"));
                iv.setImageResource(R.drawable.ic_locationsel);
                iv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                iv.setImageResource(R.drawable.ic_location);*/
                Intent i = new Intent(context, MapActivity.class);
                context.startActivity(i);
                Log.d(MainActivity.DEBUGTAG, "Clicked Map on " + sessionName);
            }
        });

        return view;
    }
}
