package uwinfosessions.uwinfosessions;

/**
 * Created by Vishal on 21/07/2015.
 */
public class InfoNode {
    private int id;
    private String sessionName;
    private String sessionDate;
    private String sessionTime;
    private String sessionLocation;
    private String sessionFor;

    public String getSessionName() {
        return sessionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
