package uwinfosessions.uwinfosessions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_info_node, null);

        InfoNode infoNode = infoNodes.get(position);
        String sessionName = infoNode.getSessionName();
        String sessionDate = infoNode.getSessionDate();
        String sessionTime = infoNode.getSessionTime();
        String sessionLocation = infoNode.getSessionLocation();
        String sessionFor = infoNode.getSessionFor();

        TextView sessionNameView = (TextView)view.findViewById(R.id.text_session_name);
        TextView sessionDateTimeView = (TextView)view.findViewById(R.id.text_session_date_time);
        TextView sessionLocationView = (TextView)view.findViewById(R.id.text_session_location);
        TextView sessionForView = (TextView)view.findViewById(R.id.text_session_for);

        sessionNameView.setText(sessionName);
        sessionDateTimeView.setText(sessionDate + ", " + sessionTime);
        sessionLocationView.setText(sessionLocation);
        sessionForView.setText(sessionFor);
        return view;
    }
}
