package uwinfosessions.uwinfosessions;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

//    public InfoNodeAdapter infoNodeAdapter
    public static String DEBUGTAG="VC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInfoNodeListListner();
    }

    public void setInfoNodeListListner(){
        Log.d(DEBUGTAG, "OMG");

        final InfoNodeAdapter infoNodeAdapter = new InfoNodeAdapter(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.getting_data);

        AlertDialog dialog = builder.create();
        dialog.show();

        new AsyncTask<Void, Void, Void>(){
            protected Void doInBackground(Void... params) {
                Log.d(DEBUGTAG, "OMG1");
                ArrayList<InfoNode> infoNodeList = (ArrayList<InfoNode>)downloadPage();
                infoNodeAdapter.setInfoNodes(infoNodeList);
                Log.d(DEBUGTAG, "Adapter size: " + infoNodeAdapter.getCount());
                return null;
            }
        }.execute();
        try {
            Thread.sleep(1000);
            dialog.dismiss();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(DEBUGTAG, "Adapter sizzzzzzzze: ");
        ListView list_main = (ListView)findViewById(R.id.main_list);
        Log.d(DEBUGTAG, "Adapter sizze: "+infoNodeAdapter.getCount());
        list_main.setAdapter(infoNodeAdapter);
    }

    public List<InfoNode> downloadPage(){
        try{
            Log.d(DEBUGTAG, "OMG2");
            ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();
            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=1&year_num=2015");
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
                    infoNodeList.add(getInfoNode(++id, line));
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
//        int sessionForEnd = line.indexOf("</i>");

        InfoNode infoNode = new InfoNode(sessionId, line.substring(sessionNameStart, sessionNameEnd), line.substring(sessionDateStart, sessionDateEnd), line.substring(sessionTimeStart, sessionTimeEnd), line.substring(sessionLocationStart, sessionLocationEnd), line.substring(sessionForStart, sessionForEnd +9).replace("<br>",""));
        return infoNode;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(DEBUGTAG,"Menu inflated");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
