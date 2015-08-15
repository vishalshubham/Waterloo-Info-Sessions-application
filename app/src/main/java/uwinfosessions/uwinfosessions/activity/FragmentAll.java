package uwinfosessions.uwinfosessions.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.Database;
import uwinfosessions.uwinfosessions.adapter.InfoNode;
import uwinfosessions.uwinfosessions.adapter.InfoNodeAdapter;

/**
 * Created by Vishal on 07/08/2015.
 */
public class FragmentAll extends Fragment {

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        setInfoNodeListner(view);
        return view;
    }

    public void setInfoNodeListner(View view){
        final ListView list_main = (ListView)view.findViewById(R.id.main_list);
        final InfoNodeAdapter infoNodeAdapter = new InfoNodeAdapter(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.wait);
        builder.setMessage(R.string.getting_data);
        final AlertDialog dialog = builder.create();
        dialog.show();

        new AsyncTask<Void, Void, Void>(){
            protected Void doInBackground(Void... params) {
                ArrayList<InfoNode> infoNodeList = (ArrayList<InfoNode>)downloadPage();
                infoNodeAdapter.setInfoNodes(infoNodeList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                TextView textNotify = (TextView)getActivity().findViewById(R.id.text_notify_all);
                if(infoNodeAdapter.getCount()<=0){
                    textNotify.setText("Sorry! No sessions scheduled in this month.");
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
                Log.d(MainActivity.DEBUGTAG, "Position: " + position + "; Value: " + infoNodeAdapter.getSessionLine(position) + ";");
            }
        });
    }

    public List<InfoNode> downloadPage(){
        try{
            ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();
            SharedPreferences prefs = getActivity().getSharedPreferences(InfoSessionListActivity.CURR_DATE, 0);
            String currDate = prefs.getString(InfoSessionListActivity.CURR_DATE, "");
            String month_num = "";
            String year_num = "";
            URL oracle;
            if(currDate.equals("")){
                month_num = new Date().toString();
                year_num = new Date().toString();
                Log.d(InfoSessionListActivity.DEBUGTAG, "Month:: and Year " + month_num + year_num);
                oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php");
            }
            else{
                month_num = currDate.substring(0,3);
                year_num = currDate.substring(4);
                Log.d(InfoSessionListActivity.DEBUGTAG, "Month: and Year " + month_num + year_num);
                month_num = getMonthNum(month_num);
                oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=" + month_num + "&year_num=" + year_num);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String wholeLine="";
            while ((inputLine = in.readLine()) != null)
                wholeLine = wholeLine + inputLine;

            Log.d(DEBUGTAG, wholeLine);

            if(wholeLine.indexOf("<b>Employer</b>")>=0){
                int start = wholeLine.indexOf("<p><a href=\"sessions_details.php?id=");
                wholeLine = wholeLine.substring(start);

                int id=0;
                while(wholeLine.indexOf("<p><a href=\"sessions_details.php?id=")>=0){
                    String line = wholeLine.substring(wholeLine.indexOf("<p><a href=\"sessions_details.php?id="), wholeLine.indexOf("</a></p>")+8);
                    wholeLine = wholeLine.substring(wholeLine.indexOf("</a></p>") + 8);
                    System.out.println(line);
                    InfoNode infoNode = getInfoNode(++id, line);
                    if(!infoNode.getSessionName().contains("No info sessions") && !infoNode.getSessionName().contains("New Year")) {
                        infoNodeList.add(infoNode);
                    }
                    else{
                        id--;
                    }
                }
                return infoNodeList;
            }
            else{
                Log.d(MainActivity.DEBUGTAG, "No Records exist");
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMonthNum(String month){
        if(month.equals("Jan")){
            return "01";
        }
        else if (month.equals("Feb")){
            return "02";
        }
        else if (month.equals("Mar")){
            return "03";
        }
        else if (month.equals("Apr")){
            return "04";
        }
        else if (month.equals("May")){
            return "05";
        }
        else if (month.equals("Jun")){
            return "06";
        }
        else if (month.equals("Jul")){
            return "07";
        }
        else if (month.equals("Aug")){
            return "08";
        }
        else if (month.equals("Sep")){
            return "09";
        }
        else if (month.equals("Oct")){
            return "10";
        }
        else if (month.equals("Nov")){
            return "11";
        }
        else if (month.equals("Dec")){
            return "12";
        }
        return null;
    }

    public static InfoNode getInfoNode(int sessionId, String line){
        int sessionNameStart = line.indexOf("<b>Employer</b>:") + 17;
        int sessionNameEnd = line.indexOf("<br><b>Date</b>");
        int sessionDateStart = line.indexOf("<b>Date</b>:") + 13;
        int sessionDateEnd = line.indexOf("<br><b>Time</b>:");
        int sessionTimeStart = line.indexOf("<b>Time</b>:") + 13;
        int sessionTimeEnd = line.indexOf("<br><b>Location</b>:");
        int sessionLocationStart = line.indexOf("<b>Location</b>:") + 17;
        int sessionLocationEnd = line.indexOf("<br><b>Web Site:</b>");
        int sessionWebsiteStart = line.indexOf("<b>Web Site:</b>") + 14;
        int sessionWebsiteEnd = line.indexOf("<br><i>");
        int sessionForStart = line.indexOf("<i>") + 3;
        int sessionForEnd = line.indexOf("Students <br>");

        return new InfoNode(sessionId, line.substring(sessionNameStart, sessionNameEnd), line.substring(sessionDateStart, sessionDateEnd), line.substring(sessionTimeStart, sessionTimeEnd), line.substring(sessionLocationStart, sessionLocationEnd), line.substring(sessionForStart, sessionForEnd +9).replace("<br>",""), line);
    }
}
