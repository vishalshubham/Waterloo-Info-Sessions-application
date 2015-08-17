package uwinfosessions.uwinfosessions.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.InfoNode;
import uwinfosessions.uwinfosessions.adapter.InfoNodeAdapter;

/**
 * Created by Vishal on 07/08/2015.
 */
public class FragmentToday extends Fragment {

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
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
                TextView textNotify = (TextView)getActivity().findViewById(R.id.text_notify_today);
                if(infoNodeAdapter.getCount()<=0){
                    textNotify.setText("No sessions scheduled for today.\n OR \n Internet connection is unavailable.");
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

    public String getToday(){
        String date = (new Date()).toString();
        String day = date.substring(8, 10);
        String month = date.substring(4, 7);
        String year = date.substring(24);
        return (day + "-" + month + "-" + year);
    }

    public List<InfoNode> downloadPage(){
        try{
            ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();
            String date = getToday();
            String day_num = date.substring(0,2);
            String month_char = date.substring(3,6);
            //String month_char = "Sep";
            String year_num = date.substring(7);
            URL oracle;
            Log.d(InfoSessionListActivity.DEBUGTAG, "Date: " + day_num + month_char + year_num);
            String month_num = getMonthNum(month_char);
            oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=" + month_num + "&year_num=" + year_num);

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
                String check_date = getMonthFullName(month_char) + " " + day_num + ", "  + year_num;
                //String check_date = "September" + " " + day_num + ", "  + year_num;
                Log.d(DEBUGTAG, "Check Date------------------" + check_date);
                while(wholeLine.indexOf("<p><a href=\"sessions_details.php?id=")>=0){
                    String line = wholeLine.substring(wholeLine.indexOf("<p><a href=\"sessions_details.php?id="), wholeLine.indexOf("</a></p>") + 8);
                    wholeLine = wholeLine.substring(wholeLine.indexOf("</a></p>") + 8);
                    System.out.println(line);
                    InfoNode infoNode = getInfoNode(++id, line);
                    Log.d(DEBUGTAG, "11111111111111" + check_date);
                    Log.d(DEBUGTAG, "22222222222222" + infoNode.getSessionDate());
                    if(!infoNode.getSessionName().contains("No info sessions") && !infoNode.getSessionName().contains("New Year") && (infoNode.getSessionDate().indexOf(check_date)!=-1)) {
                        infoNodeList.add(infoNode);
                    }
                    else{
                        id--;
                    }
                }
                return infoNodeList;
            }
            else{
                Log.d(InfoSessionListActivity.DEBUGTAG, "No Records exist");
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMonthFullName(String month){
        if(month.equals("Jan")){
            return "January";
        }
        else if (month.equals("Feb")){
            return "February";
        }
        else if (month.equals("Mar")){
            return "March";
        }
        else if (month.equals("Apr")){
            return "April";
        }
        else if (month.equals("May")){
            return "May";
        }
        else if (month.equals("Jun")){
            return "June";
        }
        else if (month.equals("Jul")){
            return "July";
        }
        else if (month.equals("Aug")){
            return "August";
        }
        else if (month.equals("Sep")){
            return "September";
        }
        else if (month.equals("Oct")){
            return "October";
        }
        else if (month.equals("Nov")){
            return "November";
        }
        else if (month.equals("Dec")){
            return "December";
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
