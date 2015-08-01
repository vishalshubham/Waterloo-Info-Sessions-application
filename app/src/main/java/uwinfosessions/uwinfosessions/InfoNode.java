package uwinfosessions.uwinfosessions;

import android.widget.ImageView;

/**
 * Created by Vishal on 21/07/2015.
 */
public class InfoNode {
    private int sessionId;
    private String sessionName;
    private String sessionDate;
    private String sessionTime;
    private String sessionLocation;
    private String sessionFor;

    public InfoNode(int sessionId, String sessionName, String sessionDate, String sessionTime, String sessionLocation, String sessionFor) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.sessionLocation = sessionLocation;
        this.sessionFor = sessionFor;
    }

    public String getSessionName() {
        return sessionName;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int id) {
        this.sessionId = id;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getSessionLocation() {
        return sessionLocation;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public String getSessionFor() {
        return sessionFor;
    }

    public void setSessionFor(String sessionFor) {
        this.sessionFor = sessionFor;
    }
}
