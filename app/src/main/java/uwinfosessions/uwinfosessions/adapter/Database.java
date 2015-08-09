package uwinfosessions.uwinfosessions.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uwinfosessions.uwinfosessions.activity.InfoSessionListActivity;

/**
 * Created by Vishal on 08/08/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final String FAV_TABLE  = "DB_FAV";
    private static final String REM_TABLE  = "DB_REM";
    private static final String COL_S_NAME = "S_NAME";
    private static final String COL_S_DATE = "S_DATE";
    private static final String COL_S_TIME = "S_TIME";
    private static final String COL_S_LOCN = "S_LOCN";
    private static final String COL_S_FOR = "S_FOR";
    private static final String COL_S_LINE = "S_LINE";

    public Database(Context context) {
        super(context, "session.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s VARCHAR PRIMARY KEY, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL)", FAV_TABLE, COL_S_NAME, COL_S_DATE, COL_S_TIME, COL_S_LOCN, COL_S_FOR, COL_S_LINE);
        db.execSQL(sql);
        sql = String.format("create table %s (%s VARCHAR PRIMARY KEY, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL, %s VARCHAR NOT NULL)", REM_TABLE, COL_S_NAME, COL_S_DATE, COL_S_TIME, COL_S_LOCN, COL_S_FOR, COL_S_LINE);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeFavSession(InfoNode infoNode){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d(InfoSessionListActivity.DEBUGTAG, "Favourite Session Saved: " + infoNode.getSessionName() + " - " + infoNode.getSessionDate() + " - " + infoNode.getSessionTime() + " - " + infoNode.getSessionLocation());
        values.put(COL_S_NAME, infoNode.getSessionName());
        values.put(COL_S_DATE, infoNode.getSessionDate());
        values.put(COL_S_TIME, infoNode.getSessionTime());
        values.put(COL_S_LOCN, infoNode.getSessionLocation());
        values.put(COL_S_FOR, infoNode.getSessionFor());
        values.put(COL_S_LINE, infoNode.getSessionLine());

        db.insert(FAV_TABLE, null, values);
        db.close();
    }

    public void storeRemSession(InfoNode infoNode){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d(InfoSessionListActivity.DEBUGTAG, "Reminder Session Saved: " + infoNode.getSessionName() + " - " + infoNode.getSessionDate() + " - " + infoNode.getSessionTime() + " - " + infoNode.getSessionLocation());
        values.put(COL_S_NAME, infoNode.getSessionName());
        values.put(COL_S_DATE, infoNode.getSessionDate());
        values.put(COL_S_TIME, infoNode.getSessionTime());
        values.put(COL_S_LOCN, infoNode.getSessionLocation());
        values.put(COL_S_FOR, infoNode.getSessionFor());
        values.put(COL_S_LINE, infoNode.getSessionLine());

        db.insert(REM_TABLE, null, values);
        db.close();
    }

    public void removeFavSession(InfoNode infoNode){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d(InfoSessionListActivity.DEBUGTAG, "Favourite Session Saved: " + infoNode.getSessionName() + " - " + infoNode.getSessionDate() + " - " + infoNode.getSessionTime() + " - " + infoNode.getSessionLocation());
        values.put(COL_S_NAME, infoNode.getSessionName());
        values.put(COL_S_DATE, infoNode.getSessionDate());
        values.put(COL_S_TIME, infoNode.getSessionTime());
        values.put(COL_S_LOCN, infoNode.getSessionLocation());

        db.insert(FAV_TABLE, null, values);
        db.close();
    }

    public void removeRemSession(InfoNode infoNode){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d(InfoSessionListActivity.DEBUGTAG, "Reminder Session Saved: " + infoNode.getSessionName() + " - " + infoNode.getSessionDate() + " - " + infoNode.getSessionTime() + " - " + infoNode.getSessionLocation());
        values.put(COL_S_NAME, infoNode.getSessionName());
        values.put(COL_S_DATE, infoNode.getSessionDate());
        values.put(COL_S_TIME, infoNode.getSessionTime());
        values.put(COL_S_LOCN, infoNode.getSessionLocation());

        db.insert(REM_TABLE, null, values);
        db.close();
    }

    public List<InfoNode> getFavSession(){

        SQLiteDatabase db = getWritableDatabase();
        ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();

        String sql = String.format("select * from %s order by %s", FAV_TABLE, COL_S_NAME);
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            String time = cursor.getString(2);
            String locn = cursor.getString(3);
            String forr = cursor.getString(4);
            String line = cursor.getString(5);

            infoNodeList.add(new InfoNode(name, date, time, locn, forr, line));
        }
        db.close();
        return infoNodeList;
    }

    public List<InfoNode> getRemSession(){

        SQLiteDatabase db = getWritableDatabase();
        ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();

        String sql = String.format("select * from %s order by %s", REM_TABLE, COL_S_NAME);
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            String time = cursor.getString(2);
            String locn = cursor.getString(3);
            String forr = cursor.getString(4);
            String line = cursor.getString(5);

            infoNodeList.add(new InfoNode(name, date, time, locn, forr, line));
        }
        db.close();
        return infoNodeList;
    }
}
