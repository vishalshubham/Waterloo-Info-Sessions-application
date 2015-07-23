package uwinfosessions.uwinfosessions;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    public static String DEBUGTAG="VC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInfoNodeListListner();
    }

    public void setInfoNodeListListner(){
        Log.d(DEBUGTAG,"OMG");
        ArrayList<InfoNode> infoNodeList = (ArrayList<InfoNode>)PageParser.parsePage();

       /* for(int i=1; i<=infoNodeList.size(); i++){
            Log.d(DEBUGTAG,infoNodeList.get(i).getSessionName());
        }*/

        InfoNodeAdapter infoNodeAdapter = new InfoNodeAdapter(this, infoNodeList);

        ListView list_main = (ListView)findViewById(R.id.main_list);
        list_main.setAdapter(infoNodeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
