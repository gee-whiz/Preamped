package preamped.empirestate.co.za.preamped.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import preamped.empirestate.co.za.preamped.R;
import preamped.empirestate.co.za.preamped.loadshedding.AffectedMarkers;
import preamped.empirestate.co.za.preamped.loadshedding.MapsFragment;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;
import preamped.empirestate.co.za.preamped.loadshedding.Stage;

/**
 * Created by users em on 2015-04-21.
 */
public  class LocationCursorAdapter extends CursorAdapter {
    private static final String LOG = "";

    public LocationCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);

    }

private  Context ctx;
        @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_location, parent, false);




    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageButton tvImage = (ImageButton)view.findViewById(R.id.imgHome);
        TextView tvBody = (TextView) view.findViewById(R.id.txtLoca);
        TextView tvStage = (TextView)view.findViewById(R.id.txtStage);
        TextView tvTime = (TextView)view.findViewById(R.id.txtTimeEnd);

       //String times = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
        String body = cursor.getString(cursor.getColumnIndexOrThrow("locationName"));
        if(body.equalsIgnoreCase("Home")){
            tvImage.setImageResource(R.drawable.ic_home_white_24dp);
        }
      else  if(body.equalsIgnoreCase("Work")){
            tvImage.setImageResource(R.drawable.ic_work_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Shopping Mall")){
            tvImage.setImageResource(R.drawable.ic_shopping_basket_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Mall")){
            tvImage.setImageResource(R.drawable.ic_add_shopping_cart_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Hospital")){
            tvImage.setImageResource(R.drawable.ic_home_white_24dp);
        }
        else  if(body.equalsIgnoreCase("School")){
            tvImage.setImageResource(R.drawable.ic_home_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Mum`s Place")){
            tvImage.setImageResource(R.drawable.ic_home_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Chinese Restaurant")){
            tvImage.setImageResource(R.drawable.ic_translate_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Chinese Shop")){
            tvImage.setImageResource(R.drawable.ic_translate_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Grandmother`s House")){
            tvImage.setImageResource(R.drawable.ic_home_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Coffee Shop")){
            tvImage.setImageResource(R.drawable.ic_store_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Cafe")){
            tvImage.setImageResource(R.drawable.ic_store_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Spazza Shop")){
            tvImage.setImageResource(R.drawable.ic_store_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Girlfriend`s Place")){
            tvImage.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Office")){
            tvImage.setImageResource(R.drawable.ic_work_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Empire Sate")){
            tvImage.setImageResource(R.drawable.ic_work_white_24dp);
        }
        else  if(body.equalsIgnoreCase("Store")){
            tvImage.setImageResource(R.drawable.ic_store_white_24dp);
        }
           else
        {
            tvImage.setImageResource(R.drawable.ic_grade_white_24dp);
        }
        //tvTime.setText(times);
        tvBody.setText(body);

    }


}
