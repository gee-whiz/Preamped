package preamped.empirestate.co.za.preamped.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import preamped.empirestate.co.za.preamped.R;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;
import preamped.empirestate.co.za.preamped.loadshedding.SavedLocations;

/**
 * Created by George Kapoya on 2015-04-20.
 */
public class LocationAdapter extends ArrayAdapter<SavedLocations>{
    private final LayoutInflater mInflater;
    private  List<SavedLocations> mList;
    private Context ctx;
    private final int mLayoutRes;

    public LocationAdapter(Context context,int textViewResourceId, List<SavedLocations> list) {
       super(context, textViewResourceId,list);
        mList = list;
        ctx = context;
       this.mLayoutRes = textViewResourceId;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    View view;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem v;

            convertView = mInflater.inflate(mLayoutRes, null);
            v = new ViewHolderItem();
            v.txtLocation = (TextView) convertView.findViewById(R.id.txtLoca);


            final MyDBHandler dbHandler = new MyDBHandler(ctx);
            final List<SavedLocations> savedLocations = dbHandler.getAllLocations();
            if (savedLocations != null) {

                for (SavedLocations s : savedLocations) {
                    s.addAll(savedLocations);
                    s = mList.get(position);
                    v.txtLocation.setText((s.locName));
                }

        }
            animateView(convertView);
            return (convertView);

    }




    public void animateView(View convertView) {
        Animation a = AnimationUtils.loadAnimation(ctx,
                R.anim.grow_fade_in_center);
        a.setDuration(1000);
        if (view == null)
            return;
        view.startAnimation(a);
    }
    static class ViewHolderItem {
        TextView txtLocation;
    }

}
