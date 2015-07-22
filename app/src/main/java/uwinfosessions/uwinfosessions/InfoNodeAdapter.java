package uwinfosessions.uwinfosessions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
