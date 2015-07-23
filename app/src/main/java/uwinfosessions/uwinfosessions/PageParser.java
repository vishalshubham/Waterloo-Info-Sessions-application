package uwinfosessions.uwinfosessions;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Vishal on 21/07/2015.
 */
public class PageParser {

    private static ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();

    public static List<InfoNode> parsePage(){
        ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();
        for(int i=1; i<=10; i++){
            infoNodeList.add(new InfoNode(i, "Amazon", "21-Jul-2015", "5.00PM", "DC1301", "For Coop and graduating"));
        }

        try{
            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=1&year_num=2015");
//            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return infoNodeList;
    }

    /*public static List<InfoNode> parsePage() {
        try {
            *//*Scanner input = new Scanner(System.in);
            System.out.println("Enter month: ");
            int month = input.nextInt();

            System.out.println("Enter year: ");
            int year = input.nextInt();

            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=" + month + "&year_num=" + year);*//*
            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=1&year_num=2015");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String wholeLine=null;
            while ((inputLine = in.readLine()) != null)
                wholeLine = wholeLine + inputLine;
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
                *//*Log.d(MainActivity.DEBUGTAG, "No Records exist");*//*
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

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
        int sessionForEnd = line.indexOf("</i>");

        InfoNode infoNode = new InfoNode(sessionId, line.substring(sessionNameStart, sessionNameEnd), line.substring(sessionDateStart, sessionDateEnd), line.substring(sessionTimeStart, sessionTimeEnd), line.substring(sessionLocationStart, sessionLocationEnd), line.substring(sessionForStart, sessionForEnd));
        return infoNode;
    }

//    public static List<InfoNode> getList(){
//        return parsePage();
//    }
}
/*

*/
/**
 * Created by Vishal on 21/07/2015.
 *//*

public class PageParser {

    private static ArrayList<InfoNode> infoNodeList = new ArrayList<InfoNode>();

    public static ArrayList<InfoNode> parsePage() {
        try {
            */
/*Scanner input = new Scanner(System.in);
            System.out.println("Enter month: ");
            int month = input.nextInt();

            System.out.println("Enter year: ");
            int year = input.nextInt();

            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=" + month + "&year_num=" + year);*//*

            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=1&year_num=2015");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String wholeLine=null;
            while ((inputLine = in.readLine()) != null)
                wholeLine = wholeLine + inputLine;
            if(wholeLine.indexOf("<b>Employer</b>")>=0){
                int start = wholeLine.indexOf("<p><a href=\"sessions_details.php?id=");
                wholeLine = wholeLine.substring(start);

                int id=0;
                while(wholeLine.indexOf("<p><a href=\"sessions_details.php?id=")>=0){
                    String line = wholeLine.substring(wholeLine.indexOf("<p><a href=\"sessions_details.php?id="), wholeLine.indexOf("</a></p>")+8);
                    wholeLine = wholeLine.substring(wholeLine.indexOf("</a></p>") + 8);
                    Log.d(MainActivity.DEBUGTAG, line);
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
        int sessionForEnd = line.indexOf("</i>");

        InfoNode infoNode = new InfoNode(sessionId, line.substring(sessionNameStart, sessionNameEnd), line.substring(sessionDateStart, sessionDateEnd), line.substring(sessionTimeStart, sessionTimeEnd), line.substring(sessionLocationStart, sessionLocationEnd), line.substring(sessionForStart, sessionForEnd));
        return infoNode;
    }

    public static List<InfoNode> getList(){
        return parsePage();
    }
}*/
