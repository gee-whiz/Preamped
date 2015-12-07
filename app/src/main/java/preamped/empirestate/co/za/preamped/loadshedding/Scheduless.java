package preamped.empirestate.co.za.preamped.loadshedding;

/**
 * Created by George Kapoya on 15/06/23.
 */
public class Scheduless {


    private  String date;
    private String stage;
    private String start;
    private String end;
    private  String group;

    public Scheduless(String date, String stage, String start, String end, String group) {
        this.date = date;
        this.stage = stage;
        this.start = start;
        this.end = end;
        this.group = group;
    }

    public Scheduless() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
