package preamped.empirestate.co.za.preamped.loadshedding;

/**
 * Created by George Kapoya on 2015-05-28.
 */
public class Stage {

    private  String stage;
    private  String times;

    public Stage() {
    }

    public Stage(String stage, String times) {
        this.stage = stage;
        this.times = times;
    }


    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
