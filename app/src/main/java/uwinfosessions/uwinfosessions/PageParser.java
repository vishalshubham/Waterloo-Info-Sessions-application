package uwinfosessions.uwinfosessions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Vishal on 21/07/2015.
 */
public class PageParser {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter month: ");
            int month = input.nextInt();

            System.out.println("Enter year: ");
            int year = input.nextInt();

            URL oracle = new URL("http://www.ceca.uwaterloo.ca/students/sessions.php?month_num=" + month + "&year_num=" + year);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
