package uwinfosessions.uwinfosessions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by Vishal on 03/08/2015.
 */
public class InfoSessionAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private InfoNode infoNode;

    public InfoSessionAdapter(Context context, InfoNode infoNode) {
        this.context = context;
        this.infoNode = infoNode;
    }

    public InfoNode getInfoNode() {
        return infoNode;
    }

    public void setInfoNode(InfoNode infoNode) {
        this.infoNode = infoNode;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_info_node, null);

        TextView textInfoSessionName = (TextView)view.findViewById(R.id.text_info_session_name);
        TextView textInfoSessionDate = (TextView)view.findViewById(R.id.text_info_session_date);
        TextView textInfoSessionTime = (TextView)view.findViewById(R.id.text_info_session_time);
        TextView textInfoSessionLocation = (TextView)view.findViewById(R.id.text_info_session_location);
        TextView textInfoSessionWebsite = (TextView)view.findViewById(R.id.text_info_session_website);
        TextView textInfoSessionDetails = (TextView)view.findViewById(R.id.text_info_session_details);

        textInfoSessionName.setText(infoNode.getSessionName());
        textInfoSessionDate.setText(infoNode.getSessionDate());
        textInfoSessionTime.setText(infoNode.getSessionTime());
        textInfoSessionLocation.setText(infoNode.getSessionLocation());
        textInfoSessionWebsite.setText("www.google.com/careers");
        textInfoSessionDetails.setText(infoNode.getSessionFor());

        return null;
    }
}
