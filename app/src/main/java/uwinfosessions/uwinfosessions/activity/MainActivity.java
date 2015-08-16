package uwinfosessions.uwinfosessions.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.InfoNode;
import uwinfosessions.uwinfosessions.adapter.InfoNodeAdapter;


public class MainActivity extends Activity{

    public static String DEBUGTAG = "VC";
    public static String WHOLE_LINE = "whole_line";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DEBUGTAG, "Error checking internet connection-----");
        if(hasActiveInternetConnection(this)){
            setContentView(R.layout.activity_main);
            setInfoNodeListner();
        }
        else{
            //
        }
    }

    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                Toast.makeText(context, "Connected to the server", Toast.LENGTH_LONG).show();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.d(DEBUGTAG, "Error checking internet connection");
                Toast.makeText(context, "Sorry but there is some problem accessing Internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(DEBUGTAG, "No network available!");
            Toast.makeText(context, "Sorry but you don't have Internet Access", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void setInfoNodeListner(){
        final ListView list_main = (ListView)findViewById(R.id.main_list);
        final InfoNodeAdapter infoNodeAdapter = new InfoNodeAdapter(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                list_main.setAdapter(infoNodeAdapter);
                list_main.setItemsCanFocus(true);
                dialog.dismiss();
            }
        }.execute();

        list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Log.d(DEBUGTAG, view.toString());
                Intent i = new Intent(MainActivity.this, InfoSessionActivity.class);
                i.putExtra(WHOLE_LINE, infoNodeAdapter.getSessionLine(position));
                startActivity(i);
                Log.d(MainActivity.DEBUGTAG, "Position: " + position + "; Value: " + infoNodeAdapter.getSessionLine(position) + ";");
            }
        });
    }

    public List<InfoNode> downloadPage(){
        try{
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
