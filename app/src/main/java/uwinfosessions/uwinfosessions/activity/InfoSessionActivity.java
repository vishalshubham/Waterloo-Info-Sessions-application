package uwinfosessions.uwinfosessions.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import uwinfosessions.uwinfosessions.R;
import uwinfosessions.uwinfosessions.adapter.InfoNode;


public class InfoSessionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_session);
        String line = getIntent().getStringExtra(InfoSessionListActivity.WHOLE_LINE);
        /*TextView wholeLineTextView = (TextView)findViewById(R.id.text_whole_line);
        wholeLineTextView.setText(line);*/
        InfoNode infoNode = getInfoNode(line);

        setInfoSessionListner(infoNode);
    }

    public void setInfoSessionListner(InfoNode infoNode){

        TextView textInfoSessionName = (TextView)findViewById(R.id.text_info_session_name);
        TextView textInfoSessionDate = (TextView)findViewById(R.id.text_info_session_date);
        TextView textInfoSessionTime = (TextView)findViewById(R.id.text_info_session_time);
        TextView textInfoSessionLocation = (TextView)findViewById(R.id.text_info_session_location);
        TextView textInfoSessionWebsite = (TextView)findViewById(R.id.text_info_session_website);
        TextView textInfoSessionDetails = (TextView)findViewById(R.id.text_info_session_details);

        ImageView imgCoop = (ImageView)findViewById(R.id.img_coop_yes_no);
        ImageView imgGraduate = (ImageView)findViewById(R.id.img_graduate_yes_no);

        textInfoSessionName.setText(infoNode.getSessionName());
        textInfoSessionDate.setText(infoNode.getSessionDate());
        textInfoSessionTime.setText(infoNode.getSessionTime());
        textInfoSessionLocation.setText(infoNode.getSessionLocation());
        textInfoSessionWebsite.setText("Website goes here");
        textInfoSessionDetails.setText(infoNode.getSessionFor() + getIntent().getStringExtra(InfoSessionListActivity.WHOLE_LINE));

        boolean flag1=true;
        boolean flag2=true;
        Log.d(InfoSessionListActivity.DEBUGTAG, "For: " + infoNode.getSessionFor());
        if(infoNode.getSessionFor().indexOf("Co-op")!=-1){
            imgCoop.setImageResource(R.drawable.ic_yes);
            Log.d(InfoSessionListActivity.DEBUGTAG, "1:--------------------------- ");
        }
        else{
            imgCoop.setImageResource(R.drawable.ic_no);
            Log.d(InfoSessionListActivity.DEBUGTAG, "2:--------------------------- ");
            flag1=false;
        }

        if(infoNode.getSessionFor().indexOf("Graduat")!=-1) {
            imgGraduate.setImageResource(R.drawable.ic_yes);
            Log.d(InfoSessionListActivity.DEBUGTAG, "3:--------------------------- ");
        }
        else{
            imgGraduate.setImageResource(R.drawable.ic_no);
            Log.d(InfoSessionListActivity.DEBUGTAG, "4:--------------------------- ");
            flag2=false;
        }

        if(flag1==false && flag2 == false){
            imgCoop.setImageResource(R.drawable.ic_yes);
            imgGraduate.setImageResource(R.drawable.ic_yes);
            Log.d(InfoSessionListActivity.DEBUGTAG, "5:--------------------------- ");
        }



        textInfoSessionName.setShadowLayer(1.5f, -1, 1, Color.BLACK);
    }

    public static InfoNode getInfoNode(String line){
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

        return new InfoNode(line.substring(sessionNameStart, sessionNameEnd), line.substring(sessionDateStart, sessionDateEnd), line.substring(sessionTimeStart, sessionTimeEnd), line.substring(sessionLocationStart, sessionLocationEnd), line.substring(sessionForStart, sessionForEnd +9).replace("<br>",""), line);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_session, menu);
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
