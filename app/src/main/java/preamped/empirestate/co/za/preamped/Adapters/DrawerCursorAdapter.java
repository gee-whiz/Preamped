package preamped.empirestate.co.za.preamped.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import preamped.empirestate.co.za.preamped.R;

/**
 * Created by users em on 2015-04-26.
 */
public class DrawerCursorAdapter extends CursorAdapter {
    public DrawerCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);

    }
    private  Context ctx;

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_drawer, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvBody = (TextView) view.findViewById(R.id.txtDrawerLocation);

        String body = cursor.getString(cursor.getColumnIndexOrThrow("locationName"));

        tvBody.setText(body);

    }


}

