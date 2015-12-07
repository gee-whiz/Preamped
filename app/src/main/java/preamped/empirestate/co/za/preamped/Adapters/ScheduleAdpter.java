package preamped.empirestate.co.za.preamped.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import preamped.empirestate.co.za.preamped.R;
import preamped.empirestate.co.za.preamped.Schedules;
import preamped.empirestate.co.za.preamped.loadshedding.Scheduless;

/**
 * Created by es_air13_1 on 15/06/23.
 */
public class ScheduleAdpter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Scheduless>  schedulesList;
    private Context ctx;
    public ScheduleAdpter(Activity activity, List<Scheduless> schedulesList) {
        this.activity = activity;
        this.schedulesList = schedulesList;
    }

    @Override
    public int getCount() {
        return schedulesList.size();
    }

    @Override
    public Object getItem(int i) {
        return schedulesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.single_schedule, null);


        TextView stage =  (TextView)convertView.findViewById(R.id.txtStage1);
        TextView start = (TextView)convertView.findViewById(R.id.txtTime);

       // TextView group = (TextView)convertView.findViewById(R.id.txtGroups);
        TextView date = (TextView)convertView.findViewById(R.id.txtDates);
        Scheduless s = schedulesList.get(i);


        stage.setText(s.getStage());
        start.setText(s.getStart()+" - "+s.getEnd());

        //group.setText(s.getGroup());
        date.setText(s.getDate());
        //animateView(convertView);


        return convertView;
    }


    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(activity, R.anim.abc_slide_in_bottom);
        a.setDuration(1000);
        if (view == null)
            return;
        view.startAnimation(a);
    }
}
